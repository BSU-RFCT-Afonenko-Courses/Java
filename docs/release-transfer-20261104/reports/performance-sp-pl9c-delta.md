# Один staged SP delta: PASS / VALID

2026-11-04 00:19–00:20 Minsk. Один запуск, outer cap 120s, TERM/KILL grace 5s, retry 0; exit 0. Performance `924b306cfa34c7ce0f79ca4050d045b6d4cb3e29`, SP base `3bb0e0789545420b9947bdd85fac3d71715d0e36`, exact PL archive `9c62960ff345552dad492d8cc560dc6760787ec5`, installed Core `d7cc10eaa21fa35bbe32d73cc4afa216002393da`. Только собственная staging copy; shared SP/Java, прежние generated results и исходные каталоги сохранены. Сеть, install и Deno.Command override не использовались.

## Время и границы

| Измерение | Секунды |
|---|---:|
| Весь outer capture, включая staging | 40.931 |
| Staging preparation | 0.491 |
| Existing launcher preparation | 0.268 |
| Existing executeScenario + report | 39.925 |
| Native command wall из executeScenario | 39.798 |
| Report отдельно | UNKNOWN |

Это warm persistent XDG cache с тремя прежними reference catalogs на прежних relative paths; ignored native scratch в staging не копировался. `repeat-render` — выбранная existing cache policy, не новая off baseline. Старый `installed-packages.json` оставлен как BASE provenance; actual PL override указан в stage metadata и capture manifest. Это не regeneration descriptor/closure и не installed/release acceptance.

Все 107 existing process receipts содержат конечный `native_command.wall_seconds`, exit 0, signal null. Каждый интервал использует локальный `monotonic_process` clock, scope `launcher-spawn-to-status`, включая наблюдаемый launcher bookkeeping/I/O forwarding. Parent/child интервалы перекрываются: их нельзя складывать или называть CPU, startup и global critical path. Resource CPU/RSS остаются null; отдельного report timing API нет. Standalone SIGTERM issue из старого ad9 repro остаётся UNKNOWN; этот успешный capture не проверяет отмену.

| Категория | Count | Min / median / max, s |
|---|---:|---:|
| Quarto CLI run | 12 | 0.348 / 0.455 / 39.747 |
| Concrete TS entrypoint | 18 | 0.038 / 0.137 / 39.425 |
| Quarto CLI render | 1 | 20.078 / 20.078 / 20.078 |
| Quarto CLI inspect | 3 | 0.611 / 1.370 / 1.391 |
| Quarto CLI pandoc | 33 | 0.288 / 0.299 / 1.069 |
| Pandoc document render + Lua | 7 | 0.897 / 2.187 / 3.071 |
| Pandoc Markdown → JSON | 14 | 0.0035 / 0.0052 / 0.0117 |
| Pandoc JSON → GFM | 7 | 0.012 / 0.022 / 0.025 |
| Pandoc JSON → HTML | 12 | 0.006 / 0.202 / 0.418 |

Крупнейшие индивидуальные envelopes — root Quarto run 39.747s, его export-course TS child 39.425s, вложенный Quarto render 20.078s и документный Pandoc 3.071s. Это адреса для дальнейшего чтения уже сохранённого trace/causal, а не доказательство причины задержки или стоимости старта. Например, median Quarto pandoc 0.299s и median leaf conversion имеют разные наблюдаемые границы; их разность не выделяет чистый startup.

## События и сохранённый результат

VALID: dropped 0, incomplete 0, errors `[]`. Coverage false означает заявленную частичную семантическую область, а не ошибку потери событий. 81 executor JSONL содержит 5,214 nonempty event rows; canonical — 20 завершённых spans / 40 rows, 0.022537s inclusive TS wall, self null. В старом saved capture было 81,540 rows и 38,181 canonical spans / 76,362 rows. PL9c изменяет границу canonical instrumentation на внешние вызовы; рекурсивную работу не следует считать исчезнувшей. Ни elapsed speedup, ни tooling overhead delta из этой пары не доказаны.

Пять крупнейших semantic aggregates, все inclusive TS `monotonic_process`, self null: exportCourse 1×6.719s; export.command 12×6.580s; exportPrairieLearn 5×4.886s; reference-catalog.process.quarto 2×1.445s; Core process.command 1×0.659s. Lua CPU отдельно: Core facts 7×0.027324s, uses 14×0.024053s, walk 14×0.023818s. Эти перекрывающиеся aggregates не складываются и не объясняют Java 900s.

Public composition: 7 questions, 7 grading payloads, 5 works, 1 course instance, 105 file rows; manual 7, upload 6, workspace 1. Все 7 question UUID и 5 assessment UUID совпадают с сохранённым первым SP capture. Native reservation sealed, один child used/posted, 7 documents / 7 outputs / 7 releaseDocuments и 7 HTML; точные paths записаны в analysis.json.

## Артефакты

- [Existing Performance report](performance-sp-pl9c-delta/runs/a4ac5976-790a-4160-bc40-45794995dea7/report.md), [summary](performance-sp-pl9c-delta/runs/a4ac5976-790a-4160-bc40-45794995dea7/summary.json), [wall trace](performance-sp-pl9c-delta/runs/a4ac5976-790a-4160-bc40-45794995dea7/wall.trace.json), [causal/process receipts](performance-sp-pl9c-delta/runs/a4ac5976-790a-4160-bc40-45794995dea7/causal.json).
- [Analysis: exact counts, UUIDs, categories and process IDs](performance-sp-pl9c-delta/analysis.json), [stage provenance](performance-sp-pl9c-delta/stage-metadata.json), [capture timings](performance-sp-pl9c-delta/capture-timings.json).
- [Exact outer command/outcome](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-sp-pl9c-delta-execution.json), [log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-sp-pl9c-delta.log), [existing API driver](performance-sp-pl9c-delta.ts), [staging driver](performance-sp-pl9c-delta.py). Native argv saved in run.json; selected explicit env in driver: persistent `XDG_CACHE_HOME`, `QUARTO_RUN_NO_NETWORK=true`, `DENO_NO_PACKAGE_JSON=1`, plus existing prepareLaunchers diagnostic env.

Адресное последующее чтение: [33 пары Quarto pandoc → Pandoc, Source callers и границы кандидата](performance-pandoc-layer-reading.md), [точные пары](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-sp-pl9c-delta/pandoc-pairs.json).

Контрольная точка: повторных запусков, дополнительных capture, production deployment, push и PR не было. Партия завершена.
