#!/usr/bin/env python3
"""Проверить точные локальные Platform source, runtime closure и image config ID."""
from pathlib import Path
import argparse,hashlib,json,subprocess
p=argparse.ArgumentParser(description=__doc__);p.add_argument('--platform-root',type=Path,required=True);p.add_argument('--image',required=True);p.add_argument('--report',type=Path);a=p.parse_args()
root=Path(__file__).resolve().parents[1];pin=json.loads((root/'CI/local-candidate.json').read_text());platform=a.platform_root.resolve(strict=True)
def git(*args):return subprocess.check_output(['git',*args],cwd=platform,text=True).strip()
if git('rev-parse','HEAD')!=pin['platformCommit'] or git('status','--porcelain'):raise SystemExit('Platform candidate source commit/status отличается')
if a.image!=pin['imageConfigId']:raise SystemExit('Local candidate image config ID отличается')
for name,expected in pin['runtimeFiles'].items():
 if hashlib.sha256((platform/name).read_bytes()).hexdigest()!=expected:raise SystemExit('Platform runtime file отличается: '+name)
subprocess.run(['python3',str(root/'tools/materialize-providers.py'),'--check'],check=True)
r=subprocess.run(['docker','image','inspect',a.image,'--format','{{json .Id}} {{json .Os}} {{json .Architecture}}'],capture_output=True,text=True,check=True)
expected=json.dumps(a.image)+' "linux" "amd64"'
if r.stdout.strip()!=expected:raise SystemExit('Local image identity/architecture отличается')
result={'status':'PASS','platformCommit':pin['platformCommit'],'imageConfigId':a.image,'runtimeFiles':pin['runtimeFiles'],'scope':'Локальная candidate preflight; полный render/delivery запускается отдельно'}
if a.report:a.report.write_text(json.dumps(result,ensure_ascii=False,indent=2)+'\n')
print('PASS: exact local Platform source/runtime/image; provider closure')
