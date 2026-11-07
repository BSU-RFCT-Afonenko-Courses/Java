# Public case status: независимый технический обзор

## Собственный допуск

Роль `/root/review_single_compilation`, собственный чат `01a1a206-d7b5-72e0-9ecd-9a51668b54e4`, ожидаемая пара `gpt-6.1-sol/high`, Standard. Первичный файл `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T18-28-39-01a1a206-d7b5-72e0-9ecd-9a51668b54e4.jsonl`.

Прочитаны собственная session_meta и все четыре turn_context от исходного `01a1a206-d7e9-71f1-914b-9080eec3d241`, включая последнее возобновление `01a1a227-6afa-7023-9d16-417710f13521`. Все верхние model/effort и вложенные collaboration_mode.settings.model/reasoning_effort согласованы: `gpt-6.1-sol/high`. Начало интервала не сдвигалось. Standard назначен координатором, отдельного service tier в первичных данных нет; Fast/Ultrafast не выбирались. Нормативный `java/docs/model-admission.md` и глобальные настройки не менялись; скрытая серверная маршрутизация этим не устанавливается.

## Область

Read-only Source review Platform `a344ac90..b37febb6e46da681b16b27eadb676e29b764130b`, brief `platform-public-case-status.md`, одноимённый авторский report, полный diff шести файлов и окружающий путь listener/decoder/student feedback/pins. Повтор actual JDK25 fixture, Python/schema checks, Circle4/R1/CI/image не выполняется. Прочитаны полный diff и окружающий путь подписанных событий, schema/receipt admission, actual listener callbacks, public projection и preparation derived profiles.


## Результат: Clean

Конкретных существенных дефектов в проверенном diff не найдено. Исправление отделяет статус исполнения public case от наличия optional subject observation в согласованной границе.

- **Источник связи.** Optional `test` добавляется в существующий private case DTO самим `PlatformJUnitAdapter.caseReport()` из настоящего `MethodSource` actual JUnit TestIdentifier. DisplayName и uniqueId не парсятся, поле не берётся из TestReporter. Existing signed counts, id/status и callbacks не заменены новым механизмом.
- **Проверка до projection.** Existing shared decoder перенесён перед чтением public outcomes. Он сохраняет полную сверку complete/eventErrors/registered/started/outcomes/cases, дубликатов identities, failureKinds и protocol. Добавлено только optional непустое string поле до8192 символов; schema описывает ту же границу, v1/v2 DTO без поля остаются допустимыми.
- **Авторитет outcome.** В новом пути нужен ровно один matching `case.test`. Статус берётся из validated signed outcome `successful`/`failed`, а не из optional observation; aborted, отсутствующий/повторный selector не подтверждают public execution. Для инфраструктурного результата projection не выставляет passed/failed. Совпавший reporter actual сохраняется только при одном наблюдении с тем же публичным id/selector и согласованным статусом. Отсутствие либо конфликт actual не меняет подтверждённый leaf outcome и оставляет actual=null.
- **Совместимость.** При отсутствии MethodSource metadata сохраняется прежний путь явно разрешённого trusted reporter. При наличии cases он дополнительно требует хотя бы завершённый signed outcome заявленного вида; полностью skipped/aborted report не получает passed из одной observation. Новый путь с bound cases не отступает к reporter при missing/duplicate match. Это сохранение доверенного legacy reporter, не угадывание leaf selector по старым id/name.
- **Полнота и оценка.** Source grade по-прежнему до результата проверяет подписи, shared decoder, exact feedback protocol и отсутствие infrastructure failureKinds; skipped/aborted/container failures и недостаточный discovery не получают оценку implementation. Existing receipt `validate_outcome` повторно требует достоверный полный scored outcome и правильную равную долю. Алгоритм scoring/знаменатель и native student/tool classification в diff не менялись.
- **Приватность.** Public projection копирует только прежние явные поля descriptor и nullable actual/status; `test` не включается в publicCases, feedback.cases или native tests. Throwable/stdout/private paths не используются для восстановления actual/expected. Прежний явно опубликованный reporter value остаётся отдельным источником предметных данных. Unsupported rich assertion details этим status fix не заявлены реализованными.
- **Pins.** Обновлены adapter и оба shared runtime pins в двух Java source profiles, общий student_feedback pin обновлён у fish. Native derived profile наследует их существующим `baked_view`; fish counts не становятся JUnit shape. Runner, toolchain/image/course pins и State/Gate/Scope/Timeouts/Transform не затронуты.

## Свидетельства и ограничения

Переиспользованы адресные проверки из авторского report: один actual host JDK25 fixture с тремя leaves и configured missing selector, reported actual сохранён, статусы passed/null, failed/null, passed/42, not-run/null; denominator3 и score2/3. Дополнительно report фиксирует24Python checks и positive schema acceptance. Их запуск и source pin вычисление не повторялись.

Новых JDK/JUnit/Python/schema/grading/Gradle/render/hash/CI/image или Circle4/R1 опытов не было. Выполнены только чтение исходников/diff и запись этого report; Source/ветка не изменялись, субагенты не создавались. Clean относится к ограниченному исходному diff, не к новой OCI интеграции или завершению rich public assertion feedback.

Перед передачей повторно проверен полный исходный интервал от `01a1a206-d7e9-71f1-914b-9080eec3d241`: четыре turn_context, во всех верхние и вложенные `gpt-6.1-sol/high` согласованы; новой точки допуска нет.
