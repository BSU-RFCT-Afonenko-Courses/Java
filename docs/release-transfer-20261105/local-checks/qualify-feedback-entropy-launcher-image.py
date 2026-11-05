from pathlib import Path
import json,subprocess,time,shutil,os
fixtures=Path('/tmp/java-release-work-20261105/feedback-fixtures')
root=Path('/tmp/java-release-work-20261105/feedback-entropy-launcher-jobs');root.mkdir(exist_ok=True)
image='codex/java-feedback-gradle:20261105'
image_id=subprocess.run(['docker','image','inspect',image,'--format','{{.Id}}'],capture_output=True,text=True,check=True).stdout.strip()
records=[]
for case in ['circle']:
 job=root/case
 if job.exists():raise RuntimeError('Refuse to replace an existing qualification job: '+str(job))
 job.mkdir()
 for part in ['tests','student']:shutil.copytree(fixtures/case/part,job/part)
 start=time.monotonic()
 run=subprocess.run(['docker','run','--rm','--name','feedback-launcher-'+case+'-20261105','--network','none','--cpus','0.9','--memory','512m','--pids-limit','128','--mount','type=bind,src='+str(job)+',dst=/grade',image],capture_output=True,text=True,timeout=120)
 elapsed=time.monotonic()-start
 (job/'container-output.log').write_text(run.stdout+run.stderr)
 native=json.loads((job/'results/results.json').read_text())
 assert 'PRIVATE_' not in json.dumps(native)+run.stdout+run.stderr,(case,'private value leaked')
 expected={'circle':('behavior-failure',2/6),'string':('behavior-failure',0),'compile':('student-compilation-failure',0),'runtime':('student-runtime-failure',0),'toolerror':('infrastructure-failure',None)}[case]
 record={'case':case,'image':image_id,'exit_code':run.returncode,'classification':native.get('classification'),'score':native.get('score'),'gradable':native.get('gradable'),'native_card_count':len(native.get('tests',[])),'wall_seconds':round(elapsed,3),'results':str(job/'results/results.json'),'buildEvidence':native.get('buildEvidence'),'durations':native.get('feedback',{}).get('durations',{})}
 records.append(record)
 print(json.dumps(record,ensure_ascii=False),flush=True)
 assert native.get('classification')==expected[0],record
 assert native.get('score')==expected[1],record
 if case=='circle':
  assert len(native['tests'])==6
  assert any('Действие: Circle(2)' in card['message'] and 'Допуск:' in card['message'] for card in native['tests'])
 if case=='string':
  assert 'Ожидается: Первая строка\n' in native['tests'][0]['message']
  assert 'Строка: 2' in native['tests'][0]['message']
 if case=='compile':assert native['feedback']['compileErrors'][0]['file']=='Main.java'
 if case=='toolerror':assert native['gradable'] is False and 'score' not in native
Path('/tmp/java-release-discussion-20261105/coordination/feedback-entropy-launcher-qualification.json').write_text(json.dumps({'image':image_id,'source_commit':'b792911 + daemon startup environment fix under review','checks':records},ensure_ascii=False,indent=2))
