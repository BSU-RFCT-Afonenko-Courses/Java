# Performance: минимальный следующий diff для streaming logs

2026-11-04. **Предпочтителен один production file `src/runner/execute.ts` + расширение одного existing case в `tests/runner/execute.test.ts`.** Сохранить нынешнюю command composition, заменить только full-buffer `Deno.Command.output()` на `spawn()` с двумя параллельными file-only pumps. Existing owned supervisor целиком не переиспользовать: он меняет process/status/resource/terminal semantics. Это Source-only план; Source/эксперименты/Native/signals/compiler не запускались.

Source `performance` — HEAD `356a76b52d4fa0b20b965a82c1997e34632173fc`, status clean при чтении. Локального AGENTS.md в этом repo нет. Прочитаны только actual execute/owned-process/render/resource-child boundaries, caller и связанные existing execute/process cases. SP115s/143receipts повторно не читались: факт берётся из [сохранённого integration отчёта](sp-shared-site6-stage-integration-20261104.md).

## Дефект и полезный результат

`execute.ts:131–145` ждёт `.output()` целиком, затем пишет `stdout.log`/`stderr.log`. Пока Native выполняется, логи на диске отсутствуют, весь вывод держится в Deno buffers. Interrupt до возврата `.output()` теряет эти buffered bytes; итоговый status/normalize/report тоже могут не быть достигнуты. Это конкретно наблюдалось в interrupted SP stage, но не требует повторного course capture для планирования исправления.

После минимального diff байты stdout/stderr становятся доступны в соответствующих обычных log files **до завершения child**, без обязательной копии всего вывода в памяти. При внешнем завершении caller сохраняется уже записанный prefix — не обещаются bytes, которые child ещё не flushed, остались в pipe/read buffer или не дошли до file write. Никакого обещания `fsync`/durability после power loss или final record/status после TERM этот срез не даёт. Старый standalone SIGTERM/status/flush issue остаётся отдельным UNKNOWN.

## Два варианта

| Вариант | Реальная возможность reuse | Почему выбран / отклонён |
|---|---|---|
| Целиком `runOwnedProcess` из `process.ts` | Уже пишет incremental logs через private `tee`, drain/status, handles log failures; `runNativeRender` использует его `render.ts:17–29` | **Не drop-in.** Linux setsid, readiness/cancel/status files, `/proc` group discovery/polling, TERM/KILL policy, optional GNU time, extra env and possible PTY/combined stream/live console. Если передать уже собранный execute wrapper, получится дополнительная resource/supervisor composition; если передать raw Native command — меняются time/hyperfine status/timing rules. Это большая интеграция, не нужная для log persistence |
| Direct `spawn` + file-only pumps внутри `execute.ts` | Переиспользовать **алгоритм** `process.ts:136–181`: binary chunks, partial writes, first error, drain после log sink failure; не новый supervisor | **Выбран.** Прежние executable/args/cwd/env/stdin/status/resource branches сохраняются; нужны только перенос bytes и ожидание completion. Не экспортировать private tee и не редактировать process.ts ради одного consumer |

Не использовать `ReadableStream.tee()` ради file + console: медленная независимая ветвь способна снова накопить buffers, а live console forwarding не является нынешним поведением `executeScenario`. Существующий `process.ts` tee — пользовательский pump, не этот Web Streams API.

## Точный минимальный алгоритм

1. До command блока сохранить без изменений argv expansion, cleanEnvironment/diagnostic merge, `useTime` и Hyperfine selection. Logs остаются `outputDirectory/stdout.log` и `stderr.log`.
2. Запустить **тот же** `new Deno.Command(executable, {args,cwd,env,clearEnv:true,stdin:'inherit',stdout:'piped',stderr:'piped'}).spawn()`. Не добавлять listeners, setsid, readiness/status/cancel receipts, новый shell, PTY, environment или abort protocol.
3. Немедленно начать два concurrent file-only pumps: открыть каждый log с create/truncate/write, читать соответствующий `ReadableStream<Uint8Array>`, дописывать chunk полностью с учётом short writes, не декодировать/склеивать text. Zero-progress write — IO failure, а не бесконечный loop. Никакого global chunk array/full output Uint8Array.
4. File open/write/close error сохранить как первую capture error; не маскировать последующим close. При log-file failure продолжить дренировать соответствующий pipe, не накапливая bytes и не объявляя truncated log успешным. Это не повод нового kill/supervisor policy: прежний путь также выполнял Native до попытки final log write. Потоки читать одновременно; нельзя сначала дождаться stdout, а затем stderr/status.
5. Получение `child.status` и оба pumps наблюдать вместе, дожидаясь completion без unhandled rejection; first capture error не превращать в Native exit code. При transport read failure не заявлять, что оставшийся output/status гарантированно восстановлен; никакого нового cancellation механизма в этом diff. На успешной capture перейти к существующим resource parsing/normalize/writeRunArtifacts без изменения их форматов. Удалить прежние две final full-buffer `Deno.writeFile`.

Файлы можно открыть внутри начавшихся pumps, как existing tee: тогда IO preparation failure не превращается незаметно в новый pre-spawn Native refusal. Даже если log открыть не удалось, known Native status не придумывается: дренирование/ожидание всё ещё требуется. Локальный helper возможен как private функция `execute.ts`; общая exported abstraction/новый runner API не нужны.

## Semantics, которые обязательно удержать

