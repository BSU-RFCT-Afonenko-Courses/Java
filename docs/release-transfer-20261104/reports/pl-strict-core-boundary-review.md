# Независимый review строгой границы PL ↔ Core

3 ноября 2026. Проверяется только delta `3a6c1c14254f1ca547c3bc2577d80007c83bdcf0..f9c00aed6de610dce7d9b7cb6e50c28ebfb9573e` PrairieLearn и необходимые точные Core contracts (`f10c4e6527b1834651d59a95a0ef4373357cd8f0`).

## Собственный первичный допуск

Назначение `gpt-6.1-sol` / `high`, agent `/root/review_pl_strict_boundary`, собственный chat `01a1a258-3f6a-7102-8f36-72017d92ce01`, parent `01a1a142-7adc-7371-9765-4871d807dfcd`, исходный turn `01a1a258-3f95-7962-b714-603ef368b6b9`. Источник: `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T19-57-34-01a1a258-3f6a-7102-8f36-72017d92ce01.jsonl`: `session_meta` строка1 удостоверяет собственные ID/path; весь текущий интервал `turn_context` от исходного turn (строка8) содержит верхнеуровневые `model=gpt-6.1-sol`, `effort=high` и согласованные вложенные `collaboration_mode.settings.model/reasoning_effort`. Несовпадений нет. Standard предписан; серверный service tier первичная запись не удостоверяет. Глобальные настройки и нормативные документы не изменяются.

## Findings и verdict

**Замечаний P0–P3 в заданной delta нет. Verdict: принять ограниченную партию строгой типизации Core boundary.** Это не приёмка всей очереди ownTS и не свидетельство готовности выпуска.

## Основания

- `prairielearn/_extensions/course-prairielearn/application/native-adapter.ts:24`: `PublicCore` — настоящий `Pick<typeof CorePublic, ...>`, без собственной структурной копии методов или стирающей `parse(): any` сигнатуры. `NativePreparedRequest` ссылается на реальные identity и prepared inputs; pending callback, expectation и lease получают существующие Core types (`:32`, `:133`). В точных Core contracts identity/prepared/inventory/adapter-input схемы сохраняют Zod outputs; metadata values намеренно имеют `unknown`, а не `any`.
- `prairielearn/_extensions/course-prairielearn/application/native-entry.ts:211`: capture function получает реальную сигнатуру `capture.ts`, поэтому preparation, scope, lease и capture command проверяются контекстно. Descriptor и complete operation имеют настоящие Core types (`:219`, `:253`, `:309`). Аннотации динамического импорта описывают выбранный runtime module; они не доказывают его identity. Runtime selection, публичные Core gates и прежние parse/currentness проверки остаются источником такого подтверждения. Новых casts, suppressions или runtime validation слоёв delta не добавляет.
- `prairielearn/_extensions/course-prairielearn/application/native-input.ts:14`: parse output больше не стирается facade. `raw: Record<string, unknown>` сохраняет undecoded metadata как `unknown`; замена `includes(key as any)` на сравнение slots эквивалентна для прежнего закрытого набора строк.
- `prairielearn/tests/native-core-typecheck.ts:9`: resolver поддерживает Source root, оба existing installed layouts и непосредственно extension directory. Namespace map (`:32`) указывает на фактически переданный Core, product imports остаются type-only без абсолютного Source path. `--no-config` и явные stdlib mappings из Quarto согласованы с адресным запуском. Checker проверяет настоящий product graph, exact-output assertions с отказом верхнеуровневого `any` и четыре самостоятельных negative cases. Он не устанавливает расширения и не меняет глобальную конфигурацию.
- `prairielearn/_extensions/course-prairielearn/application/native-entry.ts:248`: сохранены порядок preparation → capture → assert → закрытие scope → current-operation fence → publish, исполнение child через Core lease и сохранение publication handle внутри callback. Локальные aliases descriptor/selection не меняют wire, authority или lifetime. Generic helper (`:342`) сохраняет прежний publish/discard порядок.

## Доказательства и пределы

Выполнен независимый read-only просмотр diff пяти файлов и адресных Core contracts; Source, ветки и нормативные документы не менялись. Тесты, render, check, build, hash, runtime, install и fmt повторно **не запускались**. Успешные Source/Java-tasks checks, negative diagnostics и inert-load erasure приняты как ранее сохранённые авторские доказательства из `pl-strict-core-boundary.md`, а не объявлены собственными запусками reviewer.

Проверяется PL Source против Core, указанного аргументом checker; для иной installed composition нужно передать её фактический Core. Совпадение произвольного runtime module с type-only mapping само по себе не гарантируется TypeScript. Прежние `any` candidate JSON/owner selection, внутреннего adapter state и `prepareNativeCourse` handoff остаются явно следующей партией: эта delta не выдаёт их за типизированную цепочку materialization/export/feedback.

Итоговый допуск относится ко всему собственному интервалу от указанного исходного turn; единственная запись `turn_context` этого интервала остаётся согласованной (`gpt-6.1-sol` / `high`).
