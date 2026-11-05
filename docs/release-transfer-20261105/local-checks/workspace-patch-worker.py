"""Узкий временный override isolated SP worker; только copied upstream source/dist."""
from pathlib import Path
import sys,json,difflib
root=Path(__file__).parent
anchor='const docker = new Docker();'
addition="""
// Только приватная локальная приёмка 3307: настройки проверяются до обращения к Docker.
const feedbackScopeLabel = 'feedback.sp-worker=feedback-sp-native-20261105';
const feedbackNetwork = process.env.FEEDBACK_SP_NETWORK || '';
const feedbackBindIp = process.env.FEEDBACK_SP_BIND_IP || '';
const feedbackOctets = feedbackBindIp.split('.').map(Number);
if (feedbackNetwork !== 'feedback-sp-native-20261105' || net.isIP(feedbackBindIp) !== 4 ||
    !(feedbackOctets[0] === 10 || feedbackOctets[0] === 172 && feedbackOctets[1] >= 16 && feedbackOctets[1] <= 31 || feedbackOctets[0] === 192 && feedbackOctets[1] === 168)) {
  throw new Error('SP.LOCAL_WORKER_CONFIG: требуется собственный bridge и его private IPv4 gateway');
}
"""
changes=[(anchor,anchor+addition,1),
 ('docker.listContainers({ all: true })','docker.listContainers({ all: true, filters: JSON.stringify({ label: [feedbackScopeLabel] }) })',2),
 ("filters: JSON.stringify({ label: ['prairielearn.workspace-id'] })","filters: JSON.stringify({ label: ['prairielearn.workspace-id', feedbackScopeLabel] })",1),
 ('filters: JSON.stringify({ name: [`workspace-${launch_uuid}`] })','filters: JSON.stringify({ name: [`workspace-${launch_uuid}`], label: [feedbackScopeLabel] })',1),
 ("let networkMode = 'bridge';","let networkMode = feedbackNetwork;",1),
 ("networkMode = 'no-internet';","throw new Error('SP.LOCAL_WORKER_CONFIG: запрещена смена выделенного private bridge');",1),
 ('[{ HostPort: `${launch_port}` }]','[{ HostIp: feedbackBindIp, HostPort: `${launch_port}` }]',1),
 ("'prairielearn.workspace-id': String(workspace.id),","'feedback.sp-worker': 'feedback-sp-native-20261105',\n            'prairielearn.workspace-id': String(workspace.id),",1),
 ('await docker.pruneVolumes();',"logger.info('SP.LOCAL_WORKER: global volume prune disabled');",1)]
for suffix in ['ts','js']:
 src=root/'upstream'/('interface.'+suffix);text=src.read_text();before=text
 for old,new,count in changes:
  actual=text.count(old)
  if actual!=count:raise SystemExit(f'upstream anchor mismatch {suffix}: {old!r}: {actual} != {count}')
  text=text.replace(old,new)
 dst=root/'patched'/src.name;dst.write_text(text)
 (root/'evidence'/('worker-'+suffix+'.diff')).write_text(''.join(difflib.unified_diff(before.splitlines(True),text.splitlines(True),fromfile='pinned-upstream/'+src.name,tofile='temporary-3307/'+src.name)))
print('Оба copied upstream файла patched; все ожидаемые anchors совпали.')
