# Авторский вид открытых тестов Java

Исследование выполнено 27 октября 2026 года. **Уточнение от 28 октября: пользователь выбрал обычный Java/JUnit, короткое пояснение и подключаемый канонический исходник.**
Fixtures и параметризованные тесты используются по необходимости; методы и коллекции не преобразуются в обязательный stdin-протокол.
Новые карточки сценариев и сценарный DSL исключены из рассмотрения. Ранний YAML-гибрид ниже сохранён только как история снятого варианта.
Конкретный способ подключения исходника, зависимости и реализация ещё требуют проработки. Умеренный повтор пояснения и кода оценивается по простоте сопровождения.
Это не отменяет принятый канон общих библиотек или полное условие QMD→README без ручного дублирования.
Код, сборки, зависимости и сервер не изменены. Доступа к порталу сейчас нет: содержательность feedback изучается по исходникам, grader и PrairieLearn; фактический UI предстоит проверить в сессии рефакторинга.

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

**Descriptor не нужен для выбранного показа исходника с пояснением; включение исходника в наш exporter ещё нужно обеспечить.**
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
Это кандидат переиспользования после Java/phase-order/strict-missing проверки, не установленная готовая функция курса.
[Javadoc 25 @snippet](https://docs.oracle.com/en/java/javase/25/docs/specs/javadoc/doc-comment-spec.html#snippet) с внешними файлами/regions — полезный образец;
переносить javadoc pipeline не требуется. Показ целого файла или выбранного фрагмента остаётся предметом выбора.

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

JUnit-демо в самостоятельном комплекте требует закреплённого JUnit JAR и явного отбора зависимости;
установленный JDK и консоль не означают запрет JUnit или обязательный Gradle.
[Download DEFAULT_EXCLUDES](../../../tasks/_extensions/Afonenko-Course-Tools/project-download/domain/config.ts#L7) включает `**/*.jar`:
выдача библиотеки через существующий механизм ещё проверяется, второго архиватора или глобального разрешения JAR не предложено.
Общий Git, отбор demo/full и полный QMD→README сохраняются; реальные студенческие ответы здесь не изменяются.
Native context кодовой папки, origins/rebasing, конкретные DTO/поля проектных метаданных и общий выпуск остаются открытыми из [цельного примера](reverse-array-example.md).

Следующая проработка использует выбранный обычный JUnit с пояснением для метода/коллекции и CLI/файлового случая; fixtures добавляются по необходимости.
Ещё нужно согласовать источник/фрагмент, упаковку зависимостей и проверить содержательность существующего feedback по исходникам и затем UI.
Выбранная база не является разрешением реализации: точные поля проектной схемы, способы подключения и мосты ещё не согласованы. Новые карточки и сценарный DSL в дальнейшие варианты не входят.
