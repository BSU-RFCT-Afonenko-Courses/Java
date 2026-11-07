# Circle: один источник → grader → student_feedback проход

Результат: на Source Platform `a344ac90989374463dac0bda6d7efc1ba6781ea3` и Circle `b65ea6a96c2606633befbe2ece3ecaec7301b449` подтверждены **reference 6/6, starter 0/6, wrong 2/6, namespace 6/6**. У всех четырёх отчётов полный достоверный набор из шести реальных JUnit leaves. Известный дефект caller сохранился: publicCases normal/boundary показывают `not-run` на reference/wrong/namespace при завершённых signed passed/failed leaves. Source в этом поручении не изменялся.

## Собственный допуск и вход

Исполнитель `/root/circle_junit_implementation`, собственный чат `01a1a20c-7986-7602-8568-3d27a20aa379`, исходный turn роли `01a1a20c-79b7-77b3-ae50-f495b8f3b56c`, возобновлённый turn `01a1a21c-64d9-7fb1-b879-1502011c6c7b`. Прочитаны обе первичные turn_context от исходной точки без её сдвига; верхние model/effort и вложенные collaboration_mode.settings во всём интервале — `gpt-6.1-sol/high`. Standard назначен поручением, отдельного service tier в первичных записях нет. Источник и проверенные поля сохранены в [admission.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/circle-source-runtime-integration/admission.json). Нормативный файл и глобальная конфигурация не менялись.

Вход принят по [Circle технической рецензии](circle-junit-implementation-review.md) и [Platform ordinary JUnit рецензии](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/platform-ordinary-junit-review.md). Это проверка реализации принятого договора, без новых учебных критериев. Java HEAD при начале чтения — `99e311487457b76f5deb5931a9c91e434470edcd`, при финальной read-only сверке — `3acbb8a2851a211c59686ae280f376fa1ce12abc`; root сохранял независимые изменения. Адресный `git diff b65ea6a9..HEAD -- tasks/projects/polymorphism/circle-subclass tasks/_quarto.yml` пуст: использованные Circle и metadata совпадают с b65ea6a9. Platform HEAD весь проход — a344ac90. Оба рабочих дерева чисты при финальной проверке.

## Метод и точные артефакты

Переиспользован существующий R1 `run_cases.py`: копия [run_circle_source.py](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/circle-source-runtime-integration/run_circle_source.py) меняет только COURSE/PLATFORM/output BASE и конечный цикл четырёх вариантов с timeout. Старые R1 jobs и setup не перезаписывались; нового framework нет. Один процесс, строго последовательные reference → starter → wrong → namespace. Команда:

```console
python /home/tolya/JavaCodex/java-release-20261110/reports/circle-source-runtime-integration/run_circle_source.py
```

Как в R1, для каждого решения скопированы его реальные Java sources, canonical observations/checks/api и common api-support; grading-job.json построен из текущего Circle project-check profile. Runtime — **java25-junit-v1**, а runtime объявленной полной поставки курса остаётся java25-gradle-junit-v1. Локальный registry фиксирует actual Source bundle/runner/shared closure и синтетическую source image identity; это не новый OCI digest и не замена course/providers pins. [isolated-runtime-registry.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/circle-source-runtime-integration/isolated-runtime-registry.json), а также executionEvidence.dependencyHashes каждого результата сохраняют потреблённую source closure.

Фактическая JVM/компилятор: `/usr/lib/jvm/java-25-openjdk`, `25.0.4.1`, `--release 25`; JUnit standalone 1.14.1 и существующие JAR из текущего Platform. Запуск выполнен вне известного sandbox UID mapping с сохранением штатного parent identity guard; никаких патчей/подмен защит нет. Grader сообщает `containment=host-diagnostic`, не official/OCI admission.

Все конфигурации, исходные копии, private rawResult, normalized counts, native feedback и команды сохранены по решениям:

- [reference/result.json](circle-source-runtime-integration/jobs/polymorphism-circle-subclass/reference/result.json), рядом execution.json и tests/grading-job.json;
- [starter/result.json](circle-source-runtime-integration/jobs/polymorphism-circle-subclass/starter/result.json);
- [wrong/result.json](circle-source-runtime-integration/jobs/polymorphism-circle-subclass/negative-controls-wrong/result.json);
- [namespace/result.json](circle-source-runtime-integration/jobs/polymorphism-circle-subclass/alternatives-namespace/result.json).

[summary.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/circle-source-runtime-integration/summary.json) — компактное извлечение уже сохранённых четырёх результатов, не новый запуск и не второй источник оценки.

## Четыре фактических исхода и стоимость

| Решение | Classification | Passed/failed | Score, знаменатель | Полное время grader | Feedback completeness |
| --- | --- | --- | --- | --- | --- |
| reference | success | 6 / 0 | 1.0; 6 | 4.067319s | complete |
| starter | student-runtime-failure | 0 / 6 | 0.0; 6 | 4.150558s | incomplete |
| negative-controls/wrong | behavior-failure | 2 / 4 | 0.3333333333333333; 6 | 4.138929s | complete |
| alternatives/namespace | success | 6 / 0 | 1.0; 6 | 4.178241s | complete |

У всех четырёх: studentCompilation/trustedCompilation/API-precheck=`success`, infrastructure/dependentExecution=`complete`, `counts.complete=true`, discovered=executed=6, skipped=aborted=eventErrors=containerFailures=0. По шесть registered/started/outcomes/cases, native tests также шесть с max_points=1; maxPoints и сумма native max_points равны 6. Четыре parameterized invocation и два normal/boundary метода — реальные самостоятельные leaves; assertAll и контейнеры баллов не добавили.

