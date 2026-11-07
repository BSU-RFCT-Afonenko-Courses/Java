# QRC: дополнительные metadata импортированного каталога

## Допуск и Source до кода

Роль Source writer: назначение `gpt-6.1-sol/medium`, Standard. Собственный chat `01a1a4e7-b119-7b11-82e2-4d3476034c5e`, agent `/root/qrc_native_metadata`, исходный turn `01a1a4e7-b14b-7db3-9e66-80d6be6b6893`. Primary: `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T07-53-30-01a1a4e7-b119-7b11-82e2-4d3476034c5e.jsonl`; session_meta строка 1, весь интервал turn_context строки 8/124 (текущий turn `01a1a4f5-f619-7070-986a-236312aa7c26`). Top-level и nested collaboration settings согласованно `gpt-6.1-sol/medium`: PASS до изменений. Исходная точка не сдвинута; Standard назначен, скрытая серверная маршрутизация не удостоверяется; globals не менялись.

Source `/home/tolya/JavaCodex/java-release-20261110/qrc`, branch `codex/java-release-20261110`, исходный HEAD `4a692ea1934224c844a1a3f277a7a77d513f012b`, status чистый. Применены ранее прочитанные AGENTS, spec/index, current contract и русское языковое правило. Предыдущий HTML diff уже находится в этом HEAD и не включается в новый diff.

До изменений поиск установил: `catalogKeys` и `targetKeys` потребляются только `catalog-validation.ts` в blanket `keys()`; других consumers нет. `validateImportedCatalog` строит новые catalog/generator/publication/target объекты из явно выбранных полей. Production consumers — `infrastructure/imports.ts` (внешний JSON → validator → importedTarget) и `entrypoints/body-links.ts` (caller catalog → validator → адресация Body). Model `Catalog` выводится из ReturnType этого validator, Target — из Catalog и typed importedTarget decoration. Поэтому игнорируемые metadata не должны расширить typed return или own producer schema. Отдельные запрещённые target `baseUrl/sourceTitle/defaultStyle` подменяют поля, назначаемые владельцем импорта, и сохраняются в самостоятельном отказе.

## Изменения и границы

В `infrastructure/catalog-validation.ts` удалены blanket `keys()` и его четыре вызова для catalog/generator/target/publication. Неиспользуемые `catalogKeys` и `targetKeys` удалены из `domain/contract.ts`. Существующий guard `isRecord` и `invalid(): never` уже сужают `value`, поэтому лишний `as Record<string, unknown>` заменён обычным alias `const item = value`. Новых casts, `any`, parser/framework или runtime qualification нет.

Schema discriminator, object guards, обязательные поля и их типы, namespace/key agreement, относительный безопасный page, fragment/slide/title checks и отдельный blacklist target binding fields сохранены буквально. Runtime return остаётся прежней явной projection; `Catalog`/`Target` продолжают выводиться из реальных producer types. Own export writer и output schema не изменены. Metadata не копируются в HTML/URL; фактические signature/label escaping и адресация остаются у прежних consumers.

`tests/imports.ts` сохраняет существующий сценарий импорта. Один вход содержит дополнительные metadata на всех четырёх уровнях: catalog/generator/publication/target; `deepStrictEqual` проверяет, что validated результат содержит прежние consumed поля и ничего сверх них. Этот же JSON записывается как реальный локальный источник существующего importTargets case. Два прежних отказа из-за дополнительных version keys заменены предметными отказами для numeric generator.quarto и target=null. Прежние unsafe-page, wrong consumed types, namespace/key и три owner-binding override отказы сохранены; unknown авторские import config keys по-прежнему запрещены. Новая матрица или отдельный тест реализации не вводились.

`docs/contract.md` уточняет принятие непотребляемых metadata только при импорте и сохраняющийся запрет binding overrides. Утверждение о closed keys авторской конфигурации не менялось, поскольку это другой контракт. Performance companion, Lua sorting, workflow, course/provider pins, installed copies и main state не затронуты. Commit/push/publication не выполнялись.

## Проверки и ошибки

Использованы готовые Quarto 1.11.5 и bundled Deno; общий cap 45 s, retry 0. Суммарное время трёх команд **1.762 s** (первый запуск 0.858 s, следующие две команды суммарно 0.904 s).

| Команда | Фактический результат |
| --- | --- |
| `/opt/quarto/bin/quarto run tests/imports.ts` | exit 1: PermissionDenied, os error 1 на `Deno.serve({hostname:"127.0.0.1", port:0})`. До этой строки новый metadata/projection `deepStrictEqual` успешно выполнен. Оставшийся HTTP/local import сценарий и его negative cases не выполнены; PASS всего файла не заявляется |
| `/opt/quarto/bin/quarto run tests/import-safety.ts` | PASS, exit 0: внешние подписи отображаются как текст, local/author markup сохраняется, URL protocols ограничены HTTP(S), mock HTTP/network/file причины отказов сохраняются |
| `DENO_DIR=/opt/quarto/share/deno_std/cache /opt/quarto/bin/tools/x86_64/deno check --no-remote --no-npm --no-lock --import-map=/opt/quarto/share/deno_std/run_import_map.json _extensions/reference-catalog/entrypoints/body-links.ts` | exit 1 до typecheck: ready cached graph недоступен, JSR manifest `@std/path` требует remote specifier; `--no-remote` запрещает resolution. Отдельный typecheck PASS не заявляется |

`--cached-only` для compiler не использовался. Повторов, новых dependency installs/cache preparation, сетевых запросов, render/inspect/build, scratch/copy negative matrix, benchmark или hashes не было. Предварительное чтение `deno check --help` подтвердило доступность `--no-remote`; эта защита оставалась включённой при единственной попытке check. Socket refusal не обходился. Ограничения тестов переданы координатору; дальнейшую независимую рецензию назначает он. `git diff --check` прошёл; поиск показывает отсутствие оставшихся consumers удалённых constants/helper. Измеренное ускорение **UNKNOWN**: устранены четыре обхода ключей ради собственного blanket refusal, замеры производительности не проводились.

Перед передачей повторно прочитан весь primary интервал от того же исходного turn: turn_context строки 8/124, top-level и nested `gpt-6.1-sol/medium` согласованы. Итоговый собственный допуск PASS.
