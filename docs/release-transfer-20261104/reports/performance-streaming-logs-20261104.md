# Performance: streaming logs в executeScenario

2026-11-04. **Ограниченный Source candidate готов к независимому review root.** Поверх exact Performance `356a76b52d4fa0b20b965a82c1997e34632173fc` изменены один production file `src/runner/execute.ts` и один existing case в `tests/runner/execute.test.ts`. [Точный diff](performance-streaming-logs-20261104.patch): два files, +126/−12. Commit/push/state/pins/installed не изменялись.

## Поведение и границы

Принят [Source plan](performance-streaming-logs-next-20261104.md): прежний `.output()` удерживал весь stdout/stderr до status, а затем записывал logs. Новый private `streamLog` пишет binary chunks в прежние `stdout.log`/`stderr.log` во время исполнения. Оба потока дренируются параллельно с `child.status`; полные output arrays не собираются. Проверяются short writes/zero progress, file handles закрываются, первая IO/status ошибка сохраняется. После log open/write failure соответствующий pipe продолжает дренироваться без накопления bytes; capture failure не превращается в успешный RunResult/VALID.

Прежние executable/argv/cwd/env/`clearEnv:true`/inherited stdin сохранены. GNU-time/resource-child и Hyperfine command composition/parsers, signal и wall overrides, normalize/writeRunArtifacts path не изменены. Native code/signal берутся только из полученного status. `runOwnedProcess`, process groups, signal listeners, receipts/schemas, новый supervisor, live console forwarding или authoritative partial report не добавлены.

Native wall без resource override по-прежнему охватывает spawn/completion+drain до normalize/report, однако incremental log IO теперь перекрывает исполнение и может давать backpressure. Численное равенство старым timings/ускорение/RSS delta не заявляется. GNU time/Hyperfine overrides остаются прежними.

При прерывании сохранятся только уже записанные disk prefixes. Это **не** исправление старой standalone SIGTERM/status/final report проблемы и не fsync/power-loss guarantee; final record на interrupted/error path не обещается. Медленный диск/большой output могут замедлять child; размер logs не ограничивается и не обрезается. File/read/close errors остаются capture errors. Их fault injection данным одним control не проверялась.

## Meaningful existing control

Расширен только existing case «runner keeps argv, cwd, outputs and build exit code and off creates no telemetry». Его собственный Deno child одновременно пишет два разных бинарных потока: на каждом256KiB всех byte values плюс прежний metadata/error prefix; затем ждёт test-owned release file. Parent проверяет точные полные bytes **до открытия gate**, пока `executeScenario` ещё не завершён. После release проверяются те же final bytes, Native exit23, очищенное diagnostic env/functional profile, argv/cwd, preparation и отсутствие off telemetry. Cleanup открывает gate и ждёт running promise даже при RED; собственный child также имеет короткий конечный deadline.

Никаких Quarto/render/signal/proc tests, новых suites или full matrix. Test использует system Deno2.9.7 с existing cached graph, без `DENO_DIR` override; bundled QuartoDeno2.7.14 integration этим результатом не утверждается.

## Фактическая RED → GREEN evidence и бюджет

Полные команды/exit/elapsed — в отдельных JSON, stdout/stderr — в соответствующих logs; прежние captures не переписаны.

| Фаза | Фактический результат | Outer wall |
|---|---|---:|
| [RED до production edit](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-streaming-logs-20261104-evidence/runtime-red.json) / [log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-streaming-logs-20261104-evidence/runtime-red.log) |exit1, один case FAILED на «Оба лога должны быть доступны до завершения child». Нужный дефект `.output()`, не preparation error |1.466652213s |
| [Первый post-production control](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-streaming-logs-20261104-evidence/runtime-green-first-assertion-error.json) / [log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-streaming-logs-20261104-evidence/runtime-green-first-assertion-error.log) |exit1: early-byte/gate проверки уже прошли, затем моя новая fixture ошибочно сравнила final stderr Uint8Array прежним `assert.equal`. Исправлена только assertion на `deepEqual` |0.667770516s |
| [Final GREEN](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-streaming-logs-20261104-evidence/runtime-green.json) / [log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-streaming-logs-20261104-evidence/runtime-green.log) |exit0;1passed/0failed/4filtered, case206ms; оба binary streams до child exit и final semantics подтверждены |0.444748514s |
| **Все runtime controls, включая ошибку fixture** |**2.579171243s ≤10s**; unchanged-code retries0 | |
| [Единственный final affected compiler](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-streaming-logs-20261104-evidence/compiler.json) / [log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-streaming-logs-20261104-evidence/compiler.log) |exit0, `deno check --cached-only src/runner/execute.ts tests/runner/execute.test.ts`; cap10s включая grace |0.817828545s |

Каждый runtime вызов имел `timeout4s --kill-after1s`; все завершились сами, grace/signals не использовались. Дополнительный post-production запуск понадобился из-за **моей ошибки test assertion**, он учтён, не скрыт за утверждением ровно двух calls. Никаких зелёных контролей после final GREEN не повторяли; после compiler Source не менялся.

`deno fmt` затронул только эти два files, `git diff --check` exit0; полный final diff прочитан. В production нет нового `any`/cast/ignored diagnostic. Проверка остаётся адресной: Hyperfine, distinct actual signal vs numeric143, injected disk failure и standalone interruption не запускались повторно. Их composition Source сохранена, нового runtime PASS для них не заявляется. Общее время сбережено строгим существующим scope, не новой матрицей.

Изменены только названные два Source files и новые report/evidence artifacts. Original course outputs/student files/results, historical SP/Core captures, globals и state не трогались; installs/dependency fetch/push/commit отсутствуют. Candidate uncommitted; следующий шаг — независимый review root.

Primary admission: свой chat `01a1a25f-b098-7242-bf6d-d0e8f6517361`, origin `01a1a25f-b0fb-7fd2-b244-83933af77834`, primary `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-05-42-01a1a25f-b098-7242-bf6d-d0e8f6517361.jsonl`. Прочитаны только model/effort/turn fields всех24 contexts, lines8–3452: top/nested все `gpt-6.1-sol/high`; latest `01a1a56a-ba17-7ab1-ad38-5260ac79349f`. Standard назначен; hidden routing не удостоверяется; globals/normative admission docs не менялись.
