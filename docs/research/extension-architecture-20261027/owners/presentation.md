# Presentation: T06/H40

Источник — `inputs/owners/core/_extensions/course-presentation`, bundle8.3.0 provider3ccc9e8; current contracts прочитаны. Новые browser/render tests не запускались. Presentation независим от Core для обычных документов; если metadata `course` присутствует, filter проверяет, что Core уже обработал AST. Он не получает нового NativeRun/snapshot/witness слоя и не вызывает inspect или Deno subprocess.

## Операции и частота

`filter.lua:28–51` всегда читает узкий config (`answers:auto|expanded`, lang, pedagogical defaults), собирает Div identity map одним whole-document walk, затем рекурсивно topdown обрабатывает Div. Возврат `false` предотвращает второй native обход уже обработанного subtree; явная рекурсия нужна для owner задач и внешних `sol-*`. `metadata.decorate` добавляет badges и переносит author attrs в data-course-* после Core capture; сохраняет canonical Header первым для native theorem title. `answers.kind/wrap` оставляет исходный ID на inner Div, использует native Bootstrap collapse в HTML, details в Reveal, expanded wrapper в print/non-HTML. Это сохраняет native crossrefs и публичность выбранного Core AST (RQ01/RQ05/RQ11; IN01/IN03).

Feature predicate, полностью выключающего эти два AST passes на plain doc без blocks/defaults, нет. В HTML/reveal filter всегда читает html-dependency.json и подключает disclosure.js/presentation.js/CSS, даже когда answers/metadata не найдены. H40 в этой узкой форме подтверждена статически; substantial latency/work не измерены. Inactive installed extension без подключения filters/assets сам по себе не выполняет Presentation.

| Browser operation | Activation/lifetime | Работа и смысл |
|---|---|---|
| initialize/localizeQuartoLabels | DOM ready, один раз; только ru запускает localization | Три selectors, начальный body и MutationObserver только added subtrees, attribute writes не повторяют callback. Нужность динамического Quarto search/anchors не требует whole body scan на каждый event |
| labelTabPanels | init и beforeprint | Добавляет title только при отсутствии marker; линейный scan tab panels для readable print |
| initializeReveal/setMode/normalizeNotes | Reveal существует; ready, mode click, slidechanged, notes mutation | Используется штатная notes panel/speaker plugin. Копии notes лишаются ID, original slides сохраняют ID; UI mode не меняет аудиторию Core. normalizeNotes scans IDs текущей копии panel, не всего course |
| expandForPrint/restoreAfterPrint | print-pdf URL либо beforeprint/afterprint | Один guard printing, раскрывает только existing DOM details/collapse, запоминает изменённые nodes и восстанавливает прежнее состояние; notes скрыты, print-pdf остаётся expanded |
| disclosure.expose | initial hash, hashchange, local anchor click, explicit navigation/search | Разворачивает ancestor chain target, native Bootstrap collapse, notes panel и Reveal fragments; requestAnimationFrame scroll после native transition. Не парсит QMD и не восстанавливает удалённые private bytes |

Presentation имеет DOM state для печати и notes, а не full filesystem snapshot. MutationObservers и listeners рассчитаны на lifetime страницы; явного public destroy API нет. Это не доказанный leak при обычном page lifetime; SPA/multiple init lifecycle не заявлен и не проверен. Notes-copy toggle ищет соответствующий original details по порядку; binding semantic correctness покрывает browser fixture, не TS typecheck.

Потенциальный cost: initial two AST passes + Div transformation O(A) относительно AST size A; decorator badge work пропорционален Div count. Browser initial selectors/body localization и tab labels O(DOM); added-subtree localization пропорциональна вставкам, normalizeNotes пропорционален copied panel. Disclosure O(ancestor depth) плюс target reveal work; print scans whole public DOM только по событию. Эти величины не равны n source inputs либо full bank proof Core.

## Coverage, evidence и ограничения

Semantic operations P01 (Lua config/identity/decoration/disclosure wrappers), P02 (browser modes/notes/localization/print), P03 (anchor/disclosure) перечисляют все runtime modules и callbacks в core-operations.json. Метрики bridge — identity/off или ABI1 registry; own overlay snapshot отсутствует. `tests/presentation.ts`, `tests/presentation/browser.cjs`, `tests/presentation/unified.browser.cjs`, navigation browser совместное поведение принадлежат Core test ledger и классифицируются отдельно как fixtures/assertions/process/UI drivers; их наличие не объявляет новый PASS.

H40: лишняя unconditional работа на подключённой plain HTML странице установлена; необходимость функций оформления/печати/раскрытия обоснована, необходимость этой частоты на отсутствии блоков — нет. Малый proposed C-11: одна plain и одна содержательная страницу, attached/absent Presentation при одинаковом Core route; count AST walks/assets/listeners/DOMContentLoaded и output equivalence. Никакого full course/browser bank повторения для этого вопроса не требуется. Контроль: ID/Header positions, native crossrefs, student privacy, public answers, print state и Bootstrap/Reveal note disclosure. Runtime доля неизвестна, продуктовых изменений нет.
