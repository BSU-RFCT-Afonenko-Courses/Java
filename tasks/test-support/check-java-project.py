#!/usr/bin/env python3
"""Отдельная компиляция решения и обычная преподавательская Java/JUnit привязка.
Это локальный диагностический запуск существующего Platform, не новый student API.
"""
import argparse,hashlib,json,os,re,shutil,signal,subprocess,sys,tempfile,time
from pathlib import Path,PurePosixPath

IMAGE='sha256:75421e71603b933f160761762949d6907aa304f282f1ec0656d9784c82e22e5f'
def sha(path):return hashlib.sha256(path.read_bytes()).hexdigest()
def guarded(path):
    absolute=path.absolute()
    if any(p.is_symlink() for p in [absolute,*absolute.parents]):raise ValueError('symlink в пути запрещён: '+str(path))
    return absolute.resolve()
def inventory(root):
    root=guarded(root)
    if not root.is_dir():raise ValueError('нужен существующий каталог: '+str(root))
    files={}
    for path in sorted(root.rglob('*')):
        if path.is_symlink():raise ValueError('symlink в source inventory запрещён: '+str(path))
        if path.is_file() and path.suffix=='.java':
            name=path.relative_to(root).as_posix();parts=PurePosixPath(name).parts
            if len(name)>512 or any(p.startswith('.') or '\\' in p for p in parts):raise ValueError('небезопасный Java путь: '+name)
            if path.stat().st_size>1048576:raise ValueError('Java файл больше технического лимита 1MiB; требуется ручная поставка')
            files[name]=path
    return files

