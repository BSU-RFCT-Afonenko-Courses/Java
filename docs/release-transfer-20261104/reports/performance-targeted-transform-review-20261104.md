# Performance targeted transform — независимый review

Root: **APPROVE** candidate поверх e726. Прочитаны весь transformer, оба changed
tests и сохранённые результаты компилятора/runtime. Повторных запусков не было.

Selector разрешает только уникальные top-level FunctionDeclaration с body;
missing/ambiguous/unsupported запрос не выдаёт успешную пустую карту. В selected
ветке walk обрабатывает только эти declarations и не вставляет wrappers во
вложенные функции. Исходные nested bodies остаются и исполняются; обычный scope
без selector идёт по прежнему рекурсивному пути. Изменение findManual сохраняет
старое поведение default, а selected scope не получает отказов от чужих
невыбранных callbacks. Manual top-level rebind по-прежнему не оборачивается дважды.

Проверен существенный эффект через настоящий TS agent: selected decode сохраняет
return/рекурсию/error identity, события возникают только у decode; default
по-прежнему наблюдает пять вызовов nested visit. Это полезный execution control
нового механизма, а не подсчёт текста wrappers. Saved affected compiler и6/6
controls PASS суммарно1.745083s/cap15/retry0. Bundled Quarto/runtime и новые Native
experiments не запускались.

Нового CLI/config/registry/schema или runtime event filter нет. Map намеренно
частичный; caller должен обозначать partial coverage. Candidate устраняет
ненужные wrappers при opt-in scope, но не доказывает ускорение курса и не
исправляет потерянные данные terminal Java trace. Следующий полный Java run
этим review не назначается. Блокирующих Source findings нет.
