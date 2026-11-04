import datetime, json, os, pathlib, re, signal, subprocess, time
release=pathlib.Path('/home/tolya/JavaCodex/java-release-20261110')
reports=release/'reports'; folder=reports/'title-acceptance-20261104'
root=pathlib.Path((folder/'fixture-root.txt').read_text().strip()); book=root/'tasks'
evidence=reports/'native-titles-public-acceptance-20261104.json'
result=json.loads(evidence.read_text()); env=os.environ.copy();env.update(result['environment'])
command=['/opt/quarto/bin/tools/x86_64/deno','run','-A','--no-check','--cached-only','--no-config','--no-lock','--import-map=/tmp/native-publication-combined-imports-20261104.json',str(book/'_extensions/course-prairielearn/entrypoints/export-course.ts'),str(root),str(root/'native'),'--instance','test','--checks-output',str(root/'private-checks.json'),'--book','tasks']
row={'status':'RUNNING','command':command,'cwd':str(root),'environment':result['environment'],'cap_seconds_including_grace':40,'root':str(root),'route':'existing public PL export-course CLI; actual prepared assignment owns whole two-page book render','started_utc':datetime.datetime.now(datetime.timezone.utc).isoformat()}
result['book_stage']=row;evidence.write_text(json.dumps(result,ensure_ascii=False,indent=2)+'\n')
started=time.monotonic()
with (folder/'book-cli.stdout.log').open('wb') as out,(folder/'book-cli.stderr.log').open('wb') as err:
    process=subprocess.Popen(command,cwd=root,env=env,stdout=out,stderr=err,start_new_session=True)
    try: row['exit_code']=process.wait(timeout=39.7);row['timed_out']=False
    except subprocess.TimeoutExpired:
        os.killpg(process.pid,signal.SIGTERM)
        try: process.wait(timeout=0.2)
        except subprocess.TimeoutExpired: os.killpg(process.pid,signal.SIGKILL);process.wait()
        row.update(exit_code=process.returncode,timed_out=True)
try:
    assert row['exit_code']==0 and not row['timed_out'],'public CLI failed; no retry'
    assessment=root/'native/courseInstances/test/assessments/sec-work/infoAssessment.json';question=root/'native/questions/readable-titles/exr-one/info.json';htmlpath=question.parent/'question.html'
    a=json.loads(assessment.read_text());q=json.loads(question.read_text());html=htmlpath.read_text()
    assert a['title']=='24.0.8 Числа — ёж x < 3 ссылка α β',repr(a['title'])
    assert q['title']=='7. Число Main.java ссылка ёж',repr(q['title'])
    visible=re.sub(r'\s+',' ',re.sub(r'<[^>]*>',' ',html))
    assert 'Авторское условие с числом 24.0.8.' in visible,'authored condition lost'
    assert not re.search(r'chapter-number|chapter-title|quarto-section-identifier|question-link|question-span|authored-id|key=value',visible),'decoration leaked into visible condition'
    assert time.monotonic()-started<40,'40 second total cap exceeded'
    row.update(status='PASS',assessment_title=a['title'],question_title=q['title'],authored_condition=True,readable_condition_without_book_decoration=True,outputs=[str(assessment),str(question),str(htmlpath)])
except Exception as error: row.update(status='FAIL_STOPPED_NO_RETRY',error=str(error))
row['elapsed_seconds']=time.monotonic()-started
result['status']='PASS_AFFECTED_GRAPH_PURE_AND_PUBLIC_BOOK_EXPORT' if row['status']=='PASS' else 'GRAPH_AND_PURE_PASS_BOOK_FAILED_STOPPED'
evidence.write_text(json.dumps(result,ensure_ascii=False,indent=2)+'\n');print(json.dumps(row,ensure_ascii=False,indent=2))
