#!/usr/bin/env python3
"""Разовая read-only приёмка фактической выдачи; не producer и не freshness registry."""
import json, pathlib, re, stat, subprocess, time, uuid, zipfile
import yaml
from html.parser import HTMLParser
from collections import Counter
START=time.perf_counter()
BASE=pathlib.Path('/tmp/java-release-work-20261105'); ROOT=BASE/'full-course-public'; JAVA=BASE/'course-java'
COORD=pathlib.Path('/tmp/java-release-discussion-20261105/coordination'); BASELINE='c2e35e7ed3ec4ca2412dd66c9d71d9ce94e6290c'
def load(p):return json.loads(pathlib.Path(p).read_text())
D=load(ROOT/'delivery.json'); C=load(BASE/'full-course-checks.json'); COVER=load(JAVA/'docs/releases/2026-11-05-course-coverage.json'); AUTHOR=load(COORD/'course-author-submissions.json')
findings=[]; checks=Counter(); details={}
def check(ok,label,**evidence):
 checks[label]+=1
 if not ok:findings.append({'check':label,**evidence})
def safe(s):
 return isinstance(s,str) and bool(s) and not s.startswith('/') and not re.search(r'[\\\x00:]',s) and all(x not in ('','.','..') for x in s.split('/'))
def closed_name(name):return bool(re.search(r'(?:ContractTest|PrivateTest)\.java$|(?:Historical|Hidden).*\.java$|.*HiddenProbe\.java$',pathlib.PurePosixPath(name).name))
def git(path):return subprocess.check_output(['git','show',BASELINE+':'+path],cwd=JAVA,text=True)
def front(text):return yaml.safe_load(text.split('---',2)[1])
def merged(a,b):
 out=dict(a)
 for k,v in b.items():out[k]=merged(out[k],v) if isinstance(v,dict) and isinstance(out.get(k),dict) else v
 return out
class Controls(HTMLParser):
 def __init__(self):super().__init__();self.uploads=[];self.downloads=[];self.editors=[]
 def handle_starttag(self,tag,attrs):
  a=dict(attrs)
  if tag=='pl-file-upload':self.uploads.append(a)
  if tag=='pl-file-download':self.downloads.append(a.get('file-name'))
  if tag in ('pl-file-editor','pl-code-editor'):self.editors.append(a)
