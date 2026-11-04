# Native publication: Source Download/Site

> Root qualification update: final independent Source **APPROVE_SOURCE**, affected combined compiler **PASS** (34roots, r2 4.875085435s; total9.800762860s/20). Four exact local commits saved in [commit evidence](native-publication-local-commits-20261104.json). One bounded Native fixture **BLOCKED before publication assertions** at Quarto Sass KV; runtime **NOT QUALIFIED**. No push/install/production. Earlier author statements below retain their original evidence/time scope.

**Consumer Source frozen для одного root combined compiler/review; compiler/runtime acceptance ещё не выполнена.**
Работа выполнена `/root/native_publication_consumers`, single writer Download+Site,
без subagents/stage/commit/push. Начальные clean HEAD подтверждены:
Download `d41705fe32b2e6c97c4944219531e405a091aebd`, Site
`85080c730ad54c8fd81fa4a3c7c806a8771c4f68`. Существующие worktrees, PR,
UUID/native IDs/results/student files сохранены. Core/PL/Performance/Platform,
курсовые исходники, DB/cache policy, production и mainstate/audit не менялись.

## Собственный допуск

[Primary admission](native-publication-consumer-source-20261104-admission.json)
ссылается на собственный primary session `01a1a634-c243-7c83-a0ed-b89c7035ebf1`:
`session_meta` ordinal0 содержит `/root/native_publication_consumers`,
`turn_context` ordinal7 — `gpt-6.1-sol`/`high`. Standard назначен root;
скрытая маршрутизация независимо не удостоверяется. Globals не менялись.
Прочитаны brief, approved design/delta, обе AGENTS/spec и локальная canonical
копия русскоязыкового правила. Бюджет compiler/runtime принадлежит root.

## Реальные API и ownership

Использован [actual handoff](native-publication-api-handoff-20261104.md), а не
локальный DTO/stub. Download импортирует `NativePublication`, `ProjectPlan`,
`ProjectContractContext` и Core module type из actual Core public exports.
`prepareNativeDownloads(publication, options)` получает root/outputDirectory из
Core getters, profiles/projection из identity. Native options содержат только
Download destination/URLs/referenceCatalog/submission; submission type выведен
из actual `materializeProject` bindings. Все document/condition/inventory,
project-contract contexts, plans и materialization helpers завершаются внутри
живого callback. `packageArtifact`/ZIP CRC остаются прежними.

Draft создаётся отдельной top-level функцией, замыкающей только собственные ZIP
bytes, root/destination, request snapshot и число готовых архивов. В нём нет
Core/PL methods, project contexts или callbacks. Publish синхронно меняет state
до первого await: `prepared → publishing → published|failed`; повторный/reentrant
publish отказывает. `discard` из prepared переводит в discarded, после
success/failure освобождает buffers, во время publishing отказывает. Он не
удаляет outputs. Request snapshot повторно проверяется перед actual writer;
это existing ownership currentness, не новый certificate.

`clearOwned` вызывается только archive writer. Именной manifest записывается
первым; partial write failure сохраняет имена для следующей обычной cleanup.
`resetDownloadRequests` удаляет только requests directory с прежними path guards,
без output inspect/ZIP cleanup. Артефактные receipts/SHA/release construction,
readers/schema cleanup guards полностью удалены из production Download.
Исторические generated receipts не мигрировались и не удалялись.

`finishDownloads(root, OrdinaryDownloadOptions)` — самостоятельный ordinary
resource режим; public native flag/projection/expectedDocuments/observe callback
удалены без alias. Native CLI orchestrates actual Core publication, inspection
только активного PL, preparation, успешный closing и publish. Download runtime
не загружает PL и не делает release/course qualification.

Site с Download и без него использует один actual `withNativePublication` route.
Независимые selected sources переданы Core selection; прежний catalogCoverage
сравнивает actual source/format/output set. При активном
`publication.course.adapters` загружается actual owner-local PL и вызывается
только `inspectPublication(publication)`. Установленный inactive PL не вызывается;
missing active module даёт `SITE.SIBLING_MISSING`, existing import/init failure
сохраняет cause. `ownerRelease`, validateRelease/validateCourse и proof bridge
удалены. Sibling map использует actual Core/PL/Download exports.

Handle хранится в локальной typed pending cell до await outer closing. После
успешного closing публикуется draft; callback/closing/publish rejection вызывает
discard, cleanup не маскирует primary cause. Composition содержит owned
catalog/course/optional inspection, без release report/proof/counters. Ранние
Site cleanOutput/render/mount-copy effects сохранены: общего rollback нет.
Boundary гарантирует отсутствие **новых archive writes этого draft** до closing.

По уточнению root удалена и ordinary pre dependency `PL.prepareNativeAdapter`
из production Site и authored site-owner-release fixture. Producer подтвердил:
Core adapterInputs независим от prepared PL transport. Full-course API остаётся
у его отдельного owner и не изменялся consumer работой.

