# Download ownership snapshot — независимая Source рецензия

**APPROVE scoped.** Конкретных блокирующих findings нет. Base Download `e0bddeb9e8574710c02f282d216e63f8932b4b0c`, actual diff ровно `infrastructure/runtime.ts` (+3/-2). Прочитаны diff, collect/pack/final-publish paths, настоящий ownership reader, writer report/patch и first/final compiler evidence. Reviewer новых запусков и Source edits не делал.

## Snapshot и все branches

После `inspectOwnedRequests(root,sources)` строки281–283 синхронно сохраняют прежний JSON signature и новую string projection `owned.files.map(file=>file.source)` до следующего await. Источник **реальные owned request files**, не полный catalog/inspect sources list. `ownership.ts` проверяет field source как string, принадлежность allowed sources, canonical paths/regular files и связь имени заявки с source; итоговый OwnedRequestState строится самим reader. Проекция не приписывает тип внешнему JSON и не добавляет validator.

Map копирует primitive strings, сохраняя порядок и любой повтор без sort/dedupe. Reader сам выдаёт files в порядке отсортированных directory entries; строгая source→SHA1-filename связь ограничивает возможность реальных duplicates, но новый код не вводит дополнительного предположения об уникальности. Для этих constructed plain objects/strings прежний JSON stringify→parse→map давал ту же string sequence. Unicode/escaped string значения не нормализуются и не меняются.

Все нормальные collect returns расположены **после** присваивания обоих snapshots: ordinary `!operation→pack`, native без projectRequests→pack, native project batch→withProjectContracts callback→pack. В finishInternal есть ровно два взаимно исключающих способа вызвать collect (native callback или ordinary); nested pack/batch не переприсваивают snapshot. Нет успешного early return до owned inspection. Любая ошибка до/после присваивания пробрасывается через await и не достигает compare/publish; начальное `ownedSources=[]` не становится обходом ошибки.

Empty owned.files даёт `ownedSources=[]`, как прежний parse/signature projection. Если request directory отсутствует/пуст, reader возвращает тот же empty state. Конечный re-inspect всё ещё использует **узкий owned-source список**, поэтому новая чужая source заявка не становится разрешённой только потому, что была catalog input. Request reader принимает список для validation/Set и не сортирует/не мутирует его.

## Публикация и guarantees

После successful collect по-прежнему выполняется существующий повторный `inspectOwnedRequests`, затем точное JSON.stringify сравнение с первоначальным signature; diagnostic `Заявки изменены перед публикацией` и порядок перед publishArchives не менялись. Удалён только повторный JSON.parse ранее собственного signature и callback any. Existing bytes/source/path/artifact/hash signature checks, request parsing, архивы, receipt и publishArchive error paths сохранены. Нет нового guard, parse, IO, revalidation pass, schema/cast либо framework. Это не удаление самой ownership signature гарантии и не полная типизация существующего JSON reader.

## Evidence и границы

Первый affected check FAIL0.9098s/22 diagnostics из-за неполного выбранного map: отсутствовали literal sibling Core/PL/QRC public prefixes. Это сохранённая ошибка checker composition, не отсутствие реальных producer types; Source для обхода diagnostics не менялся.

После отдельного разрешения root final runtime-types check **PASS4.5826s**, cap10/retry0: [download-ownership-snapshot-final-check-20261104.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/download-ownership-snapshot-final-check-20261104.json)/`compiler-final-20261104.log`. Map использует тот же actual public-prefix способ, что existing `tests/contract-consistency.ts check(root)`, и реальные extension dirs плюс bare course-core/pl-native/stdlib. Никаких stubs/copied declarations/symlinks/нового framework. Existing runtime-types импортирует actual runtime и сохраняет NotAny/DTO misuse checks. Source между first/final compilation не менялся; это changed-composition check, не повтор unchanged green. Runtime ownership/ZIP integration не выполнялась и не заявляется; столь маленькая замена собственного parse на синхронный checked projection Source-supported.

Candidate ready для root приёмки/commit, без claims hosted/integration PASS или measured speedup. Workflow/pins/installed/courses/state не менялись reviewer; no runs/commit/push/network. Own primary full-origin admission: 38 turn_context, строки8–4606, все top/nested gpt-6.1-sol/high; origin 01a1a25f-b0fb-7fd2-b244-83933af77834, latest 01a1a5c6-3074-7701-8dbd-0032f7739430. Globals не менялись; hidden serving tier не удостоверяется.
