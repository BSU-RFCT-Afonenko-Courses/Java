# Согласование release currentness fixture с Native bridge

Исходный head поручения: `0cf20ccc0a4853b704544c7252fcaf3ccb84a518`; checkout `core-native-bridge`. Изменён только `tests/release-generated-resource-currentness.ts`. Production, конфигурации и runner не менялись. Основание — [hosted CI 0cf](core-ci-0cf20cc-20261104.md): actual A3 прошёл, следующий первый отказ принадлежал старому требованию whole-owner membership.

Прочитаны весь тест, helper producer, `tools/check-core-operation-ts.sh`, адресные участки `operation.ts:validateRelease` и `source-snapshot.ts:ownedPhysical/assertSources`. Действующий bridge сохраняет initial provenance, guards выбранных raw QMD и physical project path; byte/role acquisition проекта принадлежит actual post-child A1. Изменение соседей и bytes непрочитанного project source не требует полного freeze initial owner.

## Что изменено

- Добавление root sibling и project sibling теперь требует успешного `validateRelease` с сохранённым `exr-demo`; старые отрицательные membership assertions удалены.
- Новый не наблюдавшийся `projects/demo/unregistered.svg` дополнительно запрашивается как public resource и должен получить существующий `RESOURCE_PRIVATE_OR_SOURCE`. Допустимое изменение membership не создаёт resource authority. Сосед после проверки удаляется, как прежде.
- Изменение `projects/demo/source.txt` перенесено в положительную последовательность той же существующей fixture: `validateRelease` сохраняет captured release. Никаких утверждений о byte freshness A1 inventory этот тест не делает.
- Прежний отдельный отрицательный whole-project drift случай заменён изменением выбранного `index.qmd`; ожидается `OPERATION_CHANGED`. Число Native fixture renders по исходной последовательности не увеличено.

## Что сохранено

Exact generated SVG и pre-existing resource bytes; отсутствие private sentinel в proof; private resource refusal; отсутствие authority у retained output с теми же bytes; expired operation; drift обоих наблюдённых ресурсов; подмена active pointer; отказ при symlink в **объявленном** project corridor.

Последний symlink случай сохранён по конкретной Source цепочке: release validator вызывает `ownedPhysical(root, local)` для объявленного `projects/demo`; тот проверяет компоненты пути через lstat и отказывает на symlink. Это physical ownership guard, а не прежнее требование неизменности membership/bytes всего проекта. Blanket отказ любых новых symlink где-либо в owner здесь не заявляется.

## Соседние runner cases и границы

Адресный поиск выполнен только в `tool-cache-paths-a3.ts`, `tool-cache-a3.ts`, `artifact-inventory-policy-a3-fix.ts` и уже согласованном A3. Дополнительных устаревших whole-owner assertions не найдено. `ordinary-cache-source-change-remains-meaningful` в tool-cache проверяет изменение используемого `.cache/course-data/lesson.txt` **внутри actual `readProjectContexts` callback** и ожидает `CONTEXT_CHANGED`: это сохранённый integrity fence короткой A1 acquisition, не initial-operation same-S гарантия. Остальные cache assertions сохраняют исключение tool cache и policy источника/target; inventory-policy — pure semantic target policy. Чужие файлы не менялись.

Итоговый diff просмотрен статически. Native/render/suites/compiler, новые хеши, CI dispatch/push и повтор зелёных проверок не запускались. Изменённая fixture ещё не исполнялась; далее независимый review и общий согласованный CI координатора. Mainstate, PR-комментарии, коммит и push не выполнялись.

Первичный допуск: собственный чат `01a1a518-a1da-7f91-a440-807c1b6889c2`, агент `/root/java_j25_doc_cleanup`, прежний исходный ход `01a1a518-a20b-7540-bb6a-470634b64d9c`. Все доступные turn_context от него, включая вложенные настройки, согласованы с `gpt-6.1-sol` / `low` до правки и перед отчётом. Свидетельство: `model-evidence/core-release-currentness-test-fix-20261104.json`; primary source: `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-46-57-01a1a518-a1da-7f91-a440-807c1b6889c2.jsonl`.
