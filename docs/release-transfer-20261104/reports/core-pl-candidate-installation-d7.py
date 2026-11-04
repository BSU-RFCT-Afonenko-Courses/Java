#!/usr/bin/env python3
"""One-shot addressed installation; run only after root names final clean Core SHA."""
from pathlib import Path, PurePosixPath
import argparse, copy, importlib.util, json, os, re, subprocess, tempfile
BASE=Path('/home/tolya/JavaCodex/java-release-20261110')
PL='da2ad6b6c00891590e59e5abccf3d1eb4ff552f7'
CORE_BASE='a478d2cc0dfb19e0bebd99108815ac8ef1fe85a8'
PL_BASE='1c41f03f83b26adfe58415b4d89958a3663fabf2'

def git(repo,*args):return subprocess.check_output(['git',*args],cwd=repo)
def blob(repo,commit,path):
 raw=git(repo,'ls-tree','-z',commit,'--',path).rstrip(b'\0')
 if not raw:raise ValueError('Git target file absent: '+path)
 left,name=raw.split(b'\t',1);mode,kind,oid=left.split()
 if name.decode()!=path or kind!=b'blob' or mode not in (b'100644',b'100755'):raise ValueError('Unexpected Git file type: '+path)
 return git(repo,'cat-file','blob',oid.decode()),0o755 if mode==b'100755' else 0o644

def delta(repo,old,target,package):
 prefix='_extensions/'+package+'/'
 fields=git(repo,'diff','--no-renames','--name-status','-z',old,target,'--',prefix).split(b'\0');fields=fields[:-1] if fields[-1]==b'' else fields
 if len(fields)%2:raise ValueError('Unexpected Git name-status')
 rows=[]
 for status,path in zip(fields[::2],fields[1::2]):
  status=status.decode();path=path.decode()
  if status not in ('A','M','D') or not path.startswith(prefix):raise ValueError('Unexpected delta '+status+' '+path)
  local=path[len(prefix):]
  if PurePosixPath(local).is_absolute() or '..' in PurePosixPath(local).parts:raise ValueError('Unsafe Git path')
  data,mode=(None,None) if status=='D' else blob(repo,target,path)
  rows.append((status,local,data,mode))
 return rows

def atomic_bytes(path,data,mode):
 path.parent.mkdir(parents=True,exist_ok=True)
 with tempfile.NamedTemporaryFile(dir=path.parent,delete=False) as f:
  tmp=Path(f.name);f.write(data)
 try:tmp.chmod(mode);os.replace(tmp,path)
 finally:
  if tmp.exists():tmp.unlink()

