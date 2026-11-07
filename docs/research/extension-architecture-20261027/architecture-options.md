# Архитектурные варианты

Предпочтителен последовательный путь: сначала понятная авторская модель и ранние границы совместимости, затем сокращение работы внутри неизменённой фазы. Новый build engine, resolver, постоянный cache или общий runtime расширений сейчас не обоснованы. Измеренного глобального оптимума нет.

Основание: [требования и инварианты](high-level-requirements.md), [авторская модель](authoring-model.md), [контракты](contracts.md), [выпуск и восстановление](release-and-recovery.md), [новые пожелания пользователя](user-requirements-addendum.md). Полный статус 88 исходных вопросов — [hypothesis-decisions.md](hypothesis-decisions.md).

## Неизменяемые свойства сравнения

Сохраняются шесть учебных форм Java; student/teacher разделение; semantic IDs и история оценок; native crossrefs, QRC, resources, Reveal и поиск; свободное решение и fixed API как разные учебные контракты; точные public/closed grading inputs; политика equal required / 70 auto + 30 manual / optional weight 0; объяснимые ошибки инфраструктуры; проверяемая композиция независимых компонентов. Удобство IDE не разрешает публиковать teacher материалы. Краткая задача не обязана требовать ZIP, README и загрузку файла.

## Сравнение

| Вариант | Что меняется | Ожидаемый эффект и доказанность | Цена и условие принятия |
| --- | --- | --- | --- |
| V0. Текущая модель, локальные упрощения | Убрать выводимые defaults, повторные реестры и точные копии; исправлять инструкции из одного канона | Расхождения и многоместность подтверждены source inventory. Время правки/сборки после изменения не измерено | Небольшая миграция. Сохранить исключения и native include path semantics; не объединять разные учебные формы по теме |
| V1. Возможности включаются по потребности | Plain page не строит materialization proof без consumer; export/qualification имеют отдельные entrypoints | Plain hooked path и per-document bridge подтверждены. Секундный выигрыш отключения не измерен | Нужна явная activation matrix. Сохранить failure на неожиданной декларации, а не молча пропускать ресурс/teacher guard |
| V2. Native facts и immutable контекст одной фазы | Передавать уже принятый project/document/model DTO с явным lifetime; indexes вместо повторных линейных поисков | Batch native extraction, regular owner reuse и immutable snapshot уже существуют. Оставшиеся повторные reads/inspect и O(D·N)/O(D²) места подтверждены статически | Не обещать простую замену pre/post: native render может менять facts. Проверить A/use/B и mutation controls. Начать с одного producer и одного consumer |
| V3. Экспорт из отдельной сборочной области | Авторские файлы не мутируются; profiles/source view и outputs изолированы | Owned overlay действительно порождает guards/recovery. Эквивалентность альтернативы ещё не доказана | Include/resources/native profile hook arrays и relative paths требуют fixture. Не вводить самописный YAML merger; disposable copy может стоить больше I/O |
| V4. Малые публичные контракты | Owner-owned facade/DTO вместо foreign nested private JSON; ранний actual decoder gate | Исторические allowlist/schema/ID отказы и current dynamic imports подтверждены | Один владелец каждой гарантии; request/response compatibility направлены по-разному. Не генерировать всю семантику CUE в TS одним обещанием |
| V5. Фазовый reuse до постоянного cache | Переиспользовать уже проверенные bytes/parsed HTML/model в одном immutable интервале | QRC parsedPages и PL collected уже переиспользуются; нельзя продавать их повторное внедрение. Есть точечные дубли validations/DTO hashes | Bounded lifetime проще invalidation. Не удалять freshness на границе другого процесса и не переносить receipt на изменённый tuple |
| V6. Bounded jobs полной композиции | Параллельно только независимые children/qualification scenarios после карты общих outputs | Serial loops подтверждены. Throughput/RAM/contention и выигрыш не измерены | Не лечит тяжёлый selected child и лишнюю работу. Сначала устранить повтор, затем подобрать budget на малом fixture; не запускать все ядра по умолчанию |
| V7. Внешний build engine / новый resolver / постоянный cache | Новый DAG, invalidation, locking и orchestration | Потребность не доказана: native hooks/profiles и небольшие API ещё не исчерпаны | Самая высокая поддержка и новый trust boundary. Отклонён как стартовый шаг; Ninja — сравнительный образец, не выбранная зависимость |
| V8. Workspace для System Practice | Native PL VS Code/terminal с тем же валидатором результата, что для своей системы | Возможность upstream существует. Current exporter не производит workspace seam; host readiness неизвестна | Image, persistence, gradedFiles, .git transport, sandbox/network и доступность требуют отдельного ограниченного пилота. Cloud adapter — другой продукт |

## Выбор

V0 + V4 — первые кандидаты: уменьшают число авторских решений и поздних отказов без обещания ускорения. V1/V2/V5 — следующие узкие технические пробы. V3 сравнивается с ними на одной fixture до выбора. V6 допускается после карты зависимостей. V7 не выбран. V8 относится к пользовательскому сценарию System Practice и не требует переносить весь Java в IDE-контейнеры.

Для UI Java выбирается capability-based путь: редактор для подходящего короткого ответа; optional local kit; upload/Workspace для многофайлового решения или самостоятельной структуры проекта. Свободное имя класса нельзя искусственно превращать в Main.java ради удобства редактора. Рецепт заготовки остаётся source, ZIP — выдачей; имя скачивания включает смысловой group/lab ID и exercise ID.

Текущие измерения описывают baseline и harness. Пять пар direct/off не являются сравнением V0–V8. Полная малая композиция прошла; это функциональная приёмка fixture, а не доказательство ускорения полного Java. Неисполненные различающие опыты остаются в одной очереди.
