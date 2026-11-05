# Стоимость затронутого Core пути

До изменения: Native operation делала all-owner snapshot (обход, чтение/хеширование), проверки source/runtime на границах. Code-project acquisition отдельно делал owner inspect, copyOwner с чтением всех source bytes, инструментирование YAML и inspect каждого synthetic QMD. Внутри native inspect выполнялись дополнительные стадии project discovery/metadata; число внешних вызовов не равно стоимости.

После изменения: реальный native capture по-прежнему получает состав owner и metadata настоящих rendered QMD через Quarto hooks. Namespace acquisition не вызывает subprocess/inspect и не создаёт файлов. Один cache parsed YAML на операцию; один memoized inherited state на directory/format. Plan обходит только выбранные source trees; ordinary/assignment privacy дополнительно читает физические identities объявленных ролей для отказа private aliases. Это назначение dev/ino связано с privacy фактически выбранного файла, а не с доказательством неизменности source.

Прямые и косвенные calls: withProjectContracts/withProjectContexts → operationProjectContextReuse → acquire → projectLoader → YAML reads/in-memory merge → decodeProjectValue → planProject → projectContextFiles/readProjectContextFile → materializer/Pandoc writer. Runtime acquisition использует существующий native capture catalog; loader сам defaults/groups/dotenv не выбирает. Consumer PL вызывает batch один раз для нужных code directories. Download получает конечные bytes и строит ZIP; source owner в новом Core не сканирует.

Ограниченные измерения, Quarto1.11.6 на этой машине:
- Два code leaf, два selected profiles, shared recursive include + directory include + format overlay: 7 YAML reads, 904bytes,29ms, source bytes0. Повторный include разбирается один раз. Это измерение namespace fixture, не всего курса.
- Настоящий book render,2YAML,1реальный QMD, один code directory, student/full materialization:18.366s всего процесса fixture. Loader2YAML;11selected source reads/171bytes на оба варианта,4student outputs. Внутри writer2Pandoc subprocess. Финальный ordinary privacy regression также проверяет starter/private hardlink и actual native relocated resource alias; `ProjectPlanMetrics.treeWalks` теперь считает реальные selected root walks, включая role collector, вместо прежнего нуля. Не сравнение ускорения с прежним кодом.
- Compiler affected public graph и новые regressions выполнялись отдельно. Полный курс, весь Performance phase coverage, Docker/UI/production здесь не проверялись. Старые неизменные R0–R6/A1–A5 не повторялись ради статуса.

Сравнительный full-course speedup не заявлен. Межрепозиторная actual installed композиция и типизация consumer проверяются интегратором после exact commit handoff.
