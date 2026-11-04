# Performance: удаление аттестации backend при подготовке

## Область, допуск и исходники

Исполнитель — собственный chat `01a1a51b-018c-7962-bb54-e47212c4fe94`, agent `/root/performance_backend_copy`. Первичный источник: `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-49-32-01a1a51b-018c-7962-bb54-e47212c4fe94.jsonl`, `session_meta` строка 1. Исходный turn роли `01a1a51b-01b7-7872-9705-872e66ab8fcb`; весь интервал от него содержит один `turn_context`, строка 8: верхние `model=gpt-6.1-sol`, `effort=medium` и вложенные `collaboration_mode.settings.model=gpt-6.1-sol`, `reasoning_effort=medium` согласованы. Входной и итоговый допуск PASS. Standard назначен; отдельного первичного подтверждения service tier нет, настройки не менялись.

Workspace `/home/tolya/JavaCodex/java-release-20261110/performance`; фактический HEAD перед работой `94f32fb73778a86f20b7bb970e706cb5b4f0a775`. Прочитаны `../java/docs/model-admission.md`, `../core/docs/russian-language-policy.md` и `pattern-pass-performance-ci-20261104.md`. На пути workspace и внутри Performance применимых `AGENTS.md` не обнаружено. Другие checkout не редактировались.

Принято уточнение root: сохраняются прежние файлы, evidence и данные студентов, но удаляемая собственная guarantee не обязана навсегда оставаться в новых telemetry outputs. Поэтому отсутствие backend receipt в новых запусках допустимо; старые `run.json` не переписывались.

## Что в действительности гарантировал двойной hash

Точные исходные участки на HEAD94f32:

| Source | Реальное назначение |
| --- | --- |
| `src/launchers/setup.ts:29–44` | `backendHash(project)` → копирование `src`, `bridge`, `node_modules`, трёх отдельных файлов → `backendHash(backend)` → сравнение. Каждый hash читает content/mode/relative names файлов выбранных деревьев; это наша аттестация копии, не требование Pandoc/Deno. |
| `src/launchers/setup.ts:75–80` | Пара сохранена в отдельном backend `PreparationReceipt`, `kind=bootstrap`. |
| `src/sources/snapshot.ts:11–67` | `files` обходил и сортировал дерево, отвергал специальные файлы, по выбранной опции материализовал только внутренние ссылки на файлы; `treeHash` дополнительно читал каждый файл; `copyTree` сохраняет mode и mtime. |
| `src/runner/diagnostic.ts:25–31,131–139` | Оба диагностических пути передают `setup.preparation` дальше; содержимое backend hashes не анализируют. |
| `src/runner/render.ts:152–173`; `src/runner/execute.ts:201`; `src/cli/experiment.ts:232` | Формируют/передают массив preparation для сохранения. Нет сравнения backend hashes или решения о readiness по ним. |
| `src/report/types.ts:211–220`; `src/config/types.ts:91–97` | Типизированный массив receipt допускает отсутствие backend entry; каждый существующий receipt сохраняет обязательные строки. Валидатор не требует конкретного числа записей и не проверяет их равенство. |
| `tests/report/run.test.ts:22–36`; `tests/runner/storage.test.ts:339–374`; `docs/general-use-formats.md:76–81` | Сохранение provenance и описание полей; нет отдельного потребителя backend equality. |

Полезная узкая семантика существовала: отказ при расхождении выбранных file bytes/modes исходника до копирования и готовой копии после него. Это может выявить concurrent editing или остатки ранее существовавшего backend у прямого вызывающего exported `prepareLaunchers`. Проверка не была блокировкой, атомарным снимком дерева, защитой от последующих изменений или доказательством происхождения установленного инструмента. Изменения после соответствующего чтения и последующий write не исключались. Полного consumer, которому для runtime или functional result нужна именно эта пара, в текущих Source не найдено.

Для актуальных production callers destination уже имеет другого владельца: обычный render получает новый внешний staging через `storage.ts:98`, затем `render.ts:106` выбирает его `diagnostic`; эксперимент получает `Deno.makeTempDir` в `cli/experiment.ts:225`, а `diagnostic.ts:26` выбирает `launchers`. Они не делят общий backend между runs. Exported helper по-прежнему предполагает корректно выбранный workspace; новыми preflight/validator/receipt/cache это изменение не обрастает.

## Граница данных, установки и запуска

