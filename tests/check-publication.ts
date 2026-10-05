import { dirname, fromFileUrl, join, relative, resolve } from "stdlib/path";
import { parse } from "stdlib/yaml";

const root = dirname(dirname(fromFileUrl(import.meta.url)));
const members = ["theory", "tasks", "lectures", "practice", "handbook"];
const decoder = new TextDecoder();
function assert(value: unknown, message: string): asserts value {
  if (!value) throw new Error(`Публикация Java: ${message}`);
}
async function files(directory: string): Promise<string[]> {
  const result: string[] = [];
  for await (const item of Deno.readDir(directory)) {
    const path = join(directory, item.name);
    if (item.isDirectory) result.push(...await files(path));
    else if (item.isFile) result.push(path);
    else throw new Error(`Нерегулярный файл в публикации: ${path}`);
  }
  return result;
}
function zipNames(bytes: Uint8Array): string[] {
  const view = new DataView(bytes.buffer, bytes.byteOffset, bytes.byteLength);
  const names: string[] = [];
  let offset = 0;
  while (offset + 30 <= bytes.length && view.getUint32(offset, true) === 0x04034b50) {
    const length = view.getUint16(offset + 26, true);
    const extra = view.getUint16(offset + 28, true);
    const size = view.getUint32(offset + 18, true);
    assert(view.getUint16(offset + 8, true) === 0, "стартовый ZIP должен использовать STORE");
    assert(offset + 30 + length + extra + size <= bytes.length, "повреждён стартовый ZIP");
    names.push(decoder.decode(bytes.subarray(offset + 30, offset + 30 + length)));
    offset += 30 + length + extra + size;
  }
  assert(names.length > 0, "пустой или неверный стартовый ZIP");
  return names;
}
function anchors(html: string): Set<string> {
  return new Set([...html.matchAll(/\b(?:id|name)=["']([^"']+)["']/g)].map(match => match[1]));
}

const config = parse(await Deno.readTextFile(join(root, "_quarto.yml"))) as Record<string, any>;
const tasks = parse(await Deno.readTextFile(join(root, "tasks/_quarto.yml"))) as Record<string, any>;
const exports = Object.entries(config["reference-catalog"].exports)
  .flatMap(([namespace, ids]) => (ids as string[]).map(id => `${namespace}:${id}`)).sort();
