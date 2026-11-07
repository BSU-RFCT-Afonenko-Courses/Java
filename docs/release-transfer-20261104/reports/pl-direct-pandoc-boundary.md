# PL JSON→HTML: граница прямого Pandoc

4 ноября 2026. Только чтение Source и локальных сохранённых материалов. Исходный PL `30edc03295e508f174c64b399f35b14ad37488fa` clean/independent APPROVE по заданию координатора. Production Source, Core API, installed/courses и state не менялись. Новых compiler/test/render/capture/version/inspect/hash запусков, network, commit/push не было.

## Собственный первичный допуск

Собственный агент `/root/pl_typed_materialization`, chat `01a1a25f-b098-7242-bf6d-d0e8f6517361`, исходный turn `01a1a25f-b0fb-7fd2-b244-83933af77834`. Прочитан полный собственный исходный интервал `turn_context` из `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-05-42-01a1a25f-b098-7242-bf6d-d0e8f6517361.jsonl`: строки8/596/758/823/1037/1080/1181. Во всех top-level `model/effort` и вложенных `collaboration_mode.settings` согласованы `gpt-6.1-sol/high`. Текущий turn `01a1a4b4-245c-7fd0-a31f-3df4a055f73a`, исходный интервал не сдвинут. Standard назначен; скрытая серверная маршрутизация/tier первичным контекстом не удостоверяется. Globals не менялись.

Прочитаны canonical `java/docs/release-cycle-20261110.md`, `reports/performance-pandoc-layer-reading.md`, актуальные `AGENTS.md/spec/index.md` и связанные места Source. Навык `brainstorming` применён для выбора ограниченного подхода; реализации до конкретного GO нет.

## Вывод

**Готового qualified/frozen Pandoc binding в текущем PL/Core public API нет.** В пределах ≤3 PL production files можно было бы потребить такой binding, однако нельзя честно произвести его из имеющихся публичных данных без нового контракта выбора инструмента. Эта партия останавливается на отчёте. Самостоятельный resolver по расположению Quarto или ambient `QUARTO_PANDOC` не добавляется.

Предыдущий capture установил12 PL JSON→HTML parent/child pairs с одинаковыми forwarded argv/cwd. Это основание рассматривать устранение CLI слоя, но не доказательство полной эквивалентности окружения, guards или ошибок. Наблюдаемая разность envelope не называется clean startup/CPU/critical-path выигрышем; нового измерения здесь нет.21 Core conversions вне scope.

## Реальная текущая цепочка

- `application/export.ts:466` в `renderPublicBody` передаёт private `command` точные `['pandoc','--from=json','--to=html5','--mathml']` и `JSON.stringify({'pandoc-api-version':p.apiVersion,meta:{},blocks})`. После stdout выполняются прежние замены brace/resource markers. Ни meta, ни HTML/resource обработку менять не требуется.
- `export.ts:70–140` выбирает logical tool из `auxiliary.environment.QUARTO` либо ordinary `Deno.env.get('QUARTO')`, затем fallback `quarto`. Native before получает весь frozen env, child использует `clearEnv:true`. Cwd в options **не задан**: наследуется фактический cwd при spawn, это не отдельный frozen cwd binding. Stdin/stdout/stderr piped; stdout декодируется прежним TextDecoder. Nonzero exit, stdout/stderr, cause и tool сохраняются в `ExternalToolFailure`. Post fence вызывается после полученного child output и до проверки success/writingFailure. Spawn/output exceptions имеют собственный существующий порядок; обобщённый finally вместо этого не эквивалентен.
- `native-auxiliary.ts:4–43` содержит только `environment`, `before(overlays?)`, `after()`. `captureNativeAuxiliary` замораживает env, сравнивает current ambient env и вызывает существующий live assertion. Pandoc executable/version/effective-child-env здесь не возвращаются и не квалифицируются.
- `native-course.ts:252–258` сначала вызывает `operation.assertCurrent()`, затем создаёт auxiliary с live fence. `native-entry.ts:289` прямо сохраняет executable/cwd/env/token у qualified Core lease для capture. Это не разрешение PL выбирать отдельный binary.
- Прочитанный Core `public.ts` не экспортирует selected Pandoc binding. Внутренний `infrastructure/process.ts:8` возвращает assignment Quarto либо ambient QUARTO/fallback; `invocation.ts` связывает identity именно Quarto invocation, а не выбранный Pandoc. Внутренний `assignment-execution.ts` whitelist/authority остаётся владельцем Core; его обход не предлагается.

