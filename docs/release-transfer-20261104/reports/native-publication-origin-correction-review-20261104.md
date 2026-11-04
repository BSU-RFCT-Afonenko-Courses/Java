# Native publication: независимое рассмотрение исправления origin

**Решение: APPROVE для Source correction. Блокирующих замечаний к текущему delta не найдено.** Это независимое чтение изменений другого автора, а не подтверждение compiler/Native PASS.

Рассмотрен фактический checkout `core-native-bridge` на HEAD `f90713ff3480d5c77deccef4979e98e09279b099`. В нём изменены ровно `_extensions/course-core/release/publication.ts` и `_extensions/course-core/runtime/operation.ts`; итоговый delta — 77 добавлений и 23 удаления. Reviewer не писал эту коррекцию. Прочитаны актуальный brief `briefs/session-authoring-20261104.md`, локальные инструкции, языковое правило и действующий индекс контрактов. Последнее поручение A1 имеет приоритет над историческими Source-only/compatibility ограничениями; собственная работа reviewer в этом рассмотрении ограничена Source review и настоящим отчётом.

## Причина воспроизведённого отказа и её устранение

Сохранённые `native-publication-bounded-acceptance-20261104-r2.json` и stderr подтверждают exit 1 после 12.893534791 s, без timeout: Site дошёл до проверки PL и strict origin отверг `runtime` в `publicationAdapterInput`. Суммарный Native расход этого evidence — 18.623064679 s. Это отказ прежней версии, не результат проверки исправления.

`NativeOperationIdentitySchema` действительно добавляет `runtime`; TypeScript `Pick` у параметра не удаляет поле у настоящего объекта. Оба исправленных builder в `publication.ts:235` и `publication.ts:255` явно выбирают только operation, captureWitness, source, format, projection и profiles. `runtime` остаётся в operation identity и не попадает в adapter DTO. Canonical `NativeAdapterValidationInputSchema` не расширялась.

## Настоящие типы producer и consumer

Общий `PublicationDocuments` по-прежнему допускает generic `EarlyDocument`. Его используют `validatePublicationFacts`, `validateNativeRelease` и существующий экспортируемый `publicationAdapterInput`; последний сохраняет прежний schema parse для full admission. Private `NativePublicationDocuments` усиливает только captureWitness и format через actual `NativeAdapterValidationInput["origin"]`. Это усиление применяется только к `createNativePublication`.

Реальный caller в `runtime/operation.ts:2403` передаёт `complete.releaseDocuments`, полученные через существующий `completeSchema` decode в `operation.ts:2235`. Та же схема теперь требует UUID captureWitness и Native format в releaseDocuments context (`operation.ts:234`). Producer при seal использует witness из state и успешный результат уже существовавшего format safeParse; прежний OPERATION_BINDING guard проверяет привязку к capture, profiles, operation, input и projection (`operation.ts:1473`). Он не заменён cast, assertion, fallback или фиктивным witness. Catalog использует тот же parsed format вместо повторного parse.

Отдельно рассмотрен случай отсутствующего capture: если только одна сторона не имеет witness, equality guard отвергает её; если witness отсутствует и в state lookup, и в early context, равенство `undefined` само по себе недостаточно. Однако текущая коррекция не превращает это в валидный input: обязательный UUID в `completeSchema.parse` при seal (`operation.ts:1819`) отвергает такой releaseDocument до записи complete/child JSON; повторный decode того же complete при открытии также отвергает неполный capture до вызова publication consumer. Новый внутренний validator для этого не нужен. Неподдержанный format отвергается прежним guard и Native вариантом существующей JSON boundary.

Реальный PL consumer `application/native-publication.ts` получает типизированный input из `publication.adapterInputs`, затем передаёт его в `parsedNativeBusinessInput`. Изменения не требуют ослаблять consumer или добавлять повторный parse origin. Проверены common helper, ordinary builder, Native seal/decode и этот consumer вместе.

## Данные и lifetime

Общий `adapterInputData` сохраняет прежнее предметное извлечение: configuration/assessment берутся из metadata соответствующего adapter, projectChecks/projectMaterialization — из общих planes; выбираются только requested slots, отсутствие остаётся отсутствием. Course, целевые declarations, attributes, answer и optional assessment передаются полностью. Unknown metadata values не нормализуются повторно и не теряют дополнительные поля. Тип результата и values связан с actual adapter input, без нового `any` или `Object.fromEntries` с неявной потерей типа.

Ordinary builder выполняет `structuredClone(input)` перед `immutable`. Это необходимо, поскольку существующий `immutable` только рекурсивно freeze-ит. Клонируется весь adapter input, включая profiles, course, nested attributes/answer/assessment и unknown metadata; callback DTO не удерживает ссылки на mutable private capture. Эти значения происходят из JSON capture, поэтому функции или внешние host objects не являются допустимым входом данного пути. Полный generic helper сохраняет прежний parse/freeze.

`adapterInputs` сохраняет `checkLive` и существующий decode readSet; `checkSources`, borrowed operation methods и closing fence не менялись. Сохранённые plain snapshots могут пережить callback как данные, но не получают Native authority. Изменение не обещает ускорение или zero-copy.

## Оставшаяся проверка координатора

Перед выполнением compiler следует включить в один затронутый type graph все три pure fixture roots:

- `tests/release-semantics-a4a.ts`;
- `tests/release-collection-a4a.ts`;
- `tests/native-metadata-a4c.ts`.

Первые два используют generic context без обязательного Native witness; текущий широкий общий тип сохраняет их назначение. Третий проверяет full-admission metadata/readSet/origin через прежний helper. Изменять эти fixtures ради artificial Native witness не требуется. Их execution здесь не выполнялся.

После compiler остаётся один ограниченный настоящий Native пример по уже согласованному root acceptance маршруту: он должен пройти PL inspection и публичную выдачу, на которой r2 отказал. Проверка только формы объекта или Source assertions этого не заменяет. Разрешённые остатки не сбрасываются: compiler **10.199237140/20 s**, Native **41.376935321/60 s** с grace. При новом отказе нужна конкретная диагностика, а не неизменный повтор.

Reviewer выполнил только чтение исходников/evidence, просмотр diff/status и `git diff --check` (rc 0). Compiler, tests, runtime/Native, network, commit/push и изменения Source/state не выполнялись; Platform/Gateway/Performance не затронуты. Единственная запись reviewer — этот отчёт. Source approval разрешает перейти к оставшейся ограниченной проверке, но не объявляет весь маршрут выпуска принятым.
