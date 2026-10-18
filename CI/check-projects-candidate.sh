#!/usr/bin/env bash
# Локальная полная выбранная поставка; remote clone/pull/publish отсутствуют.
set -euo pipefail
TASK_COURSE_ROOT=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)
: "${PL_PLATFORM_ROOT:?укажите exact local Platform checkout}"
: "${PL_LOCAL_IMAGE_ID:?укажите exact local image config ID}"
python3 "$TASK_COURSE_ROOT/CI/check-local-candidate.py" --platform-root "$PL_PLATFORM_ROOT" --image "$PL_LOCAL_IMAGE_ID"
if [ "${1:-}" = --preflight-only ]; then exit 0; fi
TASK_CHECK_OUTPUT=${CI_CHECK_OUTPUT:-$(mktemp -d -t java-candidate-checks-XXXXXXXX)}
mkdir -p -- "$TASK_CHECK_OUTPUT"
TASK_CHECK_OUTPUT=$(realpath -- "$TASK_CHECK_OUTPUT")
case "$TASK_CHECK_OUTPUT/" in "$TASK_COURSE_ROOT/"*) echo 'Output должен находиться вне course source' >&2; exit 2;; esac
cd -- "$TASK_COURSE_ROOT"
# Production image pins в source registry не заменяются local config ID.
python3 - "$PL_PLATFORM_ROOT/runtime-profiles.json" "$TASK_CHECK_OUTPUT/candidate-runtime.json" <<'PY'
import json,sys
from pathlib import Path
source=json.loads(Path(sys.argv[1]).read_text())
for name in ('java25-junit-v1','java25-mutation-v1'):source['profiles'][name]['image']=None
Path(sys.argv[2]).write_text(json.dumps(source,indent=2)+'\n')
PY
quarto render . --profile student --fail-if-warnings
python3 "$PL_PLATFORM_ROOT/tools/build-course.py" --source "$TASK_COURSE_ROOT" --book tasks \
  --instance pilot --output "$TASK_CHECK_OUTPUT/native" \
  --runtime-registry "$TASK_CHECK_OUTPUT/candidate-runtime.json" --candidate-image "$PL_LOCAL_IMAGE_ID"
for TASK_BACKEND in host container; do
  python3 "$PL_PLATFORM_ROOT/tools/check-course.py" verify \
    --manifest "$TASK_CHECK_OUTPUT/native-checks.json" --snapshot "$TASK_COURSE_ROOT" \
    --scope delivery --delivery "$TASK_CHECK_OUTPUT/native/delivery.json" \
    --backend "$TASK_BACKEND" --runtime-registry "$TASK_CHECK_OUTPUT/candidate-runtime.json" \
    --output "$TASK_CHECK_OUTPUT/delivery-${TASK_BACKEND}.json"
done
