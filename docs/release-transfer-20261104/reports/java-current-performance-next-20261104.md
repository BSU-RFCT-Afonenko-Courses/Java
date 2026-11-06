# Java128: следующий ограниченный Performance эксперимент

Исторический registry и его локальные пути ниже относятся к описанному запуску;
[точные исходные данные](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-native-paired-published-runtime-profiles.json) сохранены в Git.
Записанные команды не являются инструкцией выбора нынешнего runtime/image.

## Решение и один вопрос

Предлагается **один наблюдаемый prefix настоящего full128 PL export** в новой disposable копии, через принятый Performance `e7267f1dd8b186d5b95a213f7a03a9bed71559a8`, Core `4a2903e6d03d1dd489b6922d113100826913cf67`, existing launchers/trace/`executeScenario`. Общий cap **120 s вместе с preparation и grace**, retry0. Это план: Source/driver/config/stage сейчас не создавались, Native/inspect/render/compiler/probes/hash/network не запускались.

**Единственный вопрос:** после separately recorded stage/diagnostic preparation, что показывает первый доступный документный prefix текущего128-input capture: время Native/Pandoc command envelopes находится преимущественно внутри наблюдаемых TS assignment guard/normalize и Lua request/resource phases либо остаётся вне этих измеренных semantic scopes? Если cap заканчивается до первого документа, ответом будет конкретный достигнутый preparation/pre-hook boundary, а не диагноз per-QMD.

Это не новый900 s прогон и не попытка подтвердить full export/release. В native argv остаются все128 configured inputs, delivery units и ordinary Java package corridors; остановка по времени не превращается в сокращённый набор или успешный128-document результат.

## Что установлено прежними первичными данными

Прочитаны [java-native-cost-next.md](java-native-cost-next.md), [decode report](java-state-decode-20261104.md), его raw [result.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/java-state-decode-20261104/result.json) и [probe.ts](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/java-state-decode-20261104/probe.ts), прежние exact [execution](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/java-native-d7-execution.json) и [partial evidence](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/java-native-d7-partial-evidence.json), адресные acceptance sections. Дополнительно прочитаны только allowlisted поля самих saved `state.json` и `reservation.json`, без вывода environment/raw metadata:

`java/tasks/_generated/course-spec/native-ts/f7da2eb2-c4da-41cd-b7e3-1b9ed89a37ea/`.

- State2184627 B, inputs128, rows4751, memberships7305; profiles `[full]`, projection `author`. Initial counters: directoryReads21915, hashReads4751/hashBytes11619819, fullWalks1, fullSourceFences2, localFences0, inspectCalls1. Reservation bound, один child inputs128/usedtrue/postedfalse.
- Old exact Core — **d7cc10eaa21fa35bbe32d73cc4afa216002393da**, Javaa0e10aa1d85f6c141a75925e0769bfb533d2f811, PLda2ad6b6c00891590e59e5abccf3d1eb4ff552f7. Exit124 после900.001733135 s; marker93 — start,92 sidecar chains — прохождение соответствующих filters. Child post/complete/accepted release не получены. Эти saved facts не относятся к Core4a.
- `childRenders0` в initial state не означает отсутствие child: runtime работа подтверждена log/sidecars. Directory reads — initial snapshot и две полные fences; они не являются128 per-document полными fences.
- Microstage: одно read6.131 ms, median JSON.parse8.257 ms/schema decode11.445 ms в одном bundled Deno2.7.14. Это extracted d7 schema над одними bytes, не current actual guard, не repeated startup и не распределение900 s. Нельзя умножить эти числа на128 и назвать измеренным total.

## Текущий Source и наблюдаемость

