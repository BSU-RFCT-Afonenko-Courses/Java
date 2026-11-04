# Отчёт Performance

Запуск: `ed5726d6-e4a8-47f7-9a13-bce6d9489354`. Режим: `summary`. Код Quarto: 0. Сигнал: —.

Wall: 7.222 с. CPU user: недоступно; CPU system: недоступно; peak RSS, KiB: недоступно.

TypeScript измеряет wall, Lua — CPU процесса. Шкалы раздельны; inclusive интервалы могут перекрываться.

Качество данных: VALID.

## Наблюдаемые интервалы команд

Wall от запуска launcher до получения статуса; это inclusive интервалы, их нельзя складывать. Это не self time, CPU, критический путь или полный список процессов ОС. Покрытие частичное; отсутствие времени означает неизвестное значение, а не ноль.

| Категория | Wall, с | Exit | Сигнал | Процесс | Подтверждённый родитель |
| --- | ---: | --- | --- | --- | --- |
| Quarto run | 7.017 | 0 | — | 349a6b9c-0746-4068-ab79-cc4e2410c689 | не подтверждён |
| Deno | 6.526 | 0 | — | 2a462a45-460a-46f9-b52e-dadd522d88d5 | 349a6b9c-0746-4068-ab79-cc4e2410c689 |
| Quarto render | 3.197 | 0 | — | 0eec717b-9f41-4830-b170-1017e0ec1ea1 | 2a462a45-460a-46f9-b52e-dadd522d88d5 |
| Pandoc | 0.771 | 0 | — | 228871e4-252c-429e-9221-4565277c44a8 | 0eec717b-9f41-4830-b170-1017e0ec1ea1 |
| Quarto inspect | 0.675 | 0 | — | 09f3169c-1e74-47ab-a9f8-ba5dd77e85f9 | 2a462a45-460a-46f9-b52e-dadd522d88d5 |
| Quarto inspect | 0.656 | 0 | — | 54f5ed42-da64-4f49-a2c1-e21c0e28a50a | 2a462a45-460a-46f9-b52e-dadd522d88d5 |
| Quarto inspect | 0.656 | 0 | — | 3f30f0cf-6484-464b-9399-294c6574685d | 2a462a45-460a-46f9-b52e-dadd522d88d5 |
| Quarto run | 0.495 | 0 | — | e974ce2b-e736-4046-9d80-2e04d6ad286f | 0eec717b-9f41-4830-b170-1017e0ec1ea1 |
| Quarto run | 0.468 | 0 | — | d716be99-f9f3-4d9e-9fab-d57e3c74c018 | 228871e4-252c-429e-9221-4565277c44a8 |
| Quarto run | 0.450 | 0 | — | 06485112-1eb2-455f-a8c1-9adca3ce9c08 | 0eec717b-9f41-4830-b170-1017e0ec1ea1 |
| Quarto pandoc | 0.297 | 0 | — | a9ac66b5-6c5c-4ae3-a554-9f4da6656848 | 2a462a45-460a-46f9-b52e-dadd522d88d5 |
| Quarto pandoc | 0.295 | 0 | — | b493e57b-b3a4-475d-a2e3-4773344ba5fc | 2a462a45-460a-46f9-b52e-dadd522d88d5 |
| Deno | 0.158 | 0 | — | 2d56abd8-cab7-4cdd-9e13-c34b843eff88 | e974ce2b-e736-4046-9d80-2e04d6ad286f |
| Deno | 0.135 | 0 | — | d789f690-117d-4f2e-ad30-bb7b85e5ddfa | d716be99-f9f3-4d9e-9fab-d57e3c74c018 |
| Deno | 0.119 | 0 | — | 42c7576c-d457-486a-aeb6-729dcfae9844 | 06485112-1eb2-455f-a8c1-9adca3ce9c08 |
| Pandoc | 0.008 | 0 | — | b9937dc8-b7ca-49be-b0b9-5ac20fcac574 | b493e57b-b3a4-475d-a2e3-4773344ba5fc |
| Pandoc | 0.004 | 0 | — | 77bb9073-6ceb-4a50-bfc3-4a17c7cbbeb7 | a9ac66b5-6c5c-4ae3-a554-9f4da6656848 |

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
