import datetime,json,os,pathlib,signal,subprocess,time
outer_started=time.monotonic()
root=pathlib.Path('/home/tolya/JavaCodex/java-release-20261110');reports=root/'reports';out=reports/'title-acceptance-20261104'
prior=json.loads((reports/'native-titles-public-acceptance-20261104.json').read_text());env=os.environ.copy();env.update(prior['environment'])
roots=['core-native-bridge/_extensions/course-core/public.ts','prairielearn/_extensions/course-prairielearn/application/export.ts','core-native-bridge/tests/assignment-body.ts','core-native-bridge/tests/body-schema.ts','prairielearn/tests/body-projection.test.ts','prairielearn/tests/native-a1-builder-integration.ts','prairielearn/tests/native-origin-scope.test.ts','prairielearn/tests/native-auxiliary.test.ts','prairielearn/tests/performance-native-auxiliary.ts','prairielearn/tests/performance-permissions.ts','prairielearn/tests/performance-context.ts']
deno='/opt/quarto/bin/tools/x86_64/deno';flags=['--no-config','--no-lock','--import-map=/tmp/native-publication-combined-imports-20261104.json']
commands=[('body-resource-affected-typegraph',[deno,'check','--deny-import',*flags,*[str(root/p) for p in roots]])]+[(name,[deno,'run','-A','--no-check','--cached-only',*flags,str(root/'core-native-bridge/tests'/file)]) for name,file in [('body-resource-schema','body-schema.ts'),('body-resource-assignment-producer','assignment-body.ts')]]
commands.append(('body-resource-pl-renderer',[deno,'test','-A','--no-check','--cached-only',*flags,'--filter=public body resource rewrites preserve query and fragment on one verified physical target',str(root/'prairielearn/tests/body-projection.test.ts')]))
result={'scope':'BodyResource 11-root graph; existing package schema/producer plus exactly filtered real Pandoc resource renderer','source_review':'APPROVE reports/body-resource-types-review-evening-20261104.md','model_admission':'reports/native-titles-public-acceptance-model-admission-20261104.json','environment':prior['environment'],'cap_seconds_including_grace_and_overhead':10,'previous_native_seconds_untouched':12.355128194001736,'previous_native_remaining_untouched':27.644871805998264,'old_a4_costs_preserved':True,'stages':[],'started_utc':datetime.datetime.now(datetime.timezone.utc).isoformat()}
started=outer_started
for name,command in commands:
    remaining=10-(time.monotonic()-started);begun=time.monotonic();row={'name':name,'command':command,'cwd':str(root),'remaining_at_start_seconds':remaining}
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
if result['status']!='PASS':
    result['first_diagnostic']=(out/(result['stages'][-1]['name']+'.stderr.log')).read_text()
result['product_edits']=False
result['retry_count']=0
(reports/'body-resource-types-public-acceptance-20261104.json').write_text(json.dumps(result,ensure_ascii=False,indent=2)+'\n');print(json.dumps(result,ensure_ascii=False,indent=2))
