# Типы PL checked selection → materialization и feedback descriptor

3 ноября 2026. Вторая ограниченная партия очереди `typescript-any-audit.md`.

## Исходники и собственный первичный допуск

Единственный изменяемый Source — `/home/tolya/JavaCodex/java-release-20261110/prairielearn`, исходный HEAD `f9c00aed6de610dce7d9b7cb6e50c28ebfb9573e`, исходное дерево чистое. Первая принятая Core-boundary партия сохранена. Прочитаны `AGENTS.md`, `spec/index.md` и действующее языковое правило `core/docs/russian-language-policy.md`. Core читался адресно, без правок: HEAD `0478458a71f3acf34ad6d9983ac11023783dde3d`, status чистый.

Собственный агент `/root/pl_typed_materialization`, chat `01a1a25f-b098-7242-bf6d-d0e8f6517361`, parent `01a1a142-7adc-7371-9765-4871d807dfcd`. Первичный файл `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-05-42-01a1a25f-b098-7242-bf6d-d0e8f6517361.jsonl`: `session_meta` строка1 подтверждает собственные ID/path; все `turn_context` собственного интервала (строка8, turn `01a1a25f-b0fb-7fd2-b244-83933af77834`) имеют top-level `model=gpt-6.1-sol`, `effort=high` и вложенные `collaboration_mode.settings.model=gpt-6.1-sol`, `reasoning_effort=high`. Расхождений нет. Серверный service tier эта запись не удостоверяет. Глобальные и нормативные model-admission документы не менялись.

## Решение и точный scope

До правок координатору сообщён подход: существующий переносимый type-only namespace `course-core/`, `Pick` настоящего публичного Core и вывод существующей PL-схемы. Новых runtime validators, registry, framework, повторных проверок bytes/identity/currentness нет.

`native-selection.ts` сохраняет реальные выходы `ArtifactInventorySchema`/`ProjectPlanSchema`, а его plan-входы имеют `ProjectPlan`. Локальный file subset выводится из `ArtifactFile`. Внешние inventories и check остаются `unknown` до прежнего parse. Прежние hash/role/audience/limits/selector/reference checks не менялись.

`native-check-projection.ts` принимает Core schema methods, настоящие inventory/plan и нормализованный PL fact: generic fact принадлежит `NativeProjectFact`, поле check — вывод `NativeProjectCheckSchema`. Sources/references/contract-origin records сохраняют точные inferred типы. `native-check-inventory.ts` получил только необходимый `Pick` настоящей `ArtifactFileSchema`; его hash/canonical/manifest helpers не переписывались.

В contract-cases JSON исходное значение теперь `unknown`. Одинарные assertions до `Record<string, unknown>`/`Iterable<unknown>` выражают только прежний доступ к полям и `for...of`; они не объявляют JSON валидированным DTO. Существующие schemaVersion/cases/id/path проверки остаются на тех же местах. Проверенные fixture/submission пути становятся строками через прежний `safePath`: изменена только его сигнатура `unknown → p is string`, с тем же телом и исходным `typeof p === "string"`.

`native-materialize.ts` использует настоящие `NativeAssignmentProjectPlans`, `MaterializableCondition`, `MaterializationBindings`, `SubmissionInstructions`, `ArtifactInventory`, `NativeOperation`. Входы workspace adapter выводятся из существующего `createNativeAdapter`, а узкий Download consumer принимает inventory и возвращает `Promise<Uint8Array>` по фактическому существующему `packageArtifact`. Core materialize/closure signatures берутся из public module; новые копии Core DTO не создавались. Reference binding maps имеют существующие Core-типы.

`runtime-descriptor.ts` принимает `NativeProjectCheck`, выводит конкретный descriptor и feedback `{requirement, whatCheck}`. Авторская feedback-форма `{requirement, 'what-check'}` остаётся выводом существующей строгой схемы. `RuntimeProfile` выводится из прежней явной profile-signature `javaBuild`. У `publicCases`, `javaBuild` и `normalizeDiscovery` уточнены только необходимые типовые входы; прежние runtime guards сохранены. Non-null assertion у API declaration опирается на непосредственно предшествующий прежний Boolean parity guard и не добавляет runtime-действия.

Изменены девять product-файлов в `application/`: `native-selection.ts`, `native-check-projection.ts`, `native-materialize.ts`, `runtime-descriptor.ts`, а также только необходимые типовые части `native-checks.ts`, `native-check-inventory.ts`, `java-build.ts`, `grading-descriptor.ts`, `source-selection.ts`. Два test-файла: расширен существующий `tests/native-core-typecheck.ts`, добавлен `tests/native-materialization-types.ts`.

