"""Запуск только после root review и готовности D candidate; новые private объекты."""
from pathlib import Path
import os,json,subprocess,socket,shutil
B=Path(__file__).parent
NAME='feedback-sp-native-20261105'
IMAGE='codex/pl-sp-worker-local:20261105'
BASE_TAG='codex/pl-sp-worker-base-local:20261105'
BASE_PIN='docker.io/prairielearn/prairielearn@sha256:60c675a42055a430f9ad7298c7dfd2f37119aa471f806e21e2c929de28435f63'
BASE_EXPECTED_ID='sha256:e4f4164ace146dd46b1272c84a55ae94d460bd894f2721315eca2fccc43d1a9d'
CANDIDATE='codex/sp-workspace-delivery:20261105'
CANDIDATE_EXPECTED_ID='sha256:d7eaa4558c866b495371ffd7e478a0a4e1d172f66f8a5eda7f532a0d5aaead34'
COURSE=Path('/tmp/java-release-work-20261105/delivery-sp-native-20261105/course')
VOLUME=NAME+'-postgres'
if os.environ.get('FEEDBACK_SP_ROOT_REVIEW_GO')!='yes':
 raise SystemExit('Не запущено: требуется завершённый root review и FEEDBACK_SP_ROOT_REVIEW_GO=yes.')
def cmd(*args):return subprocess.run(args,check=True,capture_output=True,text=True).stdout.strip()
# До мутаций проверяем candidate и отсутствие своих конфликтующих имён/порта.
candidate=json.loads(cmd('docker','image','inspect',CANDIDATE))[0]
if candidate['Id']!=CANDIDATE_EXPECTED_ID:
 raise SystemExit('D candidate изменился после подготовки: требуется root review нового image, запуск остановлен.')
base=json.loads(cmd('docker','image','inspect',BASE_PIN))[0]
if base['Id']!=BASE_EXPECTED_ID or 'prairielearn/prairielearn@sha256:60c675a42055a430f9ad7298c7dfd2f37119aa471f806e21e2c929de28435f63' not in base.get('RepoDigests',[]):
 raise SystemExit('Pinned PL native image identity отличается; запуск остановлен без pull.')
existing_base=subprocess.run(['docker','image','inspect',BASE_TAG],capture_output=True,text=True)
if existing_base.returncode==0 and json.loads(existing_base.stdout)[0]['Id']!=BASE_EXPECTED_ID:
 raise SystemExit('Уникальный local base tag уже занят другим image; не перезаписываем.')
if cmd('docker','ps','-aq','--filter','label=feedback.sp-worker='+NAME):
 raise SystemExit('Контейнеры с этой testscope уже существуют; не изменяем их.')
for kind,name in [('container',NAME),('network',NAME),('volume',VOLUME)]:
 result=subprocess.run(['docker',kind,'inspect',name],capture_output=True)
 if result.returncode==0:raise SystemExit('Объект уже существует; не изменяем и не перезапускаем: '+kind+' '+name)
with socket.socket() as s:s.bind(('127.0.0.1',3307))
runtime=B/'runtime-course'
if runtime.exists():raise SystemExit('Private course уже существует; автоматическая перезапись запрещена.')
def prepare_private_course(source,destination):
 shutil.copytree(source,destination)
 question='system-practice/exr-version-control-restore-selected-state'
 p=destination/'questions'/question/'info.json';d=json.loads(p.read_text());d['workspaceOptions']['image']=CANDIDATE_EXPECTED_ID;p.write_text(json.dumps(d,ensure_ascii=False,indent=2)+'\n')
 p=destination/'delivery.json';d=json.loads(p.read_text());d['gradingPayloads'][question]['submission']['workspaceOptions']['image']=CANDIDATE_EXPECTED_ID;p.write_text(json.dumps(d,ensure_ascii=False,indent=2)+'\n')
prepare_private_course(COURSE,runtime)
(B/'jobs').mkdir()
cmd('docker','tag',BASE_EXPECTED_ID,BASE_TAG)
subprocess.run(['docker','build','--pull=false','--tag',IMAGE,str(B)],check=True)
cmd('docker','network','create','--driver','bridge','--internal','--label','feedback.sp-worker='+NAME,NAME)
network=json.loads(cmd('docker','network','inspect',NAME))[0];gateway=network['IPAM']['Config'][0]['Gateway']
assert network['Internal'] is True
(B/'evidence/network-created.json').write_text(json.dumps(network,indent=2)+'\n')
# Только новый volume; существующие данные не монтируются.
cmd('docker','volume','create','--label','feedback.sp-worker='+NAME,VOLUME)
args=['docker','run','-d','--name',NAME,'--label','feedback.sp-worker='+NAME,'--network',NAME,
 '--add-host','host.docker.internal:'+gateway,'-p','127.0.0.1:3307:3000',
 '--env','FEEDBACK_SP_NETWORK='+NAME,'--env','FEEDBACK_SP_BIND_IP='+gateway,'--env','HOST_JOBS_DIR='+str(B/'jobs'),
 '--mount','type=bind,src='+str(runtime)+',dst=/course,readonly',
 '--mount','type=bind,src='+str(B/'config')+',dst=/feedback-config,readonly',
 '--mount','type=bind,src='+str(B/'jobs')+',dst=/jobs',
 '--mount','type=bind,src=/var/run/docker.sock,dst=/var/run/docker.sock',
 '--mount','type=volume,src='+VOLUME+',dst=/var/postgres',IMAGE]
(B/'evidence/launch-argv.json').write_text(json.dumps(args,indent=2)+'\n')
print(cmd(*args));print('URL http://127.0.0.1:3307/pl; private gateway '+gateway)
print('Далее Main: штатный Load from disk; открыть actual restore question; живой worker ещё требует UI проверки.')
