# Task 9 / U07 — narrow fix-1 rereview: Approved

Дата: 2026-10-14. Reviewer `/root/u07_review`, тот же reviewer первого U07 review. Requested **gpt-6-astra / ultra**; actual runtime profile **not_reported**. Это повторная проверка исправлений U07, не будущая независимая authorless U08.

**Решение: Approved. Spec compliance: Approved в границах U07 и данного correction. Pedagogical quality: Approved в тех же границах. I1–I4 и M1–M2 закрыты. Новые findings: Critical 0 / Important 0 / Minor 0.** Итоговая приёмка курса и downstream gates этим решением не объявляются.

## Замороженный вход и граница проверки

Workspace `/tmp/java-course-pr13-20261013`; исходный remote PR13 `a0d1bdb9d8343809560e3ce7f9a2141d4473ee3f`; accepted input `8ddbe60cecffb7f4a632d7e6d0241efaed45c121`, принятая palette correction `15a50ed`. Основание correction — `.superpowers/sdd/execution-plan/task-9-review.md`, SHA256 `8768dfb7d5d2c23bca6ce5db43790744b79da2d4097764c2e071ea8c8105770a`.

Проверен exact root owner diff `.superpowers/sdd/execution-plan/u07-fix-1-review.diff`: **273762 bytes**, SHA256 **`bfbb9d63c77fe101381c6813ea06b1e82db7bda8f958d97c9bae2591311140b9`**. Новый evidence root — `/tmp/java-course-revision-checks/U07/fix-1`; `freeze-seal.json`: **31029 bytes**, SHA256 **`021dec374b4f3aa6571de17197f5ffad75f79a14b9cbb0b43bfb7a1ab09b996e`**.

Рассмотрены все шесть исходных findings, полный narrow source delta четырёх QMD и текущий видимый контекст их определений/клиентов/результатов, correction U07 packet и append-only `task-9-report.md`, targeted fixture assembly/results, source/context/inventory/consumer/profiles/volume/carry evidence. Прежний полный проход 11 лекций и old9→new11 из первого review сохраняется; повторное полное чтение всех 11 не заявляется. Неизменённые SVG, old37 suites и broad suites не перезапускались. Разрешённые Root/M01 config changes вне owner delta не являются finding U07.

Независимая свежая read-only SHA-сверка: **14/14 текущих owned files, 72/72 прежних sealed evidence и 153/153 новых sealed evidence совпали**. Восемь неизменённых owned source файлов и два SVG перенесены с прежними bytes. Старый review и первоначальный seal сохранены. Reviewer не исправлял курс, packet, evidence, Git или journal; единственная запись этого цикла — данный review. Делегаций, новых чатов, публикации и глобальных изменений не было.

## Закрытие Important

### I1 — Closed: возвращаемые типы, файловые клиенты и состояние после catch видимы

`lectures/exceptions-resources.qmd:33`, `#sec-j25-lecture-exceptions-resources-context`, теперь различает отдельные main, static-члены ErrorsDemo и соседние resource classes; названы imports и `main(String[] args) throws IOException` для file clients.

- `:310`, `#sec-j25-lecture-exceptions-resources-step-22`, и `:322`, `#sec-j25-lecture-exceptions-resources-step-23`: полные `static int withFinally()` / `static int replacedReturn()` и точные println-callers. Первый выводит `выход`, затем `10`; второй — `20`. Return больше не помещён в несовместимый void main.
- `:362`, `#sec-j25-lecture-exceptions-resources-step-26`; `:382`, `#sec-j25-lecture-exceptions-resources-step-28`; `:395`, `#sec-j25-lecture-exceptions-resources-step-29`: полные сигнатуры `manualFirst(Path)`, `firstLine(Path)`, `copy(Path, Path)` с checked boundary. Уточнение «reader.close() не выполнится» правильно описывает путь при отказе readLine, без более сильного обещания о внешнем состоянии ресурса.
- `:409`, `#sec-j25-lecture-exceptions-resources-file-client`: заданы отдельный каталог, UTF-8 `input.txt` с `first\n\nlast\n`, первоначально отсутствующий `copy.txt`, source/target и вызовы. Видимый stdout — три `first`, пустая строка, `last`; copy bytes совпадают с input. Для отсутствующего input видимо сказано, что IOException выходит через main и последующие вызовы не выполняются.
- `:539`, `#sec-j25-lecture-exceptions-resources-step-38`: `static List<String> nonblankLines(Path path) throws IOException` материализует результат внутри TWR; caller использует тот же определённый input и печатает `[first, last]` после возврата.
- `:570`, `#sec-j25-lecture-exceptions-resources-step-39`: объявлены mutable catalog, first/second; соседний уже определённый DataFormatException пойман. Сообщение `неверная третья строка` и `[Ada, Lin]` наблюдают сохранённые изменения.

