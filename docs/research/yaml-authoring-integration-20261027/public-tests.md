# Авторский вид открытых тестов Java

Исследование выполнено 27 октября 2026 года. **Уточнение от 28 октября: пользователь выбрал обычный Java/JUnit, короткое пояснение и подключаемый канонический исходник.**
Fixtures и параметризованные тесты используются по необходимости; методы и коллекции не преобразуются в обязательный stdin-протокол.
Новые карточки сценариев и сценарный DSL исключены из рассмотрения. Ранний YAML-гибрид ниже сохранён только как история снятого варианта.
Последнее уточнение от 28 октября: явно выбранные demo — необязательные подсказки для самопроверки, когда решение уже складывается; основная доставка — файлами. Полный JUnit-файл inline не является показом по умолчанию. Фрагмент со ссылкой допустим, но фрагмент или развёрнутое описание каждого теста не обязательны.
В условии достаточно короткого указателя; «возможно текстовое описание со ссылкой на JUnit» остаётся предпочтением к проработке, а не новым обязательным слоем прозы. Зависимости, точные составы и реализация ещё обсуждаются.
Это не отменяет принятый канон общих библиотек или полное условие QMD→README без ручного дублирования.
Код, сборки, зависимости и сервер не изменены. Доступа к порталу сейчас нет: содержательность feedback изучается по исходникам, grader и PrairieLearn; фактический UI предстоит проверить в сессии рефакторинга.

## Самопроверка и доставка файлов

Рекомендуемый путь: условие→собственная попытка/решение начинает складываться→необязательная самопроверка→сдача/feedback.
Студент сам выбирает момент обращения: не требуется изучать тесты в начале, но раннее чтение не запрещено и доступ не зависит от числа попыток.
Выбранные демонстрационные Java/JUnit-файлы входят в стартер/студенческий ZIP сайта; в PL они передаются отдельными файлами либо архивом по задаче.
Для дальнейшей проработки общий принцип — один канонический студенческий стартер конкретной задачи для сайта и PL. Это комплект помощи: можно работать по условию и базовому файлу, использовать IDE-проект или обратиться к самопроверке; глубину выбирает студент. Полный стартер может быть необязательным скачиванием, личное использование IDE и запуск demo не требуются для зачёта.
Скачиваемый комплект и сдача независимы: где возможно, сдаётся один файл с кодом; для сложных задач файлы/архив задаются отдельно. Большой ZIP не требует сдавать ZIP. Требования и критерии остаются одними; общий стартер одной задачи не означает один шаблон всего банка или включение преподавательского full с решениями. Самостоятельный выбор IDE/demo не разрешает пропустить обязательные ресурсы или части стартера из условия; проверка при оценке сохраняется. Один канон не требует побайтово одинаковой упаковки каналов.
Это принятое направление выдачи, а не проверка нынешнего ZIP/PL. Явный demo-отбор сохраняется: полный grader и авторские тесты не добавляются автоматически.
Источник включается файлом из канона без ручного копирования. Специальные markup-поля, новый сценарный DSL, карточки и обязательный source-extraction pipeline не нужны для этого решения.
Точные файлы, зависимости и обещанный самостоятельный запуск ещё требуют согласования и проверки; общий Git и все обязательные требования условия сохраняются. Отдельно предстоит доказать отображение путей проекта (например, `src`) на отправляемое имя и вход grader; готовая новая нормализация или состав grading files не заявлены.
Это упрощение показа/получения demo до отправки, а не отмена полезного feedback после проверки. Нынешние publicCases/observation/report adapter не объявляются лишними: их удаление или замена не выбраны.

## Целевой запуск самопроверки через JUnit и Gradle

