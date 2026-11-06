# Circle: независимый технический обзор реализации JUnit

## Собственный допуск

Рецензент `/root/review_single_compilation`, собственный чат `01a1a206-d7b5-72e0-9ecd-9a51668b54e4`, исходный turn всего интервала `01a1a206-d7e9-71f1-914b-9080eec3d241`. Возобновлённый turn `01a1a212-6d09-7d53-9d4c-fd9a4ac24512` относится к этому же исходному интервалу; начало не сдвигалось. Назначение `gpt-6.1-sol/high`, Standard.

Прочитаны первичные `session_meta` и все две записи `turn_context` собственного файла `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T18-28-39-01a1a206-d7b5-72e0-9ecd-9a51668b54e4.jsonl`. Во всём интервале верхние `model=gpt-6.1-sol`, `effort=high` и вложенные `collaboration_mode.settings.model=gpt-6.1-sol`, `reasoning_effort=high` согласованы. Допуск подтверждён по `java/docs/model-admission.md`. Отдельного service tier в первичных данных нет; Standard назначен координатором, Fast/Ultrafast не выбирались. Глобальные настройки и нормативный файл не менялись; скрытая серверная маршрутизация этим не устанавливается.

## Область

Read-only Source review Java `3128c77b..b65ea6a96c2606633befbe2ece3ecaec7301b449` по принятому `circle-test-contract.md` и независимому содержательному `circle-test-contract-review.md`. Новые grading/JUnit/Gradle/render/hash опыты не запускаются. Прочитаны полный diff десяти файлов, принятый договор и его Astra/high рецензия, авторский report и сохранённый plain JUnit log; исходное условие, canonical starter/API, все затронутые student/demo/checks/observations файлы, metadata и active Core source-selection/materialize путь. Применены AGENTS, языковое правило, действующая матрица и предметные решения 60/65.


## Результат: Clean

Конкретных существенных дефектов в ограниченном diff не найдено. Реализация соответствует принятому договору шести самостоятельных JUnit исходов.

- **Шесть leaves и публичность.** `checks/PublicExamplesTest` сохраняет методы `normal` и `boundary` с двумя открытыми радиусами; `checks/ContractTest` создаёт четыре самостоятельных `@ParameterizedTest` invocation из `Double.MIN_VALUE`, `1e-100`, `0.25`, `3`. Повтор `1e150` из private набора удалён. Русские DisplayName и параметризованные имена соответствуют договору. В student и demo нет скрытых входов/ContractTest; показаны только `2` и `1e150`. Вызов helper не добавляет JUnit leaf или веса.
- **Обычные assertions.** `PublicChecks.checkCircle` создаёт один объект и вычисляет `diameter=2*r`, `expectedArea=Math.PI*r*r`, `tolerance=max(1e-9,1e-12*abs(expectedArea))` из входа. Проверяемые getters не участвуют в expected. Через `assertAll` проверяются точные размеры, имя `circle`, конечность и площадь с delta. Ручные Observation/boolean/expected-строки, TestReporter-публикации и неиспользуемый capture удалены. Нет требования конкретной формулы, поля radius, public базового конструктора или обработки недопустимых радиусов. Ноль площади на двух малых входах по-прежнему допустим. Исключение создания объекта или area относится к конкретному leaf и не создаёт исходов остальных радиусов.
- **Starter/API/namespace.** Student Circle теперь совпадает с canonical незавершённым starter; student TwoDShape уже совпадал. Обязательные signatures и API-precheck не менялись. `TaskBinding.area` по-прежнему вызывает `area()` через `TwoDShape`. Явные импорты стандартных типов в helper и student TaskBinding устраняют затенение именами законной namespace-альтернативы; ContractTest явно импортирует String/Double/Stream/Arguments, public tests явно импортируют Test/DisplayName. Нового API решения не введено.
- **Selectors и оценивание.** Три открытые JUnit-копии согласованы, canonical/student helper согласованы. В `_quarto.yml` изменены только Circle public names и описание feedback; selectors `PublicExamplesTest#normal`/`#boundary` совпадают с методами и DisplayName. Runtime/java-build/scoring/references/API, action/expected и соседние профили сохранены. README.local описывает полный знаменатель 6 и отсутствие дополнительного веса demo; 80/20 и сохранение признанных прежних результатов оставлены. UUID и результаты не затронуты.

## Прослеженная выдача и legacy README/CHECKS

Отдельно проверена возможная несовместимость старого `student/README.md` → `CHECKS.md` с появившейся JUnit зависимостью helper. Для действующего native project-download этот путь **не выбирается**:

- Circle `_metadata.yml` задаёт `starter.path=starter`, `instructions.local.path=README.local.md`, `instructions.gradle.path=../../common/java/gradle.md`; delivery выбирает api+demo и необходимые apiSupport/observations, verification — api+contract.
- У установленного Core `artifacts/plan.ts:138` roots выбираются по audience, `:212` добавляет только объявленный starter tree, `:222` — выбранные components/testsets, `:263` и `:267` — именно Gradle/local instruction fragments. Никакого неявного source tree всего project-root или legacy `student` здесь нет.
- `artifacts/project.ts:447` читает только `state.trees`, применяя исключения внутри выбранных деревьев (`:460`); `:774` добавляет выбранные gradle/local fragments к условию, `:839` создаёт итоговый README. Исключения basename не используются как предположение, что legacy README снят: его отсутствие следует из source selection.
- В самом условии Circle и обоих выбранных instruction fragments нет ссылки на project-root README, `student/README.md` или `CHECKS.md`. Поэтому корневой Source README со старой ссылкой и JDK-only `tasks/test-support/CHECKS.md` не становятся содержимым/ссылкой текущего комплекта. Общая Gradle-инструкция прямо задаёт JUnit dependency через build.gradle и `gradle test`.

Это статическая трассировка действующего выбора файлов; новый ZIP/render не создавался. Отдельный запуск legacy student tree как самостоятельной готовой выдачи этим обзором не квалифицирован.

## Переиспользованные свидетельства и границы

Сохранённый [circle-junit-plain.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/circle-junit-plain.log) подтверждает ровно шесть начатых, завершённых и не пропущенных plain JUnit tests эталона (6/6), wrong (2/6), две открытые проверки student-набора с namespace (2/2) после явных imports и console exit0. Первоначальная namespace compilation error в логе сохранена и не названа результатом решения. Все эти прогоны переиспользованы без повторения.

Новых JUnit, grading, Gradle, compilation, render, inspect, image или hash запусков не было. Source/ветка не изменялись, субагенты не создавались. Известный отказ Platform Transform не повторялся и не исправлялся в этом поручении. Source review и plain JUnit не доказывают Platform feedback/scoring интеграцию: после отдельного исправления Transform остаётся согласованная root проверка полного набора reference/starter/wrong/alternative, expected/actual/delta и классификации ошибок. Clean относится к этому исходному diff, а не к уже выполненному выпуску.

Перед передачей повторно проверен полный собственный интервал от `01a1a206-d7e9-71f1-914b-9080eec3d241`: две записи turn_context с согласованными верхними и вложенными `gpt-6.1-sol/high`; начало интервала сохранено.
