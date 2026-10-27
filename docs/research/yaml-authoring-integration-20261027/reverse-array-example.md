# Цельный пример reverse-array для согласования

Пользователь принял один типизированный вход, совместимые короткие атрибуты и YAML для сложных деклараций,
а затем — один входной YAML проекта с отдельными файлами тестов, данных и решений рядом.
Последнее уточнение: **общие настройки наследуются в стиле Quarto; зависимости выбирает конкретный проект/набор тестов**.
Пользователь принял имя `_metadata.yml` для единственного локального входа вместо `project.yml` и разграничение баз путей с сохранением provenance.
Точные ключи, схемы, технический мост, обработка native rebasing и реализация ещё не утверждены.
Уточнение от 28 октября: выбраны обычный Java/JUnit, короткое пояснение и подключаемый исходник; fixtures/параметризация — по необходимости.
Новые карточки сценариев и сценарный DSL исключены. Эскиз раскладки и метаданных от 27 октября сохраняется ниже; его точные поля и привязки ещё не утверждены.
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

## Предлагаемое дерево

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
Данные `normal`/`boundary` остаются в `PublicChecks.java`; новые `cases.yml` или тестовые сценарии не вводятся.
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
`junit-jupiter` и `platform-api` — разные внешние зависимости; точное сопоставление закреплённым артефактам toolchain ещё открыто.
Самостоятельный авторский IDE-комплект с закреплённым JUnit JAR в `lib` и инструкцией — предлагаемая цель; JDK остаётся требуемым установленным инструментом.
Нынешний Download [по умолчанию исключает `**/*.jar`](../../../tasks/_extensions/Afonenko-Course-Tools/project-download/domain/config.ts#L7), поэтому автоматически такой архив не обещан.
Нужно проверить явный отбор этой зависимости toolchain существующим механизмом, без второго архиватора и общего разрешения любых JAR.
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
`show-demo: source` обозначает показ канонического исходника; новые карточки сняты с рассмотрения.
Обычный Java/JUnit с коротким пояснением и подключаемым исходником — принятая база без descriptor для показа кода.
Существующий протокол результатов PL [описан как факт для аудита](public-tests.md), а не целевая модель новых карточек; этот CLI-эскиз не навязывает YAML-формат другим тестам.
Точный способ включения и выбора фрагментов для PL ещё нужно доказать.

`alternative` удаляется из записи, если второго реального решения нет; отсутствие объявленного каталога — ошибка.
Каждый вариант компилируется отдельно вместо стартера и проходит тот же полный набор; одноимённые `Main` не объединяются.
Именованная verification использует существующий механизм после нормализации: reference all-pass, стартер компилируется,
но завершает проверку поведенческим провалом и оценкой менее единицы. Сбой инфраструктуры не становится ожидаемым провалом стартера.
Новая платформа для этого не создаётся; поля, адаптация входа и команда ещё не реализованы.

У этой задачи уже есть `starter/src/Main.java`. Основной файл сдаётся как `Main.java`, при необходимости дополняется `additional-sources.zip`.
Разрешённые вспомогательные классы, файлы и пакеты сохраняются; фиксирован только опубликованный `Main.main(String[] args)`.
`package` в стартер не добавляется; пакет `coursesupport` общей инфраструктуры сохраняется.

## Состав трёх выдач

| Выдача | Состав и запуск | Граница |
| --- | --- | --- |
| PL | Полное условие; пример из `starter/src/Main.java`; основной `Main.java` и при необходимости `additional-sources.zip`; явно выбранный показ demo из канона | Встроенный IDE не обязателен; полный набор тестов и решения не показываются как студенческая выдача |
| Pages student ZIP | README, `src/Main.java`, явно выбранный demo и все его транзитивные зависимости | Без `pages.testsets: [demo]` тесты по умолчанию отсутствуют; full, решения и PL precheck исключены |
| Авторский IDE full | README, стартер, оба набора с фактическими ресурсами/зависимостями, закреплённый JUnit, варианты решений с раздельными конфигурациями | PL precheck — отдельный tooling-вход с Platform API, не часть обычной JUnit-компиляции |

Принято: README каждого самостоятельного ZIP/IDE-комплекта содержит полное условие из канонического QMD без ручного дублирования.
Преобразование должно сохранить ссылки, ресурсы и нужное оформление; точный способ ещё проверяется, сырое копирование QMD в README не обещано.

Минимальное замыкание demo — ровно четыре исходника: `Main.java`, `PublicChecks.java`, `coursesupport/JavaProcess.java`, `coursesupport/OutputNumbers.java`.
JUnit и Platform API ему не нужны. Выбранный demo без нужной транзитивной зависимости — ошибка подготовки, а не неполный самостоятельный архив.
Состав полного IDE-комплекта окончательно фиксируется после проверки реальных full sources и инструментов.
Общий Git доступен для исходников, но не определяет состав студенческих ZIP. Миграционная политика прежних результатов не меняется.

## Где заканчивается native Quarto

Штатное [наследование Quarto](https://quarto.org/docs/projects/quarto-projects.html#shared-metadata)
объединяет `_quarto.yml`, directory `_metadata.yml` и метаданные **входного документа**; это не автоматический обработчик папки Java.
Предлагаемая цепочка для выбранного входа в каталоге задачи:
`tasks/_quarto.yml → projects/_metadata.yml → arrays/_metadata.yml → reverse-array/_metadata.yml`.
Тематический уровень необязателен. `_metadata.yml` не создаёт вложенный Quarto-проект.

В pin 1.11.5 directory inspect возвращает owner config/inventory, а не effective metadata произвольного каталога.
Java/YAML не являются Markdown input. README исключён из стандартного input inventory текущего book-owner:
его прямое чтение может уйти в single-file context и потерять родительское наследование.
Путь к условию `bank/arrays.qmd` также не даёт метаданные соседнего `projects/arrays`.

Технический кандидат — изолированный выбранный служебный input в правильном owner и каталоге задачи с native inspect и картой происхождения.
Автор не сопровождает дополнительную QMD-страницу; временный input не становится второй редакцией задачи.
Input в `/tmp` вне owner не решает задачу. Конкретный мост и его побочные действия ещё требуют ограниченной проверки; собственного resolver не выбираем.
В Core уже есть [metadata closure](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/infrastructure/metadata-closure.ts#L27)
и [owned overlays](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/infrastructure/owned-config-overlay.ts#L35),
но их нынешние listed-input/top-level-origin ограничения требуют адаптации, а не только нового имени YAML.

Официальный интерфейс — [inspect](https://quarto.org/docs/cli/inspect.html); статически проверена установка 1.11.5 (`/home/tolya/course-tools/local-tools/quarto-1.11.5/bin/quarto.js:164770`).
В её `bin/quarto.js`: `inspectConfig` 164770, `projectContext` 92317–92320,
`directoryMetadataForInputFile` 76984, README exclusion 92671, `singleFileProjectContext` 142673.
Доступный локально 1.11.6 не подменяет закреплённый 1.11.5. Здесь команды inspect/render не запускались.

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

Сейчас согласуется авторский вид четырёх записей: QMD, root configuration, directory defaults и local project input.
Имя `_metadata.yml`, native наследование и разграничение баз путей приняты; точные поля, схема, мост и реализация остаются предложениями.
