# Performance: границы измерения и необходимость операций

Выбран локальный `актуальная локальная Performance`, а не более ранний `ранняя локальная Performance` и не состав исторической Cybersecurity matrix. Эта линия сохраняет mtime при копировании и объявляет facade ABI 1. Исходные состояния сохранены отдельно. Inventory всех функций и callsites — локальные исследовательские материалы; статическая операция не означает наблюдавшийся runtime вызов.

## Операции

| ID / модули | Требование и результат | Эффекты и границы стоимости |
| --- | --- | --- |
| PERF-CONFIG: cli, config/validate | Проверить scenario/ref/mode/cache policy до запуска | CLI ориентирован на полные наборы исходников. Для малого Java использован public executeScenario с явным manifest |
| PERF-SOURCE: sources/resolve, freeze, prepare | Связать исходники, runtimes и disposable copy | Git/network/hash относятся к подготовке, а не к tool wall страницы. Clean checkout не доказывает корректность результата |
| PERF-COPY: sources/snapshot, packages | Сохранить bytes, modes, mtime и владение пакетами | Обход F файлов и B bytes; special files и выход за границы запрещены. Сначала audit всех целей, затем замена только в собственной копии |
| PERF-CAPS: adapters/quarto/capabilities | Проверить реальные Quarto/Deno/Pandoc и доступные interfaces | Version/help процессы и runtime hashes учитываются отдельно. PATH и встроенный runtime могут различаться |
| PERF-DIAG-SETUP: runner/diagnostic, launchers/setup, prepare, filter-trace | Подготовить изолированные launchers/backend/data-dir | Только активное профилирование. Копии, transforms и проверка установки имеют собственную цену |
| PERF-RUN: runner/execute, environment, resource-child | Выполнить заданные argv/cwd/env и сохранить status/stdout/stderr | Tool wall, outer wall и resource helper — разные области. Нет shell evaluation; cleanup ограничен заявленными disposable paths |
| PERF-LAUNCH: launchers/tool | Передать аргументы и сигналы native инструменту | Active добавляет preload, CPU profile и supervisor. Исходный exit/signal сохраняется; новая оболочка не считается частью Core |
| PERF-ABI: bridge.ts/lua | Отдать facade выбранного режима | Off возвращает исходную function и env. Это не доказывает нулевую цену всего внешнего harness |
| PERF-TS: agents/ts | Собирать события и async context | AsyncLocalStorage разделяет siblings; process/executor/serial идентифицируют spans. Wrap сохраняет this/result/error. Buffer limit и dropped events влияют на пригодность данных |
| PERF-LUA: agents/lua | Собирать CPU, coroutines и выбранные события Pandoc | CPU и wall различены; fallback clock помечен. Debug hooks включаются для trace. Ненаблюдавшееся не становится нулём |
| PERF-TRANSFORM: instrumentation/ts | Преобразовать Compiler AST с symbol map и skipped cases | Generators, accessors, decorators, eval и callbacks имеют ограничения; strict режим отказывает. Compiler API не загружается на каждом off render |
| PERF-QUALITY: protocol/validate, analysis/normalize | Проверить последовательность, parent graph, domains и receipts | Основной проход по E событиям; quality обязательна для интерпретации. Coverage ограничено выбранным scope |
| PERF-REPORT: analysis/report, report/perfetto | Сформировать таблицы и traces | Inclusive totals нельзя складывать в wall. Lua CPU не наносится на wall clock без отдельного основания. Сортировка и serialization выполняются после инструмента |
| PERF-PAIR: runner/benchmark, analysis/compare | Сопоставить чередующиеся парные наблюдения | Bootstrap не устраняет неверные входы или дрейф среды. Исторический порог 1.01 не является SLA этого исследования |
| PERF-EQUIV: analysis/outputs, owner-normalizer, validate-java | Проверить состав и bytes результатов | ZIP сравнивается по entries и распакованным bytes. Допустимы только объяснённые owner normalizations; произвольная очистка HTML запрещена |
| PERF-DEV: tools и tests | Проверять ABI, воспроизводимость и миграцию instrumentation | Repeat/scaling способны запускать полный курс; автоматически здесь не вызываются. Назначение test helper не доказывает необходимость каждого исторического теста |

## Что меняет профилирование

Active profiler меняет не только наблюдение. В выбранном Core `common-path-binding.ts:140` regular owner reuse разрешён при отсутствии profiling/off, а active включает дополнительный lexical inspect; статическая формула соответствующего пути меняется с `n+4` на `2n+4`. В `metadata-closure.ts:38` active также отключает cache. Эти счётчики нельзя переносить на off как описание производственного пути. См. [Core render](core-render.md).

Off, direct и naked native имеют разные вопросы. Direct и public off запускают один и тот же product workload; native floor не выполняет Core/QRC/Publisher и потому показывает нижнюю сравнительную область, а не эквивалентный заменитель. Подготовка sources, supervisor/drain и отчёт не должны исчезать из outer time, даже если они исключены из tool wall.

## Результат и ограничения

Пять новых пар selected-page direct/off используют одинаковые argv, cwd, env и cache policy. Итоговые числа и контроль outputs находятся в `../performance-findings.md` и raw локальные исследовательские материалы. Выигрыш архитектурного варианта по этим парам не устанавливается: продуктовый механизм не менялся.

H03/H04 требуют разложения процессов и независимого масштабирования входов. H50–H54 частично закрыты кодом инструмента и новыми парами, но off overhead ≤1% не доказан. H51 подтверждена статически именно различием маршрута; runtime доля этого изменения не измерена. Process IDs и async context уменьшают telemetry conflicts H52, но не обеспечивают безопасность параллельной записи native outputs. H53 требует раздельных domains, а не суммы inclusive CPU/RSS. H54/B16 запрещают причинный вывод по старой Cybersecurity matrix: там одновременно изменены несколько владельцев и машина.

Ни новый постоянный cache, ни ещё один telemetry runtime для начала упрощения не нужны. Приоритет — фазовый reuse уже принятых фактов с явным lifetime, затем один различающий опыт. Неизвестные dynamic targets и necessity тестовых функций остаются в ledger.
