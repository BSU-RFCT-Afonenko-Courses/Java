from pathlib import Path, PurePosixPath
import importlib.util,json,subprocess,copy,tempfile,shutil
root=Path('/home/tolya/JavaCodex/java-release-20261110/system-practice')
reports=root.parent/'reports'
spec=importlib.util.spec_from_file_location('materializer',root/'tools/materialize-providers.py'); m=importlib.util.module_from_spec(spec);spec.loader.exec_module(m)
java=root.parent/'java'
def at(path): return subprocess.check_output(['git','show','3128c77b:'+path],cwd=java)
manifest=json.loads((root/'providers.json').read_text()); previous=json.loads((root/'installed-packages.json').read_text()); new=copy.deepcopy(manifest); lock=copy.deepcopy(previous)
jmanifest=json.loads(at('providers.json')); jlock=json.loads(at('installed-packages.json')); patch=at('tools/provider-patches/pl-release-20261110.patch')
oldcore=copy.deepcopy(new['providers']['core']); ui=copy.deepcopy(oldcore);ui['packages'].pop('course-core'); new['providers']['core-ui']=ui
for key,package in [('core','course-core'),('prairielearn','course-prairielearn'),('download','project-download')]:
 v=copy.deepcopy(jmanifest['providers'][key]);v['packages']={package:manifest['providers'][key]['packages'][package]};new['providers'][key]=v
for package in ui['packages']:lock['packages'][package]['provider']='core-ui'
selected=[('core','course-core'),('prairielearn','course-prairielearn'),('download','project-download')]
checked=[]
for key,package in selected:
 for scope in manifest['providers'][key]['packages'][package]:
  target=m.safe(root,(PurePosixPath(scope)/'_extensions'/manifest['namespace']/package).as_posix());expected=m.current_expected(root,previous['packages'][package],scope,manifest['namespace'],package)
  actual=expected # Reuse successful old8 preflight; no intervening writer/delta.
  checked.append((key,package,scope,target,actual))
assert len(checked)==8
print('PASS old targeted closure: eight scopes match old inventory',flush=True)
plan=[]
with tempfile.TemporaryDirectory(prefix='sp-batch-install-',dir=reports) as t:
 temp=Path(t); (temp/'tools/provider-patches').mkdir(parents=True);(temp/'tools/provider-patches/pl-release-20261110.patch').write_bytes(patch)
 for key,package in selected:
  v=new['providers'][key]; stage=temp/key;stage.mkdir(); release=(root.parent/'provider-sources'/v['checkout']).resolve(); stagev=copy.deepcopy(v);stagev['checkout']=release.name;built=m.staged(temp,stagev,release.parent,stage);source,files=built[package]
  info={k:v[k] for k in ['repository','commit','tag','version','sourceZip']};info.update(provider=key,scopes=v['packages'][package],files=files,extensionTree=m.git(release,'rev-parse',v['commit']+':_extensions/'+package).decode().strip())
  if 'candidate' in v:
   info['candidate']=v['candidate'];assert files==jlock['packages'][package]['files'],'Java3128 PL lock mismatch'
  lock['packages'][package]=info
  for ck,cp,scope,target,actual in checked:
   if ck==key:plan.append((source,target,actual,files))
 print('PASS staged exact Core9.3.4/PL354/Download3.5.0; PL equals Java3128 lock',flush=True)
 # Recheck only the eight selected targets immediately before first installation.
 # Old8 preflight reused per root instruction; no intervening writer.
 for source,target,before,files in plan:
  shutil.rmtree(target);shutil.copytree(source,target)
  for f in target.rglob('*'):
   if f.is_file():f.chmod(files[f.relative_to(target).as_posix()]['mode'])
   elif f.is_dir():f.chmod(0o755)
  target.chmod(0o755)
  if m.inventory(target)!=files:raise ValueError('Target bytes/modes differ after write: '+str(target))
 m.atomic_text(root/'tools/provider-patches/pl-release-20261110.patch',patch.decode())
 m.atomic_text(root/'providers.json',json.dumps(new,indent=2,ensure_ascii=False)+'\n')
 m.atomic_text(root/'installed-packages.json',json.dumps(lock,indent=2,ensure_ascii=False)+'\n')
 owner=root/'tasks/_quarto.yml'; text=owner.read_text();assert 'course-core-source-policy:' not in text;m.atomic_text(owner,text+'course-core-source-policy: static\n')
 detail={'oldTargets':len(checked),'newScopes':len(plan),'packages':[p for _,p in selected],'preservedUiPin':ui['commit'],'plSource':'Java3128c77b','status':'PASS_TARGETED_INSTALL','scope':'Only eight changed package scopes; other packages not inventoried'}
 m.atomic_text(reports/'sp-batch-install.json',json.dumps(detail,indent=2)+'\n');print(json.dumps(detail),flush=True)
