# Стоимость source fences успешной SP generation

3 ноября 2026. Только read-only trace exact pinned Core `830381697beea4106655965e10605cc13987199b` через git show и PL `da2ad6b6c00891590e59e5abccf3d1eb4ff552f7`. Текущий Core Source writer/ownerDTO diff не читался и не оценивался. Использованы existing complete/output-evidence успешной generation `45515b7e-dc8b-48d6-87ad-c6af6428a2f5`; новых snapshots/hash/inspect/render/build/bench/tests нет. Единственная запись — отчёт.

Связь со старой картой decision74: `java-improvement-research-20261103/docs/research/requirements-and-heavy-operations-20261103.md:9,31,68`, `heavy-operations-20261103/core.md` C01/C02/C03/C07. Старое исследование уже различало initial whole-owner snapshot, последующие metadata fences и runtime/output hashing; эта работа уточняет реальные частоты успешной установленной830generation, не повторяет общий аудит. R3/report.md:64 сохраняет meaningful initial source/config plan и final publication границы, разделение профилей/ролей и resources/privacy.

## Область счётчиков

Источник: `system-practice/tasks/_generated/course-spec/native-ts/45515b7e-dc8b-48d6-87ad-c6af6428a2f5/complete.json`, state.metrics; краткая сохранённая копия `sp-native-qrc-ready-output-evidence.json`. Core capture complete: fullSourceFences4, localSourceFences9, sourceDirectoryReads710, fullWalks1, sourceHashReads873/sourceHashBytes5134554, runtimeHashReads4/runtimeHashBytes32244, outputHashReads14, childRenders1, inspectCalls1. Это persisted capture/seal counters, не total всех PL/QRC/Site процессов или всего38.195870446s export.

После seal PL открывает current native operation: operation.ts:2463 entry guard(true), :2452 exit guard(true), :2381 вызывает full assertSources и :2359 assertPrivate. Эти последующие callback counters не записаны обратно в complete.json; их нельзя выдать за часть исходных4full. QRC собственный inspect также не входит в CoreinspectCalls1. Нельзя по complete4 утверждать «всего4fences за весь экспорт».

## Exact четыре full source calls

Все ссылки на Core пути ниже относятся только к830 Git blob.

| № | Call site | Что охраняет | Оценка |
|---|---|---|---|
|1|runtime/assignment-admission.ts:203, после snapshot:196 и inspectNativeOwner:197|Проверяет, что owner inspect относится к тому же S до передачи owner в внешний caller preparation callback:214. Ловит edit во время inspect|Сохранить meaningful observed-owner/role boundary|
|2|runtime/assignment-admission.ts:240, после await caller prepare:214 и проверки selection|Внешний callback мог await/изменить source/config; его selection не должен незаметно получить другой S|Сохранить meaningful caller/owner boundary; actual PLcallback лишь clone/declarations, но публичный Core callback не ограничен этой реализацией|
|3|runtime/operation.ts:642, prepared path после bindOwner:639|Снова проверяет тот же prepared.source baseline после внутренних pointer/reservation writes и binding readonly owner; нового inspect/engine/caller prepare нет|Кандидат удаления внутреннего повторного full прохода; relevant source edits всё ещё проверяются lease/child local guards, все baseline paths — closingseal|
|4|runtime/operation.ts:2055 внутри withWriter перед reservation.status=sealed:2056|Closing guard после actual child pre/post, outputs/resources verification и merge; не допускает публикацию Source edit/new file/directory/symlink даже за пределами relevantInputs|Сохранить обязательный полный closing transition|

Первые3 уже видны в saved state.json: fullSourceFences3/sourceDirectoryReads532; child receipt остаётся3full, и seal даёт4. Snapshot membership содержит133каталога: initial snapshot133 readDir +3×133 до child =532. Complete:133 initial +4×133full +9×5local =710readDir. Здесь это exact arithmetic данного S; будущие empty scratch удаления могут менять фактическое число readDir, фиксированное133 не новый контракт.

## Откуда873 чтения и5.13МБ

В saved S ровно873distinct rows, сумма size из уже сохранённых signature tuples равна5134554байта. `source-snapshot.ts:94–133` делает один walk и один readFile+SHA256 на regular row (:111–128). Эти873 — не873×4: assertSources:194–204 сравнивает lstat/realPath/type/signature; :235–274 читает membership directories, SHA source bytes повторно не читает.

Классы вычислены только из saved rows/path/signature.size, без открытия файлов S:

|Класс|Файлов|Байт|
|---|---:|---:|
|Installed `_extensions`|763|4849490|
|Authored QMD, включая невыбранные карточки|39|202505|
|Project trees|58|59864|
|Материалы ZIP|4|15936|
|YAML/config|6|4534|
|Прочее|3|2225|
|Всего|873|5134554|

Extensions: Core295files/1781469B, PL270/1821519B, QRC94/561366B, Navigation68/549260B, Presentation13/36594B, Download23/99282B. Поэтому большая часть initial bytes — installed implementation/vendor, а не7configuredQMD. Это один accepted baseline, а не повторное per-question чтение. В данной партии исключать extensions/невыбранные QMD/projects из S нельзя: такой другой closure потребует отдельного инженерного решения и доказательства edit/resource/provider boundaries; фильтрация ради меньших цифр не предлагается. Никакого нового cache/receipt/registry не нужно, initial snapshot остаётся.

