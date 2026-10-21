"""Курсовой adapter только передаёт параметры единственному Core assembler."""
import importlib.util
from pathlib import Path
import tempfile
import unittest
from unittest.mock import patch
spec=importlib.util.spec_from_file_location('materialize',Path(__file__).with_name('materialize.py'));module=importlib.util.module_from_spec(spec);spec.loader.exec_module(module)
class MaterializeTests(unittest.TestCase):
 def test_delegate_exact_scope_and_return_failure(self):
  with tempfile.TemporaryDirectory() as d:
   root=Path(d);entry=root/'_extensions/course-core/entrypoints/materialize-projects.ts';entry.parent.mkdir(parents=True);entry.write_text('// Core')
   args=['--root',d,'--model',d+'/run.json','--output-root',d+'/external','--audience','grading','--teacher-root',d+'/teacher','--task','exr-one','--task','exr-two','--check']
   with patch.object(module.subprocess,'run') as run:
    run.return_value.returncode=7;self.assertEqual(module.main(args),7)
    command=run.call_args.args[0];self.assertEqual(command[:4],['quarto','run',str(entry),'--']);self.assertEqual(command[-1],'--check');self.assertEqual(command.count('--task'),2);self.assertIn('--teacher-root',command)
   self.assertFalse((root/'external').exists())
 def test_no_installed_owner_fails_before_command(self):
  with tempfile.TemporaryDirectory() as d,patch.object(module.subprocess,'run') as run:
   with self.assertRaises(ValueError):module.main(['--root',d,'--model',d+'/run.json','--output-root',d+'/stage','--audience','student','--task','exr-one','--write'])
   run.assert_not_called()
if __name__=='__main__':unittest.main()