| Область | Сохраняемая граница |
|---|---|
| argv/cwd/environment/stdin | Literal args, прежний cwd, `clearEnv:true`, functional profile, diagnostic cleanup/merge и inherited stdin; без auto terminal mirror/PTY и без добавленного owned-group env |
| Прямой Native status | Только полученный `child.status` даёт code/signal. Pending/spawn failure не получает искусственного0/success; signal не заменяется numeric exit |
| GNU time | Прежние `/usr/bin/time -f '%U %S %M' ... resource-child.ts tool-status.json executable args`; тот же status parser, separate tool signal, `toolWallSeconds`, resourceScope и null unavailable resources |
| Hyperfine | Прежние literal shell-none quoting, runs1/warmup0, raw schema/version/finite field checks и replacement wall/exit/resource values; signalAvailability остаётся unavailable. Не замерять уже supervised command повторным time |
| Wall | Start остаётся непосредственно перед spawn; completion — Native status плюс drain, без normalize/report. GNU-time/Hyperfine wall overrides остаются прежними. File IO теперь идёт одновременно с Native и может влиять на pipe backpressure/drain: **численное равенство прошлому wall не обещается**, observer cost нельзя выдавать за speedup/CPU |
| Invalid diagnostics | Log IO/transport error не становится dataValid=true или Native nonzero. Минимальный срез сохраняет нынешний exceptional path: не возвращать успешный RunResult/VALID и не придумывать итоговый record при отсутствии полного capture. Native code0/23, если реально известен, не переименовывать в «diagnostic exit» |
| Report/status recovery | `render.ts:139–167` имеет отдельную модель OwnedProcessError/known outcome → INVALID diagnostics. `executeScenario` сейчас её не имеет: не притворяться, что простая замена pump автоматически добавит эту recovery гарантию. Новый final report после capture IO failure/TERM выходит за этот один-file slice |

Если root требуется именно recovery полноценного final record с known Native outcome при downstream log failure, это нужно отдельно согласовать как расширение error/report control flow, а не скрыть в streaming patch. Для текущей цели достаточно сохранить error outcome и честную неполноту, сохранив полезные disk prefixes.

## Риски и стоимость

- **Flood/memory:** bounded in-flight stream chunks вместо памяти порядка всего stdout+stderr. Это уменьшение собственного accumulation по Source, не измеренный RSS speedup. Child/native/telemetry могут иметь свои buffers. Размер log files остаётся пропорциональным output; truncation/ring buffers/limits не вводятся.
- **Backpressure/log IO:** await file writes может замедлить chatty child на медленном диске. Это осознанная цена раннего persistence; параллельные stdout/stderr pumps избегают искусственного взаимного deadlock. Не обходить backpressure безграничной очередью.
- **Порядок:** byte order сохраняется внутри каждого stream; общий stdout/stderr ordering никогда не был одним контрактом. Декодирование UTF-8 по отдельным chunks не делать.
- **Visibility/privacy:** содержимое logs прежнее, теперь раньше доступно в уже назначенном report directory. Не добавлять terminal echo/full env/path headers или новый внешний destination. Наличие partial log не является authority/status attestation.
- **Failure:** disk-full/permission/close/read failures должны оставаться видимыми capture errors. После file-write failure drain продолжает освобождать pipe; успешный Native exit не доказывает полноту logs. Hard-kill может оборвать последний chunk, no fsync guarantee.

Measured cost нового пути **UNKNOWN**. Старый SP outer115.005s — timeout другого workload, не benchmark этих pumps. Existing `process.test.ts` содержит ранний streaming и large-output controls для другого helper; их не повторять как доказательство changed execute path.

## Один будущий bounded meaningful control

Расширить **существующий** `tests/runner/execute.test.ts:18–72` («runner keeps argv, cwd, outputs and build exit code…»), не создавать новый suite/fixture framework. Тот же небольшой Deno child пишет известные bytes в оба stream, затем ждёт test-owned release file. Parent начинает `executeScenario` без await, наблюдает точные ранние stdout/stderr files **пока child ещё не отпущен**, затем создаёт release file и проверяет прежний exit23, argv/cwd/profile/clean env, final bytes и absence off telemetry. Две небольшие bounded порции каждого stream можно сделать больше pipe buffer (например, суммарно256KiB на stream), чтобы проверить concurrent drain; сравнивать bytes, не timing/RSS threshold.

Gate ограничен коротким собственным deadline, test cleanup не должен оставлять child waiting при assertion failure. Один адресный existing case, общий cap≤10s/retry0, без Quarto, Native render или сигналов, **не новая30s matrix**. Сначала этот control должен показать отсутствие early files на old `.output()` пути, затем доказать early visibility на streaming пути. Исполнение сейчас не разрешалось и не проводилось. Hyperfine/time/signal old green suites не назначаются заново этим планом.

В этой работе создан только данный report. Source/tests/cache/state/pins не менялись; новых commands/experiments/коммитов/push/network нет. Primary: собственный chat `01a1a25f-b098-7242-bf6d-d0e8f6517361`, origin `01a1a25f-b0fb-7fd2-b244-83933af77834`, primary `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-05-42-01a1a25f-b098-7242-bf6d-d0e8f6517361.jsonl`. Только model/effort/turn fields всех23 contexts, lines8–3377: top/nested все `gpt-6.1-sol/high`, latest `01a1a565-4123-7c23-9f3c-2b01f875f842`. Standard назначен; hidden routing не удостоверяется; globals/admission docs не менялись.
