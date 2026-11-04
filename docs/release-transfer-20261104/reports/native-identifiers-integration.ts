// Разовая адресная адаптация tests/native-answers-integration.ts и native-entry.ts.
import { dirname, join } from 'node:path';
import { pathToFileURL } from 'node:url';
import { strict as assert } from 'node:assert';
const reports = dirname(new URL(import.meta.url).pathname);
const java = join(reports, '../java');
const installed = join(java, 'tasks/_extensions/Afonenko-Course-Tools');
const core = join(installed, 'course-core');
const pl = join(installed, 'course-prairielearn');
const Core = await import(pathToFileURL(join(core, 'public.ts')).href);
const PL = await import(pathToFileURL(join(pl, 'public.ts')).href);
const Entry = await import(pathToFileURL(join(pl, 'application/native-entry.ts')).href);
const original = await Deno.readTextFile(join(java, 'tasks/labs/objects.qmd'));
const id = original.match(/^  id: (sec-[-a-z0-9]+)$/m)![1];
const uuid = original.match(/^    uuid: ([-a-f0-9]+)$/m)![1];
const heading = original.match(/^# (.+) \{#(sec-[-a-z0-9]+)\}$/m)!;
assert.equal(heading[2], id);
const root = await Deno.makeTempDir({dir: reports, prefix:'native-identifiers-owner-'});
const outputDirectory = join(root, '_generated/render');
const put = async (p: string, text: string) => {
 await Deno.mkdir(dirname(join(root,p)), {recursive:true});
 await Deno.writeTextFile(join(root,p), text);
};
await put('.gitignore', '/.quarto/\n**/*.quarto_ipynb\n');
await put('_extensions/prairielearn/contract.json', '{"name":"prairielearn"}');
await put('_quarto.yml', `project:
  type: website
  output-dir: _generated/render
  render: [index.qmd]
  pre-render: [quarto run ${core}/entrypoints/native-pre.ts, quarto run pre.ts]
  post-render: [quarto run ${core}/entrypoints/native-post.ts]
format: {html: {theme: none}}
lang: ru
course: {id: java-semester-one, view: full, adapters: [prairielearn]}
course-core-runtime: native-ts
course-core-projection: author
exercise-bank: true
default-exercise-statement-visibility: open
default-exercise-difficulty: introductory
default-exercise-time: 7
default-exercise-course-role: false
default-exercise-target: prairielearn
project-checks: {source-profiles: {}, profiles: {}}
project-materialization: {projects: {}}
filters:
 - {at: pre-ast, path: ${core}/native-ts/early-context.lua}
 - {at: post-quarto, path: ${core}/native-ts/late-kind.lua}
 - {at: post-render, path: ${core}/native-ts/late-filter.lua}
prairielearn:
  question-defaults: {topic: Java, grading: {method: manual}, submission: {mode: upload}, single-variant: true}
`);
await put('index.qmd', `---
assessment:
  id: ${id}
  kind: lab
  prairielearn:
    uuid: ${uuid}
    assignment: {mode: assessment-id}
    pass: {at-least: 1}
    attempts: 1
    auto-points: 0
    manual-points: 20
---
# ${heading[1]} {#${id}}

Связь с разделом: @${id}.

::: {#exr-native-identifiers prairielearn-grading=manual}
## Объяснение
Объясните состояние объекта.

\`\`\`{.answer-spec}
type: manual
submission: text
\`\`\`
:::

::: {.task-items requirement=required}
- @exr-native-identifiers
:::
`);
await put('pre.ts', `import * as Core from ${JSON.stringify(pathToFileURL(join(core,'public.ts')).href)};import * as PL from ${JSON.stringify(pathToFileURL(join(pl,'public.ts')).href)};await PL.prepareNativeAdapter(Core,{root:Deno.cwd(),projection:'author',outputDirectory:Deno.cwd()+'/_generated/render'});`);
let descriptor: any;
const selected = {expectedSources:['index.qmd']};
const started = performance.now();
await Core.withNativeCaptureScope({root, projection:'author', outputDirectory}, {expectedSources:selected.expectedSources}, async (scope: any) => {
 await scope.captureInputs(selected.expectedSources, async (token: string) => {
  const rendered = await new Deno.Command('/opt/quarto/bin/quarto', {
   args:['render','index.qmd','--output-dir','_generated/render'],cwd:root,
   env:{COURSE_CORE_CAPTURE_TOKEN:token,QUARTO_RUN_NO_NETWORK:'true',DENO_NO_PACKAGE_JSON:'1'},
   stdout:'inherit',stderr:'inherit',
  }).output();
  assert(rendered.success,'Native render отказал');
  });
});
descriptor = await (await import(pathToFileURL(join(pl,'application/native-adapter.ts')).href)).readNativeAdapterExpectation(Core,root);
let consumer: any;
await Core.withCurrentNativeOperation(descriptor.expectation, async (op: any) => {
 const adapter = PL.createNativeAdapter({root, core:Core,preparedIdentity:descriptor.identity});
 const proof = await adapter.validateCourse(op,{expectedSources:selected.expectedSources});
 const business = adapter.business('index.qmd');
 const input = adapter.validatedInput('index.qmd');
 assert.equal(input.assessment.id,id);
 assert.equal(business.assessment.uuid,uuid);
 consumer = {assessmentId:input.assessment.id, uuid:business.assessment.uuid, proof, metrics:op.metrics};
});
const html = await Deno.readTextFile(join(outputDirectory,'index.html'));
assert(html.includes(`id="${id}"`));
assert(new RegExp(`href="(?:index\\.html)?#${id}"[^>]*class="[^"]*quarto-xref`).test(html));
assert(!html.includes('j25'));
const result = {status:'PASS_NATIVE_IDENTIFIERS',root,elapsedSeconds:(performance.now()-started)/1000, installedCore:core,installedPL:pl,consumer,html:{path:join(outputDirectory,'index.html'),anchor:id,xref:'#'+id,noJ25:true},limit:'Native business consumer; без полного курса, infoAssessment export, БД или OCI'};
await Deno.writeTextFile(join(reports,'native-identifiers-result.json'),JSON.stringify(result,null,2)+'\n');
console.log(JSON.stringify(result,null,2));
