# Независимая Source-рецензия A5, 4 ноября 2026

**Итог после повторного freeze: APPROVE для Source, runtime PENDING.** Адресная final-delta рецензия закрывает все три замечания первого freeze. Свежие compiler/tests/Native/CLI результаты этим решением не заявляются. Ниже сохранена история исходного REQUEST CHANGES, затем дано окончательное решение по исправлению.

## Первый freeze: исторический REQUEST CHANGES

**REQUEST CHANGES.** Удаление промежуточных output/resource SHA и согласование PL reader соответствуют цели A5, но в выбранной дельте остаются три блокирующих нарушения сохранённых privacy/path гарантий. Старые freshness guarantees не требуются обратно.

Рассмотрены зафиксированные `native-final-outputs-a5-core-20261104.patch`, `native-final-outputs-a5-pl-20261104.patch`, отчёт исполнителя и соответствующие полные исходники в `core-native-bridge` / `prairielearn`. Core HEAD — `af5ceb28b860c8637e59747faa4ca3e3661b5724`, PL HEAD — `0079b64decb30aea70cf55a1526dd17d8d66a339`. Независимая A4 title дельта, включая общий `body-export/assignment.ts`, отделена baseline patch и не пересматривается этим решением.

## Блокирующие дефекты

### P1: повторное разрешение затирает source identity закрытого hardlink

Место: `core-native-bridge/_extensions/course-core/runtime/operation.ts:269–273`, присваивания на `1970` и `2255–2259`, передача ролям на `3046–3048`.

`finishedResource()` возвращает новый `sourceIdentity` при каждом вызове. Когда исходного файла больше нет, он подставляет identity конечного output. Оба `Object.assign()` безусловно затирают уже сохранённую source identity. Это позволяет потерять именно privacy evidence при обычном завершении hooks, без конкурентной гонки:

1. Наблюдаемый `assets/a.svg` — hardlink закрытого A1 origin; output `_site/assets/a.svg` — отдельная regular копия с другим inode.
2. Core post сохраняет закрытый source inode и отдельный output inode.
3. Последующий hook удаляет source alias либо заменяет его новым regular файлом. Закрытый оригинал A1 остаётся на месте.
4. Seal или открытие complete операции заменяет сохранённый `sourceIdentity` на output inode либо на inode нового source. `assignmentRoleIndex()` получает только эти новые identities; initial `state.rows` с прежним source inode в этот вызов не входят.
5. `roles.assertPath()` и `roles.assertIdentity()` не связывают output-копию с закрытым origin; Body может выдать скопированные закрытые bytes публичным ресурсом.

`assertSources(..., false, selected documents)` проверяет выбранные raw inputs и предков, поэтому удаление отдельного `assets/a.svg` само по себе этот сценарий не останавливает. A1 acquisition после hooks видит сохранившийся закрытый оригинал, но не исчезнувший source alias. Требуется сохранять уже установленную physical source association при разрешении конечного пути; это не требует прежнего SHA/signature или неизменности bytes.

### P1: regular/realpath проверка не относится к фактически открытому файлу

Место: `core-native-bridge/_extensions/course-core/runtime/operation.ts:2515–2529`.

`regular()` возвращает проверенный pathname, затем отдельный `await Deno.open(actual)` следует symlink. Между этими действиями source/output или один из его предков может быть заменён symlink на regular файл вне owner либо на private файл, не входящий в набор A1 identities. `opened.stat().isFile` такой файл принимает; dev/inode от handle верен, но доказывает только identity открытого файла, а не его принадлежность проверенному owner path. Для generic inventory `assertIdentity` отсутствует, а для assignment он знает только закрытые A1 identities.

После чтения `guard()` проверяет pointer/runtime/raw sources и больше не проверяет выбранный resource path. Поэтому открытые через symlink посторонние bytes попадают в callback cache и выдаваемый inventory. В прежнем коде постоянная подмена после precheck обнаруживалась последующим `lstat`/signature ресурса; после удаления этого блока такого отказа нет. Нужна привязка path/containment/no-symlink проверки к actual opened handle до выдачи bytes. Сравнение с прежней byte/signature версией не требуется. Этот дефект требует конкурентной подмены между проверкой и открытием; его реальное воспроизведение данной Source-рецензией не выполнялось.

### P2: ordinary consumer не проверяет конечные HTML paths после hooks

Место: `core-native-bridge/_extensions/course-core/runtime/operation.ts:2253–2260`, удалённый output обход перед концом `guard()` на `2322–2323`.

В scoped assignment путь HTML проверяется `regular()` при seal на `1968`. У ordinary операции `complete.json` записывается Core post до последующих hooks; при входе в `withCapturedOperation()` заново разрешаются только resources. Обход `complete.outputs` удалён и из full guard.

Достаточно ordinary документа без ресурсов: следующий post hook удаляет его HTML либо заменяет HTML/его output ancestor symlink. `withCurrentNativeOperation()` и `assertCurrent()` принимают такую complete операцию, поскольку сохранённый catalog/raw input остаются валидными, а конечный output path вообще не читается. Тем самым исчезла сохранённая гарантия regular/realpath/symlink для выбранных outputs. Нужна проверка конечных выбранных HTML paths на внешней границе после hooks, без проверки прежних bytes и без обхода на каждом getter.

## Что подтверждено статически

Producer больше не создаёт SHA1 resource observation; complete output/resource DTO не содержит прежних SHA/signature. Actual bytes читаются через открытый `FsFile`; первый identity берётся из `opened.stat()`, копии inventory отделены от callback cache. На попадании в cache вызывается переданная `assertIdentity(captured.identity)` без FS equality. Generic публичные document/condition/resource getters assignment операции закрыты, а assignment Body и condition readers передают role-bound predicate; самостоятельного воспроизводимого generic→assignment cache bypass сверх описанной потери source association не найдено.

