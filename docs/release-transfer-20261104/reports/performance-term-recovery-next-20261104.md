# Performance: prefix сохранён, final artifacts потеряны при outer TERM

## Причина и что действительно известно

В [последней сохранённой попытке](java-native-filter-summary-20261104.md) stdout0B/stderr35189B доступны, но run.json/summary.json/report.md/tool-status.json/resources.txt/capture-result.json отсутствуют. Outer Python сохранил exit124 за114.002727167s. Это **разные владельцы и разные моменты записи**, а не противоречие streaming logs.

У `executeScenario` нет собственного TERM/INT lifecycle: оно может завершиться обычным действием сигнала во время ожидания child. Итоговые artifacts пишутся только после child status, EOF обоих streams, чтения ресурсов и normalize. Поэтому потоковые bytes уже на диске, а завершающий код ещё не выполнился. **Одного `finally` без signal listener недостаточно**: обычное завершение процесса сигналом не обязано выполнять async finally/catch.

Saved data не содержит timestamps доставки TERM каждому PID или последнего JS instruction. Нельзя доказать точный порядок смерти Python/Deno/time/helper/Quarto либо утверждать, что отсутствующий файл означает отказ конкретного `writeTextFile`.

## Причинная цепочка с Source refs

1. [Report-local Python:106](java-native-filter-summary-20261104.py:106) запускает GNU `timeout --signal=TERM --kill-after=5s … python … --inside`, без `--foreground`. Installed primary `/usr/share/info/coreutils.info.gz`, раздел timeout, описывает отдельную background program group и отличие foreground, в котором descendants не timed out. Это объясняет ожидаемую group-доставку; actual PID/PGID tree в saved data отсутствует.
2. **Outer Python** находится над timeout, ждёт его и пишет outer-result.json ([строки113–119](java-native-filter-summary-20261104.py:113)). Поэтому этот независимый receipt сохранился. **Inside Python** ждёт Deno `subprocess.run` ([строка91](java-native-filter-summary-20261104.py:91)), не устанавливает TERM handler; его `except Exception` не является signal recovery.
3. **TS driver** вызывает `executeScenario` и только после return пишет capture-result.json. Его catch пишет execution-error.json лишь при обычном JS rejection; оба файла отсутствуют. Driver не устанавливает TERM listener и не является владельцем recovery.
4. [execute.ts:172–190](../performance/src/runner/execute.ts:172) spawn → одновременно `child.status` и две независимые file pumps. [streamLog:24](../performance/src/runner/execute.ts:24) сразу открывает/пишет chunk bytes, включая partial-write loop. Для сохранения уже записанного prefix не нужны status/EOF/normalize/report. Это не обещание fsync/последнего буфера.
5. После status+EOF код [execute.ts:193](../performance/src/runner/execute.ts:193) сначала выбрасывает pump/status error. Для requested CPU/RSS при доступном `/usr/bin/time` путь проходит через GNU time → [resource-child.ts](../performance/src/runner/resource-child.ts:31) → scenario Quarto. Сам helper уже слушает TERM/INT и передаёт child; tool-status публикуется **после `await child.status`**, атомарным temp+rename. Назначенные options требуют CPU/RSS, но отдельного actual selected-wrapper receipt до spawn этот API не сохраняет; исчезнувшие resources/status не доказывают конкретное место остановки helper.
6. [execute.ts:226](../performance/src/runner/execute.ts:226) **безусловно читает tool-status/resources**, затем normalize, затем [writeRunArtifacts](../performance/src/runner/execute.ts:258). Это второй Source gap: даже сохранённый живой caller при missing status получит rejection до final report. Сигнал listener сам по себе не устраняет этот барьер.
7. [report/run.ts:337](../performance/src/report/run.ts:337) пишет run.json, затем summary.json, затем report.md. Они не являются pre-spawn checkpoints. Здесь нет независимого завершителя после смерти caller.

`renderCommand` ([render.ts:131](../performance/src/runner/render.ts:131)) уже удерживает TERM/INT до artifacts и после re-raises сигнал. Но этот saved driver вызывает **executeScenario напрямую**, не renderCommand. Перевод на `runOwnedProcess` также не является маленьким синонимом: там group ownership, PTY/polling/status machinery и другие semantics. Подменять этим API нынешний streaming runner в данном diff не предлагается.

## Почему часть child receipts закрыта, а parent pending

