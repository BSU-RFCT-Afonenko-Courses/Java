# Независимый review типовой границы Site → Core/PL

4 ноября 2026. Целевой diff Site `e8b345cec235c2c8438c479892f6bca2a4ce3ee7` → `d16f2322c1da3c96c2e93d32225f5428ae347756`.

## Собственный первичный допуск

Агент `/root/review_pl_legacy_types`, chat `01a1a348-784d-7771-a540-bbcf7ed8735e`, parent `01a1a142-7adc-7371-9765-4871d807dfcd`. Исходный turn собственного интервала `01a1a348-7879-7070-a59e-6a29186a8ad2`, текущий Site review turn `01a1a4d1-1fdd-7e62-b29a-98121fdb3702`. Первичный файл `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T00-19-58-01a1a348-784d-7771-a540-bbcf7ed8735e.jsonl`: session_meta строка 1, все собственные turn_context строки 8/133/170/243. Top-level и nested settings каждой записи подтверждают `gpt-6.1-sol/high`, собственные ID/путь согласованы. Исходная точка не сдвигалась. Назначен Standard; скрытый серверный tier первичные записи не удостоверяют. Глобальные настройки не менялись.

Допуск подтверждён до содержательного review production chain. Весь исходный интервал, включая текущий Site turn, согласован с назначением; исходная точка и собственные ID не подменялись.

## Решение

**APPROVE для Site `d16f2322c1da3c96c2e93d32225f5428ae347756`.** Конкретных блокирующих замечаний в согласованном type-only scope двух файлов нет. Типы соответствуют настоящим Core/PL producers; новое runtime доверие внешнему JSON, export validator, guard или требование неизменности не вводится. Полная типизация Site/провайдеров и квалификация загружаемой installed версии этим verdict не заявляются.

Прочитаны exact diff, `site-typed-core-pl.md`, Site `AGENTS.md`, `spec/index.md`/`spec/contract.md`, реальные затронутые callpaths и актуальные public signatures Core/PL. Compiler/tests/render/inspect/hash/build/network не запускались, прежние successful checks не повторялись. Source/state/installed/courses reviewer не менял, commit/push не выполнял. Единственная запись — этот отчёт вне Source.

## Реальное покрытие типов

- `sibling-contracts.ts:1–9,21–25` выводит Core/PL module types через `typeof` настоящих public modules и переэкспортирует реальные Core `NativeCatalog`, `NativeOperation`, `NativeReleaseRequest`. Новых вручную скопированных operation/proof DTO нет. `compose.ts:39–73` связывает два известных имени lazy loader с этим map, сохраняя прежние runtime namespace/path/import и missing-file обработку. Dynamic import по URL сам не доказывает форму произвольного установленного модуля; здесь это статический contract доверенного sibling provider. Нового runtime export validator ради статической типизации не требуется.
- `compose.ts:134–158,167–190,302–319`: typed PL `prepareNativeAdapter`, Core `withCurrentNativeOperation` callback, operation catalog/metrics и `validateRelease` используют реальные API. `request satisfies NativeReleaseRequest` проверяет фактически собираемый expectedSources input, не приводит raw JSON к proof и не добавляет runtime parse. Actual PL preparation signature принимает `PublicCore` и `NativePendingOperationExpectation` с `profiles?:string[]` (`native-adapter.ts:134–139`); это не локальный фасад `any` вместо provider contract.
- `NativeSiteRecord` (`sibling-contracts.ts:13–19`) берёт поля из existing Collection Record, `Workspace['selectedSources']` и `Project['to']`. Project выводится из настоящего constructor с `default|html|revealjs` (`config.ts:13–21`); selectedSources проходит прежний native input reader, возвращающий `Promise<string[]>` (`:347–350`). `catalogCoverage` получает actual Core catalog и этот узкий consumer view, а не самостоятельную модель курса.
- Typed `coreProof` выводится из `operation.validateRelease`; он не заменяется локальной proof-аннотацией. После второго PL producer `proof:unknown` сохраняет действительную недоказанную границу (`compose.ts:187–203`). В PL `validateCourse` `operation:any` и cached `proof:any` остаются реальными upstream gaps (`native-adapter.ts:230–235,320–333,365–403`); Site их не скрывает cast/схемой/названием `NativeReleaseProof`.

## Единственное adapter! подтверждено producer invariant

В Site `compose.ts:181–196` optional adapter создаётся один раз в const binding. В первый `operation.validateRelease` передаётся либо `[adapter]`, либо `[]`; иных validators на этом callpath нет.

Реальный Core `operation.ts:3053–3056,3094–3099` передаёт именно этот validators список в `validateNativeRelease`, возвращая его genuine proof после штатной validation/guard (`:3155–3159`). `release/validate.ts:41–52` сохраняет executable descriptors, `:97–101` строит byName; `:218–227` требует installed adapter и executable validator для каждого `course.adapters`, иначе бросает `RELEASE.ADAPTER_UNQUALIFIED`. После фактического validator await и остальных проверок `:273–284` создаёт proof с `validatedAdapters: course.adapters`.

Следовательно, успешный первый Core proof с `prairielearn` невозможен для переданного Site пустого списка: optional adapter в этой ветке существует. `adapter!` снимает именно это отсутствие TypeScript correlation, а не квалифицирует внешний JSON или второй PL return. Приведённый invariant использует уже имеющееся поведение producer; новых immutable/authority требований к нему для приёмки diff не добавляется.

## Сохранение Java 6 flow и честный остаток

`compose.ts:457–475` сохраняет один whole-project child `quarto render . --to project.to` и прежние profiles/env. Record по-прежнему сохраняет независимые `selectedSources` и `to` (`:484–493`), owner scope и coverage source/output/format checks прежние (`:206–238`). Native pre/post calls, порядок первой Core validation, optional второй PL validation, validationPasses и metricsBefore/After сохраняются; ветвление по `coreProof.validatedAdapters` эквивалентно прежнему тесту первого proof до его замены.

Map намеренно не закрывает `project-download`, `reference-catalog`, `course-navigation`: в overload остаётся прежний `Promise<any>` (`compose.ts:45–54`). Download `observeOperation(value:any, proof:unknown)` (`:283–287`), workspace/config JSON, state JSON (`:544`) и historical collection reader остаются отдельным долгом. Некоторые поздние values поэтому ещё могут проходить через `any`; verdict не означает доказанную полную producer→state→consumer типовую цепочку. Новая `NativeSiteRecord` — тип потребляемых полей существующей записи, не заявление о validation сохранённого JSON. Нет основания навязывать здесь новый parse/guard или дополнительную hash/immutable обвязку.

## Evidence и граница Source приёмки

Отчёт владельца корректно описывает final actual compose entry + два temporary negative TS2322 (`projection:'full'`, `profiles:[1]`) в одном compiler invocation: ошибки только в двух отрицательных файлах, ноль production diagnostics. Общий exit1 не выдан за production-only exit0. Это адресная composition с actual Core `ad80be00d6617a427ba6ae7a3e17a55fa1ef5888` и PL `30edc03295e508f174c64b399f35b14ad37488fa`, не qualification всех Site routes. Reviewer эти compiler результаты не воспроизводил.

Source acceptance данного локального type-only commit отделена от UNKNOWN штатного hosted CI gate/pins. APPROVE не подтверждает совместимость иных установленных sibling версий, push/install/release либо новые runtime гарантии. Конкретного незакрытого остатка в согласованном двухфайловом diff не найдено.
