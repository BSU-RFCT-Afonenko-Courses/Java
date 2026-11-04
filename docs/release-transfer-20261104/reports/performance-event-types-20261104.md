# Performance Event ingestion: строгая граница ABI 1

Назначение `gpt-6.1-sol/high`, Standard. Собственный chat `01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1`, agent `/root/perf_event_types`, parent `01a1a142-7adc-7371-9765-4871d807dfcd`. Primary: `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-41-59-01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1.jsonl`, `session_meta` строка 1. Origin turn `01a1a514-1688-7662-80fc-58bf7a272ae0`; полный доступный интервал начинается с него, `turn_context` строка 8: верхние `model=gpt-6.1-sol`, `effort=high` согласованы с `collaboration_mode.settings.model/reasoning_effort`. Вход PASS. Отдельный service tier первичными записями не указан; глобальные настройки не менялись.

Exact Performance base `10810e482dcecde1eecf2e697cac0e3d628f05fe`, рабочее дерево при входе чистое. В применимой цепочке каталогов и внутри Performance `AGENTS.md` отсутствует. Прочитаны `java/docs/model-admission.md`, адресные сведения `reports/typescript-any-audit.md`, `reports/pattern-pass-performance-ci-20261104.md`.

## Фактическая граница и план

Производители `src/agents/ts/agent.ts` и `src/agents/lua/agent.lua` буферизуют Event и сохраняют executor JSONL. Единственный фактический Event ingest в `src/analysis/normalize.ts:194` вызывает `validateEvent(JSON.parse(line))`, после чего нормализатор связывает span и parent ID; `operationAttribution` вызывает `.match` у operation ID. DTO — `src/protocol/types.ts:2`, соответствующий wire описан `docs/plan/03-contracts.md:88–110`; producer context приходит из `bridge/bridge.ts:2–9`.

В `src/protocol/validate.ts` вход unknown немедленно приводился к Event, при этом optional IDs, timestamp/status/counter вне соответствующего event kind проверялись не полностью. Truthy numeric span/operation ID проходили span guard; numeric operation ID мог попасть в `.match`, parent ID — в строковую карту. Это реальный semantic bug на malformed внешних данных, а не только type-only cleanup.

Согласованный scope: один production файл `validate.ts` и существующий protocol test. Настоящее assertion narrowing должно проверять весь Event DTO на той же границе, сохранить object identity/extension keys и допустимые пустые optional string IDs; обязательные span IDs и document ID сохраняют прежние ограничения. Внутри normalize повторная валидация не добавляется. Валидный ABI 1 wire по прочитанному контракту этим не меняется.

Фактические результаты и итоговый полный интервал приведены ниже.

## Candidate и фактические проверки

Изменены только собственные `src/protocol/validate.ts` и `tests/protocol/events.test.ts`. Прежний `as Event` и non-null assertions из парсера убраны. Неэкспортируемый `assertEvent(unknown): asserts value is Event` проверяет record, все обязательные поля, optional IDs, timestamp/status, primitive attributes и полную nested counter форму до выдачи прежнего публичного `validateEvent(unknown): Event`. Неизвестные extension keys и сам объект сохраняются. Array records отклоняются; для JSONL это не новый отказ валидного Event object.

Existing field requirements сохранены: finite/nonnegative timestamp и clock на begin/end, status на end, counter на counter; optional IDs могут оставаться пустыми строками вне прежнего required span guard. Optional timestamp на прочих событиях проверяется только как number, дополнительных finite/nonnegative ограничений там не добавлено. Counter name остаётся любой строкой. Валидные optional status/counter на иных event kinds принимаются по declared DTO; дополнительные обязательные поля для quality не вводятся.

Сначала выполнен `deno fmt src/protocol/validate.ts tests/protocol/events.test.ts`: 2 files checked, exit 0. Первоначальная compiler попытка:

```sh
deno check --cached-only --config deno.json --lock deno.lock --frozen --node-modules-dir=manual src/analysis/normalize.ts tests/protocol/events.test.ts
```

Runtime executable `/usr/bin/deno`, `DENO_DIR` не задан, cwd Performance. Exit 1 через 0.144 s: `Could not find a matching package for 'npm:@types/node' in the node_modules directory`. Это dependency/toolchain preparation failure до TypeScript diagnostics, compiler PASS не заявляется. Сохранённый вывод `/tmp/performance-event-types-check-20261104.json`. В исходном wrapper последующая behavioral команда не запускалась из-за failed compiler; после этого она запущена ровно один раз самостоятельно:

```sh
timeout 44s deno test --no-check --cached-only --config deno.json --lock deno.lock --frozen --node-modules-dir=manual tests/protocol/events.test.ts
```

Результат: **5 passed, 0 failed**, 4 ms test time, около 0.125 s wall по tool output. Это прежние 3 protocol tests и 2 meaningful boundary cases: malformed ID на span и nonspan, сохранение identity/extensions/empty optionals, malformed optional timestamp/status/nested counter вне обычного event kind. Первоначальная compiler попытка и один behavioral run заняли около 0.269 s в сумме. После установленной preparation ошибки root отдельно разрешил один corrected compiler run, результат ниже. Behavioral не повторялся; установка зависимостей/сеть не использовались.

Полный собственный primary интервал повторно прочитан от того же origin turn: `turn_context` строка 8, верхняя и вложенная пара `gpt-6.1-sol/high` согласованы, итоговый допуск PASS. Первоначальное ограничение compiler acceptance из-за отсутствующего manual `@types/node` устранено корректным offline runner ниже; независимый root Source review ещё предстоит. Не заявляется строгая типизация всего Performance, `Event` остаётся существующим DTO без нового discriminated union. `normalize.ts`, остальные DTO/API и принятые process/scanner файлы не менялись; capture/render/build/inspect/hash/network/install не запускались, commit/push не выполнялись. Concurrent изменения outputs/tools принадлежат другому writer и в этот diff не включены.

После failed compiler установлен конкретный toolchain mismatch по сообщению соседнего `qrc_perf_companion`: его успешный affected compiler запускался из того же cwd как `deno check --cached-only src/analysis/outputs.ts tools/validate-java.ts tools/recheck-repeat.ts tools/repeat-java.ts tools/scaling.ts`, `/usr/bin/deno`, `DENO_DIR` unset, без `--node-modules-dir=manual` и без `--frozen`, с обычным autodiscovery `deno.json`. Это соответствует штатному task check. Моя explicit manual опция ошибочно требовала местный `@types/node`; successful default runner соседа не является проверкой моего Event diff. Root уведомлён до какого-либо нового compiler действия и разрешил одну исправленную preparation попытку по установленной причине.


## Corrected offline compiler

По отдельному разрешению root после установленного manual toolchain mismatch выполнена одна исправленная compiler команда с обычным autodiscovery `deno.json`, тем же `/usr/bin/deno` и unset `DENO_DIR`:

```sh
timeout 44s /usr/bin/deno check --cached-only src/protocol/validate.ts src/analysis/normalize.ts tests/protocol/events.test.ts
```

Реальный consumer path `src/analysis/normalize.ts` установлен чтением Source выше. **Exit 0, PASS**, 0.597 s wall: проверены parser, его настоящий normalize consumer и protocol cases. Локальная установка пакетов и сеть не использовались; `--cached-only` сохранился. Это единственная исправленная подготовленная compiler попытка после первого environment refusal; зелёный check или 5 behavioral tests не повторялись. Суммарный wall двух compiler invocations и одного behavioral run ≈0.866 s, в пределах total cap 45 s. Итог: affected offline compiler PASS, protocol 5/5 PASS; root independent review остаётся отдельной приёмкой.
