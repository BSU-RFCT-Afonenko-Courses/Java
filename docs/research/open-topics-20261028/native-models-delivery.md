Подготовлено агентом. Пользователь не просматривал; выводы, варианты и рекомендации НЕ ПРИНЯТЫ

# Native Quarto, модели, условие и состав самостоятельной выдачи

Исследование 28 октября 2026 года, пакет A. Исходный Java HEAD — `dd1a8a392c570e96a7d0a7e364aee345c800424a`. Изучены установленные Core 8.3.0 (`3ccc9e81eb2fc297cd0f283a51e040f71572ce18`), PrairieLearn 8.3.0 (`11a4fd3d877c7c9c6124a2d05beb0a8c8c2833b5`), Download 3.2.1 (`498b1d89c159548c0eb1d706438178be16ddb082`) и Quarto 1.11.5 (`87d1078002991c4ba9cc2baa1f401a80402148af`). Выбранные локальные исходники сверены с хешами `installed-packages.json`; это проверка происхождения, не исполнения продукта. Ссылки ниже ведут на установленный код и закреплённый upstream.

Действующие решения [текущего плана](../../refactoring-plan.md#уже-принятые-решения-и-сохраняемые-требования), включая пункты 31–33, уже приняты пользователем: один канон условия, один `_metadata.yml` как локальный YAML, native inheritance, разные базы локальных и общих путей, один student-стартер задачи для сайта и PL, независимый состав сдачи, необязательная обычная JUnit/Gradle-самопроверка. Настоящие общие build/settings материализуются в самостоятельную выдачу; внешними зависимостями управляет Gradle. Здесь эти решения не пересогласовываются. Имена новых полей, схема, адаптер метаданных и реализация не утверждены.

**Последующее решение от 29 октября:** [пункт 48](../../refactoring-plan.md#decision-48) принимает композицию README: полное каноническое QMD-условие после обработки include/ресурсов, одна общая Markdown-инструкция рядом с Java/Gradle-профилем, необязательные особенности из локального `README.md` и состав сдачи из контракта. При отсутствии особенностей локальный файл не нужен; полный авторский комплект отделён. Это не доказательство преобразователя или пересмотр результатов ниже: ссылки/anchors, ресурсы и происхождение значений ещё требуют адресной проверки.

## Главный вывод

После подготовки исследования пользователь подтвердил ранее выбранную магистраль строгой модели курса и валидации в расширении с native Quarto/Pandoc и TypeScript; сайт обязательно рендерит Quarto, остальные получатели используют нужные проекции/экспорт ([уточнение 34](../../refactoring-plan.md#уже-принятые-решения-и-сохраняемые-требования)). Следующие варианты относятся к внутренним границам и способу получения результатов, а не к повторному выбору этой основы. Полная готовность PrairieLearn/IDE/Print/Moodle/Cloud и один универсальный DTO из подтверждения не следуют.

Существующие средства дают основу для одного пути: native контекст → проверенная модель курса → выбранный файловый состав и публичное условие → отдельные представления для README, сайта и PL. Но готового универсального вызова для всех этих шагов сейчас нет. Новые опыты различили книжный контекст и обычный проект; конвертация в GFM отдельно показала потерю явно заданного якоря, отсутствие обработки include и отсутствие копирования изображений. Поэтому обещать готовый самостоятельный комплект только по наличию merged metadata и команды Pandoc нельзя.

Технический кандидат: переиспользовать управляемый Core временный native контекст выбора входов, общий план материализации и разрешённую публичную проекцию условия. Преобразование README должно получать уже разрешённый текст, явные правила ссылок и инвентарь ресурсов. Новый общий YAML merge, второй реестр внешних зависимостей и сборка состава отдельно в каждом потребителе не нужны. Это рекомендация исследования, а не выбранная реализация.

## Три малых доказательства

Новые проверки выполнялись только на публичных минимальных `/tmp`-фикстурах. Исполняемый файл — `/home/tolya/course-tools/local-tools/quarto-1.11.5/bin/quarto`; все три `inspect` вернули `quarto.version=1.11.5`, exit 0, пустой stderr. Default Quarto 1.11.6 не использовался. Перед вызовами изучены pinned `inspect` и получение проектного контекста: это не render; фикстуры не содержали hooks, фильтров, исполняемых блоков или сетевых обращений. Пять прежних default-owner опытов не повторялись.

### 1 Книжный owner и кодовая ветка

Фикстура `/tmp/native-models-book-txdqevwp/`: `_quarto.yml` задаёт `project.type: book`, главы `index.qmd`, `bank/arrays.qmd`, формат HTML, `execute.enabled: false`; корневые исследовательские metadata содержат `root: root`. У `bank` свой `_metadata.yml` со значением `bank: bank`, front matter QMD добавляет `document: bank`. В `projects`, `projects/arrays` и `projects/arrays/reverse-array` находятся последовательные `_metadata.yml`; лист содержит обычный `README.md` без front matter. Это синтетическая книга, не inspect рабочего Java.

Два профиля явно включают `index.qmd`, банковский QMD и README в `project.render`: `research-book` оставляет тип `book`; `research-code` меняет только тип на `default`. Команды с указанным выше executable:

```text
quarto inspect <root>/bank/arrays.qmd
quarto inspect <root>/projects/arrays/reverse-array/README.md --profile research-book
quarto inspect <root>/projects/arrays/reverse-array/README.md --profile research-code
```

| Вход | Наблюдённый `project.dir` / `project.config.project.type` | Эффективные исследовательские metadata в `formats.html.metadata` |
| --- | --- | --- |
| Банковский QMD | Исходный root / `book` | root + bank + document |
| README, профиль book | Каталог reverse-array / тип отсутствует | Только листовые task + label; входов проекта нет |
| README, профиль default | Исходный root / `default` | root + projects + arrays + reverse-array; README включён в `project.files.input` |

Причина по исходнику: book-конфиг формирует свой render list из глав, заменяя `project.render`. Подтверждение в установленном pinned bundle [quarto.js](/home/tolya/course-tools/local-tools/quarto-1.11.5/bin/quarto.js:153412); upstream — [book-config.ts закреплённого коммита](https://github.com/quarto-dev/quarto-cli/blob/87d1078002991c4ba9cc2baa1f401a80402148af/src/project/types/book/book-config.ts). Не включённый во входы файл получает отдельный контекст: [project-context.ts:397](https://github.com/quarto-dev/quarto-cli/blob/87d1078002991c4ba9cc2baa1f401a80402148af/src/project/project-context.ts#L397), [inspect.ts:155](https://github.com/quarto-dev/quarto-cli/blob/87d1078002991c4ba9cc2baa1f401a80402148af/src/inspect/inspect.ts#L155). Стандартное исключение README и явный отбор входов различаются: [project-context.ts:882](https://github.com/quarto-dev/quarto-cli/blob/87d1078002991c4ba9cc2baa1f401a80402148af/src/project/project-context.ts#L882).

**Доказано:** у банка и кодовой ветки разные native цепочки; обычное добавление README в render-list книги недостаточно. Изолированный выбор default-типа в этом малом опыте сохраняет физический root и directory inheritance без авторской QMD-страницы. **Не доказано:** жизненный цикл такого выбора для текущего Java book, восстановление его владельца/профилей и работа при отсутствии любого готового входного README. Отсутствие нового обязательного авторского файла остаётся целевым требованием, которое должен выполнить внутренний адаптер; текущий опыт не даёт готового адаптера.

### 2 Строка пути и происхождение значения

В том же опыте, без дополнительных вызовов, `projects/_metadata.yml` задаёт исследовательские значения:

```yaml
research:
  inheritedStarter: starter
  helperSource: support
  unresolvedGlob: support/**/*.java
  label: same
```

`projects/starter` и листовой `starter` существуют одновременно; `projects/support/Helper.java` существует. Лист повторяет `label: same`. У default-контекста получены `inheritedStarter: ../../starter`, `helperSource: ../../support`, но `unresolvedGlob: support/**/*.java` остался строкой без перебазирования. Это native обработка существующих относительных строк, а не знание типов course-путей: [project-shared.ts:138](https://github.com/quarto-dev/quarto-cli/blob/87d1078002991c4ba9cc2baa1f401a80402148af/src/project/project-shared.ts#L138), [directory merge:300](https://github.com/quarto-dev/quarto-cli/blob/87d1078002991c4ba9cc2baa1f401a80402148af/src/project/project-shared.ts#L300).

`project.files.config` перечислил `_quarto.yml` и профиль, но не всю цепочку directory metadata; `fileInformation[README].metadata` равен `{}`. Эффективный `label: same` не сообщает, какое из двух одинаковых объявлений было последним. [Тип inspect JSON](https://github.com/quarto-dev/quarto-cli/blob/87d1078002991c4ba9cc2baa1f401a80402148af/src/inspect/inspect-types.ts#L24) не обещает per-value origin. [Документация shared metadata](https://quarto.org/docs/projects/quarto-projects.html#shared-metadata) задаёт native inheritance, а [inspect](https://quarto.org/docs/cli/inspect.html) — сведения о проекте/входе, не трассу происхождения каждого скаляра.

Следствие: нельзя трактовать произвольную унаследованную строку как готовый типизированный путь. Для принятого договора нужны исходная декларация, её физический владелец и эффективное значение; общие sources считаются от определения компонента, локальные — от кодового проекта. Их нельзя получать повторным слепым rebase уже изменённой строки. Core уже сохраняет origins файлов/контекстов/хешей и зависимости, но это не per-leaf origin: [metadata-closure.ts:169](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/infrastructure/metadata-closure.ts#L169). Более того, нынешний guard общих деклараций специально ограничивает допустимые raw origins: [там же:137](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/infrastructure/metadata-closure.ts#L137). Новое directory-объявление не становится поддержанным только потому, что Quarto его слил.

### 3 Native JSON → GFM не создаёт готовую выдачу

Фикстура `/tmp/native-models-readme-nxddsn2i/` содержит этот публичный текст и существующие `assets/schema.svg`, `fragments/task.md`:

```markdown
# Условие {#piece}

[К этой части](#piece) и [материал](https://example.invalid/task#piece).

![Схема](assets/schema.svg#view)

{{< include fragments/task.md >}}
```

Команды `quarto pandoc --from markdown --to json` и затем `quarto pandoc --from json --to gfm --resource-path <root> --output <root>/out/README.md` читали stdin; оба exit 0. JSON API — `[1,23,1,2]`. Writer сохранил ссылку `#piece`, но вывел заголовок `# Условие` без заданного ID; изображение стало HTML figure с прежним URL, файл в `out/assets` не появился; include остался экранированным буквальным shortcode, текст фрагмента отсутствует. SHA256 README — `1f547e8fd6f6f7857d5431063fa943b4197a36cc694803afd71eea139de5b67e`.

Это доказательство поведения обычного writer, не Quarto render и не тест нынешнего Body export. Оно показывает, почему нельзя объявлять conversion lossless или считать `--resource-path` упаковщиком: [Pandoc о resource-path](https://pandoc.org/MANUAL.html#option--resource-path). Include должен разрешаться native стадией раньше; [Quarto включает фрагмент как текст главного документа и считает его относительные ссылки от главного входа](https://quarto.org/docs/authoring/includes.html). Это третья предметная база путей, отличная от definition-origin общего Java helper. Поддержка GFM как формата не устраняет этих различий.

Полные входы, команды и JSON результатов сохранены в двух названных временных каталогах (`summary.json`, stdout/stderr), воспроизводящие одноразовые скрипты — `/tmp/native-models-book-proof.py` и `/tmp/native-models-readme-proof.py`. `/tmp` не является долговечным API или источником курса; существенные входы и наблюдения приведены выше. Никаких выводов о производительности из опытов нет.

## Что уже производят и потребляют существующие контракты

| Граница | Уже существует | Что нельзя приписать ей сейчас |
| --- | --- | --- |
| Native → модель | `DocumentResult` несёт публичные тела, ресурсы и native source/output; `ProjectFact` — projectRoot, check, audience/materialization. [model.ts:36–60](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/domain/model.ts#L36) | Отдельная новая модель курса для YAML не нужна; именованные компоненты и testset graph ещё не реализованы |
| Native JSON → Body | Collector читает разрешённый native writer JSON после shortcodes, отдельно полный AST и публичную проекцию. [collect.ts:134](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/body-export/collect.ts#L134) | Ранний AST capture и исходный QMD не эквивалентны готовому тексту README |
| Body → потребитель | `BodyPackage` переносит AST, API version, owner/release, ресурсы с effectiveBase/hash/bytes. Public-пакет исключает closedKey, solution, gradingNotes. [model.ts:22](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/body-export/model.ts#L22) | Это не произвольный доверенный JSON: тип узла сейчас `c?: any`, валидность ограничивает producer/runtime-схема. PublicAnswer остаётся частью публичного контракта |
| План → файлы | Материализатор связывает текущие ProjectFacts, локальный student и разрешённые common files; выдаёт sorted inventory, hashes, origins, declaration hash. [materialize.ts:132](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/artifacts/materialize.ts#L132) | Сейчас common — явные отдельные файлы; обход student не равен будущему явному выбору demo. Каталоги, requires и рекурсивный выбор компонентов — новый нормализующий вход |
| Файлы → PL | PL читает готовый materialization receipt и проверяет пути/хеши/публичное происхождение. [materialized-student.ts:4](../../../tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/application/materialized-student.ts#L4) | Повторно собирать helpers и teacher-части в PL не требуется. Совместимость исполнения PL с будущим Gradle/JUnit из этого не следует |
| Файлы → ZIP | Download фиксирует собственные байты и receipt внутри Core callback, публикует после него. [runtime.ts:261](../../../_extensions/Afonenko-Course-Tools/project-download/infrastructure/runtime.ts#L261) | ZIP writer не определяет учебный состав; опубликованный архив не должен зависеть от живой ссылки в соседний checkout |

У Core уже есть управляемый selected-input default-контекст с временным профилем и native inventory: [collect.ts:49](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/body-export/collect.ts#L49). Его следует исследовать как общий механизм, а не писать второй merge. Но этот collector запускает native JSON render с собственными фильтрами: это не готовый безопасный вызов `inspect` кодовой папки. В текущем исследовании он не исполнялся.

**Время жизни доказательства — часть контракта.** `withLoadedNativeRun` регистрирует активный run только на callback; обращение позднее требует нового `withNativeRunProof`, а чтение site URL и публикация требуют активного контекста: [native-run.ts:88–122](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/infrastructure/native-run.ts#L88). Batch дополнительно запрещает reentrant, истёкшие и незавершённые запросы: [resolve.ts:46](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/artifacts/resolve.ts#L46). Поэтому хранить объект в новом «общем DTO» и считать его навсегда разрешённым нельзя. Кандидат границы: материализовать проверенные байты/манифест в существующем proof-контексте, наружу передавать принадлежащий результату снимок и происхождение. Конкретная новая сигнатура здесь не выбирается.

## Полное условие README: два реальных пути

**Кандидат A — публичный AST с отдельным writer для README.** Переиспользовать выбор публичного упражнения, разрешение native include/shortcodes, ссылок и библиографии из Body; после этого преобразовать в GFM с явной обработкой якорей и ресурсов. Это сохраняет семантику условия раньше, чем оно стало страницей сайта. Однако нынешний `buildBodies` выбирает работу и проверяет её members: [producer.ts:96–138](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/body-export/producer.ts#L96). Для одного starter нельзя молча выдумывать assessment; потребуется выделить переиспользуемую границу публичного условия. Это изменение Core, а не копия producer в Download. Рекомендую A как основной кандидат, потому что он позволяет нескольким представлениям использовать одну публичную семантическую проекцию.

Уже имеются полезные части: исключение solution/private-контекста и download-ссылок, native разрешение ссылок и ограниченного публичного bibliography scope, перенос ресурсов с hash/bytes — [producer.ts:36](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/body-export/producer.ts#L36), [links.ts:24](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/body-export/links.ts#L24), [references.ts:39](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/body-export/references.ts#L39), [producer.ts:287](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/body-export/producer.ts#L287). Набор ресурсов нужно сузить до фактически выбранного условия/публичного дополнения, а не переносить все ресурсы работы. Существующий [evaluateResources](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/infrastructure/resources.ts#L218) различает raw/public uses, проверяет наблюдённые файлы и запрещает source/private partitions; это подходящая общая граница проверки ресурсов. [Native capture](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/native-resources.lua#L33) фиксирует effectiveBase и хеши, пока сгенерированные файлы ещё находятся у входа. Копирование по одному URL после завершения native стадии может потерять этот контекст и не заменяет проверку происхождения.

**Вариант B — уже разрешённый HTML → AST → README.** Нынешний artifact `conditions` читает текущий native HTML, выбирает exercise/preparation, связывает ресурсы и ссылки, создаёт `index.html` и README-инструкцию «откройте index.html»: [resolve.ts:77–119](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/artifacts/resolve.ts#L77). Это осязаемый путь повторного использования готовой страницы, когда она уже есть. Цена — семантический round trip через HTML и ограничение текущего контракта HTML output; он отклоняет другой формат. Его нынешний README **не содержит полного условия**. GFM после этого пути также требует проверки якорей, таблиц, формул и native элементов. B годится как сравниваемый путь для готового HTML, но не как доказательство готовности всех комплектов.

PL использует своё пространство якорей и привязки ресурсов к clientFiles: [export.ts:161](../../../tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/application/export.ts#L161). Это политика представления PL; её нельзя целиком перенести в автономный README. Общими должны быть публичное тело/владение и проверка исходных ресурсов, а конечные URL принадлежат конкретному представлению.

## Состав компонентов и внешние зависимости

Объявленное course-замыкание отвечает на вопрос «какие наши sources, data, helpers, tests и build-файлы нужны выбранному комплекту». Gradle отвечает на другой вопрос: «какие внешние модули и scopes нужны настоящей Java-сборке». Выбор demo не выводится из imports, а external dependency не делает отсутствующий course helper доступным после распаковки. Это уже принято пунктом 33; [стандартные механизмы Gradle](https://docs.gradle.org/current/userguide/declaring_dependencies.html) не заменяют course inventory.

Рекомендованный кандидат: один раз разрешать выбранное объявленное course-замыкание до существующего плана файлов, где уже проверяются target collisions, audience, source identity и хеши; затем Pages/PL получают ту же student-проекцию. Не создавать второй file walker в delivery. Нынешний materializer отклоняет повторный target даже до переноса байтов и ограничивает teacher overlay отдельными partitions: [materialize.ts:142–170](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/artifacts/materialize.ts#L142). Будущие unknown-reference, cycle и directory expansion checks должны дополнять эту границу, а не ослаблять её.

Для самостоятельной выдачи исходные helpers можно включать в локальный test source root либо оформлять локальными Gradle-проектами. Первый путь проще для небольшого reverse-array; второй сохраняет самостоятельную модульную границу при многократном сложном использовании, но добавляет settings/project wiring. Это внутренние технические кандидаты без нового авторского выбора для каждой задачи. В обоих случаях настоящий общий Gradle-код копируется из одного канона, версии не повторяются в YAML; особый проект сохраняет native build. Доступный внешний опубликованный helper возможен только при установленном реальном контракте публикации, а не как воображаемая замена локального файла.

## Один авторский сценарий и ошибка с происхождением

Возьмём [материализованный reverse-array](../yaml-authoring-integration-20261027/_examples/reverse-array/README.md). Автор изменяет полное условие в `bank/arrays.qmd`, локально выбирает demo и необходимые helpers; общие определения и настоящий Gradle-код остаются у своих владельцев. Предложенные редакции A/B — два способа кратко объявить один состав, не два одновременно активных YAML и не утверждённый API.

| Источник решения о включении | Эффективная student-выдача | База/ограничение |
| --- | --- | --- |
| Стартер проекта | `src/main/java/Main.java` | Кодовый проект; имя сдаваемого файла отдельно |
| Явно выбранный demo | `src/test/java` с JUnit-тестом и переходным PublicChecks | Локальные sources проекта; не все teacher tests |
| Выбранные JavaProcess/OutputNumbers | Нужные test sources | Физическое определение общего helper, не соседний checkout при запуске |
| Общий native Gradle-профиль | `build.gradle`, `settings.gradle` | Один реальный build-канон, не синтез Groovy из YAML |
| Публичная проекция канонического QMD | Полное условие в README и используемые assets | Ресурсные ссылки native главного входа, затем target конкретной выдачи |

Для этого примера обычная JUnit-обёртка пока механически вызывает прежнюю observe/assert-логику; это не окончательный общий runner. Историческое авторское решение в author-контексте не является выбранным полным grader. [Missing-full manifest](../yaml-authoring-integration-20261027/_examples/reverse-array/missing-full-input.yml) сохраняет отсутствие точного полного teacher input; новый resolver не может восстановить недоступные тесты по имени набора.

Если автор ошибся именем helper, полезная ошибка должна показать файл объявления и выбранное имя, затем место поиска определения. Если два компонента дают один `src/main/java/Main.java`, нужны оба источника и конфликтующий target до записи файлов. [Статические negative fixtures](../yaml-authoring-integration-20261027/_examples/reverse-array/negative/expected-diagnostics.yml) иллюстрируют это, но не являются выводом действующего валидатора. В текущем `DiagnosticContext` есть source/id/field/related/hint, однако нет line/range: [diagnostics.ts:1](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/domain/diagnostics.ts#L1). Точность до строки требует отдельной карты разбора; её нельзя обещать из одного merged object или списка конфигурационных файлов.

## Что ещё доказать адресно

1. Управляемый выбор кодового input в реальном Java book: сохранить native владельца/профили, не требовать дополнительного обязательного авторского файла, восстановить временные изменения и отвергать drift. Малый BOOK-опыт проверил только различие контекстов.
2. Связать raw declaration, effective metadata и origin для принятых двух баз путей, включая одинаковые значения в двух ancestors, существующий одноимённый путь и glob. Переиспользовать native extraction/metadata closure; не воспроизводить весь merge своим кодом.
3. На выбранном одном реальном условии сопоставить A/B пути README: include, внутренний ID, междокументная ссылка, изображение и сгенерированный ресурс, таблица/формула/цитата при их фактическом наличии. Проверять содержание и распаковку assets, не только exit writer.
4. После реализации нормализующего входа проверить неизвестный helper, цикл, конфликт targets, teacher leak и отсутствующий полный источник. Один inventory должен объяснять source → target → hash → причина включения; Pages/PL не пересобирают его самостоятельно.
5. Раздельно принять обычный Gradle/JUnit запуск после setup и нынешние PL API/scoring/observations. Проверка файлового состава не доказывает эквивалентность grader. Повторное использование run после callback и изменение sources должны давать существующий отказ доказательства, а не устаревший ZIP.

## Вопросы для обсуждения в чате

- Полное условие остаётся в README; допустим ли рядом HTML-файл для native элементов, которые Markdown заметно искажает, или для такого условия комплект должен отклоняться с понятной причиной?
- Какой краткий обзор состава удобен автору перед выдачей: выбранные наборы и причины включения файлов либо сразу полный список файлов с источниками? Технический inventory в обоих случаях один.
- Достаточно ли для ошибки сначала показывать файл объявления и связанный компонент, если точная строка ещё не доказана, при обязательном отказе от создания неполного комплекта?

Выбор метода native inheritance, конкретного внутреннего input, схемы и сигнатур остаётся технической задачей владельцев. Эти вопросы касаются обещаний и представления результата, а не переноса внутреннего API на пользователя.

## Наблюдение для следующего обсуждения: наследование выбора тестов, 29 октября

Первоначальные варианты ниже рассмотрены после решения 45; последующий выбор формы зафиксирован в [решении 46](../../refactoring-plan.md#decision-46). По адресному чтению Quarto 1.11.5 (`quarto.js`:18897–18923, `mergeArrayCustomizer`; :76984–77018, `directoryMetadata`) массивы объединяются: родительский `[demo]` и дочерний `[extra]` дают `[demo, extra]`; дочерние `[]`, `null` или `false` не удаляют родительский массив. Это статический вывод по коду, не новый прогон; штатное объединение массивов описано в [документации Quarto](https://quarto.org/docs/projects/quarto-projects.html#metadata-merging).

Пользователь принял именованную булеву карту именно для наследуемого выбора в выдачу: в иллюстративной записи `testsets: {demo: true}` локальное `{demo: false, extra: true}` снимает прямой выбор demo и выбирает другой объявленный набор через штатное переопределение скаляров Quarto. Альтернатива с выбором только в каждой задаче более не является текущей рекомендацией. Точные ключи схемы не утверждены; булева карта не распространяется автоматически на определения, `requires`/`includes` и зависимости сборки. Полный оценочный набор отделён от студенческого выбора. По последующему [решению 47](../../refactoring-plan.md#decision-47), если набор с прямым `false` входит через другой выбранный набор, это явная ошибка с цепочкой выбранный → включаемый набор. Подобные несовместимые требования эффективной конфигурации не разрешаются молча. Обычное родительское `true` → локальное `false` допустимо; определение набора не удаляется, внешними зависимостями Gradle булева карта не управляет. Объявленные зависимости выбранных тестов обязательны; точные ключи и диагностика ещё технически открыты.
