# Техническая поставка: exr-j25-pipelines-order-points

Условие взято из `tasks/bank/pipelines.qmd` в `c9d05aea901fb819a5fcbb44bbf8a117caaeb9b2`; card SHA256 `070494708fca97315f18f8bcef7320f0985b424655962721463fa1d625c8d31d`. Публичная часть — только student/. reference/ и tests/ закрыты, в student ZIP их нет.

Свободное решение сначала компилируется само. Затем преподаватель подготавливает tests/bindings/<вариант>/TaskBinding.java; это имя и методы адаптера не являются API студента. Ошибка привязки — manual/tool boundary без оценки поведения. Private/nested размещение без доступной внешней привязки проверяется по исходнику/демонстрации. Привязка не должна заново реализовывать алгоритм.

reference/primary и reference/alternate имеют разные пакет/форму вызова; alternate count-characters использует Character keys. tests/junit/public содержит открытые сценарии, tests/junit/private — границы. tests/fixtures/wrong — Пустой вывод теряет непустой список.

Ручная часть: Stream API, лямбда-компаратор, модель точки свободна. Перевод int[][] в модель — только teacher binding.

Запуск после готовности shared runner: `python3 ../../test-support/check-java-project.py --project . --sources reference/primary --binding tests/bindings/reference --work /tmp/новый-каталог --platform <platform>`; alternate выбирает свой sources/binding. До точного receipt команда не считается доказательством. Регистрация Moodle/PL и Windows не выполнялись.

