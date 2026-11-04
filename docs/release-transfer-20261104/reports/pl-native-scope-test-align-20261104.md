# PL native scope: удаление устаревшего whole-owner ожидания

**Candidate к root review:** один existing testfile, `+0/−5`; production, runner, pins и остальные cases не изменены. Base PL `0ebb9f4e49201edede210b211d7f0e3ebf067412`; прочитанный accepted Core `5f4590aa4eb524830715f8020c8ff3c504197f73`. До изменения PL checkout clean. Commit/push отсутствуют.

## Причина

По сообщению root hosted PL CI `41805118136` дошёл после `178 passed / 0 failed` и successful publication до `tests/native-scope-integration.ts:325`, где получил `Missing expected rejection`; Installed Java/Gradle job SUCCESS. Это **сведения root**, новый CI log самостоятельно не скачивался и запуск не повторялся.

Файл `tests/native-scope-integration.ts` прочитан целиком. Его последние строки `323–327` читают и меняют `project/input.txt`, затем требуют отказ `Core.withCurrentNativeOperation(candidate.expectation, () => {})`. Изменение происходит **после** окончания previous operation callback. Новый callback не запрашивает project plans/materialization: short A1 acquisition/copy здесь нет. Поэтому это исключительно старое ожидание whole-owner/source-tree equality, а не проверка actual project bytes конкретного consumer.

Accepted Core изменил эту границу намеренно: `_extensions/course-core/runtime/operation.ts:2295–2310` выбирает actual document inputs и вызывает `assertSources(..., false, [...documents])`; `runtime/source-snapshot.ts:155+` сохраняет selected source/physical provenance, а не равенство всего дерева. Рядом в operation остаются private state, pointer, generation, environment/cwd, sealed phase и observed resource/output checks. [Принятый implementation report](core-native-bridge-implementation-20261104.md) отдельно фиксирует selected raw QMD guards и перенос actual project bytes в post-child A1 acquisition. Возвращать whole-owner hash/refusal ради этого test нельзя.

## Точный diff

Удалён весь пятирядный хвост: `readTextFile(project/input.txt)` → запись `DRIFT` → `assert.rejects(withCurrentNativeOperation(...))`. Replacement acceptance/drift/guard tests не добавлены. Исходная подготовка `project/input.txt` остаётся частью genuine fixture.

`tests/check.ts` прочитан полностью: он запускает typed contract checker, existing `.test.ts` и этот genuine ordinary integration file. Собственных whole-owner/S/drift assertions там **нет**; менять runner не требуется. Слова `immutable Core companion` относятся к pinned dependency input runner, а не к rejected course-tree equality, поэтому также не изменены.

Сохранены все предшествующие assertions файла: multiformat/empty-document business, exact-request reuse/currentness, plain DTO без authority, invalid readSet/missing metadata, source/witness/profile/generation mutants, detached business/input snapshots, изменённый generated candidate с exact-request reuse и refusal нового request, partial source coverage и injected `STALE` checkpoint с очисткой cached business. В частности, transport/callback identity refusal не подменён разрешением произвольного чужого origin. Lifetime/role/privacy production guards не менялись; новые утверждения об их qualification этим diff не делаются.

Полезные существующие проверки сохраняются, а fixture больше не мутирует unrelated project bytes только для удалённой гарантии. Никакие counters/call-count assertions и их прежние границы в этом пакете не переосмысливались.

## Дополнительный узкий pass перед следующим push

По повторному поручению root проверена именно executable цепочка `tests/check.ts`, не весь workflow/repo: `tools/check-contracts.ts`, непосредственные `tests/*.test.ts` и единственный прямой genuine Native script `native-scope-integration.ts`. У checker нет других прямых standalone Native integration scripts. Для `.test.ts` выполнен адресный Source поиск whole-owner/S/engine/codeCells/carrier и inspect/copy/fence metrics, затем прочитаны соответствующие подозрительные blocks; child invocation sites рассмотрены только для определения этой границы.

| Подозрительное место | Фактический смысл | Решение |
| --- | --- | --- |
| `native-scope-integration.ts:113–145`: `validates === 1`, `currents === 4` | Existing PL same-operation/exact-request proof cache и вызовы `assertCurrent`; не Core aggregate carrier/inspect/copy count | Сохранить; обход currentness не предлагается |
| `native-contract-batch.test.ts:11–23,80–85` | Synthetic batch задаёт `inspectCalls/fullWalks/sourceBytesRead/fullFences` как observations и проверяет их forwarding внутри/после callback. Число 2 — sentinel из mock, не требование к actual Core full fences | Сохранить |
| `native-reuse-refusal.test.ts`, `native-batch-checkpoint.test.ts` | Нет silent legacy fallback, unavailable/non-numeric observations не становятся нулём; exact capability выбирается вместо fallback, expired token остаётся expired. Числа вызовов считают synthetic dispatch по числу вызванных checkpoint, не Native work | Сохранить |
| `native-auxiliary.test.ts:113–137,168–232` | Environment/pre-post callback refusal и actual Git seed recipe/private objects; `DRIFT` не означает равенство всего course owner | Сохранить |
| `native-publication.test.ts:280–302` | Мутация непосредственно acquired PL binding/catalog/registry в синтетическом publication callback, отказ и discard stages; это owner-specific consumed inputs, не source-tree membership | Сохранить |
| `native-adapter.test.ts:126+`, `native-delivery.test.ts:35+` | Workspace `carrier` — submission options; static capture callback check не запрещает computed engines и не требует aggregate carrier | Сохранить |
| `tools/check-contracts.ts` | Проверяет имя владельца и отсутствие retired CUE dependencies; нет engine/whole-owner/count assertion | Сохранить |

Прямой child в `export.test.ts:1256` — `quarto pandoc … --to=json` для настоящей Table shape/namespace, не Core capture/inspect; child sites в answer/auxiliary/entry fixtures — Python/Git/entry admission. Дополнительного вызова genuine Native scope script через эти blocks не найдено. Поиск engine/codeCells/knitr/Jupyter/OJS запретов в непосредственно выбранных `.test.ts` не дал такого assertion.

**Итог узкого pass:** кроме уже удаляемого пятирядного хвоста, ещё одного явно того же obsolete whole-owner/engine-ban/aggregate-carrier count ожидания в этой прямой цепочке не найдено. Остальные standalone scripts workflow и Download не включены в это заключение. Это не доказательство полного PASS или отсутствия иной несовместимости. Candidate остаётся ровно `+0/−5`; дополнительных Source edits/tests не требуется.

## Evidence и пределы

Сохранён [exact patch](pl-native-scope-test-align-20261104.patch). Проверены Source diff/status: только `tests/native-scope-integration.ts`, пять удалённых строк. Native, compiler, unit tests, render/inspect/hash, сеть и CI не запускались; candidate **не объявлен GREEN**. Новый hosted результат и окончательная композиция PL/Download остаются за root после review/commit. Download consumer/pin в этом пакете не менялся.

Own primary origin `01a1a25f-b0fb-7fd2-b244-83933af77834`, Sol6.1/high полного исходного интервала; последнее адресное admission чтение подтвердило все 41 доступный top/nested `turn_context` как `gpt-6.1-sol/high`. Глобальные settings, main state, courses и installed copies не менялись.
