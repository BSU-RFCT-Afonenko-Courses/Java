# Ordinary Native publication: содержимое вместо сертификата

## Решение и точная граница

**Рекомендую один breaking vertical slice: обычная публикация Core → PL inspection → Download → Site больше не требует полной grading/seed qualification и не выпускает release/ZIP сертификаты.** Она проверяет публикуемое учебное содержание, аудиторию, реальные материалы и конечные пути. Подготовка Native PL курса со scoring/runtime/seed — отдельная операция, а не скрытый admission сайта.

Root принял этот выбор как самостоятельную задачу. Пользователь разрешил менять прежние технические API/semantics; compatibility adapters не нужны. Это design для независимой high critique, **не Source implementation/qualification**. Existing Native IDs/UUID, результаты, студенческие файлы и учебные цели не меняются.

Важно: `delivery.json` полного PL курса **не находится** в обычной Site/Download цепочке. Его files/deliveryHash/source/check/verification hashes и Platform/Gateway readers — другой проект. Этот пакет не решает основной вопрос свободного обновления Native PL JSON на действующем сервере. Одностороннее удаление delivery producer без direct Source readers не предлагается; прежние persisted native IDs/hash/history там сохраняются до отдельного согласованного проекта. Не притягивать обе ветви к общему DTO.

## Два других подхода

| Подход | Результат | Почему выбран другой |
| --- | --- | --- |
| Только убрать proof из JSON, оставив `validateRelease/validateCourse` | Исчезнут поля отчёта; grading/seed gate и повторные passes останутся | Косметическое удаление, не чистый ordinary contract |
| Одновременно переписать PL delivery, Platform/Gateway bindings и ordinary publication | Может снять immutable-course identity во всём собственном стеке | Это самостоятельный persisted-data/API проект. Без actual startup/sync/history reader closure нельзя обещать сохранение numeric parents; данному ordinary route он не нужен |
| **Выбранный:** отделить ordinary publication от course-export qualification и удалить её certificates | Оба Site маршрута имеют один смысл, готовые ZIP публикуются по реальным данным, не receipt | Самостоятельный consumer-closed результат без DB/deploy/cutover |

Это применение уже существующего brainstorming-процесса к прямо порученному design. Технические вопросы о backcompat не задаются: новое разрешение их уже сняло. Root организует независимую critique, затем выполнение в пределах согласованного design.

## Что сейчас происходит

Прочитаны actual публичные Core5f/PL4d/Download671/Site850 API и нужные callers; использована новая [Download→Site карта](native-publication-consumer-contracts-20261104.md), а не повторный consumer audit.

- Core `operation.validateRelease` сканирует installed contract names, запускает общий bank/assessment validation и adapter callbacks, физические path checks; возвращает `NativeReleaseProof`. Этот DTO не имеет криптографической подписи. Реальная assignment authority хранится в live operation/состоянии, а не в скопированном proof.
- Download `runtime.ts:165–195` делает partial `PL.inspectPublication`, либо Core-only validation. Потом повторно parses proof. В Site callback передаётся **report** `{proof,validationPasses,metricsBefore,metricsAfter}`, хотя callback называет его `proof`.
- Site с Download реально читает catalog coverage и сохраняет release report. Без Download `ownerRelease` делает Core pass, затем условно полный `adapter.validateCourse`: там дополнительно `assertNativeSeedTargets`, grading-component checks и prepared-request correlation.
- Download сохраняет archive SHA/release report в `artifacts-receipt.json`. Production cleanup/conflict readers читают только schema v1/v2. Именной `.project-download-manifest.json` отдельно обеспечивает owned ZIP cleanup. Существующие tests действительно читают receipt/proof, поэтому менять только три основных файла недостаточно.

## Целевой контракт и владельцы

**Core — владелец одного живого publication session и его данных.** Добавить `withNativePublication(expectation, selection, use)`. `expectation` переиспользует canonical `NativeOperationExpectation`, `selection` — существующий request expectedSources/courseId contract либо `undefined` для явно полного текущего catalog. Site передаёт независимый exact selected set; авторская CLI может просматривать весь завершённый owner без нового inspect. Это одна typed signature без compatibility overload. В одном session:

