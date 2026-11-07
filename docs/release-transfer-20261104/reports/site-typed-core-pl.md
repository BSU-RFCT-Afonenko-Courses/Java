# Типовая граница Site Java → Core/PL

## Собственный первичный допуск

Назначение gpt-6.1-sol / high, Standard. Собственный chat `01a1a4c7-e908-76b2-8186-a6416745b443`, agent path `/root/site_typed_core_pl`, исходный turn `01a1a4c7-e935-76d1-8b1e-e233d3f57561`.
Источник: `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T07-18-47-01a1a4c7-e908-76b2-8186-a6416745b443.jsonl`.
Прочитаны session_meta и весь текущий интервал turn_context от исходного хода: верхние `model=gpt-6.1-sol`, `effort=high` и вложенные `collaboration_mode.settings.model=gpt-6.1-sol`, `reasoning_effort=high` совпадают. Standard назначен; service_tier отсутствует, скрытая серверная маршрутизация не удостоверяется.

Исходник `/home/tolya/JavaCodex/java-release-20261110/site-java`, branch `codex/java-release-20261110`, HEAD `e8b345cec235c2c8438c479892f6bca2a4ce3ee7`; первоначальный `git status --short` пуст. Прочитаны AGENTS, spec/index и contract, локальное общее правило русского языка из Core, reports/site-type-boundary-reading.md и typescript-any-audit.md. Код пока не изменён; следующий scope передан root до реализации.

## Согласованный и выполненный объём

Root GO ограничил первую партию двумя production файлами: `_extensions/course-site/application/compose.ts` и новым `application/sibling-contracts.ts`. Последний содержит только type imports/exports и actual `SiblingModuleMap`: `typeof` настоящих public modules Core и PL. Перегрузка существующего lazy loader возвращает соответствующий module type для двух этих имён. Динамическая загрузка и существующие проверки отсутствующего файла/owner namespace сохранены; новых export validators, runtime checks и casts нет.

`prepareNativeAdapter` получает настоящий Core module и проверяемый actual expectation DTO. `withCurrentNativeOperation` выводит callback `NativeOperation` из Core. `ownerRelease` принимает actual operation/Core, строит `request satisfies NativeReleaseRequest`; catalog coverage получает `NativeCatalog` и узкий `NativeSiteRecord`. Поля record выводятся из существующего Collection Record и настоящих Project/Workspace constructors; ручных дублирующих Project/Workspace DTO нет. `Workspace.config` остаётся историческим `any` и этой партией не квалифицируется.

Первый `coreProof` выводится непосредственно из настоящего `operation.validateRelease`. Возвращаемый `proof` намеренно имеет `unknown`: второй producer `prairielearn/application/native-adapter.ts:320` принимает `operation:any`, а `validatedCourse.proof:any` и вызов `operation.validateRelease` дают `Promise<any>`. У Site не было существующего schema parse на этом входе; новый runtime parse не добавлен и результат PL не объявлен NativeReleaseProof. Это точный остаточный upstream gap для отдельного PL owner.

Единственное non-null narrowing `adapter!` согласовано root после чтения producer. `core/runtime/operation.ts:3095` передаёт список validators в `validateNativeRelease`. `core/release/validate.ts:40–50` сохраняет executable descriptors до первого await; `:99` строит byName из этого списка; `:222–231` требует validator для каждого `course.adapters`, иначе бросает `RELEASE.ADAPTER_UNQUALIFIED`; только после успешной validation `:283` помещает `course.adapters` в genuine proof. Site передаёт единственный optional adapter, const binding которого не переназначается. Поэтому имя `prairielearn` в первом genuine Core proof доказывает существование именно этого adapter. Это не narrowing внешнего JSON или второго PL any. Runtime условие и вызов остались прежними.

Java 6 native flow сохранён: один child `quarto render . --to project.to`, `selectedSources/to` record, owner-root scopes, проверка catalog outputs/формата, порядок native pre/post и один/два release validation passes. Collection, state JSON/config, Download observer, QRC/Navigation lazy routes остаются явно прежним долгом. Source SP `e0efd6`, manifests/pins, installed copies, учебные исходники и чужие Core/PL writers не изменены.

