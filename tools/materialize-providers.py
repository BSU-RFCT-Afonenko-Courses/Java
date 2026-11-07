#!/usr/bin/env python3
"""Установить выбранные полные packages из локальных Git releases и patches."""
from pathlib import Path, PurePosixPath
from collections.abc import Mapping, Sequence
from typing import NotRequired, TypedDict, TypeGuard
import argparse
import io
import json
import re
import shutil
import subprocess
import sys
import tarfile
import tempfile


class Candidate(TypedDict):
    patch: str


class Provider(TypedDict):
    checkout: str
    commit: str
    packages: dict[str, list[str]]
    candidate: NotRequired[Candidate]


class Arguments(argparse.Namespace):
    course_root: Path
    providers_root: Path
    write: bool


def _mapping(value: object) -> TypeGuard[Mapping[object, object]]:
    return isinstance(value, Mapping)


def _array(value: object) -> TypeGuard[Sequence[object]]:
    return isinstance(value, list)


def record(value: object, label: str) -> dict[str, object]:
    if not _mapping(value):
        raise ValueError(label + ': нужен JSON-объект')
    result: dict[str, object] = {}
    for key, item in value.items():
        if not isinstance(key, str):
            raise ValueError(label + ': нужен строковый ключ')
        result[key] = item
    return result


def text(value: object, label: str) -> str:
    if not isinstance(value, str):
        raise ValueError(label + ': нужна строка')
    return value


def scopes(value: object) -> list[str]:
    if not _array(value):
        raise ValueError('packages: нужен массив каталогов')
    return [text(item, 'package scope') for item in value]


def read_providers(path: Path) -> tuple[str, dict[str, Provider]]:
    decoded: object = json.loads(path.read_text())
    manifest = record(decoded, 'providers.json')
    namespace = text(manifest['namespace'], 'namespace')
    providers: dict[str, Provider] = {}
    for name, value in record(manifest['providers'], 'providers').items():
        raw = record(value, name)
        provider: Provider = {
            'checkout': text(raw['checkout'], 'checkout'),
            'commit': text(raw['commit'], 'commit'),
            'packages': {
                package: scopes(destinations)
                for package, destinations in record(raw['packages'], 'packages').items()
            },
        }
        if 'candidate' in raw:
            candidate = record(raw['candidate'], 'candidate')
            provider['candidate'] = {'patch': text(candidate['patch'], 'candidate.patch')}
        providers[name] = provider
    return namespace, providers


def safe(root: Path, relative: str) -> Path:
    path = PurePosixPath(relative)
    if path.is_absolute() or '..' in path.parts:
        raise ValueError(f'Недопустимый путь: {relative}')
    current = root
    for part in path.parts:
        current = current / part
        if current.is_symlink():
            raise ValueError(f'Символическая ссылка: {relative}')
    return current


def git(repo: Path, *args: str) -> bytes:
    return subprocess.check_output(['git', *args], cwd=repo, stderr=subprocess.PIPE)


def stage(root: Path, provider: Provider, sources: Path, temporary: Path) -> dict[str, Path]:
    repo = safe(sources, provider['checkout'])
    commit = provider['commit']
    if not re.fullmatch(r'[a-f0-9]{40}', commit):
        raise ValueError('Нужен Git commit источника')
    data = git(repo, 'archive', '--format=tar', commit, '_extensions')
    with tarfile.open(fileobj=io.BytesIO(data)) as archive:
        for entry in archive:
            path = safe(temporary, entry.name)
            if entry.isdir():
                path.mkdir(parents=True, exist_ok=True)
            elif entry.isfile():
                path.parent.mkdir(parents=True, exist_ok=True)
                stream = archive.extractfile(entry)
                if stream is None:
                    raise ValueError('Не прочитан обычный файл Git archive: ' + entry.name)
                with stream:
                    path.write_bytes(stream.read())
                path.chmod(0o755 if entry.mode & 0o100 else 0o644)
            else:
                raise ValueError(f'Необычный файл Git archive: {entry.name}')
    if 'candidate' in provider:
        patch = safe(root, provider['candidate']['patch'])
        subprocess.run(['git', 'init', '-q', str(temporary)], check=True, capture_output=True)
        subprocess.run(['git', 'apply', str(patch)], cwd=temporary, check=True, capture_output=True)
    result: dict[str,Path] = {}
    for package in provider['packages']:
        path = safe(temporary, '_extensions/' + package)
        if not (path / '_extension.yml').is_file():
            raise ValueError(f'Отсутствует descriptor пакета: {package}')
        if any(entry.is_symlink() for entry in path.rglob('*')):
            raise ValueError(f'Символическая ссылка в package: {package}')
        result[package] = path
    return result


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--course-root', type=Path, default=Path(__file__).resolve().parents[1])
    parser.add_argument('--providers-root', type=Path, required=True)
    parser.add_argument('--write', action='store_true', required=True,
                        help='заменить объявленные packages; локальные изменения сохраните заранее')
    args = parser.parse_args(namespace=Arguments())
    root = args.course_root.resolve(strict=True)
    sources = args.providers_root.resolve(strict=True)
    namespace, providers = read_providers(root / 'providers.json')
    if not re.fullmatch(r'[A-Za-z0-9_-]+', namespace):
        raise ValueError('Неверный namespace provider')
    names = [name for provider in providers.values() for name in provider['packages']]
    if len(set(names)) != len(names):
        raise ValueError('Повторный package')
    planned: list[tuple[Path,Path]] = []
    with tempfile.TemporaryDirectory(prefix='course-providers-') as temporary:
        for key, provider in providers.items():
            directory = safe(Path(temporary), key)
            directory.mkdir()
            for package, source in stage(root, provider, sources, directory).items():
                for scope in provider['packages'][package]:
                    target = safe(root, (PurePosixPath(scope) / '_extensions' / namespace / package).as_posix())
                    if target.exists() and not target.is_dir():
                        raise ValueError(f'Ожидался каталог пакета: {target}')
                    planned.append((source, target))
        # Сначала подготовлены все источники и проверены пути назначений.
        for source, target in planned:
            target.parent.mkdir(parents=True, exist_ok=True)
            if target.exists():
                shutil.rmtree(target)
            shutil.copytree(source, target)
    print(f'Установлено {len(planned)} package scopes')
    return 0


if __name__ == '__main__':
    try:
        raise SystemExit(main())
    except (ValueError, KeyError, OSError, subprocess.CalledProcessError) as error:
        print(f'Установка остановлена: {error}', file=sys.stderr)
        raise SystemExit(2)
