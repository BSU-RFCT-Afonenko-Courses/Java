import {readProjectContexts,decodeProjectContract,ProjectContractSchema,planProject} from '/tmp/java-release-work-20261105/core/_extensions/course-core/public.ts';
import {projectPlanState} from '/tmp/java-release-work-20261105/core/_extensions/course-core/artifacts/plan.ts';
import {parseDocument} from '/tmp/java-release-work-20261105/core/_extensions/course-core/body-export/vendor/libraries.js';
import {resolve,relative,join} from 'stdlib/path';
import {isDeepStrictEqual} from 'node:util';
const root='/tmp/java-release-work-20261105/course-sp',owner=root+'/tasks',base='d30345098dcfb1da1530eca5021319bf91fe0f75';
const dirs=['data-processing/two-result-pipeline','project-maintenance/release','version-control/github-ssh','version-control/local-history','version-control/restore-selected-state','work-environment/file-state','work-environment/launch-counter'].map(p=>'projects/'+p);
function assert(ok:boolean, message:string){if(!ok)throw Error(message)}
const expected=new Map();
for(const directory of dirs){
 const command=await new Deno.Command('git',{cwd:root,args:['show',`${base}:tasks/${directory}/_metadata.yml`]}).output();assert(command.success,'git show');
 const raw=parseDocument(new TextDecoder().decode(command.stdout)).toJS()['course-project'];
 // Исходный договор: строки build.files относились к каталогу их YAML.
 if(raw.build)for(const [key,value]of Object.entries(raw.build.files)){assert(typeof value==='string','Исходный build.files должен быть строкой');raw.build.files[key]={path:resolve(owner,directory,String(value))};}
 raw.starter.path=resolve(owner,directory,raw.starter.path);
 raw.instructions.local.path=resolve(owner,directory,raw.instructions.local.path);
 expected.set(directory,ProjectContractSchema.parse(raw));
}
const start=performance.now(),results=[];
for(const profile of ['student','full'])await readProjectContexts([{owner,directories:dirs,profiles:[profile],format:'html'}],async([group])=>{
 for(const context of group.contexts){
  const directory=relative(owner,context.input),contract=decodeProjectContract(context),before=expected.get(directory);
  assert(isDeepStrictEqual(contract,before),'Изменены физические адреса/роли: '+directory);
  const plan=await planProject(context,{audience:profile==='student'?'student':'full'}),state=await projectPlanState(plan);
  assert(isDeepStrictEqual(state.files,Object.keys(before.build.files).sort().map(target=>({path:before.build.files[target].path,target,reason:'build'}))),'Изменены build targets/roles: '+directory);
  assert(isDeepStrictEqual(state.trees,[{path:before.starter.path,prefix:'',reason:'starter'}]),'Изменены starter targets/roles: '+directory);
  const files=[];
  async function walk(path:string,prefix:string){for await(const e of Deno.readDir(path)){const source=join(path,e.name),target=prefix+e.name;if(e.isDirectory)await walk(source,target+'/');else if(e.isFile)files.push({source,target,role:'starter',bytes:(await Deno.readFile(source)).length});else throw Error('Необычный source')}}
  await walk(before.starter.path,'');for(const f of state.files)files.push({source:f.path,target:f.target,role:f.reason,bytes:(await Deno.readFile(f.path)).length});
  assert(new Set(files.map(f=>f.target)).size===files.length,'Повторный target: '+directory);
  results.push({profile,directory,files:files.length,bytes:files.reduce((a,f)=>a+f.bytes,0)});
 }
 results.push({profile,metrics:group.metrics});
});
console.log(JSON.stringify({result:'PASS',projects:7,profiles:2,physicalTargets:'identical',roles:'identical',selectedFileTargetsAndBytes:'identical source inputs',results,wall_ms:performance.now()-start},null,2));
