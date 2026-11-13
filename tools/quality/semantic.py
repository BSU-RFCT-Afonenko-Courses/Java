#!/usr/bin/env python3
"""Семантические проверки собственных QMD, конфигураций, стилей и shell."""

from __future__ import annotations

import argparse
import json
import shutil
import subprocess
from pathlib import Path

from common import source_files

CONFIG = Path(__file__).resolve().parent
NPM_BIN = CONFIG.parents[1] / "node_modules/.bin"


def unique_object(pairs: list[tuple[str, object]]) -> dict[str, object]:
    """JSON не допускает молчаливого затенения предыдущего ключа."""
    result: dict[str, object] = {}
    for key, value in pairs:
        if key in result:
            raise ValueError(f"Повторный ключ JSON: {key}")
        result[key] = value
    return result


def run(root: Path, command: list[str], files: list[Path]) -> bool:
    if not files:
        return True
    if shutil.which(command[0]) is None:
        print(f"Отсутствует системный инструмент в PATH: {command[0]}", flush=True)
        return False
    # Один поток Panache и последовательные партии сохраняют ресурсы полного CI.
    result = subprocess.run([*command, *map(str, files)], cwd=root, check=False)
    return result.returncode == 0


def check(root: Path) -> bool:
    files = source_files(root)
    success = True
    for path in files:
        if path.suffix == ".json":
            try:
                json.loads((root / path).read_text(), object_pairs_hook=unique_object)
            except (ValueError, UnicodeError) as error:
                print(f"Некорректный JSON {path}: {error}", flush=True)
                success = False
    groups = [
        (
            [
                "panache",
                "--config",
                str(CONFIG / "panache.toml"),
                "--no-cache",
                "--color",
                "never",
                "--jobs",
                "1",
                "lint",
                "--message-format",
                "short",
            ],
            [p for p in files if p.suffix == ".qmd"],
        ),
        (
            [
                "yamllint",
                "--strict",
                "--format",
                "parsable",
                "--config-file",
                str(CONFIG / "yamllint.yml"),
            ],
            [p for p in files if p.suffix in {".yml", ".yaml"}],
        ),
        (
            ["actionlint", "-color"],
            [
                p
                for p in files
                if p.parts[:2] == (".github", "workflows")
                and p.suffix in {".yml", ".yaml"}
            ],
        ),
        (
            [str(NPM_BIN / "stylelint"), "--config", str(CONFIG / "stylelint.json")],
            [p for p in files if p.suffix == ".css"],
        ),
        (
            [
                str(NPM_BIN / "stylelint"),
                "--config",
                str(CONFIG / "stylelint-scss.json"),
            ],
            [p for p in files if p.suffix == ".scss"],
        ),
        (
            [
                str(NPM_BIN / "html-validate"),
                "--config",
                str(CONFIG / "htmlvalidate.json"),
            ],
            [p for p in files if p.suffix == ".html"],
        ),
        (["taplo", "lint", "--no-schema"], [p for p in files if p.suffix == ".toml"]),
    ]
    for command, selected in groups:
        success = run(root, command, selected) and success
    for path in files:
        if path.suffix in {".sh", ".bash"}:
            success = run(root, ["bash", "-n"], [path]) and success
    return success


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("mode", choices=("format", "check-format", "check"))
    parser.add_argument("--root", type=Path, default=CONFIG.parents[1])
    args = parser.parse_args()
    return 0 if args.mode != "check" or check(args.root.resolve()) else 1


if __name__ == "__main__":
    raise SystemExit(main())
