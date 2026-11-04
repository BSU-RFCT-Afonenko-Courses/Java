# Единственный дополнительный summary опыт

Основание: прямое поручение4ноября использовать существующий публичный instrumented-mode, не повторяя direct/off pair. Product Performance clean9ba1fd8. Core010ba917 и PL1ad5516e — только контекст основного чата, их Source/курсы не меняются.

Source trace: README/diagnostics и src/runner/diagnostic.ts:prepareRenderDiagnostic подтверждают публичный render --perf-mode summary. Используются уже установленные launchers; private Quarto adapter quarto.filter.run недоступен в render, manual annotations нужны для phase spans. На standalone QMD без extensions возможно пустое покрытие, что сохраняется как предел, не успех всех фаз.

Один вызов summary с точным прежним QMD, --no-execute --quiet. Не повторять Native direct/off. Прежний Python measure/wait4 harness переиспользован без нового framework; только один summary invocation, вывод в отдельном каталоге. Цель — observed phases/agents/data quality и стоимость observer. HTML сравнить с уже существующим Native HTML, без повторного рендера.

До запуска cap15s на весь вызов с подготовкой/overhead/cleanup: child watchdog12s + максимум2s cleanup +1s harness allowance. Дополнительно внешний timeout15s страхует весь Python вызов. Timeout — terminal outcome без retry; watchdog не является TERM/recovery/signal qualification. Старые2.946977501s/cap24 и3.216179068s/cap22 остаются отдельно; budgets не сброшены. Network/downloads, VM/Docker, course builds/inspect/hash, новые instrumented author modules и продуктовые изменения не выполняются. Runtime capability probes/public preparation принадлежат существующему API и включены в wall измерения.
