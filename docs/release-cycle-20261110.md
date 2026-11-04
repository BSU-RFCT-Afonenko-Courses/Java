# Выпуск Java/SP — единственное состояние продолжения

> **ОСТАНОВКА ПО УКАЗАНИЮ ПОЛЬЗОВАТЕЛЯ, 4 ноября.** [Подробный отчёт к15:00](extensions-work-report-20261104.md) выдан раньше срока. Самостоятельные реализации и опыты остановлены до совместного обсуждения и нового явного решения пользователя. `java-sp-10` и `java-sp-3` переведены через app tool в **PAUSED**, ответы инструмента подтверждены. Все исполнители завершили работу; активных SSH/CI операций нет. Старое разрешение автономно работать до10ноября не отменяет остановку. Дальше — обсуждение, не автоматическое возобновление.

Срок: **10 ноября 2026, 09:00 Europe/Minsk**. Обновление 4 ноября после утреннего пакета.
Корень: `/home/tolya/JavaCodex/java-release-20261110`. При возобновлении прочитать этот файл и фактических владельцев/незавершённые операции; не дублировать работу.
История и полное evidence остаются в reports и Git; прежние START, сроки и назначения не возобновлять.

## Права, усилия, окна

- Один релиз Java/SP; разрешены обратимые исправления, ветки, commits/push/PR по принятому scope.
- Перед production публикацией/переключением обязателен **итоговый просмотр пользователя**.
- Root + три независимых исполнителя; один Source writer на контракт, независимый reviewer.
- Sol6.1 / Standard: простое low, координация medium, сложные API/границы/review high; Ultra — сложная архитектура.
- [Матрицу](agent-execution-proposal-20261029.md), [model-admission](model-admission.md), globals не менять.
- Допуск — весь собственный первичный интервал от исходного turn; историю усилий не переименовывать в новый effort.
- Первичные contexts/допуски остаются в именных reports/session evidence, не копируются в state.
- Automation model/effort setter не угадывать; [контроль усилий](../../reports/adaptive-effort-controls.md).
- Гарантированное окно **07:00–22:00**; 22:00–00:00 — при доступности одна короткая сетевая попытка/10 мин.
- Ночью без циклов ожидания/build/inspect/hash; **00:00–07:00 сеть не планировать**.
- Сводки 12:00/20:00: готово/далее/риск/нужен ли ответ; вне окон — существенное изменение/решение.
- `java-sp-10`, `java-sp-3` **PAUSED после отчёта4ноября**; не возобновлять без нового решения пользователя. Дубликаты не создавать, чужие расписания не менять.
- Однократный `java-sp-4-09-45` PAUSED, [сводка выдана](../../reports/progress-20261104-0945.md).
- При завершении/итоговом отчёте к сроку обе основные automation поставить на паузу.
- 5–8 ноября — интеграция; 9 ноября 15:00 — stop новых функций, вечером итоговый review/согласованное переключение.

## Ограничения

- В scope Pages/PrairieLearn/IDE, Moodle/Gateway, student/full/IDE, оба SP-транспорта и согласованные feedback/стартеры.
- Print/Cloud исключены; методика и лекция9 отдельно. Нового общего педпилота нет.
- Сохранять UID/UUID **и родительские numeric Native DB records**, признанные результаты, файлы, выдачу и связи.
- Не мигрировать/пересоздавать DB/history/assignments/outbox под видом смены SHA/path; fake64 не допускается.
- Изменения целей/баллов, риск потери данных, расширение объёма или существенный риск сроку — к пользователю сразу.
- Сохранять checkout/PR/research, исторические captures и failed attempts; не заменять их PASS.
- Java untracked HTML labs/bank/delivery сохранять: **не cleanup, не stage, не git add all**.
- Native Quarto/Pandoc + TS-канон без CUE; hardcoded Pandoc/ambient override и собственный engine не вводить.
- Не повторять unchanged suites/render/inspect/hash/матрицы ради статуса; run только после изменения/конкретного отказа.
- Fixture не benchmark курса; интервалы parent/child inclusive, не CPU и не суммируемый critical path.
- Максимальная own TS типизация активна: настоящие public APIs/DTO, external unknown до existing guards, metadata extras сохранять.
- Any/fake casts/ts-ignore/механическое unknown не покрытие; без новых schema/framework/дублирующих validators ради типов.
- Vendor internals не наш долг; YAML provenance без matching declarations остаётся открытым, не handwritten AST fiction.
- [Актуальная очередь типов](../../reports/typescript-any-audit.md); проценты покрытия не вычислять.

