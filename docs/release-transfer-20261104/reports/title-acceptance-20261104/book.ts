// Разовая проверка actual book → ordinary Native → существующий PL writer.
import { strict as assert } from "node:assert";
import { join } from "node:path";
import { pathToFileURL } from "node:url";
const required = (key: string) => { const value = Deno.env.get(key); if (!value) throw Error(key); return value; };
const core = join(required("CORE"), "_extensions/course-core"), pl = join(required("PL"), "_extensions/course-prairielearn");
required("QRC"); required("DOWNLOAD"); required("DENO_DIR"); required("XDG_CACHE_HOME");
const Core = await import(pathToFileURL(join(core, "public.ts")).href);
const PL = await import(pathToFileURL(join(pl, "public.ts")).href);
const { readNativeAdapterExpectation } = await import(pathToFileURL(join(pl, "application/native-adapter.ts")).href);
const { buildAssignmentBody } = await import(pathToFileURL(join(core, "body-export/assignment.ts")).href);
const { exportCourse } = await import(pathToFileURL(join(pl, "application/export-course.ts")).href);
const root = required("TITLE_FIXTURE_ROOT"), expectedSources = ["index.qmd", "work.qmd"];
await Core.withNativeCaptureScope({root, projection:"student", outputDirectory:join(root,"_site")}, {expectedSources}, async (scope: any) => {
  await scope.captureInputs(expectedSources, async (token: string) => {
    const rendered = await new Deno.Command(required("QUARTO"), {args:["render","--to","html"],cwd:root,
      env:{COURSE_CORE_CAPTURE_TOKEN:token,QUARTO_RUN_NO_NETWORK:"true",DENO_NO_PACKAGE_JSON:"1"},stdout:"inherit",stderr:"inherit"}).output();
    assert(rendered.success,"actual whole numbered book render");
  });
});
const candidate = await readNativeAdapterExpectation(Core, root);
await Core.withCurrentNativeOperation(candidate.expectation, async (op: any) => {
  const adapter = PL.createNativeAdapter({root, core:Core, preparedIdentity:candidate.identity});
  await adapter.validateCourse(op,{expectedSources});
  const value = adapter.validatedInput("work.qmd"), business = adapter.business("work.qmd");
  assert.equal(value.assessment.title,"24.0.8 Числа — ёж x < 3 ссылка α β");
  const {publicPackage,conditions} = await buildAssignmentBody({root,owner:"readable-titles",work:{source:"work.qmd",composition:value.assessment},
    documents:expectedSources.map(source=>op.document({source})),
    assertPublicResource:()=>{throw Error("unexpected resource");}, readResource:()=>{throw Error("unexpected resource");}});
  assert.equal(publicPackage.questions[0].title,"7. Число Main.java ссылка ёж");
  const content = {assessments:[{...value.assessment,source:"work.qmd",extensions:{prairielearn:business.assessment}}],
    exercises:value.declarations.map((d:any)=>({id:d.id,source:"work.qmd",target:"prairielearn",extensions:{prairielearn:business.questions[d.id]}})),projects:[]};
  await exportCourse({courseId:"readable-titles",projectRoot:root,content,
    checks:{projects:[],bookRoot:"book"},config:business.raw,registry:{schemaVersion:1,profiles:{}},instance:"test",
    output:join(root,"native"),checksOutput:join(root,"private-checks.json"),body:async()=>publicPackage,nativeBody:true,
    nativeAnswers:Object.fromEntries(value.declarations.map((d:any)=>[d.id,d.answer])),
    nativeResponseSlots:{"exr-one":conditions["exr-one"].responseSlots}});
});
const assessment = JSON.parse(await Deno.readTextFile(join(root,"native/courseInstances/test/assessments/sec-work/infoAssessment.json")));
const question = JSON.parse(await Deno.readTextFile(join(root,"native/questions/readable-titles/exr-one/info.json")));
const html = await Deno.readTextFile(join(root,"native/questions/readable-titles/exr-one/question.html"));
assert.equal(assessment.title,"24.0.8 Числа — ёж x < 3 ссылка α β");
assert.equal(question.title,"7. Число Main.java ссылка ёж");
assert(html.includes("Авторское условие с числом 24.0.8."));
const visible = html.replace(/<[^>]*>/g," ").replace(/\s+/g," ");
assert(!/chapter-number|chapter-title|quarto-section-identifier|question-span|question-link|authored-id|key=value/.test(visible));
console.log(JSON.stringify({status:"PASS",root,assessmentTitle:assessment.title,questionTitle:question.title,authoredCondition:true,wholeBook:true}));
