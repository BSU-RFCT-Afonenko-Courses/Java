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
timeout --signal=TERM --kill-after=5s 5400s quarto render . --profile student --fail-if-warnings
quarto run tasks/_extensions/Afonenko-Course-Tools/course-core/entrypoints/check.ts tasks student
quarto run tasks/_extensions/Afonenko-Course-Tools/course-core/entrypoints/project-checks.ts \
  --book tasks --output "$TASK_CHECK_OUTPUT/declared-checks.json"
python3 "$PL_PLATFORM_ROOT/tools/check-course.py" inventory \
  --manifest "$TASK_CHECK_OUTPUT/declared-checks.json" --scope declared \
  --runtime-registry "$PL_RUNTIME_REGISTRY" --output "$TASK_CHECK_OUTPUT/inventory.json"
python3 - "$TASK_CHECK_OUTPUT" <<'PY'
import json,sys,os
from pathlib import Path
output=Path(sys.argv[1]);manifest=json.loads((output/'declared-checks.json').read_text());report=json.loads((output/'inventory.json').read_text());model=json.loads(Path('tasks/_generated/course-spec/course.json').read_text())
facts=[p for p in model.get('projects',[]) if p.get('check')];projects=manifest['projects'];ids=[p['exerciseId'] for p in projects]
expected={p['exerciseId']:p for p in facts}
if len(expected)!=len(facts) or len(set(ids))!=len(ids) or set(ids)!=set(expected):raise SystemExit('Core model и declared manifest имеют разные/повторные active IDs')
for p in projects:
    fact=expected[p['exerciseId']]
    missing=p['readiness']['missing']
    if not p['sources'] or any(x in ('projectRoot','sources','verificationTests','studentApiPrecheck') for x in missing):raise SystemExit('Отсутствуют обязательные public source/student inputs')
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
PY
