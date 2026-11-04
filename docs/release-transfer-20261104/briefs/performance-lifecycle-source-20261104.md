# Performance lifecycle: Source реализация принятого design

## Authority и граница

User4ноября разрешил продолжение без ограничения15:00 и глубокую смену собственных contracts без backcompat; приоритет чистая архитектура и согласованные интерфейсы. [Design](../reports/performance-lifecycle-clean-contract-20261104.md) + [независимая final critique](../reports/performance-lifecycle-clean-contract-review-20261104.md) — обязательные конкретные requirements. Последний verdict APPROVE DESIGN ONLY закрывает5P1 и initial/terminal ordering. Source implementation теперь назначена root; runtime qualification не обещается.

Checkout `/home/tolya/JavaCodex/java-release-20261110/performance-lifecycle`, ветка `codex/performance-lifecycle-20261110`, exact base `c2dcf002a7396aa3b507e49b99e0ecf00ed9f438`. Root создал отдельный worktree от принятого Source: frozen original `performance` с6dirty files сохранён, не переносить candidate wholesale. App-native create_worktree не имеет target repository для отдельного Performance repo; пригодного attached worktree нет, поэтому local git fallback. Никаких install/baseline suites: accepted unchanged baseline не проверять заново.

## Задание владельца

Single Source writer high `/root/perf_event_types`, Sol6.1/high, Standard назначен, собственный неизменный origin/admission; globals не менять. Не делегировать. Реализовать согласованный Source closure из15 production/tool files design: единый authoritative RunRecord, honest native provenance, single terminal choice/publication ordering, early cancellation/IO failure partial без status/EOF barrier; все реальные callers/render/process/storage/aggregate/history/benchmark читают тот же discriminant. Удалить mirroredRunResult без compatibility aliases. Старые run artifacts/captures сохранять; не переписывать прошлую историю и не переименовывать старое evidence в новый PASS.

Архитектурные требования — в design, не генерировать новый framework. Source timebox90min: сначала actual interface/type shape, затем producers/writer, затем все consumers. Если scope оказывается больше или невозможен без новой архитектурной развилки — описать exact gap, продолжить независимое и сообщить root; не оставлять несогласованные API как готовый пакет. Новых supervisor/watchdog/polling/proof/receipt/cache layers не добавлять. Имена API отражают фактическое действие.

## Стоимость и фиксация

Сигнальные/runner/runtime/Native/Quarto/host experiments **не разрешены**: старый TERM budget не сбрасывается. Не запускать frozen runner, tests/новую матрицу, render/inspect/hash/build/network/DB/production; не устанавливать dependencies. Допускается один affected compiler нового Source graph, если честный local toolchain уже есть: cap15s/retry0; сохранить полный diagnostic, не маскировать unresolvedimports/stubs/casts. Это новая изменённая Source, не повтор старого compiler ради статуса. Перед compiler все15 affected callers должны быть заморожены, root notification обязательна. Если нужна новая временная map — только actual existing dependencies, не copied fictive declarations. Meaningful existing controls можно адаптировать под новый результат, но сейчас не исполнять.

До независимой Source review не stage/commit/push. Root единственный writer mainstate/audit. Отчёт `reports/performance-lifecycle-source-20261104.md` + patch/check evidence, список всех changed files, unresolved risks/qualification explicit. Короткий ответ status/путь/ключевойissue. В процессе каждые10–15min сообщать root только конкретный результат/развилку, не повторять неизменныйstatus.