expected={q['questionKey']:q for q in COVER['questions']};auth={q['sourceID']:q for q in AUTHOR['questions']}
check(D['schemaVersion']==5 and C['schemaVersion']==3,'functional_schema_versions')
check(len(expected)==128 and len(auth)==128 and len(D['questions'])==128 and len(set(D['questions']))==128 and set(D['questions'])==set(expected),'question_id_coverage')
check(len(D['works'])==10 and len({w['id'] for w in D['works']})==10,'ten_unique_labs')
check(set(D['gradingPayloads'])==set(expected),'grading_payload_coverage')
actual_qdirs={str(p.parent.relative_to(ROOT/'questions')) for p in (ROOT/'questions').rglob('info.json')};check(actual_qdirs==set(expected),'no_extra_question_directories')
instance=D['instances'];check(list(instance)==['2026-2027-s1'] and set(instance['2026-2027-s1']['works'])=={w['id'] for w in D['works']},'only_ten_lab_routes')
assessments={p.parent.name:load(p) for p in ROOT.glob('courseInstances/*/assessments/*/infoAssessment.json')};check(set(assessments)=={w['id'] for w in D['works']} and not any('bank' in key or 'unit' in key for key in assessments),'no_extra_bank_assessments')
check(all(isinstance(p,str) and safe(p) for p in D['files']) and len(set(D['files']))==len(D['files']),'public_manifest_names_not_proofs')
actual_files={str(p.relative_to(ROOT)) for p in ROOT.rglob('*') if p.is_file()};check(set(D['files'])==actual_files-{'delivery.json'},'public_manifest_name_coverage')
rootdefaults=yaml.safe_load(git('tasks/_quarto.yml'))['prairielearn']['assessment-defaults']
required=[];optional=[];slots_count=0;lab_policies=[];all_assessment_ids=[]
for work in D['works']:
 lab=work['id']; current_rows=[q for q in COVER['questions'] if q['labId']==lab];labpath=current_rows[0]['labSource'];oldtext=git(labpath);old=front(oldtext)['assessment']['prairielearn'];policy=merged(rootdefaults,old);actual=work['policy'];req=[s for s in work['slots'] if s['assignment']['requirement']=='required'];opt=[s for s in work['slots'] if s['assignment']['requirement']=='optional'];slots_count+=len(req)
 # Старый список task-items имеет только требуемые слоты, включая choice pools.
 oldslots=[]
 for block in re.findall(r'::: \{\.task-items[^\n]*\}\n(.*?)\n:::',oldtext,re.S):
  for line in block.splitlines():
   if not re.match(r'\s*\d+\.',line):continue
   ids=re.findall(r'@(?:tasks:)?(exr-[\w-]+)',line)
   if not ids:continue
   m=re.search(r'#(alt-[\w-]+)',line);oldslots.append((m.group(1) if m else ids[0],ids,'choice' if m else 'fixed'))
 check([(s['id'].split('/')[-1],[q.split('/')[-1] for q in s['candidates']],s['selection']) for s in req]==oldslots,'required_slot_identity_baseline',lab=lab)
 for key in ('attempts','pass','max-points','auto-points','manual-points','question-points','question-max-points','uuid'):
  if key in policy:check(actual.get(key)==policy[key],'lab_policy_baseline',lab=lab,field=key,expected=policy[key],actual=actual.get(key))
 amap={q['id']:q for z in assessments[lab]['zones'] for q in z['questions']};all_assessment_ids.extend(amap)
 check(len(amap)==sum(len(s['candidates']) for s in work['slots']),'assessment_candidate_count',lab=lab)
 check(assessments[lab]['maxPoints']==policy['max-points'],'assessment_max_points_baseline',lab=lab)
 title=re.search(r'^# (.*?) \{#'+re.escape(lab)+r'\}',(JAVA/labpath).read_text(),re.M);check(title is not None and assessments[lab]['title']==title.group(1),'lab_visible_title',lab=lab)
 for slot in work['slots']:
  sid=slot['id'].split('/')[-1];isreq=slot['assignment']['requirement']=='required';(required if isreq else optional).extend(slot['candidates'])
  own=policy.get('slot-overrides',{}).get(sid,{}) if isreq else {'auto-points':0,'manual-points':0}
  now=actual.get('slot-overrides',{}).get(sid,{})
  if isreq:
   check(now==policy.get('slot-overrides',{}).get(sid,{}),'required_slot_weights_baseline',lab=lab,slot=sid)
  else:check(now.get('auto-points')==0 and now.get('manual-points')==0,'optional_policy_zero_components',lab=lab,slot=sid)
  for key in slot['candidates']:
   q=amap.get(key,{});check(expected[key]['labId']==lab and expected[key]['requirement']==('required' if isreq else 'optional'),'coverage_lab_requirement',key=key)
   check(q.get('triesPerVariant')==policy['attempts'],'assessment_attempts_baseline',key=key)
   for field,native in [('auto-points','autoPoints'),('manual-points','manualPoints')]:
    want=own.get(field,policy.get(field)) if isreq else 0
    if want is not None:check(q.get(native)==want,'assessment_weight_baseline' if isreq else 'optional_assessment_zero_components',key=key,field=native,expected=want,actual=q.get(native))
 lab_policies.append({'lab':lab,'requiredSlots':len(req),'requiredCandidates':sum(len(s['candidates']) for s in req),'optionalCandidates':sum(len(s['candidates']) for s in opt),'attempts':actual['attempts'],'pass':actual['pass'],'maxPoints':actual['max-points'],'baseline':'exact inherited attempts/pass/required weights and slot identities'})
check(slots_count==35 and len(required)==41 and len(set(required))==41 and len(optional)==87 and len(set(optional))==87 and set(required).isdisjoint(optional) and set(required+optional)==set(expected),'35_required_41_candidates_87_optional')
check(len(all_assessment_ids)==128 and len(set(all_assessment_ids))==128 and set(all_assessment_ids)==set(expected),'all_questions_only_through_ten_labs')
uuids=[];titles=[];layouts=Counter();zipfiles=0;zipentries=0;zipbytes=0;public_api=0;public_demo=0;privatecontract_patterns={};question_details=[]
for project in C['projects']:
 privatecontract_patterns[project['qualifiedId']]=[__import__('base64').b64decode(f['data'],validate=True) for f in project['trustedTests'] if closed_name(f['name'])]
