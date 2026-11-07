# Core Native bridge: test-only согласование A1 fixtures

2026-11-04. **Source candidate готов к независимому review root; runtime/compile PASS не заявляется.** По прямому разрешению изменены только четыре existing test files в `/home/tolya/JavaCodex/java-release-20261110/core-native-bridge` поверх exact HEAD `0cf20ccc0a4853b704544c7252fcaf3ccb84a518`. Основание — [Source-only contract очередь](core-native-bridge-test-contracts-next-20261104.md). Production/API/docs/runner order не изменялись.

[Точный собственный diff](core-native-bridge-test-align-a1-20261104.patch): четыре files, **+39/−100**. Включён только этот scope; чужой pending `release-generated-resource-currentness.ts` и отдельный medium пакет030/answers/pending не редактировались.

| Existing fixture | Что изменено | Что сохраняется |
|---|---|---|
| canonical023 `tests/native-project-contracts.ts` | Удалены cold one-inspect/zero-copy и subset zero-inspect/cache-hit assumptions. Запрет любого synthetic `.qmd` write заменён существующей полезной границей: writes в **author root** запрещены, disposable QMD разрешён. Subset callback проверяет actual requested inputs/context selection вместо cache counters. Mutation-before-first-read ожидает `CONTEXT_CHANGED` | Checked contract view, metadata/origins/plan/inventory semantic parity, author-source protection, expired/forged capabilities, reentrant failed batch и real source mutation negative |
| canonical035 `tests/operation-project-reuse.ts` | Удалены old one-inspect/zero-walk/crossbatch-probe-cache/hash-volume equalities и temp-write monkeypatch старого prefix. Actual copy race ожидает `CONTEXT_CHANGED`; zero-Native-command expectation снято, поскольку fresh owner inspect предшествует copy. Sentinel callback всё ещё не должен достигаться; `triggered` подтверждает, что fixture действительно изменила файл | Real metadata/origin/inventory bytes/rawSource parity, standalone context generation refusal, fresh batch handles, private owner witness compatibility, unknown old metrics, no optional I3 authority reads, actual copy mutation refusal |
| canonical036 `tests/operation-project-reuse-boundaries.ts` | Removed temp/call-count zero-work assumptions. Physical symlink отказ согласован с actual `copyOwner` diagnostic `CONTEXT_ENTRY`, до callback. Callback source/membership mutations — `CONTEXT_CHANGED`; `parent-final` после завершения A1 — positive. Exact closing cost counter удалён, context expiry сохранён | Existing lawful input/native frontmatter, marker/alias collision, excluded-source origin, native format, private child-owner/provenance tampering negatives, no unsafe callback |
| canonical037 `tests/operation-project-reuse-mixed.ts` | Удалены one-probe/warm zero-copy/read/inspect/cache-hit counters. Проверяется два format groups с выбранными inputs; повторная batch возвращает свежие context handles с теми же выбранными formats/format metadata. Terminal metrics immutable, без exact fence-count guarantee. Hidden-project case сохраняет actual origin assertion | Distinct HTML/Reveal metadata/origins, hidden preprojection declaration coverage, no format borrowing, no virtual author QMD, immutable terminal snapshot |

Новые exact implementation counters/cache rules не введены. Имеющиеся IO/command observations, где они остались, не объявлены новым бюджетным контрактом. Логовые PASS-тексты больше не обещают прежние one-probe/zero-copy стратегии.

## Почему diagnostics и phases различаются

`project-context.ts:140–180` делает actual fresh owner copy. Symlink/special entry отказывает `CONTEXT_ENTRY`; изменение signature после чтения — `CONTEXT_CHANGED`. `:182–234,731–733` держит actual inventory до/после короткого A1 callback, поэтому callback source/membership mutation остаётся negative. Эти коды не расширены произвольным regex ради зелёного теста.

`operation.ts:2298–2334` на parent currentness проверяет raw selected QMD плюс реальные outputs/resources, не весь project source. Поэтому `parent-final` меняет non-resource project Main.txt **после возврата** A1 и не должен восстанавливать отменённый whole-owner freeze. Сам A1 context к этому времени истёк; следующий `Core.planProject(context, ...)` по-прежнему обязан дать `CONTEXT_EXPIRED`.

New subset/mixed assertions касаются public batch selection/metadata/lifetime, а не числа Native subprocesses или копируемых bytes. Никаких production guards, fake origins, fallback caches, new schemas/validators или новых casts не добавлено. Существующий test monkeypatch debt не объявляется полной TS типизацией; в новой subset callback использован экспортированный `Core.ProjectContractContext`.

## Проверки и пределы

Выполнено только Source чтение/сверка diff и **`git diff --check` → exit0**. Проверено отсутствие оставшихся обращений к удалённым monkeypatch variables и наличие public type export. Полный patch просмотрен по всем четырём files. Это whitespace/Source evidence, не compiler/runtime evidence.

Native/render/inspect/compiler/tests/CI/network/hash/build не запускались. Existing canonical001–021/A3 controls не повторялись. Source по предполагаемому контракту согласован, но новые diagnostics/positive phases и продолжение canonical023/035/036/037 ещё **не исполнены**; единственная следующая functional evidence — отдельное решение root о coalesced CI после независимого review обоих test-only пакетов.

Commit/push/state/pins/installed/student files/results не менялись; свой diff остаётся uncommitted. Точный чужой pending ресурсный diff не включён в приложенный patch.

Primary admission: свой chat `01a1a25f-b098-7242-bf6d-d0e8f6517361`, origin `01a1a25f-b0fb-7fd2-b244-83933af77834`; primary `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-05-42-01a1a25f-b098-7242-bf6d-d0e8f6517361.jsonl`. В текущем полном доступном интервале22 turn_context, lines8–3151, top-level/nested все `gpt-6.1-sol/high`; latest `01a1a559-1a99-7b02-81b7-e6fafa4ed10d`. Standard назначен, hidden routing не удостоверяется; globals/normative admission docs не менялись.
