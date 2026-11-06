# Performance: явный выбор top-level функций

## Итог и scope

**Локальный candidate готов для независимого review root.** Exact base Performance `e7267f1dd8b186d5b95a213f7a03a9bed71559a8`, workspace `/home/tolya/JavaCodex/java-release-20261110/performance`. По прямому GO изменены ровно один production file `src/instrumentation/ts/transform.ts` и existing `tests/instrumentation/ts.test.ts`. [Exact patch](performance-targeted-transform-20261104.patch),136 insertions/7 deletions. Commit/push/state/pins/installed не менялись. Source после единственного успешного compiler/control не менялся.

Основание — terminal [Java128 trace observation](java-current-performance-20261104.md): module-wide instrumentation сохранила101571recursive visit calls и потеряла93088events. Это coverage/observer cost конкретного trace, не доказанная причина старого900 s. Этот candidate не назначает новый full128 attempt, не меняет event cap и не обещает whole-course speedup.

## Что изменено

Internal `instrumentModule` принимает optional `topLevelFunctions?: readonly string[]`. Без этого поля прежний recursive traversal, unsupported/strict handling, manual-wrapper recognition и формат receipts сохраняются. CLI/config/registry/DSL/runtime event filter не добавлены; existing generic diagnostic caller новый selector автоматически не включает.

При явном selector имена разрешаются только в top-level TypeScript function declarations данного source file. Имя должно иметь ровно один declaration с body; повтор в запросе, отсутствующее/неоднозначное имя, declaration без body дают обычный preparation Error. Запрошенные generator/decorator/direct-eval и прочие выявленные unsupported случаи отказывают даже при `strict:false`, вместо silent partial success.

Automatic wrappers вставляются только в выбранные declarations. Вложенные functions/arrows/methods и остальные declarations исполняются без новых auto wrappers. Их исходные тела не удаляются и проверки Core не отключаются. Unsupported unselected getter не мешает явно выбранной допустимой функции. Существующий top-level manual rebind сохраняет `manuallyWrapped:true` и не получает второй auto wrapper.

Output `symbols` содержит только выбранные/наблюдаемые declarations с прежними path/start/end IDs; `skipped` относится к рассматриваемому scope. Это **явный partial map, не полная coverage модуля**: отсутствие unselected functions в map не означает их удаления либо выполнения с нулевой стоимостью. Existing `InstrumentedModule` не создаёт нового coverage certificate; caller обязан сохранять честную область наблюдения. До изменения существовавшие maps/getter skips terminal Java attempt не переписаны.

## Meaningful existing controls

В том же `ts.test.ts` добавлены два controls и расширен existing manual-wrapper case; четыре прежних controls не удалены/не skipped.

Положительный case исполняет transformed module с настоящим existing TS agent, `readonly` selector `[decode]`. Рекурсивный nested visit считает12, отрицательный ввод бросает тот же exported error object, unselected function и unselected getter возвращают прежние значения. Agent записывает ровно два begin выбранного declaration и end statuses ok/error; рекурсивный visit не получает auto events. Это execution/semantic observation, не whole-AST snapshot или строковый подсчёт wrappers.

В том же control default scope без selector по-прежнему исполняет visit recursion и наблюдает пять его реальных calls для ввода4. Existing default suite отдельно сохраняет hoisting, recursion, this/arguments, async, error identity, strict unsupported и manual/shadowed bindings. Negative selected controls проверяют missing/ambiguous/bodyless/generator/eval/nested-only names и повтор имени при `strict:false`.

Тестовый env mode и actual agent registry восстанавливаются в finally; временные modules/evidence agent удаляются. Runtime/quarto subprocess не создаётся. Новых mocks/supervisor/validator frameworks, fake casts, any или ignored diagnostics в production нет.

## Фактическая проверка

Один sequential driver с outer timeout13 s + kill grace1 s внутри общего cap15 s, retry0. **Оба шага PASS, общий elapsed1.745083381 s.** Exact evidence: [results.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-targeted-transform-20261104-evidence/results.json), per-step JSON/stdout/stderr в том же каталоге.

| Единственный запуск | Результат | Elapsed |
| --- | --- | ---: |
| `/usr/bin/deno check --cached-only src/instrumentation/ts/transform.ts tests/instrumentation/ts.test.ts` | exit0 |0.832580197 s |
| `/usr/bin/deno test --cached-only --no-check -A tests/instrumentation/ts.test.ts` | exit0, **6passed/0failed**, test envelope46 ms |0.911951110 s |

Cwd — Performance workspace, `DENO_DIR` **unset**, native `deno.json` autodiscovery, без manual node_modules/config overrides и install/network. Это system Deno affected check/control; bundled QuartoDeno integration этим не подтверждается. После final Source перед проверкой выполнены formatter двух файлов и `git diff --check` exit0; успешные проверки не повторялись.

Native/full128/render/inspect, CPU/RSS/overhead benchmark, новые Source walks/hash proofs, runtime probes и матрицы не запускались. Это прямое устранение возможности recursive wrapper flood для **явно выбранного** internal scope; default широкое наблюдение сохраняется. Старый terminal attempt остаётся INVALID, startup/HTML/900 s cause и TERM final-report recovery остаются UNKNOWN. В существующий Java driver selector не подключался: текущий GO ограничен transformer/test candidate.

## Собственный первичный допуск

Назначение `gpt-6.1-sol/high`, Standard; chat `01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1`, path `/root/perf_event_types`, неизменный origin `01a1a514-1688-7662-80fc-58bf7a272ae0`. Primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-41-59-01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1.jsonl`.

Перед Source и перед итогом прочитан весь origin interval: session_meta подтверждает собственные ID/path; turn_context8/171/270/405/734/918/1031/1046/1192/1274/1437/1655 — верхние и вложенные settings `gpt-6.1-sol/high`, PASS. Глобальные настройки не менялись; service_tier явно отсутствует, скрытая серверная маршрутизация не удостоверяется. Применено общее правило русского языка; отдельного local AGENTS в Performance нет.
