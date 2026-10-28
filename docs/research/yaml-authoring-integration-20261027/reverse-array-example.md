# Цельный пример reverse-array для согласования

Пользователь принял один типизированный вход, совместимые короткие атрибуты и YAML для сложных деклараций,
а затем — один входной YAML проекта с отдельными файлами тестов, данных и решений рядом.
Последнее уточнение: **общие настройки наследуются в стиле Quarto; зависимости выбирает конкретный проект/набор тестов**.
Пользователь принял имя `_metadata.yml` для единственного локального входа вместо `project.yml` и разграничение баз путей с сохранением provenance.
Точные ключи, схемы, технический мост, обработка native rebasing и реализация ещё не утверждены.
Уточнение от 28 октября: выбраны обычный Java/JUnit, короткое пояснение и подключаемый исходник; fixtures/параметризация — по необходимости.
Новые карточки сценариев и сценарный DSL исключены. Эскиз раскладки и метаданных от 27 октября сохраняется ниже; его точные поля и привязки ещё не утверждены.
Позднее пользователь выбрал обобщение необязательной самопроверки через JUnit, общие библиотеки и готовый Gradle-проект (пункт 32 плана), а затем одобрил показанную картину одного канона по типам данных и обычного Gradle-комплекта (пункт 33). Принято направление; точная структура модулей, поля и привязка общего build ещё не утверждены.
Технические YAML-блоки разделов 2–4 сохранены как исторический эскиз 27 октября: их `libraries`, runners и прежняя JDK/JAR-форма не задают новый Gradle-контракт. Полное условие раздела 1 сохраняется.
Новые учебные ограничения и сценарии не добавлены. Продуктовые файлы не создаются и не изменяются.

## Что уже существует

