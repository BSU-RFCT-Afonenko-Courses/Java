# Task 8 — U06 report

Статус **DONE / FROZEN** после окончательного seal. Автор `/root/u06`, dispatch `U06-01`. Принятый launch HEAD: `cc1ae186f916742746779ac1f34da3ddc14459e0`; remote PR13: `a0d1bdb9d8343809560e3ce7f9a2141d4473ee3f`. Requested `gpt-6.1-sol / ultra`; actual model/effort `not_reported`. Эффект runtime settings и проверка фактического профиля не заявляются. Во время работы root сообщил docs-only commit `976647c`; предметный вход сохранился.

Переработаны ровно 28 owned sources: 27 QMD и `tasks/README.md`. Завершены собственные U06 packet, report и evidence. Полный список всех 160 конечных ID, categories, составов, минут, опубликованных случаев и передач находится в `docs/course-revision/packets/U06.md`; 128 exercise definitions включают четыре новые manual карточки. Категории упражнений наследуются от sourceTopic. Старых owned targets, aliases, redirects, migrations и скрытых legacy spans нет.

Все 17 PL карточек сохраняют body/protocol после rekey, кроме адресной preparation-подводки устойчивой сортировки. Физические project routes, числовые протоколы, ручные критерии исходника и public/private границы сохранены. PL остаётся 17 questions и 14 обязательных позиций 6/4/4 при 7/5/5 кандидатах; три случайных выбора сохраняются после создания экземпляра. Gradeapp остаётся manual.

ЛР7: 20+20+30+40+70=180, identity остаётся, swap сохраняется дополнительным в банке. ЛР8: 35+45+40+60=180, старые digit-stack/word-frequency остаются в банке, выбор скобки либо polynomial сохраняется. ЛР9: 25+30+50+45+30=180, пять позиций и две пары. ЛР10: 30+45+30+120=225, четыре старые карточки сохранены дополнительными; весь принятый gradeapp API опубликован непосредственно в canonical condition. ЛР6=270; 90 подготовки очередей уже внутри выбранной карточки 135. Итого самостоятельная работа 1935, вводные 300 отдельно. Новые 30/35/45/120 — гипотезы; реальный студенческий pilot не выполнялся.

G19: GenericQueue на переданном T[] ограничивает одновременно хранимый состав; get освобождает место для put. Перемещение элементов и кольцевое переиспользование допустимы. G20: неверные constructor/setter/distance Vehicle/Truck вызывают IAE до записи; после отказа состояние прежнее. G21: копирование получает два пути, открывает input прежде output, отклоняет same-file до открытия выхода и допускает partial output при IO-отказе. Квадратная матрица и требуемый File-механизм сохраняются.

Gradeapp condition публикует read-only независимо сформированные read/selected списки, запрет add/remove/set/sort через UOE, IAE для threshold вне 0..100 даже при пустом source, физические номера, исходные cause и принятые диагностики. Все 13 private тестов U05 сопоставлены с видимыми правилами. Student equality остаётся по всем трём компонентам; уникальность normalized id проверяет loader. U00 R1 принят: public starter имеет 17 точных файлов, предоставленные build.sh/build.cmd и публичные wrapper properties; .mvn создаётся локально. Команды и все 25 accepted U05 asset hashes переданы M04 без изменений.

Свежая структурная проверка:

- `python3 /tmp/java-course-revision-checks/U06/check_structure.py` — **469/469**, status 0.
- Native Pandoc для 27 QMD; 160 definitions, 128 own exercise, четыре новые manual и 17 PL.
- 278 ordinary consumer records, shortcode destinations, categories, точный состав десяти работ и суммы 1935+300.
- Сохранение опубликованных PL протоколов и hash boundary чужих sources.

Первый запуск дал 464/469 и сохранён в `verification-first-attempt.json`: исправлены две ошибочные новые prerequisite-ссылки и три чтения MetaInlines в harness. Один lowercase search assertion остановил начальный авторский скрипт; продолжение применило только оставшийся хвост, без нового baseline snapshot. Условия PL не менялись ради проверочного harness.

Свежие семантические свидетельства отдельно:

- `python3 /tmp/java-course-revision-checks/U06/verify_reports.py` — два независимых варианта: loop+sort+List.copyOf и Stream.sorted.toList.
- Actual javac/java **25.0.4.1**, без preview; оба compile 0, run 0, stderr пустой.
- Каждый вариант проходит **15 наблюдений**: tie order, source preservation, inclusive threshold, none/empty, score 0/100, line format, add/remove/set/sort refusal, invalid threshold −1/101 и независимость состава результата.

Это 2×15 semantic observations, отдельно от 469 structure checks. Проверка не навязывает архитектуру или имена helpers. Полные argv/source SHA/stdout/stderr/status находятся в `report-probes.json`; частные реализации только в собственном tmp evidence, в practice они не опубликованы. Broad render, container и native delivery suites здесь не повторялись.

Глубина чтения отражена в `read-log.json`: все 28 исходных owned files; operative U00–U05/R1 и точные необходимые API/prototype/tests/manifests; handoff scope/decisions/U06 и относящиеся EF/P/G. Многотысячные повторные executable appendices не заявлены прочитанными полностью. Новых содержательных книжных страниц не читалось; сохранённые bibliography ссылки не означают чтение 13 книг. Адресно сверены Java 25 List/Collections/Files/Stream API; фактический Java 25 запуск относится к двум частным Reports.

Чужие sources, U05 prototype, physical project assets, Git/index/commits, shared journals, configs/vendor, глобальные установки/настройки, публикация, чаты и подагенты не затронуты. Точные необязательные preparation clarifications переданы через packet/root; чужих prose writes нет. Конфликтов R1/R2/repeated R3 не возникло, принятая R1 bootstrap correction соблюдена.

Все авторские решения завершены. M02/M03/M04/M05/U07/U08, полный render/container/privacy/delivery и педагогический pilot остаются последующими gates; готовность всего materialized курса не заявляется. Final source/packet/report hashes и freeze UTC находятся в `/tmp/java-course-revision-checks/U06/freeze-seal.json`. После seal запись только по новому owner window от root.

Точные owned paths:

- `tasks/README.md`
- `tasks/bank/arrays.qmd`
- `tasks/bank/collections.qmd`
- `tasks/bank/control.qmd`
- `tasks/bank/exceptions.qmd`
- `tasks/bank/files.qmd`
- `tasks/bank/generics.qmd`
- `tasks/bank/index.qmd`
- `tasks/bank/numbers.qmd`
- `tasks/bank/objects.qmd`
- `tasks/bank/pipelines.qmd`
- `tasks/bank/polymorphism.qmd`
- `tasks/bank/text.qmd`
- `tasks/index.qmd`
- `tasks/labs/arrays.qmd`
- `tasks/labs/collections.qmd`
- `tasks/labs/control.qmd`
- `tasks/labs/files.qmd`
- `tasks/labs/generics.qmd`
- `tasks/labs/index.qmd`
- `tasks/labs/numbers.qmd`
- `tasks/labs/objects.qmd`
- `tasks/labs/pipelines.qmd`
- `tasks/labs/polymorphism.qmd`
- `tasks/labs/reserve-algorithms.qmd`
- `tasks/labs/reserve-data.qmd`
- `tasks/labs/reserve-models.qmd`
- `tasks/labs/text.qmd`
