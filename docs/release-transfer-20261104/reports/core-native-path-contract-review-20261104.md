# Core Native paths: независимый Source review

**APPROVE_DESIGN для замороженного ограниченного предложения**, не Source implementation/runtime qualification. Автор proposal — perf_event_types, reviewer — pl_typed_materialization; Source этой задачи reviewer не пишет. Исходный полный primary Sol6.1/high interval сохранён. Прочитаны necessity report, адресные path producer/plan/role consumers, native-context-a1/negative/roles и связанные instruction fragments. Ни compiler/tests/Native/render/inspect/hash/build/network/DB/commits/новых агентов не было. Пять сохранённых Source commits не пересматриваются.

## Самая важная поправка: Native rebasing условен

В уже установленном pinned Quarto `/opt/quarto/bin/quarto.js:76858–76915` функция `toInputRelativePaths` рекурсивно преобразует относительную строку **только если** `existsSync(join(baseDir,value))`; иначе оставляет исходное значение. `directoryMetadataForInputFile:76984–77018` использует declaring directory как baseDir и input directory как inputDir; projectMetadataForInputFile:91625–91634 использует project.dir. Это прямое Source основание, не новый Native опыт и не поиск vendor version.

Поэтому нельзя говорить «все definition-relative effective строки всегда эквивалентны» или «любой inherited project-relative path обязательно изменится». Нужны конкретные physical случаи ниже. Early сообщение reviewer автору об обязательном shared testset было уточнено после чтения этого actual алгоритма.

| Случай | Текущий Core | Native effective + input base | Вывод |
| --- | --- | --- | --- |
| `projects/_metadata.yml`: testsets.demo.path=tests/demo; inputs projects/a/input.qmd и projects/b/input.qmd; есть только a/tests/demo и b/tests/demo | raw+dirname(input) выбирает два локальных testset | Declaring-base projects/tests/demo не существует: Native может оставить tests/demo, то есть выбрать те же локальные пути | Existing native-context-a1 сам по себе **не доказывает расхождение** |
| Тот же inherited testset, но существует также shared/decoy projects/tests/demo | Всё ещё два локальных testset | Native rebases существующий declaring-base path и выбирает shared projects/tests/demo | Реальный Source counterexample. При принятии Native semantics авторская migration обязана закрепить прежние physical a/b sets leaf declarations/overrides; нельзя молча выбрать decoy |
| Definition-relative component/build/instructions.gradle с отсутствующим declaring-base path, но существующим одноимённым input-local path | collectOrigins raw+declaringDir отказывает ORIGIN_MISSING | Native может оставить строку и выбрать input-local path | Это смена accepted semantics, а не доказанная эквивалентность. Для intended existing paths, которые Native действительно rebased, достаточно input-relative effective value без declaring filename |

## Какие поля требуют осознанной authored migration

`collectOrigins` infrastructure/project-context.ts:438–448 назначает project base для **starter.path, testsets.*.path, solutions.*.path, instructions.local.path**; остальные path fields, включая components.*.path, build.files.* и instructions.gradle.path, имеют definition base. Эти четыре inherited project-relative группы нельзя автоматически трактовать как declaration-relative: можно заменить starter, закрытую solution или локальную инструкцию, сохранив формально допустимый JSON.

Безусловно достаточный для существующего физического набора вариант — объявления нужных local paths в leaf metadata того же input directory (либо явные Native paths, физический смысл которых установлен); никакого восстановления lexical raw из effective string, basename trimming или поиска «победившего файла» не нужно. Но Source обзор не перечисляет и не мигрирует все реальные курсы. Пример same-dir leaf declaration не доказывает работоспособность acquisition отсутствующего input.

Для components/root/parent/local existing native-context-a1:106–121 показывает ожидаемые root/src, projects/src, projects/a/src и native effective ../../src; это положительный конкретный пример. Target `build.gradle.kts` — имя конечного файла, а не metadata path: map key должен сохраниться буквально при удалении origin lookup.

## Роли и instruction links не равны YAML provenance

