# Штатная диагностика времени Native filters: Source-only

## Вывод и предел доказательства

У Pandoc есть узкий штатный механизм `--verbose`: в официальном Source **tag `3.10`** он пишет `Running filter …` и `Completed filter … in N ms` вокруг каждого верхнего Lua/JSON/citeproc filter. Это **CPU time процесса Pandoc**, округлённый вниз до целых миллисекунд, а не elapsed wall и не время дочернего Deno. Global Lua debug hook для этого механизма не требуется.

Но точная версия **фактически установленного Pandoc остаётся UNKNOWN**: existing saved provenance подтверждает Quarto1.11.5 и binary path, а не результат Pandoc version. Текущие drivers намеренно передают runtime `{}` без новых probes. В installed `/opt/quarto/bin/quarto.js:164449` есть требование совместимости `3.10.0`; это основание выбрать конкретный upstream tag для Source-сопоставления, **не доказательство actual binary version**. Следовательно, наличие именно этих сообщений в actual pinned binary пока не подтверждено выполнением. Нового version/help/Native probe не было.

Отдельного штатного дешёвого таймера **каждого внутреннего Quarto Lua pass** без debug hook в рассмотренном installed Source не найдено. Найденный `--verbose` полезен только для coarse верхних filter envelopes. Он не разрешает вопрос всего неизвестного wall остатка.

## Primary Source и точная семантика

