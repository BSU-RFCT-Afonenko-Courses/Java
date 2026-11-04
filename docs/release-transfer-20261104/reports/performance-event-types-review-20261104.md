# Независимое review границы Event

4 ноября 2026, root `gpt-6.1-sol/high`: **APPROVE**. База `10810e482dcecde1eecf2e697cac0e3d628f05fe`; только `src/protocol/validate.ts` и `tests/protocol/events.test.ts`. Чужой QRC companion в том же worktree этой приёмкой не охвачен.

Прочитаны весь `Event` DTO, прежний/новый входной разбор и regression cases. Assertion теперь проверяет required scalars/enums, все optional ID, timestamp/status, nested counter и primitive attributes, прежде чем вернуть исходный объект как `Event`. Удалён `as Event`, который пропускал числовые span/operation IDs и неверные optional поля. Это разбор внешней JSONL-границы, без повторных внутренних проверок. Extension keys и object identity сохраняются; пустые optional ID допустимы там, где прежняя семантика их допускала. Требования к непустым span/operation для begin/end и document ID не расширены.

Проверки исполнителя: 5 protocol cases PASS; один corrected affected offline compiler program PASS0.597s, system Deno2.9.7. Первоначальный compiler preparation отказал из-за лишнего `--node-modules-dir=manual` и отсутствующего local `@types/node`; причина установлена по успешной конфигурации соседней партии, исправлена без сети/установок. Суммарно0.866s, прежние зелёные runtime cases не повторялись. [Отчёт исполнителя](performance-event-types-20261104.md). Полные suite/Quarto execution этим не приняты.

Новых проверок reviewer не назначает: наблюдаемый malformed-wire дефект покрыт адресными cases, новый Source не менялся. Это закрывает конкретный Event cast долг, а не всё типовое покрытие Performance. Reviewer admission: исходный root interval от `01a1a4b3-8fd3-7be3-baf7-ce0cc5c60016`, contexts7160/7983/8022/9120/9348 top/nested Sol6.1/high PASS; globals unchanged.
