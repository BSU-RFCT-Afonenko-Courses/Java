# Download: узкая типизация envelope, MetaString и Attr

Результат: **source-дельта готова к независимому review; product compiler PASS и
согласованный existing pure control PASS (15 проверок)**. Первый behavioral FAIL
сохранён как stale fixture API. Root отдельно разрешил test-only alignment к
actual Core signature. Product Source после compiler PASS не менялся; после
PASS изменённого control весь Source заморожен. Неизменные проверки не повторялись.

## Вход и exact scope

Download `/home/tolya/JavaCodex/java-release-20261110/download`, исходный HEAD
`944ca76dbdef5bb4842116950621f82449b631b3`; начальный `git status --short` пуст.
Независимый root принял Sourceplan и разрешил только его безопасную первую часть.
Изменены ровно два product-файла:

- `_extensions/project-download/native-ts/early-filter.ts`;
- `_extensions/project-download/native-ts/late-filter.ts`.

Отдельным прямым разрешением root согласован один existing pure control
`tests/literal-request.ts`: только obsolete phase-observation call, остальные
unrelated test any не переписывались. Runtime/API/ZIP/domain request decoder,
CI pins и другие checkout не редактировались. Commit/push/mainstate не выполнялись.
Точный patch: [download-ast-envelope-types-20261104.patch](download-ast-envelope-types-20261104.patch).

## Что типизировано

В early `attr` принимает `unknown`. Вход по-прежнему проходит те же array/length,
class, empty-id, attribute pair/string/duplicate-key checks. Вложенный список
attributes представлен `readonly unknown[]`, поэтому его элементы доходят до
существующих checks как unknown; результат остаётся проверенным
`Record<string,string>`. Дополнительных Attr schema/check проходов нет.

Оба stdio wrappers принимают результат external `JSON.parse` в `unknown`.
Небольшой локальный record predicate сужает только envelope, meta map и
потребляемый transport field. Existing `t === "MetaString"`/string `c` guard
по-прежнему определяет admission. Никакого typed полного PandocNode union
или проверки остальных Native nodes не добавлено.

Early удаляет только собственный source transport key через тот же meta object;
остальные metadata/envelope values сохраняются, затем вызывается прежний lowering.
Late nested batch JSON также получает `unknown` перед **единственным existing**
`resolveDownloadRequests`. Повторного business parsing или нового schema decoder
нет. API version остаётся copied unknown: unused `number[]` contract не введён.
Late response envelope остаётся прежним (`meta` result, `blocks: []`), а не новым
Native pass-through договором.

Новых casts/suppressions/IO/зависимостей/Core AST imports нет. Record guards
конкретизируют ту же external transport boundary; для malformed null/nonrecord
root прежний случайный TypeError теперь может стать existing controlled
missing-source/batch diagnostic. Принятый valid Native алгоритм не менялся.
Полного AST validation или Node allowlist нет.

## Честно оставленные holes

Ничего не менялось в early:

- `downloadName` boolean и его неполные `c`/Span assumptions;
- `request` input и native child shapes;
- общий `preflight`/`scan` и recursive any;
- `document as any` в lower envelope;
- replacement `parent:any`, несвязанная пара `key:string|number`, `key!`, emitted
  replacement value typing.

Эта партия **не** выдаёт consumed MetaString/Attr narrowing за полное покрытие
Pandoc AST. Большие Core schemas не импортировались: существующий Core envelope
строже Download checks, а exported nodes всё равно unknown/any. Дальнейший
recognizer/location пакет остаётся отдельным unresolved Sourceplan.

## Проверки и точный behavioral отказ

Bundled `/opt/quarto/bin/tools/x86_64/deno`,
`DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache`, actual existing map
`/tmp/core-native-bridge-imports.json` (прочитан до запуска).
Pure control получил `CORE=/home/tolya/JavaCodex/java-release-20261110/core-native-bridge`.
Map указывает этот actual Core и текущие existing stdlib bindings; новые URL/cache
не придумывались. Compiler не использовал `--cached-only`.

