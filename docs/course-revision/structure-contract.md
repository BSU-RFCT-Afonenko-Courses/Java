# Договор структуры редакции Java 25

Дата: 2026-10-13. Решение U00, dispatch `U00-01`, автор `/root/u00`.
Входной commit: `e71a7cf2cb34e15e62654b289dd7a22cdf690708`; предметный baseline — свежий remote PR13 `a0d1bdb9d8343809560e3ce7f9a2141d4473ee3f`. Работа ведётся в `/tmp/java-course-pr13-20261013`. Назначение — `gpt-6-astra / ultra`; фактические model/effort интерфейсом не сообщены (`not_reported`). Наблюдаемого отказа профиля нет; это не подтверждение фактических настроек.

Этот договор — источник истины для структуры, маршрута, идентификаторов, меток, общих примеров и распределения авторства. Пользователь уже разрешил исполнение. Его требование новых ID и тегов без совместимости имеет приоритет над историческими рекомендациями сохранять ID. Приложения ниже — внутреннее сопоставление исходных фрагментов с новой редакцией для авторов; оно не подключается к сайту, экспорту или работающему серверу. Старые ID, aliases, redirect и миграционная инфраструктура в переработанных материалах запрещены.

## 1. Provenance, объём и границы

M00 подтвердил 23 главы теории в шести частях, 25 справочных статей, 13 вводных практик, девять исходных лекций, десять основных и три резервные лабораторные. Проверенный inventory — 619 Java fences и 876 обычных QRC-ссылок в 118 предметных QMD; ссылки-шорткоды и корневой портал дополнительно учтены приложением потребителей U00. Текстовый список M00 содержал 1288 ID-вхождений; непосредственный Pandoc AST U00 проверил 1265 `sec`/`exr`/`alt` определений в 119 QMD (с порталом), без повторов внутри файла. Повторяющиеся служебные `refs` не являются предметными идентификаторами.

Первая поставка PrairieLearn сохраняет 17 вопросов и 14 обязательных позиций: 6/4/4 при 7/5/5 кандидатах. В трёх группах `selection="random"` вариант назначается при создании экземпляра и сохраняется при повторной отправке. Условие, допустимые числовые протоколы, допуски, ручная проверка исходника и разделение public starter/public examples от reference/hidden tests сохраняются. Новая сборка переименовывает идентификаторы по этому договору; работающие назначения и серверы не меняются.

Библиографические ключи `bib-*`, стандартные классы разметки (`task-items`, `task-alternatives`, `notes`), значения `difficulty`, `target`, `project-check`, runtime profiles, Java package `grade` и пять QRC namespaces `theory`, `lectures`, `handbook`, `practice`, `tasks` — протоколы или имена предметного API, а не старые идентификаторы переработанных тем. Они сохраняются. Физические каталоги 17 проверочных проектов также сохраняются; идентификатор упражнения в их текущих ссылках обновляется.

`tasks/essays/**`, `tasks/bank/projects.qmd` и их библиография не получают авторской переработки. Их текущие ссылки на изменённые цели обновляет M03. Собственные ID этих непереработанных материалов сохраняются. Резервные практики/подборки добровольны; System Practice остаётся отдельным курсом. Vendor, toolchain pins, graders, публикация, push/merge, установки и глобальные настройки вне этой работы.

## 2. Бюджеты и критерий объёма

| Маршрут | Baseline | Новая редакция | Статус |
|---|---:|---:|---|
| Java-лекции | 9 × 80 = 720 минут | 11 × 80 = 880 минут | Выбранная структура U00, оценки для репетиции U07 |
| Предел роста теории | 720 × 1,30 | 936 минут | 880/720 = 1,2222; рост 22,22%; запас 56 минут |
| Основные лабораторные | 8 × 180 + 270 + 225 = 1935 | 1935 минут | Состав заменяется внутри бюджета; это ориентиры, не pilot |
| Вводные практики | 10 × 30 = 300 | 300 минут | Считаются отдельно от 1935 и 880 |
| Резерв | 3 добровольных маршрута | 3 добровольных маршрута | Не входит в обязательный знаменатель |
| System Practice | Отдельный курс | Отдельная системная подготовка | Не скрывает дополнительную Java-нагрузку |

Слоты 3, 5, 11 обязательно проходят сценарную репетицию U07. При нехватке времени сокращаются повтор и каталог подробностей; необходимое первое объяснение не становится дополнительным обязательным домашним чтением. Реального студенческого pilot в U00 нет. Вопрос переноса включён в бюджет занятия/существующую защиту, а не добавляет новое оцениваемое задание.

Текст сравнивается с неизменным PR13: отдельно 23 `theory/chapters/*.qmd` и девять исходных лекций против 11 новых. Единица измерения — читательская лексическая единица Pandoc: каждое `Str` и каждое непустое разделённое пробелом слово inline `Code`; обход только `blocks`, исключены `Div.notes`, `CodeBlock`, `RawBlock`, библиография `refs`, Math и метаданные. Заголовки и видимые подписи учитываются; optional-проза также учитывается. Дополнительно записываются UTF-8 bytes, строки и число fenced blocks. Это воспроизводимый индикатор объёма, не доказательство понимания или времени. M05 повторяет тот же расчёт; рост прозы теории сверх 30% требует сокращения повторов или явного возврата U00, а не переноса текста в optional ради обхода измерения. Код и схемы сравниваются отдельно по задаче и читаемости. Числовой baseline приведён в приложении объёма.

## 3. Новые ID и теги

Точные определения и все обнаруженные потребители перечислены ниже. Правила создания новых целей:

- Теория: `sec-j25-theory-<topic>[-<concept>]`; справочник: `sec-j25-api-<topic>[-<operation>]`.
- Лекции: `sec-j25-lecture-<semantic-file-stem>[-<concept>]`; практика: `sec-j25-practice-<lab-topic>[-<step>]`.
- Лабораторные: `sec-j25-lab-<topic>`; банк: `sec-j25-bank-<topic>`; задания: `exr-j25-<bank-topic>-<meaning>`; случайные группы: `alt-j25-<topic>-<meaning>`.
- Индексы: `sec-j25-course`, `sec-j25-course-route`, `sec-j25-theory`, `sec-j25-lectures`, `sec-j25-handbook`, `sec-j25-practice`, `sec-j25-tasks`, `sec-j25-labs`, `sec-j25-bank`.
- Каждая новая цель является содержательным Header/Div/Span штатной модели; старое имя не оставляется скрытым span. Объединение двух старых фрагментов приводит к одной новой содержательной цели, а все ссылки ведут туда.

Свежие теги хранятся штатно в YAML `categories`, например `categories: [j25, j25-core, j25-generics]`. Первые два значения задают редакцию и маршрут, третье — тему. Индексы получают `[j25, j25-route]`, резерв — `[j25, j25-optional, j25-reserve-…]`. Полный per-file список ниже. Карточки банка наследуют теги своей `sourceTopic`/страницы; отдельного неподдержанного `tags=` у exercise не вводить. Для сохранённых в банке заданий, снятых с обязательного маршрута, отсутствие обязательной ссылки в lab и видимая пометка «дополнительная тренировка» задают статус; `j25-core` страницы не означает обязательность каждой её карточки. Нативное основание: `course-core/filter.lua:71–82`, `TopicFact.categories` в `spec/core.cue:134–137`. Внешний PL topic `Java` остаётся настройкой доставки, а содержательная новая классификация — `categories`.

## 4. Предметный порядок и владельцы

Шесть частей и существующие 23 пути теории сохраняются. Календарь не становится оглавлением книги. Линейное чтение generics заморожено без возврата в ещё не прочитанную главу: `type-parameters.qmd` — ObjectBox/Box → generic method → смысл compareTo и простая строгая граница `Comparable<T>` → короткое введение List<T> → Arithmetic<T>/dot без wildcard → C++/Python сравнение; затем `type-relations.qmd` — invariance/array covariance → ?/extends/super → PECS → гибкая граница `Comparable<? super T>` и target inference → erasure/bridge. В первой главе запрещено использовать `? super`, PECS или flexible bound раньше локального определения: их полный пример размещается только во второй. Устный маршрут вправе сначала пройти wildcard в слоте 6 и вернуться к dot в слоте 7: первичное введение для самостоятельной книги от этого не меняется. Названия глав и частные переносы не меняют единственного владельца файла.

Таблица файлов ниже назначает единственного **автора**, включая точную цель и categories. M01/M03 — последовательные технические исполнители принятых пакетов, а не вторые одновременно пишущие авторы. Перед передачей автор завершает файл, перечисляет остаточные запросы и прекращает его редактировать до возврата. M01 не создаёт пустые лекции для получения зелёной сборки; утверждённый render-list можно применить до появления текстов U07 как промежуточное состояние. Полная сборка принимается только после материализации всех одиннадцати файлов.

Общие `_quarto.yml`, `_quarto-student.yml`, `_quarto-full.yml` корня и пяти проектов: автор порядка U00, единственный технический писатель M01 после передачи. Других изменений профилей/фильтров/resources/pins нет. `bibliography/**/*.bib` и `nocite`: авторские записи поставляют предметные U01–U07, единственный писатель общего каталога M01; M03 получает его после окончания M01 только для согласованных ссылочных исправлений. Никаких параллельных edits bibliography. `bibliography/numeric.csl` без изменений.

`lectures/assets/lesson.css` и схемы новой редакции: U07 задаёт сценарий/стиль, M02 материализует по принятому packet в последовательном окне; предметный текст схем generics задаёт U03. Остальные существующие assets сохраняются. `tasks/projects/gradeapp/**`: U05/U06 задают contract/границу предоставленного кода, M04 единственный писатель. Существующие `tasks/projects/<17 names>/**`: M04 только согласует текущие идентификаторы и проверяет неизменное поведение. `docs/prairielearn-labs.md`: автор поведения U06, механический писатель M04. Другие текущие документационные ссылки исправляет M03. Исторические отчёты/revision evidence не переписываются как текущий курс.

### Общие API и последовательные запросы

| Решение | Единственный автор файла | Точная цель/действие | Потребители |
|---|---|---|---|
| G05 generic Arrays.copyOf | U01 | `handbook/entries/arrays.qmd#sec-j25-api-arrays-copy-of`; добавить рядом overload `<T> T[] copyOf(T[] original, int newLength)`, runtime component type, расширение null и независимый внешний массив | U03 передаёт проверенный пример; `practice/reserve-models.qmd`, `tasks/bank/generics.qmd`, `handbook/entries/generic-classes.qmd` получают адресную ссылку через своих владельцев |
| G12 String.compareTo | U01 | `handbook/entries/strings.qmd#sec-j25-api-strings-compare-to`; `int compareTo(String anotherString)`, знак/UTF-16 лексикографический порядок/отличие от locale collation | U02/U04/U03 передают потребности; registry, comparison и introductory larger ссылаются на эту цель |
| G13 List minimum | U04 | `handbook/entries/lists.qmd#sec-j25-api-lists-basics`; единая таблица `size/get/add/set/isEmpty`, `ArrayList`/`List.of`, границы индекса и поддержка изменения | U03 удаляет общую таблицу из wildcards, сохраняет рядом локально объяснённый необходимый минимум и ссылку; обе старые API-цели сведены к одной новой |
| Checked minimum до L7 | U05 | `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions-early-checked` в начале главы: обычный/необычный возврат, `throw`, `throws`, `try/catch`, обязанность checked | U03 пишет самодостаточный соседний контейнерный пример в `practice/generics.qmd#sec-j25-practice-generics-checked`; U06 prerequisites L7 ведут только в минимум |
| Сигнатуры до PECS | U03 | `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-comparison-sign`; конкретные строки и знак до generic bound | Каноническая полная справка порядка U04: `handbook/entries/comparison.qmd`; simple comparison локально понятен без неё |

U01 реализует первые две опоры сразу по U00: просьба от ещё не запущенного U03 не задерживает его завершение. Если после предметной проверки требуется изменение, U03 направляет конкретный текст U01; файл получает только U01 в отдельном окне. U04 реализует List API в начале своей задачи; U03 не редактирует `lists.qmd` и не требует полного будущего раздела collections для L7. M01/M03 не сочиняют семантику недостающих API.

## 5. Понятия: введение, применение, перенос

Все строки таблицы — обязательные результаты ядра. «Объяснять» требует прогноза и видимой причины, но не отдельной домашней программы на другом языке. Внутренний пример обязан восстановить минимальные объявления до использования. Ни один optional-раздел не является prerequisite этих строк.

| Понятие / глубина | Первое введение (автор; точная цель) | Первое применение | Проверка переноса |
|---|---|---|---|
| Синтаксис, семантика, типовая и предметная ошибка; объяснять | U01 `theory/chapters/languages.qmd#sec-j25-theory-languages-levels` | `5/2` и дробное среднее журнала | `double average=5/2`: назвать место потери дроби |
| Язык/JDK/JVM/API, source/class, package/classpath, release; применять | U01 `theory/chapters/platform.qmd#sec-j25-theory-platform-components` | собрать и запустить `course.reading.Reading` | изменить source, запустить старый class; восстановить цепочку |
| Тип переменной/выражения, primitive/reference, var, копия значения; применять | U01 `theory/chapters/types.qmd#sec-j25-theory-types` | два числа и две ссылки на String; изменяемый массив только после следующего минимума | переобъявление типа недопустимо; в `sec-j25-theory-types-copying` после минимума mutation/rebinding различаются |
| Ранний массив: ссылка, объект, компоненты, создание, индекс/length; применять | U01 `theory/chapters/types.qmd#sec-j25-theory-types-array-minimum`, после primitive/reference и до initialization/copying/absence | `int[] pages={5,7}`, чтение/запись `pages[0]`, `pages.length`; затем `sec-j25-theory-types-copying` с двумя ссылками | пустой массив имеет length 0 и не имеет компонента 0; изменение общего компонента отличается от переназначения ссылки |
| Строковые аргументы launcher, проверка количества до индекса; применять | U01 `practice/numbers.qmd#sec-j25-practice-numbers-s5`, локальное повторение раннего минимума до первого Probe | `args.length` в s5, `args[0]` в `sec-j25-practice-numbers-input` после объяснения границы | `sec-j25-practice-numbers-argument-boundary`: запуск без слов не разрешает читать args[0]; guarded пример объясняет if/return на месте |
| Promotion, cast, overflow, double approximation, порядок вычисления; применять | U01 `theory/chapters/expressions.qmd#sec-j25-theory-expressions` | среднее/произведение, расширить операнд до операции | большой результат слева не исправляет переполнение справа |
| null/пустота/definite assignment/default fields, boxing/unboxing, final; применять | U01 `theory/chapters/types.qmd#sec-j25-theory-types-absence` | пустая подпись/отсутствующая подпись, безопасная проверка | отличить NPE распаковки от compile-fail неприсвоенной локальной |
| Анализ nullness Java, C# nullable analysis, JS TDZ/undefined, Zig optional/undefined; объяснять | U01 `theory/chapters/types.qmd#sec-j25-theory-types-nullness-comparison` | короткая видимая таблица: значение, binding, analyzer, runtime | объяснить, почему одинаковое слово «неинициализировано» не даёт одинакового результата |
| Python binding, typing и checker; объяснять | U01 `theory/chapters/types.qmd#sec-j25-theory-types-static-dynamic` | `pages: int`; допустимое число и checker-rejected строка | обычный Python может выполнить присваивание вопреки аннотации; checker отдельный |
| boolean/short circuit, if/switch, цикл, инвариант цикла; применять | U01 `theory/chapters/control.qmd#sec-j25-theory-control-structure` и `#sec-j25-theory-control-while`; prerequisite `sec-j25-theory-types-array-minimum` | сначала скалярный countdown; затем `sec-j25-theory-control-for` — сумма уже объяснённого int[] | ноль итераций и скалярная граница; затем `sec-j25-theory-control-for` — i < pages.length против <=; &&→& |
| Метод, параметр/результат, копирование аргумента, простой рекурсивный вызов; применять | U01 `theory/chapters/methods.qmd#sec-j25-theory-methods-call`, затем `#sec-j25-theory-methods-primitive-copy` | average с двумя числами; скалярный countdown 3→2→1→0 | изменение int-параметра не меняет переменную caller; затем `sec-j25-theory-methods-reference-copy` после types-array-minimum/copying сравнивает изменение компонента и переназначение ссылки |
| Обход/копирование массива, массив массивов, shallow clone; применять после раннего минимума | U01 `theory/chapters/arrays.qmd#sec-j25-theory-arrays-copying`, `#sec-j25-theory-arrays-nested`; модель/creation/bounds перед ними повторяют types-array-minimum | новый внешний массив и новая внешняя матрица с общими строками | `sec-j25-theory-arrays-row-copy` и L3: изменение строки видно через обе внешние копии; stable ties |
| String immutable, equals/compareTo, UTF-16 unit, bytes/encoding, regex boundaries; применять минимум | U01 `theory/chapters/text.qmd#sec-j25-theory-text-early-lab` | позиционная вставка и соседний разбор слова, сохраняющий разделители | L4: digit/tab/punctuation; не считать \b универсальным условием слова |
| Object/ссылка/alias/достижимость/GC, идентичность; применять | U02 `theory/chapters/objects.qmd#sec-j25-theory-objects` | один изменяемый Book через две ссылки | reassignment Book-параметра не заменяет caller reference |
| private/constructor/this/static/final/access, инвариант и отказ до изменения; применять | U02 `theory/chapters/classes.qmd#sec-j25-theory-classes` | ограниченное состояние и stack/queue соседний опыт | L5: отказ сохраняет вершину/состав; self-transfer не теряет элементы |
| Подтип/override/dispatch/overload/abstract, constructor order; применять | U02 `theory/chapters/inheritance.qmd#sec-j25-theory-inheritance` | общий Vehicle-клиент и демонстрация выбора метода | L6: новый subtype без ветви instanceof; full у фиксированной очереди против роста DynQueue |
| Интерфейс, номинальная роль, композиция/делегирование, default; применять | U02 `theory/chapters/interfaces.qmd#sec-j25-theory-interfaces` | Sender→Reminder с двумя реализациями | заменить получателя сообщений без изменения Reminder; частичный эффект не откатывается |
| record/enum/sealed, surface immutability; применять | U02 `theory/chapters/data-classes.qmd#sec-j25-theory-data-classes` | record grade.Student и отдельный immutable Point | разные массивы с одинаковыми числами не дают автоматически равные records |
| Reflection: экземпляр против Class/record metadata, локальная запись Class<?>; объяснять | U02 `theory/chapters/data-classes.qmd#sec-j25-theory-data-classes-reflection` | Student.class/isRecord/getRecordComponents | объяснить вывод id:String, name:String, score:int и роль int.class |
| Параметр/аргумент типа, Box, generic method/diamond, Number bound; применять | U03 `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters` | Box<Book>, identity, two-type pair | Box сохраняет alias; `Number` не разрешает `T+T` |
| Минимальный List API до wildcard; применять | U03 `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-list-basics` | ArrayList<Integer>: size/get/add, переменная List | распознать недопустимый индекс/неподдержанный add; адресная API-опора U04 |
| compareTo sign до bounds; применять | U03 `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-comparison-sign` | `"Ada".compareTo("Lin") < 0`, larger возвращает left при равенстве | знак не обещает только −1/0/1 |
| Invariance/array covariance/?, extends/super/PECS; применять | U03 `theory/chapters/type-relations.qmd#sec-j25-theory-type-relations-wildcards` | append Integer→Number | L7: разрешённое чтение, запрещённая запись, extends не immutable |
| Inference/target type/Comparable super; объяснять и применять | U03 `theory/chapters/type-relations.qmd#sec-j25-theory-type-relations-comparison-target` | ComparisonDemo.Person/Student: strict→Person, flexible→Student | strict Student-target и явный T=Student отвергаются; простой вызов не обязательно |
| Erasure/descriptor/Signature/raw/heap pollution/bridge/client cast; объяснять | U03 `theory/chapters/type-relations.qmd#sec-j25-theory-type-relations-erasure` | фактический javap Box и TextSource | показать cast у клиента отдельно от getter bridge; suppressed warning не чинит значение |
| Generic operations/dot/математические условия; применять схему, объяснять модели | U03 `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-operations` | Arithmetic<Double>: [1,2]·[3,4]→11 | zero=1→12, empty→1, unequal lengths failure; complex→11+i без conjugation |
| Type vs value parameters C++, C++ constraints, Python Protocol/checker; объяснять | U03 `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-model-comparison` | тот же dot, отдельно std::array<T,N> dimension | нельзя вывести отсутствие value parameters Java только из erasure; нет буквального C++ PECS |
| equals/hashCode/Comparable/Comparator, stable key; применять | U04 `theory/chapters/equality.qmd#sec-j25-theory-equality` | StudentId и отдельный mutable-key контрпример | L8: коллизия допустима, изменение ключа нарушает предпосылку поиска |
| List/Set/Map/Deque, iteration, ordering/duplicates, merge; применять | U04 `theory/chapters/collections.qmd#sec-j25-theory-collections` | частоты собственных ключей, FIFO, Set intersection | bracket stack и polynomial Map решают разные задачи; реализация выбирается по операции |
| view/structural snapshot/shallow copy/shared element/deep copy; применять различение | U04 `theory/chapters/collections.qmd#sec-j25-theory-collections-snapshots` | изменить состав источника, затем поле общей Book | L8: snapshot сохраняет состав, но shared Book изменяется; deep-copy не новое обязательство |
| Functional interface/lambda/target/capture/method reference/conditional invocation; применять | U04 `theory/chapters/functions.qmd#sec-j25-theory-functions` | select StudentTest; условный Supplier | L9: создание функции не вызов; невыбранная ветвь не запускает Supplier |
| Stream laziness/terminal/reuse/order/reduce/Optional; применять | U04 `theory/chapters/streams.qmd#sec-j25-theory-streams` | отбор тех же Student, видимая трасса обхода | L9: предел до/после filter, empty, source unchanged, peek/count может не вызвать peek |
| throw/throws/checked/catch — ранний минимум; применять до L7 | U05 `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions-early-checked` | U03 соседний checked container, два пути catch/declare | L7: compile obligation и runtime событие различны |
| Ошибка/отсутствие/результат, cause, политика границы; применять | U05 `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions` | parse score/строка/CLI | неверный ввод не превращается в пустой успех; место catch зависит от известного контекста |
| finally/AutoCloseable/TWR/close order/cause vs suppressed/ownership; применять | U05 `theory/chapters/resources.qmd#sec-j25-theory-resources` | подготовленные наблюдаемые ресурсы | return+close failure; GC не закрытие; System.in/out не закрываются |
| Path/UTF-8/byte vs char IO/readLine/file Stream/publication; применять | U05 `theory/chapters/io.qmd#sec-j25-theory-io` | чтение строк с физическим номером и копирование | L10: ошибка после части записи не обещает откат; lexical path не file identity |
| Parsing/value validation/catalog uniqueness/report/CLI/artifact; применять | U05 `theory/chapters/application.qmd#sec-j25-theory-application-behavior` | gradeapp input threshold, один Wrapper build | L10: duplicate после strip до stdout; из чистого каталога тот же отсортированный отчёт |

### Точный ранний маршрут массива (R1)

U01 размещает `theory/chapters/types.qmd#sec-j25-theory-types-array-minimum` **до** `#sec-j25-theory-types-initialization`, `#sec-j25-theory-types-copying` и новых absence/nullness-разделов, сразу после достаточного различения primitive/reference. Минимум объясняет `int[]` как ссылочный тип, переменную со ссылкой отдельно от объекта и компонента, `int[] pages={5,7}` и `new int[]{1,2}`, фиксированную длину, поле `length` без скобок, индексы от 0 до length−1, чтение и изменение компонента. `new int[2]` создаёт два нуля; `new String[2]` — две null-ссылки, не две строки; пустой массив допустим, чтение индекса 0 у него завершается ошибкой границы. Здесь достаточно одного массива и одной строки; цикл, массив массивов, clone и копирование диапазона ещё не используются. До этого минимума reference-примеры используют String и переназначение переменной без изменяемого контейнера. После него copying/final показывают общий `int[]`; это первое объяснение alias/mutation, а не неявная ссылка в будущую главу.

В `theory/chapters/platform.qmd#sec-j25-theory-platform-first-program` перед традиционной сигнатурой main локально поясняется только обозначение: launcher передаёт массив строк запуска, `String[] args` именует его, массив может быть пустым. Первый Reading использует заданные в исходнике scalar pages/days; не читает args, не вычисляет args.length и не индексирует его. Детали индексов/компонентов не являются условием этого первого запуска. Не делать требуемым ранний платформенный опыт с массивной памятью или alias до types-array-minimum.

Перед L1 U01 повторяет необходимую форму массива строк непосредственно в `practice/numbers.qmd#sec-j25-practice-numbers-s5` до `args.length`: список слов launcher, length, нулевая позиция и отсутствие позиции у пустого массива. В `#sec-j25-practice-numbers-input` сначала показан запуск с одним словом, затем чтение args[0]; `#sec-j25-practice-numbers-argument-boundary` локально объясняет `if`, сравнение количества и ранний `return` перед полным guarded примером. L1 не требует всей главы control или arrays. U06 указывает ранний types минимум и эту вводную практику среди подготовки; числовые протоколы заданий не меняются.

В control сначала scalar while/countdown, затем for/обход готового одномерного массива; enhanced-for использует уже введённую модель компонентов. `#sec-j25-theory-control-nested` показывает два скалярных счётчика, без `int[][]`; первое обязательное введение вложенного массива остаётся `theory/chapters/arrays.qmd#sec-j25-theory-arrays-nested`. В methods сначала scalar argument copy, затем `#sec-j25-theory-methods-reference-copy` использует уже введённые массив и alias. `#sec-j25-theory-methods-return-reference` создаёт результат через `new int[]{...}`, не необъяснённый clone; varargs локально поясняется как способ передать уже знакомый одномерный массив. Clone/матрицы и глубина копии впервые применяются после своего определения в arrays. Перед любым аналогичным примером справочника U01 даёт минимальный контекст и адресную ссылку на types-array-minimum.

U07 вводит тот же минимум в `lectures/values-absence.qmd#sec-j25-lecture-values-absence-array-minimum` до обязательных array alias/final примеров. Он входит в имеющиеся 45 минут значений/преобразований вместо повтора; в слоте 3 массив заново не считается неизвестной конструкцией. Новых глав, вводных занятий или минут нет. В semester-map минимум отмечен в строке «Значения и вычисления»; отдельная строка «Массивы» посвящена систематизации, обходу и копированию.

Optional: развёрнутые истории Smalltalk/Self, Wolfram, Lisp/R conditions/restarts; C# generic math; полный Kotlin/nullness каталог; capture helper; подробная классификация loading/JIT/benchmarking; глубокие копии произвольных графов; persistent-структуры; JPMS в деталях; сериализация; concurrency; Autotools/VCS теория. Они не требуются для защиты core и не расширяют десять работ. Краткие обязательные сравнения явно перечислены в таблице: Python typing/Protocol, адресный nullness-контраст, C++ dot и размерность, Java erasure. Enum/sealed и базовая рекурсия остаются небольшими предметными разборами; отдельной обязательной большой реализации для них нет.

## 6. Канонические семейства примеров

| Семейство | Владелец и контекст | Закреплённое назначение и результат |
|---|---|---|
| Журнал чтения | U01; полный sample `course.reading.Reading` | pages/days/title, 5/2→2, (double)5/2→2.5; затем метод/обход. Не подменять предмет другим расчётом при каждом новом синтаксисе |
| Изменяемый Book | U02; `course.examples.objects.Book`; в коротких автономных demos разрешён явно объявленный вложенный Book с теми же наблюдениями | `String title`, `boolean borrowed`; ранний plain data вариант отделён от следующего инкапсулированного. Зафиксированный reusable вариант: constructor Book(String), title(), borrowed(), borrow(); второй borrow отвергается до изменения. Box/снимки используют этот вариант, не неявный new Book() или публичное поле |
| Stack/Queue | U02; соседний `IntStackDemo`/`CharQueueDemo`, задания U06 хранят собственные имена API | Пустота/ёмкость/переход и отказ до изменения. Не давать готовые решения обязательных очередей. FixedQueue не переиспользует освобождённые позиции, CircularQueue переиспользует, DynQueue растёт; общий клиент не обещает full failure растущему контейнеру |
| Sender и Vehicle | U02; `course.examples.dispatch.Sender`, ConsoleSender, RecordingSender, Reminder; Vehicle остаётся отдельной предметной иерархией lab | Sender.send(String), Reminder получает Sender через constructor, RecordingSender позволяет наблюдать вызов. Общий Vehicle-клиент показывает dispatch; не объявлять Sender и Vehicle взаимозаменяемыми моделями. RecordingSender в раннем ядре хранит текст в уже объяснённом StringBuilder; List здесь не применяется |
| Box/PECS | U03; `course.examples.generics.Box<T>` | Один final T value, constructor/get; null допустим в базовом Box и отдельно обсуждается; Box<Book> сохраняет ту же ссылку. PECS — append source→target на разных независимых lists, source unchanged, сохранение порядка и прежнего prefix target; никакой неявной atomicity или deep copy |
| Сравнение Student→Person | U03; вложенные `ComparisonDemo.Person` и `ComparisonDemo.Student extends Person` в `course.examples.generics` | Person хранит final rank, implements Comparable<Person>, Integer.compare; Student не record. strict/flexible проверяются во всех пяти target-type вариантах EF03 |
| dot и модели обобщения | U03; `course.examples.generics.Arithmetic<T>`, DoubleArithmetic, immutable Complex | zero/add/multiply non-null, не мутируют вход; lengths проверяются; 11.0, faulty-zero12.0, empty zero, 11+i без сопряжения. C++20 и Python typing/Protocol сравнивают те же данные; команды/headers находятся в `handbook/entries/generic-methods.qmd#sec-j25-api-generic-methods-cpp-setup`, Python/checker — в `#sec-j25-api-generic-methods-python-checker`, не на сцене |
| Равенство и снимки | U04; `StudentId(String value)` — ключ-значение; MutableKey только помеченный контрпример, Book тот же | Код участника и record equality различаются. Map<String,grade.Student> безопасно фиксирует ключ. Independent list структуры не обещает independent mutable elements |
| grade.Student | Семантика U00; первое видимое определение U02, воспроизводимый полный implementation U05 | `package grade; public record Student(String id,String name,int score)`; единая нормализация/проверка. В коллекциях/функциях U04 используется этот тип, явно приведённый либо подключённый полный context. Никакого Student(int id), отдельного класса-наследника или иных правил дублей |
| Полное приложение | U05/U06; `grade.Main`, проект `tasks/projects/gradeapp/` | Файл/каталог/чистый отчёт/CLI разделены; два аргумента и stdout; loop и Stream report допустимы при одном contract |

До U03 обязательные объектные примеры используют уже объяснённые массивы/StringBuilder, а не необъяснённые List/Stream API. Record shallow immutability в U02 показывает общий массив; полные collection view/snapshot опыты вводит U04. Существующие GuestList/List-copy продолжения либо получают локально достаточное введение, либо явно становятся optional после знакомства с коллекциями, без prerequisite для L5/L6. В reflection U02 перед первым `Class<?>` прямо объясняет локальную запись: Class описывает тип, а `?` обозначает пока неизвестный аргумент типа; полные wildcard-операции вводит U03.

Короткий пример может исключить несущественную инфраструктуру, но обязан назвать свой файл/пакет/imports и все используемые объявления. Если показан plain Book как стадия развития, нельзя склеивать его с инкапсулированным вариантом в один compilation unit. Счётчик вариантов и полные fixtures проверяет M02; notes не хранят единственное определение или ожидаемый результат.

## 7. Единый gradeapp contract и заготовка

Читательский источник правил: `theory/chapters/application.qmd#sec-j25-theory-application-behavior`; каноническое условие задания: `tasks/bank/files.qmd#exr-j25-files-gradeapp-report`. Они воспроизводят следующий замороженный contract. U05 отвечает за согласованную объясняющую реализацию; U06 за проверяемую постановку; другие проекты ссылаются на точные места.

CLI: `grade.Main input threshold`, ровно два аргумента. Input — путь к UTF-8 `id;name;score`; threshold — целое 0..100. Поля нормализуются `strip()`. Id/name после нормализации непустые, score 0..100. Поля не допускают `;`, CR, LF; экранирования нет. Blank строки пропускаются, physical line counter увеличивается до пропуска. Дубликаты id после strip запрещены, сравнение case-sensitive: `s1`/` s1 ` — дубль, `s1`/`S1` — разные. Constructor проверяет один Student, loader уникальность каталога; record equals по всем компонентам не заменяет уникальность id.

