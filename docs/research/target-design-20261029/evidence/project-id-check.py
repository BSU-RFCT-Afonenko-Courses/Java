"""Узкая воспроизводимая выборка project -> ID; файлы не изменяет.

Запуск из корня курса: python3 docs/research/target-design-20261029/evidence/project-id-check.py
Лексическая проверка явных атрибутов вне fenced code; не AST/render/QRC PASS.
"""
from pathlib import Path
import json
import re

root = Path(__file__).resolve().parents[4]
starts = ['theory', 'lectures', 'practice', 'handbook', 'tasks', 'index.qmd']
excluded = {'_extensions', '_output', '_generated', '_site', '_site-student',
            '_site-full', 'provenance', 'reference', 'node_modules'}
files = []
for start in starts:
    path = root / start
    files.extend([path] if path.is_file() else path.rglob('*.qmd') if path.exists() else [])
files = sorted({path for path in files
                if not any(part.startswith('.') or part in excluded
                           for part in path.relative_to(root).parts)
                and path.relative_to(root).parts[:2] != ('tasks', 'projects')})
records = []
for path in files:
    fence = None
    for line_number, line in enumerate(path.read_text().splitlines(), 1):
        marker = re.match(r'^\s*(`{3,}|~{3,})', line)
        if fence:
            if marker and marker.group(1)[0] == fence[0] and len(marker.group(1)) >= fence[1]:
                fence = None
            continue
        for attributes in re.findall(r'\{[^{}\n]*\}', line):
            for identifier in re.findall(r'(?:^|\s)#([^\s}]+)', attributes[1:-1]):
                project = re.search(r'\bproject="([^"]+)"', attributes)
                if identifier.startswith('exr-') and project:
                    actual = project.group(1)
                    expected_id = 'exr-' + actual.removeprefix('/projects/').replace('/', '-')
                    records.append({'source': str(path.relative_to(root)), 'line': line_number,
                                    'id': identifier, 'project': actual,
                                    'expected_id': expected_id, 'match': identifier == expected_id})
        if marker:
            fence = (marker.group(1)[0], len(marker.group(1)))
print(json.dumps({'root': str(root), 'source_qmd_count': len(files),
                  'project_id_pairs': len(records),
                  'matches': sum(record['match'] for record in records),
                  'mismatches': [record for record in records if not record['match']],
                  'pairs': records}, ensure_ascii=False, indent=2))
