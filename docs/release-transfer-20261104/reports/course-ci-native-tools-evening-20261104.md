# CI курса и инструменты установки: пакет A4, 4 ноября

Автору больше не нужно поддерживать отдельный SHA/inventory установленного
расширения или каждого созданного ZIP ради обычной сборки. Java по умолчанию
собирает student один раз; SP сохраняет один student render и существующие
учебные unittest-проверки. Исходный Source-пакет и узкая дополнительная CLI-test/root-cleanup delta
получили независимое APPROVE. Один существующий test настоящего generator CLI
прошёл за 0.424348 секунды wall time. Полный CI и render не запускались.

Установка зависимостей и создание материалов остаются явными действиями.
Упаковщики используют обычные `zipfile`, Git и копирование файлов; они
заменяют объявленные назначения. Нужную рабочую правку сохраняют обычными
средствами Git перед генерацией. Ручного hash-аудита вместо удалённых locks,
нового bypass-флага, npm/nix или собственной системы зависимостей нет.

## Изменения и удалённые гарантии

| Область | Результат |
| --- | --- |
| Java `.github/workflows/check.yml`, `CI/render.sh` | Один student render в существующем disposable checkout; удалены providers job и цикл student/full/student. Явный `full` по-прежнему доступен автору. Общий лимит job сокращён с 360 до 150 минут; новый замер времени не проводился. |
| Java `CI/check-projects.sh` | Прямой вход в ту же обычную сборку. Прежнего published preflight здесь больше нет. |
| SP `.github/workflows/ci.yml` | Удалены provider/pins checks, пять generated `--check` и второй full render. Сохранены установка fish, наблюдение pwsh, существующий учебный unittest suite и student render. |
| Оба `tools/materialize-providers.py` | Сохранены Git archive выбранного commit, применение объявленного patch и копирование полных packages в scopes. Удалены `--check`, SHA/size/mode inventory, сравнение прежних bytes, проверки hashes patch/package и установленного provenance, anti-manual-edit/currentness и генерация `installed-packages.json`. Проверки безопасных путей, symlinks и descriptor сохранены. |
| `providers.json` обоих курсов | Удалены candidate `patchSha256`/`packageHashes`. Git commits/tags, выбранные packages/scopes и пути локальных patches сохраняются как выбор исходников. |
| Java `tools/sync-providers.py`, `tools/test-sync-providers.py`, optional-time `rebuild.py` | Удалены второй installer и устаревшее восстановление immutable исторического дерева. Исходные исторические patch/manifest сохранены; README указывает на текущий вход. |
| SP четыре `tools/build_* / materialize_public_materials.py` | Обычная явная запись ZIP/копий. Удалены hash manifests, режимы `--check`/`--write`, сравнение generated состояния и отказ при неизвестной ручной правке. `build(root)` создаёт материалы; `build_materials` сохраняет `project`. |
| SP `Taskfile.yml` | Render/preview/publish больше не зависят от install/inventory/materials-check. `install:from-sources` и `materials:write` остаются отдельными явными действиями. Удалены `install`, `test:providers`, `materials:check`. |
| SP `initial-state.json` проекта сопровождения | Удалён только неиспользуемый SHA патча; учебные Git IDs, родители, refs, HEAD/status и имя patch сохранены. Файл не регенерировался. |

Удалены активные `installed-packages.json` и `docs/local-extension-patches.json`
обоих курсов, четыре SP `tasks/projects/*/materials-hashes.json` и
`tools/public-materials-hashes.json`. Их исполняемые readers удалены. После закрытия A2 координатор отдельно передал A4 только техническую чистку:
в `java/_quarto.yml` и `system-practice/_quarto.yml` удалена ровно одна строка
`  - '!installed-packages.json'` в каждом файле. Остальная конфигурация,
включая subprojects, сохранена. Точная отдельная delta и baseline указаны ниже.

## Тесты и действительный смысл green

