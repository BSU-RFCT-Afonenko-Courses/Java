# Performance: материал к обсуждению в15:00, 4 ноября

За утренний пакет наблюдение стало информативнее и получило более узкую область работы. Появились сохранённые промежуточные логи и измерения ранних этапов Java export. **Ускорение всего курса пока не измерено; причина прежнего900-секундного timeout не установлена.** Незавершённый recovery candidate сохранён отдельно и не принят. Новых реализаций и experiments после подготовки этого материала не начинается.

**Как меняли само наблюдение.** Из принятых путей удалены normalizer-обвязка Core/QRC с обработкой приватных blobs, повторное backend hashing/equality/receipt и обычное перечисление процессов до получения status; необходимая transition cleanup не удалялась. Event JSONL получил типизированную внешнюю границу. Это сокращение работы самого инструмента, без измеренного общего ускорения курса.

Для stdout/stderr добавлена запись по ходу исполнения. Раньше runner держал полный вывод до завершения child; при внешнем прерывании терялся диагностический материал. Теперь два бинарных потока параллельно пишутся в прежние файлы без полного накопления в памяти. Локальный behavioral control подтвердил точные bytes обоих потоков до завершения child и сохранение прежних argv, environment и exit semantics. Это обеспечило полезные prefixes последующих timeout attempts. Финализацию отчёта после TERM это изменение не обещало.

Первый адресный trace оказался слишком широким: instrumentation оборачивала также рекурсивный внутренний обход. Сохранилось101571 вызовов `visit`, потеряно93088 events, измерение признано INVALID. Это измеренный дефект качества и цена выбранного наблюдения; переносить его на причину старого900-секундного отказа нельзя.

Следующее принятое изменение добавило внутренний явный выбор top-level TS functions. Для Java выбрали только четыре функции guard/read boundary и один decoder; остальные функции продолжают исполняться без новых wrappers. Default instrumentation сохранена. Локальные execution controls проверили recursion, return/throw semantics и реальную область записанных events. Event cap не увеличивали.

Адресное чтение Lua agent показало ещё одну цену observer: в trace mode debug hook получает общий поток Lua call/return events и выполняет stack/source работу до фильтрации модуля. Для последующих попыток использовали уже существующий summary mode с ручными Lua phases, без этого hook. Его влияние на прежний timeout численно не измеряли. Summary также имеет собственную ненулевую стоимость.

**Что действительно удалось измерить.** Две разрешённые попытки с узким observer завершились по общему114-секундному deadline, сохранив полезные ранние результаты. Последняя добавляла только штатный Pandoc `--verbose`, без Quarto DEBUG, parser trace, AST snapshots или sampling profiler. Реальные log records подтвердили способность установленного Pandoc сообщать coarse filter timings; точная версия самого Pandoc осталась UNKNOWN.

| Достигнутая граница | Измеренное | Практический смысл |
| --- | --- | --- |
| Report-local archives/overlays, до Native |Около2.7s |Это подготовка disposable stage, отдельно от подготовки курса самим Native. |
| Owner inspect внутри export |25.3–26.3s wall |Крупный завершённый этап. Его необходимость определяется контрактом; избыточность пока не доказана. |
| Первые два завершённых Pandoc |Примерно7.3–7.7s wall на документ |Известны inclusive command envelopes, а не чистый startup или writer time. |
| Early TS внутри этих документов |Около0.28s wall; guard46–48ms |Видна ранняя TS boundary. Read/JSON.parse около16ms и decode около23ms вложены в guard. |
| Верхний `main.lua` |5370/5391 emitted ms |По изученному Pandoc Source — process CPU всего Lua filter action, включая встроенные Quarto passes и наши Lua entrypoints. |
| Отдельный citeproc |250/220 emitted ms |Следующий верхний filter, отдельно от main.lua. |
| Ручные Core resource facts |Около2–3ms process CPU |Подтверждён конкретный достигнутый path с одним uses scan, не стоимость всех resource/privacy branches. |

Эти строки **не суммируются**. TS, guards и child wrappers вложены в command envelopes; Lua resource phases вложены друг в друга и используют другую шкалу CPU. CPU нельзя вычесть из wall и назвать остаток HTML writer.

В последней попытке достигнут третий документ: есть Pandoc receipt, начало main.lua и early TS child. Main.lua completion и итог третьего TS отсутствуют; Pandoc прерван SIGTERM. Общие run/summary/report/tool-status не финализированы. Доступные executor summaries не имеют dropped events, однако отсутствующие summaries и pending parents сохраняют весь capture неполным. Полный128 composition и package corridors не исключались; завершение всего курса не квалифицировано.

