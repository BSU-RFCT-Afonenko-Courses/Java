# PL: согласовать existing paired Core pin с5f

Готов workflow-only candidate для root review, без commit/push/CI. База PL `e4ffd86e9b92e8e21f07aa3c85d0d8bf572e9884`, до edits clean. Единственный diff — `.github/workflows/ci.yml`,4insertions/4deletions, ровно четыре SHA literals.

| Existing job | Checkout ref | Exact assertion |
| --- | --- | --- |
| check |4a2903e6…→`5f4590aa4eb524830715f8020c8ff3c504197f73`|та же замена|
| java-delivery |4a2903e6…→`5f4590aa4eb524830715f8020c8ff3c504197f73`|та же замена|

[Exact patch](pl-core5f-paired-ci-pin-20261104.patch). Checkout paths/actions, actual Core import-map helper, source-head assertion, suite/matrix/commands/gates/env/runtime registry/Quarto1.11.5 не менялись. Новых проверок или jobs нет. Existing comment честно оставляет hosted CI/release отдельными этапами.

По прямому handoff root Core5f включает независимо принятые condition types a06, late-cleanup556 и Navigation wrappers5f; SSH push/PR70 выполняет root отдельно. Эта задача не query-ила remote/CI и не объявляет Core5f hosted SUCCESS. Решение об одном PL push после Core hosted результата остаётся root. Existing test-only publication alignment уже отдельно принят/committed в PLe4; этот diff его не переделывает и не возвращает obsolete whole-owner guarantee.

Source diff прочитан; `git diff --check` PASS. Compiler/runtime/Native/CI/hash/network/install не запускались для простой SHA замены. Download workflow/Source/pins не трогались: его companion ждёт exact final PL commit и отдельной очереди ownership reader. Product Source, mainstate, installed courses/student files/results не менялись. No commit/push.

Own full-origin primary admission: 37 turn_context, строки8–4571, все top/nested gpt-6.1-sol/high; origin 01a1a25f-b0fb-7fd2-b244-83933af77834, latest 01a1a5c3-9732-7ea1-a1c1-35fb9bd1c7cf. Globals не менялись; hidden serving tier не удостоверяется.
