# Download: следующий узкий пакет типов JSON filters

**Sourceplan, без edits или запусков.** Полная типизация early walker пока
не объявляется решённой: существующий recognizer не доказывает полный тип Span,
а replacement хранит несвязанную пару parent/key. Обширная Pandoc AST schema
для прозрачного прохода чужих узлов не нужна и не предлагается.

## Exact scope и найденные места

Download `/home/tolya/JavaCodex/java-release-20261110/download`, HEAD
`944ca76dbdef5bb4842116950621f82449b631b3`.
Планируемый product scope — максимум два существующих файла:

- `_extensions/project-download/native-ts/early-filter.ts`;
- `_extensions/project-download/native-ts/late-filter.ts`.

Прочитаны AGENTS/spec, эти filters, их Lua/shell caller, existing request decoder,
public exports и адресные Core типы. Runtime/API/ZIP/package bytes не изменялись.

| Место | Доказанные существующим алгоритмом данные | Открытая типовая граница |
| --- | --- | --- |
| early:18–46 `attr` | Строгий собственный shortcode Attr и уникальные string key/value; результат уже `Record<string,string>` | Аргумент и вложенные array elements — any, хотя existing guards достаточны для их последовательного unknown narrowing |
| early:48–59 `downloadName` | Candidate Span с class marker, array content и первым param с `data-value=project-download` | Boolean не несёт typed witness; цепочка `c[0][1].includes` основана на any и не доказывает полный Span/Attr |
| early:61–93 `request` | Собственные literal args, Space/SoftBreak и пустые param spans; strict `attr` и existing `decodeDownloadRequest` задают string semantics | На входе предполагает результат recognizer; весь child list/`c` ещё any |
| early:101–129 | object с array `blocks`; полный preflight по arrays/objects и reserved markers | `document as any`; metadata/unknown blocks не нужно превращать в закрытый union |
| early:130–157 | Все replacements применяются после traversal в прежнем порядке | `{parent:any,key:string|number,value:any}` и `key!` не выражают связь array↔number / object↔string |
| early:160–177 | Собственный source MetaString, restrictive env mode, один lower pass | External JSON, meta record и transport field any до существующего tag/content guard |
| late:3–11 | Batch MetaString и existing `resolveDownloadRequests(unknown)` | External envelope/meta/field any; nested batch JSON можно немедленно оставить unknown, без нового decoder |
| late:15 | API version просто перенаправляется | Нельзя выдавать copied unknown за проверенный `number[]`, поскольку здесь нет такого guard |

## Реальные Core/library types и их применимость

Core public экспортирует `EarlyDocument` (через `public.ts:24–39`). Его `ast`
выведен из actual `document/contracts.ts:36–40`:

- обязательный `pandoc-api-version: number[]` с runtime bounds;
- `meta: Record<string, unknown>`;
- `blocks: unknown[]`;
- `PandocDocumentSchema` — strict envelope, но не общий typed Node union.

Он даёт полезную reference shape, **не** доказательство текущего Download input.
Early Download проверяет только object/blocks и сохраняет неизвестные поля;
late даже не требует blocks и лишь копирует API version. Поэтому прямое
`JSON.parse(...) as EarlyDocument["ast"]`, type annotation поверх parse-any или
новый `EarlyDocumentSchema.parse` были бы соответственно ложной гарантией либо
новым повторным более строгим runtime parsing. Strict Core envelope также
изменил бы поведение дополнительных верхних Native полей.

Core Body package (`body-export/package-contracts.ts:9–10`) использует
`z.array(z.any())` для native payload. Полученный body type не закрывает any hole.
`NativePandocBinding/Host` относятся к выбору executable/env, **не AST**.
У Download в scoped source нет package manifest/vendor `.d.ts` с экспортированным
Pandoc Span/Attr/Inline/Block JSON type. Новые upstream declarations/install/fetch
в этой задаче не искались и не добавлялись. Ordinary Download не должен получать
новую зависимость от Core ради типа, который всё равно оставляет nodes unknown.

## Минимальные честные consumed views

Это **локальные описания доказанных checks**, не объявление upstream Pandoc model:

1. Early envelope: `Record<string, unknown> & { blocks: unknown[] }` после
   существующего object/blocks guard. Не требовать API version/meta, которых
   guard сейчас не проверяет; сохранить дополнительные поля и сам объект.
2. В обоих stdio wrappers transport view: record `meta`, затем собственный
   `{t: "MetaString", c: string}` после существующих tag/string checks.
   Остальные metadata keys — unknown. В late API version остаётся unknown:
   существующий алгоритм только сериализует его, не интерпретирует.
