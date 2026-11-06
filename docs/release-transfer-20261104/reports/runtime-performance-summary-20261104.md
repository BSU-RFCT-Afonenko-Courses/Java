# Performance summary: проверка публичного режима 4 ноября

Срез19:02 Europe/Minsk. Прямое поручение — read-only проверить уже существующий режим для наблюдения фаз и, если маршрут имеется, выполнить ровно один дополнительный instrumented-mode опыт на прежнем малом синтетическом QMD, cap15с включая overhead/cleanup. Прежние direct/off pair и grader invocation не повторялись. VM/Docker/network, TERM/recovery/signal experiments не выполнялись.

**Результат: подготовка summary остановилась до Native render. Фазы не измерены.** CLI exit2 за1.714872273с, timeoutfalse, retry0. Ошибка:

```text
No such file or directory (os error 2): readdir '/home/tolya/JavaCodex/java-release-20261110/performance-lifecycle/node_modules'
```

## Что установлено чтением Source

Публичный `render --perf-mode summary` уже есть в `src/cli/arguments.ts` и `src/runner/diagnostic.ts:prepareRenderDiagnostic`; он использует Deno preload и Pandoc init, не переписывая авторские пакеты или системный Quarto. `quarto.filter.run` намеренно недоступен в этом простом пути (`experiment-only-private-adapter`). Summary собирает только доступную ручную разметку: наличие зарегистрированных операций не доказывает их выполнение. У standalone QMD без instrumented extensions возможна пустая сводка, но до фактического завершения это нельзя объявлять нулевым покрытием.

В `src/launchers/setup.ts:30–33` обычная подготовка копирует `src`, `bridge` и `node_modules` из самого Performance checkout. `docs/development.md` прямо требует предварительно подготовить `node_modules/typescript` и `node_modules/luaparse`. В данном release checkout каталога нет. Публичный CLI был доступен через прежний cached Deno маршрут, но готовность полной diagnostic поставки этим не подтверждена. Обнаруженный предел относится к **этому неподготовленному source checkout**, а не доказывает дефект правильно установленного продукта.

`src/runner/render.ts:110–113` вызывает diagnostic preparation до первоначальной записи `run.json` в строках204–211. Поэтому этот setup failure оставил stderr и частичный external staging, но не RunRecord/summary/coverage. Capability probes Quarto/Deno/Pandoc запускались; основной `quarto render` не начался. Для анализа полного курса это важное ограничение: successful off не означает готовность summary, а preparation failure сейчас не даёт того же единого результата, что обычный Native lifecycle.

## Единственный опыт и стоимость

Использован существующий Python `measure`/Linux `wait4` harness прежней пары; из него убраны оба старых invocation, оставлен один summary. Нового framework, author instrumentation и product code нет. QMD побайтно совпадает с прежним синтетическим QMD. HTML не создан, эквивалентность выдачи не проверена.

До запуска сохранён [plan](runtime-performance-summary-20261104-evidence/plan.md). Child watchdog12с + cleanup до2с + harness allowance1с. Внешний `timeout --signal=KILL 15s` завершился supervisor exit0, то есть не сработал; это не успех CLI, который вернул2. Полный wall Python отдельно не измерялся; измеренный1.714872273с относится к интервалу `measure()` и включает CLI preparation/probes, но исключает предварительную запись Python fixtures. [Invocation и предел измерения](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-summary-20261104-evidence/invocation.json).

| Показатель | Реальный исход |
| --- | ---: |
| CLI wall, включая diagnostic preparation | 1.714872273с |
| CLI exit / timeout / повторы | 2 / false / 0 |
| wait4 CPU user + system | 1.691690 +0.238014 =1.929704с |
| wait4 peak RSS | 651912KiB (около636.6MiB) |
| Внешний Python observer CPU | 0.018454с |
| Python process high-water RSS | 13932KiB |
| Poll interval | 10мс |
| Native render / phases / HTML | не запущен / не измерены / отсутствует |

wait4 peak RSS — high-water учтённого child/process, не сумма одновременной памяти дерева. Python RSS — high-water всего процесса, не прирост от наблюдения. Отдельная wall-стоимость Python observer не выделена;1.7149с не является инструментальным overhead успешного render. CPU может превышать wall при нескольких процессах/потоках. Memory preparation заметна относительно малого QMD, но какой внутренний шаг дал этот пик, по этому опыту не установлено.

Сохранены [исходный wrapper outcome](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-summary-20261104-evidence/wrapper-outcome.json), [stderr](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-summary-20261104-evidence/wrapper.stderr), [результат и границы](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-summary-20261104-evidence/result.json), harness и частичный staging. RunRecord, summary, coverage и initial snapshots отсутствуют. Их поля/статусы не выдуманы. `phaseCoverage=unmeasured`, не0 и неPASS.

## Полезность для следующего ограниченного анализа

Сейчас нельзя назвать фазовые узкие места Core/PL/QRC, причины стоимости полного курса или speedup. Отдельные Source commits Core010ba917909fa7d44179d4a1a05291f88f061775 и PL1ad5516e543d07804aa09e426302413c21542e3e и успешный tiny wholebook PL/QRC основного чата не измерялись этим запуском; checkout не менялись.

Перед следующим измерением нужен штатно подготовленный/установленный Performance с его обычными dependencies. Создавать собственные modules/phase wrappers или новый validator/registry для обхода отсутствующей поставки не требуется. После подготовки новый bounded опыт должен сначала подтвердить реальные observed agents/phase spans, их `coverageComplete/dataValid` и расходы подготовки, затем решать, какие фазы заслуживают адресного анализа. Сам `summary` простого render не обещает внутренний `quarto.filter.run`; это предел уже имеющегося API, а не основание молча патчить Quarto.

Этот единственный дополнительный опыт завершён failure и **не повторяется**. Старые2.946977501с/cap24 и grader3.216179068с/cap22 остаются отдельными принятыми bounded outcomes; их бюджеты/исторические TERM failures не сброшены. Performance product остаётся clean на9ba1fd87eb8f5da15fdad29c6c74bc0030b96e92, новых fixes/commit нет.

Дополнение19:11: после этого отчёта прямо разрешены одна штатная offline setup-попытка cap20с и условный corrected summary cap8с с общим summary расширением15→23с. Обычный Deno install выполнен один раз и завершился exit1 за0.117151116с: `npm:typescript was not found` для объявленного5.9.3. Первый новый failure остановил поручение; corrected summary не запускался. Manifest/lock/Source не менялись, retries0. [Точный исход и бюджеты](runtime-performance-summary-correction-20261104.md).

Дополнение19:17: отдельно разрешённая сетевая подготовка тем же manager в остатке **того же setup20с** успешна0.599202893с; оба install subprocess intervals вместе0.716354009с. Dependencies и manifest/lock проверены. Затем единственный corrected summary fullwall1.607с из cap8 вернул CLIexit2 до Native render: runtime copier отверг штатный Deno directory symlink `node_modules/typescript`. После нового failure дальнейших попыток/Source правок нет. Фазы не измерены. Старый fullsummary≤15с + corrected1.607с дают безопасный rounded upper bound≤16.608с из суммарногоcap23. [Обновлённая Source причина и полные расходы](runtime-performance-summary-correction-20261104.md).

Независимый `/root/ordinary_gradle_publication_review` read-only подтвердил корректность failure evidence и границ CPU/RSS/coverage; сам ничего не запускал. Уточнение reviewer о cap15 учтено отдельным `invocation.json`: внешний supervisor подтверждён завершением exec session73988, полное wall не выдано за измеренное. Это приёмка описания failure, не successful instrumentation qualification.
