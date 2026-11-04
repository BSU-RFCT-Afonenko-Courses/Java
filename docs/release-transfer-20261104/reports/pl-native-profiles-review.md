# Независимый обзор PL profiles handoff

Root Sol6.1/ultra, Standard. Прочитаны diff35496cdd..3a6c1c14254f1ca547c3bc2577d80007c83bdcf0 и авторский report, принятый Core API assignment.profiles. Clean для этого узкого consumer diff. Один functionalProfiles const используется при передаче и сравнении actual descriptor, в том числе пустого списка. Projection student/full и пустые строки удаляются из options либо env; порядок, дубликаты и невалидные непустые значения не скрываются, их валидирует Core. Explicit CLI full/student остаётся допустимым, функциональные профили не теряются при отсутствии explicit options.

Root/source/selection/prepared identity и captureInputs args сохранены. Runtime import Core динамический: самостоятельный PL typecheck не доказал бы композицию и не запускался. Реальный native проход требует нового Core и установки пары; он остаётся pending, текущий installed Java/SP PL354 не заменялся. Полный CI, render, inventory/hash и новые fixture не запускались.
