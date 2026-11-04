import { join } from "node:path";
import { prepareLaunchers } from "../performance/src/launchers/setup.ts";
import { executeScenario } from "../performance/src/runner/execute.ts";
import type { PreparedVariant, Scenario } from "../performance/src/config/types.ts";

const started = performance.now(), root = Deno.args[0];
const stage = JSON.parse(await Deno.readTextFile(join(root, "stage-metadata.json")));
const setup = await prepareLaunchers({
  workspace: join(root, "launchers"),
  deno: "/opt/quarto/bin/tools/x86_64/deno",
  pandoc: "/opt/quarto/bin/tools/x86_64/pandoc",
  dataDir: "/opt/quarto/share/pandoc/datadir",
});
const preparedAt = performance.now();
const prepared: PreparedVariant = {
  variant: "candidate",
  root: stage.course,
  manifest: {
    schema_version: 2,
    projectSource: "BSU-RFCT-Afonenko-Courses/System-Practice",
    replacements: stage.replacements.map((p: { repository: string; extensionId: string; destinations: string[] }) => ({
      repository: p.repository, extensionId: p.extensionId, destinations: p.destinations,
    })),
    experimentId: crypto.randomUUID(),
    sources: [{
      repository: "BSU-RFCT-Afonenko-Courses/System-Practice",
      sourceKind: "local-candidate", remoteRef: "exact-local-git-archive-no-network-fetch",
      sha: stage.spBaseHead, fetchedAt: stage.createdUTC, checkoutPath: stage.course,
    }, ...stage.replacements.map((p: { repository: string; sha: string; archiveRoot: string }) => ({
      repository: p.repository, sourceKind: "local-candidate" as const,
      remoteRef: "exact-local-git-archive-no-network-fetch", sha: p.sha,
      fetchedAt: stage.createdUTC, checkoutPath: p.archiveRoot,
    }))],
    packages: [], runtime: {},
  },
};
const scenario: Scenario = {
  id: "sp-shared-site6-student-root-stage",
  cwd: ".", executable: "/opt/quarto/bin/quarto",
  args: ["render", ".", "--profile", "student"],
  cachePolicy: "repeat-render", mode: "summary",
  scope: { modules: [], extensions: [], strict: true },
  resources: { wall: true, cpu: true, peakRss: true },
};
const executionStarted = performance.now();
const result = await executeScenario(scenario, prepared, "summary", {
  outputRoot: join(root, "runs"),
  env: { XDG_CACHE_HOME: "/home/tolya/JavaCodex/.cache", DENO_DIR: "/home/tolya/.cache/quarto/deno_std/cache",
    DENO_NO_UPDATE_CHECK: "1", QUARTO_RUN_NO_NETWORK: "true", DENO_NO_PACKAGE_JSON: "1", QUARTO: "/opt/quarto/bin/quarto" },
  diagnosticEnv: setup.env, preparation: setup.preparation,
  cleanPaths: [], coverage: { complete: false },
});
const finished = performance.now();
const timing = {
  performanceHead: stage.performanceHead, replacements: stage.replacements,
  installedPackagesInterpretation: stage.installedPackagesInterpretation, warmState: stage.warmState,
  stagePreparationSeconds: stage.stagePreparationSeconds,
  launcherPreparationSeconds: (preparedAt - started) / 1000,
  executeAndReportSeconds: (finished - executionStarted) / 1000,
  nativeWallSeconds: result.wallSeconds, reportOnlySeconds: null,
  denoCaptureTotalSeconds: (finished - started) / 1000,
  timingScope: "existing executeScenario; launcher intervals inclusive/partial/nonadditive; report-only unknown",
  result,
};
await Deno.writeTextFile(join(root, "capture-timings.json"), JSON.stringify(timing, null, 2) + "\n");
console.log(JSON.stringify(timing));
Deno.exit(result.buildExitCode ? 3 : result.dataValid ? 0 : 4);
