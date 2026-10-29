# Контракты, типы и ранняя совместимость T11

Номер bundle, Core API, Body schema, private capture format, Pandoc AST API, runtime registry и OCI identity обозначают разные гарантии. Выбранный Core8.3 не является продолжением всех изменений8.2.8. Наличие функции с тем же именем не доказывает одинаковую реализацию; отсутствие коммита в ancestry также не доказывает отсутствие независимо реализованной capability.

## Реальные границы

| Producer → consumer | Передаваемый объект | Фактическая проверка и недостаток |
| --- | --- | --- |
| Core → PrairieLearn native.lua | Active pointer/witness/source-context/common refs | Foreign closed reader знает private shape. commonPathDocuments уже поддержан в выбранном8.3; старый additive отказ исправлен. Риск следующей эволюции остаётся |
| Core → Publisher | NativeRun documents/adapters/fragments, Release | Dynamic imports и `any`; Map↔array сериализация. Exact pins воспроизводят пару, но не дают публичного типового контракта |
| Core → Download | Artifact resolver/native batch, selected bytes/receipts | Есть batch и guarded fallback. Lifetime bound operation важнее одного наличия метода; direct helper и bound resolver отличаются |
| Core Body → QRC | Public links, references, catalog resolution | Lazy dynamic API позволяет узнать несовместимость после capture. Нужен маленький installed decoder/renderer fixture до дорогой операции |
| Core8.3 Bodyv2 → Print/Moodle | Works со slots против legacy works.items/assignments | Выбранные Print/Moodle0.3.1 явно сохраняют Bodyv1 и Core4 fixtures. Новая пара не объявлена совместимой; это известный предел, не новый Java regression |
| Core8.3 → Cloud3.0.1 | Pointerv2/v3 против active pointerv1 | Cloud сохраняет legacy contract; VM/actions не исполняет. PrairieLearn Workspaces — другая функция |
| Exporter → Platform | Deliveryv3, grading descriptor/job/results, optional primary/ZIP/API | Selected runtime2.4.0 исправляет исторический embedded feedback/publicCases drift; host2.4.1 отдельно исправляет digit-leading instance. Schema PASS не равно reference execution |
| Course/Native → host admission | Exact source/payload/catalog/runtime tuple и receipt/policy | Strict verification и exceptional NOT_VERIFIED материально различаются. Hash human/private report не доказывает содержание решения без чтения |

## Типы и predicates

`any`, casts и dynamic imports обходят compile-time связи, однако не делают каждый runtime guard лишним. TS string/number обычно шире CUE/Lua grammar/range. В Core time, API version arrays и slot IDs имеют разные представления; vocabulary генерация синхронизирует enums, а не всю семантику. Нужен направленный набор accepted/rejected примеров для действительного reader, а не только тест producer schema.

Для запроса consumer задаёт требуемые поля/capabilities; для ответа producer может добавлять данные. Текущие foreign exact-key predicates иногда превращают additive response в breaking change. Предложение — owner-owned маленький DTO или facade с явной версией/возможностями. Это не разрешение принимать любой неизвестный private field: privacy, state/lifetime и разрешённые resource origins проверяются на соответствующей границе.

Optional-primary false сохраняет свободные Java имена и пути и сейчас требует upload. Legacy absent/true, непустой starter inventory, fixed API и публичные verification sources — независимые контракты. Редактор для фиксированного Main уже существует; редактор с выбором файлов/пакетов требует нового узкого adapter контракта. Workspace graded files также ещё не выражены в exporter upload/editor DTO.

## Перенос проверки в раннюю фазу

1. До render: input selection/canonical identities, required files/common placement, supported DTO/capabilities и shape конфигурации.
2. До export/compile: destination collisions, writer/format возможность, API/public/closed schema acceptance на минимальном установленном consumer.
3. На границе операции: currentness тех inputs, которые реально могли измениться, и bounded artifact lifetime; не переносить старый captured PASS на новый source.
4. Перед admission: actual complete verification receipt либо отдельно согласованное новое исключение. Исследование исключения не создаёт.

Фазовый typed context уменьшает обмен private структурами; один generated schema source может согласовать structural формы, но не автоматически докажет все Lua/CUE/TS predicates. Универсальная общая runtime-система и замена native metadata resolver не предлагаются.

Свидетельства: Core owner reports, Platform owner reports, [lineage](source-selection.md), [хронология](sections/release-history.md), [авторская модель](authoring-model.md). Runtime observations и неизвестные связи отражены отдельно в ledgers/coverage; статические выводы не переименованы в выполненные тесты.
