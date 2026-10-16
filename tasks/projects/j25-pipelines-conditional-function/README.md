# Техническая поставка: exr-j25-pipelines-conditional-function

Условие взято из `tasks/bank/pipelines.qmd` в `c9d05aea901fb819a5fcbb44bbf8a117caaeb9b2`; card SHA256 `e4ccc6d44305013072cbf18b84d5eae60adb431d86945c81a16ae8ab65c4d557`. Публичная часть — только student/. reference/ и tests/ закрыты, в student ZIP их нет.

Свободное решение сначала компилируется само. Затем преподаватель подготавливает tests/bindings/<вариант>/TaskBinding.java; это имя и методы адаптера не являются API студента. Ошибка привязки — manual/tool boundary без оценки поведения. Private/nested размещение без доступной внешней привязки проверяется по исходнику/демонстрации. Привязка не должна заново реализовывать алгоритм.

reference/primary и reference/alternate имеют разные пакет/форму вызова; alternate count-characters использует Character keys. tests/junit/public содержит открытые сценарии, tests/junit/private — границы. tests/fixtures/wrong — Всегда истинная ветвь игнорирует условие.

Ручная часть: Имя/тип ternaryOperator обязательны; класс, пакет, static и видимость свободны. Приватное размещение допускает ручную проверку/привязку внутри авторского контекста без нового public API.

Запуск после готовности shared runner: `python3 ../../test-support/check-java-project.py --project . --sources reference/primary --binding tests/bindings/reference --work /tmp/новый-каталог --platform <platform>`; alternate выбирает свой sources/binding. До точного receipt команда не считается доказательством. Регистрация Moodle/PL и Windows не выполнялись.


API preflight в tests/api/{reference,alternate} выбирается для известного teacher placement, до binding compile; отражение читает только method metadata без initialization/access suppression. Проверяются generic variance и реальные positive/negative compiler clients. Конфигурация не закрепляет Study/variant.Example, visibility или static для иной сдачи. Недоступный private вариант остаётся ручным.
