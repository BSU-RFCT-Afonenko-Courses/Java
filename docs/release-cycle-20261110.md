# Выпуск Java/SP — единственное состояние продолжения

Срок: **10 ноября 2026, 09:00 Europe/Minsk**. Обновление 3 ноября, после installer checkpoint047/f9. При входе сверить реальные HEAD и активных исполнителей, затем продолжать следующий шаг ниже. [Исторический checkpoint](../../reports/release-evidence-checkpoint-20261103-2000.md) — только evidence, никогда не state; прежние сроки/START из истории не возобновлять. Измерения и ограничения — в reports, история — в Git.

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
| `java` | `097f22f53ad5849506135254e404d167be1e4c1c`; Core047/PLf9 installed,30targets/19files на курс. PR нет; product097 ещё не pushed, сохраняется вместе со state. |
| `system-practice` | `a69081d7e3e01c9403ed39595548376f8c3ca1af`; Core047/PLf9 installed. Exact remote=HEAD; новый PR нет, старыйPR9 сохранён. |
| `platform` | `b37febb6e46da681b16b27eadb676e29b764130b`, remoteb37; [draft PR35](https://github.com/Afonenko-Course-Tools/prairielearn-platform/pull/35), CI41670994246 success. |
| `core` | `0478458a71f3acf34ad6d9983ac11023783dde3d`, Source clean, независимо PASS, installed; exact remote=HEAD. |
| `prairielearn` | Source `4e4ab0cd5499006826f5ab21d1e3fdeef9c313b1`: typing11files+284/-63, typecheck PASS; ещё не independently reviewed/pushed/installed. Installed/remote `f9c00aed6de610dce7d9b7cb6e50c28ebfb9573e` независимо ACCEPT; [draft PR36](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/pull/36) обновлён: «Сохранить UUID и типы API, убрать повторы native-экспорта». CI41680971465 наf9 FAIL, см. ниже; writer делает отдельный CI fix, до изменений сообщит подход. Evidence: `reports/pl-typed-materialization.md`. |

Installer завершён. Активны `/root/core_native_delivery` (Sol6.1/high, read-only lifetime/callorder/необходимость Downloadpost; run запрещён) и `/root/pl_typed_materialization` (Sol6.1/high, typing4e4ab готов, следующий отдельный CI fix). Предыдущие reviewers завершены. 

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
| Coref10, Java38cb/SPe579 | Shortcode fix; SP exit1/28.1436s,7QMD/QRC/Site/Corepost7 прошли, membership labs отказал; complete/seal/транспорты pending. [attempt](../../reports/sp-native-f10-acceptance.md). |
| Core047/PLf9 | Scratch falsepositive исправлен, строгая Core API/DTO boundary принята и установлена. [scratch](../../reports/core-empty-scratch-review.md), [PL](../../reports/pl-strict-core-boundary-review.md). |
| Transpose | Контракт рассмотрен, student readReport не принят;10cases/origin/refusal требуют привязки, product diff нет. [contract](../../reports/transpose-file-contract.md). |

## Точная точка продолжения

1. Core047+PLf9 установлены адресно:30targets/19files на курс. Единственный SP attempt **25.6473s/exit1/retry0**:7QMD/Corepost7/7current/finish готовы0.3s, старый labs falsepositive исчез. Новый отказ Downloadpost `withCurrentNativeOperation`: OPERATION_INCOMPLETE «Операция ещё не завершена» (`operation.ts2275→Downloadruntime633`). [Отчёт](../../reports/sp-native-047-acceptance.md). Core owner делает read-only диагноз lifetime/callorder/необходимости Downloadpost. **Новый run запрещён** до устранённой причины; Java pipeline не добавлять.
2. Максимальная типизация всего собственного активного TS: API/DTO/wire variants, unknown до existing parse, точные типы после; any/casts не покрытие. Vendor исключён; новых validators/coverage framework нет. [Очередь](../../reports/typescript-any-audit.md): candidate/ownerJSON, selection/materialization/feedback, AST/YAML, остальные providers/tools; compiler runners UNKNOWN. Новую PL партию рецензировать отдельно от pinned install.
3. PLf9 CI41680971465 FAIL: envless Coregate получил NotCapable QUARTO_PROFILE из-за раннего functionalProfiles read; existing denotest без map:3TS2307+9TS7006,106tests/exdev green. [Диагноз](../../reports/pl-ci-f9-failure.md). PL writer после typing4e4ab исправляет actual compiler entries/Corepin и gate-before-env, без --no-check/новойматрицы. PR35/36 сохранять, PR32/28/старыйSP9 не менять; новый PR перед known-red expensive gate не открывать.
4. Далее public feedback неподдержанных overload; transpose trusted-reader известных форматов с honest unrecognized без student0/PASS, origin/refusal отдельно; дубли Core/metadata/HTML/CI; канон, назначение10labs/тренировки/editor overrides. Новые обязанности/цели/баллы — к пользователю.
5. Весь состав с exact pins/images/student/full/IDE/обаSP; реальные результаты/UID/восстановление. Итоговый просмотр перед production; честный остаток без выдуманных процентов.

Нормативные источники: [README](README.md), [решения](refactoring-plan.md), [регламент](release-execution-regulation-20261103.md). Прямые указания выше исторических назначений/расписаний.