for key,row in expected.items():
 qroot=ROOT/'questions'/key;info=load(qroot/'info.json');html=(qroot/'question.html').read_text();payload=D['gradingPayloads'][key];ident=row['questionId'];a=auth[ident];control=Controls();control.feed(html)
 uuids.append(info['uuid']);titles.append(info['title']);check(info['uuid']==row['questionUuid']==payload['uuid'],'question_uuid_deterministic',key=key)
 check(info['title']==row['title']==a['title'],'question_title_exact',key=key)
 layout=row['submission']['layout'];layouts[layout]+=1
 check(payload['submission']=={'mode':'upload','layout':layout} and not control.editors and len(control.uploads)==1,'actual_html_upload_layout',key=key)
 if layout=='zip':check(control.uploads[0].get('file-names')=='solution.zip' and a['archiveOnly'],'zip_only_control',key=key)
 else:
  upload=control.uploads[0];manual=payload['grading']['method']=='manual'
  accepted=(upload.get('file-patterns')=='*' and upload.get('optional-file-patterns')=='*' and not upload.get('file-names')) if manual else (upload.get('optional-file-patterns')=='*.java' and upload.get('optional-file-names')=='additional-sources.zip' and not upload.get('file-names'))
  check(layout=='files-or-zip' and accepted and a['mode']=='files-or-zip','files_or_zip_control',key=key)

 actual_names=[name.removeprefix('src/main/java/') for name in control.downloads]
 check(sorted(a['starterFiles'])==sorted(row['submission']['starterFiles']) and (set(a['starterFiles']).issubset(actual_names) if payload['grading']['method']=='manual' else sorted(control.downloads)==sorted(a['starterFiles'])),'exact_starter_names_no_Main_rename',key=key,actual=control.downloads,expected=a['starterFiles'])
 client=qroot/'clientFilesQuestion';archive=client/'starter.zip';readme=client/'starter/README.md';check(archive.is_file() and readme.is_file() and readme.stat().st_size>0 and 'starter.zip' in html and 'starter/README.md' in html,'starter_zip_readme_links',key=key)
 with zipfile.ZipFile(archive) as z:
  zipfiles+=1;names=z.namelist();zipentries+=len(names);zipbytes+=sum(f.file_size for f in z.infolist());check(len(names)==len(set(names)) and all(safe(n.rstrip('/')) for n in names),'zip_safe_unique_paths',key=key)
  check(z.testzip() is None,'zip_crc',key=key)
  check('README.md' in names and z.read('README.md')==readme.read_bytes(),'zip_readme_exact_bytes',key=key)
  for f in z.infolist():
   name=f.filename;parts=pathlib.PurePosixPath(name).parts;mode=f.external_attr>>16
   check(not stat.S_ISLNK(mode) and not any(p in ('author','reference','solutions','solution','private','grading-notes') for p in parts) and not closed_name(name),'student_zip_privacy_paths',key=key,path=name)
   if f.is_dir():continue
   data=z.read(f);mirror=client/'starter'/name;check(mirror.is_file() and mirror.read_bytes()==data,'zip_matches_public_student_tree',key=key,path=name)
   check(not any(data==closed for closed in privatecontract_patterns.get(key,[])),'no_closed_contract_test_bytes_in_student_zip',key=key,path=name)
   public_api+=name.startswith('src/api/java/');public_demo+=pathlib.PurePosixPath(name).name=='DemoTest.java'
  check(all('src/main/java/'+n in names for n in a['starterFiles']),'starter_source_paths_preserved',key=key)
 for p in client.rglob('*'):
  check(not p.is_symlink(),'student_public_no_symlinks',key=key,path=str(p.relative_to(client)))
  if p.is_file():check(not any(part in ('author','reference','private','solutions') for part in p.relative_to(client).parts) and not closed_name(p.name),'student_public_privacy_paths',key=key,path=str(p.relative_to(client)))
 question_details.append({'key':key,'uuid':info['uuid'],'title':info['title'],'layout':layout,'starterFiles':control.downloads})
