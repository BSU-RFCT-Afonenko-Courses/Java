# Circle: реализация обычного JUnit договора

Статус: исходники реализованы и ограниченный plain JUnit проверен; source-runtime интеграция остаётся root; ограниченная реализация принятого [договора](circle-test-contract.md) после [независимой содержательной рецензии](circle-test-contract-review.md).

## Собственный первичный допуск

Назначение: `gpt-6.1-sol/high`, Standard. Прочитан собственный первичный файл `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T18-34-48-01a1a20c-7986-7602-8568-3d27a20aa379.jsonl`: `session_meta.id = 01a1a20c-7986-7602-8568-3d27a20aa379`, путь агента `/root/circle_junit_implementation`, исходный собственный `turn_id = 01a1a20c-79b7-77b3-ae50-f495b8f3b56c`, `root_turn_id = 01a1a1ee-79d1-7653-865d-9c6e1955f926`. Родительский ID не использован как собственный. Верхние `model/effort` и вложенные `collaboration_mode.settings.model/reasoning_effort` согласованы: `gpt-6.1-sol/high`. Standard задан поручением; отдельного поля service tier в первичной записи нет. Скорость и глобальная конфигурация не менялись. Свидетельство устанавливает зарегистрированные параметры, а не скрытую маршрутизацию.

Исходный Source: `/home/tolya/JavaCodex/java-release-20261110/java`, ветка `codex/java-release-20261110`, HEAD `3128c77b`, рабочее дерево перед правками чистое. Единственный writer Circle файлов — этот исполнитель; root state/pins и другие владельцы не затрагиваются.

## Границы и проверки

Требуемый набор: два открытых и четыре параметризованных скрытых случая. API/TaskBinding, условие, UUID, 80/20 и старые результаты сохраняются. Известный отказ Transform не повторяется; установленный Platform и его facade не изменяются. Реализация проверяется ограниченным plain JUnit на настоящем JDK 25, без нового Gradle/image/whole-course набора и без тестов тестовой обвязки. Полная source-runtime интеграция остаётся единственной согласованной root проверкой после исправления Platform.

## Реализация и точные изменённые пути

Все перечисленные ниже пути находятся в `/home/tolya/JavaCodex/java-release-20261110/java`:

- `tasks/projects/polymorphism/circle-subclass/checks/src/test/java/ContractTest.java`: четыре `@ParameterizedTest` leaves с русскими именами и входами `Double.MIN_VALUE`, `1e-100`, `0.25`, `3`; удалены цикл, дублирующий `1e150`, ручной массив expected и неиспользуемый capture.
- `tasks/projects/polymorphism/circle-subclass/checks/src/test/java/PublicExamplesTest.java`: два открытых `@Test`, русские DisplayName; имена методов `normal`/`boundary` сохранены. Удалены TestReporter и публикации observations.
- `tasks/projects/polymorphism/circle-subclass/demo/src/test/java/PublicExamplesTest.java`: та же открытая пара. Метаданные сохраняют взаимоисключающий выбор demo для student и contract для полной проверки.
- `tasks/projects/polymorphism/circle-subclass/observations/src/test/java/PublicChecks.java`: конкретный helper `checkCircle(double)` для свойств одного Circle. Ожидания выводятся из входного радиуса, фактические значения читаются через существующий TaskBinding и базовый тип. Пять обычных assertions объединены `assertAll` в один исход. Удалены Observation, общий оценочный boolean, строковые NORMAL_EXPECTED/BOUNDARY_EXPECTED и неиспользуемые импорты. Консольный клиент вызывает те же assertions для двух открытых входов и не хранит второй канон ожиданий.
- `tasks/projects/polymorphism/circle-subclass/student/src/test/java/PublicChecks.java`: согласованная открытая копия helper/console без скрытых входов.
- `tasks/projects/polymorphism/circle-subclass/student/src/test/java/PublicExamplesTest.java`: добавлена согласованная открытая JUnit-копия; скрытые входы в student не добавлялись.
- `tasks/projects/polymorphism/circle-subclass/student/src/test/java/TaskBinding.java`: только добавлены уже существующие в canonical observations явные импорты `java.lang.Object/String`; сигнатуры и прямой вызов area через TwoDShape сохранены. Это устраняет обнаруженный plain javac конфликт с законной namespace-альтернативой.
- `tasks/projects/polymorphism/circle-subclass/student/src/main/java/Circle.java`: устранено старое расхождение с каноническим starter; возвращена та же незавершённая заготовка конструктора вместо заранее заполненного super с диаметром. Сигнатуры сохранены. Student TwoDShape уже совпадал со starter.
- `tasks/projects/polymorphism/circle-subclass/README.local.md`: вместо прежних трёх групп указан принятый полный набор из шести равных случаев и отсутствие повторного веса demo. Формула 80/20 и сохранение признанных результатов оставлены.
- `tasks/_quarto.yml`: по отдельному разрешению root изменён только фрагмент `java-polymorphism-circle-subclass`: public names совпадают с DisplayName, `feedback.what-check` описывает обычные assertions и отдельный исход радиуса. Selectors `PublicExamplesTest#normal`/`#boundary`, action/expected, scoring, API, references и все соседние профили сохранены.

