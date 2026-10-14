# Task 8 / U06 — independent review

Статус **DONE / FROZEN**. Решение **Approved**: spec compliance и pedagogical quality приняты отдельно. Critical: **0**, Important: **0**, Minor: **0**. Обязательных corrections и affected checks для повторного U06 review нет. Последующие integration/pilot gates этим решением не закрываются.

Reviewer `/root/u06_review`, requested `gpt-6.1-sol / ultra`; actual model/effort **not_reported**. Requested profile не выдан за runtime confirmation. Review завершён 2026-10-14 06:02:51 UTC. Accepted launch input `cc1ae186f916742746779ac1f34da3ddc14459e0`; проверенный текущий HEAD `976647cfab1257312682e1137687da5b6c6306ad` содержит отдельное M02-u05 documentation window. Предметный baseline — remote PR13 `a0d1bdb9d8343809560e3ce7f9a2141d4473ee3f`.

Frozen diff `.superpowers/sdd/execution-plan/u06-review.diff`: **371060 bytes / 3191 lines**, SHA256 `f87866e3e55184d0d27577c5eda5c296575139eca3f84201f5541964c69731ef`. Все 30 hashes из `/tmp/java-course-revision-checks/U06/freeze-seal.json` совпали: 28 sources, packet, author report. Reviewer не менял sources, packet, prototype, evidence, Git/index, journal, конфигурации или global settings; не создавал агентов/чатов. Единственная запись — этот report.

## Coverage и глубина чтения

Полностью прочитаны review dispatch, task-8 brief/author dispatch, current-user/controller instructions и tracked `docs/course-revision/user-instructions.md`. Свежие semantic IDs/categories без aliases приняты как действующее человеческое требование; старые preserve-ID bullets не применялись к semantic targets. Физические project paths и Java APIs проверялись отдельно.

Полностью прочитаны все **28 owned sources**, включая все substantial main labs, весь reserve/index route и каждую retained bank card:

- `tasks/index.qmd`, `tasks/README.md`;
- `tasks/labs/{index,numbers,control,arrays,text,objects,polymorphism,generics,collections,pipelines,files,reserve-algorithms,reserve-models,reserve-data}.qmd`;
- `tasks/bank/{index,numbers,control,arrays,text,objects,polymorphism,generics,collections,pipelines,exceptions,files}.qmd`.

`tasks/bank/projects.qmd` не входит в author review scope и не объявлен переработанным. Его три ID сохранены в reserve routes по действующему U00 исключению.

Полностью прочитаны U06 packet (395 lines) и task-8 report. Полный diff рассмотрен по обеим сторонам: вся новая сторона и context прочитаны в полных frozen sources/packet; **все** удалённые строки и hunk metadata прочитаны отдельно. Независимая read-only реконструкция всех 29 file diffs подтвердили точное равенство старой стороны accepted input (1958 lines), новой — текущим frozen файлам (2561 lines). Не применялись sample selection или выборочная проверка только новых карточек.

Прочитаны operative U00 structure/ID/category/workload/ownership/canonical-family/gradeapp contracts, полный U00 packet и R1 wrapper-delivery; U01 operative text/requests 1–126, полный U02 packet, U03 contracts 1–134/requests 249–374/fix 482–513, U04 operative 1–122 и I1 fix, U05 operative application/request/delivery/manifest sections и fix Appendix G. Повторные многотысячные executable appendices не объявлены сплошь прочитанными. Читались точные необходимые prototype contexts: все шесть student Java units, parser/read reference units, public4/private13 test source, helpers и полный 25-asset manifest. Обе U06 Reports implementations и общий ReportProbe прочитаны целиком. Новых книжных страниц и самостоятельного нового чтения всех primary API catalogues reviewer не заявляет.

## Spec compliance — Approved