1. Приобретается текущий завершённый Native capture существующим Core механизмом.
2. Один раз проверяются Core-смысловые facts: exact document/format coverage, course/projection, уникальные упражнения/работы, declared targets, assessment members, statement visibility и демонстрационная public solution. Это выделенная общая часть нынешнего `release/validate.ts`, а не повторный вызов прежнего `validateRelease`.
3. Callback получает `NativePublication`: borrowed Core view с catalog/identity, typed course/adapter-input data и только уже существующими read/materialization операциями: document, condition, resources, project-contract batch. Assignment delivery/provenance/grade API в его тип не входят.
4. У view есть одна узкая `checkSources(source, references)` операция для уже существующего physical owner-relative path/type check. Она заменяет возвращение `{sources}` через validator/proof registry. PL передаёт реальные references из своего decoder; source связан с выбранным документом. Это перенос существующей проверки, не новый validator framework.

Metadata input остаётся canonical Core `NativeAdapterValidationInput`/NativeMetadata slot types, созданный из фактических parsed early/late capture facts. Metadata values — unknown до существующего PL parser, extras не вырезаются новой схемой. Producer не фабрикует origin/witness. Publication view не сериализуется и не становится новым receipt/token/registry; private state не отдаётся mutable alias. Нужная ownership copy существующих parsed данных допустима, но zero-copy не обещается.

Для реализации `runtime/operation.ts` выделяет private shared acquisition/callback/finally closure из текущего `withCurrentNativeOperation`: она остаётся единственным владельцем complete state, guards и A1 batches. Два настоящих Core entrypoints строят свои views внутри этой closure. Не вводить новую WeakMap/registry только ради доступа к capture; не запускать nested повторное acquisition. `NativePublication` derivation использует `Pick` существующих Core read methods, а course type выводится из existing `CourseIdentitySchema`. Добавочные accessor/checkSources signatures принадлежат только Core; downstream их не переписывает.

**PL — единственный владелец publication-business inspection.** Новый public contract:

```ts
inspectPublication(publication: Core.NativePublication): Promise<PublicationInspection>
```

`PublicationInspection` — вывод из существующего publication-business decoder: course и documents с source/format/assessment/business; **без proof и without publish authority**. PL проверяет свои декларации/cross-members и вызывает `publication.checkSources` для project/common references, как прежняя partial inspection. Новый инспектор не вызывает `operation.validateRelease`, `adapter.validateCourse`, prepared request transport, grading-components или seed qualification.

**Типовой scope обязательно включает actual decoder, не только ReturnType.** `native-business.ts` сейчас принимает `input:any`, приводит config кany и объявляет assessment:any; `declarations.ts` и `grading-policy.ts` тоже имеют потребляемые any seams. В новом typed producer:

- `input` общего/publication decoder — actual Core `NativeAdapterValidationInput`, а assessment/defaults — `z.output` существующих PL schemas. Raw config остаётся `Record<string,unknown>` из existing NativeBusinessSchema; до существующих parsers ему не приписывается полный config DTO.
- Нормализаторы `nativePolicy/mergePolicy/readNativeRaw` работают с unknown/record на реально потребляемой primitive/container форме, сохраняя extras и прежние вычисления. Не возвращать `any` из normalizer с последующим ручным typed return. Existing `object/closed` guards в declarations выражают только действительно проверенный record, не выдуманную полную структуру.
- `publicationQuestion/разбор delivery declarations` получают concrete checked outputs на существующих checks; общие точки этих методов входят в body-checked scope. Никаких новых Zod schemas/повторных validators ради типов. Если full-course callers разделяют эти helpers, они используют тот же истинный owner type, без parallel handwritten DTO.
- Public `PublicationInspection` допустимо выводить только после закрытия этой цепочки. Compiler roots включают actual CLI и consumed declaration helpers; одного public-export check или function-range diagnostics недостаточно. `grading-policy.ts` не нужен ordinary CLI после решения ниже; не объявлять его весь граф типизированным этим пакетом.

Удаляется **полная relation qualification** seed/grading, а не вся syntax parsing. `readNativeRaw` по-прежнему использует existing project-check/materialization/seed-target syntax parsers; report не должен утверждать, что вообще все seed/check parses исчезли.

Для этого `native-input.ts` получает общий typed mapping уже parsed `NativeAdapterValidationInput` → raw business input. Старый full-course external callback по-прежнему сначала разбирает unknown существующей Core schema, затем использует mapping; новый publication view отдаёт уже Core-constructed input и использует mapping непосредственно. Новый Core module argument/второй parse того же trusted input в новом inspector не нужны. Отдельный publication mapping не должен копировать или выдумывать полную Native metadata schema.

