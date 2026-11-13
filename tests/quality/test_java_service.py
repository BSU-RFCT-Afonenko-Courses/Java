"""Служебный PMD и реальный положительный Gradle-комплект."""

from __future__ import annotations

import os
import shutil
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]


class JavaServiceAcceptance(unittest.TestCase):
    def test_new_service_java_is_checked_by_pmd(self) -> None:
        with tempfile.TemporaryDirectory(prefix="quality-pmd-test-") as temporary:
            root = Path(temporary)
            subprocess.run(["git", "init", "--quiet", temporary], check=True)
            (root / "tools").mkdir()
            source = root / "tools/NewService.java"
            source.write_text(
                "class NewService { boolean broken(Object x) { return x == null && x.toString().isEmpty(); } }\n"
            )
            command = [
                sys.executable,
                str(ROOT / "tools/quality/pmd.py"),
                "check",
                "--root",
                temporary,
            ]
            broken = subprocess.run(
                command, text=True, capture_output=True, check=False
            )
            self.assertNotEqual(broken.returncode, 0, broken.stdout + broken.stderr)
            self.assertIn("BrokenNullCheck", broken.stdout + broken.stderr)
            source.write_text(
                "class NewService { boolean repaired(Object x) { return x != null && x.toString().isEmpty(); } }\n"
            )
            repaired = subprocess.run(
                command, text=True, capture_output=True, check=False
            )
            self.assertEqual(repaired.returncode, 0, repaired.stdout + repaired.stderr)
            isolated = root / "bin"
            isolated.mkdir()
            git = shutil.which("git")
            self.assertIsNotNone(git)
            (isolated / "git").symlink_to(str(git))
            missing = subprocess.run(
                command,
                text=True,
                capture_output=True,
                check=False,
                env={**os.environ, "PATH": str(isolated)},
            )
            self.assertNotEqual(missing.returncode, 0)
            self.assertIn("pmd", missing.stdout + missing.stderr)

    def test_gradle_classes_compiles_positive_and_rejects_bad_positive(self) -> None:
        project = ROOT / "tasks/projects/generics/generic-interface"
        with tempfile.TemporaryDirectory(
            prefix="quality-generic-interface-"
        ) as temporary:
            root = Path(temporary) / "project"
            shutil.copytree(project / "reference", root)
            shutil.copyfile(project / "build.gradle", root / "build.gradle")
            env = {
                **os.environ,
                "GRADLE_USER_HOME": str(Path(temporary) / "gradle-home"),
            }
            command = [
                "gradle",
                "--no-daemon",
                "--offline",
                "--max-workers=1",
                "--console=plain",
                "classes",
            ]
            clean = subprocess.run(
                command,
                cwd=root,
                env=env,
                text=True,
                capture_output=True,
                check=False,
                timeout=90,
            )
            self.assertEqual(clean.returncode, 0, clean.stdout + clean.stderr)
            self.assertTrue((root / "build/classes/java/positive/Good.class").is_file())
            self.assertFalse(list((root / "build/classes").rglob("Bad.class")))
            positive = root / "positive/src/main/java/Good.java"
            positive.write_text(
                positive.read_text().replace("return value;", "return 42;")
            )
            rejected = subprocess.run(
                command,
                cwd=root,
                env=env,
                text=True,
                capture_output=True,
                check=False,
                timeout=90,
            )
            self.assertNotEqual(rejected.returncode, 0)
            self.assertIn("compilePositiveJava", rejected.stdout + rejected.stderr)
            self.assertIn("incompatible types", rejected.stdout + rejected.stderr)


if __name__ == "__main__":
    unittest.main()
