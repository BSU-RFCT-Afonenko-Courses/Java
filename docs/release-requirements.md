# Требования к согласованному релизу курса Java и инструментов

Статус: конкретизированные требования к реализации. Дата: 9 октября 2026 года.
Пошаговое исполнение описано в [плане релиза](release-plan.md).
Для новой сессии используется [стартовая инструкция](codex-start.md).
Исходная разметка Java зафиксирована коммитом
`27f6fe0152d2d7e227c51e3542ca03f256e4ee35`; окончательный authoring развивается
совместно с инструментами, а не объявляется завершённым этим snapshot.

Цель релиза — получить воспроизводимый курс из авторской модели и дополнительной
декларативной конфигурации. Условия, проекты и назначения редактируются один раз.
Расширения создают сайт, скачиваемые комплекты и полный нативный курс PrairieLearn.
Локальная проверка и платформа используют один проверяющий образ и один протокол.
Moodle открывает студенту его работы и получает согласованный результат.

В текущем проходе фиксируются snapshot и инструкция. Будущая сессия,
запущенная по codex-start.md, уполномочена выполнить согласованную разработку,
PR/merge/releases и миграцию курсов по указанной последовательности.
Исследование контейнера не является интеграционной приёмкой нового релиза.

## Исходные версии и PR

Перед исполнением повторить проверку последних PR по созданию и обновлению.
Зафиксировать точные head SHA и состояние PR. Для открытого PR использовать его
точный head как базу; для слитого — актуальный потомок, сохраняющий также изменения
выпущенного тега. Нельзя возвращаться к более старому состоянию только ради номера PR.