Read-only actual HEAD: Core4a и Performancee726 совпадают с назначением. Java checkout сейчас `a6d28a42d3f0d4f4bbebcd64b5664ebef8341932`; адресный Git diff от olda0 для `tasks/_quarto.yml`, `_quarto-full.yml`, `prairielearn.yml` пуст. Current `tasks/_quarto.yml` сохраняет целый book/chapters, hooks и full/student profiles. `providers.json` всё ещё описывает installed d7/da2 candidates; эти pins не удостоверяют4a. Их не меняли.

| Граница current Source | Что уже доступно | Чего это не даёт |
| --- | --- | --- |
| Core `runtime/operation.ts:1015–1067` `assertNativeAssignmentCapture`; early TS caller `native-ts/early-filter.ts:64` | Guard реально readReservation → read+JSON.parse state → actual schema decode → source/witness/child/intent comparisons → assertReservationCurrent. Внутри этого guard нет `assertSources` | Ручного guard duration span сейчас нет; summary сам его не выделит |
| Core `native-ts/request.lua:15–24`, вызываемый early/late loaders | Lua тоже читает/декодирует state и reservation, проверяет текущий child. Это отдельная реальная per-QMD граница | TS schema microstage не измеряет Lua JSON cost, Lua wall IO либо эти comparisons |
| Core `document/normalize.ts:544–553` | Ручной `course-core.document.normalizeDocument`, document_id, TS monotonic wall | Не startup/module loading, не полный Pandoc/HTML writer |
| Core `native-resources.lua:47–49,75–76` | Current4a использует один `M.uses` при `rawequal(raw,public)`, distinct projections сохраняют два. Ручные facts/uses/full-document walk spans и counters уже есть | Старый d7 duplicate больше не proposed fix; вклад его удаления в900 s неизвестен. Lua clock — process CPU, не wall |
| Performance `launchers/tool.ts` | Initial/final `processes/<id>.json`: kind, original argv/cwd, process/parent IDs, status/signal, inclusive native_command wall от launcher spawn до status. Early-filter.sh идёт через Quarto run и выбранный QUARTO_DENO launcher | Нет отдельного pure startup interval; initial receipt не содержит финального PID/wall/status, null не доказывает живой процесс. Bare Pandoc/Quarto startup и module load входят в command envelope |
| Performance `agents/ts/agent.ts`, `agents/lua/agent.lua` | TS wall spans + calibration; Lua CPU spans + document_id; started markers; bounded event buffers, final JSONL/summary, quality/dropped/incomplete | События пишутся при flush, не непрерывно. Active process при kill может не сохранить свой buffer. Нельзя складывать nested inclusive command envelopes или напрямую вычитать Lua CPU из TS/native wall |
| Performance `runner/execute.ts` e726 | Concurrent binary file pumps сохраняют уже записанные stdout/stderr prefixes до завершения child | Не обещает final run/summary/status/report после TERM, flush всех agents, tail bytes или fsync. Внутренний caller с `.output()` может по-прежнему удерживать собственный child output |

Ручные Core `infrastructure.process.command/commandInput` видны только там, где execution действительно проходит через эти функции и действующий agent. `runAssignmentCaptureChild` создаёт native capture child напрямую; отсутствие у него такого command span не означает отсутствия child. Receipt/parent links и actual argv нужно сопоставлять с Source phase, не группировать как дубликаты по совпавшей строке команды.

## Почему выбран targeted trace

Summary уже различает normalize/resource semantic phases, но не actual TS guard/Lua request. CPU режим добавил бы V8 samples, однако сам не дал бы точного inclusive wall guard/read/decode. Для поставленного вопроса предлагается existing trace transformation только двух TS modules в **копии**:

```text
tasks/_extensions/Afonenko-Course-Tools/course-core/runtime/operation.ts
tasks/_extensions/Afonenko-Course-Tools/course-core/document/contracts.ts
```

Existing Lua debug trace scope — только `tasks/_extensions/Afonenko-Course-Tools/course-core/native-ts/request.lua`; существующие normalize/resources manual spans остаются включёнными. No source-snapshot per-file callback tracing и no Zod internals tracing: они расширили бы overhead/counter flood без нужды для первого вопроса.

