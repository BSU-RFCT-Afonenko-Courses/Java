# Отчёт Performance

Запуск: `a4ac5976-790a-4160-bc40-45794995dea7`. Режим: `summary`. Код Quarto: 0. Сигнал: —.

Wall: 39.798 с. CPU user: недоступно; CPU system: недоступно; peak RSS, KiB: недоступно.

TypeScript измеряет wall, Lua — CPU процесса. Шкалы раздельны; inclusive интервалы могут перекрываться.

Качество данных: VALID.

- [run](run.json)
- [summary](summary.json)
- [stdout](stdout.log)
- [stderr](stderr.log)
- [events](events)
- [processes](processes)
- [coverage](coverage.json)
- [manifest](manifest.json)
- [export](export.json)
- [wall](wall.trace.json)
- [luaCpu](lua-cpu.trace.json)
- [causal](causal.json)

## Просмотр измерений

Perfetto: откройте локальный trace-файл через Open trace file. Общая wall-шкала содержит только калиброванные часы; локальная wall-шкала каждого некалиброванного исполнителя и Lua CPU читаются отдельно.

Логические дорожки не являются потоками ОС. Стрелки показывают записанное родительство по началам spans; задержка передачи управления не измеряется. Inclusive интервалы могут перекрываться. Счётчики остаются в summary.json. Пиковая память: run.json: outcome.peakRssKiB.

- [общая wall-шкала](wall.trace.json)
- [Lua CPU](lua-cpu.trace.json)
