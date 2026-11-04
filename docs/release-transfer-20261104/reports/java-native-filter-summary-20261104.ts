import { join } from "node:path";
import { pathToFileURL } from "node:url";
import { prepareLaunchers } from "./java-native-filter-summary-20261104-actual/providers/performance/src/launchers/setup.ts";
import { executeScenario } from "./java-native-filter-summary-20261104-actual/providers/performance/src/runner/execute.ts";
import { instrumentModule } from "./java-native-filter-summary-20261104-actual/providers/performance/src/instrumentation/ts/transform.ts";
import { hashFile } from "./java-native-filter-summary-20261104-actual/providers/performance/src/sources/snapshot.ts";
import type { PreparationReceipt, PreparedVariant, Scenario, SourceReceipt } from "./java-native-filter-summary-20261104-actual/providers/performance/src/config/types.ts";

const started = performance.now();
const root = Deno.args[0];
if (root !== "/home/tolya/JavaCodex/java-release-20261110/reports/java-native-filter-summary-20261104-actual") {
  throw new Error("Нужен exact новый report-local stage");
}
const course = join(root, "course"), courseRepository = "BSU-RFCT-Afonenko-Courses/Java";
const coreRef = "4a2903e6d03d1dd489b6922d113100826913cf67";
const plRef = "d8efb030ee6435f3c1f7d242e7388803b55b3299";
const javaRef = "a6d28a42d3f0d4f4bbebcd64b5664ebef8341932";
const perfRef = "c2dcf002a7396aa3b507e49b99e0ecf00ed9f438";
const write = async (name: string, value: unknown) =>
  await Deno.writeTextFile(join(root, name), JSON.stringify(value, null, 2) + "\n");
