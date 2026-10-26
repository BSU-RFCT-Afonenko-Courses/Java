#!/usr/bin/env bash
# Публичные source/student inputs и declared inventory; частная qualification отдельно.
set -euo pipefail
umask 077
export PYTHONDONTWRITEBYTECODE=1
TASK_COURSE_ROOT=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)
: "${PL_PLATFORM_ROOT:?укажите точную release assembly вне курса}"
: "${PL_RUNTIME_REGISTRY:?укажите authenticated published-runtime-profiles.json}"
TASK_CHECK_OUTPUT=${CI_CHECK_OUTPUT:-$(mktemp -d -t java-project-checks-XXXXXXXX)}
mkdir -p -- "$TASK_CHECK_OUTPUT"
TASK_CHECK_OUTPUT=$(realpath -- "$TASK_CHECK_OUTPUT")
case "$TASK_CHECK_OUTPUT/" in "$TASK_COURSE_ROOT/"*) echo 'Checks output должен быть вне course source' >&2; exit 2;; esac
cd -- "$TASK_COURSE_ROOT"
python3 CI/check-production-pins.py
# Штатные public outputs остаются внутри disposable execution checkout.
timeout --signal=TERM --kill-after=5s 7200s quarto render . --profile student --fail-if-warnings
quarto run tasks/_extensions/Afonenko-Course-Tools/course-core/entrypoints/check.ts tasks student
# Следующий native pass удаляет course.json; сохранить проверенную модель приватно.
TASK_CHECK_MODEL=$(mktemp "${RUNNER_TEMP:-/tmp}/java-course-model-XXXXXXXX")
trap 'rm -f -- "$TASK_CHECK_MODEL"' EXIT
TASK_CHECK_MODEL_PATH=$(realpath -- "$TASK_CHECK_MODEL")
case "$TASK_CHECK_MODEL_PATH" in "$TASK_COURSE_ROOT/"*|"$TASK_CHECK_OUTPUT/"*) echo 'Model snapshot должен быть вне source/artifact output' >&2; exit 2;; esac
cp -- tasks/_generated/course-spec/course.json "$TASK_CHECK_MODEL"
# Stock student materialization is checked before changing this disposable tree.
python3 - "$TASK_CHECK_OUTPUT" "$TASK_CHECK_MODEL" <<'PY'
import json,sys,os
from pathlib import Path
import hashlib,stat,subprocess,tempfile,signal
def interrupted(number,frame):raise SystemExit(128+number)
for number in (signal.SIGINT,signal.SIGTERM):signal.signal(number,interrupted)
from pathlib import PurePosixPath
output=Path(sys.argv[1]);model=json.loads(Path(sys.argv[2]).read_text());owner=Path('tasks').resolve()
facts=[p for p in model.get('projects',[]) if p.get('check')]
ids=[p['exerciseId'] for p in facts]
if not ids or len(set(ids))!=len(ids) or any(p['statementVisibility']!='open' for p in facts):raise SystemExit('Некорректный набор открытых active IDs')
def local(root,name):
    parts=PurePosixPath(name).parts
    if not parts or name!='/'.join(parts) or any(v in ('..','.','') for v in parts) or name.startswith('/') or '\\' in name:raise SystemExit('Unsafe public common path')
    current=root
    for part in parts:
        current=current/part
        if current.is_symlink():raise SystemExit('Symlink public common path')
    return current
projects={p['exerciseId']:p['projectRoot'] for p in facts}
for root in projects.values():local(owner,root)
expected={}
for p in facts:
    for item in p.get('materialization',{}).get('commonFiles',[]):
        if item['audience']!='student':continue
        target=p['projectRoot']+'/'+item['target']
        if not item['target'].startswith('student/') or target in expected:raise SystemExit('Invalid public common declaration')
        expected[target]=str(local(owner,item['source']))
