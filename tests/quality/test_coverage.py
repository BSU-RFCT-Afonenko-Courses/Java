"""Приёмка публичных CLI: новые исходники и отсутствие нужного инструмента."""

from __future__ import annotations

import os
import shutil
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]


class CoverageAcceptance(unittest.TestCase):
    def setUp(self) -> None:
        self.temporary = tempfile.TemporaryDirectory(prefix="quality-coverage-")
        self.addCleanup(self.temporary.cleanup)
        self.root = Path(self.temporary.name) / "course"
        self.root.mkdir()
        subprocess.run(["git", "init", "--quiet", str(self.root)], check=True)
        self.bin = Path(self.temporary.name) / "bin"
        self.bin.mkdir()
        # Настоящие системные программы; форматтеров в этом PATH заведомо нет.
        for command in ("git", "java"):
            executable = shutil.which(command)
            if executable is None:
                self.fail(f"Для приёмки нужен системный {command}")
            (self.bin / command).symlink_to(executable)

    def put(self, name: str, content: str) -> None:
        target = self.root / name
        target.parent.mkdir(parents=True, exist_ok=True)
        target.write_text(content)

    def cli(self, name: str) -> subprocess.CompletedProcess[str]:
        return subprocess.run(
            [
                sys.executable,
                str(ROOT / "tools/quality" / name),
                "check-format",
                "--root",
                str(self.root),
            ],
            cwd=self.root,
            env={**os.environ, "PATH": str(self.bin), "PYTHONDONTWRITEBYTECODE": "1"},
            text=True,
            capture_output=True,
            check=False,
            timeout=30,
        )

    def assert_missing_tool(
        self, result: subprocess.CompletedProcess[str], tool: str
    ) -> None:
        output = result.stdout + result.stderr
        self.assertNotEqual(result.returncode, 0, output)
        self.assertIn(tool, output)
        self.assertNotIn("Traceback", output)

    def seed_intentional_syntax_errors(self) -> None:
        # Сохраняем реальные отрицательные входы курса: их удаление само по себе
        # обязано останавливать проверку независимо от нового исходника.
        files = list(
            ROOT.glob("tasks/projects/**/negative-controls/compile-error/**/Study.java")
        )
        files.append(ROOT / "handbook/examples/records/rejected/ExtraState.java")
        for source in files:
            target = self.root / source.relative_to(ROOT)
            target.parent.mkdir(parents=True, exist_ok=True)
            shutil.copyfile(source, target)

    def test_new_java_outside_existing_material_directories_is_checked(self) -> None:
        self.seed_intentional_syntax_errors()
        self.put("tools/new-example/Example.java", "class Example {int value=1;}\n")
        self.assert_missing_tool(self.cli("java.py"), "google-java-format")

    def test_new_common_formats_are_checked(self) -> None:
        examples = {
            "tools/new.py": ("value=1\n", "ruff"),
            "CI/new.sh": ("#!/bin/bash\necho new\n", "shfmt"),
            "config/new.json": ('{"value":1}\n', "prettier"),
            "tools/new.lua": ("local value=1\n", "stylua"),
            "docs/api-v2.md": ("# Новый API\n", "prettier"),
            "docs/20261113-new-guide.md": ("# Новое руководство\n", "prettier"),
            "tools/build/new.py": ("value=1\n", "ruff"),
            "config/new.toml": ("value=1\n", "taplo"),
            "bibliography/new.bib": ("@book{sample, title={Example}}\n", "bibtex-tidy"),
            "bibliography/new.csl": ('<?xml version="1.0"?><style/>\n', "xmllint"),
        }
        for name, (content, tool) in examples.items():
            with self.subTest(name=name):
                self.put(name, content)
                self.assert_missing_tool(self.cli("formats.py"), tool)
                (self.root / name).unlink()

    def test_new_gradle_is_checked(self) -> None:
        self.put("tasks/projects/new/build.gradle", "plugins { id 'java' }\n")
        self.assert_missing_tool(self.cli("groovy.py"), "npm-groovy-lint")

    def test_new_comparison_program_outside_handbook_is_checked(self) -> None:
        examples = {
            "lectures/examples/new.cpp": ("int main(){return 0;}\n", "clang-format"),
            "tools/new.kt": ("fun main(){println(1)}\n", "ktlint"),
            "practice/examples/New.cs": (
                "class New {static void Main(){}}\n",
                "dotnet",
            ),
        }
        for name, (content, tool) in examples.items():
            with self.subTest(name=name):
                self.put(name, content)
                result = self.cli("examples.py")
                output = result.stdout + result.stderr
                self.assertNotEqual(result.returncode, 0, output)
                self.assertIn(tool, output)
                (self.root / name).unlink()

    def test_new_qmd_java_fence_is_checked(self) -> None:
        self.put("theory/new.qmd", "```java\nclass Example {int value=1;}\n```\n")
        self.assert_missing_tool(self.cli("qmd.py"), "google-java-format")

    def test_pandoc_java_class_fence_is_not_silently_ignored(self) -> None:
        for attributes in ("{.java}", "{#sample .java}"):
            with self.subTest(attributes=attributes):
                self.put(
                    "theory/new.qmd",
                    f"~~~{attributes}\nclass Example {{int value=1;}}\n~~~\n",
                )
                result = self.cli("qmd.py")
                self.assertNotEqual(result.returncode, 0, result.stdout + result.stderr)
                self.assertNotIn("Traceback", result.stdout + result.stderr)

    def test_java_fence_starting_a_list_item_is_not_silently_ignored(self) -> None:
        self.put("theory/new.qmd", "- ```java\n  class Example {int value=1;}\n  ```\n")
        result = self.cli("qmd.py")
        self.assertNotEqual(result.returncode, 0, result.stdout + result.stderr)
        self.assertNotIn("Traceback", result.stdout + result.stderr)

    def test_dependency_and_generated_java_are_not_authored_inputs(self) -> None:
        self.seed_intentional_syntax_errors()
        for name in (
            "theory/_extensions/example/Example.java",
            "tasks/_generated/Example.java",
            "tasks/projects/new/build/Example.java",
        ):
            self.put(name, "class Example {int value=1;}\n")
        result = self.cli("java.py")
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)


if __name__ == "__main__":
    unittest.main()
