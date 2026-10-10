#!/usr/bin/env bash
set -euo pipefail
TASK_COURSE_ROOT=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)
TASK_RENDER_LOG_DIR=${CI_LOG_DIR:-$(mktemp -d -t java-render-XXXXXXXX)}
mkdir -p -- "$TASK_RENDER_LOG_DIR"
cd -- "$TASK_COURSE_ROOT"
quarto --version
"${CUE:-cue}" version
TASK_RENDER_PROFILES=("$@")
if [ "${#TASK_RENDER_PROFILES[@]}" -eq 0 ]; then
  TASK_RENDER_PROFILES=(student full student)
fi
for TASK_RENDER_STEP in "${TASK_RENDER_PROFILES[@]}"; do
  case "$TASK_RENDER_STEP" in student|full) ;; *) echo "Unsupported view: $TASK_RENDER_STEP" >&2; exit 2 ;; esac
  TASK_RENDER_COUNT=${TASK_RENDER_COUNT:-0}
  TASK_RENDER_COUNT=$((TASK_RENDER_COUNT + 1))
  quarto render . --profile "$TASK_RENDER_STEP" --fail-if-warnings 2>&1 |
    tee "$TASK_RENDER_LOG_DIR/render-${TASK_RENDER_COUNT}-${TASK_RENDER_STEP}.log"
done
