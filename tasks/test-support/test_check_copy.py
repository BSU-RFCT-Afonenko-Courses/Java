"""Canary/selection guards для специализированного checker копирования."""
import hashlib,json,shutil,subprocess,sys,tempfile,unittest
from pathlib import Path
from json_contracts import items, read_record, record
SCRIPT=Path(__file__).with_name('check-copy.py');PROJECT=SCRIPT.parents[1]/'projects/files/byte-file-copy'
class GuardTests(unittest.TestCase):
 def setUp(self) -> None:
  self.tmp=tempfile.TemporaryDirectory();self.addCleanup(self.tmp.cleanup);self.root=Path(self.tmp.name)
  self.project=self.root/'copy-project';(self.project/'tests').mkdir(parents=True)
  (self.project/'tests/input.txt').write_bytes(b'A B\n');(self.project/'tests/expected.txt').write_bytes(b'A-B\n')
  cases=[{'id':identifier,'scope':scope,'kind':'copy','input':'tests/input.txt','expected':'tests/expected.txt'} for identifier,scope in [('public-text','public'),('large','private')]]
  (self.project/'tests/cases.json').write_text(json.dumps({'cases':cases}))
 def call(self,work: Path,*extra: str | Path,project: Path | None = None,sources: Path | None = None) -> subprocess.CompletedProcess[str]:
  return subprocess.run([sys.executable,str(SCRIPT),'--project',str(project or self.project),'--sources',str(sources or PROJECT/'student'),'--entry','Main','--work',str(work),*map(str,extra)],capture_output=True,text=True,timeout=20)
 def reject(self,result: subprocess.CompletedProcess[str]) -> None:self.assertEqual(2,result.returncode,result.stdout+result.stderr);self.assertTrue(result.stderr.strip());self.assertNotIn('Traceback',result.stderr)
 def test_existing_work_preserves_canary(self) -> None:
  work=self.root/'foreign';work.mkdir();marker=work/'keep';marker.write_bytes(b'KEEP\x00\xff');self.reject(self.call(work));self.assertEqual(b'KEEP\x00\xff',marker.read_bytes())
 def test_same_work_sources_preserves_every_byte(self) -> None:
  source=self.root/'source';source.mkdir();(source/'Main.java').write_text('class Main {}');(source/'keep').write_bytes(b'KEEP');before={p.name:hashlib.sha256(p.read_bytes()).hexdigest() for p in source.iterdir()};self.reject(self.call(source,sources=source));self.assertEqual(before,{p.name:hashlib.sha256(p.read_bytes()).hexdigest() for p in source.iterdir()})
 def test_work_descendant_of_sources_not_created(self) -> None:
  source=self.root/'source';source.mkdir();(source/'Main.java').write_text('class Main { void main(String[] args) {} }');work=source/'new';self.reject(self.call(work,sources=source));self.assertFalse(work.exists())
 def test_work_ancestor_of_sources_not_changed(self) -> None:
  source=self.root/'source';source.mkdir();(source/'Main.java').write_text('class Main {}');self.reject(self.call(self.root,sources=source));self.assertEqual('class Main {}',(source/'Main.java').read_text())
 def test_disposable_project_overlap(self) -> None:
  project=self.root/'project';shutil.copytree(PROJECT,project);(project/'keep').write_bytes(b'KEEP');self.reject(self.call(project/'new',project=project));self.assertFalse((project/'new').exists());self.assertEqual(b'KEEP',(project/'keep').read_bytes())
 def test_disposable_runtime_overlap(self) -> None:
  java=shutil.which('java');javac=shutil.which('javac');self.assertIsNotNone(java);self.assertIsNotNone(javac)
  if java is None or javac is None:raise AssertionError('Для guard проверки нужны java/javac')
  runtime=self.root/'jdk';(runtime/'bin').mkdir(parents=True);shutil.copy2(Path(java).resolve(),runtime/'bin/java');shutil.copy2(Path(javac).resolve(),runtime/'bin/javac');(runtime/'keep').write_bytes(b'KEEP');self.reject(self.call(runtime/'new','--java',runtime/'bin/java','--javac',runtime/'bin/javac'));self.assertFalse((runtime/'new').exists());self.assertEqual(b'KEEP',(runtime/'keep').read_bytes())
 def test_unknown_filter_creates_no_work(self) -> None:
  work=self.root/'unknown';self.reject(self.call(work,'--only','typo-not-existing'));self.assertFalse(work.exists())
 def test_mixed_filter_creates_no_work(self) -> None:
  work=self.root/'mixed';self.reject(self.call(work,'--only','public-text','--only','typo-not-existing'));self.assertFalse(work.exists())
 def test_private_id_unavailable_in_public_selection(self) -> None:
  work=self.root/'private';self.reject(self.call(work,'--public','--only','large'));self.assertFalse(work.exists())
 def test_empty_selection_manifest_not_success(self) -> None:
  project=self.root/'project';(project/'tests').mkdir(parents=True);(project/'tests/cases.json').write_text(json.dumps({'cases':[]}));work=self.root/'empty';self.reject(self.call(work,project=project));self.assertFalse(work.exists())
 def test_malformed_case_shape_is_rejected_before_work_without_traceback(self) -> None:
  project=self.root/'malformed';(project/'tests').mkdir(parents=True)
  malformed_manifests: list[object] = [[],{'cases':[42]},{'cases':[{'id':'copy','kind':'copy','scope':'public','input':42,'expected':'expected.txt'}]}]
  for number,manifest in enumerate(malformed_manifests):
   with self.subTest(manifest=manifest):
    (project/'tests/cases.json').write_text(json.dumps(manifest))
    work=self.root/('malformed-work-'+str(number))
    self.reject(self.call(work,project=project));self.assertFalse(work.exists())
 def test_application_deleting_staged_input_is_behavior_not_infrastructure(self) -> None:
  source=self.root/'app';source.mkdir();(source/'DeletesInput.java').write_text('import java.nio.file.*; class DeletesInput {void main(String[] a)throws Exception{Path i=Path.of(a[0]),o=Path.of(a[1]);byte[] b=Files.readAllBytes(i);for(int n=0;n<b.length;n++)if(b[n]==32)b[n]=45;Files.write(o,b);Files.delete(i);}}')
  work=self.root/'proof';r=self.call(work,'--entry','DeletesInput','--only','public-text',sources=source)
  self.assertEqual(1,r.returncode,r.stdout+r.stderr);report=read_record(work/'report.json');self.assertEqual('behavior-failure',report['classification']);self.assertTrue(report['sourceUnchanged']);case=record(items(report['cases'],'cases')[0],'case');self.assertFalse(case['passed']);self.assertFalse(case['inputUnchanged'])
if __name__=='__main__':unittest.main(verbosity=2)