Отчёт содержит score≥threshold, score по убыванию, затем id естественным String порядком. Формат `id;name;score`, по записи на строку, без заголовка; пустой успешный результат — пустой stdout. Reports не меняет source. Весь файл читается и проверяется до первого полезного вывода. Ошибка данных/чтения → пустой stdout; ошибка вывода может возникнуть после части строк, rollback stdout не обещается. Stderr — диагностика, системные потоки не закрываются. Status: 0 success, 2 args/path-syntax/threshold, 3 data, 4 read/output IO. Неверное число args и threshold проверяются до чтения. Пустой файл — success; отсутствие файла — IO failure. Shell redirection не создаёт третий аргумент.

Уже принятые роли: `Student`, checked `DataFormatException`, `StudentParser.parse(String,long)`, `StudentFiles.read(Path)`, `Reports.selected(List<Student>,int)`, `Reports.line(Student)`, `Main`. Skeleton создаёт M04 по окончательному U05/U06 packet. Корень project declaration — `/projects/gradeapp`, а скачиваемый starter строго находится в `tasks/projects/gradeapp/student/`: штатный project-download выбирает именно каталог student, не весь корень. Раскладка ниже окончательна; пути считаются от `tasks/projects/gradeapp/`:

| Путь внутри `tasks/projects/gradeapp/` | Что предоставлено / работа студента |
|---|---|
| `README.md` | Корневое описание проекта и public/private ролей; не содержит закрытое решение |
| `student/README.md`, `student/pom.xml`, `student/mvnw`, `student/mvnw.cmd`, `student/.mvn/wrapper/maven-wrapper.properties` | Готовый единственный Maven Wrapper маршрут; `maven.compiler.release=25`, UTF-8, manifest Main-Class grade.Main, finalName gradeapp; U05 фиксирует конкретные Maven/wrapper/plugin/test pins после проверки доступных артефактов |
| `student/src/main/java/grade/Student.java`, `student/src/main/java/grade/DataFormatException.java` | Полные корректные готовые части: value normalization/validation, checked exception с cause. Студент использует готовый инвариант записи |
| `student/src/main/java/grade/StudentParser.java`, `student/src/main/java/grade/StudentFiles.java`, `student/src/main/java/grade/Reports.java` | Компилируемые явные TODO-тела (throw UnsupportedOperationException): студент пишет parse(String,long), чтение/уникальность/публикацию и отбор/порядок/форматирование; публичные сигнатуры уже даны |
| `student/src/main/java/grade/Main.java` | Готовы main, проверка args/threshold/path, отображение ошибок в 0/2/3/4, helper вывода с checkError. TODO run(Path input,int threshold) студент связывает read → selected → line/output; ошибки передаются в подготовленную внешнюю границу. Весь каталог проверяется до вызова output helper. Не оставлять скрытую настройку потоков внутри 120 минут |
| `student/src/test/java/grade/PublicExamplesTest.java`, `student/samples/students.txt`, `student/samples/blank.txt`, `student/samples/duplicate.txt` | Небольшие открытые примеры: обычный результат, empty, duplicate. Они показывают контракт, не раскрывают реализацию TODO |

Учительские части вне starter: `reference/src/main/java/grade/Student.java`, `DataFormatException.java`, `StudentParser.java`, `StudentFiles.java`, `Reports.java`, `Main.java` в том же package-каталоге; закрытые локальные проверки — `tests/junit/GradeAppContractTest.java`. Ни reference, ни tests не копируются в student ZIP/resources. `target="manual"`, `project="/projects/gradeapp"`; `project-check="java-labs"` и новая PL registration не добавляются. Полная предметная проверка M04 остаётся локальной проверкой материала.

Заготовка компилируется; незавершённые методы честно отказывают и открытые behavior tests до реализации не обязаны проходить. Готовая учительская реализация и private checks хранятся отдельно от starter/download; M04 следует штатному project-download/public-private механизму, без копирования reference в публичный ресурс. Новый exercise `target="manual"`; PL17 не расширяется. Объём самостоятельной части сохраняет U05: parser, loading, filtering/sorting и orchestration; готовые value/exception/CLI helpers сокращают инфраструктурную работу. 120 минут остаются гипотезой для pilot, а не основанием незаметно выдать парсер или связку. U05 определяет проверенные **версии артефактов** в своём авторском пакете, как предписано планом; это не оставленный выбор структуры или новой системы сборки для medium. Одобрен один маршрут из `tasks/projects/gradeapp/student/`: `./mvnw test package`, затем из другого чистого каталога `java -jar /absolute/path/target/gradeapp.jar /absolute/path/input.txt 80`. Пользователь не обязан отдельно настраивать IDE или скачивать неописанные зависимости во время лабораторной. M02/M04 фиксируют предварительную доступность Wrapper/артефактов и реальный JDK 25.

## 8. Лабораторный состав и ранняя готовность

| Работа / file stem | Достаточный вход перед работой | Принятый ориентир |
|---|---|---:|
| numbers | platform без чтения args → types-array-minimum/expressions → practice-numbers-s5/input/argument-boundary с локальной проверкой args; console | 180 + отдельно 30 |
| control | boolean/ветвления/циклы, console protocols | 180 + 30 |
| arrays | методы/индексы/внешняя и внутренняя копия, stable rows | 180 + 30 |
| text | `sec-j25-theory-text-early-lab`, `sec-j25-api-strings-regex`, `sec-j25-api-strings-compare-to`; весь application не prerequisite | 180 + 30 |
| objects | mutable Book, private/constructor/invariant, stack refusal | 180 + 30 |
| polymorphism | общий клиент/dispatch/interfaces; различия fixed/circular/dynamic | 270 + 30; подготовка очередей уже внутри 135 |
| generics | Box/method/Number/compareTo/wildcard/PECS + local List минимум и только early checked section | 20+20+30+40+70 = 180; отдельно 30 |
| collections | equals/hash/order, List/Set/Map/Deque, view/snapshot | 35+45+40+60 = 180; отдельно 30 |
| pipelines | lambda/call/capture, Stream order/laziness/terminal | пять позиций, 180; отдельно 30 |
| files | exceptions/resources, byte/char IO, готовые value/exception/build/CLI scaffold части gradeapp | 30+45+30+120 = 225; отдельно 30 |

Новые зарезервированные ID, создаваемые U06: `exr-j25-generics-append-compatible-lists` (30), `exr-j25-collections-object-hash` (35), `exr-j25-collections-views-snapshots` (45), `exr-j25-files-gradeapp-report` (120). Это оценки замены, не измеренные минуты. U06 не выбирает другие ID для этих четырёх карточек без correction packet U00.

L7 заменяет только прежний swap30; identity20 остаётся. Удалённый из назначения swap сохраняется в банке с новым ID. L8 заменяет digit-stack30 и word-frequency50; обе карточки сохраняются в банке с новыми ID. Union/intersection40 и выбор bracket matching **либо** polynomial addition60 сохраняются; Deque не становится новой обязательной техникой для всех из-за отмены digit-stack. L9 сохраняет пять позиций и обе пары альтернатив. L10 заменяет две выбранные позиции по 60 интеграцией 120; прежние четыре карточки остаются в банке. Все переназначения касаются новой редакции источников, не работающих attempts.

Личная защита: L3 clone/shared row; L5 состояние после отказа; L6 общий клиент; L7 одна разрешённая и одна запрещённая операция; L8 отдельно изменение состава и общего элемента; L9 невыбранная функция и момент обхода; L10 место проверки дубля и начала stdout. Это короткое объяснение собственного решения внутри существующей защиты. U06 оставляет два разумных допустимых способа реализации открытых задач; тест не навязывает неоговорённые helpers или layout.

## 9. Лекции, ссылки и схемы

Имена и порядок 11 лекций ниже окончательны для материализации. Удалить девять старых файлов после создания новых; aliases/redirect/дубликатов в render-list нет. Исходные `02-values` и `05-generics` разделены по содержанию после чтения их текущих сценариев. Точное распределение каждого исходного anchor — в приложении; дополнительные новые понятия из таблицы первого введения получают отдельные новые anchors. Все содержательные разделы новой лекции core, кроме явно перечисленных ниже optional-продолжений; они остаются после основного рассказа под видимым заголовком «Дополнительно» и не входят в 80 минут. U07 не оставляет необходимые объяснения внутри этого продолжения.

Author links из `lectures/**` (включая index, notes и include) идут в theory; прямых переходов в practice/tasks нет. Общая навигация расширения остаётся служебной. Каждая строка глав в lecture map ниже задаёт допустимые основные explanation targets; нужный подраздел уточняется в U07 до конкретного `sec-j25-theory-*` из карты. Отдельные handbook ссылки разрешены только: `sec-j25-api-java-tools-javac`/`sec-j25-api-java-tools-java` (полная команда запуска), `sec-j25-api-erasure-javap` (воспроизвести реальный байткод), `sec-j25-api-project-wrapper` (повторить сборку). Other API tables остаются в theory/handbook route; список не квота, а достаточные нынешние технические продолжения. При новой нужной ссылке U07 записывает её конкретную причину в packet.

Портал и theory/index используют один `_includes/semester-map.qmd` с шестью колонками: тема/теория/лекция/справочник/практика/лабораторная. Handbook теперь виден в самом маршруте. Lecture index этот include не использует. Поиск API не заменяет введение обязательного понятия в теории или локальном примере.

Две схемы — native SVG/HTML, без внешнего Compiler Explorer. U03 составляет storyboard и точные Java sources; M02 компилирует реальным JDK 25 и сохраняет фактический javap вне source evidence; затем материализует `lectures/assets/j25/erasure-client-cast.svg` и `lectures/assets/j25/erasure-getter-bridge.svg`. U07 единолично вставляет их в `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-erased-read` и `#sec-j25-lecture-erasure-algorithms-bridge`, задаёт reveal sequence/размер и текстовый fallback. U03 объясняет то же в theory и даёт текстовые результаты в `handbook/entries/erasure.qmd#sec-j25-api-erasure-javap`.

Storyboard cast: слева объявленный Box<String>/get и String-получатель; справа реальный descriptor Object, invoke/get и клиентский checkcast String; появление «статическая связь → erased descriptor → client instruction». Storyboard bridge: слева Source<T>/TextSource String get, справа настоящий ACC_BRIDGE/ACC_SYNTHETIC Object get, его вызов String get и areturn; отдельной подписью клиентский cast, не внутри bridge. Подписи/номера связи дублируют цвет. Не рисовать придуманные offsets. Существенный результат виден в student и full без animation. Читаемость проверяется на 1280×800; техническая команда и полный listing в справочнике.



### Точные optional-продолжения лекций

Эти разделы не являются обязательным чтением до лабораторной. Неуказанные разделы имеют статус core; детали можно сжать внутри имеющегося объяснения, сохранив вывод и предпосылку. Описание модели null Java/C#/JS/Zig, минимальная reflection, Python typing/Protocol и размерность C++ остаются core в указанных ранее местах.

| Новая lecture цель | Статус |
|---|---|
| `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-target-machine` | optional, после основного рассказа |
| `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-c` | optional, после основного рассказа |
| `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-cpp` | optional, после основного рассказа |
| `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-object-lifetime` | optional, после основного рассказа |
| `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-other-languages` | optional, после основного рассказа |
| `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-clr` | optional, после основного рассказа |
| `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-layout` | optional, после основного рассказа |
| `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-operand-stack` | optional, после основного рассказа |
| `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-linking` | optional, после основного рассказа |
| `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-initialization` | optional, после основного рассказа |
| `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-jit` | optional, после основного рассказа |
| `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-performance` | optional, после основного рассказа |
| `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-method-evolution` | optional, после основного рассказа |
| `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-library-version` | optional, после основного рассказа |
| `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-nullable-comparison` | optional, после основного рассказа |
| `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-deep-copy` | optional, после основного рассказа |
| `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-shared-copy` | optional, после основного рассказа |
| `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-input-alias` | optional, после основного рассказа |
| `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-snapshot` | optional, после основного рассказа |
| `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-cpp` | optional, после основного рассказа |
| `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-rectangle` | optional, после основного рассказа |
| `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-construction-risk` | optional, после основного рассказа |
| `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-construction-order` | optional, после основного рассказа |
| `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-default-conflict` | optional, после основного рассказа |
| `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-ownership` | optional, после основного рассказа |
| `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-enum-code` | optional, после основного рассказа |
| `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-search-evolution` | optional, после основного рассказа |
| `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-search-coverage` | optional, после основного рассказа |
| `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-capture` | optional, после основного рассказа |
| `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-csharp-runtime` | optional, после основного рассказа |
| `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-csharp-operations` | optional, после основного рассказа |
| `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-csharp-dot` | optional, после основного рассказа |
| `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-csharp-contract` | optional, после основного рассказа |
| `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-22` | optional, после основного рассказа |
| `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-23` | optional, после основного рассказа |
| `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-43` | optional, после основного рассказа |
| `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-rust-result` | optional, после основного рассказа |
| `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-go-result` | optional, после основного рассказа |
| `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-checked-history` | optional, после основного рассказа |
| `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-finalization` | optional, после основного рассказа |
| `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-return-close-errors` | optional, после основного рассказа |
| `lectures/io-application.qmd#sec-j25-lecture-io-application-step-41` | optional, после основного рассказа |
| `lectures/io-application.qmd#sec-j25-lecture-io-application-temp-failure` | optional, после основного рассказа |
| `lectures/io-application.qmd#sec-j25-lecture-io-application-large-input` | optional, после основного рассказа |

## 10. Decision log и последовательная сдача

- D01: принята структура 11×80; 23/25/13 существующих предметных единиц и шесть частей сохранены. Файлы лекций названы по предмету, не старым номерам.
- D02: все переработанные topic/task/alternative IDs новые; categories — поддержанный механизм новых тегов; provider namespaces/PL поведение неизменны. Это исполнение прямого текущего требования.
- D03: ранние array/text/checked/List minima являются явными короткими введениями; самостоятельная лаба не ждёт поздней лекции. R1: array minimum в types перед initialization/copying/final; platform main пока не читает args; L1 локально повторяет String[]/length/index/guard. Первые control/method примеры scalar, затем уже объяснённый одномерный массив; nested/clone остаются в arrays.
- D04: G05/G12 остаются у U01; G13 имеет одну API-цель у U04. Семантические потребности передаются владельцу, не medium.
- D05: grade.Student record, ComparisonDemo.Student class и mutable Book имеют разные явные роли; все частичные примеры сохраняют полный sample context.
- D06: gradeapp manual, skeleton path и готовые/самостоятельные части закреплены; один Maven Wrapper route, два CLI args; U05 фиксирует проверенные версии.
- D07: L6=270, L10=225, replacements остаются внутри 1935; optional/резерв не добавлены в обязательную нагрузку; pilot не заявлен.
- D08: U03 semantic storyboard → M02 actual-bytecode assets → U07 insertion/visual sequence. Иллюстрации не подменяют измеренный bytecode.
- D09: U00 обновляет общие тексты сейчас на **будущие утверждённые цели**; промежуточный checkout до U01–U07/M01/M03 не является готовой к публикации сборкой. Нельзя временно восстанавливать старые aliases ради прохождения проверки.

До завершения subject authoring M01 читает готовую схему и подготавливает изменения config; применяет их в согласованном окне. M03 после U06/U07/M01 обновляет оставшиеся текущие потребители (включая непереработанные essays/projects, документацию и include), проверяет отсутствие прежних ID в текущем корпусе и semantic target. Исторические документы и этот внутренний source map исключаются из требования «нет старых упоминаний»; они не участвуют в runtime. M04 регенерирует свежие артефакты штатным owner-механизмом вне source, не правит generated вручную. M05 выполняет fresh build/link/leak/examples/host/container проверки; U08 независимо оценивает достаточность объяснения и закрывает EF/P/G, а не принимает прошлый render log за новую проверку.

## Приложение A. Полная карта файлов, порядка и тегов

Таблицы — текущие конечные пути. В пределах каждой предметной части порядок строк сохраняется в `theory/_quarto.yml`; общий список 23. `categories` — полный новый список страницы.

| Часть | Файл | Автор | Основная новая цель | categories |
|---|---|---|---|---|
| Язык и платформа | `theory/chapters/languages.qmd` | U01 | `sec-j25-theory-languages` | `j25, j25-core, j25-languages` |
| Язык и платформа | `theory/chapters/platform.qmd` | U01 | `sec-j25-theory-platform` | `j25, j25-core, j25-platform` |
| Значения и вычисления | `theory/chapters/types.qmd` | U01 | `sec-j25-theory-types` | `j25, j25-core, j25-types` |
| Значения и вычисления | `theory/chapters/expressions.qmd` | U01 | `sec-j25-theory-expressions` | `j25, j25-core, j25-expressions` |
| Значения и вычисления | `theory/chapters/control.qmd` | U01 | `sec-j25-theory-control` | `j25, j25-core, j25-control` |
| Значения и вычисления | `theory/chapters/methods.qmd` | U01 | `sec-j25-theory-methods` | `j25, j25-core, j25-methods` |
| Значения и вычисления | `theory/chapters/arrays.qmd` | U01 | `sec-j25-theory-arrays` | `j25, j25-core, j25-arrays` |
| Значения и вычисления | `theory/chapters/text.qmd` | U01 | `sec-j25-theory-text` | `j25, j25-core, j25-text` |
| Объектная модель | `theory/chapters/objects.qmd` | U02 | `sec-j25-theory-objects` | `j25, j25-core, j25-objects` |
| Объектная модель | `theory/chapters/classes.qmd` | U02 | `sec-j25-theory-classes` | `j25, j25-core, j25-classes` |
| Объектная модель | `theory/chapters/inheritance.qmd` | U02 | `sec-j25-theory-inheritance` | `j25, j25-core, j25-inheritance` |
| Объектная модель | `theory/chapters/interfaces.qmd` | U02 | `sec-j25-theory-interfaces` | `j25, j25-core, j25-interfaces` |
| Объектная модель | `theory/chapters/data-classes.qmd` | U02 | `sec-j25-theory-data-classes` | `j25, j25-core, j25-data-classes` |
| Обобщённые типы и коллекции | `theory/chapters/type-parameters.qmd` | U03 | `sec-j25-theory-type-parameters` | `j25, j25-core, j25-type-parameters` |
| Обобщённые типы и коллекции | `theory/chapters/type-relations.qmd` | U03 | `sec-j25-theory-type-relations` | `j25, j25-core, j25-type-relations` |
| Обобщённые типы и коллекции | `theory/chapters/equality.qmd` | U04 | `sec-j25-theory-equality` | `j25, j25-core, j25-equality` |
| Обобщённые типы и коллекции | `theory/chapters/collections.qmd` | U04 | `sec-j25-theory-collections` | `j25, j25-core, j25-collections` |
| Функции и последовательности | `theory/chapters/functions.qmd` | U04 | `sec-j25-theory-functions` | `j25, j25-core, j25-functions` |
| Функции и последовательности | `theory/chapters/streams.qmd` | U04 | `sec-j25-theory-streams` | `j25, j25-core, j25-streams` |
| Ошибки, ресурсы и приложение | `theory/chapters/exceptions.qmd` | U05 | `sec-j25-theory-exceptions` | `j25, j25-core, j25-exceptions` |
| Ошибки, ресурсы и приложение | `theory/chapters/resources.qmd` | U05 | `sec-j25-theory-resources` | `j25, j25-core, j25-resources` |
| Ошибки, ресурсы и приложение | `theory/chapters/io.qmd` | U05 | `sec-j25-theory-io` | `j25, j25-core, j25-io` |
| Ошибки, ресурсы и приложение | `theory/chapters/application.qmd` | U05 | `sec-j25-theory-application` | `j25, j25-core, j25-application` |

25 справочных статей (порядок в существующих частях handbook сохраняется):

| Файл | Автор | Основная новая цель | categories |
|---|---|---|---|
| `handbook/entries/java-tools.qmd` | U01 | `sec-j25-api-java-tools` | `j25, j25-core, j25-java-tools` |
| `handbook/entries/console.qmd` | U01 | `sec-j25-api-console` | `j25, j25-core, j25-console` |
| `handbook/entries/values.qmd` | U01 | `sec-j25-api-values` | `j25, j25-core, j25-values` |
| `handbook/entries/operators.qmd` | U01 | `sec-j25-api-operators` | `j25, j25-core, j25-operators` |
| `handbook/entries/statements.qmd` | U01 | `sec-j25-api-statements` | `j25, j25-core, j25-statements` |
| `handbook/entries/methods.qmd` | U01 | `sec-j25-api-methods` | `j25, j25-core, j25-methods` |
| `handbook/entries/arrays.qmd` | U01 | `sec-j25-api-arrays` | `j25, j25-core, j25-arrays` |
| `handbook/entries/strings.qmd` | U01 | `sec-j25-api-strings` | `j25, j25-core, j25-strings` |
| `handbook/entries/classes.qmd` | U02 | `sec-j25-api-classes` | `j25, j25-core, j25-classes` |
| `handbook/entries/inheritance.qmd` | U02 | `sec-j25-api-inheritance` | `j25, j25-core, j25-inheritance` |
| `handbook/entries/interfaces.qmd` | U02 | `sec-j25-api-interfaces` | `j25, j25-core, j25-interfaces` |
| `handbook/entries/generic-classes.qmd` | U03 | `sec-j25-api-generic-classes` | `j25, j25-core, j25-generic-classes` |
| `handbook/entries/generic-methods.qmd` | U03 | `sec-j25-api-generic-methods` | `j25, j25-core, j25-generic-methods` |
| `handbook/entries/wildcards.qmd` | U03 | `sec-j25-api-wildcards` | `j25, j25-core, j25-wildcards` |
| `handbook/entries/erasure.qmd` | U03 | `sec-j25-api-erasure` | `j25, j25-core, j25-erasure` |
| `handbook/entries/comparison.qmd` | U04 | `sec-j25-api-comparison` | `j25, j25-core, j25-comparison` |
| `handbook/entries/lists.qmd` | U04 | `sec-j25-api-lists` | `j25, j25-core, j25-lists` |
| `handbook/entries/sets-maps.qmd` | U04 | `sec-j25-api-sets-maps` | `j25, j25-core, j25-sets-maps` |
| `handbook/entries/functions.qmd` | U04 | `sec-j25-api-functions` | `j25, j25-core, j25-functions` |
| `handbook/entries/streams.qmd` | U04 | `sec-j25-api-streams` | `j25, j25-core, j25-streams` |
| `handbook/entries/optional.qmd` | U04 | `sec-j25-api-optional` | `j25, j25-core, j25-optional` |
| `handbook/entries/exceptions.qmd` | U05 | `sec-j25-api-exceptions` | `j25, j25-core, j25-exceptions` |
| `handbook/entries/resources.qmd` | U05 | `sec-j25-api-resources` | `j25, j25-core, j25-resources` |
| `handbook/entries/files.qmd` | U05 | `sec-j25-api-files` | `j25, j25-core, j25-files` |
| `handbook/entries/project.qmd` | U05 | `sec-j25-api-project` | `j25, j25-core, j25-project` |

13 вводных практик (10 core, затем 3 reserve):

| Файл | Автор | Основная новая цель | categories |
|---|---|---|---|
| `practice/numbers.qmd` | U01 | `sec-j25-practice-numbers` | `j25, j25-core, j25-numbers` |
| `practice/control.qmd` | U01 | `sec-j25-practice-control` | `j25, j25-core, j25-control` |
| `practice/arrays.qmd` | U01 | `sec-j25-practice-arrays` | `j25, j25-core, j25-arrays` |
| `practice/text.qmd` | U01 | `sec-j25-practice-text` | `j25, j25-core, j25-text` |
| `practice/objects.qmd` | U02 | `sec-j25-practice-objects` | `j25, j25-core, j25-objects` |
| `practice/polymorphism.qmd` | U02 | `sec-j25-practice-polymorphism` | `j25, j25-core, j25-polymorphism` |
| `practice/generics.qmd` | U03 | `sec-j25-practice-generics` | `j25, j25-core, j25-generics` |
| `practice/collections.qmd` | U04 | `sec-j25-practice-collections` | `j25, j25-core, j25-collections` |
| `practice/pipelines.qmd` | U04 | `sec-j25-practice-pipelines` | `j25, j25-core, j25-pipelines` |
| `practice/files.qmd` | U05 | `sec-j25-practice-files` | `j25, j25-core, j25-files` |
| `practice/reserve-algorithms.qmd` | U01 | `sec-j25-practice-reserve-algorithms` | `j25, j25-optional, j25-reserve-algorithms` |
| `practice/reserve-models.qmd` | U02 | `sec-j25-practice-reserve-models` | `j25, j25-optional, j25-reserve-models` |
| `practice/reserve-data.qmd` | U04 | `sec-j25-practice-reserve-data` | `j25, j25-optional, j25-reserve-data` |

Оставшиеся авторские страницы и общие файлы:

| Файл | Автор | categories (для QMD) |
|---|---|---|
| `README.md` | U00 | не применимо |
| `_includes/semester-map.qmd` | U00 | не применимо |
| `docs/authoring-methodology.md` | U00 | не применимо |
| `handbook/index.qmd` | U00 | `j25, j25-route` |
| `index.qmd` | U00 | `j25, j25-route` |
| `practice/index.qmd` | U00 | `j25, j25-route` |
| `tasks/README.md` | U06 | не применимо |
| `tasks/bank/arrays.qmd` | U06 | `j25, j25-core, j25-arrays` |
| `tasks/bank/collections.qmd` | U06 | `j25, j25-core, j25-collections` |
| `tasks/bank/control.qmd` | U06 | `j25, j25-core, j25-control` |
| `tasks/bank/exceptions.qmd` | U06 | `j25, j25-core, j25-exceptions` |
| `tasks/bank/files.qmd` | U06 | `j25, j25-core, j25-files` |
| `tasks/bank/generics.qmd` | U06 | `j25, j25-core, j25-generics` |
| `tasks/bank/index.qmd` | U06 | `j25, j25-route` |
| `tasks/bank/numbers.qmd` | U06 | `j25, j25-core, j25-numbers` |
| `tasks/bank/objects.qmd` | U06 | `j25, j25-core, j25-objects` |
| `tasks/bank/pipelines.qmd` | U06 | `j25, j25-core, j25-pipelines` |
| `tasks/bank/polymorphism.qmd` | U06 | `j25, j25-core, j25-polymorphism` |
| `tasks/bank/text.qmd` | U06 | `j25, j25-core, j25-text` |
| `tasks/index.qmd` | U06 | `j25, j25-route` |
| `tasks/labs/arrays.qmd` | U06 | `j25, j25-core, j25-arrays` |
| `tasks/labs/collections.qmd` | U06 | `j25, j25-core, j25-collections` |
| `tasks/labs/control.qmd` | U06 | `j25, j25-core, j25-control` |
| `tasks/labs/files.qmd` | U06 | `j25, j25-core, j25-files` |
| `tasks/labs/generics.qmd` | U06 | `j25, j25-core, j25-generics` |
| `tasks/labs/index.qmd` | U06 | `j25, j25-route` |
| `tasks/labs/numbers.qmd` | U06 | `j25, j25-core, j25-numbers` |
| `tasks/labs/objects.qmd` | U06 | `j25, j25-core, j25-objects` |
| `tasks/labs/pipelines.qmd` | U06 | `j25, j25-core, j25-pipelines` |
| `tasks/labs/polymorphism.qmd` | U06 | `j25, j25-core, j25-polymorphism` |
| `tasks/labs/reserve-algorithms.qmd` | U06 | `j25, j25-optional, j25-reserve-algorithms` |
| `tasks/labs/reserve-data.qmd` | U06 | `j25, j25-optional, j25-reserve-data` |
| `tasks/labs/reserve-models.qmd` | U06 | `j25, j25-optional, j25-reserve-models` |
| `tasks/labs/text.qmd` | U06 | `j25, j25-core, j25-text` |
| `theory/index.qmd` | U00 | `j25, j25-route` |
| `theory/system-practice.qmd` | U00 | `j25, j25-route` |

Итоговый lecture render order; автор каждого файла — U07. Все слоты core; optional-продолжения не включены во время. `lectures/index.qmd` — `sec-j25-lectures`, categories `[j25, j25-route]`.

| Порядок | Новый файл и title | Основной ID | Главы theory / исходный материал | Расклад минут | categories |
|---|---|---|---|---|---|
| 1 | `lectures/platform-runtime.qmd` — Язык и выполнение программы | `sec-j25-lecture-platform-runtime` | `theory/chapters/languages.qmd#sec-j25-theory-languages`, `theory/chapters/platform.qmd#sec-j25-theory-platform`; исходный `lectures/01-platform.qmd` | 45 модель и запуск + 12 история/границы + 15 наблюдение .class/отказа + 8 вопросы = 80 | `j25, j25-core, j25-platform-runtime` |
| 2 | `lectures/values-absence.qmd` — Значения, преобразования и отсутствие | `sec-j25-lecture-values-absence` | `theory/chapters/types.qmd#sec-j25-theory-types`, `theory/chapters/expressions.qmd#sec-j25-theory-expressions`; исходный `lectures/02-values.qmd` | 45 значения/преобразования, включая ранний массив + 15 отсутствие/границы проверки + 12 трасса + 8 вопросы = 80 | `j25, j25-core, j25-values-absence` |
| 3 | `lectures/control-methods-data.qmd` — Управление, методы, массивы и текст | `sec-j25-lecture-control-methods-data` | `theory/chapters/control.qmd#sec-j25-theory-control`, `theory/chapters/methods.qmd#sec-j25-theory-methods`, `theory/chapters/arrays.qmd#sec-j25-theory-arrays`, `theory/chapters/text.qmd#sec-j25-theory-text`; исходный `lectures/02-values.qmd` | 20 управление + 20 методы + 20 массивы/текст + 12 связный пример + 8 вопросы = 80 | `j25, j25-core, j25-control-methods-data` |
| 4 | `lectures/objects-invariants.qmd` — Объекты, состояние и инварианты | `sec-j25-lecture-objects-invariants` | `theory/chapters/objects.qmd#sec-j25-theory-objects`, `theory/chapters/classes.qmd#sec-j25-theory-classes`; исходный `lectures/03-classes.qmd` | 45 построение модели + 15 изменения/отказ + 12 перенос + 8 вопросы = 80 | `j25, j25-core, j25-objects-invariants` |
| 5 | `lectures/interfaces-records.qmd` — Интерфейсы, композиция и записи | `sec-j25-lecture-interfaces-records` | `theory/chapters/inheritance.qmd#sec-j25-theory-inheritance`, `theory/chapters/interfaces.qmd#sec-j25-theory-interfaces`, `theory/chapters/data-classes.qmd#sec-j25-theory-data-classes`; исходный `lectures/04-object-design.qmd` | 25 подтипизация/диспетчеризация + 20 интерфейс/композиция + 15 record + 12 Class/record + 8 вопросы = 80 | `j25, j25-core, j25-interfaces-records` |
| 6 | `lectures/generics-variance.qmd` — Обобщённые типы, операции и wildcard | `sec-j25-lecture-generics-variance` | `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters`, `theory/chapters/type-relations.qmd#sec-j25-theory-type-relations`; исходный `lectures/05-generics.qmd` | 18 Box/метод + 12 Number/compareTo + 24 invariance/wildcards + 18 PECS/перенос + 8 вопросы = 80 | `j25, j25-core, j25-generics-variance` |
| 7 | `lectures/erasure-algorithms.qmd` — Стирание и общие алгоритмы | `sec-j25-lecture-erasure-algorithms` | `theory/chapters/type-relations.qmd#sec-j25-theory-type-relations`, `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters`; исходный `lectures/05-generics.qmd` | 25 стирание/мост + 8 совместимость языковых моделей + 15 Arithmetic/dot + 12 C++ + 10 Python Protocol/checker + 10 проверка вывода = 80 | `j25, j25-core, j25-erasure-algorithms` |
| 8 | `lectures/equality-collections.qmd` — Равенство, порядок и коллекции | `sec-j25-lecture-equality-collections` | `theory/chapters/equality.qmd#sec-j25-theory-equality`, `theory/chapters/collections.qmd#sec-j25-theory-collections`; исходный `lectures/06-collections.qmd` | 20 equals/hash/order + 20 выбор контейнера + 20 view/snapshot + 12 перенос + 8 вопросы = 80 | `j25, j25-core, j25-equality-collections` |
| 9 | `lectures/functions-streams.qmd` — Функции и последовательности | `sec-j25-lecture-functions-streams` | `theory/chapters/functions.qmd#sec-j25-theory-functions`, `theory/chapters/streams.qmd#sec-j25-theory-streams`; исходный `lectures/07-functions.qmd` | 25 передача поведения/эффекты + 15 захват/композиция + 25 Stream + 15 прогноз и перенос = 80 | `j25, j25-core, j25-functions-streams` |
| 10 | `lectures/exceptions-resources.qmd` — Исключения и ресурсы | `sec-j25-lecture-exceptions-resources` | `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions`, `theory/chapters/resources.qmd#sec-j25-theory-resources`; исходный `lectures/08-exceptions.qmd` | 25 ошибки/границы + 25 ресурс/порядок + 15 состояние/контекст + 15 разбор трассы = 80 | `j25, j25-core, j25-exceptions-resources` |
| 11 | `lectures/io-application.qmd` — Ввод-вывод и сборка приложения | `sec-j25-lecture-io-application` | `theory/chapters/io.qmd#sec-j25-theory-io`, `theory/chapters/application.qmd#sec-j25-theory-application`; исходный `lectures/09-application.qmd` | 25 формат/загрузка + 25 отчёт/политика отказа + 20 Maven/поставка + 10 проверки = 80 | `j25, j25-core, j25-io-application` |

