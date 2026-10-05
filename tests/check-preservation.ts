import { dirname, fromFileUrl, join } from "stdlib/path";
import { parse } from "stdlib/yaml";

// Исходный курс до переноса пяти частей; Git только читает объекты этого коммита.
const baseline = "c32d3f24612f050eea5e90bac8abeefd587bf971";
const root = dirname(dirname(fromFileUrl(import.meta.url)));
const decoder = new TextDecoder();
function assert(value: unknown, message: string): asserts value {
  if (!value) throw new Error(`Сохранность Java: ${message}`);
}
async function git(args: string[]): Promise<Uint8Array> {
  const result = await new Deno.Command("git", { args, cwd: root, stdout: "piped", stderr: "piped" }).output();
  assert(result.success, decoder.decode(result.stderr).trim() || "не удалось прочитать исходный Git-коммит");
  return result.stdout;
}
function destination(path: string): string {
  if (path.startsWith("book/appendices/")) return path.replace(/^book\//, "handbook/");
  if (path.startsWith("book/")) return path.replace(/^book\//, "theory/");
  return path.replace(/^essay\//, "tasks/");
}
function metadata(text: string): Record<string, unknown> {
  const front = text.match(/^---\r?\n([\s\S]*?)\r?\n---(?:\r?\n|$)/);
  return front ? parse(front[1]) as Record<string, unknown> : {};
}
function words(text: string): string[] {
  return text.replace(/^---\r?\n[\s\S]*?\r?\n---(?:\r?\n|$)/, "")
    .replace(/^\s*:{3,}.*$/gm, "")
    .replace(/^(#+[^\n]*?)\s+\{[^}]+\}\s*$/gm, "$1")
    .replace(/\{#[^}]+\}/g, "")
    .replace(/@(?:book|essay|theory|tasks|handbook):/g, "@")
    .replace(/\]\([^\n]*?\)/g, "]")
    .replace(/^\s*#+\s*/gm, "")
    .replace(/\s+/g, " ").trim().split(" ").filter(Boolean);
}
function blockAttributes(text: string): string[][] {
  // Порядок атрибутов и пробелы не меняют смысл исходной учебной разметки.
  return [...text.matchAll(/^\s*:{3,}\s*\{([^}]+)\}\s*$/gm)].map(match =>
    [...match[1].matchAll(/[^\s=]+(?:=(?:"[^"]*"|'[^']*'|[^\s]+))?/g)].map(token => token[0]).sort()
  );
}
const paths = decoder.decode(await git(["ls-tree", "-r", "--name-only", baseline, "book", "essay"])).trim().split("\n")
  .filter(path => !path.includes("/_extensions/") && !/(?:^|\/)(?:_quarto[^/]*\.yml|_bsu\.yml|\.gitignore)$/.test(path))
  .filter(path => !/^(?:book|essay)\/README\.md$/.test(path));
let chapters = 0, assets = 0, retainedIds = 0;
for (const path of paths) {
  const original = await git(["show", `${baseline}:${path}`]);
  const mapped = destination(path);
  let current: Uint8Array;
  try { current = await Deno.readFile(join(root, mapped)); }
  catch { throw new Error(`Сохранность Java: потерян ${path} -> ${mapped}`); }
  if (!path.endsWith(".qmd")) {
    assert(current.length === original.length && current.every((byte, index) => byte === original[index]), `изменён исходный проект или ресурс ${mapped}`);
    assets++;
    continue;
  }
  const before = decoder.decode(original), after = decoder.decode(current);
  const currentIds = new Set([...after.matchAll(/\{#([\w-]+)/g)].map(match => match[1]));
  for (const match of before.matchAll(/\{#([\w-]+)/g)) {
    assert(currentIds.has(match[1]), `потерян авторский ID ${match[1]} в ${mapped}`);
    retainedIds++;
  }
  assert(JSON.stringify(blockAttributes(after)) === JSON.stringify(blockAttributes(before)), `изменены исходные атрибуты учебных блоков в ${mapped}`);
  for (const key of ["semester", "difficulty", "categories", "demo"]) {
    const value = metadata(before)[key];
    if (value !== undefined) assert(JSON.stringify(metadata(after)[key]) === JSON.stringify(value), `изменены метаданные ${key} в ${mapped}`);
  }
  // Все предметные главы, задания и решения
  // сохраняют исходный текст по порядку;
  // новые заголовки и учебные обёртки допускаются между исходными словами.
  const expected = words(before), actual = words(after);
  let position = 0;
  for (const word of expected) {
    const found = actual.indexOf(word, position);
    assert(found >= 0, `потерян исходный текст в ${mapped} возле «${word}» (слово ${position})`);
    position = found + 1;
  }
  chapters++;
}
console.log(`Исходный курс ${baseline.slice(0, 7)} сохранён: ${chapters} QMD с прежними учебными блоками, ${retainedIds} авторских ID и ${assets} неизменённых файлов проектов/ресурсов.`);