`native-course`, `export-course`, AST, owner config/JSON, Core, source data, UUID и scoring не менялись. Runtime path/authority/lifetime/currentness/grade/wire fields не переключались. Новых `as any`, двойных casts, `ts-ignore`/`ts-expect-error` нет; существующие observability suppressions не переписывались.

## Проверки и результаты

Применён адресный existing checker по штатному Quarto/Deno и существующему type-only map:

`quarto run tests/native-core-typecheck.ts ../core` — итоговый exit0. В одном общем положительном графе проверены product entry `native-entry.ts`, прежние Core-boundary assertions, новые materialization assertions и сам runner. Отдельные прежние три файла и installed composition не запускались повторно. Новые assertions используют `Exact` с отдельным отказом `any`: plan/parse outputs, pair, condition, bindings, materialized inventory, check и обе feedback-формы, reference/source outputs.

Четыре новых отрицательных compiler cases требуют ненулевой exit и конкретную диагностику:

- runtime `whatCheck` на авторском входе `runtimeDescriptor` → `TS2561`;
- авторский `'what-check'` на выходной runtime feedback-форме → `TS2353`;
- `audience: 'author'` у materialization student plan → `TS2322`;
- `ProjectPlan` вместо `MaterializableCondition` → `TS2322`.

В этом же combined запуске сохранены четыре прежних controls: adapter/scope typo → `TS2551`, неверная assignment preparation/descriptor projection → `TS2322`. Все восемь получили ожидаемый отказ. Отдельных повторов первой партии не было.

До итогового зелёного запуска были исправлены compiler-signature недочёты: shadowed type-import name, ошибочно выбранный helper вместо `createNativeAdapter`, optional API declaration. Пробная `checkedJSON: unknown` открыла соседний `native-course` owner JSON consumer; изменение откатили, этот scope не включён. Два следующих прохода прошли positive graph, но runner правильно отказал из-за неточных ожиданий кодов новых feedback diagnostics; ожидания уточнены до фактических `TS2561` и `TS2353`. Итоговый полный combined проход зелёный; после него checker не повторялся.

`deno fmt --check` для восьми затронутых форматируемых TS-файлов — exit0. В прежних компактных `runtime-descriptor`, `grading-descriptor`, `source-selection` сделаны только узкие правки без общей переформатировки. `git diff --check` — exit0. Просмотрен весь собственный diff.

Runtime suites не запускались: продуктовые изменения типовые, прежние runtime guards/операции сохранены. Render/inspect/hash/build/full suite/install/push не выполнялись.

## Остатки и пределы

Эта партия не закрывает весь PL stack. `native-course` и дальнейший export/AST остаются отдельными владельцами долга; их существующие `any` callers ещё способны передать непроверенные значения. PublicExtension/QRC dynamic API и owner config/checkedJSON, внутренний adapter state, legacy `validateAdditionalJavaSources` raw input и прочие нетронутые helpers не объявлены полностью строго типизированными. Type annotations не дают runtime authority и не заменяют live Core fences.

Координатор сообщил отдельный подтверждённый CI failure на уже pushed f9: run `41680971465`, type-only mapping штатных compiler entries и gate-before-env. Этот CI-fix не смешан с данной партией; `--no-check` не применялся. Готовность всей CI/production не заявляется. Production требует отдельного итогового review и разрешения координатора; срок релиза — 10 ноября 09:00 Minsk.

## Source commit

Commit `4e4ab0cd5499006826f5ab21d1e3fdeef9c313b1` — «Сохранить типы Core при materialization и feedback PL». Ровно 11 перечисленных Source/test-файлов, `284 insertions(+), 63 deletions(-)`. После commit PL status чистый, `git diff HEAD^ HEAD --check` exit0. Core оставлен чистым на `0478458a71f3acf34ad6d9983ac11023783dde3d`. Отчёт находится вне PL Source repo. Push/install не выполнялись.


## Последующий отдельный CI-fix

После завершения этой типовой партии отдельно подготовлен commit `da2ad6b6c00891590e59e5abccf3d1eb4ff552f7`. Причины, собственный diff и адресная проверка описаны в `pl-ci-core-map-gate.md`. Типовой verdict этой партии не подменяется готовностью CI-fix: ему требуется независимый review до push/install.
