import { join } from "node:path";
import { prepareLaunchers } from "../performance/src/launchers/setup.ts";
import { executeScenario } from "../performance/src/runner/execute.ts";
import type { PreparedVariant, Scenario } from "../performance/src/config/types.ts";

// One authorized capture through existing APIs; no source/input/output cleanup.
const started = performance.now();
const reports = "/home/tolya/JavaCodex/java-release-20261110/reports";
const root = join(reports, "performance-sp-summary-capture");
const sp = "/home/tolya/JavaCodex/java-release-20261110/system-practice";
await Deno.mkdir(root);
const setup = await prepareLaunchers({
  workspace: join(root, "launchers"),
  deno: "/opt/quarto/bin/tools/x86_64/deno",
  pandoc: "/opt/quarto/bin/tools/x86_64/pandoc",
  dataDir: "/opt/quarto/share/pandoc/datadir",
});
const preparedAt = performance.now();
const experimentId = crypto.randomUUID();
const prepared: PreparedVariant = {
  variant: "candidate",
  root: sp,
  manifest: {
    schema_version: 2,
    projectSource: "BSU-RFCT-Afonenko-Courses/System-Practice",
    replacements: [],
    experimentId,
    sources: [{
      repository: "BSU-RFCT-Afonenko-Courses/System-Practice",
      sourceKind: "local-candidate",
      remoteRef: "frozen-release-checkout",
      sha: "3bb0e0789545420b9947bdd85fac3d71715d0e36",
      fetchedAt: "2026-11-03T18:07:01.968560+00:00",
      checkoutPath: sp,
    }],
    // No new package/runtime hashes: exact installed pins are preserved below.
    packages: [],
    runtime: {},
  },
};
const scenario: Scenario = {
  id: "sp-native-export-summary-propagation",
  cwd: ".",
  executable: "/opt/quarto/bin/quarto",
  args: [
    "run",
    "tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/entrypoints/export-course.ts",
    sp,
    "{run_output}/public",
    "--instance",
    "pilot",
    "--checks-output",
    "{run_output}/private",
    "--runtime-registry",
    join(reports, "sp-native-paired-published-runtime-profiles.json"),
    "--book",
    "tasks",
  ],
  cachePolicy: "repeat-render",
  mode: "summary",
  scope: { modules: [], extensions: [], strict: true },
  resources: { wall: true, cpu: true, peakRss: true },
};
const actualCommand = Deno.Command;
let commandStarted: number | undefined, commandFinished: number | undefined;
// Forward the single real command unchanged; clocks only separate native/report stages.
Deno.Command = class extends actualCommand {
  override async output(): Promise<Deno.CommandOutput> {
    commandStarted = performance.now();
    try {
      return await super.output();
    } finally {
      commandFinished = performance.now();
    }
  }
};
const executionStarted = performance.now();
let exitCode = 4;
try {
  const result = await executeScenario(scenario, prepared, "summary", {
    outputRoot: join(root, "runs"),
    env: {
      XDG_CACHE_HOME: "/home/tolya/JavaCodex/.cache",
      QUARTO_RUN_NO_NETWORK: "true",
      DENO_NO_PACKAGE_JSON: "1",
    },
    diagnosticEnv: setup.env,
    preparation: setup.preparation,
    cleanPaths: [],
    coverage: { complete: false },
  });
  const finished = performance.now();
  const timings = {
    performanceHead: "cff3edd8fd479dcd861a1b36e919d67e919f27eb",
    installedCore: "d7cc10eaa21fa35bbe32d73cc4afa216002393da",
    installedPL: "da2ad6b6c00891590e59e5abccf3d1eb4ff552f7",
    warmState: "persistent XDG cache and existing native scratch; no cleanup; no new off baseline",
    totalCaptureSeconds: (finished - started) / 1000,
    toolPreparationSeconds: (preparedAt - started) / 1000,
    hostBookkeepingBeforeExecuteSeconds: (executionStarted - preparedAt) / 1000,
    executePreCommandSeconds: commandStarted === undefined ? null :
      (commandStarted - executionStarted) / 1000,
    nativeWallSeconds: result.wallSeconds,
    commandEnvelopeSeconds: commandStarted === undefined || commandFinished === undefined ? null :
      (commandFinished - commandStarted) / 1000,
    postNativeStatusNormalizeReportSeconds: commandFinished === undefined ? null :
      (finished - commandFinished) / 1000,
    timingScope: "host monotonic clocks; native wall from existing resource-child; report stage includes status parsing/normalization/artifacts",
    result,
  };
  await Deno.writeTextFile(join(root, "capture-timings.json"), JSON.stringify(timings, null, 2) + "\n");
  console.log(JSON.stringify(timings));
  exitCode = result.buildExitCode ? 3 : result.dataValid ? 0 : 4;
} finally {
  Deno.Command = actualCommand;
}
Deno.exit(exitCode);
