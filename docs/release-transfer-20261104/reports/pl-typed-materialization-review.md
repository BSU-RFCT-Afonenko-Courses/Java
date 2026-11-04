# Независимая рецензия типизации PL materialization

3 ноября 2026. Рецензент `/root/review_pl_materialization_types`.

## Вердикт и границы

**ACCEPT для ограниченного type-delta. Существенных новых findings нет.** Рецензия относится строго к PL `f9c00aed6de610dce7d9b7cb6e50c28ebfb9573e..4e4ab0cd5499006826f5ab21d1e3fdeef9c313b1`: 11 файлов, `284 insertions(+), 63 deletions(-)`. Прочитаны сохранённые blobs и полный diff через `git show`/`git diff`, а также точные связанные публичные Core contracts на `0478458a71f3acf34ad6d9983ac11023783dde3d`. Текущие незакоммиченные изменения параллельного CI writer в вердикт не входят.

Это приёмка типов указанной партии, а не всей PL strict coverage, текущей CI или production readiness. Повторные check/tests/render/inspect/build/hash/install не выполнялись. Успех compiler-run, format и diff checks ниже — свидетельство отчёта автора `pl-typed-materialization.md`, без нового запуска рецензентом.

## Проверенные свойства

Координаты PL ниже относятся к `_extensions/course-prairielearn/application/` на `4e4ab0c`, координаты Core — к `_extensions/course-core/` на `0478458a`.

| Область | Наблюдение по исходникам |
| --- | --- |
| `native-selection.ts:5–30,38–54,139–163,225–245` | Локальная facade `parse: (...) => any` заменена `Pick<typeof Core, ...>` настоящих публичных schemas. Проверяемые inventory/check остаются `unknown` до прежних parse. После parse планы, файлы, bytes/hash/name и provenance выводятся из реальных Core типов. `File` использует `Omit<ArtifactFile, 'audience'>`, прежний cast `full.files as File[]` убран. Runtime выбор role/audience/limits/globs не изменён. |
| `native-checks.ts:125–154`; `native-check-projection.ts:14–39` | `NativeProjectCheck` выводится как `z.output<typeof NativeProjectCheckSchema>`. `CheckedNativeProjectFact` сохраняет остальные поля настоящего `NativeProjectFact` и уточняет только PL-нормализованный `check`. Core оставляет исходный fact.check неизвестным; собственная PL schema по-прежнему вызывается в projection. Нового runtime contract или permissive schema нет. |
| `native-check-projection.ts:46–69,101–108,151–174,211–230`; `native-check-inventory.ts:25–34,50–85` | Raw origin идёт через прежнюю `ArtifactFileSchema.parse`, проверки same-S hash/bytes/path и существующую origin-проекцию. `Origin` выведен из настоящего helper return. Reference/source/contract records больше не получают тип из `any` Core facade. Новые явные contract types сохраняют различие `submissionRelativePath` и `logicalTarget`. |
| `native-materialize.ts:148–208,217–260` | Core facade использует настоящую `materializeProject`; operation/pair/condition/bindings/inventory имеют публичные Core types. Core `artifacts/project.ts:353–373` возвращает `Promise<ArtifactInventory>`, поэтому `student/final`, поиск финального starter и ZIP inventory не наследуют dynamic-import `any`. Workspace method и zip callback выведены из существующего `createNativeAdapter`, а его реальные file/archive outputs остаются конкретными. Обе ветви return сохраняют inventory/files/archive; checked project/selection остаются условными, поскольку manual ветвь не обязана иметь check. |
| `native-materialize.ts:112–143`; Core `artifacts/contracts.ts:46–48,88–110` | Condition остаётся настоящим union `ResolvedCondition \| NativeAssignmentCondition`, bindings — существующим `z.input` схемы с допустимыми отсутствующими defaulted maps. `Pick` сохраняет исходную сигнатуру closure; maps имеют точные nested value types. Нет замены condition на plan, неизвестный generic result или копию Core DTO. |
| `runtime-descriptor.ts:6–7,15–48`; `java-build.ts:8–15`; `grading-descriptor.ts:4` | Authored feedback `{requirement, 'what-check'}` получен из existing schema, а runtime feedback выводится из прежнего буквального mapping в `{requirement, whatCheck}`. Проверенные поля не стираются входом `c:any`. RuntimeProfile выводится из уже существовавшей profile signature javaBuild. Уточнения publicCases/discovery/build не убирают прежние guards. |

Отдельно рассмотрен новый `!` у `c['api-precheck']` в `runtime-descriptor.ts:29`: непосредственно предшествующий прежний parity guard на строке28 уже отвергает случай `api` без authored declaration. Assertion не меняет emitted runtime и опирается на существующее условие.

