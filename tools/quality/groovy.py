#!/usr/bin/env python3
"""Форматирование и статический анализ собственных Groovy/Gradle-файлов."""

import argparse
import shutil
import subprocess
import tempfile
from pathlib import Path

from common import source_files


def lint(root: Path, files: list[Path], *, formatting: bool = False) -> int:
    command = [
        "npm-groovy-lint",
        "--config",
        str(root / ".groovylintrc.json"),
        "--noserver",
        "--javaexecutable",
        str(shutil.which("java")),
        "--failon",
        "info",
    ]
    if formatting:
        command.append("--format")
    command.extend(str(root / path) for path in files)
    return subprocess.run(command, cwd=root, check=False).returncode


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("mode", choices=("format", "check-format", "check"))
    parser.add_argument("--root", type=Path, required=True)
    args = parser.parse_args()
    root = args.root.resolve()
    files = [
        path for path in source_files(root) if path.suffix in {".groovy", ".gradle"}
    ]
    if not files:
        return 0
    if shutil.which("npm-groovy-lint") is None:
        parser.exit(2, "Требуется системный npm-groovy-lint в PATH.\n")
    if shutil.which("java") is None:
        parser.exit(2, "Требуется системная Java в PATH.\n")
    if args.mode != "check-format":
        return lint(root, files, formatting=args.mode == "format")
    # CLI не имеет dry-run: форматируем изолированные копии, исходники не меняем.
    with tempfile.TemporaryDirectory(prefix="java-groovy-format-") as directory:
        scratch = Path(directory)
        shutil.copyfile(root / ".groovylintrc.json", scratch / ".groovylintrc.json")
        for path in files:
            target = scratch / path
            target.parent.mkdir(parents=True, exist_ok=True)
            shutil.copyfile(root / path, target)
        result = lint(scratch, files, formatting=True)
        changed = [
            str(path)
            for path in files
            if (root / path).read_bytes() != (scratch / path).read_bytes()
        ]
        if changed:
            print("Требуется форматирование Groovy/Gradle:")
            print("\n".join(changed))
            return 1
        return result


if __name__ == "__main__":
    raise SystemExit(main())