## Адресная проверка

Штатный Source compiler gate остаётся **UNKNOWN**. Ниже выполнена отдельная ограниченная проверка affected graph, а не квалификация всего Site или CI.

Actual providers при проверке: Core `ad80be00d6617a427ba6ae7a3e17a55fa1ef5888`, PL `30edc03295e508f174c64b399f35b14ad37488fa`. Download `fcff6ad94c66b61948c44cf8a2c080f8758abfd9` прочитан только для установления остаточного unknown observer contract; его runtime route не вошёл в новую ModuleMap.

Временная `/tmp/site-typed-core-pl/imports.json` содержит canonical realpaths:

```json
{
  "course-core/": "file:///home/tolya/JavaCodex/java-release-20261110/core/_extensions/course-core/",
  "course-prairielearn/": "file:///home/tolya/JavaCodex/java-release-20261110/prairielearn/_extensions/course-prairielearn/",
  "stdlib/path": "jsr:@std/path@0.224.0",
  "stdlib/fs": "jsr:@std/fs@0.224.0"
}
```

Core relative sibling import `../../course-navigation/exercise-index.ts` разрешается в настоящий `/home/tolya/JavaCodex/java-release-20261110/core/_extensions/course-navigation/exercise-index.ts`, не stub или installed UI иной версии. Типовые alias probe дополнительно указывают на реальные compose/helper файлы. Использован bundled Deno 2.7, известный штатный Quarto cache `DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache`, `--deny-import --no-config --no-lock`; `--cached-only` не использовался.

Первый baseline без штатного DENO_DIR завершился отказом загрузки JSR manifest под `--deny-import`; после назначения известного cache baseline одного compose завершился exit0 (0.76 с). Baseline не повторялся. Первый type-diff pass (4.31 с) показал только ожидаемый `adapter possibly undefined` и два negative; после чтения genuine producer и согласованного non-null narrowing финальный проход (4.30 с) дал **ровно два TS2322 в двух временных negative файлах и ноль production diagnostics**:

```text
DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache \
/opt/quarto/bin/tools/x86_64/deno check --deny-import --no-config --no-lock \
  --import-map /tmp/site-typed-core-pl/imports.json \
  _extensions/course-site/application/compose.ts \
  /tmp/site-typed-core-pl/wrong-projection.ts \
  /tmp/site-typed-core-pl/wrong-prepare.ts
```

- `wrong-projection.ts`: actual Core `withCurrentNativeOperation` с `projection:"full"` → TS2322, разрешены только `student | author`.
- `wrong-prepare.ts`: actual PL `prepareNativeAdapter(Core, {profiles:[1], ...})` → TS2322, профиль должен быть string.

Общий exit1 является ожидаемым отказом negative compilation; отдельный production-only exit0 после diff не выдаётся за выполненный. Тот же invocation проверил affected compose graph и сообщил ошибки только в явно отрицательных файлах. Новый постоянный checker/framework и runtime fixtures не созданы. Суммарная стоимость compiler процессов около 9.4 с, включая baseline и диагностический промежуточный проход. `git diff --check` завершился exit0.

Полные suites, render/build/setup, `quarto inspect`, hash-аудиты, installation/network/fetch/push не выполнялись. Изменение не добавляет исполняемых guards и не меняет control flow, поэтому runtime запуск по согласованной политике не требовался.

## Итог допуска перед локальным commit

Снова прочитан весь первичный исходный интервал: единственный turn_context строка 8, исходный turn `01a1a4c7-e935-76d1-8b1e-e233d3f57561`; gpt-6.1-sol/high совпадают сверху и в collaboration_mode settings. Основание начала роли не сдвигалось. Изменения готовы к отдельному review; полный Source type coverage и выпуск не заявляются.

Локальный commit `d16f232` создан с отключёнными hooks только для двух заявленных production файлов. Report находится снаружи Site Source и в commit не входит; push не выполнялся.
