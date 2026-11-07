# Performance: чистый договор process → observation → report

**Предложение, DESIGN ONLY.** Frozen TERM candidate остаётся NOT QUALIFIED. Новых запусков, edits candidate и пересчёта бюджета нет. Compatibility старого `RunResult` не является ограничением; предлагается убрать дублирование результата, а не расширять его ещё одним fallback.

## Доказанная проблема и предел знания

Accepted `c2dcf00` в `runner/execute.ts` одновременно пишет два binary log streams, но начинает report только после `Promise.allSettled(child.status, stdout EOF, stderr EOF)`, обязательного resource-status read и normalize. Prefix уже доступен независимо от этой полноты. В шести dirty файлах frozen candidate listener добавлен, однако partial catch расположен **после того же барьера**. Живой pipe holder или недоступный status может не дать дойти до catch. Это Source-доказательство недостаточности выбранного пути, не установленная причина конкретного host hang.

Сохранённый последний control остановился на direct case: watchdog завершил caller с137/SIGKILL, run.json отсутствует; missing-status case не достигнут. Затем test-owned cross-filesystem rename получил EXDEV и замаскировал первичный отказ. Почему именно caller не завершился, не установлено. Native143 дочернего Pandoc, wrapper status и outer timeout124 не заменяют actual status родительского Quarto.

## Один результат вместо двух конкурирующих представлений

Предлагаемый owned contract — **один `RunRecord`**, который сохраняет report writer и возвращает `executeScenario` внутри подтверждения публикации `{record, directory}`. В подтверждении нет зеркальных outcome/quality полей. Отдельный `config/types.ts::RunResult` с зеркальными `buildExitCode/wallSeconds/signal/dataValid` удалить. Completion — обязательный discriminant на уровне record, а не optional поле внутри outcome:

```ts
type RunCompletion =
  | { kind: "complete"; native: ObservedNativeOutcome }
  | {
    kind: "incomplete";
    reason: "not-finalized" | "interrupted" | "observation-error";
    requestedSignal: "SIGTERM" | "SIGINT" | null;
    native: ObservedNativeOutcome | null;
  };
```

Это эскиз границы, не новая runtime schema. `ObservedNativeOutcome` содержит только реально наблюдённые code/signal/native wall с указанием источника (`direct child status`, native resource-helper receipt либо Hyperfine measurement). Результат непосредственной команды runner хранится отдельно, если runner запускает `/usr/bin/time`/Hyperfine. Он не называется результатом Native. CPU/RSS сохраняются с явным measurement scope; `/usr/bin/time` вокруг helper не измеряет изолированные ресурсы Native. Неизвестный Native — `null`, без подстановки124/143/137. Requested signal и observed signal независимы; numeric143 с явным handler сохраняет `signal:null`.

В record также входят command/argv/cwd, наблюдённый elapsed самого driver, состояние каждого log stream (`open`, `eof`, `failed`, при checkpoint — `not-started`) и исходная ошибка observation, если она получена. Driver wall не складывается с nested wall/CPU и не выдаётся за native wall. `complete` означает полученную полноту наблюдения, **не** exit0: завершённый nonzero command остаётся настоящим complete failure.

`incomplete` всегда имеет `dataValid:false`, `coverageComplete:false`; report явно пишет INCOMPLETE/INVALID. Неизмеренные counters/operations — «не обработаны», а не доказанные нули. Historical import/comparison и benchmark обязаны сначала разобрать discriminant и отвергнуть incomplete. Refresh/наличие отдельных valid executor summaries не может повысить completion. Это правило одинаково для per-run и aggregate report; итог command `report` также не может вернуть valid по incomplete run.

## Владельцы и последовательность

| Владелец | Что принадлежит ему | Чего он не утверждает |
| --- | --- | --- |
| Entrypoint/вызывающий driver | OS TERM/INT listener, передача одного cancellation signal, exit/re-raise после сохранения record | Что direct-child forwarding остановил всех descendants |
| `executeScenario` | Один child handle, две pumps, принятые actual results/errors, однократный terminal choice, forwarding cancellation своему direct child и единственный terminal writer | Native status по статусу wrapper или по запросу отмены |
| Pump | Порядок bytes своего stream, partial writes, file handle, first IO error и EOF этого stream | EOF другого stream или завершение Native |
| Resource helper/Hyperfine | Свой существующий фактический result artifact | Замену отсутствующего статуса guessed exit code |
| Report writer | Сериализация переданного snapshot и производные summary/text | Управление процессами, ожидание EOF, обязательный normalize для partial |

