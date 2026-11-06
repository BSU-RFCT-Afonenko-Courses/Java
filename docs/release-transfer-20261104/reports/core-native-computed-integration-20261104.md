# Native computed condition → README/ZIP: одна адресная интеграция

Подготовка 2026-11-04. Root после независимого APPROVE разрешил **один** actual запуск, cap120s на staging + Performance preparation + driver + Native children, retry0. Core accepted local commit `5a28314fc8b704ef7171f33063789d7cc8ec8e79`, Download `944ca76dbdef5bb4842116950621f82449b631b3`, Performance `5365bea43d7d036a893f134eb2473adf628cdd6a`. Source этих repos не меняется; нет push/pins/production/course data.

Own primary admission PASS: `/root/core_owner_review`, chat `01a1a502-5273-7260-99ab-937cf055bf6a`, прежний origin `01a1a502-52a2-7aa0-9b60-c9b0739e19cd`. Primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-22-35-01a1a502-5273-7260-99ab-937cf055bf6a.jsonl`. Полностью прочитан interval session_meta1 + turn_context8/193/370/728/798, включая повторную запись последнего turn; top/nested `gpt-6.1-sol/high` согласованы. Origin не сдвигается. Standard назначен, hidden tier не доказан.

## Подготовленная композиция

Использован existing `tests/assignment-admission.ts:67–97` capture/prepared-lease/global-render pattern и `tests/native-project-contracts.ts:65–71,110–123` project/plan/materialization pattern. Не используется их fake Native/Command instrumentation. Driver вызывает только настоящие public APIs. Core и Download лежат рядом в synthetic `_extensions`; lazy Download sibling загружает тот же Core module identity. Один authored `index.qmd`, assessment `work`, exercise `exr-computed`, declared project `projects/one` со starter/reference roles. Единственная R cell вычисляет42, печатает обычную Markdown Table и строит одну PNG; inline после неё использует то же значение. `.solution` и reference file имеют разные private sentinels.

Точный путь: `withNativeCaptureScope` → один `lease.captureInputs(...,[index.qmd],{args:[render,--output-dir,...]})` → `withCurrentNativeOperation` → `validateRelease` → одна `withProjectContracts` batch одного project/format → student/full plans → `assignmentDelivery(...,{projectPlans})` → actual `conditions[exr-computed]` → student `materializeProject` → Download `packageArtifact`. Повторной acquisition на body/README/ZIP нет. Evidence публикуется только после закрытия actual Core operation. Python stdlib ZipFile проверяет CRC каждой entry, точный набор targets и каждый byte относительно Core inventory.

Ожидаемые explicit команды внутри driver:1 global render,1 preparation owner inspect,2 A1 inspect (owner и synthetic project),2 materialization Pandoc; Performance scenario добавляет1 `quarto run` wrapper. Всего **7 planned explicit Quarto commands**; служебные version queries, Native hooks/R/Pandoc — отдельно actual observation, это не обещание полного process count. Performance preparation дополнительно делает1 Pandoc builtin init query, копирует existing backend/data; install отсутствует. Installed knitr directory `/usr/lib/R/library/knitr` наблюдён без R version/package probe.

Performance использует existing `prepareLaunchers/executeScenario`, summary, `coverage.complete:false`, CPU/RSS false. Native-command inclusive spans не складываются; outer staging/setup/report clocks не выдаются за semantic stage timings. Реальные existing Core `process.command` spans и A1/operation counters сохраняются как есть; semantic coverage частична. New framework/receipt/cache policy/eligibility graph не вводится.

До запуска учтён конкретный чужой Sass failure: explicit `XDG_CACHE_HOME=/home/tolya/JavaCodex/.cache` ведёт в writable existing root; Quarto Source36511–36576 и87867–87869 выбирает его либо Native temp/sass до `openKv`80229. Historical/R3 caches не очищаются. `DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache`, `DENO_NO_PACKAGE_JSON=1`, `QUARTO_RUN_NO_NETWORK=true`; последнее не называется sandbox для авторского R.

Первый возможный product failure ожидается в `assignmentDelivery` на Native figure/Raw wrapper/resource qualification либо позднее в `materializeProject` на generated PNG closure. Ранний environment/admission отказ сохраняется именно своим phase. На первом отказе/timeout остановка, никаких повторных render или адаптаций matrix. Не проверяется freeze/data/profile freshness, и отсутствие закрытых sentinels не заявляется taint analysis произвольного R.

Подготовленные файлы: [native driver](core-native-computed-probe.ts), [existing Performance API wrapper](core-native-computed-performance.ts), [synthetic staging и ZIP verification](core-native-computed-integration.py).

## Actual результат: PASS

Единственный запуск 06:16:09–06:16:17 UTC, exit0, **8,536s outer <120s**, timeout false, retry0. Source не редактировался между подготовкой и запуском. Actual Core complete owner witness подтверждает Quarto **1.11.5**. Installed `/usr/lib/R/library/knitr/DESCRIPTION` —1.51; новую runtime version-пробу не запускали. Native `static` bank с knitr принят, computed результаты прошли actual assignment/roles, позднюю materialization и closing fences. Reservation sealed: ровно1 child, used/posted true, input index.qmd. Никакого второго render/capture или compiler после него нет.

- Actual `assignmentDelivery.conditions[exr-computed]`:1 Table,1 Image, inline42; student/publicPackage не содержат ни solution sentinel, ни reference sentinel/путь.
- HTML image URL `index_files/figure-html/computed-1.png`. Это обычная Native PNG15196bytes; signature PNG верна; bytes файла HTML output **равны** actual Core inventory bytes. README содержит и table42, и inline42, и URL `resources/index_files/figure-html/computed-1.png`.
- Student inventory: `Main.txt`15bytes, `README.md`452bytes, `resources/index_files/figure-html/computed-1.png`15196bytes. Reference path/bytes отсутствуют во всех inventory targets/bytes, condition/publicPackage/HTML/README. Это проверка именно этой declared роли и private block, без заявления о taint analysis произвольного авторского R.
- Download actual ZIP:3entries, точный набор targets; Python `ZipFile.testzip()` проверил все CRC, чтение каждой entry побайтно совпало с сохранёнными actual Core inventory files. Не заявляется, что Download сам удостоверяет declared SHA/size: packageArtifact944 упаковывает переданные bytes.

Путь ZIP/README не потребовал дополнительного engine/render или нового producer. Штатный resource corridor сохранил generated PNG из actual Native late body. PL caller/его rawSource SHA contract этим probe не принят; starter не генерировался ячейкой. `metadata-file(s)` A1, concurrent batch mutation, role/profile cache freshness, old frozen outputs и произвольный HTML/widget не проверены и не переименованы в supported.

## Actual стоимость и counts

| Наблюдение | Actual значение | Область |
| --- | --- | --- |
| Staging outer |0,027s| Python FS copies/synthetic fixture, не semantic stage |
| Performance preparation outer |0,064s| existing backend/data copy +1 builtin init query, не spawn-only timing |
| Performance native wall |8,131s| executeScenario wrapper, inclusive |
| Capture+report outer |8,146s| report-only cost отдельно UNKNOWN |
| Полный outer |8,536s| staging, preparation, driver, ZIP checks, wrapper |
| Preparation owner inspect |1| Core operation metrics snapshot |
| A1 inspect/fullWalks |2 /1| one current owner inspect+one project input, один copy для student/full |
| A1 copy bytes/reads |1866964bytes /326files| actual counters, fixture включает Core/Download extensions |
| A1 tree hashes/cache hits |0 /0| не выдаётся cache miss за hit |
| Initial source hash |1866964bytes /326reads| transitional whole-source расход сохранён |
| Materialization Pandoc calls |2| submission Markdown→JSON, condition JSON→GFM |

**Важная граница counters:** `artifact/result.json` сохраняет `batch.metrics`, `op.metrics`, `projectPlanMetrics` сразу после packageArtifact внутри callback. Это не финальные totals после closing fences. Например A1 `fullFences:1` наблюдён до завершающего checkState; Source `project-context.ts:721–723` выполняет ещё closing check после use. Успешный возврат и публикация evidence подтверждают прохождение закрытия, но не превращают ранний snapshot в финальную метрику. Initial op `childRenders:1`, `fullSourceFences:0` и selected/raw path checks сохранены.

Performance summary **VALID**, dropped0, incomplete0, errors[], complete:false. Сохранено **17 process receipts**:10 actual Quarto CLI (render1, inspect3, pandoc2, run4),4 Deno payloads (probe, pre-hook, early-filter, post-hook),3 Pandoc processes. Разница с7 planned explicit Quarto commands —3 штатных hook/early-filter run. Дополнительно1 Pandoc builtin init query preparation находится вне process receipts. Во всех17 receipts exit0/signal null, finite native_command.wall_seconds. R не проходит через этот launcher, поэтому R process census/timing **UNKNOWN**, не «0 engine children».

Actual process-local `launcher-spawn-to-status` inclusive envelopes: globalrender3,964s; preparation inspect0,686s; A1 owner inspect0,653s; A1 project inspect0,632s; condition materialization Quarto Pandoc0,289s/0,302s; вложенные3 raw Pandoc0,783s/0,0037s/0,0170s. Parent/child перекрываются, эти интервалы нельзя складывать. Source setup/spawn overhead/CPU/self и global critical path не вычислялись.

Existing semantic summary содержит49 completed spans: Core process.command2×1,377865s inclusive TS wall; normalizeDocument1×0,011593s; quartoExecutable39 коротких getter calls; references resolve2/assert2. Lua CPU отдельно facts1×0,001972s, uses1×0,000135s, walk1×0,000116s. Это partial semantic attribution: например process.command2 не означает всего2 Native commands. Plain outerwall не подменяет stage Performance; whole-course speedup неизвестен.

## Evidence и передача

- [Exact execution/cap/outcome](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-computed-integration-20261104-execution.json), [outer log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-computed-integration-20261104.log), [stage Source refs](core-native-computed-integration-20261104/stage-metadata.json).
- [Analysis: actual counts/argv/intervals/quality](core-native-computed-integration-20261104/analysis.json), [capture timings](core-native-computed-integration-20261104/capture-timings.json), [Performance report](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-computed-integration-20261104/runs/ade920b1-3657-4273-a63b-b2f47773982b/report.md), [summary](core-native-computed-integration-20261104/runs/ade920b1-3657-4273-a63b-b2f47773982b/summary.json).
- [Actual condition/publicPackage/inventory counters](core-native-computed-integration-20261104/artifact/result.json), [student README](core-native-computed-integration-20261104/artifact/inventory/README.md), [ZIP](core-native-computed-integration-20261104/artifact/artifact.zip), [independent ZIP CRC/bytes result](core-native-computed-integration-20261104/zip-verification.json).

Core worktree остаётся clean на accepted5a28314; no commit/push/pins/mainstate. Подготовительные исправления собственных scripts сделаны до единственного запуска; после PASS менялись только этот отчёт и Source-only analysis сохранённых данных. Новых probes/tests/render/network/install/cache cleanup нет. Цели/баллы/данные/UID/UUID не менялись; temporary Native operation UUID штатно выдан Core только synthetic fixture.

Перед передачей ещё раз прочитан весь собственный primary interval от прежнего origin: turn_context8/193/370/728/798, все top/nested gpt-6.1-sol/high, agent path/chat/origin совпали — PASS. Download extension Source и Performance src/bridge/config/registry tracked paths clean на944/536; Core Source clean на5a28314. Это проверки локальных Source refs/status, не новый execution или tree hash.
