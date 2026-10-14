# U06 — подготовка рядом с самостоятельным условием

Статус **DONE / FROZEN** после финального seal этой ограниченной корректировки. Автор `/root/u06`; accepted input `9f28162c77cccbb2d651a569bce5ed88bd4d67df`. Requested `gpt-6.1-sol / ultra`, effective model/effort `not_reported`. Текущий owner window разрешает только `tasks/bank/arrays.qmd`, этот новый packet/report и новые evidence в `/tmp/java-course-revision-checks/U06/native-preparation-layout`. Старые U06/M02/M03/M04 доказательства и запечатанные пакеты не переписывались.

## Решение автора и педагогическое основание

В публичном каноническом банке две адресные ссылки подготовки сохранены обычными QRC. Они находятся в видимом абзаце непосредственно перед `exr-j25-arrays-sort-matrix-rows`; этот абзац явно связывает подготовку с упражнением через его собственную QRC. Новый heading, semantic ID, ручная нумерация или отдельная навигационная система не создавались.

```markdown
Подготовка к заданию @tasks:exr-j25-arrays-sort-matrix-rows: @practice:sec-j25-practice-arrays-row-key и @handbook:sec-j25-api-arrays-sort-stability.

:::: {#exr-j25-arrays-sort-matrix-rows difficulty="advanced" time="75" target="prairielearn" project="/projects/sort-matrix-rows" project-check="java-labs"}
```

Изнутри упражнения удалено только первое предложение прежнего абзаца:

```text
Подготовка: @practice:sec-j25-practice-arrays-row-key и @handbook:sec-j25-api-arrays-sort-stability.
```

Следующее предложение остаётся внутри экспортируемого условия без изменения:

> Можно написать собственную устойчивую сортировку строк или использовать устойчивую сортировку массива объектов; конкретный алгоритм не предписан.

Ссылки ведут к объяснению ключа строки и устойчивости; они не задавали дополнительную обязательную реализацию или ограничение. Все обязательства уже прямо изложены в самостоятельной карточке: матрица3×3, диапазон−3…3, перестановка целых строк, неубывание сумм, сохранение порядка при равных суммах и элементов внутри строки, обязательный исходный пример, пустой stdin, допустимый дополнительный ввод и точный вывод. Обязательные примеры и свобода двух подходов сохранены побайтно. Ни одно требование не переведено в «подготовку» и не скрыто условным блоком.

Такое расположение совместимо с принятым разделением объяснения и проверки: читатель банка видит точные введения рядом с конкретной задачей, а standalone grading condition сохраняет все обязательства и остаётся самостоятельным. Native Body не обязан импортировать чужой учебный раздел, чтобы сообщить уже опубликованное правило. Смена source representation ограничена этой карточкой; исходные runtime/педагогические контракты остальных работ не пересмотрены.

## Почему исправляется именно расположение

Полностью прочитан frozen `.superpowers/sdd/execution-plan/m04-export-diagnosis-report.md`. Принята его фактическая диагностика: HTML QRC targets существуют, но stock JSON Body содержит marker и pinned producer намеренно отказывает с `BODY.QRC_REFERENCE_UNRESOLVED`. У установленного Body нет catalogue resolver/CLI input; verified публичного HTTP(S) адреса нет. Изменение target spelling, относительная подмена ссылкой на чужой HTML и повтор полного CI не устраняют этот guard.

Здесь marker остаётся в исходном документе и его публичной bank projection, но находится за границей exercise Div. Штатный collector извлекает только body выбранной карточки; в нём marker больше нет. Guard остаётся активным и не обходится. QRC policy, vendor, pins, CI/config, JSON producer, project assets, публичный URL и внешняя платформа не менялись. Git в исходнике или experimental copy не изменялся; clean candidate commit для этой stock проверки не потребовался.

## Source, AST и аудит 17 подключённых карточек

Авторский before совпадает с root snapshot `.superpowers/sdd/execution-plan/scratch/u06-native-prep-before/arrays.qmd`. Изолированная копия содержит1431 tracked файла, снятых read-only `git ls-files`, с точными bytes/modes и отдельным manifest; `.git` не копировался и не создавался. Единственный изменённый исходник уже был конкретным кандидатом до запуска инструментов.

