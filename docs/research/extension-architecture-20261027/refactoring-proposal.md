# Предложение рефакторинга

Предлагается уменьшить число авторских источников истины и независимых внутренних протоколов. Начинать с замены всего toolchain либо массового удаления guards оснований нет. [Карта требований/evidence/gates](proposal-traceability.md) связывает рекомендации с проверками. Ниже — последующий продуктовый план; реализация, releases, staging и server import в исследование не входят.

## 1. Понятный путь автора и студента

**Владельцы: Java и Core; граница выдачи — PrairieLearn.** Ввести компактный авторский рецепт задачи: semantic ID, каноническое условие, форма ответа, действительно нужный starter/API/resources, grading contract. Состав лабораторной и choice slots остаётся авторским решением. Повторные filenames/mappings, wrapper metadata, package archive и delivery JSON должны выводиться там, где их значение однозначно. Это больше, чем перенос огромного YAML в sidecar.

Root, theory, lectures, practice, handbook и tasks сохраняют разные назначения. Точные копии условия/API и повторные примеры сводятся к канону только после проверки consumers и include-relative paths. 13 handbook ZIP сейчас совпадают с исходниками; обвинять их в устаревании нельзя. Противоречивые Maven/Gradle/build.sh инструкции GradeApp требуют одного поддерживаемого workflow. Teacher/reference остаётся в переносимом закрытом root, привязанном к exercise IDs. IDE edit/compare/export требует явного обратного переноса; материализация сама его не обеспечивает.

Для короткой задачи основная страница содержит условие, ограничения, пример и непосредственное поле решения. ZIP/README становятся optional local kit, когда они полезны. Для нескольких файлов, packages, data/JAR и свободных имён проектный режим сохраняется. Выбор поля определяется учебным контрактом, а не наличием любого starter ZIP. Квалификация free-name/fixed API/UI — разные проверки.

Имя скачивания: **`<lab-or-group-id>--<exercise-id>.zip`**, если смысловая группа есть, иначе `<exercise-id>.zip`. Не использовать `starter-1` или технический `sec-unit-*` как педагогическую группу. Когда один exercise входит в несколько работ, contextual download name принадлежит месту выдачи; внутренний deduplicated payload остаётся идентичен. Проверить безопасные имена, повторные скачивания и сохранность question identity/history. Ни одно опубликованное имя сейчас не менялось.

Вместо 97 одинаковых Homework entries показать 10 лабораторных с порядком, выбором и связями, отдельно тренировку и резервные подборки. Все 10 labs в Native существуют; непонятная витрина не доказывает отсутствие экспортированных labs. 87 unit works не обязаны превращаться в новые обязательные лабораторные. Не менять веса/completion при перегруппировке.

## 2. Баллы и feedback

**Владельцы: Java teaching policy, PrairieLearn presentation, Platform grading.** Хранить математическую политику отдельно от способа вывода. При трёх равных задачах auto budget 70 даёт 70/3; замена трёх значений на 23.33 даёт 69.99, а 24/23/23 нарушает равенство. Первый вариант — точный расчёт и форматирование UI до разумной точности с согласованным итогом 70/100. Если upstream UI не позволяет это без смены данных, исследовать отдельную шкалу сырых баллов и явное нормирование; это другая продуктовая миграция, не косметическое округление.

Приёмка: 0/частичный/полный результат, choice slots, optional weight 0, auto + manual = 100, best attempt, отсутствие двойного начисления, infra failure отдельно от wrong answer. Сверить принятую политику неограниченных попыток с attempts:3. Реальную Moodle/PL UI и gradebook надо проверить отдельно после нового разрешения на серверный сценарий.

## 3. System Practice: результат, среда и первичные проверки

**Владельцы: System Practice, PrairieLearn exporter, Platform.** Переписать каждое условие по схеме «цель → исходное состояние → действие → наблюдаемый результат → способ проверки». Файлы показывать по назначению; убрать необходимость открывать несколько служебных ссылок, чтобы понять задачу. Подробный план по семи задачам и уже существующим checker scripts — [system-practice.md](system-practice.md).

Предложить два пути: своя система и native PL Workspace с VS Code, Git, shell и файлами. Один валидатор проверяет итоговые данные; оболочки доставки различаются. Файловые задачи проверяются по содержимому/состоянию; Git — по графу, blobs, refs и требуемой операции восстановления, а не списку скопированных команд. Существующие локальные скрипты нужно разделить по новым tasks, а не подключать как монолит.

