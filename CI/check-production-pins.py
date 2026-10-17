#!/usr/bin/env python3
"""Не смешивать локальную candidate-поставку с опубликованной production средой."""
from pathlib import Path
import json
root=Path(__file__).resolve().parents[1]
providers=json.loads((root/'providers.json').read_text())['providers']
if any(p.get('candidate') for p in providers.values()):
    raise SystemExit('Production grading остановлен: local candidate ещё не опубликован. Используйте CI/check-projects-candidate.sh с exact local Platform/image; для внешнего CI нужны отдельные release/pins. Непушенные SHA не запрашиваются.')
print('PASS: production использует опубликованные provider pins')