- Before source SHA256: `311a8f7627c4d29bdd5e1e9d5d9398b99f5a13c89b30bab95651129145f86b9e`.
- After source SHA256: `777763b2ec4f86768954098ba106af6fbc2038edb111b66f536ff35017da3d0d`.
- Before selected source-body SHA256: `6b0c302958c0f083dc88e87d4e73aede99c7e3bf980b987ef36c5daa7c92f70e`.
- After selected source-body SHA256: `a121443155eb859e08b9bcac3fd9a66464dc2407d09f16c53a984011f440a37f`.

`source-change.json` и `layout.diff` фиксируют полное точное правило изменения. Проверка whole-source equality допускает только добавление указанного соседнего абзаца и удаление указанного предложения из body. Categories, все IDs/attributes/time/project route, bibliography key и остальной текст неизменны. Новая self-exercise QRC учтена: её authored occurrences0→1; два reading-target occurrences остаются1→1 каждый.

Raw Pandoc before/after сохранены. Семантическая AST comparison исключает только автоматически вычисляемый `citationNoteNum`: новая self-QRC добавляет одну citation occurrence, поэтому этот счётчик сдвигается на1 у существующей библиографической ссылки выбранной карточки и последующих карточек. CitationId, attrs, исходный текст и все остальные поля сравниваются строго. Только один содержательный Para внутри выбранного body меняется; его suffix начиная с «Можно» полностью совпадает. Остальные array exercise AST совпадают после этой единственной технической нормализации. Это не разрешение удалять или игнорировать ссылки/библиографию.

Read-only audit охватил все17 подключённых PL bodies из numbers/control/arrays: после переноса0 QRC blockers внутри них. Attributes всех17 сохранены; файлы трёх labs и случайное назначение6/4/4 не менялись. В выбранном native пакете ЛР3 четыре required slots и одна random пара из двух кандидатов. L3 remains25+25+55+75=180; всего1935+отдельно300 и остальные оценки не затронуты.

## Actual stock selected-work export

В exact isolated copy выполнен неизменённый установленный Core CLI:

```sh
quarto run tasks/_extensions/Afonenko-Course-Tools/course-core/entrypoints/export.ts -- --book tasks --work sec-j25-lab-arrays --output /tmp/java-course-revision-checks/U06/native-preparation-layout/arrays-body.json
```

Cwd: `/tmp/java-course-revision-checks/U06/native-preparation-layout/source`. Process-local `XDG_CACHE_HOME`/`TMPDIR` направлены в новые evidence; CUE — `/home/tolya/course-tools/local-tools/cue/cue`. Actual Quarto1.11.5, CUE0.17.1; global tools/settings не менялись.

`native-export.result.json`: начало `2026-10-14T14:18:40.546749Z`, завершение `2026-10-14T14:19:55.862465Z`,75.316s,exit0. Stdout: `Экспорт: 5 вопросов, java-semester-one/sec-j25-lab-arrays`. Этот selected-work API штатно выполняет обязательный JSON проход41taskdocuments, затем выбирает5candidate bodies. Это не полный course HTML render, не новый PL course delivery и не host/container backend acceptance.

Actual native writer сохранён в `actual-arrays-native-writer.json`: обе full/public exercise Div выбранного задания не содержат QRC marker; соседний prep с тремя exact QRC остаётся в обеих document projections. Public Body имеет5questions/4slots; ни одна из пяти condition не содержит QRC. Body producer/citeproc/resource guards использованы штатно. Все исходные обязательства и предложение с двумя подходами присутствуют в actual public condition.

Canonical JSON hashes actual пяти candidate conditions (UTF-8 compact JSON, ключи в исходном порядке):

| Candidate | Condition SHA256 | Blocks |
|---|---|---:|
| `exr-j25-arrays-array-average` | `86c974224303d33e1ab1ee77df06163b401e79fa49595e110bf0a716e64aecc4` | 9 |
| `exr-j25-arrays-reverse-array` | `f4f5b63cebaeff0e40cf9951f773261134b51593f44e33960486bbf4730cc06f` | 8 |
| `exr-j25-arrays-subtract-array-average` | `f4f91a810de2045f4143b3e2ec8d43fcf130b9055313a84558af695cf4d94196` | 10 |
| `exr-j25-arrays-move-row-zeros` | `a8c65b585e73fa895c68c26f9fb7623964a970dcb4f7c8b19a5ad96f48c94310` | 8 |
| `exr-j25-arrays-sort-matrix-rows` | `887040282882253fcad749100b859b7a8328dacae3ce29d64a799210339e8e84` | 9 |

