# Performance: ожидание зарегистрированного процесса без normal /proc scanning

2026-11-04. Source writer: `gpt-6.1-sol/high`, независимое review назначает root. База checkout `/home/tolya/JavaCodex/java-release-20261110/performance`: `744dfac5e872dec71700971e00142689c7ef23fd`, первоначально clean. Commit/push/PR/state/production не менялись.

## Изменение

В `src/runner/process.ts:404–410` добавлен один ранний переход цикла: когда PGID уже подтверждён, `child.status` ещё pending, нет failure, pending signal или начатого termination, supervisor ждёт прежние 10 ms и продолжает цикл без `liveGroup` / enumeration `/proc`. Это единственный production участок; новый event/receipt/framework/cache, `any` или typing protocol не добавлены. Inventory/capabilities/launchers из базы744 не затронуты.

Readiness и `.signal` receipt проверяются **до** этого перехода; signal/abort handlers остаются подключены. Это не безусловное `await child.status`: оно могло бы потерять своевременную обработку отказа sink или signal notice от PTY. Частота обычного wakeup/чтения signal receipt сохраняется; убран именно дорогостоящий `/proc` scan. После status, при failure или signal исполняется прежний scanner, confirmed-group fallback, grace/escalation, descendant/zombie cleanup, `await completed`, drain/EOF и выбор native status/resource receipt.

## Почему этот переход допустим

Перед правкой прочитан полный `runOwnedProcess`, `resource-child.ts` и существующие lifecycle fixtures.

- `resource-child.ts:69–91` публикует readiness и держит parent-chain до registration ack. Supervisor принимает candidate только после `ownedGroups(child.pid, sessions).has(candidate)`, сохраняет sessions, затем пишет `.registered`. Ранняя отмена остаётся в очереди; неподтверждённому PGID сигнал не отправляется.
- В старом нормальном цикле scan выдавал `remaining`, но до `status` этот набор не мог завершить ожидание: break требовал `status && remaining.size===0`. Без ошибки/сигнала набор также не запускал stop/escalation. Пропускается вычисление, результат которого в этом состоянии не используется для перехода.
- Pending статус setsid/script wrapper не доказывает, что native parent или каждый участник group жив. Новая ветка такого утверждения не делает. Возврат по одному native parent exit запрещён: как только wrapper status наблюдается, scanner проверяет живых потомков, в том числе orphan, и zombie-only group; EOF ожидается отдельно.
- PGID не переиспользуется, пока в соответствующей kernel group остаются процессы. Если group уже опустела при ещё живом proxy, старый normal scan всё равно не отзывал сохранённый `group` и не создавал start-time/PID-reuse identity. Поэтому новая ветка не удаляет существовавшую защиту от reuse: такой дополнительной защиты в ней не было. Числовой PGID, подтверждённый readiness/session ownership, остаётся прежней моделью; усиление этой модели здесь не заявляется.
- Если `status`, signal или failure появляются после проверки и во время pause, следующий цикл входит в сохранённый transition path; direct signal handler по-прежнему сразу вызывает `send`. Нового длительного безусловного ожидания нет.

Граница знания: отдельный искусственный PID-reuse эксперимент не проводился. Изменение не заявляется исправлением standalone SIGTERM другого `executeScenario` пути; тот код не менялся. Результаты SP captures другого runner и Java900 без Performance не используются как доказательство стоимости или причины.

## Адаптация существующих стендов

Новых тестов, проверяющих сам `if` или счётчик его вызовов, нет. Изменены три existing fixture, завязанные на убираемое normal polling:

1. `tests/runner/fixtures/orphan-stale-scan.ts:98–118`: стенд сам ждёт `parent-ready`, фиксирует stale enumeration **до fork**, затем разрешает native parent выйти. Раньше release происходил только при normal scan, что после оптимизации создавало бы тестовый deadlock. Сохранены real live descendant, его отсутствие в старом snapshot, один/два stale snapshots и обязательный `doneAtReturn`.
2. `proc-disappeared.ts:49–56`: stage `live` оставляет дочерний `sleep` после native exit7. Ошибка ESRCH/permission/IO попадает в сохранённый post-status scan живой group; fixture больше не требует необязательного сканирования parent во время 0.2 s работы.
3. `proc-supervisor-failure.ts:99–105`: stage `live` оставляет heartbeat descendant после exit7, как уже существовавший complete corridor. Persistent fault проверяется на реальном cleanup, не на удалённом idle polling. Registration setup, sibling preservation, heartbeat-after-return, controlled error и EOF assertions не ослаблены.

