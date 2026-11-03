# Выпуск Java/SP — единственное состояние продолжения

Срок: **10 ноября 2026, 09:00 Europe/Minsk**. Обновление 3 ноября, после Java timeout и допуска performance scope. При входе сверить реальные HEAD и активных исполнителей, затем продолжать следующий шаг ниже. [Исторический checkpoint](../../reports/release-evidence-checkpoint-20261103-2000.md) — только evidence, никогда не state; прежние сроки/START из истории не возобновлять. Измерения и ограничения — в reports, история — в Git.

## Права, объём и границы

Прямое согласование 3 ноября: начать сейчас, один релизный пакет, самостоятельные обратимые решения/исправления/ветки/commit/push/PR, до трёх технических исполнителей по владельцам; без ожидания просмотра каждой задачи. **Итоговый просмотр пользователя обязателен перед production публикацией/переключением.** Изменение целей/баллов, потеря данных, расширение объёма и существенный риск сроку сообщаются сразу; независимая работа продолжается.

Весь активный Java/SP: Pages/PrairieLearn/IDE, действующая Moodle/Gateway-связь, student/full/IDE и оба SP-транспорта; согласованные условия/стартеры/оценочные случаи/JUnit feedback. Native Quarto/Pandoc + TS-канон без CUE; proof/receipt не обязательная архитектура.74группы — очередь решений. Print/Cloud исключены; методика/лекция9 отдельно.

Сохранять UID/UUID, признанные результаты, файлы/выдачу/связи. Identity/DB приёмка ещё pending. Нового общего педпилота нет. Старые checkout/PR/research сохранять; deploy/DB/данные пока не менялись.

## Стоимость, допуск и связь

Переиспользовать [R0–R6](research/improvement-research-results-20261103.md)/reports. Неизменные проверки, полные render/inspect/hash/матрицы для статуса не повторять. Run только под конкретный вопрос после значимого изменения/ошибки; fixture не benchmark курса. Один Source writer/контракт, независимый reviewer.

Постоянный Ultra отменён прямым указанием: Sol6.1/Standard; простые операции low, координация medium, сложные API/границы/review high, Ultra лишь для сложного архитектурного вопроса. Глобальную конфигурацию и нормативную [матрицу](agent-execution-proposal-20261029.md) не менять. [Первичный допуск](model-admission.md) проверяется от исходного хода по всему собственному интервалу. Root chat `01a1a142-7adc-7371-9765-4871d807dfcd`, исходный turn `01a1a144-b034-7730-9f55-4dc3c195943d`:10own contexts до line3907, latest `01a1a25e-a126-7dc1-becd-62fb670aa364`, top/nested Sol6.1/ultra; первичный session путь в историческом checkpoint. Сообщённая смена UI в этом heartbeat не отразилась; это не подтверждение medium. Основная работа делегирована low/medium/high. Automation TOML не содержит model/effort, view показывает карточку; неподдержанный setter не угадывать. [Контроль усилий](../../reports/adaptive-effort-controls.md).

**07:00–22:00** — гарантированное окно; **22:00–00:00** — продолжать при доступности, одна короткая сетевая попытка каждые10минут без циклов ожидания/build/inspect/hash; **00:00–07:00** сетевые продолжения не планировать. После возврата сначала установить исход незавершённого действия. Сводки12:00/20:00: готово/далее/риск/нужен ли ответ; вне них только существенное изменение. Активны оба расписания: **java-sp-10** (продолжение), **java-sp-3** (22:00–00:00); не создавать дубликаты/не трогать чужие paused. При завершении/итоговом отчёте к сроку поставить оба на паузу.

3–4 ноября — первое сквозное исправление;5–8 — основные изменения/интеграция;9 ноября15:00 — остановка добавления функциональности; вечером — итоговый просмотр и согласованное переключение;10 ноября09:00 — доступность и честный остаток.

## HEAD и владельцы

Область `/home/tolya/JavaCodex/java-release-20261110`, ветки `codex/java-release-20261110`; продуктовые HEAD ниже, state отдельно.

