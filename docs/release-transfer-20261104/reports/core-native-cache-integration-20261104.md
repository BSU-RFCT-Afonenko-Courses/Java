# Native cache → Core → README/Download: два последовательных captures

Поддерживаемый контроль G1 — [тест Core](https://github.com/Afonenko-Course-Tools/quarto-course/blob/ee461578fe8ba704395cd86eeb3847a0930ea22d/tests/release-generated-resource-currentness.ts) с `--r-cache`:
два настоящих R/knitr captures, ledger1 и цепь public → README/PNG → Download ZIP.
Узкая передача гарантий принята отдельно; результаты и ограничения исторического
опыта ниже сохраняют прежнюю область. Это не приёмка всего Core/Lua/PL/курса/CI.

**PASS scoped.** Единственный разрешённый двухcapture lifecycle завершён exit0 за **15,504 s outer**, без timeout/retry. В одном disposable owner Native knitr cache сохранился между двумя настоящими global renders: внешний счётчик исполнения R-ячейки был **1 после первого и 1 после второго capture**. В обоих случаях поздние Table/Image/inline facts прошли Core, README/resource inventory и Download ZIP. Это подтверждение reuse данного cache при неизменном authored QMD/профиле, не benchmark или общая гарантия свежести.

## Exact состав и разрешённая граница

- Core `4a2903e6d03d1dd489b6922d113100826913cf67`;
- Download `944ca76dbdef5bb4842116950621f82449b631b3`;
- Performance `e7267f1dd8b186d5b95a213f7a03a9bed71559a8`.

Новый stage: [core-native-cache-integration-20261104](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-integration-20261104/stage-metadata.json). Core/Download extensions извлечены `git archive` из exact committed refs; pending Download typing diff не попал в fixture. Performance checkout был clean на exacte726. Переиспользованы существующие computed staging/probe/Performance drivers, с тремя report-local адаптациями: [staging](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-integration.py), [probe](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-probe.ts), [Performance wrapper](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-performance.ts). Product Source/installed курсы/pins/state не менялись.

Один synthetic `index.qmd`, один assessment/work, exercise/exr-computed и project starter/reference. Настройка Native: `execute: {freeze: false, cache: true}`. Единственная R-ячейка увеличивает `execution-ledger.txt`, лежащий **вне owner**, затем создаёт таблицу42 и PNG; inline использует сохранённое `value`. QMD, cache path, output directory и профили между captures не переписывались. Использован тот же Core author capture/full profile и student materialization, что в ранее принятом computed probe; переключения audience/profile между renders нет.

После сообщения perf_event_types и явного GO root о завершении конкурирующего full128 trace выполнен один lifecycle. Общая ограда `timeout --signal=TERM --kill-after=3s 57s` включает staging, launcher preparation, оба captures, поздних consumers и ZIP verification; общий ceiling60 s с grace. Retry0, максимум2 captures; driver выходит при первом failed build/dataValid. Дополнительных Native/inspect/compiler/tests/hash/benchmark/install/network запусков нет. Использованы existing DENO_DIR, writable XDG cache, `--cached-only` и `QUARTO_RUN_NO_NETWORK=true`; это не называется сетевым sandbox для авторского R.

## Что реально прошло

| Наблюдение | Capture 1 | Capture 2 |
| --- | --- | --- |
| Native render / generation | PASS / отдельный operation UUID | PASS / новый operation UUID |
| External execution ledger сразу после render |1|1|
| Late condition |1 Table,1 Image, inline42|1 Table,1 Image, inline42|
| HTML PNG = inventory PNG |PASS,15196bytes|PASS,15196bytes|
| README |таблица42, inline42, URL resource PNG|то же содержимое; штатно новый response anchor UUID|
| Student inventory |Main.txt15B, README452B, PNG15196B|те же target/size|
| Download ZIP |3 entries, CRC каждого PASS, все bytes = inventory|3 entries, CRC каждого PASS, все bytes = inventory|
| Private sentinels |не обнаружены в checked surfaces|не обнаружены в checked surfaces|
| Performance buildExitCode / signal |0 / null|0 / null|
| Performance dataValid |true|true|

Native cache действительно создан в `owner/index_cache/html`: сохранены `__packages` и штатные `computed_…RData/.rdb/.rdx`. Это ограниченное чтение данного известного каталога, не новый recursive inventory/hash. Ledger находится за пределами owner, поэтому его запись не подменяет currentness проверку авторского input и не является причиной собственного Core refusal. Вторая ячейка не увеличила ledger: knitr выполнил cache reuse. R engine как процесс этим не объявляется пропущенным; Native всё равно прошёл второй global render, в том числе обработал inline.

В каждом capture остался реальный маршрут `withNativeCaptureScope` → prepared lease → один `captureInputs` global render → `withCurrentNativeOperation` → validateRelease → одна withProjectContracts batch → student/full plans → assignmentDelivery → student materializeProject → packageArtifact. Выдача artifacts выполняется после успешного closing fence. Проверены condition/publicPackage, student README, inventory targets/bytes и HTML на прежние solution/reference sentinels; HTML PNG URL `index_files/figure-html/computed-1.png` указывает на реальные bytes, равные inventory `resources/index_files/figure-html/computed-1.png`. Проверена PNG signature. Это роль/блок privacy конкретного fixture, не taint analysis произвольного computed текста.

Независимая Python stdlib ZIP проверка после двух captures прочитала каждую entry, проверила все CRC, точный набор targets и каждый byte против сохранённого actual inventory. Она не утверждает, что Download сам удостоверяет declared SHA/size. README между captures закономерно различаются operation-scoped response anchor UUID; ZIP/README byte identity **между разными generations не заявляется**.

## Partial сохранность и timings

До первого Native сохранены stage-metadata и performance-preparation. После каждого render записан `capture-N-render.json` с ledger/operation. После закрытия Core operation сохранены `artifact-N/result.json`, inventory bytes/ZIP и `capture-N-completed.json`. Wrapper записал результат первого Performance run и `capture-timings.json` **до await второго executeScenario**. Значит второй отказ/timeout не затёр бы первый готовый artifact/progress. После успешного lifecycle отдельно сохранены обе ZIP verification записи.

| Clock/counter | Значение | Предел |
| --- | --- | --- |
| Stage preparation outer |0,027 s|FS archive extraction/fixture, не semantic stage span|
| Один Performance launcher setup |0,056 s|existing backend/data copy + builtin Pandoc init query|
| Полный lifecycle outer |15,504 s|оба captures + preparation + packaging/verification|
| Capture1 native wall / capture+report outer |7,921 /7,938 s|inclusive executeScenario wrapper|
| Capture2 native wall / capture+report outer |7,222 /7,230 s|inclusive executeScenario wrapper|
| Global-render receipt intervals |3,754 /3,197 s|process-local launcher-spawn-to-status; не R self time|
| Process receipts |17 /17|по одному render,3 inspect,2 pandoc,4 run CLI +4 Deno payload +3 raw Pandoc|
| Completed semantic spans |49 /49|частичная instrumented coverage|
| A1 inspect/fullWalks/copy |2 /1 /1 каждый|owner+project inspect, одна post-child copy для batch|
| A1 source copy |1885006B /332files каждый|cache/generated members входят post-child owner; не устранённый copy расход|
| Initial whole source hash |1869154B/327reads →1885006B/332reads|transitional whole snapshot **сохранился**|
| Materialization Pandoc calls |2 каждый|прежние Markdown/condition conversions|

Оба summary: droppedEvents0, incompleteSpans0, errors[], **coverageComplete:false**. Во всех34 сохранённых receipts exit_code0/signalnull и finite native_command interval. Подсчёт CLI сделан только для известного exact `/opt/quarto/bin/quarto.js` в сохранённых синтетических argv; full argv/env не публикуются в отчёте. Одна builtin Pandoc init preparation query лежит вне этих receipts. R child census/CPU/RSS/self/global critical path **UNKNOWN**. Inclusive parent/child/semantic intervals перекрываются и не складываются. Событийный delta и executor totals не суммируются как разные работы.

A1/operation/plan metrics взяты внутри callback до окончательных closing fences, поэтому это не final-total certificate; успешное завершение operation подтверждает закрытие, но не меняет область snapshot. Core `probeCacheHits:0`/plan cacheHits0 относится к собственной Core обвязке и **не** опровергает observed knitr cache hit. Разница wall между первым/вторым captures не является matched performance comparison и не превращается в speedup claim. Cache data входит в subsequent initial source walk/copy, что сохраняет расход, названный в source-only [next plan](core-current-cache-next-20261104.md).

## Evidence и остатки

- [Outer command/cap/outcome](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-integration-20261104-execution.json), [outer log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-integration-20261104.log).
- [Stage refs/preparation](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-integration-20261104/stage-metadata.json), [setup](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-integration-20261104/performance-preparation.json), [external ledger](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-integration-20261104/execution-ledger.txt).
- [Capture1 render](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-integration-20261104/capture-1-render.json), [completed](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-integration-20261104/capture-1-completed.json), [actual condition/inventory](core-native-cache-integration-20261104/artifact-1/result.json), [README](core-native-cache-integration-20261104/artifact-1/inventory/README.md).
- [Capture2 render](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-integration-20261104/capture-2-render.json), [completed](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-integration-20261104/capture-2-completed.json), [actual condition/inventory](core-native-cache-integration-20261104/artifact-2/result.json), [README](core-native-cache-integration-20261104/artifact-2/inventory/README.md).
- [Both ZIP CRC/bytes](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-integration-20261104/zip-verification.json), [capture timings](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-integration-20261104/capture-timings.json), [saved-only observations](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-integration-20261104/saved-observations.json).
- [Capture1 summary](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-integration-20261104/runs/38507964-f67d-4cab-b4e2-f378bd379a4d/summary.json), [Capture2 summary](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-cache-integration-20261104/runs/ed5726d6-e4a8-47f7-9a13-bce6d9489354/summary.json).

Результат закрывает только вопрос двух последовательных unchanged-QMD knitr-cache captures на exact данном составе. Project freeze, индивидуальный render, data/include freshness, cross-profile unmarked computed text privacy, Jupyter, widgets/произвольный HTML, metadata-files A1, concurrent cache writes, installed courses и whole-course performance остаются вне этого опыта. R3 не повторён; ни новое cachekey/framework/validator, ни cleanup не добавлены. Исторические captures/реальные caches/student files не тронуты. Никакого commit/push/mainstate изменения.

Own primary full-origin admission:28 turn_context, строки8–3795, от `01a1a25f-b0fb-7fd2-b244-83933af77834` до `01a1a588-69da-7fb2-ad36-79127df43967`; единственные top model/effort `gpt-6.1-sol/high`, вложенные настройки последней записи совпадают. Globals не менялись; hidden serving tier не устанавливался. Report и saved-only postprocessing подготовлены после единственного успешного lifecycle без повторных execution.
