# Performance: итог публичного summary после удаления backend copy

Итоговый срез19:34 Europe/Minsk, 4 ноября. Последний прямо разрешённый bounded шаг завершён; Source и дальнейшие опыты заморожены до совместного обсуждения. Других запусков/fix не назначено.

**Публичный summary на прежнем tiny QMD работает после маленького Source fix.** Один полный invocation2.706с изcap6с, CLI/Native exit0, completion complete, dataValid true, stdout/stderr EOF. HTML побайтно совпадает с сохранённым прежним Native HTML; повторного direct/off рендера не было. RunId `6541d83d-809d-4151-adf2-1637c2d41074`.

## Изменение и приёмка

Единственный Source writer изменил только `performance-lifecycle/src/launchers/setup.ts`: убрано копирование src/bridge/node_modules/config/registry в backend, использован настоящий установленный module root через существующий assetPath. Обычные Deno imports/node_modules и package-manager links больше не проходят через наш file copier. Generated launchers и изменяемые Pandoc data/init остаются в workspace. Нового resolver/registry/proof и манипуляции links нет. Установленные Performance/Quarto и другие продукты не переписываются.

Independent Source review — **Scoped APPROVE**, findings0 до исполнения. Последующий independent evidence review — **APPROVE_BOUNDED_RUNTIME_EVIDENCE**, findings0; reviewer только читал Source/сохранённые outputs, дополнительных запусков/правок не делал. Это ограниченная приёмка данного публичного пути, не всей Performance.

Compiler graph остаётся **blocked/UNKNOWN на отсутствующем npm:@types/node**, не PASS. Первая ошибка CLI --cached-only0.006с сохранена; единственная исправленная --deny-import проверка началась и остановилась на type dependency0.339с. Два intervals0.345с в том же graphcap8. Новых type stubs/dependencies/checks не вводилось. Последнее прямое уточнение отдельно разрешило публичный synthetic runtime без compilerPASS; этот gate не снимался молча.

## Что реально наблюдается

| Уровень измерения | Значение | Смысл |
| --- | ---: | --- |
| Полный Python invocation, Bash time | 2.706с | Включает fixture preparation, CLI, publication, cleanup; cap6 |
| CLI interval внешнего wait4 harness | 2.671036704с | От child spawn до exit с polling10мс |
| Native receipt wall | 1.031581260с | resource-helper-envelope, exit0 |
| Command wall | 1.066752697с | Отдельный статус command,exit0 |
| Driver observed wall | 1.081932552с | Собственная область наблюдения lifecycle |
| Quarto render launcher interval | 0.972313960с | Inclusive child-spawn-to-status |
| Pandoc launcher interval | 0.212143249с | Вложенный в Quarto, parent link confirmed |
| wait4 CPU user+system | 3.127389с | CLI/preparation и учтённые descendants |
| wait4 peak RSS | 653668KiB (около638.3MiB) | Максимум отдельного процесса, не сумма памяти дерева |
| Python observer CPU | 0.038015с | Стоимость внешнего polling/сохранения snapshots |
| Python process high-water RSS | 14068KiB | Не прирост памяти observer |

Inclusive интервалы Quarto/Pandoc нельзя складывать или называть self-time/критическим путём. Разность полного2.706с и Native receipt1.031581260с≈1.674с включает preparation/probes/запуск/нормализацию/публикацию/оболочку; это не выделенная стоимость observer и не статистическая оценка instrumentation overhead. Сам Native CPU/RSS остался null/resourceScope unavailable, поскольку GNU time отсутствует; внешние metrics не подставлены вместо него. Высокий peak RSS относится к полному CLI с preparation; источник пика по этому опыту не разделён.

Реально запущены1TS и2Lua executors: имеются started receipts и корректные summary каждого. На standalone QMD нет instrumented author extensions, поэтому operation/counter events отсутствуют (`summary.operations=[]`, `counters=[]`). Нормализованный `coverage.observed.ts/lua=false` соответствует отсутствию clock events в `src/analysis/normalize.ts:438`, **не отсутствию agent activation**. `coverageComplete=false`, errors/dropped/incomplete0. Внутренние фазы Core/PL/QRC и полного курса не измерены. Публичный render не даёт `quarto.filter.run`; private adapter/новая разметка не вводились.

Практический результат для следующего анализа: обычный путь summary теперь может передавать собственные модули без полной копии дерева и даёт достоверные границы команд. Для поиска фазового bottleneck нужны существующие реальные operation events авторских расширений, а не приписывание времени пустой сводке. Новые опыты/инструментация в этом завершённом эпизоде не начинаются.

## Учёт всех расходов и пределы

Первый failed summary полныйwall≤15с (точный fullwall не измерялся), второй failed summary1.607с округлён безопасно вверх1.608с, последний2.706с округлён вверх2.707с. Консервативно **≤19.315с из неизменного cumulative summarycap23с**. Старые failures сохранены, не переименованы в baseline. Последний cap6с соблюдён; повторов после результата0.

Offline install failure0.117151116с + отдельно разрешённая network install0.599202893с = measured setup intervals0.716354009с из того жеcap20с. Setup wrapper overhead отдельно не timed. Ранее разрешённый максимум setup20+summary23=43с не увеличен. Graph0.345с/cap8 — отдельно явно разрешённая Source qualification. Предыдущие direct/off2.946977501с/cap24, grader3.216179068с/cap22 и исторические failed TERM бюджеты не сброшены/не повторялись.

## Сохранённые доказательства

- [Final result](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-module-root-20261104-evidence/final-summary-result.json), [проверки сохранённой выдачи](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-module-root-20261104-evidence/final-evidence-checks.json), [полное время](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-module-root-20261104-evidence/final-fullwall.txt).
- [RunRecord](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-module-root-20261104-evidence/runs/6541d83d-809d-4151-adf2-1637c2d41074/run.json), [summary](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-module-root-20261104-evidence/runs/6541d83d-809d-4151-adf2-1637c2d41074/summary.json), [coverage](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-module-root-20261104-evidence/runs/6541d83d-809d-4151-adf2-1637c2d41074/coverage.json), [публичный report](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-module-root-20261104-evidence/runs/6541d83d-809d-4151-adf2-1637c2d41074/report.md).
- [Source closure и вся история проверки](runtime-performance-module-root-20261104.md), [setup и прежний corrected failure](runtime-performance-summary-correction-20261104.md).

Source fix frozen dirty/uncommitted на базе9ba1fd87eb8f5da15fdad29c6c74bc0030b96e92; один файл. Compilerblocked, full-course/speedup/TERM не квалифицированы. Core/PL/Platform/Gateway/VM/live DB/config не менялись, production/deploy/publication нет. Основной release state не редактировался; сообщений другим чатам нет. Все WIP/старые outcomes/data сохранены.
