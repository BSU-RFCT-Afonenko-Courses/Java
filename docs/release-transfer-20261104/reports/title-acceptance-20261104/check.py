import datetime, json, os, pathlib, signal, subprocess, time
root=pathlib.Path('/home/tolya/JavaCodex/java-release-20261110')
reports=root/'reports'; out=reports/'title-acceptance-20261104'
env=os.environ.copy()
env.update(DENO_DIR='/home/tolya/JavaCodex/.cache/quarto/deno_std/cache',XDG_CACHE_HOME='/home/tolya/JavaCodex/.cache',CORE=str(root/'core-native-bridge'),PL=str(root/'prairielearn'),QRC=str(root/'qrc'),DOWNLOAD=str(root/'download'),QUARTO='/opt/quarto/bin/quarto',PATH='/opt/quarto/bin:'+env['PATH'],QUARTO_RUN_NO_NETWORK='true',DENO_NO_PACKAGE_JSON='1')
base=['/opt/quarto/bin/tools/x86_64/deno']
flags=['--cached-only','--no-config','--no-lock','--import-map=/tmp/native-publication-combined-imports-20261104.json']
commands=[base+['check']+flags+[str(out/'affected-roots.ts')],base+['run','-A']+flags+[str(root/'core-native-bridge/tests/native-readable-titles.ts')]]
result={'task':'A4 new title public fixture; prior A1 costs preserved, not reset','started_utc':datetime.datetime.now(datetime.timezone.utc).isoformat(),'cap_seconds_including_grace':10,'environment':{k:env[k] for k in ['CORE','PL','QRC','DOWNLOAD','QUARTO','PATH','DENO_DIR','XDG_CACHE_HOME','QUARTO_RUN_NO_NETWORK','DENO_NO_PACKAGE_JSON']},'stages':[],'book_stage':{'status':'NOT_EXECUTED','reason':'Ordinary public operation lacks assignmentDelivery authority; private builder draft must not execute.'}}
start=time.monotonic()
for name,cmd in zip(['affected-typegraph','pure-public-normalizeDocument'],commands):
    remaining=10-(time.monotonic()-start)
    if remaining<=0: result['status']='BUDGET_EXHAUSTED'; break
    begun=time.monotonic(); row={'name':name,'command':cmd,'cwd':str(root),'remaining_at_start_seconds':remaining}
    with (out/(name+'.stdout.log')).open('wb') as stdout,(out/(name+'.stderr.log')).open('wb') as stderr:
        proc=subprocess.Popen(cmd,cwd=root,env=env,stdout=stdout,stderr=stderr,start_new_session=True)
        try: code=proc.wait(timeout=min(5,remaining)-0.25 if name.startswith('pure') else remaining-0.25); row.update(exit_code=code,timed_out=False)
        except subprocess.TimeoutExpired:
            os.killpg(proc.pid,signal.SIGTERM)
            try: proc.wait(timeout=0.15)
            except subprocess.TimeoutExpired: os.killpg(proc.pid,signal.SIGKILL); proc.wait()
            row.update(exit_code=proc.returncode,timed_out=True)
    row['elapsed_seconds']=time.monotonic()-begun;result['stages'].append(row)
    if row['exit_code'] or row['timed_out']: result['status']='FAIL_STOPPED_NO_RETRY';break
else: result['status']='PASS_GRAPH_AND_PURE_BOOK_PENDING'
result['elapsed_seconds']=time.monotonic()-start
(reports/'native-titles-public-acceptance-20261104.json').write_text(json.dumps(result,ensure_ascii=False,indent=2)+'\n')
print(json.dumps(result,ensure_ascii=False,indent=2))
