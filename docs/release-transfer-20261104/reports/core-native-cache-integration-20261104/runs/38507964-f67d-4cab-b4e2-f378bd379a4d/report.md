# Отчёт Performance

Запуск: `38507964-f67d-4cab-b4e2-f378bd379a4d`. Режим: `summary`. Код Quarto: 0. Сигнал: —.

Wall: 7.921 с. CPU user: недоступно; CPU system: недоступно; peak RSS, KiB: недоступно.

TypeScript измеряет wall, Lua — CPU процесса. Шкалы раздельны; inclusive интервалы могут перекрываться.

Качество данных: VALID.

## Наблюдаемые интервалы команд

Wall от запуска launcher до получения статуса; это inclusive интервалы, их нельзя складывать. Это не self time, CPU, критический путь или полный список процессов ОС. Покрытие частичное; отсутствие времени означает неизвестное значение, а не ноль.

| Категория | Wall, с | Exit | Сигнал | Процесс | Подтверждённый родитель |
| --- | ---: | --- | --- | --- | --- |
| Quarto run | 7.714 | 0 | — | 40e4c6c7-7c7c-4674-b076-6d648cf201a2 | не подтверждён |
| Deno | 7.215 | 0 | — | 8edb95b6-2856-4244-86e4-cb41164b8d70 | 40e4c6c7-7c7c-4674-b076-6d648cf201a2 |
| Quarto render | 3.754 | 0 | — | e0ec6975-f020-4ba6-b4bb-aebf153eceb0 | 8edb95b6-2856-4244-86e4-cb41164b8d70 |
| Pandoc | 0.833 | 0 | — | 89627cd9-45af-48fa-a8f0-46f790551e80 | e0ec6975-f020-4ba6-b4bb-aebf153eceb0 |
| Quarto inspect | 0.672 | 0 | — | 9251f717-aaea-4045-8525-6ec7f9a4ca39 | 8edb95b6-2856-4244-86e4-cb41164b8d70 |
| Quarto inspect | 0.662 | 0 | — | 23810958-6277-468c-838e-accf9c71aca9 | 8edb95b6-2856-4244-86e4-cb41164b8d70 |
| Quarto inspect | 0.651 | 0 | — | c7ccc1d6-9ffd-4ec4-9306-ecda98cf8673 | 8edb95b6-2856-4244-86e4-cb41164b8d70 |
| Quarto run | 0.499 | 0 | — | 790236fd-7007-4f32-8d17-4b2752ae60a0 | e0ec6975-f020-4ba6-b4bb-aebf153eceb0 |
| Quarto run | 0.479 | 0 | — | adbdfb41-ae47-453d-96df-1681b622f4cd | 89627cd9-45af-48fa-a8f0-46f790551e80 |
| Quarto run | 0.458 | 0 | — | 61fb7f91-8eea-4acd-975f-48eed42f687c | e0ec6975-f020-4ba6-b4bb-aebf153eceb0 |
| Quarto pandoc | 0.295 | 0 | — | f06333bd-bab3-4a7b-9b05-cfdbef3648e7 | 8edb95b6-2856-4244-86e4-cb41164b8d70 |
| Quarto pandoc | 0.290 | 0 | — | 643b692a-8922-4b65-85f7-47a837eeeb82 | 8edb95b6-2856-4244-86e4-cb41164b8d70 |
| Deno | 0.159 | 0 | — | d27e02bb-32a2-4e2f-ad95-24c687771813 | 790236fd-7007-4f32-8d17-4b2752ae60a0 |
| Deno | 0.138 | 0 | — | 4799d93f-78ce-4872-b7bd-99239d30efd9 | adbdfb41-ae47-453d-96df-1681b622f4cd |
| Deno | 0.120 | 0 | — | 9d13e049-6edd-4d9e-ab42-0944d6a898f1 | 61fb7f91-8eea-4acd-975f-48eed42f687c |
| Pandoc | 0.008 | 0 | — | edd03d4d-a96a-444d-9e66-9e20ed5ff907 | f06333bd-bab3-4a7b-9b05-cfdbef3648e7 |
| Pandoc | 0.004 | 0 | — | 440482aa-24fd-4ffe-a0cc-e18fdf5f5572 | 643b692a-8922-4b65-85f7-47a837eeeb82 |

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
