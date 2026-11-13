"""Public QMD behavior: standard formatter, preserved prose, explicit failures."""

from __future__ import annotations

import hashlib
import importlib.util
import json
import os
import shutil
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
ADAPTER = ROOT / "tools" / "quality" / "qmd.py"
SPEC = importlib.util.spec_from_file_location("qmd_quality", ADAPTER)
qmd = importlib.util.module_from_spec(SPEC)
sys.modules[SPEC.name] = qmd
SPEC.loader.exec_module(qmd)


class FenceReaderTests(unittest.TestCase):
    def test_containers_attributes_options_and_opaque_outer_fence(self):
        source = (
            "---\ntitle: Keep this spacing\n---\n"
            "````markdown\n```java\nnot actual Java\n```\n````\n"
            "> ```{java #example echo=false}\n"
            "> //| label: unchanged-option\n"
            "> int x=1;\n> ```\n"
            "- item\n\n  ~~~java\n  return  2;\n  ~~~\n"
        )
        blocks = qmd.fences(Path("lesson.qmd"), source)
        self.assertEqual(
            [block.source for block in blocks], ["int x=1;\n", "return  2;\n"]
        )
        self.assertEqual(
            [source[b.start : b.end] for b in blocks],
            ["> int x=1;\n", "  return  2;\n"],
        )
        self.assertEqual([b.number for b in blocks], [1, 2])

    def test_pandoc_classes_and_same_line_list_fences(self):
        source = "- ~~~{#sample .java}\n  int x=1;\n  ~~~\n> - ```{.java}\n>   int y=2;\n>   ```\n"
        blocks = qmd.fences(Path("lesson.qmd"), source)
        self.assertEqual([b.source for b in blocks], ["int x=1;\n", "int y=2;\n"])
        self.assertEqual([b.prefix for b in blocks], ["  ", ">   "])

    def test_unclosed_java_fence_is_an_error(self):
        with self.assertRaisesRegex(ValueError, "unclosed Java fence"):
            qmd.fences(Path("lesson.qmd"), "```java\nint x = 1;\n")


@unittest.skipUnless(
    shutil.which("google-java-format") and shutil.which("java"),
    "JDK and google-java-format required",
)
class QmdCommandTests(unittest.TestCase):
    def setUp(self):
        self.directory = tempfile.TemporaryDirectory(prefix="qmd-quality-test-")
        self.root = Path(self.directory.name)
        subprocess.run(["git", "init", "-q", str(self.root)], check=True)

    def tearDown(self):
        self.directory.cleanup()

    def run_mode(self, mode):
        return subprocess.run(
            [sys.executable, str(ADAPTER), mode, "--root", str(self.root)],
            capture_output=True,
            text=True,
            env=os.environ.copy(),
        )

    def test_real_java25_fragments_idempotence_and_prose_preservation(self):
        before = (
            "---\ntitle: 'Keep:  exact bytes'\n---\n\nПроза  с   пробелами.\n\n"
            "> ```{java #one echo=false}\n> //| label: keep-option\n"
            '> import module java.base;\n> void main(){IO.println("Java 25");}\n> ```\n\n'
            "```python\nx=  2\n```\n\n"
            "- ~~~{#sample .java}\n  int x=1;\n  System.out.println(x);\n  ~~~\n\n"
            "```java\nstatic int twice(int x){return x*2;}\nSystem.out.println(twice(3));\n```\n\n"
            "```java\nExample(int n){this.n=n;}\n```\n\n"
            "```java\n(x,y)->x+y\n```\n"
            '```java\nString poem = """\n    One   two\n    Three\n    """;\nSystem.out.println(poem);\n```\n'
        )
        path = self.root / "new.qmd"  # Deliberately untracked: new source is covered.
        path.write_text(before)
        needs = self.run_mode("check-format")
        self.assertEqual(needs.returncode, 1, needs.stdout + needs.stderr)
        self.assertEqual(path.read_text(), before)
        formatted = self.run_mode("format")
        self.assertEqual(formatted.returncode, 0, formatted.stdout + formatted.stderr)
        after = path.read_text()
        self.assertNotEqual(after, before)
        old_blocks, new_blocks = qmd.fences(path, before), qmd.fences(path, after)
        for text, blocks in [(before, old_blocks), (after, new_blocks)]:
            for block in reversed(blocks):
                text = text[: block.start] + "<JAVA>\n" + text[block.end :]
            if text is not None:
                if blocks is old_blocks:
                    preserved = text
                else:
                    self.assertEqual(text, preserved)
        for mode in ("format", "check-format", "check"):
            result = self.run_mode(mode)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertEqual(path.read_text(), after)

    def test_reviewed_incomplete_example_is_content_addressed(self):
        source = "public int unfinished(\n"
        self.root.joinpath("lesson.qmd").write_text("```java\n" + source + "```\n")
        manifest = self.root / "tools/quality/qmd/exclusions.json"
        manifest.parent.mkdir(parents=True)
        manifest.write_text(
            json.dumps(
                {
                    "lesson.qmd#1": {
                        "sha256": hashlib.sha256(source.encode()).hexdigest(),
                        "category": "signature",
                        "reason": "Reviewed signature excerpt without body.",
                    }
                }
            )
        )
        reviewed = self.run_mode("check")
        self.assertEqual(reviewed.returncode, 0, reviewed.stdout + reviewed.stderr)
        self.assertIn("Reviewed signature", reviewed.stdout)
        self.root.joinpath("lesson.qmd").write_text(
            "```java\npublic int unfinished(int x\n```\n"
        )
        changed = self.run_mode("check")
        self.assertNotEqual(changed.returncode, 0)
        self.assertIn("stale reviewed Java exclusion", changed.stderr)
        self.root.joinpath("lesson.qmd").write_text("```java\nclass Complete {}\n```\n")
        repaired = self.run_mode("check")
        self.assertNotEqual(repaired.returncode, 0)
        self.assertIn("stale reviewed Java exclusion", repaired.stderr)

    def test_unknown_incomplete_example_fails_and_format_is_atomic(self):
        before = "```java\nint x=1;\n```\n```java\npublic int broken(\n```\n"
        path = self.root / "broken.qmd"
        path.write_text(before)
        result = self.run_mode("format")
        self.assertEqual(result.returncode, 1)
        self.assertIn("unclassified Java fragment", result.stderr)
        self.assertIn("broken.qmd#2", result.stderr)
        self.assertEqual(path.read_text(), before)


if __name__ == "__main__":
    unittest.main()
