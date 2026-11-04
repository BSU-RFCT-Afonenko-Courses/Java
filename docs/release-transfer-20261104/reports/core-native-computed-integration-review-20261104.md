# Независимый review actual computed integration

Вердикт: **APPROVE SCOPED**. Сохранённый единственный actual запуск подтверждает цепочку **knitr cell/inline → Native Table/PNG → Core condition/publicPackage → student README/inventory → Download ZIP** указанной synthetic fixture. Нового семантического blocker перед обновлением Core PR в этой области не найдено. Это отдельный review evidence, не повтор [Source approval](core-native-bridge-review-20261104.md) и не общая приёмка Java/SP/всех engines.

## Scope и реальные входы

Прочитаны обновлённый [writer report](core-native-computed-integration-20261104.md), Source [native probe](core-native-computed-probe.ts), [Performance wrapper](core-native-computed-performance.ts), [staging/ZIP driver](core-native-computed-integration.py), сохранённые execution/timing/result/quality/process evidence и реальные HTML/README/PNG/ZIP. Core `5a28314fc8b704ef7171f33063789d7cc8ec8e79`, Download `944ca76dbdef5bb4842116950621f82449b631b3`, Performance `5365bea43d7d036a893f134eb2473adf628cdd6a` — refs staging driver; известный Core Source уже независимо принят этим reviewer.

Новых Native/Quarto/test/compiler/process probes не запускалось. Выполнено только чтение существующих JSON/text/binary artifacts и data-only декодирование сохранённых ZIP entries/publicPackage PNG для сравнения. Никаких SHA/tree inventory runs, полного env decode/snapshot, DB/state/pins/production query/install/commit/push. Чужой Source не правился.

## Почему это actual integration

- Python driver проверяет HEAD выбранных repos, копирует соседние Core/Download extensions в отдельный synthetic owner и записывает authored QMD/config/project files. Staging использует working extensions с указанными accepted refs; новое полное byte-attestation дерева ради review не строилось.
- Native probe импортирует actual Core/Download public APIs из этих соседних extensions. Нет `Deno.Command` замены, fake Quarto JSON, поддельного condition/inventory или direct ZIP fixture. Lazy Download sibling обращается к тому же Core module identity.
- `withNativeCaptureScope`/prepared lease вызывает один root `render --output-dir …`; затем sealed current operation, validation, одна A1 contracts batch, student/full plans, actual assignmentDelivery condition и student materialization. Evidence files записаны **после** успешного возвращения Core callbacks/closing fences, не до них.
- Сохранённый `causal.json` содержит actual `/opt/quarto/bin/quarto.js` global `render --profile full`, owner inspect, technical A1 input inspect, pre/early/post hooks и реальные Pandoc argv/exit0. Это instrumentation настоящих процессов, не payload double прежних private controls. R сам этим launcher не переписывается и не входит в полный process census.
- Fixture действительно вызывает R `value <- 21L * 2L`, Markdown table, `plot` и inline `r value`; standalone вычисленного payload в input нет. Наличие конечной Table/inline42/PNG согласовано с execution Source и output, а не только с авторским JSON expected.

## Что проверено в сохранённых результатах

Evidence root: `reports/core-native-computed-integration-20261104`; run `ade920b1-3657-4273-a63b-b2f47773982b`.

| Предмет | Фактическое evidence | Предел |
| --- | --- | --- |
| Позднее условие | `artifact/result.json`: ровно1Table и1Image; body содержит computed table result42 и отдельный Para «Вычисленное значение:42» | Один известный exercise/project/format, не весь AST profile |
| Inline отдельно от Table | `artifact/inventory/README.md` реально содержит «Вычисленное значение: 42.»; HTML содержит тот же отдельный текст | Driver `.includes("42")` один не различал бы table/inline; reviewer проверил содержимое отдельно, без rerun |
| HTML→inventory PNG | HTML имеет1PNG URL `index_files/figure-html/computed-1.png`; HTML resource bytes равны `resources/index_files/figure-html/computed-1.png` inventory | Байтовая связь конкретного generated resource, не переносимость widgets |
| PNG→publicPackage | Единственный publicPackage resource, base64 data после декодирования равна тому же inventory PNG; visibility public | JSON-only sentinel check не анализирует скрытые секреты внутри произвольного изображения; здесь проверены именно сохранённые bytes |
| Формат/размер изображения | PNG signature верна;15196bytes, IHDR1344×960 | Это фактический PNG, не placeholder image path |
| README URL | `<img src="resources/index_files/figure-html/computed-1.png" …>` указывает на существующий inventory target | Подтверждено для обычной Native figure этой fixture |
| Inventory/ZIP | Точно3entries: `Main.txt`15bytes, `README.md`452bytes, PNG15196bytes; каждую ZIP entry reviewer прочёл и сравнил с сохранённым inventory file | Archive read валидирует CRC; production ZIP function повторно не вызывалась. Stored CRC: `ca16207d`, `143fd838`, `ebf211ef` соответственно |
| Private absence | `PRIVATE-SOLUTION-SENTINEL`, `PRIVATE-REFERENCE-SENTINEL`, `reference/Secret.txt` отсутствуют в actual condition/publicPackage/HTML/README, inventory targets и декодированных inventory/ZIP bytes; reference file в3entries отсутствует | Положительная fixture с declared reference и private block, не adversarial hardlink/resource injection и не taint analysis авторского R |

Writer ZIP verification использовал stdlib `ZipFile.testzip` плюс exact entry set и byte equality. Reviewer отдельно прочёл те же сохранённые entries; data-only compare подтвердил совпадение без нового Native run. Download944 при этом **не назван verifier SHA/size caller DTO**: он пакует переданные bytes. Валидность computed materialization установлена до packaging, а не новой криптографической гарантией ZIP.