В `tools/test-materialize-providers.py` обоих курсов оставлены проверки
содержимого установленного patch/package, выбранных scopes, сохранения
постороннего package, executable Git mode, разных descriptor versions,
отказа при symlink scope и отсутствующем Git source. Удалены проверки
побайтовой повторяемости, inventory/provenance drift и запрета ручных правок.
Эти tools tests больше не включены в default course CI.

В четырёх SP `tests/test_*materials.py` удалены generated equality,
mtime/compression snapshots, read-only `--check` и anti-manual-edit cases.
Оставлены состав/данные starter/demo ZIP, доступность публичной копии в обеих
книгах, безопасные пути, реальный fetch/конфликт/apply и объявленная Git-история,
отсутствие частной конфигурации и host paths. Остальной существующий suite
не менялся: graders/checkers, references, допустимые варианты, отрицательные
и учебные hidden cases, shell-runtime, ZIP/TAR-безопасность сохраняются.

По замечанию независимого рецензента существующий
`test_demo_continues_latency_N_and_receives_U_without_local_U_ref` теперь
вместо прямого `build(self.root)` вызывает настоящий
`tools/build_maintenance_materials.py --root <временный fixture>` отдельным
Python-процессом с `-B`, `check=True` и лимитом 30 секунд. Публичный `--root`
уже существовал. Дальнейшие assertions ZIP, history/родителей, данных,
локального fetch/fast-forward и применения guide.patch сохранены.
Нового теста, флага, зеркальных проверок или счётчиков нет.

Java default workflow и раньше не запускал учебные reference/starter/negative
cases: прежний providers green этого не доказывал. Новый workflow честно
заявляет student render; проверку настоящего оценивания требуется запускать
штатным способом владельца после его перехода. Новой системы отбора тестов
или source assertion вместо grader не создано. Новых link/privacy проверок
в этом пакете нет; существующие проверки результата в расширениях и SP
не удалены. Полный render сайта после интеграции остаётся открытым.

## Остаток и документация

Полностью штатная установка исключительно через `quarto add/update` ещё
не подтверждена для текущей scoped раскладки: Core/PL имеют локальные patches,
SP выбирает Core и UI из разных версий, namespaces/scopes отличаются.
Сохранён простой существующий локальный installer без locks. Инструкция
приводит обычные Quarto-команды для published bundle и точно описывает
оставшийся локальный путь. Здесь установка не запускалась.

В обоих курсах остались `CI/check-production-pins.py`,
`CI/test-production-pins.py`, `CI/toolchain.json`; у Java также
`CI/check-local-candidate.py`, `CI/check-projects-candidate.sh`,
`CI/local-candidate.json`. Они принадлежат прежней интеграции с внешним
Platform release/OCI/delivery contract и содержат собственные asset/runtime
hash/receipt проверки. Из default CI они исключены. Из candidate preflight
удалён reader vendor `--check`; остальная внешняя проверка не изменена.
Нужен согласованный переход с Platform owner, включая producer/consumer и
данные курса; этот пакет не объявляет полное удаление системы на всём маршруте.
Новую обязательную ручную hash-проверку как замену CI документ не предлагает.

Обновлены текущие UPSTREAM, provider-installation, Java README и
qualified-course-checks, SP build-and-check и технические README четырёх
проектов. Старые `docs/research/`, `docs/provider-history/`, исторические
планы/обсуждения сохраняют прежние hashes/receipts/команды как историю.
`docs/refactoring-discussion.md` и `docs/release-execution-regulation-20261103.md`
ещё упоминают прежний installed inventory; это оставшиеся исторические ссылки,
они не являются исполняемыми readers. Канонические state/report A4 не менял.

## Основания и границы проверки

Фактические исходные HEAD: Java `6b0026a493779d87e08ec28b40341d16b8dd369a`,
SP `9a2f8120a37d1b42384e2f008b3e0a3a34247008`. Все прежние dirty/untracked,
UUID, учебные условия, веса и JUnit cases сохранены. До правок сохранены
адресные копии 46 файлов-кандидатов и status в
`course-ci-native-tools-baseline-20261104/`; список реально изменённых
45 файлов исходного пакета — `changed-files.json`. Основные patches включают
только собственные файлы и узкую CLI-test delta, без чужих root/tasks/config/state
edits. Две последующие root-cleanup правки сохранены отдельными patches:

- [Java patch](course-ci-native-tools-java-20261104.patch).
- [SP patch](course-ci-native-tools-system-practice-20261104.patch).
- [Java root cleanup](course-ci-native-tools-java-root-cleanup-20261104.patch).
- [SP root cleanup](course-ci-native-tools-system-practice-root-cleanup-20261104.patch).

Baseline двух root configs непосредственно перед этой узкой чисткой сохранён
в `course-ci-native-tools-cleanup-baseline-20261104/`. Суммарно A4 затронул
47 продуктовых файлов; root deltas не включают ранее рассмотренные правки A2.

Первичный допуск: собственный chat `01a1a6c3-e3b6-7141-94e9-3fadd26624b8`,
agent `/root/a4_course_ci_native_tools`, исходный turn
`01a1a6c3-e3e8-7821-b303-c8e9d59e7c0d`; `gpt-6.1-sol/high` совпадают в
верхних и вложенных полях всех `turn_context` от исходной точки. Baseline и
полный интервал сохранены в `../model-evidence/a4-course-ci-native-tools-*.json`.

Выполнено адресное чтение Source, сохранение baseline и подготовка diff.
До отдельного Root GO исполнений не было. После GO выполнен ровно один
описанный ниже behavioral case с генерацией временных fixtures. Compiler,
Quarto render/inspect/build, provider installer, whole-course generation,
content hashing, сеть/CI-trigger/commit/push не запускались.

После Source freeze прежняя косметическая `--help` проверка отклонена Root
и не исполнялась. Узкая CLI-test delta и две root-cleanup правки получили
независимое APPROVE. Root разрешил **одну** существующую behavioral проверку:

```sh
cd system-practice
timeout --signal=TERM --kill-after=1s 9s python3 -B -m unittest discover \
  -s tests -p test_maintenance_materials.py \
  -k test_demo_continues_latency_N_and_receives_U_without_local_U_ref -v
```

Общий предел — 10 секунд с учётом termination grace; 60-секундное предложение
снято до запуска. Вложенный generator CLI сохраняет существующий test timeout
30 секунд, но внешний timeout ограничивает весь case. Цель — наблюдать
результат настоящего публичного CLI через существующие проверки
ZIP/Git/content. Работа только с двумя временными учебными fixtures и локальным
Git-транспортом; Quarto, full course, сеть, hashes и install в этот запуск
не входят. Запланирован один запуск без неизменного повтора при отказе.
Результат сохранён ниже; Source/index/commit не изменялись.

## Результат разрешённого запуска

**PASS**, exit code 0; один выбранный test, unittest сообщает 0.348 секунды,
полное wall time команды **0.424348 секунды** из предела 10 секунд.
Наблюдались фактический generator CLI с временным `--root`, его ZIP/Git-history,
данные, локальные fetch/fast-forward и применение patch. Повторов и других
checks не было. stdout пуст; stderr содержит выбранный case `... ok`,
`Ran 1 test in 0.348s`, `OK`.

Точная команда/argv/cwd, timestamp, exit code и wall time:
[run.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/course-ci-native-tools-public-cli-case-20261104/run.json).
Исходные потоки сохранены отдельно:
[stdout](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/course-ci-native-tools-public-cli-case-20261104/stdout.log),
[stderr](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/course-ci-native-tools-public-cli-case-20261104/stderr.log).

Полный модельный интервал перед запуском: PASS, три `turn_context` от прежнего
исходного baseline; верхние и вложенные `gpt-6.1-sol/high` совпадают.
Дальнейшая работа заморожена; index/staging не затрагивались, Source commit
не создавался. Этот PASS подтверждает выбранный CLI-result contract и
не объявляет full course/render/оценивание проверенными.