Две последние зависимости я обнаружил при финальном Source проходе после первого зелёного набора, а не до него. Это порядок моей работы; не runtime failure продукта. Исходные 6 успешных тестов не повторялись.

## Выполненная ограниченная проверка

Actual runtime: `/usr/bin/deno` **2.9.7**, V8 `15.0.245.2-rusty`, TypeScript `6.0.3`, Linux. Это dev system Deno, **не** Quarto CI Deno2.7. Не использован неподдерживаемый bundled `check --cached-only`; команды offline `--no-remote --frozen`, без network/install/cache cleanup. Actual Quarto cache `/home/tolya/.cache/quarto/deno_std/cache` не перенастраивался и не очищался.

| Запуск | Фактический результат | Измеренная wall |
| --- | --- | --- |
| Первый affected typecheck: `deno check --no-remote --frozen tests/runner/process.test.ts tests/runner/fixtures/orphan-stale-scan.ts` | PASS; production process/resource-child/assets и первоначально изменённый fixture входят в program | 0.911 s |
| `process.test.ts`, filter `owned process streams early output`, `normal parent exit waits`, `registered PTY group waits for orphan`, `a stale proc directory snapshot`, `zombie-only group returns`, `early abort queues` | 6 PASS / 0 FAIL / 18 filtered | 6.473 s |
| Только вновь затронутые scanner cases: `proc live:` (3), `persistent … (live)` (3), `known registered group survives a stale error-path` (1) | 7 PASS / 0 FAIL / 32 filtered; прежние зелёные cases не повторялись | 4.016 s |
| Дополнительно разрешённый root check только новых roots: `deno check --no-remote --frozen tests/runner/fixtures/proc-disappeared.ts tests/runner/fixtures/proc-supervisor-failure.ts` | PASS; прежняя affected compiler программа не повторялась | 0.820 s |

Совокупно compiler + runtime commands **12.220 s**, общий cap60 s, retry=0. Первая команда и первые 6 tests были под общим deadline60; новые cases получили cap45 при остатке52.616 s, дополнительный check — cap45 при остатке48.600 s. Cap не повышался, timeout/отказов/перезапусков не было. `--no-check` на обоих runtime test invocations не выдаётся за TypeScript check.

Проверены полезные результаты: ранний вывод и полный журнал, numeric7/signal null, потомок доживает после родителя до `done`, PTY orphan не теряется, stale enumeration не скрывает живого потомка, zombie-only не блокирует возврат при живом holder в другой group, реальный live member ожидается, ранняя отмена и stubborn escalation сохраняют signal, persistent scanner errors останавливают собственное дерево и оставляют unrelated sibling живым, available log сохраняется.

`proc-disappeared.ts` и `proc-supervisor-failure.ts` изменены **после** первого affected check. Root явно разрешил закончить целостный candidate и проверить только эти вновь затронутые offline roots; их отдельный compiler PASS приведён выше. Production после первого compiler PASS не менялся, все Source после последних runtime/compiler PASS также неизменны. Полная suite, вся signal/launcher matrix, Quarto render/inspect, сборка, hashes и benchmark не запускались. `git diff --check` был PASS для первоначальной двухфайловой дельты; за последующую четырёхфайловую дельту этот результат не выдаётся.

## Стоимость и остаток

Source доказывает отсутствие normal `/proc` enumeration на описанной ветке, а не measured speedup. В старой реализации следующий scan начинался после стоимости предыдущего scan и pause10 ms; это не строгое измерение100 scans/s. CPU/IO доля, course wall saving и поведение QuartoCI2.7 **UNKNOWN**. Test durations — приёмка lifecycle, не сравнительный benchmark. Readiness scan, signal notice file polling, terminating scans и post-status descendant scans сохраняются.

Готовая дельта: один production файл и три существующих fixture. Данные пользователя и состояние релиза не затронуты; independent review и решение root о принятии ещё впереди.

## Допуск модели

Собственные chat `01a1a34e-02f6-7021-ba73-02522b0a6a14`, agent `/root/review_download_types`; primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T00-26-01-01a1a34e-02f6-7021-ba73-02522b0a6a14.jsonl`. Исходный turn роли `01a1a34e-032a-7541-b085-5af16bf83f02` сохранён. Полный интервал `turn_context` строк8,105,169,207,346,569,649,706,721,878: верхние `model/effort` и вложенные `collaboration_mode` settings согласованы с назначенной `gpt-6.1-sol/high`. PASS. Standard назначен; скрытый service tier не утверждается.