Активность PL определяется имеющимся `course.adapters`, не тем, что extension просто установлен. Installed-but-inactive/no-PL-metadata путь не должен превращаться в missing loaded coverage. Активный PL при отсутствующем sibling — ошибка orchestration; initialization error существующего sibling не скрывается. Course-export `createNativeAdapter/validateCourse` остаётся у своего реального full-course caller, **не как compatibility adapter ordinary route**. Его grading/seed semantics в этой задаче не переписываются.

**Download — владелец requests, ZIP и записи собственных archives.** Разделить операции:

```ts
prepareNativeDownloads(publication: Core.NativePublication,
  options: NativeDownloadOptions): Promise<PreparedDownloads>
finishDownloads(root: string, options: OrdinaryDownloadOptions): Promise<number>
resetDownloadRequests(root: string): Promise<void>
```

`PreparedDownloads` — принадлежащий Download draft готовых archive bytes с `publish():Promise<number>` и `discard():Promise<void>`, без Core capabilities/receipt/hash certificate. `prepareNativeDownloads` не вызывает clearOwned/final-output cleanup/write: его результат — только Download-owned готовые bytes/options/request snapshot. `publish` вызывается **после** успешного закрытия Core session; при closing error **новые archive writes этого draft не происходят**. Это не обещание общего rollback render/Site outputs.

Выбранная судьба старого pre-hook явная: `prepareDownloads` удалить как misleading destructive API, без alias. `resetDownloadRequests` удаляет только прежний owned requests directory по existing safe path checks; он не inspect-ит output, не вызывает `clearOwned` и не читает/удаляет artifacts receipt. `entrypoints/pre.ts` и обе Site pre branches вызывают новый reset API. `clearOwned` остаётся только в фактическом writer, перед записью нового именного ZIP manifest. Следовательно Native pre больше не удаляет archives отдельно от publication; прежний повторный archive cleanup снимается, а не прячется в новой preparation.

Draft одноразовый: `prepared → publishing → published|failed` либо `prepared → discarded`. Publish меняет state до первого await; повторный/reentrant publish и publish discarded/failed draft отказывают, без повторного acquisition. Discard до publish освобождает draft; после success/failure безопасно освобождает лишь принадлежащие ему buffers, **не удаляет старые/чужие или частично записанные outputs**. Во время publishing discard отказывает. Частичная writer failure не выдаётся за rollback: manifest записан первым, следующая обычная owned-cleanup операция знает свои имена. Cleanup error не маскирует исходную причину. Нового transaction/rollback framework нет.

Native options содержат destination/baseUrl/catalogBaseUrl/referenceCatalog/submission; root/profiles/projection/source-output берутся из единственного Core view, не задаются второй независимой копией. Для model/package работы используются existing Core APIs actual module и typed plans/inventories; `packageArtifact` и ZIP CRC сохраняются. Download не загружает PL и не делает qualification сам. Native CLI orchestrator вызывает тот же PL inspector перед preparation. Ordinary resources без Core продолжают работать как отдельный предметный режим, а не legacy fallback нового Native API. `native` flag и `observeOperation(catalog, proof:unknown)` из public finish-options удаляются; старого overload/shim нет.

Public preparation options/result и внутренний request consumer view принадлежат Download. JSON requests остаются unknown до существующих actual recognizers/guards; snapshot прежнего чтения не оправдывает `JSON.parse as Request`. Нельзя переименовать прежний `configuration:any` в новый supposedly checked contract: типизируются реально потребляемые поля и сохраняемые unknown extras. Не вводить full Pandoc/Quarto schema ради нового API. `PreparedDownloads.publish/discard` захватывают только Download-owned bytes/options/именной request snapshot и не вызывают истёкшие Core/PL методы.

**Site — владелец orchestration, mounts и final coverage.** Оба native маршрута открывают один `Core.withNativePublication`, выполняют одну PL publication inspection при активном PL, проверяют catalog source/output/format против независимой Native selection; затем либо готовят Download draft, либо заканчивают без него. После успешного выхода записываются новые Download bytes и success composition report. Нет обходного noDownload full grading admission. Site продолжает определять реальные mount/base URLs; не переносит/переименовывает текущие student/full outputs.

Existing ранние эффекты сохраняются и названы прямо: Native render уже пишет output; Site pre `compose.ts:432,443` чистит собственные result dirs, post `:576` копирует mounts **до** финальной публикации конкретного owner. Этот slice их не переставляет и не обещает сохранения предыдущего сайта/архивов после общего closing failure: Site cleanOutput может удалить их раньше независимо от Download. Предметное новое обещание — отсутствие writer-вызова/новых ZIP из подготовленного draft до успешного Core closing. Ранее выполненные render/mount writes остаются ранними результатами, не success certificate.

