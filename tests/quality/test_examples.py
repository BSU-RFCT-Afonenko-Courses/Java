"""Публичный check отклоняет неверный формат корректной C++ программы."""

from __future__ import annotations

import shutil
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]


class ExampleCheckAcceptance(unittest.TestCase):
    @unittest.skipUnless(
        shutil.which("clang-format") and shutil.which("g++"), "Нужны clang-format и g++"
    )
    def test_check_rejects_valid_cpp_with_bad_format_without_rewriting(self) -> None:
        with tempfile.TemporaryDirectory(prefix="quality-example-format-") as temporary:
            root = Path(temporary)
            subprocess.run(["git", "init", "--quiet", str(root)], check=True)
            config = root / "tools/quality/examples-clang-format.yaml"
            config.parent.mkdir(parents=True)
            shutil.copyfile(ROOT / config.relative_to(root), config)
            shutil.copyfile(
                ROOT / "tools/quality/examples-clang-tidy.yaml",
                config.parent / "examples-clang-tidy.yaml",
            )
            source = root / "lectures/examples/new.cpp"
            source.parent.mkdir(parents=True)
            original = "int main(){return 0;}\n"
            source.write_text(original)
            # Программа корректна для того же компилятора, который вызывает check.
            subprocess.run(
                ["g++", "-std=c++20", "-fsyntax-only", str(source)], check=True
            )
            command = [
                sys.executable,
                str(ROOT / "tools/quality/examples.py"),
                "check",
                "--root",
                str(root),
            ]
            failed = subprocess.run(
                command, text=True, capture_output=True, check=False
            )
            self.assertNotEqual(failed.returncode, 0, failed.stdout + failed.stderr)
            self.assertIn("clang-format", failed.stdout + failed.stderr)
            self.assertEqual(source.read_text(), original)
            subprocess.run(
                ["clang-format", "--style=file:" + str(config), "-i", str(source)],
                check=True,
            )
            passed = subprocess.run(
                command, text=True, capture_output=True, check=False
            )
            self.assertEqual(passed.returncode, 0, passed.stdout + passed.stderr)
            source.write_text("int main() { int *value = nullptr; return *value; }\n")
            subprocess.run(
                ["clang-format", "--style=file:" + str(config), "-i", str(source)],
                check=True,
            )
            unsafe = subprocess.run(
                command, text=True, capture_output=True, check=False
            )
            self.assertNotEqual(unsafe.returncode, 0)
            self.assertIn(
                "clang-analyzer-core.NullDereference", unsafe.stdout + unsafe.stderr
            )

    def test_new_csharp_file_is_checked_by_sdk_analyzers(self) -> None:
        with tempfile.TemporaryDirectory(
            prefix="quality-csharp-analysis-"
        ) as temporary:
            root = Path(temporary)
            subprocess.run(["git", "init", "--quiet", temporary], check=True)
            folder = root / "examples/csharp"
            shutil.copytree(ROOT / "handbook/examples/generics/csharp", folder)
            bug = folder / "Bug.cs"
            bug.write_text("""using System;
internal static class Bug
{
    internal static void Run()
    {
        try { throw new InvalidOperationException(); }
        catch (InvalidOperationException error) { throw error; }
    }
}
""")
            subprocess.run(
                [
                    "dotnet",
                    "format",
                    "whitespace",
                    "--folder",
                    str(folder),
                    "--include",
                    "Bug.cs",
                ],
                check=True,
            )
            command = [
                sys.executable,
                str(ROOT / "tools/quality/examples.py"),
                "check",
                "--root",
                temporary,
            ]
            rejected = subprocess.run(
                command, text=True, capture_output=True, check=False
            )
            self.assertNotEqual(rejected.returncode, 0)
            self.assertIn("CA2200", rejected.stdout + rejected.stderr)
            bug.write_text(
                bug.read_text()
                .replace(
                    "catch (InvalidOperationException error)",
                    "catch (InvalidOperationException)",
                )
                .replace("throw error;", "throw;")
            )
            passed = subprocess.run(
                command, text=True, capture_output=True, check=False
            )
            self.assertEqual(passed.returncode, 0, passed.stdout + passed.stderr)


if __name__ == "__main__":
    unittest.main()