| Проверяемая область | Результат и конкретные опоры |
|---|---|
| IDs/categories/consumers | 27 QMD независимо разобраны native `quarto pandoc … -f markdown -t json`; 160 уникальных definitions, 128 own exercises, все 156 назначенных U00 targets и четыре reserved manual IDs. Categories соответствуют U00 route/core/optional правилам, карточки наследуют sourceTopic. Все 278 ordinary records совпали с текущими координатами и имеют единственную существующую landing target; shortcode routes рассмотрены в полных sources. Owned legacy IDs/aliases не сохранены. |
| L1–L3 / PL freeze | Все 17 card bodies сопоставлены непосредственно с original PR13 после exact rekey; единственное дополнение внутри card — адресная preparation стабильной сортировки. Bodies/protocols, physical project routes и project-check сохранены. 7/5/5 кандидатов, 14 позиций 6/4/4 и три persisted random выбора сохранены. Gradeapp manual, нового PL18 нет. |
| Manual source properties | Numbers `&&`/double declarations; arrays use/nonmutation/new double[][]; ASCII code32; stack/queue storage; wildcard, checked exceptions, lambda/Stream, explicit close/TWR описаны до сдачи. Published output tests не объявлены доказательством устройства исходника или авторского намерения. |
| L4/L5 | Text alphabet/char/word spans/delimiters и insertion positions сохранены; возвращающий метод видим, substring и builder допустимы. Swap разных объектов и self-call, Stack refusal-before-mutation и B/A объяснение опубликованы. Нет deep-copy/full-Unicode/произвольного alias требования. |
| L6 / G02 | `tasks/bank/polymorphism.qmd#sec-j25-bank-character-queues`: empty refusal всех трёх, full fixed/circular, Dyn growth/FIFO; Fixed nonreuse, Circular reuse. Reset и transfer имеют отдельные видимые contracts, transfer distinct/count/empty-capacious destination. 45+30+60+135=270; 90 подготовки уже внутри135. |
| L7 / G19 | 20+20+30+40+70=180, identity сохранён, только swap заменён и retained bank-only. PECS публикует порядок/prefix/source preservation и non-null/independent-list допустимые данные без rollback/deep copy. `#exr-j25-generics-generic-queue` явно освобождает capacity после get; shifting/ring допустимы, переданный T[]/checked exceptions сохраняются. |
| L8 | Hash35 + view45 + union40 + (brackets OR polynomial)60=180; прежние digit-stack/word-frequency retained. Own stable key с independent equal objects, HashSet1/2 и Map2/1; grade.Student equality не превращён в id-only. View/copy/snapshot и shared Book различены; copyOf identity не обещана. Set inputs сохраняются, порядок HashSet не тестовый oracle. Stack/HashMap техника адресная, blanket Deque нет. |
| L9 | Пять позиций180 и обе альтернативные пары сохранены. Strict filter boundary, source/order/duplicates, stable point ties и finite coordinate limit, char/space/zero-frequency policies видимы. Conditional function creation/apply и selected-only branch опубликованы; mapper/anyMatch debug trace не объявлен portable oracle. |
| L10 / G21 | 30+45+30+integration120=225; все четыре старые карточки retained bank-only. Copy conditions имеют два paths/relative cwd/distinct existing files/open-input-first, refusal без false success и возможный partial output. Byte explicit close и char UTF8/TWR сохраняются. Reserve transpose остаётся square3×3/1×1 и требует File mechanism; rectangular hidden cases не добавлены. |
| G20 | Vehicle/Truck invalid constructor/setter/distance → IAE теперь видимо в обеих карточках; setter проверяет до записи и сохраняет всё прежнее состояние. Это опубликованный owner выбор, а не новый hidden test. |
| Budget/readiness | Все десять строк composition и соответствующие metadata проверены по bank/lab. Main1935 и intro300 отдельны; новые30/35/45/120 явно hypotheses, реальный student pilot не заявлен. Reserves добровольны, preserved projects не стали prerequisites. |

`tasks/bank/files.qmd#exr-j25-files-gradeapp-report` (lines 140–181) публикует **полный** accepted API, включая `StudentFiles.read` input order/closed resource/independent read-only result и `Reports.selected` independent read-only composition/add-remove-set-sort UOE/invalid threshold IAE даже при пустом source. Сигнатуры, предоставленные роли и пять TODO методов совпадают со scaffold. Student strip/null/separators/0..100, UTF8/blank physical numbering/normalized case-sensitive unique id, inclusive threshold/score↓/natural String id↑, source preservation, full validation before useful stdout, two-arg CLI0/2/3/4, output IO partial и открытые System streams согласованы.

