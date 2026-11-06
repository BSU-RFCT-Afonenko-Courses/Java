# Java128: последний Native prefix с Pandoc verbose

## Результат

**Terminal TIMEOUT exit124**, общий elapsed **114.002727167s**, включая stage/preparation; общий cap120s с grace сохранён. Native export invocation1, capture child1, retry0. Это последняя назначенная Native попытка утреннего пакета; последующих запусков, fallback или устранения отказа нет.

Штатная verbose capability **подтвердилась actual records**: первые два документа получили completed `main.lua` и citeproc timings. У третьего документа наблюдается новый Pandoc receipt, `Running filter main.lua` и начатый early TS subprocess; completion main.lua отсутствует. Его Pandoc имеет actual SIGTERM/exit143, но общей final report/status recovery по-прежнему нет. Это scoped diagnostic evidence, не завершённый Java128 export и не объяснение старых900s.

**Actual Pandoc version UNKNOWN**. Она не подставлена из Quarto compatibility constant3.10.0. CPU-интерпретация emitted milliseconds основана на ранее адресно прочитанном [официальном `Filter.hs` tag3.10](https://github.com/jgm/pandoc/blob/3.10/src/Text/Pandoc/Filter.hs): `getCPUTime` вокруг верхнего filter action, integer ms. Сам текущий log сообщает только ms; clock/version probe не выполнялся. Wall envelopes отдельно берутся из actual Performance launcher receipts.

## Exact Source и изоляция

Новый [stage](java-native-filter-summary-20261104-actual/) и новые копии [Python driver](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/java-native-filter-summary-20261104.py), [TS driver](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/java-native-filter-summary-20261104.ts). Старые stages/drivers не редактировались. [Source refs](java-native-filter-summary-20261104-actual/source-refs.json) сохраняет раздельно exact used archive и actual checkout HEAD:

| Source | Использованный archive | Actual checkout HEAD перед archive |
| --- | --- | --- |
| Java |a6d28a42d3f0d4f4bbebcd64b5664ebef8341932 |6aa1cedd4b98b6dbd41cd6318627dc5d3a89990e |
| Core |4a2903e6d03d1dd489b6922d113100826913cf67 |a06ded0b9dea67367e6064452655cbaf6b16a4be |
| PL |d8efb030ee6435f3c1f7d242e7388803b55b3299 |20f9a4f7b481c01f7720f5ff75e50e1c70564a39 |
| Performance |c2dcf002a7396aa3b507e49b99e0ecf00ed9f438 |c2dcf002a7396aa3b507e49b99e0ecf00ed9f438 |

Core4a выбран намеренно для сопоставимого provider composition. Actual Corea06 не выдаётся за использованный Source. Java archive целиком сохраняет128 composition/package corridors; whole Core overlays установлены в шести owners, PL whole package — в tasks. Штатный pre сообщил `inputs=128`. Installed source pins не переименованы в actual provider provenance.

Performance также архивирован exactc2dc в stage; imports/config/lock driver используют этот archive. Existing offline `performance/node_modules` переиспользован через stage-local symlink; prepareLaunchers материализует зависимости в своём backend. Новых install/network/runtime capability inventories нет.

Product Source, original checkouts/state/installed/pins/outputs/userdata/privacy/UUID/propsGrade/баллы не менялись. Собственные native generations/outputs созданы только действительным capture в новом disposable stage. Commit/push отсутствуют.

## Observer и preparation

Содержательное отличие от [предыдущего summary prefix](java-current-guard-summary-20261104.md) — **только `--verbose` в новом prepared Pandoc shell launcher**. [pandoc-verbose-preparation.json](java-native-filter-summary-20261104-actual/pandoc-verbose-preparation.json) сохраняет original/prepared shell bytes. Каждый из трёх actual Pandoc receipts содержит `--verbose`; `--trace` отсутствует в argv. Quarto DEBUG не включался. Environment данной копии driver исключает inherited `QUARTO_TRACE_FILTERS`, `QUARTO_FLUSH_TRACE`, `QUARTO_COMBINED_LUA_PROFILE`, `QUARTO_LOG_LEVEL`; AST snapshots и sampling profiler не назначены.

Summary mode, TS named selector4+1 и existing manual Lua остаются прежними. Actual maps до Native: operation.ts `assertNativeAssignmentCapture/json/readReservation/assertReservationCurrent` ровно4; contracts.ts `decode` ровно1, stricttrue/skipped[]. Scope coveragefalse, luaModules[], luaAutomaticTracefalse. Performance summary init не запускает global debug hook. Не заявляются auto request CPU или полное покрытие Lua/TS.

| Сохранено ДО Native | Elapsed |
| --- | ---: |
| Exact archives/whole-package overlays |2.678369349s |
| prepareLaunchers и verbose launcher |0.056540221s |
| Targeted transforms/maps/scope |0.072951832s |
| TS first statement → Native boundary |0.129492053s |

Последняя строка включает предыдущие две TS стадии, не суммируется с ними. Driver/module startup до первой statement не выделен как чистая startup метрика. Данные находятся в stage-metadata.json, launcher-preparation.json, targeted-preparation.json; native-start.json записан после них, до executeScenario.

Actual command: `python3 reports/java-native-filter-summary-20261104.py` из release-root. [outer-command.json](java-native-filter-summary-20261104-actual/outer-command.json) содержит timeout/cwd/env: TERM deadline чуть меньше114s + grace5s,1s зарезервирована на bookkeeping в общем120s. [outer-result.json](java-native-filter-summary-20261104-actual/outer-result.json) — exit124/114.002727167s; graceKILL не потребовался. Использованы known bundled Deno/Quarto/Pandoc paths, actual map и существующие writable XDG/DENO_DIR, offline environment. Отдельных compiler/help/version/probes не выполнялось. Перед Native опубликовано явное сообщение о последней попытке.

## Actual native filter records и envelopes

Run `5763e000-387f-4561-b45d-77cf062d3454`. Первичный [stderr.log](java-native-filter-summary-20261104-actual/runs/5763e000-387f-4561-b45d-77cf062d3454/stderr.log) сохранён streaming; извлечённые номера строк/markers/records и полные receipts — [saved-evidence.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/java-native-filter-summary-20261104-actual/saved-evidence.json).

| Документ | Pandoc receipt / status | Pandoc wall | `main.lua` emitted ms | citeproc emitted ms |
| --- | --- | ---: | ---: | ---: |
| index.qmd |e7f70a84-48d6-4682-9cfb-c830d3d1a02e / exit0 |7.333130758s |5370 |250 |
| labs/index.qmd |9ca83b92-06f3-4336-b277-c4b8642af858 / exit0 |7.560321904s |5391 |220 |
| labs/numbers.qmd |9ff41d98-a1ee-42c7-8993-cee5c5bb0a1c / exit143 SIGTERM |2.085585818s до status |**UNKNOWN**, Running без Completed |**UNKNOWN**, Running не наблюдается |

Log lines51–82 относятся к первому документу;164–195 — ко второму. Marker3 находится на236, третье `Running filter /opt/quarto/share/filters/main.lua` —277, последняя сохранённая строка. Сопоставление основано на последовательных document markers и одном child render с подтверждёнными parent links; cwd/parent/doc-specific TS normalize spans согласуются с первыми двумя inputs.

`main.lua` — весь Quarto native Lua filter, внутри которого много emulated passes. Эти значения не означают CPU request.lua или конкретного нашего resource handler. Штатный verbose также выдал Loaded/Not rendering INFO messages: stderr35189B вместо1022B предыдущего summary capture; это **дополнительная диагностическая работа**, её стоимость отдельно не измерена. Parser trace/per-node observer не включался; неизменный uninstrumented performance baseline не заявляется.

Нельзя вычесть CPU из wall и назвать разность pure startup/HTML. Нельзя приписать всё время до третьего Pandoc writer: Native inter-document preparation также остаётся неразмеченной.

## TS/manual Lua рядом с coarse timings

Actual direct-child связи: первый Pandoc → wrapper486c7abd → TSaa7e779f; второй → wrapper26b9411f → TS70c0939a. Подробные matched begin/end пары, clocks, sequence и parents сохранены в [semantic-intervals.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/java-native-filter-summary-20261104-actual/semantic-intervals.json), без повторного исполнения/report pipeline.

| Документ | Wrapper wall | Early TS wall | Guard inclusive wall | Direct state json wall | Direct state decode wall | Normalize wall |
| --- | ---: | ---: | ---: | ---: | ---: | ---: |
| index.qmd |0.613637763s |0.276298027s |45.804924ms |16.134072ms |23.206520ms |65.411586ms |
| labs/index.qmd |0.621320125s |0.281631585s |48.202834ms |15.872622ms |22.973990ms |67.058633ms |

Direct state json/decode — пары с parent guard span `…-ts-5`, не сумма всех вызовов. json включает read/UTF-8/JSON.parse; guard также содержит readReservation/assertReservationCurrent. Всё вложено в соответствующий early TS и Pandoc command envelope. Значения не складываются как self time.

| Документ | Lua facts CPU | Nested uses CPU | Nested resource walk CPU |
| --- | ---: | ---: | ---: |
| index.qmd |2.577938ms |2.450665ms |2.434045ms |
| labs/index.qmd |3.109367ms |2.979556ms |2.962343ms |

Это existing manual `process_cpu/pandoc.system.cputime` intervals, по одному facts/uses/walk на достигнутом path. Facts содержит uses, uses содержит walk. Нельзя прибавлять их к верхнему `main.lua` CPU или вычитать из command wall как полное объяснение.

## Достигнутая граница и UNKNOWN

Completed owner inspect tasks/full —25.288216365s; Core pre0.175100947s; Download pre0.060467443s. Это реальные command envelopes, не чистые смысловые затраты каждой операции. Не доказывают ненужность inspect или причину старого900s отказа.

Всего15 receipts:10 имеют final statuses (9 exit0, третий Pandoc143/SIGTERM),5 pending. Третий main.lua дошёл до early-filter wrapper26fcc13d и direct TS5d5956da; у обоих status/summary отсутствуют. Это **граница наблюдения**, не доказательство времени конкретного guard/парсера/загрузчика внутри третьего документа.

13 доступных executor summaries имеют data_validtrue/dropped0/incomplete0/errors[]. Третий Pandoc имеет лишь один пустой Lua executor summary; это не завершённое исполнение всех Lua phases. Недостающие executors и overall run нельзя считать VALID на основании сохранённых зелёных summaries.

Final run.json/summary.json/report.md/tool-status.json и capture-result.json отсутствуют. stdout0B, stderr35189B — usable prefix. Один actual third-Pandoc SIGTERM receipt не означает исправление общего TERM/final-report recovery. Весь128 capture/package closure/доставка не завершены; grading execution/целый курс не квалифицированы.

На вопрос получен ограниченный ответ: **две completed coarse native filter CPU пары сопоставлены с command/TS/manual envelopes; достигнут третий main.lua/early-TS boundary**. Неизвестный остаток остаётся UNKNOWN, без процентов ускорения, причин114/900s и предложения новых Native попыток этого пакета.

## Собственный primary admission

[own-model-admission.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/java-native-filter-summary-20261104-actual/own-model-admission.json) сохраняет own chat `01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1`, immutable origin `01a1a514-1688-7662-80fc-58bf7a272ae0`, expected `gpt-6.1-sol/high`, Standard и primary rollout path. Полный origin interval turn_context lines8/171/270/405/734/918/1031/1046/1192/1274/1437/1655/1749/1809/1997/2098 проверен: top-level model/effort и nested collaboration settings согласованы, PASS. Hidden server routing этим не подтверждается.
