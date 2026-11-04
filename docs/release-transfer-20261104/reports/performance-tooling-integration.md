# Performance: готовность и ограниченные изменения

3 ноября 2026. Runtime source обновлён в отдельном checkout до exact remote default HEAD `0dc6ca31228da400a48e1278c13853d088b9dec9`. Исходный `/home/tolya/course-tools/quarto-course-performance` на clean `ce19c1209a4647c63d1b853ff711a0617e1138f0` сохранён. Единственный сетевой fetch сделан в новом clone. Runtime не подменялся Arch-рецептом. Архитектурный brief: `briefs/performance-release-integration.md`; собственный допуск: `reports/performance-model-admission.json`.

## Кандидат

`0dc6ca3..cff3edd` в `/home/tolya/JavaCodex/java-release-20261110/performance`, ветка `codex/release-performance-20261110`. Только четыре own файла:

- `src/adapters/quarto/capabilities.ts`: уже полученная внутри одного probe версия Pandoc используется также для Lua capability; уже вычисленный SHA bundle используется для `runtimeHash`. Удалены один повторный native version subprocess и одно повторное чтение/hash `quarto.js`. Выбор effective runtime/env и набор runtime receipts сохранены; межзапускового кэша нет.
- `deno.json`: strict check охватывает все 53 собственных TS-файла в `src/`, `bridge/`, `tools/`, включая bootstrap/tool/resource-child/другие entrypoints, запускаемые по строковому пути. Не добавлены suppress/any, валидаторы или новая проверочная матрица.
- `tests/runner/environment.test.ts`: существующий environment fixture проверяет однократный version probe каждого выбранного Deno/Pandoc и согласованность capability/receipt.
- `docs/development.md`: описан фактический own TS охват.

## Адресная проверка

RED существующего fixture: `probe preserves effective runtime…` завершился assertion `3 !== 2` (169 ms). После исправления полный этот небольшой environment файл: 4 passed/0 failed, 4 s; остальные тестовые файлы не запускались. Сначала system Deno с `--cached-only --node-modules-dir=none` проверил все 53 own TS. Финальная проверка на bundled `/opt/quarto/bin/tools/x86_64/deno` с `--node-modules-dir=none` также exit 0, 53 файла; affected config/TS/test formatting exit 0 (3 файла). Дополнительных повторов graph после успешного финального прохода нет.

Между ними bundled check с `--node-modules-dir=manual` остановился до TS diagnostics: отсутствовал `npm:@types/node` в локальном node_modules. Это среда typecheck, не ошибка TS и не PASS. Для runtime launcher по разрешению root read-only скопированы только два уже существовавших pinned cache дерева TypeScript 5.9.3 и luaparse 0.3.1 в ignored `node_modules` нового checkout; package manager/install/network не запускались. Typecheck использовал существующий global cache, не добавление третьей зависимости в локальный runtime.

## Совместимость actual native цепочки

| Граница | Установленное доказательство | Оставшаяся граница |
| --- | --- | --- |
| Installed Java Core TS/Lua bridge → Performance 2.0 ABI1 | Прямое `cmp` обоих файлов с canonical 0dc: оба 0; ID symbols, wrapSync/Async/Walk и childEnv API совпадают | Динамическое исполнение пока не измерено |
| Public Performance CLI → `quarto run export-course.ts` | CLI parser принимает `render` и `experiment`; простой путь жёстко добавляет `render` | Простой CLI не является wrapper произвольного export; расширять его ради release не требуется |
| Existing `executeScenario` → actual export argv | API сохраняет executable/argv/cwd/env, принимает diagnostic env/preparation и пишет current native/raw/report | Нужен один координированный capture |
| Export Deno → Core assignment Quarto child | `assignment-admission.ts` фиксирует Deno env, удаляет только project/script/document/Core transport; `assignment-execution.ts` запускает exact executable с fixed env/clearEnv, сохраняет QUARTO_DENO/PANDOC и COURSE_PERF keys | Нет нового childEnv вызова в прямом assignment spawn, поэтому command span конкретной стадии может отсутствовать; process ancestry не заменяет stage duration |
| Quarto/Pandoc → native early TS | Installed `native-ts/early-context.lua` вызывает `early-filter.sh`; shell исполняет `${QUARTO} run early-filter.ts`. С унаследованным QUARTO_DENO это существующий launcher/preload | Реальную активацию и уникальные process/executor outputs надо увидеть в capture |
| Lua semantic AST scopes | Installed ABI1 и existing `native-resources.lua` wrappers поддерживаются агентом | Незавёрнутые early guard/normalize/HTML/I/O в summary остаются неизвестны |

## Стоимость самого Performance