## Ожидающее решение и принятые уточнения

| Решение | До ответа |
| --- | --- |
| Production structural export: config/mount/workID | Auto-review отклонил sensitive export **до исполнения**; JSON не создан. Не повторять/не обходить SSH/config query. [Граница](../../reports/live-image-sources-20261104.md). |
| Site alternate-audience coexistence — **ответ получен** | Пользователь: «Убрать; разделять output-каталоги конфигурацией». Site850 применён clean FF; Java/SP уже имеют12 раздельных student/full profile pairs, config diff0. [Приёмка layout](../../reports/site-audience-output-config-20261104.md). Production до итогового просмотра. |
| GitHub workflow push — **ответ получен** | Пользователь: «сделай через ssh как обычно». PL20f/Downloade0 успешно pushed по явным SSH URLs без смены globals. OAuth ограничение больше не блокирует работу; результаты новых CI ниже. |

## Source IDs и приёмка

| Checkout | Последний известный Source / статус |
| --- | --- |
| Java | Product `f774559ef290cf78d621ab63333cb5677439510d`, 31MD cleanup root APPROVE. State backup `44bb2e9a` pushed SSH; следующий отдельно. Installed Core d7/PL da. |
| SP | Local `9a2f8120a37d1b42384e2f008b3e0a3a34247008` поверх remote3bb: Site6 config APPROVE, **не pushed**. Не render со старым installed Site5.3. Старый PR9 сохранён. |
| Core | Remote `5f4590aa4eb524830715f8020c8ff3c504197f73`, [PR70](https://github.com/Afonenko-Course-Tools/quarto-course/pull/70). Condition a06, late cleanup556 и Navigation wrappers5f Source APPROVE; CI41803137079 **SUCCESS** ([evidence](../../reports/core-ci-5f4590a-20261104.md)). Дополнительная remote backup branch codex/core-native-bridge-20261110 сохранена; фактический PR head codex/java-release-20261110. Original46b+rejected A dirty6paths сохранены. |
| PL | Remote `4d39a6648d2816bab9a372877deded3320ff35e1` через SSH, [PR36](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/pull/36), **CI41806801145 SUCCESS**. Core5f pin, obsolete publication/native-scope DRIFT хвосты удалены. Прежние failures20f/0ebb и причины сохранены; не rerun. |
| Download | Remote `671093945a8a6f76c25e60840aee71964d995496` через SSH, [PR19](https://github.com/Afonenko-Course-Tools/quarto-project-download/pull/19), **CI41806903708 SUCCESS**. Ownership643 APPROVE, count-only wrappers c3 удалены, exact PL4d/Core5f pins. SSH42513 завершён; прежние failed runs сохранены. |
| Performance | Remote c2dcf00 SUCCESS, targeted selector APPROVE; ранее e726 streaming/356 table/108/e88/94f/536 APPROVE. **6 dirty files TERM recovery NOT QUALIFIED**, frozen в release checkout; не stage/push и не использовать этот runner в новых измерениях. [Patch/evidence](../../reports/performance-term-recovery-20261104.md) сохранены; архивировать c2 для принятого инструмента. Overall speedup не доказан. |
| Platform | Remote `9b82e29bffd77cc462ffdef23cd3b96ca041db92`, [PR35](https://github.com/Afonenko-Course-Tools/prairielearn-platform/pull/35), CI41772299873 SUCCESS. Raw payload/hash retained, partial typing APPROVE. Local `6491517`: JDK official preflight independent high APPROVE; пока не pushed, следующую Docker/JDK CI коалесцировать. Новый image/OCI UNKNOWN. |
| Site | Remote `85080c730ad54c8fd81fa4a3c7c806a8771c4f68`, clean после разрешённого FF d16→850 и успешного SSH push. Alternate inspect removal принят пользователем; config/output rename не нужен. Open PR exact head lookup пуст; новый PR/pins/install не делались. Isolated850 и старый site-sp сохранены. |
| QRC | Remote541a448 SUCCESS: direct Native Lua Attr maps, semantic cleanup APPROVE; ранее77abe92 post DTO/f0d6 SearchRow/873 extras+HTML cleanup приняты. Installed798 прежний; Lua cleanup не TS coverage. |

Installed/published composition не приравнивать к local Source; новые image digests/history не переписывать.
[Core CI evidence](../../reports/core-ci-4a2903e-20261104.md), [Download envelope review](../../reports/download-ast-envelope-types-review-20261104.md), [QRC Attr review](../../reports/qrc-lua-attr-native-review-20261104.md).

## Активные владельцы и ближайшее действие

| Владелец | Текущая граница |
| --- | --- |
| Root | Отчёт выдан, PR descriptions актуализированы, Core/PL/Download green. Остановлено до обсуждения; остаётся только документальное сохранение этого состояния отдельным Java commit. |
| High `/root/perf_event_types` | Recovery frozen NOT QUALIFIED, исполнения закончены: runtime8.7468/15s, compiler PASS0.9274/10s; caller TERM control timeout, history runtime NOTRUN. Новых попыток/поручения нет. |
| High `/root/pl_typed_materialization` | Завершён. JDK hash candidate frozen/unaccepted,4files/pure RED→GREEN0.479s; не commit/push/image до обсуждения. Platform material и factual review отчёта готовы. |
| Medium `/root/core_yaml_types` | Завершён. Types material и редакционное чтение готовы, замечания исправлены root. Новых Source поручений нет. |

**Первый следующий шаг:** совместное обсуждение [отчёта](extensions-work-report-20261104.md), без нового implementation. Три новые hosted CI Core/PL/Download SUCCESS ([финальный срез](../../reports/paired-ci-final-success-20261104.json)); не повторять. Первый [publication drift](../../reports/paired-ci-publication-test-align-review-20261104.md) и [второй failure](../../reports/paired-ci-second-failure-20261104.json) объяснены/исправлены; [Source review](../../reports/paired-ci-test-cleanup-review-20261104.md). Удалены count-only Proxy/subclass/any wrappers, replacement checks/count3 нет. Новых Native runs не назначать.
Не запускать новую Java/SP matrix или увеличивать cap. PL computed fixture пока не registered, hosted R/knitr **UNKNOWN**.
Site backup выполнен SSH d16→850 после exact open-head PR lookup `[]`; [план/evidence](../../reports/site-backup-pr-next-20261104.md). PR trigger запускает широкую suite; новый PR открывать с согласованным integration composition, не ради статуса.
[Paired Source plan](../../reports/pl-core-paired-ci-next-20261104.md), [Download pin](../../reports/download-core-paired-ci-pin-20261104.md).
JDK649 принят: official-only второй javac-version process удалён; первая JVM/toolchainreturn и host refusal сохранены.
[JDK evidence](../../reports/jdk-official-preflight-20261104.md)/[independent review](../../reports/jdk-official-preflight-review-20261104.md): первый filesystem fixture ERROR сохранён; changed isolated control PASS0.1933s, total0.435s.
Production runner SHA согласован одним чтением; image digests/history не менялись. [Per-job read map](../../reports/jdk-per-job-read-boundary-20261104.md): lib/modules без byte SHA на каждой оценке; runner/bundle/shared/hash helper остаются per-job, lock минимум дважды parsed. [Следующий кандидат](../../reports/jdk-official-image-hashes-next-20261104.md) снимает только fixed-image byte guarantee после genuine official admission и registryNone; host/direct/custom registry полные, helper и job-local checks вне diff. Не новый proof/cache/attestation; actual deployed overrides/qualification UNKNOWN, до production review.
Platform service typing **STOP**: checker/declaration graph не собран; public PG92584 Source получен root, не installed declaration composition.
[Service gap](../../reports/platform-service-types-20261104.md), [PG Source](../../reports/platform-postgres-source-92584/README.md).
Live binding дальше — только после structural-export consent; Gateway901/Platform845 labels известны, runtime config/activity binding UNKNOWN.
[Actual Gateway901 plan](../../reports/platform-native-binding-actual-next-20261104.md); saved f9 нельзя выдавать за deployed Gateway.

## Принятые измерения и пределы — не повторять ради статуса

| Evidence | Узкий результат / предел |
| --- | --- |
| [SP PL9c delta](../../reports/performance-sp-pl9c-delta.md) | PASS_NATIVE+VALID, Native39.798s/outer40.931s, 7questions/5works/7payloads, UUID stable; coveragefalse, не speedup/CPU/full release. |
| [Site6 small fixture](../../reports/sp-shared-site6-integration-20261104.md) | Scoped PASS после cache-path correction; обе attempts17.151281s<60. Не full SP, originals/installed не менялись. |
| [SP paired stage](../../reports/sp-shared-site6-stage-integration-20261104.md) | LIMIT_STOP116.129s/exit124/cap120/retry0. Root index/mount/downloadlinks не достигнуты; нет PASS/VALID, автоматический retry не назначен. |
| [Core computed](../../reports/core-native-computed-integration-20261104.md) | PASS Table/Image/inline42→README/PNG/ZIP, privacy; outer8.536s, Native8.131s, coveragefalse. Не whole course/assignment/CLI. |
| [PL computed](../../reports/pl-computed-consumer-integration-20261104.md) | Ordinary standaloneA1 PASS, actual PNG/README/ZIP; не hosted/assignment/JVM grading/cache freshness. |
| [Native knitr cache](../../reports/core-native-cache-integration-20261104.md) | Два captures одного disposable QMD/cache/profile, R ledger1; outer15.504s. Не cross-profile privacy/data freshness/Jupyter/speedup. |
| [Java128 original](../../reports/java-native-d7-acceptance.md) | TIMEOUT900.001733s/exit124; start93/persisted92, no seal/complete/public/private. Причина900s UNKNOWN; 128composition/цели не сокращать. |
| [Java current diagnostic](../../reports/java-current-performance-20261104.md) | Total114.004s/exit124/cap120, selected owner inspect26.197s/earlyTS1.075s, первый Pandoc pending. Trace INVALID/dropped93088; не full export/breakdown. |
| [Java targeted summary](../../reports/java-current-guard-summary-20261104.md) / [review](../../reports/java-current-guard-summary-review-20261104.md) | Scoped APPROVE: два Pandoc7.489/7.712s, guard46.8/47.6ms, valid available summaries/dropped0; outer114.003s/exit124. Overall VALID/full export UNKNOWN. |
| [Java verbose prefix](../../reports/java-native-filter-summary-20261104.md) | Последняя утренняя попытка114.002727s/exit124/retry0. Два Pandoc7.333/7.560s; emitted main.lua5370/5391ms, citeproc250/220ms. CPU interpretation условна по official Source, actual Pandoc version UNKNOWN. Третий Pandoc143/SIGTERM, main/earlyTS completion UNKNOWN;13 available summaries valid,5 receipts pending. Не объясняет900s. |
| [State decode](../../reports/java-state-decode-20261104.md) | Process443.053ms; parse median8.257ms/decode11.445ms, один saved input. Не причина900s; повтор не назначен. |
| [Process timing](../../reports/performance-process-timing-review.md) | Completed command wall narrow ACCEPT; pending/старое missing UNKNOWN. Standalone SIGTERM UNKNOWN/FAIL evidence сохранено, SIGKILL не достигнут. |

Canonical33pairs/20spans — observed inclusive envelopes, не clean startup/CPU; [reading](../../reports/performance-pandoc-layer-reading.md).
[Canonical cost](../../reports/pl-canonical-performance-reading.md): serialization NOIO, нет доказанного F×S дубля/cache основания.
Performance streaming сохраняет prefix, не гарантирует TERM final-report; [review](../../reports/performance-streaming-logs-review-20261104.md).
[TERM Source причина/план](../../reports/performance-term-recovery-next-20261104.md): executeScenario не удерживает caller при TERM, missing tool-status/resources затем блокируют финализацию. Pending parent не закрывать status дочернего Pandoc или outer124; новый candidate обязан хранить UNKNOWN/INVALID, не fake outcome.
Frozen candidate **не принят**: [отчёт](../../reports/performance-term-recovery-20261104.md). 4prod+2existingtests, known350ms fixture race, hardtimeout и EXDEV сохранены; final direct caller не завершил finalization, test-watchdog SIGKILL137. Compiler PASS не доказывает recovery. Новых signals/runs этого пакета не назначать, бюджет не сбрасывать новым агентом; опубликованный c2 и prefix streaming остаются принятыми отдельно.
Новых signal/SP paired stage runs и четвёртой утренней Java попытки нет. Штатный verbose дал coarse native filter records без --trace/global Lua hook, но дополнительные35189B INFO имеют неизвестную observer cost. Не вычитать CPU из wall и не называть остаток HTML/startup; два документа не экстраполировать на128. [Независимый saved-only review](../../reports/java-native-filter-summary-review-20261104.md) APPROVE в границе diagnostic prefix; причины900s и полный export остаются открыты. Логи/failed evidence сохранять.
[Dispatch main.lua](../../reports/java-main-filter-dispatch-next-20261104.md) выделил повторный whole-document cleanup Core late-filter.lua для native body и return. High владеет1-file candidate в core-native-bridge, без нового Native; это Source-дубль, не доказанная причина5s/900s.

## Необходимость механизмов, честный остаток и навигация

Native JSON/engine/cache — исходная authority; immutable whole-owner/course SHA и canonical schema являются нашей обвязкой.
Убирать ненужную гарантию, не заменять её новым proof/receipt/cachekey/qualification framework; измерения не обосновывают вечность механизма.
Persisted SHA в PG/Gateway/history отделять от stable Native IDs совместимо, без скрытого DB cutover/per-slot scoring change.
[Source pass summary](../../reports/pattern-pass-summary-20261104.md): карта завершена в указанном Source scope, реализации продолжаются адресно.
[Export](../../reports/pattern-pass-export-20261104.md), [Core/QRC](../../reports/pattern-pass-core-20261104.md), [Platform](../../reports/pattern-pass-platform-20261104.md), [Performance/CI](../../reports/pattern-pass-performance-ci-20261104.md).
R0–R6/74-группы — существующая очередь/evidence, не новый аудит; история остаётся в reports/Git.
[Hash producer](../../reports/hash-mechanism-producer-review.md), [consumer/identity](../../reports/hash-mechanism-consumer-review.md).
[Cache](../../reports/quarto-cache-boundary-20261104.md), [cells](../../reports/quarto-execution-boundary-20261104.md): shared `_freeze` не изолирован output dirs; live leak не установлен.
Реальные caches не чистить; отдельная cache/execution policy профилей ещё не реализована. Output profiles уже разделены, это не cache isolation. Никаких новых kernels/install/render ради статуса.
[YAML remainder](../../reports/core-yaml-node-types-20261104.md): productdiff0/compiler0; нужен exact vendor pin + matching portable declarations.
[Navigation](../../reports/core-navigation-wrappers-review-20261104.md): wrappers приняты, но полный FFI candidate FAIL из-за transitive parse5 JS→d.ts resolution; local AST/doublecast остаются долгом, failed evidence сохранено.
[Download ownership](../../reports/download-ownership-snapshot-20261104.md)/[review](../../reports/download-ownership-snapshot-review-20261104.md): APPROVE/committed643; typed scalar snapshot удаляет внутренний JSON round-trip. Повторная signature/currentness гарантия этим не оправдана и не удалена. [Paired companion](../../reports/download-core5f-pl0ebb-paired-ci-pin-20261104.md) committed893, ждёт последовательной remote PL→Download отправки.
Открыты: live identity/DB/результаты, automatic grader/OCI, оба SP transports E2E, feedback overloads/trusted-reader origin/refusal, full Java export.
Канон/10labs/training/editor overrides и [SP catalogs](../../reports/sp-qrc-reference-readiness.md) сохраняют предметные требования; local export не production.
Print/Cloud, лекция9 и новые образовательные цели не втягивать в техническую оптимизацию. Весь релиз пока **не завершён**, процентов нет.

Bundled Quarto Deno2.7.14/TS5.9.2 check: **не `--cached-only`**; `--deny-import --no-config --no-lock` + existing actual cached map.
Bundled cache: `/home/tolya/.cache/quarto/deno_std/cache`; runtime cached-only и check CLI различаются.
Performance checker known context: system Deno2.9.7 cached-only с config autodiscovery; manual/frozen override prep failures не повторять.
[Исторический checkpoint](../../reports/release-evidence-checkpoint-20261103-2000.md) — evidence, не state; [README](README.md)/[решения](refactoring-plan.md)/[регламент](release-execution-regulation-20261103.md) сохранены.
