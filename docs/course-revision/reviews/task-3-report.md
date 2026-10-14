# Task 3 report — U01

Статус: **DONE_WITH_CONCERNS**. Авторская работа завершена и заморожена для независимого review. Dispatch `U01-01`, автор `/root/u01`; назначенный профиль `gpt-6.1-sol / ultra`, effective model/effort `not_reported`. Вход: принятый U00 commit `2365532bca35ad12035c79c681748834f0719954`; предметный baseline: remote PR13 `a0d1bdb9d8343809560e3ce7f9a2141d4473ee3f`. Checkout `/tmp/java-course-pr13-20261013`.

## Результат и граница записи

Закончены 21 owned QMD: 8 theory, 8 handbook, 5 practice; пакет `docs/course-revision/packets/U01.md`, этот report и временное evidence `/tmp/java-course-revision-checks/U01`. Пакет содержит решение revise/preserve для каждого файла и полный executable context каждого Java fence. Новые ID и categories применены в каждом owned файле; все текущие потребители внутри них обновлены, включая shortcode tokens. Runtime aliases, redirects и migrations не создавались.

Общие индексы, конфигурации и bibliography; U02 files; tasks/bank/lab/assets/PL projects; structure-contract и model-routing не редактировались. Subagents, новые чаты, stage, commit, push, merge, публикация, глобальные установки и настройки отсутствуют. CF JAR получены только в task-scoped `/tmp` для требуемого анализа. C# compiler использовал установленные reference assemblies без project restore. PL17, 14 позиций 6/4/4, случайное назначение и public/private boundary не менялись. Единый журнал Reading использует `course.reading.Reading` и согласованный `src/course/reading`.

## Изменённые пути

- `theory/chapters/languages.qmd`
- `theory/chapters/platform.qmd`
- `theory/chapters/types.qmd`
- `theory/chapters/expressions.qmd`
- `theory/chapters/control.qmd`
- `theory/chapters/methods.qmd`
- `theory/chapters/arrays.qmd`
- `theory/chapters/text.qmd`
- `handbook/entries/java-tools.qmd`
- `handbook/entries/console.qmd`
- `handbook/entries/values.qmd`
- `handbook/entries/operators.qmd`
- `handbook/entries/statements.qmd`
- `handbook/entries/methods.qmd`
- `handbook/entries/arrays.qmd`
- `handbook/entries/strings.qmd`
- `practice/numbers.qmd`
- `practice/control.qmd`
- `practice/arrays.qmd`
- `practice/text.qmd`
- `practice/reserve-algorithms.qmd`
- `docs/course-revision/packets/U01.md`
- `.superpowers/sdd/execution-plan/task-3-report.md`

## Свежие проверки

| Проверка | Фактический результат / evidence |
|---|---|
| Approved Java executable fences | **115/115 PASS** на actual OpenJDK 25.0.4.1. Обычная компиляция 115; нормальный запуск 114 и один намеренный NPE. Три синтаксические схемы не объявлены executable. `snippets.json`: полный source, argv, stdout/stderr, expected, anchor, fence ordinal |
| Focused semantic matrix | **43/43 PASS**: compile/runtime negative cases, input boundaries, Python/Pyright, direct CF processor, stale/source/class chain, JS. `semantics.json` |
| C# comparison | **2/2 PASS**: Roslyn 5.11.0-1.26425.128, C#13, SDK11 RC; nullable enabled, warnings allowed. Bad: CS8602, затем runtime NullReferenceException. Guarded: без CS8602, stdout `0`. `csharp.json` |
| Native structure, ID, categories, prerequisite order, volume | **141/141 PASS**, `verification.json`, `definitions.json`, `source-volume.json` |
| Frozen source fingerprints | `frozen-source-sha256.json`; все 23 deliverables включены |

Команды: `python /tmp/java-course-revision-checks/U01/verify_samples.py`, `verify_semantics.py`, `verify_csharp.py`, `verify_structure.py`. Это временные авторские проверки, не новые grading tests или runtime dependencies курса. Default javac27 не использовался для acceptance.

После основного запуска 115 snippets была уточнена только закомментированная отрицательная строка в languages: вместо повторного объявления `int total = "five"` теперь `total = "five"`, чтобы наблюдение относилось к несовместимому присваиванию. Заново проверен только этот изменённый sample — PASS; широкого повторного запуска 115 не было.

Два первоначальных сбоя собственной verification infrastructure исправлены по установленной причине. Source-launch fixture сначала лежал как `temp/Reading.java` при package `course.reading`, и JDK25 отверг несогласованный путь. Курс уже указывал `src/course/reading/Reading.java`; исправленный fixture воспроизвёл stale `2.5`, source/fresh `3.5`. Categories decoder сначала сравнивал Pandoc MetaInlines arrays со строками и дал 21 ложное замечание; исправлен decoder, исходные categories не менялись. Эти сбои не включены в результаты PASS. Намеренный compile-fail считается PASS только при ожидаемой категории диагностики.

