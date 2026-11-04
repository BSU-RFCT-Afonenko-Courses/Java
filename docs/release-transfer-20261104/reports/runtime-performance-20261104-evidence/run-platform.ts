import { executeScenario } from "../../performance-lifecycle/src/runner/execute.ts";
import type { PreparedVariant, Scenario } from "../../performance-lifecycle/src/config/types.ts";

const root = "/home/tolya/JavaCodex/java-release-20261110/platform";
const outputRoot = new URL("./platform-runs/", import.meta.url).pathname;
const scenario: Scenario = {
  id: "platform-correct42-junit-xml",
  cwd: ".",
  executable: "/usr/bin/python3",
  args: ["-B", "-m", "unittest", "discover", "-s", "tests", "-p", "test_plain_runtime.py", "-k", "correct_answer", "-v"],
  cachePolicy: "repeat-render",
  mode: "off",
  scope: { modules: [], extensions: [], strict: false },
  resources: { wall: true, cpu: true, peakRss: true },
};
const prepared: PreparedVariant = {
  variant: "candidate",
  root,
  manifest: {
    schema_version: 2,
    projectSource: "Afonenko-Course-Tools/prairielearn-platform",
    replacements: [],
    experimentId: "platform-runtime-20261104",
    sources: [],
    packages: [],
    runtime: {},
  },
};
const result = await executeScenario(scenario, prepared, "off", { outputRoot });
console.log(JSON.stringify({ directory: result.directory, record: result.record }));
Deno.exit(result.record.completion.kind === "complete" ? result.record.completion.native.code : 4);
