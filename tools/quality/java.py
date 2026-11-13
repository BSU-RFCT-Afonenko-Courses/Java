"""Форматирование и ограниченная синтаксическая статика авторских Java-исходников."""

from __future__ import annotations

import argparse
import collections
import hashlib
import json
import shutil
import subprocess
import sys
import tempfile
import xml.etree.ElementTree as ET
from pathlib import Path
from typing import TypedDict

SUPPORT = Path(__file__).with_suffix("")


class SyntaxException(TypedDict):
    path: str
    sha256: str
    reason: str


class ExpectedDiagnostic(TypedDict):
    path: str
    check: str
    count: int
    reason: str


class FormatterError(RuntimeError):
    """Ошибка установленного форматтера или неподдержанный исходник."""


def formatter_command() -> list[str]:
    """Команда общего форматтера standalone-файлов и Java-фрагментов QMD."""
    executable = shutil.which("google-java-format")
    if executable is None:
        raise FormatterError("Нужен системный google-java-format в PATH")
    return [
        executable,
        "--aosp",
        "--skip-sorting-imports",
        "--skip-removing-unused-imports",
        "--skip-reflowing-long-strings",
        "--skip-javadoc-formatting",
        "--skip-reordering-modifiers",
        "--cleanup=false",
    ]


def format_source(source: str, *, assume_filename: str = "Example.java") -> str:
    """Форматирует compilation unit; импорт пригоден адаптеру QMD."""
    for _ in range(4):
        result = subprocess.run(
            [*formatter_command(), "--assume-filename", assume_filename, "-"],
            input=source,
            text=True,
            capture_output=True,
            check=False,
        )
        if result.returncode:
            raise FormatterError(
                result.stderr.strip() or "google-java-format завершился с ошибкой"
            )
        if result.stdout == source:
            return source
        source = result.stdout
    raise FormatterError(
        "google-java-format не достиг устойчивого формата за четыре прохода"
    )


def java_files(root: Path) -> list[Path]:
    # Импорт здесь оставляет formatter API независимым от общего CLI.
    from common import source_files

    return [path for path in source_files(root) if path.suffix == ".java"]


def supported_files(root: Path, files: list[Path]) -> list[Path]:
    """Намеренно неразбираемые fixtures: явный охват вместо directory skip."""
    entries: list[SyntaxException] = json.loads(
        (SUPPORT / "syntax-exceptions.json").read_text()
    )
    excluded: set[Path] = set()
    for entry in entries:
        path = Path(entry["path"])
        if path not in files:
            raise FormatterError(
                f"Исчез ожидаемый синтаксически неверный пример: {path}"
            )
        if hashlib.sha256((root / path).read_bytes()).hexdigest() != entry["sha256"]:
            raise FormatterError(
                f"Изменён синтаксически неверный пример; нужен пересмотр допуска: {path}"
            )
        excluded.add(path)
    if excluded:
        print(
            f"Синтаксически неверные учебные fixtures: {len(excluded)} (проверены точные исходники)"
        )
    return [path for path in files if path not in excluded]


def fingerprints(root: Path, files: list[Path]) -> dict[str, str]:
    """AST без type checking: ошибочные учебные программы также входят в охват."""
    java = shutil.which("java")
    if java is None:
        raise FormatterError("Нужен системный JDK 25 или новее в PATH")
    result = subprocess.run(
        [java, str(SUPPORT / "SourceFingerprint.java"), *map(str, files)],
        cwd=root,
        text=True,
        capture_output=True,
        check=False,
    )
    if result.returncode:
        raise FormatterError(
            result.stderr.strip() or "Не удалось разобрать Java-исходники"
        )
    return dict(line.split("\t", 1) for line in result.stdout.splitlines())