`NativeCatalog`, identity/projection, adapter inputs, request и operation types импортируются из одного Core public owner; PL inspection type — из PL, Download options/result — из Download. Site `SiblingModuleMap` получает actual Download public type вместо `Promise<any>`. Не заводить параллельный DTO или `proof:unknown` под новым именем. Значения audience `student|full` для artifacts принадлежат Core artifact contract; operation projection `student|author` — Core runtime contract; conversion одна в явном Core/Download boundary, не две ручные union-копии. Profile names — обычная Native конфигурация, не role authority.

## Что удаляется и что остаётся

Удалить **из ordinary route**: full PL grading/seed vet, подготовленный PL request как требование Site admission, repeated Core/PL release passes, installed-contract inventory scan ради proof, proof construction/parse/forwarding, validationPasses report contract, artifacts receipt read/write/schema conflict checks, per-ZIP SHA и report fields `release/proof/validatedAdapters`. Никакого v3 receipt или replacement policy hash.

`composition.json`, если нужен для диагностики, содержит только Site data с imported catalog/course types; он не читается как разрешение публикации. Download использует существующий именной ZIP manifest для owned cleanup. Старые generated receipts/composition в реальных курсах **не чистятся и не перемещаются**; новый Source их не превращает в authority и не переписывает ради migration.

Согласованный порядок caller без hidden certificate callback:

```ts
let downloads: Awaited<ReturnType<typeof Download.prepareNativeDownloads>> | undefined;
try {
  const catalog = await Core.withNativePublication(expectation, selection,
    async (publication) => {
      catalogCoverage(publication.catalog, record); // Site-only mount/selection check
      if (publication.course.adapters.includes("prairielearn")) {
        await PL.inspectPublication(publication);
      }
      if (needsDownloads) {
        downloads = await Download.prepareNativeDownloads(publication, downloadOptions);
      }
      return publication.catalog;
    });
  await downloads?.publish(); // только после успешного Core closing
  // Site использует catalog как данные конечной композиции.
} catch (cause) {
  try {
    await downloads?.discard(); // handle сохранён и при rejection RHS/closing
  } catch (cleanup) {
    throw new AggregateError([cause, cleanup], "Ошибка публикации и очистки");
  }
  throw cause;
}
```

Это target API example, не исполняемый prototype: actual types экспортируют владельцы. Native Download CLI повторяет orchestration внутри своего entrypoint, без Site coverage/mount step; он не вызывает старый polymorphic finish. Если callback/closing отвергнут, owning caller discards подготовленный draft в error path, не пишет composition success. Общего нового orchestrator/adapter registry не требуется.

Остаются NativeOperation private pointer/generation/output/environment/selected-QMD/resource checks, before-child/closing lifetime, actual A1 acquisition и role/public-private path checks. Core owns paths/selected public condition/resources; A1 owns actual selected project bytes, student/testset/solution roles и lifetime. Download owns final target/collision/CRC; Site owns selected outputs/mounts. JSON proof больше не «выдаёт» эти права. Session — переиспользование реального владельца жизни данных, не фасад над `validateRelease`.

**Честный остаток:** existing `withCurrentNativeOperation`/sourceSnapshot/подписи selected inputs и часть повторных guards остаются внутри acquisition. Этот пакет не заявляет удаление всех Core currentness costs, initial whole-owner snapshot или complete clean stack. Не добавлять новые guards взамен удалённых. Ordinary physical sources проверяются по текущему owner path/type; прежнюю initial-membership freeze не переносить в новый publication protocol как обязательность всех будущих bytes. Конкретные cached/generated project bytes приобретаются существующим fresh A1 только при реальном consumer.

Role filtering защищает размеченные/выбранные solution/test content/files. Оно не обнаруживает arbitrary unmarked profile-dependent computed text. Раздельные output dirs не изолируют execution cache. Native freeze/cache/data freshness остаются авторской политикой и отдельным принятым исследованием, без нового cachekey/framework в этом slice.

## Source closure и шаги реализации

