# Чтение сохранённых process receipts — 3 ноября 2026

Источник: `performance-sp-summary-capture/runs/b2b5cee6-0382-4326-83e3-457b113d4562/{processes,events,wall.trace.json,causal.json}`, parent `observations.json`, `performance-tooling-integration.md`. Один actual native export SP: 7 документов, frozen SP 3bb/Core d7/PL da/Performance cff. Новых runs/inspect/build/hash/network не было; выводы на full Java не переносятся.

**Доказано.** Все 107 receipts имеют exit 0: 67 Deno agent activated/executed, 7 render Pandoc с Lua и 33 conversion Pandoc без Lua. По actual argv (отдельного поля `mainModule` в receipts нет):

| Назначение | Deno | Pandoc |
| --- | ---: | ---: |
| Quarto `run export-course.ts` → actual export module | 2 | — |
| Quarto CLI `render` | 1 | 7 |
| Quarto CLI `inspect` (tasks ×2, batch contract ×1) | 3 | — |
| Quarto `run early-filter.ts` → actual early filter, по 7 каждого | 14 | — |
| Quarto `run late-filter.ts` → project-download late filter, по 4 | 8 | — |
| Hooks: Core pre/post, project-download pre/post, reference-catalog post, course-site collect | 6 | — |
| Quarto CLI `pandoc`: markdown→JSON / JSON→GFM / JSON→HTML5 | 14 / 7 / 12 | 14 / 7 / 12 |

49 Deno исполняют `/opt/quarto/bin/quarto.js`, 18 — конкретные TS entrypoints. Конверсии — ещё 33 пары CLI Deno→Pandoc, не ещё 33 документа. Все 33 пары находятся под export process `67de6db9…`; render `727144a7…` также его ребёнок. У 106 процессов parent link confirmed, у корневого `76aa1a15…` — unlinked. 52 листа дерева: 33 conversion Pandoc и 19 Deno. Ancestry доказывает цепочку `76aa1a15… → 67de6db9… → 727144a7… → 76c430e3… Pandoc → fbcf83e9… Quarto run early → 4589d200… actual early`.

**Что время позволяет утверждать.** Ни один process receipt не содержит start/end/duration; started markers также без времени. Lifetime/serial cost/startup для всех категорий выше неизвестны. Wall trace содержит semantic spans: например actual early-filter `4589d200…`, index.qmd, `normalizeDocument` **0.013920 s**; по семи документам этот scope 0.013784–0.050486 s, inclusive total 0.189977 s. Это длительность normalization scope, не всего early process. Ancestry не связывает каждый spawn с command span; command attrs не содержат argv. Нельзя надёжно назначить 12 `export.command` spans конкретным 33 conversions.

TS clocks локальные monotonic, epoch calibration с локальной uncertainty около 1 ms и неограниченным системным дрейфом; Lua — отдельный process CPU. Causal spans дают вложенность, wall trace — частичные интервалы, но полных process lifetimes/полного покрытия нет. Полный critical path, exclusive startup и unwrapped gap **не вычисляются**. Inclusive spans не суммировались и не вычитались из native **41.972 s**. Host total **44.816 s**, prep **0.368 s**, combined post-native **2.245 s** — отдельные уже измеренные границы; CPU/RSS и self time null. Core `assertNativeAssignmentCapture`, state read/decode и остальные незавёрнутые функции измеренными scopes не являются.

**Гипотезы и один следующий замер.** 49 CLI Deno и 33 conversions делают запуск CLI/конверсии разумным кандидатом; неизвестные state decode/validation также остаются кандидатами. Counts не доказывают их стоимость. Самый малый полезный следующий capture: один выбранный документ в actual native pipeline через существующий Performance **cpu** mode, cap ≤120 s, retry 0, без full Java. Цель — получить новые V8 samples actual early-filter и CLI, с поиском sampled JS initialization, JSON/schema decode/validation и подготовки конверсий; не повторить passed summary. Ожидаемые existing fields: `profiles/<process_id>/cpu.cpuprofile` с `nodes/callFrame`, `samples`, `timeDeltas`, `startTime/endTime`, process_id→argv/parent linkage, bounded command wall и data quality. CPU profile — samples, не точные call counts и не wall lifetime; native startup и ожидание I/O сами по себе из него не следуют; отсутствующие profile/native frames остаются unknown. Такой замер выберет следующий конкретный CPU hotspot, но сам не закроет process wall/startup/gap без launch/exit clocks; новых framework/validators не требуется. Сейчас замер не запускался.

Допуск исполнителя: own chat `01a1a2cc-abe0-7003-910d-72e9d3fc022d`, agent `/root/performance_process_reading`, исходный turn `01a1a2cc-ac0e-7990-adb9-6d79b4cd6fee`. Собственные session_meta/весь текущий interval turn_context в `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T22-04-44-01a1a2cc-abe0-7003-910d-72e9d3fc022d.jsonl`: ожидаемые и зарегистрированные gpt-6.1-sol/medium совпадают с nested collaboration settings. Standard — назначение; скрытая routing не проверяема, globals не менялись.