Compiled contexts теперь содержат именно видимые non-void members, parameters, imports, setup и callers. Шесть новых author cases `finally-return`, `finally-replaces-return`, `file-client`, `file-missing-input`, `materialize-before-close`, `catalog-after-caught-failure` подтверждают соответствующие expected outcomes. Недостающая семантика больше не выбирается скрытым harness или будущим M02.

### I2 — Closed: конкретный capture и четыре исхода Optional

`lectures/functions-streams.qmd:291`, `#sec-j25-lecture-functions-streams-step-20`: `student = students.get(0)` связывает аргумент с ранее заданным s1/Ada/90, после `remember.accept(student)` видим `println(log)` → `[s1]`. В `:536`, `#sec-j25-lecture-functions-streams-step-38`, отказ set использует определённый `students.get(0)`, поэтому достигает UnsupportedOperationException.

`:590`, `#sec-j25-lecture-functions-streams-step-42`, задаёт полный static `fallback()` класса FunctionsDemo с наблюдаемым `println("fallback")` и возвращаемым `"нет участников"`; перед клиентами определены `Optional<String> present/empty`. В `:605`, `#sec-j25-lecture-functions-streams-fallback-present`, eager печатает `fallback` → `Ada`, lazy — только `Ada`. В `:618`, `#sec-j25-lecture-functions-streams-fallback-empty`, каждый отдельный опыт печатает `fallback` → `нет участников`. Условие независимых main сформулировано явно; helper и значения доступны до вызовов.

Новые author cases `captured-list`, `unmodifiable-set`, `fallback-present-eager`, `fallback-present-lazy`, `fallback-empty-eager`, `fallback-empty-lazy` достигают именно этих результатов. Наблюдаемый эффект отделён от возвращаемого значения; optional reasoning не требует несуществующих expensive/optional declarations и не раскрывает решение назначенной работы.

### I3 — Closed: обычный Comparator и реальный клиент предшествуют фабрике

`lectures/equality-collections.qmd:210`, `#sec-j25-lecture-equality-collections-step-11`, теперь определяет знак `compare(a,b)`, отдельный ReportOrder.java и его imports; полный `ReportOrder implements Comparator<Student>` использует score descending, затем natural String id ascending. В `:228`, `#sec-j25-lecture-equality-collections-step-12`, есть самостоятельный main, mutable rows s3:90 / s2:70 / s1:90, создание `new ReportOrder()`, наблюдение `compare(...) < 0`, вызов `rows.sort(order)` и печать ids. Видимый результат — `true`, `s1`, `s3`, `s2`; явно указано, что sort вызывает compare.

Только затем в `:244`, `#sec-j25-lecture-equality-collections-comparator-factory`, предложена замена создания order в том же клиенте. Извлечение ключа, reversed и thenComparing связаны с уже наблюдаемой ролью. Оба author cases `ordinary-comparator` и `factory-comparator` дают одинаковый результат, включая tie по score. Причинный prerequisite восстановлен до использования factory/reference; equality Student не переопределена сортировкой.

### I4 — Closed: собственный ключ перенесён в Map frequency

`lectures/equality-collections.qmd:490`, `#sec-j25-lecture-equality-collections-step-35`, использует ранее полностью определённый StudentId: два независимых `new StudentId("s1")` и третий `new StudentId("s2")`; `Map<StudentId,Integer>` обновляется понятными `getOrDefault`/`put`. Наблюдаются `first == second` → false и lookup новыми равными экземплярами → 2/1. Проза объясняет две частоты и не использует iteration order как oracle.

Author case `own-key-frequency` включает именно видимый полный StudentId и клиент, stdout `false\n2\n1\n`. Требуемый перенос equals/hash из Set в собственный Map key выполнен; новая callback-зависимость не введена.

## Закрытие Minor

### M1 — Closed: imports позволяют достичь предметного negative outcome

`lectures/erasure-algorithms.qmd:211`, `#sec-j25-lecture-erasure-algorithms-raw-types`: видимый RawDemo.java, default package, imports `java.util.List` и `java.util.ArrayList`, точный main wrapper. Case `raw-warning-cce` компилируется с rawtypes/unchecked warnings и затем падает с ClassCastException при String read.

`lectures/functions-streams.qmd:361`, `#sec-j25-lecture-functions-streams-step-23`: negative snippet использует `java.nio.file.Path` и `java.nio.file.Files::readString`. Case `checked-method-reference` снимает только показанные comment markers и получает `incompatible thrown types IOException in functional expression`; `cannot find symbol` отсутствует. Expected negative проверяет checked constraint, а не нехватку imports. SVG/javap bytes и claims не изменены.

### M2 — Closed: iterator действительно удаляет элемент mutable списка

