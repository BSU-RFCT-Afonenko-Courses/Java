# PL mixed trusted-tests: read-only оценка ограниченного продолжения

3 ноября 2026. Исходный PL HEAD `09064082b48b1bd60b7d3910098db30138d9fed6`, дерево чистое. Независимый review предшествующей партии — APPROVE (`pl-typed-course-export-review.md`). Данный отчёт не расширяет этот verdict до смешанного legacy/native loop.

## Решение

**В заданный предел ≤3 production files с опорой на уже существующие real DTO цепочка полностью не укладывается. Изменения Source/tests не выполнялись; новый commit не создавался.** Координатору передана трёхстрочная оценка, работа остановлена по его условию «если невозможно так узко — записать точный остаток».

Причина не в сложности одного цикла: у legacy ветви пока нет точного producer DTO. Объявить тип локальной переменной после `checks:any` означало бы сохранить тот же непроверенный producer, скрыв его за аннотацией. Создавать такую facade или casts ради compiler green не стали.

## Прочитанный chain и точный остаток

- `export-course.ts:143,470–473,528–549`: `input.checks:any` делает `find` result и `fact.trustedTests` неточными. В условном iterable typed native `{submissionRelativePath, bytes}` смешивается с legacy `any[]`, поэтому общий `test` теряет точный payload type. Проверка `nativeSelection ? test.bytes : readSelected(root,test)` дополнительно выражает корреляцию выбора iterable и reader, которой пока нет в DTO.
- `native-check-inventory.ts:92–108`: `nativeChecksManifest` принимает `projects:any[]`. Сортировка сохраняет значения runtime, но return type не сохраняет точный generic project producer. Локальная типизация exporter без этой границы не докажет real native producer→manifest→consumer flow.
- `native-check-projection.ts:55–85` и `native-check-inventory.ts:25–87`: native origin берётся после настоящей `ArtifactFileSchema.parse` и существующих source/path/hash checks. Это реальный inferred union с `projectRelativePath` **либо** `ownerRelativePath`, общими `submissionRelativePath`/`sha256`. Подменять этот union legacy-only формой нельзя: shared owner origin — действующая version2 semantics.
- `source-selection.ts:41–61`: `readSelected(root,s:any)` — legacy physical-source reader. Его существующие checks требуют `projectRelativePath`, затем читают bytes и сверяют hash. Готового typed legacy selection/decoder output здесь нет. Недостаточно приписать ему весь native-origin union, не выразив реальную допустимость legacy fallback.
- `runtime-descriptor.ts:24` / `native-checks.ts:125–154`: новый native descriptor input — полный `NativeProjectCheckSchema` output. Действующие legacy exporter fixtures принимают более короткую историческую check-форму: например `tests/export-course.test.ts:258–279` не имеет `'source-profile'`/`tests`, но runtime reader потребляет mode/root/include, compiler settings/scoring и policy fields. Предыдущая партия добавляла полный parse только для отдельного прямого native descriptor вызова, не меняя весь accepted legacy manifest. Принудительно типизировать все `checks.projects` как native projection изменило бы этот статический контракт и потребовало бы дальнейших правок callers/fixtures.

Следующая осмысленная партия должна отдельно установить настоящий legacy check/selection consumer view по существующим guards и accepted fields, сохранить тип project output в manifest producer, затем выразить mixed-loop корреляцию без стирания bytes/origin. Это затрагивает как минимум legacy reader, manifest producer, exporter и разграничение descriptor input contract; не является только оставшимся маленьким callback annotation. Новые runtime schemas/guards и восстановление retired authority для этого не предлагаются.

## Допуск и действия

Собственный агент `/root/pl_typed_materialization`, chat `01a1a25f-b098-7242-bf6d-d0e8f6517361`, parent `01a1a142-7adc-7371-9765-4871d807dfcd`. Primary `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-05-42-01a1a25f-b098-7242-bf6d-d0e8f6517361.jsonl`, собственный новый интервал `turn_context` строка758, turn `01a1a2a6-8b8f-7743-bba8-7a0c177bf5c0`: top-level и вложенные settings подтверждают `gpt-6.1-sol/high`, расхождений нет. Standard prescribed; серверный tier первичная запись не удостоверяет. Глобальные/normative settings и root state не менялись.

Выполнено только адресное чтение перечисленных Source-файлов/текущего review и проверка исходного HEAD/status. Compiler pass, negative-case, runtime suites, прежний course-export graph, render/inspect/hash/build/install/CI/push не запускались. Production и tests не менялись, Core/Java/installed/courses не затрагивались. Единственная запись этой read-only оценки — данный отчёт вне PL Source repo.

## Следующий bounded package — расширение 4 ноября

По отдельному разрешению root потолок расширен до шести production-файлов. Выбран пакет: type-only `legacy-checks.ts` с коротким accepted consumer-view; existing path/hash boundary `source-selection.readSelected`; прежние `runtime-descriptor`/`java-build` guards без forced native schema; generic project producer `native-check-inventory.nativeChecksManifest`; mixed-loop consumer `export-course`. DTO удерживается после настоящего Core ArtifactFile parse для native origin и через существующий physical reader guard для legacy; новых runtime validators не требуется. Scope и результат отдельной реализации/проверки описаны в `pl-typed-legacy-boundary.md`. Исходная оценка ≤3files выше остаётся исторически верной; общая очередь/root state здесь не редактируется.
