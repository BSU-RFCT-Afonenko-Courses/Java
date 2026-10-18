"""Real Git guard regressions; no Quarto invocation or destination writes allowed."""
import importlib.util,json,pathlib,subprocess,tempfile,unittest
spec=importlib.util.spec_from_file_location('sync',pathlib.Path(__file__).with_name('sync-providers.py'))
sync=importlib.util.module_from_spec(spec);spec.loader.exec_module(sync)
class Guards(unittest.TestCase):
 def setUp(self):
  self.temp=tempfile.TemporaryDirectory();self.root=pathlib.Path(self.temp.name);self.repo=self.root/'repos/core';self.repo.mkdir(parents=True)
  self.git('init','-q');self.git('config','user.name','Regression');self.git('config','user.email','regression@example.invalid')
  package=self.repo/'_extensions/course-core';package.mkdir(parents=True);(package/'_extension.yml').write_text('version: 7.1.0\n')
  self.git('add','.');self.git('commit','-qm','fixture');self.sha=self.git('rev-parse','HEAD');self.git('tag','v7.1.0')
  sync.ROOT=self.root
  self.provider={'repository':'Afonenko-Course-Tools/quarto-course','checkout':'core','commit':self.sha,'tag':'v7.1.0','version':'7.1.0','sourceZip':'https://github.com/Afonenko-Course-Tools/quarto-course/archive/refs/tags/v7.1.0.zip','packages':{'course-core':['.']}}
  self.destination=self.root/'_extensions/Afonenko-Course-Tools/course-core';self.destination.mkdir(parents=True);(self.destination/'sentinel').write_text('preserve')
 def tearDown(self):self.temp.cleanup()
 def git(self,*args):return subprocess.check_output(['git',*args],cwd=self.repo,text=True,stderr=subprocess.DEVNULL).strip()
 def rejected(self):
  (self.root/'providers.json').write_text(json.dumps({'namespace':'Afonenko-Course-Tools','providers':{'core':self.provider}}))
  with self.assertRaises((ValueError,RuntimeError)):sync.sync(self.root/'repos')
  self.assertEqual((self.destination/'sentinel').read_text(),'preserve')
 def test_valid_release(self):sync.verify_release(self.repo,self.provider)
 def test_descriptor_mismatch(self):
  (self.repo/'_extensions/course-core/_extension.yml').write_text('version: 7.0.0\n');self.git('add','.');self.git('commit','-qm','wrong descriptor');self.provider['commit']=self.git('rev-parse','HEAD');self.git('tag','-f','v7.1.0');self.rejected()
 def test_missing_tag(self):self.provider.pop('tag');self.rejected()
 def test_tag_mismatch(self):self.provider['commit']='0'*40;self.rejected()
 def test_version_mismatch(self):self.provider['version']='7.0.0';self.rejected()
 def test_local_candidate_cannot_be_overwritten_by_legacy_sync(self):
  from unittest.mock import patch
  self.provider['candidate']={'status':'local-unpublished'}
  with patch.object(sync,'verify_release',side_effect=AssertionError('legacy installer entered release path')):
   self.rejected()
if __name__=='__main__':unittest.main()
