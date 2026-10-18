"""Canary-пробы safety CLI до компилятора; живой проект не используется как work."""
import json,os,shutil,subprocess,sys,tempfile,unittest
from pathlib import Path
P=Path(__file__).resolve().parents[1]
RUNTIME=Path(os.environ['GRADEAPP_RUNTIME']).resolve()
class GuardTests(unittest.TestCase):
 def setUp(self):
  self.tmp=tempfile.TemporaryDirectory();self.addCleanup(self.tmp.cleanup);self.root=Path(self.tmp.name)
 def call(self,script,*args):
  return subprocess.run([sys.executable,str(script),'--runtime',str(RUNTIME),*map(str,args)],capture_output=True,text=True,timeout=45)
 def assertReject(self,r):
  self.assertEqual(2,r.returncode,r.stdout+r.stderr);self.assertTrue(r.stderr.strip());self.assertNotIn('Traceback',r.stderr)
 def test_same_source_work_preserves_existing_api_canary(self):
  source=self.root/'source';shutil.copytree(P/'reference/src/main/java',source);canary=source/'api/keep.txt';canary.parent.mkdir();canary.write_text('keep')
  r=self.call(P/'tools/check.py','--sources',source,'--work',source)
  self.assertReject(r);self.assertEqual('keep',canary.read_text())
 def test_work_descendant_of_sources_is_rejected_before_creation(self):
  source=self.root/'source';shutil.copytree(P/'reference/src/main/java',source);work=source/'new-work'
  r=self.call(P/'tools/check.py','--sources',source,'--work',work)
  self.assertReject(r);self.assertFalse(work.exists())
 def test_existing_foreign_work_is_not_deleted_or_overwritten(self):
  work=self.root/'foreign';canary=work/'classes/keep.txt';canary.parent.mkdir(parents=True);canary.write_text('keep')
  r=self.call(P/'tools/check.py','--work',work)
  self.assertReject(r);self.assertEqual('keep',canary.read_text())
 def test_work_cannot_be_ancestor_of_sources(self):
  work=self.root/'ancestor';source=work/'source';shutil.copytree(P/'reference/src/main/java',source)
  r=self.call(P/'tools/check.py','--sources',source,'--work',work)
  self.assertReject(r);self.assertTrue((source/'grade/Main.java').exists())
 def test_project_overlap_uses_disposable_project_copy_only(self):
  project=self.root/'project';(project/'tools').mkdir(parents=True);shutil.copyfile(P/'tools/check.py',project/'tools/check.py')
  source=self.root/'sources';shutil.copytree(P/'reference/src/main/java',source);canary=project/'tests/keep.txt';canary.parent.mkdir();canary.write_text('keep')
  r=self.call(project/'tools/check.py','--sources',source,'--work',project)
  self.assertReject(r);self.assertEqual('keep',canary.read_text())
 def test_runtime_overlap_uses_disposable_runtime_only(self):
  runtime=self.root/'runtime';runtime.mkdir();canary=runtime/'classes/keep.txt';canary.parent.mkdir();canary.write_text('keep')
  for n in ['PlatformApiPrecheck.java','json-simple-1.1.1.jar','junit-platform-console-standalone-1.14.1.jar']:shutil.copyfile(RUNTIME/n,runtime/n)
  r=subprocess.run([sys.executable,str(P/'tools/check.py'),'--runtime',str(runtime),'--work',str(runtime)],capture_output=True,text=True,timeout=45)
  self.assertReject(r);self.assertEqual('keep',canary.read_text())
 def test_unknown_case_is_not_success_and_creates_no_work(self):
  work=self.root/'unknown';r=self.call(P/'tools/check-cases.py','--work',work,'--only','typo-not-existing')
  self.assertReject(r);self.assertFalse(work.exists())
 def test_known_plus_unknown_case_is_rejected_before_any_run(self):
  work=self.root/'mixed';r=self.call(P/'tools/check-cases.py','--work',work,'--only','valid-loop-reports','--only','typo-not-existing')
  self.assertReject(r);self.assertFalse(work.exists())
 def test_existing_case_work_preserves_foreign_sources(self):
  work=self.root/'cases';canary=work/'missing-parse/sources/grade/Main.java';canary.parent.mkdir(parents=True);canary.write_text('keep')
  r=self.call(P/'tools/check-cases.py','--work',work,'--only','missing-parse')
  self.assertReject(r);self.assertEqual('keep',canary.read_text())
 def test_cases_work_under_project_is_rejected_before_creation(self):
  # Каталог проекта — только временная копия, не живой P.
  project=self.root/'project';(project/'tools').mkdir(parents=True);(project/'tests').mkdir();shutil.copyfile(P/'tools/check-cases.py',project/'tools/check-cases.py');shutil.copyfile(P/'tests/contract-cases.json',project/'tests/contract-cases.json')
  work=project/'new-work';r=self.call(project/'tools/check-cases.py','--work',work,'--only','typo-not-existing')
  self.assertReject(r);self.assertFalse(work.exists())
if __name__=='__main__':unittest.main(verbosity=2)
