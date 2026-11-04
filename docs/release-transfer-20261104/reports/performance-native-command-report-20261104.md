# Performance: наблюдаемые командные интервалы в Markdown

## Состав и допуск

Workspace `/home/tolya/JavaCodex/java-release-20261110/performance`; actual HEAD перед работой однократно проверен: `5365bea43d7d036a893f134eb2473adf628cdd6a`. Изменены один production файл `src/report/run.ts` и один существующий test-файл `tests/report/run.test.ts`. Setup, process ownership, события, схемы JSON, прежние outputs и чужие checkout не менялись. Новых runtime validators, `any`, приведений к `ProcessReceipt`, protocol/cache framework нет.

Собственный chat `01a1a51b-018c-7962-bb54-e47212c4fe94`, agent `/root/performance_backend_copy`; primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-49-32-01a1a51b-018c-7962-bb54-e47212c4fe94.jsonl`. Origin предыдущей роли сохранён: `01a1a51b-01b7-7872-9705-872e66ab8fcb`; новые turns `01a1a538-f66f-76f2-8199-34710c2cb73b` и `01a1a53e-9bcf-7bc1-b479-f23508bd5a37` (исправление после независимого review). Полный origin interval включает `turn_context` строки 8, 173 и 309, верхние и вложенные назначения `gpt-6.1-sol/medium` согласованы. Допуск PASS. Standard назначен; отдельного подтверждения service tier primary не содержит. Прочитанные ранее `java/docs/model-admission.md` и `core/docs/russian-language-policy.md` применены; применимых AGENTS на пути Performance нет.

## Поведение

В existing `report.md` добавлена таблица «Наблюдаемые интервалы команд». Она использует ровно тот массив `processes`, который `writeRunArtifacts` уже читал для существующих trace/causal exports: candidate `src/report/run.ts:245–248`. Новых `readDir`, `readTextFile`, чтений JSON, exports или отдельных scans нет. Formatter `nativeCommandMarkdown` (`run.ts:118–196`) — чистая функция представления existing `Record<string, unknown>[]`, без притворного typed DTO. Это фактический parsed shape существующего `jsonFiles`; обязательный decoder всего process protocol не вводится.

Таблица содержит фиксированную категорию, wall в секундах до трёх знаков, exit, сигнал, process ID и ID только подтверждённого родителя. Для kind pandoc отображается Pandoc; для Deno — Deno. Категория Quarto разрешается только для kind deno, когда исполняемый script в argv имеет exact basename `quarto.js`, а следующий элемент ровно `render`, `inspect`, `pandoc` или `run`. Позиция script — первый argv либо первый элемент после `run` и распознанных switches из сохранённого actual prefix (`--unstable-ffi`, `--unstable-kv`, `--no-config`, `--no-lock`, `--cached-only`, `--allow-all`, `-A`, `--no-check`, `--v8-flags=...`, необязательный `--`). Unknown prefix оставляет Deno. Аргумент чужого script `/private/quarto.js render` не классифицируется как Quarto. Это консервативная классификация recorded argv, а не аттестация исполняемого файла или новый общий CLI parser. Полные argv, cwd, env, названия пользовательских скриптов и пути к ним в таблицу не попадают. Process/parent IDs выводятся только в UUID-формате фактического producer `crypto.randomUUID`; произвольные строки, пути и токены заменяются на «не показан», без hash replacement; новые маршруты/ссылки не создаются, existing causal.json остаётся в общем перечне артефактов.

Wall выводится только при точных `clock_domain=monotonic_process`, `scope=launcher-spawn-to-status` и числовом finite неотрицательном `native_command.wall_seconds`; отсутствие поля/всего legacy native_command, null, NaN, Infinity, отрицательное число и другая/неизвестная timing-семантика отображаются как «неизвестно». Настоящий ноль сохраняется как 0.000. Строки сортируются по убыванию исходного wall; unknown идут после известных, входной массив не сортируется и не изменяется. Известный exit отображается целым числом; отсутствующий/null — неизвестно; null signal — отсутствие сигнала, отсутствующий/нераспознанный — неизвестно.

Над таблицей прямо указано: wall от запуска launcher до статуса, inclusive интервалы могут перекрываться и не складываются; это не self time, CPU, критический путь или полный census процессов ОС. Покрытие частичное. Не вводятся сумма интервалов, сравнение с baseline, доля от общего wall или заключение об ускорении.

Integration вызывается только там, где processes уже читались: normalized non-off путь. Режим off и отсутствие прочитанных receipts не вызывают дополнительных IO и не выдумывают таблицу. Existing quality/dataValid и summary остаются независимыми; новая таблица не объявляет полноценное покрытие. Старые сохранённые report.md не обновлялись.

## Предпросмотр существующего capture

Вызван только чистый formatter через `deno eval --cached-only`; `writeRunArtifacts`, trace exports и native pipeline для preview не вызывались. До исправления трёх display-only краёв прочитаны 17 JSON receipts из `reports/core-native-computed-integration-20261104/runs/ade920b1-3657-4273-a63b-b2f47773982b/processes/`; исходный capture не переписывался. Ниже первые пять строк полученного 17-строчного представления; это разные вложенные интервалы, не пять независимых расходов.

| Категория | Wall, с | Exit | Сигнал | Процесс | Подтверждённый родитель |
| --- | ---: | --- | --- | --- | --- |
| Quarto run | 7.925 | 0 | — | 8094c765-815e-4278-b051-6a5fbb72bb59 | не подтверждён |
| Deno | 7.432 | 0 | — | 17508e10-52e6-4644-8eae-db2333d0c5f5 | 8094c765-815e-4278-b051-6a5fbb72bb59 |
| Quarto render | 3.964 | 0 | — | 05fe0956-fd03-4289-813b-7787fcd96ccd | 17508e10-52e6-4644-8eae-db2333d0c5f5 |
| Pandoc | 0.783 | 0 | — | d3635947-5d36-46c1-938d-b7a3783cf8b1 | 05fe0956-fd03-4289-813b-7787fcd96ccd |
| Quarto inspect | 0.686 | 0 | — | f1ed56f1-19c8-4b2c-b115-ec308ca350e7 | 17508e10-52e6-4644-8eae-db2333d0c5f5 |

Эти значения помогают выбрать кандидатов для следующего предметного анализа; их нельзя суммировать, называть self time или считать доказательством причины Java128/900. Preview не является новым benchmark/capture. После review preview повторно не исполнялся; actual сохранённый Quarto prefix и producer UUID прочитаны до изменения, итоговый recognizer явно сохраняет именно этот prefix, а таблица выше фиксирует реально полученный первоначальный preview, а не выдана за новый прогон.

## Фактическая проверка

Изначально один общий `timeout 30s`, без retry, штатный `/usr/bin/deno` с `DENO_DIR` unset, config autodiscovery, без принудительных manual/frozen overrides. Команда завершилась exit 0 за 0.948 s:

- `deno check --cached-only src/report/run.ts tests/report/run.test.ts` — PASS обоих affected roots.
- `deno test --no-check --cached-only --allow-read --allow-write --filter "run summary retains event deltas and executor totals as separate observations" tests/report/run.test.ts` — 1 passed, 0 failed, 5 filtered, case 8 ms.
- `deno fmt --check src/report/run.ts tests/report/run.test.ts` — PASS, 2 files.
- `git diff --check` — PASS.

Расширен один существующий pure report case: проверены распознанный Quarto inspect, fallback Deno/Pandoc, legacy missing timing, настоящий ноль, NaN/Infinity/negative, unknown status, SIGTERM, parent identity, порядок известных/неизвестных интервалов, отсутствие private argv/cwd/script/token, empty receipts, partial/nonadditive текст. Его прежние assertions summary event deltas/executor totals также прошли. Case использует существующий off writer и не запускает trace/native; новую таблицу он проверяет через чистый formatter. Подключение formatter к existing normalized writer проверено по Source, полный export pipeline не запускался.

Новых native children, render/inspect runtime calls, общей матрицы, benchmark, install/network/build, full-tree hash, повторов зелёных cases и изменений historical capture нет. Commit/push/main state не выполнялись. Native readiness и wall/CPU стоимость этой Markdown дельты отдельно не измерены; ускорение UNKNOWN. Независимый review выявил три конкретных края первоначального formatter: произвольный ID мог раскрыть путь/токен, findIndex мог принять аргумент другого script за Quarto, timing domain/scope не проверялись перед wall подписью. Все три исправлены в том же production файле только на Markdown boundary. В том же existing case добавлены meaningful negatives: private process/parent IDs, fake quarto.js после other.ts, unknown Deno prefix, чужие domain/scope и missing timing semantics. Новые protocol validators или FS reads не добавлены.

После исправления по явному возобновлению root выполнен один affected compiler и тот же изменённый case в `timeout 28s` (остаток общего cap 30 s): оба roots PASS; case PASS 8 ms, 1 passed/0 failed/5 filtered; fmt 2 files и diff PASS. Shell exit 0, elapsed 0.951 s; вместе с прежним check budget использовано около 1.899 s. Это повтор из-за нового Source diff, не retry неизменённого результата. Финальный diff и evidence переданы независимому reviewer.
