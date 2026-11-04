# Исправление Native adapter origin: Source freeze

Изменены и frozen ровно два Core файла: `release/publication.ts` и
`runtime/operation.ts`. Это Source candidate для независимого review; после
коррекции компилятор и Native здесь не запускались, Native PASS не заявляется.
Base Core HEAD: `f90713ff3480d5c77deccef4979e98e09279b099`.

## Исходный факт и причина

Сохранённый root запуск
[native-publication-bounded-acceptance-20261104-r2.stderr.log](native-publication-bounded-acceptance-20261104-r2.stderr.log)
дошёл до Site finalization / PL inspection и отказал в
`publicationAdapterInput:207`: strict Zod origin получил лишний `runtime`.
Запуск занял 12.893534791 s, общий Native used 18.623064679/60 s.

Actual NativeOperationIdentity содержит runtime, а PublicationIdentity является
только TypeScript Pick трёх полей. Pick не удаляет runtime property у настоящего
объекта. `origin: { ...identity, ... }` копировал лишнее поле из
`operation.identity`. Canonical NativeAdapterValidationInput origin имеет ровно
operation, captureWitness, source, format, projection, profiles. Схема не должна
расширяться ради этого spread.

## Итоговая коррекция

В `publication.ts` оба builder явно выбирают шесть canonical origin fields.
Общая `adapterInputData` единожды извлекает course, выбранные metadata slots,
целевые declarations/answer и optional assessment. Тип результата — Pick
actual NativeAdapterValidationInput. Values типизированы actual envelope;
Object.fromEntries с неявным any больше нет. Unknown metadata не декодируются
повторно, лишние поля внутри выбранных значений сохраняются.

Общий PublicationDocuments остаётся широким: он нужен существующим common
semantics и full-release pure fixtures с generic EarlyDocument context.
Новый приватный NativePublicationDocuments усиливает только captureWitness и
format через Pick actual NativeAdapterValidationInput origin. Его получает
createNativePublication, поскольку этот путь имеет реальную Native authority.
Три pure fixture файла и validate.ts не менялись, fake witness не добавлялся.

Existing exported publicationAdapterInput полного validateNativeRelease
сохраняет NativeAdapterValidationInputSchema.parse: его broad input действительно
нуждается в существующей границе декодирования. Новый приватный ordinary
nativePublicationAdapterInput конструирует actual typed input и возвращает
`immutable(structuredClone(input))` без self-parse и без runtime guards.

В `operation.ts` existing completeSchema releaseDocuments.early.context теперь
native variant: captureWitness required UUID, format NativeWebFormatSchema.
Это уточнение существующей JSON boundary, отдельного decoder framework нет.
В существующем seal ожидаемый captureWitness берётся из state, результат уже
существовавшего NativeWebFormatSchema.safeParse сохраняется локально. Тот же
OPERATION_BINDING guard проверяет equality witness, profiles, operation, input,
успех native format и projection. После guard native context получает именно
подтверждённые witness/format. Тот же parsed format поступает в catalog; второй
NativeWebFormatSchema.parse удалён. Новых проверок или условий в guard нет.

## Семантика и владение

Для настоящего валидного Native capture origin теперь исключает runtime;
role/path/currentness/lifetime, private capture и closing проверки не менялись.
Неполный либо не-native context не маскируется fallback, cast или assertion.
Существующая serialized-complete JSON boundary теперь явно требует те два поля,
которые Native seal уже подтверждает. Для malformed complete ошибка может
возникнуть при его decode раньше использования adapter input. Broad common
semantics и existing full admission сохраняют свои прежние границы.

Ordinary builder больше не выполняет повторную schema normalization над уже
parsed capture: course/defaults, declarations/answer и assessment приходят из
существующего Native release capture decoder. Full-release helper сохраняет
свою прежнюю normalization/parse. Добавление readSet array copy не меняет slots.

Immutable рекурсивно freeze-ит, но не клонирует. Поэтому ordinary snapshot явно
structuredClone-ит весь typed input до freeze, включая nested course,
attributes, assessment и unknown metadata. Раньше Zod клонировал известные
контейнеры, но unknown metadata могло сохранить private alias; snapshot теперь
отделяет и его. Это реальное ownership отличие, а не обещание zero-copy или
ускорения. Actual capture получен из JSON; функции/произвольные внешние объекты
не являются этим входом. Full-release helper сохраняет прежний parse/freeze.

## Проверки и handoff

Выполнены только Source чтение, просмотр exact diff и `git diff --check` (rc0).
Изменены ровно два разрешённых product файла. Нет новых any, casts,
suppressions, validators, schema runtime field, fixture миграции, PL/Download/
Site Source правок или изменений DB/results/config/globals. Compiler, tests,
Native/render/inspect, hash/build/install/network, stage/commit/push не запускал.

Root-only budgets не расходовал и не сбрасывал: compiler remaining
**10.199237140/20 s**, Native remaining **41.376935321/60 s**. Три direct Core
fixture roots остаются на root changed compiler graph, без fixture execution.
Независимый high reviewer — PL owner; самоприёмки нет.

[Exact patch](native-publication-origin-correction-20261104.patch).
Source writer `/root/native_publication_consumers`; own primary Sol6.1/high
admission: [evidence](native-publication-consumer-source-20261104-admission.json).
Standard назначен, hidden routing независимо не удостоверяется. Новых агентов
нет. Source freeze действует после этой записи.

## Точные Source paths

- `core-native-bridge/_extensions/course-core/release/publication.ts`
- `core-native-bridge/_extensions/course-core/runtime/operation.ts`

Прочитанные canonical boundaries: `release/contracts.ts:127`,
`runtime/contracts.ts:31`, `document/contracts.ts` EarlyDocument context,
`document/condition.ts` immutable. Эти файлы не менялись.