3. `attr` принимает unknown; existing length/array/string/duplicate-key checks
   поэтапно дают существующий string map. Array narrowing следует удержать через
   `readonly unknown[]`/`unknown[]` views вместо распространения стандартного
   `Array.isArray` any[]. Новый tuple validator не нужен.
4. Traversal values — unknown; containers — unknown arrays либо records.
   Не строить общий discriminated union «всех допустимых Native nodes».
   Preflight должен пройти и по неизвестным node names/fields; scan должен
   оставить другие shortcodes прежнему Core consumer и не терять неизвестный AST.
5. Тип emitted replacement можно описать только для **самостоятельно
   конструируемого** `Span/Str` transport. Это не тип всех input Spans.

Эти views позволяют частичный low-risk охват late и attr/preflight early без
ложной полной AST типизации. Нельзя объявить этим закрытыми `downloadName`,
`request` и parent/key remainder.

## Две конкретные unresolved части перед implementation

**Recognizer witness.** `downloadName` не вызывает `attr` и не проверяет весь
`v.c` как tuple/array. Например, индексируемый object `c` с ключами `0`/`1`
может пройти используемые property accesses; `.includes` различает реальные
string/array values, а не декларированный Span. Predicate `v is PandocSpan`
поверх текущего boolean был бы handmade fiction. Нужно отдельно выбрать:

- минимальный candidate-view, каждое свойство которого доказано actual checks,
  затем strict owned `attr`/args используются один раз; либо
- явно разрешённое узкое дополнение existing boundary checks для собственных
  shortcode candidates с описанием malformed-input diagnostic changes.

Нельзя при этом повторно проверять весь document, закрывать неизвестные Native
узлы или выдавать новая schema parsing за type-only правку. Точные дополнительные
candidate guards следует предложить финальным diff до запуска; этот Sourceplan
их не добавляет и не гарантирует полную устранённость early any.

**Replacement target correlation.** Unknown array/object parent нельзя честно
индексировать произвольным `string|number`. Существующее `key!` показывает
невыраженный инвариант, а не доказательство. Узкое предложение — хранить typed
location из тех же traversal branches: array parent + numeric key либо object
parent + string key, с корреляцией в discriminated local record. Apply сохраняет
отложенный порядок mutations и лишь выбирает правильную index operation.
Это не AST registry/cache; но изменение следует проверить как конкретную
типовую refactoring-дельту. Не заменять parent/key на `any` или fake casts.
Если для сохранения всех malformed-input cases потребуется заметно иной walker,
остановиться отдельно; полной переработки traversal в этой партии нет.

## Рекомендуемая последовательность следующей партии

Сначала согласовать минимальный final diff в этих двух files. При невозможности
сразу доказать recognizer/location не раздувать scope: взять late envelope/
MetaString + early attr/preflight и явно оставить unresolved walker sites.
Нет необходимости вводить большую AST/library declarations поверхность ради
максимального процента coverage.

Для будущей адресной проверки можно использовать existing pure
`tests/literal-request.ts` и `tests/request-contract.ts`, расширяя только
meaningful cases: unknown node/extra envelope roundtrip, другой shortcode
сохраняется, owned literal lowering/strict args/reserved markers и array/object
replacement positions. Нынешний literal control уже проверяет actual saved reader
fixture, hostile Attr/args/UUID markers и передачу другого handler обратно Core.
Он не доказывает complete Pandoc AST typing и сейчас сам содержит any helpers.

Late stdio wrapper top-level исполняет stdin; его не следует импортировать в
pure control как будто это inert library. Existing request-contract проверяет
business batch decoder, но не заменяет MetaString/envelope boundary assertion.
Не запускать Native render/phase-cost ради type-only дельты; будущие compiler и
control назначаются отдельно после review. Здесь они не запускались.

## Сохранность и первичный модельный интервал

No source edits/compiler/Native/inspect/render/hash/network/install/build/tests,
commit/push/mainstate. Созданы только этот отчёт и admission evidence.

Agent `/root/core_yaml_types`, собственный chat
`01a1a54c-ab39-7a83-808d-a190428ff628`, неизменённый origin turn
`01a1a54c-ab6a-7412-894c-270be9b9aac3`, назначение `gpt-6.1-sol/medium`.
Весь primary interval проверен: **10 turn_context**, top-level model/effort и
nested collaboration model/reasoning_effort соответствуют назначению — PASS.
Standard назначен; hidden routing не утверждается, globals не менялись.
[Первичное свидетельство](download-ast-types-next-admission-20261104.json).