| Owner/ref | Production/API change | Direct callers/docs/tests в том же пакете |
| --- | --- | --- |
| Core `5f4590aa4eb524830715f8020c8ff3c504197f73` | `release/validate.ts`: выделить общую semantic validation; новый узкий `release/publication.ts` для owned types/data builder; `runtime/operation.ts`: publication entry/view на existing lifetime; `public.ts` | `docs/native-operation-ts.md`, public API/spec entry; адресные existing release/lifetime fixtures. Full course-export validator использует common helper, ordinary session его не вызывает |
| PL `4d39a6648d2816bab9a372877deded3320ff35e1` | `application/native-publication.ts`, `application/native-input.ts` общий parsed-input mapping, `application/native-business.ts`, consumed guards/parsers `application/declarations.ts`, `public.ts` | Rename CLI `entrypoints/inspect-grading.ts → inspect-publication.ts`, `_extension.yml` metadata key и `tests/performance-native-boundary.ts`; README/native-ts/authoring current references; `tests/native-publication-sources.ts`, relevant calls в `native-scope-integration.ts` |
| Download remote671; reviewed local `d41705fe32b2e6c97c4944219531e405a091aebd` | `infrastructure/runtime.ts` split reset/preparation/write/ordinary orchestration, delete receipt/SHA/PL release logic; `public.ts`, `entrypoints/pre.ts`, `entrypoints/post.ts`, `infrastructure/siblings.ts` actual Core type import | `README.md:246`, `spec/contract.md:50`, `docs/public-runtime.md:50`; `tests/public-runtime.ts`, `model-receipts.ts`, `model-installed.ts` и imported PL fixture calls |
| Site `85080c730ad54c8fd81fa4a3c7c806a8771c4f68` | `application/compose.ts` единый route; `application/sibling-contracts.ts` actual Core/PL/Download types | `spec/contract.md:24`, `docs/public-operation.md`, `tests/legacy-harness-mapping.md`; receipt/proof consumers `site-public-operation`, `site-owner-release`, `site-native-pipeline`, `site-child-profiles`, `site-domains`, `site-project-native` |

Пути в таблице relative соответствующему `_extensions/<owner>/`, docs/tests relative repo. Download pending typing пакеты **не правились/не считались semantic изменением**; baseline implementation брать после их принятой вершины, не поверх меняющегося dirty файла.

Порядок: (1) Core common split + реальный scoped API/types; (2) PL инспектор/CLI на этом API; (3) Download preparation/write и полное удаление receipt readers; (4) Site единая orchestration + все direct docs/test callers; (5) один combined affected type graph. Это один согласованный Source пакет до приёмки: не объявлять producer законченным, пока старый actual caller намеренно сломан. Старые опубликованные refs остаются историей, новых compatibility adapters нет.

Inferred assessment/question/config producer в PL row — проверяемый implementation, не annotation надany. Full grading-policy module остаётся отдельным real full-course consumer; ordinary CLI ниже больше не тянет его в свой result. Его прежний any debt не считается закрытым этим design.

**CLI semantic naming, без alias.** Actual `inspect-grading.ts` сейчас показывает policy и дополнительно вычисляет `gradingPolicy(...)`; это больше, чем данные partial inspector, но меньше full grading/seed qualification. Новая ordinary команда — **`entrypoints/inspect-publication.ts`**, старый файл/descriptor key не сохраняются. Она показывает parsed publication course, document source/format и assessment/question policy summaries, **не** native grading projection/readiness и не whole business.raw/private projectChecks: убрать `gradingPolicy` import/вызов и exporter-only `declarations(...instance)` selection. Вместо прежних book/instance скрытых assumptions — explicit COURSE_OWNER, projection/profiles/output-directory expectation и необязательный output report path; `selection=undefined` означает весь текущий completed catalog. Ни PL prepared request candidate, ни новый Native inspect для этого не нужны. CLI аргументы задают обычный Native context; несовпадение с завершённым capture отказывает существующий Core boundary. Отдельную full-grading CLI в этот пакет не добавлять.

Actual callers, подлежащие согласованному rename: PL `_extension.yml` metadata `grading-inspection-entrypoint → publication-inspection-entrypoint`; `tests/performance-native-boundary.ts:71`; README211/231/332, docs/native-ts42, authoring260/342/407/673 и текущие CLI examples. `effective-grading.json` в новом примере назвать publication-inspection.json, не перезаписывая старые generated reports. Исторический `docs/release-9.0.0.md` не переписывать как историю нового command: добавить явное указание retired command/нового entry в актуальном разделе при необходимости. В просмотренных active Site/Download/course-tool paths внешних CLI callers не найдено. Type-check включает новый actual CLI, старого alias нет.

