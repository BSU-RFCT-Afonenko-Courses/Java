# Performance lifecycle: миграция существующих TS fixtures

> Root final Source update: independent **APPROVE_SOURCE**, affected compiler **PASS36roots**, r3 1.617579024s; total compiler4.514768394s/15. Exact local commit **9ba1fd87eb8f5da15fdad29c6c74bc0030b96e92**, clean isolated worktree. Original initial/r2 failures/evidence preserved. **Runtime/TERM NOT QUALIFIED**, new tests/signals/Native0; no push/install/production. Earlier author statements below retain their original time scope.

**SOURCE FROZEN, independent root review и общий affected compiler PENDING. Ни runtime, ни signal qualification не проводились.**

Рабочая копия: `/home/tolya/JavaCodex/java-release-20261110/performance-lifecycle`, исходная база `c2dcf002a7396aa3b507e49b99e0ecf00ed9f438`. Это отдельная test-only часть общего lifecycle candidate. Production/tools/execute.test.ts принадлежат другому исполнителю и не входят в [patch](performance-lifecycle-fixtures-source-20261104.patch). Original Performance6dirty не изменялся.

## Что согласовано с actual API

Current fixtures строят настоящий RunRecord с completion, command, streams, обязательным nullable stdioMode и observedWallSeconds; RunSummary содержит completion/normalization. Из результата process/render читается ProcessObservation.native, из execute/storage — PublishedRun.directory и record. Удалённый outputDirectory больше не подставляется в current record. Действующий ProjectTarget.outputDirectory сохранён. Storage controls используют actual PublicationError и последнее подтверждение публикации, а не поиск staging в старом тексте ошибки.

Native unknown сохраняется как null: pre-abort, остановленный до Native bootstrap и stubborn cancellation без final receipt. Wrapper status и escalation не превращаются в Native137/SIGKILL. Direct process error после действительного receipt по-прежнему может содержать реальный Native code; render при первом pump failure фиксирует более ранний incomplete snapshot и не заимствует поздний receipt.

Generated OS-signal drivers передают abortSignal через existing withScenarioSignals и сохраняют observation внутри callback до re-raise. PTY Ctrl-C driver тоже использует этот entrypoint lifetime; отменённый success assertion удалён без подстановки угаданного OS/script code. Проверки signal evidence, heartbeat и завершения bounded driver сохранены. Это Source adaptation; ни generated code, ни его races исполнением не квалифицированы.

Raw historical JSON, captures, buildExitCode и importProvenance.sourceStatus.buildExitCode не переписывались. Менялись только assertions над новым importer result; historical-record/historical provenance проверяется явно. Новых aliases/adapters, casts, any, runtime guards или test families не добавлено; прежние unrelated casts в fault injection не расширялись.

## Storage/CLI: точная семантическая дельта

- Complete off/argv/log/report/provenance controls сохраняют реальные Native0/7. При отказе publication CLI возвращает2 и сообщает last confirmed staging; это не старый native-exit-priority contract. При ошибке только diagnostic stderr после successful publication Native code сохраняется, отдельный performance-error.json больше не ожидается.
- Обычный внешний TERM/INT с существующим shell-ready сохраняет incomplete/INVALID staging, requestedSignal и unknown Native, затем повторяет исходный сигнал. Partial не обещает final directory, EOF или завершённый teardown. Проверки available stdout/report сохранены, без новых sleeps/watchdogs/Native probes.
- Existing mixed TTY rejected-sink control остаётся: incomplete observation-error/native:null, доступный payload и report в staging. Удалены его old `for (const unlink of [false,true])`, generated Deno.remove override для `.pipe` и assertion наличия/отсутствия `.pipe` после CLI return. Это дополнительная отменённая ветвь в сохранённом test: early partial publication происходит до process finally/unlink и не обещает teardown completion. Блокировка final parent по-прежнему не мешает staged partial.
- С разрешения root полностью удалены ровно два obsolete controls: `sink failure and failed finalization preserve original external signals with staged report` (signal отправлялся после sink marker, когда новый lifecycle уже мог завершиться; без нового supervisor это race вне нового договора) и `diagnostic receipt does not follow an existing symlink after failed finalization` (producer performance-error.json отменён). Не использовались skip/новый supervisor. Полезные independent sink, ordinary external-signal и actual storage symlink controls сохранены.
- Existing CLI cleanup fault injection перенесён с отменённой записи `/run.json` на реальную atomic `run.<uuid>.tmp` запись, только initial not-finalized checkpoint; остальные записи используют тот же write. Проверки committed final, сохранённых raw bytes и вторичности cleanup failure остались.

## Независимый P2 review: cleanup ordinary signal fixture

