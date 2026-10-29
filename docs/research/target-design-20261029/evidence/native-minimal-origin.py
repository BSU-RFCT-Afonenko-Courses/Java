"""Четыре изолированных inspect; только исследовательский scalar origin, не producer.
Запуск: python3 native-minimal-origin.py /absolute/path/to/quarto
"""
from pathlib import Path
import copy,hashlib,json,os,subprocess,sys,tempfile
exe=str(Path(sys.argv[1]).resolve());root=Path(tempfile.mkdtemp(prefix='native-minimal-origin-'));env={**os.environ,'XDG_CACHE_HOME':str(root/'_cache')};trace='course-literal-origin-research';records=[]
for case in ['directory-value','front-matter-value']:
 d=root/case;d.mkdir();inputname='projects/task/_course-input.qmd'
 configs={'_quarto.yml':{'project':{'type':'default','render':[inputname]},'format':'html','execute':{'enabled':False},'course-project':{'semantic-name':'helper','sources':'helper'}},'projects/_metadata.yml':{'course-project':{'semantic-name':'helper','sources':'helper','selection':{'demo':True}}},'projects/task/_metadata.yml':{'course-project':{'selection':{'demo':False}}}}
 fm={'course-project':{'semantic-name':'helper'}} if case=='front-matter-value' else {}
 for name in ['helper','projects/helper','projects/task/helper']:(d/name).mkdir(parents=True,exist_ok=True)
 original={**{f:json.dumps(v,ensure_ascii=False,indent=2)+'\n' for f,v in configs.items()},inputname:'---\n'+json.dumps(fm,ensure_ascii=False,indent=2)+'\n---\n# Контроль\n'}
 for f,t in original.items():(d/f).write_text(t)
 origins={};nextid=0
 def add_marker(obj,file):
  global nextid
  result=copy.deepcopy(obj)
  if 'semantic-name' in obj.get('course-project',{}):
   nextid+=1;origins[str(nextid)]={'file':file,'field':'course-project.semantic-name','raw':obj['course-project']['semantic-name']};result[trace]={'course-project':{'semantic-name':nextid}}
  return result
 runs=[]
 for mode in ['baseline','origin-shadow']:
  if mode=='origin-shadow':
   for f,v in configs.items():(d/f).write_text(json.dumps(add_marker(v,f),ensure_ascii=False,indent=2)+'\n')
   (d/inputname).write_text('---\n'+json.dumps(add_marker(fm,inputname),ensure_ascii=False,indent=2)+'\n---\n# Контроль\n')
  command=[exe,'inspect',str(d/inputname)];r=subprocess.run(command,cwd=d,env=env,text=True,capture_output=True,timeout=30);(d/f'{mode}.out').write_text(r.stdout);(d/f'{mode}.err').write_text(r.stderr);assert r.returncode==0,r.stderr
  x=json.loads(r.stdout);m=x['formats']['html']['metadata'];runs.append({'mode':mode,'argv':command,'exit':r.returncode,'version':x['quarto']['version'],'course_project':m['course-project'],'origin_marker':m.get(trace),'stderr':r.stderr})
 assert runs[0]['course_project']==runs[1]['course_project']
 token=runs[1]['origin_marker']['course-project']['semantic-name'];origin=origins[str(token)];restored=copy.deepcopy(runs[1]['course_project']);restored['semantic-name']=origin['raw'];assert restored['sources']==runs[0]['course_project']['sources'];assert restored['semantic-name']=='helper';assert restored['selection']['demo'] is False
 records.append({'case':case,'original_fixture':original,'original_sha256':{f:hashlib.sha256(t.encode()).hexdigest() for f,t in original.items()},'runs':runs,'effective_course_unchanged_by_shadow':True,'selected_origin':origin,'typed_result_only_semantic_field_restored':restored})
summary={'root':str(root),'source_pin':'87d1078002991c4ba9cc2baa1f401a80402148af','runs_total':4,'records':records,'limits':['Только одно declared непутевое scalar-поле; пути не переписываются','Нет CST extraction, aliases, arrays, metadata-files, активных профилей и полного кодового bridge','Front matter сохраняет локальный смысл, но не решает inherited metadata без нового обязательногоQMD','Четыре inspect, без render/фильтров/hooks/execute и продукта']};(root/'summary.json').write_text(json.dumps(summary,ensure_ascii=False,indent=2)+'\n');print(json.dumps(summary,ensure_ascii=False,indent=2))
