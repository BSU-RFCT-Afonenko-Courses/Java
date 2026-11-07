# CI ee3aa08: устаревший whole-operation digest count

3 ноября 2026. Exact Core `ee3aa0897fefadff2543528e45032231b90c13c4`, [run41695552507/job126124992977](https://github.com/Afonenko-Course-Tools/quarto-course/actions/runs/41695552507/job/126124992977), draft [PR70](https://github.com/Afonenko-Course-Tools/quarto-course/pull/70). Existing hosted log получен primary GitHub `fetch_workflow_job_logs`, полностью сохранён в [core-ci-ee-job-126124992977.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-ci-ee-job-126124992977.log). Новый hosted rerun/push не выполнялся.

## Фактический first cause

Hosted canonical **--quick прошёл полностью**:61 top-level checks, включая прежние5 addressed tests. Early wrapper затем успешно выполнил `native-format-scope-a4c` и `native-metadata-a4c`. Первый отказ — `native-capture-scope-a4c.ts:133`, в `2026-11-03T18:43:22Z`:

`Error: scoped pending uses local source/runtime guards`.

Это joint assertion `op.metrics.fullSourceFences === 3 && op.metrics.runtimeHashReads === 5`. После независимо принятого d7 perf diff из seal entry убран ровно один повторный full runtime digest; closing full guard сохранён. Число5 стало устаревшим whole-operation implementation count. В hosted failure actual counters не выводились до assertion; первоначальное объяснение4 вместо5 было помечено как trace inference, не measurement. Ни TypeError старого API, ни missing import map, ни новый product refusal в этом месте не наблюдались. Evidence [artifact13260628382](https://github.com/Afonenko-Course-Tools/quarto-course/actions/runs/41695552507/artifacts/13260628382) успешно сохранён hosted job.

Root получил факт и минимальный вариант до изменения/запуска. Подмена5 на4 ради green не предлагалась: эти итоговые числа всего capture включают внутренний entry/closing порядок, а строка не проверяет сама по себе, что pending применяет meaningful local guards.

## Единственный test-only diff

Удалены только4строки brittle whole-operation count assertion в `tests/native-capture-scope-a4c.ts`. Полезные проверки остались: reservation ещё не даёт pending descriptor, каждый actual child успешен, все expected inputs связаны один раз, pending/current принадлежит одной generation, implicit profiles настоящие, coverage объединяет оба текущих captures/formats, inspect/snapshot не повторены, `validateRelease` принимает actual documents. Actual metrics и proof по-прежнему выводятся в test log.

Product Source/guards, public API/wire, workflows/canonical policy, fixture helpers и actual installed courses не менялись. Новый unit seam/receipt/profile/framework не создан. Closing/output/lifetime integrity не заменяется счётчиком: для неё отдельно исполнены уже существующие ещё не достигнутые hosted negative suites в согласованном batch ниже.

## Адресный same-pattern audit и непройденный tail

Read-only `rg` по active tests на `fullSourceFences`, `localSourceFences`, `runtimeHashReads/Bytes`, `sourceDirectoryReads` обнаружил единственную literal совместную expectation этих whole-operation counters — удалённую строку. Остальные вхождения — data-only fixture counters/diagnostic output, а не такое expectation. Существующие meaningful source-row/inspect/coverage и resource-cache assertions не трогались.

`pending-a4a` имеет отдельное exact whole-call IO expectation двух digests. Оно относится к ordinary **unreserved** pending entry/exit до native render completion, не к удалённому seal-entry digest. Оно сохранено и затем фактически прошло в batch. Этот похожий текст не был объявлен устаревшим автоматически.

После hosted first failure оставались22 early scripts и `navigation-model`; уже прошедшие61canonical и первые2early не повторялись. Root согласовал один subset8: изменённый scope и семь ещё не исполненных relevant capture/pending suites. Они связаны с тем же capture/pending API, closing/output/source/lifetime fences и имеют собственные маленькие fixtures. Root budget: общий240s, TERM grace5s, retry0, first-failure stop; не весь CI/early wrapper и не новый test framework.

## Один разрешённый tail batch

|Test|Исход|Время|
|---|---|---:|
|native-capture-scope-a4c|PASS, actual2children/HTML+Reveal|7.285s|
|native-capture-fences-a4c|PASS, actual Source/output/collision/binding failure controls|52.145s|
|native-capture-transitions-a4c|PASS, phase/failure/transition controls|23.630s|
|native-capture-currentness-a4c|PASS, current generation/coverage/resources controls|32.501s|
|native-capture-expiry-a4c|PASS, callback after scope forbidden|0.440s|
|native-binding-fences-a4c|PASS, actual binding mutation negatives|29.389s|
|native-operation-pointer-race-a4c|PASS, newer active pointer preserved|2.129s|
|pending-a4a|PASS, ordinary pending IO/metadata/adapter/lifetime/final fences|15.152s|

**8/8 PASS**, wall162.676s (<240), timeout0, retry0. Commands/exit codes/per-test times: [core-ci-ee-tail.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-ci-ee-tail.json); aggregate output [core-ci-ee-tail.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-ci-ee-tail.log) и восемь `core-ci-ee-tail-<test>.log`. Согласованный stop-on-first-failure не потребовался, расширения batch после запуска не было.

Изменённый scope test фактически напечатал внутри current callback `inspectCalls1`, `fullWalks1`, `fullSourceFences3`, `runtimeHashReads4`, `localSourceFences11`, `childRenders2`. Это подтверждает точную причину прежнего assertion failure; новый контракт на эти числа не введён. Это также не новые SP performance/capture counters и не обещание ускорения курса. Batch использовал existing actual Quarto/native fixtures на собственных временных owners; записи root/выводов сохранены. Это root-разрешённый новый integrity вопрос, а не повтор ранее PASS на unchanged Source.

Остальные15early scripts не запускались здесь: `native-metadata-producer-a4c`, `native-sp-index-a4c`, `answers-a4b`, `metadata-a4b`, `answers-native-a4b`, `native-formats-a4b`, `release-semantics-a4a`, `release-collection-a4a`, `release-project-source-a4a`, `release-a4a`, `materialization-a4a`, `native-full-a4a`, `generated-condition-a4a`, `generated-policy-a4a`, `early-layout-a4a`. `navigation-model` тоже не запускался. Их PASS и полный hosted green не приписываются этому пакету; прежняя workflow политика сохранена для следующего единственного штатного CI, который назначает root после review.

## Передача и пределы

Новых типов/fixtures/API нет — production/полный compiler graph не повторялся ради удаления assertion. `git diff --check` PASS. Предыдущие4+1 addressed greens и61canonical не воспроизводились локально. Course installs на Core d7/PLda, Java actual owner, новый Performance owner/root measurement и PL Source writer не затронуты; root SP/Java pipeline в этой фазе не запускался.

Полный собственный primary interval проверен от original `01a1a21a-f52f-73a3-85e9-758306144504`:13records/12unique turns; current `01a1a2bc-b081-79c2-a425-181879585b03`, строка2047. Везде top+nested `gpt-6.1-sol/high`; Standard назначен root, service_tier absent и отдельно не утверждается. Globals/normative untouched.

Source commit `2e71100f773c8185c7f125c44167b1555ec669f5` поверх `ee3aa0897fefadff2543528e45032231b90c13c4`: один `tests/native-capture-scope-a4c.ts`,−4строки. Source после commit clean, staged diff check PASS. Exact independent review range — `ee3aa0897fefadff2543528e45032231b90c13c4..2e71100f773c8185c7f125c44167b1555ec669f5`; read-only high review обязателен до root push. Ни ручного CI rerun, ни самостоятельного push/release/production здесь нет.