При этом Core owner **уже знает выбранный Quarto runtime**. `runtime/assignment-admission.ts:175–205` получает существующую `nativeRuntimeIdentity`, берёт physical executable из invocation и создаёт единую environment map с fixed QUARTO/profiles/no-network. `AssignmentAdmission.invocation` сохраняет identity, а `AssignmentRuntimeWitness` — executable/environment/profiles; результат кладётся в admission при preparation (`:239–260`). `assignment-execution.ts:198–209` использует именно сохранённую runtime.environment для qualified children. Это исходная frozen runtime опора следующего анализа, а не причина квалифицировать Quarto повторно в PL. Недостаёт только выбранного дочернего Pandoc и эффективной passthrough composition, выведенных владельцем из **того же** runtime один раз на operation.

## Почему локальный Quarto не даёт готовый API

Для выяснения семантики прочитаны существующие файлы `/opt/quarto/bin/quarto` и `/opt/quarto/bin/quarto.js`; эти абсолютные пути — адреса прочитанной установленной копии, **не proposed product binding**.

`quarto.js:26669–26705` private `toolsPath/pandocBinaryPath` выбирают override как файл либо directory+binary, при отсутствующем override предупреждают и используют bundled architecture path. Учитываются OS/architecture и Quarto config. Один `QUARTO_PANDOC` этого поведения не описывает. Frozen `QUARTO_BIN_PATH` родительского TS process также не доказывает выбранный child binary: frozen QUARTO может указывать на другой launcher, который сам выставляет bin path.

Shell launcher перед Deno заново устанавливает ряд переменных, включая `QUARTO_ROOT`, `QUARTO_BIN_PATH`, `QUARTO_SHARE_PATH` при отсутствии, `DENO_DOM_PLUGIN`, `QUARTO_DENO` при отсутствии, `DENO_TLS_CA_STORE`, `DENO_NO_UPDATE_CHECK`; dev mode имеет дополнительные ветви. Поэтому env на входе PL→Quarto и effective env Quarto→Pandoc нельзя автоматически считать одной картой.

`quarto.js:190301–190355` перед passthrough вызывает `checkReconfiguration()` и `checkVersionRequirement()`; затем `passThroughPandoc` выбирает private binary, удаляет только первый `pandoc` argv и передаёт env2. В этом пути нет добавленного Pandoc data-dir. Отказ version requirement/debug reconfiguration, warning fallback и начальная CLI logging/profile обработка могут быть видимы через stderr/exit. Удалить их молча при прямом spawn нельзя.

Единственный export bundle в `quarto.js:190423` — `quarto4 as quarto`. `pandocBinaryPath` не экспортирован. Импортировать весь bundle и вызвать `quarto(['pandoc',...])` — не direct Pandoc API: это та же CLI branch, включающая `Deno.exit`; qualifier не появляется. Bundled run import map содержит stdlib, не resolver/runtime service для выбранного Pandoc.

## Варианты и минимальный недостающий договор

1. **Сохранить текущую command composition** до появления qualified binding. Сейчас единственный вариант, удовлетворяющий заданным ограничениям без изменения договора.
2. **Потребить binding от существующего runtime/tool owner**, если владелец отдельно утвердит его producer. Возможный PL consumer scope: `native-auxiliary.ts` (внутренний доступ к уже квалифицированной зависимости), `native-course.ts` (передача из composition), `export.ts` (только JSON→HTML direct launch). Это максимум3 PL production files, без Core21. Это предложение границы, не утверждённый новый API и не разрешение добавить implementation сейчас.

Минимальный смысл binding: выбранный фактический executable и относящийся к той же Quarto composition effective env/наследование cwd; учтённые override+fallback и применимость Quarto version/reconfiguration checks; сохранение видимых warning/отказов и logical diagnostic tool. Binding должен происходить из владельца выбора инструмента и жить в том же existing live/frozen lifecycle. Следующий Core owner анализ должен переиспользовать уже имеющуюся identity Quarto runtime и frozen environment, выполнить **одну selection на operation** и убрать повторную CLI/preflight работу12×. Новая серия version/hash qualification или12 дополнительных probes не предлагаются. Конкретную форму record/callback здесь не назначаю: одного произвольного path недостаточно, а новый registry/resolver/qualification framework противоречит scope. Новых runtime guards/хешей для его имитации не предлагается.

Если владелец согласует binding, минимальная предметная приёмка — одна bounded проверка настоящего JSON→HTML body с прежними bytes/argv/stdin, эффективным env/cwd и существующими auxiliary fences плюс прежним внешним отказом nonzero/stderr/tool; точные cases выбирать по конкретному diff, не полную матрицу и не повтор course capture. Сейчас такая проверка не назначена и не выполнялась.

Координатор выбрал **report-only** для этой партии и отдельно распределяет Core owner анализ. Это технический producer gap внутри уже одобренного пользователем bounded performance scope; новой пользовательской author-boundary схемы/разрешения не требуется. До конкретного implementation GO production не меняется; одного GO на consumer недостаточно без решения описанного producer gap.