После первого freeze reviewer выявил реальную fixture leak: early partial CLI re-raise не ждёт завершения Native group; existing finally убивал только уже завершившийся CLI. По отдельному разрешению root исправлен только ordinary TERM/INT control в storage-cli.test.ts. Existing stubborn trap оставлен: дополнительной смены поведения fixture не нужно.

Shell атомарно публикует `/proc/$$/stat` через ready.tmp → ready. Existing readiness loop читает PID до comm, PGID/session из suffix после последней `)`, проверяет positive safe PID/PGID >1, отличие Native PID от test/caller, PGID=session existing setsid boundary и обязательное PGID≠current test PGID из `/proc/self/stat`. Known owned group сохраняется до сигнала caller и любых output/stage assertions. Finally сначала отправляет SIGKILL только этой сохранённой group, даже если caller/report уже завершился или assertion отказал; отсутствие процесса принимается только через existing Deno.errors.NotFound. Прочие cleanup errors не глотаются, nested finally всё равно очищает caller и await output. Ни guessed/shared group, ни новый supervisor/watchdog/retry не добавлены.

Минимум согласован напрямую с independent `performance_lifecycle_review`; Source delta повторно заморожена. Это исправление владения самим test fixture, не runtime/signal qualification. После него compiler, tests, signals и другие проверки не запускались. Прежний diff --check относится к первому freeze, не выдаётся за проверку новой дельты.

## Scope и evidence

Изменены только следующие16 существующих test/helper файлов:

- `tests/report/fixture.ts`
- `tests/report/formats.test.ts`
- `tests/report/run.test.ts`
- `tests/analysis/report.test.ts`
- `tests/analysis/refreshed-report.test.ts`
- `tests/analysis/historical-import.test.ts`
- `tests/runner/process.test.ts`
- `tests/runner/render.test.ts`
- `tests/runner/launchers.test.ts`
- `tests/runner/proc-disappeared.test.ts`
- `tests/runner/storage-cli.test.ts`
- `tests/runner/storage.test.ts`
- `tests/runner/lazy.test.ts`
- `tests/runner/fixtures/proc-supervisor-failure.ts`
- `tests/runner/fixtures/orphan-stale-scan.ts`
- `tests/final-findings.test.ts`

`tests/runner/fixtures/proc-disappeared.ts` был прочитан как связанный affected producer; Source менять не потребовалось: он уже передаёт observation без старой аннотации. Для общей compiler composition этот generated fixture также нужен отдельным root (сам TS test ссылается на него через URL). Аналогично два изменённых generated runner fixtures выше нельзя считать покрытыми только проверкой внешнего process.test.ts. Shared fixture можно покрыть его importing roots. Root выбирает единый batch вместе со своим production/execute/tools графом.

Выполнен только `git diff --check -- <перечисленные16paths>`: exit0, без вывода. Compiler/tests/CLI/process/signal/Native/Quarto/render/network/install не запускались. Никакого PASS runtime или production acceptance не заявляется. Остаток общего compiler budget13.7708s не расходовался этим исполнителем. Stage/commit/push/mainstate/audit не менялись.

Основание: [actual lifecycle Source](performance-lifecycle-source-20261104.md), [независимый Source review, finding1](performance-lifecycle-source-review-20261104.md), actual production freeze high и точечные решения root по storage semantics/удалённым controls. Собственный исходный Sol6.1/medium admission сохраняется: chat `01a1a54c-ab39-7a83-808d-a190428ff628`, origin turn `01a1a54c-ab6a-7412-894c-270be9b9aac3`, [сохранённое evidence](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/download-owned-request-types-admission-20261104.json). Origin не сдвигался, globals не менялись; Standard назначен, hidden routing не удостоверяется.

## Root compiler r2: RED и точечная changed-revision поправка

Root выполнил combined affected compiler36roots: RED, один TS7022 в tests/runner/process.test.ts:868 (`const supervisorPid = supervisor.pid`, cyclic control-flow inference). Сохранены root [stderr](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-lifecycle-source-20261104-evidence/compiler-r2.stderr.log) и [JSON](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-lifecycle-source-20261104-evidence/compiler-r2.json). Это compiler failure, не runtime результат; прежний independent Source APPROVE не выдаётся за зелёную compilation.

По разрешению root изменена только эта строка: `const supervisorPid: number = supervisor.pid;`. Это явная numeric annotation настоящего Deno.ChildProcess.pid, без cast/stub/suppression или runtime изменения. Новая revision SOURCE FROZEN; собственный compiler/tests/runtime не запускались. Root координирует один changed-revision check; до него итоговый compiler остаётся RED/PENDING. Общий compiler расход со слов root2.897189370s/15s, остаток12.102810630s; budget не сбрасывался.