Последнее прямое намерение пользователя — обобщённый комплект обычных JUnit-тестов и общих библиотек в готовом Gradle-проекте: после первоначальной настройки одна команда `gradle test` из README или запуск средствами IDE. Это уточнение пункта 32 плана, без выбора новых версий или реализации.
Нужны JDK и установленный Gradle без Wrapper; первый resolve требует сети/cache ([Gradle caching](https://docs.gradle.org/current/userguide/dependency_caching.html)). Полный offline на чистой машине не обещается. Основной маршрут написать/сдать один `Main.java` по-прежнему не обязывает использовать Gradle, IDE или самостоятельно запускать demo.
Внешние зависимости/версии задаются стандартными Gradle-механизмами; компоненты/файлы курса — другой уровень, не второй metadata-реестр версий. Shared helper/build logic должны реально попадать в самостоятельный комплект или быть доступной обоснованной зависимостью; соседний исходный checkout не может быть обязательной скрытой зависимостью. Один канон допускает производные копии выдачи, точная модульная организация открыта.

Локальная база уже есть: [GradeApp build.gradle](../../../tasks/projects/files/gradeapp-report/student/build.gradle#L5) подключает Jupiter и launcher, [тесты](../../../tasks/projects/files/gradeapp-report/student/src/test/java/grade/PublicExamplesTest.java#L25) — обычные методы/коллекции/файлы. Версии этого кейса и старого console runner не объявляются общим будущим pin. Напротив, [старый shared build.gradle](../../../tasks/test-support/shared/b42bf4ab370d/build.gradle#L4) запускает `PublicChecks.main` и отключает `test`: это конкретная инфраструктура для проработки рефакторинга, не готовый JUnit-шаблон.
[Gradle Java testing](https://docs.gradle.org/current/userguide/java_testing.html#sec:junit_jupiter), [JUnit build support](https://docs.junit.org/5.14.1/running-tests/build-support.html) и [IDE Gradle tests](https://www.jetbrains.com/help/idea/work-with-tests-in-gradle.html) описывают нужные штатные механизмы. Курс не создаёт новый dependency resolver; `java-test-fixtures` и варианты общих модулей пока кандидаты, не принятая схема.
PL должен использовать те же канонические тесты/helper; нынешний обычный JUnit-путь grader компилируется через javac/java отдельно от локального Gradle, а [GradeApp runtime-профиль](../../../tasks/_quarto.yml#L1594) не квалифицирует все задачи. Выбор адаптации исполнения, API-precheck, scoring и полезного feedback/observations ещё проверяется. JavaProcess/CLI-helper не признаются лишними вместе со снятием универсальной самописной системы проверок.
Стоимость нового пути не измерена; эта модель не требует запускать Gradle при рендере каждой страницы курса. Обязательные требования, независимый простой контракт сдачи и необязательность личной самопроверки сохраняются.

## Стартер и проекция ответа

Узкое чтение исходников от 28 октября, без исполнения, сборки архивов и живого сервера. Это уточняет технические основания пункта 31 плана, не добавляя решения или схемы.
Целевой договор: автор один раз определяет студенческий состав/API/ресурсы/явные demo и допустимый ответ. Сайт и PL получают один student-канон; ZIP помощи не становится форматом ответа, PL-адаптер и API-precheck остаются в проверяющей поставке.

- **Текущий reverse-array уже сопоставляет пути без нового basename-правила.** [java-main](../../../tasks/_quarto.yml#L427) задаёт root `student/src/main/java`; [Core collect](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/project-checks/collect.ts#L37) получает относительное имя, [PL selection](../../../tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/application/source-selection.ts#L87) проверяет точное соответствие. В скачиваемом дереве остаётся `src/main/java/Main.java`, в ответе — `Main.java`; [descriptor/binding](../../../tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/application/export-course.ts#L344) сохраняют выбранные имена.
- **Комплект и ответ идут разными путями.** [Core student producer](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/artifacts/materialize.ts#L246) сохраняет пути внутри student; [PL export](../../../tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/application/export.ts#L564) отдельно выдаёт дерево стартера/ZIP и файлы для начала ответа. Полный artifact зависит от staging receipt: равенство будущих site/PL inventories ещё надо проверить.
- **Скачанный starter ZIP не равен Java-архиву дополнительных исходников.** Закреплённый [grader:841–899](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/images/java25-grader/grade.py#L841) принимает в additional ZIP ограниченные Java-файлы/пути, а [923–942](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/images/java25-grader/grade.py#L923) использует declared primary и разрешённое дополнение. README/demo из стартера не становятся grading sources автоматически. Для [пакетного примера](../../../tasks/_quarto.yml#L4254) `qpack/ICharQ.java` сохраняет путь пакета; basename `ICharQ.java` его не заменяет. Обязательные non-Java ресурсы этим случаем не доказаны; это source pin, не версия наблюдённого сервера.
- **Нынешний малый selfcheck reverse-array — JDK-only.** [Объявленный состав](../../../tasks/_quarto.yml#L5029): README, Main, PublicChecks, JavaProcess, OutputNumbers и CHECKS с сохранением путей; [команды CHECKS](../../../tasks/test-support/CHECKS.md#L3) не требуют JUnit. Это не обязательная будущая форма всех demo и не свидетельство выполненного запуска/архива или полноты будущего README.
- **Обычные helper и адаптер различаются.** JavaProcess/OutputNumbers используют JDK, тесты библиотеки — обычный Jupiter; PublicScenarioTest/DirectPublicExamplesTest добавляют PL reporting, CliPrecheck — отдельную Platform API-стадию. [JavaProcess](../../../tasks/test-support/src/main/java/coursesupport/JavaProcess.java#L142) привязан к JDK 25 internal MethodFinder/add-exports и classpath entryPoint; переносимость на любую JDK и произвольные внешние runtime-зависимости не обещаны.
- **Именованные зависимости не означают автоматический анализ Java imports.** [Materialization](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/artifacts/materialize.ts#L132) переносит объявленные файлы; полнота helper/fixtures/runtime-зависимостей выбранного demo ещё требует проверки. Внутренние классы используемого launcher также должны сохраняться в собранном запуске.
- **Предсобранный JUnit-runtime в ZIP ограничен нынешним producer.** [Core publicExcluded/planner](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/artifacts/materialize.ts#L43) исключает JAR и из обычного student-состава; одного ResourceDownload include недостаточно. При этом готовые model artifact/receipt/ZIP проходят нижним файловым транспортом. Это факт нынешней предсобранной binary-выдачи, а не доказанная неспособность всего pipeline. При целевых Gradle-managed зависимостях он не является обязательным первым препятствием или выбором ZIP с test runner; новый архиватор, JAR-версия и обход правил не выбираются.

Следующие технические проверки: одинаковый содержательный student inventory сайта/PL; путь source→answer→staging для single-file и пакета; неверное имя/отсутствие файла/дополнительные helper/коллизии; ресурсы, demo и минимальный runnable-состав; ссылки, feedback и повторная сдача.
Полный IDE-комплект всех проверок и авторских решений пока не подтверждён. Эти проверки выполняются владельцами в будущей согласованной работе; пункт 31, учебные требования и оценивание сохраняются.

## Штатных Java и JUnit достаточно для разных предметов проверки

Прочитана версионированная документация Jupiter **5.14.1**, а не новый JUnit 6.
Общий ввод `stdin` не нужен для методов, аргументов, коллекций, файлов и состояния объектов.

| Что проверяется | Уже доступная основа | Граница |
| --- | --- | --- |
| Метод и результат | Прямой Java-вызов и [Assertions](https://docs.junit.org/5.14.1/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html) | Вызов и сравнение остаются кодом, без общего языка действий |
| Идентичность и исключение | `assertSame`, `assertThrows` | Не заменяются строковым выводом или сравнением `toString()` |
| Коллекции и массивы | `assertIterableEquals`, `assertArrayEquals`, обычные Java-значения | Выбор порядка, равенства и иных свойств определяется условием и тестом |
| Много однотипных случаев | `@ParameterizedTest` и [@MethodSource](https://docs.junit.org/5.14.1/api/org.junit.jupiter.params/org/junit/jupiter/params/provider/MethodSource.html) | Можно передавать объекты/коллекции Java, не сериализуя их в YAML или stdin |
| Файлы | [@TempDir](https://docs.junit.org/5.14.1/api/org.junit.jupiter.api/org/junit/jupiter/api/io/TempDir.html), обычный `java.nio.file` | Входные файлы, ресурсы и состояние после операции выражаются предметным тестом |
| CLI, stdin и аргументы | Существующий [JavaProcess.run](../../../tasks/test-support/src/main/java/coursesupport/JavaProcess.java#L41) с `String... arguments` | CLI остаётся отдельным видом программы, а не обязательной формой всех API |

Иллюстрация обычного JUnit по [нынешнему API стека](../../../tasks/projects/generics/generic-stack/student/src/test/java/PublicChecks.java#L14), без нового формата сценариев; импорты опущены:

```java
@Test void overflowPreservesStoredObject() throws Exception {
    IGenStack<String> stack = new ArrayStack<>(new String[1]);
    assertThrows(StackEmptyException.class, () -> stack.pop());
    String value = new String("Лист");
    stack.push(value);
    assertThrows(StackFullException.class, () -> stack.push("Ветка"));
    assertSame(value, stack.pop());
    assertThrows(StackEmptyException.class, () -> stack.pop());
}
```

Это форма записи существующих свойств, не замена нынешних проверок и не подтверждение их запуска.
В курсе уже есть [обычные JUnit-тесты библиотеки](../../../tasks/test-support/src/test/java/coursesupport/OutputNumbersTest.java#L7):
прямые вызовы, массивы и `assertThrows` не требуют descriptor открытого сценария.
Java provider часто проще отдельного формата для объектов/коллекций; большой файл может быть удобнее Java literal.
Данные и стиль можно выбирать локально, не унифицируя весь банк.

[AssertJ](https://assertj.github.io/doc/) — возможная дополнительная библиотека выразительных assertions, без обязательной установки всем.
[JUnit Pioneer](https://junit-pioneer.org/docs/standard-input-output/) предлагает подмену глобальных потоков;
для отдельной JVM это не автоматическая замена существующего JavaProcess.
У [System Stubs](https://github.com/webcompere/system-stubs) путь перехвата `System.exit` через SecurityManager
не подходит JDK 25, где [setSecurityManager](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/System.html#setSecurityManager(java.lang.SecurityManager)) не поддерживается.
Это ограничение конкретного пути, а не утверждение несовместимости всех средств библиотеки; новые зависимости не выбраны.

## Что действительно умеет наш путь PL

База Java — PR27 `273ce2d63cc245d8c70e00167acb15e4bc63ef64`, установленные Core/PL 8.3.0.
Pin Platform из [toolchain](../../../CI/toolchain.json#L6) — `1cab18c5e693210b2b52930cacb00ceb5acd9797`.
Это прочитанные исходники, не подтверждение фактической выдачи живого сервера.
Файловый транспорт уже существует: [export.ts:564–577](../../../tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/application/export.ts#L564) пишет готовый student artifact/ZIP и отдельные student-файлы; [628–640](../../../tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/application/export.ts#L628) создаёт native file-download и ссылки starter ZIP/README. Grading tests идут отдельным путём в `tests`, а не автоматически в demo-выдачу.
[readMaterializedStudent](../../../tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/application/materialized-student.ts#L4) читает явный Core student inventory и public-common origins; он не строит отбор demo или замыкание зависимостей сам. Поэтому транспорт не доказывает готовность нового состава.

- [PublicChecks:5–22](../../../tasks/projects/arrays/reverse-array/student/src/test/java/PublicChecks.java#L5) и
  [public-cases:659–669](../../../tasks/_quarto.yml#L659) сейчас повторяют названия, пояснения входа/expected и проверяющие значения.
  Такой повтор не обязан исчезнуть любой ценой; следует сравнить его сопровождение с ценой генерации.
- [Exporter:640–648](../../../tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/application/export.ts#L640)
  создаёт условие/файлы/средства сдачи и панель `pl-external-grader-results`.
  Автоматического показа public-cases **до сдачи** в этом пути нет.
- [Core PublicCase](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/domain/model.ts#L40) и
  [PL descriptor](../../../tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/application/runtime-descriptor.ts#L13)
  требуют `id/name/test/expected` и хотя бы одно из `input/state/action`; descriptor передаётся runtime как `publicCases`.
  Нынешний [предел 32](../../../tasks/_extensions/Afonenko-Course-Tools/course-core/projects.lua#L109) относится **только к publicCases**,
  а не к обычным JUnit-тестам или всем параметризованным вызовам.
- Два случая в [PublicChecks:25](../../../tasks/projects/arrays/reverse-array/student/src/test/java/PublicChecks.java#L25)
  и два метода [PublicScenarioTest:7–15](../../../tasks/test-support/junit/PublicScenarioTest.java#L7) — свойство нашей CLI-обёртки, не ограничение JUnit.
- В pin Platform [JUnit adapter:84–115](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/runtime/java25-junit-v1/PlatformJUnitAdapter.java#L84)
  связывает `public-case`/`actual` с физическим методом;
  [student_feedback:43–56](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/runtime/student_feedback.py#L43)
  выбирает ровно одно совпадение id и test. При отсутствии/неоднозначности остаются `actual=null`, `not-run`.

**Descriptor не нужен для доставки канонического demo-файла или необязательного фрагмента со ссылкой; конкретную подготовку файлов и ссылки в нашем exporter ещё нужно проверить.**
Существующий формат результатов Platform 2.4 описан здесь как предмет аудита, а не как рекомендация новой модели карточек.
Он зависит от `publicCases`, `id/test` binding и TestReporter/обёртки с `public-case` и `actual`: при обычном успехе/assertion failure metadata без наблюдения недостаточно, bridge оставит `not-run`.
В этом протоколе смысловой case ID и физический `Class#method` различаются. Это факт старого пути, не требование новой декларации сценариев.
[Аудит feedback от 28 октября](feedback-audit-20261028.md) проследил потерю javac/assertion/API-диагностики в student-проекции и неоднозначность `not-run`; текущие названия поступают из русскоязычных authored names. Живой UI не проверен; новый формат результатов или замена grader этим документом не утверждены.
Успех demo не означает полный score: [student_feedback:26–42](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/runtime/student_feedback.py#L26)
сохраняет общий результат полного запуска. Сохраняется оценивание 70/30; ручная часть оценивается отдельно.

## Две внешние учебные практики

### 1 PrairieLearn upstream

Срез исходников — `6053e6871f343dc24867bd05522782c2425deb3b`, 27.10.2026 18:55:05Z.
[Java grader](https://docs.prairielearn.com/java-grader/) принимает обычные `tests/junit/*.java`;
названия и сообщения передаются средствами JUnit, TestReporter и адаптера.
[JUnitAutograder:167–188](https://github.com/PrairieLearn/PrairieLearn/blob/6053e6871f343dc24867bd05522782c2425deb3b/graders/java/JUnitAutograder.java#L167)
принимает TestReporter output, а [246–267](https://github.com/PrairieLearn/PrairieLearn/blob/6053e6871f343dc24867bd05522782c2425deb3b/graders/java/JUnitAutograder.java#L246) собирает results JSON.
Штатный [pl-code](https://docs.prairielearn.com/elements/pl-code/) показывает выбранный файл через `source-file-name`/`directory` без Java descriptor.
[Results element](https://github.com/PrairieLearn/PrairieLearn/blob/6053e6871f343dc24867bd05522782c2425deb3b/apps/prairielearn/elements/pl-external-grader-results/pl-external-grader-results.py#L119) отображает результаты проверки.
Вывод: код, короткое пояснение и ограниченный адаптер жизнеспособны. Наш exporter должен отдельно выбрать/подготовить исходник;
upstream не является готовой заменой курсовой Platform 2.4 или доказательством её выдачи.

### 2 Artemis и Ares

Официальные [authoring docs](https://docs.artemis.tum.de/instructor/exercises/programming-exercise/write-code-and-tests/)
прочитаны 27.10.2026, без номера версии установленного продукта: Java/JUnit и Markdown задания поддерживаются отдельно,
а учебные шаги связываются с именами тестов. Это проверяемая связь текста и кода, а не обязательный единый сценарный язык.
[Оценивание/видимость](https://docs.artemis.tum.de/instructor/exercises/programming-exercise/grade-and-verify/) различают исполнение и показ feedback.
[Ares 1.15.0](https://github.com/ls1intum/Ares/blob/86356b21793aea9355fd8efaa288e378e98793ea/README.adoc#L186),
commit `86356b21793aea9355fd8efaa288e378e98793ea` от 11.09.2025, показывает специализированные аннотации поверх обычных JUnit-тел.
Вывод: небольшой повтор пояснения и теста — реальный способ авторства. Его полезность для нас нужно сравнить;
закрытые репозитории, сроки и runtime-политику другой системы не переносим в выбранный общий Git. Совместимость Ares с JDK 25 не проверялась.

## Подключение исходника и необязательная отчётность

[include-code-files README](https://github.com/quarto-ext/include-code-files/blob/01b35ae5b62dc71c7d472d141341a8ae3f3df928/README.md)
и [Lua-фильтр](https://github.com/quarto-ext/include-code-files/blob/01b35ae5b62dc71c7d472d141341a8ae3f3df928/_extensions/include-code-files/include-code-files.lua)
прочитаны на commit `01b35ae5b62dc71c7d472d141341a8ae3f3df928` (04.09.2024), source blob `c74aa536a36a8f49482a7226b43ef6430cc02bc9`.
Есть whole-file, `start-line`/`end-line`, `dedent` и `snippet`; fallback-шаблон комментария допускает `//`, но README не гарантирует Java.
Missing file пишет в stderr и оставляет пустой CodeBlock; отсутствующие snippet markers могут молча показать весь файл.
Это кандидат только для необязательного показа фрагмента после Java/phase-order/strict-missing проверки, не готовая функция курса и не обязательное условие файловой доставки.
[Javadoc 25 @snippet](https://docs.oracle.com/en/java/javase/25/docs/specs/javadoc/doc-comment-spec.html#snippet) с внешними файлами/regions — полезный образец;
переносить javadoc pipeline не требуется. Последнее решение исключает целый inline-файл по умолчанию; нужен ли полезный фрагмент сверх краткого указателя и файлов, ещё предстоит проработать.

[JUnit 5.14.1 reporting](https://docs.junit.org/5.14.1/advanced-topics/junit-platform-reporting.html) уже предлагает Legacy XML и Open Test Reporting;
OTR по умолчанию выключен. Это результаты исполнения, не автоматически полученное expected до запуска.
[Allure](https://allurereport.org/docs/junit-jupiter/) полезен при отдельной потребности преподавательского отчёта, но добавляет adapter/artifacts/HTML generation.
PL уже принимает results JSON; обязательная промежуточная XML↔JSON или Allure-ступень не обоснована.
[JGiven](https://github.com/TNG/JGiven/blob/7c90b8f6b85469e46e60e21b196a0c52b916de1f/README.md) предлагает Java stages и отчёты после исполнения,
а [Cucumber](https://cucumber.io/docs/cucumber/step-definitions/) — feature text и step bindings:
готовые средства документации существуют, но добавляют свои договоры. Для малых задач обязательный слой не рекомендуется;
это оценка сложности, не benchmark или утверждение несовместимости с JDK 25.

## Историческое сравнение стоимости

Сравнение ниже обосновывало выбор: 28 октября принят A, B остаётся локальной техникой, C исключён из рассмотрения.
Оценки качественные: «место» — авторская обязанность, не измеренное число файлов на всём банке.
Неизбежные изменения самого условия/API/стартера/эталонов одинаковы для всех вариантов и не считаются лишним связыванием.

| Изменение | A Принятый Java/JUnit + пояснение + исходник | B Fixtures/MethodSource по необходимости | C Исключённый YAML-гибрид |
| --- | --- | --- | --- |
| Rename API, данные прежние | Один вызывающий тест; пояснение, если называет API; binding существующего publicCases — при изменении физического теста | Один общий тест; provider меняется при изменении типов, не от каждого rename | Один evaluator при прежнем payload; schema/codec/cards меняются лишь при изменении договора данных |
| Новый expected | Одно assertion/Java value, возможно второе место в короткой прозе | Одна строка provider/fixture, возможно prose | Один YAML literal/fixture, без ручной правки generated outputs |
| Новый случай того же поведения | Один @Test/case, при необходимости prose и запись native publicCases | Одна строка/набор fixtures; общее действие остаётся прежним | Одна YAML-запись и optional fixtures; новое действие всё равно требует Java/адаптации |

A часто требует 1–2 авторских мест, при отдельных publicCases — 2–3. B — одно data/provider и возможное пояснение/binding.
C может требовать всего одну каноническую правку данных: неверно приписывать ему обязательные 3–5 ручных изменений.
Его дополнительная цена — обслуживаемые границы native merge/provenance → schema → resources → reader → id/test → cards/results.
Компилятор находит многие ошибки вызова/типа A/B; корректность прозы проверяет чтение.
Согласованность параметров и discovery проверяет запуск JUnit; fixtures требуют проверки формата/путей.
C добавляет раннюю schema-валидацию, но требует исполнения всей цепочки для проверки показанных и реально использованных данных.
Ни один вариант не доказывает правильность comparator только структурной схемой.

## История исключённой YAML-гипотезы

27 октября рассматривался общий канон title/input/expected в `_metadata.yml`, материализация JDK-ресурсов и PL-карточек.
28 октября пользователь исключил новые карточки и сценарный DSL; этот маршрут снят с рассмотрения, его пример не является действующим шаблоном.
История объясняет прежнюю оценку стоимости, а не оставляет скрытый выбор карточек или требование descriptor для обычного Java/JUnit.

## Границы дальнейшей проработки

Целевой JUnit/Gradle-комплект содержит код курса и настройку сборки, а стандартные внешние зависимости разрешает Gradle. Выбор «везти JUnit JAR в ZIP или вручную устанавливать JUnit» больше не остаётся открытым маршрутом; однофайловое решение и сдача сохраняют простой JDK-путь.
Если понадобится предсобранная бинарная выдача, [Download DEFAULT_EXCLUDES](../../../tasks/_extensions/Afonenko-Course-Tools/project-download/domain/config.ts#L7) включает `**/*.jar`:
выдача библиотеки через выбранный существующий маршрут ещё проверяется, второго архиватора или глобального разрешения JAR не предложено. Обычный `include` не перебивает [exclude](../../../tasks/_extensions/Afonenko-Course-Tools/project-download/infrastructure/files.ts#L59); [fallback PL](../../../tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/application/export.ts#L30) тоже исключает JAR, но это не доказательство невозможности JAR в любом receipt/model-artifact маршруте.
Общий Git, отбор demo/full и полный QMD→README сохраняются; реальные студенческие ответы здесь не изменяются.
Native context кодовой папки, origins/rebasing, конкретные DTO/поля проектных метаданных и общий выпуск остаются открытыми из [цельного примера](reverse-array-example.md).

Следующая проработка использует обычный JUnit для метода/коллекции и CLI/файлового случая; fixtures добавляются по необходимости.
Ещё нужно согласовать состав файловой выдачи и общие библиотеки/build logic для Gradle-пути, краткий указатель к самопроверке и при необходимости полезный фрагмент, затем проверить существующий feedback и живой UI. Развёрнутая проза по каждому тесту не является обязательным результатом. Проверить выбранные demo/данные/helpers, минимальные зависимости и обещанный запуск, правильные file/ZIP/README-ссылки и отсутствие лишних обязательных файлов сдачи.
Выбранная база не является разрешением реализации: точные поля проектной схемы, способы подключения и мосты ещё не согласованы. Новые карточки и сценарный DSL в дальнейшие варианты не входят.