| Запуск | Результат | Время |
| --- | --- | --- |
| `deno run --no-check … tests/literal-request.ts` | FAIL, exit1 до changed path | 0.2603 s |
| `deno check … early-filter.ts late-filter.ts tests/literal-request.ts` | PASS, exit0 | 0.7536 s |
| Изменённый pure control после разрешённого test-only alignment | PASS, exit0, 15 controls | 0.3268 s |

Первоначальная compiler/control пара заняла1.0143 s. Дополнительный изменённый
pure control отдельно разрешён root с cap5 s/retry0. Общий расход **1.3411 s**,
ни одного unchanged retry. `git diff --check` — PASS. Compiler после test-only
alignment по прямому указанию root не повторялся: product Source не менялся.
Все commands/elapsed/exits:
[download-ast-envelope-types-checks-20261104.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/download-ast-envelope-types-checks-20261104.json).
Логи:
[control](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/download-ast-envelope-types-control-20261104.log),
[compiler](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/download-ast-envelope-types-compiler-20261104.log),
[изменённый pure control](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/download-ast-envelope-types-control-aligned-20261104.log).

Первый точный отказ:

```text
before13: CoreContractError: BODY.DELIVERY_PHASE_UNSUPPORTED:
Нет actual native phase/format/engine observation
at tests/literal-request.ts:40
```

Existing literal control ожидает на первом `qualify(ast)` отказ
`Shortcode не квалифицирован`, но actual Core раньше отклоняет phase evidence.
Source причина: текущий
`Core/document/assignment-producer.ts:22–37` принимает **три** аргумента
`(ast, context, evidence: AssignmentProducerObservation)` и требует
`format/engine/filters/shortcodes`. Existing Download test вызывает **четыре**
аргумента `(v, context, profile, evidence)`, с legacy `profile.native` в третьем;
четвёртый аргумент не используется. Отказ возникает **до**
`lowerLiteralDownloadRequests`, поэтому он не доказывает regression нового Attr
и одновременно не даёт behavioral PASS изменённого пути.

Core phase guards не ослаблялись. Root отдельно разрешил исправить только
existing pure control: удалены legacy `profile`/`sourceProbe`, вызов теперь
трёхаргументный с `{format:"html",engine:"markdown",filters:{},shortcodes:[]}`.
`engine:"markdown"` и actual signature сверены с Core
`assignment-literal-shortcodes.ts` и `assignment-registered-shortcodes.ts`.
Observation явно помечен **synthetic pure qualifier**; он не объявляется actual
producer authority/admission, новый Native capture не выполнялся.

Изменённый control завершился PASS15: baseline literal qualification после
lowering, hostile outer/param Attr, duplicate/unknown attrs, nested/dynamic args,
reserved markers и передача другого handler обратно Core. Это meaningful
behavioral coverage изменённого Attr пути. Stdio wrappers дополнительно отдельно
не исполнялись; их проверил product compiler, actual Native integration остаётся
вне этой scoped партии. Альтернативный request-contract control не запускался.

No actual Native/render/inspect/hash/network/install/build/CI/full suite.
Pure control работает с сохранённым JSON и pure qualifier; настоящего Native
producer/capture не запускалось.

## Полный собственный модельный допуск

Agent `/root/core_yaml_types`, chat `01a1a54c-ab39-7a83-808d-a190428ff628`,
неизменённый origin turn `01a1a54c-ab6a-7412-894c-270be9b9aac3`,
ожидаемая пара `gpt-6.1-sol/medium`. Весь primary origin interval проверен:
**11 turn_context**, top-level и nested model/effort соответствуют назначению — PASS.
Standard назначен; hidden routing не утверждается; globals не менялись.
[Машинное свидетельство](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/download-ast-envelope-types-admission-20261104.json).
