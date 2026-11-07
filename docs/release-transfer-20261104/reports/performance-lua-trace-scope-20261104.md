# Performance Lua trace: цена до module filter

## Вывод

**Да: при активном `start_trace` hook обрабатывает каждый доставленный call/return/tail-call event наблюдаемых Lua threads до module filtering.** Выбор одного `request.lua` ограничивает создание automatic spans, но не dispatch hook, `debug.getinfo`, path classification и frame bookkeeping для остальных Quarto/Pandoc Lua calls. Конкретная частота событий и потраченное CPU/wall в terminal Java attempt не измерены. Неизвестный первый Pandoc не объявляется замедлившимся из-за Lua; причины114 s и прежних900 s не установлены.

Минимальный уже доступный следующий разрез — **явно instrumented TS named functions + общий mode `summary`, Lua manual phases без запуска global debug hook**. Для этого не нужны новый API или product diff. Новая Native попытка этим Source отчётом не назначается.

## Что действительно было включено в сохранённой попытке

Прочитаны только actual terminal stage `/home/tolya/JavaCodex/java-release-20261110/reports/java-current-performance-20261104-actual/`, related current Lua agent/init/launcher Source и адресные existing Lua controls. Product Source/stage/history не менялись; Native/CPU/probes/hash/compiler/tests/matrix/inspect/network не запускались.

- `native-start.json.scenario.mode` и `runs/f826a72a-ac50-40ad-bfd8-f930a557d72e/mode.json.mode` — **trace**. Existing executeScenario выставляет COURSE_PERF_MODE из этого argument поверх diagnostic env. В report-local driver diagnosticEnv содержит actual `prepareLaunchers.env` и `COURSE_PERF_SCOPE_FILE=<stage>/scope.json`.
- Actual `scope.json`: `complete:false`, scopeKind selected-functions-and-semantic, **один** Lua module — абсолютный `<stage>/course/tasks/_extensions/Afonenko-Course-Tools/course-core/native-ts/request.lua`. Это не предположение по названию отчёта.
- Staged `launchers/pandoc-data/init.lua` сначала исполняет сохранённый original init, затем actual `launchers/backend/src/agents/lua/init.lua`. Backend `agent.lua`, `init.lua`, `launchers/tool.ts` при адресном byte comparison совпали с текущими Source. Новые hashes не вычислялись. Terminal backend взят изe726; нынешний accepted Performance `c2dcf002a7396aa3b507e49b99e0ecf00ed9f438` менял TS selector, Lua Source тот же.
- Lua init читает mode из env и при trace читает scope file, вызывает `agent.start_trace(scope.luaModules or {})`; при summary/cpu этот вызов отсутствует (`src/agents/lua/init.lua:1–10`). Одного mode.json недостаточно как runtime hook attestation, но saved init/env-flow показывает реально настроенный путь активации.
- Для pending Pandoc `3186f2a4-7406-4f08-8416-266f4cdec465` сохранены **два** started marker: `lua-table0x7f8d329f84a0` и `lua-table0x7f8d328e03e0`, оба agent=lua/executed=true с этим process_id. Значит, фактическая активация agent была; initial receipt activatedfalse/executedfalse не обновлён и не является отрицательным фактом исполнения.
- Только первый Lua executor сохранил JSONL0 B и summary: counters{}, symbols[], errors[], ABI failures[], dropped0/incomplete0/data_validtrue. Для второго started executor summary/JSONL отсутствуют. Пустая **первая** summary не описывает второй Lua state или весь Pandoc. Нет durable hook-installed/lifetime/count receipt; отсутствие recorded conflict не доказывает непрерывную работу hook до timeout.

Это trace-mode manual/automatic spans, не CPU sampling profile: V8 cpu mode не выбран, Lua clock_provider pandoc.system.cputime в summary обозначает источник часов span. Из пустого JSONL CPU duration не извлекается.

## Точный алгоритм и побочная работа

Source `/performance/src/agents/lua/agent.lua`:

1. `start_trace(:181–198)` проверяет existing debug hooks, нормализует выбранные paths и строит selected_files. Выбор source сравнивается по нормализованному полному имени, не substring.
2. `:232–233` ставит `debug.sethook(hook,'cr')` для текущего thread и уже зарегистрированных живых coroutine threads. `register_thread(:129–142)` ставит тот же active hook новым threads, созданным через proxy factories. Это не OS/global hook всех subprocess: область — Lua threads, на которых он установлен.
3. **Для каждого доставленного event** `:200–207` получает coroutine/main thread, lookup/creation trace_frames[thread], `debug.getinfo(2,'fSn')`; tail call ещё снимает предыдущий frame и переносит tail chain. Здесь ещё нет проверки module source.
4. Для call/tail-call `:209–221` вызывает `selected(info.source)`. `identity(:186–190)` обрабатывает source prefix, relative path/current directory и path normalization; вручную wrapped function исключается после этого. Только выбранный ещё не wrapped function создаёт automatic begin/symbol.
5. Frame `{id=id,fn=info.func,tails=tails}` добавляется **и при id=nil**, то есть для unselected calls тоже. На return `:222–228` `debug.getinfo` уже выполнен; поиск соответствующего function идёт назад по frames и удаляет/закрывает frames/tails. Стоимость поиска зависит от реального stack/unwind pattern; её upper bound/timing на этом run не измерен.
6. `stop_trace`/shutdown отключают hooks; stop_trace также закрывает оставшиеся trace frames. Nonselected frames не увеличивают semantic counters или active span set, поэтому пустые counters/incomplete0 не являются счётчиком всех обработанных hook events.

