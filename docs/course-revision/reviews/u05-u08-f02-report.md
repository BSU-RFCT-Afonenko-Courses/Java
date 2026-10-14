# U05 / U08-F02 theory — отчёт узкого исправления

Статус: **DONE / FROZEN**. Accepted input `3c90ebd`; owner `/root/u05`.
Requested `gpt-6.1-sol / ultra`; effective model/effort `not_reported`.

В `theory/chapters/exceptions.qmd` перед первым record-pattern switch
добавлено видимое введение: проверка варианта и связывание компонента;
`value` имеет тип `int`, `problem` — `ScoreProblem`. Инструкции и API
не изменены. Новый packet — `docs/course-revision/packets/U05-U08-F02.md`.
Lecture counterpart отправлен root отдельно владельцу U07; здесь лекции
не редактировались.

SHA256 before: `a77f5bd6e50e4512c877fb87313b5793c53229f7e5723742db91666b00e07a00`.
SHA256 after: `127d6c39e2ec883de972973ab06f71000220b13eb36cca4877206d5adf0a1dc6`.
Delta: +653 bytes, +8 строк, +54 U00 reader units (2964→3018).
CodeBlock14/Cite11 и все ID/categories сохранены побайтно/структурно.

Fresh focused verification **9/9 PASS**, exit0:
`python3 /tmp/java-course-revision-checks/U08/owner-F02-theory/verify.py`.
Native parse ограничен affected chapter before/after; доказано точное
добавление только одного абзаца, его положение до первого case, неизменность
CodeBlock/Cite/IDs/metadata/fences и явные типы bindings. Новые строки fence
записаны в `fence-metadata.json`. Первоначальный local checker по ошибке
возвращал exit1 при всех true; полярность исправлена, исходные checker/output
сохранены, source/assertions не менялись.

Normative source: JLS25 §14.30.1–14.30.2, record patterns, nested var type,
component binding. Новая цитата/ID в QMD не добавлена. Полное before/after,
diff/proof находятся только в `/tmp/java-course-revision-checks/U08/owner-F02-theory`.

Контракт, время, текущие примеры и результаты, старые U05 packet/report/
seal/evidence, prototype/assets/tests и остальные исходники не менялись.
Git/journal/subagents/global settings не использовались. Broad render/tests
и публикация не выполнялись; общий пакет финальных проверок у root.
Теоретическая часть F02 заморожена и возвращается root.