Workspace сохраняет рабочую область, но grader получает только явно выбранные артефакты. `.git` нельзя считать автоматически включённым; вариант — проверяемый Git bundle/явно захваченный repository state. Реальный GitHub SSH требует внешней identity/network проверки и не подменяется локальным bare remote. Linux Workspace не доказывает Windows/PowerShell результат. Current host readiness неизвестна. Нужен один отдельный пилот с persistence, reset/save/submit, feedback и ресурсными лимитами; сервер здесь не настраивался.

## 4. Ранний контракт вместо позднего чужого JSON

**Владельцы: Core и каждый consumer.** Выделить малые versioned DTO/facade: native project facts, выбранный public Body/resources, materialized artifacts, grading descriptor и capability declaration. Producer отвечает за свою гарантию; consumer проверяет собственный вход. Bundle version, API version, schema и Pandoc AST version не должны подменять друг друга.

Сначала tiny actual installed producer→consumer fixture с добавленным допустимым response field, отсутствующей capability, неподдерживаемой schema, digit-leading instance и optional primary union. Простой producer double не ловит decoder drift. Закрытые поля не становятся допустимыми ради additive compatibility. Print/Moodle/Cloud имеют объявленные legacy границы; их миграция к Core 8.3 — отдельная работа, не новая регрессия Java.

## 5. Меньше повторной работы внутри доказанного lifetime

**Владельцы: Core → Download/Publisher/QRC/PL → Performance.** Начать с точек, где вход не меняется: повторная validation Body в Print/Moodle; непосредственный повтор DTO hash в grading_job; индексы вместо линейных поисков и повторных больших representation в Core; per-export unique-question preparation до membership packing при сохранении conflict guard.

Отдельно сравнить plain page без ненужного materialization proof, узкий native facts DTO вместо document subprocess и accepted model между CLI-фазами. Уже существующие batch extraction, regular row reuse, immutable snapshots, QRC parsedPages и stock PL collected не «внедрять» повторно. Active Performance сейчас меняет Core reuse/cache ветви; сначала разделить instrumentation overhead и изменение workload. Все пробы — один механизм, малый Java, matched outputs/privacy/links, новая запись в общей очереди.

Постоянный cache не нужен, пока не исчерпан фазовый reuse. Bounded jobs полной композиции обсуждать после карты shared outputs и RAM. Сокращение полезной функции ради красивого времени недопустимо.

## 6. Выпуск и recovery

**Владельцы: Platform и интеграция курса.** Показать раздельные состояния source release, assembled runtime, render/export, semantic qualification, admission и server activation. Compatibility tuple позволяет обновлять host отдельно от OCI. Exact pins остаются воспроизводимым свидетельством одной композиции, а не запретом проверить новый совместимый producer.

До дорогого запуска проверять actual decoder, canonical selection, сохранность модели/common inputs и destination conflicts. Для повторной попытки документировать input closure и уже завершённую фазу; не выдавать существующий файл за пригодный checkpoint. Закрытие старого долга требует новых real results и нового решения, а не продления истёкшей policy. Полный путь — [release-and-recovery.md](release-and-recovery.md).

## Порядок и критерии перехода

| Шаг | Минимальный результат | Gate перед следующим шагом |
| --- | --- | --- |
| P1: авторский/UI договор | 3 разных Java задачи + 1 System task, понятная страница, optional kit, contextual names | Сохранены condition/API/resources, semantic IDs, equal points; преподаватель может выполнить типовую правку из одного канона |
| P2: actual contracts | Tiny producer→consumer matrix на выбранных source tuples | Негативные случаи действительно меняют relevant input; правильный ранний отказ, privacy и legacy limits явны |
| P3: фазовое упрощение | Один механизм на малом Java, paired direct/off отдельно от product variant | Эквивалентные outputs и границы времени; mutation controls отвергают stale результат; без SLA из воздуха |
| P4: System Workspace pilot | Один сценарий local + workspace с общим checker | Persistence, gradedFiles/Git state, feedback, reset и sandbox проверены; host setup — отдельное разрешённое действие |
| P5: recovery/qualification | Новый точный tuple и реальные reference/starter/negative results | Новая строгая receipt, явно закрытый/оставшийся долг, сохранена история. Большой курс только с отдельным обоснованием |

Предложение готово для выбора направлений. Измеренные секунды ускорения этих изменений отсутствуют: исследование не делало продуктовую реализацию. Неисполненные equivalence/scaling/qualification опыты остаются видимыми, а не превращаются в обещанный выигрыш.
