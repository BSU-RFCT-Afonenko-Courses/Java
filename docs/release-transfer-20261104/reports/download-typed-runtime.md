# Типизация native-цепи Download

## Допуск Source owner

Собственный chat `01a1a342-7ad5-7220-bcde-98cdbd32cf05`, путь `/root/download_typed_runtime`;
исходный turn `01a1a342-7b00-70b2-9ee6-23ae6c883f79`, root turn `01a1a338-3b4c-79a1-98e8-f635eca07373`.
Первичный источник: `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T00-13-25-01a1a342-7ad5-7220-bcde-98cdbd32cf05.jsonl`.
`session_meta` строка 1 подтверждает собственный chat/path; `turn_context` строка 8:
верхние `model=gpt-6.1-sol`, `effort=high`, вложенные `collaboration_mode.settings.model=gpt-6.1-sol`,
`reasoning_effort=high`. Назначение Sol6.1/high — PASS. Standard назначен координатором;
первичные записи не содержат отдельного `service_tier`, скрытый серверный tier не утверждается.
Глобальная конфигурация не менялась. Перед завершением перечитан полный интервал от исходного turn: единственный `turn_context` строка 8; назначение top/nested совпадает на всём интервале, исходная точка не сдвинута.

## Исходная точка

Source `/home/tolya/JavaCodex/java-release-20261110/download`, ветка `codex/java-release-20261110`;
HEAD `c81df7364dd1fbbd07a0e54d67714b3d66f9695e`, рабочее дерево чистое при входе.
Ранее созданный изолированный worktree принят от координатора; исходный `--no-checkout` clone не изменялся.
Прочитаны `AGENTS.md`, `spec/index.md`, `spec/contract.md`, локальное общее языковое правило Core,
нормативный `java/docs/model-admission.md`, release state и аудит TypeScript.

## Согласованная граница

Три production-файла: общий module map типов и ленивый loader, типизированная native-цепь
`infrastructure/runtime.ts`, schema-inferred inventory в `application/package-artifact.ts`.
Обычные ресурсы сохраняют ленивую runtime-зависимость от Core: все статические ссылки на sibling contracts стираются.
Compiler composition проверена в отдельном scratch с фактическими public sources Core/PL/QRC.
Два отрицательных DTO-контроля: resource inventory нельзя передать вместо artifact inventory;
project context нельзя передать вместо project plan. Новые runtime validators, guards и casts не планируются.
JSON Inspect/ownership/request остаются отдельной обозначенной legacy-границей.

Проверки сборки, render/inspect/hash, сеть, install, push и PR не выполнялись.

## Реализованный diff до внешнего review

`infrastructure/siblings.ts` связывает literal имя реально загружаемого sibling с `typeof import` его фактического `public.ts`.
Generic lazy loader возвращает именно соответствующий module type. Вызов `import()` остаётся динамическим по прежнему sibling пути;
отдельного facade interface, `as`, `any` или runtime-проверки этого модуля не добавлено. Внешний module loader — доверенная
граница исполнения кода; статический договор берётся у фактического provider, а не придумывается Download.

`runtime.ts`: optional пара `{operation, Core}` исключает независимые фиктивные значения;
сам `NativeOperation` выведен public Core. Maps хранят `ProjectContractContext` и `ProjectPlan`.
Batch callback, documents, closure/reference callbacks и materialized/resource files выводятся из реальных методов.
`plan` и format input set сохраняются локально через get-or-create. Ветка `if (!operation) return pack()`
явно маршрутизирует прежний standalone случай и не добавляет validation/refusal. В native сборке project requests
сохранён короткий `find`; `if (doc)` выражает прежнюю ветку `doc?.exercises … ?? []`. Порядок artifacts,
форматов и inputs сохраняется. Новых scans/validators для DTO не добавлено.

