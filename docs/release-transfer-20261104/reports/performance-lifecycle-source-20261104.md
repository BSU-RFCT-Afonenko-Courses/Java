# Performance lifecycle: Source candidate, runtime NOT QUALIFIED

> Root final Source update: independent **APPROVE_SOURCE**, affected compiler **PASS36roots**, r3 1.617579024s; total compiler4.514768394s/15. Exact local commit **9ba1fd87eb8f5da15fdad29c6c74bc0030b96e92**, clean isolated worktree. Original initial/r2 failures/evidence preserved. **Runtime/TERM NOT QUALIFIED**, new tests/signals/Native0; no push/install/production. Earlier author statements below retain their original time scope.

**Исправленная production ревизия готова к delta review; combined compiler PENDING. Initial affected compiler PASS относится только к первой ревизии.** TERM/runtime recovery не квалифицирована, новый signal/runner budget не появился. Старый frozen candidate с6dirty files в `performance` сохранён без изменений.

Рабочая копия: `/home/tolya/JavaCodex/java-release-20261110/performance-lifecycle`, ветка `codex/performance-lifecycle-20261110`, exact base `c2dcf002a7396aa3b507e49b99e0ecf00ed9f438`. До начала правок `git status --short` пуст. Реализация по [brief](../briefs/performance-lifecycle-source-20261104.md), [принятому design](performance-lifecycle-clean-contract-20261104.md) и [независимой critique](performance-lifecycle-clean-contract-review-20261104.md). [Exact patch](performance-lifecycle-source-20261104.patch).

## Новый договор

Отдельный зеркальный `RunResult` удалён. Execute возвращает `{record, directory}`: record — ровно опубликованный `run.json`; completion обязателен. Native outcome имеет свой источник и measurement scope, wrapper command result хранится отдельно. Unknown Native — null; wrapper/escalation/timeout коды не становятся Native status. Direct status wall фиксируется на получении status, не после EOF. Helper envelope, Hyperfine и historical clocks подписаны раздельно; CPU/RSS вокруг helper не названы изолированным Native measurement.

В execute и render один синхронный terminal choice. Cancellation либо первая observation error выбирает incomplete до status/EOF/resource/normalize; поздняя normal ветка не вызывает writer и не перезаписывает partial. Complete выбирается только после необходимых наблюдений и normalization. Signal после уже выбранного complete меняет exit entrypoint, не completion. Initial not-finalized checkpoint пишется до spawn; initial и terminal writes последовательны, cancellation во время initial запрещает spawn. Partial не ожидает teardown; pump остаётся владельцем своего handle.

Writer сначала атомарно заменяет run.json через same-directory temp/rename, затем строит derived artifacts. Он не меняет переданный record скрыто. `PublicationError` содержит phase, runId/path, attempted record и последнее подтверждение публикации, когда оно есть. Initial write failure запрещает spawn. Отказ final record не возвращает desired complete как success; derived/storage failure сохраняет подтверждённый record и возвращает отказ. Storage копирует тот же record без смены содержащихся в нём outcome/path fields. Существующая cleanup ошибка не отменяет подтверждённую final copy.

Existing process group/PTY machinery не переносилась в execute и не заменялась новым supervisor. Process boundary теперь отдаёт actual observation/provenance и ранний first error. Прежняя синтетическая137/SIGKILL при escalation больше не повышается до Native outcome без фактического native receipt/started evidence. Существующий helper status read разобран как unknown на consumed полях; Hyperfine остаётся отдельным существующим measurement boundary.

Incomplete всегда INVALID/coveragefalse. Per-run summary помечает отсутствие normalization как not-processed. Aggregate refresh сначала читает completion и не нормализует incomplete raw ради повышения качества. Builds rows, per-run/aggregate quality и returned validity используют тот же record. Benchmark/history отвергают incomplete до comparison/следующего sample. Старые артефакты не переписаны; existing explicit historical adapter маркирует исходные declared outcomes как historical-record, не как новое живое measurement.

## Source closure

Initial revision меняла15 production/tool files; correction добавляет ещё два direct tools (итого17):

- `src/report/types.ts`, `src/report/run.ts`, `src/config/types.ts` — shared contracts, consumed status read, publication/error result.
- `src/runner/execute.ts`, `process.ts`, `render.ts`, `storage.ts`, `benchmark.ts` — lifecycle, provenance, publication и completion branches.
- `src/analysis/historical.ts`, `src/analysis/report.ts` — incomplete refusal и согласованные refresh/aggregate результаты.
- `src/cli/experiment.ts`, `tools/scaling.ts`, `tools/repeat-java.ts`, `tools/check-java-general-use.ts`, `tools/check-installed.ts`, `tools/validate-java.ts`, `tools/recheck-repeat.ts` — actual callers, включая generated installed probe. Одна explicit cancellation lifetime принадлежит whole run/series; execute/process каждый владеет forwarding только своему child/group.

