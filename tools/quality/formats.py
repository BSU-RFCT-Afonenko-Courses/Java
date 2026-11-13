#!/usr/bin/env python3
"""Форматирование собственных исходников штатными инструментами из PATH."""

from __future__ import annotations

import argparse
import re
import shutil
import subprocess
from pathlib import Path

from common import source_files

BIBTEX_OPTIONS = [
    "--v2",
    "--no-curly",
    "--no-numeric",
    "--no-escape",
    "--no-remove-empty-fields",
    "--no-remove-dupe-fields",
    "--no-lowercase",
    "--no-strip-comments",
    "--no-tidy-comments",
    "--no-sort",
    "--no-wrap",
    "--no-merge",
    "--no-align",
    "--blank-lines",
]

PRETTIER_SUFFIXES = {
    ".css",
    ".js",
    ".jsx",
    ".json",
    ".jsonc",
    ".md",
    ".mjs",
    ".cjs",
    ".ts",
    ".tsx",
    ".yaml",
    ".yml",
}


def run(root: Path, command: list[str], files: list[Path]) -> bool:
    """Проверить каждую группу, не превращая пропущенный инструмент в успех."""
    if not files:
        return True
    if shutil.which(command[0]) is None:
        print(f"Отсутствует системный инструмент в PATH: {command[0]}", flush=True)
        return False
    success = True
    for offset in range(0, len(files), 100):
        batch = [str(path) for path in files[offset : offset + 100]]
        result = subprocess.run([*command, *batch], cwd=root, check=False)
        success = result.returncode == 0 and success
    return success


def normalize_output(root: Path, files: list[Path], tool: str, write: bool) -> bool:
    """Сравнить вывод штатного форматтера; запись допустима лишь после успеха."""
    if not files:
        return True
    if shutil.which(tool) is None:
        print(f"Отсутствует системный инструмент в PATH: {tool}", flush=True)
        return False
    success = True
    for path in files:
        command = (
            [tool, str(path), *BIBTEX_OPTIONS]
            if tool == "bibtex-tidy"
            else [tool, "--nonet", "--format", str(path)]
        )
        result = subprocess.run(command, cwd=root, stdout=subprocess.PIPE, check=False)
        original = (root / path).read_bytes()
        if result.returncode:
            success = False
        elif tool == "bibtex-tidy" and re.sub(rb"\s+", b"", result.stdout) != re.sub(
            rb"\s+", b"", original
        ):
            print(
                f"BibTeX formatter изменил содержимое помимо пробелов: {path}",
                flush=True,
            )
            success = False
        elif result.stdout != original:
            if write:
                (root / path).write_bytes(result.stdout)
            else:
                print(f"Требуется форматирование: {path}", flush=True)
                success = False
    return success


def check(root: Path, mode: str) -> bool:
    files = source_files(root)
    python = [path for path in files if path.suffix == ".py"]
    shell = [path for path in files if path.suffix in {".sh", ".bash"}]
    prettier = [path for path in files if path.suffix in PRETTIER_SUFFIXES]
    lua = [path for path in files if path.suffix == ".lua"]
    xml = [
        path
        for path in files
        if path.suffix in {".xml", ".csl", ".svg", ".csproj"}
        or path.name == "NuGet.Config"
    ]
    bibtex = [path for path in files if path.suffix == ".bib"]
    toml = [path for path in files if path.suffix == ".toml"]
    gitmodules = [path for path in files if path.name == ".gitmodules"]
    write = mode == "format"
    commands = [
        (["ruff", "format", "--no-cache", *([] if write else ["--check"])], python),
        (["shfmt", "-i", "2", "-ci", "-w" if write else "-d"], shell),
        (["prettier", "--write" if write else "--check", "--"], prettier),
        (["stylua", *([] if write else ["--check"]), "--"], lua),
    ]
    if mode == "check":
        commands.extend(
            [
                (["ruff", "check", "--no-cache"], python),
                (["shellcheck"], shell),
            ]
        )
    commands.append((["taplo", "format", *([] if write else ["--check"])], toml))
    success = True
    for path in gitmodules:
        # Git config задаёт собственный синтаксис .gitmodules, без style rewrite.
        result = subprocess.run(
            ["git", "config", "--file", str(path), "--list"],
            cwd=root,
            stdout=subprocess.DEVNULL,
            check=False,
        )
        success = result.returncode == 0 and success
    success = normalize_output(root, xml, "xmllint", write) and success
    success = normalize_output(root, bibtex, "bibtex-tidy", write) and success
    for command, selected in commands:
        success = run(root, command, selected) and success
    return success


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("mode", choices=("format", "check-format", "check"))
    parser.add_argument(
        "--root", type=Path, default=Path(__file__).resolve().parents[2]
    )
    args = parser.parse_args()
    return 0 if check(args.root.resolve(), args.mode) else 1


if __name__ == "__main__":
    raise SystemExit(main())
