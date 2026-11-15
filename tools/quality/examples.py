#!/usr/bin/env python3
"""Форматирование и статические проверки собственных не-Java программ.

C++ проверяется по отдельным translation units, заголовки — через включающий TU.
Kotlin: все .kt одного каталога образуют compilation unit; .kts без отдельного
контекста проверки отклоняется. C# требует ровно одного ближайшего .csproj;
его сборка во временной копии должна разрешить все зависимости. Более сложным
проектам нужен явный контекст сборки, отсутствие контекста не пропускает проверку.
Python type-check ограничен учебными примерами, общий Ruff вызывается formats.py.
"""

from __future__ import annotations

import argparse
import json
import os
import re
import shutil
import subprocess
import tempfile
from pathlib import Path
from typing import TypedDict

from common import source_files


class Diagnostic(TypedDict):
    file: str
    severity: str
    rule: str


def run(
    root: Path, args: list[str], *, capture: bool = False
) -> subprocess.CompletedProcess[str]:
    return subprocess.run(
        args,
        cwd=root,
        text=True,
        capture_output=capture,
        check=not capture,
        env={**os.environ, "LC_ALL": "C.UTF-8", "DOTNET_CLI_TELEMETRY_OPTOUT": "1"},
    )


def python_types(root: Path, files: list[str]) -> None:
    dynamic = "handbook/examples/generics/python/python_dot.py"
    diagnostics: list[Diagnostic] = []
    projects: dict[str, list[str]] = {}
    for file in files:
        config = (
            "tools/quality/examples-pyright-dynamic.json"
            if file == dynamic
            else str(Path(file).parent / "pyrightconfig.json")
        )
        if not (root / config).is_file():
            raise RuntimeError(
                f"Для Python-примера требуется pyrightconfig.json: {file}"
            )
        projects.setdefault(config, []).append(file)
    for config, selected in sorted(projects.items()):
        if not selected:
            continue
        result = run(
            root,
            ["pyright", "--outputjson", "--project", config, *selected],
            capture=True,
        )
        if result.returncode not in (0, 1):
            raise RuntimeError(result.stderr or result.stdout)
        diagnostics.extend(json.loads(result.stdout)["generalDiagnostics"])
    actual: dict[tuple[str, str, str], int] = {}
    for diagnostic in diagnostics:
        key = (
            Path(diagnostic["file"]).relative_to(root).as_posix(),
            diagnostic["severity"],
            diagnostic.get("rule", ""),
        )
        actual[key] = actual.get(key, 0) + 1
    expected = {
        (item["path"], "error", item["rule"]): item["count"]
        for item in json.loads(
            (root / "tools/quality/examples-python-diagnostics.json").read_text()
        )
    }
    if actual != expected:
        raise RuntimeError(
            "Pyright: изменились ожидаемые учебные диагностики:\n"
            + json.dumps(diagnostics, ensure_ascii=False, indent=2)
        )
    print(
        f"Pyright: {len(files)} файлов, {sum(expected.values())} ожидаемых учебных диагностик"
    )


def cpp_analysis(root: Path, file: str) -> None:
    run(
        root,
        [
            "clang-tidy",
            file,
            "--config-file=" + str(root / "tools/quality/examples-clang-tidy.yaml"),
            "--",
            "-std=c++20",
            "-Wall",
            "-Wextra",
            "-Wpedantic",
        ],
    )


