import { nativeCommandMarkdown } from "../performance/src/report/run.ts";

const directory = new URL(
  "./core-native-computed-integration-20261104/runs/ade920b1-3657-4273-a63b-b2f47773982b/processes/",
  import.meta.url,
);
const receipts: Record<string, unknown>[] = [];
for await (const entry of Deno.readDir(directory)) {
  if (entry.isFile && entry.name.endsWith(".json")) {
    const receipt: unknown = JSON.parse(await Deno.readTextFile(new URL(entry.name, directory)));
    if (!receipt || typeof receipt !== "object" || Array.isArray(receipt)) {
      throw new Error("Сохранённый receipt должен быть объектом");
    }
    receipts.push({ ...receipt });
  }
}
await Deno.writeTextFile(
  new URL("./performance-command-table-preview-20261104.md", import.meta.url),
  [
    "# Performance: представление сохранённого computed capture",
    "",
    "Чистый formatter принятого Source356a76b применён к17 уже сохранённым receipts единственного опыта04.11. Native команды и полный export pipeline не запускались; исходный capture не менялся. Это новая таблица прежних наблюдений, не новый замер и не доказательство ускорения курса. R-процесс launcher не наблюдал.",
    "",
    ...nativeCommandMarkdown(receipts),
  ].join("\n"),
);
console.log(`Сохранена таблица: ${receipts.length} receipts`);
