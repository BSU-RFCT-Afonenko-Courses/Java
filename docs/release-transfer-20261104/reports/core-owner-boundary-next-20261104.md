# Первый согласованный шаг от whole-owner same-S Core

4 ноября 2026. Технический план по конкретной consumer chain, без новой реализации или запуска. Источники: Core local `46b35909c9878196294f54b6be2d7505b336fe8d`, PL exact `30edc03295e508f174c64b399f35b14ad37488fa`, Download `fcff6ad94c66b61948c44cf8a2c080f8758abfd9`. Идущая PL medium партия удаления двух unused digest не редактируется и не включается молча в этот baseline.

## Допуск и граница

Агент `/root/review_pl_legacy_types`, chat `01a1a348-784d-7771-a540-bbcf7ed8735e`, parent `01a1a142-7adc-7371-9765-4871d807dfcd`. Исходный turn полного собственного интервала `01a1a348-7879-7070-a59e-6a29186a8ad2`; текущий turn `01a1a4f0-68f3-74c0-bd3f-c617bfcf0dbc`. Primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T00-19-58-01a1a348-784d-7771-a540-bbcf7ed8735e.jsonl`: session_meta1, все turn_context8/133/170/243/306/488/541 подтверждают top и nested `gpt-6.1-sol/high`. Standard назначен, hidden tier не удостоверяется, baseline не сдвигался, globals не менялись.

Прежний `pattern-pass-core-20261104.md` не повторяется: ниже читаются только нужные producer/consumer callsites. Source/tests/state не редактируются; compiler/validation execution/tests/build/render/inspect/hash/network/commit/push не выполняются. Единственная запись — этот отчёт.

## Выбран первый шаг

**Сначала снять blanket whole-owner source admission на существующем native static/contract маршруте: unrelated файл/каталог больше не должен аннулировать выпуск.** Существующие sourceRows и Artifact payload digest пока оставить реальными наблюдениями для совместимости consumers; перестать трактовать весь rows/memberships как условие допуска на каждой closing/current operation границе. Это смысловое изменение — прежде запрещённый unrelated edit теперь разрешён, а не прежняя гарантия с более быстрым cache.

Первый пакет не объявляет удалёнными initial whole-owner scan/hash, все selected-byte checks либо fallback A1 whole-copy. Он снимает конкретную общую гарантию на ограниченном реальном маршруте. Полное удаление sourceRows/SHA из всех DTO — второй связанный пакет с большим consumer impact, рассмотренный ниже и не выбранный первым. Однострочное `return` в assertSources ни один вариант не описывает.

Реальный предел: PL30ed предпочитает `operation.withProjectContracts` (`native-course.ts:36–38`), однако его narrow fast path имеет условия, а при несовпадении `project-context.ts:1203–1218` проверяет и копирует **все** rows. Первый пакет должен явно оставить этот fallback legacy-ограничением либо расшириться отдельным решением. Нельзя обещать снятие whole-owner refusal для fallback только после замены outer full fence на local. Нового flag/режима/eligibility validator для обхода fallback не предлагается.

## Actual consumer chain: SHA-поля имеют разные роли

Пути Core относительно `_extensions/course-core`, PL — `_extensions/course-prairielearn`, Download — `_extensions/project-download` указанных exact refs. PL читался через `git show/grep 30ed…`, чтобы не читать промежуточный medium diff. Core HEAD отдельно прочитан как46b35909c9878196294f54b6be2d7505b336fe8d. Download использован какfcff по назначению; live/installed комбинация этим планом не подтверждается.

| Поле/операция | Настоящий producer→consumer | Роль и последствия удаления |
| --- | --- | --- |
| `SourceSnapshotState.rows{path,signature,sha256}`, `memberships` | `runtime/source-snapshot.ts:10–25,94–133`; `operation.ts` передаёт rows/memberships A1 reuse, role selection и assignment provenance | Initial SHA каждого неисключённого файла. Последующие `assertSources` сравнивают metadata/membership, а не повторный SHA всего S. Rows одновременно служат captured file discovery и inode/path role facts; SHA не нужен этим двум функциям. Удаление целого rows уничтожит не только equality |
| `assertSources full ? state.rows : selected rows` + equality всех selected ancestor memberships | `source-snapshot.ts:155–276`; подготовка `assignment-admission.ts:203,237`, child/post/seal/current operation `operation.ts:1311,1408,1938,2136,2274,2463` | Это собственно blanket admission: прежний full отклоняет unrelated edits, local всё ещё отклоняет unrelated addition/removal в общем предке. Нет lower-layer требования неизменности всего owner. Для первого шага удалить именно эти all-owner equality ветви, а не lifecycle/containment |
| `NativeAssignmentProvenance.sourceRows` | schema `assignment-provenance.ts:31–33`; builder `:164–177`; `operation.assignmentProvenance():3197–3202`; PL `native-provenance.ts:31–50,85–90` | Mandatory public DTO из initial observations. PL проверяет key uniqueness/safe path и наличие captured QMD. Это не permission читать произвольный source; но удалить поле producer без PL consumer нельзя. Первый шаг сохраняет значения, явно перестаёт обещать whole-owner currentness по этому списку |
| PL `sourceRows` raw-copy comparison | PL `native-course.ts:291–292,464–475`→`native-materialize.ts:163,202–208`→`native-check-projection.ts:28,46–50`→`native-check-inventory.ts:44–66` | Принимается лишь actual ArtifactFile с rawSource, size и bytes hash, совпадающим с baseline row для **реально выбранного** файла. Это selected raw-copy equality, не blanket проверка всех rows. Unrelated entry не материализуется и не сравнивается с actual FS этим PL path |
| PL selected QMD hash / checks hashes | `native-provenance.ts:73` sourceSha256; `native-course.ts:502–519` selected expectedSources→`sources{source,sha256}`→selectionScope/nativeChecksManifest; `native-check-inventory.ts:129,138–149` | Selected source hash также участвует в checks DTO/selectionScope hashes. Отдельные `sourceSnapshotHash`/legacy `inventoryHash` попадают в export (`export-course.ts:792–793`). Их нельзя восстанавливать фиктивными SHA после удаления Core sourceRows. Это совместимость checks домена, не обоснование whole-owner запрета |
| `ArtifactFile{bytes,size,sha256,target,audience,reason,rawSource?}` | Core `artifacts/project.ts:406–435` хеширует **материализованные bytes**; mandatory schema `artifacts/contracts.ts:111–132`; PL `native-selection.ts:38–54`, `native-materialize.ts:223–228`; Download packageArtifact | Это payload digest, его значение не берётся из whole-owner SourceSnapshot. Можно снять blanket source equality, не меняя эти fields. В дальнейшем SHA не обязателен ZIP как формату: это наш admission/cache/provenance договор; для его удаления нужен отдельный согласованный payload DTO change |
| `Core.writeInventory` | `artifacts/project.ts:887–920` ещё раз проверяет bytes/size/SHA перед записью | Public exported consumer готового Artifact DTO. Не обнаружен как обязательный путь PL→Download; не выдаётся за доказанный горячий runtime. Удаление Artifact sha поля должно обновить его тоже, даже если нынешний PL его не вызывает |
| Download `packageArtifact` | `application/package-artifact.ts:9–38,63–80`: clone→actual Core schema→SHA bytes compare→zip `{name,bytes}` | Не читает whole-owner sourceRows/SourceSnapshot, не ищет source files. SHA — его собственный input consistency gate, а ZIP фактически потребляет names/bytes. Первый выбранный шаг Download production не меняет. Не удалять target/audience/prefix/path checks под названием SHA |
| Download ordinary/native materialization and archives | `infrastructure/runtime.ts:486–508` Core.materializeProject/Condition→packageArtifact; `:514–523` resourceInventory→names/bytes; `:545–568` digest архива в receipt | Core sourceRows не требуются Download; archive hash и ownership request hash — иные локальные couplings, их не смешивать с SourceSnapshot. Первый шаг не затрагивает выдаваемые файлы/ZIP/receipts |

Root уточнил активную PL medium партию: удаляются **только** `nativeProjectWitnessHash` и `nativeVerificationInventory.inventoryHash`. Никакого нового плана на их использование нет. Она не удаляет sourceRows, sourceSnapshotHash, legacy inventoryHash, scenariosHash или verificationInventoryHash. Отчёт30ed содержит эти orphan digests исторически; план не требует их вернуть.

## Почему выбран именно этот первый пакет

**Вариант A, выбран:** прекратить общее условие «любой row/membership owner обязан совпасть»; сохранить real observed row data и payload SHA, поскольку PL/Download уже потребляют их в ограниченных выбранных местах. Это меняет accepted behavior, снимает метаданные полного дерева на consumer boundaries и не заставляет менять всю public Artifact wire schema/ZIP цепочку.

**Вариант B, отложен:** сразу убрать SourceSnapshot rows.sha256/sourceRows и SHA из public Artifact DTO, затем PL origin/checks hashes и Download package checks. Он действительно убрал бы initial hashing/повтор payload hashing, но одновременно требует как минимум Core SourceSnapshot/state/provenance/Artifact/project/resource DTO consumers, PL provenance/course/materialize/check-projection/check-inventory/selection и Download package-artifact. Он также меняет persisted checks/public inventory signatures и совместимость published Core/Download pins. Называть это «одна Core строка» было бы ложью. Для него сначала нужен отдельный coordinated payload/release change; не новый hash/receipt framework.

Вариант A не защищает текущий механизм от будущего удаления: initial whole-owner hash остаётся **переходной ценой mandatory observed-data DTO**, а не нормативно нужной гарантией. После снятия admission следующий вопрос — оставить только действительно потребляемые source observations, а не придумывать вечный cache целого дерева.

## Конкретный bounded Source scope варианта A

Ориентир — три Core production файла и относящиеся существующие contract/test/doc места. Это техническое предложение; автор перед реализацией фиксирует точный file scope и независимый review. PL/Download production изменения в этом первом шаге не требуются.

1. **`runtime/source-snapshot.ts`: прекратить implicit full-owner и ancestor-membership equality как допуск.** Внутренний source assertion должен получать конкретный набор semantic dependency paths, а не выбирать `state.rows` по full boolean. Убрать ветви обхода всех rows/всех memberships и сравнения полного списка детей общих предков, включая обслуживавшие это равенство `allowed additions/emptyNativeDirectories` исключения. Для переданных настоящих selected файлов оставить existing physical path/type/signature проверку; для directories — existing containment/type там, где consumer действительно требует directory. Root/ancestor physical traversal сохраняется, но наличие соседнего файла не сравнивается. Не заменять equality новым digest/политикой.
2. **`runtime/operation.ts`: передавать уже известный dependency set на всех relevant стадиях.** В post это existing `configurationInputs + inputs→documentInputs` (`1938–1942`); в current operation existing вычисленный набор `configurationInputs + documentInputs + additionalInputs` (`2454–2467`); на closing использовать этот же semantic input scope. Full flag guard остаётся для тех существующих pointer/runtime/output/lifetime работ, которые он ещё выбирает, но **не** означает «сравнить всё source дерево». Prepared/child explicit inputs уже есть (`1311–1315`, `2274`); не стирать их заменой общей no-op функцией. Обновить комментарии, которые утверждают неизменность owner S только на основании full boundary.
3. **`runtime/assignment-admission.ts`: не оставить ранние hidden blanket veto.** На203/237 сейчас `source.relevantInputs` ещё не заполнен поздним bindOwner; слепое `full=false` даст пустую проверку. Взять config/input/include dependency set из уже полученного actual owner inspection и expectedSources. Для этого допустимо выделить и переиспользовать нынешнюю extraction логику `operation.bindOwner:1054–1093` как приватный typed helper над настоящим NativeOwnerInspection. Это не новый resolver/scanner/schema: fileInformation.includeMap и files.config уже принадлежат Quarto. До caller prepare известны native owner inputs; после callback — выбранные expectedSources. Owner/profile/projection/intent/callback checks не удалять.

Сразу удаляются **внутренние equality branches и exceptions, существующие только ради них**, а не public mandatory digest fields. Rows/memberships пока нужны A1 captured lookup/role identity и SHA compatibility; initial snapshot function пока остаётся. Новые fresh metrics должны честно не записывать снятые full source traversals; старые counters/исторические complete не переписывать. Нельзя сохранить ложный «full source fence» счётчик за локальную проверку или объявить отсутствие измерения нулевым wall.

### Обязательное ограничение A1 fallback

`operationProjectContextReuse` уже построен из rows/memberships (`project-context.ts:948–960`), narrow contract path доступен лишь при существующих условиях (`1024–1085`). Full-copy fallback делает `source.guard([...state.rows.keys()])` **и** читает каждый source row через source.read (`1203–1218`), а последний сравнивает row.signature (`operation.ts:2927–2941`). Это не исчезает от изменения outer guard.

Поэтому первый пакет принимается как снятие blanket admission для existing native static/contract route; fallback продолжает явно старую whole-copy совместимость. Если фактическая course composition в следующем assigned execution попала в fallback, нельзя назвать цель этого ограниченного route достигнутой для неё или скрыть refusal как «unrelated». Перевод fallback на actual native dependencies/обычное native inspect без frozen whole-copy — отдельный объём; его нельзя импровизировать снятием всех signature checks и одновременным сохранением старого metadata cache. Новых cached proof/mode flags не добавлять.

## Сознательно прекращаемое отрицательное поведение

На выбранном route перестают быть ошибкой:

- изменение/удаление **непотребляемого** vendor или невыбранного QMD/project файла после первоначального observation;
- добавление/удаление unrelated sibling файла/каталога в общем предке selected input;
- исчезновение пустого native supporting scratch, если этот путь не является реальным selected input; специальный global-membership exception для него больше не нужен.

Нельзя оставить старый test «любая правка unselected/vendor обязана отказать до seal» как acceptance criterion и затем ускорять прежний отказ. Existing `assignment-closing-source-fence` должен изменить именно эти ожидаемые исходы, сохранив реальные selected config/input mutation и path alias/foreign-owner negatives. Это сознательное расширение допустимого поведения, не поломка пользовательской защиты.

Это **не** разрешение подставить чужую projection, selected student path из private test/solution, другой owner, conflicting destination, stale/foreign assignment occurrence, неверную native input/profile/format связь. Также пока сохраняются selected materialized bytes/Artifact SHA consistency и прежний refusal, когда изменился реально потребляемый origin, а PL сравнивает его с source row. Первый шаг не обещает автоматического включения вновь появившихся файлов в уже спланированный native render/A1 inventory: план определяется существующим native observation; новый запуск получает новый plan.

## Данные selected semantic/privacy boundary

Нужны уже существующие, а не новый proof:

- реальный canonical owner/output root, callback lifetime, operation/profile/projection и actual native input/format;
- expectedSources/selected assessment intent и actual captured occurrences/definitions, необходимые для учебного содержания и ранее выбранной выдачи;
- native `files.config`, `fileInformation.includeMap`, существующие per-document inputs и реально прочитанные A1 metadata contributors;
- project plan audience, rawSource/target, concrete source/test/reference roles, physical containment/symlink/type и target collision checks;
- текущие bytes и size передаваемого файла; в первом compatible пакете — честно вычисленный payload SHA с нынешним типом. Whole-owner equality из этого набора не следует.

В `assignment-roles.ts:195–201` все row signatures используются также как inode identity map для hardlink/alias audience policy. Это metadata ownership coupling, **не SHA**. Не удалять его вместе с source hash rows и не выдавать удаление byte-equality за разрешение student aliases к закрытому пути. В selected read по-прежнему проверяется actual physical path. Общая runtime validation ради TypeScript coverage не добавляется.

Максимальная honest TypeScript типизация сохраняется: actual NativeOwnerInspection/SourceSnapshot row consumer views, реальные NativeAssignmentProvenance/ArtifactInventory/PL check origins/Download bytes types, `unknown` для внешнего payload до существующего parser. Новый helper не возвращает `any`, не принимает fabricated NativeOperation и не требует fake `as` для старой schema. Неиспользуемые поля удаляются только одновременно с собственными consumers; payload SHA не подменяется policy hash.

## Compatibility и release transition

1. Первый пакет оставляет public provenance/Artifact/checks schemas и реальные SHA поля прежними; old PL30ed + Downloadfcff не требуют whole-owner equality на каждом source row, они потребляют selected paths/payload. Это доказанный меньший consumer impact, но documented Core behavior меняется: old stronger unrelated-edit refusal исчезает на указанном route. Его нужно явно описать как accepted release delta, а не silent optimisation.
2. Existing completed generations/private records не изменять, не «пересчитать» sha/proof и не rebrand старое evidence. Новая integrated candidate composition должна получать fresh native capture обычным штатным путём после согласования Source — сейчас ничего не запускалось. Сохранённые old generation остаются историческим evidence старого contract. Не вводить runtime migration registry/compact receipt для различения; actual installed pin и release notes задают выбранную реализацию.
3. Published pins Download spec по-прежнему указывают иные Core refs (не эту local candidate composition); весь будущий Source bundle должен иметь согласованные exact Core/PL/Download maps/pins перед установкой. Локальный план не доказывает совместимость всех старых installed versions. Широкую внешнюю совместимость schema B не обещать: это отдельный API migration.
4. Course/exercise/work UUID, grading targets/denominator, признанные результаты, native DB course/assessment IDs, student submissions/files, archive names/URLs не меняются этим scope. PL delivery hash/DB coupling не трогается. Даже при неизменных полях новая full export может быть другим content artifact; root должен сравнить expected release semantics перед install/publish, не менять live records ради этого технического плана.

## Что проверять в будущем решении, без запуска сейчас

Достаточный предмет будущей адресной приёмки — один existing source-boundary control с newly accepted unrelated edit/addition и retained selected config/path/audience refusal, плюс actual producer→PL/Download type composition без schema weakening. Если route unexpectedly использует A1 whole-copy fallback, это scope finding, а не основание нового 128-QMD прогона/таймаута. Какие ровно existing checks запускать и их budget определяет root после review implementer diff; в этой аналитической задаче никаких checks не выполнялось.

Нет измеренного ускорения. Initial hashing5.14MBSP/11.62MBJava в вариантеA ещё остаётся; снимается выбранная blanket equality requirement и связанная metadata traversal, а не вся цена S. Для полного удаления initial SHA/window/provenance потребуется вариантB с конкретными consumers и отдельным release scope. Этот план не принимает Native-first scoring, новую методику, новый публичный framework или переоценку целей.

Перед передачей повторно прочитан полный primary интервал от исходного turn: те же7turn_context8/133/170/243/306/488/541, top/nested Sol6.1/high согласованы; расхождений нет. После чтения consumers были только записи данного отчёта; никаких Source/проверочных/state действий не выполнялось.
