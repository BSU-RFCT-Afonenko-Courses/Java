# PL: упаковка Workspace без выбрасываемого ZIP

## Допуск исполнителя

Назначение: `gpt-6.1-sol` / `medium`, Standard. Собственный chat ID
`01a1a1cc-b4a3-7e33-8c74-9897da146a51`, путь агента
`/root/release_workspace_zip`, исходный turn ID
`01a1a1cc-b4d4-74e3-8c9e-70fb49287872` (root turn
`01a1a1c0-328b-7b42-8658-38cdfc9926ba`). Первичный источник:
`/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T17-25-09-01a1a1cc-b4a3-7e33-8c74-9897da146a51.jsonl`.
`session_meta` подтверждает собственные ID и путь; единственная запись
`turn_context` за весь интервал от исходного хода подтверждает
`model=gpt-6.1-sol`, `effort=medium`, те же model/reasoning_effort во
вложенном `collaboration_mode.settings`. Первичный и итоговый допуск: PASS.
Метаданные не содержат отдельного поля service_tier; Fast/Ultrafast не запрашивались.

## Причина и изменение

Repo: `/home/tolya/JavaCodex/java-release-20261110/prairielearn`, ветка
`codex/java-release-20261110`, исходный чистый HEAD
`1c41f03f83b26adfe58415b4d89958a3663fabf2`.
Адресный commit: `7a41b7c` («Убрать выбрасываемый ZIP при materialization Workspace»).
Прочитаны `AGENTS.md`, `spec/index.md`, Workspace-договор в
`docs/authoring.md`, реализация Download `packageArtifact` и `zip` в
установленной композиции курса, общее русское языковое правило.

`materializeNativeQuestion` создавал обычный ZIP и проекцию `final.files`
перед ранним возвратом Workspace/includeGit, хотя возвращались отдельные
`workspace.archive` и `workspace.files`. Обычные mapping и `packageArtifact`
перемещены после этого возврата. В upload/file ветке сохранены тот же
`final`, упаковщик и порядок mapping → package → checkpoint → return.
Условия ветвления, число materialize, selection checks, имя/байты итогового
архива и интерфейсы не изменены.

## Побочные гарантии упаковщика

Core `ArtifactInventorySchema` в существующем `workspaceArtifact` уже
проверяет target policy, аудиторию каждого файла, дубли и конфликты
файл/каталог. Там же проверяются operation/exercise/student identity и
SHA-256 bytes. `nativeWorkspaceArtifact` проверяет README, seed admission,
common-file binding, пути и generated inventory; тот же `Download.zip`
проверяет конечный ZIP32. Клонирование и сортировка внутри выбрасываемого
`packageArtifact` не меняли передаваемый workspace inventory или его архив.

Найдена реальная потеря: схема отдельно ограничивает `size` и длину bytes,
но не требует их равенства. Сравнение `file.bytes.byteLength !== file.size`
добавлено в уже существующий workspace hash guard с прежней диагностикой
`PL.WORKSPACE_SEED: inventory bytes changed`. Нового validator или второго
ZIP нет; доверенная seed/package граница сохранена.

## Проверки и границы

- `deno check --no-remote _extensions/course-prairielearn/application/native-materialize.ts _extensions/course-prairielearn/application/native-adapter.ts` — exit 0, оба файла проверены.
- `git diff --check` — exit 0; итоговый diff: два TS-файла, 9 добавленных / 6 удалённых строк.
- После commit: `git status --short` пуст; `git show --format= --check HEAD` — exit 0.
- Повторная проверка всех первичных `turn_context` от исходного turn — PASS, один контекст, ожидаемая пара совпала на обоих уровнях.

Новые тесты не добавлялись согласно brief. Runtime materialization, полная
матрица, render/course/CI, Docker и inventories не запускались. Typecheck не
доказывает готовность релиза или Workspace host. Push/PR оставлены до
независимого обзора координатором; version pins, UUID, grading и scoring не
затронуты.