Риск конкретный: dispatch/debug.getinfo, repeated path normalization, frame allocations, stack lookup/matching/tail bookkeeping масштабируются по общему потоку hook events, а не только по calls request.lua. Масштаб каждого компонента и влияние на allocator/CPU/first Pandoc UNKNOWN. Нельзя приписать весь residual native envelope этим операциям без измерения. Удаление unselected frames вслепую опасно для имеющихся tail-call/error/coroutine relationships; их нельзя объявить ненужным дублем только потому, что id=nil.

Есть ещё два current observer факта:

- Trace `wrap_walk(:112–120)` дополнительно оборачивает function handlers переданного filter; это отдельный per-node источник работы, даже когда selected Lua debug file один. Summary оставляет manual walk phase и не создаёт эти новые per-node wrappers; обычные non-shortcode per-node function handlers тоже пропускаются `wrap(:96–100)`.
- `coroutine.create/wrap` proxies устанавливаются при любом включённом agent mode (`:129–145`), включая summary. Summary не равен нулевой observer cost: остаются manual begin/end/counters/flush и coroutine context propagation. Разница здесь — отсутствие automatic debug hook и per-node trace expansion, без количественного обещания выигрыша.

## Минимальный existing вариант следующего разреза

После отдельного разрешения execution можно адаптировать тот же report-local invocation, используя уже accepted TS `topLevelFunctions` изc2dc. Явный preparer вызывает existing `instrumentModule` для Core operation.ts с guard/json/readReservation/assertReservationCurrent и contracts.ts с decode, сохраняет реальные selected maps/partial coverage. Затем scenario.mode и mode argument `executeScenario` задаются **summary**; Lua scope не заявляет automatic request coverage.

TS injected wrappers используют обычный Metrics API; `installTsAgent` сохраняет их begin/end и в summary. Generic `prepareDiagnostic` в summary сам TS modules не преобразует, поэтому один CLI `--mode summary` без этого явного existing selector preparation не даёт названные guard spans. Нового config/CLI/registry/DSL не требуется.

Lua init при summary не вызывает `start_trace`. Existing manual normalize/resource/walk phases остаются доступны **если они реально достигнуты и flushed**; повторения каждого filter handler/debug call не добавляются. Сохраняются process receipts, inclusive Native envelopes, e726 streaming prefixes, quality flags и pending/final границы. Automatic Lua request read/decode CPU останется ненаблюдаемым, а startup/HTML residual UNKNOWN. Этот вариант не обещает одновременно полную Lua function breakdown и отсутствие глобального hook cost.

**Просто оставить trace и задать `luaModules:[]` сейчас недостаточно:** `start_trace({})` всё равно устанавливает global call/return hook, хотя ни один module не может быть выбран. Для следующего лёгкого разреза уже существующий mode summary решает это без product edits.

## Единственный очевидный one-file Source plan, пока без edit

В current `start_trace` есть гарантированная пустая работа **при пустом effective selected_files**: automatic spans невозможны, но hook/frame machinery всё равно включается. Возможный будущий узкий diff — только `src/agents/lua/agent.lua`: после прежних conflict checks и нормализации scope вернуть штатный успех без `active_hook/debug.sethook`, когда selected_files пуст. Сохранить manual Metrics, чужие hooks, coroutine identities и существующую stop/shutdown семантику. Existing Lua trace/recursion/tail/error/coroutine controls — место последующей адресной проверки; сейчас их Source только просмотрен, исполнения не было.

Это **не исправление конкретного terminal request.lua scope**, который непуст. Размер выигрыша неизвестен. Negative classification cache, удаление unselected stack frames, новые sampling/event filters, увеличение event cap и новое supervisor/runtime API не предлагаются. Для actual непустого trace scope здесь установлен overhead risk, а не доказан безопасный one-line bypass всей bookkeeping.

## Сохранность и собственный допуск

Создан только этот Markdown report. Source/actual terminal artifacts/pins/outputs/учебные данные не менялись; commits/push не делались. R0–R6/полный audit не повторялись. Это адресное Source расследование после новой фактической observation gap; новых runs/benchmarks не назначено.

`gpt-6.1-sol/high`, Standard; chat `01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1`, path `/root/perf_event_types`, прежний origin `01a1a514-1688-7662-80fc-58bf7a272ae0`. Primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-41-59-01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1.jsonl`: session_meta собственные ID/path; весь origin interval turn_context8/171/270/405/734/918/1031/1046/1192/1274/1437/1655/1749 — top/nested `gpt-6.1-sol/high`, PASS. Globals не менялись; явный service_tier отсутствует, hidden routing не удостоверяется.
