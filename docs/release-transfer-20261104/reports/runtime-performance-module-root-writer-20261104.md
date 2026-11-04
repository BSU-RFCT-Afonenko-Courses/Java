# Performance: writer, прямой установленный module root

Статус: **candidate frozen для независимого Source review; исполнение не проводилось**.

Checkout: `/home/tolya/JavaCodex/java-release-20261110/performance-lifecycle`.
Базовый HEAD: `9ba1fd87eb8f5da15fdad29c6c74bc0030b96e92`.
Применимых `AGENTS.md` в цепочке родителей и внутри этого checkout не обнаружено.

## Изменение

Единственный изменённый Source: `src/launchers/setup.ts`, 9 добавленных и 16 удалённых строк.
Из `prepareLaunchers` удалены `backend`, копирование `src`, `bridge`, `node_modules`, `deno.json`, `deno.lock`, `docs/operation-registry.json` и импорт `copyTree`.
`project = assetPath(".")` теперь служит корнем собственных module paths и `appDenoArgs(project)`.
Существующие executable shell launchers продолжают создаваться в workspace.
`preparePandocData`, generated Pandoc init/bootstrap и материальная изолированная копия изменяемого data-dir не менялись.

Тестовый файл не менялся: прежний реальный summary failure при обходе directory symlink штатного Deno install уже является RED. Новые mirror implementation tests не добавлялись.

## Точная closure загрузки

1. `prepareDiagnostic` и `prepareRenderDiagnostic` в `src/runner/diagnostic.ts` вызывают `prepareLaunchers`.
2. Он создаёт только shell entrypoints `workspace/deno` и `workspace/pandoc` и подготавливает `workspace/pandoc-data`. Вызов Pandoc builtin init сохраняется.
3. Оба shell entrypoints вызывают физический выбранный Deno с `run --config <project>/deno.json --lock <project>/deno.lock --frozen --node-modules-dir=manual --cached-only -A <project>/src/launchers/tool.ts <kind> "$@"`. Каждый сгенерированный собственный аргумент проходит существующий `shellQuote`; входной argv передаётся через `"$@"`.
4. Runtime import closure `tool.ts -> pandoc-data.ts -> setup.ts -> snapshot.ts, assets.ts`; дополнительные runtime imports здесь — `node:path`, `node:url`. `type` imports в config/report не становятся runtime imports. Все относительные импорты остаются в установленном project; свой resolver не добавлен.
5. TS branch `tool.ts` передаёт выбранному native Deno прежний argv, добавляя `--preload <project>/src/agents/ts/bootstrap.ts`. Далее `bootstrap.ts -> agent.ts -> node:async_hooks, node:path`. Config/protocol imports агента являются `type` imports. Окружение и argv native курса не подменяются собственной конфигурацией launcher. Сам preload, как и прежде, зависит только от абсолютного пути и встроенных Node APIs.
6. Lua branch: `tool.ts -> preparePandocArgs -> preparePandocData` использует отдельный Pandoc data-dir. Его generated `init.lua` сначала выполняет отдельную копию оригинального init, затем `dofile(<project>/src/agents/lua/init.lua)`. Тот выполняет `dofile(COURSE_PERF_AGENT_LUA)` с `<project>/src/agents/lua/agent.lua`. Lua agent самодостаточен; нового require/module resolver нет. Повторное подключение сохраняет существующий registry guard.
7. Trace branch `prepareDiagnostic` преобразует `bridgePath = <project>/bridge/bridge.ts` через существующий `pathToFileURL(...).href`; `instrumentModule` вставляет этот URL в копии выбранных модулей курса. Bridge самодостаточен. Dynamic import `../instrumentation/ts/transform.ts` остаётся в установленном Source, а его `npm:typescript@5.9.3` разрешается штатно из уже установленного module tree; ничего не копируется и не материализуется дополнительно.
8. Operation registry уже читается через `assetPath("docs/operation-registry.json")` в `prepareDiagnostic`; удалённая backend-копия не была необходима для этого пути.

`assetPath` использует `fileURLToPath(new URL("../", import.meta.url))`, поэтому module root не зависит от чужого cwd и корректно декодирует URL. Shell quoting установленного root сохранён; trace bridge преобразуется в URL, Lua получает filesystem path через прежнее JSON-кодирование generated `dofile`.

## Границы и риски

- Установленный Performance module root должен оставаться доступным до завершения дочерних процессов. Workspace с generated launchers теперь не является самостоятельным переносимым пакетом Source/dependencies; это прямое следствие выбранного установленного module root.
- Штатная установка dependencies и frozen/cached-only config/lock остаются предпосылкой запуска. Directory symlinks из штатного Deno install больше не передаются generic copier.
- Installed Performance/Quarto tree и исходный Pandoc data-dir не получают новых записей. Собственная generated init и копия оригинального init остаются в workspace. Код `copyPandocData`, generic snapshot copier, lifecycle, TERM и security boundaries не менялись.
- Source inspection не является runtime attestation. Проверка путей установленного root со специальными символами и настоящий summary ещё ожидают независимый review и разрешённые bounded execution.

## Freeze и бюджет

На момент freeze `git status --short` показывает только `M src/launchers/setup.ts`.

Writer не запускал Deno check/test/render, formatter/compiler, network/install, VM или git commit. Только чтение Source, один Source patch и этот отчёт. Никакой execution budget не израсходован writer.
История failures и исходные budgets не переписывались: setup `0.716354009/20`, summary cumulative `23`; после независимого review у root остаётся один targeted affected graph с cap 8 s и только при PASS один synthetic summary с полным external cap не более `6.392 s`. First new failure — stop.

Кандидат заморожен; дальнейшие Source edits в этом writer turn не планируются.
