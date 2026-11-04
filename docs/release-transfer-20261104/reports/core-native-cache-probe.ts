import { dirname, join, resolve } from "node:path";
import { fileURLToPath } from "node:url";
import * as Core from "./core-native-cache-integration-20261104/owner/_extensions/course-core/public.ts";
import * as Download from "./core-native-cache-integration-20261104/owner/_extensions/project-download/public.ts";

const root = join(dirname(fileURLToPath(import.meta.url)), "core-native-cache-integration-20261104/owner");
const capture = Number(Deno.args[0]);
if (capture !== 1 && capture !== 2) throw Error("Capture должен быть 1 или 2");
const evidence = join(dirname(root), `artifact-${capture}`);
const ledgerPath = join(dirname(root), "execution-ledger.txt");
const outputDirectory = join(root, "_generated/assignment-output");
const privateMarkers = ["PRIVATE-SOLUTION-SENTINEL", "PRIVATE-REFERENCE-SENTINEL", "reference/Secret.txt"];
function assert(value: unknown, message: string): asserts value {
  if (!value) throw Error(message);
}
function nodes(value: unknown, type: string): number {
  if (Array.isArray(value)) return value.reduce((n, child) => n + nodes(child, type), 0);
  if (!value || typeof value !== "object") return 0;
  return ("t" in value && value.t === type ? 1 : 0) + Object.values(value).reduce<number>((n, child) => n + nodes(child, type), 0);
}
function publicOnly(value: string, phase: string) {
  for (const marker of privateMarkers) assert(!value.includes(marker), phase + " leaked " + marker);
}
function equal(a: Uint8Array, b: Uint8Array) {
  return a.length === b.length && a.every((v, i) => v === b[i]);
}
let phase = "capture-prepare-and-render";
try {
  const descriptor = await Core.withNativeCaptureScope(
    { root, projection: "author", outputDirectory },
    { assignment: { mode: "new", profiles: [], prepare: async () => ({
      expectedSources: ["index.qmd"], assessments: [{ source: "index.qmd", id: "work" }],
    }) } },
    (scope) => scope.withPreparedOperation(async (lease) => {
      await lease.captureInputs(lease.descriptor, ["index.qmd"], {
        args: ["render", "--output-dir", "_generated/assignment-output"], timeoutMs: 45000,
      });
      return lease.descriptor;
    }),
  );
  const ledgerAfterRender = (await Deno.readTextFile(ledgerPath)).trim();
  await Deno.writeTextFile(join(dirname(root), `capture-${capture}-render.json`), JSON.stringify({
    capture, status: "native-capture-returned", ledgerAfterRender, operation: descriptor.identity.operation,
  }, null, 2) + "\n");
  assert(ledgerAfterRender === "1", "Native cell executed again or ledger invalid: " + ledgerAfterRender);
  phase = "current-operation-and-A1-batch";
  const result = await Core.withCurrentNativeOperation({
    root, projection: "author", profiles: ["full"], outputDirectory,
  }, async (op) => {
    assert(op.identity.operation === descriptor.identity.operation, "Different generation");
    await op.validateRelease({ expectedSources: ["index.qmd"] });
    return await op.withProjectContracts([{
      owner: root, inputs: ["projects/one/native-input.qmd"], profiles: ["full"], format: "html",
    }], async (batch) => {
      const context = batch.groups[0]?.contexts[0];
      assert(context, "Missing actual A1 context");
      const student = await Core.planProject(context, { audience: "student" });
      const full = await Core.planProject(context, { audience: "full" });
      phase = "assignment-delivery";
      const delivery = await op.assignmentDelivery({ source: "index.qmd", assessmentId: "work" }, {
        projectPlans: [{ source: "index.qmd", exerciseId: "exr-computed", student, full }],
      });
      const condition = delivery.conditions["exr-computed"];
      assert(condition, "Missing actual assignment condition");
      assert(nodes(condition.body, "Table") === 1, "Computed ordinary table missing");
      assert(nodes(condition.body, "Image") === 1, "Computed ordinary image missing");
      assert(JSON.stringify(condition.body).includes("42"), "Computed inline value missing");
      publicOnly(JSON.stringify(delivery), "assignment-delivery");
      phase = "materialize-project";
      const inventory = await Core.materializeProject(student, condition, {
        submission: { files: [], instructions: "Сдайте Main.txt." },
      });
      const readmeFile = inventory.files.find((file) => file.target === "README.md");
      assert(readmeFile, "README missing");
      const readme = new TextDecoder().decode(readmeFile.bytes);
      publicOnly(readme, "README");
      assert(readme.includes("42") && readme.includes("computed"), "README lost computed content");
      const images = inventory.files.filter((file) => file.target.endsWith(".png"));
      assert(images.length === 1, "Expected one PNG inventory entry");
      const png = images[0];
      assert(equal(png.bytes.slice(0, 8), new Uint8Array([137,80,78,71,13,10,26,10])), "Not PNG bytes");
      assert(readme.includes(png.target), "README image URL does not target inventory");
      for (const file of inventory.files) {
        publicOnly(file.target, "inventory-target");
        publicOnly(new TextDecoder().decode(file.bytes), "inventory-bytes");
      }
      const html = await Deno.readTextFile(join(outputDirectory, "index.html"));
      publicOnly(html, "HTML");
      assert(html.includes("42"), "HTML lost computed inline");
      const htmlSources = [...html.matchAll(/<img\b[^>]*\bsrc="([^"]+\.png)"/g)].map((m) => m[1]);
      assert(htmlSources.length === 1, "Expected one PNG URL in HTML");
      const htmlPng = await Deno.readFile(resolve(outputDirectory, decodeURIComponent(htmlSources[0])));
      assert(equal(htmlPng, png.bytes), "HTML/inventory PNG bytes differ");
      phase = "download-package";
      const zip = await Download.packageArtifact(inventory);
      return {
        inventory, zip, condition, publicPackage: delivery.publicPackage,
        htmlSource: htmlSources[0], pngTarget: png.target,
        a1Metrics: batch.metrics, operationMetrics: op.metrics,
        planMetrics: Core.projectPlanMetrics(student),
      };
    });
  });
  phase = "publish-synthetic-evidence-after-closing-fence";
  await Deno.mkdir(join(evidence, "inventory"), { recursive: true });
  for (const file of result.inventory.files) {
    const target = join(evidence, "inventory", file.target);
    await Deno.mkdir(dirname(target), { recursive: true });
    await Deno.writeFile(target, file.bytes);
  }
  await Deno.writeFile(join(evidence, "artifact.zip"), result.zip);
  await Deno.writeTextFile(join(evidence, "result.json"), JSON.stringify({
    status: "native-and-packaging-pass-pending-independent-ZIP-byte-CRC-check",
    operation: descriptor.identity.operation,
    condition: result.condition, publicPackage: result.publicPackage,
    htmlSource: result.htmlSource, pngTarget: result.pngTarget,
    a1Metrics: result.a1Metrics, operationMetrics: result.operationMetrics,
    planMetrics: result.planMetrics,
    files: result.inventory.files.map(({ bytes: _, ...file }) => file),
  }, null, 2) + "\n");
  await Deno.writeTextFile(join(dirname(root), `capture-${capture}-completed.json`), JSON.stringify({
    capture, status: "native-consumer-and-packaging-completed-pending-independent-ZIP-check",
    ledger: (await Deno.readTextFile(ledgerPath)).trim(), artifactDirectory: evidence,
  }, null, 2) + "\n");
  console.log(`PASS capture ${capture}: Native computed condition, README/inventory PNG, private roles and Download bytes`);
} catch (error) {
  await Deno.writeTextFile(join(dirname(root), "first-failure.json"), JSON.stringify({ capture, phase, error: String(error), stack: error instanceof Error ? error.stack : undefined }, null, 2) + "\n");
  throw error;
}
