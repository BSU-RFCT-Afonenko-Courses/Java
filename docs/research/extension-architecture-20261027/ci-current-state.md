# Публичный CI и его доказательная граница

Read-only проверка 27 октября 2026 года подтвердила успешное завершение двух запусков Java:

| Исходник | Запуск | Проверенная область |
| --- | --- | --- |
| Head PR №26 | [Проверка курса](https://github.com/BSU-RFCT-Afonenko-Courses/Java/actions/runs/40401766940) | Оба jobs и все перечисленные steps успешны: публичные source/student, production pins, release assembly, declared inventory и student→full→student |
| Merge PR №26 | [Проверка после merge](https://github.com/BSU-RFCT-Afonenko-Courses/Java/actions/runs/40448249512) | Завершён успешно 27 октября в 07:00 UTC; оба jobs и все steps успешны |

Исследование не запускало и не повторяло эти jobs. Старые pending записи в статусной документации не определяют финальный результат.

Успех CI не означает выполненную приватную qualification, 672 runtime scenarios, реальную Moodle/PL сдачу и правильное начисление баллов. Исторический NOT_VERIFIED admission остаётся отдельным состоянием; зелёный CI не продлевает старый допуск.
