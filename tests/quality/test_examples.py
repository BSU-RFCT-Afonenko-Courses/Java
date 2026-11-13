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


if __name__ == "__main__":
    unittest.main()
