# Независимый обзор обычного JUnit

**Результат: clean в принятой ограниченной области.** Actionable findings в diff `503e0ccb61ab5460fe0433fa0d13d623e1e6ffef..a344ac90989374463dac0bda6d7efc1ba6781ea3` не обнаружены. Проверены все шесть изменённых файлов и непосредственные producer→consumer связи. Source `/home/tolya/JavaCodex/java-release-20261110/platform`, HEAD `a344ac90989374463dac0bda6d7efc1ba6781ea3`; production и tests рецензентом не изменялись.

## Собственный первичный допуск

Рецензент `/root/review_ordinary_junit`, собственный чат `01a1a213-d42a-7230-8b4b-cdf6feaddf3e`, исходный turn `01a1a213-d45b-77d2-8491-08cc6965b7ed`. Назначение по критичной технической роли: `gpt-6.1-sol` / `high`, Standard. Первичный источник: `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T18-42-50-01a1a213-d42a-7230-8b4b-cdf6feaddf3e.jsonl`. Собственный ID установлен по `session_meta.payload.id`, а не по унаследованному `session_id` родителя.

Проверен весь текущий интервал от исходного turn: все присутствующие `turn_context` (1 запись, ordinal7), верхние `model/effort` и вложенные `collaboration_mode.settings` совпадают с назначением. Допуск пройден; машинная запись — [platform-ordinary-junit-review-admission.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/platform-ordinary-junit-review-admission.json). Standard задан поручением; отдельного speed-поля в первичной записи нет. Проверка подтверждает зарегистрированную пару, а не скрытую серверную маршрутизацию. Нормативный `java/docs/model-admission.md` и глобальная конфигурация не менялись.

## Основания вывода

- `Transform.java:245` меняет только условие перенаправления: supported exact descriptor идёт прежнему facade, остальные вызовы сохраняют исходную JUnit instruction. Компиляция и исполнение остаются привязаны к настоящему закреплённому JUnit. Новых фасадов, повторного исполнения выражений/callbacks или вычисления lazy message не добавлено. Сбор sealed sites по фактически перенаправленным instructions остаётся прежним.
- Прочитаны `Assertions`, `State`, `Gate`, `Scope`, `Timeouts` и adapter. Их код не меняется. Сохраняются bound adapter/control BCI, sealed trusted classes/sites, проверка стека до invocation scope, отказ student intermediary и lifecycle/grant отзыв timeout worker. Обычный `assertAll` проходит уже разрешённые pinned JUnit frames; это не выдаёт student callback полномочий. Unsupported observer-вызов не создаёт typed observation.
- `PlatformJUnitAdapter.executionStarted/executionFinished`, `junit_result.validate_counts` и grader продолжают считать реальные JUnit identities и outcomes. `assertAll` не создаёт дополнительные scoring leaves; parameterized invocation остаётся отдельным leaf. Обязательная event/case completeness, API gate, границы student/trusted/tool failure и denominator не изменены.
- `student_feedback.py:280` освобождает только успешно завершённый leaf с пустыми optional checks от ложной неполноты. Failed leaf без checks и явный pending/incomplete check остаются неполными. Новая строка `Допуск` выводит существующее разрешённое поле `delta`, которое до этого уже проходило decoder и было в публичном checks; новый источник данных не появляется.
- Existing private путь `JUnitAutograder.executionFinished` → raw leaf message → `grade.py.rawResult` сохраняется. Native consumer по-прежнему берёт только разрешённые counts/checks/reporter: Throwable text, stack, stdout, hidden expected и private paths не получают новой public authority.
- Pins изменены согласованно по области: Transform в обоих Java profiles, shared consumer в обоих Java и fish. Runner/JUnit/toolchain/image pins прежние. Прочитанный `prepare_native_toolchain.baked_view` выводит native profile из этих source profiles и проверяет их closure; отдельного пропущенного native source pin не требуется.

## Проверочные свидетельства

Повторные JDK25/security/fullsuite/build/render/hash/CI503 запуски не выполнялись. Независимо прочитаны существующие первичные результаты автора в `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T18-16-11-01a1a1fb-6d1d-7760-9a87-e2f25f95f8ef.jsonl`:

- ordinal459 (строка460): завершённый процесс, exit0; 21 tests / 47.000s / OK. В точной команде и выводе есть mandatory `test_mutable_throwable_stack_cannot_authorize_subject_values`, callback/lazy/opaque, student reentry, late worker, numeric tolerance, API/trusted failure и baked dependency pins. Прочитан сам mutable regression: настоящий student `AssertionFailedError` с forged expected/private classpath и двумя вариантами mutable stack оставляет публичные checks пустыми и canaries скрытыми.
- ordinal471 (строка472): уточнённый `test_ordinary_junit_keeps_real_leaves_and_private_failure_details`, exit0; 1 test / 3.913s / OK. Прочитан fixture: executed6/passed3/failed3/score0.5, один aggregate leaf с двумя checks и двумя private причинами, две parameterized rows, ordinary double/String, typed tolerance и lazy success без checks. Проверяется приватность сообщений/expected/path и native строка delta.

Рабочее дерево Platform при завершении обзора чистое. Новых опытов, source edits, commits или push рецензент не выполнял.

## Граница приёмки

Этот clean review принимает ordinary JUnit compatibility и две согласованные native правки. Он **не закрывает весь public assertion feedback**: rich details неподдержанных overload остаются private, публичная карточка такого failure может не иметь expected/actual/message. Public-case status/binding и установленный сервер/OCI/kernel containment этим обзором не принимаются.
