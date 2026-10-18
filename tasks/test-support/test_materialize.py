"""Проверки реального CLI на временных проектах; без изменения учебных данных."""
import json
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

SCRIPT = Path(__file__).with_name('materialize.py')


class MaterializeTests(unittest.TestCase):
    def setUp(self):
        self.temporary = tempfile.TemporaryDirectory(prefix='support-materialize-')
        self.addCleanup(self.temporary.cleanup)
        self.root = Path(self.temporary.name)
        self.support = self.root / 'tasks/test-support'
        self.source = self.support / 'src/main/java/coursesupport/Runner.java'
        self.source.parent.mkdir(parents=True)
        self.source.write_text('package coursesupport; class Runner {}\n')
        self.api = self.support / 'api/CliPrecheck.java'
        self.api.parent.mkdir()
        self.api.write_text('class CliPrecheck {}\n')
        (self.support / 'projects.json').write_text(json.dumps(['sample']))
        self.public = self.root / 'tasks/projects/sample/student/src/test/java'
        self.trusted = self.root / 'tasks/projects/sample/tests/junit'
        self.public.mkdir(parents=True)
        self.trusted.mkdir(parents=True)

    def command(self, mode):
        return subprocess.run([sys.executable, str(SCRIPT), '--root', str(self.root), mode],
                              capture_output=True, text=True, timeout=10)

    def test_check_is_read_only_and_write_supplies_both_partitions(self):
        self.assertNotEqual(0, self.command('--check').returncode)
        self.assertFalse((self.public / 'coursesupport').exists())
        result = self.command('--write')
        self.assertEqual(0, result.returncode, result.stderr)
        for parent in [self.public, self.trusted]:
            self.assertEqual(self.source.read_bytes(), (parent / 'coursesupport/Runner.java').read_bytes())
        self.assertEqual(0, self.command('--check').returncode)

    def test_precheck_is_delivered_separately_and_local_edit_preserved(self):
        result = self.command('--write')
        self.assertEqual(0, result.returncode, result.stderr)
        for parent in [self.public, self.trusted]:
            target = parent / 'api/CliPrecheck.java'
            self.assertTrue(target.is_file(), 'API precheck delivery is missing')
            self.assertEqual(self.api.read_bytes(), target.read_bytes())
        protected = self.trusted / 'api/CliPrecheck.java'
        protected.write_text('local API edit\n')
        old_public = (self.public / 'coursesupport/Runner.java').read_bytes()
        self.source.write_text('new library bytes\n')
        result = self.command('--write')
        self.assertNotEqual(0, result.returncode)
        self.assertEqual('local API edit\n', protected.read_text())
        self.assertEqual(old_public, (self.public / 'coursesupport/Runner.java').read_bytes())

    def test_precheck_symlink_rejected_before_any_copy(self):
        outside = self.root / 'outside-api'
        outside.mkdir()
        (self.trusted / 'api').symlink_to(outside, target_is_directory=True)
        self.assertNotEqual(0, self.command('--write').returncode)
        self.assertEqual([], list(outside.iterdir()))
        self.assertFalse((self.public / 'coursesupport').exists())

    def test_source_update_is_detected_then_replaces_only_known_generated_bytes(self):
        self.assertEqual(0, self.command('--write').returncode)
        self.source.write_text('package coursesupport; class Runner { int updated; }\n')
        self.assertNotEqual(0, self.command('--check').returncode)
        result = self.command('--write')
        self.assertEqual(0, result.returncode, result.stderr)
        self.assertEqual(self.source.read_bytes(), (self.public / 'coursesupport/Runner.java').read_bytes())

    def test_local_edit_stops_all_writes_before_other_partition_changes(self):
        self.assertEqual(0, self.command('--write').returncode)
        target = self.trusted / 'coursesupport/Runner.java'
        target.write_text('local user edit\n')
        previous = (self.public / 'coursesupport/Runner.java').read_bytes()
        self.source.write_text('new canonical bytes\n')
        result = self.command('--write')
        self.assertNotEqual(0, result.returncode)
        self.assertEqual('local user edit\n', target.read_text())
        self.assertEqual(previous, (self.public / 'coursesupport/Runner.java').read_bytes())

    def test_symlink_destination_is_rejected_without_writing_outside(self):
        outside = self.root / 'outside'
        outside.mkdir()
        (self.trusted / 'coursesupport').symlink_to(outside, target_is_directory=True)
        self.assertNotEqual(0, self.command('--write').returncode)
        self.assertEqual([], list(outside.iterdir()))
        self.assertFalse((self.public / 'coursesupport').exists())


if __name__ == '__main__':
    unittest.main()
