# Download: упаковка переданных bytes без повторного SHA/size certification

## Допуск и исходная точка

Исполнитель `/root/qrc_perf_companion`, назначение `gpt-6.1-sol/medium`, Standard. Собственный chat `01a1a50e-ccbf-79e1-987c-e5a2fc9f6003`, origin полного интервала `01a1a50e-cceb-7e32-8be1-03b769ca00fb`, root `01a1a4dd-242d-7930-89b5-5da44195e538`. Задание Download получено в turn `01a1a519-96da-71a0-9556-0ba4741c2b06`. Primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-36-12-01a1a50e-ccbf-79e1-987c-e5a2fc9f6003.jsonl`: session_meta строка 1 подтверждает собственный chat/path. До предметной роли и перед передачей прочитаны **все** turn_context от того же origin: строки 8,129,277; в каждой top-level `gpt-6.1-sol/medium` согласован с nested settings. Вход и итог PASS; исходная точка не сдвигалась. Глобальные настройки не менялись, Standard назначен, скрытая маршрутизация не удостоверяется.

Source `/home/tolya/JavaCodex/java-release-20261110/download`, exact HEAD при входе `fcff6ad94c66b61948c44cf8a2c080f8758abfd9`, начальный status чист. Прочитаны `AGENTS.md`, `spec/index.md`, `spec/contract.md`, `docs/public-inventory.md`, `reports/pattern-pass-export-20261104.md` D1/следующий пакет №2, `download-typed-runtime.md` и его review. Model-admission и общее русское языковое правило прочитаны в предыдущей части того же полного интервала. Предыдущая QRC/Performance дельта больше не редактировалась.

## Проверка действительного public caller и владения

Public export остаётся `_extensions/project-download/public.ts:2`: `packageArtifact(value: unknown): Promise<Uint8Array>`. Внешний unknown проходит существующий `core.ArtifactInventorySchema.parse`; module/file/inventory types по-прежнему выводятся из actual public Core, без `any`, casts или скопированного DTO.

Реальные Source callers:

- Download `_extensions/project-download/infrastructure/runtime.ts:489–508` получает готовый результат `materializeCondition/materializeProject`, отдаёт filenames/bytes и замыкание `package: () => packageArtifact(inventory)`. Ему нужны ZIP переданных файлов; он не читает digest-validation result и не использует helper как отдельный SHA certifier. Orchestration/publication fences и owned cleanup не менялись.
- PL `application/native-materialize.ts:252–260` передаёт готовый `final` inventory, получает ZIP и выполняет собственный checkpoint/currentness. Ветка workspace использует собственный workspaceArtifact и этот helper не вызывает. PL Source не менялся.
- Остальные найденные непосредственные calls — existing `tests/public-inventory.ts` и новый адресный offline fixture. Другого известного product caller, которому возвращаемый результат подтверждал caller.sha256, адресный поиск не выявил. Неизвестные внешние consumers не исключены; гарантия digest-consistency намеренно убрана из публичного описания.

До первого await helper уже выполняет `structuredClone(value)` (`package-artifact.ts:11–22`), затем один lazy `sibling("course-core")` (`:23`). Core parse синхронен (`:24–34`). Core `artifacts/contracts.ts:111–181` проверяет schema fields, Uint8Array/размерные пределы, audience/path/collisions; schema не пересчитывает SHA и не вызывает изменяемый async callback. Wrapper objects Zod не дают caller доступ к ранее клонированным bytes. После parse в новом helper до синхронного ZIP writer нет await/callback, предоставляемого caller. Следовательно, второй boundary snapshot **для найденного обычного producer path не нужен**.

SAB — отдельный публично принимаемый случай: `instanceof(Uint8Array)` в schema допускает view с SharedArrayBuffer, хотя inferred output type текущего Zod/TypeScript сужает buffer до ArrayBuffer. Стандартный structuredClone сохраняет shared memory. По уточнению root старая late ownership семантика сохранена: **только SAB-backed bytes** получают `new Uint8Array(file.bytes)` после существующего parse (`:39–42`). Для ordinary bytes второй копии нет. До lazy await переносить SAB copy без нового unknown walker/parse нельзя, поскольку existing Core schema загружается именно этим await; scope не расширен. Shared-memory mutation до поздней копии может попасть в ZIP, как и раньше, зато после копии ZIP writer работает с обычной собственной памятью. Новый validator/refusal не введён.

## Реализация

Изменён ровно один production-файл: `_extensions/project-download/application/package-artifact.ts`.

- Сохранён ранний clone до lazy import, существующий parse/diagnostics и schema-inferred inventory.
- Удалена вторая `new Uint8Array(file.bytes)` копия каждого уже private файла. `files = inventory.files` (`:35–36`) используется только внутри helper; сортируется приватный список, исходный caller список не меняется. Узкая ownership copy оставлена только для SAB (`:39–42`), по прямому уточнению root.
- Удалены digest-await loop, повторный SHA256 и comparison `file.bytes.byteLength !== file.size`. Упаковка больше не удостоверяет self-described size/hash. Формат/тип/предел этих полей по existing Core schema остаётся; actual ZIP size берётся из bytes, а CRC32 остаётся у ZIP writer.
- Existing audience/duplicate/prefix collisions (`:43–64`), target sorting и единственный `zip` call (`:65–69`) сохранены. Domain ZIP/path/CRC/ZIP32 code, Core schema, runtime Download/PL, receipts/pins/cleanup не редактировались.

Новых protocol/schema/receipt/version/hash/framework нет. Public signature сохранена; TypeScript DTO-гарантии не ослаблены. Source доказывает удаление одного per-file SHA прохода, сравнения заявленного размера, второй ordinary bytes allocation и digest awaits. Wall/CPU/RSS выгода **UNKNOWN**, benchmark не назначался.

Current docs: `docs/public-inventory.md` объясняет snapshot/проверяемый scope, значение несогласованных SHA/size и позднюю SAB ownership copy; `spec/contract.md` добавляет тот же bounded contract со ссылкой на public helper. Исторические отчёты и receipts не переписывались. Новый смысл — **package переданных bytes**, не authority/currentness/digest certification.

## Полезные проверки

Existing `tests/public-inventory.ts` адаптирован по назначению: вместо старых size/hash rejection — одинаковый ZIP при изменённых заявленных size/hash и тех же bytes; borrowed mutation включает target вместе с bytes. Остальные audience/path/collision/JSON refusal, README, ZIP extraction/privacy controls сохранены. Этот fixture требует native render, поэтому здесь **не запускался**, его полная integration готовность не заявляется.

Для разрешённого offline исполнения добавлен маленький `tests/package-bytes.ts`: реальная public Core schema создаёт валидный по форме inventory с намеренно несогласованными size/hash. Три первоначальных cases проверяют:

1. ZIP содержит ровно `README.md` с literal bytes `[0,1,128,255]`; `unzip -t` принимает CRC. Выводится сравнение содержимого, а не счётчик hash calls.
2. Изменение caller bytes, target и file-list после вызова не меняет ZIP.
3. Чужая audience, unsafe target, duplicate target и file/directory prefix collision отвергаются.

По уточнению root добавлен четвёртый targeted SAB case: реальные shared bytes принимаются публичным helper, изменения общей памяти до прежней latecopy видны в ZIP, последующая mutation его не изменяет; `unzip -p/-t` проверяет содержимое и CRC. Workers, timing benchmark и новый mutation callback/API не добавлены. Case не считает copies и не проверяет Source text; Source review отдельно удостоверяет условный ownership copy. Он не является доказательством поведения произвольных concurrent worker races.

Fixture не materialize-ит Core project, не читает course source и не запускает render. Публичные modules/schema настоящие, mock provider/facade нет. Он пока отдельный адресный case; новый постоянный CI/harness этой партией не вводился. Для исполнения использована временная композиция `/tmp/download-package-bytes-lydd1ldd`: symlinks actual Download/Core/Core Navigation/PL/QRC под `_extensions`, два test entrypoints и один imports map. Provider Source не копировались и не менялись. Map содержит лишь существующие stdlib/path, stdlib/fs и course-core prefix. Scratch сохраняется для review.

Actual public composition при передаче: Core `46b35909c9878196294f54b6be2d7505b336fe8d`, PL `f3a09cd060609b57e516779588d635abedb1ca21`, QRC `8732dbbe0d8502f8b6a77049e27907ae8a66e46a`, Download указанная база плюс текущий diff. Это не qualification старых hosted pins.

## Фактический запуск, ошибки и бюджет

До production edits новый fixture прошёл настоящий RED: **2 FAIL / 1 PASS**, оба отказа на прежнем SHA/size check. После удаления повторного certification: **3 PASS / 0 FAIL** (20 ms собственное test execution). После разрешённой SAB correction выполнен **только** `--filter "SAB bytes"`: **1 PASS / 0 FAIL / 3 filtered out**, 12 ms; три зелёных cases не повторялись. Повторов неизменённого failing case не было.

Первоначально были две ошибки invocation **до tests**: bundled Deno `test --deny-import` с canonical `jsr:` map, затем с HTTPS JSR map отказал на registry/version manifest permission. Их суммарный elapsed **0.0987 s**; это не product failures. Адресное чтение `test --help` установило `--cached-only`: в отличие от `check`, bundled `test` этот флаг поддерживает. Канонический `jsr:` map восстановлен; корректный offline runtime invocation исполнил RED/GREEN без скачиваний. Не использовался доступ к сети ради исправления кэша.

Фактические команды (scratch `S=/tmp/download-package-bytes-lydd1ldd`, `DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache`):

```sh
/opt/quarto/bin/tools/x86_64/deno test --cached-only --no-config --no-lock --no-check \
  --import-map "$S/imports.json" -A "$S/tests/package-bytes.ts"