## Приложение B. Полное сопоставление исходных целей и текущих потребителей

Это закрытая от публикации авторская карта для пересборки, не таблица aliases или миграции. Колонка «потребители» получена из tracked текстов исходного PR13, исключая vendor и исторический revision evidence; включает ordinary QRC, shortcode, project-download, Markdown fragments и configuration. Координаты — строка:столбец **baseline**, не новые line numbers. Переписанный автором фрагмент использует новую цель сразу; M03 выполняет остаточные ссылки. `—` означает отсутствие текущих входящих текстовых вхождений, не разрешение оставить старый ID. Определение удаляется/переименовывается в точной новой цели; сам этот appendix с прежними токенами не включён в consumer scan. Все новыеlecture inner anchors относятся к настоящим смысловым разделам; U07 может объединить соседние экраны внутри раздела, не теряя его результата.


### handbook/entries/arrays.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-arrays` (1) | `handbook/entries/arrays.qmd#sec-j25-api-arrays` | U01 | `lectures/02-values.qmd:866:11`; `practice/arrays.qmd:71:11`; `practice/reserve-algorithms.qmd:47:11`; `practice/reserve-models.qmd:70:11`; `tasks/labs/arrays.qmd:14:53`; `tasks/labs/reserve-algorithms.qmd:11:53`; `theory/chapters/arrays.qmd:138:261` |
| `sec-h-arrays-creation` (5) | `handbook/entries/arrays.qmd#sec-j25-api-arrays-creation` | U01 | — |
| `sec-h-arrays-api` (21) | `handbook/entries/arrays.qmd#sec-j25-api-arrays-api` | U01 | — |
| `sec-h-arrays-arraycopy` (52) | `handbook/entries/arrays.qmd#sec-j25-api-arrays-arraycopy` | U01 | — |

### handbook/entries/classes.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-classes` (5) | `handbook/entries/classes.qmd#sec-j25-api-classes` | U02 | `practice/objects.qmd:73:66`; `practice/objects.qmd:82:11`; `tasks/labs/objects.qmd:11:54`; `theory/chapters/classes.qmd:285:435`; `theory/chapters/objects.qmd:278:325` |
| `sec-h-classes-declaration` (9) | `handbook/entries/classes.qmd#sec-j25-api-classes-declaration` | U02 | — |
| `sec-h-classes-access` (25) | `handbook/entries/classes.qmd#sec-j25-api-classes-access` | U02 | `lectures/03-classes.qmd:618:146` |
| `sec-h-classes-constructors` (55) | `handbook/entries/classes.qmd#sec-j25-api-classes-constructors` | U02 | — |
| `sec-h-classes-modifiers` (72) | `handbook/entries/classes.qmd#sec-j25-api-classes-modifiers` | U02 | — |
| `sec-h-classes-records` (85) | `handbook/entries/classes.qmd#sec-j25-api-classes-records` | U02 | `theory/chapters/data-classes.qmd:126:409` |

### handbook/entries/comparison.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-comparison` (1) | `handbook/entries/comparison.qmd#sec-j25-api-comparison` | U04 | `handbook/entries/generic-classes.qmd:55:154`; `lectures/05-generics.qmd:315:31`; `lectures/05-generics.qmd:1182:11`; `lectures/06-collections.qmd:206:60`; `practice/collections.qmd:92:38`; `practice/pipelines.qmd:93:37`; `theory/chapters/equality.qmd:255:11`; `theory/chapters/type-parameters.qmd:190:386` |
| `sec-h-comparison-equals` (7) | `handbook/entries/comparison.qmd#sec-j25-api-comparison-equals` | U04 | — |
| `sec-h-comparison-order` (34) | `handbook/entries/comparison.qmd#sec-j25-api-comparison-order` | U04 | — |

### handbook/entries/console.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-console` (1) | `handbook/entries/console.qmd#sec-j25-api-console` | U01 | `practice/numbers.qmd:188:43`; `tasks/bank/control.qmd:17:44`; `tasks/labs/control.qmd:15:11`; `theory/chapters/control.qmd:237:11` |
| `sec-h-console-text` (9) | `handbook/entries/console.qmd#sec-j25-api-console-text` | U01 | `practice/control.qmd:86:101`; `practice/control.qmd:92:11` |
| `sec-h-console-integers` (69) | `handbook/entries/console.qmd#sec-j25-api-console-integers` | U01 | `practice/control.qmd:93:11` |

### handbook/entries/erasure.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-erasure` (5) | `handbook/entries/erasure.qmd#sec-j25-api-erasure` | U03 | `handbook/entries/generic-classes.qmd:74:272`; `lectures/05-generics.qmd:709:29`; `lectures/05-generics.qmd:1181:21`; `practice/generics.qmd:76:11`; `theory/chapters/type-relations.qmd:159:421`; `theory/chapters/type-relations.qmd:229:463` |
| `sec-h-erasure-mapping` (9) | `handbook/entries/erasure.qmd#sec-j25-api-erasure-mapping` | U03 | — |
| `sec-h-erasure-limits` (27) | `handbook/entries/erasure.qmd#sec-j25-api-erasure-limits` | U03 | — |
| `sec-h-erasure-instanceof` (39) | `handbook/entries/erasure.qmd#sec-j25-api-erasure-instanceof` | U03 | — |
| `sec-h-erasure-unchecked` (58) | `handbook/entries/erasure.qmd#sec-j25-api-erasure-unchecked` | U03 | — |

### handbook/entries/exceptions.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-exceptions` (1) | `handbook/entries/exceptions.qmd#sec-j25-api-exceptions` | U05 | `lectures/08-exceptions.qmd:516:18`; `practice/generics.qmd:93:37`; `practice/reserve-data.qmd:88:61`; `theory/chapters/exceptions.qmd:439:11` |
| `sec-h-exceptions-syntax` (7) | `handbook/entries/exceptions.qmd#sec-j25-api-exceptions-syntax` | U05 | — |
| `sec-h-exceptions-handlers` (34) | `handbook/entries/exceptions.qmd#sec-j25-api-exceptions-handlers` | U05 | — |
| `sec-h-exceptions-diagnostics` (67) | `handbook/entries/exceptions.qmd#sec-j25-api-exceptions-diagnostics` | U05 | `practice/files.qmd:151:25` |
| `sec-h-exceptions-contract` (81) | `handbook/entries/exceptions.qmd#sec-j25-api-exceptions-contract` | U05 | — |

### handbook/entries/files.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-files` (1) | `handbook/entries/files.qmd#sec-j25-api-files` | U05 | `lectures/09-application.qmd:166:28`; `practice/reserve-data.qmd:88:11`; `tasks/labs/files.qmd:11:56`; `theory/chapters/io.qmd:292:60` |
| `sec-h-files-path` (7) | `handbook/entries/files.qmd#sec-j25-api-files-path` | U05 | `practice/files.qmd:49:23` |
| `sec-h-files-operations` (25) | `handbook/entries/files.qmd#sec-j25-api-files-operations` | U05 | `practice/files.qmd:96:22` |
| `sec-h-files-options` (76) | `handbook/entries/files.qmd#sec-j25-api-files-options` | U05 | `practice/files.qmd:162:11` |

### handbook/entries/functions.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-functions` (1) | `handbook/entries/functions.qmd#sec-j25-api-functions` | U04 | `lectures/07-functions.qmd:151:42`; `practice/pipelines.qmd:55:56`; `practice/pipelines.qmd:87:11`; `practice/reserve-data.qmd:82:11`; `theory/chapters/functions.qmd:199:60` |
| `sec-h-functions-interfaces` (7) | `handbook/entries/functions.qmd#sec-j25-api-functions-interfaces` | U04 | — |
| `sec-h-functions-syntax` (29) | `handbook/entries/functions.qmd#sec-j25-api-functions-syntax` | U04 | — |
| `sec-h-functions-references` (50) | `handbook/entries/functions.qmd#sec-j25-api-functions-references` | U04 | — |
| `sec-h-functions-compose` (64) | `handbook/entries/functions.qmd#sec-j25-api-functions-compose` | U04 | — |
| `sec-h-functions-returned` (82) | `handbook/entries/functions.qmd#sec-j25-api-functions-returned` | U04 | — |

### handbook/entries/generic-classes.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-generic-classes` (5) | `handbook/entries/generic-classes.qmd#sec-j25-api-generic-classes` | U03 | `handbook/entries/erasure.qmd:18:26`; `handbook/entries/interfaces.qmd:53:330`; `lectures/05-generics.qmd:1180:11`; `practice/generics.qmd:34:11`; `practice/reserve-models.qmd:70:35`; `tasks/labs/generics.qmd:11:62`; `theory/chapters/type-parameters.qmd:365:361`; `theory/chapters/type-relations.qmd:239:36` |
| `sec-h-generic-classes-forms` (9) | `handbook/entries/generic-classes.qmd#sec-j25-api-generic-classes-forms` | U03 | — |
| `sec-h-generic-classes-bounds` (45) | `handbook/entries/generic-classes.qmd#sec-j25-api-generic-classes-bounds` | U03 | — |
| `sec-h-generic-classes-inheritance` (59) | `handbook/entries/generic-classes.qmd#sec-j25-api-generic-classes-inheritance` | U03 | — |

### handbook/entries/generic-methods.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-generic-methods` (5) | `handbook/entries/generic-methods.qmd#sec-j25-api-generic-methods` | U03 | `practice/generics.qmd:34:44`; `theory/chapters/type-parameters.qmd:365:395`; `theory/chapters/type-relations.qmd:239:69` |
| `sec-h-generic-methods-method` (9) | `handbook/entries/generic-methods.qmd#sec-j25-api-generic-methods-method` | U03 | — |
| `sec-h-generic-methods-bounds` (28) | `handbook/entries/generic-methods.qmd#sec-j25-api-generic-methods-bounds` | U03 | — |
| `sec-h-generic-methods-inference` (44) | `handbook/entries/generic-methods.qmd#sec-j25-api-generic-methods-inference` | U03 | — |

### handbook/entries/inheritance.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-inheritance` (5) | `handbook/entries/inheritance.qmd#sec-j25-api-inheritance` | U02 | `practice/polymorphism.qmd:33:11`; `theory/chapters/inheritance.qmd:237:445` |
| `sec-h-inheritance-forms` (9) | `handbook/entries/inheritance.qmd#sec-j25-api-inheritance-forms` | U02 | — |
| `sec-h-inheritance-dispatch` (28) | `handbook/entries/inheritance.qmd#sec-j25-api-inheritance-dispatch` | U02 | — |
| `sec-h-inheritance-casts` (40) | `handbook/entries/inheritance.qmd#sec-j25-api-inheritance-casts` | U02 | — |
| `sec-h-inheritance-sealed` (52) | `handbook/entries/inheritance.qmd#sec-j25-api-inheritance-sealed` | U02 | — |

### handbook/entries/interfaces.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-interfaces` (5) | `handbook/entries/interfaces.qmd#sec-j25-api-interfaces` | U02 | `lectures/04-object-design.qmd:729:187`; `practice/polymorphism.qmd:60:11`; `tasks/labs/polymorphism.qmd:11:57`; `tasks/labs/reserve-models.qmd:11:57`; `theory/chapters/interfaces.qmd:274:351` |
| `sec-h-interfaces-members` (9) | `handbook/entries/interfaces.qmd#sec-j25-api-interfaces-members` | U02 | — |
| `sec-h-interfaces-multiple` (32) | `handbook/entries/interfaces.qmd#sec-j25-api-interfaces-multiple` | U02 | — |
| `sec-h-interfaces-generic` (42) | `handbook/entries/interfaces.qmd#sec-j25-api-interfaces-generic` | U02 | — |

### handbook/entries/java-tools.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-java-tools` (1) | `handbook/entries/java-tools.qmd#sec-j25-api-java-tools` | U01 | `handbook/entries/erasure.qmd:25:166`; `lectures/01-platform.qmd:686:69`; `lectures/05-generics.qmd:1183:11`; `practice/numbers.qmd:43:11`; `theory/chapters/platform.qmd:78:358` |
| `sec-h-java-tools-javac` (5) | `handbook/entries/java-tools.qmd#sec-j25-api-java-tools-javac` | U01 | — |
| `sec-h-java-tools-java` (24) | `handbook/entries/java-tools.qmd#sec-j25-api-java-tools-java` | U01 | `lectures/01-platform.qmd:321:149`; `lectures/01-platform.qmd:603:183` |
| `sec-h-java-tools-inspection` (45) | `handbook/entries/java-tools.qmd#sec-j25-api-java-tools-inspection` | U01 | — |
| `sec-h-java-tools-errors` (57) | `handbook/entries/java-tools.qmd#sec-j25-api-java-tools-errors` | U01 | — |

### handbook/entries/lists.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-lists` (1) | `handbook/entries/lists.qmd#sec-j25-api-lists` | U04 | `practice/reserve-models.qmd:80:44`; `tasks/labs/collections.qmd:11:58`; `theory/chapters/collections.qmd:319:13` |
| `sec-h-lists-operations` (9) | `handbook/entries/lists.qmd#sec-j25-api-lists-basics` | U04 | `practice/collections.qmd:85:11` |
| `sec-h-lists-factories` (39) | `handbook/entries/lists.qmd#sec-j25-api-lists-factories` | U04 | — |

### handbook/entries/methods.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-methods` (1) | `handbook/entries/methods.qmd#sec-j25-api-methods` | U01 | `practice/objects.qmd:34:28`; `theory/chapters/methods.qmd:195:429` |
| `sec-h-methods-declaration` (5) | `handbook/entries/methods.qmd#sec-j25-api-methods-declaration` | U01 | — |
| `sec-h-methods-signature` (20) | `handbook/entries/methods.qmd#sec-j25-api-methods-signature` | U01 | — |
| `sec-h-methods-arguments` (28) | `handbook/entries/methods.qmd#sec-j25-api-methods-arguments` | U01 | — |
| `sec-h-methods-varargs` (40) | `handbook/entries/methods.qmd#sec-j25-api-methods-varargs` | U01 | — |

### handbook/entries/operators.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-operators` (1) | `handbook/entries/operators.qmd#sec-j25-api-operators` | U01 | `practice/arrays.qmd:91:36`; `practice/numbers.qmd:151:11`; `practice/reserve-algorithms.qmd:47:59`; `theory/chapters/expressions.qmd:191:353` |
| `sec-h-operators-kinds` (5) | `handbook/entries/operators.qmd#sec-j25-api-operators-kinds` | U01 | — |
| `sec-h-operators-division` (24) | `handbook/entries/operators.qmd#sec-j25-api-operators-division` | U01 | — |
| `sec-h-operators-precedence` (39) | `handbook/entries/operators.qmd#sec-j25-api-operators-precedence` | U01 | — |

### handbook/entries/optional.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-optional` (1) | `handbook/entries/optional.qmd#sec-j25-api-optional` | U04 | `lectures/07-functions.qmd:555:39`; `practice/reserve-data.qmd:82:63`; `theory/chapters/streams.qmd:295:43` |
| `sec-h-optional-basic` (10) | `handbook/entries/optional.qmd#sec-j25-api-optional-basic` | U04 | — |
| `sec-h-optional-transform` (43) | `handbook/entries/optional.qmd#sec-j25-api-optional-transform` | U04 | — |

### handbook/entries/project.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-project` (1) | `handbook/entries/project.qmd#sec-j25-api-project` | U05 | `lectures/09-application.qmd:510:60`; `tasks/labs/reserve-data.qmd:11:58`; `theory/chapters/application.qmd:414:11` |
| `sec-h-project-layout` (7) | `handbook/entries/project.qmd#sec-j25-api-project-layout` | U05 | — |
| `sec-h-project-contract` (41) | `handbook/entries/project.qmd#sec-j25-api-project-contract` | U05 | — |
| `sec-h-project-checks` (68) | `handbook/entries/project.qmd#sec-j25-api-project-checks` | U05 | — |
| `sec-h-project-failure` (85) | `handbook/entries/project.qmd#sec-j25-api-project-failure` | U05 | — |

### handbook/entries/resources.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-resources` (1) | `handbook/entries/resources.qmd#sec-j25-api-resources` | U05 | `handbook/entries/exceptions.qmd:77:11`; `lectures/08-exceptions.qmd:516:47`; `practice/reserve-data.qmd:88:34`; `theory/chapters/resources.qmd:336:58` |
| `sec-h-resources-syntax` (9) | `handbook/entries/resources.qmd#sec-j25-api-resources-syntax` | U05 | — |
| `sec-h-resources-order` (38) | `handbook/entries/resources.qmd#sec-j25-api-resources-order` | U05 | `practice/files.qmd:141:33` |
| `sec-h-resources-ownership` (55) | `handbook/entries/resources.qmd#sec-j25-api-resources-ownership` | U05 | — |

### handbook/entries/sets-maps.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-sets-maps` (1) | `handbook/entries/sets-maps.qmd#sec-j25-api-sets-maps` | U04 | `practice/collections.qmd:77:40`; `practice/collections.qmd:92:11`; `practice/reserve-models.qmd:80:67`; `theory/chapters/collections.qmd:287:35`; `theory/chapters/collections.qmd:319:37` |
| `sec-h-sets-maps-set` (8) | `handbook/entries/sets-maps.qmd#sec-j25-api-sets-maps-set` | U04 | — |
| `sec-h-sets-maps-bulk` (29) | `handbook/entries/sets-maps.qmd#sec-j25-api-sets-maps-bulk` | U04 | `practice/collections.qmd:65:44` |
| `sec-h-sets-maps-map` (52) | `handbook/entries/sets-maps.qmd#sec-j25-api-sets-maps-map` | U04 | — |
| `sec-h-sets-maps-queue` (89) | `handbook/entries/sets-maps.qmd#sec-j25-api-sets-maps-queue` | U04 | `practice/collections.qmd:86:11` |

### handbook/entries/statements.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-statements` (1) | `handbook/entries/statements.qmd#sec-j25-api-statements` | U01 | `practice/control.qmd:58:11`; `practice/numbers.qmd:74:54`; `tasks/labs/control.qmd:14:54`; `theory/chapters/control.qmd:208:334` |
| `sec-h-statements-branches` (5) | `handbook/entries/statements.qmd#sec-j25-api-statements-branches` | U01 | — |
| `sec-h-statements-loops` (36) | `handbook/entries/statements.qmd#sec-j25-api-statements-loops` | U01 | — |
| `sec-h-statements-transfers` (49) | `handbook/entries/statements.qmd#sec-j25-api-statements-transfers` | U01 | — |

### handbook/entries/streams.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-streams` (1) | `handbook/entries/streams.qmd#sec-j25-api-streams` | U04 | `practice/pipelines.qmd:93:11`; `practice/reserve-data.qmd:82:38`; `tasks/labs/pipelines.qmd:11:54`; `theory/chapters/streams.qmd:311:11` |
| `sec-h-streams-operations` (8) | `handbook/entries/streams.qmd#sec-j25-api-streams-operations` | U04 | — |
| `sec-h-streams-collectors` (55) | `handbook/entries/streams.qmd#sec-j25-api-streams-collectors` | U04 | — |
| `sec-h-streams-behavior` (108) | `handbook/entries/streams.qmd#sec-j25-api-streams-behavior` | U04 | — |

### handbook/entries/strings.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-strings` (1) | `handbook/entries/strings.qmd#sec-j25-api-strings` | U01 | `practice/reserve-algorithms.qmd:65:11`; `practice/text.qmd:55:11`; `tasks/labs/text.qmd:11:51`; `theory/chapters/text.qmd:145:341` |
| `sec-h-strings-string` (5) | `handbook/entries/strings.qmd#sec-j25-api-strings-string` | U01 | — |
| `sec-h-strings-unicode` (31) | `handbook/entries/strings.qmd#sec-j25-api-strings-unicode` | U01 | `handbook/entries/console.qmd:60:37` |
| `sec-h-strings-parse-int` (45) | `handbook/entries/strings.qmd#sec-j25-api-strings-parse-int` | U01 | `handbook/entries/console.qmd:85:33`; `lectures/02-values.qmd:950:11`; `practice/numbers.qmd:61:38`; `theory/chapters/types.qmd:27:50` |
| `sec-h-strings-regex` (61) | `handbook/entries/strings.qmd#sec-j25-api-strings-regex` | U01 | `practice/text.qmd:89:31`; `theory/chapters/text.qmd:211:53` |
| `sec-h-strings-builder` (108) | `handbook/entries/strings.qmd#sec-j25-api-strings-builder` | U01 | — |

### handbook/entries/values.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-values` (1) | `handbook/entries/values.qmd#sec-j25-api-values` | U01 | `lectures/02-values.qmd:230:11`; `practice/arrays.qmd:91:11`; `practice/reserve-algorithms.qmd:47:35`; `tasks/labs/numbers.qmd:14:52`; `theory/chapters/types.qmd:409:25` |
| `sec-h-values-primitives` (7) | `handbook/entries/values.qmd#sec-j25-api-values-primitives` | U01 | — |
| `sec-h-values-declarations` (22) | `handbook/entries/values.qmd#sec-j25-api-values-declarations` | U01 | — |
| `sec-h-values-conversions` (38) | `handbook/entries/values.qmd#sec-j25-api-values-conversions` | U01 | `practice/numbers.qmd:130:27` |
| `sec-h-values-references` (51) | `handbook/entries/values.qmd#sec-j25-api-values-references` | U01 | — |
| `sec-h-values-type-levels` (57) | `handbook/entries/values.qmd#sec-j25-api-values-type-levels` | U01 | — |
| `sec-h-values-copying` (71) | `handbook/entries/values.qmd#sec-j25-api-values-copying` | U01 | — |

### handbook/entries/wildcards.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-wildcards` (5) | `handbook/entries/wildcards.qmd#sec-j25-api-wildcards` | U03 | `handbook/entries/generic-classes.qmd:20:284`; `handbook/entries/generic-methods.qmd:42:296`; `lectures/05-generics.qmd:1180:45`; `practice/generics.qmd:60:41` |
| `sec-h-wildcards-access` (9) | `handbook/entries/wildcards.qmd#sec-j25-api-wildcards-access` | U03 | — |
| `sec-h-wildcards-copy` (37) | `handbook/entries/wildcards.qmd#sec-j25-api-wildcards-copy` | U03 | — |
| `sec-h-wildcards-list-api` (52) | `handbook/entries/lists.qmd#sec-j25-api-lists-basics` | U04 | — |
| `sec-h-wildcards-input` (71) | `handbook/entries/wildcards.qmd#sec-j25-api-wildcards-input` | U03 | — |

### handbook/index.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-h-program` (1) | `handbook/index.qmd#sec-j25-handbook` | U00 | `index.qmd:11:313`; `tasks/index.qmd:11:114`; `theory/index.qmd:19:146` |

### index.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-course-program` (1) | `index.qmd#sec-j25-course` | U00 | — |
| `sec-course-route` (5) | `index.qmd#sec-j25-course-route` | U00 | — |

### lectures/01-platform.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-l-01-platform` (12) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime` | U07 | `_includes/semester-map.qmd:3:144`; `lectures/index.qmd:11:21` |
| `sec-l-01-platform-meaning` (20) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-meaning` | U07 | — |
| `sec-l-01-platform-average` (22) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-average` | U07 | — |
| `sec-l-01-platform-conversion` (40) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-conversion` | U07 | — |
| `sec-l-01-platform-python-division` (59) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-python-division` | U07 | `lectures/02-values.qmd:363:48` |
| `sec-l-01-platform-three-levels` (75) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-three-levels` | U07 | — |
| `sec-l-01-platform-failure-stage` (92) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-failure-stage` | U07 | — |
| `sec-l-01-platform-correctness` (107) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-correctness` | U07 | — |
| `sec-l-01-platform-abstraction` (123) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-abstraction` | U07 | — |
| `sec-l-01-platform-guarantee` (133) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-guarantee` | U07 | — |
| `sec-l-01-platform-history` (145) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-history` | U07 | — |
| `sec-l-01-platform-target-machine` (147) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-target-machine` | U07 | — |
| `sec-l-01-platform-c` (159) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-c` | U07 | — |
| `sec-l-01-platform-cpp` (171) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-cpp` | U07 | — |
| `sec-l-01-platform-object-lifetime` (185) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-object-lifetime` | U07 | — |
| `sec-l-01-platform-java-origins` (199) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-java-origins` | U07 | — |
| `sec-l-01-platform-portability-design` (211) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-portability-design` | U07 | — |
| `sec-l-01-platform-syntax-limits` (223) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-syntax-limits` | U07 | — |
| `sec-l-01-platform-paradigms` (239) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-paradigms` | U07 | — |
| `sec-l-01-platform-static-dynamic` (251) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-static-dynamic` | U07 | — |
| `sec-l-01-platform-toolchain` (269) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-toolchain` | U07 | — |
| `sec-l-01-platform-platform-parts` (271) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-platform-parts` | U07 | — |
| `sec-l-01-platform-other-languages` (285) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-other-languages` | U07 | — |
| `sec-l-01-platform-clr` (297) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-clr` | U07 | — |
| `sec-l-01-platform-source` (307) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-source` | U07 | — |
| `sec-l-01-platform-compile` (327) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-compile` | U07 | — |
| `sec-l-01-platform-classfile` (341) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-classfile` | U07 | — |
| `sec-l-01-platform-run` (353) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-run` | U07 | — |
| `sec-l-01-platform-stale` (367) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-stale` | U07 | — |
| `sec-l-01-platform-package` (383) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-package` | U07 | — |
| `sec-l-01-platform-classpath-root` (403) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-classpath-root` | U07 | — |
| `sec-l-01-platform-dependencies` (419) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-dependencies` | U07 | — |
| `sec-l-01-platform-vm` (431) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-vm` | U07 | — |
| `sec-l-01-platform-bytecode` (433) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-bytecode` | U07 | — |
| `sec-l-01-platform-managed` (445) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-managed` | U07 | — |
| `sec-l-01-platform-resource` (455) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-resource` | U07 | — |
| `sec-l-01-platform-layout` (463) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-layout` | U07 | — |
| `sec-l-01-platform-javap` (471) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-javap` | U07 | — |
| `sec-l-01-platform-operand-stack` (485) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-operand-stack` | U07 | — |
| `sec-l-01-platform-loading` (501) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-loading` | U07 | — |
| `sec-l-01-platform-verification` (513) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-verification` | U07 | — |
| `sec-l-01-platform-linking` (525) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-linking` | U07 | — |
| `sec-l-01-platform-initialization` (537) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-initialization` | U07 | — |
| `sec-l-01-platform-jit` (549) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-jit` | U07 | — |
| `sec-l-01-platform-performance` (561) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-performance` | U07 | — |
| `sec-l-01-platform-versions` (573) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-versions` | U07 | — |
| `sec-l-01-platform-compatibility` (575) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-compatibility` | U07 | — |
| `sec-l-01-platform-method-evolution` (585) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-method-evolution` | U07 | — |
| `sec-l-01-platform-behavior` (593) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-behavior` | U07 | — |
| `sec-l-01-platform-evolution` (601) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-evolution` | U07 | — |
| `sec-l-01-platform-two-versions` (609) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-two-versions` | U07 | — |
| `sec-l-01-platform-release` (624) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-release` | U07 | — |
| `sec-l-01-platform-library-version` (639) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-library-version` | U07 | — |
| `sec-l-01-platform-ide` (651) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-ide` | U07 | — |
| `sec-l-01-platform-portability-limits` (663) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-portability-limits` | U07 | — |
| `sec-l-01-platform-reconstruct` (678) | `lectures/platform-runtime.qmd#sec-j25-lecture-platform-runtime-reconstruct` | U07 | — |