| Компонент | Проверенная база на 9 октября | Точный head PR или исходный SHA |
| --- | --- | --- |
| Экспорт PrairieLearn | [PR 11](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/pull/11), open draft, editor; descriptor 3.1.0 candidate | `11a6c73ee306c51b25f0714869af69def0234667` |
| Платформа PrairieLearn | [PR 1](https://github.com/Afonenko-Course-Tools/prairielearn-platform/pull/1), open draft, локальная платформа Java 25 | `b622a1ea4e695e0deb819672656fd5c43a422b38` |
| Moodle gateway | PR отсутствуют; начальный `main`, реализации пока нет | `d6d164b16806350216df2b1b53ffa3be1f4408f3` |
| Core, Presentation, Navigation | [PR 27](https://github.com/Afonenko-Course-Tools/quarto-course/pull/27), merged; выпуск 4.0.1 | PR: `33e0a4ef59c394aa8a0eaf3f4dc220259c96fea4` |
| Download | [PR 6](https://github.com/Afonenko-Course-Tools/quarto-project-download/pull/6), merged; выпуск 2.0.0 | PR: `ffbc71eafbb0a8d9a0e7c111ef4aa03b24304b11` |

Установленные immutable bases: Core 4.0.1 —
`a9a439bd6e6498806d4d4943efd71232e70170be`; Download 2.0.0 —
`ee5ae76255d265ad7c7f43a765bc061ffc8eec75`; опубликованный PL 3.0.1 —
`9f815582ad271d2c0d2335d644de119c6afdb3aa`. Editor находится в кандидате PR 11.
Проверки прежних PR не подтверждают новую совместную комбинацию.

Для gateway требуется первый функциональный PR на основе указанного `main`.
Номер будущего PR или ещё не выпущенную версию нельзя представлять существующими.
Итоговые версии, исходные SHA, схемы, минимальные зависимости и OCI digests
фиксируются одной таблицей совместимости до установки в курс.

## Владельцы и границы

| Владелец | Ответственность |
| --- | --- |
| Core | Канонические задания, effective properties, работы и назначения, видимость, текущая модель и разрешения на артефакты |
| Presentation | Представление уже разрешённого содержания, подписи и суммы времени |
| Download | Разрешённые моделью комплекты, детерминированные ZIP и ссылки |
| Расширение PrairieLearn | Платформенные декларации и генерация полного нативного курса |
| PrairieLearn platform | Проверяющий образ, единый runner, локальная проверка, хранение и импорт поставок, исполнение заданий |
| Moodle gateway | Доверенный запуск, runtime identity, запись и подтверждение выбора, персональный доступ, передача результата |
| Курс Java | Условия, метаданные, Java-код, заготовки, эталоны, JUnit-тесты и предметные критерии |
| Template | Документация выпущенного формата и согласованные примеры |
| CI курса | Тонкий запуск опубликованных средств и проверка результатов курса |

В курс не добавляются собственные экспортёр, grader, общий runtime или копии
проверок владельцев. Vendored `_extensions` обновляются только целыми пакетами.
Body остаётся платформенно нейтральным; PL-параметры находятся в области адаптера.

## R01 Удаление ручной поставки PrairieLearn

Первое изменение курса при исполнении — удалить авторский каталог
`prairielearn/` с пятью файлами:

- `binding.json`;
- `export.json`;
- `native/infoCourse.json`;
- `native/courseInstances/pilot/infoCourseInstance.json`;
- `native/courseInstances/pilot/assessments/java25-pilot/infoAssessment.json`.

Сейчас в этом каталоге нет вручную поддерживаемых `questions/`: существующий
экспортёр уже генерирует вопросы. Удаляется ручное описание нативной поставки,
а не исходное учебное условие.

Сохранить `tasks/assessments/java25-pilot.qmd`, его проект, эталон и JUnit-тесты.
Использовать ID `sec-pl-java25-pilot`, `exr-pl-java25-unicode-slice`,
`sec-lab-numbers`, `exr-lab-integer-arithmetic` как исходные устойчивые ключи.
На этом этапе ID разрешено менять согласованной миграцией; ручные UUID не нужны.
Сам перенос каталогов не меняет вычисляемую идентичность.

Параметры старой поставки переносятся в поддержанную семантическую конфигурацию
расширения: logical course `java-portal`, native name `JAVA`, тема Java,
`Europe/Minsk`, устойчивый ID instance, editor, принимаемые исходники,
Java 25, UTF-8 и три попытки пилота. Прежнюю бинарную оценку записать как baseline,
а новую политику определить явно после сравнения альтернатив. Настройки публичного
доступа старого локального fixture не становятся production policy автоматически.

Не допускаются replacement shell, ручные native JSON в другом каталоге,
подготовка native файлов курсным Python-скриптом или исправление generated output.

## R02 Defaults упражнений

Новый авторский контракт использует скалярные metadata:

```yaml
default-exercise-target: manual
default-exercise-course-role: independent-study
default-exercise-statement-visibility: open
default-exercise-difficulty: introductory
default-exercise-time: 90
```

Quarto выполняет project/directory/document inheritance. Core не читает
`_quarto.yml` и `_metadata.yml` повторно собственным механизмом. Настройки
применяются к объявлениям упражнений; они не меняют семантику `exm`, `sol`,
preparation, reading и списков назначений. Небанковское упражнение сохраняет
свой статус: наличие defaults не включает его в банк.

| Поле | Правило после объединения metadata Quarto |
| --- | --- |
| target | Отсутствующее значение берётся из общего; совпадающее явное допустимо; отличающееся явное — ошибка |
| statement-visibility | Обычный default: индивидуальное open/restricted имеет приоритет |
| course-role | Индивидуальное значение имеет приоритет над default; без обоих обычное `exr` остаётся exercise |
| difficulty, time | Индивидуальное значение имеет приоритет; в банке итоговые значения обязательны |
| project-check | Индивидуальное имя профиля имеет приоритет над default; профиль проверки обязателен для PL export |

Для смешанного документа `default-exercise-target: false` означает отсутствие
общего target и отменяет значение каталога. Аналогично `false` отменяет общий
default visibility, после чего банковские задания указывают видимость сами.
Это специальное значение настройки default, а не допустимый target задания.
Null, пустая строка и неизвестные значения не подменяют явную отмену.

Ошибка конфликта содержит source, ID задания, поле, default и explicit value.
Проверки выполняются до audience projection и охватывают скрытые ветви.
Неизвестные ключи `default-exercise-*` отвергаются.

Старый `exercise-statement-visibility` мигрируется на новое имя в том же релизе.
Если оба имени встречаются во время диагностической миграции и значения
различаются, выдаётся ошибка; итоговый формат имеет один способ записи.

Core один раз вычисляет effective properties до проверки адаптеров. Их получают
Course, Body, NativeRun, Presentation, CUE, Download и PL. Унаследованный target
активирует PL так же, как явный, и сохраняет требования к заголовку и проекту.
Сокращение разметки при неизменных ID и итоговых значениях не меняет UUID.

`default-exercise-project-check` разрешён для каталога однотипных программных
заданий. Упражнение с итоговым project-check обязано иметь project; поэтому
общий default нельзя задавать смешанному документу с непрограммными упражнениями.
При необходимости `default-exercise-project-check: false` отменяет наследование.
ID, project, связь с решением и фактическое наличие решения не наследуются.
Семестр и категории остаются метаданными тематического документа.

## R03 Defaults назначений

```markdown
::: {.task-items requirement="required" work-mode="individual" stage="classroom"}
1. @exr-lab-integer-arithmetic
2. [@exr-another]{requirement="optional"}
:::
```

Для requirement/work-mode: Span → `.task-items` → required/individual.
Stage принадлежит блоку и может отсутствовать; скрытого classroom нет.
Stage на Span должен диагностироваться как ошибочное размещение.
Порядок, повторный участник, required/optional и individual/pair/group
проверяются прежними правилами. Суммы времени используют итоговые назначения.

Это свойства назначения, а не задания. Одно упражнение может назначаться
по-разному в разных работах. Правило «одна QMD — одна работа» сохраняется;
несколько task-items могут описывать её этапы.

## R04 Модель скачиваемых комплектов

Путь проекта объявляется один раз. Download запрашивает разрешённый комплект
по ID упражнения и текущему контексту. Ручной словарь
`project-download.resources` для модельных упражнений удаляется.

| Вид | Источник и содержимое | Доступ |
| --- | --- | --- |
| starter | Содержимое student partition в корне ZIP: открытые примеры, build files, самостоятельная инструкция | По разрешению модели |
| full | Корень программного проекта: student, reference, tests, README | full view; для открытой demonstration также student view |
| conditions | Разрешённые условия и необходимая подготовка, переносимые ссылки и ресурсы | По политике условий; без ответов, решений и teacher notes |

Вид комплекта `full` и профиль сайта `full` — разные понятия. Один вызов
`{{< project-download ID >}}` выводит ровно одну контекстную ссылку:
обычная задача в student получает starter, в full — полный проект; открытая
demonstration получает полный проект в обоих профилях. Упражнение должно иметь
effective course-role demonstration и открытое условие; имя каталога или
произвольный `demo: true` сами по себе не разрешают раскрытие проекта.
Миграция Unicode задаёт эту роль также трём программным упражнениям примера.

Открытое обычное условие не разрешает полный преподавательский ZIP в student.
Restricted задача не оставляет запрос Download в student projection.
Дополнительный явный `kind="conditions"` может запросить переносимые условия;
даже у demonstration такой комплект не включает решение. Это выбор автора,
а не группа из нескольких ссылок для студента.

Core предоставляет project/artifact facts также для упражнений вне банка.
Открытая Unicode demonstration не включается в банк ради Download.
Условия формируются из resolved native AST с учётом общей подготовки;
необработанные Cite/shortcode/include не копируются в переносимый документ.

Модель задаёт разрешённые части и намерение скачивания. Общая конфигурация
Download задаёт только упаковочные правила. Shortcode не повторяет путь,
профиль и exclude. Оставшийся запрос обрабатывается после завершения Core post.
Запрос вне разрешённой области или на отсутствующий комплект — диагностируемая
ошибка, а не пустая ссылка.

Исправить пустую подпись shortcode: отсутствующая/пустая text получает понятное
имя комплекта; явная подпись сохраняется. Проверить видимость и keyboard access
в браузере, а не только существование href.

ZIP детерминирован; нет source QMD, Quarto config/hooks, extensions, generated
outputs, caches, `.git`, class/jar результатов и машинных путей. Разрешённый
корневой `student/.gitignore` можно сохранить. Symlink/path escape/ownership
guards остаются. Разрешение полного проекта demonstration не ослабляет
public resource guard для остальных материалов.

## R05 Самостоятельные README и преподавательский текст

README является непрозрачным файловым payload: не render target и не вход QRC.
Quarto includes, ссылки на QMD и условия профиля внутри него не исполняются.
README каждого архива содержит работающие команды для именно этого архива.
Ссылки ведут внутрь комплекта либо на устойчивый опубликованный ресурс.
Ссылок на отсутствующие `../reference`, `../tests` в starter README нет.

Убрать исходные QMD-ссылки в README integer-arithmetic и charset-decoding.
Исправить инструкции пилота: starter описывает `gradle test`, full — также
проверку эталона. Переносимые полные условия поставляются отдельным документом.

Авторские audience wrappers `content-visible/content-hidden` с
`when-profile/unless-profile="student|full"` и их обходные эквиваленты запрещены.
Проверка expanded author AST включает скрытые ветви. Functional profiles,
format conditions и выбор `course.view` в конфигурации не запрещаются этим правилом.

Restricted conditions управляются моделью; grading-notes остаются при задании;
общие преподавательские инструкции располагаются в full-only страницах книги
и README полного проекта. Новая разновидность teacher-material для этого
релиза не требуется. Нельзя превращать
произвольные инструкции в grading-notes только ради сокрытия.

## R06 Полный экспорт PrairieLearn

Установленное расширение генерирует course, instances, assessments, questions,
editor/upload payload, trusted tests, delivery manifest и provenance из модели
курса и собственных проверенных деклараций. Обязательных binding.json,
export.json и authored native shell нет.

PL adapter хранит topic, именованный source profile, submission mode и ссылку
на профиль проверки в `extensions.prairielearn`. Собственный
нормализованный контекст адаптера включает course/instance и assessment policy.
Эти данные не внедряются в платформенно нейтральное тело условий.

Конфигурация может выбирать instance и экспортируемые работы, но не повторяет
состав заданий: его определяют `.task-items` и модель Core. Private grading
assets копируются по проверенным project partitions. Reference не входит
в native delivery или editor; он используется только локальной авторской проверкой.

Editor содержит только разрешённые UTF-8 исходники; starter Gradle/public tests
относятся к Download. HTML/Mustache literals, NUL, binary, oversize, missing file,
unsafe source name, symlink и неизвестные поля проверяются до записи output.
Проверка native upstream schemas предшествует atomic публикации свежего каталога.

Course/work/question/instance IDs и UUID воспроизводимы по устойчивым ключам.
Question UUID сохраняет существующий алгоритм exporter; UUID остальных
сущностей вычисляются в namespace курса по типу сущности и её устойчивому ID.
Ручные UUID из прежней native shell не становятся авторской конфигурацией.
Две независимые поставки имеют одинаковое содержимое. Изменение пути QMD или
расположения проекта не меняет идентичность. Если политика UUID изменяется,
релиз содержит явную однократную миграцию.

Инвентаризуются проекты с явно выбранным project-check, включая унаследованный
default. Для выбранного PL export такой выбор обязателен. Обычная HTML-сборка
не запускает Java и не требует проверочного профиля у ещё не экспортируемой задачи.
Если профиль указан, его декларация проверяется уже при сборке. Пустые placeholder
directories не считаются ready.
Выбор неполной работы для executable export вызывает ошибку с отсутствующими
входами. Начальная интеграционная поставка явно выбирает numbers lab и pilot;
остальные работы добавляются по мере готовности. Банк курса при этом сохраняется.

## R07 Архитектура локальной проверки

Один platform-owned runner обслуживает local verification и PrairieLearn.
Владелец Platform поддерживает образ на основе закреплённого официального Java
grader с JUnit, результатами PL и нужными проверками комплектности. Курс не
содержит собственного проверяющего main. Exporter создаёт декларацию job, но
не запускает Java; Download не выполняет tests.

| Уровень | Задача | Значение результата |
| --- | --- | --- |
| Системный Gradle | Быстрая разработка и public/trusted suite при авторской проверке | Полезная ранняя проверка, не доказательство PL результата |
| Host Java runner | Компиляция и тот же JUnit runner на выбранном JDK | Диагностика; обязательная сверка с контейнерным результатом |
| Контейнер | Точный production image digest, payload, JVM flags, limits, offline libraries | Авторитетная проверка grading перед staging |
| Реальная PL | Импорт, editor/upload, grader, attempts, результат | Платформенная интеграционная приёмка |

Общий платформенно нейтральный атрибут `project-check="java25-junit"` ссылается
на именованный профиль в metadata `project-checks`. Он описывает способ проверки
проекта независимо от target и course-role. PL adapter использует тот же профиль
для grading и требует его наличия при экспорте; демонстрация может подключить
проверку без включения в банк и без экспорта в PL.

Inventory включает только проекты с таким явным выбором автора, непосредственно
или через default. Наличие Java/reference/tests само по себе ничего не включает.
Manual проект без project-check не запускается. Для трёх Java-проектов Unicode
demonstration профиль объявляется явно. Повтор одного qualified ID в нескольких
работах не дублирует проверку. Есть два scope: declared — все объявленные
project-check; delivery — только вопросы текущей нативной поставки.
Нет скрытого включения placeholders в успешный отчёт: только opted-in проекты
перечисляются как incomplete, а выбранные для исполнения incomplete задачи
блокируют поставку. Текущие 177 PL placeholders получат общий явный check default
при миграции; не подключённые manual проекты остаются вне inventory.

При наличии reference у проекта в выбранном scope оно компилируется и проходит
все обязательные public и trusted проверки. Проверяются все предоставленные
эталоны в этом scope, не один выбранный
пример. При отсутствии reference обязательны компиляция starter и корректное
завершение runner с фактическими test outcomes. При наличии reference starter
также проверяется отдельно. Отсутствие reference не считается доказательством
реализуемости: отчёт явно различает checked starter и verified solution.

«Проверка заготовки прошла» означает, что compilation/job/report корректны.
Это не требует зелёных behavior tests. Public smoke/API tests могут проходить.
Отчёт показывает, что именно уже реализовано и чем объясняются баллы заготовки.
Если автор задал expected outcome, проверяется его совпадение; иначе запуск
характеризует поведение и не навязывает expected score 0.

Не устанавливать универсальный бинарный критерий. Сравнить weighted JUnit,
all-tests-pass, группы контрактов с весами и порог прохождения. Для каждой
выбранной политики отдельно определить диагностические/non-scoring tests,
максимум баллов и поведение partial solution. Не путать корректное исполнение
runner, соответствие программному контракту, реализуемость постановки и оценку.
Scoring policy фиксируется до публикации задания, а не выводится из starter.

Положительный эталон показывает реализуемость только в сочетании с проверкой
соответствия условию и достаточности тестов. Контрпримеры — пустой результат,
no-op, константа, известные ошибки границ — должны выявлять слепые зоны. Успех
правильного решения при принятии заведомо неверного не считается достаточным.

В профиле implementation участник отправляет только разрешённые исходники.
Public tests
из `student/src/test` и его build.gradle не определяют официальную оценку.
Trusted tests/runner не заменяются student классами. Student и trusted sources
компилируются отдельно; коллизии FQCN и служебных namespaces отвергаются.
Оба этапа используют Java 25, UTF-8, `-proc:none`, ограниченный вывод.
Профиль mutation явно принимает авторские тесты студента как предмет ответа:
они запускаются против доверенных correct/mutant fixtures. Это общий тип
профиля, а не обход запрета на подмену grader или выполнение student build.gradle.

| Ситуация | Обязательная классификация |
| --- | --- |
| Правильное решение | Compile clean, обязательные tests действительно выполнены и пройдены |
| Незавершённый starter | Compile clean, grader завершён, фактические failures и score объяснены |
| Отсутствующий/неверный API при компилируемом source | Предметный JUnit failure через reflection; балл по объявленной политике |
| Синтаксическая ошибка student source | Неоцениваемая submission с javac file/line/diagnostic |
| Ошибка trusted tests/runner, нулевое обнаружение trusted tests, missing engine/report | Infrastructure failure, не обычный student zero |
| Пустые/disabled submitted tests в student-tests mode | Дефект качества ответа, корректный grading result по объявленной policy |
| Student timeout/output overflow | Явная failure policy и корректное внутреннее завершение |
| Docker failure, outer timeout/OOM до корректного result | Infrastructure failure |

JUnit tests не имеют compile-time зависимости от изменяемого API. Reflection
проверяет public/static/signature и разворачивает InvocationTargetException.
Для задания на разработку тестов предоставленный API остаётся typed.
Mutation задания имеют отдельную JUnit-backed проверку: correct implementations
принимаются, обязательные mutants отвергаются. Ошибка компиляции mutant fixture
не считается обнаруженной студентом мутацией. Disabled/skipped/aborted tests
не заменяют фактическое исполнение; expected count/points проверяются.

Для numbers лабораторной оценивается только код sum/mean. Объяснение расширения
типа рекомендуется для обучения и не входит в PL submission, scoring или
результат Moodle. Удалить противоположное требование из текущих grading-notes
во время будущей миграции курса.

## R08 Фаза и интерфейс запуска

Обычный `quarto render` проверяет модель и публикацию, не запускает все Java
проекты и Docker. Быстрые команды Gradle доступны автору по требованию.
Полная executable verification — явная обязательная фаза **после генерации
delivery и до staging/import/publication**. Она проверяет именно экспортированный
payload. Scope declared дополнительно проверяет только явно подключённые
project-check вне выбранной поставки, в том числе демонстрации. Автоматического
обхода всех runnable каталогов нет.

Platform предоставляет CLI для inventory, host/container verify и сценариев
reference/starter/contracts. Он читает delivery manifest и source snapshot,
готовит стандартный `/grade`, запускает выбранный backend, проверяет result и
возвращает отдельный status проверки комплекта. Провал student tests сам по себе
не превращает исследовательский запуск starter в ошибку проверки комплекта.

CLI exit: 0 — все ожидания выполнены; 1 — обнаружен дефект комплекта/результат
не соответствует ожиданию; 2 — prerequisite или infrastructure не позволили
завершить проверку. Машиночитаемый отчёт сохраняет фактическую классификацию.
JSON содержит qualified ID, scenario, source/tests hashes, toolchain/image digest,
phases/durations, compile diagnostics, discovered/executed/passed/failed/skipped,
grader result, scoring policy и при наличии expected outcome проверку совпадения.

Кэш допустим только по полному ключу source/tests/fixtures/image/JDK/runner/limits/
policy. Релизная приёмка включает cold compilation и независимые свежие каталоги.
Параллельность ограничивается бюджетом ноутбука; staging требует successful
verification receipt с совпадающим delivery hash и grading profile.

## R09 Платформа, gateway и назначение рефератов

Platform сохраняет exact Git provenance, immutable staging, read-only course
mounts, предыдущие delivery и результаты при upgrade/restart. Builder вызывает
установленный exporter, а не дополняет ручную shell. Native sync проверяется
через реальную PL; успешное staging ещё не означает импорт.

Основная архитектура — Community PrairieLearn с gateway. Gateway реализуется
из начального main отдельным PR, принимает Moodle LTI 1.3 и возвращает результат
через AGS. Нативный Enterprise LTI PrairieLearn в этот релиз не входит.

Gateway после проверенного Moodle launch выдаёт одноразовый opaque handoff
с TTL 60 секунд. Закрытый proxy проверяет его сервером gateway, удаляет все
переданные клиентом trusted identity headers и передаёт подтверждённую identity
в Community `/pl/shibcallback`. PL сама создаёт штатную сессию. Нельзя подделывать
PL cookie или выставлять identity headers на открытом клиентском endpoint.
Использовать общий HTTPS origin и top-level navigation; devMode выключен,
Community hasShib включён, приватный PL upstream недоступен в обход proxy.

У публичного API нет необходимой операции изменения ACL. Platform владеет
узким приватным bridge, вызывающим штатные enrollment/student-label операции
и native access resolver. Gateway не пишет непосредственно в базу PL.
Оценки читаются через course-instance scoped gradebook API, затем gateway
агрегирует текущую защиту и отправляет AGS score в нужную Moodle activity.
Для pass-two-of-three нужны per-question scores/status из узкого results bridge:
aggregate gradebook score при partial credit не заменяет число полностью
выполненных required задач. Optional задачи не подменяют required pool.
Проверить этот путь на pinned Community/Moodle до production реализации;
неудача одного из двух bounded spikes блокирует только зависимые работы.

Runtime идентичности, роли, keys, enrollment, заявки и оценки остаются вне Git.
Обычный Student, без staff/dev override, получает лабораторные и только свою
защиту. Чужой assessment недоступен и по прямому URL. PL список не показывает
студенту все 60 вариантов. Серверная ACL обязательна, сокрытия ссылок недостаточно.

Режимы потока: назначение преподавателем; самостоятельный выбор с немедленным
закреплением; самостоятельный выбор с подтверждением. Прямое назначение:
unassigned → assigned → completed. Подтверждаемый выбор:
unassigned → pending → approved → completed; отказ: pending → rejected.
После отказа новый выбор создаёт новую заявку, сохраняя прежнюю запись для audit.
Approved/assigned дают доступ; pending/rejected не дают. Замена назначения
отзывает прежние права согласно явной политике.

Студент выбирает конкретную постановку реферата, а не только тему. Связь
essay → defense входит в каноническую модель; выбор человека — только runtime.
Связь задаётся один раз metadata `assessment.related-exercise: exr-essay-...`;
Core хранит relatedExercise, проверяет существование упражнения, Presentation
строит ссылку через QRC. Не вычислять её из сходства имён sec/exr и не дублировать
вручную в binding map. В одной выбранной instance одному реферату соответствует
одна активная защита. Выбор доступен только для готовых exported works.
Конкурентные заявки, квоты, retries и повторные callback обрабатываются атомарно
и идемпотентно. Потоковые настройки не вшиваются в QMD.

Moodle имеет один результат «Защита реферата» для назначенного варианта;
отдельные лабораторные сохраняют свои результаты. Required/optional и
pass-two-of-three у защит не заменяются простой суммой случайно пройденных задач.
Callback связывает пользователя, activity/context, instance, work и delivery
version; replay/дублирование/старое событие не портят оценку.

Grade события создаются серверным чтением gradebook, а не сообщением браузера
студента. AGS outbox имеет устойчивый idempotency key и сохраняется при restart.
После замены назначения старый результат остаётся в истории; прежняя Moodle
оценка сохраняется до результата новой защиты. Завершение reassign подтверждается
только после применения и проверки новых ACL, включая существующую PL сессию.

## R10 Структура курса, CI и документации

Сохранить темы в `projects/essays/<theme>/<task>` и
`projects/exercises/<theme>/<task>`. Рефераты остаются подразделом заданий.
Начальное проверяемое множество банка — 242 задачи: 60 открытых постановок,
numbers lab и 181 restricted control. Публичная demonstration остаётся вне банка.
Сравнение производится по свежему source snapshot, не только по старому HEAD.

Повторяющиеся listing заменяются штатным оглавлением и модельным указателем по
семестру/сложности. Index выводит ID/название/категории/semester/difficulty/time
из модели, не содержит вручную перечисленных 60 постановок. Указатель не меняет
порядок работ и не импортирует чужие условия через QRC.

CI-related helpers находятся в `CI/`, по примеру
[Cybersecurity PR 4](https://github.com/BSU-RFCT-Afonenko-Courses/Cybersecurity/pull/4).
`.github/workflows` сохраняется для GitHub orchestration. CI вызывает owner CLI,
не копирует бизнес-логику. Tests/reference при заданиях не перемещаются в CI.
`CI/` и журналы исключаются из публикации.

Текущий docs содержит свежие стартовую инструкцию, требования и план, позднее один
отчёт релиза. Старые планы, migration notes, дубли metadata и receipt trees
остаются в Git history; `docs/history` не создаётся. Перед удалением старых
docs сохранить ещё не закоммиченное состояние в recoverable Git snapshot.
README/UPSTREAM не ссылаются на удалённые файлы.

## R11 Интеграционная приёмка на ноутбуке

Обязательные ступени: owner tests → installed-package tests → render/privacy/
downloads → full native export → same-image local verification → PL sync/editor
→ gateway с synthetic users → настоящий локальный Moodle Student/teacher.
Ни генерация JSON, ни docker healthcheck не заменяют последнюю ступень.

Проверить correct, starter, wrong behavior, compiling wrong API, syntax error,
timeout, output flood, broken trusted test, zero tests, wrong engine, Unicode
byte preservation, repeated grading и фактические attempts. Проверить teacher
assignment и оба self-selection режима, direct URL denial и единый gradebook result.

Сайт собирается student → full → student с `--fail-if-warnings`. Проверяются
HTML/DOM, поиск, QRC, локальные href/anchors, сырые ресурсы и каждый ZIP.
Reference/private grading обычных задач не появляются в student. Полный проект
открытой demonstration доступен там намеренно. Сохранённые выводы
прошлых запусков и obsolete cached source paths не являются evidence.

Релиз завершён только после проверки опубликованных/упакованных owners и
повторения интеграции из их точных pins. Публикация docs использует штатный
`gh-pages`; курс получает целые пакеты и обновлённые provenance manifests.
Отчёт содержит реальные команды/exit codes, версии/digests, source snapshot,
active native run, hashes артефактов, результаты и ограничения.

## Исследование локального запуска

Проверенная среда ноутбука: Quarto 1.11.5; JDK 25 установлен в
`/usr/lib/jvm/java-25-openjdk`, default shell Java — 27; Docker 29.8.2,
Compose 5.6.0, 8 logical CPU, около 15 GiB RAM. Системный CUE devel не является
заявленным CUE 0.17.1; использовать проверенный exact release.

В исследовании загружен официальный image:
`docker.io/prairielearn/grader-java@sha256:dacce5e76d24cf1eaee21bec3e20b3ea40739408afdc89e6607eb542dc6b7a2e`.
Это исследовательская база, не готовый выпуск нашей платформы. Внутри Java 25.0.4
и `junit-platform-console-standalone-1.14.1.jar`; host Gradle проектов использует
JUnit 5.11.4. Версии и поведение необходимо согласовать, а не объявлять равными.

Официальный grader использует JUnit и `tests/junit`, без курсного main и Gradle
в grading job. [Контракт Java autograder](https://docs.prairielearn.com/java-grader/).
PR платформы пока использует собственный main harness. Новый runner должен
сохранить полезные ограничения platform и устранить параллельную систему тестов.

Pinned local PL executor задаёт 0.9 CPU, 2 GiB RAM/no swap, 1024 pids и outer
timeout 30 s. Inner student timeout должен успеть вернуть корректный результат;
outer job failure остаётся infrastructure error. Cold measurements определяют
внутренние бюджеты; fixed compile 5 s нельзя принимать без такого измерения.
[Исходный executor](https://github.com/PrairieLearn/PrairieLearn/blob/92584fe426ececb84bc2d09de9975c7056c0c5f6/apps/prairielearn/src/lib/externalGraderLocal.ts).

Исследовательские запуски завершены: восемь host Gradle reference/starter пар
без ошибок компиляции; эталоны проходят 81 обычный JUnit test и отдельную
mutation проверку. Выполнены 23 пробы официального контейнера; семь эталонов
проходят 58 private tests, все семь starters компилируются и оцениваются.
Numbers starter получает 0.25, pilot — 1/18, остальные — 0. Это наблюдение
weighted policy, а не навязанный критерий приёмки задания.

Выявлены две private coverage ошибки: empty examples и no-op verify получают
полный балл. Запуск без tests также принимается официальным grader как
gradable с max_points 0. Эти факты требуют исправления suites и проверки
комплектности независимо от будущего scoring. Полная таблица, границы пробы и
фазы запуска находятся в [плане](release-plan.md#исследование-локальных-проверок).
Реальный PL sync, Moodle round trip и timeout policy в этом исследовании не
проверялись; они остаются обязательными gates будущего релиза.
