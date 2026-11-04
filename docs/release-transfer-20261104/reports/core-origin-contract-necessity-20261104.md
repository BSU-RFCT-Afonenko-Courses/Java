# Core: необходимость declared origin и возможность Native contract

**Вывод:** exact YAML pin не является единственным следующим путём. В Core5f нет непосредственного предметного consumer, которому нужны winning YAML filename, UTF-16 range, lexical raw и `native-scalar-marker` для каждого scalar. Нужны **выбранные physical paths, их роль/audience и корректная база разрешения**. Однако нынешний договор намеренно разрешает часть унаследованных paths относительно выбранного code-project, а не файла определения. Это реальная семантика выбора starter/tests/solutions. Заменить все origins на Native effective strings одним удалением parser нельзя: для этой части выбор файлов изменится.

Это Source-only dependency analysis после прямого разрешения менять собственные breaking contracts. Прочитаны [node-types](core-yaml-node-types-20261104.md), [vendor follow-up](core-yaml-provenance-followup-20261104.md), адресные строки Core [pattern map](pattern-pass-core-20261104.md) и [hash producer map](hash-mechanism-producer-review.md), parse/rewrite и непосредственные origin consumers. Новый поиск версии vendor не выполнялся.

Источник conclusions — `/home/tolya/JavaCodex/java-release-20261110/core-native-bridge`, exact HEAD `5f4590aa4eb524830715f8020c8ff3c504197f73`. Первоначально прочитанная соседняя `core` имеет HEAD `46b35909c9878196294f54b6be2d7505b336fe8d`; её более поздний/иной carrier маршрут в conclusions **не используется**. Relevant direct-consumer diff между этими refs проверен: отличие в assignment roles добавляет source physical identities; точные references ниже относятся к5f. У5f `operationProjectContextReuse:911–953` вызывает тот же общий acquisition с owner copy, а `contractsOnly` только убирает `effectiveMetadata` из view после acquisition.

## Что реально потребляется

Пути ниже относительно exact5f `_extensions/course-core`.

| Consumer | Реально читаемые origin facts | Предметное назначение |
| --- | --- | --- |
| `artifacts/plan.ts:197–239,263–268` | `origins[field].resolvedPath` | Выбрать starter tree, build file sources, выбранные testsets/components, full solution trees и instruction fragments. `raw/sourceFile/range/evidence` не читаются. |
| `artifacts/project.ts:326–335` | `instructions.<kind>.path.baseKind`, resolved path уже хранится в plan | Записать тип базы instruction fragment в materialization closure. BaseKind задан правилом поля, а не обнаруживается YAML source range. Links/resources разбираются относительно фактического source fragment (`300–305`). |
| `runtime/assignment-roles.ts:165–193` | field name + `resolvedPath` | Starter/build/instructions публичны; solutions закрыты; testsets/components публичны по student selection. Предотвратить попадание private resources в student Body. |
| `runtime/assignment-roles.ts:194–216` → `project-context.ts:849–860` | physical `{path,identity}` captured files, **не YAML declaration origin** | Выявить physical alias публичного и закрытого пути; predicate43–110 не заменяется успешным Native inspect. Это отдельный filesystem/privacy fact. |
| Сам `project-context.ts:438–484` | `raw`, declaring `sourceFile`, field kind | Построить `resolvedPath`, error location и relevant source set. `range` только записывается в DTO; в перечисленных production consumers не читается. |

Настоящий потребитель declared role существует; consumer полного CST provenance не найден в этом прямом Core graph. Public export `NativeOrigin` и schema сами по себе — собственный опубликованный договор, а не доказательство необходимости его каждого поля. Старые tests `native-context-a1.ts:128–130` и negative profile91–93 требуют filename; это ожидания существующего API, которые разрешённый breaking slice вправе заменить. Они не являются новым предметным требованием пользователя.

## Где origin влияет на выбор файлов

`collectOrigins:438–448` назначает две базы:

- `testsets.*.path`, `starter.path`, `solutions.*.path`, `instructions.local.path`: `dirname(input) + raw` (`project`).
- `components.*.path`, `build.files.*`, `instructions.gradle.path`: `dirname(declaring sourceFile) + raw` (`definition`).

Native effective value сохраняется отдельно (`479`), но path selection использует **raw**. Accepted Source control `tests/native-context-a1.ts:37–44,78–86` фиксирует пример: inherited `projects/_metadata.yml` задаёт `testsets.demo.path: tests/demo`; inputs `projects/a/input.qmd` и `projects/b/input.qmd` должны выбрать соответственно `projects/a/tests/demo` и `projects/b/tests/demo`. Это выбранная Core предметная семантика reusable local testsets. Для components control106–121 одновременно ожидает declaration-relative selection и native rebased effective `../../src`.

Следовательно, для definition-relative части native rebased effective path и input base могут дать нужный physical source без winning declaration. Для project-relative inherited части effective path уже имеет другую базу; восстановить исходную lexical строку в общем случае из одного effective string нельзя. Ни basename, ни удаление `../`, ни выбор одного config filename из `files.config` не являются честной обратной функцией Native merge/rebase. Форматные contributors/profiles усиливают эту неоднозначность.

## Какие гарантии добавили мы сами

`instrument:311–399` повторно парсит YAML/QMD front matter, требует отсутствие всех parser errors/warnings, индексирует каждый scalar всего namespace, запрещает alias/anchor/tag и arrays, добавляет параллельное дерево ID. `collectOrigins:401–437` требует полного совпадения деревьев и однозначного winning declaration для каждого leaf, включая booleans selection/requires. Затем provenance сохраняется в `NativeOriginSchema:116–127`.

Для Native boolean merge, выбора named components/testsets и strict subject shape не нужен filename каждого bool. Эти guarantees созданы для собственной origin qualification:

- YAML warnings/aliases/anchors/tags отклоняются из-за недоказанной origin связи; downstream plan не принимает решений по синтаксису alias. Это не основание обещать поддержку любого YAML: действуют Native parser и предметный `ProjectContractSchema`.
- Namespace arrays могут быть недопустимы как subject shape по `contracts/project.ts:64–91`, но второе требование «каждый YAML node обязан быть scalar-map» шире этой схемы.
- `metadata-files` и format-specific namespace отклоняются (`338–348`) потому, что их origin transport не квалифицирован, а не потому, что selection schema принципиально не может принять effective результат Native.
- Marker collision и точное равенство marker shape защищают наш служебный transport. После удаления marker transport самостоятельной subject семантики у этих проверок нет.
- UTF-16 ranges и filename полезны для диагностики, но не требуются непосредственным production consumers. Их можно честно убрать из обязательного результата, не подменяя фиктивными offsets/filename.
- Whole-owner copy и same-source signature/membership fences обеспечивают дополнительную coherence guarantee. Их наличие не доказывает, что Native требует копировать все соседние проекты ради selected physical paths. Это согласуется с прежним hash/pattern review, но filesystem containment/privacy и physical alias selection от этой coherence гарантии отличны.

Существующая qualification не является самостоятельным YAML merge engine: победившие ID и effective values всё ещё объединяет Native. Это существенный положительный факт; удалять её нужно вместе с созданной ею семантической зависимостью, а не заменять вторым собственным resolver.

## Что можно передать Native и чего его результата не хватает

Effective `course-project` уже подходит для dependencies, delivery/verification boolean maps, named selection, overrides и предметного decode. Для source paths с **принятой Native rebasing семантикой** можно передать дальнейшим consumers resolved effective paths и field-derived role/base kind. Тогда raw/sourceFile/range/evidence и scalar marker tree перестают быть необходимой authority; alias/format/metadata-file квалификационные запреты могут исчезнуть вместе с parser. Unknown JSON по-прежнему проходит существующий subject decoder; TS-типы не заменяют эту boundary.