Подтверждённые прямые дубли probe устранены. `prepareLaunchers` копирует и сверяет backend (`src`, `bridge`, `node_modules`, config/lock/registry), Pandoc data-dir; это preparation до native measurement, но часть total CLI wall. Внутри diagnostic child есть launcher Deno process, optional data-dir preparation и telemetry writes; это часть instrumented native wall. Их величину нельзя вычитать из одного прежнего off числа. `collectPackageCoverage` обходит selected-project tree и лексически читает TS/Lua пакеты; запускается в стандартном render diagnostic preparation, не в off. Удалять этот inventory по догадке нельзя.

TS/Lua events bounded и flush переписывает накопленный executor buffer; несколько exit/error hooks могут повторить flush, но исключать их без проверки fatal/signal semantics сейчас не предлагается. Merge/normalization и report читают сохранённые события и строят несколько derived views; реальная стоимость не измерена. Дополнительного AST walk для node count нет. Summary не включает per-node handlers; trace/CPU opt-in. `off` — identity bridge без agent clocks/hooks/telemetry, но imports/registration и внешнее supervision имеют ненулевую неизвестную стоимость.

Inclusive/счётчики/declared AST scopes доступны только для реально наблюдавшихся операций; `self_us`/`selfMicroseconds` null. Lua CPU и TS wall раздельны; child time из process relationship не выводится. Это границы готового инструмента, не основание объявлять critical stage нулевым.

## Единственный согласованный SP summary capture

После GO root выполнен один existing-API `executeScenario` summary capture actual SP native export: тот же frozen SP `3bb0e0789545420b9947bdd85fac3d71715d0e36`, installed Core d7/PL da, existing published runtime registry и готовые foreign catalogs, без изменения inputs. Точная команда/UTC/код в `performance-sp-summary-capture-execution.json`, external script `performance-sp-summary-capture.ts`. Warm persistent XDG cache и существующий native scratch, `cleanPaths=[]`, API cachePolicy `repeat-render`; это не новый off baseline. Общий cap 120 s/TERM grace 5 s/retry 0 включал всё preparation/execute/report. Exit 0; timeout не достигнут; повторов нет.

Run `b2b5cee6-0382-4326-83e3-457b113d4562`, durable outputs:

- `performance-sp-summary-capture/runs/b2b5cee6-0382-4326-83e3-457b113d4562/report.md` — существующий отчёт Performance;
- рядом `run.json`, `summary.json`, `coverage.json`, raw `events/`, `processes/`, `wall.trace.json`, `lua-cpu.trace.json`, `causal.json`, новые `public/` и `private/`;
- `performance-sp-summary-capture/capture-timings.json` — host clocks; внешний полный wall в execution JSON.

Внешний wall **44.816 s**, host script interval **44.587 s**; подготовка launchers/backend/data-dir **0.368 s**; native command wall **41.972 s**; host post-command status/normalize/report stage **2.245 s**; pre-command bookkeeping **0.002 s**. CPU user/system и peak RSS **null**, `resourceScope=unavailable`: GNU time в этом запуске не использован, native wall получен direct command clock в existing `executeScenario`, а не `resource-child`. Это исправляет предварительное ожидание в плане/поле `timingScope` raw capture; raw не переписывается.

Host script до уточнения root уже стартовал с прямым forwarding subclass `Deno.Command.output`, сохранявшим только timestamps начала/окончания `super.output()`. Аргументы, env и child не подменялись. После уточнения capture не прерывался и не повторялся. Его собственный overhead **unknown**. Число 2.245 s — объединённая стадия status/normalize/artifacts после native, а не время одного report renderer или exclusive время курса. Полное `report-only` время **unknown**. Прежние 37.882 s используются только как контекст; этот одиночный warm summary run не доказывает процент накладных расходов.

Динамически подтверждена вся actual цепочка: export Deno → Core Quarto `render` Deno → Pandoc/Lua → Quarto `run early-filter.ts` Deno → actual early-filter Deno. Один пример ID ancestry сохранён ниже; все перечисленные native receipts имеют exit 0 и executed agent:

`76aa1a15… (Quarto run)` → `67de6db9… (export-course.ts)` → `727144a7… (Quarto render)` → `76c430e3… (Pandoc)` → `fbcf83e9… (Quarto run early-filter)` → `4589d200… (early-filter.ts)`.

Всего 107 launcher process receipts: 67 Deno activated/executed, 7 Pandoc с Lua activated/executed и 33 Pandoc без Lua activation. Семь actual `early-filter.ts` и семь дополнительных Quarto Deno wrappers — разные процессы; их нельзя называть 14 документами. Сохранены 81 executor event file и 81 540 event rows. Quality: dropped 0, incomplete 0, errors [], dataValid true, coverageComplete false. Наличие valid raw не означает полного покрытия операций курса.

