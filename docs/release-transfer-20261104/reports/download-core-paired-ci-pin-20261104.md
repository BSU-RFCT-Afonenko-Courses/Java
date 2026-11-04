# Download: exact Core pin для существующего CI

Результат: **изолированная CI composition-дельта готова к root review**.
В `.github/workflows/check.yml` изменены ровно две строки: Core checkout ref
с коротким Native-комментарием и согласованный exact assertion.
No tests/compiler/Native/CI/network/push/commit/mainstate.

## Вход и основание

Download HEAD `944ca76dbdef5bb4842116950621f82449b631b3`.
На входе уже существовал independently reviewed frozen candidate:
`native-ts/early-filter.ts`, `native-ts/late-filter.ts`, `tests/literal-request.ts`.
Эти три pending файла не менялись в данной задаче.

Root сообщил full hosted Core CI SUCCESS для exact
`4a2903e6d03d1dd489b6922d113100826913cf67` (run41790570793).
Повторного CI query/сетевого опроса не было. Это основание выбора reviewed Core
SHA, а не доказательство полного paired Download runtime PASS.

Старый pin `68074174b3d7546302bc98e5c80b39e5f56b7713` заменён на
`4a2903e6d03d1dd489b6922d113100826913cf67` в workflow lines33/39.
Существующие `core-fixture` path, exact checkout action и source-head assertion
сохранены. Комментарий сообщает проверенную Native capture/actual A1 границу и
Core hosted PASS; новой consumer authority он не задаёт.

[Точный workflow-only diff](download-core-paired-ci-pin-20261104.patch).
`git diff --numstat -- .github/workflows/check.yml`: **2 insertions/2 deletions**.
Diff прочитан целиком. Других изменений в этой задаче нет.

## Source-проверка public consumers до edits

- Download `application/package-artifact.ts` потребляет public
  `ArtifactInventorySchema.parse`, затем пакует supplied bytes. Этот export
  присутствует в Core4a; ZIP/SHA semantics не меняются pin-дельтой.
- `infrastructure/runtime.ts` использует public `withCurrentNativeOperation`,
  `NativeReleaseProofSchema`, `planProject`, `materializationClosure`,
  `materializeCondition/materializeProject`, а также
  `operation.withProjectContracts`. Эти existing public APIs присутствуют
  в actual Core4a; actual A1 batch договор сохраняется.
- QRC sibling используется через `createBodyLinks` и `bindConditionLinks`.
  Exact старый QRC pin798f8c0d Source `public.ts` экспортирует обе функции.
- Optional PL sibling используется через `inspectPublication`, который возвращает
  реальный proof от `operation.validateRelease`. Exact pinned PLdf8bd4aa Source
  экспортирует этот consumer и не требует legacy qualifier profile/sourceProbe.
- `tests/ordinary-publication.ts` импортирует existing
  `runPublicationCases` из exact PLdf8 source. Этот export существует; он использует
  ordinary public Core operation/PL inspection. Сопутствующий PL/QRC pin не
  требуется из-за отсутствующего public symbol, выявленного в этой границе.
- Новый synthetic pure `literal-request` control ожидает actual трёхаргументный
  `qualifyAssignmentProducer(ast, context, observation)`. Core4a даёт именно эту
  сигнатуру; переход old680→4a устраняет известную fixture/API composition разницу.

Нового обязательного sibling export или версии в этих source callers не найдено.
Это ограниченная source-сверка, **не** доказательство совместимости всей матрицы:
existing `tools/check.sh` содержит реальные Native suites и старые test contracts,
которые здесь не исполнялись. Конкретного source blocker для pin-only не найдено;
новый paired CI после push остаётся реальным gate.

## Что явно осталось открытым

- PL остаётся `df8bd4aad274d618a782265b1db9c63e9b9a40f9`, QRC остаётся
  `798f8c0d9f87fd5dcfe9ee85b79df57e647d2f3c`; их refs/asserts/jobs не менялись.
- Никакая новая computed fixture/engine/install/job/matrix не добавлена.
- `spec/index.md` хранит прежнее описательное Core608 reference; документация не
  переписывалась в узкой двухстрочной CI-дельте. Фактический exact CI pin задаёт
  workflow. Это конкретная оставшаяся documentary discrepancy.
- Старые unrelated test any/strategy assertions не исследовались как новая
  полная очередь и не редактировались ради потенциального зелёного CI.
- OAuth ранее отклонил workflow push из-за отсутствующего workflow scope
  (свидетельство root). Push **не выполнялся**, разрешения не обходились,
  авторизацию/token/config не менял. Пользовательский вопрос остаётся у root.

## Полный собственный допуск

Agent `/root/core_yaml_types`, chat `01a1a54c-ab39-7a83-808d-a190428ff628`,
исходный turn `01a1a54c-ab6a-7412-894c-270be9b9aac3` не сдвигался.
Назначение `gpt-6.1-sol/medium`; весь primary interval проверен:
**13 turn_context**, top-level model/effort и nested settings соответствуют — PASS.
Standard назначен; hidden routing не утверждается, globals не менялись.
[Первичное свидетельство](download-core-paired-ci-pin-admission-20261104.json).
