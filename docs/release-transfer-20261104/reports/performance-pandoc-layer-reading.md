# Quarto pandoc → Pandoc: чтение сохранённого SP capture

Только существующий run `a4ac5976-790a-4160-bc40-45794995dea7`, 107 receipts, causal.json и events; без новых course/fixture/compiler/network запусков и без Source edits. Exact пары с обоими process ID, argv, cwd и длительностями: [pandoc-pairs.json](performance-sp-pl9c-delta/pandoc-pairs.json).

## Что установлено

Все **33 из33** Quarto CLI `pandoc` имеют ровно одного непосредственного child Pandoc: `child.parent_process_id === parent.process_id`, link confirmed. Все child — leaves, неизвестных/неоднозначных пар0. Causal processes точно совпадают с107 receipts. Во всех33 парах child argv побайтно равен аргументам parent после `pandoc`; cwd совпадает. Parent каждого Quarto pandoc — export-course TS process `9ee62ec1-5491-4c41-b5c6-143873b5df5c`.

| Конверсия | Pairs | Parent min/median/max, s | Child min/median/max, s | Разность envelope min/median/max, s |
|---|---:|---:|---:|---:|
| Markdown → JSON (`--from markdown --to json --wrap=none`) | 14 | .288/.293/1.069 | .0035/.0052/.0117 | .284/.287/1.058 |
| JSON → GFM (`--from json --to gfm --wrap=none`) | 7 | .299/.310/.313 | .012/.022/.025 | .285/.287/.289 |
| JSON → HTML (`--from=json --to=html5 --mathml`) | 12 | .291/.494/.713 | .006/.202/.418 | .284/.290/.300 |

Разность — **наблюдаемая длительность parent вне envelope парного child**, вычисленная из двух локальных monotonic duration, без сопоставления абсолютных часов. Это не exclusive semantic span, clean startup, CPU или global critical path. Она включает доступную parent execution, ожидания/передачу I/O, Performance child launcher preparation до его native clock, завершение/flush и другую неразличённую работу. Сумма для run не вычислялась. Большой первый Markdown→JSON parent1.069s не имеет доказанной причины.

## Конкретные Source callers в frozen staging

Пути ниже относительно `performance-sp-pl9c-delta/course/tasks/_extensions/Afonenko-Course-Tools/`; анализировались именно сохранённые installed bytes, не текущие owner checkouts.

- **Core d7cc10e** `course-core/artifacts/project.ts:76` private `pandoc(state, from, to, input)` строит эти exact argv и вызывает `assignmentBeforeChild()` → `assignmentChild(quartoExecutable(), args, state.context.owner, {QUARTO_RUN_NO_NETWORK:"true"})`. `parsedFragment:161` читает/кэширует fragment и вызывает Markdown→JSON; materialization около777 переводит submission instructions Markdown→JSON, около813 итоговые blocks JSON→GFM. Это единственный найденный в frozen Core caller данных двух argv shapes;14+7 соответствуют21 receipts. Сохранённый process/event протокол не даёт каждому из14 Markdown вызовов отдельный source-line/fragment label, поэтому дальнейшее распределение между parsedFragment и instructions неизвестно.
- **PL exact9c62960** `course-prairielearn/application/export.ts:212` `renderPublicBody` → около466 `command(["pandoc","--from=json","--to=html5","--mathml"], JSON.stringify(...), ...)`; private `command:70` использует frozen auxiliary `QUARTO`, `before()/after()` fences, piped stdin/stdout/stderr. Root events содержат12 `course-prairielearn.application.export.command` begin — count совпадает с12 соответствующими receipts; finer question→process correlation не записана.
- **QRC798f8c0 /v3.2.0**, без candidate override в staged BASE provenance: `reference-catalog/infrastructure/process.ts:4` `quarto` — output-only wrapper. В frozen QRC нет caller этих трёх argv shapes. Его2 observed semantic calls не следует приписывать33 конверсиям.

Core d7 candidate указан в staged installed-packages candidate metadata, QRC798 — там же; PL BASE entry исторический, actual override9c задан exact archive manifest, как описано в основном отчёте. Никаких новых package hashes не считалось.

## Что можно предложить дальше

В установленном `/opt/quarto/bin/quarto.js:190322` `passThroughPandoc` действительно выбирает `pandocBinaryPath()`, передаёт `args.slice(1)` без additions, env2, inherited streams и выходит с native code. Ветка около190353 вызывается до ordinary CLI parse/session setup, но после `checkReconfiguration()` и `checkVersionRequirement()`. `pandocBinaryPath` около26704 учитывает `QUARTO_PANDOC` через `toolsPath` и иначе выбирает architecture tool; passthrough не добавляет Quarto data-dir. Это согласуется с exact argv/cwd всех33 пар.

**Ограниченный следующий кандидат для PL owner:** рассмотреть только12 JSON→HTML вызовов `renderPublicBody/command`, чтобы запускать тот же квалифицированный Pandoc напрямую, сохранив frozen auxiliary before/after, точные argv/stdin/stdout/stderr/error и cwd. Сначала нужен existing runtime/provider способ получить выбранный executable при explicit override и fallback; hardcoded `/opt/.../pandoc` либо чтение только `QUARTO_PANDOC` не эквивалентны текущему выбору. Quarto entrypoint/env setup и version/reconfiguration guards при обходе тоже требуют явного решения. Поэтому это Source-supported candidate, **ещё не подтверждённая замена поведения**.

Для21 Core conversions простой swap сейчас некорректен: `course-core/runtime/assignment-execution.ts:130–215` требует exact Quarto executable, owner cwd, whitelist Quarto argv и frozen env. Снятие CLI слоя должно сохранить этот authority/qualification contract и chosen Pandoc binding; не обходить guard и не подменять executable из ambient env. Это отдельное конкретное решение владельца Core, а не patch Performance. Performance здесь нужен только для существующих receipts/trace; новый wrapper/профайлер не требуется.

Остаётся неизвестным: сколько разности принадлежит Quarto initialization, Deno runtime/agent, Pandoc-launcher preparation и I/O. Raw native envelopes показывают, где читать дальше, но не выделяют эти составляющие. Root39.747/export TS39.425/render20.078 и semantic PL export6.719 имеют разные/перекрывающиеся границы; их разности не объявляются exclusive. Новых замеров или изменений этим чтением не назначено.