**Честная partial boundary:** весь operation.ts при `strict:true` заранее непригоден: getters `identity`, `inputs`, `metrics` (`:2128/:2132/:2713/:2766`) относятся к явно unsupported transform nodes. Не предлагать такой гарантированно отказывающий preflight. Existing `instrumentModule` позволяет `strict:false`: сохраняются реальные symbol maps и skipped getter entries, `coverage.complete=false`. Это пропуск наблюдения unsupported symbols, **не** пропуск их исполнения или ослабление Core guard. Functions `assertNativeAssignmentCapture`, `json`, `readReservation`, `assertReservationCurrent` и actual `document/contracts.decode` должны присутствовать в actual generated map; иначе Native не запускать и сохранить конкретную preparation причину. Не объявлять whole-module coverage и не внедрять вручную новые spans в product.

В early TS executor guard direct child `json` охватывает read+JSON.parse совместно, nested decode — schema.safeParse. Полное отделение read от parse нынешними function scopes не достигается; old microstage не подставляется вместо этих interval. Автоматические операции идентифицируются existing symbol IDs/maps, без вывода аргументов/state bodies. Lua request spans дают CPU loader/read/decode, не wall IO. Native startup/module loading/Pandoc reader/writer и прочее остаются **неразложенным остатком command envelope**, не именуются одной причиной. Trace overhead/200k-event cap и partial coverage учитываются в выводе; ускорение/RSS effect не измеряются.

## Exact stage и будущая единственная попытка

Адаптировать уже использованные report-local drivers [stage Python](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-shared-site6-stage-20261104.py) и [Performance TS](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-shared-site6-stage-20261104.ts), сохранив их exact local Git archive/overlays и existing `prepareLaunchers` + `executeScenario`. Это ограниченный invocation glue, не новый runner/supervisor/cache/receipt framework. Файлы будущей адаптации сейчас не создавались.

Новый отсутствовавший stage: `reports/java-current-performance-20261104-actual/`. Полный Java exact archivea6, без untracked старых HTML/generations. Никаких исключений `projects`, package directories или delivery QMD. Whole-package Core4a overlay во все шесть уже существующих owner destinations, whole PL current `d8efb030ee6435f3c1f7d242e7388803b55b3299` в tasks; actual mapping/ref записать отдельно от старого installed/pins provenance. Остальные пакеты/авторские входы сохраняются из Javaa6, Site active-audience candidate не применяется. Подмена отдельных bridge/files поверх старого Core недопустима. Это текущий paired candidate, не чистое измерение одной переменной относительно d7.

Performance driver использует exacte726 checkout; если ref/source не соответствует согласованному — первая preparation причина и остановка, без автообновления. Использовать уже известные Quarto1.11.5 и bundled Deno2.7.14/Pandoc paths, фактические existing cache/dependencies, без install/fetch/capabilities/version/writable-cache probes. При реальном import/runtime отказе остановиться, а не чинить toolchain цепочкой попыток.

В invocation bookkeeping отдельно и **сразу после завершения каждой стадии, до executeScenario** сохранять measured monotonic:

1. exact course/provider archive+whole-package overlays;
2. `prepareLaunchers` и diagnostic backend/data-dir copy;
3. два existing TS transforms/symbol maps + existing Lua scope; Source hashes/prepared receipts остаются штатными Performance receipts, новый proof/schema не вводится.

Не записывать эти preparation интервалы только в финальный capture-timings после `await executeScenario`: при timeout они снова потеряются. Не запускать `prepareDiagnostic` с общим physical package inventory ради подготовки этого узкого scope: прежний whole128 source/corridor walk не должен дублироваться новым coverage census. Воспользоваться существующими APIs, как stage driver, и честным partial coverage.

Native scenario argv сохраняет прежнюю полную операцию:

