# Независимый review import map для performance-context

3 ноября 2026. Проверен exact Core диапазон `ea197ad7e88a3410c4eea52f293d975aea63db4c..ee3aa0897fefadff2543528e45032231b90c13c4`. Изменён только `tests/performance-context.ts`: +157/−44, в том числе форматирование прежнего compact файла. Production, workflow, API и suite scope не менялись.

**Вердикт: APPROVE. Существенных findings нет.** Test setup передаёт существующий parent dependency alias в raw Deno child и сохраняет ограничения прав и функциональные assertions. Новый hosted CI green или весь canonical PASS этим выводом не утверждаются.

## Причина и исправление

Сохранённый hosted log `reports/core-ci-ea-job-126119794613.log:3369–3373` подтверждает canonical013/performance-context failure: ordinary parent/context часть вывела PASS, затем raw child с `--deny-env` отказал на `Import "stdlib/path" not a dependency` из `runtime/assignment-execution.ts:4`. Это module resolution до functional shell assertions. Прочитанный exact test до/после согласуется с причиной: child импортирует `infrastructure/process.ts` с транзитивным assignment execution, но запускался через `Deno.execPath() run --no-config` без Quarto import map.

В target `tests/performance-context.ts:96–108` temporary map содержит только `stdlib/path`. Значение берётся из actual parent `import.meta.resolve("stdlib/path")`; dependency version/URL не угадывается, дерево исходников не копируется. Нормализация `jsr:/`→`jsr:` соответствует уже используемому spelling в existing native map helper. На `:130–139` к прежнему child argv добавлены лишь `--import-map` и путь к этой карте. Alias относится к тому же dependency, который уже загрузил parent при импорте product process module; новой сетевой библиотеки или произвольного endpoint diff не вводит.

## Сохранённые ограничения и проверки

`tests/performance-context.ts:123–149` сохраняет все три режима: summary с `--deny-env`, off с единственным `--allow-env=COURSE_PERF_MODE`, summary с тем же narrow allow-env. `--no-config`, `--no-lock`, `--no-prompt` и `--allow-run=/bin/sh` прежние. Новые allow-env/read/net/all, fallback с более широкими правами или дополнительные executable permissions отсутствуют. Передача import map — настройка module resolution; право перечислять окружение она не добавляет. Абсолютная гарантия отсутствия любых loader network обращений в произвольной пустой среде этим read-only review не заявляется; addressed execution использует уже существующие parent dependencies/cache.

Restricted parent entry (`:113–121`) по-прежнему вызывает настоящий product `command('/bin/sh', ...)`, передаёт explicit override и проверяет неизменность caller env. Все три child обязаны завершиться успешно и вернуть exact `inherited|explicit|student\n` (`:150–157`). Результат не подменяется fixture или mock process.

Ordinary часть сохраняет actual parent PID, active parent span, удаление stale inherited span, functional env/profile и неизменность переданного env (`:35–50`); extension context command (`:51–56`) и двух document IDs (`:60–78`) также проверяются. Optional chaining у поиска spec теперь приводит к тому же failing assertion при отсутствии extension id, поэтому coverage не ослаблен. Ни одна функциональная проверка не заменена простым smoke import.

Existing Performance ABI observer на `:11–31` получил конкретные `Metrics`/`OperationSpec`; generic identity wrapper возвращает ту же функцию и записывает actual spec. Его соответствие `wrapSync`/`wrapAsync`/`wrapWalk` следует current interface `observability/bridge.ts:16–20`. Reflect registry access убирает прежний двойной cast; новых explicit `any`/assertions нет. Это прежний private observer, который не создаёт producer admission/receipt и не подменяет child result. Cleanup env/temporary directories сохранён.

## Доказательства и пределы

Прочитаны `reports/core-ci-ea-failure.md`, exact diff, current test/interface и сохранённые execution/typecheck JSON/logs. Addressed runtime автора имеет exit 0 за 0.744 s; one-file typecheck — exit 0 за 3.454 s с existing Quarto cache/import map. Это прочитанные результаты автора, а не новые запуски reviewer. Старые четыре addressed tests не повторялись и по этому review повторного PASS им не приписывается.

Заявленный в авторском отчёте read-only поиск других raw callers ограничен просмотренной active областью; он не доказывает общий suite PASS и так здесь не трактуется. Сам reviewer tests/check/compile/network/native/render/inspect/hash/install не запускал. Source/state не менялись; единственная запись — этот отчёт. Установка, push и новый hosted CI относятся к следующему действию root; Java owner на frozen installed d7 этим test-only diff не затронут.

## Собственный первичный допуск

Исполнитель `/root/review_core_owner_types`; собственный ID `01a1a28b-583e-7d71-ab50-0f123b8de5a4`, ранее подтверждённый `session_meta.agent_path` собственного rollout `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-53-23-01a1a28b-583e-7d71-ab50-0f123b8de5a4.jsonl`.

Полный reviewer interval начинается исходным turn `01a1a28b-586d-7d31-94ce-e366ffbd375f`, строка 8. Проверены все четыре записи `turn_context` на строках 8, 142, 226, 299; последняя — нынешний turn `01a1a2b1-944b-7260-81ec-9cccf00d7f8d`. Во всех top `model=gpt-6.1-sol`, `effort=high` и те же nested `collaboration_mode.settings.model/reasoning_effort`. Baseline не сдвигался; нынешний узкий review начинается в четвёртом ходе. Standard назначен root, `service_tier` отсутствует и скрытый серверный tier не утверждается. Глобальные settings и нормативные документы не менялись.
