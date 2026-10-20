import unittest,tempfile,subprocess,sys
from pathlib import Path
SCRIPT=Path(__file__).with_name("materialize.py")
class MaterializeTests(unittest.TestCase):
 def setUp(self):
  self.tmp=tempfile.TemporaryDirectory();self.addCleanup(self.tmp.cleanup);self.root=Path(self.tmp.name)
  for path in ["api/QueueApi.java","queuecheck/QueueAccess.java"]:
   f=self.root/"support"/path;f.parent.mkdir(parents=True,exist_ok=True);f.write_text("original "+path)
 def run_cli(self):return subprocess.run([sys.executable,str(SCRIPT),"--root",str(self.root),"--write"],capture_output=True,text=True)
 def test_delivers_public_trusted_and_preserves_local_edit_before_any_write(self):
  r=self.run_cli();self.assertEqual(0,r.returncode,r.stderr)
  target=self.root/"tests/junit/api/QueueApi.java";target.write_text("local")
  other=self.root/"student/src/test/java/queuecheck/QueueAccess.java";before=other.read_bytes()
  (self.root/"support/queuecheck/QueueAccess.java").write_text("updated")
  r=self.run_cli();self.assertNotEqual(0,r.returncode);self.assertEqual("local",target.read_text());self.assertEqual(before,other.read_bytes())
 def test_symlink_destination_rejected_before_other_delivery(self):
  outside=self.root/"outside";outside.mkdir();target=self.root/"tests/junit";target.mkdir(parents=True);(target/"api").symlink_to(outside,target_is_directory=True)
  r=self.run_cli();self.assertNotEqual(0,r.returncode);self.assertEqual([],list(outside.iterdir()));self.assertFalse((self.root/"student/src/test/java").exists())
if __name__=="__main__":unittest.main()
