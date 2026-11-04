# Жизнеспособность Native boundary Core

4 ноября 2026. Source-only исследование завершено. Pending Core candidate не менялся и не принят. Исполнений и приёмки Native композиции этим отчётом нет.

Собственный первичный допуск: PASS. Агент `/root/core_owner_review`, чат `01a1a502-5273-7260-99ab-937cf055bf6a`; origin turn `01a1a502-52a2-7aa0-9b60-c9b0739e19cd` сохранён. Primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-22-35-01a1a502-5273-7260-99ab-937cf055bf6a.jsonl`: session_meta1, весь interval turn_context8/193, top/nested `gpt-6.1-sol/high` согласованы. Standard назначен; скрытая серверная маршрутизация не удостоверяется.


## Решение для root

**Не продолжать candidate A построением полного metadata-dependency freezer. В Native Quarto 1.11.5 нет экспортируемого полного набора selected metadata dependencies и absence facts. Для bank/site принять уже существующий actual Native AST/context за источник содержания; для code-project A1 использовать одно actual acquisition после завершённого child, переиспользуя существующий Native inspect + scalar-origin machinery.** Это смысловой отказ от pre-child same-S metadata expectations, а не оптимизация прежней гарантии.

Реальный минимальный мост к обычным cells/cache существует, но он сохраняет дорогую текущую A1 copy/inspect реализацию и её ограничения. Он не обещает полную Native parity, поддержку произвольных `metadata-file(s)` в A1 или ускорение. Вариант «убрать engine gate и оставить текущий generation reuse как есть» недостаточен: baseline cache/output rows всё равно читаются с прежними signatures.

## Источники и неизменность candidate

Прочитаны `core-owner-boundary-next-20261104.md`, `core-owner-boundary-implementation-20261104.md`, собственный `core-owner-boundary-review-20261104.md`, `quarto-cache-boundary-20261104.md` и `quarto-execution-boundary-20261104.md`, applicable Core AGENTS/spec index/языковая политика, actual Core/PL Source и локальные `/opt/quarto/bin/quarto.js`, `/opt/quarto/share/pandoc/datadir/init.lua`. Quarto ref — установленный 1.11.5 Source, уже установленный в предыдущих evidence, без нового вызова version/inspect. Core candidate остаётся против `46b35909c9878196294f54b6be2d7505b336fe8d`; те же шесть dirty paths видны при чтении статуса. Предыдущий writer не считается продолжающим работу.

Ниже Core paths относительно `_extensions/course-core/`; Quarto строки относительно `/opt/quarto/bin/quarto.js`. Source lines относятся к текущему pending candidate, поэтому отличаются от stable46b reports. Чисел wall/performance не добавлено. Вся новая запись — этот отчёт.

## Что Native действительно отдаёт

| Actual источник | Что доступно | Чего нет и следствие |
| --- | --- | --- |
| Project `inspect`: Quarto `164793–164845`, `populateFileInformation` | `config`, project `files`, engines, extension information; для каждого input — `includeMap`, `codeCells`, `metadata` | `projectFileMetadata:77082–77091` читает YAML из Markdown input; это frontmatter plane, не полный effective per-format context и не dependency graph |
| Document `inspect`: `164847–164930` | Полные effective `formats`, input resources, fileInformation, project config | Нет scalar sourceFile/range для произвольного namespace, нет полного списка directory/document metadata reads или negative facts |
| `directoryMetadataForInputFile:76984–77019` | Native ищет `_metadata.yml`, затем `.yaml` по фактическому ancestor пути и сливает прочитанное | `.find(existsSync)` не экспортирует absence. Из merged value нельзя восстановить, какой отсутствовавший candidate нужно было бы удерживать отсутствующим |
| `includedMetadata:19784–19814` и `resolveFormatsFromMetadata:91240–91256` | Native разрешает `metadata-file(s)` и читает YAML. Внутри функции есть `files: yamlFiles` | Document format resolver использует `included.metadata`, а список `included.files` не помещает в inspect output. Project resolver `92477–92512` отдельно добавляет project-level files в config list; это не покрывает document/directory contributions |
| `resolveFormats:91442–91503` | Project, directory и input metadata разрешаются Native отдельно, затем сливаются в format | Из project config / frontmatter / includeMap нельзя получить уже готовую полную closed metadata closure |
| Filter dependency JSONL | `initFilterParams:105070–105080` задаёт `QUARTO_FILTER_DEPENDENCY_FILE`; `share/pandoc/datadir/init.lua:342–375,947–963` пишет явные resources/supporting/file declarations для downstream copying | Это не журнал всех read/open и не metadata provenance/absence manifest; использовать как полный graph было бы ложным |

Native внутренний `FileInformationCacheMap` (`quarto.js:77328+`) кеширует значения по filename; это тоже не dependency/absence API. Новый repeat inspect дал бы свежий effective результат, но сам по себе не создал бы полного списка отсутствующих кандидатов. Поэтому «повторить inspect, затем считать frozen metadata доказанным» не решает поставленную задачу.

Existing Native paths зависят от контекста и существования файлов. `toInputRelativePaths:76858–76916` рекурсивно корректирует строки при `existsSync(baseDir/value)`; `projectMetadataForInputFile:91625–91635` применяет это к project metadata. Это не универсальная карта scalar origins. Нельзя просто приписать `sourceFile` по догадке или заменить каждый A1 raw path на effective string и сохранить прежний смысл.

## Bank/site: actual результат уже authoritative

`native-ts/early-context.lua:6–48` берёт из настоящего `doc.meta` роль exercise-bank, projection и effective exercise defaults; input/native format сопоставляются с текущей операцией. Эти поля не копируются из pre-child inspect как содержание. `early-filter.ts:44–59` строит проверенный контекст именно из actual native format/role/defaults и связывает его с request; `:63–79` квалифицирует actual producer и нормализует AST. `document/assignment-producer.ts:25–40` требует наблюдённый engine string, но не требует `markdown`.

`document/normalize.ts:386–391` собирает release facts из actual AST до privacy projection; `:467–482` сверяет actual assessment.id с назначенным intent. `release/collect.ts:74+` декодирует native metadata, а `document/project-capture.ts:13–92` строит project facts из того же `EarlyDocument`. `native-ts/late-filter.lua` сохраняет фактически видимые occurrences/body/role/resources. `runtime/operation.ts:1605–1637` проверяет capture witness, owner/input/profiles/projection/format/role и финализирует этот early+late результат. Это настоящие существующие consumer boundaries, которым не нужен наш metadata-file resolver.

Следовательно новый ancestor `_metadata` либо изменённый document metadata-file до actual render может легитимно изменить actual native metadata этого render. В предлагаемой семантике это не нужно запрещать как отличие от pre-child baseline: содержание берётся из actual AST. Два прежних P2 остаются справедливы к обещанию candidate A «selected metadata неизменны»; новый дизайн должен прямо отказаться от этого обещания, а не назвать неполную closure полной. Fixed input selection, exact assessment intent и actual projection/ownership/capture validation остаются.

Чтобы computed результат дошёл сюда, нужно убрать собственный static engine/codeCells/OJS admission (`operation.ts:1053–1102`) и blanket owner source equality на render/current boundaries, **включая general**, а не только поменять course key `static` на `general`. Не добавлять новый engine whitelist/source-policy флаг. Native freeze/cache остаются авторским Native выбором; result currentness означает результат текущего render с выбранным reuse. Native output, private pointer/state, runtime/owner, lifecycle, actual resources и role checks продолжают быть обязательными.

Это не даёт произвольным browser widgets переносимости в README/PL и не даёт taint analysis произвольного авторского кода. Сохранённый cross-profile freeze пример из cache report остаётся существенным: Native cache не разделён автоматически по audience. Нового общего cache/proof gate этот отчёт не предлагает.

## Почему bank AST не заменяет A1 автоматически

PL `application/native-course.ts:26–58` уже открывает **одну** `withProjectContracts` batch на все реальные project roots/formats, запрашивая `<project>/native-input.qmd`. Это технический input, обычно отсутствующий среди rendered bank QMD. Actual bank release/project facts содержат project root, artifact policy, check/materialization metadata; они не содержат все effective `course-project` starter/testset/solution/build origins code-project metadata.

A1 `contracts/project.ts:116–135` обещает actual `raw`, `effective`, `sourceFile`, optional range, baseKind/resolvedPath и literal `native-scalar-marker`. Это не декоративные поля:

- `artifacts/plan.ts:197–239` получает actual starter/build/testset/solution paths через `context.origins[field].resolvedPath`.
- `runtime/assignment-roles.ts:143–201` проверяет owner/profiles/format/generation и строит публичные/закрытые роли именно из этих paths; inode identity закрывает hardlink alias.
- `artifacts/project.ts:324–338` использует baseKind для instruction/resource closure.

Existing `project-context.ts:305–479` инструментирует scalar declarations и разрешает их Native inspect, затем связывает реальные origins. Raw declaration semantics различает project-relative starter/testsets/solutions и definition-relative build/instructions. Например Java `tasks/projects/collections/balanced-brackets/_metadata.yml` имеет starter/reference, shared components и `../../common/java/settings.gradle`; SP launch-counter metadata имеет shared `../../../common/...`. Замена этих путей на придуманный root или на непроверенные effective строки могла бы изменить выдаваемые файлы/роли.

Нельзя cast actual doc.meta в `ProjectContext`, создать fabricated `native-scalar-marker`, убрать generation check или превратить provenance DTO в authority. Полный A1 отказ от scalar origin договора — отдельная API/consumer migration, а не нужное условие обычных cells.

## Текущий generation reuse и actual post-child acquisition

| Свойство | Нынешний `operationProjectContextReuse` | Минимальный post-child acquisition |
| --- | --- | --- |
| Откуда owner/config/rows | `operation.ts:2929–2931`: pre-child witness, initial rows/memberships | Existing `readProjectContexts:583–602` получает actual owner inspect и `copyOwner` текущего owner после завершения engine child |
| Native context | Existing qualified carrier либо fallback instrument+inspect | Тот же existing instrument+Native inspect; не новый resolver и не отдельный execution runner |
| Cache lifetime | `project-context.ts:869–890` byte/contract/probe caches на reuse closure; key `:971–980` profiles+inputs/formats+phase | Один acquisition на весь already grouped operation batch; immutable captured values/bytes переиспользуются внутри этой batch, README/body/ZIP не получают каждый новый inspect |
| Mutable workspace | Fallback `:1203–1218` читает **каждый initial row**; `operation.ts:2955–2967` сравнивает старую signature. Cache write может отказать ещё до selected project reads | Existing copy/inspect наблюдают owner уже после engine writes. Старые cache signatures не являются условием допуска этого нового context |
| Lifetime/roles | Bound generation/active callback | Сохраняются private operation generation + active native A1 callback, actual scalar origins и captured physical identity; не standalone DTO authority |
| Цена | Carrier иногда избегает copy; general fallback всё равно full copy | Один actual owner inspect + current owner copy + Native probes по нужным project inputs/formats. Больше работы на eligible carrier route; wall UNKNOWN, ускорение не заявляется |

**Это узкий compatibility bridge, а не готовый вызов public standalone API из PL.** `assertProjectContextGeneration:815–825` сейчас требует `state.bound.generation`. Existing `readProjectContexts` отдаёт собственные active states и в finally их инвалидирует (`:723–729`). Нужен внутренний способ присоединить эту же actual acquired State к текущей operation generation без подмены её readers на старые pre-child rows. Внешние callers не должны получать право произвольно attach context. Owner/profiles/format coverage и exact allowed project set в `operation.ts:2834–2880` сохраняются.

Также standalone helper сейчас запрещает duplicate owner requests (`:542–562`), а current operation reuse уже группирует несколько formats одного owner. Поэтому буквальная замена вызова на `readProjectContexts(requests, ...)` недостаточна. Минимально переиспользовать его acquisition/copy/instrument machinery **внутри существующего operation A1 batch**, один owner copy, существующий formats loop (`:1264–1315`); не делать по копии на format/consumer и не дублировать resolver.

Generation cache не должен молча смешивать старую pre-child metadata с новой mutable acquisition. Для первого моста достаточно acquisition на один live all-project batch, который уже существует в PL. Не нужен persistent cache, новый content key или invalidation framework. Если caller потом открывает отдельную batch, это новый actual context, а не гарантированно тот же старый source state; API не должен обещать прежнюю filesystem equality. Lifetime/failed state по-прежнему проверяются.

## Минимальный production scope и настоящие остатки

Рекомендуемый следующий implementation scope — **четыре существующих Core production modules**, с проверкой фактического diff перед расширением:

1. `runtime/assignment-admission.ts`: owner inspect задаёт scheduling, profiles/runtime и exact intent; убрать попытку frozen metadata closure из нового helper до/после prepare. Mandatory real selected-source provenance сохранять честной: пока exported sourceRows/PL raw comparison остаются, реально выбранный QMD нельзя молча заменить другим input при том же SHA.
2. `runtime/operation.ts`: убрать engine/static gate и blanket full-owner source admission на Native render/current маршруте; actual AST/capture/identity вместо frozen metadata expectations. Перенаправить existing project batch на post-child acquired context; сохранить actual runtime/private/output/lifecycle/selected resource проверки. Для artifact raw-source bytes оставить существующий честный coupling, а не объявлять SHA source всем metadata.
3. `infrastructure/project-context.ts`: переиспользовать existing current acquisition/copy/instrument + multi-format Native probes в operation-owned batch; присоединить private generation к actual State, сохранить copied bytes и actual origin/lifetime semantics. Не строить closure parser и не добавлять eligibility policy. Existing fast carrier можно рассматривать позднее как оптимизацию actual acquisition, но не выдавать pre-child cache за актуальную metadata.
4. `runtime/assignment-roles.ts`: роль и physical alias facts должны относиться к **той actual acquired A1 source State**, из которой создан план, а не только к initial pre-child rows. Уже полученные Deno identity facts можно передать внутренним typed view без нового filesystem scanner. Иначе новые/заменённые hardlink роли будут сопоставлены с устаревшим inode list. Public unknown/private role refusal сохраняется.

`source-snapshot.ts` может остаться producer реальных transitional initial rows/SHA и existing selected physical/read guards; новый dependency freezer в нём не нужен. Если реализация вынуждена менять его интерфейс для selected raw-source guards, это адресное пятое место, которое надо назвать до обещания четырёхфайлового scope. Документация/существующие controls должны отражать новую authority semantics. Это предложение области реализации, не уже проверенный diff и не обещание уложиться в три старых production файла.

В scope не требуется повторный context/inspect для каждой condition, body, README или ZIP: current PL `withNativeProjectPlans` уже держит все actual formats, student/full plans и callback до выдачи; Core materials/readers используют тот же private context. Сохраняются existing per-plan materialization cache и копии inventory bytes. Совпадение group/request order, exact all-project coverage и failed callback являются реальными consumer checks, их не удалять ради оптимизации.

Остатки и пределы:

- Existing actual A1 acquisition всё ещё full owner copy и full checks в `readProjectContexts:723–725`; это унаследованная собственная цена в короткой post-render batch, а не новый freeze всей сборки. Она может отказать при конкурентной модификации owner во время самой A1 batch и при unrelated symlink/special entry (`copyOwner:141–177`). Это не универсальная поддержка любого custom mutable cache layout. Новый scoped immutability договор вместо неё не предлагается; дальнейшее снятие этой стоимости — отдельная конкретная consumer задача.
- A1 `instrument:328–329` по-прежнему отвергает `metadata-file(s)`; aliases/tags/некоторые format origins тоже ограничены. **Post-child acquisition не объявляет эту функцию поддержанной.** В обычном bank/site actual Native AST такие metadata могут быть разрешены самим Quarto без нашего resolver; code-project A1 имеет более узкую существующую поддержку.
- Initial whole-owner snapshot/hash остаётся transitional cost. Native source/operation schema migration для полного удаления rows/SHA этим мостом не выполнена. До root решения нет чисел ускорения или performance acceptance.
- `assignment-provenance.ts:157–177` экспортирует real initial sourceRows. PL `native-provenance.ts:43–73` использует selected source SHA; `native-check-inventory.ts:63–69` сравнивает **actual rawSource Artifact bytes** с этим SHA. Поэтому native-generated figure вне code project уже может идти через existing observed resource corridor, а произвольная новая/перезаписанная cell-generated starter/test source не становится автоматически совместимой с PL raw-source contract. Не обходить его фиктивным SHA или новой receipt.
- Физические facts для alias должны быть свежими относительно выбранных bytes/contexts. Same-process произвольный авторский код не является taint-sandbox; current capture/output/role boundaries не доказывают отсутствие секрета в обычном вычисленном Para/PNG. Audience-dependent Native freeze reuse остаётся авторской Native настройкой, а не поводом возвращать blanket source immutability.

**Root может назначить bounded implementation этого моста с явным legacy A1 cost, либо сначала принять только bank/site Native corridor. Называть pending A «готовой поддержкой cells/caches» и commit/publish его сейчас оснований нет.** Production/data/UID/UUID/targets/goals/баллы не менялись. Новых tests, render/inspect, cache writes/cleanups, hash/build/compiler, сети, установок и зависимостей не было.

Перед передачей перечитан весь собственный primary interval от того же origin turn: session_meta1, все turn_context строки 8,193. Top/nested gpt-6.1-sol/high согласованы; origin не сдвигался, globals не менялись.
