#!/usr/bin/env python3
"""Поставить ровно две версии trusted support в public/trusted GenericQueue."""
import argparse,hashlib,json,os,tempfile
from pathlib import Path
SOURCES=['api/QueueApi.java','queuecheck/QueueAccess.java']
def checked(root,name):
 current=root
 for part in Path(name).parts:
  if part in ['..','.'] or Path(name).is_absolute():raise ValueError('Недопустимый путь')
  current=current/part
  if current.is_symlink():raise ValueError('Символическая ссылка запрещена: '+name)
 return current

def sha(path):return hashlib.sha256(path.read_bytes()).hexdigest()
def write(path,data):
 path.parent.mkdir(parents=True,exist_ok=True)
 with tempfile.NamedTemporaryFile(dir=path.parent,delete=False) as stream:temporary=Path(stream.name);stream.write(data)
 try:os.replace(temporary,path)
 finally:temporary.unlink(missing_ok=True)
def main():
 p=argparse.ArgumentParser(description=__doc__);p.add_argument('--root',type=Path,default=Path(__file__).resolve().parents[1]);m=p.add_mutually_exclusive_group(required=True);m.add_argument('--write',action='store_true');m.add_argument('--check',action='store_true');a=p.parse_args();root=a.root.resolve(strict=True);manifest=checked(root,'support/materialized.json');previous=json.loads(manifest.read_text()) if manifest.exists() else {};planned={}
 for partition in ['tests/junit','student/src/test/java']:
  for name in SOURCES:
   source=checked(root,'support/'+name);target=checked(root,partition+'/'+name);expected=sha(source);actual=sha(target) if target.exists() else None
   if actual is not None and actual!=expected and actual!=previous.get(partition+'/'+name):raise ValueError('Локальная правка сохранена: '+str(target.relative_to(root)))
   planned[partition+'/'+name]=(source,target,expected,actual)
 hashes={name:row[2] for name,row in sorted(planned.items())}
 if set(previous)-set(hashes):raise ValueError('Удаление поставки требует отдельной проверки')
 if a.check:
  if hashes!=previous or any(expected!=actual for _,_,expected,actual in planned.values()):raise ValueError('Поставка отсутствует или устарела')
 else:
  for source,target,expected,actual in planned.values():
   if expected!=actual:write(target,source.read_bytes())
  data=(json.dumps(hashes,indent=2)+'\n').encode()
  if not manifest.exists() or manifest.read_bytes()!=data:write(manifest,data)
 print('PASS4 public/trusted support files')
if __name__=='__main__':
 try:main()
 except (OSError,ValueError) as error:print(error);raise SystemExit(1)
