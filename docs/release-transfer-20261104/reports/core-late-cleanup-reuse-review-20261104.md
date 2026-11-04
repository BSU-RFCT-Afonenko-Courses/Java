# Core late cleanup reuse — независимая Source рецензия

**APPROVE scoped Source candidate; runtime equivalence UNKNOWN.** Конкретных блокирующих findings в однопроизводственной дельте нет. Проверены actual HEAD `a06ded0b9dea67367e6064452655cbaf6b16a4be`, exact diff `_extensions/course-core/native-ts/late-filter.lua` (+10/-2), полный changed file, writer report/patch, связанный `native-resources.lua`, request transport, actual metadata producer и local Pandoc API. Source рецензент не менял; Native/runtime/compiler/tests/hash/network не запускал.

## Pandoc copy, body и metadata

Локальная API документация установленного Quarto `/opt/quarto/share/lua-types/pandoc/pandoc.lua:45–62` описывает `Pandoc:walk` как deep copy с сохранением original без изменений. Это достаточный Source/API anchor, чтобы не считать `clean_document(doc)` in-place mutation исходного документа; новый clone/AST model здесь не нужен. Документация read-only, Pandoc command/version probe не выполнялся.

`clean_document` (`late-filter.lua:10–12`) содержит ровно прежние callbacks: Div удаляет occurrence/native-kind attrs, Span с native-title marker возвращает content. Они не читают environment, metadata, resource state или counters и не зависят от того, выполняются до/после Native observation. Native body на строках68–69 по-прежнему сериализует очищенную копию и берёт только decoded `.blocks`; до этой операции ранее делался тот же cleanup. Различие — retained reference вместо повторного cleanup перед final return.

Два transport metadata keys удаляются после Native writes на исходном doc, как прежде (88–89), **и** на saved cleaned copy перед return (91–92). Actual producer `document/normalize.ts:524–527` задаёт processed как MetaBool, binding как MetaString: внутри этих удаляемых значений нет Div/Span children, whose traversal мог бы менять remaining metadata. Остальная metadata уже проходит те же cleanup callbacks, без strip/нормализации. Поэтому Source даёт эквивалентный возвращаемый body/meta для данного штатного producer contract. `pandoc.write(cleaned,'json')` использует очищенный документ для serialization, не подменяет исходный doc.

## Resource observation остаётся от original doc

`late-filter.lua:71–73` не изменены: observedDoc строится из исходных `doc.blocks` с прежним context-derived course.view; `facts(observedDoc,observedDoc,{})` получает **тот же объект дважды**, сохраняя same-object uses reuse. Saved cleaned AST не передан ни raw/public resource observations, ни role lookup. Следовательно перемещение title Span/удаление attrs не становится новой policy/resource границей.

`native-resources.lua:5–20` add/raw callbacks читают target/src/text и пишут только собственные result/seen; walk return игнорируется. Facts34–73 читает views/uris/files и строит observations, не меняя входной Pandoc. Нет скрытого обязательного AST mutation, которую последняя прежняя cleanup-копия должна была захватить. Observability facade сохраняет actual function delegation; отсутствие или identity mode не вводит другого изменения AST. Чужой произвольный monkeypatch observer/Pandoc writer этим review не квалифицирован.

## TS-run и порядок side effects/errors

| Ветка/фаза | Проверенный порядок |
| --- | --- |
| All guards |binding/request/format/profiles/assignment matching и duplicate late-sidecar отказ до записи, без изменения|
| Occurrence/title/introductions |прежние fragment walks/sidecar data, без изменения callbacks/validation|
| `.late.json` |write/close до Native cleanup как прежде|
| Native |clean copy→body JSON→open native sidecar→original-doc resource observation→role/declaration checks→native write/close|
| Native return |исходные transport meta removals→те же removals cleaned copy→return saved cleaned|
| COURSE_CORE_TS_RUN |saved cleaned остаётся nil; после исходных meta removals выполняется один `clean_document(doc)`, как прежний финальный walk|

Если request, assignment declarations, resource observation, JSON serialization или file open/write отказывает, ошибка по-прежнему прерывает filter без usable successful return. Existing sidecar-open/error cleanup поведение не исправлялось и не расширялось: например Native file уже открывался до resource observation и раньше. Новый fallback/catch/authority/status не введён. Удалённый second cleanup имеет те же callbacks и не добавлял независимую проверку; повторно требовать его ради старой гарантии оснований не найдено.

## Что принято и что неизвестно

Source устанавливает удаление одного повторного whole-document cleanup в обычной Native ветке; TS-run сохраняет один walk. Не меняются privacy/role/projection/resources bytes/owner/lifetime/capture/grade/UUID/source policy. Новых IO/schema/guards/cache/receipt нет.

Writer выполнил только `git diff --check` PASS. Нового meaningful runtime control этого Lua candidate не было: ни existing TS finalize fixture, ни Presentation performance-context не исполняют изменённую Pandoc логику. Рецензент нерелевантные тесты не запускал и mock/call-count framework не предлагал. Реальные Native consumers будут проверены одним планируемым coalesced Core CI по решению root; это будущая qualification, не нынешний PASS.

Wall/CPU benefit **не измерен**. Одновременно saved cleaned AST теперь удерживается во время resource/dependency observation, тогда как прежняя первая temporary copy могла раньше освободиться; peak-memory эффект/GC UNKNOWN. Отсутствие второй allocation/walk не доказывает снижение peak RSS или объяснение5370ms/старых900s. Custom Native Table/Image/metadata/TS-run equivalence Source-supported, но фактический новый Native replay остаётся UNKNOWN до существующего CI. Это честный остаток, не конкретный Source blocker.

Candidate готов к root commit/reviewed coalesced CI. No Source/state/pins/installed/production edits, no commit/push со стороны рецензента.

Own full-origin primary admission: 35 turn_context, строки8–4449, все top/nested gpt-6.1-sol/high; origin 01a1a25f-b0fb-7fd2-b244-83933af77834, latest 01a1a5bb-217e-7c12-9c8d-161414caefca. Globals не менялись; hidden serving tier не удостоверяется; полный env/секреты не читались.
