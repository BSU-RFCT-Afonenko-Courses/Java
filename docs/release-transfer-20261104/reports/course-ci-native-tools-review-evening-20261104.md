# Независимая Source-рецензия A4: CI курса и инструменты установки

**APPROVE для ограниченного Source-пакета и его узкой последующей delta.**
Блокирующих дефектов в предъявленных patches не найдено. Это решение не подтверждает исполнение
генераторов, установку зависимостей, render, CI или оценивание курса.

Рецензент — отдельный исполнитель `/root/a4_course_ci_review`, собственный chat
`01a1a6cd-f847-7b10-a55a-d338db0965ff`, исходный turn
`01a1a6cd-f878-7f63-8f72-a5a83f7a7560`. Первичные `session_meta` и все
`turn_context` от этой точки подтверждают `gpt-6.1-sol/high`, включая вложенные
настройки `collaboration_mode`. Свидетельства находятся в
`../model-evidence/a4-course-ci-native-tools-review-baseline-20261104.json` и
`../model-evidence/a4-course-ci-native-tools-review-full-interval-20261104.json`.

## Точная граница

Прочитаны отчёт автора `course-ci-native-tools-evening-20261104.md` и patches
`course-ci-native-tools-java-20261104.patch`,
`course-ci-native-tools-system-practice-20261104.patch`: 17 Java и 28 SP paths.
Проверена связь всех postimage hunks с текущими файлами без content hashing;
удалённые paths отсутствуют. HEAD Java —
`ea17038679a7b8fdca0c8ad26394658c2a0d85e2`, SP —
`1c038dd35e88ae7afa62c0f98d777dbc31319f5a`.
Исходные базы авторского diff — Java
`6b0026a493779d87e08ec28b40341d16b8dd369a`, SP
`9a2f8120a37d1b42384e2f008b3e0a3a34247008`.
Посторонние изменения root/docs/Site/A3 не принимались и не индексировались.

После первого решения отдельно рассмотрена только узкая delta: один retained
behavioral test переведён на настоящий generator CLI; в Java/SP root configs
удалено по одному устаревшему исключению `!installed-packages.json`.
Отдельные `course-ci-native-tools-{java,system-practice}-root-cleanup-20261104.patch`
сверены с сохранёнными непосредственно перед чисткой configs: иных изменений
относительно этих baseline в них нет. Суммарная граница A4 теперь 47 продуктовых
paths. Ранее рассмотренные изменения A2 и остальные прежние paths повторно
не рецензировались. Исходная точка модельного допуска сохранена; весь интервал
из двух `turn_context` повторно подтверждён перед обновлённым решением.

## Основания решения

- Оба installer сохраняют действующий способ установки: Git archive выбранного
  commit, обычный `git apply` объявленного patch, затем копирование полного
  package в scopes. Непубликованный candidate commit для этого не требуется.
  Манифест SP продолжает выбирать Core и UI из разных commits; версии
  descriptor не обязаны совпадать с версией bundle. Нового resolver нет.
- Hash/inventory/currentness machinery удалена из этих инструментов вместе с
  активными manifests. Удалены generated equality, mtime, compression и
  anti-manual-edit tests. Отдельного bypass и нового ручного hash-аудита нет.
  Installer сохраняет явный `--write`; четыре material generators теперь
  выполняют обычную запись без `--check`/`--write`, что отражено в документации
  и Taskfile.
- Java default workflow вызывает один student render в disposable checkout.
  SP сохраняет один student render и существующий `unittest discover -s tests
  -p 'test_*.py'`. Проверки providers не включены в default CI. Редактирование
  сгенерированного материала больше не блокирует render.
- Сохранены проверки безопасных путей и symlinks установки, descriptor,
  допустимых назначений ZIP, границ seed roots. Переписывание выбранного
  package удаляет его собственное прежнее содержимое; посторонние packages
  остаются вне списка назначений. Необходимые исходники текущей установки
  не удалены: исторические optional-time patch/manifest остаются, удалён
  только его старый rebuild и второй sync installer.
- Генерация учебного содержимого, родителей/ref/HEAD Git и очистка личной
  конфигурации в retained generators сохранены. В тестах остаются реальные
  fetch/conflict/apply, точные данные starter/demo, отсутствие приватной
  конфигурации/host paths и публичные копии обеих книг. Остальные grader,
  alternative/negative/hidden educational cases в этих patches не меняются.
- Адресный поиск исполняемых callers не выявил импорта удалённых
  sync/rebuild modules, обращений к удалённым manifests или старых
  `build(..., write=...)` в retained tests. Импорты общих Git helpers,
  используемых учебными seed recipes, сохраняются.

## Ограничения и следующий малый запуск

Предложенный автором `build_materials.py --help` проверяет загрузку CLI и
справку; он не подтверждает генерацию и содержимое материала. По нему
нельзя принимать публичное поведение генератора.

Существующий небольшой retained behavioral test —
`MaintenanceMaterials.test_demo_continues_latency_N_and_receives_U_without_local_U_ref`
в `system-practice/tests/test_maintenance_materials.py`. Он создаёт временные
материалы через настоящий `build_maintenance_materials.py --root <temp>`,
читает demo ZIP и initial state, проверяет данные проекта,
родителей M/N/U и отсутствие локальной ссылки U, затем делает настоящий Git
fetch/merge/apply и проверяет полученный текст. Для отдельного разрешённого
запуска из `system-practice/` предлагается ровно:

```sh
timeout --signal=TERM --kill-after=1s 9s python3 -B -m unittest discover -s tests -p test_maintenance_materials.py -k test_demo_continues_latency_N_and_receives_U_without_local_U_ref -v
```

Жёсткая граница — до 10 секунд с учётом kill-after; измеренного времени и
результата нет. В обновлённом случае путь CLI вычислен абсолютно от
`Path(__file__).resolve().parents[1]`; `sys.executable -B` запускает тот же
Python без записи bytecode. `cwd=self.root` и `--root self.root` указывают на
временную fixture с заранее скопированным seed. Импорты генератора разрешаются
из его существующего каталога tools. Старых `--write`/`--check` в вызове нет,
новых CLI flags не добавлено. Все прежние content/history assertions после
вызова сохранены; новый mirror/internal test не создан. Прежняя граница
отсутствия generator-CLI behavioral case устранена в Source.

Вложенный `subprocess.run(timeout=30)` ограничивает самостоятельное исполнение
test case; он не требует внешнего лимита 60 секунд. Указанное в обновлённом
отчёте автора предложение `timeout 60s` не является согласованным следующим
запуском: конкретной причины для увеличения лимита в Source не найдено.
Предлагается только приведённая выше одна команда с общим пределом 10 секунд.

Остаточные `CI/check-production-pins.py`, `CI/test-production-pins.py`,
`CI/toolchain.json`, а в Java также candidate tools, сохраняют прежний внешний
Platform contract с hash/receipt. Default workflows и обычные render tasks
их не вызывают. Из изменённого candidate preflight удалён vendor `--check`.
Полное удаление прежнего production assembly contract этим решением не
подтверждается; нужен согласованный переход владельца Platform. Устаревшие
исключения `installed-packages.json` в двух root Quarto configs устранены
рассмотренной отдельной узкой чисткой.

Не выполнялись tests, installer/materials build, compiler, render/inspect,
сеть, CI trigger, content hashing, stage/commit/push. Вердикт основан на Source,
реальных сохранённых assertions и проверке состава diff, а не на `--help`
или предположении об успешном исполнении.