| Checkout | Продуктовый HEAD / состояние |
| --- | --- |
| `java` | продукт `76da97e5b72b03aae3484109b37378769eb0519e`, HEAD/state `a0e10aa1d85f6c141a75925e0769bfb533d2f811` remote SUCCESS; Cored7/PLda installed. PR нет; новый state commit отдельно. |
| `system-practice` | `3bb0e0789545420b9947bdd85fac3d71715d0e36` remote SUCCESS; imports babe83d1 приняты, Cored7/PLda installed. Новый PR нет, старыйPR9 сохранён. |
| `platform` | `b37febb6e46da681b16b27eadb676e29b764130b`, remoteb37; [draft PR35](https://github.com/Afonenko-Course-Tools/prairielearn-platform/pull/35), CI41670994246 success. |
| `core` | `ee3aa0897fefadff2543528e45032231b90c13c4` remote SUCCESS, [draft PR70](https://github.com/Afonenko-Course-Tools/quarto-course/pull/70); independent APPROVE, hosted CI ожидается. Product d7 accepted/installed; ea/ee — test-only fixes. |
| `prairielearn` | `09064082b48b1bd60b7d3910098db30138d9fed6` remote SUCCESS/independent APPROVE, [draft PR36](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/pull/36); hosted CI41693726908 SUCCESS. Курсы пока installedda; PL090 НЕ installed. |

Активны `/root/performance_tooling` (Sol6.1/high, отдельный Source owner) и root CI coordination; installer завершён. Performance release checkout `performance` ещё preparing; исходный actual `/home/tolya/course-tools/quarto-course-performance` сохранять, с historical `quarto-course-perf-arch` не путать. [Brief](../../briefs/performance-release-integration.md), отчёт `reports/performance-tooling-integration.md`. SP d7 native PASS прежний; не повторять. Core CI12–14min/main без прямых suite-дублей: existing full policy, без draft subsets.

Java timeout оставил untracked intermediate HTML рядом с labs/bank/delivery: сохранять, НЕ stage/cleanup. Tracked Source неизменён; не использовать общий git add.

Допуск этого state writer `/root/checkpoint_sp_core_d7`: собственный chat `01a1a29c-619a-7af2-8a94-cee2f914248a`, исходный turn `01a1a29c-61c3-7ce0-8067-cc19b95909ca`; primary `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T21-12-00-01a1a29c-619a-7af2-8a94-cee2f914248a.jsonl`, session_meta line1, полный собственный интервал от line8 включает contexts8/93, top/nested Sol6.1/low PASS; baseline не сдвинут. Standard назначен root; скрытый tier не утверждается, globals не менялись.

## Принятое evidence

Старые принятые пакеты остаются принятыми; детали не копировать и проверки без причины не повторять:

- Platform single compilation/CI и JUnit/public status: [compile](../../reports/platform-single-compilation-review.md), [CI](../../reports/platform-additional-source-ci.md), [JUnit](../../reports/platform-ordinary-junit.md), [status](../../reports/platform-public-case-status.md); rich public details pending, это не deployment/benchmark курса.
- Java IDs/UUID5labs сохранены: [IDs](../../reports/java-identifiers.md), [UUID](../../reports/java-assessment-uuid.md), [native fragment](../../reports/native-identifiers-integration.md). Circle6равных cases2open+4param, reference6/6/wrong2/6/starter0/6: [runtime](../../reports/circle-source-runtime-integration.md); результаты не пересчитывать.
- PL UUID/fallback/discarded ZIP/sourceRows: [candidate35496cdd](../../reports/pl-candidate-35496cdd.md). Mixed versions/batch adoption/убран третий student render: [versions](../../reports/materializer-package-versions.md), [SP](../../reports/sp-batch-adoption.md), [CI](../../reports/sp-ci-render-repeat.md).
- Core trusted owner/profiles/install и scratch false positives: [native review](../../reports/core-native-delivery-review.md), [profiles](../../reports/pl-native-profiles.md), [install](../../reports/core-pl-candidate-installation.md), [f10](../../reports/sp-native-f10-acceptance.md), [047](../../reports/sp-native-047-acceptance.md). Download defer/убран duplicate Site finalization: [review](../../reports/core-download-defer-review.md), PL [typing](../../reports/pl-typed-materialization-review.md)/[CI fix](../../reports/pl-ci-core-map-gate-review.md).
- Transpose: [contract](../../reports/transpose-file-contract.md) рассмотрен, student readReport не принят;10cases/origin/refusal требуют привязки, product diff нет.

## Точная точка продолжения

1. Установить исход hosted CI Coreee после successful push. Test-only ea/ee исправили старую withAssignmentExecution signature и отсутствующий stdlib/path import-map raw Deno child, independent reviews APPROVE; прежние d7/ea CI FAIL разобраны. [Последний review](../../reports/core-ci-perf-context-review.md): addressed runtime0.744s/typecheck3.454s. Новых draft policy/subsets нет; PR70/35/36 и старыеPR32/28/SP9 сохранять. Не повторять unchanged suites/full SP native для статуса.

2. SP ordinary student artifacts theory9 → practice5(default revealjs website) → handbook6 готовы за67.3886s: real catalogs74/5/43targets,27foreignrefs readinessPASS. Imports babe83d1 используют existing relative paths `_output/student` и реальные URLs, independent low ACCEPT. Первый complete native Core830/PLda PASS38.195870446s; установленный Cored7/PLda PASS37.882386316s. Оба actual exports:7questions/5works/7sourceRoots/6upload+1Workspace, все manual; automaticIds=[], projects=[]. [Readiness](../../reports/sp-qrc-reference-readiness.md), [d7 acceptance](../../reports/sp-native-d7-perf-acceptance.md). Capture fullSource4→3, directoryreads710→577, runtimehash4→3; source873/local9/output14 прежние. Singlepair не доказывает wallclock speedup. UUID/meaningful outputs сохранены; единственная seed-дельта — generation-bound README anchor, потери данных нет. Ignored producer outputs нужны для fresh standalone export на другой машине; runtime images/live grading/Workspace/deployment ещё не приняты.
3. Java ONE full native128QMD attempt: TIMEOUT900.001733s/exit124, UTC18:15:24.686→18:30:24.688. Last START93of128, persistedfacts92; reservationbound/128requests/childpostedfalse, no seal/complete/public/private. До timeout предметного отказа нет; это НЕ Java/automatic/live-grading приёмка. [Acceptance](../../reports/java-native-d7-acceptance.md), existing logs/execution `reports/java-native-d7-*`; [read-only cost report](../../reports/java-native-cost-next.md) готов по saved facts. Initial metrics inspect1/walk1/fullSource2/4751hashreads/11,619,819B/21915dirs — НЕ whole-export metrics; hash cost не объявлять причиной900s. Saved snapshot:7305dirs, из них7149projects — real package corridors, не cache/build/.git/empty;21915=3×7305 (initialwalk+2fences), реальные исходники не исключать. Не ограничивать128composition/не менять цели ради скорости; не повторять full run без нового обоснования.
4. **Приоритет — Performance tooling и подключение instrumentation.** Пользователь прямо разрешил параллельно актуализировать сам инструмент, оптимизировать его внутреннее устройство и применять для общего анализа/критичных мест; инструментальная статистика нужна итоговому review перед выпуском. До этого были Core counters+logs; ABI1 agent к Java не подключали, env unset даёт identity. Этого недостаточно для объяснения900s. Known finding: sameobject raw/public resource AST doublewalk, вклад во время не измерен. До новых full Java/SP нужны existing agent/bootstrap, TS/Lua child context и durable trace outputs; instrumented cap≤120s/retry0 план согласовать root. Off/summary/trace/cpu overhead самого tool — адресная оптимизация, strict own TS включает Performance; без нового framework/методики/validators и blind full run.

5. Максимальная типизация собственного активного TS остаётся задачей по [audit](../../reports/typescript-any-audit.md): реальные API/DTO/wire variants, unknown до same guards/checkedDTO; any/casts не покрытие. Core3af7600c owner types и d7 duplicate prepared fullSource/seal-entry runtime removal независимо ACCEPT/high; full closing guards сохранены ([owner](../../reports/core-typed-owner-boundary-review.md), [fences](../../reports/core-bounded-fences-review.md)). PL090:3production DTO files native-course→export-course→exportwriter, sharedStudentArtifact, one final graph+real-data/bytes negative PASS; [mixed legacy checks](../../reports/pl-typed-mixed-checks.md) отдельно. Следом bounded legacy DTO и AST/YAML/providers/tools/UNKNOWN compiler runners по карте; vendor исключён, новых schema/validators/extra qualification/coverage framework нет.

6. Далее public feedback неподдержанных overload; transpose trusted-reader известных форматов с honest unrecognized без student0/PASS, origin/refusal отдельно; дубли Core/metadata/HTML/CI; канон, назначение10labs/тренировки/editor overrides. Новые обязанности/цели/баллы — к пользователю.
7. Весь состав с exact pins/images/student/full/IDE/обаSP; реальные результаты/UID/восстановление. Итоговый просмотр перед production; честный остаток без выдуманных процентов.

Нормативные источники: [README](README.md), [решения](refactoring-plan.md), [регламент](release-execution-regulation-20261103.md). Прямые указания выше исторических назначений/расписаний.