### lectures/02-values.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-l-02-values` (12) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence` | U07 | `_includes/semester-map.qmd:4:150`; `_includes/semester-map.qmd:5:156`; `_includes/semester-map.qmd:6:80`; `_includes/semester-map.qmd:7:76`; `_includes/semester-map.qmd:15:192`; `lectures/01-platform.qmd:56:90`; `lectures/index.qmd:12:21` |
| `sec-l-02-values-types` (20) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-types` | U07 | — |
| `sec-l-02-values-data-model` (22) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-data-model` | U07 | — |
| `sec-l-02-values-number-text` (38) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-number-text` | U07 | — |
| `sec-l-02-values-type-contract` (55) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-type-contract` | U07 | — |
| `sec-l-02-values-python-binding` (71) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-python-binding` | U07 | — |
| `sec-l-02-values-average-type` (88) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-average-type` | U07 | — |
| `sec-l-02-values-primitive-types` (105) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-primitive-types` | U07 | — |
| `sec-l-02-values-portable-numbers` (120) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-portable-numbers` | U07 | — |
| `sec-l-02-values-boolean-char` (135) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-boolean-char` | U07 | — |
| `sec-l-02-values-literals` (151) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-literals` | U07 | — |
| `sec-l-02-values-definite-assignment` (168) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-definite-assignment` | U07 | — |
| `sec-l-02-values-branch-assignment` (185) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-branch-assignment` | U07 | — |
| `sec-l-02-values-final` (203) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-final` | U07 | — |
| `sec-l-02-values-inference` (219) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-inference` | U07 | — |
| `sec-l-02-values-inference-design` (236) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-inference-design` | U07 | — |
| `sec-l-02-values-conversions` (254) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-conversions` | U07 | — |
| `sec-l-02-values-widening` (256) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-widening` | U07 | — |
| `sec-l-02-values-narrowing` (272) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-narrowing` | U07 | — |
| `sec-l-02-values-promotion` (287) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-promotion` | U07 | — |
| `sec-l-02-values-overflow` (304) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-overflow` | U07 | — |
| `sec-l-02-values-early-long` (319) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-early-long` | U07 | — |
| `sec-l-02-values-exact` (334) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-exact` | U07 | — |
| `sec-l-02-values-division` (350) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-division` | U07 | — |
| `sec-l-02-values-floating` (366) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-floating` | U07 | — |
| `sec-l-02-values-numeric-choice` (379) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-numeric-choice` | U07 | — |
| `sec-l-02-values-special-floating` (395) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-special-floating` | U07 | — |
| `sec-l-02-values-expressions-control` (409) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-expressions-control` | U07 | — |
| `sec-l-02-values-precedence` (411) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-precedence` | U07 | — |
| `sec-l-02-values-increment` (423) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-increment` | U07 | — |
| `sec-l-02-values-null-model` (440) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-null-model` | U07 | — |
| `sec-l-02-values-null-assignment` (458) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-null-assignment` | U07 | — |
| `sec-l-02-values-short-circuit` (475) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-short-circuit` | U07 | — |
| `sec-l-02-values-if` (491) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-if` | U07 | — |
| `sec-l-02-values-guard` (509) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-guard` | U07 | — |
| `sec-l-02-values-switch` (526) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-switch` | U07 | — |
| `sec-l-02-values-while` (543) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-while` | U07 | — |
| `sec-l-02-values-for` (559) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-for` | U07 | — |
| `sec-l-02-values-invariant` (576) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-invariant` | U07 | — |
| `sec-l-02-values-enhanced-for` (588) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-enhanced-for` | U07 | — |
| `sec-l-02-values-transfers` (604) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-transfers` | U07 | — |
| `sec-l-02-values-copying` (620) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-copying` | U07 | — |
| `sec-l-02-values-copy-model` (622) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-copy-model` | U07 | — |
| `sec-l-02-values-clr-model` (641) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-clr-model` | U07 | — |
| `sec-l-02-values-boxing-model` (656) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-boxing-model` | U07 | — |
| `sec-l-02-values-unboxing-null` (672) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-unboxing-null` | U07 | — |
| `sec-l-02-values-shallow-value` (689) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-shallow-value` | U07 | — |
| `sec-l-02-values-python-copy` (702) | `lectures/values-absence.qmd#sec-j25-lecture-values-absence-python-copy` | U07 | — |
| `sec-l-02-values-methods` (719) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-methods` | U07 | — |
| `sec-l-02-values-method` (721) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-method` | U07 | — |
| `sec-l-02-values-argument` (740) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-argument` | U07 | — |
| `sec-l-02-values-primitive-parameter` (757) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-primitive-parameter` | U07 | — |
| `sec-l-02-values-shared-array` (773) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-shared-array` | U07 | — |
| `sec-l-02-values-rebinding` (789) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-rebinding` | U07 | — |
| `sec-l-02-values-arrays` (805) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-arrays` | U07 | — |
| `sec-l-02-values-array-creation` (807) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-array-creation` | U07 | — |
| `sec-l-02-values-array-empty` (823) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-array-empty` | U07 | — |
| `sec-l-02-values-array-copy` (839) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-array-copy` | U07 | — |
| `sec-l-02-values-array-rows` (855) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-array-rows` | U07 | — |
| `sec-l-02-values-text` (872) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-text` | U07 | — |
| `sec-l-02-values-string-immutability` (874) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-string-immutability` | U07 | — |
| `sec-l-02-values-string-equality` (892) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-string-equality` | U07 | — |
| `sec-l-02-values-unicode` (907) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-unicode` | U07 | — |
| `sec-l-02-values-encoding` (923) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-encoding` | U07 | — |
| `sec-l-02-values-parsing` (939) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-parsing` | U07 | — |
| `sec-l-02-values-transfer-check` (956) | `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data-transfer-check` | U07 | — |

### lectures/03-classes.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-l-03-classes` (15) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants` | U07 | `_includes/semester-map.qmd:8:141`; `_includes/semester-map.qmd:16:205`; `lectures/index.qmd:13:21` |
| `sec-l-03-classes-objects` (27) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-objects` | U07 | — |
| `sec-l-03-classes-new` (44) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-new` | U07 | — |
| `sec-l-03-classes-declaration` (57) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-declaration` | U07 | — |
| `sec-l-03-classes-references` (72) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-references` | U07 | — |
| `sec-l-03-classes-aliases` (79) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-aliases` | U07 | — |
| `sec-l-03-classes-identity` (95) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-identity` | U07 | — |
| `sec-l-03-classes-domain-identity` (110) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-domain-identity` | U07 | — |
| `sec-l-03-classes-reassignment` (121) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-reassignment` | U07 | — |
| `sec-l-03-classes-null` (136) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-null` | U07 | — |
| `sec-l-03-classes-null-contract` (150) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-null-contract` | U07 | — |
| `sec-l-03-classes-nullable-comparison` (166) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-nullable-comparison` | U07 | — |
| `sec-l-03-classes-arguments` (183) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-arguments` | U07 | — |
| `sec-l-03-classes-replace` (197) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-replace` | U07 | — |
| `sec-l-03-classes-return` (214) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-return` | U07 | — |
| `sec-l-03-classes-copy` (231) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-copy` | U07 | — |
| `sec-l-03-classes-deep-copy` (245) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-deep-copy` | U07 | — |
| `sec-l-03-classes-shared-copy` (252) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-shared-copy` | U07 | — |
| `sec-l-03-classes-lifetime` (269) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-lifetime` | U07 | — |
| `sec-l-03-classes-resources` (284) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-resources` | U07 | — |
| `sec-l-03-classes-invariant` (294) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-invariant` | U07 | — |
| `sec-l-03-classes-bad-fields` (310) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-bad-fields` | U07 | — |
| `sec-l-03-classes-private-fields` (328) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-private-fields` | U07 | — |
| `sec-l-03-classes-construct` (341) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-construct` | U07 | — |
| `sec-l-03-classes-available` (358) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-available` | U07 | — |
| `sec-l-03-classes-reserve` (369) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-reserve` | U07 | — |
| `sec-l-03-classes-reservation-result` (386) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-reservation-result` | U07 | — |
| `sec-l-03-classes-setter` (401) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-setter` | U07 | — |
| `sec-l-03-classes-constructors` (411) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-constructors` | U07 | — |
| `sec-l-03-classes-this` (426) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-this` | U07 | — |
| `sec-l-03-classes-delegation` (442) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-delegation` | U07 | — |
| `sec-l-03-classes-validation` (456) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-validation` | U07 | — |
| `sec-l-03-classes-java25` (467) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-java25` | U07 | — |
| `sec-l-03-classes-methods` (481) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-methods` | U07 | — |
| `sec-l-03-classes-overload` (492) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-overload` | U07 | — |
| `sec-l-03-classes-null-overload` (504) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-null-overload` | U07 | — |
| `sec-l-03-classes-static` (519) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-static` | U07 | — |
| `sec-l-03-classes-static-result` (532) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-static-result` | U07 | — |
| `sec-l-03-classes-static-this` (547) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-static-this` | U07 | — |
| `sec-l-03-classes-final` (556) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-final` | U07 | — |
| `sec-l-03-classes-input-alias` (570) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-input-alias` | U07 | — |
| `sec-l-03-classes-snapshot` (585) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-snapshot` | U07 | — |
| `sec-l-03-classes-immutable` (602) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-immutable` | U07 | — |
| `sec-l-03-classes-access` (609) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-access` | U07 | — |
| `sec-l-03-classes-initialization` (621) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-initialization` | U07 | — |
| `sec-l-03-classes-escape` (640) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-escape` | U07 | — |
| `sec-l-03-classes-python` (647) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-python` | U07 | — |
| `sec-l-03-classes-prediction` (654) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-prediction` | U07 | — |
| `sec-l-03-classes-design-check` (670) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-design-check` | U07 | — |
| `sec-l-03-classes-route` (680) | `lectures/objects-invariants.qmd#sec-j25-lecture-objects-invariants-route` | U07 | — |

### lectures/04-object-design.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-l-04-object-design` (15) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records` | U07 | `_includes/semester-map.qmd:9:219`; `_includes/semester-map.qmd:16:261`; `lectures/index.qmd:14:21` |
| `sec-l-04-object-design-subtype` (26) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-subtype` | U07 | — |
| `sec-l-04-object-design-base` (36) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-base` | U07 | — |
| `sec-l-04-object-design-child` (51) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-child` | U07 | — |
| `sec-l-04-object-design-override` (71) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-override` | U07 | — |
| `sec-l-04-object-design-constructors` (86) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-constructors` | U07 | — |
| `sec-l-04-object-design-dispatch` (100) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-dispatch` | U07 | — |
| `sec-l-04-object-design-general-method` (113) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-general-method` | U07 | — |
| `sec-l-04-object-design-cpp` (129) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-cpp` | U07 | — |
| `sec-l-04-object-design-overload` (136) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-overload` | U07 | — |
| `sec-l-04-object-design-field-hiding` (152) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-field-hiding` | U07 | — |
| `sec-l-04-object-design-static-hiding` (167) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-static-hiding` | U07 | — |
| `sec-l-04-object-design-override-rules` (179) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-override-rules` | U07 | — |
| `sec-l-04-object-design-behavior` (186) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-behavior` | U07 | — |
| `sec-l-04-object-design-broken-contract` (196) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-broken-contract` | U07 | — |
| `sec-l-04-object-design-rectangle` (212) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-rectangle` | U07 | — |
| `sec-l-04-object-design-abstract` (222) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-abstract` | U07 | — |
| `sec-l-04-object-design-square` (234) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-square` | U07 | — |
| `sec-l-04-object-design-construction-risk` (249) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-construction-risk` | U07 | — |
| `sec-l-04-object-design-construction-order` (268) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-construction-order` | U07 | — |
| `sec-l-04-object-design-changing-requirements` (275) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-changing-requirements` | U07 | — |
| `sec-l-04-object-design-interface` (292) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-interface` | U07 | — |
| `sec-l-04-object-design-implementation` (306) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-implementation` | U07 | — |
| `sec-l-04-object-design-composition` (322) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-composition` | U07 | — |
| `sec-l-04-object-design-reminder-output` (340) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-reminder-output` | U07 | — |
| `sec-l-04-object-design-recording` (350) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-recording` | U07 | — |
| `sec-l-04-object-design-recording-result` (367) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-recording-result` | U07 | — |
| `sec-l-04-object-design-decorator` (381) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-decorator` | U07 | — |
| `sec-l-04-object-design-decorator-output` (396) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-decorator-output` | U07 | — |
| `sec-l-04-object-design-ownership` (410) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-ownership` | U07 | — |
| `sec-l-04-object-design-changing-output` (417) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-changing-output` | U07 | — |
| `sec-l-04-object-design-multiple-roles` (432) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-multiple-roles` | U07 | — |
| `sec-l-04-object-design-default` (446) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-default` | U07 | — |
| `sec-l-04-object-design-default-failure` (460) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-default-failure` | U07 | — |
| `sec-l-04-object-design-default-conflict` (476) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-default-conflict` | U07 | — |
| `sec-l-04-object-design-interface-members` (492) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-interface-members` | U07 | — |
| `sec-l-04-object-design-choice` (499) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-choice` | U07 | — |
| `sec-l-04-object-design-data-values` (509) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-data-values` | U07 | — |
| `sec-l-04-object-design-record` (516) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-record` | U07 | — |
| `sec-l-04-object-design-record-output` (533) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-record-output` | U07 | — |
| `sec-l-04-object-design-record-constraints` (542) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-record-constraints` | U07 | — |
| `sec-l-04-object-design-record-model` (549) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-record-model` | U07 | — |
| `sec-l-04-object-design-compact-constructor` (561) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-compact-constructor` | U07 | — |
| `sec-l-04-object-design-normalization` (578) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-normalization` | U07 | — |
| `sec-l-04-object-design-shallow` (596) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-shallow` | U07 | — |
| `sec-l-04-object-design-snapshot` (613) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-snapshot` | U07 | — |
| `sec-l-04-object-design-array-record` (627) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-array-record` | U07 | — |
| `sec-l-04-object-design-enum` (637) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-enum` | U07 | — |
| `sec-l-04-object-design-enum-code` (648) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-enum-code` | U07 | — |
| `sec-l-04-object-design-sealed` (665) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-sealed` | U07 | — |
| `sec-l-04-object-design-exhaustive` (676) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-exhaustive` | U07 | — |
| `sec-l-04-object-design-search-evolution` (693) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-search-evolution` | U07 | — |
| `sec-l-04-object-design-search-coverage` (708) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-search-coverage` | U07 | — |
| `sec-l-04-object-design-design-route` (725) | `lectures/interfaces-records.qmd#sec-j25-lecture-interfaces-records-design-route` | U07 | — |

### lectures/05-generics.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-l-05-generics` (16) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance` | U07 | `_includes/semester-map.qmd:10:155`; `lectures/index.qmd:15:21` |
| `sec-l-05-generics-object` (35) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-object` | U07 | — |
| `sec-l-05-generics-object-cast` (59) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-object-cast` | U07 | — |
| `sec-l-05-generics-object-failure` (81) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-object-failure` | U07 | — |
| `sec-l-05-generics-type-parameter` (105) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-type-parameter` | U07 | — |
| `sec-l-05-generics-type-inference` (130) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-type-inference` | U07 | — |
| `sec-l-05-generics-reference-arguments` (155) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-reference-arguments` | U07 | — |
| `sec-l-05-generics-sharing` (178) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-sharing` | U07 | — |
| `sec-l-05-generics-independent-parameters` (194) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-independent-parameters` | U07 | — |
| `sec-l-05-generics-methods` (218) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-methods` | U07 | — |
| `sec-l-05-generics-method-inference` (243) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-method-inference` | U07 | — |
| `sec-l-05-generics-number-bound` (269) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-number-bound` | U07 | — |
| `sec-l-05-generics-comparable-bound` (293) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-comparable-bound` | U07 | — |
| `sec-l-05-generics-invariance` (318) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-invariance` | U07 | — |
| `sec-l-05-generics-array-covariance` (343) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-array-covariance` | U07 | — |
| `sec-l-05-generics-unbounded-wildcard` (366) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-unbounded-wildcard` | U07 | — |
| `sec-l-05-generics-extends-wildcard` (391) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-extends-wildcard` | U07 | — |
| `sec-l-05-generics-super-wildcard` (417) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-super-wildcard` | U07 | — |
| `sec-l-05-generics-copy` (444) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-copy` | U07 | — |
| `sec-l-05-generics-wildcard-contract` (470) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-wildcard-contract` | U07 | — |
| `sec-l-05-generics-capture` (493) | `lectures/generics-variance.qmd#sec-j25-lecture-generics-variance-capture` | U07 | — |
| `sec-l-05-generics-java-one` (509) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-java-one` | U07 | — |
| `sec-l-05-generics-gj` (531) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-gj` | U07 | — |
| `sec-l-05-generics-jsr-fourteen` (553) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-jsr-fourteen` | U07 | — |
| `sec-l-05-generics-java-five` (576) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-java-five` | U07 | — |
| `sec-l-05-generics-erasure` (598) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-erasure` | U07 | — |
| `sec-l-05-generics-erased-read` (625) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-erased-read` | U07 | — |
| `sec-l-05-generics-signature` (649) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-signature` | U07 | — |
| `sec-l-05-generics-bridge` (673) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-bridge` | U07 | — |
| `sec-l-05-generics-runtime-limits` (691) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-runtime-limits` | U07 | — |
| `sec-l-05-generics-raw-types` (714) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-raw-types` | U07 | — |
| `sec-l-05-generics-heap-pollution` (739) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-heap-pollution` | U07 | — |
| `sec-l-05-generics-migration-consequences` (762) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-migration-consequences` | U07 | — |
| `sec-l-05-generics-arithmetic-problem` (784) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-arithmetic-problem` | U07 | — |
| `sec-l-05-generics-arithmetic-interface` (806) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-arithmetic-interface` | U07 | — |
| `sec-l-05-generics-java-dot` (830) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-java-dot` | U07 | — |
| `sec-l-05-generics-double-arithmetic` (855) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-double-arithmetic` | U07 | — |
| `sec-l-05-generics-complex-addition` (880) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-complex-addition` | U07 | — |
| `sec-l-05-generics-arithmetic-laws` (892) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-arithmetic-laws` | U07 | — |
| `sec-l-05-generics-cpp-templates` (913) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-cpp-templates` | U07 | — |
| `sec-l-05-generics-cpp-concept` (934) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-cpp-concept` | U07 | — |
| `sec-l-05-generics-cpp-dot` (960) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-cpp-dot` | U07 | — |
| `sec-l-05-generics-cpp-calls` (988) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-cpp-calls` | U07 | — |
| `sec-l-05-generics-cpp-specialization` (1012) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-cpp-specialization` | U07 | — |
| `sec-l-05-generics-csharp-runtime` (1035) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-csharp-runtime` | U07 | — |
| `sec-l-05-generics-csharp-operations` (1057) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-csharp-operations` | U07 | — |
| `sec-l-05-generics-csharp-dot` (1081) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-csharp-dot` | U07 | — |
| `sec-l-05-generics-csharp-contract` (1109) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-csharp-contract` | U07 | — |
| `sec-l-05-generics-language-comparison` (1130) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-language-comparison` | U07 | — |
| `sec-l-05-generics-model-comparison` (1148) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-model-comparison` | U07 | — |
| `sec-l-05-generics-api-design` (1168) | `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-api-design` | U07 | — |

### lectures/06-collections.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-l-06-collections` (14) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections` | U07 | `_includes/semester-map.qmd:11:151`; `_includes/semester-map.qmd:17:199`; `lectures/index.qmd:16:21` |
| `sec-l-06-collections-group-1` (24) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-group-1` | U07 | — |
| `sec-l-06-collections-step-01` (30) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-01` | U07 | — |
| `sec-l-06-collections-step-02` (41) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-02` | U07 | — |
| `sec-l-06-collections-step-03` (51) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-03` | U07 | — |
| `sec-l-06-collections-step-04` (61) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-04` | U07 | — |
| `sec-l-06-collections-step-05` (73) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-05` | U07 | — |
| `sec-l-06-collections-step-06` (83) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-06` | U07 | — |
| `sec-l-06-collections-step-07` (92) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-07` | U07 | — |
| `sec-l-06-collections-value-version` (103) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-value-version` | U07 | — |
| `sec-l-06-collections-step-08` (116) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-08` | U07 | — |
| `sec-l-06-collections-step-09` (127) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-09` | U07 | — |
| `sec-l-06-collections-group-2` (137) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-group-2` | U07 | — |
| `sec-l-06-collections-step-10` (143) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-10` | U07 | — |
| `sec-l-06-collections-step-11` (152) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-11` | U07 | — |
| `sec-l-06-collections-step-12` (166) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-12` | U07 | — |
| `sec-l-06-collections-step-13` (176) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-13` | U07 | — |
| `sec-l-06-collections-step-14` (187) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-14` | U07 | — |
| `sec-l-06-collections-step-15` (197) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-15` | U07 | — |
| `sec-l-06-collections-group-3` (208) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-group-3` | U07 | — |
| `sec-l-06-collections-step-16` (214) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-16` | U07 | — |
| `sec-l-06-collections-step-17` (227) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-17` | U07 | — |
| `sec-l-06-collections-step-18` (237) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-18` | U07 | — |
| `sec-l-06-collections-step-19` (247) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-19` | U07 | — |
| `sec-l-06-collections-step-20` (257) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-20` | U07 | — |
| `sec-l-06-collections-step-21` (266) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-21` | U07 | — |
| `sec-l-06-collections-step-22` (277) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-22` | U07 | — |
| `sec-l-06-collections-step-23` (288) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-23` | U07 | — |
| `sec-l-06-collections-step-24` (297) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-24` | U07 | — |
| `sec-l-06-collections-step-25` (309) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-25` | U07 | — |
| `sec-l-06-collections-group-4` (319) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-group-4` | U07 | — |
| `sec-l-06-collections-step-26` (325) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-26` | U07 | — |
| `sec-l-06-collections-step-27` (336) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-27` | U07 | — |
| `sec-l-06-collections-step-28` (347) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-28` | U07 | — |
| `sec-l-06-collections-data-model` (356) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-data-model` | U07 | — |
| `sec-l-06-collections-step-29` (369) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-29` | U07 | — |
| `sec-l-06-collections-step-30` (380) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-30` | U07 | — |
| `sec-l-06-collections-step-31` (390) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-31` | U07 | — |
| `sec-l-06-collections-step-32` (401) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-32` | U07 | — |
| `sec-l-06-collections-step-33` (413) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-33` | U07 | — |
| `sec-l-06-collections-step-34` (426) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-34` | U07 | — |
| `sec-l-06-collections-step-35` (436) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-35` | U07 | — |
| `sec-l-06-collections-group-5` (448) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-group-5` | U07 | — |
| `sec-l-06-collections-step-36` (454) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-36` | U07 | — |
| `sec-l-06-collections-step-37` (464) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-37` | U07 | — |
| `sec-l-06-collections-step-38` (475) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-38` | U07 | — |
| `sec-l-06-collections-step-39` (486) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-39` | U07 | — |
| `sec-l-06-collections-step-40` (498) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-40` | U07 | — |
| `sec-l-06-collections-snapshot-owner` (509) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-snapshot-owner` | U07 | — |
| `sec-l-06-collections-step-41` (521) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-41` | U07 | — |
| `sec-l-06-collections-step-42` (532) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-42` | U07 | — |
| `sec-l-06-collections-step-43` (542) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-43` | U07 | — |
| `sec-l-06-collections-step-44` (553) | `lectures/equality-collections.qmd#sec-j25-lecture-equality-collections-step-44` | U07 | — |

### lectures/07-functions.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-l-07-functions` (14) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams` | U07 | `_includes/semester-map.qmd:12:166`; `_includes/semester-map.qmd:17:259`; `lectures/index.qmd:17:21` |
| `sec-l-07-functions-group-1` (24) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-group-1` | U07 | — |
| `sec-l-07-functions-step-01` (30) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-01` | U07 | — |
| `sec-l-07-functions-step-02` (42) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-02` | U07 | — |
| `sec-l-07-functions-step-03` (54) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-03` | U07 | — |
| `sec-l-07-functions-step-04` (67) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-04` | U07 | — |
| `sec-l-07-functions-step-05` (76) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-05` | U07 | — |
| `sec-l-07-functions-step-06` (85) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-06` | U07 | — |
| `sec-l-07-functions-step-07` (94) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-07` | U07 | — |
| `sec-l-07-functions-step-08` (104) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-08` | U07 | — |
| `sec-l-07-functions-step-09` (115) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-09` | U07 | — |
| `sec-l-07-functions-step-10` (124) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-10` | U07 | — |
| `sec-l-07-functions-group-2` (135) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-group-2` | U07 | — |
| `sec-l-07-functions-step-11` (141) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-11` | U07 | — |
| `sec-l-07-functions-step-12` (153) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-12` | U07 | — |
| `sec-l-07-functions-step-13` (162) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-13` | U07 | — |
| `sec-l-07-functions-step-14` (172) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-14` | U07 | — |
| `sec-l-07-functions-step-15` (182) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-15` | U07 | — |
| `sec-l-07-functions-step-16` (193) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-16` | U07 | — |
| `sec-l-07-functions-group-3` (206) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-group-3` | U07 | — |
| `sec-l-07-functions-step-17` (212) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-17` | U07 | — |
| `sec-l-07-functions-step-18` (221) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-18` | U07 | — |
| `sec-l-07-functions-step-19` (232) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-19` | U07 | — |
| `sec-l-07-functions-step-20` (243) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-20` | U07 | — |
| `sec-l-07-functions-returned-rule` (254) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-returned-rule` | U07 | — |
| `sec-l-07-functions-live-setting` (266) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-live-setting` | U07 | — |
| `sec-l-07-functions-step-21` (281) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-21` | U07 | — |
| `sec-l-07-functions-step-22` (291) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-22` | U07 | — |
| `sec-l-07-functions-step-23` (302) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-23` | U07 | — |
| `sec-l-07-functions-group-4` (312) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-group-4` | U07 | — |
| `sec-l-07-functions-step-24` (318) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-24` | U07 | — |
| `sec-l-07-functions-step-25` (330) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-25` | U07 | — |
| `sec-l-07-functions-step-26` (342) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-26` | U07 | — |
| `sec-l-07-functions-report-type` (354) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-report-type` | U07 | — |
| `sec-l-07-functions-step-27` (369) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-27` | U07 | — |
| `sec-l-07-functions-step-28` (378) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-28` | U07 | — |
| `sec-l-07-functions-step-29` (389) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-29` | U07 | — |
| `sec-l-07-functions-step-30` (398) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-30` | U07 | — |
| `sec-l-07-functions-step-31` (409) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-31` | U07 | — |
| `sec-l-07-functions-step-32` (419) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-32` | U07 | — |
| `sec-l-07-functions-step-33` (428) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-33` | U07 | — |
| `sec-l-07-functions-stage-order` (438) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-stage-order` | U07 | — |
| `sec-l-07-functions-step-34` (451) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-34` | U07 | — |
| `sec-l-07-functions-step-35` (462) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-35` | U07 | — |
| `sec-l-07-functions-step-36` (472) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-36` | U07 | — |
| `sec-l-07-functions-step-37` (482) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-37` | U07 | — |
| `sec-l-07-functions-step-38` (491) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-38` | U07 | — |
| `sec-l-07-functions-step-39` (504) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-39` | U07 | — |
| `sec-l-07-functions-step-40` (514) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-40` | U07 | — |
| `sec-l-07-functions-step-41` (524) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-41` | U07 | — |
| `sec-l-07-functions-absence-error` (534) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-absence-error` | U07 | — |
| `sec-l-07-functions-step-42` (547) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-42` | U07 | — |
| `sec-l-07-functions-step-43` (557) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-43` | U07 | — |
| `sec-l-07-functions-step-44` (566) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-step-44` | U07 | — |
| `sec-l-07-functions-publication-effect` (576) | `lectures/functions-streams.qmd#sec-j25-lecture-functions-streams-publication-effect` | U07 | — |

### lectures/08-exceptions.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-l-08-exceptions` (14) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources` | U07 | `_includes/semester-map.qmd:13:150`; `lectures/index.qmd:18:21` |
| `sec-l-08-exceptions-group-1` (24) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-group-1` | U07 | — |
| `sec-l-08-exceptions-contract` (30) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-contract` | U07 | — |
| `sec-l-08-exceptions-step-01` (39) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-01` | U07 | — |
| `sec-l-08-exceptions-step-02` (49) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-02` | U07 | — |
| `sec-l-08-exceptions-step-03` (60) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-03` | U07 | — |
| `sec-l-08-exceptions-step-04` (69) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-04` | U07 | — |
| `sec-l-08-exceptions-step-05` (80) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-05` | U07 | — |
| `sec-l-08-exceptions-step-06` (93) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-06` | U07 | — |
| `sec-l-08-exceptions-step-07` (104) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-07` | U07 | — |
| `sec-l-08-exceptions-step-08` (114) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-08` | U07 | — |
| `sec-l-08-exceptions-results` (122) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-results` | U07 | — |
| `sec-l-08-exceptions-results-interface` (129) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-results-interface` | U07 | — |
| `sec-l-08-exceptions-results-types` (141) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-results-types` | U07 | — |
| `sec-l-08-exceptions-results-adapter` (153) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-results-adapter` | U07 | — |
| `sec-l-08-exceptions-results-inputs` (167) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-results-inputs` | U07 | — |
| `sec-l-08-exceptions-results-switch` (179) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-results-switch` | U07 | — |
| `sec-l-08-exceptions-rust-result` (193) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-rust-result` | U07 | — |
| `sec-l-08-exceptions-go-result` (209) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-go-result` | U07 | — |
| `sec-l-08-exceptions-results-recovery` (225) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-results-recovery` | U07 | — |
| `sec-l-08-exceptions-group-2` (237) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-group-2` | U07 | — |
| `sec-l-08-exceptions-step-09` (243) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-09` | U07 | — |
| `sec-l-08-exceptions-step-10` (256) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-10` | U07 | — |
| `sec-l-08-exceptions-step-12` (269) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-12` | U07 | — |
| `sec-l-08-exceptions-checked-history` (277) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-checked-history` | U07 | — |
| `sec-l-08-exceptions-step-13` (287) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-13` | U07 | — |
| `sec-l-08-exceptions-step-14` (298) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-14` | U07 | — |
| `sec-l-08-exceptions-step-17` (310) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-17` | U07 | — |
| `sec-l-08-exceptions-step-18` (322) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-18` | U07 | — |
| `sec-l-08-exceptions-step-20` (335) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-20` | U07 | — |
| `sec-l-08-exceptions-group-3` (344) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-group-3` | U07 | — |
| `sec-l-08-exceptions-step-22` (351) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-22` | U07 | — |
| `sec-l-08-exceptions-step-23` (363) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-23` | U07 | — |
| `sec-l-08-exceptions-step-24` (373) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-24` | U07 | — |
| `sec-l-08-exceptions-step-25` (382) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-25` | U07 | — |
| `sec-l-08-exceptions-lifetimes` (390) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-lifetimes` | U07 | — |
| `sec-l-08-exceptions-finalization` (401) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-finalization` | U07 | — |
| `sec-l-08-exceptions-step-26` (409) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-26` | U07 | — |
| `sec-l-08-exceptions-step-27` (420) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-27` | U07 | — |
| `sec-l-08-exceptions-step-28` (431) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-28` | U07 | — |
| `sec-l-08-exceptions-step-29` (442) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-29` | U07 | — |
| `sec-l-08-exceptions-step-30` (454) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-30` | U07 | — |
| `sec-l-08-exceptions-step-31` (467) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-31` | U07 | — |
| `sec-l-08-exceptions-group-4` (479) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-group-4` | U07 | — |
| `sec-l-08-exceptions-step-32` (485) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-32` | U07 | — |
| `sec-l-08-exceptions-step-33` (495) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-33` | U07 | — |
| `sec-l-08-exceptions-step-34` (508) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-34` | U07 | — |
| `sec-l-08-exceptions-step-35` (518) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-35` | U07 | — |
| `sec-l-08-exceptions-step-36` (531) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-36` | U07 | — |
| `sec-l-08-exceptions-step-38` (541) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-38` | U07 | — |
| `sec-l-08-exceptions-group-5` (552) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-group-5` | U07 | — |
| `sec-l-08-exceptions-cleanup-models` (558) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-cleanup-models` | U07 | — |
| `sec-l-08-exceptions-step-39` (575) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-39` | U07 | — |
| `sec-l-08-exceptions-step-40` (586) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-40` | U07 | — |
| `sec-l-08-exceptions-results-state` (596) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-results-state` | U07 | — |
| `sec-l-08-exceptions-return-close` (604) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-return-close` | U07 | — |
| `sec-l-08-exceptions-return-close-errors` (615) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-return-close-errors` | U07 | — |
| `sec-l-08-exceptions-step-41` (629) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-41` | U07 | — |
| `sec-l-08-exceptions-step-42` (639) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-42` | U07 | — |
| `sec-l-08-exceptions-retry` (652) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-retry` | U07 | — |
| `sec-l-08-exceptions-step-43` (661) | `lectures/exceptions-resources.qmd#sec-j25-lecture-exceptions-resources-step-43` | U07 | — |

### lectures/09-application.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-l-09-application` (14) | `lectures/io-application.qmd#sec-j25-lecture-io-application` | U07 | `_includes/semester-map.qmd:7:131`; `_includes/semester-map.qmd:14:147`; `_includes/semester-map.qmd:17:317`; `index.qmd:11:177`; `lectures/index.qmd:19:21` |
| `sec-l-09-application-group-1` (24) | `lectures/io-application.qmd#sec-j25-lecture-io-application-group-1` | U07 | — |
| `sec-l-09-application-step-01` (30) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-01` | U07 | — |
| `sec-l-09-application-step-02` (42) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-02` | U07 | — |
| `sec-l-09-application-step-03` (53) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-03` | U07 | — |
| `sec-l-09-application-step-04` (62) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-04` | U07 | — |
| `sec-l-09-application-step-05` (74) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-05` | U07 | — |
| `sec-l-09-application-step-06` (84) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-06` | U07 | — |
| `sec-l-09-application-step-07` (96) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-07` | U07 | — |
| `sec-l-09-application-step-08` (108) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-08` | U07 | — |
| `sec-l-09-application-group-2` (119) | `lectures/io-application.qmd#sec-j25-lecture-io-application-group-2` | U07 | — |
| `sec-l-09-application-step-09` (125) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-09` | U07 | — |
| `sec-l-09-application-step-10` (135) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-10` | U07 | — |
| `sec-l-09-application-encoding-size` (145) | `lectures/io-application.qmd#sec-j25-lecture-io-application-encoding-size` | U07 | — |
| `sec-l-09-application-step-11` (158) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-11` | U07 | — |
| `sec-l-09-application-step-12` (168) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-12` | U07 | — |
| `sec-l-09-application-step-13` (177) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-13` | U07 | — |
| `sec-l-09-application-step-14` (186) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-14` | U07 | — |
| `sec-l-09-application-step-15` (195) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-15` | U07 | — |
| `sec-l-09-application-step-16` (205) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-16` | U07 | — |
| `sec-l-09-application-step-17` (215) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-17` | U07 | — |
| `sec-l-09-application-step-18` (228) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-18` | U07 | — |
| `sec-l-09-application-group-3` (239) | `lectures/io-application.qmd#sec-j25-lecture-io-application-group-3` | U07 | — |
| `sec-l-09-application-step-19` (245) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-19` | U07 | — |
| `sec-l-09-application-step-20` (256) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-20` | U07 | — |
| `sec-l-09-application-step-21` (271) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-21` | U07 | — |
| `sec-l-09-application-value-catalog` (281) | `lectures/io-application.qmd#sec-j25-lecture-io-application-value-catalog` | U07 | — |
| `sec-l-09-application-step-22` (293) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-22` | U07 | — |
| `sec-l-09-application-step-23` (304) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-23` | U07 | — |
| `sec-l-09-application-step-24` (314) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-24` | U07 | — |
| `sec-l-09-application-step-25` (325) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-25` | U07 | — |
| `sec-l-09-application-step-26` (335) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-26` | U07 | — |
| `sec-l-09-application-step-27` (346) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-27` | U07 | — |
| `sec-l-09-application-step-28` (358) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-28` | U07 | — |
| `sec-l-09-application-group-4` (368) | `lectures/io-application.qmd#sec-j25-lecture-io-application-group-4` | U07 | — |
| `sec-l-09-application-step-29` (374) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-29` | U07 | — |
| `sec-l-09-application-step-30` (386) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-30` | U07 | — |
| `sec-l-09-application-step-31` (396) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-31` | U07 | — |
| `sec-l-09-application-pure-boundary` (407) | `lectures/io-application.qmd#sec-j25-lecture-io-application-pure-boundary` | U07 | — |
| `sec-l-09-application-step-32` (421) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-32` | U07 | — |
| `sec-l-09-application-step-33` (433) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-33` | U07 | — |
| `sec-l-09-application-step-34` (444) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-34` | U07 | — |
| `sec-l-09-application-step-35` (452) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-35` | U07 | — |
| `sec-l-09-application-step-36` (464) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-36` | U07 | — |
| `sec-l-09-application-step-37` (474) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-37` | U07 | — |
| `sec-l-09-application-empty-success` (486) | `lectures/io-application.qmd#sec-j25-lecture-io-application-empty-success` | U07 | — |
| `sec-l-09-application-step-38` (499) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-38` | U07 | — |
| `sec-l-09-application-group-5` (512) | `lectures/io-application.qmd#sec-j25-lecture-io-application-group-5` | U07 | — |
| `sec-l-09-application-step-39` (518) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-39` | U07 | — |
| `sec-l-09-application-step-40` (528) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-40` | U07 | — |
| `sec-l-09-application-step-41` (540) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-41` | U07 | — |
| `sec-l-09-application-temp-failure` (549) | `lectures/io-application.qmd#sec-j25-lecture-io-application-temp-failure` | U07 | — |
| `sec-l-09-application-step-42` (562) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-42` | U07 | — |
| `sec-l-09-application-step-43` (575) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-43` | U07 | — |
| `sec-l-09-application-step-44` (584) | `lectures/io-application.qmd#sec-j25-lecture-io-application-step-44` | U07 | — |
| `sec-l-09-application-large-input` (594) | `lectures/io-application.qmd#sec-j25-lecture-io-application-large-input` | U07 | — |

