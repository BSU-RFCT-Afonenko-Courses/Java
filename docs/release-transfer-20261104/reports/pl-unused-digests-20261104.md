# Удаление непотребляемых digest PrairieLearn

4 ноября 2026. Ограниченная работа Source: только `prairielearn` на `codex/java-release-20261110`, исходный HEAD `30edc03295e508f174c64b399f35b14ad37488fa`; дерево перед работой чистое. Без commit/push/PR, изменений mainstate и установленных копий расширения.

## Собственный допуск до изменения

Роль Technical Source writer, назначение `gpt-6.1-sol/medium`, Standard. Собственный chat `01a1a4f0-2f29-7a82-aea6-3ca60da341f8`, путь `/root/pl_unused_digests`, исходный turn `01a1a4f0-2f52-7491-a118-127ea689dcee`. Первичные записи: `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-02-46-01a1a4f0-2f29-7a82-aea6-3ca60da341f8.jsonl`, session_meta строка1, turn_context строка8. Верхние `model/effort` и вложенные `collaboration_mode.settings.model/reasoning_effort` согласованы с назначением. Перед приёмкой проверяется полный интервал от этого исходного turn. Standard назначен; скрытая серверная маршрутизация и фактическая скорость неизвестны.

Прочитаны `prairielearn/AGENTS.md`, `spec/index.md`, применимая часть `docs/authoring.md`, локальная копия общего `core/docs/russian-language-policy.md`, `java/docs/model-admission.md`, producer review и pattern pass. Ожидается независимый review root.

## Consumers до изменения

Адресный `rg` по активному стеку `core`, `prairielearn`, `platform`, `download`, `qrc`, `site-java`, `system-practice`, `java`, `performance`, `provider-sources` проверил имена функций, полей и schema strings в коде, схемах и документации. Raw savedstate/outputs не читались; длинные research JSON строки не печатались (`--max-columns`); incidental исторические research совпадения не использованы как production evidence.

- `nativeProjectWitnessHash`: production producer найден только в PL `application/native-provenance.ts`; настоящие обращения к полю были в `tests/native-provenance-values.ts` и `tests/native-fact-owner.test.ts`. Production `native-course.ts` использует `witness.projects` и `witness.sourceRows`, а не digest. Установленные PL-копии в `java/tasks` и `system-practice/tasks` содержат прежний producer; они не изменялись и не стали независимым consumer.
- `nativeVerificationInventory`: `native-course.ts:521` создаёт результат и передаёт его целиком в exporter на строке555. Замыкающая сигнатура `export-course.ts:157` принимает только `{scenarios,scenariosHash}`; строки794/825–838 используют их для настоящей completeness-проверки и существующего `verificationInventoryHash`. Эти потребляемые значения сохранены.
- Integration fixtures `native-selection-integration.ts` читают scenarios; `native-a1-builder-integration.ts`/`native-a1-reuse-integration.ts` сохраняют весь объект как diagnostic fixture output. Их Python reader `native-a1-platform.py:28` использует `scenariosHash`; production read именно private `inventoryHash` не найден. Единственное явное сравнение private inventory digest было в `native-verification-inventory.test.ts`.
- Схемы и действующая документация отдельного consumer удаляемых digest не показали. Общие `inventoryHash` legacy checks/delivery являются иной областью и сохранены. Вывод ограничен доступным активным стеком; произвольный внешний import частного модуля здесь не удостоверен.

## Точный diff

В `prairielearn` изменены пять файлов, `+4/−27`:

1. `application/native-provenance.ts`: удалены импорты `canonical/hash`, wrapper spread и `nativeProjectWitnessHash`; непосредственно возвращается прежний witness object. Все identity/coverage/sourceRows/project declaration/hidden-nonbank guards, typed normalization и `requireNativeChecksContract` сохранены.
2. `application/verification-inventory.ts`: удалены только `inventoryHash: await hash(canonical(inventory))` и устаревшая строка комментария о нём. Весь inventory/schema/courseId/operation/questions, descriptor/provenance cloning, actual selected file byte hashes, references, contract cases, scenario sorting и `scenariosHash` сохранены.
3. `tests/native-fact-owner.test.ts`: название одного existing теста обновлено, удалён блок смены scoring ради смены opaque hash. Existing проверки нормализации policy, declaration target и foreign project owner остаются.
4. `tests/native-provenance-values.ts`: удалён блок смены source-row hash ради смены opaque witness hash и его единственный summary flag `sameSTraceCommitted`. Existing witness projects/sourceRows/materialization/formats/hidden-nonbank assertions, восемь invalid DTO controls и strict-v3 refusal остаются.
5. `tests/native-verification-inventory.test.ts`: название existing теста уточнено; удалён только descriptor mutation ради сравнения private inventory digest. Existing проверки реальных scenario IDs, changed bytes, foreign exercise, duplicate references и duplicate contract cases остаются.

Новый proof/hash/validator, cache, version, зеркальный test не добавлялись. `deliveryHash`, `verificationInventoryHash`, `scenariosHash`, Core46/ad80 и PL Pandoc consumer не менялись. Сокращение самого `questions` payload из прежнего audit proposal намеренно не включено в этот узкий diff.

## Выполненные проверки и ошибки

До запуска полностью прочитаны оба запускаемых pure fixtures и изменённый `native-provenance-values.ts`. Выполнен один runtime run, retry0, cap45s:

```sh
timeout 45s /opt/quarto/bin/tools/x86_64/deno test --no-check --no-config --no-lock --deny-import tests/native-fact-owner.test.ts tests/native-verification-inventory.test.ts
```

Bundled executable отдельно сообщил Deno2.7.14, TypeScript5.9.2. Результат exit0, **5 passed/0 failed**, test envelope83ms. После этого только название verification-теста уточнено под сохранённую предметную проверку; его тело повторно не менялось и run не повторялся. Разрешения read/write/run/env/net не предоставлялись. Это pure DTO/byte/scenario checks; Core operation, native child, render/inspect/course/build/fullmatrix, установки и сеть не запускались. `native-provenance-values.ts` не исполнялся: он динамически импортирует настоящий Core.

Один affected TS program с существующей `/tmp/pl-ci-core-imports.json`:

```sh
timeout 45s /opt/quarto/bin/tools/x86_64/deno check --no-config --no-lock --deny-import --import-map /tmp/pl-ci-core-imports.json _extensions/course-prairielearn/application/native-provenance.ts _extensions/course-prairielearn/application/verification-inventory.ts tests/native-fact-owner.test.ts tests/native-provenance-values.ts tests/native-verification-inventory.test.ts
```

**Exit1 до typecheck:** map содержит локальный Core, но stdlib aliases `jsr:@std/path@0.224.0` и `jsr:@std/fs@0.224.0` не оказались доступными через текущий cache. Deno напечатал `Download https://jsr.io/@std/path/meta.json`, затем отказ `JSR package manifest ... failed to load` из `core/.../body-export/references.ts:3:34`. `--deny-import` сохранён, сетевые разрешения не выдавались. Это не TS diagnostic и не успешная compilation. Подготовительная ошибка исполнителя: отсутствие найденного std cache следовало принять как limitation до запуска. Повторов, нового map/resolver, cache warming или installs не было; неподдерживаемый `deno check --cached-only` не использовался.

Read-only discovery дополнительно получил ожидаемые ошибки для отсутствующего ошибочно названного `tests/native-provenance_test.ts` и `/tmp/*deno*`; после чтения фактических файлов они не повторялись. Build/test failures в успешном pure run отсутствуют. `git diff --check` exit0; полный собственный diff прочитан; повторный `rg nativeProjectWitnessHash` в Source/tests/spec/docs PL не находит ссылок. HEAD остался исходным. Готовность TS graph и всего release не заявлена.

Стоимость удалённых двух canonical/digest операций и ускорение курса **неизвестны**.83ms — envelope пяти маленьких fixtures, не benchmark и не прогноз производительности курса.

## Допуск перед передачей

Повторно прочитан весь собственный primary интервал от исходного turn: единственный `turn_context` на строке8, top/nested `gpt-6.1-sol/medium`, новых несовпадений нет. Исходная точка не сдвигалась. Source diff и отчёт готовы для независимого review; commit/push/PR/mainstate не выполнялись.
