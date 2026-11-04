# Независимый review удаления routine package inventory Performance

4 ноября 2026. База Source `924b306cfa34c7ce0f79ca4050d045b6d4cb3e29`; предмет review — рабочий diff пяти файлов поверх этой базы, без commit автора.

## Собственный первичный допуск

Агент `/root/review_pl_legacy_types`, chat `01a1a348-784d-7771-a540-bbcf7ed8735e`, parent `01a1a142-7adc-7371-9765-4871d807dfcd`. Исходный turn полного собственного интервала `01a1a348-7879-7070-a59e-6a29186a8ad2`; текущий Performance review turn `01a1a4eb-a098-7e50-a284-938e90f8838d`. Primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T00-19-58-01a1a348-784d-7771-a540-bbcf7ed8735e.jsonl`, session_meta1, все turn_context8/133/170/243/306/488: top-level и nested `gpt-6.1-sol/high` согласованы. Исходная точка не сдвигалась. Standard назначен, скрытый серверный tier не удостоверяется; globals не менялись.

Допуск выполнен до содержательного чтения consumer chain. Applicable AGENTS в Performance не обнаружен по сообщению владельца; проверка корневого `AGENTS.md` также даёт отсутствие файла. Весь исходный интервал роли с перечисленными шестью turn_context согласован; baseline не сдвигался.

## Решение

**APPROVE рабочего diff пяти файлов поверх `924b306cfa34c7ce0f79ca4050d045b6d4cb3e29`.** Блокирующих findings нет. Единственный production change удаляет четыре строки обязательного package inventory в `prepareRenderDiagnostic`; explicit scenario collector и существующая неизвестность downstream сохранены. Новый CLI/cache/framework/validator не введён, TypeScript contracts не ослаблены.

Проверены полный diff, отчёт владельца, реальные producer/normalizer/report/experiment consumers и относящиеся existing tests. Compiler/tests/native render/inspect/hash/build/network reviewer не запускал. Source/state не менял, commit/push не выполнял; записан только этот report.

## Реальные условные пути

- `src/runner/diagnostic.ts:113–155`: обычный summary/cpu producer по-прежнему создаёт coverage object, `complete:false`, языковые installed/instrumented/observed flags, target, runtime и hooks. Убрана только рекурсивная inventory collection; создание launchers, capability probe и сохранение наблюдений не выключены.
- `src/runner/render.ts:104–123,151–155`: при mode≠off ordinary path вызывает этот producer, записывает coverage.json даже без packageCoverage, затем выполняет прежний normalizeRun. Поэтому новый случай — **существующий coverage object с undefined inventory**, а не пропавший файл, не `packages:[]` и не выдуманный отрицательный результат установки.
- `src/analysis/normalize.ts:265–270,417–447`: полное покрытие берётся только из `c.complete===true` и дополнительно ограничивается dataValid. Ordinary producer оставляет false; normalizer передаёт отсутствующий packageCoverage в существующий `observePackages`. Scoped event filter run/experiment остаётся прежним.
- `src/runner/package-coverage.ts:174–211`: undefined inventory создаёт `inventoryStatus:'unknown'`, `scopeRoot:null`, `packages:null`, `requested:null`. Наблюдения независимо выводятся из actual `extension_id` или documented derived operation prefix. При корректных events observationStatus остаётся valid; при некорректных — partial. Повторная нормализация этого recorded unknown object сохраняет его null inventory fields через существующий spread, а не превращает их в пустые массивы/installed:false.
- `src/report/run.ts:117–127,184–193` сохраняет нормализованный coverage как artifact и экспортирует quality.coverageComplete; неизвестный inventory не становится coverageComplete. `render.ts:175–181` дополнительно оставляет ordinary RunRecord coverageComplete false. Прочитанные report/perfetto consumers не выводят physical installation из отсутствующего списка пакетов.
- `src/runner/diagnostic.ts:13–68`: explicit `prepareDiagnostic` сохраняет `collectPackageCoverage(target.projectRoot, scenario.scope.extensions, exclusions)`, requestedExtensions и collector import. `src/cli/experiment.ts:220–254` выбирает именно этот producer и читает recorded observations для requested extension IDs; его inventory path не затронут.

`installed:{ts:true,lua:true}` в ordinary coverage — существующие agent activation/setup flags, не список установленных extension packages. Они не подменяют null `packageCoverage.packages/requested`. Поле `complete:false` и документация прямо сохраняют различие между наличием agents, данными событий и неизвестным physical inventory.

## Тест не скрывает исчезнувшее Native поведение

Из `tests/runner/package-coverage.test.ts` удалена прежняя подготовка `prepareRenderDiagnostic`, которая тащила runtime capability/launcher setup ради доступа к inventory. Предмет прежних assertions был physical package inventory, static marking и observations; после намеренного исключения inventory из ordinary producer его правильный владелец — explicit collector, который теперь вызывается напрямую.

Сохранены meaningful проверки nested root: родительский package не попадает в список; реальные marked/unmarked package files отличаются от комментариев/строк; requested marked/unmarked/absent получают true/true/false именно при полном explicit inventory; collector не меняет собственную fixture. Убраны прежние `as unknown as Record<string,any>`/callback any вокруг результата подготовщика; typed actual `PackageCoverage` теперь используется напрямую. Остаточный any после JSON.parse — историческая test boundary, новых production any/casts нет.

Новые строки94–104 повторно используют те же recorded events с coverage без inventory и прямо проверяют null/unknown поля, false complete, valid data/observation status и точное сохранение observations. Это конкретный новый production case, а не assertion расположения вызова или нового числа checks. Прежние unknown-history и static marking/duplicate physical copies cases остаются.

Actual public render preparation/selected binaries/share/functional options и выполнение подключённого Pandoc init по-прежнему покрывает отдельный existing `tests/agents/quarto.test.ts:140–218+`; этот файл/assertions diff не удаляет. Explicit experiment observation semantics проверяет прежний `tests/analysis/refreshed-report.test.ts:170–178`. Reviewer эти native tests не повторял и не объявляет новым PASS. Нет основания требовать новый runtime seam, framework или full native matrix для удаления единственного inventory вызова.

## Документация, evidence и пределы

README, `docs/diagnostics.md`, `docs/general-use-formats.md` корректно перестают обещать ordinary physical inventory и описывают null unknown fields после normalization. Explicit diagnostic experiment продолжает собирать selected-project-tree inventory. Наблюдение операции не объявляется выполнением всех операций установленного пакета.

Принято предоставленное evidence владельца: один affected program typecheck PASS (`diagnostic.ts` + существующий test), три existing tests PASS за35ms, fmt и diffcheck PASS. Эти успешные проверки не повторялись.35ms — стоимость малых tests, не измеренный выигрыш рендера. Удаление actual recursive/read/static-token/hash path из ordinary preparation подтверждено Source; ускорение курса, CPU/RSS и стоимость оставшихся capability/launcher операций остаются UNKNOWN.

Дельта не меняет supervisor/status handling/SIGTERM, process clocks, receipt timing или Java grading. Она не исправляет standalone SIGTERM UNKNOWN и не объясняет/устраняет Java900s. APPROVE относится к данному ограниченному Source diff, не к установленному пакету, whole release или новой общей гарантии качества.
