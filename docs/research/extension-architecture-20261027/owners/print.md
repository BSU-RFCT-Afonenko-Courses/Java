# Print: явный экспорт и ранние проверки

Выбран Print0.3.1. Его контракт прямо сохраняет public Body v1 и legacy Core4 fixtures. Совместимость с Core8.3/Bodyv2 не заявлена; legacy ограничение не названо новой регрессией Java. Print не установлен в baseline Java и не относится к цене обычного HTML render.

`preparePrint` сначала вызывает validatePackage, затем prepareValidatedPrint. `renderPrint` также получает validated package и вызывает тот же внутренний helper. В transport validateProduction проверяет condition/publicAnswer каждого вопроса; prepareValidatedPrint повторяет validateBody выбранных вопросов до structuredClone. Между этими проверками нет await или изменения входного Body. Это подтверждённый кандидат на устранение повторной семантической проверки внутри одного вызова; проверку чужого JSON на входе сохранять обязательно.

`materialize` выполняет native compile во временном build root прежде проверки прежнего public.json, зарезервированных resource targets и всех destination paths. Часть destination/input конфликтов известна до compile и может отказывать раньше. Это цена отказа явного экспорта, не render каждой страницы. Старый output не разрешает reuse; source/protected files и symlinks должны сохраняться.

Операции: transport validators — public/closed AST и resource contract; preparePrint/strip/para — форма раздаточного материала; verifyResources/resourceTargets — integrity и точный набор включений; compilePrint — native Pandoc/Typst; materialize/files/write/safePath — изолированная сборка, планирование выдачи и сохранность; diagnostics/process — исходный exit/cause. Dev/tests подтверждают эти границы на legacy входах, но не новый Core8.3. Все функции и callbacks учтены отдельным ledger; ненаблюдавшаяся функция автоматически не удаляется.

H36 подтверждена для неизменённого Body на этом callpath; H37 подтверждена как поздний дешёвый conflict gate. Измеренные секунды и актуальная installed Bodyv2 compatibility неизвестны. Будущий малый опыт: tiny public package и заранее конфликтующий resource target с контролем числа запущенных compilers. PDF/render больших курсов не требуется.
