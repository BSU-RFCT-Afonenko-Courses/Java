# Требования, осуществимость и карта тяжёлых операций

3 ноября 2026 года. Дополнение к [исследованию R0–R6](improvement-research-results-20261103.md), исходная Java `dd5b76a0168133f0760ece6cb21f4bdf4f6203bb`, [PR 32](https://github.com/BSU-RFCT-Afonenko-Courses/Java/pull/32). Повод: прямое поручение пользователя сопоставить требования и выводы, проверить все относящиеся к курсам расширения/проекты, найти фиксацию и хеширование файлов, inspect и другие тяжёлые операции для следующего прохода.

## Ответ

**Принципиального противоречия между пользовательскими целями нет; существующий стек позволяет их реализовать. Нынешняя реализация этим целям соответствует частично.** Обычные Java/JUnit, Gradle для самостоятельного проекта, native Quarto/Pandoc, TypeScript-модель, PrairieLearn и Workspace дают необходимые механизмы. Исследование обнаружило дефекты договоров, повторную служебную работу и непринятые внутренние решения, а не необходимость заменить весь стек.

Сокращение проверочной обвязки не новое послабление. Пункт 74 уже прямо требовал сократить повторные inspect и хеш-проверки в расширениях, сборке и агентском цикле. Сохранность данных, актуальность результата и содержательные предметные тесты не предписывают хешировать неизменную поставку перед каждым дочерним процессом. Конкретные SAME S, authority, receipts и qualified launcher — инженерные механизмы нынешнего кода; их буквальное устройство не следует возводить в неизменное требование пользователя.

Отсутствие сервера/фактического OCI в исследовании ограничивает утверждения о развёртывании. Оно не мешает установить повторы в закреплённом Source. Новых render, inspect, сборок, тестов, контейнеров, benchmark или полного хеширования в этом аудите не было. Частоты ниже получены трассировкой исходников; новые секунды и проценты ускорения не заявляются.

## Где требования и реализация расходятся

| Принятое требование | Что показало исследование | Осуществимость на стеке |
| --- | --- | --- |
| Пункты 12, 32–33: простой вход в задачу, обычный JUnit, необязательный самостоятельный Gradle-проект, один канон | Проверка ответа повторяет компиляцию и сборку JAR; обвязка Transform отвергает часть обычных assertions | Да. Пункт 32 прямо оставляет исполнение JUnit обычным адаптером или Gradle открытым техническим выбором. Gradle при каждом ответе и при render сайта не требовался |
| Пункты 37, 65, 71: самостоятельные оценочные случаи, русские имена, expected/actual проваленного случая, краткий отчёт | Circle сворачивает несколько случаев в boolean/один leaf; часть полезной диагностики теряется; есть ручной дубль ожиданий | Да. Обычные JUnit names/parameterized tests/assertions и получение реальных failures подходят. Требование не обязывает инструментировать каждую успешную assertion и поддерживать второй ручной язык карточек |
| Пункты 34–35: native Quarto, строгая TS-модель и исполняемые контракты; без второго resolver | Сохранились legacy CUE, Lua/JSON transport, повторные walks/serialization, сложная фиксация capture | Да, но TS-миграция ещё не реализована целиком. Runtime-валидация входного договора нужна; повтор всей проверки на каждом getter не следует из этого требования |
| Пункты 40–41, 73–74: удалять заменённую обвязку, проверять затронутое, переиспользовать результат, быстрые native/incremental builds | Полные snapshots, повторные inspect, local/full HTML passes, повторные CI suites и дорогая подготовка измерителя | Да. Это прямые кандидаты удаления/объединения/переноса в подготовку. Отчёт не предлагает новый общий validator, runner или менеджер доказательств |
| Пункты 36, 81: ясный минимальный учебный API; редкие обоснованные ручные исключения | Файловый reader требует скрытые точные заголовки при свободном формате условия; некоторые starter/API договоры расходятся | Да после согласования условия, стартера и наблюдения. Произвольный необъявленный формат результата нельзя автоматически угадать; пользователь уже разрешил минимальный объявленный API |
| Исполняемые QMD, inline/include и ресурсы без постоянной переиндексации | Native R/Python работают; freeze/cache не обнаруживают произвольные внешние изменения и могут переиспользовать результат другого профиля | Да при явных зависимостях/профиле или явном обновлении. Нельзя одновременно обещать обнаружение любых необъявленных внешних изменений и отсутствие наблюдения за ними. Новые QMD следует генерировать до планирования либо выделить явную фазу генерации |
| Пункты 79–81: сохранность реальных результатов, файлов и соответствий пользователей | Реальный server runtime и часть причин старых начислений неизвестны; Source-анализ не доказывает состояние БД | Реализуемо штатным резервированием/восстановлением и адресной миграцией. Это отдельная граница данных, не основание повторно читать всё дерево курса при status |

Основания: [решения 32–35](../refactoring-plan.md#decision-32), [40–41](../refactoring-plan.md#decision-40), [65](../refactoring-plan.md#decision-65), [71](../refactoring-plan.md#decision-71), [74](../refactoring-plan.md#decision-74), [81](../refactoring-plan.md#decision-81). Черновики target-design описывают варианты внутреннего устройства и не заменяют эти решения.

### Уточнения к первому отчёту

1. Фраза о необходимости «определить оценочную единицу» для Circle была неточной: пункт 65 уже определяет самостоятельный случай, включая каждый параметризованный набор. Следующий этап должен привести тесты к этому правилу и явно оформить влияние на прежние результаты, а не заново выбирать правило или закреплять нынешние 3 leaf как норму.
2. Содержательная обратная связь не доказывает необходимость нынешнего assertion/report фасада. Полезные данные обычного JUnit и требование рассказывать про каждую успешную assertion — разные вещи; второе пользователь не устанавливал.
3. Java и System Practice имеют разные pins Core/Publisher/PL. Старые N-inspect/IO пути SP нельзя приписывать обновлённому Java. У Java static checkpoint внутри batch **не делает source/file IO**; утверждение «каждый getter заново хеширует весь курс» неверно. Full fences и исходный whole-owner snapshot при этом остаются.
4. Противоречие свободного файлового условия скрытому parser — дефект учебного договора. Повторные проверки неизменного JDK — инженерная избыточность. Оба требуют исправления, но имеют разные причины; расширением проверочной системы они не лечатся.

## Охват

Проверены собственные исполнимые пути всех относящихся к Java/SP владельцев. Установленные копии одного расширения в root/theory/tasks/lectures/practice/handbook учитывались как разные scopes вызова, а не как новые независимые проекты. Исторические worktrees не выдавались за актуальный Source. Сторонние vendor-библиотеки не разбирались как отдельные продукты.

| Владелец / проект | Какие пути охвачены | Карта |
| --- | --- | --- |
| quarto-course: Core, Presentation, Navigation | Source/runtime identity и snapshots, authority, metadata contexts, AST/Body, materialization, output sealing, HTML composition | [Core: C01–C14](heavy-operations-20261103/core.md) |
| quarto-reference-catalog | Post inspect, local/full linking, search, HTML read/parse/write | [C11–C12](heavy-operations-20261103/core.md) |
| quarto-course-performance | Off/summary/cpu wrapper, diagnostic preparation, package inventory, freeze/prepare/compare/benchmark | [C15–C17](heavy-operations-20261103/core.md) |
| quarto-project-download | Request/ownership files, JSON parsing, hashes, package materialization | [Export](heavy-operations-20261103/export.md) |
| quarto-project-publish / course-site | Inspect, native composition, stage collection/copy, audience validation | [Export](heavy-operations-20261103/export.md) |
| quarto-course-prairielearn | Per-question/full/student materialization, ZIP/Workspace seed, staging, CI suites | [Export](heavy-operations-20261103/export.md) |
| quarto-course-moodle, quarto-template-course | Legacy XML export/resources/Pandoc; установка и публикация template | [Export](heavy-operations-20261103/export.md) |
| prairielearn-platform | Java/JDK/Gradle, helpers/API/Transform, runtime evidence, Workspace, release/build/check-course/private staging/CI | [Platform](heavy-operations-20261103/platform.md) |
| moodle-prairielearn-gateway | Registry/link validation, deployment/release/startup and filesystem operations | [Platform](heavy-operations-20261103/platform.md) |
| Java, System-Practice | Pinned extensions, package/release/check scripts; различия закреплённых версий | [Export](heavy-operations-20261103/export.md), [Platform](heavy-operations-20261103/platform.md) |
| quarto-course-print | Resource digests, assets inventory/copy, Pandoc/Typst, dev benchmark | [EX01–EX04](heavy-operations-20261103/extra.md) |
| quarto-course-cloud | Native transport, AST/JSON, legacy CUE helper и path subprocess | [EX05–EX06](heavy-operations-20261103/extra.md) |
| quarto-course-perf-arch | Makepkg source checks, full installed-tree smoke, холодная Podman CI | [EX07–EX09](heavy-operations-20261103/extra.md) |

Print/Cloud/Moodle XML не объявляются активными горячими путями нынешнего выпуска Java/SP. Cloud имеет `export_implemented: false`; общая экспортная цель ещё не означает готовую реализацию Cloud. `quarto-course-budget` — старый worktree quarto-course, отдельного текущего владельца нет. Attendance не относится к стеку этих курсов. Карта включает source места и callers; она не является обещанием измерить каждую найденную функцию.

## Что поставить под вопрос первым

Приоритет означает размер доказанного повтора и близость к пользовательскому действию; рейтинг по измеренной доле времени не строился.

| Участок | Доказанный повтор | Предмет следующего решения |
| --- | --- | --- |
| Java grading: установка | Успешный native Source path семь раз проверяет полные Gradle/JDK inventories; прежний lock — 1035 файлов / 516 164 993 байта за проход | Убрать повторное доказательство неизменной поставки из обработки одного ответа; проверять поставку в её подготовке/установке |
| Java grading: version/preflight | Уже только два preflight дают четыре java/javac version запуска; каждый имеет before/after boundary с чтением+SHA256 пяти JDK files, обходом JDK и canary processes. Это 8 boundary, номинально 1 390 007 520 прочитанных bytes core JDK и 40 canary subprocess, без остальных запусков | Зачем повторять весь допуск вокруг короткой version-команды; оставить проверку реально меняющегося состояния на нужной границе job. Числа не суммируются с предыдущей строкой в измеренный disk I/O |
| Java grading: execution | javac/API, затем повторная compilation Gradle/JAR; JUnit исполняет прежние classes. Helpers/adapters/API компилируются на job | Один используемый результат компиляции; постоянный доверенный код готовить отдельно от студенческого ответа |
| Platform release/build/check | Context.recheck заново создаёт GitSource, делает cat-file/hash source blobs и physical tree; вложенные stage/scenario пути повторяют inventories/runtime_evidence. Private staging делает два полных inventory подряд, только потом фильтрует runtimeRoots | Срок жизни уже прочитанного Source и прямое удаление дублей; не заменять их новой цепочкой receipts |
| Workspace | Restart читает/hash весь seed.zip даже при существующем work; подготовка seed повторяет inventories, Git и Python recipe; обычный ZIP иногда уже построен и затем заменён Workspace ZIP | Отделить создание/проверку seed от повторного открытия рабочего места; не строить заведомо неиспользуемую выдачу |
| SP native metadata | Старые Core9.1.0/Publisher5.3.0 сохраняют per-input/per-document inspect и whole-source temp copy; старый PL вызывает assertCurrent с metadata IO | Перейти к существующему пакетному пути по реальным owner/input/profile/format, не увеличивать timeout старой схемы |
| Java Core | Whole-owner SHA256 snapshot, включая установленный extension/vendor; full boundary metadata walks, runtime identity hashes, output rehash/seal | Какие bytes действительно относятся к операции, где заканчивается один результат и начинается новая необходимость проверки |
| Download / PL | Повторное чтение+parse request/ownership; hashing уже материализованных bytes; preliminary student → optional full → final student; повтор общего question на разные work | Один раз получать неизменные request/bytes/общие parts и использовать их в той же операции |
| QRC / Publisher / Navigation | QRC снова делает owner inspect после Core; local/full publish повторно разбирают HTML; Navigation затем читает/парсит HTML ещё раз | Передавать существующий native context; один финальный проход публикации и переиспользование одного Page tree внутри composition |
| Native AST/resources | Два обхода одного raw===public AST; две whole-document cleanup/JSON стадии; ресурс Lua SHA1 → post SHA1+SHA256 → выдача SHA256; общий README ранее разбирался Pandoc 8 раз | Удалить прямые дубли, переиспользовать AST/bytes в известном lifetime; сохранить действительно разные преобразования |
| Performance | Off всё равно добавляет inspect/Git/version. Summary/cpu дважды hashes quarto.js и готовят backend treeHash→copy→treeHash, включая node_modules | Сам измеритель не должен навязывать эту работу каждому render; прямые дубли удалить, неизменную подготовку переиспользовать |
| Тесты / CI / упаковка | PL общий check запускает все suites, следующие gates повторяют большие subsets; Arch каждый PR строит холодный Podman/makepkg и twice hashes installed tree | Один meaningful запуск каждой группы на нужном уровне дельты. Release/install проверки не превращать в цикл обычной правки |

В подробных картах 74 группы: Core/QRC/Performance — 17, export/download/publication — 24, Platform/Gateway/project scripts — 24, дополнительные проекты — 9. Это группы связанных операций, не 74 независимых дефекта и не число отдельных функций.

Отдельно найдены фоновые и проектные расходы: Gateway по умолчанию каждые 15 секунд последовательно обходит assignments/bindings/outbox; Java/SP CI повторяет полный student/full/student render, provider inventories и генерацию материалов. Это разные scopes: фон сервера, обычная сборка, тестовая приёмка, установка. SQLite transaction locks не названы лишней фиксацией файлов, а version/pin metadata preflight не выдан за полный source hash.

Подробные карты содержат точные repository/ref/path:line, caller, scope и известную частоту. Hash короткого ключа URL/имени, hash при установке архива и hash гигабайт неизменного JDK перед каждым child имеют разную цену и назначение. Определение helper без caller не выдано за горячий путь. Там, где время неизвестно, оно остаётся неизвестным; наличие повторов не требует ещё одного benchmark, чтобы поставить их под вопрос.

## Дополнительное прямое требование: удалить `j25` из учебника

Пользователь в ходе этого аудита отдельно поручил записать: **убрать `j25` из всех идентификаторов глав и заданий и вообще из учебника**. Это пункт следующего прохода, не выполненное переименование.

В закреплённом Java Source адресный поиск `j25` по theory/tasks/lectures/practice/handbook/_includes без `_extensions` нашёл 4149 строк в 132 файлах. Это количество строк с совпадением, не количество уникальных идентификаторов. Пример определения: `theory/chapters/classes.qmd:6` — `sec-j25-theory-classes`; ссылки есть в `handbook/index.qmd:10`, `tasks/bank/projects.qmd:14` и других текстах.

Объём будущего изменения: определения chapter/exercise IDs, native/QRC xrefs и anchors, metadata/состав работ, ссылки в лекциях/теории/справочнике/практикуме, выдаваемые README и производная публикация. Имена должны описывать смысл без версии JDK. Удаление префикса следует выполнить согласованно с потребителями идентификаторов; ранее признанные результаты студентов не переоцениваются из-за смены имени. Речь не об отмене используемой версии JDK или переименовании внешних Docker images. Итоговый учебник и выдача должны обходиться без `j25`, а не сохранять его в новых видимых aliases.

## Следующая ограниченная смысловая задача

Для обсуждения предлагается один первый diff в Platform Java grading: **убрать повторные проверки неизменных JDK/Gradle из пути одного ответа**, включая полные inventories и повторный допуск вокруг version-команд. Отдельно определить, что действительно зависит от состояния текущего job, и оставить эту работу на соответствующей границе. Не менять этим diff предметные тесты, правила баллов, студенческие данные, протокол публикации и все расширения сразу. Не создавать вместо удалённой обвязки новый универсальный proof/receipt механизм.

Следующими отдельными задачами остаются одна compilation/JUnit цепочка, SP batch metadata, повторная материализация/HTML, чистка тестовых gates и согласованное удаление `j25`. Нынешний аудит не запускает эту очередь автоматически. Продуктовые исходники, pins, существующие worktrees, незакоммиченные изменения и открытые PR сохранены; после отчёта — совместное обсуждение.
