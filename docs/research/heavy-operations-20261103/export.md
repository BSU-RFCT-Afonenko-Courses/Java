# Read-only аудит тяжёлых операций: export/download/publication

3 ноября 2026. Исполнитель: `/root/audit_export`, первичный допуск `gpt-6.1-sol/ultra` в [admission.json](/home/tolya/JavaCodex/java-research-20261103/followup-heavy-operations/export/admission.json). Standard/глобальные настройки не менялись. Source call graph: tests/build/render/inspect и полное хеширование не запускались. Запись только в этот каталог; source checkouts и untracked research results сохранены.

## Границы и версии

Java: `dd5b76a0168133f0760ece6cb21f4bdf4f6203bb`, `/home/tolya/JavaCodex/java-improvement-research-20261103`. Providers: Download `c81df7364dd1fbbd07a0e54d67714b3d66f9695e`; Publisher `e8b345cec235c2c8438c479892f6bca2a4ce3ee7`; PL `1c41f03f83b26adfe58415b4d89958a3663fabf2`. Использованы root/tasks installed first-party source и upstream файлы точного ref через GitHub connector. Vendor API hashes и occurrence matches без caller не выданы за hotspot.

SP: `fcc5a2ac1eff3760d306a337a9c8871780365488`; manifest ранее прочитан в R4, exact copy предоставлена audit_platform. Его Download `e4ce084d8d15381368def66c746b51c6ba810a7d`, Publisher `e0efd6b5428412500569d0da5a5e0b33d62a5663`, PL `df8bd4aad274d618a782265b1db9c63e9b9a40f9`, Core `04c189fccfaa56badfc079f7be46fea4969ccf37`. Адресные upstream compare/old source: `refs/old-diff-*.json`, `refs/*-old`. Fast paths Java не перенесены на SP.

Moodle `ee370260b2c47a59c3946fce2d97b4c5f580d8af` и template `9468c4ebc28b84ea31c16d83ee4886233c878132` — public main Source, **не providers Java/SP**. Локальные `/home/tolya/course-tools/quarto-course-moodle` (6ca11a0d) и template (b3fa91d1) старее; их bytes не смешаны с новыми refs. Moodle legacy Core4/Bodyv1 не подтверждает Gateway runtime/Core9. UID/auth/results принадлежат Gateway/Platform, не XML exporter.

## Что обсудить первым

1. Явные лишние операции: E03 выбрасываемый ZIP; E19 повтор suites/contract checks CI; E09 materialize до дедупликации; E06 sourceRows Map на каждый файл.
2. Массовая работа: E01 SP inspect каждого input; E02 seed generation/Git/fsck/closure на экспорт; E04 два student materialize; E05 повтор SHA; E07 повтор question export/output inventory.
3. Подготовка одной партии: E08/E16 Pandoc и binding fences; E13–E15 planning/collection/release passes; E18 whole installed check; E23 ready demo hashes.

Конечные гарантии отделены ниже от исторической proof/receipt/fence обвязки. Новый registry/validator не предлагается. Любое конкурентное внешнее изменение нельзя обещать обнаруживать бесплатно.

## Различия Java/SP

- Publisher5.3.0/SP: project inspect + inspect каждого input и mixed-format per-document renders. Publisher6.0.1/Java: один project inspect на компонент и component render --to. Это существующая дельта, не выполненная здесь миграция SP.
- PL9.0.1/SP: `withProjectContexts` и `operation.assertCurrent` на внутренних границах. PL9.0.7: возможны `withProjectContracts`/`batch.checkpoint`. Audit_core подтвердил: SP Core9.1.0 ordinary assertCurrent проверяет env/pointer/private metadata/config+input+resource lstats, **не source-byte SHA на каждом guard**; source hash/full close отдельно. Static callback checkpoint current Core9.3.4 у SP отсутствует.
- Download3.3.1→3.5.0: тяжёлая runtime дельта — withProjectContexts→withProjectContracts плюс progress. Request rereads, packageArtifact hashes и resource walks те же. Новый Core batching нельзя приписать старому SP.

## Находки

### E01 · P1 · Планирование Publisher inspect каждого документа

