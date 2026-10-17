#!/usr/bin/env python3
"""Локальная teacher-проверка: компиляция → канонический API preflight → JUnit."""
import argparse,hashlib,json,os,shutil,subprocess,time,uuid
from pathlib import Path
PROJECT=Path(__file__).resolve().parents[1]
p=argparse.ArgumentParser(description=__doc__)
p.add_argument('--sources',type=Path,default=PROJECT/'reference/src/main/java')
p.add_argument('--runtime',type=Path,required=True,help='platform/runtime/java25-junit-v1')
p.add_argument('--work',type=Path,required=True,help='отдельный каталог артефактов вне исходников')
p.add_argument('--java-home',type=Path,default=Path(os.environ.get('JAVA_HOME','/usr/lib/jvm/java-25-openjdk')))
p.add_argument('--public-only',action='store_true')
a=p.parse_args();work=a.work.resolve();sources=a.sources.resolve();runtime=a.runtime.resolve()
# Только новый каталог. До всех записей проверяются оба направления overlap,
# в том числе после разрешения symlink в пользовательских аргументах.
for protected in (PROJECT.resolve(),sources,runtime):
 if work==protected or work.is_relative_to(protected) or protected.is_relative_to(work):
  p.error('work пересекается с project/sources/runtime; нужен отдельный новый каталог')
if a.work.is_symlink() or work.exists():p.error('work уже существует; выберите новый каталог, существующие файлы не удаляются')
if not sources.is_dir() or not list(sources.rglob('*.java')):p.error('sources должен содержать Java-исходники')
for name in ('PlatformApiPrecheck.java','json-simple-1.1.1.jar','junit-platform-console-standalone-1.14.1.jar'):
 if not (runtime/name).is_file():p.error('неполный runtime: '+name)
try:work.mkdir(parents=True,exist_ok=False)
except OSError as error:p.error('не удалось создать новый work: '+str(error))
commands=[]
def digest(path):return hashlib.sha256(path.read_bytes()).hexdigest()
source_hashes={str(f.relative_to(sources)):digest(f) for f in sorted(sources.rglob('*.java'))}
report={'status':'incomplete','sourceHashesBefore':source_hashes,'commands':commands,'dependentTestsCompiled':False}
def finish(category,ok=False):
 report.update(status='PASS' if ok else 'FAIL',classification=category,sourceHashesAfter={str(f.relative_to(sources)):digest(f) for f in sorted(sources.rglob('*.java'))})
 assert report['sourceHashesBefore']==report['sourceHashesAfter'],'источники изменились во время проверки'
 (work/'report.json').write_text(json.dumps(report,ensure_ascii=False,indent=2)+'\n')
 print(category,flush=True);raise SystemExit(0 if ok else 1)
def run(name,argv):
 started=time.monotonic();r=subprocess.run(list(map(str,argv)),cwd=work,capture_output=True,text=True,timeout=90)
 commands.append(dict(name=name,argv=list(map(str,argv)),cwd=str(work),exitCode=r.returncode,stdout=r.stdout,stderr=r.stderr,elapsedSeconds=time.monotonic()-started))
 (work/(name+'.log')).write_text(r.stdout+r.stderr)
 return r
java=a.java_home/'bin/java';javac=a.java_home/'bin/javac';classes=work/'classes';api=work/'api';tests=work/'tests';scratch=work/'scratch'
for d in [classes,api,tests,scratch]:
 d.mkdir()
jar=runtime/'junit-platform-console-standalone-1.14.1.jar';jsonjar=runtime/'json-simple-1.1.1.jar';helper=runtime/'PlatformApiPrecheck.java'
report['runtimeHashes']={str(x.name):digest(x) for x in [jar,jsonjar,helper]}
flags=['--release','25','-encoding','UTF-8','-proc:none']
r=run('student-compile',[javac,*flags,'-d',classes,*sorted(sources.rglob('*.java'))])
if r.returncode:finish('student-compilation-failure')
r=run('api-compile',[javac,*flags,'-cp',jsonjar,'-d',api,helper,PROJECT/'tests/api/GradeAppApi.java'])
if r.returncode:finish('infrastructure-failure')
request=work/'api-request.json';result=work/'api-result.json';signature=uuid.uuid4().hex
request.write_text(json.dumps(dict(classes=str(classes),scratch=str(scratch),entry='GradeAppApi',result_file=str(result),signature=signature)))
r=run('api-preflight',[java,'-cp',str(api)+os.pathsep+str(jsonjar),'org.prairielearn.api.PlatformApiPrecheck',request])
if r.returncode or not result.exists():finish('infrastructure-failure')
preflight=json.loads(result.read_text());report['apiPreflight']=preflight
if preflight.get('signature')!=signature:finish('infrastructure-failure')
if preflight['status']!='success':finish('api-mismatch' if preflight['status']=='mismatch' else 'infrastructure-failure')
testfiles=sorted((PROJECT/'student/src/test/java').rglob('*.java'))
if not a.public_only:testfiles+=sorted((PROJECT/'tests/junit').rglob('*.java'))
r=run('dependent-test-compile',[javac,*flags,'-cp',str(classes)+os.pathsep+str(jar),'-d',tests,*testfiles])
report['dependentTestsCompiled']=r.returncode==0
if r.returncode:finish('dependent-test-compilation-failure')
r=run('behavior',[java,'-ea','-jar',jar,'execute','--class-path',str(classes)+os.pathsep+str(tests),'--scan-class-path','--disable-banner','--disable-ansi-colors','--details=tree','--reports-dir',work/'junit-reports'])
finish('correct' if r.returncode==0 else 'behavior-failure',r.returncode==0)
