# Core Native bridge: согласование policy/early tests

Результат: **Source-only test-дельта готова к независимому root review**.
Runtime/compiler/Native/render/hash/suites не запускались; PASS исполнения
новой редакции не заявляется. После обеих test-партий решение об одном
coalesced CI остаётся у root.

## Вход и единственный scope автора

Core checkout: `/home/tolya/JavaCodex/java-release-20261110/core-native-bridge`.
Исходный HEAD: `0cf20ccc0a4853b704544c7252fcaf3ccb84a518`.
При входе был чужой pending diff только
`tests/release-generated-resource-currentness.ts`; он не изменялся.
Во время работы другой назначенный high исполнитель редактирует четыре
отдельных tests. Его файлы не редактировались и не включены в мой patch.

Изменены ровно:

- `tests/static-source-policy.ts`;
- `tests/answers-native-a4b.ts`;
- `tests/pending-a4a.ts`.

Production, runner, mainstate, глобальные настройки и остальные checkout
не менялись; commit/push не выполнялись. Регистрация `static-source-policy`
в canonical runner сохранена: файл сокращён до полезных действующих controls,
поэтому удалять или условно пропускать регистрацию не требуется.

Прочитаны действующие `AGENTS.md`, `spec/index.md`, связанная Native/A1
документация и фактические production sites. Применён навык receiving-code-review:
замечания проверены по Source до edits. Адресный вход high reviewer:
[core-native-bridge-test-contracts-next-20261104.md](core-native-bridge-test-contracts-next-20261104.md).
Его source anchors сверены с настоящим checkout, а не приняты как тестовый PASS.

## Основание контракта

- `runtime/operation.ts:940–944`: enum `general/static` ещё существует;
  unknown и null policy остаются неподдержанными данными.
- `runtime/operation.ts:2298–2334`: current operation проверяет выбранные QMD,
  actual resources/outputs, private/runtime/pointer/lifetime; supporting config
  и произвольные project bytes не получают blanket whole-owner freeze.
- `runtime/operation.ts:2873–2891`: adapter-returned source имеет initial
  admission, physical containment и фактический тип; удалённое old byte-signature
  сравнение обычного source не возвращается ради test.
- `infrastructure/project-context.ts:140–180,182–234,731–733`: fresh actual A1
  acquisition, затем собственные captured bytes/identity и короткие callback fences.
  Изменение файла **до** acquisition должно попасть в свежий snapshot; изменение
  внутри живой batch по-прежнему может отказать. Cross-batch zero-copy/probe-cache
  стратегия не является договором.
- `docs/native-context-ts.md` и актуальные заключительные разделы
  `docs/native-operation-ts.md` разделяют captured bank AST, текущую A1 acquisition
  и selected-QMD/resource provenance. Native engines выполняет Quarto.

## Точная дельта

### `static-source-policy.ts`

Файл сокращён с 315 до 122 строк; fixed control list содержит шесть случаев.
Удалены executable/OJS/missing-codeCells blanket engine refusal, monkeypatch
`Deno.Command`, все FS/copy/inspect/fence/cache-counter assertions, warm-batch
same-S/zero-work и blanket отказ из-за project bytes/new unselected QMD.
Удалён старый symlink case, который вообще не потреблял изменённый subtree и
ошибочно ожидал parent whole-owner discovery. Не добавлены новые точные counters
или новый engine gate. Старый `ONLY_CASE` selector не используется для скрытого
пропуска: исполняется полный явно заданный оставшийся список.

Сохранены и адресно согласованы полезные controls:

- unknown и null source-policy → прежний `OPERATION_SOURCE_POLICY`;
- getter/A1: фактическое наличие ожидаемого project file, его bytes, metadata/origins
  parity внутри живой batch и изоляция caller byte-copy;
- `actual-read`: project file изменяется после bank capture, но **до** actual A1;
  callback должен получить новые bytes. Parent operation завершается успешно;
- retained A1 context отказывает `CONTEXT_EXPIRED` сразу после собственной batch,
  даже пока parent operation ещё жива; parent handle после callback отказывает
  `OPERATION_EXPIRED`;
