import { join } from "node:path";
import { prepareLaunchers } from "../performance/src/launchers/setup.ts";
import { executeScenario } from "../performance/src/runner/execute.ts";
import type { PreparedVariant, Scenario } from "../performance/src/config/types.ts";

const stage = Deno.args[0], started = performance.now();
const release = "/home/tolya/JavaCodex/java-release-20261110";
const metadata = JSON.parse(await Deno.readTextFile(join(stage, "stage-metadata.json")));
const setup = await prepareLaunchers({ workspace: join(stage, "launchers"),
  deno: "/opt/quarto/bin/tools/x86_64/deno", pandoc: "/opt/quarto/bin/tools/x86_64/pandoc",
  dataDir: "/opt/quarto/share/pandoc/datadir" });
const preparedAt = performance.now();
const prepared: PreparedVariant = { variant: "candidate", root: join(stage, "owner"), manifest: {
  schema_version: 2, projectSource: "Afonenko-Course-Tools/quarto-course-core",
  replacements: [{ repository: "Afonenko-Course-Tools/quarto-course-core", extensionId: "course-core", destinations: ["_extensions/course-core"] },
    { repository: "Afonenko-Course-Tools/quarto-project-download", extensionId: "project-download", destinations: ["_extensions/project-download"] }],
  experimentId: crypto.randomUUID(), sources: [
    { repository: "Afonenko-Course-Tools/quarto-course-core", sourceKind: "local-candidate", remoteRef: "exact-local-accepted-commit-synthetic-fixture", sha: metadata.coreHead, fetchedAt: metadata.createdUTC, checkoutPath: join(release, "core-native-bridge") },
    { repository: "Afonenko-Course-Tools/quarto-project-download", sourceKind: "local-candidate", remoteRef: "exact-local-accepted-commit", sha: metadata.downloadHead, fetchedAt: metadata.createdUTC, checkoutPath: join(release, "download") },
    { repository: "Afonenko-Course-Tools/quarto-course-performance", sourceKind: "local-candidate", remoteRef: "exact-local-accepted-commit", sha: metadata.performanceHead, fetchedAt: metadata.createdUTC, checkoutPath: join(release, "performance") },
  ], packages: [], runtime: {},
} };
await Deno.writeTextFile(join(stage, "performance-preparation.json"), JSON.stringify({
  setupOuterSeconds: (preparedAt - started) / 1000, status: "prepared-before-first-capture",
}, null, 2) + "\n");
const captures = [];
for (const capture of [1, 2]) {
const captureStarted = performance.now();
const scenario: Scenario = { id: `core-native-cache-capture-${capture}`, cwd: ".",
  executable: "/opt/quarto/bin/quarto", args: ["run", join(release, "reports/core-native-cache-probe.ts"), String(capture)],
  cachePolicy: "repeat-render", mode: "summary", scope: { modules: [], extensions: [], strict: true },
  resources: { wall: true, cpu: false, peakRss: false } };
const result = await executeScenario(scenario, prepared, "summary", {
  outputRoot: join(stage, "runs"), env: { XDG_CACHE_HOME: "/home/tolya/JavaCodex/.cache",
    DENO_DIR: "/home/tolya/.cache/quarto/deno_std/cache", DENO_NO_PACKAGE_JSON: "1", QUARTO_RUN_NO_NETWORK: "true" },
  diagnosticEnv: setup.env, preparation: setup.preparation, cleanPaths: [], coverage: { complete: false },
});
const observation = {capture, captureAndReportOuterSeconds: (performance.now() - captureStarted) / 1000, result};
captures.push(observation);
await Deno.writeTextFile(join(stage, `capture-${capture}-performance.json`), JSON.stringify(observation, null, 2) + "\n");
await Deno.writeTextFile(join(stage, "capture-timings.json"), JSON.stringify({
  setupOuterSeconds: (preparedAt - started) / 1000, captures,
  timingScope: "inclusive outer/native intervals are not additive semantic timings",
}, null, 2) + "\n");
if (result.buildExitCode || !result.dataValid) Deno.exit(result.buildExitCode ? 3 : 4);
}
