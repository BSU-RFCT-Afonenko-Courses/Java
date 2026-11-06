# QRC post inspect: честная consumed JSON/config boundary

Результат: **дельта готова к независимому root review**. Existing diagnostics
control и affected bundled compiler прошли с первой попытки; общий расход
**1.2346 s** при cap20 s/retry0. Production Source после PASS не менялся.

## Вход, scope и разрешённая конкретизация

Repo `/home/tolya/JavaCodex/java-release-20261110/qrc`, исходный HEAD
`f0d6c3ec52d045fd76c7eb58464d5cf488a2384c`, начальный status чистый.
Прочитаны применимые AGENTS/spec и только post/config/publish/process APIs
и существующие relevant diagnostic/output controls.

Первичный source-анализ установил, что post имел только namespace string guard,
но не inspect/config decoder или selected output-dir narrowing. Root явно разрешил
**однократную узкую external boundary**: record narrowing рядом с namespace guard
и string guard лишь выбранного output path. Новая controlled malformed diagnostic
разрешена; общий Native decoder, повторный catalogConfig и Core owner guards — нет.

Изменены ровно два product-файла:

- `_extensions/reference-catalog/entrypoints/post.ts`;
- новый focused pure `_extensions/reference-catalog/infrastructure/post-input.ts`.

Изменён один existing control: `tests/diagnostics.ts`. Search-дельта, HTML FFI,
imported catalog, config/publish business validators и остальные entrypoints
не редактировались. Commit/push/mainstate не менялись.

## Итоговый договор типов и поведения

`JSON.parse` в post возвращается в переменную `unknown`.
`postConfiguration` однократно проверяет внешние envelope/config records и
потребляемый reference namespace; возвращает честный `PostInspection`:
`config: Record<string, unknown>`, `namespace: string`.
Config передаётся **тем же объектом**, дополнительные Native metadata keys
сохраняются без закрытия schema, stripping или копирования.
Namespace остаётся только string/nonempty: новый pattern/trim gate не добавлен.

`postOutputDirectory` обращается к `project` лишь как к record для безопасного
optional access и выбирает ровно
`ENV override ?? native project.output-dir ?? "_site"`.
Только выбранное значение проверяется как string перед прежним `resolve`.
Пустые ENV/Native строки сохраняются; nullish fallback прежний; malformed Native
path, перекрытый ENV override, не превращается в отказ или новый default.

Разрешённая malformed-boundary дельта: отсутствующий/неправильный inspect config
получает controlled existing namespace diagnostic, а выбранный nonstring output
получает `QRC.CONFIG_INVALID` с `field=project.output-dir` вместо случайного
TypeError внутри path API. Невалидный выбранный path не маскируется fallback.

`quarto inspect`, profile аргументы, output-list file/ENV loading и обязательность,
фильтрация пустых строк, version command, stage resolution и `publish` с
`scope: "local"`, `format: "html"` сохранены. `catalogConfig` по-прежнему вызывается
только existing publish boundary; повторного business config parsing нет.
Новых IO, casts, suppressions, dependencies, Core imports или общего framework нет.

## Meaningful existing control

`tests/diagnostics.ts` импортирует pure helper, **не post entrypoint**:
проверка не запускает inspect. Добавлены assertions:

- null/array/missing/malformed envelope/config/namespace получают existing
  diagnostic code и source/field context;
- config identity и дополнительные вложенные Native metadata сохраняются;
- whitespace namespace не запрещается новым pattern gate;
- Native output, ENV precedence и обе пустые строки сохраняются;
- пустой ENV override перекрывает malformed Native output-dir без его отказа;
- missing/nullish output и primitive/array project дают прежний `_site`;
- выбранные boolean/number/object/array output values получают controlled refusal,
  а не молчаливый valid default.

Существующие business/target/import/HTML/output diagnostic assertions не менялись.
Existing control использует свои маленькие tempfile проверки publish отказа,
но не запускает Native/render/quarto child.

## Проверки и exact evidence

Bundled executable `/opt/quarto/bin/tools/x86_64/deno`, actual existing map
`/opt/quarto/share/deno_std/run_import_map.json`,
`DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache`.
Map был прочитан; новые URL/версии/caches не угадывались.

| Проверка | Результат | Время |
| --- | --- | --- |
| Existing `tests/diagnostics.ts`, `deno run --no-check` | PASS, exit0 | 0.2428 s |
| `deno check post.ts post-input.ts tests/diagnostics.ts` | PASS, exit0 | 0.9916 s |

Оба вызова использовали `--deny-import --no-config --no-lock` и actual import map.
Runtime control дополнительно `--allow-read --allow-write --allow-env --deny-net`.
Compiler не использовал `--cached-only`. Total1.2346 s, retry0.
`git diff --check` — PASS.

Точный diff, включая **новый untracked helper**:
[qrc-post-inspection-types-20261104.patch](qrc-post-inspection-types-20261104.patch).
Команды/elapsed/exit:
[qrc-post-inspection-types-checks-20261104.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/qrc-post-inspection-types-checks-20261104.json).
Полные логи:
[qrc-post-inspection-types-control-20261104.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/qrc-post-inspection-types-control-20261104.log),
[qrc-post-inspection-types-compiler-20261104.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/qrc-post-inspection-types-compiler-20261104.log).

Не запускались actual post/Native/render/inspect/build/hash/full suite,
network/install или CI. Actual Quarto inspect integration этим scoped pure
control/compiler не подтверждается; до приёмки нужен независимый root review.

## Полный собственный модельный допуск

Agent `/root/core_yaml_types`, собственный chat
`01a1a54c-ab39-7a83-808d-a190428ff628`, неизменённый origin turn
`01a1a54c-ab6a-7412-894c-270be9b9aac3`, назначение `gpt-6.1-sol/medium`.
Собственный primary source:
`/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T09-43-47-01a1a54c-ab39-7a83-808d-a190428ff628.jsonl`.
Весь исходный интервал проверен по session_meta и всем turn_context, включая
вложенные collaboration settings: **8 записей, PASS**. Origin не сдвигался.
Standard назначен; hidden routing не удостоверяется, globals не менялись.
[Машинное свидетельство](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/qrc-post-inspection-types-admission-20261104.json).