## Один native HTML bank page и фактическая читаемость

В той же isolated copy выполнен ровно один page render:

```sh
quarto render tasks/bank/arrays.qmd --profile student
```

`bank-html.result.json`:12.951s,exit0; actual page `source/tasks/_output/student/bank/arrays.html`. Native local QRC post-report:1resolved/5deferred/12targets/1page. Разрешённая локальная ссылка — новая self-exercise association; иностранные reading targets штатно deferred до root-level linking.

Далее неизменённый stock QRC `readPage/linkPages` обработал именно этот свежий HTML с target records из принятого прежнего root catalogue:

- Source: `/tmp/java-course-revision-checks/M04/ready-project-checks/source/_site-student/reference-catalog.json`.
- SHA256: `81ec5cdb7922f626cd6e061ee84d18c67827c78eecfd072a7973c5e571d78e1d`.
- Обе foreign reading target records побайтно по JSON-содержимому равны принятым records; обе exact definitions присутствуют в текущих `practice/arrays.qmd` и `handbook/entries/arrays.qmd`.
- Self-exercise target взят из свежего rendered page и проверен по текущему `exr-j25-arrays-sort-matrix-rows`; старый record этого page не использован вместо нового.
- Результат:6resolved/0deferred. Это шесть QRC этой страницы, включая новую self association; не новый whole-course catalogue или M05 cycle.

`link_witness.ts`, `html-link-witness.json`, `bank-readability-linked.html` и `readability-fragment.html` сохраняют вызов, точный источник каталога/hashes и actual output. Перед `<div id="exr-j25-arrays-sort-matrix-rows">` находится видимый отдельный paragraph:

> Подготовка к заданию Упражнение19.4: Характеристика строки и порядок и Устойчивость сортировки строк массива.

В actual HTML все три подписи — обычные ссылки. Self href `arrays.html#exr-j25-arrays-sort-matrix-rows`; practice href `../../practice/arrays.html#/sec-j25-practice-arrays-row-key`; handbook href `../../handbook/entries/arrays.html#sec-j25-api-arrays-sort-stability`. Hash-slide form practice является нативной reveal ссылкой из существующего каталога, не выдуманным адресом публикации. Paragraph не вложен в exercise, не спрятан и непосредственно соседствует с ним. Внутри следующего Div видны заголовок,75мин, обязательный контракт и неизменённая свобода подходов.

Это узкая HTML/DOM/link readability evidence с reuse принятого каталога для других страниц. Она не заявляется новой полной сборкой курса, новым просмотром каждого viewport или финальной публикацией.

## Проверка, incidents и handoff

`python3 /tmp/java-course-revision-checks/U06/native-preparation-layout/verify.py` — **107/107**,exit0. Это source/AST/17-body audit/actual native package/one-page HTML/catalogue/hash/scope checks. Первый comparator дал97/105 из-за автоматического Pandoc citationNoteNum после новой self-QRC; исходные raw AST и `verification-first-attempt.json` сохранены. `citation-counter-diagnosis.json` фиксирует реальное различие; comparator поправлен только на счётчик, курс из-за harness не менялся. После уточнения включились два дополнительных проверочных утверждения о единственном paragraph suffix и двух removed Cite nodes. Native export и HTML повторно не запускались.

Все1431 tracked bytes isolated copy после native/HTML операций сохраняют source manifest; постоянные configs/vendor/pins/CI/project/Java files не менялись. Остальные bank sources не затронуты. Старые sealed evidence/packets/reports, M04 clone, глобальные настройки, Git и live grading не изменены. No agents/chats/publication; requested profile не выдан за verified runtime.

Root получает concrete source candidate и этот frozen handoff. Оригинальный независимый U06 reviewer проверяет narrow pedagogical/native closure; новый собственный reviewer не запускался. После review root обновляет source-hash provenance M02/M03 и передаёт M04 повтор полного stock native PL export с обоими backends. Исходные17questions/14positions6/4/4 и public/private safeguards остаются обязательными. Полный M04/Task13/M05 acceptance ещё не заявлен этой авторской коррекцией.

Final source/packet/report hashes и UTC находятся в `/tmp/java-course-revision-checks/U06/native-preparation-layout/freeze-seal.json`. После него записей не будет без нового owner window от root. Авторского blocker или оставленного решения в этой корректировке нет.