def main():
 ap=argparse.ArgumentParser();ap.add_argument('--core-commit',required=True);ap.add_argument('--write',action='store_true');args=ap.parse_args();core=args.core_commit
 if not re.fullmatch('[a-f0-9]{40}',core):raise ValueError('Explicit final clean full Core SHA required from root')
 specs=[('core','course-core',BASE/'core',CORE_BASE,core,'tools/provider-patches/core-release-20261110.patch'),('prairielearn','course-prairielearn',BASE/'prairielearn',PL_BASE,PL,'tools/provider-patches/pl-release-20261110.patch')]
 snapshots=[];mutations=[];patches=[];summaries=[]
 for course in ('java','system-practice'):
  root=BASE/course;spec=importlib.util.spec_from_file_location('materializer_'+course.replace('-','_'),root/'tools/materialize-providers.py');m=importlib.util.module_from_spec(spec);spec.loader.exec_module(m)
  manifest_bytes=(root/'providers.json').read_bytes();lock_bytes=(root/'installed-packages.json').read_bytes();manifest=json.loads(manifest_bytes);lock=json.loads(lock_bytes);newmanifest=copy.deepcopy(manifest);newlock=copy.deepcopy(lock)
  for key,package,repo,published,target,patchpath in specs:
   provider=manifest['providers'][key]
   if provider['commit']!=published:raise ValueError('Published base changed: '+course+'/'+key)
   old=provider.get('candidate',{}).get('commit',published)
   if git(repo,'rev-parse',target+'^{commit}').decode().strip()!=target:raise ValueError('Target SHA unavailable')
   # Core UI stays at the published release in Java; SP core-ui is separate.
   for other in provider['packages']:
    if other!=package and git(repo,'diff','--name-only',published,target,'--','_extensions/'+other).strip():raise ValueError('Candidate unexpectedly changes UI: '+other)
   changed=delta(repo,old,target,package);files=copy.deepcopy(lock['packages'][package]['files'])
   patch=git(repo,'diff','--binary','--no-ext-diff',published,target,'--','_extensions/'+package)
   oldpatch=provider.get('candidate',{}).get('patch')
   if oldpatch:
    p=m.safe(root,oldpatch)
    if m.sha(p.read_bytes())!=provider['candidate']['patchSha256']:raise ValueError('Existing patch modified: '+str(p))
   destination=m.safe(root,patchpath)
   if oldpatch and oldpatch!=patchpath:raise ValueError('Unexpected patch contract path change')
   if not oldpatch and destination.exists():raise ValueError('New patch path already exists: '+str(destination))
   for status,local,data,mode in changed:
    previous=files.get(local)
    if status=='A' and previous is not None or status!='A' and previous is None:raise ValueError('Old lock/Git delta mismatch: '+local)
    for scope in provider['packages'][package]:
     relative=(PurePosixPath(scope)/'_extensions'/manifest['namespace']/package/local).as_posix();path=m.safe(root,relative)
     expected=m.current_expected(root,lock['packages'][package],scope,manifest['namespace'],package).get(local)
     if status=='A':
      if path.exists():raise ValueError('Unexpected added target exists: '+str(path))
     else:
      if not path.is_file():raise ValueError('Changed target not regular: '+str(path))
      actual={'sha256':m.sha(path.read_bytes()),'size':path.stat().st_size,'mode':0o755 if path.stat().st_mode&0o100 else 0o644}
      if expected is None or any(actual.get(k)!=v for k,v in expected.items()):raise ValueError('Local modification preserved: '+str(path))
     mutations.append((m,root,relative,status,data,mode))
    if status=='D':del files[local]
    else:files[local]={'sha256':m.sha(data),'size':len(data),'mode':mode}
   newlock['packages'][package]['files']=files
   hashes={p:m.tree_hash(newlock['packages'][p]['files']) for p in provider['packages']}
   candidate={'status':'local-unpublished','baseCommit':published,'commit':target,'patch':patchpath,'patchSha256':m.sha(patch),'packageHashes':hashes}
   newmanifest['providers'][key]['candidate']=candidate
   for p in provider['packages']:newlock['packages'][p]['candidate']=copy.deepcopy(candidate)
   m.valid_provider(newmanifest['providers'][key]);patches.append((m,root,patchpath,patch))
   summaries.append({'course':course,'provider':key,'old':old,'target':target,'patch':patchpath,'packageDelta':[{'status':s,'path':p} for s,p,_,_ in changed],'scopes':provider['packages'][package],'targetFileOperations':len(changed)*len(provider['packages'][package]),'packageHashes':hashes})
  snapshots.append((m,root,manifest_bytes,lock_bytes,newmanifest,newlock))
 # Preparation above only reads addressed files; no other vendor walk.
 result={'coreFinal':core,'plFinal':PL,'mode':'write' if args.write else 'preflight','courses':summaries,'totalTargetFileOperations':len(mutations),'unchangedVendorFiles':'not read or rewritten'}
 if args.write:
  for m,root,mb,lb,_,_ in snapshots:
   if (root/'providers.json').read_bytes()!=mb or (root/'installed-packages.json').read_bytes()!=lb:raise ValueError('Concurrent manifest/lock writer: '+str(root))
  for m,root,relative,status,data,mode in mutations:
   path=m.safe(root,relative)
   if status=='D':path.unlink()
   else:atomic_bytes(path,data,mode)
  for m,root,relative,status,data,mode in mutations:
   path=m.safe(root,relative)
   if status=='D':
    if path.exists():raise ValueError('Deleted target survived: '+str(path))
   elif path.read_bytes()!=data or (0o755 if path.stat().st_mode&0o100 else 0o644)!=mode:raise ValueError('Postwrite bytes/mode mismatch: '+str(path))
  for m,root,path,patch in patches:m.atomic_text(m.safe(root,path),patch.decode())
  for m,root,_,_,manifest,lock in snapshots:
   m.atomic_text(root/'providers.json',json.dumps(manifest,indent=2,ensure_ascii=False)+'\n');m.atomic_text(root/'installed-packages.json',json.dumps(lock,indent=2,ensure_ascii=False)+'\n')
  result['status']='PASS_ADDRESSED_INSTALL'
 else:result['status']='PASS_ADDRESSED_PREFLIGHT_NO_WRITES'
 (BASE/'reports/core-pl-candidate-installation-d7-result.json').write_text(json.dumps(result,indent=2)+'\n');print(json.dumps(result,indent=2))
if __name__=='__main__':main()
