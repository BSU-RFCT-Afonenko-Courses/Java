import { stateSchema } from "./schema.ts";
import { decode } from "file:///home/tolya/JavaCodex/java-release-20261110/java/tasks/_extensions/Afonenko-Course-Tools/course-core/document/contracts.ts";
const path = "/home/tolya/JavaCodex/java-release-20261110/java/tasks/_generated/course-spec/native-ts/f7da2eb2-c4da-41cd-b7e3-1b9ed89a37ea/state.json";
try {
  if (Deno.version.deno !== "2.7.14") throw new Error("RUNTIME_MISMATCH");
  const start = performance.now();
  const text = await Deno.readTextFile(path);
  const readMs = performance.now() - start;
  const stateBytes = new TextEncoder().encode(text).byteLength;
  const raw = [];
  let counts;
  for (let iteration = 1; iteration <= 8; iteration++) {
    const p = performance.now();
    const value = JSON.parse(text);
    const parsed = performance.now();
    const state = decode(stateSchema, value, "OPERATION_BINDING");
    const decoded = performance.now();
    raw.push({iteration, parseMs: parsed - p, decodeMs: decoded - parsed});
    counts = {inputs: state.inputs.length, rows: state.rows.length, memberships: Object.keys(state.memberships).length};
  }
  const median = (xs: number[]) => { const a = xs.toSorted((a,b) => a-b); return (a[3]+a[4])/2; };
  console.log(JSON.stringify({status:"ok", schemaId:"course-native-operation-private-v2", runtime:Deno.version.deno, stateBytes, readCount:1, iterations:8, readMs, raw, first:raw[0], median:{parseMs:median(raw.map(x=>x.parseMs)), decodeMs:median(raw.map(x=>x.decodeMs))}, counts}));
} catch {
  console.log(JSON.stringify({status:"failed", reason:"runtime-or-read-or-schema-failure-no-payload-logged"}));
  Deno.exitCode=1;
}
