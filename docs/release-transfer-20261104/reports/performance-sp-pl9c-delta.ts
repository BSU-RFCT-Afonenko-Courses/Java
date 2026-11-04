import { join } from "node:path";
import { prepareLaunchers } from "../performance/src/launchers/setup.ts";
import { executeScenario } from "../performance/src/runner/execute.ts";
import type { PreparedVariant, Scenario } from "../performance/src/config/types.ts";

const started = performance.now(), root = Deno.args[0];
const reports = "/home/tolya/JavaCodex/java-release-20261110/reports";
const stage = JSON.parse(await Deno.readTextFile(join(root, "stage-metadata.json")));
const sp: string = stage.course;
const setup = await prepareLaunchers({
  workspace: join(root, "launchers"),
  deno: "/opt/quarto/bin/tools/x86_64/deno",
  pandoc: "/opt/quarto/bin/tools/x86_64/pandoc",
  dataDir: "/opt/quarto/share/pandoc/datadir",
});
const preparedAt = performance.now();
const prepared: PreparedVariant = {
  variant: "candidate",
  root: sp,
  manifest: {
    schema_version: 2,
    projectSource: "BSU-RFCT-Afonenko-Courses/System-Practice",
    replacements: [{
      repository: "Afonenko-Course-Tools/quarto-course-prairielearn",
      extensionId: "course-prairielearn",
      destinations: [stage.packageOverride],
    }],
    experimentId: crypto.randomUUID(),
    sources: [{
      repository: "BSU-RFCT-Afonenko-Courses/System-Practice",
      sourceKind: "local-candidate",
      remoteRef: "exact-local-base-archive-with-documented-installed-package-overlays",
      sha: stage.spBaseHead,
      fetchedAt: stage.createdUTC,
      checkoutPath: sp,
    }, {
      repository: "Afonenko-Course-Tools/quarto-course-prairielearn",
      sourceKind: "local-candidate",
      remoteRef: "exact-local-git-archive-no-network-fetch",
      sha: stage.plArchiveHead,
      fetchedAt: stage.createdUTC,
      checkoutPath: stage.plArchiveRoot,
    }],
    packages: [],
    runtime: {},
  },
};
const scenario: Scenario = {
  id: "sp-pl9c-native-command-summary-delta",
  cwd: ".",
  executable: "/opt/quarto/bin/quarto",
  args: ["run", "tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/entrypoints/export-course.ts",
    sp, "{run_output}/public", "--instance", "pilot", "--checks-output", "{run_output}/private",
    "--runtime-registry", join(reports, "sp-native-paired-published-runtime-profiles.json"), "--book", "tasks"],
  cachePolicy: "repeat-render",
  mode: "summary",
  scope: { modules: [], extensions: [], strict: true },
  resources: { wall: true, cpu: true, peakRss: true },
};
const executionStarted = performance.now();
const result = await executeScenario(scenario, prepared, "summary", {
  outputRoot: join(root, "runs"),
  env: { XDG_CACHE_HOME: "/home/tolya/JavaCodex/.cache", QUARTO_RUN_NO_NETWORK: "true", DENO_NO_PACKAGE_JSON: "1" },
  diagnosticEnv: setup.env,
  preparation: setup.preparation,
  cleanPaths: [],
  coverage: { complete: false },
});
const finished = performance.now();
const timing = {
  performanceHead: stage.performanceHead,
  installedPL: stage.plArchiveHead,
  installedCore: stage.installedCore,
  warmState: stage.warmState,
  installedPackagesInterpretation: stage.installedPackagesInterpretation,
  stagePreparationSeconds: stage.stagePreparationSeconds,
  toolPreparationSeconds: (preparedAt - started) / 1000,
  executeAndReportSeconds: (finished - executionStarted) / 1000,
  nativeWallSeconds: result.wallSeconds,
  reportOnlySeconds: null,
  denoCaptureTotalSeconds: (finished - started) / 1000,
  timingScope: "ordinary outer clocks and existing executeScenario wall; native_command is per-launcher spawn-to-status; report-only unknown; no Deno.Command override",
  result,
};
await Deno.writeTextFile(join(root, "capture-timings.json"), JSON.stringify(timing, null, 2) + "\n");
console.log(JSON.stringify(timing));
Deno.exit(result.buildExitCode ? 3 : result.dataValid ? 0 : 4);