artifacts/plan.ts:197–239,263–268 потребляет выбранные resolved paths; runtime/assignment-roles.ts:165–193 классифицирует starter/build/instructions как public, solutions как private, testsets/components по student selection. lexical YAML range/sourceFile/evidence для этого не нужны. Но физические paths, selected role и actual alias identities нужны: assignmentRolePredicate:43–110 отдельно запрещает пересечение public/private и alias закрытого файла. Нельзя удалить identities вместе с copy и считать Native inspect заменой privacy.

Instruction fragments читаются по фактическому выбранному source; относительные ссылки/resources вычисляются от dirname(fragment source) (artifacts/project.ts:290–305). При сохранении того же physical shared/gradle.md и projects/a/README.local.md должны сохраниться соответствующие shared/guide.txt и projects/a/data.txt. `baseKind` дополнительно сериализуется :326–335 и объявлен в artifacts/contracts.ts:210. Если provenance API удаляется, этот consumer/schema/его direct readers должны быть согласованы; не подставлять фиктивный definition/sourceFile/evidence. Это не причина вводить новый resolver.

## Acquisition — отдельная граница

native-context-a1 имеет book chapters=[index.qmd], но запрашивает **несуществующие** projects/a/input.qmd и projects/b/input.qmd; в конце отдельно требует отсутствие этих файлов в original owner. Current project-context.ts:641–669 создаёт технические input/profile только в disposable copy, снимает authored render selection и вызывает реальный Native inspect. Следовательно plain direct inspect original input не является Source-доказанной заменой: отсутствующий QMD и excluded book input — конкретная нерешённая часть. Тот же dirname для synthetic native-input.qmd помогает path arithmetic, но не доказывает Native project/engine/format acquisition.

Разумная граница первого Source среза: убрать CST marker/declaration authority, потреблять реальные Native effective path fields, согласовать actual typed path/role/plan/closure consumers; оставить необходимый existing acquisition/copy механизм, если direct synthetic/excluded acquisition ещё не установлена. Не добавлять fresh copy proof/registry/новый YAML fallback. Удаление whole-owner copy и гарантий currentness не должно происходить молча в том же path contract change.

## Delta-review замороженного proposal

Прочитан final reports/core-native-path-contract-proposal-20261104.md автора perf_event_types, exact baseline f90713ff3480d5c77deccef4979e98e09279b099. Существенных незакрытых design blockers не найдено:

- Conditional exists-sensitive Native факт исправлен; differentiator теперь shared/decoy directory, а не ложное расхождение уже существующего A1 fixture. Missing-definition/local-decoy эквивалентность явно не обещана.
- Четыре project-relative inherited класса мигрируют path declarations в same-directory leaf metadata. Общие selection/dependency boolean maps остаются. Реальные Java/SP примеры названы примерами, не полным scan/qualification. Definition-relative paths тоже требуют explicit authored target там, где нужна строгая прежняя physical цель.
- physicalPaths становится Core-owned typed builder result, а не новым proof; unknown metadata декодирует существующий ProjectContractSchema. План/роли/targets остаются предметными consumers actual normalized paths. Новый lexical resolver/fake NativeOrigin/sourceFile/evidence не предложен.
- Включён artifacts/contracts.ts и удаление MaterializationClosure.baseKind; actual fragment source и его link/resource base сохраняются. Адресный поиск actual runtime/PL application/Download consumers не обнаружил скрытого обращения к origins/baseKind вне описанного Core graph.
- Existing synthetic/excluded acquisition, copy, scoped source identities и технический probeConfiguration CST остаются честно названным остатком. Удаление origin CST transport не объявлено удалением всех parser/copy dependencies. Приватность/alias/lifetime не выведены из Native merge.

Граница приёмки следующего Source среза — одинаковые выбранные normalized physical roots/files/targets/roles и instruction resource bases для мигрированных fixtures, включая decoy differentiator; не filename/offset/hash/counter-only expectations. Это design criterion, не назначенный здесь новый тест/матрица и не разрешение менять реальные курсы или student data. Количество Native calls/ускорение и direct original acquisition остаются UNKNOWN. В этой рецензии product Source не менялся и runs отсутствуют.
