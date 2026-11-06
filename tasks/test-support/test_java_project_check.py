"""Настоящие локальные проверки orchestration и фаз; JDK25, Platform из окружения."""
import json,os,subprocess,sys,tempfile,unittest
from pathlib import Path
from json_contracts import read_record
RUNNER=Path(__file__).with_name('check-java-project.py')
PLATFORM=Path(os.environ.get('PL_PLATFORM_ROOT','/missing-platform'))
class RunnerTests(unittest.TestCase):
    def setUp(self) -> None:
        self.temp=tempfile.TemporaryDirectory();self.root=Path(self.temp.name)
        self.project=self.root/'project';self.sources=self.root/'source';self.binding=self.root/'binding';self.work=self.root/'work'
        for p in [self.project/'tests/junit/public',self.sources,self.binding]:p.mkdir(parents=True)
        (self.sources/'Solution.java').write_text('public class Solution { public static int value(){return 42;} }')
        (self.binding/'TaskBinding.java').write_text('class TaskBinding { static int value(){return Solution.value();} }')
        (self.project/'tests/junit/public/ExampleTest.java').write_text('import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;class ExampleTest{@Test void value(){assertEquals(42,TaskBinding.value());}}')
    def tearDown(self) -> None:self.temp.cleanup()
    def run_check(self,*extra: str) -> subprocess.CompletedProcess[str]:
        return subprocess.run([sys.executable,str(RUNNER),'--project',str(self.project),'--sources',str(self.sources),'--binding',str(self.binding),'--work',str(self.work),'--platform',str(PLATFORM),*extra],capture_output=True,text=True,timeout=90)
    def receipt(self) -> dict[str,object]:return read_record(self.work/'report.json')
    def test_unknown_filter_fails_before_writes(self) -> None:
        run=self.run_check('--only','public/Typo.java');self.assertNotEqual(run.returncode,0);self.assertFalse(self.work.exists());self.assertIn('неизвест',run.stderr)
    def test_symlink_source_fails_before_writes(self) -> None:
        (self.sources/'Alias.java').symlink_to(self.sources/'Solution.java');run=self.run_check();self.assertNotEqual(run.returncode,0);self.assertFalse(self.work.exists());self.assertIn('symlink',run.stderr)
    def test_existing_work_is_preserved(self) -> None:
        self.work.mkdir();(self.work/'keep').write_text('KEEP');run=self.run_check();self.assertNotEqual(run.returncode,0);self.assertEqual((self.work/'keep').read_text(),'KEEP')
    def test_malformed_platform_receipt_is_infrastructure_failure_without_traceback(self) -> None:
        platform=self.root/'platform';runner=platform/'images/java25-grader/grade.py';runner.parent.mkdir(parents=True)
        (platform/'runtime-profiles.json').write_text('{}')
        runner.write_text('import json,pathlib,sys\njob=pathlib.Path(sys.argv[sys.argv.index("--job-dir")+1])\n(job/"results").mkdir()\n(job/"results/results.json").write_text("[1]")\n')
        run=self.run_check('--platform',str(platform))
        self.assertEqual(1,run.returncode,run.stdout+run.stderr)
        self.assertNotIn('Traceback',run.stderr)
        self.assertEqual('infrastructure-failure',self.receipt()['classification'])
        self.assertNotIn('score',self.receipt())
    def test_compilation_failure_is_not_binding_or_behavior(self) -> None:
        (self.sources/'Solution.java').write_text('public class Solution { invalid }');self.run_check();v=self.receipt();self.assertEqual(v['classification'],'student-compilation-failure');self.assertTrue(v['gradable']);self.assertEqual(0,v['score']);self.assertEqual('complete',v['infrastructure']);self.assertEqual('failure',v['studentCompilation']);self.assertEqual('not-run',v['trustedCompilation']);self.assertEqual(v['dependentExecution'],'not-run')
    def test_wrong_binding_is_manual_without_score(self) -> None:
        (self.binding/'TaskBinding.java').write_text('class TaskBinding { static int value(){return Solution.missing();} }');self.run_check();v=self.receipt();self.assertEqual(v['classification'],'binding-setup-failure');self.assertTrue(v['manualRequired']);self.assertNotIn('score',v);self.assertEqual(v['dependentExecution'],'not-run')
    def test_api_mismatch_precedes_dependent_compile(self) -> None:
        api=self.root/'api';api.mkdir()
        (api/'PublishedApi.java').write_text('import org.prairielearn.api.PlatformApiPrecheck;public class PublishedApi{public static void check(PlatformApiPrecheck.Api a)throws Exception{a.method("Solution","missing",int.class,0,0);}}')
        config=api/'config.json';config.write_text(json.dumps({'files':['PublishedApi.java'],'entry':'PublishedApi'}))
        (self.binding/'TaskBinding.java').write_text('this deliberately cannot compile')
        self.run_check('--api-config',str(config));v=self.receipt();self.assertEqual(v['classification'],'student-api-mismatch');self.assertEqual(v['studentCompilation'],'success');self.assertEqual(v['trustedCompilation'],'not-run');self.assertEqual(v['dependentExecution'],'not-run');self.assertEqual('failure',v['apiPrecheck']);self.assertEqual('complete',v['infrastructure']);self.assertTrue(v['gradable']);self.assertEqual(0,v['score'])
    def test_work_inside_sources_is_rejected(self) -> None:
        self.work=self.sources/'work';run=self.run_check();self.assertNotEqual(run.returncode,0);self.assertFalse(self.work.exists())
    def test_wrong_behavior_and_correct_solution(self) -> None:
        self.run_check();v=self.receipt();self.assertEqual(v['classification'],'success');self.assertEqual(v['score'],1);self.assertTrue(v['sourceUnchanged'])
        self.work=self.root/'wrong-work';(self.sources/'Solution.java').write_text('public class Solution { public static int value(){return 41;} }');self.run_check();v=self.receipt();self.assertEqual(v['classification'],'behavior-failure');self.assertEqual(v['score'],0)
if __name__=='__main__':unittest.main()