В `native-check-projection.ts:181–207` новые одинарные assertions до `Record<string, unknown>`/`Iterable<unknown>` дают доступ к прежнему JSON/for-of разбору; они не утверждают, что JSON уже является validated DTO. Используемые `id`, `fixture`, `submission` проверяются прежними условиями. `safePath` получил честный predicate `unknown → p is string` с тем же телом. Некорректные null/non-iterable значения могли приводить к прежним отказам доступа/итерации и до этой партии; новых validators или изменения refusal/wire semantics здесь нет. Новых `as any`, двойных casts либо suppressions в diff нет.

## Реальность и пределы статической проверки

`tests/native-materialization-types.ts:21–115` использует `IsAny` вместе с двусторонней assignability-проверкой `Exact`. Assertions сопоставляют реальные Core schema parse outputs, pair, condition, bindings, inventory, normalized check, feedback и выбранные source/reference leaf types. Это осмысленные проверки именно прежних дыр: подмена facade parse output на `any` ломает соответствующий assertion. Они не являются доказательством рекурсивного отсутствия каждого возможного `any`; оставшаяся цепочка проверена чтением реализаций.

`tests/native-core-typecheck.ts:44–59,61–147` включает новый файл в тот же positive graph и требует ненулевой compiler exit с ожидаемой диагностикой у четырёх новых cases: authored/runtime feedback `TS2561`/`TS2353`, неверные plan/condition `TS2322`. Прежние четыре controls сохранены в том же цикле. Проверки не зависят от runtime исполнения fixtures. По авторскому отчёту combined run завершился exit0 после уточнения ожидаемых diagnostic codes; автор также сообщил clean fmt/diff checks. Рецензент эти команды не повторял.

Неверный `audience: 'author'` — полезный negative case, но он не доказывает отдельный student-only type: публичный `ProjectPlan` Core допускает `student | full`. В партии сохранён этот настоящий публичный договор; различие конкретных student/full inventories по-прежнему дополнительно охраняется runtime. Это не новая регрессия.

## Сохранённые ограничения вне findings

`native-materialize.ts:21–37,40–49,63,67–92,125–140` сохраняет прежние dynamic `publicExtension`/QRC API и JSON/config `any`. Новая annotation maps/return проверяет форму своих consumers, но не превращает `QRC.bindConditionLinks` или `links.resolve` в статически проверенный upstream API: присвоение из старого `any` компилятор допускает. Этот долг явно оставлен автором; партия не заявляет его закрытия. Core schema/materialization facade при этом действительно восстановлена через public types, а не через cast результата dynamic import.

`native-course`/export и adapter internal state всё ещё способны передавать `any` на новые typed границы; end-to-end strictness всей composition этим commit не доказана. Неизменённые canonical/manifest helpers и `validateAdditionalJavaSources(value:any, ...)` тоже не объявляются закрытыми. Их runtime guards сохранены. Следующие native-course/export/owner JSON партии остаются вне этой рецензии.

Известный CI failure на исходном `f9c00ae` с bare type-only import map и ранним `QUARTO_PROFILE` рассматривается отдельно. Новая партия использует уже существующий `course-core/` способ разрешения и расширяет отдельный checker тем же образом. Параллельный fix actual Core pin/map существующих compiler entries/gate-before-env не читался и не включён в findings или в утверждение готовности CI.

## Собственный первичный допуск и действия

Собственный session file: `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-16-00-01a1a269-1e58-7e52-805f-d93c2a9cd8ef.jsonl`. `session_meta` строка1 подтверждает chat `01a1a269-1e58-7e52-805f-d93c2a9cd8ef`, parent `01a1a142-7adc-7371-9765-4871d807dfcd` и agent path `/root/review_pl_materialization_types`. Единственный собственный `turn_context` текущего нового интервала — строка8, turn `01a1a269-1e87-73a2-9c49-4483ed472c6e`: top-level `model=gpt-6.1-sol`, `effort=high`; вложенные settings — те же `model` и `reasoning_effort=high`. Расхождений в собственном интервале нет. Prescribed service tier — Standard; эта primary запись серверный tier не удостоверяет, настройки не менялись.

Прочитаны PL `AGENTS.md`, `spec/index.md`, локальная копия общего языкового правила Core и два заданных отчёта. Product Source, ветки, pins, глобальные settings и нормативные документы не менялись. Единственная запись рецензента — этот русский report вне Source repo. Проверки автора не повторялись; новый runtime/probe/compiler/check/install запуск не выполнялся.