**Режим:** production/SP. **Source:** [Afonenko-Course-Tools/quarto-project-publish@e0efd6b5 · _extensions/course-site/infrastructure/config.ts:339](https://github.com/Afonenko-Course-Tools/quarto-project-publish/blob/e0efd6b5428412500569d0da5a5e0b33d62a5663/_extensions/course-site/infrastructure/config.ts#L339), `inspectDocuments`.

**Caller:** compose.pre:294 → inspectDocuments(component):344/374 → quarto inspect project + каждый input:376; mixed-format compose:378–456 → render каждого документа и capture scope.

**Когда / сколько / scope:** На полный render композиции: 1 inspect root workspace + по компоненту 1 project inspect + D_i document inspect; отдельно alternative audience inspect. Mixed-format: до D_i Quarto render children на компонент.

**Fast/slow:** SP5.3.0: per-document путь остаётся даже если все formats одинаковы. Java6.0.1 inspectProject:397 делает один project inspect и component render --to; этот fast path нельзя приписывать SP.

**Цель:** Конечный выбранный format и полный набор результатов; per-document inspect — выбранный исторический механизм.

**Обсудить:** Удалить SP per-document format planning, обсудить явный component to и один штатный render; это уже отдельная реализованная дельта Publisher6.0.1, не доказательство миграции SP.

**Неизвестно / предел:** Не измерены D_i/current wall time и пригодность точного SP config к новой форме subprojects; Core/SP pins имеют собственную стоимость.

### E02 · P1 · Seed воссоздаётся и полностью перепроверяется на экспорт

**Режим:** production/Workspace+include-git. **Source:** [Afonenko-Course-Tools/quarto-course-prairielearn@1c41f03f · _extensions/course-prairielearn/application/native-workspace.ts:125](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/blob/1c41f03f83b26adfe58415b4d89958a3663fabf2/_extensions/course-prairielearn/application/native-workspace.ts#L125), `nativeWorkspaceArtifact`.

**Caller:** native-course.prepareNativeCourse → materializeNativeQuestion:200 → adapter.workspaceArtifact:512 → nativeWorkspaceArtifact; только workspace или includeGit.

**Когда / сколько / scope:** На уникальный materialized вопрос: Python generate; 9 внешних Git детей (symbolic-ref/show-ref/status/rev-list/rev-parse/rev-list count/fsck --strict --full/cat-file all/rev-list object closure); до/после каждого child checkpoint+env check; helper read+hash дважды; full generated work tree walk + starter/common bytes comparisons.

**Fast/slow:** Обычный upload без includeGit этот путь не вызывает. 9 outer Git не включают Git внутри Python recipe/helper; старый PL/SP не имеет облегчённого batch checkpoint.

**Цель:** Корректный начальный репозиторий и нужные учебные commits/refs, точные starter bytes; fsck/object-closure, helper hash на каждую задачу и генерация на каждом экспорте — механизмы.

**Обсудить:** Перенести генерацию и проверку seed в подготовку авторских материалов; переиспользовать готовый seed внутри текущей партии; обсудить удаление повторных fsck/closure и helper rehash из export.

**Неизвестно / предел:** Число recipe Git children зависит от generate; wall time не измерен; фактическая нагрузка Workspace определяется roster.

### E03 · P1 · Обычный ZIP сразу выбрасывается

**Режим:** production/Workspace+include-git. **Source:** [Afonenko-Course-Tools/quarto-course-prairielearn@1c41f03f · _extensions/course-prairielearn/application/native-materialize.ts:195](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/blob/1c41f03f83b26adfe58415b4d89958a3663fabf2/_extensions/course-prairielearn/application/native-materialize.ts#L195), `materializeNativeQuestion`.

**Caller:** prepareNativeCourse:462 → materializeNativeQuestion → Download.packageArtifact(final):195 → workspaceArtifact:200 → return workspace.archive:214.

**Когда / сколько / scope:** На каждый workspace/includeGit вопрос: целый обычный student ZIP + clone/schema/hash всех final files, затем второй ZIP с Git; первый archive не используется.

**Fast/slow:** Upload без includeGit использует первый ZIP; workspace/includeGit всегда заменяет его.

**Цель:** Один конечный нужный ZIP; предварительный обычный ZIP не даёт отдельного пользовательского результата.

**Обсудить:** Удалить безусловный packageArtifact из Workspace ветки и упаковывать один конечный набор.

**Неизвестно / предел:** Внутренняя публичная проверка packageArtifact сейчас побочно проверяет inventory; место единичной проверки доверительной границы обсудить, новый валидатор не нужен.

### E04 · P1 · Student materialize выполняется дважды

**Режим:** production/материализация. **Source:** [Afonenko-Course-Tools/quarto-course-prairielearn@1c41f03f · _extensions/course-prairielearn/application/native-materialize.ts:148](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/blob/1c41f03f83b26adfe58415b4d89958a3663fabf2/_extensions/course-prairielearn/application/native-materialize.ts#L148), `materializeNativeQuestion`.

**Caller:** prepareNativeCourse:462 → initial student materialize:148 → full:154 для check → select/compiler projection → final student materialize:177 с submission; Download package после.

**Когда / сколько / scope:** На уникальный non-native-answer вопрос: 2 student materialize; automatic check ещё 1 full materialize. К каждому вызову bindings заново строит closure/links/references.

**Fast/slow:** Manual file question тоже делает 2 student materialize; closed answer без файлов эту функцию обходит.

**Цель:** README должен содержать верные submission instructions, а compiler selection — правильные source files; повтор целой выдачи ради изменённого README не является самостоятельной целью.

**Обсудить:** Разделить подготовку selection/submission от единственной финальной материализации либо пересобрать только README; обсудить необходимость full bytes для частей проверки, которым хватает плана/metadata.

**Неизвестно / предел:** Сколько Core guard/copy/hash внутри materialize относится к аудиту Core; без замера не утверждается процент ускорения.

### E05 · P1 · Одни source bytes проходят цепь SHA256 заново

**Режим:** production/хеши в памяти. **Source:** [Afonenko-Course-Tools/quarto-course-prairielearn@1c41f03f · _extensions/course-prairielearn/application/native-selection.ts:42](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/blob/1c41f03f83b26adfe58415b4d89958a3663fabf2/_extensions/course-prairielearn/application/native-selection.ts#L42), `selectNativeCompilerInputs.file; nativeRawSourceSelection; nativeVerificationInventory`.

**Caller:** materializeNativeQuestion → projectNativeChecks → selectNativeCompilerInputs.file hash45 → nativeRawSourceSelection hash53; final starter compare hash187 → Download.packageArtifact hash каждого файла; prepareNativeCourse:519 → nativeVerificationInventory.files hash29.

**Когда / сколько / scope:** На checked student source в native course instance без Workspace: 5 повторных content digest стадий в PL+Download после/между Core materialize; tests/references минимум select+raw-origin+verification. Core digest сюда не включён.

**Fast/slow:** Standalone может не строить verification inventory; ungraded/manual и native-answer имеют другой путь. Это hashing уже находящихся в памяти bytes, не disk fullwalk.

**Цель:** Согласованность выбранных raw source, конечного ZIP и приватного grading inventory; пять независимых digest проходов — механизм.

**Обсудить:** Свести content validation к одной границе получения bytes, передавать принятые bytes/selection внутри одной композиции; при финальной упаковке не перепроверять все предыдущие преобразования по инерции.

**Неизвестно / предел:** Мутируемые Uint8Array сейчас защитная причина копий; конкретное владение bytes нужно решить до удаления проверок. Нет требования вводить ещё один registry.

### E06 · P1 · Raw-origin проверка строит sourceRows Map на каждый файл

**Режим:** production/повторная индексация. **Source:** [Afonenko-Course-Tools/quarto-course-prairielearn@1c41f03f · _extensions/course-prairielearn/application/native-check-inventory.ts:43](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/blob/1c41f03f83b26adfe58415b4d89958a3663fabf2/_extensions/course-prairielearn/application/native-check-inventory.ts#L43), `nativeRawSourceSelection`.

**Caller:** projectNativeChecks.origin:31 вызывается для student sources:48, trustedTests:57, каждого reference:85, public verification:103, contract cases:150 и fixtures; каждый вызов заново перебирает input.sourceRows.

**Когда / сколько / scope:** F выбранных файлов × S строк sourceRows: O(F*S) проходов, allocations и safePath проверок вместо O(S+F); затем per-file SHA отдельно.

**Fast/slow:** Это чистая in-memory обработка, без inspect/discovery/raw read. Разные роли одного raw файла также повторяют работу.

**Цель:** Связать каждый raw source с текущим owner snapshot; повторять полную индексацию S ради одного lookup не обязательно.

**Обсудить:** Создать этот локальный Map один раз в projectNativeChecks/партии и передать lookup в существующую проекцию; не создавать новый persistent registry.

**Неизвестно / предел:** Фактические F/S и доля времени неизвестны; значение sourceRows по всей композиции фиксирует владелец Core.

### E07 · P1 · Общий вопрос повторно строится на каждую работу и перечитывается

**Режим:** production/whole-course export. **Source:** [Afonenko-Course-Tools/quarto-course-prairielearn@1c41f03f · _extensions/course-prairielearn/application/export-course.ts:565](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/blob/1c41f03f83b26adfe58415b4d89958a3663fabf2/_extensions/course-prairielearn/application/export-course.ts#L565), `exportCourse; inventory`.

**Caller:** prepareNativeCourse:529 → exportCourse loop по works → exportPrairieLearn(partial) каждой работы:565 → inventory(partial question):630 → при уже имеющемся вопросе inventory(stage question):655 → inventory(stage):716.

**Когда / сколько / scope:** Для вопроса в k работах: k полных question exports/Pandoc/сopies, k per-question read+SHA деревьев, k−1 hash деревьев existing question, затем ещё 1 full delivery tree read+SHA. Новые вопросы также сканируются минимум per-question и full stage.

**Fast/slow:** nativeInputs устраняет чтение authored selected source, но не scratch write→read, partial reconstruction и output inventory. Конфликт повторного вопроса нужен семантически; нынешнее выявление после полного export дорого.

**Цель:** Один согласованный вопрос может входить в несколько works; конфликтующие payloads должны быть обнаружены.

**Обсудить:** До render объединить совпадающие question inputs/политики, materialize вопрос один раз; получать delivery file hashes при финальной записи; убрать повторные directory inventories где bytes уже есть.

**Неизвестно / предел:** Стоимость зависит от числа повторов и resources; не предлагается молча принимать первый конфликтующий payload.

### E08 · P1 · Pandoc child на каждое условие и введение плюс fences

**Режим:** production/рендер фрагментов. **Source:** [Afonenko-Course-Tools/quarto-course-prairielearn@1c41f03f · _extensions/course-prairielearn/application/export.ts:465](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/blob/1c41f03f83b26adfe58415b4d89958a3663fabf2/_extensions/course-prairielearn/application/export.ts#L465), `renderPublicBody; command`.

**Caller:** exportPrairieLearn condition branches:675/908 → renderPublicBody → quarto pandoc; exportCourse:332 → renderPublicBody каждого overview context; command:108/134 → auxiliary.before/after → liveFence → checkpoint + checkedJSON asserts.

**Когда / сколько / scope:** Один Pandoc на question occurrence и каждый work/menu introduction, в том числе пустое introduction []; перед ним JSON serialize/clone/AST walk/resource Base64 decode+hash; 2 auxiliary checks на child.

**Fast/slow:** Одинаковый вопрос в нескольких works повторяет это. nativeBody сохраняет bibliography, но не объединяет Pandoc процессы. Java batch checkpoint легче SP, JSON binding checks продолжаются.

**Цель:** Нативная HTML семантика условия/введения; per-fragment процесс и весь parent fence на каждый fragment — механизм.

**Обсудить:** Подготовить HTML уникальных неизменных фрагментов один раз на batch, обсудить пакетное Pandoc преобразование и пропуск пустого introduction; убрать повторное декодирование одинаковых ресурсов.

**Неизвестно / предел:** Пакетирование должно сохранять resource URL/ID/math semantics; не измерено время запуска child против самого Pandoc.

### E09 · P1 · Повторный artifact материализуется до проверки Map

**Режим:** production/дедупликация. **Source:** [Afonenko-Course-Tools/quarto-project-download@c81df736 · _extensions/project-download/application/publish.ts:75](https://github.com/Afonenko-Course-Tools/quarto-project-download/blob/c81df7364dd1fbbd07a0e54d67714b3d66f9695e/_extensions/project-download/application/publish.ts#L75), `publishInternal`.

**Caller:** finishInternal.pack → publishInternal loop requests/artifacts → ports.modelArtifact:75 (Core materialization+QRC binding) → model.get(name):87 → equalFiles:88.

**Когда / сколько / scope:** На каждое повторное появление exerciseId/kind сначала дорогая материализация; лишь ZIP packaging дедуплицировано по имени:169–174. equalFiles сканирует все bytes повторно.

**Fast/slow:** Resource IDs дедуплицируются selected Map до files/ZIP; project plans кешированы runtime.plans, но готовый inventory не дедуплицирован до modelArtifact.

**Цель:** Консистентность requests и один ZIP на имя; повторный producer не самоцель.

**Обсудить:** Объединить совместимые typed artifact requests до materialize; сравнить source/condition/policy до bytes и переиспользовать один результат внутри finish.

**Неизвестно / предел:** Повторы могут представлять разные условия; нельзя удалить конфликтную проверку без определения эквивалентности inputs.

### E10 · P2 · Request файл читается и парсится четыре раза за finish

**Режим:** production/requests. **Source:** [Afonenko-Course-Tools/quarto-project-download@c81df736 · _extensions/project-download/infrastructure/runtime.ts:201](https://github.com/Afonenko-Course-Tools/quarto-project-download/blob/c81df7364dd1fbbd07a0e54d67714b3d66f9695e/_extensions/project-download/infrastructure/runtime.ts#L201), `finishInternal; inspectOwnedRequests`.

**Caller:** finish probe201–207 → inspectOwnedRequests274 (ownership.readFile110+parse113+hash) → requests.readTextFile278 → closing inspectOwnedRequests645 перед publish.

**Когда / сколько / scope:** На request: 4 reads+JSON parses; 2 SHA256 полного request; 2 SHA1 source key; повторные directory scans и lstat/noSymlinks всех ancestors. Дополнительно ownedSignature JSON.stringify/parse.

**Fast/slow:** options.native=true не обходит первый probe loop. Scope только generated requests owner, не весь курс/все source bytes.

**Цель:** Состав запросов известен после render, публикация соответствует им; повторное чтение одного закрытого transport — механизм.

**Обсудить:** Один раз получить проверенный request вместе с parsed value; убрать отдельный native probe/readTextFile. Обсудить единственный final check на реальной mutable границе вместо полного повторного parse/hash.

**Неизвестно / предел:** Размер request обычно мал; это подтверждённая лишняя работа, приоритет ниже materialize/inspect.

### E11 · P2 · Для каждого resource каталог обходится и создаётся bare Git

**Режим:** production/ресурсы. **Source:** [Afonenko-Course-Tools/quarto-project-download@c81df736 · _extensions/project-download/infrastructure/files.ts:164](https://github.com/Afonenko-Course-Tools/quarto-project-download/blob/c81df7364dd1fbbd07a0e54d67714b3d66f9695e/_extensions/project-download/infrastructure/files.ts#L164), `resourcePaths; gitIgnored`.

**Caller:** publishInternal:166 → ports.files → runtime:501 resourcePaths / standalone:513 resourceFiles → resourcePaths.walk:200 → gitIgnored:init bare + check-ignore.

**Когда / сколько / scope:** Один recursive readDir/sort каждой выбранной resource directory и 2 Git child с temporary bare repo при gitignore по умолчанию; затем чтение всех selected bytes или Core.resourceInventory.

**Fast/slow:** resource.gitignore=false убирает Git children; excludes отсекают subtrees; одинаковый resource ID читается 1раз, разные IDs с тем же root повторяют traversal.

**Цель:** Точный публичный набор файлов и gitignore semantics; создание отдельного bare Git на ресурс — механизм.

**Обсудить:** Сделать selection известных ресурсов частью подготовки партии; объединить одинаковые roots/ignore context и check-ignore за один вызов; не добавлять глобальный discovery.

**Неизвестно / предел:** Scope может быть маленькой data directory либо широким root; реальная конфигурация определяет цену.

### E12 · P2 · Обычный Download finish всегда вызывает inspect

**Режим:** production/standalone. **Source:** [Afonenko-Course-Tools/quarto-project-download@c81df736 · _extensions/project-download/infrastructure/runtime.ts:229](https://github.com/Afonenko-Course-Tools/quarto-project-download/blob/c81df7364dd1fbbd07a0e54d67714b3d66f9695e/_extensions/project-download/infrastructure/runtime.ts#L229), `prepareInternal; finishInternal.collect; inspect`.

**Caller:** entrypoints/pre → prepareInternal97; entrypoints/post / public finishDownloads → collect229 → inspect(root)31.

**Когда / сколько / scope:** Standalone finish: 1 project inspect даже при известном inputFiles/output env, потому что !operation. Вне Quarto hook prepare добавляет ещё 1 inspect.

**Fast/slow:** Native current operation с catalog+output: direct Download inspect=0. Нет оснований считать весь Download inspect-per-document.

**Цель:** Independent ресурсы требуют config и selected authored inputs; повторная project metadata resolution — механизм.

**Обсудить:** Передавать уже полученный native project context из hook или подготовленной партии; оставить fallback лишь действительно независимому вызову.

**Неизвестно / предел:** Parent Core/Site inspect считаются отдельно; число project inspections нельзя получить одним grep.

### E13 · P2 · Inspect альтернативных audience profiles на каждой полной сборке

**Режим:** production/планирование. **Source:** [Afonenko-Course-Tools/quarto-project-publish@e8b345ce · _extensions/course-site/infrastructure/config.ts:263](https://github.com/Afonenko-Course-Tools/quarto-project-publish/blob/e8b345cec235c2c8438c479892f6bca2a4ce3ee7/_extensions/course-site/infrastructure/config.ts#L263), `workspace; inspectProject; validateAudienceOutputs`.

**Caller:** compose.pre → workspace201 (root inspect204) → validateAudienceOutputs327; per component inspectProject349 → validateAudienceOutputs379 → inspect(root,alternate profile):302.

**Когда / сколько / scope:** Для current Publisher полного render: 1 root + 1/компонент selected inspect и до 1 alternate audience inspect на owner при известной audience, до2 при unknown; каждый вызов заново native metadata.

**Fast/slow:** Partial pre/post workspace отдельно делает inspect root; нет pre active state при partial. known config reuse только если selection полностью равна профилям; альтернативный profile обычно отличается.

**Цель:** Student/full destinations не перекрываются; разрешение всех profile outputs на каждом render — механизм.

**Обсудить:** Перенести проверку неизменной destination/profile схемы к изменению config/подготовке; использовать текущий план в обеих фазах; обсуждать явные output destinations.

**Неизвестно / предел:** Сложные profile defaults/groups/metadata-files запрещают угадывать effective config по имени файла. SP старого per-document пути см.E01.

### E14 · P2 · Коллекция повторно stat-проверяется до копирования

**Режим:** production/outputs. **Source:** [Afonenko-Course-Tools/quarto-project-publish@e8b345ce · _extensions/course-site/infrastructure/collection.ts:96](https://github.com/Afonenko-Course-Tools/quarto-project-publish/blob/e8b345cec235c2c8438c479892f6bca2a4ce3ee7/_extensions/course-site/infrastructure/collection.ts#L96), `collect; readCollection; copyFiles; cleanOutput`.

**Caller:** component post collect → publicOutputs:47 safePath+lstat → files(outputDir):105 walk; parent pre readCollection:170 по [...nativeOutputs,...files] safePath+lstat; post copyFiles:200 safePath+lstat ещё раз → copyFile.

**Когда / сколько / scope:** Каждый output из nativeOutputs одновременно обычно есть files, поэтому readCollection проверяет его дважды; путь дополнительно проверяется в collect и copyFiles, каждый safePath lstat-ит ancestors. Полный root files walk в post542. Перед rebuild cleanOutput147 обходит старый output, .md bytes сравнивает source/copy.

**Fast/slow:** Partial path не копирует все child outputs; полной composition всё равно нужна одна публикационная copy. Здесь нет SHA всего site.

**Цель:** Безопасный destination и наличие результатов; кратные проверки одних путей — механизм.

**Обсудить:** Проверить коллекцию и общий parent path один раз на границе, копировать подготовленный список в контролируемый output. Обсудить dedicated staging вместо проверки каждого старого output перед очисткой.

**Неизвестно / предел:** Нельзя удалять единственную защиту пользовательских исходников при destructive clean; это отдельная реальная гарантия.

### E15 · P2 · Публикация без Download дважды вызывает release validation

**Режим:** production/PL release. **Source:** [Afonenko-Course-Tools/quarto-project-publish@e8b345ce · _extensions/course-site/application/compose.ts:169](https://github.com/Afonenko-Course-Tools/quarto-project-publish/blob/e8b345cec235c2c8438c479892f6bca2a4ce3ee7/_extensions/course-site/application/compose.ts#L169), `ownerRelease`.

**Caller:** post finalizeDownloadsImpl no project-download branch280 → Core.withCurrentNativeOperation → ownerRelease → operation.validateRelease(adapter)169 → если PL validated → adapter.validateCourse175 → ещё operation.validateRelease.

**Когда / сколько / scope:** 2 validateRelease на owner с PL; второй PL путь дополнительно assertCurrent до/после и correlation request reads. По одному набору source metadata повторяется decode/validation.

**Fast/slow:** С Download используется PL.inspectPublication и один validateRelease, а не этот двупроходный adapter path. Без PL только один pass.

**Цель:** Согласованность PL declaration/grading composition; два release passes — выбранная обвязка.

**Обсудить:** Выполнить PL composition validation из единственного release callback, переиспользовать разобранный business input; не сохранять validationPasses==2 как продуктовый контракт.

**Неизвестно / предел:** Нужны те же конечные grading invariants; стоимость Core guard отдельно.

### E16 · P2 · Каждый child перечитывает и хеширует неизменные JSON bindings

**Режим:** production/binding fences. **Source:** [Afonenko-Course-Tools/quarto-course-prairielearn@1c41f03f · _extensions/course-prairielearn/application/native-materialize.ts:26](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/blob/1c41f03f83b26adfe58415b4d89958a3663fabf2/_extensions/course-prairielearn/application/native-materialize.ts#L26), `checkedJSON.assert; captureNativeAuxiliary.check`.

**Caller:** prepareNativeCourse → nativeReferenceBindings checkedJSON local catalog; registry()/standalone binding checkedJSON; liveFence → checkBindings:366; Pandoc/Python/Git before+after используют liveFence.

**Когда / сколько / scope:** Initial 1 JSON read+hash; каждый fence повторяет realPath/readFile/SHA256 каталога, runtime registry и/или binding. Два fences на Pandoc child; Workspace 1Python+9Git дают20 таких before/after вызовов помимо других fences.

**Fast/slow:** Current batch checkpoint может не делать source IO, но checkedJSON asserts остаются. SP9.0.1 каждое такое место также operation.assertCurrent metadata checks.

**Цель:** Binding должен соответствовать подготовленной выдаче; перечитывать его вокруг каждого fragment-child — механизм.

**Обсудить:** Фиксировать эти inputs в подготовке одной export операции; сверять один раз перед публикацией при выбранном договоре внешних изменений, исключить repeat checks на внутреннем Pandoc/Git.

**Неизвестно / предел:** Удаление признаёт договор об отсутствии конкурентного редактирования в batch; обещание обнаруживать любое внешнее изменение нельзя сохранять бесплатно.

### E17 · P2 · Publication-conditions повторно читает принятые ZIP/manifest dependencies

**Режим:** production/legacy independent export. **Source:** [Afonenko-Course-Tools/quarto-course-prairielearn@1c41f03f · _extensions/course-prairielearn/application/publication-conditions.ts:39](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/blob/1c41f03f83b26adfe58415b4d89958a3663fabf2/_extensions/course-prairielearn/application/publication-conditions.ts#L39), `publicationConditions snapshot/check`.

**Caller:** exportCourse input.publicationConditions → loader snapshots manifest/whole ZIP; exportCourse715 check перед inventory и837 check перед rename.

**Когда / сколько / scope:** Полные bytes каждого bound input читаются и хешируются initial + на check перед output inventory + на check перед publish; lstat/realPath/identity до/после.

**Fast/slow:** Native prepareNativeCourse outright rejects options.publicationConditions:266 и stagingReceipt261; этот путь нельзя приписать обычному native Java export. Public standalone consumer может вызвать его.

**Цель:** Точное соответствие заранее принятой публикационной зависимости; троекратное whole ZIP hashing — механизм.

**Обсудить:** Передавать уже принятые immutable artifact bytes в одну подготовку и объединить проверки до публикации; обсуждать отказ от лишних receipts при сохранении реального результата.

**Неизвестно / предел:** Активный Java/SP CLI caller с этим flag не установлен; приоритет ниже активного native пути.

### E18 · P2 · Полный installed provider hash несколько раз в соседних путях

**Режим:** tooling/install+CI. **Source:** [BSU-RFCT-Afonenko-Courses/Java@dd5b76a0 · tools/materialize-providers.py:100](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/tools/materialize-providers.py#L100), `main --check; inventory; write; sync-providers.sync`.

**Caller:** CI/render.sh:7 и CI/check-projects.sh:10 независимо → materialize --check; check-local-candidate.py:12 тоже → --check. --write заранее inventory всех scopes, изменённый target ещё раз до и после copy. Legacy sync делает git archive+quarto add, hashes original+installed+каждый destination..

**Когда / сколько / scope:** По existing installed-packages lock один --check nominally читает2957файлов/18134333байта по всем package scopes (включая vendor), вычисляет SHA; это расчёт metadata, не измерение. CI render и providers jobs дают2 таких независимых прохода.

**Fast/slow:** Повтор --write unchanged пропускает copy, но preflight всё равно хеширует весь installed closure. Никаких таких команд в текущем аудите не запускалось.

**Цель:** Воспроизводимая установка и сохранение local changes; полный hash на каждом чтении статуса/несвязанной сборке не цель.

**Обсудить:** Проверять комплект при install/change/release boundary и переиспользовать результат внутри одного pipeline; удалить дублирование соседних commands, обсудить legacy sync/quarto add round-trip.

**Неизвестно / предел:** 2 jobs — разные checkout/runner процессы, простая память между ними не общая; режимы сохранения пользовательских изменений при write требуют явного договора.

### E19 · P1 · Полные TS suites исполняются повторно в одном workflow

**Режим:** CI. **Source:** [Afonenko-Course-Tools/quarto-course-prairielearn@1c41f03f · .github/workflows/ci.yml:93](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/blob/1c41f03f83b26adfe58415b4d89958a3663fabf2/.github/workflows/ci.yml#L93), `check job / java-delivery job / tests.check`.

**Caller:** workflow check:93 → tests/check.ts:7 discovers все33 *.test.ts + tools/check-contracts; workflow:137 запускает16 из этих suites, :141 ещё9; java-build и native-seed-policy входят в обе explicit lists. check-contracts также standalone:58..

**Когда / сколько / scope:** 33+16+9=58 whole-suite invocations при33 уникальных .test suites; export-course test дополнительно filter EXDEV:139. tools/check-contracts 2раза. Запуск push/PR/manual/weekly.

**Fast/slow:** EXDEV=/dev/shm действительно другая filesystem проверка, её нельзя назвать точным дублем default temp без анализа. Дубликаты16/9 используют те же source/ref и команды permisssions.

**Цель:** Проверить contracts/реальный export; повтор одного suite в двух jobs не даёт другой продуктовый результат.

**Обсудить:** Убрать совпадающие suite invocations и contract-check; Java-specific job оставить только фактические JDK/Gradle/EXDEV отличия. Обсудить убрать giant full export для локальных negative value cases.

**Неизвестно / предел:** Тайминги suites не измерены, окружения jobs имеют setup различия; unit suites не требуют Java по самому факту имени job.

### E20 · P2 · Матрица границ повторяет реальные course render

**Режим:** CI/tests. **Source:** [Afonenko-Course-Tools/quarto-project-publish@e8b345ce · .github/workflows/ci.yml:99](https://github.com/Afonenko-Course-Tools/quarto-project-publish/blob/e8b345cec235c2c8438c479892f6bca2a4ce3ee7/.github/workflows/ci.yml#L99), `site-native-pipeline; site-owner-release; site-public-operation`.

**Caller:** workflow loop pipeline modes4+default; owner-release14modes:107, каждый tests/site-owner-release.ts172 запускает реальный quarto render; public-operation3 env combinations104–106; ещё domain/profile integration fixtures..

**Когда / сколько / scope:** Минимум14 отдельных installed owner-release full renders; ещё5 pipeline запусков и3 public-operation setups; многие проверки относятся к payload/value refusal, metrics/proof schema и transport details.

**Fast/slow:** Не все mode случаи дублируют семантику; site-native-planning/effective-formats относятся к отличающимся planning contracts. Запускать все они не нужно для каждой мелкой правки.

**Цель:** Student privacy, корректные refs/формат/состав и output результата; обязательность конкретной transport proof формы и полного render на каждый wrong field — историческая обвязка.

**Обсудить:** Оставить реальные маршруты для уникальных integration boundaries; отрицательные metadata/value cases перенести к существующим чистым функциям или убрать избыточные случаи; не превращать metrics equality/pass count в пользовательское требование.

**Неизвестно / предел:** Полная матрица всех tests не доказана как duplicate; здесь точный caller/дорогая гранулярность, не рекомендация удалить любой negative контроль.

### E21 · P2 · Большой export test повторяет весь exporter ради малых дефектов

**Режим:** tests. **Source:** [Afonenko-Course-Tools/quarto-course-prairielearn@1c41f03f · tests/export-course.test.ts:184](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/blob/1c41f03f83b26adfe58415b4d89958a3663fabf2/tests/export-course.test.ts#L184), `Deno.test full native export deterministic/atomic`.

**Caller:** CI tests/check.ts + java-delivery → full native export test; внутри native patches loop503, source tamper509, multiple policies/rosters, повтор deterministic one/two + random/randomAgain; one/two output bytes заново hashes519–527.

**Когда / сколько / scope:** Один test включает много exportCourse calls и вызовы Pandoc/файловую staging; whole-course snapshots уже production.inventory hashes, затем test читает и хеширует1и2 output ещё раз. Всё удвоено CI E19.

**Fast/slow:** Отдельные source rejection tests:845 работают непосредственно с selectedSources и показывают уже доступную дешёвую гранулярность.

**Цель:** Атомарность и детерминизм одного реального export нужны как результат; множество полноразмерных exports для отдельных input field defects не обязательны.

**Обсудить:** Удалить повторные whole-export data-only negatives либо выполнять их на существующих validators; оставить один meaningful atomic export и только реально отличающиеся FS path.

**Неизвестно / предел:** Содержательная достаточность каждого assertions-case отдельно не исследована; не заявляется что все проверки redundant.

### E22 · P3 · Moodle: Pandoc на condition и каждый choice, resource hash один раз

**Режим:** production/unpinned Source. **Source:** [Afonenko-Course-Tools/quarto-course-moodle@ee370260 · _extensions/course-moodle/application/export.ts:57](https://github.com/Afonenko-Course-Tools/quarto-course-moodle/blob/ee370260b2c47a59c3946fce2d97b4c5f580d8af/_extensions/course-moodle/application/export.ts#L57), `exportMoodle`.

**Caller:** entrypoints/export.ts15 parses Body/binding → exportMoodle → validatePackage+validateBody каждого question → verifyResources56 → html(condition)92 + html(each choice)118.

**Когда / сколько / scope:** Q условий + сумма choices single-choice Pandoc процессов; resources целого input Base64 decode+SHA один раз в verifyResources491, AST walks validation/map/resourceTargets для каждого вопроса.

**Fast/slow:** Это legacy Bodyv1/Core4 Source ee370260, не Java/Core9 pin и не Gateway. Прямого активного Java/SP caller XML exporter не найдено.

**Цель:** Нативный HTML/XML банка и корректные attachments; per-fragment children потенциально объединяемы, hash однажды здесь не доказан как избыточный hotspot.

**Обсудить:** При реальной большой Moodle XML выдаче обсуждать пакетный render и только используемые resources; ниже активных Java/SP затрат.

**Неизвестно / предел:** Размер/активность XML банка неизвестны. Gateway UID/auth/results не исполняются этим source; server/runtime исследуется отдельно.

### E23 · P2 · Готовые demos повторно хешируются после fetch и render

**Режим:** tooling/prepare+publish+CI. **Source:** [Afonenko-Course-Tools/quarto-template-course@9468c4eb · tests/site.cjs:58](https://github.com/Afonenko-Course-Tools/quarto-template-course/blob/9468c4ebc28b84ea31c16d83ee4886233c878132/tests/site.cjs#L58), `checkBuild; checkCopied; Task fetch/check/publish`.

**Caller:** Taskfile _fetch:61 asset sha check → extract → site.cjs --demo-dir:71 checkBuild all files; task render копирует demos; task check → site.cjs every8groups checkBuild all site copies + checkCopied source and target; task publish → task check заново.

**Когда / сколько / scope:** На явный fetch+render+check: compressed archive SHA256×1 каждого8demo; expanded file hash1pass в fetch +1pass rendered copy в check; checkCopied ещё read source+target bytes; отдельный HTML walk всего _site. Publish повторяет check и authoring fixtures.

**Fast/slow:** Task render сам не делает download или hash проверки; явное fetch уже отделено от render. В8groups входят Print/Cloud/external, это Source шаблона, не активный состав Java.

**Цель:** Готовые демонстрации и сохранность ресурсов в документации; повторять producer hashes/полное tree comparison/authoring renders перед каждой публикацией не цель.

**Обсудить:** Проверять pinned ready assets единожды при явном fetch/обновлении; согласовать один copy-integrity проход при изменении bundling; убрать повтор task check при публикации уже проверенного результата.

**Неизвестно / предел:** Template9468c4eb не входит в Java providers; test fetch.py использует synthetic assets, его occurrence loops не считаются массовой реальной сетью.

### E24 · P2 · Отдельный Native exporter всегда делает новый author render owner

**Режим:** production/CLI. **Source:** [Afonenko-Course-Tools/quarto-course-prairielearn@1c41f03f · _extensions/course-prairielearn/application/native-entry.ts:211](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/blob/1c41f03f83b26adfe58415b4d89958a3663fabf2/_extensions/course-prairielearn/application/native-entry.ts#L211), `nativeEntry`.

**Caller:** entrypoints/export-course.ts/export.ts → nativeEntry → withNativeCaptureScope assignment new211 → lease.captureInputs descriptor.expectedSources265 → render270 → prepareNativeCourse299.

**Когда / сколько / scope:** Каждый CLI export: новый owner author render выбранных source документов до экспорта; даже при ранее собранном Pages/student owner output он не consume-existing.

**Fast/slow:** consume-existing намеренно не реализован (nativeEntry174). Это отдельная user-command, не обычный Publisher post-render; два разных output-dir/projection означают что student output не готовый author payload.

**Цель:** Закрытая trusted grading/delivery выдача в точном авторском контексте; безусловная полная повторная capture при неизменном prepared input — механизм.

**Обсудить:** После упрощения общего договора обсуждать создание needed delivery inputs в штатной author подготовке и использование одного результата для PL/README/ZIP; не добавлять новый validator/receipt protocol.

**Неизвестно / предел:** Нельзя обещать reuse student HTML как author input; реальный owner selected roster/повтор CLI нужно считать отдельно.

## Покрытие и пределы

`refs/{download,site,pl,moodle,template}/tree.json` — полный upstream file inventory из GitHub metadata. Через connector сохранены79 first-party production .ts/.lua/.sh/.py/descriptor текущих Download/Site/PL (23/13/43), все6 TS Moodle. Семь transient fetch_file ошибок PL закрыты одиночным raw-resource fallback. Generated validators/vendor не интерпретировались как собственные горячие API без caller; source schema/consumers просмотрены.

CI/tooling отделены от production: workflows трёх providers, Moodle/template, Java render/provider preflights, Download check, PL canonical tests/check, template Task/site/fetch. Полные tests/tools списки известны из trees; каждое assertion33 PL suites и все Publisher fixtures не оценены семантически. Дубли CI доказаны runner graph. Vendor-update/generator tools перечислены, их редкая ручная стоимость не углублялась.

Глубокое чтение tests: PL export-course/whole-bank/native-scope/native-render/check; Publisher support/owner-release/native-pipeline/release-composition; Download runtime-batch/render/model-receipts/package-once. Это повод удалить full-export/full-render обвязку data-only negatives, не создать ещё тесты.

2957файлов/18134333байта provider --check вычислены из существующего installed-packages.json; source bytes ради этого не хешировались. Частоты статические, timings/проценты экономии неизвестны. Серверные image/runtime/Gateway не проверялись. Core/QRC/Navigation/Platform — другие владельцы аудита; их полный fence count сюда не добавлен.

[hotspots.json](/home/tolya/JavaCodex/java-research-20261103/followup-heavy-operations/export/hotspots.json) содержит машинные записи и дополнительные точные ссылки. [coverage.json](/home/tolya/JavaCodex/java-research-20261103/followup-heavy-operations/export/coverage.json) — refs/пределы.

