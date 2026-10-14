# U00 R1 — доставка готового Maven Wrapper для gradeapp

Статус: **FROZEN_FOR_REVIEW**. Dispatch `U00-R1-wrapper-01`; accepted input `16eaa3d0eb9e83c6502ec3065fca5a66aedcc250`; original U00 owner `/root/u00`; requested `gpt-6-astra / ultra`, actual model/effort `not_reported`. Дата 2026-10-14. Это ограниченная корректировка раздела 7 договора и affected handoff; прежние решения о приложении, самостоятельной работе, ID/categories, grading и времени сохраняются. До независимой приёмки решения U05 продолжает несвязанные авторские задачи, но не закрепляет окончательный delivery helper.

## Основание и выбор

Путь `student/.mvn/wrapper/maven-wrapper.properties` из принятой структуры не попадает в model starter: pinned `tasks/_extensions/Afonenko-Course-Tools/project-download/infrastructure/runtime.ts:204–211` выбирает student и отфильтровывает dot-paths. Ветка ordinary resource (`:220–232`) запрещает их как private/source. `domain/config.ts` также исключает JAR; переход на Wrapper с JAR не решает задачу. Стандартный only-script Wrapper 3.3.4, прочитанный в U05 prototype, требует именно `.mvn/wrapper/maven-wrapper.properties` рядом с mvnw/mvnw.cmd.

Вторая независимая причина: native `domain/zip.ts` не записывает Unix mode. Root реально вызвал этот ZIP-код: распакованный mvnw имеет 0644, прямой запуск даёт PermissionError errno13, `sh ./mvnw` выполняется. Доказательство — `/tmp/java-course-revision-checks/R1-wrapper-delivery/native-zip-mode-result.json` и соседние native-mode.zip/TS. Эта проба использует маленький marker script, а не полный Maven project. Dot-path конфликт установлен по точному коду; полный starter export ещё предстоит.

| Вариант | Последствие | Решение |
|---|---|---|
| A. Готовые helpers + публичные properties; generated .mvn локально; официальный Wrapper вызывается интерпретатором | Один student build command для своей платформы; подготовка предоставлена; не зависит от executable bit | Принят |
| B. Инструкция вручную создать .mvn, скопировать properties и установить executable bit/вызвать sh | Несколько новых setup шагов, ошибки путей и прав; потребляет необоснованное время начинающего | Не принят |
| Только `sh ./mvnw` | Исправляет mode, но конфигурация всё ещё отсутствует | Недостаточно |
| Установленный глобально mvn / JAR Wrapper / изменение native фильтров | Меняет принятый воспроизводимый маршрут либо конфликтует с поставкой | Не принят |

Выбор A — обычное создание локального build state из публичного файла. Он не сохраняет старые ID, не создаёт migrations/aliases и не упаковывает private материалы под другим именем. Wrapper и Maven остаются теми же; нового build system нет. Предоставление двух маленьких helpers добавляет авторскую инфраструктуру, **0 новых назначенных студенту реализаций** и не добавляет обязательные минуты/темы. Это не измерение elapsed build time: скачивание/кеши и готовность инструментов проверяются преподавателем заранее. Оценки 120 для интеграции, L10=225 и lecture11=80 остаются гипотезами принятых бюджетов.

## Точные файлы и состояния

Корень объявления остаётся `tasks/projects/gradeapp/`. Единственный authored starter root — `tasks/projects/gradeapp/student/`. Следующий manifest записан относительно student; **чистый student source и содержимое model starter ZIP имеют эти же 17 путей**. Native ZIP записывает их без префикса student; студент распаковывает архив в отдельную папку и работает там, где находится pom.xml.

```text
README.md
pom.xml
build.sh
build.cmd
mvnw
mvnw.cmd
wrapper/maven-wrapper.properties
src/main/java/grade/Student.java
src/main/java/grade/DataFormatException.java
src/main/java/grade/StudentParser.java
src/main/java/grade/StudentFiles.java
src/main/java/grade/Reports.java
src/main/java/grade/Main.java
src/test/java/grade/PublicExamplesTest.java
samples/students.txt
samples/blank.txt
samples/duplicate.txt
```

`build.sh` и `build.cmd` — полные предоставленные course-side helpers, автор U05, материальный писатель M04. `wrapper/maven-wrapper.properties` — единственный authored источник Wrapper configuration. `.mvn/` не хранится как второй authored источник и не входит в ZIP. Только запуск helper создаёт `.mvn/wrapper/maven-wrapper.properties` в распакованном проекте; Maven создаёт target и использует обычный cache. Для каждого нового delivery proof берут чистую staging copy без этих generated каталогов; не исправляют native ZIP вручную и не подмешивают .mvn после распаковки.