def format_files(root: Path, files: list[Path], *, write: bool) -> bool:
    if not files:
        return True
    before = fingerprints(root, files)
    with tempfile.TemporaryDirectory(prefix="course-java-format-") as directory:
        temporary = Path(directory)
        copies: list[Path] = []
        for path in files:
            target = temporary / path
            target.parent.mkdir(parents=True, exist_ok=True)
            target.write_bytes((root / path).read_bytes())
            copies.append(target)
        # Некоторые длинные конкатенации требуют повторного прохода GJF.
        # Дожидаемся устойчивого результата до записи, исключая цикл форматтера.
        for _ in range(4):
            previous = {path: path.read_bytes() for path in copies}
            result = subprocess.run(
                [*formatter_command(), "--replace", *map(str, copies)],
                text=True,
                capture_output=True,
                check=False,
            )
            if result.returncode:
                raise FormatterError(
                    result.stderr.strip() or "google-java-format завершился с ошибкой"
                )
            copies = [path for path in copies if path.read_bytes() != previous[path]]
            if not copies:
                break
        else:
            raise FormatterError(
                "google-java-format не достиг устойчивого формата за четыре прохода"
            )
        after = fingerprints(temporary, files)
        if before != after:
            changed = [
                str(path) for path in files if before[str(path)] != after[str(path)]
            ]
            raise FormatterError("Форматтер изменил Java AST: " + ", ".join(changed))
        differences: list[Path] = []
        for path in files:
            formatted = (temporary / path).read_bytes()
            if formatted != (root / path).read_bytes():
                differences.append(path)
                if write:
                    (root / path).write_bytes(formatted)
        if differences:
            action = "Отформатировано" if write else "Требуют форматирования"
            print(f"{action}: {len(differences)} Java-файлов")
            if not write:
                for path in differences:
                    print(path)
        return write or not differences


def static_check(root: Path, files: list[Path]) -> bool:
    if not files:
        return True
    executable = shutil.which("checkstyle")
    if executable is None:
        raise FormatterError("Нужен системный checkstyle с поддержкой Java 25 в PATH")
    result = subprocess.run(
        [
            executable,
            "-c",
            str(SUPPORT / "checkstyle.xml"),
            "-f",
            "xml",
            *map(str, files),
        ],
        cwd=root,
        text=True,
        capture_output=True,
        check=False,
    )
    try:
        report = ET.fromstring(result.stdout)
    except ET.ParseError as error:
        raise FormatterError(result.stderr.strip() or result.stdout.strip()) from error
    actual: collections.Counter[tuple[str, str]] = collections.Counter()
    reported: set[Path] = set()
    for file in report.findall("file"):
        path = Path(file.attrib["name"])
        if path.is_absolute():
            path = path.relative_to(root)
        reported.add(path)
        for diagnostic in file.findall("error"):
            check = (
                diagnostic.attrib.get("source", "")
                .rsplit(".", 1)[-1]
                .removesuffix("Check")
            )
            actual[str(path), check] += 1
    if reported != set(files):
        raise FormatterError(
            "Checkstyle вернул неполный или посторонний охват Java-файлов"
        )
    expected_entries: list[ExpectedDiagnostic] = json.loads(
        (SUPPORT / "expected-diagnostics.json").read_text()
    )
    expected: collections.Counter[tuple[str, str]] = collections.Counter(
        {(entry["path"], entry["check"]): entry["count"] for entry in expected_entries}
    )
    unexpected = actual - expected
    missing = expected - actual
    for (path, check), count in unexpected.items():
        print(f"{path}: неожиданная диагностика {check} ({count})")
    for (path, check), count in missing.items():
        print(f"{path}: исчезла ожидаемая учебная диагностика {check} ({count})")
    if result.returncode and not actual:
        raise FormatterError(result.stderr.strip() or "Checkstyle завершился с ошибкой")
    return not unexpected and not missing


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("mode", choices=("format", "check-format", "check"))
    parser.add_argument("--root", type=Path, required=True)
    args = parser.parse_args()
    root = args.root.resolve()
    try:
        files = java_files(root)
        print(f"Java: {len(files)} авторских standalone-файлов")
        files = supported_files(root, files)
        formatted = format_files(root, files, write=args.mode == "format")
        checked = static_check(root, files) if args.mode == "check" else True
        return 0 if formatted and checked else 1
    except (FormatterError, OSError, ValueError) as error:
        print(f"Java quality: {error}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