[Saved receipts](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/java-native-filter-summary-20261104-actual/saved-evidence.json):15 процессов,9 exit0,1 Pandoc exit143/SIGTERM,5 pending. Закрытый третий Pandoc `9ff41d98…` имеет wall2.085585818s; это **его status**, не статус outer Quarto/whole capture. Pending: early wrapper26fcc13d, direct early TS5d5956da, capture render2df7a37c, PL61dff649, outer Quarto7d3bb75e.

[tool.ts:101](../performance/src/launchers/tool.ts:101) пишет initial receipt **до spawn** с pid/status/wall null. После spawn pid присваивается только in-memory, а следующая запись — после child.status и поиска started summaries ([строки143–171](../performance/src/launchers/tool.ts:143)). Поэтому pending pidnull **не означает, что child не запускался**: nested receipts/started files могут уже подтвердить descendants. Pending нельзя закрыть кодом дочернего процесса либо124 от timeout.

Launcher слушает TERM/INT, ждёт status, пишет final receipt и лишь затем re-signals себя. Но listeners удаляются **до** async started-read/final write ([tool.ts:145](../performance/src/launchers/tool.ts:145)): повторная group/forwarded доставка в этом окне — отдельный Source risk. Она не доказана причиной данных пяти pending receipts; не следует включать её исправление в первый recovery diff без расширения scope. TS agent `bootstrap.ts:23` также может flush executor summary и re-signal себя: executor summary — не parent status/final RunRecord.

## Минимальный полезный следующий diff и предел

**Рекомендуемый первый bounded candidate — один production file `runner/execute.ts`:**

- Существующий execution lifetime получает idempotent TERM/INT listeners перед spawn/await; первый внешний сигнал запоминается, передаётся существующему child, caller удерживается до уже имеющихся status+двух EOF promises и finalization. Никаких новых process groups, supervisor, polling, kill(0)/`/proc`/status checks.
- Listener lifetime охватывает resource read/normalize/writeRunArtifacts; remove в finally. Завершение/возврат после signal должно сохранять observed signal, а не выдавать обычный успешный run. Не вызывать `Deno.kill` всего caller из библиотечной функции до сохранения artifacts; способ re-raise остаётся ответственностью entrypoint, как в renderCommand.
- Ошибки pumps/resource receipt/normalize не должны мгновенно стирать уже полученный **actual** outcome: удержать first error, выполнить доступную finalization с dataValidfalse/quality error, затем передать исходный отказ вызывающему. Существующие actual reads выполняются один раз, отсутствующие bytes/status не додумываются.

Этот минимальный compatible slice устраняет явную преждевременную смерть caller, **но обещает final RunRecord только при доступном действительном tool outcome**. Он не гарантирует tool-status при group TERM, EOF при живом descendant или завершение в оставшиеся5s; SIGKILL recovery этим способом невозможно.

**Полная честная fallback finalization при missing native status требует отдельного узкого contract решения.** Нынешние `BuildOutcome.code`/`RunResult.buildExitCode` обязательные numbers; [validateRunRecord:198](../performance/src/report/types.ts:198) требует integer, report называет его «Код Quarto». Нельзя заполнить143/124 или status `/usr/bin/time` и назвать это наблюдённым Quarto status. Нельзя синтезировать tool-status.json от имени умершего helper.

Если root требует final run.json/summary/report и в этой ветке, следующий bounded план должен явно разрешить **interrupted/incomplete RunRecord с UNKNOWN native outcome**: narrow report typing/validator + rendering неизвестного результата, и execute finalizer, без изменения event ABI/нового validator framework. Completed outcome/RunResult остаются прежними; при unknown outcome после partial artifacts — исходный отказ, не выдуманный успешный RunResult. Historical consumer обязательного native code тоже должен явно отказаться от такого incomplete outcome либо поддержать его; это конкретный compatibility seam, не повод расширять весь аудит. Пока это **план, не внесённый контракт**.

Для будущего candidate адресный existing execute control надо расширить проверкой завершения **самого caller** при внешнем TERM и сохранения raw/report/честного статуса; нынешний `resource measurement retains the tool signal…` проверяет самосигнал child, оставляя executeScenario живым, и эту дыру не покрывает. Сейчас signals/processes/tests не запускались. Нельзя объявлять recovery исправленным по одному Source diff или прежнему streaming GREEN.

## Область работы

Только чтение existing saved evidence/current Source/installed timeout docs, запись этого отчёта. Product edits, новые signals/Native/process reproductions, status probes, tests, compiler, network/install —0. Действующий own high admission остаётся прежним: [предыдущий admission reference](core-late-cleanup-reuse-20261104.md#собственный-допуск); роль/model/effort/globals не менялись.
