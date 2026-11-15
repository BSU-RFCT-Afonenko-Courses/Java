"""Публичный семантический CLI: реальные анализаторы, новые входы и отказы."""

from __future__ import annotations

import os
import shutil
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]


class SemanticAcceptance(unittest.TestCase):
    def setUp(self) -> None:
        self.temporary = tempfile.TemporaryDirectory(prefix="quality-semantic-")
        self.addCleanup(self.temporary.cleanup)
        self.root = Path(self.temporary.name)
        subprocess.run(["git", "init", "--quiet", str(self.root)], check=True)

    def put(self, path: str, text: str) -> None:
        target = self.root / path
        target.parent.mkdir(parents=True, exist_ok=True)
        target.write_text(text)

    def cli(self, path: str | None = None) -> subprocess.CompletedProcess[str]:
        return subprocess.run(
            [
                sys.executable,
                str(ROOT / "tools/quality/semantic.py"),
                "check",
                "--root",
                str(self.root),
            ],
            env={**os.environ, **({"PATH": path} if path else {})},
            text=True,
            capture_output=True,
            check=False,
            timeout=60,
        )

    def test_new_files_are_checked_and_repaired_sources_pass(self) -> None:
        examples = {
            "lectures/new.qmd": (
                "# Заголовок\n\n#### Пропуск\n",
                "heading-hierarchy",
                "# Заголовок\n\n## Раздел\n",
            ),
            "config/new.yml": ("key: 1\nkey: 2\n", "key-duplicates", "key: 1\n"),
            "config/new.json": (
                '{"key":1,"key":2}',
                "Повторный ключ JSON",
                '{"key":1}',
            ),
            "config/new.toml": ("key=1\nkey=2\n", "duplicate", "key=1\n"),
            "styles/new.scss": (
                "$tone: #abc; p { unknown-property: $tone; }",
                "property-no-unknown",
                "$tone: #abc; p { color: $tone; }",
            ),
            "styles/new.css": (
                "p { color: #ggg; }",
                "color-no-invalid-hex",
                "p { color: #abc; }",
            ),
            "_includes/new.html": (
                '<div id="one" id="two"></div>',
                "no-dup-attr",
                '<div id="one"></div>',
            ),
            ".github/workflows/new.yml": (
                "on: push\njobs:\n  demo:\n    runs-on: ubuntu-latest\n    steps:\n      - run: echo ${{ github.does-not-exist }}\n",
                "does-not-exist",
                "on: push\njobs:\n  demo:\n    runs-on: ubuntu-latest\n    steps:\n      - run: echo ok\n",
            ),
            "CI/new.sh": (
                "#!/bin/bash\nif then\n",
                "syntax error",
                "#!/bin/bash\necho ok\n",
            ),
        }
        for name, (broken, diagnostic, repaired) in examples.items():
            with self.subTest(name=name):
                self.put(name, broken)
                result = self.cli()
                self.assertNotEqual(result.returncode, 0, result.stdout + result.stderr)
                self.assertIn(diagnostic, result.stdout + result.stderr)
                self.put(name, repaired)
                result = self.cli()
                self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
                (self.root / name).unlink()

    def test_dependency_and_history_are_excluded(self) -> None:
        for name in (
            "_extensions/new/no.qmd",
            "_generated/no.qmd",
            "docs/research/no.qmd",
        ):
            self.put(name, "# Heading\n\n#### Skip\n")
        result = self.cli()
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)

    def test_missing_and_crashing_tool_fail_closed(self) -> None:
        self.put("lectures/new.qmd", "# Тема\n")
        isolated = self.root / "bin"
        isolated.mkdir()
        git = shutil.which("git")
        self.assertIsNotNone(git)
        (isolated / "git").symlink_to(str(git))
        result = self.cli(str(isolated))
        self.assertNotEqual(result.returncode, 0)
        self.assertIn("panache", result.stdout + result.stderr)
        tool = isolated / "panache"
        tool.write_text("#!/bin/sh\necho failed >&2\nexit 2\n")
        tool.chmod(0o755)
        result = self.cli(str(isolated))
        self.assertNotEqual(result.returncode, 0)
        self.assertIn("failed", result.stdout + result.stderr)


if __name__ == "__main__":
    unittest.main()