## Содержательные результаты

- Early array minimum действительно предшествует initialization/copying/absence/final. До него нет mutation контейнера. Первый Reading использует скаляры; practice-numbers локально повторяет String[]/length/index и вводит if/return до guarded indexing.
- null, empty и unassigned local различаются. Nullable wrapper даёт NPE при unboxing; final фиксирует переменную, оставляя компонент изменяемым. Annotation contract, analyzer и runtime разделены. Обычный javac с `-proc:none` принимает пример; CF4.3.1 NullnessChecker отвергает bad и принимает guard. C#/JS выполнены, Zig явно документирован.
- Methods начинается со scalar argument copying; далее component mutation и parameter rebind — отдельные действия. Первый независимый возвращаемый массив создаётся через new; clone и nested rows определены позже. Рекурсия прослежена 3→2→1→0 и обратными возвратами.
- String minimum доступен до L4: immutable, equals, UTF-16/bytes, insertAt и границы, точный word alphabet и сохранение разделителей. Unicode normalization сохранена как расширение, regex catalogue находится в handbook.
- G04/G05/G06/G08/G09/G12 авторски закрыты. В пакет переданы точные запросы U06/U02/U03/U04/U07/M01/M03. Generic Arrays.copyOf и String.compareTo выполнены U01 сразу, без ожидания другого автора.
- Все 21 файла имеют explicit decision, замороженные новые цели/categories и обновлённые own current consumers. Чтение книг и источников описано с фактической глубиной.

Восемь theory chapters U01: **15 379→16 538 reader units, +7,536%**; добавлено 1 159, то есть 2,66% полного неизменного baseline 43 615. Bytes: 221 548→239 444; lines: 1 854→1 987; fenced blocks: 74→80. Итог для всех 23 глав считает M05. Четыре core practice сохраняют 4×30=120 минут из общих 300; резерв добровольный. Лекции 880 минут и основные лабораторные 1 935 минут не менялись. Pilot не заявлен выполненным.

## Чтение и оставшиеся ограничения

Подробный read log и primary-source locators находятся в пакете. Книжное чтение: Шилдт, руководство для начинающих, JDK17; PDF166–171 — титул главы и печатные166–170, §5 arrays/one-dimensional/index/initializer/bounds. Также прочитаны первые65 строк извлечения PDF185–197: печатная184 и начало185 о foreach; остальные извлечённые страницы не объявляются прочитанными. Оглавление PDF1–16 использовано для навигации. Остальные12 книг не объявляются прочитанными. JLS/API25, Python/Pyright, CF, C#, ECMAScript и Zig читались адресно, не целиком. Новых bibliography records пакет не требует.

Full Quarto/reveal build, final cross-owner link acceptance, PL delivery/container grading и student/scenario pilot здесь не выполнены. Они относятся к M02/M05/U07 после появления всех targets. Чужие будущие targets не маскируются legacy aliases. Zig0.15.2 остаётся документированной иллюстрацией; installed0.17dev не выдан за execution этой версии. Эти concerns не означают незавершённое owned authoring или внешний blocker.

U01 прекращает редактирование owned files до возврата review. Parent может заморозить owner-only diff для независимой приёмки. Medium получает готовые тексты, полные contexts, expected stdout/errors и адресные owner requests; новых содержательных решений ему не оставлено.

## U01 fix round 1 — R1/R2

Окно возвращено только для `handbook/entries/java-tools.qmd`, своего packet/report и evidence. Прочитан независимый `task-3-review.md`. **U01-R1** исправлен: полное имя `course.reading.Reading` теперь объясняется путём `course/reading/Reading.class` относительно корня `out`. Исполняемые команды, theory и остальные20 QMD не менялись.

**U01-R2**: в двух намеренно отрицательных contexts пакета комментарии `// false` и `// Ja-va` заменены точными отказами. При логическом `&` NPE возникает во время вычисления `hasText`, печать не выполняется. При вставке в позицию5 возникает IndexOutOfBoundsException до печати; последующие строки помечены как не выполняемые. Обновлены соответствующие временные sample contexts и генерация comments в focused verifier.

Свежая адресная проверка: **13/13 PASS**, `python /tmp/java-course-revision-checks/U01/fix_round_1.py`, результаты `verification-fix-1.json`. Проверены точная область diff, правильный путь, неизменность команд/IDs/categories и executable text, сохранение фактических compile/run логов и expected/pass metadata. Остальные20 QMD совпадают с прежними frozen hashes. Комментарии и prose не меняют поведение кода; широкие115/43/C# suites не запускались, новых execution claims нет. В semantic metadata сохранены SHA256 фактически исполненного текста и текущего текста с исправленными comments.

Все23 hashes обновлены в `frozen-source-sha256.json` с изменением только трёх возвращённых deliverables. Статус исправлений: **DONE/frozen** для адресного review тем же независимым рецензентом. U01 снова прекращает редактирование до возможного возврата.
