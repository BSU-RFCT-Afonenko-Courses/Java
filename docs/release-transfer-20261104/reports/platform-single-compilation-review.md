# Независимый обзор single compilation

## Первичный допуск

Роль: независимый технический reviewer, Sol6.1/high, Standard. Собственный ID чата `01a1a206-d7b5-72e0-9ecd-9a51668b54e4`, исходный turn `01a1a206-d7e9-71f1-914b-9080eec3d241`, путь `/root/review_single_compilation`; родитель `01a1a142-7adc-7371-9765-4871d807dfcd`.

Первичный источник: `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T18-28-39-01a1a206-d7b5-72e0-9ecd-9a51668b54e4.jsonl`. Прочитаны собственная `session_meta` и все текущие `turn_context` (один от исходного turn). Зарегистрированы верхние `model=gpt-6.1-sol`, `effort=high`; вложенные `collaboration_mode.settings.model=gpt-6.1-sol`, `reasoning_effort=high` согласованы. Допуск пройден. Standard назначен координатором, поле `service_tier` в первичных данных отсутствует; Fast/Ultrafast не выбирались. Нормативный `java/docs/model-admission.md` и глобальные настройки не менялись. Первичные записи подтверждают зарегистрированные параметры, не отсутствие скрытой серверной маршрутизации.

## Область

Read-only Source review диапазона `d14231d0129ca065aa12414e6b4c49c376d72a2a..503e0ccb61ab5460fe0433fa0d13d623e1e6ffef` в Platform. Тесты/build/inspect/hash не перезапускаются, source и ветка не изменяются. Прочитаны полный diff семи файлов, brief, авторский report, применимые AGENTS/языковое правило, Java model-admission и матрица ролей, R4 cross-check, окружающие execution/preflight/toolchain/role/artifact переходы и затронутые тесты.


## Результат: Clean

Конкретных существенных дефектов в проверенном diff не найдено. Для source review изменение допустимо в согласованном контракте одного production compiler и настоящего Gradle packaging.

- **Compiler/classpath.** В `grade.py:1455` native production получает свежий `stage/student` как единственный classpath; это пустой внешний classpath без fallback на cwd. Единственный настоящий JDK25 javac сохраняет `--release 25`, UTF-8, `-proc:none` и закрытый sourcepath. Обычный `java25-junit-v1` получает прежний `libs`; mutation ветвь не затронута. Native profile закрыт на `mode=implementation` и обязательный `javaBuild`, поэтому новая ветвь не расширяет допустимые режимы. Неподдерживаемый native framework import теперь даёт согласованный ранний student compile outcome; это явно описанное изменение классификации, а не случайная перемена баллов.
- **API и зависимые тесты.** API продолжает читать original `stage/student`, выполняется до native build и trusted subject compilation. Compile failure и API mismatch возвращаются до build/dependent tests. Trusted/test classpath сохраняет JUnit/framework и original student classes; оценочная логика, discovery, scoring, публичные cases и feedback protocol в diff не менялись.
- **Одна implementation в JAR.** `grade.py:2281` копирует только actual `.class` из private javac output с проверками пути, owner и single link; packages и nested classes сохраняются. Копии принадлежат supervisor, файлы0444/каталоги0555, включены в существующее admitted/copy сравнение. Fixed recipe использует только эти classes, исключает sources/resources из compilation и отключает `JavaCompile`. Настоящие `clean jar`, optional Main-Class, artifact basename, воспроизводимые timestamp/order и прежний cwd-relative JAR path сохранены.
- **Передача и containment.** До запуска build закрываются stage/runtime; отдельный UID, Landlock, canary denial, fresh build role, private full-JDK root и проверка отсутствия live build processes сохранены. `finish_role` находится в `finally` до чтения и передачи JAR. Новая проверка `grade.py:2390` требует ровно Manifest и весь набор скомпилированных `.class`, запрещает повторные file entries и сверяет class bytes. Затем действуют прежние inode/size/link/transfer/drift guards и read-only job artifact. Build role не получает trusted/API inputs или compiler archive.
- **Ошибки и pins.** Новые отказы binding остаются `GraderError` и обрабатываются native infrastructure/noScore путём; trusted/tool/Gradle failures не превращены в student0. Обновлены оба source runner pins и существующий preparation `RUNNER`; native recipe pin формируется существующим `baked_view` из точных recipe bytes. Image/server/Java/course pins не изменены. Полные installation-byte guards в response path не возвращены; новый bounded class/JAR binding соответствует отдельной границе артефакта.

## Свидетельства и ограничения

Независимо выполнено чтение исходников и diff; новых tests, compilation, Gradle, image build, benchmarks, inspect или пересчёта source/installation hashes не было. Из авторского report переиспользованы заявленные результаты одного настоящего host JDK25/Gradle9.8.0 success route, адресных unit/negative tests и preparation closure test. Прочитанный host test действительно запускает javac/API/Gradle/JAR/JUnit и отдельно оговаривает doubles для role/namespace boundary; он не доказывает actual OCI containment.

Actual image с новым diff и новый image digest отсутствуют. Поэтому Clean относится к source review; окончательная image/installed-runtime интеграция этим отчётом не заявляется. Новая тяжёлая проверка по поручению не запускалась. Source/ветка не изменялись; создан только этот report.

Перед сдачей повторно прочитан полный собственный интервал от того же исходного turn: одна запись `turn_context`, верхняя и вложенная пара `gpt-6.1-sol`/`high` согласованы; исходная точка допуска не сдвигалась.
