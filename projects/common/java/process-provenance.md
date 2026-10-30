# Происхождение консольного помощника

`process/src/test/java/coursesupport/JavaProcess.java` — точная производная копия общего Java-помощника из `tasks/test-support/src/main/java/coursesupport/JavaProcess.java`, Java `ce4ca6e24a772dd73720670055d2328e3c712522`. SHA256: `4e0e3d55d57e732c6988155cf3f5273f61f794841ba6a6330b0f06054cee75e0`. Канонический владелец — общий Java runtime, используемый Platform/C1. Копия не редактируется; обновление требует проверки равенства каноническим байтам.

Предметные проверки прямо используют `JavaProcess.runCheckingUncaught(Main.class, input).stdout()`. Сохраняются типизированные timeout/output-limit/uncaught-сбои, отдельная JVM для каждого запуска и отсутствие требования `exitCode == 0`. Локальная поставка не подменяет доверенные ограничения grader.