На вход `executeScenario` передаётся cancellation (`AbortSignal` с типизированной причиной), без установки второго OS listener внутри report/library. **Только execute** передаёт cancellation своему direct child, не более одного раза; entrypoint не дублирует forwarding. Уже отменённый вход либо отмена до spawn выбирает incomplete с native:null и не запускает child. Между последней проверкой cancellation и синхронным spawn нет await; отмена после spawn идёт через того же единственного владельца child. Entrypoint держит OS listener до завершения **либо отказа** публикации, затем обязательно завершает interrupted lifecycle даже при write failure. Report-local caller должен подключаться к этой же явной границе; naked вызов без signal не обещает OS recovery. `renderCommand` сейчас использует другой owned runner; автоматически смешивать его group/PTY протокол с этим slice не нужно.

1. До spawn, в уже созданном run directory записать минимальный record `incomplete/not-finalized`, summary и читаемый report. **Любой отказ обязательной pre-spawn публикации запрещает spawn**. Initial checkpoint и terminal publication принадлежат **одной последовательности writer**, а не двум параллельным вызовам. Cancellation selection уже активно во время async initial temp/write/rename: оно выбирает immutable interrupted snapshot и запрещает spawn, но terminal writer начинается лишь после завершения либо отказа initial writer. Поэтому поздний initial rename никогда не может затереть terminal record. После initial failure child не запускается; выбранный terminal snapshot допускает одну попытку публикации в той же последовательности, а при её отказе действует typed publication failure, без retry initial. Это checkpoint существующих artifacts, без нового receipt family. Он даёт честный результат даже если дальнейшая смерть не оставит времени на interrupt write; есть дополнительная фиксированная запись на запуск, её стоимость пока не измерена.
2. После spawn продолжить две существующие streaming pumps. Status fulfillment и pump completion/error **сразу обновляют собственные in-memory facts**, а не присваиваются только после общего allSettled. Реальные launcher receipts/events сохраняются своими прежними writers.
3. Normal branch ждёт настоящий status и успешные EOF обеих pumps, получает требуемые доступные resource facts, затем нормализует. Это только **candidate facts**, не permission писать complete. Status rejection либо первый pump open/write/read/close IO failure немедленно предлагает incomplete observation-error с исходной причиной и известными фактами, не ожидая второго EOF/status. Drain/teardown не блокирует выбор snapshot. Missing required native result и normalization/resource-read failure также предлагают incomplete. Complete разрешён только после успешного получения всех требуемых observation facts.
4. Cancellation становится отдельным событием выбора ветки, а не только переменной, которую проверят после EOF. Ветка interruption снимает один snapshot **немедленно**, до ожидания status/EOF/resource receipt. Сохраняет run.json/минимальный summary/report, не вызывает full normalize и не сканирует растущие event files. Уже имеющиеся paths/prefixes остаются доступными; неизвестное остаётся неизвестным.
5. Запись partial не ждёт teardown: reader cancellation/закрытие собственных pump handles начинается отдельно после snapshot и не является предусловием отчёта. Один pump остаётся владельцем своего file handle; report не закрывает файл из-под pending write. Повторный TERM не запускает вторую competing finalization. Запоздавшие results не переписывают сохранённый incomplete record в complete. Их настоящие raw receipts могут появиться позже и остаются evidence без изменения результата этого capture. Точная точка выбора и write/error contract — ниже; простой race двух writers недопустим.
6. После сохранения incomplete вызывающий entrypoint завершает свой interrupted lifecycle/re-raises сигнал. Возврат incomplete **не доказывает** закрытие всех pipes/descendants. Внешний group deadline остаётся существующей ответственностью вызывающего, без нового supervisor, polling, escalation или watchdog в продукте.

### Одноразовый terminal choice

В existing execute lifetime есть одна локальная переменная `selected: RunRecord | undefined`. Единственная синхронная операция выбора принимает первый готовый terminal snapshot, если selected ещё нет. Она не делает IO и не await. Snapshot собирается как отделённые итоговые значения/arrays из уже полученных facts и после выбора не мутируется; новый freeze/clone framework не нужен. Только выбранный record передаётся **одному** terminal writer.

Конкуренты выбора: cancellation; первый observation failure; normal facts плюс завершённая normalization. Последняя сторона только предлагает complete, если selected ещё пуст. Если TERM приходит во время await resource read/normalize, выбирается incomplete; поздний normal результат игнорируется для публикации и вообще не запускает writer. Optional exports/derived artifacts создаются после выбора и не блокируют interruption selection. Если complete уже выбран (полнота действительно получена), последующий OS TERM относится только к exit entrypoint: опубликованный completion не меняется, второй incomplete writer не возникает. Listener остаётся до success/failure записи, чтобы entrypoint всё равно завершился нужным внешним сигналом. Первый выбор сохраняет исходную причину; повторная отмена/поздние errors не создают новый terminal record.

