# Performance: ограниченный runtime — 4 ноября 2026

Область продуктового владения: только `performance-lifecycle/`; исходный clean
HEAD `9ba1fd87eb8f5da15fdad29c6c74bc0030b96e92`. Старый `performance/` с failed
TERM WIP заморожен; его изменения не читались как принятый runner и не переносились.
Независимая Source-рецензия и affected compiler уже пройдены; ради статуса они
не повторялись. Конфигурация модели не выдана за подтверждение runtime.

## Предел, записанный до запуска

Один публичный сценарий: обычный рендер одной QMD в HTML без extensions и
вычисляемых ячеек. Один direct Native и один публичный CLI
`render --perf-mode off` используют одинаковый исходник в разных новых каталогах.
Пара нужна для оценки стоимости наблюдения; единичная пара не устанавливает
статистическое ускорение и не требует сборки курса.

Предел wall: direct Native 8с, публичная оболочка 12с, не более 2с cleanup на
каждый вызов; общий cap 24с. Повторов нет. Превышение, unknown или incomplete
сохраняются как исход. Watchdog предназначен только для ограничения стоимости;
его аварийный путь не является проверкой восстановления после TERM.

До реального Native обнаружена ошибка подготовки внешнего наблюдателя:
`/usr/bin/time` отсутствует, `Popen` вернул `FileNotFoundError` до запуска процесса.
Сохранены исходный harness и `setup-failure.json`; Native 0/wrapper 0. Подготовку
исправили на Linux `wait4` для внешних CPU/RSS без установки зависимостей.
Продуктовый код самостоятельно использовал штатный fallback с недоступными
resource metrics. Исторический TERM budget не сбрасывался.

## Фактический исход единственной пары

| Наблюдение | Direct Native | Performance CLI off |
| --- | ---: | ---: |
| Внешний wall, с | 1.183158984 | 1.763818517 |
| Код внешнего процесса | 0 | 0 |
| CPU user, с, wait4 | 1.094526 | 1.999218 |
| CPU system, с, wait4 | 0.126931 | 0.205394 |
| Пиковый RSS, KiB, wait4 | 169744 | 172612 |
| CPU внешнего наблюдателя, с | 0.003614 | 0.027733 |

Пара заняла **2.946977501с / cap 24с**, без timeout, сигналов и повторов.
На 0.745660011с жизни wrapper наблюдался сохранённый
`incomplete/not-finalized`, Native null, dataValid false. Итог того же runId
`df1f91c0-8c85-4b56-8f0e-d4c034819847` — **complete**, Native code 0/signal null,
отдельный command code 0/signal null, оба owned log channels EOF, capture separate,
dataValid true, ошибок наблюдения нет. `run.json`, `summary.json` и `report.md`
согласованы. HTML direct и wrapper совпал побайтно.

Внутри оболочки Native receipt хранит wall 0.964723816с с областью
`resource-helper-envelope`; command wall 0.999550591с, driver wall 1.010289599с.
Native CPU/RSS честно null, `resourceScope=unavailable`: GNU time отсутствует.
Внешние метрики `wait4` относятся ко всему соответствующему дереву вызова;
их нельзя выдавать за чистые метрики Quarto внутри Performance. Фактическая
версия Quarto из RunRecord — **1.11.5**, прежняя запись среды 1.11.6 к этому
запуску не приписана.

Разность wall пары **+0.580659533с**, CPU **+0.983155с**, пикового RSS **+2868KiB**.
Внутри единственного wrapper внешняя оболочка сверх его собственного Native
receipt заняла 0.799094701с. Это разовая оценка расходов запуска, inspect,
наблюдения и публикации; порядок direct→wrapper и прогретый кэш не позволяют
называть её устойчивым процентом накладных расходов. Внешний observer с polling
10мс потребил 0.027733с CPU на wrapper, peak RSS 14116KiB; wall resolution около 10мс.
Пиковые RSS не складываются и разность пиков не является памятью одной функции.

## Граница подтверждения

Подтверждён обычный публичный `off` путь: предварительный incomplete и конечный
complete, разделение Native/command/внешнего статуса, EOF и единая выдача без
изменения HTML. Завершение после внешнего TERM, observation failure, stubborn
teardown, summary/cpu/trace и полноценные курсы **не квалифицированы**. Исторические
TERM failures не объявлены PASS. Положительный обычный путь не доказывает
отрицательные ветви нового lifecycle.

11 адресных проверок сохранённых публичных результатов прошли без нового
Native запуска; `public-pair-checks.json`. Это проверки JSON/HTML/report, не
повторная compiler/test матрица. Продуктовых изменений 0, commit/push 0.

