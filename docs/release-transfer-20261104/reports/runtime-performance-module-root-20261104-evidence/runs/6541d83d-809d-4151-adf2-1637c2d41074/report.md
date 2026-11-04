# Отчёт Performance

Запуск: `6541d83d-809d-4151-adf2-1637c2d41074`. Режим: `summary`. Код Native: 0. Сигнал: —.

Completion: COMPLETE. Наблюдённый wall driver: 1.082 с.
Capture mode: separate; log channels: stdout=eof, stderr=eof.
Wall Native: 1.032 с. CPU user: недоступно; CPU system: недоступно; peak RSS, KiB: недоступно.

TypeScript измеряет wall, Lua — CPU процесса. Шкалы раздельны; inclusive интервалы могут перекрываться.

Качество данных: VALID. Resource scope: unavailable; wall scope: resource-helper-envelope.

## Наблюдаемые интервалы команд

Wall от запуска launcher до получения статуса; это inclusive интервалы, их нельзя складывать. Это не self time, CPU, критический путь или полный список процессов ОС. Покрытие частичное; отсутствие времени означает неизвестное значение, а не ноль.

| Категория | Wall, с | Exit | Сигнал | Процесс | Подтверждённый родитель |
| --- | ---: | --- | --- | --- | --- |
| Quarto render | 0.972 | 0 | — | aeeacdd6-ce1b-4c89-9cf4-138d0184c5fe | не подтверждён |
| Pandoc | 0.212 | 0 | — | ca415c0c-64b6-4881-a02c-aa35779031c6 | aeeacdd6-ce1b-4c89-9cf4-138d0184c5fe |

- [run](run.json)
- [summary](summary.json)
- [stdout](stdout.log)
- [stderr](stderr.log)
- [events](events)
- [processes](processes)
- [coverage](coverage.json)
- [export](export.json)
- [wall](wall.trace.json)
- [luaCpu](lua-cpu.trace.json)
- [causal](causal.json)

## Просмотр измерений

Perfetto: откройте локальный trace-файл через Open trace file. Общая wall-шкала содержит только калиброванные часы; локальная wall-шкала каждого некалиброванного исполнителя и Lua CPU читаются отдельно.

Логические дорожки не являются потоками ОС. Стрелки показывают записанное родительство по началам spans; задержка передачи управления не измеряется. Inclusive интервалы могут перекрываться. Счётчики остаются в summary.json. Пиковая память: run.json: completion.native.peakRssKiB.

- [общая wall-шкала](wall.trace.json)
- [Lua CPU](lua-cpu.trace.json)
