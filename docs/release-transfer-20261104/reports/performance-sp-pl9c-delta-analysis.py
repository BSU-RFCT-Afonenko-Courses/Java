import json,pathlib,collections,statistics,math
base=pathlib.Path('/home/tolya/JavaCodex/java-release-20261110/reports')
root=base/'performance-sp-pl9c-delta'
r=root/'runs/a4ac5976-790a-4160-bc40-45794995dea7'
old=base/'performance-sp-summary-capture/runs/b2b5cee6-0382-4326-83e3-457b113d4562'
receipts=[json.loads(f.read_text()) for f in (r/'processes').glob('*.json')]
def category(p):
 a=p['argv']
 if p['kind']=='deno':
  if '/opt/quarto/bin/quarto.js' in a:
   i=a.index('/opt/quarto/bin/quarto.js'); return 'Quarto CLI '+a[i+1]
  return 'TS entrypoint'
 if p.get('expectation',{}).get('activated'): return 'Pandoc document render + Lua'
 if '--from=json' in a:return 'Pandoc JSON→HTML'
 if '--from' in a and a[a.index('--from')+1]=='markdown':return 'Pandoc Markdown→JSON'
 return 'Pandoc JSON→GFM'
groups=collections.defaultdict(list)
for p in receipts:groups[category(p)].append(p)
rows=[];file_count=0; canonical_rows=0
for f in (r/'events').rglob('*.jsonl'):
 file_count+=1
 for line in f.open():
  if not line.strip(): continue
  e=json.loads(line);rows.append(e)
  if e.get('operation_id')=='course-prairielearn.application.source-selection.canonical':canonical_rows+=1
def uuids(pub):
 q={str(f.relative_to(pub)):json.loads(f.read_text()).get('uuid') for f in (pub/'questions').glob('*/*/info.json')}
 w={str(f.relative_to(pub)):json.loads(f.read_text()).get('uuid') for f in (pub/'courseInstances').glob('*/assessments/*/infoAssessment.json')}
 return {'questions':q,'assessments':w}
u=uuids(r/'public'); old_u=uuids(old/'public')
d=json.loads((r/'public/delivery.json').read_text());s=json.loads((r/'summary.json').read_text())
active=json.loads((root/'course/tasks/_generated/course-spec/native-ts/active.json').read_text()); native=pathlib.Path(active['directory']); complete=json.loads((native/'complete.json').read_text()); reserve=json.loads((native/'reservation.json').read_text())
htmlroot=pathlib.Path(complete['state']['outputDirectory']); htmls=[str(f.relative_to(htmlroot)) for f in htmlroot.rglob('*.html')]
finite=lambda p: isinstance(p.get('native_command',{}).get('wall_seconds'),(int,float)) and math.isfinite(p['native_command']['wall_seconds'])
result={
 'runId':r.name,'quality':s['quality'],'rawRows':len(rows),'executorJsonlFiles':file_count,'canonicalRows':canonical_rows,
 'operations':s['operations'],'processCount':len(receipts),'finiteNativeCommandDurations':sum(map(finite,receipts)),
 'processExitCodes':dict(collections.Counter(str(p['exit_code']) for p in receipts)),
 'processSignals':dict(collections.Counter(str(p['signal']) for p in receipts)),
 'durationScope':'monotonic_process; launcher-spawn-to-status; overlapping parent/child envelopes; no CPU/startup/global critical path inference',
 'categories':{k:{'count':len(ps),'minimumSeconds':min(p['native_command']['wall_seconds'] for p in ps),'medianSeconds':statistics.median(p['native_command']['wall_seconds'] for p in ps),'maximumSeconds':max(p['native_command']['wall_seconds'] for p in ps)} for k,ps in sorted(groups.items())},
 'largestIndividualNativeCommands':[{'processId':p['process_id'],'parentProcessId':p['parent_process_id'],'category':category(p),'wallSeconds':p['native_command']['wall_seconds']} for p in sorted(receipts,key=lambda p:p['native_command']['wall_seconds'],reverse=True)[:8]],
 'composition':{'questions':len(d['questions']),'gradingPayloads':len(d['gradingPayloads']),'works':len(d['works']),'instances':len(d['instances']),'fileRows':len(d['files']),'gradingMethods':dict(collections.Counter(p['grading']['method'] for p in d['gradingPayloads'].values())),'submissionModes':dict(collections.Counter(p['submission']['mode'] for p in d['gradingPayloads'].values()))},
 'stableUUIDs':{'comparisonTo':str(old/'public'),'questionCount':len(u['questions']),'assessmentCount':len(u['assessments']),'exactEqual':u==old_u,'values':u},
 'nativeComposition':{'operation':active['operation'],'documents':len(complete['documents']),'outputs':len(complete['outputs']),'releaseDocuments':len(complete['releaseDocuments']),'reservationStatus':reserve['status'],'children':len(reserve['children']),'childrenUsedPosted':all(c['used'] and c['posted'] for c in reserve['children']),'HTML':sorted(htmls)},
}
(root/'analysis.json').write_text(json.dumps(result,indent=2,ensure_ascii=False)+'\n')
print(json.dumps({k:v for k,v in result.items() if k not in ['operations','stableUUIDs']},indent=2,ensure_ascii=False))
print('stable UUIDs',result['stableUUIDs']['questionCount'],result['stableUUIDs']['assessmentCount'],result['stableUUIDs']['exactEqual'])
print('top operations',json.dumps(s['operations'][:5],indent=2))