Evidence: `reports/runtime-performance-20261104-evidence/`; исходники обоих
fixture, команды/окружение назначения, выводы, initial snapshot, Native receipt
и конечные отчёты сохранены. Production/VM/liveDB/auth/deploy/global config
не затрагивались. Основной state не менялся.

## Следующий адресный Platform анализ

Координатор согласовал один invocation `executeScenario off`:
`python3 -B -m unittest discover -s tests -p test_plain_runtime.py -k correct_answer -v`,
cwd `/home/tolya/JavaCodex/java-release-20261110/platform`.
Это настоящий synthetic correct42 Java/JUnit case без mocks, ожидается score 1,
штатный `junit-xml`, без execution inventory. **До запуска записан cap 22с:
20с outer и не более2с cleanup, один invocation, без retry и полной matrix.**
Platform остаётся у другого writer; точные HEAD и changed paths сохраняются
перед/после как dirty candidate, изменения не объявляются новым commit.


### Фактический адресный результат Platform

Один запуск завершён без timeout/retry за **3.216179068с / cap 22с**, outer exit 0.
`unittest` сообщил **Ran 1 test in 2.928s, OK**: публичный fixture проверил score 1,
passed 1, `reportFormat=junit-xml` и отсутствие `executionEvidence` на настоящем
synthetic Java/JUnit correct42. Исходный fixture использует настоящий JDK25 и
`grader.grade`, без mocks. Созданные им temp grading outputs удалены его штатным
cleanup; необработанный XML этим измерением отдельно не сохранён.

Единый RunRecord `68174101-d55f-40f3-b8a2-911541b10996`: **complete**, Native
code 0/signal null, `direct-status`, wall 2.999461143с, `direct-spawn-to-status`.
Command code 0, stdout/stderr EOF, capture separate, dataValid true, errors[].
CPU/RSS Native остались null, `resourceScope=unavailable`. Summary и report
согласованы; новые 9 адресных assertions сохранённых outputs прошли без повторного
исполнения.

Внешний `wait4` для всего Performance invocation: CPU user 4.469907с,
system 0.285308с, всего**4.755215с**; peak RSS**137892KiB**. CPU больше wall
допустим при нескольких потоках Java. Внешний observer: CPU 0.009656с,
peak RSS 13988KiB. Разность внешнего wall и Native direct status:
**0.216717925с**; она включает запуск Deno, подготовку/публикацию результата и
дискретность внешнего observer. Это один запуск без direct control пары для
Platform, поэтому точный причинный процент overhead не установлен.

Практический вывод этого узкого измерения: сама полезная проверка заняла 2.928с
из 3.216с полного наблюдаемого вызова; повторные запуски этой же проверки ради
статистики не нужны. Разбиение затрат между javac, JUnit adapter и XML reader
не наблюдалось; на этом основании менять их ради ускорения нельзя. Весь пакет,
ошибочные student cases, Docker/Gradle image и service/SQL этим опытом
не квалифицированы; их проверки принадлежат Platform writer.

Source — **dirty candidate** на HEAD
`6491517b896b17119fd2ab44db1a2de2ff9c93b1`, другой writer был активен. Точные
изменённые/удалённые/untracked paths до и после находятся в
`platform-source-before.json` / `platform-source-after.json`; списки совпали.
Совпадение списков не доказывает неподвижности bytes. Исходный SHA не выдан за
новый commit, дополнительный hash/inventory framework не вводился.

Performance product остаётся clean на 9ba1fd8; product fixes 0, commit/push 0.
Малый public Quarto pair и адресный Platform invocation завершены. Дальнейших
runtime запусков в этом пакете не назначено. Независимую рецензию организует root.

Примечание к памяти: `wait4.ru_maxrss` — максимальный resident set отдельного
процесса в учтённом вызове, а не сумма одновременной памяти всего дерева.
Суммарное потребление памяти дерева здесь не измерялось.

## Дополнительный публичный summary — 19:02

По новому прямому поручению выполнен ровно один instrumented-mode опыт на том же synthetic QMD, с заранее сохранённым общим cap15с. Прежний direct/off pair и grader не повторялись. **CLI exit2 за1.714872273с до Native render:** у этого source checkout отсутствует обязательный `node_modules`, diagnostic setup не завершён. RunRecord/summary/coverage не созданы; фазовое покрытие не измерено, не0 и неPASS. Зависимости не устанавливались, обход/новая обвязка не вводились; повторов нет.

wait4 CPU1.929704с, peak RSS651912KiB; внешний observer CPU0.018454с и process high-water RSS13932KiB. Это ресурсы неуспешной подготовки, не Native metrics и не overhead успешного render. Внешний timeout15с не сработал; полный wall Python отдельно не измерялся. Подготовка diagnostic предшествует initial RunRecord, поэтому этот failure не получил единого lifecycle отчёта. Product clean9ba сохранён.