`package-artifact.ts` использует тот же lazy loader и выводит inventory/files из существующего
`ArtifactInventorySchema.parse`; ручные типы отдельных file callbacks удалены. Clone-before-await,
схема, аудитории, target collisions, hash/size checks, порядок ZIP и публикационные fences прежние.
Публичный `packageArtifact(value: unknown)` сохраняется: внешняя JSON/данная граница обязана проходить имеющийся parse.

Две локальные `!` после `catalog.find` согласованы координатором как конкретная доказанная correlation,
не cast внешнего JSON: Core `runtime/operation.ts:1748–1798` создаёт documents и catalog из одного finalized document,
а exercises берёт из тех же visible occurrences; `document()` (`:2709–2727`) отвергает чужой source,
`condition()` и `document/condition.ts:22–50` требуют ровно одно видимое упражнение. Оба успешных вызова
предшествуют этим двум lookup. Нормализованный `catalog.exercises.project` сохранён; сырой `condition.project`
ему не подставляется. Для других nullable lookup assertions не добавлены.

Остаток честно ограничен: Inspect/config, ownership signature JSON/request receipt и другие отдельные legacy JSON/AST границы
не типизировались этой партией. В `runtime.ts` остаётся один явный `any` в consumer JSON ownership signature;
он не перенесён в operation/materialization API. Текущий PL `inspectPublication` сам содержит any-параметры:
module map отражает фактический API, а возвращаемый proof Download по-прежнему разбирает публичной Core schema.
Эта партия не объявляет PL implementation полностью typed.

## Адресная проверка и текущий результат

Новый `tests/runtime-types.ts` импортирует affected graph и actual public module types;
`NotAny` проверяет module/inventory/file/resource inventory. Два предметных `@ts-expect-error`:
resource inventory нельзя присвоить artifact inventory (в нём отсутствует exercise/другой schema kind),
project context нельзя подать в `materializationClosure` вместо результата `planProject`.
Если соответствующая гарантия исчезнет, директива станет неиспользованной и compiler откажет.

Compiler composition создана только symlink в `/tmp/download-typed-ba50b79657404218`:
живые Source Core/PL/Download (после исправления также Core Navigation) и одна фактическая installed QRC-копия из Java/tasks
(ref `798f8c0d9f87fd5dcfe9ee85b79df57e647d2f3c` по принятому audit). Исходники provider не копировались,
installed курсы не менялись. Запуск через `quarto run /tmp/download-check.ts` наследует штатный dependency cache;
child выполняет bundled Deno `check --deny-import --no-config --no-lock --import-map … tests/runtime-types.ts`.
`stdlib/path`/`fs` закреплены на имеющихся `jsr:@std/*@0.224.0`, сеть запрещена `--deny-import`.

1. Первый запуск остановился до графа: `deno check` не поддерживает `--cached-only` (0.005s). Ошибка invocation,
   не TypeScript-ошибка. Исправлен флаг, успешные проверки не повторялись.
2. Второй остановился при resolution: `import.meta.resolve(stdlib/path)` вернул HTTPS JSR URL,
   который bundled compiler отвергает при static typechecking (0.093s). Переведён в canonical `jsr:`.
3. Следующий affected запуск (6.463s) дошёл до графа, но scratch не содержал реальный Core sibling
   `course-navigation`: literal dynamic import `../../course-navigation/exercise-index.ts` не предоставил contextual
   typing callback. Появились два TS7006 (`source`, `anchor` в `operation.ts:1909`). Это **моя ошибка compiler
   composition**, а не дефект Core Source; координатор установил причину, чужой Source не исправлялся.
4. В scratch добавлен symlink фактического Core `_extensions/course-navigation`; другие external literal imports
   Core просмотрены. Исправленный affected compiler graph **PASS**, exit 0, 6.424s,
   `/tmp/download-typed-eebfe497b5ffb12c`. Два отрицательных DTO cases и четыре `NotAny` включены в этот один
   проход. Финальные args/stdout/stderr/env cache: `download-typed-compiler.json`.
   Dependency cache `/home/tolya/.cache/quarto/deno_std/cache`, `--deny-import`; сеть не использовалась.

