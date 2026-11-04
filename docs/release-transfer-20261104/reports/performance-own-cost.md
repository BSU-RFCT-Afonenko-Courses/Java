# Performance: одна партия обработки сохранённых событий

3 ноября 2026. Исходный материал — существующий SP summary run `b2b5cee6-0382-4326-83e3-457b113d4562`: 81 540 events, 81 executor event files, 107 process receipts. Курс не запускался; original raw/outputs не переписывались. Origin release clone остаётся GitHub; original source не менялся.

**Подтверждённый расход и исправление.** `src/analysis/normalize.ts:validatedIntervals` после проверки `rows.length===2` копировал каждую пару `[...rows]` и вызывал общий `sort`. `writeRunArtifacts` использует matcher через `traces` и `operationTotals`: для 40 715 согласованных spans это два прохода и 81 430 ненужных pair-copy/sort операций; PL `canonical` даёт 38 181 span, то есть минимум 76 362 таких операций. Теперь пара выбирается прямым сравнением sequence; равенство сохраняет исходный порядок. Все проверки identity/executor/operation/clock/sequence/timestamp, исключение duplicates/aborted и quality остаются. Один production файл; протокол, API, агрегации, число событий и causal semantics не менялись. Общего кэша/нового DTO/validator нет.

**Проверка.** Existing `tests/report/perfetto.test.ts`: 16 passed/0 failed, 0.832 s; включает повреждённые/дублированные пары, reversed storage, equal sequence, aborted, циклы, partial, разные часы и крупный существующий fixture. Его обычный typecheck прошёл с affected graph; полный 53-file graph повторно не запускался. Formatting изменённого файла PASS. Новых зеркальных тестов нет.

Один saved-raw comparison, cap 60 s/retry 0, exit 0: `normalizeRun(readOnly:true)` читает/проверяет raw один раз; exact baseline `cff3edd` modules сохранены только в external `performance-own-cost-baseline/`, изменены лишь локальные import URLs. Старый и новый matcher/summary/trace работают на одном DTO. **Точное равенство** всех 40 715 интервалов, всех 68 operation aggregates, всей summary с counters/quality и всего trace bundle с causal links/clock metadata. VALID; drops 0/incomplete 0/errors []; coverage false сохраняется как ширина semantic scope. Сводка сравнения — `performance-own-cost-comparison.json`; script — `performance-own-cost-compare.ts`.

| Один описательный проход | cff baseline | candidate |
| --- | ---: | ---: |
| Pair matcher | 0.0550 s | 0.0449 s |
| Summary | 0.0754 s | 0.0627 s |
| Traces | 1.0250 s | 0.9879 s |

Raw read/normalize один раз 0.4624 s; весь comparison 2.8768 s. Порядок old→new/JIT/cache не контролировался, поэтому это **не доказательство speedup/overhead acceptance**; Source доказывает устранение copy/sort. Эти числа не объясняют целиком прежние 2.245 s status/normalize/artifacts. Counts/inclusive/partial/errors сохранены; TS wall и Lua CPU не складываются, self остаётся null. Существующий [отчёт SP](performance-sp-summary-capture/runs/b2b5cee6-0382-4326-83e3-457b113d4562/report.md) остаётся пользовательской точкой просмотра статистики.

Повторное interval matching у разных exporters и полные 76 362 begin/end rows `canonical` остаются: reuse потребовал бы отдельной границы API/cache, а отбрасывать события ради ускорения нельзя. Дальнейшая реализация/измерения остановлены на checkpoint. Commit отдельный; независимый review до push; автопубликации/PR нет.