### lectures/index.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-l-program` (5) | `lectures/index.qmd#sec-j25-lectures` | U07 | `theory/index.qmd:19:53` |

### practice/arrays.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-p-arrays` (11) | `practice/arrays.qmd#sec-j25-practice-arrays` | U01 | `_includes/semester-map.qmd:6:134`; `practice/index.qmd:11:21`; `tasks/labs/arrays.qmd:14:94`; `tasks/labs/index.qmd:11:82`; `theory/chapters/arrays.qmd:242:68` |
| `sec-p-arrays-recap` (23) | `practice/arrays.qmd#sec-j25-practice-arrays-recap` | U01 | — |
| `sec-p-arrays-s1` (25) | `practice/arrays.qmd#sec-j25-practice-arrays-s1` | U01 | — |
| `sec-p-arrays-s2` (36) | `practice/arrays.qmd#sec-j25-practice-arrays-s2` | U01 | — |
| `sec-p-arrays-traversal-order` (47) | `practice/arrays.qmd#sec-j25-practice-arrays-traversal-order` | U01 | — |
| `sec-p-arrays-s3` (61) | `practice/arrays.qmd#sec-j25-practice-arrays-s3` | U01 | — |
| `sec-p-arrays-reference` (67) | `practice/arrays.qmd#sec-j25-practice-arrays-reference` | U01 | — |
| `sec-p-arrays-s4` (69) | `practice/arrays.qmd#sec-j25-practice-arrays-s4` | U01 | — |
| `sec-p-arrays-row-reference` (75) | `practice/arrays.qmd#sec-j25-practice-arrays-row-reference` | U01 | — |
| `sec-p-arrays-s5` (89) | `practice/arrays.qmd#sec-j25-practice-arrays-s5` | U01 | — |
| `sec-p-arrays-row-key` (95) | `practice/arrays.qmd#sec-j25-practice-arrays-row-key` | U01 | — |
| `sec-p-arrays-assignments` (103) | `practice/arrays.qmd#sec-j25-practice-arrays-assignments` | U01 | — |
| `sec-p-arrays-s6` (105) | `practice/arrays.qmd#sec-j25-practice-arrays-s6` | U01 | — |
| `sec-p-arrays-s7` (111) | `practice/arrays.qmd#sec-j25-practice-arrays-s7` | U01 | — |
| `sec-p-arrays-result` (117) | `practice/arrays.qmd#sec-j25-practice-arrays-result` | U01 | — |

### practice/collections.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-p-collections` (11) | `practice/collections.qmd#sec-j25-practice-collections` | U04 | `_includes/semester-map.qmd:11:210`; `practice/index.qmd:16:21`; `tasks/labs/collections.qmd:11:98`; `tasks/labs/index.qmd:16:87` |
| `sec-p-collections-recap` (21) | `practice/collections.qmd#sec-j25-practice-collections-recap` | U04 | — |
| `sec-p-collections-s1` (23) | `practice/collections.qmd#sec-j25-practice-collections-s1` | U04 | — |
| `sec-p-collections-s2` (29) | `practice/collections.qmd#sec-j25-practice-collections-s2` | U04 | — |
| `sec-p-collections-s3` (35) | `practice/collections.qmd#sec-j25-practice-collections-s3` | U04 | — |
| `sec-p-collections-deque-methods` (41) | `practice/collections.qmd#sec-j25-practice-collections-deque-methods` | U04 | — |
| `sec-p-collections-set-copy` (54) | `practice/collections.qmd#sec-j25-practice-collections-set-copy` | U04 | — |
| `sec-p-collections-map-update` (67) | `practice/collections.qmd#sec-j25-practice-collections-map-update` | U04 | — |
| `sec-p-collections-reference` (81) | `practice/collections.qmd#sec-j25-practice-collections-reference` | U04 | — |
| `sec-p-collections-s4` (83) | `practice/collections.qmd#sec-j25-practice-collections-s4` | U04 | — |
| `sec-p-collections-s5` (90) | `practice/collections.qmd#sec-j25-practice-collections-s5` | U04 | — |
| `sec-p-collections-assignments` (96) | `practice/collections.qmd#sec-j25-practice-collections-assignments` | U04 | — |
| `sec-p-collections-s6` (98) | `practice/collections.qmd#sec-j25-practice-collections-s6` | U04 | — |
| `sec-p-collections-case-policy` (104) | `practice/collections.qmd#sec-j25-practice-collections-case-policy` | U04 | — |
| `sec-p-collections-set-borders` (113) | `practice/collections.qmd#sec-j25-practice-collections-set-borders` | U04 | — |
| `sec-p-collections-s7` (125) | `practice/collections.qmd#sec-j25-practice-collections-s7` | U04 | — |
| `sec-p-collections-alternative-border` (131) | `practice/collections.qmd#sec-j25-practice-collections-alternative-border` | U04 | — |
| `sec-p-collections-result` (143) | `practice/collections.qmd#sec-j25-practice-collections-result` | U04 | — |

### practice/control.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-p-control` (11) | `practice/control.qmd#sec-j25-practice-control` | U01 | `_includes/semester-map.qmd:5:210`; `practice/index.qmd:10:21`; `tasks/labs/control.qmd:16:28`; `tasks/labs/index.qmd:10:83`; `theory/chapters/control.qmd:238:11` |
| `sec-p-control-recap` (23) | `practice/control.qmd#sec-j25-practice-control-recap` | U01 | — |
| `sec-p-control-s1` (25) | `practice/control.qmd#sec-j25-practice-control-s1` | U01 | — |
| `sec-p-control-s2` (38) | `practice/control.qmd#sec-j25-practice-control-s2` | U01 | — |
| `sec-p-control-s3` (48) | `practice/control.qmd#sec-j25-practice-control-s3` | U01 | — |
| `sec-p-control-reference` (54) | `practice/control.qmd#sec-j25-practice-control-reference` | U01 | — |
| `sec-p-control-s4` (56) | `practice/control.qmd#sec-j25-practice-control-s4` | U01 | — |
| `sec-p-control-read-one` (62) | `practice/control.qmd#sec-j25-practice-control-read-one` | U01 | — |
| `sec-p-control-reader` (76) | `practice/control.qmd#sec-j25-practice-control-reader` | U01 | — |
| `sec-p-control-s5` (90) | `practice/control.qmd#sec-j25-practice-control-s5` | U01 | — |
| `sec-p-control-input-states` (98) | `practice/control.qmd#sec-j25-practice-control-input-states` | U01 | — |
| `sec-p-control-assignments` (110) | `practice/control.qmd#sec-j25-practice-control-assignments` | U01 | — |
| `sec-p-control-s6` (112) | `practice/control.qmd#sec-j25-practice-control-s6` | U01 | — |
| `sec-p-control-s7` (118) | `practice/control.qmd#sec-j25-practice-control-s7` | U01 | — |
| `sec-p-control-result` (124) | `practice/control.qmd#sec-j25-practice-control-result` | U01 | — |

### practice/files.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-p-files` (11) | `practice/files.qmd#sec-j25-practice-files` | U05 | `_includes/semester-map.qmd:13:208`; `_includes/semester-map.qmd:14:206`; `practice/index.qmd:18:21`; `tasks/labs/files.qmd:11:96`; `tasks/labs/index.qmd:18:81` |
| `sec-p-files-recap` (22) | `practice/files.qmd#sec-j25-practice-files-recap` | U05 | — |
| `sec-p-files-s1` (24) | `practice/files.qmd#sec-j25-practice-files-s1` | U05 | — |
| `sec-p-files-s2` (39) | `practice/files.qmd#sec-j25-practice-files-s2` | U05 | — |
| `sec-p-files-input` (52) | `practice/files.qmd#sec-j25-practice-files-input` | U05 | — |
| `sec-p-files-reading` (61) | `practice/files.qmd#sec-j25-practice-files-reading` | U05 | — |
| `sec-p-files-byte` (63) | `practice/files.qmd#sec-j25-practice-files-byte` | U05 | — |
| `sec-p-files-text` (75) | `practice/files.qmd#sec-j25-practice-files-text` | U05 | — |
| `sec-p-files-s3` (88) | `practice/files.qmd#sec-j25-practice-files-s3` | U05 | — |
| `sec-p-files-reference` (98) | `practice/files.qmd#sec-j25-practice-files-reference` | U05 | — |
| `sec-p-files-manual` (100) | `practice/files.qmd#sec-j25-practice-files-manual` | U05 | — |
| `sec-p-files-manual-many` (115) | `practice/files.qmd#sec-j25-practice-files-manual-many` | U05 | — |
| `sec-p-files-s4` (129) | `practice/files.qmd#sec-j25-practice-files-s4` | U05 | — |
| `sec-p-files-catch` (143) | `practice/files.qmd#sec-j25-practice-files-catch` | U05 | — |
| `sec-p-files-s5` (153) | `practice/files.qmd#sec-j25-practice-files-s5` | U05 | — |
| `sec-p-files-assignments` (164) | `practice/files.qmd#sec-j25-practice-files-assignments` | U05 | — |
| `sec-p-files-exceptions` (166) | `practice/files.qmd#sec-j25-practice-files-exceptions` | U05 | — |
| `sec-p-files-s6` (176) | `practice/files.qmd#sec-j25-practice-files-s6` | U05 | — |
| `sec-p-files-s7` (185) | `practice/files.qmd#sec-j25-practice-files-s7` | U05 | — |
| `sec-p-files-checks` (194) | `practice/files.qmd#sec-j25-practice-files-checks` | U05 | — |
| `sec-p-files-result` (206) | `practice/files.qmd#sec-j25-practice-files-result` | U05 | — |

### practice/generics.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-p-generics` (11) | `practice/generics.qmd#sec-j25-practice-generics` | U03 | `_includes/semester-map.qmd:10:211`; `practice/index.qmd:15:21`; `tasks/labs/generics.qmd:11:112`; `tasks/labs/index.qmd:15:84` |
| `sec-p-generics-recap` (23) | `practice/generics.qmd#sec-j25-practice-generics-recap` | U03 | — |
| `sec-p-generics-s6` (36) | `practice/generics.qmd#sec-j25-practice-generics-s6` | U03 | — |
| `sec-p-generics-reference` (45) | `practice/generics.qmd#sec-j25-practice-generics-reference` | U03 | — |
| `sec-p-generics-s3` (54) | `practice/generics.qmd#sec-j25-practice-generics-s3` | U03 | — |
| `sec-p-generics-assignments` (63) | `practice/generics.qmd#sec-j25-practice-generics-assignments` | U03 | — |
| `sec-p-generics-s5` (78) | `practice/generics.qmd#sec-j25-practice-generics-s5` | U03 | — |
| `sec-p-generics-s7` (95) | `practice/generics.qmd#sec-j25-practice-generics-s7` | U03 | — |
| `sec-p-generics-result` (105) | `practice/generics.qmd#sec-j25-practice-generics-result` | U03 | — |

### practice/index.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-p-program` (5) | `practice/index.qmd#sec-j25-practice` | U00 | `handbook/index.qmd:7:140`; `tasks/index.qmd:11:195`; `theory/index.qmd:19:354` |

### practice/numbers.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-p-numbers` (11) | `practice/numbers.qmd#sec-j25-practice-numbers` | U01 | `_includes/semester-map.qmd:3:200`; `_includes/semester-map.qmd:4:204`; `practice/index.qmd:9:21`; `tasks/labs/index.qmd:9:83`; `tasks/labs/numbers.qmd:14:93`; `theory/chapters/types.qmd:408:63` |
| `sec-p-numbers-recap` (23) | `practice/numbers.qmd#sec-j25-practice-numbers-recap` | U01 | — |
| `sec-p-numbers-s5` (25) | `practice/numbers.qmd#sec-j25-practice-numbers-s5` | U01 | — |
| `sec-p-numbers-input` (49) | `practice/numbers.qmd#sec-j25-practice-numbers-input` | U01 | — |
| `sec-p-numbers-argument-boundary` (67) | `practice/numbers.qmd#sec-j25-practice-numbers-argument-boundary` | U01 | — |
| `sec-p-numbers-numeric-model` (81) | `practice/numbers.qmd#sec-j25-practice-numbers-numeric-model` | U01 | — |
| `sec-p-numbers-s1` (83) | `practice/numbers.qmd#sec-j25-practice-numbers-s1` | U01 | — |
| `sec-p-numbers-units` (101) | `practice/numbers.qmd#sec-j25-practice-numbers-units` | U01 | — |
| `sec-p-numbers-range` (119) | `practice/numbers.qmd#sec-j25-practice-numbers-range` | U01 | — |
| `sec-p-numbers-logical-model` (136) | `practice/numbers.qmd#sec-j25-practice-numbers-logical-model` | U01 | — |
| `sec-p-numbers-s3` (138) | `practice/numbers.qmd#sec-j25-practice-numbers-s3` | U01 | — |
| `sec-p-numbers-s2` (157) | `practice/numbers.qmd#sec-j25-practice-numbers-s2` | U01 | — |
| `sec-p-numbers-s7` (175) | `practice/numbers.qmd#sec-j25-practice-numbers-s7` | U01 | — |
| `sec-p-numbers-assignments` (194) | `practice/numbers.qmd#sec-j25-practice-numbers-assignments` | U01 | — |
| `sec-p-numbers-checks` (196) | `practice/numbers.qmd#sec-j25-practice-numbers-checks` | U01 | — |
| `sec-p-numbers-s6` (210) | `practice/numbers.qmd#sec-j25-practice-numbers-s6` | U01 | — |
| `sec-p-numbers-result` (225) | `practice/numbers.qmd#sec-j25-practice-numbers-result` | U01 | — |

### practice/objects.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-p-objects` (11) | `practice/objects.qmd#sec-j25-practice-objects` | U02 | `_includes/semester-map.qmd:8:196`; `practice/index.qmd:13:21`; `tasks/labs/index.qmd:13:83`; `tasks/labs/objects.qmd:11:96` |
| `sec-p-objects-recap` (22) | `practice/objects.qmd#sec-j25-practice-objects-recap` | U02 | — |
| `sec-p-objects-s2` (36) | `practice/objects.qmd#sec-j25-practice-objects-s2` | U02 | — |
| `sec-p-objects-assignments` (51) | `practice/objects.qmd#sec-j25-practice-objects-assignments` | U02 | — |
| `sec-p-objects-s6` (64) | `practice/objects.qmd#sec-j25-practice-objects-s6` | U02 | — |
| `sec-p-objects-reference` (75) | `practice/objects.qmd#sec-j25-practice-objects-reference` | U02 | — |
| `sec-p-objects-s7` (84) | `practice/objects.qmd#sec-j25-practice-objects-s7` | U02 | — |
| `sec-p-objects-result` (96) | `practice/objects.qmd#sec-j25-practice-objects-result` | U02 | — |

### practice/pipelines.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-p-pipelines` (11) | `practice/pipelines.qmd#sec-j25-practice-pipelines` | U04 | `_includes/semester-map.qmd:12:223`; `practice/index.qmd:17:21`; `tasks/labs/index.qmd:17:85`; `tasks/labs/pipelines.qmd:11:96` |
| `sec-p-pipelines-recap` (21) | `practice/pipelines.qmd#sec-j25-practice-pipelines-recap` | U04 | — |
| `sec-p-pipelines-s1` (23) | `practice/pipelines.qmd#sec-j25-practice-pipelines-s1` | U04 | — |
| `sec-p-pipelines-s2` (32) | `practice/pipelines.qmd#sec-j25-practice-pipelines-s2` | U04 | — |
| `sec-p-pipelines-s3` (41) | `practice/pipelines.qmd#sec-j25-practice-pipelines-s3` | U04 | — |
| `sec-p-pipelines-target-call` (47) | `practice/pipelines.qmd#sec-j25-practice-pipelines-target-call` | U04 | — |
| `sec-p-pipelines-array-result` (57) | `practice/pipelines.qmd#sec-j25-practice-pipelines-array-result` | U04 | — |
| `sec-p-pipelines-ordered-sort` (70) | `practice/pipelines.qmd#sec-j25-practice-pipelines-ordered-sort` | U04 | — |
| `sec-p-pipelines-reference` (83) | `practice/pipelines.qmd#sec-j25-practice-pipelines-reference` | U04 | — |
| `sec-p-pipelines-s4` (85) | `practice/pipelines.qmd#sec-j25-practice-pipelines-s4` | U04 | — |
| `sec-p-pipelines-s5` (91) | `practice/pipelines.qmd#sec-j25-practice-pipelines-s5` | U04 | — |
| `sec-p-pipelines-assignments` (97) | `practice/pipelines.qmd#sec-j25-practice-pipelines-assignments` | U04 | — |
| `sec-p-pipelines-s6` (99) | `practice/pipelines.qmd#sec-j25-practice-pipelines-s6` | U04 | — |
| `sec-p-pipelines-boundaries` (105) | `practice/pipelines.qmd#sec-j25-practice-pipelines-boundaries` | U04 | — |
| `sec-p-pipelines-s7` (117) | `practice/pipelines.qmd#sec-j25-practice-pipelines-s7` | U04 | — |
| `sec-p-pipelines-conditional-execution` (123) | `practice/pipelines.qmd#sec-j25-practice-pipelines-conditional-execution` | U04 | — |
| `sec-p-pipelines-count-model` (133) | `practice/pipelines.qmd#sec-j25-practice-pipelines-count-model` | U04 | — |
| `sec-p-pipelines-result` (145) | `practice/pipelines.qmd#sec-j25-practice-pipelines-result` | U04 | — |

### practice/polymorphism.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-p-polymorphism` (11) | `practice/polymorphism.qmd#sec-j25-practice-polymorphism` | U02 | `_includes/semester-map.qmd:9:280`; `practice/index.qmd:14:21`; `tasks/labs/index.qmd:14:88`; `tasks/labs/polymorphism.qmd:11:102` |
| `sec-p-polymorphism-recap` (23) | `practice/polymorphism.qmd#sec-j25-practice-polymorphism-recap` | U02 | — |
| `sec-p-polymorphism-s2` (35) | `practice/polymorphism.qmd#sec-j25-practice-polymorphism-s2` | U02 | — |
| `sec-p-polymorphism-reference` (47) | `practice/polymorphism.qmd#sec-j25-practice-polymorphism-reference` | U02 | — |
| `sec-p-polymorphism-s7` (62) | `practice/polymorphism.qmd#sec-j25-practice-polymorphism-s7` | U02 | — |
| `sec-p-polymorphism-assignments` (73) | `practice/polymorphism.qmd#sec-j25-practice-polymorphism-assignments` | U02 | — |
| `sec-p-polymorphism-s6` (86) | `practice/polymorphism.qmd#sec-j25-practice-polymorphism-s6` | U02 | — |
| `sec-p-polymorphism-queue-client` (97) | `practice/polymorphism.qmd#sec-j25-practice-polymorphism-queue-client` | U02 | — |
| `sec-p-polymorphism-result` (112) | `practice/polymorphism.qmd#sec-j25-practice-polymorphism-result` | U02 | — |

### practice/reserve-algorithms.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-p-reserve-algorithms` (11) | `practice/reserve-algorithms.qmd#sec-j25-practice-reserve-algorithms` | U01 | `_includes/semester-map.qmd:15:246`; `practice/index.qmd:19:21`; `tasks/labs/index.qmd:19:92`; `tasks/labs/reserve-algorithms.qmd:11:94` |
| `sec-p-reserve-algorithms-recap` (23) | `practice/reserve-algorithms.qmd#sec-j25-practice-reserve-algorithms-recap` | U01 | — |
| `sec-p-reserve-algorithms-s1` (25) | `practice/reserve-algorithms.qmd#sec-j25-practice-reserve-algorithms-s1` | U01 | — |
| `sec-p-reserve-algorithms-s2` (31) | `practice/reserve-algorithms.qmd#sec-j25-practice-reserve-algorithms-s2` | U01 | — |
| `sec-p-reserve-algorithms-s3` (37) | `practice/reserve-algorithms.qmd#sec-j25-practice-reserve-algorithms-s3` | U01 | — |
| `sec-p-reserve-algorithms-reference` (43) | `practice/reserve-algorithms.qmd#sec-j25-practice-reserve-algorithms-reference` | U01 | — |
| `sec-p-reserve-algorithms-s4` (45) | `practice/reserve-algorithms.qmd#sec-j25-practice-reserve-algorithms-s4` | U01 | — |
| `sec-p-reserve-algorithms-cyclic-index` (51) | `practice/reserve-algorithms.qmd#sec-j25-practice-reserve-algorithms-cyclic-index` | U01 | — |
| `sec-p-reserve-algorithms-s5` (63) | `practice/reserve-algorithms.qmd#sec-j25-practice-reserve-algorithms-s5` | U01 | — |
| `sec-p-reserve-algorithms-snapshot` (69) | `practice/reserve-algorithms.qmd#sec-j25-practice-reserve-algorithms-snapshot` | U01 | — |
| `sec-p-reserve-algorithms-number-fragment` (77) | `practice/reserve-algorithms.qmd#sec-j25-practice-reserve-algorithms-number-fragment` | U01 | — |
| `sec-p-reserve-algorithms-assignments` (89) | `practice/reserve-algorithms.qmd#sec-j25-practice-reserve-algorithms-assignments` | U01 | — |
| `sec-p-reserve-algorithms-s6` (91) | `practice/reserve-algorithms.qmd#sec-j25-practice-reserve-algorithms-s6` | U01 | — |
| `sec-p-reserve-algorithms-s7` (97) | `practice/reserve-algorithms.qmd#sec-j25-practice-reserve-algorithms-s7` | U01 | — |
| `sec-p-reserve-algorithms-result` (103) | `practice/reserve-algorithms.qmd#sec-j25-practice-reserve-algorithms-result` | U01 | — |

### practice/reserve-data.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-p-reserve-data` (11) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data` | U04 | `_includes/semester-map.qmd:17:376`; `practice/index.qmd:21:21`; `tasks/labs/index.qmd:21:86`; `tasks/labs/reserve-data.qmd:11:100` |
| `sec-p-reserve-data-recap` (21) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data-recap` | U04 | — |
| `sec-p-reserve-data-s1` (23) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data-s1` | U04 | — |
| `sec-p-reserve-data-s2` (29) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data-s2` | U04 | — |
| `sec-p-reserve-data-s3` (35) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data-s3` | U04 | — |
| `sec-p-reserve-data-route` (41) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data-route` | U04 | — |
| `sec-p-reserve-data-returned-step` (53) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data-returned-step` | U04 | — |
| `sec-p-reserve-data-sample-file` (64) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data-sample-file` | U04 | — |
| `sec-p-reserve-data-reference` (78) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data-reference` | U04 | — |
| `sec-p-reserve-data-s4` (80) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data-s4` | U04 | — |
| `sec-p-reserve-data-s5` (86) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data-s5` | U04 | — |
| `sec-p-reserve-data-assignments` (92) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data-assignments` | U04 | — |
| `sec-p-reserve-data-s6` (94) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data-s6` | U04 | — |
| `sec-p-reserve-data-matrix-form` (100) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data-matrix-form` | U04 | — |
| `sec-p-reserve-data-byte-eof` (109) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data-byte-eof` | U04 | — |
| `sec-p-reserve-data-s7` (121) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data-s7` | U04 | — |
| `sec-p-reserve-data-text-state` (127) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data-text-state` | U04 | — |
| `sec-p-reserve-data-result` (140) | `practice/reserve-data.qmd#sec-j25-practice-reserve-data-result` | U04 | — |

### practice/reserve-models.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-p-reserve-models` (11) | `practice/reserve-models.qmd#sec-j25-practice-reserve-models` | U02 | `_includes/semester-map.qmd:16:322`; `practice/index.qmd:20:21`; `tasks/labs/index.qmd:20:88`; `tasks/labs/reserve-models.qmd:11:102` |
| `sec-p-reserve-models-recap` (22) | `practice/reserve-models.qmd#sec-j25-practice-reserve-models-recap` | U02 | — |
| `sec-p-reserve-models-fractions` (35) | `practice/reserve-models.qmd#sec-j25-practice-reserve-models-fractions` | U02 | — |
| `sec-p-reserve-models-reference` (44) | `practice/reserve-models.qmd#sec-j25-practice-reserve-models-reference` | U02 | — |
| `sec-p-reserve-models-queue-growth` (56) | `practice/reserve-models.qmd#sec-j25-practice-reserve-models-queue-growth` | U02 | — |
| `sec-p-reserve-models-assignments` (72) | `practice/reserve-models.qmd#sec-j25-practice-reserve-models-assignments` | U02 | — |
| `sec-p-reserve-models-projects` (82) | `practice/reserve-models.qmd#sec-j25-practice-reserve-models-projects` | U02 | — |
| `sec-p-reserve-models-result` (95) | `practice/reserve-models.qmd#sec-j25-practice-reserve-models-result` | U02 | — |

### practice/text.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-p-text` (11) | `practice/text.qmd#sec-j25-practice-text` | U01 | `_includes/semester-map.qmd:7:190`; `practice/index.qmd:12:21`; `tasks/labs/index.qmd:12:80`; `tasks/labs/text.qmd:11:93`; `theory/chapters/text.qmd:231:58` |
| `sec-p-text-recap` (23) | `practice/text.qmd#sec-j25-practice-text-recap` | U01 | — |
| `sec-p-text-s1` (25) | `practice/text.qmd#sec-j25-practice-text-s1` | U01 | — |
| `sec-p-text-s2` (35) | `practice/text.qmd#sec-j25-practice-text-s2` | U01 | — |
| `sec-p-text-s3` (45) | `practice/text.qmd#sec-j25-practice-text-s3` | U01 | — |
| `sec-p-text-reference` (51) | `practice/text.qmd#sec-j25-practice-text-reference` | U01 | — |
| `sec-p-text-s4` (53) | `practice/text.qmd#sec-j25-practice-text-s4` | U01 | — |
| `sec-p-text-separator` (59) | `practice/text.qmd#sec-j25-practice-text-separator` | U01 | — |
| `sec-p-text-building` (72) | `practice/text.qmd#sec-j25-practice-text-building` | U01 | — |
| `sec-p-text-s5` (84) | `practice/text.qmd#sec-j25-practice-text-s5` | U01 | — |
| `sec-p-text-matching` (91) | `practice/text.qmd#sec-j25-practice-text-matching` | U01 | — |
| `sec-p-text-boundaries` (102) | `practice/text.qmd#sec-j25-practice-text-boundaries` | U01 | — |
| `sec-p-text-assignments` (110) | `practice/text.qmd#sec-j25-practice-text-assignments` | U01 | — |
| `sec-p-text-s6` (112) | `practice/text.qmd#sec-j25-practice-text-s6` | U01 | — |
| `sec-p-text-s7` (118) | `practice/text.qmd#sec-j25-practice-text-s7` | U01 | — |
| `sec-p-text-result` (124) | `practice/text.qmd#sec-j25-practice-text-result` | U01 | — |

### tasks/bank/arrays.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-bank-arrays` (7) | `tasks/bank/arrays.qmd#sec-j25-bank-arrays` | U06 | `tasks/bank/index.qmd:11:18` |
| `exr-array-average` (18) | `tasks/bank/arrays.qmd#exr-j25-arrays-array-average` | U06 | `practice/arrays.qmd:107:8`; `tasks/bank/arrays.qmd:29:22`; `tasks/essays/numbers.qmd:44:82`; `tasks/labs/arrays.qmd:25:5` |
| `exr-reverse-array` (35) | `tasks/bank/arrays.qmd#exr-j25-arrays-reverse-array` | U06 | `practice/arrays.qmd:109:8`; `tasks/bank/arrays.qmd:44:22`; `tasks/labs/arrays.qmd:26:5` |
| `exr-subtract-array-average` (50) | `tasks/bank/arrays.qmd#exr-j25-arrays-subtract-array-average` | U06 | `practice/arrays.qmd:115:30`; `tasks/bank/arrays.qmd:63:22`; `tasks/essays/arrays.qmd:14:27`; `tasks/labs/arrays.qmd:27:6` |
| `exr-sort-matrix-rows` (69) | `tasks/bank/arrays.qmd#exr-j25-arrays-sort-matrix-rows` | U06 | `practice/arrays.qmd:101:185`; `practice/arrays.qmd:113:8`; `tasks/bank/arrays.qmd:78:22`; `tasks/essays/arrays.qmd:14:115`; `tasks/labs/arrays.qmd:28:5` |
| `exr-move-row-zeros` (84) | `tasks/bank/arrays.qmd#exr-j25-arrays-move-row-zeros` | U06 | `practice/arrays.qmd:115:69`; `tasks/bank/arrays.qmd:93:22`; `tasks/labs/arrays.qmd:27:35` |
| `exr-cyclic-matrix-shift` (99) | `tasks/bank/arrays.qmd#exr-j25-arrays-cyclic-matrix-shift` | U06 | `practice/reserve-algorithms.qmd:93:25`; `tasks/essays/numbers.qmd:29:118`; `tasks/labs/reserve-algorithms.qmd:31:5` |
| `exr-rotate-matrix` (110) | `tasks/bank/arrays.qmd#exr-j25-arrays-rotate-matrix` | U06 | `practice/reserve-algorithms.qmd:33:120`; `practice/reserve-algorithms.qmd:93:57`; `tasks/labs/reserve-algorithms.qmd:32:5` |
| `exr-remove-max-rows-columns` (121) | `tasks/bank/arrays.qmd#exr-j25-arrays-remove-max-rows-columns` | U06 | `practice/reserve-algorithms.qmd:93:83`; `tasks/labs/reserve-algorithms.qmd:33:5` |
| `exr-saddle-points` (132) | `tasks/bank/arrays.qmd#exr-j25-arrays-saddle-points` | U06 | `tasks/labs/reserve-algorithms.qmd:34:5` |
| `exr-matrix-local-minima` (143) | `tasks/bank/arrays.qmd#exr-j25-arrays-matrix-local-minima` | U06 | `tasks/labs/reserve-algorithms.qmd:35:5` |
| `exr-sort-array` (154) | `tasks/bank/arrays.qmd#exr-j25-arrays-sort-array` | U06 | `practice/reserve-algorithms.qmd:49:8`; `tasks/labs/reserve-algorithms.qmd:36:5` |

### tasks/bank/collections.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-bank-collections` (7) | `tasks/bank/collections.qmd#sec-j25-bank-collections` | U06 | `tasks/bank/index.qmd:16:18` |
| `exr-digit-stack` (12) | `tasks/bank/collections.qmd#exr-j25-collections-digit-stack` | U06 | `practice/collections.qmd:52:15`; `practice/collections.qmd:88:12`; `tasks/labs/collections.qmd:22:5` |
| `exr-set-union-intersection` (21) | `tasks/bank/collections.qmd#exr-j25-collections-set-union-intersection` | U06 | `practice/collections.qmd:64:16`; `practice/collections.qmd:100:8`; `tasks/labs/collections.qmd:23:5` |
| `exr-word-frequency` (28) | `tasks/bank/collections.qmd#exr-j25-collections-word-frequency` | U06 | `practice/collections.qmd:76:12`; `practice/collections.qmd:102:8`; `tasks/essays/collections.qmd:14:27`; `tasks/essays/objects.qmd:59:108`; `tasks/essays/pipelines.qmd:44:27`; `tasks/labs/collections.qmd:24:5` |
| `exr-balanced-brackets` (37) | `tasks/bank/collections.qmd#exr-j25-collections-balanced-brackets` | U06 | `practice/collections.qmd:127:17`; `practice/collections.qmd:133:12`; `tasks/labs/collections.qmd:19:10`; `tasks/labs/collections.qmd:25:6` |
| `exr-polynomial-map-addition` (44) | `tasks/bank/collections.qmd#exr-j25-collections-polynomial-map-addition` | U06 | `practice/collections.qmd:127:51`; `practice/collections.qmd:137:12`; `tasks/labs/collections.qmd:19:48`; `tasks/labs/collections.qmd:26:6` |
| `exr-poem-line-order` (53) | `tasks/bank/collections.qmd#exr-j25-collections-poem-line-order` | U06 | `tasks/essays/collections.qmd:29:27`; `tasks/essays/collections.qmd:44:163`; `tasks/labs/reserve-models.qmd:36:5` |
| `exr-distinct-word-set` (65) | `tasks/bank/collections.qmd#exr-j25-collections-distinct-word-set` | U06 | `tasks/labs/reserve-models.qmd:37:5` |
| `exr-elimination-circle` (74) | `tasks/bank/collections.qmd#exr-j25-collections-elimination-circle` | U06 | `tasks/labs/reserve-models.qmd:38:5` |
| `exr-partition-list` (81) | `tasks/bank/collections.qmd#exr-j25-collections-partition-list` | U06 | `tasks/essays/collections.qmd:44:27`; `tasks/labs/reserve-models.qmd:39:5` |
| `exr-graph-model` (88) | `tasks/bank/collections.qmd#exr-j25-collections-graph-model` | U06 | `practice/reserve-models.qmd:74:51`; `tasks/labs/reserve-models.qmd:40:5` |
| `exr-nearest-value` (97) | `tasks/bank/collections.qmd#exr-j25-collections-nearest-value` | U06 | `tasks/essays/collections.qmd:29:147`; `tasks/labs/reserve-models.qmd:41:5` |
| `exr-polynomial-list-multiplication` (106) | `tasks/bank/collections.qmd#exr-j25-collections-polynomial-list-multiplication` | U06 | `tasks/labs/reserve-models.qmd:42:5` |

