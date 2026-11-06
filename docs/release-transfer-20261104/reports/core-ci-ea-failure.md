# CI ea197ad: import map restricted Deno child

3 ноября 2026. Exact hosted Core `ea197ad7e88a3410c4eea52f293d975aea63db4c`, [run41694056884/job126119794613](https://github.com/Afonenko-Course-Tools/quarto-course/actions/runs/41694056884/job/126119794613), draft [PR70](https://github.com/Afonenko-Course-Tools/quarto-course/pull/70). Existing job log получен primary GitHub `fetch_workflow_job_logs`; полный decoded ответ сохранён в [core-ci-ea-job-126119794613.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-ci-ea-job-126119794613.log). Нового CI rerun, canonical/full suite/native pipeline здесь не запускалось.

## Первая hosted причина

После успешного setup/native fixture preparation canonical прошёл12первых checks, включая4 ранее исправленных tests. Первый новый FAIL — **013 performance-context**, `2026-11-03T18:29:05Z`, exit1. Ordinary actual-child/context часть успела вывести PASS. Затем restricted Deno child остановился до функционального поведения:

`summary/--deny-env: error: Import "stdlib/path" not a dependency`.

Import stack указывает на `runtime/assignment-execution.ts:4`, assertion — `tests/performance-context.ts:47`. Evidence artifact [13260510806](https://github.com/Afonenko-Course-Tools/quarto-course/actions/runs/41694056884/artifacts/13260510806) успешно выгружен. Это не повтор старого positional API TypeError, не product refusal и не проверенный дефект closed env inheritance.

Причина: test запускает raw `Deno.execPath() run --no-config ... parent.ts` с denied/narrow env permissions. `parent.ts` импортирует Core `infrastructure/process.ts`, который транзитивно импортирует current `runtime/assignment-execution.ts` со `stdlib/path`. В отличие от `quarto run`, raw Deno child не получает Quarto import map автоматически. Поэтому module resolution ломается прежде, чем child сможет исполнить существующий `command('/bin/sh',...)` и проверить inheritance/explicit override. Нужный parent map уже существует и dependency была разрешена у самого test parent; менять production imports/guards или permissions для этого не требуется.

Root получил cause/smallest scope до правки и согласовал **performance-context only test setup fix**.

## Адресный read-only просмотр однотипных patterns

Для предотвращения следующего blind first-failure цикла прочитаны точные raw child/compiler call sites из active canonical/native scripts. Это чтение Source, **не исполненные PASS** остальных tests:

- `performance-context.ts` — единственный найденный raw Deno `run` child, импортирующий product process module без import map; изменяется здесь.
- `navigation-typecheck.ts` и `native-context-a1-typecheck.ts` передают собственные existing explicit import maps в raw Deno `check`; такой же missing-map pattern не найден.
- `system-toolchain.ts` запускает CLI fixture через выбранный **Quarto** executable с `run`, а не bare Deno; Quarto задаёт map/cache штатно. Его hosted PASS в этой попытке также уже есть, новый запуск не нужен.
- `process-utf8.ts` и сохранённый `process-input.ts` запускают Deno `eval` с self-contained stdin/stdout JS, без imports product/stdlib modules. `assignment-child-errors.ts` использует deliberate nonzero Deno argv как error-path control, не module loader positive. Missing map не добавляется этим разным сценариям.
- Existing pending hook child использует `quarto run`; остальные native render/Pandoc scripts не являются найденным bare-Deno-module-launch pattern и не запускались для данного аудита.

Других однотипных callers в просмотренной active области не обнаружено. Это не утверждение, что вся canonical matrix пройдёт: тесты после hosted013 ещё не были исполнены этой попыткой. Obsolete protocol или произвольные runtime schemas для устранения import setup не создавались.

## Единственный Source diff

Изменён только `tests/performance-context.ts`. Test parent сохраняет маленький temporary import map с единственным используемым alias `stdlib/path`, взятым из **actual parent** `import.meta.resolve('stdlib/path')`; existing Quarto spelling `jsr:/` нормализуется тем же `jsr:` способом, который уже используется map helper. В raw child добавлены лишь `--import-map` и путь к этой карте. Stdlib tree не копируется, version/installation не угадывается, нет нового shared runner/helper/framework.

Child permissions остались прежними: `--deny-env` либо только `--allow-env=COURSE_PERF_MODE`, прежний `--allow-run=/bin/sh`, no-prompt. Не добавлялись allow-env/read/all или permissive fallback. Все3 restricted cases по-прежнему требуют actual shell result `inherited|explicit|student`, проверяют, что caller env не изменён, и требуют child success. Ordinary parent/stale-span removal/document extension context/functional env assertions сохранены.

Existing fake Performance ABI fixture — прежний test observer, не producer authority. Он теперь типизирован existing `Metrics`/`OperationSpec`: generic wrap сохраняет тип функции, observation bag конкретный, registry доступен через Reflect без прежнего двойного cast. Явные any/casts убраны из этого test. Product Runtime/API/wire, env behavior, workflow/CI policy, canonical scope и previous4 tests не менялись. Однофайловое форматирование увеличивает строковый diff старого compact файла, но assertion meanings не переписываются.

## Проверка и пределы

Только изменённый test, один раз каждый вид проверки:

|Проверка|Исход|Время|
|---|---|---:|
|`quarto run tests/performance-context.ts`|PASS: ordinary parent/context и actual3 restricted Deno children|0.744s|
|Targeted bundled `deno check` только `tests/performance-context.ts`|PASS, typed Metrics fixture проверена compiler|3.454s|

Суммарно4.198s. Commands/codes/output: [core-ci-ea-performance-context.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-ci-ea-performance-context.log)/`.json`, [core-ci-ea-performance-context-typecheck.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-ci-ea-performance-context-typecheck.log)/`.json`. Compiler использовал known Quarto cache `/home/tolya/JavaCodex/.cache/quarto/deno_std/cache` и existing type-only map `/tmp/core-typed-owner-check-6lu44p5j/imports.json`; новых downloads/escalation/install не было. Это маленький test graph, не повтор общей Core→PL matrix. Bundled Deno format и `git diff --check` PASS.

Hosted failure служит RED; локальный redundant RED не запускался. Старые4 runtime/compiler greens не повторялись. Render/inspect/course hash/build, actual SP/Java attempt, production/deploy/install и PL Source writer не затронуты; frozen Java owner остаётся на installed d7/PLda. Новый hosted CI green/вся release готовность этим адресным PASS не заявляются.

## Собственный допуск и передача

Original turn `01a1a21a-f52f-73a3-85e9-758306144504` сохранён. Полный primary interval теперь12records/11unique turns, current `01a1a2ac-f2bf-7890-8796-257dbcf2dd88`, строка1929. Все top+nested model/effort — `gpt-6.1-sol/high`; Standard назначен root, service_tier absent и отдельно не утверждается. Globals/normative untouched.

Root уточнил порядок: после addressed checks reversible Source commit разрешён сразу; independent high review обязателен **перед push**. Source commit `ee3aa0897fefadff2543528e45032231b90c13c4` поверх `ea197ad7e88a3410c4eea52f293d975aea63db4c`: единственный `tests/performance-context.ts`,+157/−44. После commit Source clean, fmt/staged diff check PASS. Exact review range — `ea197ad7e88a3410c4eea52f293d975aea63db4c..ee3aa0897fefadff2543528e45032231b90c13c4`; сохранён также `core-ci-ea-candidate.diff`. Push/CI rerun остаются у root после review; самостоятельно не выполнялись.
