#!/usr/bin/env python3
"""Два узких договора файлового копирования: javac → Java25 launcher → bytes/отказы.
Техника explicit close или Reader/Writer/UTF8/TWR остаётся ручной проверкой.
"""
import argparse,hashlib,json,os,re,selectors,shutil,signal,subprocess,time
from pathlib import Path

def digest(p):return hashlib.sha256(p.read_bytes()).hexdigest()
def bounded(argv,cwd,timeout=10,limit=65536):
    started=time.monotonic();out=bytearray();err=bytearray();reason=None
    process=subprocess.Popen(list(map(str,argv)),cwd=cwd,stdin=subprocess.DEVNULL,stdout=subprocess.PIPE,stderr=subprocess.PIPE,start_new_session=True,env={**os.environ,'LANG':'C.UTF-8','LC_ALL':'C.UTF-8'})
    try:
        with selectors.DefaultSelector() as sel:
            sel.register(process.stdout,selectors.EVENT_READ,out);sel.register(process.stderr,selectors.EVENT_READ,err)
            while sel.get_map():
                remaining=timeout-(time.monotonic()-started)
                if remaining<=0:reason='timeout';break
                for key,_ in sel.select(min(remaining,0.1)):
                    chunk=os.read(key.fileobj.fileno(),8192)
                    if not chunk:sel.unregister(key.fileobj);continue
                    room=limit-len(out)-len(err);key.data.extend(chunk[:max(0,room)])
                    if len(chunk)>room:reason='output-limit';break
                if reason:break
            if not reason:
                try:process.wait(timeout=max(0.01,timeout-(time.monotonic()-started)))
                except subprocess.TimeoutExpired:reason='timeout'
    finally:
        try:os.killpg(process.pid,signal.SIGKILL)
        except ProcessLookupError:pass
        process.wait();process.stdout.close();process.stderr.close()
    return dict(argv=list(map(str,argv)),cwd=str(cwd),exitCode=process.returncode,stdout=out.decode('utf8','replace'),stderr=err.decode('utf8','replace'),limitReason=reason,executionLocale='C.UTF-8',elapsedSeconds=time.monotonic()-started)