### tasks/bank/control.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-bank-control` (7) | `tasks/bank/control.qmd#sec-j25-bank-control` | U06 | `tasks/bank/index.qmd:10:18` |
| `exr-month-name` (19) | `tasks/bank/control.qmd#exr-j25-control-month-name` | U06 | `practice/control.qmd:114:8`; `tasks/bank/control.qmd:26:22`; `tasks/labs/control.qmd:27:5` |
| `exr-multiplication-table` (32) | `tasks/bank/control.qmd#exr-j25-control-multiplication-table` | U06 | `practice/control.qmd:116:8`; `tasks/bank/control.qmd:39:22`; `tasks/labs/control.qmd:28:5` |
| `exr-count-console-spaces` (45) | `tasks/bank/control.qmd#exr-j25-control-count-console-spaces` | U06 | `practice/control.qmd:122:46`; `tasks/bank/control.qmd:52:22`; `tasks/labs/control.qmd:29:6` |
| `exr-toggle-console-case` (58) | `tasks/bank/control.qmd#exr-j25-control-toggle-console-case` | U06 | `practice/control.qmd:120:8`; `tasks/bank/control.qmd:65:22`; `tasks/labs/control.qmd:30:5` |
| `exr-number-extrema` (71) | `tasks/bank/control.qmd#exr-j25-control-number-extrema` | U06 | `practice/control.qmd:122:80`; `tasks/bank/control.qmd:78:22`; `tasks/labs/control.qmd:29:33` |
| `exr-inch-conversion-table` (84) | `tasks/bank/control.qmd#exr-j25-control-inch-conversion-table` | U06 | `tasks/labs/reserve-algorithms.qmd:25:5` |
| `exr-primes-to-hundred` (93) | `tasks/bank/control.qmd#exr-j25-control-primes-to-hundred` | U06 | `tasks/labs/reserve-algorithms.qmd:26:5` |
| `exr-distinct-three-digits` (100) | `tasks/bank/control.qmd#exr-j25-control-distinct-three-digits` | U06 | `tasks/labs/reserve-algorithms.qmd:27:5` |
| `exr-numeric-palindromes` (112) | `tasks/bank/control.qmd#exr-j25-control-numeric-palindromes` | U06 | `tasks/labs/reserve-algorithms.qmd:28:5` |
| `exr-increasing-digits` (124) | `tasks/bank/control.qmd#exr-j25-control-increasing-digits` | U06 | `tasks/labs/reserve-algorithms.qmd:29:5` |
| `exr-control-help-menu` (136) | `tasks/bank/control.qmd#exr-j25-control-control-help-menu` | U06 | `tasks/labs/reserve-algorithms.qmd:30:5` |

### tasks/bank/exceptions.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-bank-exceptions` (7) | `tasks/bank/exceptions.qmd#sec-j25-bank-exceptions` | U06 | `tasks/bank/index.qmd:18:18` |
| `exr-exception-flow-errors` (12) | `tasks/bank/exceptions.qmd#exr-j25-exceptions-exception-flow-errors` | U06 | `practice/files.qmd:168:10`; `tasks/labs/files.qmd:23:5` |
| `exr-queue-exceptions` (23) | `tasks/bank/exceptions.qmd#exr-j25-exceptions-queue-exceptions` | U06 | `practice/files.qmd:172:15`; `tasks/essays/exceptions.qmd:14:27`; `tasks/essays/exceptions.qmd:29:27`; `tasks/labs/files.qmd:19:10`; `tasks/labs/files.qmd:24:6` |
| `exr-locale-number-errors` (34) | `tasks/bank/exceptions.qmd#exr-j25-exceptions-locale-number-errors` | U06 | `tasks/essays/exceptions.qmd:14:101`; `tasks/labs/reserve-data.qmd:29:5` |
| `exr-stack-exceptions` (45) | `tasks/bank/exceptions.qmd#exr-j25-exceptions-stack-exceptions` | U06 | `practice/files.qmd:172:45`; `tasks/labs/files.qmd:19:47`; `tasks/labs/files.qmd:28:6` |
| `exr-constructor-checked-exception` (54) | `tasks/bank/exceptions.qmd#exr-j25-exceptions-constructor-checked-exception` | U06 | `tasks/labs/reserve-data.qmd:30:5` |
| `exr-override-exceptions` (61) | `tasks/bank/exceptions.qmd#exr-j25-exceptions-override-exceptions` | U06 | `tasks/labs/reserve-data.qmd:31:5` |

### tasks/bank/files.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-bank-files` (7) | `tasks/bank/files.qmd#sec-j25-bank-files` | U06 | `tasks/bank/index.qmd:19:18` |
| `exr-byte-file-copy` (12) | `tasks/bank/files.qmd#exr-j25-files-byte-file-copy` | U06 | `practice/files.qmd:178:8`; `tasks/essays/files.qmd:14:27`; `tasks/essays/files.qmd:29:27`; `tasks/labs/files.qmd:25:5` |
| `exr-character-file-copy` (21) | `tasks/bank/files.qmd#exr-j25-files-character-file-copy` | U06 | `practice/files.qmd:181:8`; `tasks/essays/files.qmd:14:55`; `tasks/essays/files.qmd:29:55`; `tasks/labs/files.qmd:26:5` |
| `exr-transpose-file-matrix` (30) | `tasks/bank/files.qmd#exr-j25-files-transpose-file-matrix` | U06 | `practice/reserve-data.qmd:96:8`; `practice/reserve-data.qmd:102:8`; `tasks/labs/reserve-data.qmd:32:5` |
| `exr-poem-word-frequency` (39) | `tasks/bank/files.qmd#exr-j25-files-poem-word-frequency` | U06 | `practice/files.qmd:187:17`; `tasks/essays/files.qmd:29:118`; `tasks/labs/files.qmd:20:10`; `tasks/labs/files.qmd:27:6` |
| `exr-poem-letter-word-frequency` (48) | `tasks/bank/files.qmd#exr-j25-files-poem-letter-word-frequency` | U06 | `practice/files.qmd:188:8`; `tasks/labs/files.qmd:20:50`; `tasks/labs/files.qmd:29:6` |
| `exr-compare-files` (57) | `tasks/bank/files.qmd#exr-j25-files-compare-files` | U06 | `practice/reserve-data.qmd:98:8`; `practice/reserve-data.qmd:111:12`; `tasks/labs/reserve-data.qmd:33:5` |
| `exr-sort-file-numbers` (68) | `tasks/bank/files.qmd#exr-j25-files-sort-file-numbers` | U06 | `tasks/labs/reserve-data.qmd:34:5` |
| `exr-final-line-words` (77) | `tasks/bank/files.qmd#exr-j25-files-final-line-words` | U06 | `tasks/labs/reserve-data.qmd:35:5` |
| `exr-remove-short-words` (86) | `tasks/bank/files.qmd#exr-j25-files-remove-short-words` | U06 | `tasks/labs/reserve-data.qmd:36:5` |
| `exr-phone-owner-records` (95) | `tasks/bank/files.qmd#exr-j25-files-phone-owner-records` | U06 | `tasks/labs/reserve-data.qmd:37:5` |
| `exr-matching-line-numbers` (104) | `tasks/bank/files.qmd#exr-j25-files-matching-line-numbers` | U06 | `practice/reserve-data.qmd:71:44`; `practice/reserve-data.qmd:96:77`; `tasks/labs/reserve-data.qmd:38:5` |
| `exr-disk-help-menu` (113) | `tasks/bank/files.qmd#exr-j25-files-disk-help-menu` | U06 | `tasks/labs/reserve-data.qmd:39:5` |
| `exr-remove-java-comments` (126) | `tasks/bank/files.qmd#exr-j25-files-remove-java-comments` | U06 | `practice/reserve-data.qmd:39:57`; `practice/reserve-data.qmd:134:12`; `tasks/labs/reserve-data.qmd:40:5` |

### tasks/bank/generics.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-bank-generics` (7) | `tasks/bank/generics.qmd#sec-j25-bank-generics` | U06 | `tasks/bank/index.qmd:15:18` |
| `exr-two-type-container` (12) | `tasks/bank/generics.qmd#exr-j25-generics-two-type-container` | U06 | `practice/generics.qmd:28:12`; `tasks/essays/generics.qmd:14:27`; `tasks/labs/generics.qmd:24:5` |
| `exr-generic-method` (19) | `tasks/bank/generics.qmd#exr-j25-generics-generic-method` | U06 | `practice/generics.qmd:32:12`; `tasks/labs/generics.qmd:25:5` |
| `exr-swap-generic-array` (26) | `tasks/bank/generics.qmd#exr-j25-generics-swap-generic-array` | U06 | `practice/generics.qmd:38:10`; `tasks/essays/generics.qmd:29:160`; `tasks/labs/generics.qmd:26:5` |
| `exr-range-maximum` (33) | `tasks/bank/generics.qmd#exr-j25-generics-range-maximum` | U06 | `practice/generics.qmd:47:12`; `tasks/labs/generics.qmd:27:5` |
| `exr-generic-queue` (42) | `tasks/bank/generics.qmd#exr-j25-generics-generic-queue` | U06 | `tasks/essays/generics.qmd:14:107`; `tasks/essays/generics.qmd:29:27`; `tasks/labs/generics.qmd:21:10`; `tasks/labs/generics.qmd:28:6` |
| `exr-generic-stack` (51) | `tasks/bank/generics.qmd#exr-j25-generics-generic-stack` | U06 | `tasks/labs/generics.qmd:21:44`; `tasks/labs/generics.qmd:29:6` |
| `exr-generic-circular-queue` (60) | `tasks/bank/generics.qmd#exr-j25-generics-generic-circular-queue` | U06 | `tasks/labs/reserve-models.qmd:32:5` |
| `exr-generic-dynamic-queue` (69) | `tasks/bank/generics.qmd#exr-j25-generics-generic-dynamic-queue` | U06 | `practice/reserve-models.qmd:68:12`; `tasks/labs/reserve-models.qmd:33:5` |
| `exr-generic-interface` (78) | `tasks/bank/generics.qmd#exr-j25-generics-generic-interface` | U06 | `tasks/labs/reserve-models.qmd:34:5` |
| `exr-property-count` (85) | `tasks/bank/generics.qmd#exr-j25-generics-property-count` | U06 | `tasks/labs/reserve-models.qmd:35:5` |

### tasks/bank/index.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-bank-program` (1) | `tasks/bank/index.qmd#sec-j25-bank` | U06 | `index.qmd:11:393`; `tasks/index.qmd:6:18` |

### tasks/bank/numbers.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-bank-numbers` (7) | `tasks/bank/numbers.qmd#sec-j25-bank-numbers` | U06 | `tasks/bank/index.qmd:9:18` |
| `exr-console-greeting` (18) | `tasks/bank/numbers.qmd#exr-j25-numbers-console-greeting` | U06 | `tasks/bank/numbers.qmd:25:22`; `tasks/essays/numbers.qmd:14:27`; `tasks/labs/numbers.qmd:27:5` |
| `exr-lunar-weight` (31) | `tasks/bank/numbers.qmd#exr-j25-numbers-lunar-weight` | U06 | `practice/numbers.qmd:215:44`; `tasks/bank/numbers.qmd:40:22`; `tasks/labs/numbers.qmd:28:6` |
| `exr-lightning-distance` (46) | `tasks/bank/numbers.qmd#exr-j25-numbers-lightning-distance` | U06 | `practice/numbers.qmd:219:31`; `tasks/bank/numbers.qmd:57:22`; `tasks/essays/numbers.qmd:44:27`; `tasks/labs/numbers.qmd:29:5` |
| `exr-boolean-truth-table` (63) | `tasks/bank/numbers.qmd#exr-j25-numbers-boolean-truth-table` | U06 | `tasks/bank/numbers.qmd:70:22`; `tasks/essays/control.qmd:14:27`; `tasks/labs/numbers.qmd:30:5` |
| `exr-interval-membership` (76) | `tasks/bank/numbers.qmd#exr-j25-numbers-interval-membership` | U06 | `tasks/bank/numbers.qmd:83:22`; `tasks/essays/control.qmd:14:132`; `tasks/labs/numbers.qmd:31:5` |
| `exr-short-circuit-and` (89) | `tasks/bank/numbers.qmd#exr-j25-numbers-short-circuit-and` | U06 | `tasks/bank/numbers.qmd:98:22`; `tasks/essays/control.qmd:14:60`; `tasks/labs/numbers.qmd:32:5` |
| `exr-gallon-conversion` (104) | `tasks/bank/numbers.qmd#exr-j25-numbers-gallon-conversion` | U06 | `practice/numbers.qmd:216:8`; `tasks/bank/numbers.qmd:115:22`; `tasks/labs/numbers.qmd:28:25` |
| `exr-random-console-values` (121) | `tasks/bank/numbers.qmd#exr-j25-numbers-random-console-values` | U06 | `tasks/labs/reserve-algorithms.qmd:20:5` |
| `exr-argument-sum-product` (130) | `tasks/bank/numbers.qmd#exr-j25-numbers-argument-sum-product` | U06 | `tasks/essays/numbers.qmd:29:27`; `tasks/labs/reserve-algorithms.qmd:21:5` |
| `exr-quadratic-roots` (137) | `tasks/bank/numbers.qmd#exr-j25-numbers-quadratic-roots` | U06 | `practice/reserve-algorithms.qmd:99:126`; `tasks/labs/reserve-algorithms.qmd:22:5` |
| `exr-radix-conversion` (146) | `tasks/bank/numbers.qmd#exr-j25-numbers-radix-conversion` | U06 | `practice/reserve-algorithms.qmd:99:97`; `tasks/labs/reserve-algorithms.qmd:23:5` |
| `exr-binary-truth-table` (155) | `tasks/bank/numbers.qmd#exr-j25-numbers-binary-truth-table` | U06 | `tasks/labs/reserve-algorithms.qmd:24:5` |

### tasks/bank/objects.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-bank-objects` (7) | `tasks/bank/objects.qmd#sec-j25-bank-objects` | U06 | `tasks/bank/index.qmd:13:18` |
| `exr-variadic-sum` (12) | `tasks/bank/objects.qmd#exr-j25-objects-variadic-sum` | U06 | `practice/objects.qmd:32:38`; `tasks/essays/numbers.qmd:59:27`; `tasks/labs/objects.qmd:22:5` |
| `exr-swap-object-fields` (19) | `tasks/bank/objects.qmd#exr-j25-objects-swap-object-fields` | U06 | `practice/objects.qmd:45:10`; `tasks/essays/objects.qmd:29:120`; `tasks/labs/objects.qmd:23:5` |
| `exr-character-stack` (28) | `tasks/bank/objects.qmd#exr-j25-objects-character-stack` | U06 | `practice/objects.qmd:53:12`; `tasks/labs/objects.qmd:24:5` |
| `exr-book-selection` (37) | `tasks/bank/objects.qmd#exr-j25-objects-book-selection` | U06 | `practice/objects.qmd:77:17`; `tasks/essays/numbers.qmd:59:84`; `tasks/essays/objects.qmd:14:27`; `tasks/essays/objects.qmd:74:27`; `tasks/labs/objects.qmd:19:10`; `tasks/labs/objects.qmd:25:6` |
| `exr-car-selection` (51) | `tasks/bank/objects.qmd#exr-j25-objects-car-selection` | U06 | `practice/objects.qmd:77:47`; `tasks/labs/objects.qmd:19:45`; `tasks/labs/objects.qmd:26:6` |
| `exr-customer-registry` (65) | `tasks/bank/objects.qmd#exr-j25-objects-customer-registry` | U06 | `tasks/essays/objects.qmd:59:27`; `tasks/labs/reserve-models.qmd:22:5` |
| `exr-patient-registry` (78) | `tasks/bank/objects.qmd#exr-j25-objects-patient-registry` | U06 | `tasks/labs/reserve-models.qmd:23:5` |
| `exr-rational-fractions` (91) | `tasks/bank/objects.qmd#exr-j25-objects-rational-fractions` | U06 | `practice/reserve-models.qmd:37:10`; `tasks/essays/objects.qmd:29:27`; `tasks/labs/reserve-models.qmd:24:5` |
| `exr-complex-values` (100) | `tasks/bank/objects.qmd#exr-j25-objects-complex-values` | U06 | `tasks/labs/reserve-models.qmd:25:5` |
| `exr-recursive-string-reversal` (109) | `tasks/bank/objects.qmd#exr-j25-objects-recursive-string-reversal` | U06 | `tasks/labs/reserve-models.qmd:26:5` |

### tasks/bank/pipelines.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-bank-pipelines` (7) | `tasks/bank/pipelines.qmd#sec-j25-bank-pipelines` | U06 | `tasks/bank/index.qmd:17:18` |
| `exr-divisibility-function` (12) | `tasks/bank/pipelines.qmd#exr-j25-pipelines-divisibility-function` | U06 | `tasks/labs/pipelines.qmd:20:10`; `tasks/labs/pipelines.qmd:23:6` |
| `exr-filter-string-array` (19) | `tasks/bank/pipelines.qmd#exr-j25-pipelines-filter-string-array` | U06 | `practice/pipelines.qmd:67:10`; `practice/pipelines.qmd:101:8`; `tasks/essays/pipelines.qmd:14:27`; `tasks/essays/pipelines.qmd:29:27`; `tasks/labs/pipelines.qmd:24:5` |
| `exr-order-points` (26) | `tasks/bank/pipelines.qmd#exr-j25-pipelines-order-points` | U06 | `practice/pipelines.qmd:78:10`; `practice/pipelines.qmd:101:62`; `tasks/labs/pipelines.qmd:25:5` |
| `exr-count-selected-words` (35) | `tasks/bank/pipelines.qmd#exr-j25-pipelines-count-selected-words` | U06 | `practice/pipelines.qmd:135:8`; `tasks/essays/pipelines.qmd:44:86`; `tasks/essays/text.qmd:14:27`; `tasks/labs/pipelines.qmd:19:10`; `tasks/labs/pipelines.qmd:26:6` |
| `exr-conditional-function` (44) | `tasks/bank/pipelines.qmd#exr-j25-pipelines-conditional-function` | U06 | `practice/pipelines.qmd:119:8`; `practice/pipelines.qmd:125:12`; `tasks/essays/pipelines.qmd:14:187`; `tasks/essays/pipelines.qmd:59:185`; `tasks/labs/pipelines.qmd:27:5` |
| `exr-count-characters` (53) | `tasks/bank/pipelines.qmd#exr-j25-pipelines-count-characters` | U06 | `practice/pipelines.qmd:136:31`; `tasks/labs/pipelines.qmd:19:51`; `tasks/labs/pipelines.qmd:28:6` |
| `exr-discriminant-function` (62) | `tasks/bank/pipelines.qmd#exr-j25-pipelines-discriminant-function` | U06 | `tasks/labs/pipelines.qmd:20:52`; `tasks/labs/pipelines.qmd:29:6` |
| `exr-longer-string` (69) | `tasks/bank/pipelines.qmd#exr-j25-pipelines-longer-string` | U06 | `tasks/labs/reserve-data.qmd:20:5` |
| `exr-remove-even-values` (78) | `tasks/bank/pipelines.qmd#exr-j25-pipelines-remove-even-values` | U06 | `tasks/labs/reserve-data.qmd:21:5` |
| `exr-descending-array` (85) | `tasks/bank/pipelines.qmd#exr-j25-pipelines-descending-array` | U06 | `tasks/labs/reserve-data.qmd:22:5` |
| `exr-vowels-before-consonants` (94) | `tasks/bank/pipelines.qmd#exr-j25-pipelines-vowels-before-consonants` | U06 | `tasks/labs/reserve-data.qmd:23:5` |
| `exr-first-primes` (103) | `tasks/bank/pipelines.qmd#exr-j25-pipelines-first-primes` | U06 | `tasks/labs/reserve-data.qmd:24:5` |
| `exr-price-transformation` (110) | `tasks/bank/pipelines.qmd#exr-j25-pipelines-price-transformation` | U06 | `tasks/labs/reserve-data.qmd:25:5` |
| `exr-distinct-object-array` (119) | `tasks/bank/pipelines.qmd#exr-j25-pipelines-distinct-object-array` | U06 | `tasks/labs/reserve-data.qmd:26:5` |
| `exr-select-initial-length` (128) | `tasks/bank/pipelines.qmd#exr-j25-pipelines-select-initial-length` | U06 | `tasks/essays/pipelines.qmd:29:115`; `tasks/essays/pipelines.qmd:59:27`; `tasks/labs/reserve-data.qmd:27:5` |
| `exr-curried-functions` (137) | `tasks/bank/pipelines.qmd#exr-j25-pipelines-curried-functions` | U06 | `practice/reserve-data.qmd:61:12`; `practice/reserve-data.qmd:84:8`; `tasks/labs/reserve-data.qmd:28:5` |

### tasks/bank/polymorphism.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-bank-polymorphism` (7) | `tasks/bank/polymorphism.qmd#sec-j25-bank-polymorphism` | U06 | `tasks/bank/index.qmd:14:18` |
| `exr-circle-subclass` (12) | `tasks/bank/polymorphism.qmd#exr-j25-polymorphism-circle-subclass` | U06 | `practice/polymorphism.qmd:25:10`; `tasks/essays/objects.qmd:44:27`; `tasks/essays/polymorphism.qmd:14:27`; `tasks/labs/polymorphism.qmd:22:5` |
| `exr-vehicle-interface` (21) | `tasks/bank/polymorphism.qmd#exr-j25-polymorphism-vehicle-interface` | U06 | `practice/polymorphism.qmd:49:10`; `tasks/labs/polymorphism.qmd:23:5` |
| `exr-animal-hierarchy` (30) | `tasks/bank/polymorphism.qmd#exr-j25-polymorphism-animal-hierarchy` | U06 | `practice/polymorphism.qmd:43:10`; `practice/polymorphism.qmd:64:12`; `tasks/labs/polymorphism.qmd:24:5` |
| `sec-bank-character-queues` (39) | `tasks/bank/polymorphism.qmd#sec-j25-bank-character-queues` | U06 | `practice/polymorphism.qmd:75:8`; `tasks/bank/exceptions.qmd:26:44`; `tasks/bank/polymorphism.qmd:61:20`; `tasks/bank/polymorphism.qmd:70:47`; `tasks/bank/polymorphism.qmd:88:40`; `tasks/bank/polymorphism.qmd:97:20`; `tasks/bank/polymorphism.qmd:115:52` |
| `exr-reset-character-queues` (58) | `tasks/bank/polymorphism.qmd#exr-j25-polymorphism-reset-character-queues` | U06 | `practice/polymorphism.qmd:88:10`; `tasks/essays/polymorphism.qmd:29:27`; `tasks/labs/polymorphism.qmd:19:10`; `tasks/labs/polymorphism.qmd:25:6` |
| `exr-queue-package` (67) | `tasks/bank/polymorphism.qmd#exr-j25-polymorphism-queue-package` | U06 | `tasks/essays/numbers.qmd:14:93`; `tasks/labs/reserve-models.qmd:27:5` |
| `exr-truck-subclass` (76) | `tasks/bank/polymorphism.qmd#exr-j25-polymorphism-truck-subclass` | U06 | `tasks/labs/reserve-models.qmd:28:5` |
| `exr-circular-dynamic-queue` (85) | `tasks/bank/polymorphism.qmd#exr-j25-polymorphism-circular-dynamic-queue` | U06 | `practice/reserve-models.qmd:46:10`; `tasks/labs/reserve-models.qmd:29:5` |
| `exr-copy-character-queues` (94) | `tasks/bank/polymorphism.qmd#exr-j25-polymorphism-copy-character-queues` | U06 | `practice/polymorphism.qmd:92:10`; `tasks/essays/exceptions.qmd:29:86`; `tasks/essays/polymorphism.qmd:29:118`; `tasks/labs/polymorphism.qmd:19:53`; `tasks/labs/polymorphism.qmd:26:6` |
| `exr-fraction-number-subclass` (103) | `tasks/bank/polymorphism.qmd#exr-j25-polymorphism-fraction-number-subclass` | U06 | `practice/reserve-models.qmd:24:12`; `tasks/labs/reserve-models.qmd:30:5` |
| `exr-compatible-queue-reset` (112) | `tasks/bank/polymorphism.qmd#exr-j25-polymorphism-compatible-queue-reset` | U06 | `tasks/labs/reserve-models.qmd:31:5` |

### tasks/bank/text.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-bank-text` (7) | `tasks/bank/text.qmd#sec-j25-bank-text` | U06 | `tasks/bank/index.qmd:12:18` |
| `sec-bank-text-input` (12) | `tasks/bank/text.qmd#sec-j25-bank-text-input` | U06 | `practice/text.qmd:104:146`; `tasks/bank/text.qmd:30:33`; `tasks/bank/text.qmd:43:47`; `tasks/bank/text.qmd:57:47`; `tasks/bank/text.qmd:92:32`; `tasks/bank/text.qmd:131:8` |
| `exr-insert-substring` (27) | `tasks/bank/text.qmd#exr-j25-text-insert-substring` | U06 | `practice/text.qmd:114:8`; `tasks/labs/text.qmd:22:5` |
| `exr-replace-word-letter` (40) | `tasks/bank/text.qmd#exr-j25-text-replace-word-letter` | U06 | `practice/text.qmd:122:175`; `tasks/essays/text.qmd:29:27`; `tasks/labs/text.qmd:19:10`; `tasks/labs/text.qmd:23:6` |
| `exr-word-length-extrema` (54) | `tasks/bank/text.qmd#exr-j25-text-word-length-extrema` | U06 | `practice/text.qmd:114:68`; `tasks/essays/text.qmd:44:113`; `tasks/labs/text.qmd:24:5` |
| `exr-strong-password` (68) | `tasks/bank/text.qmd#exr-j25-text-strong-password` | U06 | `practice/text.qmd:120:8`; `tasks/essays/text.qmd:44:27`; `tasks/labs/text.qmd:25:5` |
| `exr-compress-character-runs` (75) | `tasks/bank/text.qmd#exr-j25-text-compress-character-runs` | U06 | `practice/text.qmd:122:8`; `tasks/bank/text.qmd:141:49`; `tasks/labs/text.qmd:26:5` |
| `exr-insert-after-word-ending` (89) | `tasks/bank/text.qmd#exr-j25-text-insert-after-word-ending` | U06 | `practice/text.qmd:122:211`; `tasks/labs/text.qmd:19:50`; `tasks/labs/text.qmd:27:6` |
| `exr-remove-nonletters` (104) | `tasks/bank/text.qmd#exr-j25-text-remove-nonletters` | U06 | `tasks/labs/reserve-algorithms.qmd:37:5` |
| `exr-longest-nonletter-substring` (111) | `tasks/bank/text.qmd#exr-j25-text-longest-nonletter-substring` | U06 | `tasks/labs/reserve-algorithms.qmd:38:5` |
| `exr-sum-text-digits` (118) | `tasks/bank/text.qmd#exr-j25-text-sum-text-digits` | U06 | `tasks/labs/reserve-algorithms.qmd:39:5` |
| `exr-count-normalized-word` (125) | `tasks/bank/text.qmd#exr-j25-text-count-normalized-word` | U06 | `practice/reserve-algorithms.qmd:99:25`; `tasks/essays/text.qmd:29:123`; `tasks/labs/reserve-algorithms.qmd:40:5` |
| `exr-decompress-character-runs` (138) | `tasks/bank/text.qmd#exr-j25-text-decompress-character-runs` | U06 | `practice/reserve-algorithms.qmd:67:12`; `practice/reserve-algorithms.qmd:87:79`; `tasks/labs/reserve-algorithms.qmd:41:5` |
| `exr-left-align-text` (152) | `tasks/bank/text.qmd#exr-j25-text-left-align-text` | U06 | `practice/reserve-algorithms.qmd:99:59`; `tasks/labs/reserve-algorithms.qmd:42:5` |
| `exr-bubble-sort-strings` (172) | `tasks/bank/text.qmd#exr-j25-text-bubble-sort-strings` | U06 | `tasks/labs/reserve-algorithms.qmd:43:5` |

### tasks/index.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-tasks-program` (1) | `tasks/index.qmd#sec-j25-tasks` | U06 | — |

### tasks/labs/arrays.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-lab-arrays` (10) | `tasks/labs/arrays.qmd#sec-j25-lab-arrays` | U06 | `_includes/semester-map.qmd:6:182`; `practice/arrays.qmd:13:19`; `practice/arrays.qmd:121:38`; `practice/index.qmd:11:69`; `tasks/_quarto.yml:247:11`; `tasks/labs/index.qmd:11:29` |
| `alt-arrays-transform` (27) | `tasks/labs/arrays.qmd#alt-j25-arrays-transform` | U06 | `docs/prairielearn-labs.md:9:22` |

### tasks/labs/collections.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-lab-collections` (7) | `tasks/labs/collections.qmd#sec-j25-lab-collections` | U06 | `_includes/semester-map.qmd:11:263`; `practice/collections.qmd:13:19`; `practice/collections.qmd:147:38`; `practice/index.qmd:16:74`; `tasks/labs/index.qmd:16:29` |

### tasks/labs/control.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-lab-control` (10) | `tasks/labs/control.qmd#sec-j25-lab-control` | U06 | `_includes/semester-map.qmd:5:259`; `practice/control.qmd:13:19`; `practice/control.qmd:128:38`; `practice/index.qmd:10:70`; `tasks/_quarto.yml:246:11`; `tasks/labs/index.qmd:10:29` |
| `alt-control-input` (29) | `tasks/labs/control.qmd#alt-j25-control-input` | U06 | `docs/prairielearn-labs.md:8:38` |

### tasks/labs/files.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-lab-files` (7) | `tasks/labs/files.qmd#sec-j25-lab-files` | U06 | `_includes/semester-map.qmd:13:255`; `_includes/semester-map.qmd:14:253`; `practice/files.qmd:15:32`; `practice/files.qmd:214:36`; `practice/index.qmd:18:68`; `tasks/labs/index.qmd:18:29` |

### tasks/labs/generics.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-lab-generics` (7) | `tasks/labs/generics.qmd#sec-j25-lab-generics` | U06 | `_includes/semester-map.qmd:10:261`; `practice/generics.qmd:13:21`; `practice/generics.qmd:111:24`; `practice/index.qmd:15:71`; `tasks/labs/index.qmd:15:29` |

### tasks/labs/index.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-lab-program` (1) | `tasks/labs/index.qmd#sec-j25-labs` | U06 | `handbook/index.qmd:7:87`; `lectures/index.qmd:21:51`; `tasks/bank/index.qmd:7:54`; `tasks/index.qmd:5:18`; `theory/index.qmd:19:269` |

### tasks/labs/numbers.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-lab-numbers` (10) | `tasks/labs/numbers.qmd#sec-j25-lab-numbers` | U06 | `_includes/semester-map.qmd:3:249`; `_includes/semester-map.qmd:4:253`; `practice/index.qmd:9:70`; `practice/numbers.qmd:13:21`; `practice/numbers.qmd:212:8`; `practice/numbers.qmd:233:36`; `tasks/_quarto.yml:245:11`; `tasks/labs/index.qmd:9:29`; `theory/chapters/types.qmd:408:8` |
| `alt-numbers-conversion` (28) | `tasks/labs/numbers.qmd#alt-j25-numbers-conversion` | U06 | `docs/prairielearn-labs.md:7:34` |

### tasks/labs/objects.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-lab-objects` (7) | `tasks/labs/objects.qmd#sec-j25-lab-objects` | U06 | `_includes/semester-map.qmd:8:245`; `practice/index.qmd:13:70`; `practice/objects.qmd:13:21`; `practice/objects.qmd:102:39`; `tasks/labs/index.qmd:13:29` |

