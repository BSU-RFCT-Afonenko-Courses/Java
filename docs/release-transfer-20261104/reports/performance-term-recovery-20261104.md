# Performance partial TERM report: frozen candidate, NOT QUALIFIED

**Runtime recovery не подтверждена. Candidate заморожен по указанию root после последнего отказа.** Compiler PASS; новых Java/SP Native/Quarto запусков не было. Commit/push/state/pins не менялись.

Base `c2dcf002a7396aa3b507e49b99e0ecf00ed9f438`. [Exact patch](performance-term-recovery-20261104.patch) затрагивает4 production files и2 existing controls:

- `src/runner/execute.ts`: TERM/INT listener lifetime вокруг существующих child-status/двух EOF/report promises; первый сигнал запоминается/forwarded direct child; обычный completed RunResult остаётся прежним. При interrupted finalization задуман partial report, затем `ScenarioInterruptedError`, без возвращения выдуманного completed результата.
- `src/report/types.ts`: отдельный `IncompleteOutcome` (`completion: incomplete`) с nullable actual native code/wall, отдельными requested interruptionSignal и observed runner wall. UNKNOWN не заполняется124/143; при null code все actual signal/wall/resources должны быть null. Completed BuildOutcome/API сохранены.
- `src/report/run.ts`: UNKNOWN текст и устойчивый INVALID/coveragefalse для incomplete даже при refresh. `src/analysis/historical.ts`: явный refusal incomplete до materialization/comparison.
- Existing `tests/runner/execute.test.ts` расширен callerTERM control; existing historical guards — incomplete refusal. Широкого consumer rewrite/нового framework/supervisor/polling или production status probes нет.

## Actual verification, бюджеты и evidence

[Evidence directory](performance-term-recovery-20261104-evidence/) содержит exact commands/exit/elapsed и raw logs каждого действия. Runtime budget15s, compiler budget10s, unchanged retry0.

| Действие | Фактический результат | Elapsed |
| --- | --- | ---: |
| RED до production edits: caller TERM | Expected FAIL: partial run.json отсутствует |0.609701408s |
| Первый GREEN:3 affected execute controls |2 existing completed/API и child-signal controls PASS; new caller control FAIL code0 |1.514967564s |
| Changed held-child control | Outer4s +1s grace, exit-9; direct false step завис |5.005144502s |
| Последний согласованный changed control | FAIL; direct step не завершился, test watchdog завершил caller |1.616981664s |
| **Runtime total** |**NOT QUALIFIED** |**8.746795138s /15s** |
| Affected compiler, frozen Source |**PASS**,6 roots |**0.927376827s /10s** |

Runtime checks использовали `/usr/bin/deno test --cached-only --no-check -A` с адресными filters. Compiler: `/usr/bin/deno check --cached-only src/runner/execute.ts src/report/types.ts src/report/run.ts src/analysis/historical.ts tests/runner/execute.test.ts tests/analysis/historical-import.test.ts`. Полный argv с outer caps находится в JSON. `git diff --check` PASS. Полные suites/матрицы не запускались по ограниченному scope.

Historical runtime control **NOT RUN**: последовательный runner остановился на execute refusal. Его Source-компиляция прошла, behavioral refusal не квалифицирован.

## Что исправлялось в control и что осталось неизвестным

Первый control позволял fake child естественно завершиться через350ms после ready. Readiness не гарантировала живой процесс в момент доставки TERM; code0 не доказывал product bug. Следующая fixture удерживала child, но controller ожидал caller.status до fallback cleanup — Source review выявил leak/circular-wait путь.

Последний согласованный control моделирует outer group TERM двумя адресными сигналами caller и known-owned fixture native, без новых groups/supervisor. Fake до ready устанавливает явный TERM handler → **numeric143**, поэтому actual native signal в ожиданиях остаётся **null**, requested TERM хранится отдельно. Missing-status step намеренно блокирует реальный helper status publish каталогом `tool-status.json`, без подмены callback/статуса. В fixture добавлены bounded wait и cleanup собственных handles/PID, сохранение stdout/stderr/status/artifacts.

Однако последний запуск остановился уже на первом **false/direct** step. Сохранённый [caller-status.json](performance-term-recovery-20261104-evidence/final-caller-control/false/caller-status.json) показывает **137/SIGKILL от test watchdog cleanup**, caller stdout/stderr пусты, final run.json отсутствует. Это не наблюдённый native status и не partial-recovery PASS. Missing-status step не достигнут. Почему caller не завершился после TERM в этой fixture, по имеющимся files/Source однозначно не установлено; forwarding отдельно **UNKNOWN** и не квалифицируется.

Верхний test finally затем попытался `Deno.rename` из `/tmp` в reports и получил **EXDEV**. Это ошибка test-owned evidence preservation, которая маскировала исходный timeout; она не превращает underlying control в GREEN. Оставшийся temp fixture был **скопирован** в evidence ordinary filesystem copy без replay. Предыдущий timeout fixture также сохранён отдельно. Этот известный дефект теста оставлен видимым в frozen diff; новых Source fixes/runs после freeze не делалось.

## Граница приёмки

Patch передан root как **неквалифицированный candidate для Source решения**, не готовое исправление TERM recovery. Listener не может гарантировать EOF при продолжающемся pipe holder, остановку всех descendants через direct-child kill или recovery после SIGKILL. Новый incomplete report contract также требует независимого review; наличие TS PASS не подтверждает нужное runtime поведение.

Все RED/неудачные GREEN/timeout/EXDEV сохранены, budget не расширялся. Новых signals/контрольных процессов/Native попыток после последнего отказа нет. Действующий high admission — [существующий reference](core-late-cleanup-reuse-20261104.md#собственный-допуск); роль/model/effort/globals не менялись.
