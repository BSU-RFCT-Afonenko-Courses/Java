# Проверки курса и оставшийся переход Platform

Обычный [workflow](../.github/workflows/check.yml) выполняет одну
`go-task build` в disposable checkout и сохраняет сайт и Native export.
`bash CI/render.sh` и `bash CI/check-projects.sh` выполняют ту же сборку;
явный аргумент `full` выбирает авторскую выдачу. Повторный цикл
student/full/student и отдельный vendor/source-pins job удалены.

CI не сравнивает content SHA, immutable inventories, proof или receipts
курса. Установленные расширения можно редактировать обычным способом;
самостоятельные source assertions не заменяют исполнение grader.
Declared/delivery jobs исполняют проверки готового Native выпуска.
Green сайта сам по себе не является grading PASS. В SP сохранён существующий учебный unittest suite,
включая работу готовых fish/PowerShell scripts и содержимое выдаваемых ZIP.

Проверки student privacy, ссылок, содержимого выдачи, UUID/assessment slots,
эталонов, допустимых альтернатив и отрицательных решений остаются полезными
проверками результата у соответствующих владельцев. Их учебный договор,
баллы и hidden cases этим пакетом не меняются.

Адресный [тест Core](https://github.com/Afonenko-Course-Tools/quarto-course/blob/ee461578fe8ba704395cd86eeb3847a0930ea22d/tests/release-generated-resource-currentness.ts) с `--r-cache` проверяет два захвата неизменного QMD с R/knitr:
однократное исполнение ячейки, позднее тело и ресурсы, выбранные роли,
материализованные README/PNG и настоящий Download ZIP с проверкой состава,
CRC и байтов. Этот узкий состав не подтверждает все режимы cache/freeze,
другие движки, PL, Site или полный курс.

Выбор исполнения, cache/freeze и обновления остаётся у автора средствами Quarto.
[Авторская инструкция](https://github.com/Afonenko-Course-Tools/quarto-course/blob/0ee57462e4fa41465540ae7ccc7cd4ac30338d40/docs/authoring-style-guide.md#L284) описывает неполноту автоматического учёта внешних зависимостей
и условный риск повторного использования неразмеченного вычисляемого текста
между аудиториями. Для Site сохраняются [договор строкового списка subprojects](https://github.com/Afonenko-Course-Tools/quarto-project-publish/blob/main/spec/contract.md)
и [границы публичной Native-композиции](https://github.com/Afonenko-Course-Tools/quarto-project-publish/blob/main/docs/public-operation.md);
адресная проверка R/knitr не заменяет их отдельную приёмку.

## Оставшийся внешний переход

Native export выполняется штатной Platform из `.platform`, закреплённой
Git submodule. CI строит один выпуск и запускает declared/delivery проверки
с тем же фактическим Platform HEAD. Результаты этих jobs устанавливают
готовность соответствующих сценариев; учебные договоры этим переходом
не изменяются. Порядок обновления зависимостей описан в
[инструкции](provider-installation.md).

Исторические отчёты сохраняют границы проверок своих source revisions.
Новый установленный выпуск требует реального render/export и проверки
соответствующих результатов.

## Границы прежних приёмок

[Срез 5 ноября](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-checkpoint-20261105-1130/REPORT.md)
подтвердил конкретные student/export/runtime/Workspace маршруты своего source.
Он не закрывает эксплуатационную проверку существующего Moodle/Gateway-входа,
принадлежности записей, соответствия SP учреждению БГУ, восстановления данных
и переключения выпуска. Текущий запуск не изменяет недоступный сервер,
Moodle, аккаунты или данные; эта приёмка остаётся отдельной фазой.

Адресные примеры не подтверждают весь корпус feedback, наличие отдельного
типизированного поля допуска, межпрофильную приватность cache/freeze,
обновление внешних данных или все движки. Штатные кеши Quarto сохраняются
без собственной гарантии свежести. Полнота внутренних фаз Performance
и сопоставимое ускорение полного курса не выводятся из ограниченных измерений;
inclusive acquire включает callback и не равен собственному времени YAML.
Предметные знаменатели, обязательные веса и закрытые значения при этом
сохраняются по действующему договору. Исторический календарь и прежние
локальные разрешения чтением отчётов не возобновляются.