Project-root `README.md`, `reference/src/main/java/grade/{Student,DataFormatException,StudentParser,StudentFiles,Reports,Main}.java` и `tests/junit/GradeAppContractTest.java` остаются вне student, как закреплено ранее. Публичные условия и starter не содержат reference/closed tests/готовых решений TODO. Новая correction не добавляет ссылку на full solution archive, project-check или PL registration. Student Java roles, открытые тестовые примеры и самостоятельные parser/loading/report/Main.run остаются прежними.

## Официальные assets и provenance

Сохраняются уже выбранные U05 **Wrapper 3.3.4, distributionType=only-script, Maven 3.9.16**. U00 прочитал только предоставленные официальные scripts/properties в `/tmp/java-course-revision-checks/U05/prototype/{student,reference}/`; не читал незавершённый предметный текст. U05 сообщил проверку official Maven SHA512; U00 не выдаёт её за самостоятельно повторённую загрузку.

Публичный `wrapper/maven-wrapper.properties` содержит те же байты, что проверенные prototype properties:

```properties
wrapperVersion=3.3.4
distributionType=only-script
distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.16/apache-maven-3.9.16-bin.zip
distributionSha256Sum=5af3b743dd8b876b5c45da33b676251e5f1687712644abb4ee519ca56e1d89ce
```

| Asset | SHA-256 локальных проверенных байтов | Размер |
|---|---|---:|
| mvnw | `cae96cef89ebea3531221f4ae17c23cf8edf67d00eae8306d4186ae1bbed4d02` | 11790 |
| mvnw.cmd | `46eedb8419bd14fe70d5bb2916d7b6f51806e51b39d5b76a42610384ca929c1c` | 8481 |
| wrapper/maven-wrapper.properties | `9e702bcd7e68ba1cace0a3382a7082e6e1e1b321688a7a3fbf4d5ae7eb0af533` | 255 |

U00 подтвердил равенство student/reference assets и version headers; точный provenance official sources сохраняет U05 в packet. Scripts сохраняются byte-for-byte, включая лицензионные headers и CRLF mvnw.cmd. Bootstrap не редактирует scripts/properties, distribution URL/checksum или версии. Дополнительный JAR, самописный downloader и alternate Maven не добавляются. Проверенные compiler/plugin/test versions остаются предметным выходом U05, course toolchain/vendor pins не меняются.

## Единственный маршрут и договор helpers

В корне распакованной заготовки:

- POSIX: `sh ./build.sh test package`.
- Windows cmd.exe или PowerShell: `.\build.cmd test package`.

После завершения TODO тот же маршрут выполняет тесты и создаёт `target/gradeapp.jar`. Запуск приложения из другого чистого рабочего каталога остаётся `java -jar /absolute/path/target/gradeapp.jar /absolute/path/input.txt 80`, с абсолютными путями конкретной системы. Ни третьего CLI argument, ни новых статусов приложения correction не вводит.

Оба helper делают один и тот же ограниченный набор действий, без student TODO:

1. Определяют каталог собственного файла, с корректным quoting пути с пробелами; находят рядом pom.xml, platform Wrapper script и public wrapper properties. Работа не зависит от исходного текущего каталога shell.
2. Проверяют доступность необходимых файлов и подготовленных инструментов. POSIX требует `unzip` и хотя бы один из `sha256sum`/`shasum`; при отсутствии unzip печатает в stderr ровно `gradeapp build: required tool not found: unzip`, при отсутствии обоих checksum tools — `gradeapp build: required tool not found: sha256sum or shasum`. Windows counterpart при отсутствии доступного `powershell` сообщает `gradeapp build: required tool not found: powershell`. Отсутствующий файл: `gradeapp build: required file not found: <relative-path>`; невозможность создать/скопировать configuration: `gradeapp build: cannot prepare .mvn/wrapper/maven-wrapper.properties`. Это ошибки подготовки helper, exit 2; Maven не запускается. Они не являются новой частью grading или кодами grade.Main.
3. Создают в своём project root `.mvn/wrapper/` и копируют в `maven-wrapper.properties` **точные байты** публичного файла. Повторяют копирование при каждом запуске, так что generated copy не становится конкурирующим источником. При ошибке останавливаются; не запускают Wrapper с отсутствующей/частичной configuration.
4. Переходят в свой project root и передают все данные аргументы Wrapper в исходном порядке, сохраняя обычные shell argument boundaries. POSIX вызывает через `sh` (эквивалент `exec sh "$projectRoot/mvnw" "$@"` после cd), не через прямой exec файла и не через chmod. Windows вызывает stock mvnw.cmd через `call`, затем возвращает его exit status. После удачной подготовки stdout/stderr и exit status Maven сохраняются. Для caller из чужого каталога итоговый target всё равно принадлежит этому проекту.

