# Выпуск Java/SP — единственное состояние продолжения

Срок: **10 ноября 2026, 09:00 Europe/Minsk**. Обновление 3 ноября, 21:12 Minsk. При входе сверить реальные HEAD и активных исполнителей, затем продолжать следующий шаг ниже. [Исторический checkpoint](../../reports/release-evidence-checkpoint-20261103-2000.md) — только evidence, никогда не state; прежние сроки/START из истории не возобновлять. Измерения и ограничения — в reports, история — в Git.

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
| `java` | `76da97e5b72b03aae3484109b37378769eb0519e` local, remote `22bdd4f35dca28421420e7e49ed5b61fcfaa6d9b`; Cored7/PLda installed. PR нет; state commit отдельно, push pending. |
| `system-practice` | `3bb0e0789545420b9947bdd85fac3d71715d0e36` local, remote `1626aa0792301f6c91cf2a04bd4d242f89736578`; imports babe83d1 приняты, Cored7/PLda installed. Новый PR нет, старыйPR9 сохранён. |
| `platform` | `b37febb6e46da681b16b27eadb676e29b764130b`, remoteb37; [draft PR35](https://github.com/Afonenko-Course-Tools/prairielearn-platform/pull/35), CI41670994246 success. |
| `core` | `d7cc10eaa21fa35bbe32d73cc4afa216002393da` accepted/installed, remote `830381697beea4106655965e10605cc13987199b`. Owner typing3af7600c и bounded fencesd7 приняты независимым high review; push/PR pending. |
| `prairielearn` | `da2ad6b6c00891590e59e5abccf3d1eb4ff552f7` installed/SSHremote, [draft PR36](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/pull/36) обновлён. Typing4e и CI fixda независимо ACCEPT; full hosted CI41684816393 SUCCESS, старыйf9FAIL устранён. |

Все пять checkout clean перед этим state edit. `/root/install_empty_scratch_pair` завершён; повтор full SP native не нужен. Единственный активный исполнитель — `/root/core_ci_cost_scope`, read-only отчёт: Core CI фактически12–14min/main, дублирующих suites не найдено. Root принял existing full CI за12–14min: новых draft-specific policy/subsets не вводить. Первый Core PR на actuald7 получит один existing full run без новых local runs; PR ещё не создан.

Допуск этого state writer `/root/checkpoint_sp_core_d7`: собственный chat `01a1a29c-619a-7af2-8a94-cee2f914248a`, исходный turn `01a1a29c-61c3-7ce0-8067-cc19b95909ca`; primary `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T21-12-00-01a1a29c-619a-7af2-8a94-cee2f914248a.jsonl`, session_meta line1/turn_context line8: полный собственный интервал, top/nested Sol6.1/low PASS. Standard назначен root; скрытый tier не утверждается, globals не менялись.

## Принятые пакеты

Подробные команды/измерения — в existing reports, не дублировать.

| Пакет | Принято / evidence |
| --- | --- |
| Platform `0e4a12dc`/`503e0ccb`/`d14231d0` | Без повторных installation checks, одна production compile/JAR; hosted CI50341664797570 success. [compile/review](../../reports/platform-single-compilation-review.md), [CI](../../reports/platform-additional-source-ci.md). Не deployment/benchmark курса. |
| Platform `a344ac90`/`b37febb6` | Ordinary JUnit и signed actual public status, sandbox/infra сохранены. [JUnit](../../reports/platform-ordinary-junit.md), [status](../../reports/platform-public-case-status.md). Rich public details pending. |
| Java IDs/UUID | Активныеj25 убраны, UUID5labs прежние. [IDs](../../reports/java-identifiers.md), [UUID](../../reports/java-assessment-uuid.md), [native fragment](../../reports/native-identifiers-integration.md). Не full-course/DB. |
| PL `7a41b7cc`/`5a04576e`/`35496cdd` | UUID/fallback, discarded ZIP/sourceRows index; CI41664497387 success. [candidate](../../reports/pl-candidate-35496cdd.md). |
| Circle `b65ea6a9` |6равных cases:2открытых+4параметризованных; reference6/6, wrong2/6, starter0/6. Старые результаты не пересчитывать. [runtime](../../reports/circle-source-runtime-integration.md); четыре варианта без причины не повторять. |
| Materializer99e/SP8103/2659/6ed2 | Mixed versions законны, batch adoption, убран третий student render. [versions](../../reports/materializer-package-versions.md), [SP](../../reports/sp-batch-adoption.md), [CI](../../reports/sp-ci-render-repeat.md). |
| Core9331/332e, PL3a6c | Trusted-owner Quarto вместо priorP launcher graph, P2/profiles приняты. [Core](../../reports/core-native-delivery-review.md), [profiles](../../reports/pl-native-profiles.md), [install](../../reports/core-pl-candidate-installation.md). |
| Coref10/047 | Shortcode/scratch falsepositive исправлены; история [f10](../../reports/sp-native-f10-acceptance.md), [047](../../reports/sp-native-047-acceptance.md). |
| Core830/PLda | Download defer assignment render принят, дублирующая Site finalization внутри capture убрана, Core guard прежний. [Core review](../../reports/core-download-defer-review.md); PL [typing](../../reports/pl-typed-materialization-review.md)/[CI fix](../../reports/pl-ci-core-map-gate-review.md) ACCEPT. |
| Transpose | Контракт рассмотрен, student readReport не принят;10cases/origin/refusal требуют привязки, product diff нет. [contract](../../reports/transpose-file-contract.md). |

## Точная точка продолжения

1. **Сохранить push exact HEAD выше**, включая отдельный state commit; оформить первый Core draft PR на actuald7 с одним existing full CI run (12–14min), без новой CI policy/subsets и local runs. Remote HEAD в таблице — последние подтверждённые до начатого root push Core/SP; сначала установить его исход. Не запускать повторные unchanged suites/full SP native для статуса. PLda hosted CI41684816393 SUCCESS; PR35/36 сохранять, PR32/28/старыйSP9 не менять.
2. SP ordinary student artifacts theory9 → practice5(default revealjs website) → handbook6 готовы за67.3886s: real catalogs74/5/43targets,27foreignrefs readinessPASS. Imports babe83d1 используют existing relative paths `_output/student` и реальные URLs, independent low ACCEPT. Первый complete native Core830/PLda PASS38.195870446s; установленный Cored7/PLda PASS37.882386316s. Оба actual exports:7questions/5works/7sourceRoots/6upload+1Workspace, все manual; automaticIds=[], projects=[]. [Readiness](../../reports/sp-qrc-reference-readiness.md), [d7 acceptance](../../reports/sp-native-d7-perf-acceptance.md). Capture fullSource4→3, directoryreads710→577, runtimehash4→3; source873/local9/output14 прежние. Singlepair не доказывает wallclock speedup. UUID/meaningful outputs сохранены; единственная seed-дельта — generation-bound README anchor, потери данных нет. Ignored producer outputs нужны для fresh standalone export на другой машине; runtime images/live grading/Workspace/deployment ещё не приняты.
3. Следующий продуктовый шаг — readiness Java и оставшаяся типизация по [existing audit](../../reports/typescript-any-audit.md). Core3af7600c owner boundary types и Cored7 removal duplicate prepared fullSource/seal-entry runtime проверки независимо ACCEPT/high: [owner](../../reports/core-typed-owner-boundary-review.md), [fences](../../reports/core-bounded-fences-review.md). Полные closing guards до sealed сохранены. Максимальная типизация собственного активного TS: реальные API/DTO/wire variants, unknown до same existing guards/checkedDTO; any/casts не покрытие. Vendor исключён, новых schema/validators/extra qualification/coverage framework нет. PL typing4e/CI fixda приняты; оставшиеся AST/YAML/providers/tools и UNKNOWN compiler runners — по карте.

4. Далее public feedback неподдержанных overload; transpose trusted-reader известных форматов с honest unrecognized без student0/PASS, origin/refusal отдельно; дубли Core/metadata/HTML/CI; канон, назначение10labs/тренировки/editor overrides. Новые обязанности/цели/баллы — к пользователю.
5. Весь состав с exact pins/images/student/full/IDE/обаSP; реальные результаты/UID/восстановление. Итоговый просмотр перед production; честный остаток без выдуманных процентов.

Нормативные источники: [README](README.md), [решения](refactoring-plan.md), [регламент](release-execution-regulation-20261103.md). Прямые указания выше исторических назначений/расписаний.
