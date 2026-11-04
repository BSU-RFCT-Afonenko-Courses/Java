# Независимый обзор версий пакетов provider release

## Первичный допуск

Назначение рецензента `gpt-6.1-sol/high`, Standard; путь `/root/review_single_compilation`, собственный чат `01a1a206-d7b5-72e0-9ecd-9a51668b54e4`. Первичный источник `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T18-28-39-01a1a206-d7b5-72e0-9ecd-9a51668b54e4.jsonl`.

Проверены собственная session_meta и полный интервал от исходного turn `01a1a206-d7e9-71f1-914b-9080eec3d241`, включая возобновления `01a1a212-6d09-7d53-9d4c-fd9a4ac24512` и `01a1a216-47c0-73b1-b32a-4ccc6f47584e`. Во всех трёх turn_context верхние model/effort и вложенные collaboration_mode.settings.model/reasoning_effort согласованы: `gpt-6.1-sol/high`. Начало допуска не сдвигалось. Standard назначен координатором; отдельного service tier в первичных записях нет, Fast/Ultrafast не выбирались. Глобальная конфигурация и нормативный `java/docs/model-admission.md` не менялись. Допуск подтверждает зарегистрированные платформой параметры, не скрытую маршрутизацию.

## Область

Read-only review Java `b65ea6a..99e31148`: `tools/materialize-providers.py`, `tools/test-materialize-providers.py`, `docs/provider-installation.md`, окружающий prewrite путь и авторский `materializer-package-versions.md`. Повтор tests, materializer, whole-course, render/build или hash запрещён поручением и не выполняется. Прочитаны полный diff трёх файлов и полный materializer/test source; новые опыты не выполнялись.


## Результат: Clean

Существенных замечаний к diff нет. Удалён именно неверный инвариант равенства версии каждого descriptor версии общего provider release; новый реестр версий или обход проверки происхождения не добавлен.

- `staged()` по-прежнему сначала проверяет форму точных tag/version/commit/sourceZip и совпадение `tag^{commit}` с закреплённым commit. Stage строится настоящим `git archive` этого commit, а не из изменяемого working tree; небезопасные пути/ссылки/специальные archive entries по-прежнему запрещены.
- Наличие `_extension.yml` проверяется в уже построенном inventory каждого объявленного package. Каталог вместо файла не удовлетворит этому условию, symlink откажет ещё при inventory. Descriptor остаётся частью exact package bytes/mode inventory и последующего installed lock. Его UTF-8/regex чтение для сравнения с общим номером release больше не нужно.
- Candidate путь не изменён: закреплённые provenance/baseCommit, patch SHA, `git apply --check`, применение patch и packageHashes сохраняются. Для published release источником всех собственных package versions остаётся тот же точный commit.
- `main()` по-прежнему полностью собирает stage всех providers/packages и проверяет каждый старый scope до первой записи target. Local edit/content/mode/symlink guards, повторный inventory перед заменой, mode normalization, post-copy comparison и atomic installed manifest сохранены. `--check` по-прежнему сверяет scopes/provenance/candidate и полный inventory; descriptor bytes не исключены из сравнения.
- Новый test создаёт настоящий mixed Git release, закрепляет его актуальный tag/commit, выполняет реальный CLI `--write`, проверяет доставленные UI descriptor/content, provenance в installed lock и последующий `--check`. Он адресно отличает корректную mixed поставку от прежнего отказа. Существующие отрицательные tests не удалены и не ослаблены.
- Документация правильно различает provider release version и собственные package versions; обещания source pins, local-change и prewrite защиты сохранены. Provider/installed schema и сами installed packages в diff не менялись.

## Свидетельства и ограничения

Переиспользованы результаты root report: новый fixture до правки воспроизвёл отказ за0.096s; после правки12существующих tool tests прошли за1.657s. Независимый reviewer эти tests/CLI materializer не повторял. Whole-course, inventory/hash, render/build/inspect не запускались; source/ветка и установленные файлы не изменялись. Создан только этот review report.

Clean относится к проверенному source diff и сохранению его границ; опубликованная/установленная course квалификация этим обзором не заявляется. Перед передачей повторно проверен весь тот же собственный исходный интервал: три turn_context, все верхние и вложенные `gpt-6.1-sol/high` согласованы; новой точки допуска нет.
