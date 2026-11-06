# SP shared Site6 stage: первый Native остановлен лимитом

2026-11-04. **LIMIT_STOP, первый actual Native attempt exit124 на115.005s outer; полный PASS/VALID не получен.** Exact stage подготовлен за1.124s. Четыре child renders завершились exit0, пятый handbook был прерван лимитом; root output ещё не создан. До этого собственный избыточный DENO_DIR writable preflight отказал до Native; root отдельно разрешил удалить этот guard и продолжить в новом stage. Native retry не было. Обе фазы и их пределы сохранены ниже.

## Разрешение и подготовленный scope

Root принял [Source-only план](sp-shared-site6-stage-next-20261104.md): exact SP9a + Core0cf/PLf3/QRC873/Download944/Sited16, 15 package overlays, один настоящий root student render через existing Performance356 summary. Total Native outer cap120s включает grace: `timeout115s --kill-after5s`, retry0. Original courses/pins/installed/provenance, caches и state не менять; no installer, inventory/hash status, dependency installation, network или второго PL export.

Созданы две адаптации существующих [Python](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-sp-pl9c-delta.py)/[TS](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-sp-pl9c-delta.ts) drivers: [stage driver](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-shared-site6-stage-20261104.py) и [Performance driver](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-shared-site6-stage-20261104.ts). Они сохраняют existing archive helper и `prepareLaunchers`/`executeScenario`, перечисляют actual five replacement groups и separate preparation timings. Python syntax прочитана через `ast.parse`, product compiler/test не запускался. Self-review относится только к подготовке disposable stage, не к production приёмке.

Назначенные refs: SP `9a2f8120a37d1b42384e2f008b3e0a3a34247008`; Core `0cf20ccc0a4853b704544c7252fcaf3ccb84a518`; PL `f3a09cd060609b57e516779588d635abedb1ca21`; QRC `8732dbbe0d8502f8b6a77049e27907ae8a66e46a`; Download `944ca76dbdef5bb4842116950621f82449b631b3`; Site `d16f2322c1da3c96c2e93d32225f5428ae347756`; Performance `356a76b52d4fa0b20b965a82c1997e34632173fc`. Core берётся exact ref, не уже сдвинувшийся HEAD.

## Фаза1: preparation-only отказ

```sh
python3 reports/sp-shared-site6-stage-20261104.py
```

Cwd `/home/tolya/JavaCodex/java-release-20261110`. Exit1. Driver создал новый отсутствовавший `reports/sp-shared-site6-stage-20261104`, затем один раз разрешил все шесть exact Source refs, подтвердил Performance HEAD356/clean, проверил наличие трёх catalog inputs и cache paths. Чтение version/cache dependencies через Native command не выполнялось.

Exact first cause:

```text
RuntimeError: existing writable cache unavailable: /home/tolya/.cache/quarto/deno_std/cache
```

Новая Python адаптация проверяет оба cache paths через `is_dir()` и `os.access(..., os.W_OK)`. Для XDG это соответствует прежнему Sass KV отказу и corrected environment. Для DENO_DIR writable — более сильное требование, чем обосновано cached-only reuse. Существующий successful Site6 fixture уже использовал тот же DENO_DIR. Этот preflight не различает отсутствие каталога и отсутствие writable доступа, поэтому из его сообщения нельзя выводить ни unavailable cached graph, ни необходимый install, ни неспособность Quarto выполнить render. Это собственная ошибка подготовки; Core/Site/PL/Download/QRC semantic coverage не достигнута.

Записан [preparation-failure.json](sp-shared-site6-stage-20261104/preparation-failure.json): preparation0.012329218s, `nativeAttempted:false`. [Полный stderr](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-shared-site6-stage-20261104/preparation.stderr.log) сохранён из tool result без повторного исполнения. Root немедленно получил точную причину и различие между own preflight и product miss.

### Что не было достигнуто первой подготовкой

Архивирование SP и пяти provider packages, 15 overlays, copied catalogs, completed stage metadata, Performance launcher preparation, `executeScenario`, Quarto root render и child commands не выполнялись. Native budget не расходовался; cap120 не был активирован. Native run ID, process receipts/report/HTML/QRC/download outputs отсутствуют, поэтому их PASS/VALID не утверждается. Root student пять mounted parts, tasks owner validation, текущие links, privacy и actual integration остаются UNKNOWN.