Дополнительно адаптирован существующий `tests/runner/execute.test.ts`: прежние binary/argv/exit/Hyperfine/signal assertions читают новый published/native result. Existing binary gate также проверяет ранний not-finalized INVALID record и буквальное совпадение final JSON с returned record. Control **не исполнялся**; новый TERM control из failed fixture не переносился.

## Реальная проверка

```text
/usr/bin/deno check --cached-only src/report/types.ts src/report/run.ts src/config/types.ts src/runner/execute.ts src/runner/process.ts src/runner/render.ts src/runner/storage.ts src/runner/benchmark.ts src/analysis/historical.ts src/analysis/report.ts src/cli/experiment.ts tools/scaling.ts tools/repeat-java.ts tools/check-java-general-use.ts tools/check-installed.ts tests/runner/execute.test.ts
```

Actual cwd — новый checkout; native `deno.json` autodiscovery, DENO_DIR unset, без manual node_modules/config/runtime overrides. **PASS**, exit0, elapsed **1.229189803s**, cap15s, retry0. [Полная команда/result](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-lifecycle-source-20261104-evidence/compiler.json), [полный log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-lifecycle-source-20261104-evidence/compiler.log). Это16 roots, а не исполнение generated probe string или installed qualification. Initial Source был заморожен после этого PASS; затем внесены нижеописанные review corrections. Сохранён [проверенный initial patch](performance-lifecycle-source-20261104.initial.patch). Новая ревизия compiler не запускала; initial1.229189803s учитывается в общем cap15s, remaining13.770810197s, решение о changed-graph check принадлежит root. Formatter и scoped `git diff --check` после corrections PASS.

## Пределы приёмки

Runner/tests/signals/Native/Quarto/render/inspect/hash/build/CI/network/install — **0**. Commit/stage/push/mainstate/audit отсутствуют; делегирования не было. Собственный Sol6.1/high origin/admission остаётся прежним: [existing reference](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-late-cleanup-reuse-20261104.md), globals не менялись.

Нужен delta Source review исправленной production ревизии и общий changed-graph compiler после отдельного freeze medium fixtures по решению root; затем отдельно разрешённая bounded behavioral qualification. Ни своевременность partial при удержанном pipe, ни delivery TERM, ни failure/initial-write race сейчас не доказаны исполнением. Отсутствует обещание descendant termination через direct-child kill, fsync/SIGKILL recovery, атомарности всего набора JSON/report либо прежнего host-hang объяснения. Disk failure может оставить только initial checkpoint; successful native code сам по себе не означает successful publication. Старые full suites/fixture shapes вне адресного execute control не мигрировали и не проверялись этой единственной compiler-командой.


## Production correction по независимому Source review

[Review](performance-lifecycle-source-review-20261104.md) дал NEEDS_CHANGES_SOURCE по двум P1 и одному P2. Внесены только разрешённые production corrections; medium owner отдельно мигрирует existing tests/fixtures, я их не редактирую.

- `tools/validate-java.ts` удаляет собственный старый Build DTO. Внешний JSON проходит actual `validateRunRecord`; successful selection требует complete + actual native code0/signalnull/dataValid, mode/cwd берутся из canonical request. К record прикрепляется только acknowledgement реального каталога run и actual source manifest. `java-pl-export` outputs ищутся относительно этого directory; отсутствие старого buildExitCode больше не является success.
- `tools/recheck-repeat.ts` читает actual RunRecord и отвергает incomplete/nonzero/signalled/invalid до сравнения. Проверяются canonical request mode/cwd и ожидаемый scenario, без legacy aliases.
- `render → finalizeRun(paths, record, normalized?, previous?:PublishedRun) → writeRunArtifacts` передаёт подтверждённый initial checkpoint. Storage инициализирует им last-confirmed, сохраняет при target-validation/record failure, заменяет только после подтверждённой stage publication, возвращает final acknowledgement лишь после successful copy. Intended complete не подменяет published success.
- Process pumps теперь сообщают отдельные actual open/EOF/failed facts сразу; failed sticky для своего log channel, чужой поздний supervisor failure не переоткрывает уже закрытый stream. Общий streamsFinished остаётся только существующим условием group cleanup, не источником stream facts.
- Канонический `stdioMode` находится только на ProcessObservation/RunRecord: `separate | pty-combined | null`. Nested Native mode удалён. До actual channel acquisition и в historical data без evidence — null, без придуманного separate. Historical raw inputs не переписываются; known old mode переносится в новый record явно, остальные unknown остаются null. Mixed PTY mapping соответствует прежним actual log destinations; combined stdout — mixed Native terminal channel, stderr — supervisor channel, не независимый Native stderr EOF. Report показывает capture mode и log facts даже при native:null.

Текущий `.patch` содержит **production/tools-only** correction state относительно exactc2; initial compiled patch сохранён отдельно. Combined test/fixture diff принадлежит medium owner и root. API handoff передан medium: новый optional fourth previous argument finalizeRun; required nullable stdioMode на current RunRecord/ProcessObservation; nested native mode больше нет. Compiler/tests/runtime/signals/runner/Native/network0 после review. Original frozen6, commits/state/pins не менялись.
