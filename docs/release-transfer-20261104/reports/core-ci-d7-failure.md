# Первый CI отказ Core d7: устаревшая unit обвязка execution API

3 ноября 2026. Draft [Core PR70](https://github.com/Afonenko-Course-Tools/quarto-course/pull/70), exact Source `d7cc10eaa21fa35bbe32d73cc4afa216002393da`; hosted run [41691770182/job126111898696](https://github.com/Afonenko-Course-Tools/quarto-course/actions/runs/41691770182/job/126111898696). Лог получен штатным GitHub `fetch_workflow_job_logs`, полный decoded ответ сохранён как `core-ci-d7-job-126111898696.log`. CI/local canonical/suites/render/inspect/actual course hash/build здесь не повторялись.

## Первая hosted ошибка

Hosted checkout и expected SHA совпали с d7; setup Quarto1.11.5/npm/exact fixture preparation успешны. В native workflow step canonical успел пройти `vocabulary`, `retired-tool`, `quarto-version-policy`. Первый отказ — **004 static-batch-checkpoint**, `2026-11-03T18:15:00Z`, exit1:

`TypeError: Function.prototype.apply was called on undefined, which is undefined and not a function`.

Stack: `AsyncLocalStorage.run` → `runtime/assignment-execution.ts:35` → `withAssignmentExecution:49` → `tests/static-batch-checkpoint.ts:51`. Upload evidence успешен, artifact [13256289132](https://github.com/Afonenko-Course-Tools/quarto-course/actions/runs/41691770182/artifacts/13256289132). Остальные canonical/early/native-model tests после этого отказа не выполнялись; их hosted PASS/FAIL не утверждается.

Причина подтверждается сигнатурами: unit test вызывал старый `withAssignmentExecution({native:{executable}} as any, use[,beforeChild])`. После accepted native runtime перехода93310279 функция принимает `(AssignmentRuntimeWitness, root, use[,beforeChild])`. Поэтому callback оказался в `root`, а `use` был undefined, и AsyncLocalStorage не смог его вызвать. Product guard ещё не начал исполняться. Это не refusal closing Source/runtime guard и не обнаруженная regression двухточечной perf дельты d7. Сравнение `a478..d7` показало, что четыре затронутых старых test файла не мигрировали вместе с API.

Причина и smallest action переданы root **до правки**: исправить callers в unit обвязке, сохранив lifecycle/physical/source/privacy/transport assertions; production API/guards/workflow не ослаблять. Root затем отдельно согласовал адресный поиск точно таких старых test callers и semantic cleanup obsolete protocol частей.

## Другие найденные callers — не hosted failures

Адресный `rg withAssignmentExecution` выявил три дополнительных active canonical файла. Они не были достигнуты hosted run и не выдаются за его отказы:

- `carrier-physical-guard`: тот же старый profile/callback positional call. Переведён на current `AssignmentRuntimeWitness`, explicit owner root и прежний beforeChild callback. Physical carrier extra-file/YAML/symlink и mutation-after-await-before-constructor отказы сохранены; fixture/Carrier/probe casts any убраны.
- `fixed-stdin-native`: тот же старый runtime wrapper. Используется actual current runtime/root и actual Quarto Pandoc stdin/citeproc. Старая проверка foreign source base внутри owner опиралась на retired exact-argv allowlist. Root подтвердил current accepted contract: canonical resource path внутри owner разрешён, вне owner — отказ. Negative теперь задаёт outside-owner path и по-прежнему требует отказ до spawn; positive дополнительно сверяет exact observed Pandoc resource-path, executable, cwd и clearEnv. Package/citation/privacy/nested-resource/concurrency assertions сохранены. Runtime fixture, package, metadata bag, process observations и errors получили конкретные/unknown types без новых any/casts.
- `process-input`: obsolete `profile.renders` давал synthetic разрешение arbitrary `Deno eval` внутри assignment execution, чего current closed assignment argv намеренно не допускает. Восстанавливать эту production дырку ради test не требуется. Root согласовал удалить только obsolete profile/launcher wrapper и дублирующие assignment-env/source sentinel cases. Их полезное покрытие уже есть в `assignment-child-execution`, `carrier-physical-guard` и actual `fixed-stdin-native`; они не требуют compatibility слоя.

`process-input` и его canonical entry **сохранены**: уникальные large duplex/early failure при сломанном pipe/closed stdin/missing tool и metrics BEGIN/END реального stdin child продолжают проверяться ordinary `commandInput`, с explicit functional env marker. Ordinary branch больше не заявляет assignment clearEnv coverage. Generic command API не менялся. Metrics fixture использует existing `Metrics`/`OperationSpec`; actual child не заменяется stub. False legacy profile/allowed-renders, new fixture framework, production/workflow policy, suite selection и draft subset не добавлены.

В `static-batch-checkpoint` все три calls теперь используют current typed runtime и explicit root. Прежние retained-child/zero→one→zero/failed-active-expired closing controls и static/general assertions оставлены. Results bag — unknown. Existing dynamic `publicOperation` unit harness и его оставшиеся legacy any вне runtime fixture не объявляются типизированными этой партией; массовый type audit не смешан с CI fix.

## Единственный адресный runtime пакет

Запущены только4 изменённых small tests, один раз каждый, без canonical и compiler matrix:

|Test|Исход|Время|
|---|---|---:|
|static-batch-checkpoint|PASS, прежние lifecycle/static/general cases, repeat5|0.647s|
|carrier-physical-guard|PASS, no mutated carrier spawn|0.390s|
|fixed-stdin-native|PASS, actual7Pandoc children/8guards, privacy/source-root/concurrency|2.011s|
|process-input|PASS, actual4MiB stdin/2MiB stdout, pipe/error/functional env/metrics|0.873s|

Суммарно3.921s. `core-ci-d7-addressed-tests.log`/`.json` сохраняют commands/codes/output. Citeproc `citation unknown not found` — ожидаемый negative unresolved-citation case; `EXPECTED_STDIN_WARNING` — existing warning-only success control. Это не скрытые failures. Unit scratch/source signature/hash операции выполнялись только на собственных малых test files; actual course Source, native render/inspect/hash/build/Java/SP pipeline не запускались. Java128QMD owner на frozen installed d7/PLda и PL Source writer не затронуты.

## Compiler статус четырёх tests

`quarto run` транспилирует эти tests без typecheck; runtime PASS сам по себе не доказывает новые annotations. Root поэтому отдельно назначил один targeted `deno check` только4 изменённых tests, без повторного product graph. Из existing Quarto Source установлены cache directory `/home/tolya/JavaCodex/.cache/quarto/deno_std/cache` и уже проверенная type-only map `/tmp/core-typed-owner-check-6lu44p5j/imports.json`.

Первый invocation **не дошёл до compiler**: я добавил `--cached-only` для гарантии отсутствия сети, но bundled Deno2.7.14 `check` этот flag не поддерживает. Exit1 за0.006s, `unexpected argument '--cached-only' found`; `core-ci-d7-test-typecheck.log`/`.json`. Это ошибка командной подготовки проверки, не TypeScript diagnostic. Root немедленно получил точный gap; подбор новых флагов/fetch/install не выполнялся.

Root подтвердил routine corrected invocation без ошибочного flag, с тем же known Quarto `DENO_DIR`/import map/default sandbox. **PASS**,4.111s, один адресный `deno check` четырёх изменённых файлов: `core-ci-d7-test-typecheck-corrected.log`/`.json`. Только четыре test entrypoints и их импортируемые declarations; новый product/API graph и negative matrix не назначались и отдельно не запускались. Compiler действительно проверил current `AssignmentRuntimeWitness` и typed fixtures. Вывод содержит четыре `Check`, новых download/network resolution сообщений нет; escalation/fetch/install не было. Это исправляет setup gap предыдущей команды, но не заменяет её историю на PASS.

Четыре изменённых TS файла отформатированы bundled Deno, `git diff --check` PASS. Новый full CI green, весь static/native/early suite и release/production readiness не заявляются. Push остаётся после независимого review/root; новый hosted run в этой фазе не запускался.

## Собственный допуск и сохранность

Original primary turn `01a1a21a-f52f-73a3-85e9-758306144504` сохранён. Полный interval теперь11records/10unique turns, current `01a1a29f-e62d-7432-8a46-eaf8d10a82bb` на строке1678; top+nested `gpt-6.1-sol/high` согласованы во всех records. Standard назначен root; service_tier отсутствует, скрытый server tier не утверждается. Globals/normative untouched. Изменён только test-only Source; production, installs, user data, deploy и workflow политика не менялись.

Source commit `ea197ad7e88a3410c4eea52f293d975aea63db4c` поверх `d7cc10eaa21fa35bbe32d73cc4afa216002393da`:4 test файла,+139/−148. Core Source после commit clean, fmt/staged diff check PASS. Exact диапазон независимого review — `d7cc10eaa21fa35bbe32d73cc4afa216002393da..ea197ad7e88a3410c4eea52f293d975aea63db4c`. Push/новый CI/установка/production исполнителем не выполнялись.
