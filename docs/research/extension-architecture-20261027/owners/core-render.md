# Core render: статический аудит T06

Выбранный владелец — `inputs/owners/core`, Core/Presentation/Navigation 8.3.0, provider `Core 8.3.0`. Это линия на 8.2.7, расходящаяся с 8.2.8; номера не образуют линейную историю. Код курса совпадает с этим provider. Новые render/tests/profile здесь не выполнялись. Ссылки ниже относятся к файлам внутри этого owner, а не к более позднему main. Карта операций — `results/sections/core-operations.json`; механический denominator берётся из T01, качество смыслового обоснования считается отдельно.

## Активация и предметная необходимость

| Маршрут | Фактические операции | Граница гарантии |
|---|---|---|
| Документ вне Quarto project | `filter.lua:28–34` вызывает только pedagogy collector и возвращает AST | Нет NativeRun/common proof/TS document bridge; локальная семантика педагогических блоков остаётся |
| Project, filters-only, отсутствующий active pointer | invalidate → defaults → `bound_materialization` без run → local project/AST/answer/adapter guards → visibility → факты/resources | Common declaration без текущего proof отклоняется; старые completed facts не подмешиваются. Без hooks неизвестные междокументные назначения student удаляются, полного времени нет |
| Project с Core pre/post, включая plain Markdown | `pre.ts` без feature predicate вызывает `beginNativeRun`; тот всегда производит common binding, selected records, active pointer. Каждый Lua project doc вызывает `output.bound_materialization`, затем `quarto run …common-path-binding.ts` | Proof включён даже без банка/projects/common-files. Это установленная область активации H06, а не измеренная доля времени |
| Partial/page/preview с hooks | Тот же begin/capture; finish сопоставляет только actual current outputs/docs. `post.ts:13–17` не объявляет пустое preview notification завершённым run | Нельзя использовать прошлую полную сводку. `renderAll` ограничивает full assemble/CUE/index, но не begin proof. Старт preview с пустым output остаётся незавершённым pointer до фактических outputs |
| Полный HTML/Reveal | finish → public-solution witness → student deferred assignments/resource cleanup → pure release + CUE → current completion; exercise index только при marker | Native crossrefs/search остаются у Quarto. HTML читается/перезаписывается public-solution и assessment-preview по отдельности даже без matching marker; эффект IO — отдельный кандидат, не весь proof |

Текущий `output.current_run` проверяет pointer/schema/config/profile и completed slot, но не liveness PID или непрерывную неизменность всего FS. Совпадающий незавершённый pointer после abort нельзя автоматически считать живым процессом. Это документированная граница (`docs/native-run.md`), не разрешение доверять произвольному concurrent render. Filters-only вывод выше предполагает отсутствие такого active pointer.

Локальная семантическая проверка нужна для корректных difficulty/time/roles, принадлежности solutions/notes, состава slots, закрытой проекции и native references (RQ01–RQ07, RQ10–RQ11; IN01–IN05). Она не требует сама по себе SHA256 всего owner, exact stdout всех inspect, FFI либо отдельного процесса для одного null materialization row. Включение общего proof в plain hook route — реализация, а не высокоуровневое требование.

## Capture, inspect и текущие bytes

`common-path-binding.ts:118–230` строит producer: два owner inspect, physical inspect на каждый выбранный input; для regular physical=lexical в обычном off-режиме второй document inspect переиспользуется. Before/after `inventory()` читает все негenerated owner-local файлы рекурсивно; `_extensions` исключён здесь, отдельно читаются extension descriptors. Даже selected page может заплатить за нерелевантные project trees. Это временный список hashes, не retained FD snapshot.

Следующий admission (`withCommonPathBinding`, строки 278–349) имеет A/use/B. Каждая сторона получает один fresh owner frame; regular source использует его `fileInformation`, direct inspect остаётся для alias/unlisted/restored exceptions. Новая active per-document selected ветка (`native-run.ts:449–456`, `common-path-selected.ts:53–62`) проверяет source/metadata/config/common/absence локально до/после возврата row. Она **не** открывает глобальный witness или full inspect. После retirement/restored lifecycle используется полный путь; ошибка thin path не включает fallback.