def main():
    parser=argparse.ArgumentParser(description=__doc__)
    for name in ['project','sources','binding','work','platform']:parser.add_argument('--'+name,type=Path,required=True)
    parser.add_argument('--api-config',type=Path)
    parser.add_argument('--public',action='store_true')
    parser.add_argument('--only',action='append',help='relative путь suite внутри tests/junit, например public/ExampleTest.java')
    parser.add_argument('--backend',choices=['host','container'],default='host')
    parser.add_argument('--image',default=IMAGE)
    args=parser.parse_args()
    try:
        project,sources,binding,work,platform=[guarded(getattr(args,n)) for n in ['project','sources','binding','work','platform']]
        if work.exists():raise ValueError('work уже существует; никакие файлы не удаляются')
        for protected in [project,sources,binding,platform,Path(__file__).resolve().parent]:
            if work==protected or work.is_relative_to(protected) or protected.is_relative_to(work):raise ValueError('work пересекается с source/project/binding/runtime/support')
        student=inventory(sources);bound=inventory(binding);alltests=inventory(project/'tests/junit')
        available={n:p for n,p in alltests.items() if n.startswith('public/') or not args.public and n.startswith('private/')}
        unknown=set(args.only or [])-set(available)
        if unknown:raise ValueError('неизвестные suites в выбранной области: '+', '.join(sorted(unknown)))
        selected={n:p for n,p in available.items() if not args.only or n in args.only}
        if not student or not bound or not selected:raise ValueError('пустая source/binding/suite выборка не является успешной проверкой; нужна ручная привязка')
        trusted={**{'binding/'+n:p for n,p in bound.items()},**{n:p for n,p in selected.items()},**{n:p for n,p in alltests.items() if n.startswith('support/')}}
        api=None;configpath=None
        if args.api_config:
            configpath=guarded(args.api_config);api=json.loads(configpath.read_text())
            if not isinstance(api,dict) or set(api)!={'files','entry'} or not isinstance(api['files'],list) or not api['files'] or len(api['files'])!=len(set(api['files'])):raise ValueError('нужен closed api config files/entry')
            if not isinstance(api['entry'],str) or not re.fullmatch(r'[A-Za-z_$][\w$]*(?:\.[A-Za-z_$][\w$]*)*',api['entry']):raise ValueError('неверный API entry')
            for name in api['files']:
                if not isinstance(name,str) or not name.endswith('.java') or '\\' in name or name!=PurePosixPath(name).as_posix() or PurePosixPath(name).is_absolute() or any(p.startswith('.') for p in PurePosixPath(name).parts):raise ValueError('небезопасный API путь')
                path=guarded(configpath.parent/name)
                if not path.is_relative_to(configpath.parent) or not path.is_file() or path.stat().st_size>1048576:raise ValueError('небезопасный/отсутствующий API файл')
                if name in trusted:raise ValueError('API path collision с trusted binding/suite')
                trusted[name]=path
        if len(student)>64 or len(trusted)>64:raise ValueError('Platform manifest поддерживает до 64 файлов в каждом списке; требуется ручная поставка, это не ошибка решения')
        runner=guarded(platform/'images/java25-grader/grade.py');registry=guarded(platform/'runtime-profiles.json')
        if not runner.is_file() or not registry.is_file():raise ValueError('не найден существующий Platform runtime')
        if args.backend=='container' and not re.fullmatch('sha256:[0-9a-f]{64}',args.image):raise ValueError('container image должен быть exact local config ID sha256')
        tracked=sorted(set([*student.values(),*trusted.values(),runner,registry,Path(__file__).resolve(),*([configpath] if configpath else [])]))
        before={str(p):sha(p) for p in tracked}
    except (OSError,ValueError,TypeError) as error:parser.error(str(error))
    work.mkdir(parents=True,exist_ok=False);job=work/'job';(job/'student').mkdir(parents=True);(job/'tests').mkdir()
    for folder,files in [('student',student),('tests',trusted)]:
        for name,path in files.items():
            target=job/folder/name;target.parent.mkdir(parents=True,exist_ok=True);target.write_bytes(path.read_bytes())
    cfg={'schemaVersion':1,'runtime':'java25-junit-v1','mode':'implementation','java':{'release':25,'encoding':'UTF-8','compiler-options':['-proc:none','-Xmaxerrs','5']},'limits':{'outer-seconds':60,'compile-seconds':15,'run-seconds':15,'max-output-bytes':65536,'networking':False},'scoring':{'mode':'all-pass'},'discovery':{'min-executed':1,'allow-skipped':False},'sourceFiles':list(student),'testFiles':list(trusted)}
    if api:cfg['apiPrecheck']=api
    (job/'tests/grading-job.json').write_text(json.dumps(cfg,ensure_ascii=False,indent=2)+'\n')
    argv=[sys.executable,str(runner),'--job-dir',str(job),'--verification'] if args.backend=='host' else ['docker','run','--rm','--pull','never','--network','none','--cpus','0.9','--memory','512m','--cidfile',str(work/'container.cid'),'--mount',f'type=bind,src={job},dst=/grade',args.image,'--verification']
    started=time.monotonic();code=None;reason=None;output='';process=None
    with tempfile.TemporaryDirectory(prefix='anonymous-docker-',dir=work) as dockerconfig:
        env={**os.environ,'PYTHONDONTWRITEBYTECODE':'1','DOCKER_CONFIG':dockerconfig}
        try:
            process=subprocess.Popen(argv,stdout=subprocess.PIPE,stderr=subprocess.STDOUT,start_new_session=True,env=env)
            try:raw,_=process.communicate(timeout=120);code=process.returncode;output=raw[:65536].decode('utf8','replace')
            except subprocess.TimeoutExpired:
                reason='orchestration-timeout';os.killpg(process.pid,signal.SIGKILL);process.communicate()
        except OSError as error:reason=str(error)
        finally:
            cid=work/'container.cid'
            if reason and args.backend=='container' and cid.is_file():subprocess.run(['docker','kill',cid.read_text().strip()],capture_output=True,timeout=15,env=env)
    (work/'runner.log').write_text(output)
    rawpath=job/'results/results.json'
    try:value=json.loads(rawpath.read_text()) if not reason else {}
    except (OSError,ValueError):value={}
    rawvalue=dict(value)
    if not value:value={'classification':'infrastructure-failure','gradable':False,'manualRequired':True,'message':reason or 'Platform не создал читаемый receipt'}
    if (value.get('classification')=='infrastructure-failure'
        and value.get('studentCompilation')=='success'
        and value.get('trustedCompilation')=='failure'
        and value.get('dependentExecution')=='not-run'):
        value.update(classification='binding-setup-failure',manualRequired=True,message='Преподавательская Java-привязка/зависимые тесты не скомпилировались. Требуется ручной разбор причины; ошибка решения не установлена.')
        value.pop('score',None)
    if value.get('classification')=='student-compilation-failure':value.setdefault('dependentExecution','not-run')
    after={str(p):sha(p) if p.is_file() else None for p in tracked}
    value.update(sourceHashesBefore=before,sourceHashesAfter=after,sourceUnchanged=before==after,backend=args.backend,imageConfigId=args.image if args.backend=='container' else None,argv=argv,exitCode=code,elapsedSeconds=time.monotonic()-started,rawPlatformResult=rawvalue,manualReviewRequired=['требуемая техника и исходник','объяснение','правильность привязки конкретного свободного API'],automaticAcceptanceOfArbitraryApi=False)
    if before!=after:value.update(classification='infrastructure-failure',gradable=False,manualRequired=True,message='Source изменился во время запуска');value.pop('score',None)
    (work/'report.json').write_text(json.dumps(value,ensure_ascii=False,indent=2)+'\n')
    print(value['classification'],flush=True)
    return 0 if value['classification']=='success' else 1
if __name__=='__main__':raise SystemExit(main())