def cpp_types(root: Path, files: list[str]) -> None:
    negative = "handbook/examples/generics/cpp/negative.cpp"
    for file in files:
        args = [
            "g++",
            "-std=c++20",
            "-Wall",
            "-Wextra",
            "-Wpedantic",
            "-Werror",
            "-fsyntax-only",
            "-fdiagnostics-color=never",
            file,
        ]
        if file.endswith((".h", ".hpp")):
            with tempfile.TemporaryDirectory(
                prefix="java-quality-header-"
            ) as temporary:
                unit = Path(temporary) / "header.cpp"
                unit.write_text(
                    "#include "
                    + json.dumps(str(root / file), ensure_ascii=False)
                    + "\n"
                )
                run(root, [*args[:-1], str(unit)])
                cpp_analysis(root, str(unit))
            continue
        if file != negative:
            run(root, args)
            cpp_analysis(root, file)
            continue
        result = run(root, args, capture=True)
        errors = re.findall(
            r"^.*?:\d+:\d+: (?:fatal )?error: (.*)$", result.stderr, re.MULTILINE
        )
        expected = "no matching function for call to"
        if (
            result.returncode != 1
            or len(errors) != 1
            or not errors[0].startswith(expected)
            or "dot(std::array<double, 2>, std::array<double, 3>)" not in errors[0]
            or "warning:" in result.stderr
        ):
            raise RuntimeError(
                "C++: изменилось ожидаемое отклонение negative.cpp:\n" + result.stderr
            )
        print(f"C++: ожидаемая несовместимость размеров массива: {file}")


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("mode", choices=("format", "check-format", "check"))
    parser.add_argument(
        "--root", type=Path, default=Path(__file__).resolve().parents[2]
    )
    options = parser.parse_args()
    root = options.root.resolve()
    files = [str(p) for p in source_files(root)]
    python = [
        p for p in files if p.startswith("handbook/examples/") and p.endswith(".py")
    ]
    cpp = [p for p in files if p.endswith((".cpp", ".cc", ".cxx", ".h", ".hpp"))]
    kotlin = [p for p in files if p.endswith((".kt", ".kts"))]
    csharp = [p for p in files if p.endswith(".cs")]
    if python:
        run(
            root,
            [
                "ruff",
                "format",
                *([] if options.mode == "format" else ["--check"]),
                *python,
            ],
        )
    if cpp:
        run(
            root,
            [
                "clang-format",
                "--style=file:tools/quality/examples-clang-format.yaml",
                *(["-i"] if options.mode == "format" else ["--dry-run", "--Werror"]),
                *cpp,
            ],
        )
    if kotlin:
        run(
            root,
            [
                "ktlint",
                *(["--format"] if options.mode == "format" else []),
                *kotlin,
            ],
        )
    for folder in sorted({str(Path(p).parent) for p in csharp}):
        run(
            root,
            [
                "dotnet",
                "format",
                "whitespace",
                "--folder",
                folder,
                *([] if options.mode == "format" else ["--verify-no-changes"]),
                "--include",
                *[Path(p).name for p in csharp if str(Path(p).parent) == folder],
            ],
        )
    if options.mode != "check":
        return
    if python:
        run(root, ["ruff", "check", *python])
        python_types(root, python)
    cpp_types(root, cpp)
    with tempfile.TemporaryDirectory(prefix="java-quality-examples-") as temporary:
        scripts = [p for p in kotlin if p.endswith(".kts")]
        if scripts:
            raise RuntimeError(
                "Kotlin script требует отдельного контекста проверки без выполнения: "
                + ", ".join(scripts)
            )
        kotlin_units: dict[str, list[str]] = {}
        for source in kotlin:
            kotlin_units.setdefault(str(Path(source).parent), []).append(source)
        for index, sources in enumerate(kotlin_units.values()):
            run(
                root,
                [
                    "kotlinc",
                    *sources,
                    "-Werror",
                    "-d",
                    str(Path(temporary) / f"kotlin-{index}.jar"),
                ],
            )
        projects: set[Path] = set()
        for source in csharp:
            directory = (root / source).parent
            candidates: list[Path] = []
            while directory.is_relative_to(root):
                candidates = sorted(directory.glob("*.csproj"))
                if candidates or directory == root:
                    break
                directory = directory.parent
            if len(candidates) != 1:
                raise RuntimeError(
                    f"C# source требует однозначного соседнего/родительского .csproj: {source}"
                )
            projects.add(candidates[0])
        for index, project in enumerate(sorted(projects)):
            target = Path(temporary) / f"csharp-{index}"
            shutil.copytree(
                project.parent, target, ignore=shutil.ignore_patterns("bin", "obj")
            )
            run(
                root,
                [
                    "dotnet",
                    "build",
                    str(target / project.name),
                    "--nologo",
                    "--verbosity",
                    "quiet",
                    "-warnaserror",
                    "-p:EnableNETAnalyzers=true",
                    "-p:RunAnalyzersDuringBuild=true",
                    "-p:AnalysisLevel=latest-all",
                    "-p:UseSharedCompilation=false",
                    "-p:BuildInParallel=false",
                ],
            )


if __name__ == "__main__":
    main()