[Полный результат, Source причина, независимая рецензия и следующий ограниченный шаг](runtime-performance-summary-20261104.md). Публичный summary не предоставляет `quarto.filter.run` и требует доступной ручной разметки; перед анализом фаз курса нужна обычная готовая поставка Performance, а затем фактически наблюдённое покрытие, не ещё один registry/framework.

### Однократная штатная offline correction — 19:11

По последующему прямому поручению проведена одна обычная `deno install --no-save --cached-only --frozen --node-modules-dir=auto` только для declared typescript5.9.3/luaparse0.3.1, с cap20с. Exit1 за0.117151116с: `npm:typescript was not found`. Version directories в cache есть, но resolver не смог разрешить пакет; этого недостаточно для подготовки. По first-newfailure-stop никаких повторов/другого manager/ручных symlinks нет. Условный corrected summary cap8с не запускался. Manifest/lock побайтно прежние, product clean. Старый summary fullwall≤15с сохранён, разрешённый combined cap23с и общий setup+summary максимум43с не выданы за фактический wall. [Полный исход](runtime-performance-summary-correction-20261104.md).

### Последующее разрешение сетевой подготовки — 19:17

Одна прямо разрешённая сетевая deno install в остатке того же setup20с успешна0.599202893с (offline+network measured install sum0.716354009с). Объявленные версии установлены, Source/manifest/lock прежние. Единственный разрешённый corrected summary fullwall1.607с при cap8 завершился CLIexit2: существующий copier отвергает обычные Deno directory symlinks `node_modules/typescript`. Native render/phase observation не начались. Повторов/linker changes/manual materialization/Source changes нет. Старый fullsummary≤15с сохранён; combined summary safe rounded upper bound≤16.608с/cap23, setup20с отдельно, overallmax43. Подробный report содержит ресурсы и Source anchors. Это не successful instrumentation и не замер фаз курса.

### Удалена backend module/dependency copy — 19:30

Прямо порученный ограниченный Source fix изменил только `src/launchers/setup.ts`: appDenoArgs/tool/agents/bridge теперь используют существующий installed assetPathroot; copy-all src/bridge/node_modules/configs удалена. Mutable launchers/Pandoc data по-прежнему workspace. Один writer, independent scoped Source APPROVE до execution. Performance теперь dirty на прежнейбазе9ba, безcommit; прежние clean assertions относятся к ранним измерениям.

Один targeted graph cap8с завершился exit1/fullwall0.006с **до compiler**: coordinator указал unsupported `deno check --cached-only`. Firstnewfailure-stop, исправленныйprefix/другиеtests/summaryпослеfixне запускались. Source reviewed, compiler/runtime не доказаны. Setup0.716354009/20 и oldsummaryfull<=15+secondfull1.607rounded сохранены; остаток<=6.392с из23 не расходуется безgraphPASS. [Полный Source closure/review/остаток](runtime-performance-module-root-20261104.md).

Последующая прямо разрешённая одноразовая correction CLI в остатке **того жеcap8с** использовала фактически поддерживаемый `--deny-import` вместо cached-only, inputs/Source прежние. Compiler начал Check, но exit1/fullwall0.339с: missing `npm:@types/node` in node_modules. С первоначальными0.006с вместе0.345с/cap8. Новых dependencies/подмен declarations/Source edits/install/retry нет; firstnewfailure stop. Graph PASS не получен, summary послеfix NOT RUN, остатокsummary6.392/23 не использован. Точные logs/result сохранены в module-root evidence; runtime/phase qualification не заявляется.

## Окончательный публичный summary — frozen итог19:34

Последнее прямое уточнение сняло compilerPASSgate только для одного synthetic runtime; compilerblocked@types/node сохранён. Единственный public summary после Source fix завершён **complete/dataValidtrue**,CLI/Nativeexit0, fullwall2.706с/cap6, HTML равен прежнемуNative. Подтверждены Quarto0.972313960с и вложенныйPandoc0.212143249с inclusive, TS1/Lua2started+summaryfiles. Operation/counterevents0, coverageCompletefalse; внутренниефазыкурса не измерены. Independent evidenceAPPROVE, findings0.

Source fix (единственный setup.ts) и результат frozenбезcommit, другихзапусков/fixнет. Summarycumulativeupper≤19.315с/cap23 с обоимипрежнимиfailures; setup0.716354009/20 иgraph0.345/8 отдельнонеобнулены. ObserverCPU0.038015с, wait4CPU3.127389с, peak653668KiB не суммированнаяпамятьдерева. [Полный человеческий итог/границы](runtime-performance-module-root-result-20261104.md).