- [Условие](../../../tasks/bank/arrays.qmd#L62) требует `int[]`, обратного порядка без изменения массива,
  JDK 25 и `Main.main(String[] args)`; разрешает самостоятельно выбирать алгоритм, методы и организацию программы.
- [Нынешний стартер](../../../tasks/projects/arrays/reverse-array/student/src/main/java/Main.java#L1)
  — `Main.java` без `package`; [README](../../../tasks/projects/arrays/reverse-array/student/README.md) уже описывает отдельные исходники и запуск.
- [PublicChecks](../../../tasks/projects/arrays/reverse-array/student/src/test/java/PublicChecks.java#L4)
  содержит две проверки `normal` и `boundary`, их входы, ожидаемые значения и пояснения.
  [Конфигурация](../../../tasks/_quarto.yml#L642) повторяет часть этих сведений; этот повтор в эскиз не переносится.
- [JavaProcess](../../../tasks/test-support/src/main/java/coursesupport/JavaProcess.java#L1) и
  [OutputNumbers](../../../tasks/test-support/src/main/java/coursesupport/OutputNumbers.java#L1) используют обычный JDK.
  [PublicScenarioTest](../../../tasks/test-support/junit/PublicScenarioTest.java#L1) — JUnit Jupiter с соглашением `public-case`/`actual` для PL.
  [CliPrecheck](../../../tasks/test-support/api/CliPrecheck.java#L3) зависит от Platform API и является отдельной стадией.
- [References и ожидания стартера](../../../tasks/_quarto.yml#L406) уже существуют.
  Прежний [teacher root](../../../tasks/test-support/README.md) — факт действующей поставки; будущий канон проектов, тестов и решений находится в общем Git.

## Предложение обычного Gradle-комплекта от 28 октября

**Направление принято пользователем**: для малого reverse-array — обычный самостоятельный **выходной** проект, без обязательного отдельного модуля ради двух helper. Внутренние канонические каталоги этим деревом не утверждаются; имя demo-теста иллюстративно, его код и новые случаи не создаются.

```text
reverse-array/                         # полученный студенческий комплект
├── README.md                          # полное условие из QMD и запуск
├── build.gradle                       # настоящий канонический Gradle-код
├── settings.gradle                    # настоящий канонический Gradle-код
└── src/
    ├── main/java/Main.java
    └── test/
        ├── java/
        │   ├── ReverseArrayDemoTest.java   # выбранный обычный JUnit demo
        │   └── coursesupport/
        │       ├── JavaProcess.java       # только если нужен этому demo
        │       └── OutputNumbers.java     # только если нужен этому demo
        └── resources/                     # только реально нужные данные
```

Канонические источники и производный состав разделены:

| Канон | Что автор задаёт | Чего не дублируем / что производно |
| --- | --- | --- |
| QMD | Полное условие и необязательный краткий указатель к самопроверке | Текст не превращается в описание внешних build-зависимостей |
| Локальный `_metadata.yml` и native наследование Quarto | Свойства курса, выбор компонентов/наборов/выдачи и их объявленное замыкание | Это граф выбранных course-файлов, не второй внешний Gradle-граф; Gradle не выводит предметные demo по Java imports |
| Настоящие Gradle build/settings | Внешние зависимости, repositories, scopes, версии и tasks | Gradle не читает course YAML; мы не разбираем Groovy и не строим второй внешний граф зависимостей |
| Общие helper и build-файлы | Один канон; для типовых проектов — настоящие общие `build.gradle`/`settings.gradle` | Существующий producer переносит их в корень student, а не генерирует скрипт из YAML; отдельный канонический build каждой задачи не обязателен |
| Производные artifact inventory и receipt | Автор не ведёт их вручную: producer фиксирует состав, source→target, origin/hash | Проверяемые копии выдачи допустимы, но не становятся вторым вручную редактируемым каноном |

Такой перенос уже имеет файловую основу: [common mappings build/settings](../../../tasks/_quarto.yml#L7846), [producer/origins/конфликты](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/artifacts/materialize.ts#L137) и [receipt](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/artifacts/materialize.ts#L194). Это не готовый новый JUnit-шаблон: нынешний [shared build](../../../tasks/test-support/shared/b42bf4ab370d/build.gradle#L4) запускает `PublicChecks.main` и отключает `test`.
Специальный [GradeApp](../../../tasks/projects/files/gradeapp-report/student/build.gradle#L1) сохраняет собственный настоящий Gradle build как канон. Повтор `grade.Main`/`gradeapp.jar` в [build](../../../tasks/projects/files/gradeapp-report/student/build.gradle#L18) и [course config](../../../tasks/_quarto.yml#L1595) — конкретное место будущего согласования native convention/производного значения либо equality-check, не повод писать Groovy parser.

[Convention plugins](https://docs.gradle.org/current/userguide/implementing_gradle_plugins_convention.html), [buildSrc/shared build logic](https://docs.gradle.org/current/userguide/sharing_build_logic_between_subprojects.html), [composite builds](https://docs.gradle.org/current/userguide/composite_builds.html), [version catalogs](https://docs.gradle.org/current/userguide/version_catalogs.html) и [test fixtures](https://docs.gradle.org/current/userguide/java_testing.html#sec:java_test_fixtures) — готовые кандидаты при оправданном объёме, не обязательный каркас малого Main-проекта. Пока версии принадлежат реальному общему Gradle-коду; catalog не предписан.
Если build действительно использует catalog/build logic/локальный helper-модуль, нужные файлы и настройки входят в самостоятельный ZIP либо зависимость реально доступна. Соседний авторский checkout не является скрытой runtime-зависимостью; included build не наследует чужие repositories/catalog автоматически.

После первоначальной настройки JDK/установленного Gradle и разрешения внешних зависимостей — `gradle test` или IDE. Полный offline на чистой машине не обещан; базовое написание/сдача одного `Main.java` не обязывают пользоваться Gradle или demo. Путь `src/main/java/Main.java`→ответ `Main.java` уже задаётся нынешним source profile; скачанный student ZIP не равен Java-only архиву дополнительных исходников ответа.
PL может повторно использовать те же канонические JUnit-файлы/helper, но его исполнение не объявляется локальным Gradle-запуском: закреплённый [gradle-jar build](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/runtime/gradle-jar-v1/build.gradle#L1) использует native-config и отключает tests; [grader recipe](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/images/java25-grader/grade.py#L776) не является student `build.gradle`. Classpath/scoring/API-precheck/feedback/observations требуют отдельной приёмки; это source pin, не допуск живого сервера.

Связь code-project metadata с native input/origins остаётся открытой: `bank/arrays.qmd` не наследует `_metadata.yml` соседнего `projects/arrays/reverse-array` по одной ссылке `project`. Gradle-проект эту границу не решает; сохранённое ниже исследование моста применимо.
Нужны проверка автономного состава/запуска, точного student inventory сайта/PL, пути ответа и проверки полной авторской выдачи. Время подготовки/Gradle/исполнения не измерено; новая схема и реализация не выбраны. Полный IDE-комплект включает фактические проверки и решения в раздельных контекстах, а не набор student-профилей.

## Эскиз канонических каталогов от 27 октября

Корень владельца здесь — `tasks/_quarto.yml`; внешний Quarto-проект Java не подставляется вместо него.
Имя локального `_metadata.yml` принято; перемещённые пути и внутреннее устройство ниже остаются эскизом. Отдельной страницы задачи нет.

```text
tasks/
├── _quarto.yml                            # определения компонентов и технологии
├── bank/arrays.qmd
├── test-support/
│   ├── java/process/coursesupport/JavaProcess.java
│   ├── java/numbers/coursesupport/OutputNumbers.java
│   └── adapters/
│       ├── junit/PublicScenarioTest.java
│       └── prairielearn/CliPrecheck.java
└── projects/
    ├── _metadata.yml                      # общие значения Java-проектов
    └── arrays/
        ├── _metadata.yml                  # необязательные тематические исключения
        └── reverse-array/
            ├── _metadata.yml              # принятый единственный вход проекта
            ├── README.md
            ├── starter/src/Main.java
            ├── tests/
            │   ├── demo/PublicChecks.java  # нынешние normal/boundary и их данные
            │   └── full/…                 # нынешние предметные тесты/данные
            └── solutions/
                ├── reference/src/Main.java
                └── alternative/src/Main.java  # если добавлен второй вариант
```

Общие файлы получают смысловые каталоги вместо каталогов по хешам и не копируются в каждый канонический проект.
`tests/full/…` означает перенос фактических предметных тестов, без материализованных копий общей инфраструктуры.
Имена этих файлов и их дополнительные зависимости уточняются по исходникам полного набора, а не угадываются.
В эскизе 27 октября данные `normal`/`boundary` оставались в `PublicChecks.java`; эта прежняя форма не задаёт будущий JUnit-тест. Новые `cases.yml` и сценарный DSL не вводятся.
Если тесту действительно нужны отдельные данные, они находятся рядом и объявляются его ресурсами.

## 1 Учебный QMD

Div сохраняет нынешний ID. Заголовок остаётся **первым блоком**; служебный YAML идёт после него.
Маркер `.course-declaration` предложен только для чтения примера: установленный Core его не поддерживает.
Содержимое условия ниже воспроизведено из нынешнего QMD; канон равноправно может подключаться include.

````qmd
:::: {#exr-arrays-reverse-array}
## Элементы массива в обратном порядке

```{.yaml .course-declaration}
difficulty: introductory
project: /projects/arrays/reverse-array
```

Выведите элементы заданного `int[]` от последнего к первому,
не изменяя сам массив.

Стандартный ввод содержит неотрицательную длину `n`, затем ровно
`n` значений типа `int`, разделённых пробельными символами.
Выведите только элементы в обратном порядке через пробельные символы.
Для `4 7 -2 7 0` результат — `0 7 -2 7`; для `0` вывод не содержит чисел.
При пустом стандартном вводе используйте демонстрационный массив,
который выберите в исходнике.

Используйте JDK 25. Точка входа — `Main.main(String[] args)`.
Алгоритм, вспомогательные методы и организацию программы выберите
самостоятельно.

Представьте исходники, команды и выбранные входы. Покажите обычный,
пустой и одноэлементный массивы, а также исходный массив после вывода.
Объясните его участие в алгоритме и сохранность компонентов:
один консольный результат не доказывает, что массив не изменялся.

{{< project-download exr-arrays-reverse-array kind="starter" text="Скачать заготовку и инструкцию запуска" >}}
::::
````

`project` сохраняет понятную ссылку на каталог от корня owner `tasks`, как в нынешнем авторском пути.
Единственный локальный YAML в этом каталоге использует принятое имя `_metadata.yml`; способ получения native наследования техническим мостом ещё предстоит доказать.
Это новое разрешение проектного входа, а не уже существующая возможность `project`.
В QMD не повторяются runtime, списки тестов, источники общих библиотек, варианты решений и настройки PL.
Унаследованные значения и локальный вход задают `target` и проверку вместо повторяемого `project-check` в каждой Div; это предложение привязки к существующим полям после нормализации, не отдельная модель.
Выбор назначения в конкретной лабораторной остаётся в составе работы; из этой записи не выводятся stage, slot или обязательность.

## 2 Определения в корневом _quarto.yml

Все три YAML-уровня используют один предложенный namespace `course-project`.
Quarto владеет объединением метаданных; расширение — типами полей этого namespace и их нормализацией в существующие модели.
Имена схемы ниже не означают реализованный API. Общий профиль задаёт технологию и runners, **не выбирает тестовые зависимости**.

```yaml
# tasks/_quarto.yml — фрагмент
course-project:
  components:
    java-process:
      sources: test-support/java/process/**/*.java
    numeric-output:
      sources: test-support/java/numbers/**/*.java
    junit-scenarios:
      sources: test-support/adapters/junit/PublicScenarioTest.java
      libraries: [junit-jupiter]
    pl-cli-api:
      sources: test-support/adapters/prairielearn/CliPrecheck.java
      libraries: [platform-api]
      entry: CliPrecheck$StringArrayMain
  profiles:
    java-console:
      java: {release: 25, encoding: UTF-8}
      implementation: {include: ["**/*.java"]}
      runners:
        jdk: {kind: java-main}
        junit: {kind: junit-console}
        platform: {runtime: java25-junit-v1, scoring: all-pass}
  verification-profiles:
    starter-and-references:
      reference: {student-compilation: success, job: complete, required-tests: all-pass}
      starter:
        student-compilation: success
        job: complete
        classification: behavior-failure
        score: {less-than: 1}
      discovery: {min-executed: 1, allow-skipped: false}
```

Определить компонент не значит включить его во все проекты или выдачи.
`junit-jupiter`/`platform-api` и runners здесь — имена исторического эскиза 27 октября, не утверждённый YAML-граф внешних Gradle-зависимостей. По пункту 32 версии и разрешение стандартных зависимостей принадлежат настоящему Gradle-коду.
Прежний кандидат JUnit JAR в `lib` не выбран как общий путь. Download [исключает `**/*.jar`](../../../tasks/_extensions/Afonenko-Course-Tools/project-download/domain/config.ts#L7), но это условная граница возможной предсобранной бинарной выдачи, не первая обязательная задача Gradle-managed пути; новый архиватор и глобальное разрешение JAR не предлагаются.
Текущий материализатор `student`/`grading` ещё не реализует новый договор `student`/`full`/IDE.
PL precheck использует отдельную стадию платформы и сохраняет нынешний [CliPrecheck$StringArrayMain](../../../tasks/_quarto.yml#L655),
а не более широкий общий `CliPrecheck.check`. Договор `Main.main(String[] args)` не меняется.

## 3 Общие значения каталога

Это значения для Java-проектов под `projects`, без списков конкретных тестов и helper-библиотек.
В `projects/arrays/_metadata.yml` можно оставить только реальные тематические исключения; пустой файл не нужен.
В принятом разграничении путей `starter: starter` означает расположение внутри каждого выбранного проекта; происхождение правила остаётся у этого `_metadata.yml`. Имя поля здесь ещё иллюстративно.
Пример значений для обсуждения:

```yaml
# tasks/projects/_metadata.yml
course-project:
  profile: java-console
  starter: starter
  target: prairielearn
  verification: starter-and-references
  student-tests: explicit-demo-only
  delivery:
    prairielearn: {grading: mixed, submission: upload}
```

Многофайловые, пакетные и иные задачи сохраняют собственные договоры; этот общий профиль не устанавливает им `Main` или запрет `package`.
Ссылок `defaults: ../../../...` в каждом проекте больше нет. Это схема желаемого авторского наследования;
для папки Java её ещё должен обеспечить доказанный мост к native Quarto, описанный ниже.

## 4 Единственный локальный вход

Принято имя `_metadata.yml` в самом каталоге задачи: оно использует штатный уровень directory metadata.
Дополнительный `project.yml` не создаётся. Это сокращает сопоставления по сравнению с отдельным YAML и его подключением через `metadata-files`.
Приняты имя и принцип наследования; конкретные поля и техническое обеспечение native контекста ниже ещё не утверждены.

```yaml
# tasks/projects/arrays/reverse-array/_metadata.yml
course-project:
  entrypoint: Main.main(String[] args)
  testsets:
    demo:
      sources: [tests/demo/PublicChecks.java]
      dependencies: [java-process, numeric-output]
      runner: {use: jdk, main: PublicChecks}
      scenarios: [normal, boundary]
    full:
      sources: [tests/full/**/*.java]
      include-testsets: [demo]
      # Дополнительные зависимости/ресурсы — после сверки полного набора.
      contexts:
        ide: {runner: junit, adapter: junit-scenarios}
        prairielearn:
          runner: platform
          adapter: junit-scenarios
          api-precheck: pl-cli-api
  solutions:
    reference: solutions/reference/src
    alternative: solutions/alternative/src
  delivery:
    prairielearn:
      example: starter/src/Main.java
      accept:
        primary: Main.java
        additional-java-sources: additional-sources.zip
      show-demo: source
    pages:
      testsets: [demo]
```

Теперь `java-process`/`numeric-output` выбраны именно для demo этой задачи. Full явно включает demo и получает его зависимости;
его собственное дополнение ещё надо сверить. Отсутствие дополнения в эскизе не является доказательством, что полный набор больше ничего не требует.
`junit-scenarios` выбирается только контекстами IDE/PL полного набора, а `pl-cli-api` — только контекстом PL.
JUnit-обёртка сама использует обычный JUnit; `public-case`/`actual` — соглашение обратной связи PL, не зависимость числовых проверок от платформы.
Поле `scenarios` в эскизе от 27 октября отсылало к нынешним `normal`/`boundary`; это прежняя привязка, а не утверждённая новая декларация сценариев.
`show-demo: source` — прежний эскиз показа, не утверждённое обязательство inline: по пункту 31 основные demo выдаются файлами, краткого указателя достаточно; фрагмент/подробная проза необязательны, карточки сняты с рассмотрения.
Обычный Java/JUnit с коротким пояснением и подключаемым исходником — принятая база без descriptor для показа кода.
Существующий протокол результатов PL [описан как факт для аудита](public-tests.md), а не целевая модель новых карточек; этот CLI-эскиз не навязывает YAML-формат другим тестам.
Точный способ включения и выбора фрагментов для PL ещё нужно доказать.

`alternative` удаляется из записи, если второго реального решения нет; отсутствие объявленного каталога — ошибка.
Каждый вариант компилируется отдельно вместо стартера и проходит тот же полный набор; одноимённые `Main` не объединяются.
Именованная verification использует существующий механизм после нормализации: reference all-pass, стартер компилируется,
но завершает проверку поведенческим провалом и оценкой менее единицы. Сбой инфраструктуры не становится ожидаемым провалом стартера.
Новая платформа для этого не создаётся; поля, адаптация входа и команда ещё не реализованы.

В эскизе 27 октября использован `starter/src/Main.java`; нынешний канонический стартер находится в `student/src/main/java/Main.java`, выходной Gradle-комплект выше сохраняет `src/main/java/Main.java`. Основной файл сдаётся как `Main.java`, при необходимости дополняется `additional-sources.zip`.
Разрешённые вспомогательные классы, файлы и пакеты сохраняются; фиксирован только опубликованный `Main.main(String[] args)`.
`package` в стартер не добавляется; пакет `coursesupport` общей инфраструктуры сохраняется.

## Состав трёх выдач

| Выдача | Состав и запуск | Граница |
| --- | --- | --- |
| PL | Полное условие, простой базовый файл и необязательный canonical student-комплект; ответ `Main.java` и при необходимости `additional-sources.zip` | Краткий указатель/файлы demo не требуют полного inline-показа; скачивание ZIP не обязывает сдавать ZIP; выполнение grader проверяется отдельно |
| Pages student ZIP | README, настоящий build/settings, `src/main/java/Main.java`, явно выбранные ordinary JUnit demo/helper/ресурсы | Внешние зависимости разрешает Gradle; full, решения и Platform API-precheck не добавляются автоматически; точный inventory ещё проверяется |
| Авторский IDE full | README, полный фактический набор проверок/ресурсов/зависимостей и варианты решений с раздельными контекстами | Не студентский комплект с несколькими профилями; PL precheck — отдельная стадия с Platform API, не обычная JUnit-компиляция |

Принято: README каждого самостоятельного ZIP/IDE-комплекта содержит полное условие из канонического QMD без ручного дублирования.
Преобразование должно сохранить ссылки, ресурсы и нужное оформление; точный способ ещё проверяется, сырое копирование QMD в README не обещано.

У нынешней JDK-only самопроверки минимальное замыкание — четыре исходника: `Main.java`, `PublicChecks.java`, `coursesupport/JavaProcess.java`, `coursesupport/OutputNumbers.java`.
JUnit и Platform API этой прежней форме не нужны; это не будущее замыкание ordinary JUnit demo, для которого стандартные зависимости разрешает Gradle. Выбранный demo без нужной объявленной зависимости не становится самостоятельным комплектом.
Состав полного IDE-комплекта окончательно фиксируется после проверки реальных full sources и инструментов.
Общий Git доступен для исходников, но не определяет состав студенческих ZIP. Миграционная политика прежних результатов не меняется.

## Где заканчивается native Quarto

Штатное [наследование Quarto](https://quarto.org/docs/projects/quarto-projects.html#shared-metadata)
объединяет `_quarto.yml`, directory `_metadata.yml` и метаданные **входного документа**; это не автоматический обработчик папки Java.
Принятая авторская цепочка для выбранного входа в каталоге задачи:
`tasks/_quarto.yml → projects/_metadata.yml → arrays/_metadata.yml → reverse-array/_metadata.yml`.
Тематический уровень необязателен. `_metadata.yml` не создаёт вложенный Quarto-проект.

Статическое исследование 27 октября дополнено пятью опытами 28 октября на закреплённом Quarto **1.11.5**
(исходный коммит `87d1078002991c4ba9cc2baa1f401a80402148af`). Во всех опытах владелец — **`project.type: default`**, не нынешний Java `book`.
Выполнен только [inspect](https://quarto.org/docs/cli/inspect.html), без render, execute и pre-render.
Поскольку создание контекста затрагивает `.quarto/cache/temp`, опыты проведены в изолированном временном дереве без hooks, filters, extensions, кода и настроек окружения; авторский репозиторий не инспектировался.

| Случай | Наблюдение | Граница вывода |
| --- | --- | --- |
| `bank/arrays.qmd` | Получены root + bank + document metadata | Ссылка на кодовый проект не добавляет его `_metadata.yml` |
| Каталог только с кодом | Возвращены root config/inventory: `dir=ROOT`, только root research, input `bank/arrays.qmd`, `files.config` только root `_quarto.yml` | Это не эффективные метаданные кодового каталога |
| Настоящий `probe.qmd` в кодовом каталоге, вход владельца | Получена цепочка root → projects → theme → task + document | Вход в правильном контексте использует native наследование |
| `README.md` по умолчанию | Вход не включён в inventory владельца; fallback даёт `project.dir=task` и только локальные metadata | Прямой inspect README сам по себе теряет родительскую цепочку |
| Тот же README, явно включённый через `project.render` | `project.dir=root`, inputs bank + README, полная цепочка root/projects/theme/task | Проверена возможность native-контекста у default owner; не публикация README/страницы задачи и не квалификация Java book |

Различие directory/document inspect видно в [ветках команды][inspect-project] и [single-file fallback][inspect-fallback].
[Допуск входа и исключение README][inspect-inputs] объясняют разные результаты одной и той же Markdown-записи.
Авторский договор не меняется: **один локальный `_metadata.yml`, без нового обязательного QMD или другого файла**.
README с внутренним допуском либо выбранный служебный input — варианты технического адаптера, а не новый выбор автора.
Для Java book ещё нужно доказать корректный owner/input, жизненный цикл изоляции и отсутствие побочной публикации; собственного resolver не выбираем.

Проверено и [native rebasing существующих относительных строк][inspect-rebase]: унаследованное `localLiteral: src`
при наличии `projects/src` и собственного task `src` стало `../../src`, то есть сохранило родительский источник.
`commonExisting` стало `../../shared/Helper.java`; отсутствующий путь и glob остались прежними, CSS перебазирован.
Это **не** реализация принятого project-relative смысла `starter`: базу определяет тип поля, происхождение каждого значения нужно сохранять отдельно.
В результате эффективные значения находятся в `formats.html.metadata`; [fileInformation.metadata][inspect-frontmatter] содержит только исходный front matter.
[JSON-договор inspect][inspect-json] не даёт per-value origins, а `files.config` не перечисляет directory contributors.
Сверка raw/effective, происхождение и типизированное перебазирование ещё не решены; уже изменённую строку нельзя вслепую трактовать заново.

В Core уже есть [native metadata closure с memo](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/infrastructure/metadata-closure.ts#L27),
[инвентаризация цепочки каталогов](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/infrastructure/metadata-closure.ts#L169)
и [owned overlays](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/infrastructure/owned-config-overlay.ts#L35).
Это основания адаптации, а не готовый мост Java book: listed-input/materialization-origin ограничения сохраняются.
Native raw reader в этих опытах не запускался. Локальные свидетельства — `summary.json` и `readme-explicit-summary.json` в `/tmp/quarto115-code-metadata-2p7330ob/`; закреплённые исходники ниже сохраняют проверяемую основу вне временных файлов.
Доступный локально Quarto 1.11.6 не подменяет pin 1.11.5. Выполнение и выдача самого Java этим исследованием не проверены.

[inspect-project]: https://github.com/quarto-dev/quarto-cli/blob/87d1078002991c4ba9cc2baa1f401a80402148af/src/inspect/inspect.ts#L70
[inspect-fallback]: https://github.com/quarto-dev/quarto-cli/blob/87d1078002991c4ba9cc2baa1f401a80402148af/src/inspect/inspect.ts#L155
[inspect-inputs]: https://github.com/quarto-dev/quarto-cli/blob/87d1078002991c4ba9cc2baa1f401a80402148af/src/project/project-context.ts#L882
[inspect-rebase]: https://github.com/quarto-dev/quarto-cli/blob/87d1078002991c4ba9cc2baa1f401a80402148af/src/project/project-shared.ts#L138
[inspect-frontmatter]: https://github.com/quarto-dev/quarto-cli/blob/87d1078002991c4ba9cc2baa1f401a80402148af/src/project/project-shared.ts#L447
[inspect-json]: https://github.com/quarto-dev/quarto-cli/blob/87d1078002991c4ba9cc2baa1f401a80402148af/src/inspect/inspect-types.ts#L24

## Наследование привязка и ранние проверки

1. Native Quarto объединяет отображения и массивы по своим правилам, включая concatenation/deduplication и исключения.
   Утверждения «любой список заменяется» или «`[]` очищает наследованное» из прежнего эскиза не сохраняются.
   `false`/`null` и maps с true/false не являются универсальным reset. Нужны точные типы и примеры отмены наследования, если она вообще потребуется.
2. Конкретные `testsets` и выбор их dependencies остаются в локальном входе; общие уровни определяют компоненты и технологические политики.
   Это предотвращает неявное включение helper-библиотек всех задач. Поддержка отмены случайно унаследованного списка не подменяется пустым map.
3. Происхождение каждого значения сохраняется для диагностики; базу пути определяет **тип поля**, а не сам факт наследования.
   `components.*.sources` разрешается относительно файла/owner общего определения.
   `starter`, локальные пути `testsets.*.sources/resources`, `solutions.*` и пути ресурсов в `delivery` — относительно выбранного кодового проекта, даже при наследовании правила.
   Поэтому `starter: starter` из `projects/_metadata.yml` означает `projects/arrays/reverse-array/starter`, а не `projects/starter`.
   Принято это разграничение баз путей, а точные поля остаются эскизом. Quarto может перебазировать существующие строки; glob, отсутствующий путь и символическое имя ведут себя иначе.
   Мост обязан сверить raw/effective и origin, включая одноимённый каталог у родительского определения; уже перебазированную строку нельзя вслепую трактовать как project-relative.
   Корректность этого маршрута ещё не доказана; новый merge/resolver этим разграничением не вводится.
4. Один служебный блок относится к непосредственному Div, после первого Header. Дубли attrs/YAML и повторные YAML-ключи предлагается отклонять.
   Обычный учебный YAML без маркера не интерпретируется; include сохраняет владельца и происхождение ошибки.
5. Нормализация направляется в существующие модели/валидацию до раннего AST-reader и отдельно до materialization origins.
   Допуск нового локального origin, указатель на профиль/компонент и эффективная конфигурация — отдельные проверки одного маршрута, а не второй источник правды.
6. Для source→target предложены дедупликация одного источника и ошибка разных источников в один target.
   Точное формирование target и правила произвольных include/exclude ещё открыты; пакетные пути Java не теряются.
7. Проверить isolated owner/input context, project/directory/leaf precedence, профиль Quarto, конфликтующий скаляр и массив,
   provenance и путь при metadata rebasing; отдельно — demo closure и full с другими зависимостями, API entry,
   references/starter/discovery и три выдачи. Положительный разбор YAML не заменяет эту проверку.

Четыре course-записи — QMD, root configuration, directory defaults и local project input — описывают учебный выбор; настоящий Gradle build/settings остаётся отдельным native-каноном внешних build-зависимостей, не новой секцией YAML. Направление комплекта от 28 октября принято; точные имена/пути, схема, модульная структура и техническая реализация ещё требуют проработки и согласования.
Имя `_metadata.yml`, native наследование и разграничение баз путей приняты; точные поля, схема, мост и реализация остаются предложениями.