### Подтверждённая публикация и typed failure

`run.json` — authoritative snapshot; summary/report производные. Writer больше не clamp-ит/обогащает переданный record скрыто, как current `report/run.ts:203–215,349–355`. Paths/quality/completion и известные relative raw-artifact refs определены до terminal choice. Record не содержит меняющегося абсолютного «текущего места» копии: collection path — provenance, relative artifact paths разрешаются относительно подтверждённой publication directory. Поэтому storage может копировать **тот же** record без замены outputDirectory внутри него.

У writer явный результат `Promise<{record: RunRecord; directory: string}>`: record буквально тот, который записан в run.json, directory — место подтверждённой публикации, без зеркальных exit/quality. Замена run.json — temp+rename **в том же run directory**; успешный commit подтверждается до optional derived writes. Один terminal snapshot допускает stage/final копии, но они byte-equivalent по record; storage не изготавливает другую его версию. Это не обещание атомарности всех файлов или fsync.

При отказе terminal run.json writer бросает узкую typed publication error с runId/path, phase, исходной причиной и последним **подтверждённым** checkpoint; intended complete не возвращается как сохранённый success. При отказе только summary/report error содержит уже опубликованные record/directory вместе с исходным child result/interruption. Это failure публикации, даже если native code0; report/CLI не возвращает success и не затирает причину. Тот же договор проходит через `storage.finalizeRun`: успешная финальная copy подтверждается отдельно, отказ сохраняет известную stage publication и реальные paths, не подменяя её intended final destination. Ошибка cleanup после подтверждённой final copy вторична и не стирает published record. Entry point в `finally` завершает свою signal policy также при typed write failure; listener не оставляет caller жить вместо re-raise.

Ошибка самого диска и SIGKILL до записи остаются физическими пределами; pre-spawn checkpoint уменьшает потери, но не даёт гарантии durable recovery. Typed error — узкая форма отказа существующего writer, не новый status/proof framework.

## Что удалить и что менять

Удаляется глобальное требование «сначала status + оба EOF + resource receipt + normalize, затем любой report». Оно остаётся только условием normal complete branch. Удаляются отдельный mirrored `RunResult`, signal-driven throw/catch как единственный вход в partial path и обязательный normalize partial capture. Не переносить failed fixture wholesale; EXDEV preservation и прежняя350ms readiness-race должны быть исправлены в собственном control design.

Связный contract slice: `runner/execute.ts`, `report/types.ts`, `report/run.ts`, `config/types.ts`, `analysis/historical.ts`, `analysis/report.ts`; общие producers/adapters **`runner/render.ts`, `runner/process.ts`, `runner/storage.ts`** тоже обязательны. Нужны явные discriminant branches у actual callers: `runner/benchmark.ts`, `cli/experiment.ts`, `tools/scaling.ts`, `tools/repeat-java.ts`, `tools/check-java-general-use.ts`, `tools/check-installed.ts` (включая его generated probe string). Это **15 production/tool files**, а не прежний четырёхфайловый candidate. Перенос ownership/group/PTY machinery в execute не входит; для process разрешается лишь перенос фактического outcome provenance/error/fullness через existing boundary. Перед реализацией root должен выбрать этот целостный scope, а frozen six-file candidate сохранить как failed history.

### Provenance existing render/process/storage

В current `runner/process.ts:459–498` начальный nativeStatus — wrapper status; escalation без native receipt синтезирует137/SIGKILL, а `knownNativeOutcome` допускает escalation вместо receipt. Новая форма **не признаёт** ни этот fallback, ни started marker доказанным Native outcome. Existing process boundary должна передавать отдельно actual wrapper result, native receipt/result если он реально получен, measurement scope и observation errors/fullness. Без native receipt — native:null, при observation failure — incomplete с известными actual facts. Action escalation остаётся отдельным фактом, не observed Native status.

`runner/render.ts:145–192` не может безусловно обернуть любой outcome/OwnedProcessError в complete. Он строит тот же completion по provenance и полноте; доказанный nonzero native result при полноценном observation — complete failure, normalization failure — incomplete observation-error. Direct-child wall фиксируется в момент получения status, не после EOF/cleanup; `/usr/bin/time` CPU/RSS подписываются scope helper+tool. `runner/storage.ts:145–157` передаёт и возвращает именно publication acknowledgement/typed failure, не переписывает selected record и не возвращает только intended directory. Без этих трёх adaptations единый clean contract для render/execute не заявляется.

