import datetime,json,os,pathlib,signal,subprocess,time
root=pathlib.Path('/home/tolya/JavaCodex/java-release-20261110');reports=root/'reports';out=reports/'title-acceptance-20261104'
prior=json.loads((reports/'native-titles-public-acceptance-20261104.json').read_text());env=os.environ.copy();env.update(prior['environment'])
roots=['core-native-bridge/_extensions/course-core/public.ts','prairielearn/_extensions/course-prairielearn/application/export.ts','core-native-bridge/tests/assignment-role-alias.ts','core-native-bridge/tests/assignment-body.ts','core-native-bridge/tests/native-operation-a3.ts','core-native-bridge/tests/native-capture-currentness-a4c.ts','core-native-bridge/tests/native-capture-fences-a4c.ts','core-native-bridge/tests/static-source-policy.ts','core-native-bridge/tests/release-generated-resource-currentness.ts','core-native-bridge/tests/native-output-resource-paths.ts']
deno='/opt/quarto/bin/tools/x86_64/deno';flags=['--no-config','--no-lock','--import-map=/tmp/native-publication-combined-imports-20261104.json']
commands=[('a5-affected-typegraph',[deno,'check','--deny-import',*flags,*[str(root/p) for p in roots]])]+[(name,[deno,'run','-A','--no-check','--cached-only',*flags,str(root/'core-native-bridge/tests'/file)]) for name,file in [('a5-role-alias','assignment-role-alias.ts'),('a5-output-resource-paths','native-output-resource-paths.ts')]]
result={'scope':'A5 affected graph and two pure fixtures only','source_review':'APPROVE reports/native-final-outputs-review-evening-20261104.md final freeze','model_admission':'reports/native-titles-public-acceptance-model-admission-20261104.json','environment':prior['environment'],'cap_seconds_including_grace_and_overhead':12,'old_a4_costs_preserved':True,'stages':[],'started_utc':datetime.datetime.now(datetime.timezone.utc).isoformat()}
started=time.monotonic()
for name,command in commands:
    remaining=12-(time.monotonic()-started);begun=time.monotonic();row={'name':name,'command':command,'cwd':str(root),'remaining_at_start_seconds':remaining}
    if remaining<=0.25:result['status']='BUDGET_EXHAUSTED';break
    with (out/(name+'.stdout.log')).open('wb') as stdout,(out/(name+'.stderr.log')).open('wb') as stderr:
        process=subprocess.Popen(command,cwd=root,env=env,stdout=stdout,stderr=stderr,start_new_session=True)
        try:row.update(exit_code=process.wait(timeout=remaining-0.25),timed_out=False)
        except subprocess.TimeoutExpired:
            os.killpg(process.pid,signal.SIGTERM)
            try:process.wait(timeout=0.15)
            except subprocess.TimeoutExpired:os.killpg(process.pid,signal.SIGKILL);process.wait()
            row.update(exit_code=process.returncode,timed_out=True)
    row['elapsed_seconds']=time.monotonic()-begun;result['stages'].append(row)
    if row['exit_code'] or row['timed_out']:result['status']='FAIL_STOPPED_NO_RETRY';break
else:result['status']='PASS'
result['elapsed_seconds']=time.monotonic()-started
(reports/'native-final-outputs-public-acceptance-20261104.json').write_text(json.dumps(result,ensure_ascii=False,indent=2)+'\n');print(json.dumps(result,ensure_ascii=False,indent=2))