Полная последовательность заняла **16.546660s** ([wall-time.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/circle-source-runtime-integration/wall-time.json)); параллелизм 1, ограничения 60s на сценарий /180s суммарно. В каждом scenario 13 штатных дочерних Java-команд, всего 52; все exit0 без timeout/reason. В частности, student/trusted compilation и observer/transformer/runner/API compilation выполнялись штатной цепочкой, с API и зависимым JUnit запуском. Время собственно JUnit execution соответственно 0.447854/0.457112/0.441478/0.431896s. Стоимость относится только к этому проходу, не является новым R1 benchmark или заявлением об ускорении курса. Повторов не было.

Wrong проходит только минимальный положительный и малый 1e-100 радиусы: абсолютный допуск законно принимает их площадь. Отвергнуты 0.25, 3, 1e150 и 2. Starter проходит API, но каждый из шести объектов падает в TODO базового конструктора; это корректная student-runtime-failure с достоверным 0/6, не инфраструктурный ноль.

## Фактические private и native details

У reference/wrong/namespace каждый leaf содержит три typed checks: имя (`circle`), конечность (`true`) и площадь с expected/actual/delta. Они доходят до native.feedback.cases и native.tests.description; строка `Допуск: ...` присутствует. Exact double assertions ширины/высоты исполняются настоящим JUnit без typed records, поскольку этот ordinary overload не поддержан facade. Наличие трёх checks не превращает пять assertions в три балла и не меняет полноту шести JUnit исходов. Провал именно ширины/высоты отдельным новым контролем здесь не исследовался.

Для wrong площадь передана без потери дельты:

| Радиус | Expected | Actual | Delta |
| --- | --- | --- | --- |
| 0.25 | 0.19634954084936207 | 0.7853981633974483 | 1.0E-9 |
| 3 | 28.274333882308138 | 113.09733552923255 | 1.0E-9 |
| 1e150 | 3.141592653589793E300 | 1.2566370614359172E301 | 3.141592653589793E288 |
| 2 | 12.566370614359172 | 50.26548245743669 | 1.0E-9 |

В wrong private rawResult каждый из четырёх провалов содержит настоящий `org.opentest4j.MultipleFailuresError: Multiple Failures (1 failure)` с вложенным AssertionFailedError, авторским сообщением «Площадь круга должна соответствовать радиусу с заданным допуском» и соответствующими expected/actual. У passed raw leaves message пуст. Native не копирует эти private throwable messages: failure tests имеют `message="Проверка не пройдена."`, description показывает trusted property «равенство с допуском», expected/actual и delta. Общая native classification — behavior-failure, message начинается «Результат не удовлетворяет проверяемому требованию.» и указывает действующее требование/whatCheck.

У starter все шесть private raw messages равны `java.lang.UnsupportedOperationException: TODO: состояние базового класса`; checks пусты. Native классифицирует runtime, сообщает «Программа завершилась с ошибкой во время выполнения», сохраняет шесть failed test rows по 0/1, но не публикует private TODO: descriptions содержат «Ожидается: не установлено / Получено: не установлено». feedbackCompleteness=incomplete здесь относится к деталям feedback; event/case completeness=true и знаменатель 6 сохранены. Reference/namespace native kind=success, все test messages «Проверка пройдена.».

Это подтверждает ordinary JUnit совместимость текущего Source, existing supported typed projection и delta rendering. Граница unsupported rich private details остаётся прежней: никакие Throwable/stack/message не использованы как новые полномочия публикации, generic facade или обход observation не вводился.

## Сохранившийся caller defect publicCases

Точный consumer результат — `native.feedback.publicCases`, при одновременно завершённых signed `result.counts.cases/outcomes`:

| Решение | normal/boundary signed leaves | normal/boundary publicCases | actual |
| --- | --- | --- | --- |
| reference | passed / passed | not-run / not-run | null / null |
| wrong | failed / failed | not-run / not-run | null / null |
| namespace | passed / passed | not-run / not-run | null / null |
| starter | failed / failed, runtime | failed / failed | «Выполнение прервано ошибкой программы; результат не получен.» у обоих |

У первых трёх result.counts.publicCaseObservations пуст; у starter adapter создал две runtime-failure observations. Поэтому удаление ручных TestReporter observations не мешает достоверным leaf status/score, но существующий public-case consumer продолжает показывать ложное `not-run` для завершённых ordinary passed/assertion-failed публичных случаев. При этом feedback.cases/native.tests показывают правильные статусы, а reference/namespace и wrong имеют feedbackCompleteness=complete. Этот известный R1 caller defect **не устранён** текущим Platform ordinary JUnit commit и воспроизведён здесь без отдельного исследования. Исправления в Java, возврата ручных observations или повторных probes не делалось; дальнейшее решение принадлежит root/Platform.

## Границы результата и передача

Новых Source правок, commit, push, PR, Quarto, assignment-authority, plain JUnit, Gradle, image/OCI/native-server запусков, инвентарей или субагентов не было. Существующие accepted результаты и 80/20 не пересчитывались; score показан как фактическая автоматическая доля source grader. Проверен ordinary java25-junit-v1 Source путь, а не java25-gradle-junit-v1 поставка, containment, installed image/server, ZIP или реальный UI.

Запрошенный единственный интеграционный проход завершён. Четыре исхода и шестой знаменатель подтверждены; открытый caller defect и ограничение public rich failure details остаются явно открытыми. Перед передачей весь исходный модельный интервал повторно подтверждён: две turn_context, gpt-6.1-sol/high, исходная точка не сдвигалась.
