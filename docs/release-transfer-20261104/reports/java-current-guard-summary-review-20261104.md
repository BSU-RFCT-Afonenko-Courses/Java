# Java guard summary — независимая saved-only рецензия

**APPROVE scoped partial evidence/report.** Блокирующих расхождений в проверенных counts/pairs/quality и causal пределах нет. Общий результат остаётся **TIMEOUT exit124,114.003086s**, не VALID run, не принятый PL export и не квалификация image/production. Никакая новая попытка этим review не назначается.

## Область чтения

Прочитаны `java-current-guard-summary-20261104.md`, обе report-local driver Source (`.py`, `.ts`), `targeted-preparation.json`, `outer-result.json`, `saved-evidence.json`, `document-intervals.json`, actual mode receipt. Адресно сверены перечисленные12 raw process receipts и12 raw executor summary quality/counter records. Для двух уже указанных документов проверены только относящиеся к таблице begin/end пары из их TS и active Lua JSONL. Из нетрансформированной exact provider copy прочитаны по сохранённым Source ranges только json, assertNativeAssignmentCapture и decode: это подтверждает интерпретацию названных интервалов, без повторного whole Source audit.

Driver использует настоящий full Java archive и exact Core/PL whole-package overlays; Native сценарий вызывает реальный PL export-course entrypoint, book tasks, instance2026-2027-s1. Observer — существующий instrumentModule с strict topLevelFunctions: ровно4 operation declarations +1 decode, карты проверяются до Native. Lua automatic trace=false/modules[], summary mode; это не прежний recursive visitor trace. Whole-course guards/QMD/corridors не вырезаны ради скорости. В Source есть один executeScenario invocation и terminal outer guard; preparation сохранена до него. Marker native-start сам по себе не выдаётся за spawn/status proof.

## Независимо сверенные partial facts

| Объект | Проверка | Результат |
| --- | --- | --- |
| Process receipts |saved rows ↔ raw process_id/parent/status/signal/native wall|12 совпадают:9 completed,3 pending status|
| Executor quality/counters |allowlisted paths ↔ raw summary fields/counter maps|12 совпадают; все data_validtrue/dropped0/incomplete0/errors[]|
| Parent chain index |98a8a7ce →424a3ef2 →db3d10ed|confirmed earlyTS→wrapper→Pandoc; все exit0|
| Parent chain labs/index |cc2f755e →49b336c0 →4cc0dc43|то же; все exit0|
| Final state |saved finalArtifacts/nativeFacts/outer result|no final run/summary/report/tool-status/complete; failed reservation/postedfalse|

Completed envelopes согласованы: index Pandoc7.489221s /wrapper0.629541s /earlyTS0.280555s; labs/index7.712422s /0.622232s /0.285524s. Три pending receipts относятся к outer Quarto/PL entry/128-input capture render; отсутствие final status не превращено в exit0 или duration0. Два completed Pandoc не превращены в завершённые128 документов. Третий log marker без третьего receipt даёт только достигнутый marker, не диагноз дальнейшей фазы.

В каждом earlyTS raw guard span `…-ts-5` имеет begin/end sequences9/32 и statusok; normalize `…-ts-17` —33/42. Для guard и каждого прямого ребёнка совпали experiment/run/process/executor/operation/span/clock identity, begin-before-end sequence, длительность и parent containment. Непосредственные children: readReservation span6, json10, decode11, assertReservationCurrent12. Итого проверены12 TS пар для двух документов; normalize document_id совпадает с соответствующим QMD.

| Документ | Guard ms | Direct json ms | Direct decode ms | Normalize ms |
| --- | ---: | ---: | ---: | ---: |
| index.qmd |46.829274|16.621017|22.917038|66.892523|
| labs/index.qmd |47.608665|15.665690|22.973105|67.123290|

Семантика labels подтверждена Source: `json` включает await readTextFile и JSON.parse; decode вызывает schema.safeParse; прямой json/decode внутри assertNativeAssignmentCapture читает/разбирает state.json. Это не isolated disk/UTF-8/parse timings и не сумма всех json/decode calls процесса. Guard также содержит readReservation/assertReservationCurrent и другие действия; его inclusive время нельзя ещё раз складывать с children.

Для каждого Pandoc сверены3 active Lua resource пары (6 всего), все statusok, тот же document_id, process_cpu clock и совпадающие begin/end identities. index sequences facts1308/1314, uses1309/1313, walk1311/1312; labs/index facts1732/1738, uses1733/1737, walk1735/1736. Parent IDs и timestamp containment подтверждают facts⊃uses⊃walk. Значения2.126479/2.000354/1.984346ms и1.984558/1.846984/1.829087ms совпадают с report. Raw active summary counters facts/uses/walk=1 каждый. На каждом Pandoc имеется также отдельный пустой Lua summary; он не принят за отсутствие active manual phases.

## Почему выводы ограничены правильно

- `summary.data_valid` — качество конкретного доступного executor stream, в том числе partial top-level процесса; это не product RunResult.dataValid и не full coverage. Mode summary подтверждён receipt; coverageCompletefalse/final missing сохранены в выводе.
- Native command envelopes inclusive и вложены. TS semantic monotonic_process и Lua process_cpu имеют разные clock domains; timestamp anchors между процессами не синхронизированы для общей subtraction/critical path. Parent links дают причинную вложенность, не стоимость self/startup/HTML.
- 26.283385s owner inspect — реальная completed команда внутри preparation Native/export пути, не доказанный лишний inspect и не объяснение старых900s.114s остаток после наблюдённого prefix остаётся UNKNOWN.
- Уменьшенный observer дал достигнутые streams без прежнего drop/recursive explosion, но старый broad trace — другой observer/workload; matched speedup из этой пары не выведен. По двум early inputs нельзя экстраполировать128× costs или обещать завершение всего export в120s.
- requests128/early2/late2/native2/project-facts2 и child.usedtrue/postedfalse подтверждают reached phase prefix. Initial serialized counters sourceDirectoryReads7305/hashReads4756/hashBytes11609548 — не final totals. Ledger/sidecars не заменяют owner release completion/privacy corpus/grades.
- Streaming stderr prefix1022B — сохранённые bytes, не доказательство final flush/status recovery. Timeout не удостоверяет состояние оставшихся процессов; этот review liveness не проверял и чужие процессы не трогал.

Существенных завышений completion или causal claims в report не найдено. Допустимый итог — два completed document envelopes сопоставлены с guard/read+decode/normalize и manual Lua resources, общий128 capture не завершён. Pure startup/module loading/Pandoc reader-writer/HTML, полный wall residual, причина старого900s, whole-course privacy/scoring/export validity остаются UNKNOWN/непроверены.

Новых Native/компиляторов/tests/hash/network/liveness probes, Source/state/pins/installed edits или повторной общей extraction/report pipeline не выполнялось. Создан только этот review report; Source/исторические traces не менялись. Own full-origin admission: 32 turn_context, строки8–4175, все top/nested gpt-6.1-sol/high; origin 01a1a25f-b0fb-7fd2-b244-83933af77834, latest 01a1a5a4-9a9c-7823-879f-f18f6a16b931. Globals не менялись, hidden serving tier не удостоверяется.
