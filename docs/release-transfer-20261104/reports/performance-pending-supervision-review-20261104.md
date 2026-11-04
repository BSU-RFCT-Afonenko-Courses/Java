# Performance supervision: независимое review

4 ноября 2026. Reviewer root, Sol6.1/high. **APPROVE** ограниченной Source-дельты поверх `744dfac5e872dec71700971e00142689c7ef23fd`: один production участок `src/runner/process.ts` и три существующих scanner fixture. Это не приёмка всего Performance и не доказательство ускорения курса.

Прочитаны полный `runOwnedProcess`, фактический diff, изменённые fixtures и потребляющие их assertions, [отчёт исполнителя](performance-pending-supervision-20261104.md). У normal pending ветки удалён результат, который до native status не мог ни завершить цикл, ни инициировать остановку без отдельного signal/failure. Ownership registration и чтение PTY signal notice происходят перед новой веткой; callbacks сохраняют failure/pending. После status либо failure/signal используется прежний scanner, grace/escalation и ожидание потомков. `await completed` и drain/EOF не обходятся. Новый переход не усиливает прежнюю числовую PGID-модель и не заявляет защиты от PID reuse.

Изменения стендов обоснованы поведением: normal stale snapshot формируется до fork самим стендом, а не удалённым polling; ESRCH/permission/IO и persistent failure возникают на остающемся post-status descendant cleanup. Проверки фактического отсутствующего в snapshot живого потомка, `doneAtReturn`, native code7, контролируемого error-path, остановки heartbeat и сохранности unrelated sibling не заменены утверждениями о числе вызовов новой ветки.

Исполнитель предъявил 13 scoped runtime cases и affected compiler roots: суммарно12.220s, cap60/retry0, system Deno2.9.7. Второй compiler invocation относится только к двум fixture, обнаруженным и изменённым после первого check; прежние зелёные случаи не повторялись. Reviewer не запускал их повторно: новых изменений и отдельного неразрешённого вопроса нет. Source-only review не выдаётся за Quarto bundled2.7/fullCI или benchmark.

Normal10ms wakeup и signal-receipt file read остаются; устранена normal `/proc` enumeration. Standalone `executeScenario` SIGTERM другой реализации остаётся UNKNOWN. Следующий осмысленный замер общей стоимости нужен при содержательной интеграции, а не для повторной квалификации этого `if`.

Собственный первичный допуск reviewer: root chat `01a1a142-7adc-7371-9765-4871d807dfcd`, исходный текущий turn `01a1a4b3-8fd3-7be3-baf7-ce0cc5c60016`; contexts7160/7983/8022/9120 во всём интервале top/nested `gpt-6.1-sol/high` PASS. Исторические Ultra назначения не переписаны; глобальные настройки не менялись.
