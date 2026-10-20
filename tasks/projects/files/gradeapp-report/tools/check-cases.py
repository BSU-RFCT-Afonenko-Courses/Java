#!/usr/bin/env python3
"""Воспроизвести реальные мутации и допустимые варианты gradeapp локально."""
import argparse,json,os,shutil,subprocess,sys,hashlib,concurrent.futures,re
from pathlib import Path
P=Path(__file__).resolve().parents[1]
a=argparse.ArgumentParser(description=__doc__);a.add_argument('--runtime',type=Path,required=True);a.add_argument('--work',type=Path,required=True);a.add_argument('--only',action='append');args=a.parse_args();work=args.work.resolve();runtime=args.runtime.resolve()
manifest=json.loads((P/'tests/contract-cases.json').read_text());all_cases=manifest['cases'];ids=[x['id'] for x in all_cases]
if not ids or len(ids)!=len(set(ids)) or any(not re.fullmatch(r'[a-z0-9][a-z0-9-]*',i) for i in ids):a.error('непустой manifest требует уникальные безопасные case IDs')
unknown=set(args.only or [])-set(ids)
if unknown:a.error('неизвестные case IDs: '+', '.join(sorted(unknown)))
cases=[x for x in all_cases if not args.only or x['id'] in args.only]
if not cases:a.error('пустая выборка не является успешной проверкой')
for protected in (P.resolve(),runtime):
 if work==protected or work.is_relative_to(protected) or protected.is_relative_to(work):a.error('work пересекается с project/runtime; нужен отдельный новый каталог')
if args.work.is_symlink() or work.exists():a.error('work уже существует; выберите новый каталог, существующие файлы не удаляются')
if not (P/'reference/src/main/java').is_dir():a.error('reference source отсутствует')
for name in ('PlatformApiPrecheck.java','json-simple-1.1.1.jar','junit-platform-console-standalone-1.14.1.jar'):
 if not (runtime/name).is_file():a.error('неполный runtime: '+name)
for case in cases:
 for item in case['sources']:
  relative=Path(item['submission']);fixture=(P/item['fixture']).resolve()
  if relative.is_absolute() or '..' in relative.parts or not relative.parts or not fixture.is_relative_to(P.resolve()) or not fixture.is_file():a.error('небезопасный или отсутствующий fixture/submission path')
try:work.mkdir(parents=True,exist_ok=False)
except OSError as error:a.error('не удалось создать новый work: '+str(error))
def digest(p):return hashlib.sha256(p.read_bytes()).hexdigest()
frozen={str(p.relative_to(P)):digest(p) for p in P.rglob('*') if p.is_file() and ('reference' in p.parts or 'tests' in p.parts or 'tools' in p.parts)}
def run(case):
 root=work/case['id'];root.mkdir();source=root/'sources'
 shutil.copytree(P/'reference/src/main/java',source)
 for item in case['sources']:
  dest=source/item['submission'];dest.parent.mkdir(parents=True,exist_ok=True);shutil.copyfile(P/item['fixture'],dest)
 cmd=[sys.executable,str(P/'tools/check.py'),'--sources',str(source),'--runtime',str(args.runtime.resolve()),'--work',str(root/'check')]
 proc=subprocess.run(cmd,capture_output=True,text=True,timeout=150)
 (root/'check.log').write_text(proc.stdout+proc.stderr)
 report=json.loads((root/'check/report.json').read_text());actual={'correct':'success','api-mismatch':'student-api-mismatch'}.get(report['classification'],report['classification']);matched=actual==case['expected']['classification']
 if actual in ('student-api-mismatch','student-compilation-failure'):matched=matched and not report['dependentTestsCompiled']
 if actual=='behavior-failure':matched=matched and report['dependentTestsCompiled'] and report['apiPreflight']['status']=='success'
 result=dict(id=case['id'],argv=cmd,exitCode=proc.returncode,expected=case['expected'],actual=actual,matched=matched,sourceHashes=report['sourceHashesBefore'],apiPreflight=report.get('apiPreflight'),dependentTestsCompiled=report['dependentTestsCompiled'])
 print(('PASS' if matched else 'FAIL'),case['id'],actual,flush=True);return result
with concurrent.futures.ThreadPoolExecutor(max_workers=3) as pool:results=list(pool.map(run,cases))
assert frozen=={str(p.relative_to(P)):digest(p) for p in P.rglob('*') if p.is_file() and ('reference' in p.parts or 'tests' in p.parts or 'tools' in p.parts)}
report=dict(status='PASS' if len(results)==len(cases) and all(x['matched'] for x in results) else 'FAIL',cases=results,sourceHashes=frozen)
(work/'report.json').write_text(json.dumps(report,ensure_ascii=False,indent=2)+'\n');raise SystemExit(report['status']!='PASS')