Поэтому старые сильные формулировки H08/H17 о глобальном inspect на каждом документе и обязательной exception цене каждого regular input опровергнуты выбранным кодом. Остаются стоимость самого bridge, local duplicated reads и full boundaries. В `readSelectedCommonPath` source читается дважды, а каждый selected `record.files` дважды проходит metadataBytes/hash; readRecord дополнительно проверяется на входе и в каждом check. Нужность проверки bytes не доказывает нужность именно этого числа чтений.

Exact inspection store сохраняет полный stdout, включая повторяющиеся project-level config/fileInformation, а не узкий DTO. Content hash дедуплирует равные bytes, но не почти одинаковые document responses. `withInspectionWriter` уже append-only: старый префикс не перечитывается при каждом append. `withInspectionSnapshot` один раз допускает/замораживает plain конечный JSON DAG; accessor не сериализует binding целиком. Старый storeInspection с полным before/after store scan остаётся exported, но текущие producer callsites используют writer. Его наличие не доказывает runtime quadratic append path.

Metadata closure учитывает не только physical path, но `(path, source, format, declaredBy)`: одно `_metadata.yml` может иметь несколько контекстных edges. Это нужно для Quarto rebase/include/metadata-file semantics; число context origins не равно числу уникальных bytes. Native extraction cache ключуется path+hash+kind в lifetime одной raw phase; контекстные origins всё равно повторяют filesystem reads/hash и final checks. Статус H07 — повтор чтений доказан, оправдание каждого контекстного edge и наблюдаемая доля стоимости ещё не завершены. QMD/include eligible LF payloads идут batch через native Lua helper; BOM/CR/tab используют direct extraction. `native-raw-metadata.ts:39` уже делает `await Promise.all(eligible.map(...))`; отсутствие одноимённого исторического коммита в ancestry не доказывает отсутствие этого поведения.

## Кардинальность и границы

Обозначения: B — все input records owner inspect; n — inputs текущего run; d — source/format rows; U — уникальные physical metadata paths; E — context origins; F — негenerated owner-local files; V — installed extension payload files; k — artifact requests; S — общий размер retained exact stdout. Это разные знаменатели.

* Для regular LF fixture одного профиля off-режима producer имеет n+2 stock inspect, finish ещё 2; итого n+4. Это формула выбранного source и assertion `tests/render-inspection-budget-native.ts`, не новое наблюдение. Owner inspect может сам возвращать O(B) данных. Произвольные aliases/profiles/retirement не входят в равенство.
* Активация Performance меняет сам product path: `common-path-binding.ts:140` отключает physical→lexical reuse, а `metadata-closure.ts:38` выключает direct-response memo при COURSE_PERF_MODE, отличном от absent/off. Для той же regular fixture static inspect budget становится 2n+4 вместо n+4 (без иных факторов). Это confound B16/H03: tracing нельзя выдавать за off workload с одним измеренным overhead. Нужен C-10: absent/off/active mode, cardinality и output parity до timing.
* Raw subprocess budget в этой же fixture — `2*(Y+1)`, Y — существующие non-QMD/include origins по unique path; source/native-test поддерживает две фазы. Файловых reads/hash значительно больше: E и n входят в повторные checks отдельно.
* Before/after producer inventory читает O(F) bytes на сторону. Full admission читает protected closure, а не весь произвольный owner F. Overlay route отдельно защищает source/vendor F+V; не переносить его стоимость на обычный v2.
* `p.commonPathDocuments.filter` на каждый doc даёт O(d) поиск; d documents дают O(d²) сравнения. `selectedRecord` на каждый row копирует binding с `initialInputFiles[n]` и глобальными metadata files, source `.find` даёт O(n*d). Это representation/lookup CPU, а не O(d²) native processes.
* В producer есть `.find` источника внутри n-цикла; finish uses `documents.some` для output/duplicate checks; эти lookups также потенциально квадратичны. В inspection accessor осталась сортировка refset, но полной binding serialization на каждом access уже нет.
* Batch artifact API держит одну outer A/use/B; nested `assertCommonPathRun` использует admitted run. Standalone load/resolve/read — отдельные операции, требующие новых границ. Нельзя умножать цену standalone на k и приписывать её batch consumer без чтения caller.