Канонические starter/reference, API, observations TaskBinding и решение wrong не изменялись. Допуск площади ровно `max(1e-9, 1e-12*abs(Math.PI*r*r))`, отдельно проверяется конечность; размеры сравниваются точно, имя — с circle. Новых требований к способу вычисления, конструкторам или недопустимым входам нет. Нулевой результат площади для двух малых входов остаётся законным.

## Выполненная проверка

Подробные команды, дерево настоящих JUnit исходов, полные ошибки и exit codes находятся в [circle-junit-plain.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/circle-junit-plain.log). Компилятор и JVM — `/usr/lib/jvm/java-25-openjdk/bin/{javac,java}`, фактическая версия `25.0.4.1`; компиляция с `--release 25 -encoding UTF-8`. Имеющийся JAR — `platform/runtime/java25-junit-v1/junit-platform-console-standalone-1.14.1.jar`. Никакой Transform/facade при этом не вызывается.

| Сценарий | Компиляция / JUnit | Наблюдение |
| --- | --- | --- |
| reference + observations + checks | javac exit0; JUnit exit0 | Найдены и начаты все 6 tests, 6 successful, 0 skipped/aborted/failed; четыре параметризованных leaf видны отдельно; исполнение JUnit 168ms |
| wrong + observations + checks | javac exit0; JUnit ожидаемый exit1 | Найдены и начаты все 6; 2 successful, 4 failed; два малых радиуса проходят по абсолютному допуску, остальные отвергнуты assertions площади; исполнение JUnit 189ms |
| namespace + открытые student tests, первая попытка | javac exit1 | Выявлено старое отсутствие явных imports Object/String в student TaskBinding; JUnit не запускался. Ошибка не объявлена результатом решения |
| namespace + открытые student tests после синхронизации imports | javac exit0; JUnit exit0 | Найдены и начаты 2 открытых tests, 2 successful, 0 skipped/aborted/failed; исполнение JUnit 99ms |
| console PublicChecks на namespace после исправления | exit0 | Оба открытых входа проходят те же assertions |

Первый ограниченный проход использовал отдельный timeout не более 30s на дочернюю команду и общий предел 120s; после найденного расхождения повторён только затронутый namespace/student сценарий (javac + JUnit + console: 1.454s). Reference и wrong неизменно не перезапускались. Стартер и полный namespace contract в этом plain проходе не исполнялись; их будущая source-runtime проверка остаётся root. Gradle/image/whole-course/R1 матрица не запускалась, новые тесты тестовой обвязки не добавлялись.

`tasks/_quarto.yml` прочитан `yaml.safe_load`: синтаксис корректен, Circle public selectors и имена доступны в `project-checks.profiles`. Перечитан весь diff; открытые helper/test копии и canonical demo согласованы, student main приведён к canonical starter, скрытые inputs находятся только в checks. `git diff --check` выполнен без замечаний.

## Оставшийся интеграционный шаг

После готовности Platform root один раз согласует source-runtime интеграцию reference/starter/wrong/alternative: полный достоверный набор 6 leaves, ожидаемые 6/6, 0/6, 2/6 и 6/6, сохранение expected/actual/delta и правильной классификации ошибок. Здесь это прогноз для будущего маршрута, а не его PASS. Самостоятельного повторения известного Transform отказа, изменения установленного Platform, pins/providers, push/PR/deploy не было. Авторская самостоятельная проверка diff не заменяет независимую техническую рецензию.

## Допуск перед передачей

Повторно прочитан весь доступный исходный интервал от `01a1a20c-79b7-77b3-ae50-f495b8f3b56c`, без сдвига начала: 1 запись turn_context; все верхние и вложенные параметры `gpt-6.1-sol/high`. Собственные ID и первичный файл сохранены выше. Отдельное поле service tier отсутствует, Standard остаётся назначением поручения.

## Сохранение результата

Source commit: `b65ea6a9` — «Circle: шесть самостоятельных JUnit случаев и согласованная выдача», родитель `3128c77b`; 10 файлов, 156 добавленных и 94 удалённых строки. Перед commit `git diff --cached --check` завершился exit0, перечень staged путей сверён с перечислением выше. В commit включены только 9 Circle файлов и разрешённый фрагмент `tasks/_quarto.yml`; отчёты хранятся рядом с brief, вне Source Git. [Обозримый diff](circle-junit-implementation.diff) сохранён по этому commit. Push/PR/deploy не выполнялись.