## Direct Source closure и fixtures

`ownership.ts` теперь сохраняет уже проверенную configuration как unknown;
потребитель не делает второй JSON.parse и не объявляет raw Request/config полным
DTO. `application/publish.ts` выводит source/resources/artifacts из owner request,
оставляя configuration unknown до existing configuration parser. Existing
source/resources/artifacts predicates/diagnostics не менялись. Narrow ordinary
Quarto-inspect consumed containers проверяются до использования; full config DTO
не выдуман. Site existing broad config/QRC/navigation any debt вне этого slice
не объявлен исправленным; новый Core/PL/Download contract имеет actual types.

Существующие direct fixtures адаптированы: удалены receipt SHA/proof/pass counters,
сохранены actual ZIP bytes/README/resource/URL/privacy/source-format assertions.
`public-runtime` использует actual module types и сохраняет profile/projection/
format/output drift, generation switch и foreign symlink controls. Closing
negative задаёт свежий destination, сохраняет handle, проверяет rejected publish
после discard и отсутствие нового ZIP; повторный publish success draft отказывает.
Это Source assertions, ещё не результаты runtime.

`project-contract-consumer.ts` — дополнительно найденный прямой native
finishDownloads caller; root подтвердил его включение в semantic closure. Он
теперь использует actual Core callback/draft/close/publish с сохранением проверки
реальных starter bytes. `ownership.ts`/Request port — согласованное narrow
дополнение для удаления any-producing/double-read seam, не новая policy/schema.
`site-siblings` прямо переименован на reset API с сохранением foreign-error control.

`site-owner-release root-pl` сохраняет два independently authored owners/captures:
root PL без Download и tasks child с Download. Конфиги заданы до render; capture
не подменяется между ветвями. NoPL/inactive Native acceptance этой одной будущей
invocation не заявляется. Шесть известных Site consumers читают course/catalog/
inspection вместо proof. Existing project-native broad NativeOperation отдельно
сохраняет effectiveMetadata/project-context/materialization controls; full release
validation из ordinary fixture comparison удалена, новые capabilities view не
фабрикуются. Historical legacy/private fixture model-installed не объявлен
канонически runnable; удалён только retired receipt reader.

## Проверки и ограничения

Выполнены Source reads, адресные searches и `git diff --check` обоих trees (rc0).
Ни compiler/tests/Native/render/inspect/hash/build/install/network/DB/production
не запускались. Hash-вызовы в existing ownership/fixture Source не исполнялись;
per-ZIP SHA code удалён. Это не compiler GREEN, runtime qualification, выпуск,
production rollout, performance measurement или full-course grading acceptance.
Root должен выполнить один согласованный affected compiler cap20/retry0 после
обоих Source freezes и решить отдельный runtime cap60 после review.

Незакрытого API handoff gap в consumer Source не известно. Сохраняются реальные
Core currentness/acquisition/A1/path/role guards, обычный request snapshot и
materialization costs. Native cache freshness и arbitrary unmarked computed text
не квалифицированы. Учебные policy/баллы/UUID/data contracts не менялись.

## Patch и изменённые файлы

- [Download patch](native-publication-consumer-download-source-20261104.patch)
- [Site patch](native-publication-consumer-site-source-20261104.patch)

### Download

- `README.md`
- `_extensions/project-download/application/publish.ts`
- `_extensions/project-download/entrypoints/post.ts`
- `_extensions/project-download/entrypoints/pre.ts`
- `_extensions/project-download/infrastructure/runtime.ts`
- `_extensions/project-download/infrastructure/siblings.ts`
- `_extensions/project-download/ownership.ts`
- `_extensions/project-download/public.ts`
- `docs/public-runtime.md`
- `spec/contract.md`
- `spec/index.md`
- `tests/model-installed.ts`
- `tests/model-receipts.ts`
- `tests/project-contract-consumer.ts`
- `tests/public-runtime.ts`
- `tests/runtime-types.ts`

### Site

- `README.md`
- `_extensions/course-site/application/compose.ts`
- `_extensions/course-site/application/sibling-contracts.ts`
- `docs/public-operation.md`
- `spec/contract.md`
- `spec/index.md`
- `tests/legacy-harness-mapping.md`
- `tests/site-child-profiles.ts`
- `tests/site-domains.ts`
- `tests/site-native-pipeline.ts`
- `tests/site-owner-release.ts`
- `tests/site-project-native.ts`
- `tests/site-public-operation.ts`
- `tests/site-siblings.ts`

## Узкая correction после freeze для первого combined graph

Root отдельно разрешил заменить ровно три type usages `NativeOperation` на actual
`NativePublication` в `download/tests/runtime-types.ts`: import, resource inventory
и condition ReturnType. Это direct compiler consumer нового sibling export, без
compat alias/casts и без production mutation. `runtime-types.ts` и существующий
`contract-types.ts` включены в proposed first combined graph. Consumer повторно
заморожен после этой correction. Compiler/runtime не запускались.