### tasks/labs/pipelines.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-lab-pipelines` (7) | `tasks/labs/pipelines.qmd#sec-j25-lab-pipelines` | U06 | `_includes/semester-map.qmd:12:274`; `practice/index.qmd:17:72`; `practice/pipelines.qmd:13:19`; `practice/pipelines.qmd:113:23`; `practice/pipelines.qmd:149:38`; `tasks/labs/index.qmd:17:29` |

### tasks/labs/polymorphism.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-lab-polymorphism` (7) | `tasks/labs/polymorphism.qmd#sec-j25-lab-polymorphism` | U06 | `_includes/semester-map.qmd:9:334`; `practice/index.qmd:14:75`; `practice/polymorphism.qmd:13:21`; `practice/polymorphism.qmd:117:24`; `tasks/labs/index.qmd:14:29` |

### tasks/labs/reserve-algorithms.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-lab-reserve-algorithms` (7) | `tasks/labs/reserve-algorithms.qmd#sec-j25-lab-reserve-algorithms` | U06 | `_includes/semester-map.qmd:15:306`; `practice/index.qmd:19:81`; `practice/reserve-algorithms.qmd:13:19`; `practice/reserve-algorithms.qmd:107:38`; `tasks/labs/index.qmd:19:27` |

### tasks/labs/reserve-data.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-lab-reserve-data` (7) | `tasks/labs/reserve-data.qmd#sec-j25-lab-reserve-data` | U06 | `_includes/semester-map.qmd:17:430`; `practice/index.qmd:21:75`; `practice/reserve-data.qmd:13:19`; `practice/reserve-data.qmd:50:21`; `practice/reserve-data.qmd:144:38`; `tasks/labs/index.qmd:21:27` |

### tasks/labs/reserve-models.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-lab-reserve-models` (7) | `tasks/labs/reserve-models.qmd#sec-j25-lab-reserve-models` | U06 | `_includes/semester-map.qmd:16:378`; `practice/index.qmd:20:77`; `practice/reserve-models.qmd:13:8`; `practice/reserve-models.qmd:101:21`; `tasks/labs/index.qmd:20:27` |

### tasks/labs/text.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-lab-text` (7) | `tasks/labs/text.qmd#sec-j25-lab-text` | U06 | `_includes/semester-map.qmd:7:236`; `index.qmd:11:81`; `practice/index.qmd:12:67`; `practice/text.qmd:13:19`; `practice/text.qmd:128:38`; `tasks/labs/index.qmd:12:29` |

### theory/chapters/application.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-application` (1) | `theory/chapters/application.qmd#sec-j25-theory-application` | U05 | `_includes/semester-map.qmd:14:91`; `handbook/entries/files.qmd:72:9`; `handbook/entries/java-tools.qmd:68:175`; `handbook/entries/project.qmd:4:9`; `lectures/01-platform.qmd:425:68`; `lectures/08-exceptions.qmd:669:49`; `lectures/09-application.qmd:40:23`; `lectures/09-application.qmd:431:39`; `lectures/index.qmd:19:168`; `practice/reserve-data.qmd:15:17`; `tasks/bank/projects.qmd:9:17`; `tasks/labs/reserve-data.qmd:11:17`; `theory/chapters/collections.qmd:71:12`; `theory/chapters/collections.qmd:320:9`; `theory/chapters/io.qmd:106:12`; `theory/chapters/io.qmd:293:11`; `theory/chapters/platform.qmd:115:433`; `theory/chapters/streams.qmd:112:17` |
| `sec-t-application-behavior` (17) | `theory/chapters/application.qmd#sec-j25-theory-application-behavior` | U05 | — |
| `sec-t-application-student` (53) | `theory/chapters/application.qmd#sec-j25-theory-application-student` | U05 | — |
| `sec-t-application-parser` (94) | `theory/chapters/application.qmd#sec-j25-theory-application-parser` | U05 | — |
| `sec-t-application-loader` (145) | `theory/chapters/application.qmd#sec-j25-theory-application-loader` | U05 | — |
| `sec-t-application-service` (201) | `theory/chapters/application.qmd#sec-j25-theory-application-service` | U05 | — |
| `sec-t-application-main` (244) | `theory/chapters/application.qmd#sec-j25-theory-application-main` | U05 | — |
| `sec-t-application-validation` (315) | `theory/chapters/application.qmd#sec-j25-theory-application-validation` | U05 | `lectures/09-application.qmd:289:25` |
| `sec-t-application-boundaries` (337) | `theory/chapters/application.qmd#sec-j25-theory-application-boundaries` | U05 | — |
| `sec-t-application-success` (366) | `theory/chapters/application.qmd#sec-j25-theory-application-success` | U05 | `handbook/entries/project.qmd:96:9` |
| `sec-t-application-checks` (396) | `theory/chapters/application.qmd#sec-j25-theory-application-checks` | U05 | — |
| `sec-t-application-evolution` (421) | `theory/chapters/application.qmd#sec-j25-theory-application-evolution` | U05 | — |

### theory/chapters/arrays.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-arrays` (1) | `theory/chapters/arrays.qmd#sec-j25-theory-arrays` | U01 | `_includes/semester-map.qmd:6:29`; `_includes/semester-map.qmd:15:141`; `handbook/entries/arrays.qmd:3:162`; `lectures/index.qmd:12:271`; `practice/arrays.qmd:15:17`; `practice/reserve-algorithms.qmd:15:17`; `tasks/bank/arrays.qmd:9:17`; `tasks/essays/arrays.qmd:12:32`; `tasks/essays/collections.qmd:42:30`; `tasks/essays/exceptions.qmd:27:33`; `tasks/essays/generics.qmd:27:30`; `tasks/essays/numbers.qmd:27:103`; `tasks/essays/objects.qmd:12:25`; `tasks/essays/objects.qmd:27:46`; `tasks/essays/objects.qmd:72:25`; `tasks/essays/pipelines.qmd:27:53`; `tasks/essays/polymorphism.qmd:27:25`; `tasks/essays/text.qmd:27:33`; `tasks/labs/arrays.qmd:14:17`; `tasks/labs/reserve-algorithms.qmd:11:17`; `theory/chapters/control.qmd:168:322`; `theory/chapters/types.qmd:272:30` |
| `sec-t-arrays-model` (5) | `theory/chapters/arrays.qmd#sec-j25-theory-arrays-model` | U01 | — |
| `sec-t-arrays-creation` (41) | `theory/chapters/arrays.qmd#sec-j25-theory-arrays-creation` | U01 | `lectures/02-values.qmd:817:9` |
| `sec-t-arrays-bounds` (67) | `theory/chapters/arrays.qmd#sec-j25-theory-arrays-bounds` | U01 | — |
| `sec-t-arrays-traversal` (82) | `theory/chapters/arrays.qmd#sec-j25-theory-arrays-traversal` | U01 | — |
| `sec-t-arrays-copying` (115) | `theory/chapters/arrays.qmd#sec-j25-theory-arrays-copying` | U01 | — |
| `sec-t-arrays-nested` (140) | `theory/chapters/arrays.qmd#sec-j25-theory-arrays-nested` | U01 | `practice/arrays.qmd:87:9` |
| `sec-t-arrays-row-copy` (168) | `theory/chapters/arrays.qmd#sec-j25-theory-arrays-row-copy` | U01 | `practice/arrays.qmd:45:252` |
| `sec-t-arrays-covariance` (188) | `theory/chapters/arrays.qmd#sec-j25-theory-arrays-covariance` | U01 | — |
| `sec-t-arrays-utilities` (204) | `theory/chapters/arrays.qmd#sec-j25-theory-arrays-utilities` | U01 | — |
| `sec-t-arrays-algorithm-design` (212) | `theory/chapters/arrays.qmd#sec-j25-theory-arrays-algorithm-design` | U01 | `practice/reserve-algorithms.qmd:75:123` |

### theory/chapters/classes.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-classes` (5) | `theory/chapters/classes.qmd#sec-j25-theory-classes` | U02 | `_includes/semester-map.qmd:8:89`; `_includes/semester-map.qmd:16:43`; `handbook/entries/classes.qmd:7:214`; `handbook/entries/methods.qmd:18:295`; `lectures/01-platform.qmd:543:71`; `lectures/03-classes.qmd:684:191`; `lectures/index.qmd:13:123`; `tasks/bank/objects.qmd:9:17`; `tasks/bank/projects.qmd:36:45`; `tasks/bank/projects.qmd:50:45`; `tasks/essays/collections.qmd:12:73`; `tasks/essays/collections.qmd:42:52`; `tasks/essays/control.qmd:12:132`; `tasks/essays/exceptions.qmd:12:33`; `tasks/essays/exceptions.qmd:27:55`; `tasks/essays/generics.qmd:12:30`; `tasks/essays/generics.qmd:27:52`; `tasks/essays/numbers.qmd:12:55`; `tasks/essays/numbers.qmd:42:81`; `tasks/essays/numbers.qmd:57:55`; `tasks/essays/objects.qmd:12:67`; `tasks/essays/objects.qmd:27:68`; `tasks/essays/objects.qmd:42:25`; `tasks/essays/objects.qmd:57:25`; `tasks/essays/objects.qmd:72:47`; `tasks/essays/pipelines.qmd:12:50`; `tasks/essays/pipelines.qmd:57:50`; `tasks/essays/polymorphism.qmd:12:25`; `tasks/essays/polymorphism.qmd:27:47`; `tasks/essays/polymorphism.qmd:42:48`; `tasks/essays/text.qmd:12:53`; `tasks/essays/text.qmd:42:95`; `tasks/labs/objects.qmd:11:17`; `theory/chapters/methods.qmd:46:439`; `theory/chapters/objects.qmd:13:87`; `theory/chapters/objects.qmd:166:18`; `theory/chapters/objects.qmd:278:385`; `theory/chapters/platform.qmd:102:204` |
| `sec-t-classes-state` (11) | `theory/chapters/classes.qmd#sec-j25-theory-classes-state` | U02 | — |
| `sec-t-classes-invariant` (49) | `theory/chapters/classes.qmd#sec-j25-theory-classes-invariant` | U02 | `lectures/03-classes.qmd:304:36`; `practice/objects.qmd:73:9`; `practice/polymorphism.qmd:60:37` |
| `sec-t-classes-constructor` (87) | `theory/chapters/classes.qmd#sec-j25-theory-classes-constructor` | U02 | `practice/objects.qmd:82:34` |
| `sec-t-classes-this` (122) | `theory/chapters/classes.qmd#sec-j25-theory-classes-this` | U02 | — |
| `sec-t-classes-overload` (138) | `theory/chapters/classes.qmd#sec-j25-theory-classes-overload` | U02 | — |
| `sec-t-classes-static` (154) | `theory/chapters/classes.qmd#sec-j25-theory-classes-static` | U02 | — |
| `sec-t-classes-final` (175) | `theory/chapters/classes.qmd#sec-j25-theory-classes-final` | U02 | — |
| `sec-t-classes-sharing` (187) | `theory/chapters/classes.qmd#sec-j25-theory-classes-sharing` | U02 | `lectures/03-classes.qmd:583:9`; `theory/chapters/interfaces.qmd:114:2` |
| `sec-t-classes-initialization` (265) | `theory/chapters/classes.qmd#sec-j25-theory-classes-initialization` | U02 | — |

### theory/chapters/collections.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-collections` (1) | `theory/chapters/collections.qmd#sec-j25-theory-collections` | U04 | `_includes/semester-map.qmd:11:95`; `_includes/semester-map.qmd:17:46`; `handbook/entries/arrays.qmd:60:256`; `handbook/entries/lists.qmd:7:35`; `handbook/entries/sets-maps.qmd:4:9`; `lectures/06-collections.qmd:563:24`; `lectures/index.qmd:16:128`; `practice/collections.qmd:15:17`; `tasks/bank/collections.qmd:9:17`; `tasks/bank/projects.qmd:37:49`; `tasks/bank/projects.qmd:51:10`; `tasks/essays/collections.qmd:12:128`; `tasks/essays/collections.qmd:27:108`; `tasks/essays/collections.qmd:42:106`; `tasks/essays/generics.qmd:12:111`; `tasks/essays/generics.qmd:27:132`; `tasks/essays/numbers.qmd:42:154`; `tasks/essays/objects.qmd:57:105`; `tasks/essays/pipelines.qmd:12:266`; `tasks/essays/pipelines.qmd:27:126`; `tasks/essays/pipelines.qmd:42:81`; `tasks/essays/pipelines.qmd:57:104`; `tasks/labs/collections.qmd:11:17`; `theory/chapters/application.qmd:11:33`; `theory/chapters/arrays.qmd:210:403`; `theory/chapters/equality.qmd:255:39` |
| `sec-t-collections-framework` (12) | `theory/chapters/collections.qmd#sec-j25-theory-collections-framework` | U04 | — |
| `sec-t-collections-catalog` (37) | `theory/chapters/collections.qmd#sec-j25-theory-collections-catalog` | U04 | `lectures/06-collections.qmd:424:22` |
| `sec-t-collections-list` (79) | `theory/chapters/collections.qmd#sec-j25-theory-collections-list` | U04 | — |
| `sec-t-collections-cost` (113) | `theory/chapters/collections.qmd#sec-j25-theory-collections-cost` | U04 | — |
| `sec-t-collections-iteration` (135) | `theory/chapters/collections.qmd#sec-j25-theory-collections-iteration` | U04 | — |
| `sec-t-collections-set` (165) | `theory/chapters/collections.qmd#sec-j25-theory-collections-set` | U04 | — |
| `sec-t-collections-map` (195) | `theory/chapters/collections.qmd#sec-j25-theory-collections-map` | U04 | — |
| `sec-t-collections-views` (224) | `theory/chapters/collections.qmd#sec-j25-theory-collections-views` | U04 | — |
| `sec-t-collections-queue` (250) | `theory/chapters/collections.qmd#sec-j25-theory-collections-queue` | U04 | — |
| `sec-t-collections-unmodifiable` (289) | `theory/chapters/collections.qmd#sec-j25-theory-collections-unmodifiable` | U04 | — |
| `sec-t-collections-container-equality` (323) | `theory/chapters/collections.qmd#sec-j25-theory-collections-container-equality` | U04 | — |

### theory/chapters/control.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-control` (1) | `theory/chapters/control.qmd#sec-j25-theory-control` | U01 | `_includes/semester-map.qmd:5:53`; `_includes/semester-map.qmd:15:39`; `handbook/entries/statements.qmd:3:158`; `lectures/index.qmd:12:173`; `practice/control.qmd:15:17`; `tasks/bank/control.qmd:9:17`; `tasks/essays/collections.qmd:12:30`; `tasks/essays/control.qmd:12:54`; `tasks/essays/numbers.qmd:27:54`; `tasks/essays/pipelines.qmd:27:30`; `tasks/essays/polymorphism.qmd:42:25`; `tasks/essays/text.qmd:42:33`; `tasks/labs/control.qmd:14:17`; `theory/chapters/expressions.qmd:170:79` |
| `sec-t-control-structure` (5) | `theory/chapters/control.qmd#sec-j25-theory-control-structure` | U01 | — |
| `sec-t-control-if` (33) | `theory/chapters/control.qmd#sec-j25-theory-control-if` | U01 | `lectures/02-values.qmd:503:9` |
| `sec-t-control-guards` (53) | `theory/chapters/control.qmd#sec-j25-theory-control-guards` | U01 | — |
| `sec-t-control-switch` (73) | `theory/chapters/control.qmd#sec-j25-theory-control-switch` | U01 | — |
| `sec-t-control-while` (120) | `theory/chapters/control.qmd#sec-j25-theory-control-while` | U01 | — |
| `sec-t-control-for` (137) | `theory/chapters/control.qmd#sec-j25-theory-control-for` | U01 | — |
| `sec-t-control-enhanced` (156) | `theory/chapters/control.qmd#sec-j25-theory-control-enhanced` | U01 | — |
| `sec-t-control-nested` (170) | `theory/chapters/control.qmd#sec-j25-theory-control-nested` | U01 | — |
| `sec-t-control-transfers` (191) | `theory/chapters/control.qmd#sec-j25-theory-control-transfers` | U01 | — |
| `sec-t-control-input-state` (210) | `theory/chapters/control.qmd#sec-j25-theory-control-input-state` | U01 | `practice/control.qmd:108:9` |

### theory/chapters/data-classes.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-data-classes` (5) | `theory/chapters/data-classes.qmd#sec-j25-theory-data-classes` | U02 | `_includes/semester-map.qmd:9:162`; `_includes/semester-map.qmd:16:148`; `handbook/entries/classes.qmd:96:661`; `lectures/04-object-design.qmd:513:166`; `lectures/index.qmd:14:185`; `practice/reserve-models.qmd:33:9`; `theory/chapters/inheritance.qmd:237:242`; `theory/chapters/interfaces.qmd:274:458`; `theory/chapters/objects.qmd:85:2` |
| `sec-t-data-classes-value` (11) | `theory/chapters/data-classes.qmd#sec-j25-theory-data-classes-value` | U02 | — |
| `sec-t-data-classes-record` (50) | `theory/chapters/data-classes.qmd#sec-j25-theory-data-classes-record` | U02 | — |
| `sec-t-data-classes-record-model` (67) | `theory/chapters/data-classes.qmd#sec-j25-theory-data-classes-record-model` | U02 | — |
| `sec-t-data-classes-constructor` (94) | `theory/chapters/data-classes.qmd#sec-j25-theory-data-classes-constructor` | U02 | — |
| `sec-t-data-classes-shallow` (128) | `theory/chapters/data-classes.qmd#sec-j25-theory-data-classes-shallow` | U02 | — |
| `sec-t-data-classes-enum` (163) | `theory/chapters/data-classes.qmd#sec-j25-theory-data-classes-enum` | U02 | — |
| `sec-t-data-classes-sealed` (191) | `theory/chapters/data-classes.qmd#sec-j25-theory-data-classes-sealed` | U02 | — |
| `sec-t-data-classes-result-evolution` (216) | `theory/chapters/data-classes.qmd#sec-j25-theory-data-classes-result-evolution` | U02 | `lectures/04-object-design.qmd:705:52`; `theory/chapters/type-parameters.qmd:394:46` |
| `sec-t-data-classes-choice` (272) | `theory/chapters/data-classes.qmd#sec-j25-theory-data-classes-choice` | U02 | — |

### theory/chapters/equality.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-equality` (1) | `theory/chapters/equality.qmd#sec-j25-theory-equality` | U04 | `_includes/semester-map.qmd:11:43`; `handbook/entries/comparison.qmd:4:9`; `handbook/entries/sets-maps.qmd:4:36`; `lectures/06-collections.qmd:39:53`; `lectures/index.qmd:16:78`; `practice/collections.qmd:39:95`; `practice/polymorphism.qmd:71:9`; `practice/reserve-models.qmd:33:37`; `theory/chapters/collections.qmd:10:9`; `theory/chapters/text.qmd:78:212` |
| `sec-t-equality-identity` (11) | `theory/chapters/equality.qmd#sec-j25-theory-equality-identity` | U04 | — |
| `sec-t-equality-domain` (43) | `theory/chapters/equality.qmd#sec-j25-theory-equality-domain` | U04 | `lectures/06-collections.qmd:411:38`; `theory/chapters/collections.qmd:39:43` |
| `sec-t-equality-contract` (83) | `theory/chapters/equality.qmd#sec-j25-theory-equality-contract` | U04 | — |
| `sec-t-equality-hash` (132) | `theory/chapters/equality.qmd#sec-j25-theory-equality-hash` | U04 | — |
| `sec-t-equality-mutable` (163) | `theory/chapters/equality.qmd#sec-j25-theory-equality-mutable` | U04 | — |
| `sec-t-equality-order` (185) | `theory/chapters/equality.qmd#sec-j25-theory-equality-order` | U04 | — |
| `sec-t-equality-tree` (225) | `theory/chapters/equality.qmd#sec-j25-theory-equality-tree` | U04 | — |
| `sec-t-equality-composite` (259) | `theory/chapters/equality.qmd#sec-j25-theory-equality-composite` | U04 | — |

### theory/chapters/exceptions.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-exceptions` (1) | `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions` | U05 | `_includes/semester-map.qmd:13:42`; `handbook/entries/exceptions.qmd:4:9`; `lectures/08-exceptions.qmd:47:29`; `lectures/index.qmd:18:77`; `practice/generics.qmd:93:9`; `tasks/bank/exceptions.qmd:9:17`; `tasks/bank/generics.qmd:47:378`; `tasks/bank/generics.qmd:56:322`; `tasks/essays/collections.qmd:42:146`; `tasks/essays/control.qmd:10:495`; `tasks/essays/exceptions.qmd:12:83`; `tasks/essays/exceptions.qmd:27:79`; `tasks/essays/files.qmd:12:60`; `tasks/essays/files.qmd:27:53`; `tasks/essays/generics.qmd:27:160`; `tasks/essays/numbers.qmd:27:189`; `tasks/essays/numbers.qmd:57:140`; `tasks/essays/pipelines.qmd:42:307`; `tasks/essays/pipelines.qmd:57:167`; `tasks/essays/polymorphism.qmd:27:255`; `tasks/labs/generics.qmd:13:92`; `theory/chapters/application.qmd:12:34`; `theory/chapters/classes.qmd:76:49`; `theory/chapters/control.qmd:69:251`; `theory/chapters/functions.qmd:331:9` |
| `sec-t-exceptions-contract` (12) | `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions-contract` | U05 | — |
| `sec-t-exceptions-result` (35) | `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions-result` | U05 | — |
| `sec-t-exceptions-typed-result` (70) | `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions-typed-result` | U05 | `handbook/entries/exceptions.qmd:91:43`; `handbook/entries/optional.qmd:64:9`; `lectures/08-exceptions.qmd:165:25`; `theory/chapters/resources.qmd:405:11`; `theory/chapters/streams.qmd:101:11` |
| `sec-t-exceptions-language-results` (137) | `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions-language-results` | U05 | `lectures/08-exceptions.qmd:223:51` |
| `sec-t-exceptions-unwinding` (208) | `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions-unwinding` | U05 | — |
| `sec-t-exceptions-hierarchy` (239) | `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions-hierarchy` | U05 | `handbook/entries/console.qmd:67:42` |
| `sec-t-exceptions-catch` (287) | `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions-catch` | U05 | — |
| `sec-t-exceptions-cause` (322) | `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions-cause` | U05 | — |
| `sec-t-exceptions-finally` (360) | `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions-finally` | U05 | — |
| `sec-t-exceptions-state` (390) | `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions-state` | U05 | `practice/files.qmd:161:9`; `theory/chapters/streams.qmd:77:9` |
| `sec-t-exceptions-recovery` (442) | `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions-recovery` | U05 | `handbook/entries/exceptions.qmd:98:28` |
| `sec-t-exceptions-boundary` (484) | `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions-boundary` | U05 | — |

### theory/chapters/expressions.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-expressions` (1) | `theory/chapters/expressions.qmd#sec-j25-theory-expressions` | U01 | `_includes/semester-map.qmd:4:94`; `handbook/entries/operators.qmd:3:97`; `lectures/index.qmd:12:120`; `practice/numbers.qmd:169:9`; `theory/chapters/types.qmd:192:9` |
| `sec-t-expressions-state` (5) | `theory/chapters/expressions.qmd#sec-j25-theory-expressions-state` | U01 | — |
| `sec-t-expressions-promotion` (44) | `theory/chapters/expressions.qmd#sec-j25-theory-expressions-promotion` | U01 | — |
| `sec-t-expressions-overflow` (71) | `theory/chapters/expressions.qmd#sec-j25-theory-expressions-overflow` | U01 | — |
| `sec-t-expressions-division` (88) | `theory/chapters/expressions.qmd#sec-j25-theory-expressions-division` | U01 | `practice/reserve-algorithms.qmd:61:65` |
| `sec-t-expressions-floating` (103) | `theory/chapters/expressions.qmd#sec-j25-theory-expressions-floating` | U01 | — |
| `sec-t-expressions-order` (117) | `theory/chapters/expressions.qmd#sec-j25-theory-expressions-order` | U01 | `lectures/02-values.qmd:417:89` |
| `sec-t-expressions-short-circuit` (132) | `theory/chapters/expressions.qmd#sec-j25-theory-expressions-short-circuit` | U01 | — |
| `sec-t-expressions-predicates` (146) | `theory/chapters/expressions.qmd#sec-j25-theory-expressions-predicates` | U01 | — |
| `sec-t-expressions-conditional` (172) | `theory/chapters/expressions.qmd#sec-j25-theory-expressions-conditional` | U01 | — |
| `sec-t-expressions-bits-text` (187) | `theory/chapters/expressions.qmd#sec-j25-theory-expressions-bits-text` | U01 | — |

### theory/chapters/functions.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-functions` (1) | `theory/chapters/functions.qmd#sec-j25-theory-functions` | U04 | `_includes/semester-map.qmd:12:61`; `handbook/entries/functions.qmd:3:55`; `lectures/07-functions.qmd:40:44`; `lectures/index.qmd:17:76`; `theory/chapters/application.qmd:12:9`; `theory/chapters/collections.qmd:221:20`; `theory/chapters/streams.qmd:14:9` |
| `sec-t-functions-selection` (11) | `theory/chapters/functions.qmd#sec-j25-theory-functions-selection` | U04 | — |
| `sec-t-functions-context` (61) | `theory/chapters/functions.qmd#sec-j25-theory-functions-context` | U04 | `lectures/07-functions.qmd:204:24` |
| `sec-t-functions-call` (99) | `theory/chapters/functions.qmd#sec-j25-theory-functions-call` | U04 | — |
| `sec-t-functions-target` (127) | `theory/chapters/functions.qmd#sec-j25-theory-functions-target` | U04 | — |
| `sec-t-functions-standard` (168) | `theory/chapters/functions.qmd#sec-j25-theory-functions-standard` | U04 | — |
| `sec-t-functions-method-reference` (201) | `theory/chapters/functions.qmd#sec-j25-theory-functions-method-reference` | U04 | `lectures/06-collections.qmd:164:9`; `theory/chapters/equality.qmd:211:9` |
| `sec-t-functions-capture` (223) | `theory/chapters/functions.qmd#sec-j25-theory-functions-capture` | U04 | — |
| `sec-t-functions-returned` (248) | `theory/chapters/functions.qmd#sec-j25-theory-functions-returned` | U04 | `handbook/entries/functions.qmd:93:45`; `lectures/07-functions.qmd:277:48`; `practice/pipelines.qmd:131:19`; `practice/reserve-data.qmd:62:56` |
| `sec-t-functions-composition` (302) | `theory/chapters/functions.qmd#sec-j25-theory-functions-composition` | U04 | — |
| `sec-t-functions-testing` (336) | `theory/chapters/functions.qmd#sec-j25-theory-functions-testing` | U04 | — |

### theory/chapters/inheritance.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-inheritance` (5) | `theory/chapters/inheritance.qmd#sec-j25-theory-inheritance` | U02 | `_includes/semester-map.qmd:9:53`; `handbook/entries/inheritance.qmd:7:181`; `lectures/04-object-design.qmd:30:187`; `lectures/index.qmd:14:80`; `practice/polymorphism.qmd:33:38`; `theory/chapters/classes.qmd:287:372` |
| `sec-t-inheritance-subclass` (11) | `theory/chapters/inheritance.qmd#sec-j25-theory-inheritance-subclass` | U02 | — |
| `sec-t-inheritance-construction` (48) | `theory/chapters/inheritance.qmd#sec-j25-theory-inheritance-construction` | U02 | — |
| `sec-t-inheritance-dispatch` (56) | `theory/chapters/inheritance.qmd#sec-j25-theory-inheritance-dispatch` | U02 | — |
| `sec-t-inheritance-overload` (93) | `theory/chapters/inheritance.qmd#sec-j25-theory-inheritance-overload` | U02 | — |
| `sec-t-inheritance-contract` (128) | `theory/chapters/inheritance.qmd#sec-j25-theory-inheritance-contract` | U02 | — |
| `sec-t-inheritance-substitution` (134) | `theory/chapters/inheritance.qmd#sec-j25-theory-inheritance-substitution` | U02 | `lectures/04-object-design.qmd:210:9` |
| `sec-t-inheritance-abstract` (185) | `theory/chapters/inheritance.qmd#sec-j25-theory-inheritance-abstract` | U02 | — |
| `sec-t-inheritance-constructor-call` (221) | `theory/chapters/inheritance.qmd#sec-j25-theory-inheritance-constructor-call` | U02 | `handbook/entries/inheritance.qmd:62:201` |

### theory/chapters/interfaces.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-interfaces` (5) | `theory/chapters/interfaces.qmd#sec-j25-theory-interfaces` | U02 | `_includes/semester-map.qmd:9:108`; `_includes/semester-map.qmd:16:94`; `handbook/entries/interfaces.qmd:7:208`; `lectures/04-object-design.qmd:300:172`; `lectures/index.qmd:14:133`; `tasks/bank/polymorphism.qmd:9:17`; `tasks/bank/projects.qmd:37:10`; `tasks/essays/collections.qmd:27:50`; `tasks/essays/exceptions.qmd:12:56`; `tasks/essays/exceptions.qmd:27:142`; `tasks/essays/files.qmd:12:33`; `tasks/essays/generics.qmd:12:53`; `tasks/essays/generics.qmd:27:75`; `tasks/essays/numbers.qmd:12:105`; `tasks/essays/objects.qmd:42:48`; `tasks/essays/objects.qmd:57:48`; `tasks/essays/objects.qmd:72:70`; `tasks/essays/pipelines.qmd:12:73`; `tasks/essays/polymorphism.qmd:12:48`; `tasks/essays/polymorphism.qmd:27:70`; `tasks/essays/polymorphism.qmd:42:71`; `tasks/labs/polymorphism.qmd:11:17`; `tasks/labs/reserve-models.qmd:11:17`; `theory/chapters/inheritance.qmd:46:257`; `theory/chapters/inheritance.qmd:217:50`; `theory/chapters/inheritance.qmd:237:402` |
| `sec-t-interfaces-requirements` (11) | `theory/chapters/interfaces.qmd#sec-j25-theory-interfaces-requirements` | U02 | `lectures/04-object-design.qmd:430:52` |
| `sec-t-interfaces-role` (41) | `theory/chapters/interfaces.qmd#sec-j25-theory-interfaces-role` | U02 | — |
| `sec-t-interfaces-contract` (84) | `theory/chapters/interfaces.qmd#sec-j25-theory-interfaces-contract` | U02 | — |
| `sec-t-interfaces-composition` (126) | `theory/chapters/interfaces.qmd#sec-j25-theory-interfaces-composition` | U02 | `lectures/04-object-design.qmd:393:139` |
| `sec-t-interfaces-multiple` (180) | `theory/chapters/interfaces.qmd#sec-j25-theory-interfaces-multiple` | U02 | — |
| `sec-t-interfaces-default` (203) | `theory/chapters/interfaces.qmd#sec-j25-theory-interfaces-default` | U02 | `lectures/04-object-design.qmd:474:9` |
| `sec-t-interfaces-helpers` (262) | `theory/chapters/interfaces.qmd#sec-j25-theory-interfaces-helpers` | U02 | — |
| `sec-t-interfaces-design` (268) | `theory/chapters/interfaces.qmd#sec-j25-theory-interfaces-design` | U02 | — |

### theory/chapters/io.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-io` (1) | `theory/chapters/io.qmd#sec-j25-theory-io` | U05 | `_includes/semester-map.qmd:14:45`; `_includes/semester-map.qmd:17:152`; `handbook/entries/files.qmd:4:9`; `lectures/09-application.qmd:133:17`; `lectures/index.qmd:19:124`; `tasks/bank/files.qmd:9:17`; `tasks/essays/files.qmd:12:97`; `tasks/essays/files.qmd:27:79`; `tasks/essays/objects.qmd:12:219`; `theory/chapters/application.qmd:12:61`; `theory/chapters/exceptions.qmd:431:23`; `theory/chapters/resources.qmd:337:29`; `theory/chapters/streams.qmd:310:35`; `theory/chapters/text.qmd:195:236`; `theory/system-practice.qmd:11:227` |
| `sec-t-io-path` (11) | `theory/chapters/io.qmd#sec-j25-theory-io-path` | U05 | — |
| `sec-t-io-bytes` (43) | `theory/chapters/io.qmd#sec-j25-theory-io-bytes` | U05 | `practice/reserve-data.qmd:117:26` |
| `sec-t-io-charset` (76) | `theory/chapters/io.qmd#sec-j25-theory-io-charset` | U05 | — |
| `sec-t-io-decoding` (110) | `theory/chapters/io.qmd#sec-j25-theory-io-decoding` | U05 | `lectures/09-application.qmd:154:22` |
| `sec-t-io-lines` (144) | `theory/chapters/io.qmd#sec-j25-theory-io-lines` | U05 | — |
| `sec-t-io-format` (191) | `theory/chapters/io.qmd#sec-j25-theory-io-format` | U05 | — |
| `sec-t-io-writing` (217) | `theory/chapters/io.qmd#sec-j25-theory-io-writing` | U05 | — |
| `sec-t-io-publication` (245) | `theory/chapters/io.qmd#sec-j25-theory-io-publication` | U05 | `handbook/entries/project.qmd:97:28`; `lectures/09-application.qmd:558:47`; `practice/reserve-data.qmd:107:30`; `theory/chapters/application.qmd:392:11` |
| `sec-t-io-boundaries` (277) | `theory/chapters/io.qmd#sec-j25-theory-io-boundaries` | U05 | — |
| `sec-t-io-line-endings` (297) | `theory/chapters/io.qmd#sec-j25-theory-io-line-endings` | U05 | — |

