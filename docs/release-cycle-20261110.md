# Выпуск Java/SP — единственное состояние продолжения

Срок: **10 ноября 2026, 09:00 Europe/Minsk**. Обновление 3 ноября, 20:35 Minsk. При входе сверить реальные HEAD и активных исполнителей, затем продолжать следующий шаг ниже. [Исторический checkpoint](../../reports/release-evidence-checkpoint-20261103-2000.md) — только evidence, никогда не state; прежние сроки/START из истории не возобновлять. Измерения и ограничения — в reports, история — в Git.

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
| `java` | `22604ae18fb43bb52e9d4aad6ba52f77779685e9` local после state22bdd; Core830/PLda installed. PR нет; root сохраняет вместе со state. |
| `system-practice` | install `904fed875d546247cd84df23c8128b50071344f4` → config `1626aa0792301f6c91cf2a04bd4d242f89736578`, уже remote1626; Core830/PLda installed. Новый PR нет, старыйPR9 сохранён. |
| `platform` | `b37febb6e46da681b16b27eadb676e29b764130b`, remoteb37; [draft PR35](https://github.com/Afonenko-Course-Tools/prairielearn-platform/pull/35), CI41670994246 success. |
| `core` | `830381697beea4106655965e10605cc13987199b` accepted/installed/remote. Следующая bounded JSON owner typing партия в Source, не installed. |
| `prairielearn` | `da2ad6b6c00891590e59e5abccf3d1eb4ff552f7` installed/SSHremote, [draft PR36](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/pull/36) обновлён. Typing4e и CI fixda независимо ACCEPT; full hosted CI41684816393 SUCCESS, старыйf9FAIL устранён. |

Активны `/root/install_empty_scratch_pair` (Sol6.1/medium, один ordinary release artifact пакет, исход ещё неизвестен) и `/root/core_native_delivery` (Sol6.1/high, runtime operation/assignment-admission JSON owner types; без actual pipeline). SP читает frozen installed830, не dirty Core Source.

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

1. SP830 attempt22.2385s завершил7docs/HTML, потребовал late binding. Book project URL ошибочно был в website: SP1626 переносит **тот же URL** в `book.site-url`, namespace/ID/value прежние; low review ACCEPT ([review](../../reports/course-book-site-url-review.md)), Java6bookconfigs без дефекта. Следующий attempt23.4663s завершил7docs/HTML и отказал late `QRC.TARGET_UNKNOWN system-practice-theory:sec-work-environment`. ID реальный; locals толькоtasks, imports отсутствуют, это не stale catalog. [830](../../reports/sp-native-830-acceptance.md), [book URL](../../reports/sp-native-book-url-acceptance.md), [readiness](../../reports/sp-qrc-reference-readiness.md). **Exports нет; полный capture не означает доставку/готовность обоих transports.**
2. Сейчас запущен **один** ordinary полезный release artifact пакет theory9 → practice5(revealjswebsite) → handbook6student, shared180s/TERM5/retry0/firstfailurestop, без standaloneinspect/PLcapture; результат ещё неизвестен. После успеха3реальных local catalogs → existing relative imports config с realURL и readiness27knownrefs → независимый review → только один следующий SP native. Imports пока не менялись. Не повторять pipeline/checks для статуса.
3. Максимальная типизация всего собственного активного TS: реальные API/DTO/wire variants, unknown до same existing guards/checkedDTO; any/casts не покрытие. Vendor исключён, новых schema/validators/extra qualification/coverage framework нет. [Очередь](../../reports/typescript-any-audit.md): PL typing4e/CI fixda приняты; Core owner продолжает bounded runtime operation/assignment-admission JSON партию, не installed/без actual pipeline. Остальные AST/YAML/providers/tools и UNKNOWN compiler runners остаются по карте. PR35/36 сохранять, PR32/28/старыйSP9 не менять.

4. Далее public feedback неподдержанных overload; transpose trusted-reader известных форматов с honest unrecognized без student0/PASS, origin/refusal отдельно; дубли Core/metadata/HTML/CI; канон, назначение10labs/тренировки/editor overrides. Новые обязанности/цели/баллы — к пользователю.
5. Весь состав с exact pins/images/student/full/IDE/обаSP; реальные результаты/UID/восстановление. Итоговый просмотр перед production; честный остаток без выдуманных процентов.

Нормативные источники: [README](README.md), [решения](refactoring-plan.md), [регламент](release-execution-regulation-20261103.md). Прямые указания выше исторических назначений/расписаний.
