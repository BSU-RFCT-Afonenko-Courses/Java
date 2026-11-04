# QRC: штатная семантика HTML metadata

## Первичный допуск до изменений

Роль: Source writer, назначение `gpt-6.1-sol/medium`, Standard. Собственный chat `01a1a4e7-b119-7b11-82e2-4d3476034c5e`, путь `/root/qrc_native_metadata`, parent `01a1a142-7adc-7371-9765-4871d807dfcd`; исходный turn `01a1a4e7-b14b-7db3-9e66-80d6be6b6893`. Primary: `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T07-53-30-01a1a4e7-b119-7b11-82e2-4d3476034c5e.jsonl`. `session_meta` строка 1 подтверждает собственную идентичность; единственный `turn_context` строка 8 содержит top-level `model=gpt-6.1-sol`, `effort=medium` и согласованные `collaboration_mode.settings`. PASS до начала роли. Standard назначен координатором; скрытая серверная маршрутизация не удостоверяется. Глобальные настройки не менялись.

Source: `/home/tolya/JavaCodex/java-release-20261110/qrc`, ветка `codex/java-release-20261110`, HEAD `798f8c0d9f87fd5dcfe9ee85b79df57e647d2f3c`; исходный status чистый. Прочитаны AGENTS, spec/index, current contract и source-only исследование `reports/pattern-pass-core-20261104.md`. Русское языковое правило прочитано в локальной source-копии `/home/tolya/course-tools/performance-20261012/sources/quarto-course/docs/russian-language-policy.md`.

До кода поиск по репозиторию установил ровно два production вызова `canonicalizeMetadata`: `readPage(html)` и `linkPages(replace(page.html, edits))`, оба без `sourceRoot`. Маскирование `data-qrc-source` при переданном корне использовалось только helper-тестом. Current contract также явно указывает отсутствие параметра в обычной публикации. Следовательно, удаляемая сортировка и неиспользуемое маскирование не являются границей student privacy. Полный linker удаляет контейнеры probes собственными edits, local сохраняет их для последующей full-финализации; эти механизмы остаются.

## Diff и обоснование

- `infrastructure/pages.ts`: HTML сразу передаётся штатному parser, без предварительного regex-прохода сортировки.
- `infrastructure/linker.ts`: возвращает результат существующих edits, без повторного прохода сортировки всего HTML.
- `infrastructure/metadata.ts` удалён: других production consumers поиск не выявил.
- Существующий `tests/metadata-order.ts` проверяет два порядка входных атрибутов через реальные `readPage`/`linkPages`: цели и title, HTML и текст подписи, href, ref/style/custom/deferred, сохранение source/namespace/hidden/aria-hidden в local и удаление probes в full. Отдельно проверена local-отсрочка без href с сохранением авторской подписи. Сравнения bytes двух публикаций, helper idempotence/root masking и тесты исчезнувшей regex-обвязки удалены. Новых snapshots, hashes или normalization нет.
- Узкий раздел current `docs/contract.md` описывает фактическую семантику и больше не ссылается на удалённый helper. CI entry остаётся существующим, поэтому workflow не изменён.

Типы API не менялись; `any` и type casts не добавлены. Существующие label/link validation, escaping, strict full, probe removal, URL/path containment, search и student privacy механизмы не редактировались. Нативная семантика parser по имени атрибута уже обеспечивает независимость от порядка. Цена удалённого слоя — два полных regex-прохода по HTML на обычном пути read/link, создание массивов атрибутов и сортировка выбранных групп. **Измеренное ускорение UNKNOWN**: benchmark не запускался, количество секунд ускорения не выводится из структуры кода. Утраченное собственное свойство — равенство сериализованных bytes при перестановке служебных атрибутов.

## Проверки и ошибки

Готовый `/opt/quarto/bin/quarto --version` вернул `1.11.5` (exit 0). Один ограниченный запуск команд, общий cap 45 s, retry 0:

| Команда | Результат |
| --- | --- |
| `/opt/quarto/bin/quarto run tests/metadata-order.ts` | PASS, exit 0: реальная семантика targets/labels/links/local/full при перестановке атрибутов |
| `/opt/quarto/bin/quarto run tests/local-linking.ts` | PASS, exit 0: отсрочка, meaningful labels, known targets, current facts, strict full/default, malformed requests |

Версия и обе адресные проверки заняли суммарно **1.807 s**. Дополнительная адресная статическая проверка `/opt/quarto/bin/tools/x86_64/deno check --cached-only --import-map=/opt/quarto/share/deno_std/run_import_map.json tests/metadata-order.ts tests/local-linking.ts` завершилась exit 1 за **0.005 s** до typecheck: bundled Deno отверг `--cached-only` как unexpected argument. Повтора не было (retry 0); успешный отдельный typecheck этим отчётом **не заявляется**. Суммарный расход адресных проверок ниже 45 s. `git diff --check` прошёл. После diff поиск `canonicalizeMetadata|infrastructure/metadata.ts` по `_extensions tests docs` не нашёл оставшихся ссылок.

При чтении были исправлены предположенные пути: `java/reports/pattern-pass-core-20261104.md` отсутствует, прочитан действительный release-root `reports/pattern-pass-core-20261104.md`; языковое правило отсутствует в `java/docs`, прочитана указанная выше локальная source-копия. Предположенные `/opt/quarto/share/run_import_map.json` и `/opt/quarto/share/deno/run_import_map.json` отсутствовали; действительный путь найден адресным поиском. Эти ошибки чтения не изменяли Source и не запускали установки/сборки.

Ни full CI, ни render/inspect/build, whole-course, benchmark, network install, hashes не запускались. Installed copies, чужие worktrees, provider pins и оригинальный репозиторий не изменялись. Commits/push/publication не выполнялись. Приёмка независимым reviewer назначается координатором.

## Отдельный следующий diff: Lua

`lua/attributes.lua:3–8` превращает map в список пар с `table.sort(keys)`; производственные consumers — `lua/links.lua` (native Span source-export и Link) и `lua/probes.lua` (probe rows и container). Значения из исходной карты копируются без semantic transformation; в прочитанных consumers сортировка не выполняет validation, escaping, адресацию или privacy policy. `tests/metadata-order.lua` проверяет только порядок пар и смежность сериализованных data attributes, плюс неизменность исходной карты. Следовательно, обоснование этого слоя — собственная детерминированная сериализация. Native Pandoc Attr может принимать карту без требования sorted bytes; кандидат отдельного next diff — убрать helper/его calls и служебный byte-order test, оставив фактические значения/классы/source-export marker/probes и предметные consumers. Lua production и test в текущем diff не менялись; такое изменение здесь не проверено и не заявлено готовым.

Перед передачей повторно прочитан весь собственный primary интервал от исходного turn; все `turn_context` (строка 8, тот же turn) подтверждают top-level и nested `gpt-6.1-sol/medium`. Исходная точка не сдвигалась; итоговый допуск PASS.
