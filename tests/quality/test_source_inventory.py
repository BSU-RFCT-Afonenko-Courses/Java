"""Git-контекст копии курса: реальный helper и публичные quality CLI."""

from __future__ import annotations

import json
import os
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
INVENTORY = (
    "import json,sys; from pathlib import Path; "
    "sys.path.insert(0,sys.argv[1]); from common import source_files; "
    "print(json.dumps([p.as_posix() for p in source_files(Path(sys.argv[2]))]))"
)


class SourceInventoryAcceptance(unittest.TestCase):
    def setUp(self) -> None:
        temporary = tempfile.TemporaryDirectory(prefix="quality-git-context-")
        self.addCleanup(temporary.cleanup)
        self.base = Path(temporary.name)
        self.root = self.base / "course"
        self.root.mkdir()
        self.env = {
            name: value
            for name, value in os.environ.items()
            if not name.startswith("GIT_")
        }
        self.env["PYTHONDONTWRITEBYTECODE"] = "1"
        self.git("init", "--quiet", str(self.root))

    def git(self, *args: str, root: Path | None = None) -> str:
        return subprocess.check_output(
            ["git", *args], cwd=root or self.root, env=self.env, text=True
        ).strip()

    def inventory(
        self, root: Path, env: dict[str, str] | None = None
    ) -> subprocess.CompletedProcess[str]:
        return subprocess.run(
            [sys.executable, "-c", INVENTORY, str(ROOT / "tools/quality"), str(root)],
            cwd=root,
            env=env or self.env,
            capture_output=True,
            text=True,
            check=False,
            timeout=30,
        )

    def cli(
        self, root: Path, name: str = "formats.py"
    ) -> subprocess.CompletedProcess[str]:
        return subprocess.run(
            [
                sys.executable,
                str(ROOT / "tools/quality" / name),
                "check" if name == "semantic.py" else "check-format",
                "--root",
                str(root),
            ],
            cwd=root,
            env=self.env,
            capture_output=True,
            text=True,
            check=False,
            timeout=30,
        )

    def assert_refused(
        self, result: subprocess.CompletedProcess[str], marker: str
    ) -> None:
        output = result.stdout + result.stderr
        self.assertNotEqual(result.returncode, 0, output)
        self.assertIn(marker, output)
        self.assertNotIn("Traceback", output)

    def test_public_cli_refuses_copy_without_git(self) -> None:
        copied = self.base / "copy"
        copied.mkdir()
        (copied / "README.md").write_text("# Copy\n")
        self.assert_refused(self.cli(copied), "Git")

    def test_public_cli_refuses_ancestor_git_even_with_visible_sources(self) -> None:
        copied = self.root / "copy"
        copied.mkdir()
        (copied / "README.md").write_text("# Copy\n")
        self.assert_refused(self.cli(copied), "Корень Git")

    def test_public_cli_refuses_ignored_copy_in_ancestor_git(self) -> None:
        (self.root / ".git/info/exclude").write_text("state/\n")
        copied = self.root / "state/source"
        copied.mkdir(parents=True)
        (copied / "README.md").write_text("# Copy\n")
        self.assertEqual(
            self.git("ls-files", "--others", "--exclude-standard", root=copied), ""
        )
        self.assert_refused(self.cli(copied), "Корень Git")

    def test_helper_refuses_empty_git_inventory(self) -> None:
        self.assert_refused(self.inventory(self.root), "Пустой Git")

    def test_helper_refuses_inventory_with_only_ignored_sources(self) -> None:
        (self.root / ".git/info/exclude").write_text("*.md\n")
        (self.root / "README.md").write_text("# Ignored\n")
        self.assert_refused(self.inventory(self.root), "Пустой Git")

    def test_helper_preserves_tracked_ignored_and_new_sources(self) -> None:
        (self.root / ".git/info/exclude").write_text("tracked.md\n")
        (self.root / "tracked.md").write_text("# Tracked\n")
        self.git("add", "--force", "tracked.md")
        (self.root / "tracked.md").write_text("# Current dirty bytes\n")
        (self.root / "new.md").write_text("# Untracked\n")
        result = self.inventory(self.root)
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        self.assertEqual(json.loads(result.stdout), ["new.md", "tracked.md"])

    def test_helper_accepts_linked_worktree_git_file(self) -> None:
        (self.root / "README.md").write_text("# Course\n")
        self.git("add", "README.md")
        self.git(
            "-c",
            "user.name=Quality Test",
            "-c",
            "user.email=quality@example.invalid",
            "-c",
            "core.hooksPath=/dev/null",
            "commit",
            "--quiet",
            "-m",
            "fixture",
        )
        worktree = self.base / "linked worktree"
        self.git("worktree", "add", "--quiet", "--detach", str(worktree), "HEAD")
        self.assertTrue((worktree / ".git").is_file())
        (worktree / "new.md").write_text("# Current new source\n")
        result = self.inventory(worktree)
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        self.assertEqual(json.loads(result.stdout), ["README.md", "new.md"])

    def test_helper_ignores_foreign_git_environment_and_index(self) -> None:
        (self.root / "allowed.md").write_text("# Own\n")
        (self.root / "ignored.md").write_text("# Ignored by own repository\n")
        (self.root / ".git/info/exclude").write_text("ignored.md\n")
        foreign = self.base / "foreign"
        foreign.mkdir()
        self.git("init", "--quiet", str(foreign))
        (foreign / "ignored.md").write_text("# Foreign tracked name\n")
        self.git("add", "ignored.md", root=foreign)
        result = self.inventory(
            self.root,
            {
                **self.env,
                "GIT_DIR": str(foreign / ".git"),
                "GIT_WORK_TREE": str(self.root),
                "GIT_INDEX_FILE": str(foreign / ".git/index"),
            },
        )
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        self.assertEqual(json.loads(result.stdout), ["allowed.md"])

    def test_public_semantic_cli_allows_empty_language_subsets(self) -> None:
        (self.root / "README.md").write_text("# Course\n")
        result = self.cli(self.root, "semantic.py")
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)


if __name__ == "__main__":
    unittest.main()