```text
/opt/quarto/bin/quarto run
tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/entrypoints/export-course.ts
{course_root}
{run_output}/pl-native
--instance 2026-2027-s1
--checks-output {run_output}/project-checks
--runtime-registry {runtime_registry}
--book tasks
```

`cwd` — root disposable Java, не original Java/tasks. Registry input — сохранённый `reports/sp-native-paired-published-runtime-profiles.json`; без remote query/новых runtime registrations. `mode=trace`, `cachePolicy=repeat-render`, `cleanPaths=[]`, resources wall/cpu/peakRss как existing scenario; env `QUARTO=/opt/quarto/bin/quarto`, `XDG_CACHE_HOME=/home/tolya/JavaCodex/.cache`, `DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache`, `DENO_NO_PACKAGE_JSON=1`, `DENO_NO_UPDATE_CHECK=1`, `QUARTO_RUN_NO_NETWORK=true`, плюс **actual** `prepareLaunchers.env`. Одного `COURSE_PERF_MODE` недостаточно. Profiles/author projection выбирает штатный PL/Core admission; не менять их вручную ради наблюдения.

Внешний cap `timeout --signal=TERM --kill-after=5s 115s` обрамляет **всю** будущую stage+launcher/transform+Native invocation. Общий максимум120 s; Native attempt не более1, retry0, без900 s continuation. Driver до вызова пишет command/ref/env allowlist и completed preparation timings. Outer owner сохраняет exit/elapsed/stdout/stderr после timeout как invocation result; это не восстановленный product RunResult. Если time cap исчерпан на preparation, Native не выполняется. Existing cached graph отказ, unsupported scope или semantic guard refusal — первый действительный результат, не повод второй попытки.

## Что пригодно при timeout

Сохранять только уже существующие partial artifacts stage: pre-Native timing/ref/transform receipts, streaming stdout/stderr prefixes e726, initial/final process receipts, `*.started.json`, JSONL/summary завершённых/успевших flush executors и native request/early/late/project-facts sidecars. Ничего не удалять ради повторного запуска. Final `run.json`/normalize/report может отсутствовать; `dataValid`/accepted export тогда UNKNOWN.

В postmortem использовать finished non-null command intervals и реальные process/parent/document links; pending rows оставить pending. Для completed early executor сопоставить TS guard/read+parse/decode/normalize spans с его inclusive Deno command envelope; для связанного Pandoc — manual resource/request Lua CPU и native wall отдельно. Не вычитать nested children дважды и не выводить pure startup/HTML цену из остатка. Raw surviving events с broken/missing pairs, dropped/incomplete/ABI errors не становятся VALID measurements. Sidecars показывают reached boundaries, не accepted/completed owner.

Благодаря такому prefix либо появится конкретный текущий per-QMD guard/Pandoc observation, либо будет известен preparation/pre-hook boundary до Native документов. Даже первый полезный разрез не доказывает причину старых900 s/полный current speedup или возможность завершения128 export в120 s. UUID/назначения/оценки/propsGrade/student files/production outputs не меняются; grader execution и publication не входят в опыт.

## Собственный допуск и выполненные действия

Source-only: адресные чтения названных reports/primary saved JSON/current Core/Performance APIs, Git ref/config diff reads. R0–R6 и полный аудит не повторялись. Product Source, drivers, staging, installed/pins/userdata/outputs не менялись; запусков/компиляции/новых hash/scans/probes/network нет. Создан только этот Markdown report.

Reviewer/research role `gpt-6.1-sol/high`, Standard; chat `01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1`, path `/root/perf_event_types`, неизменный origin `01a1a514-1688-7662-80fc-58bf7a272ae0`. Primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-41-59-01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1.jsonl`: session_meta собственные ID/path, весь origin interval turn_context8/171/270/405/734/918/1031/1046/1192/1274 — top/nested `gpt-6.1-sol/high`, PASS. Globals не менялись, явный service_tier отсутствует; скрытая маршрутизация не удостоверяется.
