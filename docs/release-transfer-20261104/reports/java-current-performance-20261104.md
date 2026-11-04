# Java128 current trace: terminal timeout и неполное наблюдение

## Итог

**TIMEOUT exit124; попытка terminal, retry0.** Общий elapsed **114.003598 s**, включая archive/overlays, launcher/transform preparation и Native. Общий разрешённый cap120 s не увеличивался. Один full128 Native export invocation, один capture child; composition/delivery units/package corridors не сокращались. Принятый export/полный Performance VALID не получены. Нового запуска или scope-fix после отказа нет.

Trace early executor **INVALID: dropped_events93088**. Это нельзя скрывать за успешно найденными symbol maps или raw paired intervals. Время старого d7 timeout900 s данным prefix не объяснено. Native startup/HTML writer и pending first Pandoc остаются UNKNOWN; final TERM/status/report recovery не исправлено.

## Точные Source и новый stage

Исполнен [согласованный план](java-current-performance-next-20261104.md), прямой GO root. Stage `/home/tolya/JavaCodex/java-release-20261110/reports/java-current-performance-20261104-actual`, создан впервые; historical attempts не перезаписывались.

| Источник | Exact ref и использование |
| --- | --- |
| Java | `a6d28a42d3f0d4f4bbebcd64b5664ebef8341932`, полный local Git archive |
| Core | `4a2903e6d03d1dd489b6922d113100826913cf67`, whole course-core package в root/theory/tasks/lectures/practice/handbook |
| PL | `d8efb030ee6435f3c1f7d242e7388803b55b3299`, whole package в tasks |
| Performance | `e7267f1dd8b186d5b95a213f7a03a9bed71559a8`, actual existing checkout/APIs/launcher backend |

Actual [source-refs.json](java-current-performance-20261104-actual/source-refs.json) и [stage-metadata.json](java-current-performance-20261104-actual/stage-metadata.json) отделяют эти overlays от сохранённого Java installed/pins provenance d7/da2. Другие пакеты и весь authored Source взяты из Javaa6; Site active-audience candidate не применялся. Existing ignored generations/outputs не копировались; tracked full course и package directories сохранены. Изменены только две instrumented TS копии в stage и report-local artifacts. Product Source/state/pins/installed/userdata/UUID/propsGrade/оценки/настоящие outputs не менялись. Commit/push/SSH/network/install/production query отсутствуют.

Report-local adaptations существующих stage drivers: [Python](java-current-performance-20261104.py), [TS](java-current-performance-20261104.ts). Использованы existing `prepareLaunchers`, `instrumentModule`, `hashFile` для штатных transform receipts и `executeScenario`; новых runner/supervisor/public API/framework нет. Python Source syntax прочитана через ast.parse, отдельный compiler/test/probe не запускался.

## Preparation до Native

Все значения сохранены **до** executeScenario:

| Фаза | Measured elapsed |
| --- | ---: |
| Exact archive + whole-package overlays | 2.759990053 s |
| prepareLaunchers/backend/data-dir preparation | 0.047806040 s |
| Targeted transforms/maps/scope persistence | 0.124516666 s |
| TS driver first statement → pre-Native boundary | 0.172322706 s |

Источники: stage-metadata, [launcher-preparation.json](java-current-performance-20261104-actual/launcher-preparation.json), [targeted-preparation.json](java-current-performance-20261104-actual/targeted-preparation.json). Последняя строка включает предыдущие две TS стадии и не прибавляется к ним. Deno startup/module import до первой statement не выделен в отдельный чистый интервал.

Actual maps подтвердили единственные instrumented functions `assertNativeAssignmentCapture`, `json`, `readReservation`, `assertReservationCurrent`, `decode`. В operation.ts сохранены четыре skipped accessor positions72681/72762/91593/92997; contracts.ts skipped пуст. `strict:false`, `coverage.complete=false` заявлены заранее; unsupported getters исполняются как раньше. Lua selected file — native-ts/request.lua. Maps доказывают transform, а не успешное наблюдение/coverage всех функций.

## Фактическая команда и cap

Запуск: `python3 reports/java-current-performance-20261104.py` из release-root. [outer-command.json](java-current-performance-20261104-actual/outer-command.json) хранит exact invocation/env. Он обрамил все stage/preparation/Native действия `timeout --signal=TERM --kill-after=5s` с TERM deadline чуть меньше114 s;1 s общего120 оставлен на outer bookkeeping. [outer-result.json](java-current-performance-20261104-actual/outer-result.json): exit124, elapsed114.003597954 s; до KILL grace лимит не дошёл. Эти5 s — разрешённый максимум, не измеренный расход.

Actual Native scenario — Quarto run штатного PL export-course.ts, root полной disposable Java, instance2026-2027-s1, `--book tasks`, outputs/checks только внутри нового run. Existing registry `reports/sp-native-paired-published-runtime-profiles.json` переиспользован без получения/изменения registrations. Из env использованы известные XDG/Deno caches, offline Quarto, DENO_NO_PACKAGE_JSON и actual launchers, перечисленные в command JSON. `prepareLaunchers` выполнил свою штатную Pandoc builtin-init подготовку; новых capability/version/writability probes не добавляли.

