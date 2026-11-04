import { nativeCommandMarkdown } from "../../performance/src/report/run.ts";

const directory = new URL("./runs/6d0026fe-08a9-45ca-8e94-023083fa5056/processes/", import.meta.url);
const receipts: Record<string, unknown>[] = [];
for await (const entry of Deno.readDir(directory)) {
  if (entry.isFile && entry.name.endsWith(".json")) {
    if (receipts.length >= 500) throw new Error("Bounded receipt read cap exceeded");
    const receipt: unknown = JSON.parse(await Deno.readTextFile(new URL(entry.name, directory)));
    if (!receipt || typeof receipt !== "object" || Array.isArray(receipt)) {
      throw new Error("Сохранённый receipt должен быть объектом");
    }
    receipts.push({ ...receipt });
  }
}
await Deno.writeTextFile(new URL("./command-intervals-incomplete.md", import.meta.url), [
  "# TIMEOUT / INCOMPLETE: SP shared Site6 command intervals",
  "",
  "Первый actual root student render остановлен timeout: outer115.005s (включая Deno/launcher preparation), exit124; это НЕ PASS. Четыре child render завершены exit0 (theory, lectures, tasks, practice); handbook и root completion UNKNOWN. Root output отсутствует. Итоговый Performance dataValid/VALID не сформирован; CPU, whole closure, mount/QRC/download link correctness не квалифицированы.",
  "",
  `Чистый nativeCommandMarkdown принятого Performance356 применён к${receipts.length} сохранённым process receipts этого прерванного опыта. Pending/missing timing остаётся UNKNOWN, не0. Интервалы inclusive, partial и nonadditive: не суммировать как CPU/self/critical path. Сохранённые receipts/исторические captures не менялись; Native, writeRunArtifacts, normalize и полный report pipeline не вызывались. Это postprocess вне Native cap, не benchmark/новый run.`,
  "",
  ...nativeCommandMarkdown(receipts),
  "",
].join("\n"));
console.log(`Сохранена incomplete таблица: ${receipts.length} receipts`);