Сохранённые39.798s Native/40.931s outer относятся к прежнему tasks-only [delta](performance-sp-pl9c-delta.md). Текущие0.012329s — только failed preflight, не stage cost и не speedup. CPU/RSS/report-only/full-course timing UNKNOWN.

Root затем прямо разрешил минимальную коррекцию адаптации: удалить writableguard для DENO_DIR **без заменяющих probes/diagnostic checks**, оставить writable XDG и продолжить в новом stage. Это own preparation correction, не product fix. Изменены только этот guard и имя будущего stage; cached graph/permissions/import map/Source refs/dependency policy не менялись. First preparation evidence сохранено.

## Фаза2: exact stage и первый actual Native

Повторный вызов той же report-local Python адаптации после прямого разрешения root создал **новый** [actual stage](sp-shared-site6-stage-20261104-actual/stage-metadata.json), оставив предыдущий каталог отказа. Его refs, exact archive roots и15 destinations перечислены в metadata. SP взят целиком из9a Git archive, provider package roots взяты exact refs таблицы, Presentation/Navigation сохранены из SP9a, BASE installed/provides manifests не регенерировались. Перенесены ровно три прежних student catalogs по старым relative paths; ignored execution scratch/outputs не копировались.

[Preflight](sp-shared-site6-stage-20261104-actual/preflight.json) подтвердил локальные refs/Performance356 clean и необходимые existing files; DENO_DIR writable/readability probe после коррекции не добавлялся. Source ref/preflight чтение при этой продолженной preparation выполнено вновь; это не Native qualification повтор и не hash/inventory scan. Stage preparation **1.123828051s**.

[Exact outer command / selected environment / cap](sp-shared-site6-stage-20261104-actual/outer-command.json): existing bundledDeno+Performance config/lock, `--frozen --node-modules-dir=manual --cached-only`, existing launcher setup, summary `executeScenario`. Сценарий — `/opt/quarto/bin/quarto render . --profile student` из stage/course. Только report-stage read/write и existing cache use; `XDG_CACHE_HOME=/home/tolya/JavaCodex/.cache`, прежний `DENO_DIR`, `DENO_NO_PACKAGE_JSON=1`, `QUARTO_RUN_NO_NETWORK=true`, pinned Quarto. Сеть/installation/cache cleanup не выполнялись.

[Outer result](sp-shared-site6-stage-20261104-actual/outer-result.json):

| Измерение | Результат |
|---|---:|
| Stage preparation | 1.123828051s |
| Native outer **включая Deno/launcher preparation** | 115.005032494s |
| Весь stage + outer | 116.129180713s |
| Outer status | exit124, timeout |
| Cap |115s TERM +5s kill grace, total≤120s |
| Actual Native attempts / retry |1 /0 |

Это **не** чистое `executeScenario.wallSeconds`: драйвер не вернулся к записи capture timings после timeout. Отдельные launcher preparation, Native root wall, report-only, CPU/RSS UNKNOWN. SIGKILL grace не понадобился: timeout завершился на115.005s. Нового запуска после этого не было.

### Достигнутая фаза по сохранённым receipts

Run `6d0026fe-08a9-45ca-8e94-023083fa5056`. Сохранены existing [manifest](sp-shared-site6-stage-20261104-actual/runs/6d0026fe-08a9-45ca-8e94-023083fa5056/manifest.json), mode/coverage/expectations и143 process receipts, partial events/preparation artifacts. Область чтения — только этот direct `processes` directory с cap500 файлов, конкретные known output paths и Site state path; full tree/inventory/hash прохода не было.

[Partial analysis](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-shared-site6-stage-20261104-actual/partial-analysis.json):139 receipts с exit0, один handbook Pandoc с exit143/SIGTERM, три initial pending receipts. Из них actual five child render boundaries:

| Child owner | Receipt result | `native_command.wall_seconds` |
|---|---|---:|
| theory |exit0 |24.786489663 |
| lectures |exit0 |14.111141134 |
| tasks |exit0 |22.096570514 |
| practice |exit0 |15.209881678 |
| handbook |started/pending; descendant Pandoc SIGTERM |UNKNOWN |

