#!/usr/bin/env python3
"""Поставить одну версию общей поддержки в явный список проектов."""
import argparse
import hashlib
import json
import os
from pathlib import Path, PurePosixPath
import re
import sys
import tempfile


def safe(root, relative):
    value = PurePosixPath(relative)
    if value.is_absolute() or not value.parts or any(p in ('..', '.') for p in value.parts):
        raise ValueError(f'Недопустимый путь: {relative}')
    current = root
    for component in value.parts:
        current = current / component
        if current.is_symlink():
            raise ValueError(f'Символическая ссылка запрещена: {relative}')
    return current


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def replace(path, data):
    path.parent.mkdir(parents=True, exist_ok=True)
    temporary = None
    try:
        with tempfile.NamedTemporaryFile(dir=path.parent, prefix='.' + path.name + '.', delete=False) as out:
            temporary = Path(out.name)
            out.write(data)
        os.replace(temporary, path)
    finally:
        if temporary and temporary.exists():
            temporary.unlink()


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--root', type=Path, default=Path(__file__).resolve().parents[2])
    mode = parser.add_mutually_exclusive_group(required=True)
    mode.add_argument('--write', action='store_true')
    mode.add_argument('--check', action='store_true')
    args = parser.parse_args()
    root = args.root.resolve(strict=True)
    base = 'tasks/test-support/'
    projects = json.loads(safe(root, base + 'projects.json').read_text())
    if not isinstance(projects, list) or not projects or any(
        not isinstance(p, str) or not re.fullmatch(r'[a-z0-9]+(?:-[a-z0-9]+)*', p) for p in projects
    ) or len(set(projects)) != len(projects):
        raise ValueError('Нужен непустой список различных имён проектов')
    source = safe(root, base + 'src/main/java/coursesupport')
    for path in source.rglob('*'):
        safe(root, path.relative_to(root).as_posix())
    sources = sorted(source.rglob('*.java'))
    if not sources:
        raise ValueError('Исходники общей поддержки не найдены')
    manifest_path = safe(root, base + 'materialized.json')
    previous = {}
    if manifest_path.exists():
        manifest = json.loads(manifest_path.read_text())
        if manifest.get('schemaVersion') != 1 or not isinstance(manifest.get('files'), dict):
            raise ValueError('Неверный manifest поставки')
        previous = manifest['files']
        for relative in previous:
            safe(root, relative)
    planned = {}
    for project in projects:
        for partition in ['student/src/test/java', 'tests/junit']:
            target_base = f'tasks/projects/{project}/{partition}'
            if not safe(root, target_base).is_dir():
                raise ValueError(f'Раздел проекта не существует: {target_base}')
            for path in sources:
                relative = target_base + '/coursesupport/' + path.relative_to(source).as_posix()
                target = safe(root, relative)
                if target.exists() and not target.is_file():
                    raise ValueError(f'Ожидался файл: {relative}')
                expected = digest(path)
                actual = digest(target) if target.exists() else None
                if actual is not None and actual != expected and actual != previous.get(relative):
                    raise ValueError(f'Локальная правка сохранена, запись остановлена: {relative}')
                planned[relative] = (path, target, expected, actual)
    if set(previous) - set(planned):
        raise ValueError('Состав поставки уменьшился; старые файлы требуют отдельного рассмотрения')
    hashes = {name: row[2] for name, row in sorted(planned.items())}
    if args.check:
        if previous != hashes or any(expected != actual for _, _, expected, actual in planned.values()):
            raise ValueError('Поставка отсутствует или устарела; выполните --write после проверки изменений')
        print(f'PASS: {len(hashes)} файлов совпали с единственным исходником')
        return 0
    # Все пути и локальные изменения проверены до первой записи.
    for path, target, expected, actual in planned.values():
        if expected != actual:
            replace(target, path.read_bytes())
    data = json.dumps({'schemaVersion': 1, 'files': hashes}, ensure_ascii=False, indent=2) + '\n'
    if not manifest_path.exists() or manifest_path.read_text() != data:
        replace(manifest_path, data.encode('utf-8'))
    print(f'PASS: материализовано {len(hashes)} файлов')
    return 0


if __name__ == '__main__':
    try:
        raise SystemExit(main())
    except (ValueError, OSError) as problem:
        print(problem, file=sys.stderr)
        raise SystemExit(1)
