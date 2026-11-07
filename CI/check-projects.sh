#!/usr/bin/env bash
# Обычная публичная сборка; оценивание выполняется штатными grader-командами отдельно.
set -euo pipefail
TASK_COURSE_ROOT=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)
exec bash "$TASK_COURSE_ROOT/CI/render.sh" "$@"