`lectures/equality-collections.qmd:355`, `#sec-j25-lecture-equality-collections-step-24`: объявлен `new ArrayList<>(List.of("Ada", "", "Lin"))`, показан iterator.remove и финальная печать `[Ada, Lin]`. Case `iterator-removal` использует этот вход и совпадающий результат; предыдущий immutable List.of не нужен как скрытый setup.

`:640`, `#sec-j25-lecture-equality-collections-step-23`, теперь явно документальная схема стоимости обхода, с объяснёнными ролями linked/process и fence `text`. Inventory согласован с этой классификацией; исполняемый Java-контекст для псевдокода больше не заявляется.

## Доказательства, качество и пределы вывода

Прочитаны `targeted.py`, `context-manifest.json` и результаты всех **18 новых author executions**: точные argv/cwd/status/stdout/stderr/inputs/output-file expectations. Использован Java25.0.4.1, `--release 25`, без preview. Ожидаемые compile/runtime refusals сохранены как negative outcomes. Fixtures собираются из видимых fenced members/statements, точных prose callers/imports и ранее принятого grade.Student; эти additions не скрывают пропущенных авторских решений. **Reviewer не запускал эти Java cases заново**: решение опирается на чтение кода, проверку причинных исходов и неизменности зафиксированного execution evidence.

Свежая независимая read-only проверка подтвердила **49 section-hash references, 24 fixture-file references, все 18 case-result associations и все 159 fence records четырёх изменённых QMD**: code bytes, source SHA и line locations соответствуют текущему source. В 27 consumer moves namespace/target сохранены; у двух вместе с материалом изменён containing anchor (`comparator-factory`, `fallback-empty`), что ожидаемо. Новых anchors четыре, прежние targets/503-row old map сохранены. Авторские suites содержат 412 source/AST/carry, 1247 profile/context и 2028 integrity checks без записанных failures; это отдельно от проведённых reviewer consistency checks и не обозначает новый полный запуск этих suites.

Проверены author render results для четырёх decks в student/full, включая узкий повтор exception deck после prose correction. По сохранённому реальному DOM traversal — **241 screen на профиль**: 45/62/65/69, viewport1280×800; recorded bounds/pre-scroll checks и visible section text comparison выполнены. Рассмотрены scripts и диагностика ожидания MathJax2: гонка касалась неизменённого formula screen; изменение source ради сравнения не вносилось.

Reviewer визуально прочитал **21 исправленный причинный PNG student**, затем **все 8 отличающихся от student PNG full среди этих 21**; у остальных 13 соответствующих PNG совпадают SHA. Definitions/imports/input/client/result доступны и читаемы; clipping/overflow, мешающих данному correction, не обнаружено. Три дополнительных unchanged dependency PNG в каждом профиле повторно визуально не принимались. Это точный объём просмотра, а не заявление о ручном просмотре всех 482 screens или всех 48 сохранённых PNG. Прежние SVG/fallback visual acceptance и hashes перенесены без повторного render.

Current inventory: **365 fences / 300 Java**. Исправление добавило полноценные контексты вместо прежних placeholders, но не доказывает executable pass всех 300 Java fences. Для unaffected contexts сохраняются прежняя классификация и пределы evidence; полная техническая проверка M02 остаётся отдельной.

Reader metric независимо пересчитана точной функцией U00 по четырём сохранённым текущим Pandoc AST: erasure2179, equality1722, functions1838, exceptions2036; семь прежних измерений перенесены после SHA-сверки. Сумма **19678≤20585**, delta+302, запас907. План **11×80=880≤936** сохранён. Actual correction rehearsal содержит четыре causal chains slots7/8/9/10, start/end и 290.16s wall, включая tooling. Перераспределение внутри прежних сегментов объяснено. Старые all-slot и дополнительные3/5/11 rehearsal records неизменны; это compressed agent rehearsals, не student pilot и не эмпирическая гарантия 80-минутного темпа.

Изменённые definitions, clients и наблюдения улучшают самостоятельное воспроизведение и необходимый порядок введения. Лекции сохраняют source freedom, локальные примеры и переход к теории; новых practice/tasks links или полного assigned-practice solution в correction нет. Формат isolated author preview честно отделён от native filters/QRC/export: видимый сырой QRC здесь не принимается за проверенный итоговый href.

## Решение и handoff

**Approved — все I1–I4/M1–M2 закрыты; новых исправлений U07 по этому narrow rereview не требуется.** Для затронутых M02/M03/M05 checks передаются current source hashes и `fix-1` evidence. Полные M02 executable provenance, M03 actual hrefs, M04 delivery, M05 native build/export/privacy/global volume и независимая U08 сохраняют свои границы; это review их не заменяет. Intermediate M01 attempt не является итоговой pedagogical acceptance.

Review завершён и **FROZEN**. Авторские source/evidence остаются замороженными; reviewer не открывает новое correction window и не продолжает записи после сохранения/контрольной hash-сверки данного файла.
