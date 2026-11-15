#!/usr/bin/env python3
"""Обязательный PMD для служебного Java; отдельный полный аудит банка."""

from __future__ import annotations

import argparse
import json
import shutil
import subprocess
import tempfile
from pathlib import Path

from common import source_files

CONFIG = Path(__file__).resolve().parent


def service(path: Path) -> bool:
    """Область совместимых служебных исходников, без студенческих решений."""
    return (
        path.parts[0] == "tools"
        or path.as_posix().startswith(("tasks/test-support/", "tasks/projects/common/"))
        or any(part in {"observations", "checks", "demo"} for part in path.parts)
    )


def check(root: Path, *, audit: Path | None = None) -> bool:
    files = [p for p in source_files(root) if p.suffix == ".java"]
    if audit is None:
        files = [p for p in files if service(p)]
    else:
        # Эти пять входов уже проверяет JDK: точный fingerprint и ожидаемый
        # синтаксический отказ. PMD не распознаёт все запрещённые Java конструкции.
        excluded: list[dict[str, str]] = json.loads(
            (CONFIG / "java/syntax-exceptions.json").read_text()
        )
        files = [p for p in files if p.as_posix() not in {e["path"] for e in excluded}]
    if not files:
        return True
    if shutil.which("pmd") is None:
        print("Отсутствует системный инструмент в PATH: pmd", flush=True)
        return False
    with tempfile.TemporaryDirectory(prefix="course-pmd-") as temporary:
        paths = Path(temporary) / "files.txt"
        paths.write_text("".join(str(p) + "\n" for p in files))
        report = audit or Path(temporary) / "report.json"
        result = subprocess.run(
            [
                "pmd",
                "check",
                "--rulesets",
                str(CONFIG / "pmd.xml"),
                "--use-version",
                "java-25",
                "--no-cache",
                "--no-progress",
                "--threads",
                "1",
                "--file-list",
                str(paths),
                "--format",
                "json",
                "--report-file",
                str(report),
            ],
            cwd=root,
            check=False,
        )
        if not report.is_file():
            print(f"PMD не создал отчёт: exit {result.returncode}", flush=True)
            return False
        data = json.loads(report.read_text())
        for item in data["files"]:
            for violation in item["violations"]:
                print(
                    f"{item['filename']}:{violation['beginline']}: "
                    f"{violation['rule']}: {violation['description']}"
                )
        for error in data["processingErrors"]:
            print(f"PMD processing error: {error['filename']}: {error['message']}")
        print(f"PMD: {len(files)} файлов; exit {result.returncode}", flush=True)
        # Code 5 (processing error) никогда не превращается в успешный аудит.
        return (
            result.returncode == 0
            and not data["files"]
            and not data["processingErrors"]
            and not data["configurationErrors"]
        )


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("mode", choices=("format", "check-format", "check"))
    parser.add_argument("--root", type=Path, default=CONFIG.parents[1])
    parser.add_argument(
        "--audit", type=Path, help="Полный аудит; JSON в указанном внешнем файле"
    )
    args = parser.parse_args()
    return (
        0 if args.mode != "check" or check(args.root.resolve(), audit=args.audit) else 1
    )


if __name__ == "__main__":
    raise SystemExit(main())