const closed = new Set<string>();
const solutions = new Set<string>();
// Эти два пояснения открыты в исходном Java-курсе и сохраняются в обоих профилях.
const openSolutions = new Set(["sol-essay-unicode-prediction", "sol-essay-unicode-self-check"]);
const privateProse = ["Базовые критерии автоматической проверки:", "Закрытый стенд подставляет реализации в отдельные процессы JUnit."];
for (const file of (await files(join(root, "tasks"))).filter(path => path.endsWith(".qmd") && !path.includes("/_extensions/"))) {
  const text = await Deno.readTextFile(file);
  if (file.endsWith("/_control.qmd") && text.includes(".when-full")) {
    for (const match of text.matchAll(/\{#(exr-[\w-]+)/g)) closed.add(match[1]);
  }
  for (const match of text.matchAll(/\{#(sol-[\w-]+)/g)) solutions.add(match[1]);
}
assert(closed.size > 0, "не найдены исходные закрытые задания контроля");
assert(solutions.size > 0, "не найдены исходные открытые объяснения");
for (const id of openSolutions) assert(solutions.has(id), `потеряно открытое пояснение ${id}`);
const privateSolutions = [...solutions].filter(id => !openSolutions.has(id));
const chapterPages: string[] = [];
for (const member of ["theory", "tasks", "handbook"]) {
  for (const source of await files(join(root, member))) {
    const path = relative(root, source);
    if (!path.endsWith(".qmd") || path.split("/").some(part => part.startsWith("_") || part.startsWith(".") || ["projects", "tests", "docs"].includes(part))) continue;
    chapterPages.push(path.replace(/\.qmd$/, ".html"));
  }
}

let links = 0, fragments = 0;
for (const profile of ["full", "student"]) {
  const output = join(root, `_site-${profile}`);
  try { await Deno.stat(join(output, "index.html")); }
  catch { throw new Error(`Публикация Java: профиль ${profile} ещё не собран; запустите quarto render --profile ${profile}`); }
  const paths = await files(output);
  for (const chapter of chapterPages) {
    try { await Deno.stat(join(output, chapter)); }
    catch { throw new Error(`Публикация Java: ${profile}: потеряна глава ${chapter}`); }
  }
  const portal = await Deno.readTextFile(join(output, "index.html"));
  assert(portal.includes('id="sec-portal"'), `${profile}: нет самостоятельной страницы курса`);
  for (const member of members) {
    assert(new RegExp(`href=["'][^"']*${member}/`).test(portal), `${profile}: портал не связан с ${member}`);
    const home = await Deno.readTextFile(join(output, member, "index.html"));
    assert(/href="(?:\.\/)?\.\.\/index\.html#sec-portal"/.test(home), `${profile}: нет возврата ${member} к порталу`);
  }
  for (const member of ["lectures", "practice"]) {
    const slides = await Deno.readTextFile(join(output, member, "index.html"));
    assert(slides.includes('class="reveal"'), `${profile}: ${member} не собран в Reveal.js`);
  }
  const expectedArchives = Object.entries(tasks["project-download"].resources)
    .filter(([, resource]) => !(resource as any).profiles || (resource as any).profiles.includes(profile))
    .map(([id]) => `${id}.zip`).sort();
  const archivePaths = paths.filter(path => path.endsWith(".zip"));
  assert(JSON.stringify(archivePaths.map(path => path.split("/").at(-1)).sort()) === JSON.stringify(expectedArchives), `${profile}: состав архивов не соответствует явному реестру`);
  for (const archive of archivePaths) {
    assert(relative(output, archive).startsWith("tasks/"), `${profile}: архив опубликован вне заданий`);
    const names = zipNames(await Deno.readFile(archive));
    assert(names.includes("build.gradle") && names.includes("settings.gradle") && names.some(name => name.endsWith(".java")), `нет самостоятельного Java-проекта: ${archive}`);
    assert(names.every(name => !/(^|\/)(?:reference|tests|build|target|out|\.gradle|\.git)(?:\/|$)/.test(name)), `в стартовом архиве закрытые или сгенерированные файлы: ${archive}`);
  }
  const catalog = JSON.parse(await Deno.readTextFile(join(output, "reference-catalog.json")));
  assert(catalog.schema === "quarto-reference-catalog", `${profile}: неверная схема каталога`);
  assert(JSON.stringify(Object.keys(catalog.targets).sort()) === JSON.stringify(exports), `${profile}: нарушен явный экспорт глав`);
  const html = new Map<string, string>();
  const ids = new Map<string, Set<string>>();
  let searchable = 0;
  for (const path of paths) {
    const published = relative(output, path);
    // Quarto копирует манифесты штатных плагинов вместе с ресурсами Reveal.js.
    const revealManifest = /^(?:lectures|practice)\/index_files\/libs\/revealjs\/plugin\/[a-z0-9-]+\/plugin\.yml$/.test(published);
    assert((revealManifest || !/\.(?:qmd|java|gradle|tsv|csv|ts|lua|cue|ya?ml|md)$/.test(path)) && !published.split("/").some(part => ["_extensions", "_publication", "_generated", ".course-owner", ".project-publish", ".quarto", "projects", "tests", "test", "docs"].includes(part)), `опубликован служебный исходник: ${published}`);
    if (!path.endsWith(".html") && !path.endsWith("search.json")) continue;
    const text = await Deno.readTextFile(path);
    if (path.endsWith("search.json")) searchable++;
    if (profile === "student") {
      for (const id of [...closed, ...privateSolutions]) assert(!text.includes(id), `закрытый контроль или решение ${id} в ${published}`);
      assert(!text.includes("grading-notes"), `заметки оценивания в ${published}`);
      for (const prose of privateProse) assert(!text.includes(prose), `текст закрытого оценивания в ${published}`);
    }
    if (!path.endsWith(".html")) continue;
    assert(published.startsWith("tasks/") || !/\bid="exr-[^"]+"/.test(text), `каноническое задание объявлено вне tasks: ${published}`);
    html.set(path, text);
    ids.set(path, anchors(text));
  }
  assert(searchable > 0, `${profile}: нет поискового индекса`);
  const allIds = new Set([...ids.values()].flatMap(value => [...value]));
  for (const id of openSolutions) assert(allIds.has(id), `${profile}: потеряно открытое пояснение ${id}`);
  if (profile === "full") {
    for (const id of [...closed, ...solutions]) assert(allIds.has(id), `full: потерян исходный контроль или решение ${id}`);
    assert([...html.values()].some(text => text.includes("grading-notes")), "full: потеряны заметки оценивания");
  }
  for (const [path, text] of html) for (const match of text.matchAll(/\b(?:href|src)="([^"]+)"/g)) {
    const raw = match[1].replaceAll("&amp;", "&");
    if (/^(?:[A-Za-z][A-Za-z0-9+.-]*:|\/\/|#)/.test(raw)) continue;
    const [address, fragment] = raw.split("#", 2);
    const url = decodeURIComponent(address.split("?")[0]);
    if (!url) continue;
    let target = url.startsWith("/") ? join(output, url.slice(1)) : resolve(dirname(path), url);
    assert(!relative(output, target).startsWith(".."), `ссылка выходит за публикацию: ${raw}`);
    try { if ((await Deno.stat(target)).isDirectory) target = join(target, "index.html"); await Deno.stat(target); }
    catch { throw new Error(`Неработающая ссылка ${raw}: ${relative(output, path)}`); }
    if (fragment && target.endsWith(".html") && !fragment.startsWith("/") && !fragment.startsWith(":~:text=")) {
      assert(ids.get(target)?.has(decodeURIComponent(fragment)), `нет якоря ${raw}: ${relative(output, path)}`);
      fragments++;
    }
    links++;
  }
}
console.log(`Публикация Java проверена: оба профиля, портал и пять частей, явный экспорт, стартовые архивы, ${closed.size} закрытых заданий, ${solutions.size} решений; ${links} локальных ссылок и ${fragments} якорей.`);
