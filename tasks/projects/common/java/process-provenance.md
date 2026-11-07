# Происхождение консольного помощника

`process/src/test/java/coursesupport/JavaProcess.java` — точная производная копия общего Java-помощника из `tasks/test-support/src/main/java/coursesupport/JavaProcess.java`, вечернее обновление 5 ноября 2026 года. SHA256: `329d79c702e952f46cd4113bf9e1b6378f737109dc72ad743dac6b38c79ac3d8`. Канонический владелец — общий Java runtime, используемый Platform/C1. Копия не редактируется; обновление требует проверки равенства каноническим байтам.

Предметные проверки прямо используют `JavaProcess.runCheckingUncaught(Main.class, input).stdout()`. Сохраняются типизированные timeout/output-limit/uncaught-сбои, отдельная JVM для каждого запуска и отсутствие требования `exitCode == 0`. Локальная поставка не подменяет доверенные ограничения grader.

Перегрузка `runCheckingUncaught(Path, String, String, String...)` запускает доверенный адаптер вместе с отдельно скомпилированным решением. Отсутствие или ошибка статической инициализации адаптера до готовности наблюдателя остаётся ошибкой инфраструктуры (`IOException`); необработанное исключение после запуска сохраняет тип `UncaughtStudentException`.
