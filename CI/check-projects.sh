#!/usr/bin/env bash
# Owner CLIs supply model collection, grading and native publication.
set -euo pipefail
TASK_COURSE_ROOT=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)
: "${PL_PLATFORM_ROOT:?set the exact released Platform checkout outside the course}"
TASK_CHECK_OUTPUT=${CI_CHECK_OUTPUT:-$(mktemp -d -t java-project-checks-XXXXXXXX)}
mkdir -p -- "$TASK_CHECK_OUTPUT"
TASK_CHECK_OUTPUT=$(realpath -- "$TASK_CHECK_OUTPUT")
case "$TASK_CHECK_OUTPUT/" in "$TASK_COURSE_ROOT/"*) echo 'Checks output must be outside course source' >&2; exit 2;; esac
cd -- "$TASK_COURSE_ROOT"
quarto run tasks/_extensions/Afonenko-Course-Tools/course-core/entrypoints/project-checks.ts \
  --book tasks --output "$TASK_CHECK_OUTPUT/declared-checks.json"
python3 "$PL_PLATFORM_ROOT/tools/check-course.py" inventory \
  --manifest "$TASK_CHECK_OUTPUT/declared-checks.json" --scope declared \
  --output "$TASK_CHECK_OUTPUT/inventory.json"
# These receipts cover the opted-in ready subset; incomplete declarations remain visible.
for TASK_BACKEND in host container; do
  python3 "$PL_PLATFORM_ROOT/tools/check-course.py" verify \
    --manifest "$TASK_CHECK_OUTPUT/declared-checks.json" --snapshot "$TASK_COURSE_ROOT" \
    --scope declared --ready-only --backend "$TASK_BACKEND" \
    --output "$TASK_CHECK_OUTPUT/ready-${TASK_BACKEND}.json"
done
python3 "$PL_PLATFORM_ROOT/tools/build-course.py" --source "$TASK_COURSE_ROOT" \
  --book tasks --instance pilot --output "$TASK_CHECK_OUTPUT/native"
# Selected delivery is verified in full: no ready-only or scenario subset.
for TASK_BACKEND in host container; do
  python3 "$PL_PLATFORM_ROOT/tools/check-course.py" verify \
    --manifest "$TASK_CHECK_OUTPUT/native-checks.json" --snapshot "$TASK_COURSE_ROOT" \
    --scope delivery --delivery "$TASK_CHECK_OUTPUT/native/delivery.json" \
    --backend "$TASK_BACKEND" --output "$TASK_CHECK_OUTPUT/delivery-${TASK_BACKEND}.json"
done
