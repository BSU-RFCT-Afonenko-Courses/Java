import { strict as assert } from "node:assert";
import { join } from "node:path";
import { normalizeRun, validatedIntervals } from "../performance/src/analysis/normalize.ts";
import { jsonFiles, summarizeRunRecord } from "../performance/src/report/run.ts";
import { traces } from "../performance/src/report/perfetto.ts";
import * as previousNormalize from "./performance-own-cost-baseline/normalize.ts";
import * as previousReport from "./performance-own-cost-baseline/run.ts";
import * as previousPerfetto from "./performance-own-cost-baseline/perfetto.ts";
import type { RunRecord } from "../performance/src/report/types.ts";

const reports = "/home/tolya/JavaCodex/java-release-20261110/reports";
const directory = join(reports, "performance-sp-summary-capture/runs/b2b5cee6-0382-4326-83e3-457b113d4562");
const start = performance.now();
// Parse/check saved raw exactly once; never update its expectations/coverage/artifacts.
const normalized = await normalizeRun(directory, { readOnly: true });
const record: RunRecord = JSON.parse(await Deno.readTextFile(join(directory, "run.json")));
const processes = await jsonFiles(join(directory, "processes"), ".json");
const readSeconds = (performance.now() - start) / 1000;
function timed<T>(action: () => T): { seconds: number; result: T } {
  const before = performance.now();
  const result = action();
  return { seconds: (performance.now() - before) / 1000, result };
}
const oldIntervals = timed(() => previousNormalize.validatedIntervals(normalized));
const newIntervals = timed(() => validatedIntervals(normalized));
assert.deepEqual(newIntervals.result, oldIntervals.result);
const oldSummary = timed(() => previousReport.summarizeRunRecord(record, normalized));
const newSummary = timed(() => summarizeRunRecord(record, normalized));
assert.deepEqual(newSummary.result, oldSummary.result);
const oldTrace = timed(() => previousPerfetto.traces(normalized, processes));
const newTrace = timed(() => traces(normalized, processes));
assert.deepEqual(newTrace.result, oldTrace.result);
const result = {
  baseline: "cff3edd8fd479dcd861a1b36e919d67e919f27eb",
  candidate: "working tree: fixed two-row sequence selection; no public API/schema changes",
  rawDirectory: directory,
  originalReadOnly: true,
  events: normalized.events.length,
  processes: processes.length,
  intervals: newIntervals.result.length,
  operations: newSummary.result.operations.length,
  quality: normalized.quality,
  dataValid: normalized.dataValid,
  equality: { intervals: "exact", summary: "exact", traceBundle: "exact" },
  seconds: {
    readAndNormalizeOnce: readSeconds,
    baselineIntervals: oldIntervals.seconds,
    candidateIntervals: newIntervals.seconds,
    baselineSummary: oldSummary.seconds,
    candidateSummary: newSummary.seconds,
    baselineTraces: oldTrace.seconds,
    candidateTraces: newTrace.seconds,
    total: (performance.now() - start) / 1000,
  },
  timingLimit: "one descriptive old-then-new observation; JIT/warm order uncontrolled; no speedup/acceptance claim",
};
await Deno.writeTextFile(join(reports, "performance-own-cost-comparison.json"), JSON.stringify(result, null, 2) + "\n");
console.log(JSON.stringify(result));
