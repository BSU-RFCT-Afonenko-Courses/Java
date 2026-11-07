# Происхождение API-помощника

`api-support/src/api/java/org/prairielearn/api/PlatformApiPrecheck.java` — точная производная копия Platform `0128212082c21e7ff2ae5868e191aa7f02984e43`, `runtime/java25-junit-v1/PlatformApiPrecheck.java`. SHA256: `2d12c5c210123a4621819a102928d54f51d4ce7e1d7064547c80e25c283b89aa`. Канонический владелец — Platform. Копия не редактируется; при изменении канона обновляется с проверкой равенства байтов.

Это существующий API precheck Platform, общий для IDE и grader. `ApiContract.java` каждого проекта — единственный предметный клиент. JSON-simple объявлен зависимостью только в Gradle. Локальный Gradle вызывает штатный `PlatformApiPrecheck.main`, затем проверяет результат; оценку, scoring и student feedback он не рассчитывает.