- observed `visible.txt` bytes остаются содержательным positive;
  изменение наблюдённого ресурса перед выдачей и current output mutation остаются
  negative `OPERATION_CHANGED`.

Непокрытого полезного поведения в новый framework не переносилось. Physical
symlink/alias/marker/format и короткие A1 mutation guards остаются в существующих
`operation-project-reuse-boundaries.ts`, `carrier-physical-guard.ts`,
`native-context-a1-negative.ts` и других адресных controls; связанный high пакет
сохраняет actual-copy `CONTEXT_ENTRY` и callback `CONTEXT_CHANGED`.
Selected raw QMD supporting-vs-selected правила уже различает
`assignment-closing-source-fence.ts`, а pointer/selected-QMD aliases сохраняются
в `pending-a4a.ts`. Это source coverage mapping, не утверждение их нового PASS.

### `answers-native-a4b.ts`

Удалён guarantee-only десятикратный getter loop с равенством metrics/no-IO.
Существующие answer semantics, immutable captured native config,
readSet/namespace isolation, student/full privacy, HTML отсутствие закрытого key,
expired handle и restricted invalid answer до projection не менялись.

Supporting `settings.yml` дописывается после capture/validateRelease. Вместо
старого parent drift refusal проверяются `assertCurrent()` и повторный
`validateRelease` через те же adapter validators. Они снова проверяют captured
`configuration` inheritance и answer facts; proof обязан включать `adapter-a`.
Settings восстанавливаются в `finally` после завершения callback, а не до
проверяемого parent return. Таким образом test не маскирует supporting mutation
временным восстановлением внутри fence.

### `pending-a4a.ts`

Ordinary `source.txt` изменяется внутри adapter callback и возвращается как
существующий physical file. Теперь проверяется успешный proof с `fixture`,
вместо старых nested/parent byte-signature refusals.

Оба guarantee-only IO/digest probes (`pending-getter-complete-IO-zero`,
`pending-whole-call-IO`) удалены **вместе** с их monkeypatch scaffolding;
жёсткие `readTextFile=4/readFile=2/digest=2` не заменены новыми числами.
Identity/inputs всё ещё сериализуются для actual raw-capture witness; binding
profile/projection/format/owner и expired pending getter сохраняются.

Сохранены outside/cache/missing/wrong-type adapter source refusals,
selected-QMD symlink, old raw-witness/new-generation, pending pointer switch,
selected source change до entry и до final fence, а также отсутствующая generation.

## Статическое свидетельство и ограничения

Точный scoped patch:
[core-native-bridge-test-align-policy-20261104.patch](core-native-bridge-test-align-policy-20261104.patch).
`git diff --check -- tests/static-source-policy.ts tests/answers-native-a4b.ts tests/pending-a4a.ts`
завершился exit 0.
Адресный source-поиск не нашёл в этих трёх файлах удалённых engine/codeCells,
zero-IO/cache/fence probes и pending monkeypatch counters.

No Native/compiler/render/inspect/build/hash/suites/network/install.
Исполняемые `PASS` строки внутри fixtures — сообщения будущего запуска,
не результат этой source-only работы. Новая редакция требует независимого
root review и согласованного coalesced CI.

## Полный собственный модельный допуск

Собственный агент `/root/core_yaml_types`, chat
`01a1a54c-ab39-7a83-808d-a190428ff628`, исходный turn
`01a1a54c-ab6a-7412-894c-270be9b9aac3`; начало интервала не сдвигалось при смене
пакетов YAML/QRC/Core tests. Назначение `gpt-6.1-sol` / `medium`, Standard назначен.

Первичный source:
`/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T09-43-47-01a1a54c-ab39-7a83-808d-a190428ff628.jsonl`.
Проверены собственные session_meta id/agent_path и весь интервал от исходного
turn: **5 turn_context**, все верхнеуровневые model/effort и вложенные
collaboration_mode model/reasoning_effort соответствуют назначению — PASS.
Свидетельство:
[core-native-bridge-test-align-policy-admission-20261104.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-bridge-test-align-policy-admission-20261104.json).
Скрытая серверная маршрутизация не утверждается; globals не менялись.
