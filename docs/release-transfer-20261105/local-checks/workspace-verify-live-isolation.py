"""Read-only проверка только нового 3307 стенда после UI launch настоящего Workspace."""
from pathlib import Path
import json,subprocess,ipaddress,socket
B=Path(__file__).parent;NAME='feedback-sp-native-20261105'
def cmd(*args):return subprocess.run(args,check=True,capture_output=True,text=True).stdout
net=json.loads(cmd('docker','network','inspect',NAME))[0];assert net['Name']==NAME and net['Internal'] is True
gateway=net['IPAM']['Config'][0]['Gateway'];assert ipaddress.ip_address(gateway).is_private
pl=json.loads(cmd('docker','inspect',NAME))[0]
assert pl['HostConfig']['PortBindings']=={'3000/tcp':[{'HostIp':'127.0.0.1','HostPort':'3307'}]}
assert pl['HostConfig']['NetworkMode']==NAME
assert 'host.docker.internal:'+gateway in pl['HostConfig']['ExtraHosts']
assert set(pl['NetworkSettings']['Networks'])=={NAME}
ids=cmd('docker','ps','-aq','--filter','label=feedback.sp-worker='+NAME).split()
children=[]
for cid in ids:
 d=json.loads(cmd('docker','inspect',cid))[0]
 if d['Id']==pl['Id']:continue
 assert d['Config']['Labels']['feedback.sp-worker']==NAME
 assert d['HostConfig']['NetworkMode']==NAME
 ports=d['HostConfig']['PortBindings'];assert set(ports)=={'8080/tcp'}
 mappings=ports['8080/tcp'];assert len(mappings)==1 and mappings[0]['HostIp']==gateway
 port=int(mappings[0]['HostPort']);assert 34000<=port<34020
 assert any(m['Type']=='bind' and m['Destination']=='/home/coder/workspace' and m['Source'].startswith(str(B/'jobs/workspaces')+'/') for m in d['Mounts'])
 # Проверяем actual service без PL-auth только внутри выделенного private gateway.
 curl=cmd('docker','exec',NAME,'curl','-sS','--max-time','5','-o','/dev/null','-w','%{http_code}','http://host.docker.internal:'+str(port)+'/')
 assert curl.isdigit() and int(curl)>0
 children.append({'container':d['Name'],'image':d['Image'],'port_bindings':ports,'network':NAME,'gateway_http_status':curl})
assert children,'Нет настоящего Workspace: открыть его штатно через PL до этой проверки.'
# Это локальные адреса самой машины; чужие узлы не опрашиваются.
addresses=[]
for iface in json.loads(cmd('ip','-j','-4','addr','show','scope','global')):
 if iface['ifname']=='lo' or iface['ifname'].startswith(('docker','br-','veth')):continue
 for a in iface.get('addr_info',[]):
  if a['family']=='inet' and a['local']!=gateway:addresses.append(a['local'])
checks=[]
for ip in addresses:
 for child in children:
  port=int(child['port_bindings']['8080/tcp'][0]['HostPort'])
  with socket.socket() as s:
   s.settimeout(2);opened=s.connect_ex((ip,port))==0
  assert not opened,('Неожиданный editor на host interface',ip,port)
  checks.append({'host_interface_address':ip,'port':port,'connected':False})
status=json.loads(cmd('docker','exec',NAME,'curl','-fsS','http://127.0.0.1:8081/status'));assert status['postgres']=='ok'
report={'result':'PASS','pl_loopback':'127.0.0.1:3307','worker_host_port_published':False,'private_network':NAME,'private_gateway':gateway,'internal_network':True,'workspaces':children,'host_interface_negative_connect':checks,'worker_status':'ok','note':'PL auth/proxy browser route и save/resume проверяются отдельно Main; здесь только actual isolation/reachability.'}
(B/'evidence/live-isolation.json').write_text(json.dumps(report,ensure_ascii=False,indent=2)+'\n');print(json.dumps(report,ensure_ascii=False,indent=2))