Tool identity: invocationIdentity различает stat/env/cwd/profile local check и full executable-byte hash на границе. Raw phase дополнительно проверяет helper bytes; overlay runtime — Deno/libc. Эти guards защищают реально выбранный executor/provenance и смену среды, но threat assumptions и необходимость identity всего env на каждом local guard не следуют из обычной авторской правки. Env значения в отчёт не выводятся.

## Локальный AST и представление

Core AST pipeline сохраняет raw declarations до projection; independently public clone нужен для Body и public-solution witness, full clone — для teacher facts. В full run student clone не равен выбрасываемому дублю. Но actual plain pipeline всё ещё выполняет несколько whole-doc passes: normalization/condition validation, occurrence ownership, visibility indexing/projection дважды, grading, references, pedagogy, raw/public resource uses; некоторые collectors возвращают пустой результат лишь после walk. `native-document.validate` повторно вызывает pedagogy.collect, затем filter собирает public/current pedagogy снова; это проверка и сбор разных проекций, а raw pass его результата не сохраняет. Кандидат на совмещение traversal должен сохранить порядок raw validation→projection, solution ownership и native conditions.

`native-answers` early returns вне exercise-bank; configured adapter список пуст — `native-adapters.validate` возвращает до installed directory scan. Установленный, но не выбранный adapter не выполняет свой validator/read/write. Адаптеры с конфигурацией загружают validate.lua/native.lua динамически; dispatch остаётся явной unresolved межowner edge T01, а набор кандидатов определяется current `course.adapters` и contract.name. Отсутствие trace не доказывает неиспользование.

Семантические типы не являются decode proof: `JSON.parse(...) as DocumentResult`, `any` Pandoc JSON и dynamic import QRC не защищаются только Deno check. Pure assembleRelease/CUE/adapter guards проверяют разные предикаты; public DTO и private filesystem lifecycle разобраны отдельно в `core-export.md`.

## Статусы H06–H11, H17, H20 и малые различающие опыты

| H | Статический итог | Остающееся свидетельство |
|---|---|---|
| H06 | Подтверждено включение proof/bridge на plain hooked project; no-project исключён | C-01: no-project/filters-only/hooked plain/bank/common с одинаковыми bytes, n=1; latency и subprocess/read counters |
| H07 | Подтверждены повтор reads/hash и E>U; extraction memo уже есть | C-02: один shared metadata/include, 1/3 docs, physical bytes против context origin reads |
| H08 | Старый per-doc full boundary опровергнут; outer/nested операции разделены | C-03: 1/3 artifact requests standalone vs batch, no mutation и одна допустимая mutation между A/use/B |
| H09 | Exact full stdout и повтор project facts подтверждены; magnitude неизвестна | C-02: total stdout S, union unique preimages, B/n отдельно |
| H10 | Широкая identity проверка подтверждена; нужность частоты неизвестна | C-03 с учётом full hash bytes/env serializations; harmless env variant отдельно |
| H11 | TS мост возвращает малый row, даже при null declarations; bridge cost неизвестна | C-01: count/duration child bridge, payload size; native fact handoff только альтернативный вопрос |
| H17 | Regular admission reuse и exception-only direct inspect уже реализованы | Не повторять старый тезис; один alias против regular в C-02 при необходимости |
| H20 | Broad producer inventory и overlay vendor closure подтверждены; доля реально лишних bytes не измерена | C-04: добавить один unreferenced noninput file; отдельно installed inactive payload change. Должны сохраняться config/include/common/privacy/provenance invariants |

Опыты предложены координатору, не запускались здесь; новые изменения product sources запрещены. Полное runtime подтверждение и окончательное закрытие гипотез остаются после T05/T13. H40 вынесена в Presentation/Navigation документы. Ни один исторический PASS не обозначен как новый PASS выбранного окружения.
