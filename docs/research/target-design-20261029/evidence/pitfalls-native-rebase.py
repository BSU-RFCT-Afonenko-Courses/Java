from pathlib import Path
import json,os,subprocess,tempfile
root=Path(tempfile.mkdtemp(prefix='pitfalls-native-nonpath-'));exe='/home/tolya/course-tools/local-tools/quarto-1.11.5/bin/quarto';env={**os.environ,'XDG_CACHE_HOME':str(root/'_cache')}
files={'_quarto.yml':'project:\n  type: default\n  render: [sub/task/doc.qmd]\nformat: html\nexecute:\n  enabled: false\n','sub/_metadata.yml':'course-project:\n  semantic-name: helper\n  sources: helper\n  selection: {demo: true}\n  choices: [demo]\n','sub/task/_metadata.yml':'course-project:\n  selection: {demo: false}\n  choices: []\n','sub/task/doc.qmd':'# Контроль\n'}
for n,t in files.items():
 p=root/n;p.parent.mkdir(parents=True,exist_ok=True);p.write_text(t)
runs=[]
for exists in [False,True]:
 if exists:(root/'sub/helper').mkdir()
 cmd=[exe,'inspect',str(root/'sub/task/doc.qmd')];r=subprocess.run(cmd,cwd=root,env=env,text=True,capture_output=True,timeout=30)
 (root/f'{exists}.out').write_text(r.stdout);(root/f'{exists}.err').write_text(r.stderr)
 x=json.loads(r.stdout) if r.returncode==0 else {};runs.append({'helper_directory_exists':exists,'argv':cmd,'exit':r.returncode,'version':x.get('quarto',{}).get('version'),'course_project':x.get('formats',{}).get('html',{}).get('metadata',{}).get('course-project'),'stderr':r.stderr[:250]})
out={'root':str(root),'fixture':files,'runs':runs,'scope':'Два inspect, без render/фильтров/hooks/execute; проверка rebase строк, не producer курса'};(root/'summary.json').write_text(json.dumps(out,ensure_ascii=False,indent=2)+'\n');print(json.dumps(out,ensure_ascii=False,indent=2))