**Где задержка локализована, а где остаётся вопрос.** Завершённый owner inspect занимает десятки секунд. Первые документы проводят несколько секунд в общем main.lua CPU envelope. Карта Source показывает внутри него normalization, shortcodes, crossrefs, layout/render/finalize и Lua contributions extensions. Early TS вызывается дочерним JSON filter из этой цепочки; его CPU не равен CPU Pandoc. Стоимость отдельных внутренних passes не измерена. Междокументная Native подготовка и другие неразмеченные фазы также остаются открытыми. Доказательства единой причины900s, процента ускорения или линейного прогноза на128 документов нет.

**Почему recovery пока не принята.** Source объясняет разницу между сохранёнными логами и потерянным финальным отчётом: pumps пишут сразу, а отчёт ждёт status и EOF; caller не владеет внешним TERM lifecycle. Дополнительно missing tool-status блокирует нынешний путь finalization. Подготовленный candidate добавляет interrupted outcome с честным UNKNOWN вместо подстановки124/143, сохраняя обычный completed API, и запрещает historical comparison неполных результатов.

Но behavioral qualification не прошла. Начальный RED подтвердил отсутствие partial report. Два старых completed/child-signal controls прошли. Новые callerTERM controls обнаружили слабую границу fixture, затем зависание; последний control остановлен watchdog. Ошибка переноса evidence между файловыми системами дополнительно маскировала underlying timeout, поэтому оставшиеся artifacts скопированы отдельно. Compiler PASS этого не исправляет. Candidate frozen/unaccepted; forwarding и partial recovery остаются неподтверждёнными. Все локальные recovery controls учтены: runtime8.75s из15s, compiler0.93s из10s, новых курсовых запусков не было.

Конкретный duplicate cleanup Core уже устранён: один cleaned AST используется для native body и return, исходный документ сохранён для resource observation. Изменение принято в556 и вошло вCore5f, чей полный CI41803137079 завершился SUCCESS. Снятие одного обхода не является измерением ускорения или объяснением всех5s main.lua; peak-memory эффект отдельно неизвестен.

**Два приоритетных варианта после совместного обсуждения.**

1. **Разобрать и закрыть маленький recovery control.** Сначала Source решение по границе caller/status/EOF и сохранности test evidence, затем один детерминированный локальный контроль с fake child. Проверять наличие partial artifacts, UNKNOWN и отказ historical comparison; Quarto/курс для этого не нужны. Тип стоимости: ограниченная работа с кодом и секунды локального execution. До успеха этот candidate не интегрировать.
2. **Адресно продолжить локализацию main.lua и междокументной подготовки.** Сначала выбрать один конкретный вопрос по уже сохранённой карте dispatch; повторный cleanup уже снят. Не возвращать общий call/return hook и не назначать полный курс просто для новых цифр. Тип стоимости: Source-разбор; новый ограниченный runtime — только после согласования вопроса и бюджета. Весь5-секундный envelope пока не разложен по внутренним passes.

Выбор дальнейшего измерения main.lua имеет смысл только после обсуждения конкретного вопроса. Широкий trace, новая матрица или повтор полного курса сами по себе не дадут гарантированной локализации.

**Ссылки на первичные материалы.** Принятые Performance изменения: streaming logs `e7267f1`, selector `c2dcf00`; Native captures использовали exact Javaa6/Core4a/PLd8, отдельно от новых checkout HEAD.

- [Streaming logs и behavioral evidence](performance-streaming-logs-20261104.md), [независимый review](performance-streaming-logs-review-20261104.md).
- [Invalid broad trace](java-current-performance-20261104.md), [TS selector](performance-targeted-transform-20261104.md), [Lua hook scope](performance-lua-trace-scope-20261104.md).
- [Узкий summary capture](java-current-guard-summary-20261104.md), [последний verbose capture и saved records](java-native-filter-summary-20261104.md), [карта main.lua](java-main-filter-dispatch-next-20261104.md).
- [Recovery causal map](performance-term-recovery-next-20261104.md), [frozen candidate/report/evidence](performance-term-recovery-20261104.md).
- [Core cleanup и ограничения](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-late-cleanup-reuse-20261104.md), [независимый review](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-late-cleanup-reuse-review-20261104.md), [принятый Core5f CI](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-ci-5f4590a-20261104.md).
