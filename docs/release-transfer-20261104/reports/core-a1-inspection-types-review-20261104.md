# Независимый review Core A1 inspection typing

Вердикт: **APPROVE** локального candidate от `5a28314fc8b704ef7171f33063789d7cc8ec8e79`. Блокирующих findings не найдено. Это типизация существующих consumed JSON boundaries с отдельно согласованными controlled malformed refusals; не новая Native schema и не повтор Native integration acceptance.

## Scope и независимость

Checkout `/home/tolya/JavaCodex/java-release-20261110/core-native-types`:3production modules (`infrastructure/native-owner-inspection.ts`, `infrastructure/project-context.ts`, `runtime/operation.ts`) и2existing controls. Новый pure helper79lines учтён отдельно от tracked diff; unchanged accepted bridge worktree не редактировалась. Reviewer прочёл AGENTS, [writer report](core-a1-inspection-types-20261104.md), полный production/test diff и [checks JSON](core-a1-inspection-types-checks-20261104.json). Source reviewer не писал/не менял; tests/compiler/Native/render/inspect/hash/network не повторялись. DB/state/pins/production/commit/push не затрагивались.

## Проверенные границы

Пути ниже относительно `_extensions/course-core/`, строки candidate.

| Предмет | Source evidence | Вывод |
| --- | --- | --- |
| Общий consumed owner subset | `native-owner-inspection.ts:9–55`: проверены record owner, dir, supported quarto.version, record config, optional files/config:string[]; spread сохраняет остальной payload/config/quarto/files | A1 не получает нового обязательного files.input. Native extension bags и вложенные значения остаются unknown, не закрываются allowlist/schema и не пересоздаются из обрезанной модели |
| Strong operation contract | helper `:57–79`: input:string[] проверяется до общего decoder; `operation.ts:7–10` импортирует/reexports прежние имена | Required files.input и порядок input→config paths→version/dir/config сохранились. Operation callers не переведены на более слабый optional-input тип; существующий import path остаётся работоспособным |
| Нет нового cycle/IO | Новый helper импортирует только contracts/project и quarto-version; оба pure schema/diagnostic зависимости, без обратного импорта operation/project-context | Runtime→infrastructure→contracts не создаёт infrastructure→operation cycle. Нет повторного Native read, файловых/hash checks или stringify-reparse для типизации |
| External unknown до guard | `project-context.ts:118–138`, `:578–590`: inspect возвращает Promise<unknown>, затем общий owner decoder | Нет cast неизвестного inspect JSON в ProjectContext/NativeOwner DTO. На A1 boundary CoreContractError remap в существующий CONTEXT_RUNTIME; tool/JSON parser errors не маскируются этим catch |
| Document live shapes | `project-context.ts:665–688`: record live/project/quarto, same disposable dir/version, record formats/selected format/metadata | Shape narrowing стоит до индексирования. Primitive/array metadata и malformed format получают existing CONTEXT_OWNER/FORMAT. Проверка не объявляет неизвестные metadata значения предметно валидированными |
| Metadata сохранность | `:678–681` клонируется весь выбранный metadata record; `:719` весь owner config; обе группы ownerMetadata стали Record<string,unknown> | Произвольные custom keys/вложенные JSON сохраняются. Существующий ProjectContext/contract parser не заменён новой закрытой схемой. Точечный Source поиск PL/QRC не выявил дополнительных production ownerMetadata consumers, которым требовалась бы фасадная any совместимость |
| Origins | `project-context.ts:401–480`: unknown effective/ids сужаются прежними record/shape/key/field checks; Map lookup только numeric marker ID | Не придуманы sourceFile/resolvedPath/evidence; не пропущен scalar-marker proof. Ненумерический marker раньше не соответствовал numeric declaration Map и теперь также отказывает |
| Lifetime/roles/acquisition | Изменения не затрагивают copyOwner, current acquisition grouping, WeakMap private State/generation, role/physical identity, callbacks/closing | Никакого обхода inherited authority через новый typed DTO. Accepted actual post-child bridge остаётся тем же по механизму |

Новые runtime отказы ограничены заранее согласованными malformed shapes на реально потребляемых полях. Это не blanket запрет Native engines, custom metadata или отсутствие unused owner.files.input. Особые A1 metadata-file(s)/aliases/format origins остаются прежним unsupported остатком; типизация не объявляет их поддержанными.

## Evidence и пределы

Сохранённый writer evidence:

- meaningful RED до edits: truthy primitive owner config прошёл прежнюю A1 проверку и дал consumer assertion вместо CONTEXT_RUNTIME; exit1/0.267s. Это конкретное отсутствие controlled refusal.
- Existing marker-isolation: PASS0.452s.6malformed shapes получили нужные existing diagnostics; positive valid/custom metadata cases сохранили origins/bytes, отсутствие unused input, private hardlink refusal, generation/clone/expired guards и один multiformat acquisition. Это actual Core logic с **Native payload double**, не новые Native children.
- Existing assignment-owner-boundary: PASS0.354s после переноса сильного decoder, включая computed engines и owner/projection/input/config/runtime/output checks.
- Единственный affected bundled compiler: PASS7entrypoints/3.220s с deny-import/cached actual map. `native-context-a1` только скомпилирован после честного narrowing ownerMetadata.project, actual Native сценарий не запускался.

Reviewer ничего из этого не повторял. Исходный actual knitrPNG→README/ZIP run на5a не присвоен новой дельте; для этой небольшой boundary дельты сохранённые shape controls + Source trace достаточны, автоматическое назначение новой Native матрицы не требуется. Existing test any/Command double не выданы за production DTO. YAML-node `tree(n:any)` и inherited generic adapter остаются за границей этого scope; максимальная типизация всей Core не заявляется.

После compiler writer Source не менялся по ledger; дополнительных casts/fake origins/closed schemas в финальном diff не найдено. Реальные выбранные Native данные, target/UUID/scoring и роль студентов этим diff не меняются.

## Собственный допуск и решение

Reviewer `/root/pl_typed_materialization`, primary chat `01a1a25f-b098-7242-bf6d-d0e8f6517361`. Полный origin interval от `01a1a25f-b0fb-7fd2-b244-83933af77834` до current `01a1a546-5715-7411-a34a-380621e9b3eb`:18turn_context, line8→2732; top/nested везде gpt-6.1-sol/high. Standard назначен, hidden service tier логом не подтверждается. Globals/настройки не менялись; полный environment/secrets не печатались.

**APPROVE Source candidate.** Новых findings, требующих исправления или исполнения, нет. Commit/backup/CI/production решения остаются root.
