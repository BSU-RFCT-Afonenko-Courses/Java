# Независимый review обновления Core test callers

3 ноября 2026. Проверен exact Core диапазон `d7cc10eaa21fa35bbe32d73cc4afa216002393da..ea197ad7e88a3410c4eea52f293d975aea63db4c`: четыре test файла, +139/−148. Production, workflow, API и выбор suite в diff отсутствуют.

**Вердикт: APPROVE. Существенных findings нет.** Test maintenance приводит callers к текущему execution API и сохраняет полезные гарантии transport/lifecycle/privacy/physical guards. Новое hosted CI green этим review не утверждается.

## Соответствие текущему API

`tests/static-batch-checkpoint.ts:50–73,188–216,331–340` использует конкретный `AssignmentRuntimeWitness` и отдельный owner root во всех трёх вызовах. Это соответствует неизменённой сигнатуре `runtime/assignment-execution.ts:43–49`: `(runtime, root, use, beforeChild?)`. Прежний вызов передавал callback на место root и оставлял use неопределённым. Существующий dynamic publicOperation harness, retained-child/closing failed-active-expired, zero→one→zero, source/membership/static/general и lifetime assertions не удалены. Добавленный runtime — private unit context для этих проверок; он не объявлен genuine admission или producer witness.

`tests/carrier-physical-guard.ts:63–95` исправляет тот же positional caller; probe сохраняет real `Carrier.contractCarrierWitness` и actual carrier YAML mutation в awaited beforeChild. Constructor proxy на `:57–62` намеренно запрещает spawn; проверяется `CONTEXT_OWNER` и ноль constructors (`:87–97`). Это meaningful negative до OS boundary, а не положительный fake producer. Прежние extra-file и symlink отказы (`:23–54`) сохранены. Новые casts/`as any` не добавлены; старые Carrier/runtime/probe casts убраны.

## Fixed stdin и удалённая obsolete обвязка

`tests/fixed-stdin-native.ts:55–64,137–151` задаёт current execution witness/root и наблюдает настоящий `Deno.Command` через forwarding Proxy. `Reflect.construct` сохраняет actual Quarto Pandoc execution. Package имеет `BodyPackage`, document metadata — unknown bag, errors и JSON output не маскируются новым `any`.

Negative source base на `:253–267` теперь находится действительно вне owner (`resolve(root, "../foreign")`) и требует прежний `BODY.PRODUCER_INPUT_CHANGED` до spawn. Это соответствует неизменённому production contract `assignment-execution.ts:173–193`: canonical resource path внутри root разрешён, вне root не проходит qualified Pandoc argv. Прежняя ожидаемая блокировка любого другого каталога внутри owner зависела от retired profile.renders allowlist. Её замена не ослабляет текущую границу.

Actual nested source resource-path, executable, cwd и clearEnv дополнительно проверяются для каждого наблюдённого child (`fixed-stdin-native.ts:276–284`). Сохраняются public attribution без private/root-collision данных, unresolved citation, reserved bibliography collision, forged metadata refusal до spawn и parallel isolation (`:208–274`). Проверка package policy теперь требует `restricted` в сообщении вместо прежней альтернативы context/message; она не стала слабее.

`tests/process-input.ts` переведён на ordinary `commandInput`, что соответствует его реальной цели: arbitrary `Deno eval` transport не принадлежит closed assignment argv. Сохраняются UTF‑8/BOM и concurrent isolation (`:50–62`), actual 4 MiB stdin и 2 MiB stdout duplex (`:63–68`), exit7/stderr несмотря на раннее закрытие pipe, closed stdin, malformed UTF‑8, warning-only success, missing-tool diagnostics и прежний command API (`:69–115`). Forwarding Proxy не заменяет child result.

Удалены obsolete assignment profile/renders, ambient clearEnv case и env/source sentinel cases, а не уникальные pipe проверки. Общие assignment gates расположены до transport branch в неизменённом `infrastructure/process.ts:43–49`; их полезное покрытие остаётся в `tests/assignment-child-execution.ts:80–87` (foreign env), `:142–153` (beforeChild refusal без spawn), обновлённом carrier test (реальная post-await mutation до constructor), а actual stdin path использует assignment context в fixed-stdin test. Ordinary test больше не обещает assignment clearEnv/source qualification. Его metrics fixture типизирован existing `Metrics`/`OperationSpec` и оборачивает настоящие success/failure children (`process-input.ts:116–189`), сохраняя BEGIN/END и functional env/parent context assertions.

Оставшиеся legacy `any` и dynamic fixture в static test вне изменённых runtime callers не выдаются за завершённый type audit. В четырёхфайловом diff новых explicit `any`, `as any`, fake admission или compatibility слоя нет.

## Прочитанные свидетельства и ограничения

Прочитаны `reports/core-ci-d7-failure.md` и сохранённые логи/JSON. Локальный hosted log `core-ci-d7-job-126111898696.log:3350–3358` подтверждает первый отказ canonical004/static-batch-checkpoint с `Function.prototype.apply ... undefined` через `assignment-execution.ts:35,49` и старый test caller. Три остальных файла не объявляются отдельными hosted failures: они найдены адресным поиском того же stale API pattern. Нет оснований из этой ошибки выводить regression production fences d7.

Сохранённый runtime пакет автора — по одному запуску четырёх изменённых tests: все exit 0, суммарно 3.921 s. Лог фиксирует static lifecycle cases, carrier negative, actual семь Pandoc children/восемь guards и ordinary 4,194,304 B stdin/2,097,152 B stdout. Первый typecheck invocation отказал на неподдерживаемом `--cached-only` до compiler (`core-ci-d7-test-typecheck.log:1`); TypeScript failure или PASS ему не приписывается. Corrected invocation имеет exit 0 за 4.111 s и четыре `Check` в `core-ci-d7-test-typecheck-corrected.log:1–4`.

Эти результаты прочитаны, не воспроизводились reviewer. Tests/compile/network/native/render/inspect/hash/install не запускались, Source/state не менялись; единственная запись — этот отчёт. Existing remaining canonical/early/native tests и новый hosted CI не проверены этим узким review. Java full run на frozen installed d7 от test-only patch не зависит; новой установленной production готовности данный diff не заявляет.

## Собственный первичный допуск

Исполнитель `/root/review_core_owner_types`, собственный ID `01a1a28b-583e-7d71-ab50-0f123b8de5a4`; primary `session_meta.agent_path` подтверждён ранее в собственном rollout `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-53-23-01a1a28b-583e-7d71-ab50-0f123b8de5a4.jsonl`.

Весь reviewer interval сохранён от исходного turn `01a1a28b-586d-7d31-94ce-e366ffbd375f` (строка 8), включает `01a1a295-200e-7430-bf17-fd32ab456ba8` (строка 142) и нынешний `01a1a2a9-c84c-7b92-acc3-5270045b6d07` (строка 226). Все три `turn_context` имеют top `gpt-6.1-sol/high` и ту же nested пару в `collaboration_mode.settings`. Baseline не сдвигался; нынешний test maintenance review начинается в третьем ходе. Standard назначен root, `service_tier` отсутствует и скрытый серверный tier не утверждается. Глобальные settings и нормативные документы не менялись.
