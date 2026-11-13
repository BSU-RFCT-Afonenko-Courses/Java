# Происхождение API-помощника

`api-support/src/api/java/org/prairielearn/api/PlatformApiPrecheck.java` — локальная адаптация Platform `runtime/java25-junit-v1/PlatformApiPrecheck.java` для самостоятельных Gradle-комплектов. Каноническая реализация проверки API принадлежит Platform; адаптация сохраняет существующий договор локального запуска.

Отличия адаптации: в JSON-результат переносится переданная `signature`; JSON собирается через `JSONObject`; `Api.close` объявляет `throws Exception`; локальный `Mismatch` не имеет `serialVersionUID`. Эти отличия существовали до подключения форматирования. Прямое копирование текущего Platform-файла удалило бы локальную `signature`, поэтому побайтовое равенство с ним не заявляется. Изменение общей логики сначала проверяется у владельца Platform, затем переносится с сохранением локального договора. Форматирование и статические проверки охватывают обе реализации.

Это существующий API precheck Platform, общий для IDE и grader. `ApiContract.java` каждого проекта — единственный предметный клиент. JSON-simple объявлен зависимостью только в Gradle. Локальный Gradle вызывает штатный `PlatformApiPrecheck.main`, затем проверяет результат; оценку, scoring и student feedback он не рассчитывает.