Точный поиск receipt path/schema/proof method выполнен по четырём owners, их API/CLI/tests/docs и адресно Platform/Gateway/course tools. В просмотренных Platform/Gateway/course tool paths дополнительных Download receipt readers не найдено. Это не обещание отсутствия произвольных внешних scripts; известные direct Source readers в таблице закрываются, старые generated captures/research не переписываются. [Подробная consumer table](native-publication-consumer-contracts-20261104.md) даёт actual field-level anchors.

## Малая приёмка после реализации

- Один affected compiler graph всех изменённых production/entry/type consumers, cap20 s/retry0; один-два meaningful negatives: assignment-only method недоступен publication view; removed proof/observe callback нельзя использовать как новый public result. Не checker по одному файлу/старой suite.
- Адаптировать existing publication/Download/Site fixtures: проверять готовые ZIP entries/CRC/реальные README/image bytes, selected source/format/mount links и отсутствие private file/solution. Assertions исключительно receipt SHA/proof/count удалить, без replacement counters.
- Одна actual disposable invocation существующей `tests/site-owner-release.ts` в authored режиме `root-pl`, outer cap60 s включая grace/retry0: **два независимых owners/captures**, root с PL без Download и tasks child с Download. Их configs фиксируются до render, каждый владеет своим Core session/A1 lifetime. Это не одно capture, переиспользуемое под другой config; `_quarto.yml`/config аргументы между ветвями не подменяются, expired view не переиспользуется. Сохранённый Native результат каждой своей ветви проверяется по actual links/ZIP bytes. Если существующий root-pl setup не помещается/не даёт нужной конфигурации — остановиться с точным пределом, не добавлять третий owner/авторский fake view.
- NoPL/inactive PL в этой invocation **не qualified**: это только Source/typed-parser control на existing plain business facts, без fabricated live view. Отдельную Native матрицу core-only/inactive не назначать. Предметный differentiator для existing pure parsed-publication control: declaration допустима ordinary syntax/visibility, но не проходит full seed/grading relation qualification; Site ordinary orchestration не вызывает этот full validator, existing full-course negative остаётся отказом. Actual Native validation именно такого malformed-grading варианта не заявлять без отдельного согласованного изменения fixture.
- Closing-failure existing control проверяет, что новый draft ZIP по свежему destination не записан и сохранённый handle discarded. Он **не** утверждает отсутствие всех pre-render clean/mount writes и не проверяет rollback прежних outputs. Не заменять это count-only assurance; actual file absence/owned state и сохранённая исходная ошибка — полезный результат. При first failure stop, no matrix/retry.
- Не запускать R0–R6/whole Java/SP/full grading/DB/image qualification; штатные upstream render/export и namespaces не заменять fake Native. Native fixture budget — будущий cap, не разрешение запуска этим design и не доказательство срока.

Сохранённые8.536 s computed и15.504 s cache probes подтверждают прежние узкие маршруты, **не цену нового API**. Число убираемых passes/read/SHA видно по Source, wall/CPU/RSS improvement UNKNOWN. Production/DB cutover, UUID migration, course policy/баллы и cache freshness не входят в приёмку этого slice. Genuine NativeOperation остаток и отдельный PL-format долг должны остаться в итоговом отчёте, а не исчезнуть за новым именем session.

## Evidence/ограничения этого design

**Delta после [NEEDS_CHANGES review](native-publication-clean-contract-review-20261104.md):** P1 actual PL decoder/type scope расширен и прежний `ReturnType<any>` не принят; P1 pre/reset/write/early Site effects и одноразовый draft явно разделены без rollback обещания; P2 future acceptance использует existing authored root-pl invocation с двумя независимыми owner captures, noPL path actual qualification не заявляет. Root naming criterion закрывается breaking rename CLI/descriptor/callers и удалением grading projection из ordinary command. Это исправления design, не заявления о compiler/runtime GREEN.

Переиспользованы mainstate, producer/consumer hash audits, pattern-pass summary/Core/export и actual public callgraphs; прежние specs прочитаны как изменяемое описание, не обоснование вечности гарантий. Только Source/report чтение и данный Markdown. Новых runtime/compiler/render/inspect/hash/build/network/install/DB/config/commit/push не было, Source/mainstate/audit/frozen candidates не менялись, делегирования не выполнялось. Root отдельно предоставил готовую medium consumer карту.

Own full-origin Sol6.1/high сохраняется, Ultra не включался; existing primary admission reference — [JDK candidate report](jdk-official-image-hashes-20261104.md). Следующий шаг — независимая high critique конкретной границы; технический backcompat вопрос пользователю повторно не требуется.
