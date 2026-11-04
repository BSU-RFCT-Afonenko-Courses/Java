import pathlib, importlib.util, json, os, time, shutil, hashlib, traceback, copy, yaml
BASE=pathlib.Path(__file__).resolve().parent
COURSE=pathlib.Path('/home/tolya/JavaCodex/java-release-20261110/java')
PLATFORM=pathlib.Path('/home/tolya/JavaCodex/java-release-20261110/platform')
os.environ['PATH']='/usr/lib/jvm/java-25-openjdk/bin:'+os.environ['PATH']
os.environ['JAVA_HOME']='/usr/lib/jvm/java-25-openjdk'
spec=importlib.util.spec_from_file_location('r1_grader',PLATFORM/'images/java25-grader/grade.py');grader=importlib.util.module_from_spec(spec);spec.loader.exec_module(grader)
feedback=__import__('runpy').run_path(str(PLATFORM/'runtime/student_feedback.py'))['student_result']
registry=json.loads((PLATFORM/'runtime-profiles.json').read_text());profile=registry['profiles']['java25-junit-v1'];bundle=PLATFORM/profile['bundle']
sha=lambda p:hashlib.sha256(p.read_bytes()).hexdigest()
profile['image']='unit.invalid/source-research@sha256:'+'1'*64
profile['files']={p.relative_to(bundle).as_posix():sha(p) for p in sorted(bundle.rglob('*')) if p.is_file()}
profile['runnerSha256']=sha(PLATFORM/'images/java25-grader/grade.py')
profile['sharedFiles']={p:sha(PLATFORM/p) for p in ['runtime/student_feedback.py','runtime/junit_result.py']}
(BASE/'isolated-runtime-registry.json').write_text(json.dumps(registry,indent=2)+'\n')
config=yaml.safe_load((COURSE/'tasks/_quarto.yml').read_text())
def copy_sources(source,dest):
 for p in source.rglob('*.java'):
  target=dest/p.relative_to(source);target.parent.mkdir(parents=True,exist_ok=True);shutil.copy2(p,target)
def run_project(name,variant,label=None,mutate=None):
 project=COURSE/'tasks/projects'/name;job=BASE/'jobs'/name.replace('/','-')/(label or variant.replace('/','-'));job.mkdir(parents=True,exist_ok=False)
 student=job/'student';tests=job/'tests';student.mkdir();tests.mkdir()
 copy_sources(project/variant/'src/main/java',student)
 if mutate:mutate(student)
 for source in [project/'observations/src/test/java',project/'checks/src/test/java',project/'api/src/api/java',COURSE/'tasks/projects/common/java/api-support/src/api/java']:copy_sources(source,tests)
 p=config['project-checks']['profiles']['java-'+name.replace('/','-')]
 cfg={'schemaVersion':1,'sourceFiles':[f.relative_to(student).as_posix() for f in student.rglob('*.java')],'testFiles':[f.relative_to(tests).as_posix() for f in tests.rglob('*.java')],'mode':'implementation','runtime':'java25-junit-v1','java':{'release':25,'encoding':'UTF-8','compiler-options':['-proc:none','-Xmaxerrs','5']},'limits':{'outer-seconds':60,'compile-seconds':15,'run-seconds':5,'max-output-bytes':65536,'networking':False},'scoring':{'mode':'weighted'},'discovery':{'min-executed':1,'allow-skipped':False},'apiPrecheck':{'files':['ApiContract.java','org/prairielearn/api/PlatformApiPrecheck.java'],'entry':'ApiContract'},'publicCases':[{({'what-check':'whatCheck'}.get(k,k)):v for k,v in c.items()} for c in p.get('public-cases',[])],'feedback':{'requirement':p['feedback']['requirement'],'whatCheck':p['feedback']['what-check']}}
 (tests/'grading-job.json').write_text(json.dumps(cfg,ensure_ascii=False,indent=2)+'\n')
 started=time.monotonic();calls=[];original=grader._run
 def traced(command,*args,**kwargs):
  t=time.monotonic();result=original(command,*args,**kwargs);calls.append({'argv':list(map(str,command)),'seconds':time.monotonic()-t,'exit':result[0],'reason':result[2],'output':result[1]});return result
 grader._run=traced
 try:
  result=grader.grade(job,registry=registry);native=feedback(result,cfg);out={'sourceRuntime':'source-isolated-java25-junit-v1','courseDeliveryRuntime':'java25-gradle-junit-v1','excludes':['Gradle/JAR delivery','OCI containment','real Native server/UI'],'seconds':time.monotonic()-started,'result':result,'native':native}
 except Exception as e:out={'seconds':time.monotonic()-started,'error':type(e).__name__,'message':str(e),'trace':traceback.format_exc()}
 finally:grader._run=original
 (job/'result.json').write_text(json.dumps(out,ensure_ascii=False,indent=2)+'\n');(job/'execution.json').write_text(json.dumps(calls,ensure_ascii=False,indent=2)+'\n')
 print(json.dumps({'project':name,'variant':variant,'seconds':round(out['seconds'],3),'error':out.get('error'),'classification':out.get('result',{}).get('classification'),'score':out.get('result',{}).get('score'),'counts':{k:out.get('result',{}).get('counts',{}).get(k) for k in ['discovered','executed','passed','failed']},'message':out.get('message')},ensure_ascii=False),flush=True)
 return out
if __name__=='__main__':
 import signal, math
 def timeout(signum, frame):
  raise TimeoutError('Circle source-runtime scenario exceeded allowed wall time')
 signal.signal(signal.SIGALRM,timeout)
 started=time.monotonic()
 for variant in ['reference','starter','negative-controls/wrong','alternatives/namespace']:
  remaining=180-(time.monotonic()-started)
  if remaining<=0: raise TimeoutError('Circle total limit 180s exceeded')
  signal.alarm(min(60,math.ceil(remaining)))
  try:
   out=run_project('polymorphism/circle-subclass',variant)
  finally:
   signal.alarm(0)
  if out.get('error'):
   raise RuntimeError('Scenario failed as infrastructure; no duplicate scenarios run')
 (BASE/'wall-time.json').write_text(json.dumps({'seconds':time.monotonic()-started,'scenarioLimitSeconds':60,'totalLimitSeconds':180,'parallelism':1},indent=2)+'\n')
