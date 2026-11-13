"""Публичный договор ZIP: синхронизация исходников, безопасность и идемпотентность."""

from __future__ import annotations

import stat
import subprocess
import sys
import tempfile
import unittest
import zipfile
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
CLI = ROOT / "tools/quality/archives.py"


class ArchivesAcceptance(unittest.TestCase):
    def setUp(self) -> None:
        self.temporary = tempfile.TemporaryDirectory(prefix="quality-archives-")
        self.addCleanup(self.temporary.cleanup)
        self.root = Path(self.temporary.name)
        subprocess.run(["git", "init", "--quiet", str(self.root)], check=True)
        self.examples = self.root / "handbook/examples"
        self.canonical = self.examples / "topic"
        self.canonical.mkdir(parents=True)
        self.archive = self.examples / "unrelated-name.zip"
        (self.canonical / "Example.java").write_text("class Example {}\n")
        (self.canonical / "README.md").write_text("# Пример\n")

    def zip(self, entries: dict[str, str], *, symlink: str | None = None) -> None:
        with zipfile.ZipFile(self.archive, "w") as zipped:
            for name, content in entries.items():
                entry = zipfile.ZipInfo(name)
                entry.create_system = 3
                entry.external_attr = (stat.S_IFREG | 0o640) << 16
                if name == symlink:
                    entry.external_attr = (stat.S_IFLNK | 0o777) << 16
                zipped.writestr(entry, content)

    def cli(self, mode: str) -> subprocess.CompletedProcess[str]:
        return subprocess.run(
            [sys.executable, str(CLI), mode, "--root", str(self.root)],
            text=True,
            capture_output=True,
            check=False,
            timeout=30,
        )

    def assert_success(self, mode: str) -> None:
        result = self.cli(mode)
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)

    def test_regeneration_tracks_source_bytes_paths_readme_and_generated_exclusions(
        self,
    ) -> None:
        self.zip({"topic/Example.java": "stale", "topic/removed.txt": "removed"})
        for name in (
            "build/Generated.java",
            "target/data.txt",
            "out/A.class",
            "bin/a.dll",
            ".gradle/cache",
            "__pycache__/a.pyc",
            "A.class",
            "app.jar",
        ):
            target = self.canonical / name
            target.parent.mkdir(parents=True, exist_ok=True)
            target.write_text("generated")
        (self.canonical / "new.txt").write_text("new")
        external = self.root / "external.txt"
        external.write_text("outside")
        (self.canonical / "link.txt").symlink_to(external)
        for mode in ("check", "check-format"):
            before = self.archive.read_bytes()
            self.assertNotEqual(self.cli(mode).returncode, 0)
            self.assertEqual(self.archive.read_bytes(), before)
        self.assert_success("format")
        with zipfile.ZipFile(self.archive) as zipped:
            self.assertEqual(
                set(zipped.namelist()),
                {"topic/Example.java", "topic/README.md", "topic/new.txt"},
            )
            self.assertEqual(
                zipped.read("topic/README.md"),
                (self.canonical / "README.md").read_bytes(),
            )
            self.assertEqual(zipped.read("topic/Example.java"), b"class Example {}\n")
            self.assertEqual(
                stat.S_IMODE(zipped.getinfo("topic/Example.java").external_attr >> 16),
                0o640,
            )
            self.assertTrue(
                all(i.date_time == (1980, 1, 1, 0, 0, 0) for i in zipped.infolist())
            )
        stable = self.archive.read_bytes()
        self.assert_success("check")
        self.assert_success("check-format")
        self.assert_success("format")
        self.assertEqual(self.archive.read_bytes(), stable)
        # Одни и те же входы дают те же байты при повторной реальной регенерации.
        self.zip({"topic/Example.java": "stale again"})
        self.assert_success("format")
        self.assertEqual(self.archive.read_bytes(), stable)

    def test_content_change_and_deleted_source_fail_check(self) -> None:
        self.zip({"topic/Example.java": "stale"})
        self.assert_success("format")
        (self.canonical / "Example.java").write_text("class Changed {}\n")
        self.assertNotEqual(self.cli("check").returncode, 0)
        self.assert_success("format")
        (self.canonical / "README.md").unlink()
        self.assertNotEqual(self.cli("check").returncode, 0)

    def test_unsafe_unknown_multiple_prefix_and_symlink_entries_are_never_rewritten(
        self,
    ) -> None:
        cases = (
            ({"../escape.txt": "bad"}, None),
            ({"/topic/file": "bad"}, None),
            ({"topic/../escape": "bad"}, None),
            ({"topic\\escape": "bad"}, None),
            ({"unknown/file": "bad"}, None),
            ({"topic/a": "a", "other/b": "b"}, None),
            ({"topic/link": "target"}, "topic/link"),
        )
        for entries, symlink in cases:
            with self.subTest(entries=entries):
                self.zip(entries, symlink=symlink)
                before = self.archive.read_bytes()
                for mode in ("check", "check-format", "format"):
                    result = self.cli(mode)
                    self.assertNotEqual(result.returncode, 0)
                    self.assertNotIn("Traceback", result.stderr)
                    self.assertEqual(self.archive.read_bytes(), before)

    def test_missing_tracked_archive_fails(self) -> None:
        self.zip({"topic/Example.java": "stale"})
        subprocess.run(["git", "add", str(self.archive)], cwd=self.root, check=True)
        self.archive.unlink()
        self.assertNotEqual(self.cli("check").returncode, 0)

    def test_canonical_directory_symlink_is_rejected(self) -> None:
        self.zip({"linked/file": "data"})
        (self.examples / "linked").symlink_to(self.canonical, target_is_directory=True)
        self.assertNotEqual(self.cli("format").returncode, 0)


if __name__ == "__main__":
    unittest.main()