Перед Native было заметное сообщение и сохранён [native-start.json](java-current-performance-20261104-actual/native-start.json). Сам marker означает invocation boundary, не доказательство spawn/status. Реальный Native child подтверждён последующими receipts/log/sidecars. Никаких concurrent Native задач до завершения этого outer attempt не назначали; root и следующий worker уведомлены об окончании.

## Что реально достигнуто

Run `f826a72a-ac50-40ad-bfd8-f930a557d72e`, experiment `685b2512-5016-4a07-8579-e62757537982`, directory `java-current-performance-20261104-actual/runs/f826a72a-ac50-40ad-bfd8-f930a557d72e/`.

**Раздельные command facts; nested envelopes не складываются и не вычитаются:**

| Receipt / Source phase | Actual native_command wall | Состояние |
| --- | ---: | --- |
| `382e0743-85d4-4ff3-8f76-7a3f12e2adcb`, selected `inspect tasks --profile full` | **26.196986745 s** | exit0; owner inspect внутри PL/export preparation, а не report staging |
| `45e1a8be-c35e-4342-94f1-fe3d3dc0bdc5`, Core pre hook | 0.495724967 s | exit0; progress сообщил inputs128, preparation0.2 s |
| `a99b3d7c-8132-4947-8be0-529bfe5b6eaa`, Download pre | 0.061128396 s | exit0 |
| `7dd7da0a-c653-48df-ad77-05c235b451ef`, Quarto run early-filter wrapper | 1.430744289 s | exit0; содержит следующий Deno child |
| `00c49d9a-8308-4e05-9256-f5a988ee6b64`, actual early-filter.ts Deno | **1.075235176 s** | exit0; executor INVALID из-за dropped events |
| `3186f2a4-7406-4f08-8416-266f4cdec465`, первый Pandoc | **UNKNOWN** | final wall/status null; parent — native capture render |
| `eee5efb6-2859-474c-92ab-4a9fe66406e5`, native128 capture render | **UNKNOWN** | pending final receipt |
| PL entry `e5e43825-0acb-45ae-892d-746c83171fb9` и outer Quarto JS `5ef8ee62-bf2e-4a7e-9f44-24ebbb1b817e` | **UNKNOWN** | pending final receipts |

Всего9 launcher receipts:5 completed и4 pending. Эти times — launcher spawn-to-status inclusive envelopes, не pure startup/CPU/exclusive phases. Один completed owner inspect26.197 s не доказывает причину old900 s или необходимость удаления inspect.

E726 сохранил partial logs: stdout0 B, **stderr960 B**. Stderr содержит Core/Download pre progress и **start marker1/128 index.qmd**; marker2 отсутствует. Это наблюдение сохранённых bytes в данном bundled/runtime пути, не исправленная final TERM recovery.

Saved native generation `3c894c95-ca11-4c64-bfff-7928379cecc7`: requests128, early1, project-facts1, late0, native0; reservation **failed**, child inputs128/usedtrue/postedfalse, complete.json отсутствует. Это прохождение early boundary первого документа, не completed/accepted документ. State initial metrics: fullWalks1, memberships7305, rows4756, sourceDirectoryReads7305, sourceHashReads4756/sourceHashBytes11709010, fullSourceFences0/localSourceFences2, inspectCalls1. Это serialized initial state нынешнего4a со staged transform/maps, не final total и не old d7 metrics.

## Наблюдение потеряно в early executor

[Saved evidence extraction](java-current-performance-20261104-actual/saved-evidence.json) относится только к уже сохранённым JSON/JSONL; Native/normalize/report pipeline не повторялся.

Actual early summary: dropped_events**93088**, incomplete_spans0, errors[], **data_valid=false**. Counter recursive `document/contracts.ts:3955:4626` (`boundDocument.visit`) — **101571 calls**. Existing module-level transform обернул его вместе с top-level decode; выбранный module scope оказался существенно шире пяти required functions. Это наблюдение overhead/coverage механизма trace, не причина старого900 s. Default200000 event cap достигнут, нормализация целиком VALID не заявляется.

Несколько raw paired spans до потери сохранились: guard inclusive212.812090 ms, direct guard child read+JSON.parse state17.065159 ms, direct schema decode173.880825 ms. Это **raw surviving pairs внутри INVALID instrumented executor**, с вложенными auto wrappers; они не являются uninstrumented guard cost, полной раскладкой документа или измеренным эффектом4a. Их нельзя суммировать с inclusive Deno envelope или умножать на128. Normalize span сохранил begin, end потерян; его duration UNKNOWN даже при counter calls1 и active incomplete_spans0 в итоговой summary.

Единственная surviving Lua summary имеет counters{} и0 events; request/resource Lua CPU durations отсутствуют. Pending Pandoc initial expectation activatedfalse при наличии Lua started/summary не доказывает неисполнение hooks: final expectation update не записан. Наблюдаемого Lua semantic breakdown здесь нет.