Helpers не модифицируют Java/pom/tests, не меняют глобальные настройки, не задают другую JVM, не устанавливают инструменты и не подавляют ошибки. Документирование ручного mkdir/copy/chmod как шага основного маршрута запрещено: это ровно та предоставленная работа, которую выполняет helper. Прямой `./mvnw` не является учебным build command, даже если он случайно работает из Git checkout.

POSIX primary readiness заранее включает JDK25 java/javac, стандартные shell tools, unzip, sha256sum либо shasum и доступ к выбранной Maven distribution/dependencies или подготовленному cache. Причина обязательного unzip: official mvnw при его отсутствии меняет ZIP URL на tar.gz; установленный SHA256 относится к ZIP. Helper отказывает до этого неявного перехода, **не** удаляет checksum и не подбирает другой архив. Выбор curl/wget/Java downloader остаётся внутри неизменённого official Wrapper. Helpers не превращают подготовку среды в отдельное задание студента.

Windows build.cmd — поставляемый counterpart с теми же preparation/output obligations. Current environment не предоставляет Windows runtime: U00 имеет только статическое основание по официальному mvnw.cmd, Windows execution не заявляется. Отсутствие Windows-run в этой среде **не создаёт нового блокера** для основного POSIX delivery gate; U05/M02 честно отмечают unexecuted counterpart. Не менять execution policy и не устанавливать Windows ради этого correction.

## Передача владельцам и точные проверки

| Владелец | Ограниченное действие после приёмки R1 |
|---|---|
| U05 | Написать оба helper в своём prototype, public properties и объясняющий README; зафиксировать same assets/hashes/provenance. Обновить принадлежащие ему theory/handbook/practice build instructions на единый helper route, не менять application/TODO contract. Реально выполнить native model starter export → распаковку в чистый каталог → POSIX helper build; проверить случай пути с пробелами/чужого cwd, повтор, отсутствие .mvn до запуска и byte equality после него, ошибки отсутствующих unzip/checksum/config, сохранение Maven failure. Windows status отметить static/unexecuted |
| U06 | В условии manual gradeapp считать helpers полностью предоставленными; exact POSIX/Windows commands и адресный handbook route. Не добавлять mkdir/copy/chmod/Wrapper implementation в 120 минут, новую автоматическую проверку или PL question |
| U07 | В application/build слоте использовать ту же primary POSIX команду и при необходимости показать готовый Windows counterpart; объяснять artifact/run результат, не устройство bootstrap. 20 минут Maven/поставка и 80 минут слота остаются прежними |
| M02 | Независимо повторить принятую U05 native-export/unpack/POSIX проверку с evidence вне source и process-local cache; проверить реальные asset hashes, archive manifest, mode0644, generated properties, status propagation и clean-cwd jar run готовой реализации. Static Windows ограничение сохранить |
| M04 | Единственный materializer `tasks/projects/gradeapp/**`: скопировать принятые public files и private layout без догадок/вендорных правок. Проверить scaffold compile через тот же helper с `-DskipTests package`; канонический `test package` для незавершённых TODO может ожидаемо провалиться. Completed reference проходит test/package. Не подменять предоставленный starter решением ради зелёного лога |
| M05 | Проверить реальный final public starter/conditions ZIP, точный manifest/hashes и отсутствие `.mvn`, reference/hidden tests/compiled solutions; затем реальную распаковку и POSIX route. Проверять полученный ZIP, не только source checkout. Существующие student→full→student/private gates и PL17 остаются |

U05/M02 не должны подделывать отсутствие tool посредством удаления системных программ: достаточно изолированного PATH/probe harness в evidence. Чистый Maven cache используется с task-local настройками процесса, без глобальных installs/settings. Full native-export должен пройти pinned owner pipeline, не вручную изготовленный ZIP. Условия остаются переносимым текстом задачи; build source поставляется отдельным starter, private решение остаётся отдельно.

## Проверка самого решения и предел доказательства

В этом bounded U00 dispatch прочитаны pinned runtime/files/config/publish/zip, root native-mode evidence и только official Wrapper/properties из U05 prototype. Проверки коррекции сравнивают два permitted файла с root snapshot: все разделы структуры вне build/delivery в §7, application behavior/roles/TODO, ownership/maps/budgets и исторические packet записи сохраняются; затронуты только operative manifest/route и добавленная история correction. Точный public manifest, checksums, links и native Markdown разбираются отдельно. Результаты — `/tmp/java-course-revision-checks/R1-wrapper-delivery/u00/verification.json` и `.superpowers/sdd/execution-plan/u00-r1-wrapper-report.md`.

U00 **не реализовал helper и не выполнил full starter export/build**: это точный, готовый к review structural contract для автора U05. Результат не выдаётся за Windows acceptance, final delivery/private acceptance или педагогический pilot. Root передаёт решение тому же независимому U00 reviewer; U05 начинает affected implementation после его принятия. Новая пользовательская permission не требуется.