Native inspect не даёт в используемой API проекции winning scalar declaration filename/range/raw syntax. Он также не сообщает Core намерение «это inherited path, но надо трактовать его локально к каждому code-project», public/private selected role, отсутствие physical hardlink alias с private file или lifetime текущего Core callback. Последние правила остаются нашими предметными/ownership обязанностями и не требуют собственного YAML CST.

Отказ от parser сам по себе не доказывает возможность немедленно убрать owner copy. На5f copy (`140–180,592–640`) используется ещё для synthetic отсутствующего input (`654–658`), технического default-project/render selector (`264–309,641–669`) и изоляции authored project configuration. `probeConfiguration` удаляет authored `project.render` из временной копии. В этом разрешённом Source slice нет подтверждения, что обычный `inspect` original owner обслуживает отсутствующий или исключённый book input с теми же profile/format/engine facts. Поэтому «прямой original Native inspect без копии работает для всех current requests» здесь UNKNOWN; обещать его нельзя.

## Минимальный следующий согласованный срез

Следующая работа должна выбрать **path semantics**, а не искать типы ради сохранения transport:

1. Предпочтительный чистый договор: Native effective contract + явно разрешённые physical paths/roles, без обязательного declaration provenance. Для current project-relative inherited полей сначала перенести authoring declarations так, чтобы Native effective путь выбирал **те же реальные starter/tests/solutions/instructions**; либо отдельно принять изменение этой конкретной семантики. Разрешение менять contracts не разрешает молча выбирать чужой testset или включать закрытые solutions в student.
2. Согласованный Core slice: `contracts/project.ts`, `infrastructure/project-context.ts`, `artifacts/plan.ts`, `artifacts/project.ts`, `runtime/assignment-roles.ts`; их direct public type exports и адресные native-context/role expectations. Paths/baseKind передавать из Native effective + field rule, а роли продолжать выводить из selection. Не сохранять старый `NativeOrigin` с fake sourceFile/evidence. Не вводить новый proof/AST validator/registry.
3. Отдельно от origin transport доказать способ acquisition для **реально нужных inputs**: существующий authored input против synthetic/excluded input. Если direct Native acquisition для выбранного входа достаточен, убрать связанную с ним YAML rewrite/copy ветку. Если current synthetic request нуждается в техническом файле, сначала сменить этот request contract на реально существующий Native input либо сохранить ровно необходимую техническую ветку; все-owner copy не объявлять автоматически неизбежной.
4. Privacy path containment, selected student/full partition, actual file existence и physical alias checks перенести вместе с physical-path producer. `projectContextSourceIdentities` сейчас зависит от copy inventory; его буквальное удаление без replacement actual selected filesystem facts оставит hole в `assignmentRolePredicate`. Новый all-tree freeze взамен не требуется. Broad same-S/hash redesign остаётся отдельным согласованным scope из прежних maps.

Если требуется сохранить **всю нынешнюю** project-relative inheritance семантику и обязательный declaring-source provenance, origin transport пока необходим и exact vendor pin/declarations остаются честной задачей. Если принимается Native path-base contract с сохранением выбранных физических inputs посредством migration, существование старого origin API и negative tests больше не является причиной продолжать YAML pin archaeology. Это два разных scope, их нельзя смешать обещанием type-only cleanup.

Никаких Source edits, compiler/runtime/Native/network/DB/production действий, новых агентов, hashes, budget resets или выполнения failed recovery не было. Worktrees, student data/results/UUID сохранены. Mainstate/audit принадлежат root и не менялись. [Собственный admission](core-origin-contract-necessity-20261104-admission.json): отдельный исходный turn этой роли `01a1a622-ed16-7513-9d69-44353c884ed7`, собственный chat `01a1a61b-3db5-75b2-9e4c-eb949da79c4f`, primary top/nested `gpt-6.1-sol/high` PASS; Standard назначен, globals не менялись.
