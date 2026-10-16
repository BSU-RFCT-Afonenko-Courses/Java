# Compile-only поставка exr-j25-exceptions-override-exceptions

Frozen `tasks/bank/exceptions.qmd` / `c9d05aea901fb819a5fcbb44bbf8a117caaeb9b2` / SHA `3e736a838b9f4c817708610ddda11b9a3caac7200a2f258683f035a77a4ba11b`. Даны A/B с наследованием; f() — instance void override. checked exception B сужается/сохраняется/отсутствует; unchecked допускаются. Нет обязательного runtime результата: даже допустимый RuntimeException из f не превращается в ошибку решения этой compile-only задачи.

Reference contexts независимы: source компилируется по одному каталогу, отдельный output на каждый. Alternate содержит private nested A/B, другие параметры constructors и unchecked throws. Никакого getDeclaredFields/access suppression/Student initialization в API preflight нет. Selected teacher placement неизвестно — manual/tool failure без student blame. Методные/constructor names внешнего launcher свободны. Published A/B/checked areas проверяет canonical PlatformApiPrecheck до любой необязательной демонстрации; helper compile без student classpath, реальные typed clients только для доступного flat placement.

Это compile-only/signature цель. JUnit-заглушки и искусственный behavior score не добавлялись. tests/compiler-cases.json — полная inventory positive/negative/API/no-init контекстов; реальные команды/logs/receipts host и image966c находятся в CONTROL/technical/remaining-delivery-2. Source compilation/API failure отделяются; runtime не требуется и не используется для отказа в приёмке. В частности unchecked-throws вариант override может завершиться исключением, оставаясь корректным compile-only решением. Проверка независимости версий, объяснение и сохранность заданного контекста остаются ручными.

Public ZIP только student/, исходный original.java.txt не является готовым эталоном и не попадает в src compilation. Reference/negative/API fixtures не публикуются. Shared runner/Platform runtime не расширены. Нет регистрации/Windows/commit/external действий.