## Девять local calls

Exact saved deltas: `pre-fe723fa4-69d0-4da5-ac04-68c416875d35.metrics.json` local1; child receipt local2 (pre+post); pending-d110…metrics local5 и pending-6d8…metrics local2; complete local9. Трасса publiclease/actualPL объясняет семь parent local calls без нового probe:

- withPreparedLease entry `prepared-operation.ts:102`:1.
- PL `native-adapter.ts:156,158` lease.assert вокруг saveRequest в excluded `_generated`:2.
- lease.captureInputs `prepared-operation.ts:85`: ещё1.
- Core assignment beforeChild `capture.ts:35–36`, вызванный реальным launcher `assignment-execution.ts:237`: ещё1. Эти5 попадают в первый pendingdelta.
- PL native-entry.ts:297 explicit lease.assert после child и withPreparedLease exit:107: ещё2, второй pendingdelta.
- Child pre `operation.ts:1236` и child post:1860: по1local.

Все prepared lease assertions используют guard operation.ts:824–844, selected relevantInputs; child pre/post ограничивают config+documentInputs. Для данного owner local corridor содержит5baseline directories. Они проверяют не только Source: рядом остаются operation lifetime, pointer/reservation phase/identity, environment/cwd и state-file replacement. Поэтому удалять целый guard ради SourceIO нельзя. Часть parent assertions вокруг собственного saveRequest и подряд lease/launcher boundary выглядит объединяемой, но затрагивает публичный lease lifetime/caller awaits. В первый предлагаемый Core diff это не входит; local9 сохранены. Closingfull2055 остаётся проверкой любых edits за relevant scope.

## Runtime4 и output14

Native executable content identity —4чтения по8061B, всего32244B: `assignment-admission.ts:179` initial runtime до owner inspect, :204 после него; `operation.ts:1915` seal entry assertPrivate(true), :2054 seal closing assertPrivate(true). assertPrivate:395–400 передаёт full в nativeRuntimeIdentity:233–271; полное чтение digest только приfull. RuntimePathReads120 — отдельные implementation signature checks; это не120полных hashes установки.

Initial runtime+послеinspect охраняют внешнее исполнение owner observation. Sealentry1915 и closing2054 — два content reads одного executable в одном owner closing function; между ними внутреннее чтение/merge child receipts и проверка outputs/resources. Closing2054 дополнительно видит merged native implementation files, которых entry может ещё не иметь. Кандидат: entry1915 сделать assertPrivate(state) с прежними pointer/type/signature/runtime checks, content digest оставить на closing2054. Это отложит обнаружение executable edit до той же закрывающей транзакции, не допустит publication edit.

OutputHashReads14 =7initial native HTML read+hash в post `operation.ts:1616–1626` +7rehash перед seal :2023–2039. В reservation path exercise-index rewrite block:1806 не исполнялся; дополнительных index hashes здесь нет. Эти два7прохода пересекают native child/post → parentseal/process/hooks boundary, а не соседние getters. Пока сохранить: child-output ownership/inventory и конечная проверка изменившихся HTML/resources остаются meaningful. Прямое удаление7seal rehash ради цифр не предлагается.

## Один следующий bounded Core perf diff — предложение без кода

Один owner/file `runtime/operation.ts`, только prepared assignment/seal путь:

1. Убрать повторный `await assertSources(state)` на830:642 после bindOwner. Не менять initial snapshot/rows/membership/exclusions,203/240 и closing2055, relevant localguards и correlated generation checks.
2. В sealentry830:1915 заменить full assertPrivate(state,true) на прежний signature-only assertPrivate(state); финальный2054 оставить full. Не трогать source guards/runtime environment/executable canonicalization/merged native implementation checks.

Для неизменного данного S структурное ожидание persisted capture counters: fullSourceFences4→3, sourceDirectoryReads710→577 (один133directory обход снят), runtimeHashReads4→3/runtimeHashBytes32244→24183. sourceHashReads873/bytes5134554, fullWalks1, local9, output14, child1 должны сохраниться. Это ожидаемые изменения call counts, не измеренное ускорение/секунды/проценты и не гарантированный итог после иных Source edits. Не требуется новая cache key, proof/receipt или API.

Сохранность будущей адресной проверки: existing boundary controls должны продолжать отвергать Source/config/vendor/user edit до seal, arbitrary added file/directory, symlink/physical alias, changed runtime и child output; прежнее разрешение disappearance exact empty native scratch сохранить. Отдельно продемонстрировать edit в промежутке removed642→closing2055: завершение должно отказать до sealed/publication. Не повторять все suites или native pipeline лишь ради измерения; root назначает минимальные существующие controls и одну осмысленную acceptance попытку после review только если захочет выполнить diff. В этой задаче никакого кода или проверки не запускалось.

**Root decision point:** разрешить этот двухточечный operation.ts perf diff после окончания текущего ownerDTO writer с сохранением exact203/240/2055 boundaries, либо оставить830baseline. Initial873snapshotreads и14outputreads в этот diff не включать; новая общая архитектура не нужна.

Весь собственный исходный admission interval от baseline01a1a261-9811-7ca3-8a48-e4482c3beafc перепроверен: turn_context[8, 155, 244, 342, 393, 435, 484, 524, 617, 711], top+nested Sol6.1/medium PASS; Standard prescribed, globals untouched.
