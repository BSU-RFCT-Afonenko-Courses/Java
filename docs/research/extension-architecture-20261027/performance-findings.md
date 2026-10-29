# Малые runtime observations T04/T05/T13

Принята настоящая reduced Java fixture: root + пять ролей,12 renderable QMD +1 unused include,25 unchanged installed vendor scopes. Smoke04 завершился exit0 за172.645s; это **весь маленький портал**, не цена страницы. Все12 expected pages,4 QRC crossproject links/0deferred,17targets,3 starterZIP и handbook examplesZIP сохранены. Нет broken local links или synthetic closed marker в student output. Negative output-copy control обнаружил одновременно injected marker и missing href при наличии всех12pages; complete=false/exit1.

Первые два smoke отказали из-за ошибок fixture (ссылки на sibling source QMD; unpaired synthetic solution), исправлены только fixture. Smoke03 прерван ровно120s при здоровом продолжении; это не доказательство дефекта продукта. Единственное разрешённое расширение smoke04 до300s сохранило прежний timeout.

## Цена одной выбранной страницы

Только `quarto render chapters/types.qmd --profile student`, cwd `fixtures/selected-page/theory`. N1=один unrelated index +target. Отдельно записанная подготовка копирует принятые native output index/resources и относительный CSS dependency; она не входит в tool wall. Одинаковые argv/env/cwd, локальные общие warm caches. Правильные CUE0.17.1/JDK25 и native Quarto1.11.5. Public Performance executeScenario импортирован напрямую; prepareVariant/full-course copy не используется. Ни active mode, ни вложенные public runners не запускались.

Один excluded warmup на вариант; затем5 alternating AB/BA pairs,10 successful runs:

| База | Median tool wall,s | Min–max,s |
|---|---:|---:|
| Direct installed extensions |14.6335|13.9386–15.2724|
| Public Performance off |14.7791|14.1736–16.0588|

Paired off−direct: +0.2350,−0.4295,+0.2906,−0.3023,+0.7864s; median+0.2350s. Знаки смешаны; observed uncertainty range−0.4295…+0.7864s при пяти парах. Эти данные не устанавливают малый стабильный overhead и не проходят произвольный SLA/1% threshold. Во всех10 случаях full target HTML bytes и main HTML bytes одинаковы, local link checks проходят, closed marker отсутствует. Raw stdout/stderr, native/off tool status, outer wall (включает Deno setup/report) и output manifest сохранены отдельно. CPU/RSS public off включают resource helper; direct CPU/RSS не измерены и не сравниваются.

N4 setup — standalone5-page theory native outputs,23.865s outer; не benchmark. Один N4 selected sample:15.6867s tool/15.7478s outer. Target source hash и main HTML hash совпадают сN1; whole HTML меняется с native sidebar/page navigation (47438→48768bytes). Одного unpaired sample недостаточно для вывода о росте;5AB/BA scale series остаётся queued. Нельзя подменить measured calls статической формулой inspect growth.

## Границы

Результат относится к selected installed composition8.3.0 и small warm selected page на данном ПК. Не измерены полныйJava, qualification, Docker/Gradle, server/submission, student→full→student, repeated memberships/common origin axes, full API/optional-primary branch или стоимость отдельной owner function. Active mode меняет common-path inspect multiplicity и metadata closure cache, поэтому его counts не переносятся в off; active diagnostics не запускались.

Preview one-shot первоначально не дошёл до Quarto: sandbox отказал в loopback bind127.0.0.1 (PermissionError). Это environment restriction, не product failure; Listening не использован как readiness. Явные дальнейшие probe statuses и raw artifacts дополняются после единственного разрешённого bound.

## Последние адресные probes

Native floor: один selected-page render без custom hooks/filters, exit0,3.3454s tool/3.4226s outer, links ready. Target source bytes те же, writer сохраняет предметную страницу; HTML/main hashes отличаются. Это нижняя граница с явными пробелами Core capture/currentness, QRC, Site collect и Presentation; разность с direct **не** приписывается overhead одного Core. Статистической серии native floor нет.

Preview после первоначального sandbox denial: тот же loopback-only script разрешён automatic approval review через `require_escalated`, без deploy. Один bounded60s: реальный HTTP target готов через38.8699s от старта, сохранённый sentinel виден через15.6216s после save; total54.4930s. `before.html`/`after.html` сохранены, source восстановлен, собственный preview process group завершён. Это одно наблюдение; распределение latency неизвестно.

Explicit Body export только sec-lab-numbers (3exercises): hard60s, exit−15. Все3nativeJSON и Core post закончились; package.json/public package ещё не выпущены. Это **incomplete timeout**, без product diagnostic и без вывода «экспорт сломан». В disposable copy сохранён временный overlay YAML после принудительного завершения. Бюджет не расширялся; cleanup/recovery и package public/closed split остаются открытыми. DownloadZIP уже наблюдался в composition, но это не заменяет Body/PrairieLearn/Print/Moodle qualification.

Все measurement workloads остановлены; token возвращён координатору. `experiment-queue.csv` различает accepted/observed single/completed5pairs/timeout/queued. локальные исследовательские материалы индексирует raw artifacts. Финальная проверка selected Java и Performance HEAD/status/size/SHA256/mtime_ns совпадает с исходной; изменения только в исследовательских disposable paths.

Следующие различающие опыты: paired N1/N4 selected-page series (не full composition); Body completion с отдельно согласованным бюджетом и явным recovery contract; затем optional-primary/fixedAPI либо canonical+materialized/common lifetime только по реальному branch question. Ни большой Java, ни active instrumentation не требуются для этих выводов.