### Коррекция aggregate/refresh contract

Первый draft пропустил actual consumer `src/analysis/report.ts`. В текущем Source он добавляет `summarizeRun(run)` в aggregate summary до передачи record в per-run writer (строки130–146), выводит прежние зеркальные `buildExitCode/wallSeconds` (202–206), а returned `dataValid` получает через `summary.every(...)` (276). Per-run clamp внутри writer не исправляет уже собранный aggregate. Поэтому одного обновления `RunRecord`/writer недостаточно.

В новом договоре completion разбирается из authoritative run record **до** normalization/aggregation. Incomplete показывается из сохранённого snapshot; refresh не сканирует растущие raw files ради повышения качества. Для complete normalization может уточнить observation quality без изменения Native facts. Затем из одного итогового record строят **обе** per-run и aggregate summaries/rows. Нельзя сначала сохранить более оптимистичную aggregate summary и рассчитывать на последующий writer clamp. `report` refresh сохраняет incomplete reason/native unknown и не трактует свежие executor summaries как complete capture. Returned report validity проверяет те же итоговые records; наличие incomplete делает его false даже при valid отдельных observations. Строки таблиц читают native code/wall из completion outcome, а requested signal/driver wall подписываются отдельно. Удалённые `buildExitCode/wallSeconds` не восстанавливаются adapter-подстановкой.

Scope closure callers: benchmark отказывает до warmup/sample/runId append и следующего запуска; experiment run — до coverage rewrite, report/verify — до normalize/native-code проверки; scaling — до output copy/normalize/comparison; repeat-java — до snapshot и второго render; check-java-general-use — до audit/normalize/acceptance; generated check-installed driver — внутри probe до check/дальнейшего IO. Одна cancellation принадлежит всей run/series, а не создаётся заново на sample. Report-local drivers мигрируют явно, старые archived drivers не меняются.

Будущий адресный existing aggregate control должен проверять весь путь refresh: incomplete per-run JSON, summary, aggregate row/JSON и returned validity остаются согласованно INVALID, с UNKNOWN native outcome; complete row сохраняет реальный status/wall. Пока этот control только требование design, не новый запуск.

## Ограниченная будущая приёмка

Новых запусков сейчас нет и budget не восстановлен. После отдельного разрешения достаточно адресных existing controls, без Native/Quarto:

- Normal child пишет binary prefixes, закрывает streams и возвращает известный exit: complete содержит exact bytes/status; nonzero и numeric143 не превращаются в сигнал или success.
- Готовый held child/pipe-holder сохраняет prefix, а controller доставляет TERM caller и своим known-owned fixture children, моделируя group boundary. Partial run.json/summary/report должны появиться **пока EOF/status намеренно ещё не предоставлены**, и содержать open/unknown, INVALID, coveragefalse. Это проверяет отсутствие legacy barrier, а не количество signal callbacks. Control самостоятельно закрывает свои handles после проверки; production не получает watchdog.
- Отдельный missing-helper-result случай: реально полученный wrapper status остаётся wrapper status, native:null, incomplete; historical comparison отказывает. Не ждать callback, который специально не сможет опубликовать result.
- Ошибка одной pump при удержанном втором stream должна выбрать observation-error и сохранить snapshot до второго EOF. Publication отказ до spawn не запускает child; отказ final run.json не возвращает intended complete; отказ derived write возвращает typed failure с уже опубликованным exact record. Cancellation во время pending normalize не допускает позднего complete overwrite; cancellation после selected complete меняет только entrypoint exit. Existing render/process adapter control должен отличать native receipt от escalation/wrapper fallback, не присваивая137 Native автоматически.

Fixture evidence с начала создаётся на целевом filesystem либо копируется без cross-device rename; cleanup failures не заменяют первичный failure. Acceptance проверяет сохранённые outcomes/artifacts и своевременное появление partial, а не строковый вид реализации. Не утверждать заранее, что это объяснит прежний host hang или исправит descendant forwarding.

## Основания

- [Причинная карта accepted lifecycle](performance-term-recovery-next-20261104.md).
- [Frozen candidate и все реальные отказы/бюджеты](performance-term-recovery-20261104.md), [patch](performance-term-recovery-20261104.patch), [final caller result](performance-term-recovery-20261104-evidence/final-caller-control/false/caller-status.json).
- Actual accepted `c2dcf00:src/runner/execute.ts`; current dirty `execute.ts`, `report/{types,run}.ts`; существующие `runner/{render,resource-child}.ts` и actual execute callers просмотрены только как Source. Собственный high admission прежний, globals/mainstate не менялись.
