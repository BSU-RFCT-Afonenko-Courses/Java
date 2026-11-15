#!/usr/bin/env python3
"""Синхронизация ZIP примеров с соседними каноническими исходниками."""

from __future__ import annotations

import argparse
import os
import stat
import subprocess
import tempfile
import zipfile
from pathlib import Path, PurePosixPath

from common import source_files

GENERATED = {"out", "build", "target", "bin", "obj", "__pycache__", ".gradle"}
ARTIFACTS = {
    ".class",
    ".pyc",
    ".pyo",
    ".jar",
    ".war",
    ".o",
    ".obj",
    ".exe",
    ".dll",
    ".pdb",
}
STAMP = (1980, 1, 1, 0, 0, 0)


def snapshot(
    root: Path, archive: Path, sources: list[Path]
) -> tuple[dict[str, bytes], dict[str, int], dict[str, bytes]]:
    """Проверить структуру ZIP и получить его собственные соседние исходники."""
    actual: dict[str, bytes] = {}
    modes: dict[str, int] = {}
    prefixes: set[str] = set()
    seen: set[str] = set()
    if (root / archive).is_symlink():
        raise ValueError("Архив не должен быть символической ссылкой")
    with zipfile.ZipFile(root / archive) as zipped:
        for entry in zipped.infolist():
            name = entry.filename
            parts = name.rstrip("/").split("/")
            if (
                not name
                or "\\" in name
                or any(p in {"", ".", ".."} or ":" in p for p in parts)
            ):
                raise ValueError(f"Небезопасный путь ZIP: {name!r}")
            if name in seen:
                raise ValueError(f"Повторный путь ZIP: {name}")
            seen.add(name)
            prefixes.add(parts[0])
            mode = entry.external_attr >> 16
            kind = stat.S_IFMT(mode)
            if entry.is_dir():
                if kind not in (0, stat.S_IFDIR):
                    raise ValueError(f"Необычный тип записи ZIP: {name}")
                continue
            if len(parts) < 2 or kind not in (0, stat.S_IFREG):
                raise ValueError(
                    f"Ожидается обычный файл под единственным корнем ZIP: {name}"
                )
            actual[name] = zipped.read(entry)
            modes[name] = stat.S_IMODE(mode)
    if len(prefixes) != 1:
        raise ValueError("ZIP должен иметь единственный верхний каталог")
    prefix = next(iter(prefixes))
    canonical = archive.parent / prefix
    physical = root / canonical
    if physical.is_symlink() or not physical.is_dir():
        raise ValueError(f"Нет соседнего канонического каталога: {canonical}")
    expected: dict[str, bytes] = {}
    for path in sources:
        if not path.is_relative_to(canonical):
            continue
        relative = path.relative_to(canonical)
        if (
            any(part in GENERATED for part in relative.parts)
            or path.suffix.lower() in ARTIFACTS
        ):
            continue
        # Не следуем даже промежуточной ссылке внутри канонического дерева.
        if any(parent.is_symlink() for parent in (root / path, *(root / path).parents)):
            continue
        name = (PurePosixPath(prefix) / relative.as_posix()).as_posix()
        expected[name] = (root / path).read_bytes()
        modes.setdefault(name, stat.S_IMODE((root / path).stat().st_mode))
    return expected, modes, actual


def regenerate(path: Path, expected: dict[str, bytes], modes: dict[str, int]) -> None:
    """Атомарно записать стабильный ZIP, сохранив права существующих записей."""
    archive_mode = stat.S_IMODE(path.stat().st_mode)
    descriptor, temporary = tempfile.mkstemp(prefix=f".{path.name}.", dir=path.parent)
    os.close(descriptor)
    try:
        with zipfile.ZipFile(
            temporary, "w", compression=zipfile.ZIP_DEFLATED, compresslevel=9
        ) as zipped:
            for name, content in sorted(expected.items()):
                entry = zipfile.ZipInfo(name, STAMP)
                entry.create_system = 3
                entry.external_attr = (stat.S_IFREG | modes[name]) << 16
                entry.compress_type = zipfile.ZIP_DEFLATED
                zipped.writestr(entry, content, compresslevel=9)
        os.chmod(temporary, archive_mode)
        os.replace(temporary, path)
    finally:
        if os.path.exists(temporary):
            os.unlink(temporary)


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("mode", choices=("format", "check-format", "check"))
    parser.add_argument("--root", type=Path, required=True)
    args = parser.parse_args()
    root = args.root.resolve()
    sources = source_files(root)
    tracked = (
        subprocess.check_output(
            ["git", "ls-files", "--cached", "-z", "--", "handbook/examples/*.zip"],
            cwd=root,
        )
        .decode("utf-8")
        .split("\0")
    )
    archives = sorted(
        {
            p
            for p in [*sources, *(Path(name) for name in tracked if name)]
            if p.parent == Path("handbook/examples") and p.suffix == ".zip"
        }
    )
    stale: list[tuple[Path, dict[str, bytes], dict[str, int]]] = []
    errors: list[str] = []
    for archive in archives:
        try:
            expected, modes, actual = snapshot(root, archive, sources)
            if expected != actual:
                stale.append((archive, expected, modes))
        except (OSError, ValueError, zipfile.BadZipFile, RuntimeError) as error:
            errors.append(f"{archive}: {error}")
    if errors:
        print("\n".join(errors))
        return 1
    for archive, expected, modes in stale:
        if args.mode == "format":
            regenerate(root / archive, expected, modes)
            print(f"Обновлён ZIP: {archive}")
        else:
            print(f"ZIP расходится с каноническими исходниками: {archive}")
    return int(bool(stale) and args.mode != "format")


if __name__ == "__main__":
    raise SystemExit(main())