`assignmentRolePredicate()` теперь сохраняет несколько identities одного source path и проверяет physical closed roles. Это устраняет прежнее взаимное затирание source/output identities внутри самого predicate, но producer выше всё ещё может потерять первоначальную identity. Pure fixture `tests/assignment-role-alias.ts` вручную передаёт обе identities; он не проходит через повторные вызовы `finishedResource()` и не покрывает первый дефект.

Body resource callback согласован как `{bytes, identity}`, PL reader перестал требовать `r.sha256`. Download reader использует `target/bytes` и не требует SHA из Native resource DTO. Новые тестовые ожидания допускают законное postprocessing и не возвращают старую immutability. Их выполнение этой рецензией не подтверждается.

## Границы и собственный допуск

Это независимое статическое решение по точным Source. Компилятор, tests, render/Native, inspect, hash, build и сеть не запускались; Git и реализация не изменялись. Raw source snapshot, `projectArtifactInventory`, Java/compiler hashes, полный аудит privacy вне выбранных readers и installed/runtime composition не входят в решение. До устранения дефектов и отдельного runtime evidence приёмка A5 отсутствует.

Собственный reviewer: `/root/a5_final_outputs_review`, chat `01a1a6f7-51cf-72c3-88c9-81143cd73e84`, неизменный origin turn `01a1a6f7-5200-7220-95e1-ffcf377d1217`. Первичные `session_meta` и весь доступный origin interval `turn_context` прочитаны до verdict: верхние и вложенные настройки `gpt-6.1-sol/high` согласованы, PASS. Свидетельство — `native-final-outputs-review-model-admission-20261104.json`. Родительский ID не принят за собственный. Глобальные настройки не менялись; скрытая серверная маршрутизация не удостоверяется.

## Повторный freeze: окончательная адресная рецензия

**APPROVE для Source.** Прочитаны текущий `native-final-outputs-a5-core-20261104.patch`, неизменившийся PL reader patch, обновлённый отчёт исполнителя, новый production `runtime/native-output-resources.ts` целиком и его точные вызовы в `operation.ts`. Первый freeze сохранён автором в `native-final-outputs-a5-core-first-freeze-20261104.patch` и `native-final-outputs-evening-first-freeze-20261104.md`; первоначальные выводы выше относятся к нему.

1. Первое P1 закрыто. `finishedNativeResource()` сохраняет переданные установленные `sourceIdentities`, даже когда source удалён или заменён. Seal и внешний вход передают эти associations при refresh, а merge children объединяет множества для одного source. `assignmentRoleIndex()` получает все сохранённые associations и конечную identity, не затирая их внутри path map. Поэтому сценарий первого freeze с исходным private hardlink, отдельной output-копией и удалением/заменой source больше не теряет уже установленную закрытую связь.
2. Второе P1 закрыто в отдельно согласованной границе. `nativeResourceHandleIdentity()` получает identity от `opened.stat()`, затем однократно вызывает `regular()` для актуального pathname и сравнивает текущий dev/inode с handle. Постоянная symlink/ancestor подмена и чужой открытый handle отклоняются до чтения bytes, заполнения snapshots или выдачи DTO. Этот же шаг происходит перед первым logical-source попаданием в `physicalSnapshots`. На обычном `snapshots` hit нет FS equality; переданный role predicate проверяет сохранённую identity. Сравнения с capture/seal inode, size, mtime/ctime или SHA не восстановлены; второго post-read resource fence нет. Защита от adversarial double-swap не заявляется и не является условием этого APPROVE.
3. P2 закрыто. На внешнем входе `withCapturedOperation()` перебирает `complete.outputs` и проверяет конечные пути через `regular()` до consumer callback. Проверка действует и для ordinary документа без ресурсов; повторный output обход на каждом getter не введён. Scoped seal сохраняет отдельную regular проверку outputs.

Сверены реальные типы и обязательные readers: resource callback остаётся `Promise<{bytes: Uint8Array; identity: string}>`; Native resource inventory и Assignment Body не производят SHA-поле; PL декодирует реальные bytes без требования `r.sha256`; Download читает `target/bytes`. Assignment Body и condition materialization передают `roles.assertIdentity` внутрь общего reader. Перед использованием cache сохранены owning-document witness и role path checks. Generic публичные resource/document/condition маршруты assignment операции остаются закрыты. Нового самостоятельного нарушения этих границ в адресной final delta не найдено.

Новый `tests/native-output-resource-paths.ts` использует настоящий filesystem и production helper. Он проверяет отказ closed alias после удаления и замены source, чтение законно заменённого regular output, отказ чужому handle и постоянной symlink подмене после open. В существующий `native-operation-a3.ts` добавлен ordinary external-entry отказ HTML symlink с восстановлением исходного output, без дополнительного render для этого случая. Эти проверки описывают полезные наблюдаемые результаты; они не доказывают полный race proof, а filesystem fixture не является публичной end-to-end assignment приёмкой. Ни один fixture reviewer не запускал.

Собственный origin не сдвинут: перед окончательным решением повторно прочитан весь первичный интервал reviewer. `turn_context` на строках 8, 155 и 175 имеют согласованные верхние и вложенные `gpt-6.1-sol/high`; собственный chat/path и origin остаются указанными выше. Допуск PASS, обновлённое evidence — `native-final-outputs-review-model-admission-20261104.json`.

Компилятор, tests, render/Native, inspect, hash, build и сеть в этой рецензии не запускались; реализация и Git не менялись. Историческое отсутствие приёмки первого freeze не переписано. Текущий итог — **Source APPROVE / runtime PENDING**; дальнейшие ограниченные graph/pure и tiny public CLI проверки назначает координатор отдельно.