Backend принадлежит Performance, а не дереву курса. `assetPath` разрешает source относительно модуля, независимо от cwd (`src/assets.ts:5–6`). Установочный состав уже явно задан в `packaging/arch/PKGBUILD:23–25,37–39`: локальные npm dependencies, `src`, `bridge`, `deno.json`, `deno.lock`, `docs/operation-registry.json`. Этот packaging не запускался и его готовность данным чтением не подтверждается.

Candidate сохраняет в `src/launchers/setup.ts:29–36` тот же фактический состав копии. Никакого сокращения до предположительно достаточного import graph нет. `src/assets.ts:10–21` по-прежнему задаёт copied config/lock, frozen/manual/cached-only для собственных launchers. В candidate `setup.ts:48–50` запускает именно `backend/src/launchers/tool.ts`; `setup.ts:62–66` выбирает Lua init/agent, TS bootstrap и bridge из этой же копии. Выбранные real Deno/Pandoc и argv shell quoting сохранены. Data-dir preparation и его реальный source/prepared receipt сохранены.

`copyTree` не менялся: внутренние file links допускаются только после `contained`, external links/directory links/special files отвергаются; modes/mtime остаются. Важно: прежний `treeHash(project)` сам отвергал любые symlinks, поэтому после его удаления собственный уже существовавший контракт copy materialization для внутренних file links больше не перекрывается ранней аттестацией. Безопасность от concurrent symlink replacement старая последовательность hash/copy также не доказывала; новая такая гарантия не заявляется. Проверки физического пересечения Pandoc data-dir/destination в `copyPandocData` не затронуты. Для прямого caller с reuse destination больше нет backend equality refusal — это часть удалённой guarantee; production fresh staging остаётся.

## Изменение

Изменён один production файл `src/launchers/setup.ts`: удалены обе full backend hash операции, equality gate, импорт `treeHash` и backend receipt. `preparation` возвращает существующий Pandoc receipt. `hashBytes`/`hashFile` остаются для реальной трансформации Pandoc init. Нового DTO, fake hashes, `any`, cast, validators, registry или cache нет. Документ `docs/general-use-formats.md` явно сообщает об отсутствии аттестации backend в новых запусках.

Новая подготовка **не аттестует побайтную идентичность backend исходному дереву**. Ownership, состав копии и runtime paths сохранены по Source. Это сознательное удаление нашего контроля, а не перенос его на Native Quarto. При concurrent editing Performance во время подготовки корректность единого снимка больше не заявляется. Старые сохранённые свидетельства не меняются.

## Фактическая проверка и ограничения

Общий установленный cap проверок 45 s, без общей матрицы. Фактические команды завершились значительно раньше; длительности ниже — elapsed tool command, не время backend и не benchmark.

1. Первая команда `deno check --cached-only --frozen --node-modules-dir=manual src/launchers/setup.ts` отказала за 0.047 s: в выбранном manual `node_modules` нет `npm:@types/node`. Следующие команды в том shell из-за `set -e` не исполнялись. Это отказ подготовки режима проверки, не установленный дефект Source.
2. Root передал уже установленную причину этого режима и разрешил ровно один исправленный compiler invocation. `timeout 35s env -u DENO_DIR /usr/bin/deno check --cached-only src/launchers/setup.ts` с обычным обнаружением config завершился exit 0 за 0.622 s. Сеть и установка не использовались.
3. Два независимых existing pure cases выполнены один раз с `--no-check --cached-only --frozen --node-modules-dir=manual`: `runtime copy materializes internal file links and rejects external targets` в `tests/sources/packages.test.ts` — 1 passed, 0 failed, 7 ms; `run metadata validates provenance and preparation without changing native arguments` в `tests/report/formats.test.ts` — 1 passed, 0 failed, 2 ms. Они проверяют сохранённую copy/link границу и допустимые receipt metadata; полноценный setup/native execution ими не подтверждается.
4. `deno fmt --check src/launchers/setup.ts` — PASS, 1 file. `git diff --check` — PASS. После документационного исправления регистра повторно просмотрен итоговый diff.

Native render, capture, полный tree hash, install, build, inspect, сеть, commit и push не выполнялись. Новые tests не добавлялись; принятые R0–R6 и captures не воспроизводились. Старое SP preparation 0.268420 s включает этот путь целиком и не измеряет отдельно два backend hash. Выигрыш времени, CPU, bytes, RSS и влияние на Java128/900 остаются UNKNOWN. Full setup/installed offline readiness на изменённом Source остаётся отдельной фактической проверкой релиза, этим bounded Source/pure pass не заявляется.
