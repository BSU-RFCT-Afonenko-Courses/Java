# Независимый review Core owner boundary

Вердикт: **CHANGES REQUESTED — два конкретных P2 findings**. Общий отказ от unrelated owner edits/additions на static маршруте принят по смыслу; вернуть blanket ancestor membership equality не предлагается.

Собственный первичный допуск: PASS. Агент `/root/core_owner_review`, чат `01a1a502-5273-7260-99ab-937cf055bf6a`, исходный ход `01a1a502-52a2-7aa0-9b60-c9b0739e19cd`. Ожидаемая и зарегистрированная пара: `gpt-6.1-sol` / `high`. Источник: `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-22-35-01a1a502-5273-7260-99ab-937cf055bf6a.jsonl`, `session_meta` строка 1 и весь интервал `turn_context` с исходного хода (на начало проверки: строка 8). Верхние поля и вложенные настройки `collaboration_mode` согласованы. Standard: отдельный скоростной режим в первичных записях не указан; скрытая серверная маршрутизация этим не подтверждается.


## Проверенный scope

Прочитаны Core `AGENTS.md`, `spec/index.md`, локальная русская языковая политика, оба согласованных owner-boundary отчёта, весь working diff трёх production файлов, двух existing controls и узкой документации. Base HEAD при review: `46b35909c9878196294f54b6be2d7505b336fe8d`; diff `+164/-85`. Source не редактировался, commit/push/state не выполнялись.

Проверены preparation до callback/после selection, начальный bind, bound child pre/post, pending, seal, current consumer/assertCurrent и release dependency subtrees. Extraction работает с actual `NativeOwnerInspection` и сохраняет `unknown` для внешних bags; public `sourceRows`, signatures/SHA и Artifact DTO не ослаблены. Existing relative/absolute config/include resolution перенесён без смыслового изменения; новый runtime import cycle не добавлен. Local guards продолжают проверять выбранные file signatures, canonical physical paths и типы известных selected каталогов/предков; разрешение sibling membership changes само по себе не разрешает новый source/alias как materialization authority. Snapshot inode roles и actual selected-byte readers сохранены. Existing audience/alias controls прочитаны в сохранённых logs.

Счётчики `fullSourceFences`/`localSourceFences` отражают выбранную source ветвь; общий `fullFences` продолжает обозначать внешнюю boundary, которая также проверяет private/runtime/output. В отчёте эти counts не названы full source scan или измеренным ускорением курса.

## P2: появление отсутствовавшего ancestor `_metadata` проходит selected source guard

Место: `_extensions/course-core/runtime/source-snapshot.ts:206–216` в сочетании с `operation.ts:990–995`; изменённые static callsites включают `operation.ts:1426,1956,2159,2297,2491` и `assignment-admission.ts:209,247`.

`nativeSourceInputs` добавляет `_metadata.yml/.yaml` лишь при наличии в baseline rows. После нового local return проверка больше не замечает появления одного из этих известных native metadata candidates у выбранного input. Это изменение selected metadata, даже если остальной новый sibling правильно считается unrelated. Native Quarto `directoryMetadataForInputFile` (`/opt/quarto/bin/quarto.js:76984–77022`) при следующем child читает эти имена на пути input к project root. Фазы production до исполнения child — prepared lease guard `operation.ts:898` и bound child pre `:1329` — используют именно эту local source ветвь, поэтому новый metadata contributor не вызывает отказа до native command.

Root разрешил один tiny data-only counterexample. Actual `nativeSourceInputs` и `assertSources` импортированы из проверяемого checkout. Два baseline regular файла: `_quarto.yml` и `selected/index.qmd`; baseline memberships содержат только их и каталог `selected`; SHA не вычислялся и не потреблялся. После extraction и первого local fence создан `selected/_metadata.yml` с `title: changed after preparation`, затем вызван тот же `assertSources(state, [], false)`.

Ожидание: отказ, потому что появился native metadata contributor выбранного QMD. Фактически: **ACCEPTED**, `relevantInputs=["_quarto.yml","selected/index.qmd"]`; `localSourceFences=2`, `fullWalks=sourceDirectoryReads=sourceHashReads=sourceHashBytes=fullSourceFences=0`.

