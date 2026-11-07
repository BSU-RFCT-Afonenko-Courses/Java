# Performance: обычная диагностика без обязательного package inventory

## Допуск и база

Technical Source writer: назначено `gpt-6.1-sol/medium`, Standard. Собственный chat `01a1a4e9-0520-7a60-8acf-168b69447c20`, agent `/root/performance_routine_inventory`. Primary: `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T07-54-57-01a1a4e9-0520-7a60-8acf-168b69447c20.jsonl`, session_meta строка 1; исходный turn `01a1a4e9-054a-77b3-9cf6-0ce7798b69e5`, root turn `01a1a4dd-242d-7930-89b5-5da44195e538`. Весь интервал роли от этого turn содержит один turn_context (строка 8); верхние `model=gpt-6.1-sol`, `effort=medium` согласованы с вложенными collaboration_mode model/reasoning_effort. Вход и итоговая проверка PASS. Standard назначен; отдельного service_tier в primary нет, серверная маршрутизация не подтверждается, настройки не менялись.

Рабочая область только `/home/tolya/JavaCodex/java-release-20261110/performance`, исходный HEAD `924b306cfa34c7ce0f79ca4050d045b6d4cb3e29`, ветка `codex/release-performance-20261110`, на входе дерево чистое. Прочитаны README, docs/development.md, docs/plan/03-contracts.md, текущие docs/diagnostics.md и docs/general-use-formats.md, `java/docs/model-admission.md` и `reports/pattern-pass-performance-ci-20261104.md`. Applicable AGENTS на цепочке родителей/в Performance не найден. Использована verification-before-completion для проверки утверждений; новый workflow/проверяющая утилита не вводились.

## Точный diff

- `src/runner/diagnostic.ts`: удалены только четыре строки `packageCoverage: await collectPackageCoverage(target.projectRoot, [], [workspace, ...outputDirectory])` в `prepareRenderDiagnostic`. Обычные summary/cpu больше не требуют recursive project inventory, чтения/лексического анализа TS/Lua и hash evidence ради установленного списка пакетов. `complete:false`, языковые agent flags, target/runtime/hooks остаются прежними. `prepareDiagnostic` с `scenario.scope.extensions` и его import сборщика сохранены.
- `tests/runner/package-coverage.test.ts`: первый существующий fixture обращается непосредственно к явному `collectPackageCoverage(project, ["marked", "unmarked", "absent"])`; сохраняет проверку nested scope, physical marking, requested installation и неизменности fixture. Убраны probe/launcher/native dependencies теста и приведение prepared.coverage через unknown/any. Те же recorded events затем нормализуются с coverage без inventory: `inventoryStatus=unknown`, `scopeRoot/packages/requested=null`, `complete=false`, `dataValid=true`, наблюдения и valid observationStatus сохраняются. Старый no-inventory fixture и static marking fixture сохранены.
- README, docs/diagnostics.md, docs/general-use-formats.md: обычные summary/cpu явно описаны как logical observations без installed inventory; физический inventory остаётся у диагностического эксперимента. Нет нового CLI flag, scanner/cache, receipt, DTO или runtime validation.

Внутри Performance ровно один production file; всего пять tracked files, diff 35 additions / 35 deletions. Этот отчёт находится в общем reports вне Performance. Commit/push/PR не выполнялись.

## Фактические consumers и неизвестность

`normalizeRun` (`src/analysis/normalize.ts:265–270,418–449`) читает coverage, использует `complete === true` и передаёт отсутствующее packageCoverage в существующий `observePackages`. Последний (`src/runner/package-coverage.ts:174–217`) создаёт unknown/null inventory и независимо группирует scoped events по extension_id или operation prefix. Поэтому отсутствие installed списка не превращается в пустой список, false installation или complete coverage. Повторная нормализация уже записанного unknown объекта также сохраняет null поля.

По поиску фактических packageCoverage consumers: обычный renderer передаёт coverage на сохранение и нормализацию; report/export используют качество нормализованного run и artifact coverage.json, не выводят отсутствие physical пакета из отсутствующего inventory. Единственный отдельный consumer `src/cli/experiment.ts:244` ищет recorded observations для requestedExtensions; экспериментальный producer inventory не менялся. `installed.ts/lua` остаются флагами агентов, а не установкой пакетов. Production TS types/casts не добавлялись и не ослаблялись; строгая проверка затронутого графа выполнена одним program.

## Проверки и ошибки

1. `/usr/bin/deno check --cached-only --frozen src/runner/diagnostic.ts tests/runner/package-coverage.test.ts` — exit 0, оба entry points проверены одним program.
2. `/usr/bin/deno test --cached-only --frozen --no-check -A tests/runner/package-coverage.test.ts` — 3 PASS / 0 FAIL, 35 ms: explicit inventory + recorded observation без inventory, старый unknown fixture, static marking/duplicates.
3. `/usr/bin/deno fmt --check src/runner/diagnostic.ts tests/runner/package-coverage.test.ts` — exit 0, 2 files.
4. `git diff --check` — exit 0.

Typecheck и tests выполнены по одному разу; diff whitespace check повторён после записи отчёта. Native child, general render, capture, benchmark, build, inspect и сетевые установки не запускались. Существующая проверка treeHash относится только к временной маленькой package fixture и её неизменности. Ошибок typecheck/tests нет. Первый read отчёта по пути performance/reports дал NotFound; прочитан правильный общий reports. Formatter первоначально переформатировал несвязанные абзацы Markdown; эти изменения сняты из diff, оставлены только содержательные изменения.

## Ограничения и результат

Удалён доказанный обязательный полный обход обычного diagnostic пути. Его wall/CPU/RSS стоимость и ускорение на курсе — **UNKNOWN**; 35 ms относятся только к fixture tests и не являются измерением ускорения. Ordinary runtime end-to-end этой дельтой не перепроверялся; доказательства — source removal, affected graph typecheck и existing normalization fixtures без Native child. Часть подсказок об установленных/статически размеченных пакетах в новых обычных отчётах становится unknown по замыслу. Supervisor/signals, capability probe, binary hashes, launcher setup и trace exports вне дельты. Независимое scoped review назначает root; данный отчёт его не заменяет.