### theory/chapters/languages.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-languages` (1) | `theory/chapters/languages.qmd#sec-j25-theory-languages` | U01 | `_includes/semester-map.qmd:3:38`; `lectures/index.qmd:11:75` |
| `sec-t-languages-abstraction` (5) | `theory/chapters/languages.qmd#sec-j25-theory-languages-abstraction` | U01 | `lectures/01-platform.qmd:131:9` |
| `sec-t-languages-calculation` (35) | `theory/chapters/languages.qmd#sec-j25-theory-languages-calculation` | U01 | `lectures/01-platform.qmd:34:9` |
| `sec-t-languages-levels` (52) | `theory/chapters/languages.qmd#sec-j25-theory-languages-levels` | U01 | `lectures/01-platform.qmd:86:9` |
| `sec-t-languages-c-family` (69) | `theory/chapters/languages.qmd#sec-j25-theory-languages-c-family` | U01 | `lectures/01-platform.qmd:179:9` |
| `sec-t-languages-typing` (79) | `theory/chapters/languages.qmd#sec-j25-theory-languages-typing` | U01 | — |
| `sec-t-languages-paradigms` (99) | `theory/chapters/languages.qmd#sec-j25-theory-languages-paradigms` | U01 | — |
| `sec-t-languages-java-origins` (107) | `theory/chapters/languages.qmd#sec-j25-theory-languages-java-origins` | U01 | — |
| `sec-t-languages-evolution` (115) | `theory/chapters/languages.qmd#sec-j25-theory-languages-evolution` | U01 | `lectures/01-platform.qmd:607:105` |

### theory/chapters/methods.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-methods` (1) | `theory/chapters/methods.qmd#sec-j25-theory-methods` | U01 | `_includes/semester-map.qmd:5:104`; `_includes/semester-map.qmd:15:90`; `handbook/entries/methods.qmd:3:59`; `lectures/index.qmd:12:222`; `theory/chapters/platform.qmd:59:348`; `theory/chapters/text.qmd:61:294` |
| `sec-t-methods-decomposition` (5) | `theory/chapters/methods.qmd#sec-j25-theory-methods-decomposition` | U01 | — |
| `sec-t-methods-declaration` (33) | `theory/chapters/methods.qmd#sec-j25-theory-methods-declaration` | U01 | `lectures/02-values.qmd:734:9` |
| `sec-t-methods-call` (56) | `theory/chapters/methods.qmd#sec-j25-theory-methods-call` | U01 | — |
| `sec-t-methods-primitive-copy` (73) | `theory/chapters/methods.qmd#sec-j25-theory-methods-primitive-copy` | U01 | — |
| `sec-t-methods-reference-copy` (103) | `theory/chapters/methods.qmd#sec-j25-theory-methods-reference-copy` | U01 | — |
| `sec-t-methods-contract` (126) | `theory/chapters/methods.qmd#sec-j25-theory-methods-contract` | U01 | — |
| `sec-t-methods-overloading` (134) | `theory/chapters/methods.qmd#sec-j25-theory-methods-overloading` | U01 | — |
| `sec-t-methods-return-reference` (150) | `theory/chapters/methods.qmd#sec-j25-theory-methods-return-reference` | U01 | — |
| `sec-t-methods-varargs` (175) | `theory/chapters/methods.qmd#sec-j25-theory-methods-varargs` | U01 | — |
| `sec-t-methods-effects-design` (197) | `theory/chapters/methods.qmd#sec-j25-theory-methods-effects-design` | U01 | `handbook/entries/methods.qmd:38:60` |

### theory/chapters/objects.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-objects` (5) | `theory/chapters/objects.qmd#sec-j25-theory-objects` | U02 | `_includes/semester-map.qmd:8:38`; `lectures/01-platform.qmd:193:9`; `lectures/03-classes.qmd:38:70`; `lectures/03-classes.qmd:684:168`; `lectures/index.qmd:13:74`; `theory/chapters/arrays.qmd:186:280`; `theory/chapters/languages.qmd:75:468`; `theory/chapters/type-parameters.qmd:96:33`; `theory/chapters/types.qmd:65:47`; `theory/chapters/types.qmd:227:379` |
| `sec-t-objects-reference` (11) | `theory/chapters/objects.qmd#sec-j25-theory-objects-reference` | U02 | — |
| `sec-t-objects-aliases` (35) | `theory/chapters/objects.qmd#sec-j25-theory-objects-aliases` | U02 | — |
| `sec-t-objects-identity` (69) | `theory/chapters/objects.qmd#sec-j25-theory-objects-identity` | U02 | `lectures/03-classes.qmd:119:9` |
| `sec-t-objects-arguments` (93) | `theory/chapters/objects.qmd#sec-j25-theory-objects-arguments` | U02 | `lectures/03-classes.qmd:191:176`; `practice/objects.qmd:49:9` |
| `sec-t-objects-null` (132) | `theory/chapters/objects.qmd#sec-j25-theory-objects-null` | U02 | — |
| `sec-t-objects-null-contract` (146) | `theory/chapters/objects.qmd#sec-j25-theory-objects-null-contract` | U02 | — |
| `sec-t-objects-copy` (200) | `theory/chapters/objects.qmd#sec-j25-theory-objects-copy` | U02 | — |
| `sec-t-objects-copy-sharing` (217) | `theory/chapters/objects.qmd#sec-j25-theory-objects-copy-sharing` | U02 | `lectures/03-classes.qmd:267:9`; `theory/chapters/classes.qmd:261:21` |
| `sec-t-objects-lifetime` (255) | `theory/chapters/objects.qmd#sec-j25-theory-objects-lifetime` | U02 | — |

### theory/chapters/platform.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-platform` (1) | `theory/chapters/platform.qmd#sec-j25-theory-platform` | U01 | `_includes/semester-map.qmd:3:91`; `handbook/entries/java-tools.qmd:3:256`; `lectures/index.qmd:11:126`; `theory/chapters/languages.qmd:111:325` |
| `sec-t-platform-managed` (5) | `theory/chapters/platform.qmd#sec-j25-theory-platform-managed` | U01 | `lectures/01-platform.qmd:453:9` |
| `sec-t-platform-first-program` (44) | `theory/chapters/platform.qmd#sec-j25-theory-platform-first-program` | U01 | — |
| `sec-t-platform-components` (70) | `theory/chapters/platform.qmd#sec-j25-theory-platform-components` | U01 | `lectures/01-platform.qmd:279:9` |
| `sec-t-platform-bytecode` (80) | `theory/chapters/platform.qmd#sec-j25-theory-platform-bytecode` | U01 | — |
| `sec-t-platform-loading` (94) | `theory/chapters/platform.qmd#sec-j25-theory-platform-loading` | U01 | `lectures/01-platform.qmd:507:9` |
| `sec-t-platform-classpath` (104) | `theory/chapters/platform.qmd#sec-j25-theory-platform-classpath` | U01 | `lectures/01-platform.qmd:413:9` |
| `sec-t-platform-compatibility` (117) | `theory/chapters/platform.qmd#sec-j25-theory-platform-compatibility` | U01 | `handbook/entries/java-tools.qmd:71:59`; `lectures/01-platform.qmd:583:9`; `theory/chapters/languages.qmd:136:23` |
| `sec-t-platform-ide` (147) | `theory/chapters/platform.qmd#sec-j25-theory-platform-ide` | U01 | `lectures/01-platform.qmd:657:64` |
| `sec-t-platform-versions` (155) | `theory/chapters/platform.qmd#sec-j25-theory-platform-versions` | U01 | — |

### theory/chapters/resources.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-resources` (1) | `theory/chapters/resources.qmd#sec-j25-theory-resources` | U05 | `_includes/semester-map.qmd:13:96`; `handbook/entries/resources.qmd:7:39`; `handbook/entries/statements.qmd:60:573`; `lectures/01-platform.qmd:461:64`; `lectures/08-exceptions.qmd:388:61`; `lectures/index.qmd:18:129`; `tasks/labs/files.qmd:11:17`; `theory/chapters/application.qmd:357:29`; `theory/chapters/exceptions.qmd:384:57`; `theory/chapters/exceptions.qmd:506:9`; `theory/chapters/io.qmd:9:9`; `theory/chapters/streams.qmd:310:9` |
| `sec-t-resources-memory` (12) | `theory/chapters/resources.qmd#sec-j25-theory-resources-memory` | U05 | — |
| `sec-t-resources-manual` (63) | `theory/chapters/resources.qmd#sec-j25-theory-resources-manual` | U05 | — |
| `sec-t-resources-manual-two` (97) | `theory/chapters/resources.qmd#sec-j25-theory-resources-manual-two` | U05 | `practice/files.qmd:126:9` |
| `sec-t-resources-autocloseable` (168) | `theory/chapters/resources.qmd#sec-j25-theory-resources-autocloseable` | U05 | — |
| `sec-t-resources-order` (198) | `theory/chapters/resources.qmd#sec-j25-theory-resources-order` | U05 | `practice/reserve-data.qmd:116:44` |
| `sec-t-resources-suppressed` (244) | `theory/chapters/resources.qmd#sec-j25-theory-resources-suppressed` | U05 | `practice/files.qmd:127:9` |
| `sec-t-resources-ownership` (279) | `theory/chapters/resources.qmd#sec-j25-theory-resources-ownership` | U05 | — |
| `sec-t-resources-existing` (306) | `theory/chapters/resources.qmd#sec-j25-theory-resources-existing` | U05 | — |
| `sec-t-resources-wrappers` (340) | `theory/chapters/resources.qmd#sec-j25-theory-resources-wrappers` | U05 | — |
| `sec-t-resources-results` (384) | `theory/chapters/resources.qmd#sec-j25-theory-resources-results` | U05 | `handbook/entries/resources.qmd:80:9`; `lectures/08-exceptions.qmd:627:25` |

### theory/chapters/streams.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-streams` (1) | `theory/chapters/streams.qmd#sec-j25-theory-streams` | U04 | `_includes/semester-map.qmd:12:114`; `_includes/semester-map.qmd:17:101`; `handbook/entries/optional.qmd:8:21`; `handbook/entries/streams.qmd:6:32`; `lectures/07-functions.qmd:340:50`; `lectures/index.qmd:17:127`; `practice/pipelines.qmd:15:17`; `tasks/bank/pipelines.qmd:9:17`; `tasks/essays/collections.qmd:27:225`; `tasks/essays/pipelines.qmd:12:131`; `tasks/essays/pipelines.qmd:27:154`; `tasks/essays/pipelines.qmd:42:109`; `tasks/essays/pipelines.qmd:57:131`; `tasks/labs/pipelines.qmd:11:17`; `theory/chapters/functions.qmd:333:9` |
| `sec-t-streams-pipeline` (16) | `theory/chapters/streams.qmd#sec-j25-theory-streams-pipeline` | U04 | — |
| `sec-t-streams-report` (53) | `theory/chapters/streams.qmd#sec-j25-theory-streams-report` | U04 | `lectures/07-functions.qmd:586:9` |
| `sec-t-streams-outcomes` (93) | `theory/chapters/streams.qmd#sec-j25-theory-streams-outcomes` | U04 | `handbook/entries/optional.qmd:65:9`; `lectures/07-functions.qmd:543:27` |
| `sec-t-streams-lazy` (115) | `theory/chapters/streams.qmd#sec-j25-theory-streams-lazy` | U04 | — |
| `sec-t-streams-once` (143) | `theory/chapters/streams.qmd#sec-j25-theory-streams-once` | U04 | — |
| `sec-t-streams-short-circuit` (168) | `theory/chapters/streams.qmd#sec-j25-theory-streams-short-circuit` | U04 | — |
| `sec-t-streams-flatmap` (197) | `theory/chapters/streams.qmd#sec-j25-theory-streams-flatmap` | U04 | — |
| `sec-t-streams-numbers` (226) | `theory/chapters/streams.qmd#sec-j25-theory-streams-numbers` | U04 | — |
| `sec-t-streams-collect` (251) | `theory/chapters/streams.qmd#sec-j25-theory-streams-collect` | U04 | `practice/pipelines.qmd:140:41` |
| `sec-t-streams-optional` (278) | `theory/chapters/streams.qmd#sec-j25-theory-streams-optional` | U04 | — |
| `sec-t-streams-operation-order` (314) | `theory/chapters/streams.qmd#sec-j25-theory-streams-operation-order` | U04 | — |

### theory/chapters/text.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-text` (1) | `theory/chapters/text.qmd#sec-j25-theory-text` | U01 | `_includes/semester-map.qmd:7:27`; `handbook/entries/strings.qmd:3:140`; `lectures/09-application.qmd:71:45`; `lectures/index.qmd:12:319`; `lectures/index.qmd:19:78`; `practice/text.qmd:15:17`; `tasks/bank/text.qmd:9:17`; `tasks/essays/collections.qmd:12:53`; `tasks/essays/collections.qmd:27:30`; `tasks/essays/files.qmd:27:33`; `tasks/essays/objects.qmd:12:47`; `tasks/essays/pipelines.qmd:12:30`; `tasks/essays/pipelines.qmd:27:75`; `tasks/essays/pipelines.qmd:42:30`; `tasks/essays/pipelines.qmd:57:30`; `tasks/essays/text.qmd:12:33`; `tasks/essays/text.qmd:27:55`; `tasks/essays/text.qmd:42:57`; `tasks/labs/text.qmd:11:17`; `theory/chapters/application.qmd:11:60`; `theory/chapters/io.qmd:9:52`; `theory/chapters/types.qmd:120:460` |
| `sec-t-text-model` (5) | `theory/chapters/text.qmd#sec-j25-theory-text-model` | U01 | — |
| `sec-t-text-immutability` (35) | `theory/chapters/text.qmd#sec-j25-theory-text-immutability` | U01 | `lectures/02-values.qmd:886:9` |
| `sec-t-text-equality` (63) | `theory/chapters/text.qmd#sec-j25-theory-text-equality` | U01 | — |
| `sec-t-text-unicode` (80) | `theory/chapters/text.qmd#sec-j25-theory-text-unicode` | U01 | — |
| `sec-t-text-normalization` (107) | `theory/chapters/text.qmd#sec-j25-theory-text-normalization` | U01 | — |
| `sec-t-text-operations` (129) | `theory/chapters/text.qmd#sec-j25-theory-text-operations` | U01 | — |
| `sec-t-text-parsing` (147) | `theory/chapters/text.qmd#sec-j25-theory-text-parsing` | U01 | `handbook/entries/strings.qmd:59:187` |
| `sec-t-text-builder` (164) | `theory/chapters/text.qmd#sec-j25-theory-text-builder` | U01 | — |
| `sec-t-text-encoding` (181) | `theory/chapters/text.qmd#sec-j25-theory-text-encoding` | U01 | `lectures/02-values.qmd:933:86` |
| `sec-t-text-processing-contract` (197) | `theory/chapters/text.qmd#sec-j25-theory-text-processing-contract` | U01 | — |

### theory/chapters/type-parameters.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-type-parameters` (5) | `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters` | U03 | `_includes/semester-map.qmd:10:37`; `handbook/entries/generic-classes.qmd:7:167`; `lectures/04-object-design.qmd:729:142`; `lectures/05-generics.qmd:23:23`; `lectures/05-generics.qmd:1179:17`; `lectures/index.qmd:15:75`; `tasks/bank/generics.qmd:9:17`; `tasks/essays/collections.qmd:12:96`; `tasks/essays/collections.qmd:27:76`; `tasks/essays/collections.qmd:42:75`; `tasks/essays/generics.qmd:12:79`; `tasks/essays/generics.qmd:27:101`; `tasks/essays/objects.qmd:57:74`; `tasks/essays/pipelines.qmd:12:99`; `tasks/essays/pipelines.qmd:27:95`; `tasks/essays/pipelines.qmd:42:50`; `tasks/essays/pipelines.qmd:57:73`; `tasks/labs/generics.qmd:11:17`; `theory/chapters/data-classes.qmd:278:329`; `theory/chapters/type-relations.qmd:7:404` |
| `sec-t-type-parameters-object` (11) | `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-object` | U03 | — |
| `sec-t-type-parameters-type-parameter` (54) | `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-type-parameter` | U03 | — |
| `sec-t-type-parameters-sharing` (94) | `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-sharing` | U03 | — |
| `sec-t-type-parameters-method` (138) | `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-method` | U03 | — |
| `sec-t-type-parameters-bounds` (166) | `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-bounds` | U03 | — |
| `sec-t-type-parameters-checking` (194) | `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-checking` | U03 | `handbook/entries/generic-methods.qmd:53:311` |
| `sec-t-type-parameters-operations` (214) | `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-operations` | U03 | `lectures/05-generics.qmd:883:38` |
| `sec-t-type-parameters-templates` (291) | `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-templates` | U03 | — |
| `sec-t-type-parameters-clr` (335) | `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-clr` | U03 | — |
| `sec-t-type-parameters-design` (361) | `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-design` | U03 | — |
| `sec-t-type-parameters-optional` (367) | `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-optional` | U03 | — |

### theory/chapters/type-relations.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-type-relations` (5) | `theory/chapters/type-relations.qmd#sec-j25-theory-type-relations` | U03 | `_includes/semester-map.qmd:10:96`; `handbook/entries/generic-methods.qmd:42:349`; `lectures/05-generics.qmd:1183:63`; `lectures/index.qmd:15:132`; `practice/generics.qmd:60:9`; `theory/chapters/arrays.qmd:198:368`; `theory/chapters/type-parameters.qmd:365:297`; `theory/chapters/type-relations.qmd:239:133` |
| `sec-t-type-relations-subtyping` (11) | `theory/chapters/type-relations.qmd#sec-j25-theory-type-relations-subtyping` | U03 | — |
| `sec-t-type-relations-wildcards` (39) | `theory/chapters/type-relations.qmd#sec-j25-theory-type-relations-wildcards` | U03 | — |
| `sec-t-type-relations-copy` (80) | `theory/chapters/type-relations.qmd#sec-j25-theory-type-relations-copy` | U03 | `handbook/entries/wildcards.qmd:48:258` |
| `sec-t-type-relations-capture` (106) | `theory/chapters/type-relations.qmd#sec-j25-theory-type-relations-capture` | U03 | `lectures/05-generics.qmd:507:9` |
| `sec-t-type-relations-erasure` (126) | `theory/chapters/type-relations.qmd#sec-j25-theory-type-relations-erasure` | U03 | — |
| `sec-t-type-relations-compatibility` (161) | `theory/chapters/type-relations.qmd#sec-j25-theory-type-relations-compatibility` | U03 | — |
| `sec-t-type-relations-bridge` (212) | `theory/chapters/type-relations.qmd#sec-j25-theory-type-relations-bridge` | U03 | `lectures/05-generics.qmd:689:9` |
| `sec-t-type-relations-arrays` (225) | `theory/chapters/type-relations.qmd#sec-j25-theory-type-relations-arrays` | U03 | — |
| `sec-t-type-relations-design` (231) | `theory/chapters/type-relations.qmd#sec-j25-theory-type-relations-design` | U03 | — |

### theory/chapters/types.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-types` (1) | `theory/chapters/types.qmd#sec-j25-theory-types` | U01 | `_includes/semester-map.qmd:4:45`; `handbook/entries/values.qmd:3:124`; `lectures/index.qmd:12:73`; `tasks/bank/numbers.qmd:9:17`; `tasks/essays/control.qmd:12:33`; `tasks/essays/numbers.qmd:12:33`; `tasks/essays/numbers.qmd:27:33`; `tasks/essays/numbers.qmd:42:32`; `tasks/essays/numbers.qmd:57:33`; `tasks/essays/objects.qmd:27:25`; `tasks/labs/numbers.qmd:14:17` |
| `sec-t-types-model` (5) | `theory/chapters/types.qmd#sec-j25-theory-types-model` | U01 | `lectures/02-values.qmd:32:9` |
| `sec-t-types-static-dynamic` (45) | `theory/chapters/types.qmd#sec-j25-theory-types-static-dynamic` | U01 | `theory/chapters/languages.qmd:129:9` |
| `sec-t-types-expression-type` (81) | `theory/chapters/types.qmd#sec-j25-theory-types-expression-type` | U01 | `lectures/02-values.qmd:99:9`; `practice/numbers.qmd:95:9` |
| `sec-t-types-primitives` (96) | `theory/chapters/types.qmd#sec-j25-theory-types-primitives` | U01 | — |
| `sec-t-types-representation` (122) | `theory/chapters/types.qmd#sec-j25-theory-types-representation` | U01 | `lectures/02-values.qmd:389:9` |
| `sec-t-types-floating-model` (163) | `theory/chapters/types.qmd#sec-j25-theory-types-floating-model` | U01 | — |
| `sec-t-types-literals` (194) | `theory/chapters/types.qmd#sec-j25-theory-types-literals` | U01 | — |
| `sec-t-types-initialization` (209) | `theory/chapters/types.qmd#sec-j25-theory-types-initialization` | U01 | `lectures/02-values.qmd:179:9` |
| `sec-t-types-conversions` (229) | `theory/chapters/types.qmd#sec-j25-theory-types-conversions` | U01 | `lectures/02-values.qmd:266:9` |
| `sec-t-types-copying` (245) | `theory/chapters/types.qmd#sec-j25-theory-types-copying` | U01 | `handbook/entries/values.qmd:84:50`; `lectures/02-values.qmd:635:34` |
| `sec-t-types-clr` (281) | `theory/chapters/types.qmd#sec-j25-theory-types-clr` | U01 | `lectures/01-platform.qmd:305:9`; `lectures/02-values.qmd:650:9`; `theory/chapters/platform.qmd:36:34` |
| `sec-t-types-python-copying` (300) | `theory/chapters/types.qmd#sec-j25-theory-types-python-copying` | U01 | — |
| `sec-t-types-wrappers` (309) | `theory/chapters/types.qmd#sec-j25-theory-types-wrappers` | U01 | `lectures/02-values.qmd:683:29` |
| `sec-t-types-references` (335) | `theory/chapters/types.qmd#sec-j25-theory-types-references` | U01 | `lectures/02-values.qmd:452:9`; `theory/chapters/types.qmd:79:38` |
| `sec-t-types-choice` (392) | `theory/chapters/types.qmd#sec-j25-theory-types-choice` | U01 | — |

### theory/index.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-program` (7) | `theory/index.qmd#sec-j25-theory` | U00 | `docs/authoring-methodology.md:436:28`; `handbook/index.qmd:3:250`; `tasks/index.qmd:11:43` |

### theory/system-practice.qmd

| Исходная цель (строка) | Новая цель | Автор | Текущие потребители (строка:столбец) |
|---|---|---|---|
| `sec-t-system-practice` (1) | `theory/system-practice.qmd#sec-j25-theory-system-practice` | U00 | `index.qmd:15:94`; `lectures/index.qmd:7:274`; `tasks/labs/numbers.qmd:16:51`; `theory/index.qmd:29:159` |
| `sec-t-system-practice-shell` (5) | `theory/system-practice.qmd#sec-j25-theory-system-practice-shell` | U00 | `practice/numbers.qmd:75:42` |
| `sec-t-system-practice-files` (9) | `theory/system-practice.qmd#sec-j25-theory-system-practice-files` | U00 | `practice/files.qmd:50:9`; `tasks/essays/numbers.qmd:12:217` |
| `sec-t-system-practice-git` (13) | `theory/system-practice.qmd#sec-j25-theory-system-practice-git` | U00 | — |

## Приложение C. Новые обязательные цели без прежнего определения

| Новая цель | Автор файла | Назначение |
|---|---|---|
| `theory/chapters/types.qmd#sec-j25-theory-types-array-minimum` | U01 | Ранний одномерный массив до initialization/copying/final/control/methods; точный маршрут R1 в разделе 5 |
| `lectures/values-absence.qmd#sec-j25-lecture-values-absence-array-minimum` | U07 | Ранний минимум до alias/final; внутри 45 минут значений, без расширения слота |
| `tasks/bank/collections.qmd#exr-j25-collections-object-hash` | U06 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `tasks/bank/collections.qmd#exr-j25-collections-views-snapshots` | U06 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `tasks/bank/files.qmd#exr-j25-files-gradeapp-report` | U06 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `tasks/bank/generics.qmd#exr-j25-generics-append-compatible-lists` | U06 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `handbook/entries/arrays.qmd#sec-j25-api-arrays-copy-of` | U01 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `handbook/entries/erasure.qmd#sec-j25-api-erasure-javap` | U03 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `handbook/entries/project.qmd#sec-j25-api-project-wrapper` | U05 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `handbook/entries/strings.qmd#sec-j25-api-strings-compare-to` | U01 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `lectures/control-methods-data.qmd#sec-j25-lecture-control-methods-data` | U07 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms` | U07 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `practice/generics.qmd#sec-j25-practice-generics-checked` | U03 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `theory/chapters/collections.qmd#sec-j25-theory-collections-snapshots` | U04 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `theory/chapters/data-classes.qmd#sec-j25-theory-data-classes-reflection` | U02 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `theory/chapters/exceptions.qmd#sec-j25-theory-exceptions-early-checked` | U05 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `theory/chapters/text.qmd#sec-j25-theory-text-early-lab` | U01 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-comparison-sign` | U03 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-list-basics` | U03 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `theory/chapters/type-parameters.qmd#sec-j25-theory-type-parameters-model-comparison` | U03 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `theory/chapters/type-relations.qmd#sec-j25-theory-type-relations-comparison-target` | U03 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `theory/chapters/types.qmd#sec-j25-theory-types-absence` | U01 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |
| `theory/chapters/types.qmd#sec-j25-theory-types-nullness-comparison` | U01 | Введение/контракт из разделов 5–9; содержательный раздел, не пустая метка |

| `handbook/entries/generic-methods.qmd#sec-j25-api-generic-methods-cpp-setup` | U03 | Полные headers и команды C++20 dot/vector/array, runnable positive/negative samples |
| `handbook/entries/generic-methods.qmd#sec-j25-api-generic-methods-python-checker` | U03 | typing/Protocol и отдельные checker/run команды; канонический доступный Pyright 1.1.412, Python 3.14.7 подтверждаются M02 |

## Приложение D. Потребители прежних путей лекций

Базовое правило: старые девять QMD удаляются U07 после создания одиннадцати новых; M01 заменяет весь `lectures/_quarto.yml:project.render` точным порядком из A с `index.qmd` первым. В config basenames рассматриваются относительно lectures. Не сохранять прежний basename как redirect. Для раздвоенных путей ссылка без fragment выбирает обзорный первый слот, а точный anchor — B.

| Исходный путь | Новые файлы | Вхождения baseline вне самих девяти deck |
|---|---|---|
| `lectures/01-platform.qmd` | `lectures/platform-runtime.qmd` | `lectures/_quarto.yml:5` |
| `lectures/02-values.qmd` | `lectures/values-absence.qmd`, `lectures/control-methods-data.qmd` | `lectures/_quarto.yml:6` |
| `lectures/03-classes.qmd` | `lectures/objects-invariants.qmd` | `lectures/_quarto.yml:7` |
| `lectures/04-object-design.qmd` | `lectures/interfaces-records.qmd` | `lectures/_quarto.yml:8` |
| `lectures/05-generics.qmd` | `lectures/generics-variance.qmd`, `lectures/erasure-algorithms.qmd` | `lectures/_quarto.yml:9` |
| `lectures/06-collections.qmd` | `lectures/equality-collections.qmd` | `lectures/_quarto.yml:10` |
| `lectures/07-functions.qmd` | `lectures/functions-streams.qmd` | `lectures/_quarto.yml:11` |
| `lectures/08-exceptions.qmd` | `lectures/exceptions-resources.qmd` | `lectures/_quarto.yml:12` |
| `lectures/09-application.qmd` | `lectures/io-application.qmd` | `lectures/_quarto.yml:13` |

## Приложение E. Измеренный source-volume baseline

Измерено U00 по `git show a0d1bdb9d8343809560e3ce7f9a2141d4473ee3f:<path>` и нативному `quarto pandoc --from markdown --to json`; без render hooks. Полный manifest с SHA-256 исходников: `/tmp/java-course-revision-checks/U00/source-volume.json`; воспроизводимый алгоритм: `/tmp/java-course-revision-checks/U00/measure_volume.py`.

| Корпус | Файлы | Reader units | UTF-8 bytes | Строки | Fenced blocks (все языки) |
|---|---:|---:|---:|---:|---:|
| theory | 23 | 43615 | 660449 | 7007 | 252 |
| lectures | 9 | 15835 | 363613 | 6734 | 346 |

Предел прозы 23 глав: floor(43615×1,30) = **56699 reader units**. Сопоставимый ориентир видимой прозы лекций: floor(15835×1,30) = **20585**, с отдельной проверкой времени и читаемости. В новые 11 файлов входят все core/optional читательские разделы. Число fenced blocks — диагностический показатель, не квота и не обещание исполнимости. Общие индексы, handbook и practices не маскируются под уменьшение измеряемого корпуса.

| Файл | Reader units | Bytes | Строки | Fences |
|---|---:|---:|---:|---:|
| `lectures/01-platform.qmd` | 2080 | 44758 | 697 | 19 |
| `lectures/02-values.qmd` | 1708 | 48623 | 975 | 49 |
| `lectures/03-classes.qmd` | 1609 | 41156 | 690 | 37 |
| `lectures/04-object-design.qmd` | 1662 | 45576 | 735 | 39 |
| `lectures/05-generics.qmd` | 2996 | 62436 | 1194 | 34 |
| `lectures/06-collections.qmd` | 1311 | 27419 | 568 | 41 |
| `lectures/07-functions.qmd` | 1357 | 28807 | 593 | 44 |
| `lectures/08-exceptions.qmd` | 1693 | 34217 | 674 | 40 |
| `lectures/09-application.qmd` | 1419 | 30621 | 608 | 43 |
| `theory/chapters/application.qmd` | 1865 | 31521 | 473 | 9 |
| `theory/chapters/arrays.qmd` | 1751 | 25931 | 242 | 12 |
| `theory/chapters/classes.qmd` | 1522 | 25441 | 287 | 12 |
| `theory/chapters/collections.qmd` | 2013 | 29697 | 358 | 8 |
| `theory/chapters/control.qmd` | 1809 | 25902 | 238 | 10 |
| `theory/chapters/data-classes.qmd` | 1657 | 26630 | 278 | 12 |
| `theory/chapters/equality.qmd` | 1648 | 24175 | 295 | 6 |
| `theory/chapters/exceptions.qmd` | 2539 | 38848 | 506 | 13 |
| `theory/chapters/expressions.qmd` | 1681 | 23676 | 191 | 9 |
| `theory/chapters/functions.qmd` | 1844 | 28487 | 376 | 14 |
| `theory/chapters/inheritance.qmd` | 1463 | 23876 | 237 | 8 |
| `theory/chapters/interfaces.qmd` | 1517 | 25149 | 274 | 12 |
| `theory/chapters/io.qmd` | 1895 | 27979 | 337 | 9 |
| `theory/chapters/languages.qmd` | 1763 | 25855 | 142 | 4 |
| `theory/chapters/methods.qmd` | 1715 | 25370 | 233 | 10 |
| `theory/chapters/objects.qmd` | 1805 | 27176 | 278 | 15 |
| `theory/chapters/platform.qmd` | 1850 | 26570 | 167 | 5 |
| `theory/chapters/resources.qmd` | 2256 | 34282 | 469 | 13 |
| `theory/chapters/streams.qmd` | 1825 | 27347 | 352 | 10 |
| `theory/chapters/text.qmd` | 1765 | 26297 | 231 | 11 |
| `theory/chapters/type-parameters.qmd` | 2333 | 37383 | 394 | 24 |
| `theory/chapters/type-relations.qmd` | 2054 | 30910 | 239 | 13 |
| `theory/chapters/types.qmd` | 3045 | 41947 | 410 | 13 |

M05 запускает тот же обход для всех 23 новых глав и 11 новых лекций, записывает оба знаменателя, абсолютную разницу и процент. U07 отдельно записывает rehearsal time и ограничения pilot. Ни bytes, ни reader units не заменяют содержательного U08 review.