Final `run.json`, `summary.json`, `report.md`, `tool-status.json`, capture-result отсутствуют. Process pending status/PID не заменяли новым status-inspect/liveness probe. Неизвестный остаток между measured scopes не называется startup, HTML writer, guard или суммой прочих фаз. Нового отчёта “успех после TERM” не синтезировали.

## Граница вывода

Попытка отделила быстрые report-stage preparation стадии от **completed native owner inspect26.197 s**, показала actual first early TS envelope1.075 s и дошла до первого pending Pandoc. Она не дала полного per-QMD wall/CPU breakdown из-за timeout и event loss; dominant cost остаётся не установлен. Этот current trace prefix не сравним численно с неинструментированным d7 whole900 s workload как benchmark. Composition128, package corridors, runtime/role/source-currentness/privacy/UUID/grading guards не ослаблялись.

Scope исправлять и вторую попытку запускать запрещено. Следующее отдельно назначенное действие — только Source-only разбор existing symbol selection/tooling gap, без исполнения. Старые R0–R6/общий аудит повторно не исследовались.

## Собственный первичный допуск

`gpt-6.1-sol/high`, Standard; chat `01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1`, path `/root/perf_event_types`, неизменный origin `01a1a514-1688-7662-80fc-58bf7a272ae0`. Primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-41-59-01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1.jsonl`: session_meta собственные ID/path; весь origin interval turn_context8/171/270/405/734/918/1031/1046/1192/1274/1437 — top/nested `gpt-6.1-sol/high`, PASS. Globals не менялись; service_tier отсутствует, скрытая маршрутизация не удостоверяется.

## Source-only после terminal attempt: selection gap

По отдельному поручению root выполнено только адресное чтение Source, ≤10 min, без нового исполнения/benchmark/продуктовых правок. **В current Performancee726 способа выбрать только top-level guard/json/decode нет.**

- `src/config/types.ts:88`: scope имеет только `modules:string[]`, `extensions:string[]`, `strict:boolean`. `config/validate.ts:62–63` проверяет эту форму; function/symbol allowlist не является рабочей настройкой.
- `src/runner/diagnostic.ts:70–96` передаёт каждому выбранному TS file только `{strict,bridgeUrl}` в `instrumentModule`. `scope.extensions` задаёт package observation/request, не TS runtime function filter.
- `src/instrumentation/ts/transform.ts:14–18,65–102`: API принимает source/path/strict/bridgeUrl; walk подходит к каждому FunctionDeclaration/FunctionExpression/ArrowFunction/MethodDeclaration и затем **безусловно** вызывает `ts.forEachChild(node,walk)`. Все supported bodies получают wrappers. `strict:false` снимает отказ по unsupported nodes, а не выбирает top-level functions.
- `symbols` и `.symbols.json` — выходная карта уже выполненного преобразования. Фильтрация карты после `instrumentModule` не отменит insertions и runtime wrappers, поэтому не предотвратит visit101571/event loss. `manuallyWrapped` исключает только уже существующие top-level manual rebind cases; это не произвольный selector. Lua `luaModules` не фильтрует TS instrumentation. У TS agent нет runtime operation allowlist, предотвращающего begin/end всех generated wrappers.

Конкретный следующий **отдельно согласуемый** минимальный tooling diff: existing internal `instrumentModule` получает optional **список имён top-level function declarations** для данного module, например guard/json/readReservation/assertReservationCurrent для operation.ts и decode для contracts.ts. При таком явном выборе выбирать `node.parent === file`, уникальное совпадение имени и supported body; не вставлять wrappers в nested callbacks/visit. Требуемое отсутствующее/неоднозначное/unsupported имя — preparation refusal, не silent success. Existing symbol IDs/source positions/maps сохраняются; default path без selector сохраняет нынешнее поведение. Ненаблюдаемые helpers/getters исполняются, и overall coverage по-прежнему не объявляется полной.

Это один bounded production file `src/instrumentation/ts/transform.ts` плюс existing `tests/instrumentation/ts.test.ts`: meaningful case named top-level decode с рекурсивным visit должен подтвердить wrapper только выбранного declaration, неизменные nested execution/return/error semantics и прежний default behavior; missing/ambiguous selector refusal проверяется до Native. Подключение selector в report-local driver было бы явной адаптацией этого internal API. Новый generic config/CLI selector, scope DSL, registry/receipt/schema/supervisor framework не требуется для ближайшего узкого эксперимента и сюда не предлагается.

**Это реальный internal API capability gap, а не уже доступный обход.** Новый optional selector/его проверки не реализовывались: текущий GO не разрешал product API extension или второй attempt. Увеличение maxEvents, postfilter готовых events/maps, удаление boundDocument validation либо ручное копирование части contracts.ts не решают поставленную задачу честной существующей selection. Наблюдение101571visit/93088dropped характеризует цену и полноту конкретного broad trace, не причину old900 s. Повтор full128/новый benchmark этим отчётом не назначается.