Результат единственного исполненного опыта: exit0, 0,352s, cap5s/retry0. Bundled Deno, `run --deny-import --no-config --no-lock --import-map=/tmp/pl-ci-core-imports.json -`, actual `DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache`, `DENO_NO_PACKAGE_JSON=1`; read/write разрешены только `/tmp/core-owner-review-01a1a502`. Fixture удалён в finally. Перед ним `deno eval --deny-import` завершился CLI error до исполнения программы (exit1, 0,006s); root отдельно разрешил исправленную stdin run команду. Других counterexample запусков не было.

Исправление должно сохранять presence/absence только известных ancestor candidates `_metadata.yml` и `_metadata.yaml` для выбранных inputs, с прежней physical containment/type проверкой. Полный `readDir`/сравнение всех sibling names возвращать не нужно. Добавление нового `_metadata.yaml` имеет тот же source-confirmed путь, отдельный опыт для него не запускался.

## P2: document `metadata-file(s)` остаётся вне суженного source scope

Место: тот же новый local return `source-snapshot.ts:206–216` и static full→local callsites; extraction `operation.ts:967–999` использует `includeMap`, конфигурационные файлы и ancestor `_metadata`, но не document metadata dependencies.

Конкретный допустимый вход: `selected/index.qmd` с frontmatter `metadata-files: [options.yml]` (аналогично `metadata-file`), существующий baseline `selected/options.yml`, owner `files.config` содержит проектный `_quarto.yml`. Native Quarto `populateFileInformation` (`/opt/quarto/bin/quarto.js:164818+`) сохраняет frontmatter в `fileInformation[source].metadata`, но не добавляет document metadata files в project `files.config`. При actual render `resolveFormatsFromMetadata` (`quarto.js:91240–91256`) вызывает `includedMetadata` (`:19784–19814`), которое разрешает `options.yml` от каталога input и читает его YAML. Helper этот наблюдённый pointer игнорирует: options.yml нет ни среди configurationInputs, ни в includeMap, ни среди ancestor `_metadata`.

Раньше внешний full source fence проверял signature baseline options.yml. После данной дельты его изменение до следующего native child или во время capture не проверяется и в static closing/current source guard. Поэтому metadata (например title либо условие/параметры projection) может соответствовать другим bytes, чем baseline выбранных native contributors. First finding об отсутствии `_metadata` эту отдельную ветвь не устраняет.

Подтверждение — source trace actual Core и bundled Quarto, без дополнительного исполнения и без утверждения о состоявшемся native render. Для приёмки нужно учесть actual selected document metadata-file(s) в существующем dependency scope либо явно остановить неподдерживаемую selected native зависимость до исполнения; unrelated owner files и whole-owner memberships возвращать в admission не нужно.

## Сохранённые проверки и ограничения

Прочитаны `core-owner-boundary-red.log`, `core-owner-boundary-controls.log`, `core-owner-boundary-native-inputs.log`, `core-owner-boundary-typecheck.log`. Writer evidence: ожидаемый RED sibling-addition; пять GREEN controls; расширенный selected config/include/baseline metadata control; один coupled bundled Deno check actual Core/PL/QRC entrypoints — PASS. Эти успешные команды не повторялись. Существующий metadata control мутирует уже присутствующий `_metadata.yml`, поэтому первый finding он не покрывает; document `metadata-files` также отсутствует в его fixture.

Initial whole-owner snapshot/hash и real public sourceRows остаются. General/full policy и A1 whole-copy fallback сохраняют собственные ограничения: unrelated baseline file edit может отказать на full route/whole-copy. Первый шаг не объявляется полным удалением same-S, native execution qualification, установленной композицией или release acceptance. Материализация actual selected bytes, ownership/audience, runtime/private/pointer/output/lifecycle должны сохраняться при исправлении findings. Цели, баллы, данные и UID/UUID этим review не менялись. Native render/inspect/hash/build, сеть/установки, benchmarks и новая матрица не выполнялись.

Перед передачей повторно проверен весь собственный primary interval от того же origin turn: `session_meta` строка1; все `turn_context` строки 8. Top/nested `gpt-6.1-sol/high` согласованы; сдвига origin нет. Отдельный Fast/Ultrafast режим в записях не указан, скрытый server tier не удостоверяется.