def main():
    parser=argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--project',type=Path,required=True)
    parser.add_argument('--sources',type=Path,required=True)
    parser.add_argument('--entry',required=True,help='trusted launch class; без фиксированного Main/API')
    parser.add_argument('--work',type=Path,required=True,help='новый, не существующий каталог')
    parser.add_argument('--public',action='store_true')
    parser.add_argument('--only',action='append')
    parser.add_argument('--java',type=Path,default=Path(shutil.which('java') or '/missing-java'))
    parser.add_argument('--javac',type=Path,default=Path(shutil.which('javac') or '/missing-javac'))
    args=parser.parse_args();project=args.project.resolve();sources=args.sources.resolve();work=args.work.resolve();java=args.java.resolve();javac=args.javac.resolve()
    for protected in [project,sources,Path(__file__).resolve().parent,java.parent.parent,javac.parent.parent]:
        if work==protected or work.is_relative_to(protected) or protected.is_relative_to(work):parser.error('work пересекается с project/sources/runtime/support; нужен отдельный новый каталог')
    if args.work.is_symlink() or work.exists():parser.error('work уже существует; существующие файлы не удаляются')
    if not project.is_dir() or not sources.is_dir():parser.error('project/sources должны существовать')
    if not args.entry.strip():parser.error('нужна явно выбранная launch class')
    if not java.is_file() or not javac.is_file() or not os.access(java,os.X_OK) or not os.access(javac,os.X_OK):parser.error('нужны java/javac из JDK25')
    source_files=sorted(sources.rglob('*.java'))
    if not source_files:parser.error('sources не содержит Java-файлов')
    try:manifest=json.loads((project/'tests/cases.json').read_text())
    except (OSError,ValueError) as error:parser.error('непрочитанный cases manifest: '+str(error))
    cases=manifest.get('cases',[]);ids=[c.get('id','') for c in cases]
    if not ids or len(set(ids))!=len(ids) or any(not re.fullmatch('[a-z0-9][a-z0-9-]*',i) for i in ids):parser.error('нужны непустые уникальные case IDs')
    allowed={'copy','copy-replace','missing-existing','missing-new','same-exact','same-normalized','same-hardlink','same-symlink','output-directory'}
    for case in cases:
        if case.get('kind') not in allowed or case.get('scope') not in ('public','private'):parser.error('неподдерживаемый copy case')
        if case['kind'].startswith('copy'):
            for field in ['input','expected']:
                path=(project/case[field]).resolve()
                if not path.is_relative_to(project) or not path.is_file():parser.error('dataset должен быть файлом внутри project')
                try:path.read_bytes().decode('utf8')
                except UnicodeError:parser.error('datasets этого договора должны быть допустимым UTF8')
    available=[c for c in cases if not args.public or c['scope']=='public'];known={c['id'] for c in available}
    unknown=set(args.only or [])-known
    if unknown:parser.error('неизвестные case IDs в выбранной области: '+', '.join(sorted(unknown)))
    selected=[c for c in available if not args.only or c['id'] in args.only]
    if not selected:parser.error('пустая выборка не является успешной проверкой')
    # Полный preflight завершён до первой записи. Ничего существующего не очищается.
    try:work.mkdir(parents=True,exist_ok=False)
    except OSError as error:parser.error('не удалось создать новый work: '+str(error))
    classes=work/'classes';classes.mkdir();commands=[];results=[]
    tracked=sorted(set(source_files+[project/'tests/cases.json']+[(project/c[f]).resolve() for c in cases if c['kind'].startswith('copy') for f in ['input','expected']]))
    before={str(p):digest(p) for p in tracked}
    report=dict(classification='incomplete',status='FAIL',entry=args.entry,sourceHashesBefore=before,commands=commands,cases=results,techniqueAcceptedAutomatically=False,manualReview=['explicit close обоих байтовых ресурсов либо Reader/Writer с UTF8/TWR','причина диагностики и отсутствие ложного сообщения об успехе','объяснение порядка открытия/закрытия ресурсов'])
    def finish(classification):
        after={str(p):digest(p) for p in tracked};report.update(classification=classification,status='PASS' if classification=='correct' else 'FAIL',sourceHashesAfter=after,sourceUnchanged=before==after)
        if before!=after:report.update(status='FAIL',classification='infrastructure-failure')
        (work/'report.json').write_text(json.dumps(report,ensure_ascii=False,indent=2)+'\n');print(report['classification'],flush=True);return 0 if report['status']=='PASS' else 1
    def run(name,argv,cwd=work,timeout=10):
        row=bounded(argv,cwd,timeout);row['name']=name;commands.append(row);(work/(name+'.log')).write_text(row['stdout']+row['stderr']);return row
    try:
        jv=run('java-version',[java,'--version']);cv=run('javac-version',[javac,'-version'])
        if jv['limitReason'] or cv['limitReason'] or jv['exitCode'] or cv['exitCode'] or jv['stdout'].split()[1].split('.')[0]!='25' or cv['stdout'].split()[1].split('.')[0]!='25':return finish('infrastructure-failure')
        compile=run('source-compile',[javac,'-J-Xmx256m','--release','25','-encoding','UTF-8','-proc:none','-d',classes,*source_files],timeout=30)
        if compile['limitReason']:return finish('infrastructure-failure')
        if compile['exitCode']:return finish('student-compilation-failure')
        launch=[java,'-Xmx128m','-XX:ActiveProcessorCount=2','-cp',classes,args.entry]
        dry=run('launch-setup',[java,'--dry-run',*launch[1:]])
        if dry['limitReason'] or dry['exitCode']:return finish('launch-setup-failure')
        for case in selected:
            root=work/case['id'];root.mkdir();cwd=root/'чужой cwd';cwd.mkdir();inputs=root/'вход с пробелом';outputs=root/'выход с пробелом';inputs.mkdir();outputs.mkdir();ip=inputs/'данные.txt';op=outputs/'результат.txt';kind=case['kind'];skip=None
            if kind.startswith('copy'):
                ip.write_bytes((project/case['input']).read_bytes());expected=(project/case['expected']).read_bytes()
                if kind=='copy-replace':op.write_bytes(b'OLD\x00\xff'+b'!'*5000)
            elif kind.startswith('missing'):
                if kind=='missing-existing':op.write_bytes(b'KEEP\x00\xff\r\n')
            else:
                ip.write_bytes('A B\r\nя 😀'.encode());expected=ip.read_bytes()
                if kind=='same-exact':op=ip
                elif kind=='same-normalized':
                    (inputs/'sub').mkdir();op=str(inputs)+'/sub/../'+ip.name
                elif kind=='same-hardlink':
                    try:os.link(ip,op)
                    except (OSError,NotImplementedError) as error:skip='hardlink unsupported: '+str(error)
                elif kind=='same-symlink':
                    try:op.symlink_to(ip)
                    except (OSError,NotImplementedError) as error:skip='symlink unsupported: '+str(error)
                elif kind=='output-directory':op.mkdir();(op/'keep.txt').write_bytes(b'KEEP')
            if skip:results.append(dict(id=case['id'],capabilitySkipped=skip,passed=None));continue
            out=Path(op);input_before=ip.read_bytes() if ip.exists() else None;output_before=out.read_bytes() if out.is_file() else None
            # Обычные опыты передают относительные пути; replace также проверяет абсолютные.
            argv=[str(ip),str(op)] if kind=='copy-replace' else [os.path.relpath(ip,cwd),os.path.relpath(op,cwd)]
            if kind=='same-normalized':argv=[os.path.relpath(ip,cwd),os.path.relpath(inputs,cwd)+'/sub/../'+ip.name]
            row=run('case-'+case['id'],[*launch,*argv],cwd);diagnostic=bool((row['stdout']+row['stderr']).strip());passed=not row['limitReason'];detail=''
            app_error=None;input_unchanged=False;out_hash=None
            try:
                current_input=ip.read_bytes() if ip.is_file() else None
                input_unchanged=(current_input==input_before and (ip.is_file() if input_before is not None else not ip.exists() and not ip.is_symlink()))
                if kind.startswith('copy'):passed=passed and out.is_file() and out.read_bytes()==expected and input_unchanged;detail='точные ожидаемые bytes и неизменный вход; process status/stdout/stderr не фиксируются'
                elif kind.startswith('missing'):passed=passed and input_unchanged and (out.is_file() and out.read_bytes()==output_before if output_before is not None else not out.exists() and not out.is_symlink()) and diagnostic;detail='отсутствие входа сохраняет/не создаёт выход, есть сигнал причины в любом канале'
                elif kind.startswith('same'):passed=passed and input_unchanged and out.is_file() and os.path.samefile(ip,out) and diagnostic;detail='один существующий файл сохранён и есть сигнал отказа; message semantics вручную'
                else:passed=passed and input_unchanged and out.is_dir() and (out/'keep.txt').read_bytes()==b'KEEP' and list(out.iterdir())==[out/'keep.txt'] and diagnostic;detail='output directory сохраняется, причина отказа наблюдаема'
                out_hash=digest(out) if out.is_file() else None
            except OSError as error:
                # Удалённые/испорченные приложением staged файлы — неверное
                # поведение; это не поломка compiler/launcher/подготовки fixture.
                passed=False;app_error=repr(error)
            results.append(dict(id=case['id'],passed=passed,kind=kind,criteria=detail,command=row['name'],expectedSha256=hashlib.sha256(expected).hexdigest() if kind.startswith('copy') else None,outputSha256=out_hash,inputUnchanged=input_unchanged,applicationFilesystemError=app_error))
        return finish('correct' if all(c['passed'] is True or c['passed'] is None and c.get('capabilitySkipped') for c in results) and len(results)==len(selected) else 'behavior-failure')
    except (OSError,ValueError,IndexError) as error:
        report['infrastructureError']=repr(error);return finish('infrastructure-failure')
if __name__=='__main__':raise SystemExit(main())