- [Pandoc3.10 `Filter.hs`](https://github.com/jgm/pandoc/blob/3.10/src/Text/Pandoc/Filter.hs), `applyFilters/withMessages`: LuaFilter → `engineApplyFilter`, JSONFilter → JSON apply, citeproc → `processCitations`; вокруг action два `System.CPUTime.getCPUTime`. Разность делится `div 1000000000`, то есть picoseconds → integer milliseconds. Сообщения включаются при verbosity INFO. Это inclusive CPU самого Pandoc вокруг action; ожидание дочернего процесса и CPU дочернего Deno не превращаются в его wall duration. При exception завершение не будет записано.
- [Pandoc3.10 `CommandLineOptions.hs:1109`](https://github.com/jgm/pandoc/blob/3.10/src/Text/Pandoc/App/CommandLineOptions.hs#L1109): `--verbose` задаёт INFO. Отдельный `--trace` задаёт reader diagnostic tracing (`optTrace`), а не filter elapsed timer.
- [Pandoc3.10 `Logging.hs`](https://github.com/jgm/pandoc/blob/3.10/src/Text/Pandoc/Logging.hs): `FilterCompleted` форматирует `Completed filter … in … ms`; JSON-представление также имеет milliseconds. Однако JSON `--log` не нужен для следующего prefix-разреза и может зависеть от штатного завершения; достаточно уже streaming stderr.
- Installed [quarto.js](/opt/quarto/bin/quarto.js:129526), `runPandoc`: **Quarto log-level DEBUG одновременно добавляет `--verbose` и `--trace`**. Поэтому DEBUG не является минимальным способом включить этот timer: reader trace создаёт другой поток диагностической работы. CLI `--debug` отдельно означает сохранение промежуточных файлов (`quarto.js:142914`), не timer.
- Installed [quarto.js](/opt/quarto/bin/quarto.js:142918) принимает дополнительные Pandoc args; [fixupPandocArgs](/opt/quarto/bin/quarto.js:20353) не удаляет `--verbose`. Поэтому для обычного прямого render конкретный штатный флаг — `quarto render <input> --verbose` при обычном INFO log-level, без `--trace`. Это Source-подтверждённый путь передачи, не выполненная команда этой партии.

Pandoc верхний filter не равен каждому Quarto pass. Installed [main.lua](/opt/quarto/share/filters/main.lua:1286) выполняет `run_emulated_filter_chain`: много именованных внутренних filters проходят внутри одного native Lua filter. `--verbose` даёт envelope этого native filter целиком, не `v.name` каждого внутреннего pass/handler/AST node. Reader/custom reader, writer и работа вне `applyFilters` этим timer не размечаются; startup, междокументная подготовка Native и ожидания остаются вне такого разреза.

## Почему другие найденные варианты не подходят

| Installed механизм | Что фактически делает | Ограничение для следующего разреза |
| --- | --- | --- |
| `QUARTO_TRACE_FILTERS` / metadata `_quarto.trace-filters` | [main.lua:1444](/opt/quarto/share/filters/main.lua:1444) сохраняет полный JSON AST на старте и после pass; custom-node walk + serialization | **Нет timestamps/durations**. Дополнительные полные walks/serialization и содержимое AST, включая private metadata; не включать ради времени. |
| `lua-profiler-output`, `lua-profiler-interval-ms`; env `QUARTO_COMBINED_LUA_PROFILE` | [main.lua:1370](/opt/quarto/share/filters/main.lua:1370) запускает profiler, помечает `v.name`, затем внешние `quarto --paths`/`quarto run` для конвертации; [quarto.js:129411](/opt/quarto/bin/quarto.js:129411) собирает combined profile | Это sampling profiler, не простой phase timer. |
| Installed [profiler.lua:49](/opt/quarto/share/pandoc/datadir/profiler.lua:49) | `debug.sethook(onDebugHook,"t",ms)`; callback обходит стек через `debug.getinfo` и пишет samples с `os.clock` | Требует специально patched Lua/Pandoc (`"alarm"`, прямо оговорено в файле), совместимость actual binary не проверена. Снова global debug hook/stack observer; не соответствует требуемому лёгкому разрезу. |

## Связь с уже сохранённым Java prefix

[Summary capture](java-current-guard-summary-20261104.md) terminal exit124: owner inspect26.283385161s; два Pandoc завершены за7.489221010/7.712421573s wall; их early TS и manual Lua summaries сохранены. Marker3 `labs/numbers.qmd` есть, **третьего Pandoc receipt нет**. Actual первый Pandoc receipt [db3d10ed…](java-current-guard-summary-20261104-actual/runs/358cfcd7-354e-4b79-bc09-42c2a32622d9/processes/db3d10ed-1003-42d5-8e9c-aec58e255c29.json) содержит defaults/input/metadata/data-dir args, без `--verbose`/`--trace`; содержимое временных defaults теперь не выводится из этого argv. Нельзя задним числом заявить filter CPU timings либо отсутствие других настроек внутри исчезнувшего defaults.

Ни прежний900s отказ, ни весь текущий residual wall не объяснены. В открытой области остаются Native preparation между документами и прочие неразмеченные фазы, не только Pandoc. CPU ms от `--verbose` нельзя вычитать из launcher wall как self elapsed и нельзя складывать с nested manual summaries.

## Один малый следующий план, пока без исполнения

Если root отдельно назначит следующий capture: сохранить exact Javaa6/Core4a/PLd8/Performancec2dc, composition128 и summary/manual Lua режим, а в **новом report-local prepared Pandoc shell launcher** добавить только аргумент `--verbose` к существующему вызову `tool.ts pandoc "$@"`. Так existing tool receipt будет содержать реальный добавленный argv; новый product API/CLI/schema не нужен. Original source, старые stages и current drivers не менять. Quarto log-level оставить INFO; `--trace`, `QUARTO_TRACE_FILTERS`, `QUARTO_COMBINED_LUA_PROFILE` и `lua-profiler-output` не включать.

Один вопрос: какие верхние Native filter actions успевают завершиться и какой **Pandoc process CPU** они сообщают рядом с уже имеющимися command wall/TS/manual summaries? Streaming stderr сохранит завершённые сообщения и последний `Running filter` даже при timeout; это не гарантия последней строки/flush или final report recovery. При отсутствии ожидаемых строк зафиксировать несовместимость/неподтверждённость capability и остановиться без fallback/повторного render. Новый cap/root authorization нужны отдельно; **третья попытка сейчас не назначена и не выполнена**.

Это разрез верхних filters, а не диагностика всех Quarto passes. Для последнего требования остаётся конкретный tooling gap: дешёвых встроенных elapsed begin/end markers `v.name` без sampling/debug hook в прочитанном primary Source нет. Новую instrumentation систему здесь не предлагаю.

## Выполненные действия и собственный допуск

Только чтение saved evidence, адресных installed Source и трёх точных official GitHub files tag3.10; broad web search не использовался. Native/render/inspect/version/help/compiler/benchmark/hash/network-probes —0. GitHub чтение primary Source выполнено штатным read-only connector по разрешённому scope. Product/drivers/stage/state/pins/выдачи/privacy/UUID/оценки не менялись; commit/push отсутствуют.

Own chat `01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1`, immutable origin `01a1a514-1688-7662-80fc-58bf7a272ae0`, роль Source reviewer, expected `gpt-6.1-sol/high`, Standard. Primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-41-59-01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1.jsonl`, session_meta line1; весь origin interval turn_context lines8/171/270/405/734/918/1031/1046/1192/1274/1437/1655/1749/1809/1997 проверен: top-level model/effort и nested collaboration settings согласованы, PASS. Hidden routing не проверяется первичными metadata.
