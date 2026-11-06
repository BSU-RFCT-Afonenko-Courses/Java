# Два внутренних повтора Core fences

3 ноября 2026. Bounded Source diff поверх независимо принятого owner DTO commit `3af7600cd44c562cb781865f02f94833c4690ada`. Основание — existing actual830 trace в `sp-native-source-fence-cost.md`, не новый benchmark. Root разрешил ровно две точки в `runtime/operation.ts`; установленные Java/SP/PL/Core, учебные данные, нормативные документы и глобальные settings не менялись.

## Изменение и сохранённые границы

- В prepared assignment пути удалён повторный full `assertSources(state)` сразу после внутреннего `bindOwner`/передачи relevantInputs и ownerInputs. Это830:642, в данной Source базе бывшая строка695. Initial snapshot, Source rows/membership/exclusions, after-inspect fence и after-caller-prepare fence не меняются. Между caller preparation и этим местом нет нового inspect/engine/external caller; проверки lease/actual child relevant scope остаются.
- На входе `sealCaptureScope` full `assertPrivate(state, true)` заменён существующим `assertPrivate(state)` —830:1915, текущая строка1991. Root/pointer/runtime signature/environment проверки остаются; повторный content digest executable на этой ранней внутренней точке снят. Полный runtime/content/merged implementation guard остаётся в closing transaction.

Closing transaction в текущем `operation.ts:2131–2143` не менялся: reservation current → full `assertPrivate(state,true)` → full `assertSources(state)` → `reservation.status="sealed"` → persisted complete. Проверка output/resource signature+bytes до этой transaction также не менялась (`:2099–2120`). Initial native HTML hashing, merged runtime dependencies, child coverage/identity/source-witness checks, private/resource/role/currentness guards и девять actual local boundaries не изменены. Нового cache, registry, proof, receipt, Source classifier, fake complete или публичного API нет. Production diff — одно удаление Source call и снятие единственного аргумента `true` на entry; прочие production строки прежние.

Возможное внешнее изменение после снятой ранней точки не разрешается. Relevant Source/config edit по-прежнему замечают local guards; edit за relevant scope должен получить отказ на закрывающем full Source fence. Изменение executable/native implementation между entry и closing обнаруживает сохранённый full private guard. Закрыть operation с изменёнными output/resource bytes по-прежнему мешает неизменённая output проверка. Изменилась ранняя стоимость/время обнаружения, а не разрешение публиковать изменённое состояние.

## Проверка

Root отдельно принял data-only negative вместо синтетических producer/complete artifacts или нового seam ради двух строк. Новый `tests/assignment-closing-source-fence.ts` вызывает настоящую existing `assertSources` на собственных малых файлах. Baseline rows получены через lstat/signature; hash поля явно `not-read-by-signature-fence`. Он не вызывает snapshot, native inspect, render или hash, не создаёт private state/child/complete/sealed и не заявляет genuine producer.

Meaningful negative: после принятия baseline и через await изменяются unselected QMD/vendor bytes, либо добавляется файл в невыбранный каталог, либо vendor path заменяется symlink. Relevant-only fence **проходит**, но full closing fence отказывает `OPERATION_CHANGED`. Это реальное различие coverage, а не assertion числа guards/вызовов или текстового расположения строки. Selected Source/config mutations также отвергаются; неизменённый S проходит. Положение full fence до sealed подтверждает неизменённая production transaction и независимый review, **не actual seal transaction execution данного test**.

Минимальный пакет из3 pure checks:

|Проверка|Исход|Время|
|---|---|---:|
|Новая `assignment-closing-source-fence.ts`|PASS: source/config/unselected/vendor/addition/symlink и unchanged control|0.999s|
|Existing `native-empty-supporting-scratch.ts`|PASS: exact empty scratch removal и Source/data/additions/aliases negatives|0.968s|
|Existing `assignment-seal-observations.ts` без generation аргумента|PASS: merge/dedup/conflict observed dependencies и small-file runtime implementation signature currentness|0.983s|

Суммарно2.950s. Логи и commands/codes: [core-bounded-fences-regression.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-bounded-fences-regression.log)/`.json`. Existing scratch exception не расширялся. Последний test проверяет реальную existing dependency currentness функцию на собственном временном файле; это не повторный digest actual Quarto executable. Existing `native-capture-fences-a4c.ts` output-edit control требует настоящих child renders: он прочитан, но по root ограничению не запускался. Output guard/его placement и closing full runtime identity остались кодово неизменными; новая runtime проверка этих двух actual путей здесь не заявляется. Новый test входит в existing canonical assignment glob без правки wiring.

`git diff --check` PASS; новый test отформатирован. Full suites, прежний Core→PL compiler graph, actual render/inspect/hash/build/SP/Java pipeline не повторялись. Для удаления одного call и boolean аргумента прежний compiler GREEN не воспроизводился. Tests проверяют сохранность integrity coverage; это не измерение производительности и не тест прежнего количества fences.

## Чего ожидать от счётчиков

В existing successful830 generation `45515b7e-dc8b-48d6-87ad-c6af6428a2f5` Source5,134,554B/873rows хешировались **один раз**, не четыре. Четыре full Source fences делали signature/path/membership проверки. Данный diff не снимает initial walk/hash и не сокращает проверяемый S.

Для того же неизменного состава S structural ожидание от capture counters: один full Source pass меньше; при прежних133membership directories это `fullSourceFences4→3`, `sourceDirectoryReads710→577`. Один runtime executable digest меньше: `runtimeHashReads4→3`, при прежних8061B — `32244→24183B`. Initial Source hash rows/bytes, fullWalk1, local9, output14 и child1 должны сохранить прежнюю семантику. Эти цифры — ожидание по exact trace, **не результат нового запуска**; фактический новый установленный Source имеет свою версию/байты и может изменить состав или IO. Post-seal public operation guards не входят в persisted capture counters и не объявляются удалёнными.

Ускорение относительно38.195870446s baseline не заявляется; seconds/percent нельзя вывести из снятого directory pass. Root после независимого review сам решает одну сопоставимую paired SP acceptance. Она здесь не запускалась.

## Допуск и передача

Собственный original turn `01a1a21a-f52f-73a3-85e9-758306144504` сохранён. Полный primary interval теперь10records/9unique turns, current `01a1a28f-fe0a-7bb0-8544-d2e59a2728ca` на строке1550; top+nested model/effort везде `gpt-6.1-sol/high`. Standard назначен root, service_tier отсутствует; скрытый серверный tier не утверждается. Globals/normative untouched.

Source commit `d7cc10eaa21fa35bbe32d73cc4afa216002393da` поверх `3af7600cd44c562cb781865f02f94833c4690ada`:2файла,+109/−2, из них production только две указанные точки. После commit Core Source clean, staged diff check PASS. Точный диапазон независимого review — `3af7600cd44c562cb781865f02f94833c4690ada..d7cc10eaa21fa35bbe32d73cc4afa216002393da`. Установка/push/PR/release/production в этой фазе не выполнялись.
