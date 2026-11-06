# Проверки курса и оставшийся переход Platform

Обычный [workflow](../.github/workflows/check.yml) выполняет одну
student-сборку в disposable checkout и сохраняет полученный сайт.
`bash CI/render.sh` и `bash CI/check-projects.sh` выполняют ту же сборку;
явный аргумент `full` выбирает авторскую выдачу. Повторный цикл
student/full/student и отдельный vendor/source-pins job удалены.

CI не сравнивает content SHA, immutable inventories, proof или receipts
курса. Установленные расширения можно редактировать обычным способом;
самостоятельные source assertions не заменяют исполнение grader.
Текущий Java workflow не запускает учебные reference/starter/negative cases.
Их реальное исполнение остаётся отдельной проверкой выпуска; green сайта
не является grading PASS. В SP сохранён существующий учебный unittest suite,
включая работу готовых fish/PowerShell scripts и содержимое выдаваемых ZIP.

Проверки student privacy, ссылок, содержимого выдачи, UUID/assessment slots,
эталонов, допустимых альтернатив и отрицательных решений остаются полезными
проверками результата у соответствующих владельцев. Их учебный договор,
баллы и hidden cases этим пакетом не меняются.

## Оставшийся внешний переход

Отключённые `CI/check-production-pins.py`, его тесты и прежний local-candidate
маршрут удалены: они проверяли собственные hashes и обращались к удалённой
Platform release assembly. `CI/toolchain.json` сохраняет версии инструментов,
Platform source/release refs, native OCI identities и адреса функциональных
assets. Собственные runner/asset hashes, Git tree/tag-object proof и ссылка
на больше не выпускаемый toolchain lock удалены. Исторические отчёты
сохранены как записи прежних проверок.

Полный Native export и фактическое контейнерное оценивание требуют отдельного
ограниченного запуска штатных команд Platform после согласования его нового
публичного контракта. Старые `inventory`, `delivery.json`, receipts и proof
не объявляются обязательным форматом передачи курса этим документом.

Ни полный render, ни экспорт, ни контейнерное оценивание в Source-пакете
4 ноября не запускались. Фактическая проверка публичной выдачи после
обновления установленных расширений остаётся открыта.