Все **13** `GradeAppContractTest` methods и четыре public examples просмотрены непосредственно и сопоставлены с видимыми условиями. Для failing sink точные `s10;A` и helper diagnostic принадлежат предоставленному helper/probe, не навязывают другую student report architecture. Loop+sort+List.copyOf и Stream.sorted.toList удовлетворяют одной опубликованной API boundary; скрытого helper/layout ограничения не обнаружено.

R1 delivery handoff точен: **25 accepted prototype hashes совпали**, из них **17 public student paths**. Provided build.sh/build.cmd/public wrapper properties, generated local `.mvn`, `sh ./build.sh test package`, mode0644/interpreter route и foreign-cwd/repeat/refusal/status obligations переданы M04. Stock versions/pins не изменены. Windows supplied/static/unexecuted честно обозначен; reviewer не заявляет Windows execution. Starter TODO failure отделён от completed implementation acceptance. Public/private layout сохраняется.

## Pedagogical quality — Approved

У каждой main lab видны цель, достаточная подготовка, точный обязательный состав/выбор, время и ordinary/boundary/failure demonstrations в границах карточек. Конкретные personal-transfer questions встроены в существующую защиту: L3 shared row/outer replacement, L5 refused push, L6 common client/Dyn growth, L7 permitted/forbidden operation, L8 structure/element, L9 unchosen branch/terminal timing, L10 duplicate/output boundary. Нового большого письменного отчёта или обязательной соседней реализации lambda механизма нет.

Два допустимых подхода названы для каждой substantial main lab: formula placement/table construction; if/switch и reading loops; stable bubble/insertion; manual/regex и substring/builder; two-pass/temporary-array selection; helper/repeated implementation logic; indexed/enhanced-for copy и shifting/ring queue; Set bulk/manual traversal и two frequency updates; point-distance/counting strategies; byte/char buffered/value reads и loop/Stream report. Они не отменяют явно обязательные техники карточек. Полный новый assigned solution не появился в соседней практике.

Резервные меню, весь банк и семь снятых карточек не потеряны. Порядок HashMap/HashSet, mutable-key diagnostic, list identity, Unicode/null/alias расширения и stdout atomicity не превращены в неоговорённые гарантии. Условия не оставляют M03/M04 педагогический выбор: final IDs/landing targets/cases/assets/TODO границы конкретны; optional foreign preparation clarification сформулированы автором и не требуют medium сочинять контракт.

## Evidence и последующие gates

Свежий independent **read-only** Python/native-Pandoc check вывел: seal30; diff371060/3191/exact29; native27/definitions160/U00mapped156; consumers278/unique landings278; originalPR13 PL17 preserved; prototype25/public17; **failures=[]**, exit0. Дополнительная hash сверка: **912 foreign sources unchanged**, все шесть source hashes двух U06 report probes актуальны; exit0. Эти проверки не записывали evidence файлы.

Авторские 469/469 structure, 140/140 final handoff и Java25 `2×15` semantic observations рассмотрены как существующее author evidence, **не** выданы за повторный execution reviewer. Broad accepted U01–U05 suites, Maven/build/delivery31, final render/container/PL/export suites не запускались. Runtime freedom оценена по двум полным Reports sources и общему probe; полная materialized course acceptance не заявляется.

U06 не требует fix/rereview. Открыты предусмотренные downstream checks: M02 exact runnable-context/native delivery reproduction; M03 foreign consumer и actual href closure; M04 accepted asset materialization/reference tests/starter TODO/native PL delivery; M05 actual public17 ZIP/privacy/student-full-student/full host-container checks; U07 lecture exits/readability/timing; U08 pedagogical synthesis и реальный student pilot. Это существующие gates, не findings U06. Любые последующие содержательные corrections возвращаются оригинальному U06 owner через root, затем тому же reviewer на narrow rereview.
