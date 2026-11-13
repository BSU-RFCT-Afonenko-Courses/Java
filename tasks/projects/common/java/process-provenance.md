# Происхождение консольного помощника

`process/src/test/java/coursesupport/JavaProcess.java` — производная копия общего помощника курса из `tasks/test-support/src/main/java/coursesupport/JavaProcess.java`. Канонический владелец — Java. Редактируется канонический исходник; `go-task format` синхронизирует копию, а `go-task check-format` и `go-task check` проверяют равенство байтов.

Предметные проверки прямо используют `JavaProcess.runCheckingUncaught(Main.class, input).stdout()`. Сохраняются типизированные timeout/output-limit/uncaught-сбои, отдельная JVM для каждого запуска и отсутствие требования `exitCode == 0`. Локальная поставка не подменяет доверенные ограничения grader.

Перегрузка `runCheckingUncaught(Path, String, String, String...)` запускает доверенный адаптер вместе с отдельно скомпилированным решением. Отсутствие или ошибка статической инициализации адаптера до готовности наблюдателя остаётся ошибкой инфраструктуры (`IOException`); необработанное исключение после запуска сохраняет тип `UncaughtStudentException`.