created=[];directories=[]
with tempfile.TemporaryDirectory(prefix='java-public-projects-',dir=Path(sys.argv[2]).parent) as temporary:
    staged=Path(temporary)/'materialized'
    command=['quarto','run','tasks/_extensions/Afonenko-Course-Tools/course-core/entrypoints/materialize-projects.ts','--','--root','tasks','--model','tasks/_generated/course-spec/native-run.json','--output-root',str(staged),'--audience','student']
    for exerciseId in ids:command+=['--task',exerciseId]
    subprocess.run(command+['--write'],check=True)
    subprocess.run(command+['--check'],check=True)
    receipt=json.loads((staged/'materialization.json').read_text())
    if receipt['schema']!='course-project-materialization-v1' or receipt['audience']!='student' or receipt['sourceRoot']!=str(owner) or receipt['exerciseIds']!=ids or receipt['projects']!=[{'exerciseId':p['exerciseId'],'projectRoot':p['projectRoot']} for p in facts]:raise SystemExit('Public materialization identity drift')
    common={};targets=set()
    for record in receipt['files']:
        target=record['target'];local(staged,target)
        if record['origin'] not in ('public','common') or target in targets or not any(target.startswith(root+'/student/') for root in projects.values()):raise SystemExit('Unexpected public materialization partition')
        targets.add(target)
        if record['origin']=='common':common[target]=record
    if set(common)!=set(expected):raise SystemExit('Public common set drift')
    payloads=[]
    for target,record in common.items():
        if record['source']!=expected[target]:raise SystemExit('Public common source drift')
        source=local(staged,target)
        if not stat.S_ISREG(source.lstat().st_mode):raise SystemExit('Nonregular public common file')
        data=source.read_bytes()
        if hashlib.sha256(data).hexdigest()!=record['sha256']:raise SystemExit('Public common SHA drift')
        payloads.append((target,data))
    try:
        for target,data in payloads:
            destination=local(owner,target);current=owner
            for part in destination.relative_to(owner).parts[:-1]:
                current=current/part
                if not current.exists():
                    current.mkdir(mode=0o700);directories.append((current,current.stat().st_ino))
                if current.is_symlink() or not current.is_dir():raise SystemExit('Unsafe public common parent')
            fd=os.open(destination,os.O_WRONLY|os.O_CREAT|os.O_EXCL|os.O_NOFOLLOW,0o600)
            created.append((destination,os.fstat(fd).st_ino))
            with os.fdopen(fd,'wb') as stream:stream.write(data)
        subprocess.run(['quarto','run','tasks/_extensions/Afonenko-Course-Tools/course-core/entrypoints/project-checks.ts','--','--book','tasks','--profile','ci-checks','--output',str(output/'declared-checks.json')],check=True)
        subprocess.run(['python3',str(Path(os.environ['PL_PLATFORM_ROOT'])/'tools/check-course.py'),'inventory','--manifest',str(output/'declared-checks.json'),'--scope','declared','--runtime-registry',os.environ['PL_RUNTIME_REGISTRY'],'--output',str(output/'inventory.json')],check=True)
        manifest=json.loads((output/'declared-checks.json').read_text());report=json.loads((output/'inventory.json').read_text())
        facts=[p for p in model.get('projects',[]) if p.get('check')];projects=manifest['projects'];ids=[p['exerciseId'] for p in projects]
        expected={p['exerciseId']:p for p in facts}
        if len(expected)!=len(facts) or len(set(ids))!=len(ids) or set(ids)!=set(expected):raise SystemExit('Core model и declared manifest имеют разные/повторные active IDs')
        for p in projects:
            fact=expected[p['exerciseId']]
            missing=p['readiness']['missing']
            if not p['sources'] or any(x in ('projectRoot','sources','verificationTests','studentApiPrecheck') for x in missing):raise SystemExit('Отсутствуют обязательные public source/student inputs: '+json.dumps({'exerciseId':p['exerciseId'],'sources':len(p['sources']),'missing':missing},ensure_ascii=False))
            if fact['check'].get('verification-tests') and not p['verificationTests']:raise SystemExit('Отсутствуют объявленные public verification tests')
            if p['check']!=fact['check'] or p['projectRoot']!=fact['projectRoot'] or p['qualifiedId']!=manifest['courseId']+'/'+p['exerciseId']:raise SystemExit('Изменены semantic check/root/identity')
            if any(r['available'] for r in p['references']) or p['contractFixtures'] or p.get('contractCasesHash'):raise SystemExit('Неожиданный закрытый reference/fixture partition в public checkout')
        seen={p['qualifiedId']:p for p in report['inventory']}
        if len(seen)!=len(report['inventory']) or set(seen)!={p['qualifiedId'] for p in projects}:raise SystemExit('Inventory потерял/добавил active IDs')
        for p in projects:
            item=seen[p['qualifiedId']]
            if item['runtime']!=p['check']['runtime'] or item['readiness']!=p['readiness']:raise SystemExit('Inventory изменил runtime/readiness')
        missing=sum(not p['readiness']['ready'] for p in projects)
        summary='Публичный source/student inventory: '+str(len(projects))+' active checks; отсутствующие closed inputs: '+str(missing)+'. private-input-not-provided / not-verified. Обязательная private qualification и manual/student UI остаются отдельно; inventory exit0 не является grading PASS.'
        print(summary)
        if os.environ.get('GITHUB_STEP_SUMMARY'):
            with open(os.environ['GITHUB_STEP_SUMMARY'],'a') as f:f.write(summary+'\n')
    finally:
        cleanupErrors=[]
        for path,inode in reversed(created):
            try:
                if path.is_symlink() or path.lstat().st_ino!=inode:raise RuntimeError('Owned public common file drift')
                path.unlink()
            except Exception as error:cleanupErrors.append(type(error).__name__)
        for path,inode in reversed(directories):
            try:
                if path.is_symlink() or path.lstat().st_ino!=inode:raise RuntimeError('Owned public common directory drift')
                path.rmdir()
            except Exception as error:cleanupErrors.append(type(error).__name__)
        if cleanupErrors:raise SystemExit('Public common cleanup refused: '+','.join(cleanupErrors))
PY
