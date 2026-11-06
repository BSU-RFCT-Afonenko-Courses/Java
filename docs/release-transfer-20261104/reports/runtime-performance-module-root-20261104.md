# Performance: установленный module root вместо копии backend

Срез19:30 Europe/Minsk. Продолжение прямо согласованного удаления собственного копирования/гарантий вокруг штатного Deno/Node package. Изменён только `performance-lifecycle/src/launchers/setup.ts` (9 добавлений/16 удалений) относительно базы9ba1fd87eb8f5da15fdad29c6c74bc0030b96e92. Checkout уже изолирован, зависимости ранее установлены обычным Deno, Source других продуктов не менялся.

**Итог19:34: Source независимо Scoped APPROVE; после явного снятия compilerPASS gate один synthetic public summary прошёл — fullwall2.706с/cap6, complete/dataValid=true, HTML сохранён. Compiler остаётся blocked/UNKNOWN npm:@types/node. Внутренние фазы курса не измерены.** [Итог, охват и все расходы](runtime-performance-module-root-result-20261104.md). Ниже сохранена предшествующая история без переименования failures.

## Что изменилось

Ранее `prepareLaunchers` копировал src, bridge, node_modules, config/lock и registry в workspace/backend. Штатные directory symlinks Deno install попадали в generic file copier и отклонялись. Удалена вся эта копия; launcher использует существующий настоящий `assetPath(".")` Performance:

- `appDenoArgs(project)` передаёт абсолютные config/lock установленного приложения.
- Generated shell launchers запускают его `src/launchers/tool.ts`.
- TS preload, Lua init/agent и bridgePath читаются из того же установленного module root.
- Deno штатно разрешает собственные imports/node_modules. Custom resolver, копирования всего node_modules, symlink registry/proof или нового validator нет.
- Generated launchers и изменяемая копия Pandoc data-dir/init остаются в workspace; исходный Pandoc init, установленный Quarto/Performance tree и функциональные параметры курса не переписываются.

Точная closure: prepareRenderDiagnostic/prepareDiagnostic → prepareLaunchers → generated shell → installed tool.ts → TS preload либо preparePandocArgs/Pandoc init → installed Lua agents. Trace использует installed bridge через прежний pathToFileURL. Существующие assetPath/shellQuote сохранены. Generic snapshot copier, hash routines, lifecycle/TERM/security и другие Source не менялись.

Writer `/root/performance_module_root_writer` — единственный Source writer; до freeze/review запусков0. [Writer report](runtime-performance-module-root-writer-20261104.md), [план](runtime-performance-module-root-plan-20261104.md). Ранее реально наблюдённый directorylink failure служит RED; новые собственные content pins не используются.

## Независимая рецензия до исполнения

`/root/ordinary_gradle_publication_review`: **Scoped APPROVE**, конкретных findings нет. Проверены absolute module root/свои config-lock при чужом cwd, прежнее shell quoting, TS/Lua paths, trace URL с пробелами/#/%, isolated mutable Pandoc data, отсутствие записей в installed tree и custom resolver.

Предел: installed root должен оставаться доступным до завершения child processes; generated workspace больше не самодостаточная копия приложения. Это Source approval одного изменения, не whole Performance/runtime qualification. [Review](runtime-performance-module-root-review-20261104.md).

## Один affected graph и остановка

До вызова сохранён [execution plan](runtime-performance-module-root-20261104-evidence/execution-plan.md): один targeted graph cap8с после review, только при PASS — один synthetic summary в остатке общегоcap23с. Фактически выполнена команда:

```sh
deno check --config deno.json --lock deno.lock --frozen \
  --node-modules-dir=manual --cached-only \
  src/launchers/setup.ts src/launchers/tool.ts \
  src/launchers/pandoc-data.ts src/runner/diagnostic.ts
```

**Координатор ошибочно добавил `--cached-only`, которого данный `deno check` не поддерживает.** Результат **exit1, полный wall0.006с**, до запуска compiler graph:

```text
error: unexpected argument '--cached-only' found
```

Это отказ проверочной команды, не установленный дефект изменённого Source. [stderr](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-module-root-20261104-evidence/graph.stderr), [полное время](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-module-root-20261104-evidence/graph-fullwall.txt), graph-exit/stdout сохранены. По прямому first-newfailure-stop нет повторного вызова/исправленного prefix/других tests. Одобренный Source остаётся непроверенным compiler/runtime candidate, без commit/push.

Summary после этого fix **NOT RUN**, поскольку graph PASS не получен. Остаток не использован. Старый первый summary fullwall≤15с и второй полностью timed1.607с (консервативно1.608с с округлением) сохранены; теоретический остаток≤6.392с из общего23с не даёт разрешения обойти требование graphPASS/stop. Setup0.716354009с/cap20с не сброшен и dependencies не переустанавливались. Graph0.006с/cap8с — отдельно назначенная qualification. Исторические failed TERM и прежние direct/off/grader бюджеты не повторялись.

## Что готово и что осталось

Убрана конкретная ненужная runtime копия module/dependency tree. Прямое использование установленного module root укладывается в маленький diff и Source review, большого rewrite не потребовалось. Реальная загрузка модулей после изменения ещё не квалифицирована: сначала нужен допустимый исправленный compiler invocation в отдельном явно разрешённом продолжении, затем только согласованный bounded summary. Нового package resolver или манипуляции dependency links для этого не нужно.

### Единственная явно разрешённая коррекция compiler CLI

После сохранения исходного0.006с failure прямо разрешён один corrected invocation в **остатке того же graphcap8с**, без Source/root changes. Help фактического `/opt/quarto/bin/tools/x86_64/deno check` подтверждает `--deny-import` (deny importing from remote hosts). Из прежней команды удалён только неподдерживаемый `--cached-only`, добавлен `--deny-import`; config/lock/frozen/manual и четыре input root прежние. До вызова сохранён `graph-correction-plan.md`; внешний timeout7.9с в разрешённом остатке≤7.99с, fullwall timed.

Исправленная команда начала Check четырёх roots, но вернула **exit1, полный wall0.339с**, из-за type dependency:

```text
Could not find a matching package for 'npm:@types/node' in the node_modules directory.
```

Это уже не ошибка parsing CLI и не Source PASS: compiler не смог завершить graph без Node declarations. Новые dependencies не устанавливались и не загружались, lock/config/Source не менялись; synthetic declarations, casts или отключение проверки не вводились. По прямому условию «при новой зависимости/новом failure — стоп» дальнейших попыток нет. [Result и сумма бюджета](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-module-root-20261104-evidence/graph-corrected-result.json), stderr/fullwall/exit сохранены.

Оба graph intervals вместе **0.345с из того же cap8с** (округлены до milliseconds), новый бюджет не создавался. Summary после fix **NOT RUN**: graph PASS нет. Теоретический остаток summary≤6.392с/cumulative23 и setup0.716354009/20 сохранены; остатки не считаются разрешением на повтор. Актуальный технический остаток — штатная доступность `@types/node` для typecheck этого installed dependency layout и новая явно разрешённая проверка; в текущем поручении новая зависимость запрещена. Установленный runtime не переделывался, других тестов/install/network/VM/TERM нет.

Наблюдённые фазы Core/PL/QRC, успешный instrumented render и overhead успешного запуска не получены; speedup/full-course bottlenecks не объявляются. Все прежние failures сохранены. Другие репозитории, VM/production/live DB/config не менялись, network/install/TERM/new project не запускались. Вечерний отчёт и session state обновлены; сообщений другим чатам нет.
