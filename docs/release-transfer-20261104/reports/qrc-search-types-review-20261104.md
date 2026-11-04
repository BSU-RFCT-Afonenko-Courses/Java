# QRC search DTO и устранение повторного чтения — review

Вердикт root: **APPROVE** финального candidate от8732dbbe0d8502f8b6a77049e27907ae8a66e46a. Прочитаны оба production diff, существующий import-config.isRecord, итоговый test и сохранённые commands/results. Новых проверок/Native запусков reviewer не делал.

Search JSON остаётся unknown до единственного existing row guard; guard теперь также корректно отказывает null/nonobject. SearchRow описывает только href/text, остальные metadata сохраняются. updateSearch возвращает final rows в прежнем порядке; publish использует их без второго чтения/JSON.parse каждого индекса. Новой Native/schema/immutable/cache-key гарантии нет. Внешние изменения файла между двумя прежними чтениями больше не определяют merge — используется только что обработанный результат; это намеренная граница, не attestation файлов.

Первый review выявил реальное отличие для повторного exact index.path с разными mounts. Исправлено локальным rowsByPath: все ordered DTO одного path используют последние записанные rows. Различающий существующий control подтверждает Last text для обоих mounts, порядок и extra metadata. Полная canonical identity произвольных hardlink/symlink aliases этим не заявляется; новых realpath/hash проверок нет.

В existing test удалены все explicit any: JSON unknown, Array/isRecord assertions выводят типы callbacks без fake casts. Production helper не дублирует проверку typed внутренних DTO. Нет новых FS/JSON чтений, кроме прежнего алгоритма updateSearch; лишний второй read удалён.

Writer evidence: final changed behavioral control и affected bundled compiler PASS; общий бюджет всех изменённых candidates4.7143s из30. Первоначальный TS2483 сохранён отдельно; следующие compiler checks относятся к новым Source fixes, неизменные зелёные controls не повторялись. Native/render/full suite не запускались; общий speedup курса не измерялся. [Отчёт и evidence](qrc-search-types-20261104.md).

Root собственный origin interval01a1a4b3-8fd3-7be3-baf7-ce0cc5c60016 сохраняется; последние прочитанные contexts7160/7983/8022/9120/9348/10492 top/nested Sol6.1/high PASS, globals не менялись. Standard назначен, hidden tier не утверждается.