`artifact/result.json.status` оставлен как `…pending-independent-ZIP-byte-CRC-check`: этот файл записан probe до внешнего Python ZIP check. Завершение последней фазы устанавливают отдельный `zip-verification.json:PASS` и overall exit0; pending строка сама по себе не обозначает незавершённый Native run.

## Performance и counts без лишних выводов

`core-native-computed-integration-20261104-execution.json`: один запуск06:16:09–06:16:17UTC, exit0, timedOut false, retry0, cap120s; outer8.535722s. `capture-timings.json`/run outcome: executeScenario wall8.130922s. Это wall всего wrapper/probe, **не только** одного render и не чистое время R. Сохранённый render process envelope3.964474s включает свои descendants.

`summary.json`/`export.json`: dataValid true, droppedEvents0, incompleteSpans0, errors[], coverageComplete false. CPU user/system и peakRSS null по выбранному summary scope. Это корректные частичные данные; не полная семантическая/OS coverage и не «R process count0».

В causal сохранены17process receipts:10Quarto CLI (render1/inspect3/pandoc2/run4),4Deno payloads,3Pandoc. Seven planned explicit Quarto commands отличаются от10actual CLI на3штатных hook/early-filter run. Preparation builtin Pandoc init query вне этого census. Все17observed exit0/signal null. R process timing/полный OS process count неизвестны.

Semantic summary содержит49completed spans, в том числе Core `process.command` **2calls с общим inclusive1.377865s**, normalizeDocument1call0.011593s, quartoExecutable39getter calls, references2+2; Lua process-CPU facts/uses/walk отделены от TS wall. Эти spans не означают всего2Native commands. Parent/child launcher envelopes, TS spans и Lua CPU **не складывались**; exclusive/self CPU, startup overhead и critical path из них не выведены. Writer уведомлён о двусмысленной записи «2×1.377865s»; правильное значение здесь — total по2calls.

### A1 и snapshot counters

Actual A1 metrics snapshot: inspectCalls2 (current owner+один technical project input), fullWalks1, sourceCopyBytes1866964, sourceFileReads326, sourceTreeHashes0, probeCacheHits0. Causal argv независимо согласуются с этими двумя A1 inspect; preparation owner inspect — отдельный третий inspect. Student/full plans, condition, README и ZIP использовали эту одну batch без повторной acquisition.

**Counter timing важен:** probe возвращает `batch.metrics`, `op.metrics`, `projectPlanMetrics` внутри callbacks, сразу после packageArtifact. Поэтому A1 fullFences1 и operation fullFences2 — **ранние snapshots**, не totals после окончательного closing. Source выполняет последующие closing checks; успешное возвращение и post-closing запись evidence подтверждают их прохождение, но не создают нового measured total. Исправленный writer report это уже явно отмечает.

Initial operation sourceHashReads326/sourceHashBytes1866964 и fullWalks1 сохраняются. A1 копирует примерно тот же fixture owner объём, включая extension files; это не доказанное устранение full copy/hash и не прогноз стоимости курса. `fullSourceFences0` означает снятие blanket admission, не «вообще нет raw-source/resource/runtime checks». A1 sourceCopy/treeHash counters и plan PandocCalls2 остаются разными областями.

## Остатки перед PR/release

- PASS относится к actual **Core→student materialization→Download** path; PL native-course/export/native-check raw-source contract этот driver не вызывал. Generated starter/test source не проверялся; starter authored и неизменён.
- Author/full Native render выбран один раз; отдельный student-profile render, profile cache separation, external-data/include refresh, warm/frozen output reuse не проверялись. R3 counterexamples сохраняются, новой cache policy не введено.
- A1 metadata-file(s)/aliases/неподдержанные format origins остаются ограничениями принятого Source. В fixture они отсутствуют. Full A1 copy/check cost и initial whole-owner hash остаются transitional.
- Browser widgets/OJS, произвольный Raw HTML/ресурсы, Python/Julia/другие engines, multi-format actual render, concurrent mutations и whole-course perf не покрыты. Нельзя заменить их PASS этим PNG.
- Private sentinels подтверждают именно отсутствие заданных private block/reference в этой выдаче. Это не новая модель авторского execution sandbox и не защита от intentional secret exfiltration в немаркированный текст/PNG.
- Сравнения со старым Core на том же Native run нет: ускорение/whole-course выигрыш неизвестны. Ранее Source RED на engine admission — отдельное evidence, не comparative performance baseline.

Обнаруженные границы coverage отражены честно; они не превращены в новые mandatory gates. Нового Source fault из этого evidence не выявлено. Для scoped Core PR evidence обновления это достаточное конкретное подтверждение ранее UNKNOWN обычного Native PNG corridor; повторять запуск, compiler/private controls или расширять матрицу reviewer не предлагает.

## Собственный допуск и статус

Reviewer `/root/pl_typed_materialization`, primary chat `01a1a25f-b098-7242-bf6d-d0e8f6517361`; полный interval от origin `01a1a25f-b0fb-7fd2-b244-83933af77834` до current `01a1a534-ec77-76e3-865f-9fb59625ce49`:16turn_context, line8→2470; top/nested везде gpt-6.1-sol/high. Standard назначен; hidden service tier логом не удостоверяется. Globals не менялись, полный env/секреты не печатались.

**APPROVE SCOPED evidence.** Source/DB/state/pins/production не менялись, новых исполняемых проверок/Native children не было. Root сохраняет решение о PR/CI/установке и дальнейшей release acceptance.