Root render и Site pre.ts остаются pending. Все интервалы — inclusive `monotonic_process`, `launcher-spawn-to-status`; не CPU/self/critical path и не суммируемые с parent. Начат fifth child, но его завершение, финальная root post/mount/search/QRC и whole release не достигнуты. Наличие `index.html` в пяти child student output directories означает только частичные generated files: handbook index может предшествовать оставшимся документам.

Root `_site-student/index.html` отсутствует; `_generated/course-site/active.json` отсутствует. Mount/QRC/task download link correctness **не квалифицирована**. Содержимое HTML/ZIP ради full acceptance после timeout не исследовалось. Четыре child exit0 подтверждают завершённые команды и их встроенные guards, но не означают root publication PASS, final PL grading delivery или payload/UUID acceptance.

### Завершение и telemetry предел

Внешние [stdout](sp-shared-site6-stage-20261104-actual/native-driver.stdout.log)/[stderr](sp-shared-site6-stage-20261104-actual/native-driver.stderr.log) пусты. Existing `executeScenario` использует `Deno.Command.output()` и пишет child stdout/stderr после полученного status; interrupted driver не дошёл до этих writes. Поэтому **полного Native stdout/stderr этого timeout нет**, нельзя выдавать partial receipts за full logs. Итоговые run.json/summary/report/dataValid/capture-timings не сформировались: `VALID` не утверждается. Это сохраняет известное ограничение status/flush на TERM, не доказывает его исправление и не назначает новую signal matrix.

У трёх pending receipts `os_pid:null`: launcher записывает initial receipt до spawn, а фактический PID в final write после status. Null не доказывает живой process. Первая попытка `ps` по этим null PID была невалидна и доказательством не является; затем отдельный bounded process-name snapshot не нашёл deno/quarto/pandoc/timeout в доступной process namespace. [Наблюдение](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-shared-site6-stage-20261104-actual/pending-process-status.txt) сохраняет эту границу, без env/full argv. Никаких сигналов чужим процессам/production запросов не было.

Старые39.798s tasks-only PL export не сравниваются как speedup с этим прерванным five-part root render. Новый результат устанавливает, что whole SP route **не уложился в заданный outer cap**, а не product semantic refusal. Следующий запуск/больший budget этим отчётом автоматически не назначается; root independently review потребления/бюджета — отдельный шаг.

### Чистое представление partial command intervals

По отдельному поручению root создана [таблица143 сохранённых receipts](sp-shared-site6-stage-20261104-actual/command-intervals-incomplete.md) через принятый `nativeCommandMarkdown` Performance356. [Небольшой postprocess](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-shared-site6-stage-20261104-actual/command-intervals-incomplete.ts) читает только existing process JSON с cap500 и пишет только новый Markdown. Один bundledDeno cached-only вызов с `--allow-read`/write только этого Markdown завершился exit0 за0.123s; Native/`writeRunArtifacts`/`normalize`/full pipeline не вызывались. Это postprocess вне Native cap, не benchmark или новый run.

Шапка явно TIMEOUT/INCOMPLETE/неPASS, four completed children и handbook/root UNKNOWN, noVALID/CPU/whole closure. Проверены header и три pending rows: timing/exit «неизвестно», не0. Formatter не выводит full argv/cwd/env/личные paths/tokens; UUID process linkage сохранён. Исходные143 receipts и исторический Core capture не изменялись.

Original courses, Source production files, pins, installed manifests, student files/results и state не изменялись; commit/push/network/installer/cache cleanup отсутствуют. Native пользовался разрешёнными persistent caches и писал только новый disposable stage/capture. Созданы две report-local адаптации, этот report, failed-preparation evidence, actual stage/capture и отдельно разрешённое pure Markdown представление с его generator; исторические artifacts не переписывались.

Primary admission: свой chat `01a1a25f-b098-7242-bf6d-d0e8f6517361`, полный origin interval `01a1a25f-b0fb-7fd2-b244-83933af77834`, primary `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-05-42-01a1a25f-b098-7242-bf6d-d0e8f6517361.jsonl`. Прочитаны только model/effort/turn fields всех21 turn_context, lines8–2922; top-level/nested везде `gpt-6.1-sol/high`, latest `01a1a54f-8f3d-78c0-a1c6-ace7712d2363`. Standard назначен; скрытая server routing не удостоверяется, globals/normative docs не менялись.