check(len(set(uuids))==128 and len(set(titles))==128,'unique_question_uuids_titles');check(layouts=={'files-or-zip':126,'zip':2},'126_files_or_zip_2_zip')
privateids={p['qualifiedId'] for p in C['projects']};runtimeids={k for k,v in D['gradingPayloads'].items() if 'runtime' in v};manualids=set(expected)-privateids
check(len(C['projects'])==118 and len(privateids)==118 and privateids==runtimeids,'118_actual_check_projects')
check(len(manualids)==10,'ten_manual_projects')
for key in manualids:
 p=D['gradingPayloads'][key];i=load(ROOT/'questions'/key/'info.json');check(p['grading']['method']=='manual' and 'runtime' not in p and i['gradingMethod']=='Manual' and 'externalGradingOptions' not in i and not (ROOT/'questions'/key/'tests/grading-job.json').exists(),'manual_without_fake_runtime',key=key)
for p in C['projects']:
 check(p['check']['runtime'] and p['trustedTests'] and p['sources'],'nonempty_real_project_runtime_inputs',key=p['qualifiedId'])
 def inspect(v):
  if isinstance(v,dict):
   check(not set(v).intersection({'sha256','rawSource','sourceRows','sourceSnapshotHash','inventoryHash','selectionScopeHash','verificationInventoryHash'}),'private_functional_no_old_proof_fields',key=p['qualifiedId'])
   for value in v.values():inspect(value)
  elif isinstance(v,list):
   for value in v:inspect(value)
 inspect(p)
result={'status':'PASS' if not findings else 'FAIL','purpose':'Разовая детерминированная приёмка уже созданной полной выдачи; не freshness registry и не новый render/test run.','inputs':{'public':str(ROOT),'private':str(BASE/'full-course-checks.json'),'coverage':str(JAVA/'docs/releases/2026-11-05-course-coverage.json'),'approvedAuthorMapResolved':str(COORD/'course-author-submissions.json'),'authorMapRequestedAlias':'approvedcourse-author-submissions.json','baseline':BASELINE,'actualRenderSourceReportedByCoordinator':'8e9d618','laterSourceDeltaReportedByCoordinator':'859: только Download request path, не учебные bytes'},'counts':{'questions':len(expected),'uniqueUuids':len(set(uuids)),'uniqueTitles':len(set(titles)),'labs':len(assessments),'requiredSlots':slots_count,'requiredCandidates':len(required),'optionalCandidates':len(optional),'layouts':dict(layouts),'starterZips':zipfiles,'starterZipEntries':zipentries,'uncompressedStarterBytes':zipbytes,'publicApiEntriesIntentionallyAllowed':public_api,'publicDemoTestsIntentionallyAllowed':public_demo,'privateCheckProjects':len(privateids),'manualWithoutRuntime':len(manualids),'manifestFileNames':len(D['files'])},'labPolicies':lab_policies,'manualHtmlControlInterpretation':{'questions':sorted(manualids),'control':'file-patterns=* optional-file-patterns=*','meaning':'Свободный ручной ответ принимает отдельные файлы и ZIP без обязательного имени; downloads сохраняют полные пути student, включая публичную поддержку. Для118checked проверен явный *.java/additional-sources.zip; для2archive-only solution.zip.','uuidRule':'Проверяются явно утверждённые канонические coverage UUID, payload и actual info.json; генерация новых UUID из question key не требуется.'},'checks':dict(checks),'findings':findings,'cost':{'wallSeconds':round(time.perf_counter()-START,3),'renderOrCompileOrTestRepeated':False,'scope':'JSON + exact baseline git blobs + HTML controls + 128 ZIP CRC/public student paths/bytes; не D consumer full-package повтор'},'limitations':['Смысловая авторская приёмка переиспользуется; сами условия и педагогика заново не оцениваются.','Hosted CI, Docker/UI, production и D consumer full-package не проверялись этой процедурой.'],'admission':'pl-tests-admission.json'}
(COORD/'java-full-export-verification.json').write_text(json.dumps(result,ensure_ascii=False,indent=2));print(json.dumps({'status':result['status'],'counts':result['counts'],'wallSeconds':result['cost']['wallSeconds'],'findings':findings[:12]},ensure_ascii=False,indent=2))
