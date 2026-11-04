# Учебные идентификаторы Java без версии JDK

Правило: `sec-j25-<смысл>` → `sec-<смысл>`, `exr-j25-<смысл>` → `exr-<смысл>`. Одновременно меняются определения, xrefs, metadata, sourceCard, CSS-селекторы и активный reference catalog; старых aliases в учебной выдаче не создаётся. Физические пути проектов и UUID не меняются. Буквальные старые пути в исторических свидетельствах контрактов сохраняются; pinned vendor, docs/research и сервер вне пакета.

Первичный допуск: собственный чат `01a1a1c5-b1a2-79a3-878e-dac24d8b9ae8`, путь `/root/release_identifiers`, исходный ход `01a1a1c5-b1d0-7391-be52-8e04c8dd7cf2`; ожидаемая и фактическая пара `gpt-6.1-sol/medium`. Источник: `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T17-17-30-01a1a1c5-b1a2-79a3-878e-dac24d8b9ae8.jsonl`, записи session_meta/turn_context. Проверка вложенного collaboration_mode согласована; service_tier не задан, Fast/Ultrafast не включались.

Перед заменой: 819 различных ID (788 section ID и 31 исторический exercise ID), отсутствуют коллизии с определёнными ID без j25; 1456 ключей reference catalog после преобразования остаются различными.

## Результат и проверки

Изменены 124 файла: учебные QMD, `tasks/_quarto.yml`, `_includes/semester-map.qmd`, `index.qmd`, `styles/book.css`, `lectures/assets/equality-collections.css`, `tasks/provenance/course-public-catalog.json`, строки ID в контрактах и sourceCard десяти JSON-ресурсов стартеров. Примеры: `sec-j25-theory-classes` → `sec-theory-classes`, `sec-j25-lab-objects` → `sec-lab-objects`, `exr-j25-objects-complex-values` → `exr-objects-complex-values`. Действующие exercise definitions уже были без j25; их UUID и физические проекты сохранены.

Выполнены адресные проверки (одноразовые Python-команды, новый общий validator не добавлялся):

- Исходные ID получены из tracked source вне docs/vendor через `git ls-files -z` и адресный `rg -n j25 theory tasks lectures practice handbook _includes styles index.qmd`.
- До замены проверены инъективность преобразования ID, отсутствие совпадений новых ID с уже определёнными и уникальность ключей каталога: новых коллизий 0.
- После замены точный текст каждого затронутого файла совпадает с исходным после двух объявленных замен. Все 788 section ID находят определение; состав и кратность определений сохранены. Все 1456 targets каталога сохраняют связь `namespace:id`, id и fragment; число ключей неизменно.
- Количества токенов ID у всех потребителей совпадают с исходными после того же преобразования. UUID-литералы побайтово сохранены. Все JSON с изменениями успешно разбираются.
- `git diff --check` — exit 0. Полный render, Gradle, CI, повторные inventories и продуктовые server-действия не запускались. Native smoke передан координатору для общей интеграции.

## Границы интеграции

В активных учебных QMD, metadata, CSS и reference catalog literal j25 больше нет. Осталось ровно 16 вхождений старых физических путей в 15 исторических свидетельствах: `tasks/projects/{collections,objects}/.../contract.md` и `tasks/test-support/shared/09a0048d487f/public-scenarios.md`. Они прямо относятся к снимку `220857b6` либо описывают перенос исходного ресурса; новые физические пути и aliases не создавались. Исторические docs/research и установленный vendor не правились. Ссылки опубликованного учебника меняются на актуальные anchors при общей публикации; серверные результаты/UUID не мигрировались.

Независимая рецензия и согласованный native-фрагмент остаются у координатора.

Допуск перед передачей: проверен весь исходный интервал от того же исходного хода, 1 turn_context; модель/усилие и вложенные настройки совпадают.

### Проверка невыдаваемых исторических путей