`git diff --check` после production/test diff — PASS. Полных suite, render/inspect/hash, install/setup,
новых IO validation, push/PR или изменения course pins не было. Отдельный старый runtime fixture не выбран:
native suites здесь запускают render и дублировали бы запрещённую проверку, а чистая типовая партия
проверяется compiler graph и предметными отрицательными cases.

## Исправление существующего compiler runner

Согласованы ещё два non-production файла: `tests/contract-consistency.ts` и одна переменная окружения
в `.github/workflows/check.yml`. Runner теперь сопоставляет source/scratch sibling URL с фактическими
`CORE`/`PL`/`QRC` public roots; принимает Source root, namespaced extension layout и сам extension root.
Реальные root paths нормализуются через `realPath`, поэтому literal Core Navigation импорт получает
настоящий sibling без отдельного facade. Bare `course-core/` map поддерживает actual текущий PL.
Новый `runtime-types.ts` включён в **тот же** positive invocation, а затем в прежний config drift graph.
Новых постоянных framework/flags/helper runner files нет. Child compiler использует `--deny-import`;
необходимые stdlib types берутся из штатного cache.

Workflow добавляет только `PL` в уже существующий `tools/check.sh` env и переиспользует его прежний
checkout `pl-fixture`. Actions/матрица/immutable Core `68074174…`, PL `df8bd4aa…`, QRC `798f8c0d…` не менялись.
Локального Git tree старого Core `68074174…` здесь нет; его hosted composition этой партией не проверена,
сеть для получения objects не вызывалась. Текущий actual stack PASS не переносится на прежний CI pin.

Координатор отдельно разрешил один запуск изменённого existing runner для проверки обеих изменённых
source/scratch maps; это заменило начальный запрет на прежний drift negative. Запуск
`CORE=…/core PL=…/prairielearn QRC=…/java/tasks/_extensions/Afonenko-Course-Tools/reference-catalog
QUARTO_RUN_NO_NETWORK=true quarto run tests/contract-consistency.ts`:
**PASS**, cap 30s, retry 0, 10.204s. Positive exit 0; прежний meaningful numeric-path drift exit 1,
8 ожидаемых diagnostics (включая actual consumer `contract-types.ts`), `consumerDriftCaught=true`.
В positive включены два новых предметных отрицательных DTO cases и четыре `NotAny` assertions;
это не процент покрытия всего Download. Полный результат: `download-typed-runner.json`.
Existing drift fixture копирует Download во временный scratch, как делал до этой партии;
это не provider install и не копирование sibling Source. После успеха compiler не повторялся.

Старые runtime/full suites не запускались. NoCore runtime-файлы сохраняют отсутствие eager imports:
public type references стираются, а lazy loader вычисляет тот же sibling путь с той же глубины каталога,
что оба прежних dynamic imports. Полный standalone render этой типовой партией не утверждается.

## Локальный результат для независимого review

README обновляет две команды `tools/check.sh` с `PL` и одной русской строкой объясняет
разницу между нужной для static composition type-only зависимостью и самостоятельным ordinary runtime.
Итоговый reviewable Source diff: **7 файлов**, из них 3 production (`runtime.ts`, `siblings.ts`, `package-artifact.ts`),
2 tests (`runtime-types.ts`, `contract-consistency.ts`), README и existing workflow env.
Локальный commit `fcff6ad` поверх `c81df7364dd1fbbd07a0e54d67714b3d66f9695e`, +184/−78.
Stage выполнен только по семи точным путям; Source worktree после commit чистый.
Перед commit перечитаны diff/docs и полный собственный primary интервал, `git diff --check` PASS.
После последнего compiler/runner PASS менялись только README и этот внешний report.
Независимый review и проверка прежних hosted CI pins остаются у координатора; install/push/PR/publication не выполнялись.
