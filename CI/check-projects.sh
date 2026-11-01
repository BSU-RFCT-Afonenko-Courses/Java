#!/usr/bin/env bash
# Только строгий published source preflight ДО operator L/S/prior P.
# Полный qualified release gate — прямые existing Platform commands:
# docs/qualified-course-checks.md. Этот exit0 не означает grading/full135 PASS.
set -euo pipefail
umask 077
export PYTHONDONTWRITEBYTECODE=1
TASK_COURSE_ROOT=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)
cd -- "$TASK_COURSE_ROOT"
python3 -B tools/materialize-providers.py --check
python3 -B CI/check-production-pins.py
echo 'Published source preflight завершён; qualified build/full inventory/container receipt остаются отдельным обязательным release gate.'