Для всех 16 остатков проверены прямые потребители. `tasks/_quarto.yml:13` исключает `**/projects/**`, строка 14 исключает `test-support/**`; корневой `_quarto.yml` также исключает tasks/projects. В учебных QMD, metadata и README нет include/link к перечисленным contract.md и `09a0048d487f/public-scenarios.md`. Их имена не входят в `course-project.build.files`, instructions, starter или components у соответствующих `_metadata.yml`. Единственное подобное включение договора — другой `queue-contract.md`, в котором j25 нет. Generic сборщик `_extensions/Afonenko-Course-Tools/course-core/artifacts/plan.ts:212` берёт объявленные starter/tree/build files и instructions, а не весь каталог проекта; `artifacts/policy.ts:26` дополнительно считает contract.md закрытым для student. Следовательно эти исторические файлы не являются учебным текстом или текущим входом student/full комплектов. Полного рендера или просмотра ZIP этим не заявлено.

Точные оставшиеся пути и строки:

- `tasks/projects/collections/balanced-brackets/contract.md`:7.
- `tasks/projects/collections/object-hash/contract.md`:7.
- `tasks/projects/collections/polynomial-map-addition/contract.md`:7.
- `tasks/projects/collections/set-union-intersection/contract.md`:7.
- `tasks/projects/collections/views-snapshots/contract.md`:7.
- `tasks/projects/objects/car-selection/contract.md`:6.
- `tasks/projects/objects/character-stack/contract.md`:6.
- `tasks/projects/objects/complex-values/contract.md`:48.
- `tasks/projects/objects/customer-registry/contract.md`:48.
- `tasks/projects/objects/patient-registry/contract.md`:48.
- `tasks/projects/objects/rational-fractions/contract.md`:48.
- `tasks/projects/objects/recursive-string-reversal/contract.md`:48.
- `tasks/projects/objects/swap-object-fields/contract.md`:6.
- `tasks/projects/objects/variadic-sum/contract.md`:6.
- `tasks/test-support/shared/09a0048d487f/public-scenarios.md`:263, 297.

Адресный commit: `ffe0ccfb` — «Убрать версию JDK из учебных идентификаторов и ссылок», 124 файла, 4144 добавленных/удалённых строк. Сторонние документы не включены. Push/PR не выполнялись.

## Исправление независимого обзора: P2

Механическая замена затронула 21 невыдаваемое историческое свидетельство, ссылающееся на commit `220857b6deb3c98064ebc4da6409d9d3ba76e563`: 5 collection contracts, 4 control subject contracts и 12 number contracts. Старые `exr-j25-*` восстановлены в точных исторических строках; отдельные текущие ID `exr-*` в этих файлах и актуальные учебные anchors/ссылки сохранены. Все 21 прежних ID адресно подтверждены через `git show 220857b6…:tasks/bank/{collections,control,numbers}.qmd`. Это исправляет недостоверное описание прошлого снимка; UUID/оценивание не затронуты.

Первоначальные числа остатков выше описывают состояние до P2: после исправления добавились 21 историческое упоминание ID. Эти закрытые контракты по-прежнему не входят в текущие учебные страницы или объявленный состав комплектов.

**Пакет пока не принят по P1:** сохранность UUID-литералов не доказывает сохранение вычисляемых UUID пяти assessment. `tasks/labs/{objects,polymorphism,collections,pipelines,files}.qmd` меняют `assessment.id` `sec-j25-lab-<тема>` → `sec-lab-<тема>`; установленный PL export-course.ts:679 вычисляет `identity("assessment/" + work.id, courseUuid)`. Значит без отдельной поддержки прежнего стабильного UUID изменится внешняя идентичность. Поддержку явного стабильного UUID и интеграционную проверку разрабатывает координатор у владельца PL. Здесь обходы, aliases и серверная миграция не выполняются.

Адресная проверка исправления: только 21 историческая строка возвращена к точным исходным байтам, все прежние ID найдены в закреплённом снимке; `git diff --check` — exit 0. Новые broad searches/tests/render не выполнялись.

P2 commit: `cac1418fee97020ab95dba3c84fe7f24773101f9` — 21 файл, 21 историческая строка. Полный допуск повторно проверен от исходного хода: 2 turn_context, совпадение gpt-6.1-sol/medium и вложенных настроек.
