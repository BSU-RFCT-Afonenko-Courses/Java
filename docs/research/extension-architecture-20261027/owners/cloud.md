# Cloud: декларации и граница поддерживаемого поведения

Выбран Cloud3.0.1. Его current spec прямо сохраняет legacy Core4 fixtures и исключает исполнение VM/actions и экспорт produced.yaml. Это адаптер деклараций, а не PrairieLearn Workspaces и не сервер облачного VS Code.

`filter.lua` загружает native module, затем возвращает документ без course. Для course проверяет порядок Core filter; при отсутствии cloud в adapters возвращает документ без native.read/write. Следовательно, неподключённый adapter не выполняет VM validation либо собственную materialization proof на каждом AST node. Сам module load и проверка metadata остаются. H40 не подтверждает широкие неактивные AST passes в этом callpath.

Активный `native.lua` ожидает pointer-v1. Выбранный Core8.3 производит v2/v3; эта новая пара не имеет раннего публичного compatibility handshake. H39 подтверждена как объявленная legacy boundary и риск позднего отказа при неподходящем наборе, но не как новый регрессировавший сценарий существующего Java. Нельзя просто ослабить pointer predicate или назвать Cloud готовым для Workspaces.

Операции: contract/validate/steps/actions — структура VM/action/step и зависимости; native read/write — сбор adapter fragment на поддерживаемой линии; validate-paths.ts — path containment и внешний command boundary с optional ABI1 context. CUE и Lua predicates имеют отдельные роли и требуют соответствия. Рабочая VM, сеть, provisioning и credentials не исполняются и не являются скрытым PASS.

Предложение: ранний capability check при подключении adapter, явный supported Core/transport range и отдельная workspace-конфигурация у владельцев PrairieLearn/Platform. Измерение неактивной ветви возможно на малой fixture, но отсутствие события в trace само по себе не доказывает нулевую стоимость.