deno check --cached-only --no-config --no-lock --import-map "$S/imports.json" \
  "$S/tests/runtime-types.ts" "$S/tests/package-bytes.ts"
```

Runtime behavioral test — bundled Deno **2.7.14**, TypeScript **5.9.2**. Первоначальный affected compiler — системный `/usr/bin/deno` **2.9.7**, TypeScript **6.0.3**, exit 0; он проверил actual helper/runtime через существующий `runtime-types.ts`, четыре NotAny и два существующих отрицательных DTO-контроля, а также новый fixture. После отдельной SAB ветки root разрешил affected check её `ArrayBufferLike` typing. Этот запуск дал TS2322 **в новом тесте**, не в production: присваивание SAB-view в schema-inferred ArrayBuffer-view. Исправлен только fixture: отдельно построенный объект с настоящим SAB-view передаётся в сохранённую public `unknown` boundary; cast/any/ослабление Core DTO не добавлены. Один corrected affected compiler — **PASS**, exit 0 за4.523s. Behavior после этой чисто type-safe перестройки объекта не повторялся. Поэтому финальный compiler evidence включает три invocations (PASS до SAB, test-type FAIL, corrected PASS), а не объявляется единственным успешным первым запуском.

Typecheck native public-inventory fixture целиком отдельно не запускался. Системный checker выбран с `--cached-only`; bundled `check` этот флаг не поддерживает. Cache/stdlib реальные, внешнего скачивания/установки нет.

`deno fmt --check` production helper и первоначального нового fixture — PASS; последующие helper/fixture SAB edits отформатированы адресно `deno fmt`. Адресный `git diff --check` своих tracked Source/test/docs — PASS, в том числе после SAB correction. Учтённый расход RED/GREEN + ошибки invocation + affected compilers + SAB targeted case + fmt/diff — **15.1294 s**. Отдельная диагностика `quarto run /tmp/download-package-env.ts` только напечатала execPath/DENO_DIR (0.8005s), без render/inspect/native course; вместе ниже общего cap45s. У первоначальных test commands cap15s, первого compiler cap25s, SAB case/corrected compilers cap15s, unchanged retry0. Удачные первоначальные tests не повторялись; дополнительный compiler был обоснован новой SAB веткой и исправлением выявленного fixture typing.

Ни renders/inspect/native-course/build/capture, ни новые inventories/full-tree hashes/benchmark/network/install/commit/push/mainstate edits не выполнялись. Student UUID/results/DB и чужие worktrees не менялись. Gate независимого review остаётся у root; полный hosted/native/installed выпуск этой дельтой не заявлен.

Перед передачей перечитан весь primary интервал от прежнего origin; строки 8,129,277 top/nested Sol6.1/medium согласованы. Итоговый допуск PASS.
