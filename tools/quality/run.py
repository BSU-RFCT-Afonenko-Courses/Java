#!/usr/bin/env python3
"""Короткий вход в штатные форматтеры и проверки авторских исходников."""

from __future__ import annotations

import argparse
import shutil
import subprocess
import sys
from pathlib import Path


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("mode", choices=("format", "check-format", "check"))
    parser.add_argument(
        "--root", type=Path, default=Path(__file__).resolve().parents[2]
    )
    args = parser.parse_args()
    root = args.root.resolve()
    success = True
    # Локальный Gradle-комплект получает производную копию общего помощника.
    canonical = root / "tasks/test-support/src/main/java/coursesupport/JavaProcess.java"
    copy = (
        root
        / "tasks/projects/common/java/process/src/test/java/coursesupport/JavaProcess.java"
    )
    if canonical.read_bytes() != copy.read_bytes():
        if args.mode == "format":
            shutil.copyfile(canonical, copy)
        else:
            print(
                f"Копия JavaProcess отличается от канонического исходника: {copy}",
                flush=True,
            )
            success = False
    # Архивы проверяются после всех канонических текстовых входов.
    for name in ("java", "qmd", "groovy", "examples", "formats", "archives"):
        print(f"Качество исходников: {name} / {args.mode}", flush=True)
        result = subprocess.run(
            [
                sys.executable,
                str(root / "tools/quality" / f"{name}.py"),
                args.mode,
                "--root",
                str(root),
            ],
            cwd=root,
            check=False,
        )
        success = result.returncode == 0 and success
    return 0 if success else 1


if __name__ == "__main__":
    raise SystemExit(main())