| Наблюдаемая операция | Домен | Завершений | Inclusive, s |
| --- | --- | ---: | ---: |
| PL `exportCourse` | TS wall | 1 | 7.528200 |
| PL `export.command` | TS wall | 12 | 7.234686 |
| PL `exportPrairieLearn` | TS wall | 5 | 5.386845 |
| QRC `infrastructure.process.quarto` | TS wall | 2 | 1.733187 |
| PL `source-selection.canonical` | TS wall | 38 181 | 1.127280 |
| Core `document.normalizeDocument` | TS wall | 7 | 0.189977 |
| Core `native-resources.facts` | Lua process CPU | 7 | 0.031261 |
| Core `native-resources.uses` | Lua process CPU | 14 | 0.027694 |
| Core `native-resources.walk.1` | Lua process CPU | 14 | 0.027407 |

Всего 68 operation aggregates. AST counters: `full-document=39`, `block=24`, `unknown=47`; это declared scopes всех observed пакетов, не число уникальных AST/узлов. Core фактически показывает два `uses`/walk на каждый из 7 `facts`, подтверждая известный same-object кандидат владельцу Core; **его долю в Java 900 s эти SP числа не определяют**. `selfMicroseconds` везде null; inclusive строки вложены и их сумма не равна native wall. TS wall с Lua CPU не складываются. Early state read/decode/assertNativeAssignmentCapture и прочие неразмеченные runtime границы отдельного span пока не имеют.

`coverageComplete=false` при VALID означает честно ограниченный semantic scope, а не потерю событий: direct existing API получил `{complete:false}`, автоматическая полная инвентаризация/selected function trace не запрашивались, и все функции native assignment не размечены. Качество записанного и широта покрытия независимы. Из одной этой сводки нельзя локализовать оставшуюся часть 41.972 s путём вычитания вложенных spans.

Команда и безопасно отобранные functional/Performance env keys, фактические post-capture Git HEAD Performance `cff3edd8fd479dcd861a1b36e919d67e919f27eb` и SP `3bb0e0789545420b9947bdd85fac3d71715d0e36`, native HTML paths и composition сохранены в `performance-sp-summary-capture/observations.json` из уже готовых артефактов, без hashwalk/inspect/rebuild. Current Core generation `system-practice/tasks/_generated/course-spec/native-ts/000f3ce9-2a1d-4aeb-8ef9-94572df36d13/complete.json`: 7 документов, 7 HTML outputs; reservation содержит 1 child. Output owner `tasks/_generated/prairielearn-native-render`; HTML: index, labs/work-environment, labs/data-processing, labs/version-control, optional/project-maintenance и две delivery/units страницы. Новая public delivery schema 4 содержит 7 questions, 7 grading payloads, 5 works, 1 instance, 105 file rows; старые public/private результаты не удалялись.

Для оптимизации самого инструмента теперь есть фактическая стоимость preparation 0.368 s и post-native processing 2.245 s данного SP scope. Высокочастотная ручная PL `canonical` дала 38 181 вызов и минимум 76 362 begin/end rows из 81 540; отсюда нельзя заключить стоимость самой функции без instrumentation. Buffer cap по умолчанию 200 000 событий на TS executor остаётся реальной границей будущего более крупного scope. Увеличение лимита, отключение разметки и изменение merge/cache без адресного доказательства не выполнялись.

Следующий bounded приоритет — отдать владельцу PL существующие counts `canonical` и перечень call sites для проверки прямого повторения; владельцу Core — observed 7 facts/14 uses/14 walks и известный same-object diff. Для Performance собственный следующий кандидат — review повторной валидации тех же event arrays при normalization → summary/traces на уже сохранённых 81 540 rows; без нового capture и без cache/freshness изменения. Реализация сейчас не выполнена. ResourceScope Source fix **не установлен**: проверен только известный `/usr/bin/time`, файла нет, и текущий контракт корректно оставляет CPU/RSS null. Устанавливать GNU time или повторять capture ради этих полей не требуется.

Независимый review `cff3edd` **APPROVE**, свидетельство root `reports/performance-capabilities-review.md`; новых проверок reviewer не запускал. В release clone origin исправлен с local source path на `https://github.com/Afonenko-Course-Tools/quarto-course-performance.git`; source original remote/refs/config не менялись. Push/PR пока отсутствуют. CI workflow запускается на pull_request, push main и workflow_dispatch: полный test suite, Quarto/Deno/Hyperfine setup, загрузка pinned npm архивов и Perfetto с импортом trace fixtures. Открытие PR запустит существенную матрицу; PR ради status не создавался. Дополнительных captures/матриц, full Java, install/build, production/DB/publish и global config не было.