let phase = "prepare-launchers", nativeAttempted = false;
try {
  await write("phase.json", { phase, nativeAttempted });
  const setup = await prepareLaunchers({
    workspace: join(root, "launchers"),
    deno: "/opt/quarto/bin/tools/x86_64/deno",
    pandoc: "/opt/quarto/bin/tools/x86_64/pandoc",
    dataDir: "/opt/quarto/share/pandoc/datadir",
  });
  const pandocLauncher = join(root, "launchers/pandoc");
  const launcherSource = await Deno.readTextFile(pandocLauncher);
  const launcherSuffix = ' pandoc "$@"\n';
  if (!launcherSource.endsWith(launcherSuffix)) throw new Error("Unexpected prepared Pandoc launcher suffix");
  const verboseLauncher = launcherSource.slice(0, -launcherSuffix.length) + ' pandoc "$@" --verbose\n';
  await Deno.writeTextFile(pandocLauncher, verboseLauncher);
  await write("pandoc-verbose-preparation.json", {
    path: pandocLauncher, original: launcherSource, prepared: verboseLauncher,
    soleLauncherArgumentAddition: "--verbose", pandocVersion: "UNKNOWN",
    diagnosticScope: "top-level native filter process CPU only; no DEBUG/trace/AST profiler",
  });
  const launcherPrepared = performance.now();
  await write("launcher-preparation.json", {
    launcherPreparationSeconds: (launcherPrepared - started) / 1000,
    preparation: setup.preparation,
    scope: "known prepareLaunchers, not generic package inventory/capability probes",
  });
  phase = "targeted-existing-transform";
  await write("phase.json", { phase, nativeAttempted });
  const tsScope = [
    { path: "tasks/_extensions/Afonenko-Course-Tools/course-core/runtime/operation.ts",
      required: ["assertNativeAssignmentCapture", "json", "readReservation", "assertReservationCurrent"] },
    { path: "tasks/_extensions/Afonenko-Course-Tools/course-core/document/contracts.ts", required: ["decode"] },
  ];
  const preparation: PreparationReceipt[] = [...setup.preparation];
  const observations = [];
  for (const scope of tsScope) {
    const path = join(course, scope.path), source = await Deno.readTextFile(path);
    const result = instrumentModule(source, scope.path, {
      strict: true, bridgeUrl: pathToFileURL(setup.bridgePath).href, topLevelFunctions: scope.required,
    });
    if (result.symbols.length !== scope.required.length) {
      throw new Error("Actual map выходит за выбранный top-level scope: " + scope.path);
    }
    const required = scope.required.map((name) => {
      const signature = new RegExp("^(?:export\\s+)?(?:async\\s+)?function\\s+" + name + "(?:\\s*<|\\s*\\()");
      const matches = result.symbols.filter((symbol) => signature.test(source.slice(symbol.start, symbol.end)));
      if (matches.length !== 1 || matches[0].manuallyWrapped) {
        throw new Error("Нет actual instrumented symbol: " + scope.path + ":" + name);
      }
      return { name, ...matches[0] };
    });
    const sourceHash = await hashFile(path);
    await Deno.writeTextFile(path, result.source);
    const map = path + ".symbols.json";
    await Deno.writeTextFile(map, JSON.stringify(result.symbols, null, 2) + "\n");
    preparation.push({ kind: "ts-transform", path, sourceHash, preparedHash: await hashFile(path), sourceMapPath: map });
    observations.push({ path: scope.path, required, symbols: result.symbols, skipped: result.skipped });
  }
  const coverage = {
    complete: false, installed: { ts: true, lua: true }, instrumented: { ts: true, lua: true },
    observed: { ts: false, lua: false }, integrity: "pending",
    scopeKind: "selected-functions-and-semantic", luaAutomaticTrace: false, requestedExtensions: ["course-core", "course-prairielearn"],
    ts: observations, luaModules: [],
    limitation: "selected top-level TS functions only; Lua existing manual phases; no automatic request CPU/global Performance debug hook; no package census",
  };
  const scopePath = join(root, "scope.json");
  await Deno.writeTextFile(scopePath, JSON.stringify(coverage, null, 2) + "\n");
  const preparedAt = performance.now();
  await write("targeted-preparation.json", {
    targetedTransformSeconds: (preparedAt - launcherPrepared) / 1000,
    driverBeforeNativeSeconds: (preparedAt - started) / 1000,
    coverageComplete: false, requiredSymbols: observations.map((o) => ({ path: o.path, required: o.required, skipped: o.skipped })),
    preparation,
  });
  const created = new Date().toISOString();
  const replacements = [
    { repository: "Afonenko-Course-Tools/quarto-course", extensionId: "course-core",
      destinations: [".", "theory", "tasks", "lectures", "practice", "handbook"].map((owner) =>
        join(owner, "_extensions/Afonenko-Course-Tools/course-core")) },
    { repository: "Afonenko-Course-Tools/quarto-course-prairielearn", extensionId: "course-prairielearn",
      destinations: ["tasks/_extensions/Afonenko-Course-Tools/course-prairielearn"] },
  ];
  const prepared: PreparedVariant = {
    variant: "candidate", root: course,
    manifest: {
      schema_version: 2, projectSource: courseRepository, replacements, experimentId: crypto.randomUUID(),
      sources: [
        { repository: courseRepository, sha: javaRef, checkoutPath: course },
        { repository: replacements[0].repository, sha: coreRef, checkoutPath: join(root, "providers/core-native-bridge") },
        { repository: replacements[1].repository, sha: plRef, checkoutPath: join(root, "providers/prairielearn") },
      ].map((source): SourceReceipt => ({ ...source, sourceKind: "local-candidate", remoteRef: "exact-local-git-archive-no-network-fetch", fetchedAt: created })),
      packages: [], runtime: {},
    },
  };
  const registry = "/home/tolya/JavaCodex/java-release-20261110/reports/sp-native-paired-published-runtime-profiles.json";
  const scenario: Scenario = {
    id: "java128-native-filter-summary-prefix", cwd: ".", executable: "/opt/quarto/bin/quarto",
    args: ["run", "tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/entrypoints/export-course.ts",
      "{course_root}", "{run_output}/pl-native", "--instance", "2026-2027-s1", "--checks-output", "{run_output}/project-checks",
      "--runtime-registry", "{runtime_registry}", "--book", "tasks"],
    cachePolicy: "repeat-render", mode: "summary", scope: { modules: tsScope.map((s) => s.path), extensions: coverage.requestedExtensions, strict: true },
    resources: { wall: true, cpu: true, peakRss: true },
  };
  phase = "native-execute";
  await write("native-start.json", { phase, nativeAttempted: true, startedUTC: new Date().toISOString(),
    performanceRef: perfRef, javaRef, coreRef, plRef, scenario, runtimeRegistry: registry,
    preparationPersistedBeforeNative: true, nativeMaximumAttempts: 1, retry: 0,
    markerScope: "executeScenario invocation follows; this marker is not Native spawn/status proof" });
  await write("phase.json", { phase, nativeAttempted: true });
  console.error("ПЕРЕД NATIVE: один полный Java128 export через existing Performance summary с TS named selector и Lua manual-only и Pandoc --verbose; ПОСЛЕДНЯЯ попытка пакета; общий outer cap120s включая уже выполненную preparation, retry0.");
  nativeAttempted = true;
  const executeStarted = performance.now();
  const result = await executeScenario(scenario, prepared, "summary", {
    outputRoot: join(root, "runs"), cleanPaths: [], runtimeRegistry: registry,
    preparation, coverage,
    env: { XDG_CACHE_HOME: "/home/tolya/JavaCodex/.cache", DENO_DIR: "/home/tolya/.cache/quarto/deno_std/cache",
      DENO_NO_UPDATE_CHECK: "1", QUARTO_RUN_NO_NETWORK: "true", DENO_NO_PACKAGE_JSON: "1", QUARTO: "/opt/quarto/bin/quarto" },
    diagnosticEnv: { ...setup.env, COURSE_PERF_SCOPE_FILE: scopePath },
  });
  await write("capture-result.json", { result, executeAndReportSeconds: (performance.now() - executeStarted) / 1000,
    driverTotalSeconds: (performance.now() - started) / 1000, nativeWallScope: "actual executeScenario/resource override; no pure startup estimate" });
  console.log(JSON.stringify({ result }));
  Deno.exit(result.buildExitCode ? 3 : result.dataValid ? 0 : 4);
} catch (error) {
  await write(nativeAttempted ? "execution-error.json" : "preparation-failure.json", {
    phase, nativeAttempted, elapsedDriverSeconds: (performance.now() - started) / 1000,
    error: error instanceof Error ? { name: error.name, message: error.message } : String(error), retry: 0,
  });
  throw error;
}
