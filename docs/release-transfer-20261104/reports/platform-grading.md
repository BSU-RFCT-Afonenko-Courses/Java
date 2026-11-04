# Пакет 1: Java grading

Исполнитель `/root/release_grading`, clone `/home/tolya/JavaCodex/java-release-20261110/platform`, branch `codex/java-release-20261110`, исходный HEAD `865d01705f8e9acee59de09661d5c00c290f1583`.

## Выбранная граница до реализации

Полная сверка байтов JDK/Gradle остаётся в существующей подготовке image (`install-native.py`/`complete-native.py`). Единственный привилегированный writer — supervisor; sbuser/nativebuild не владеют инструментами, не имеют writable group/other paths, запускаются через существующий Landlock helper без capabilities. В начале official job после возможного remap ролей допускаются root ownership, права, отсутствие symlink/hardlink и точный metadata inventory инструментов; готовый lock остаётся существующим installation input. Кэш, новый proof/receipt протокол не вводятся.

`_run` перестаёт проверять установку до/после каждой JVM. На переходах nativebuild→grading сохраняются reaper/проверка отсутствия build-процессов, актуальных roles и фактической недоступности complete JDK для sbuser. Источники, copied recipe/input, private trusted inputs и artifact сохраняют предметную проверку изменения. Повторный `finish` preflight удаляется. API/JUnit/баллы/helper compilation и вторая compilation вне изменения.

Планируемые файлы: `images/java25-grader/grade.py`, `tests/test_gradle_build.py` (адресные регрессии и существующие security checks), `runtime-profiles.json`, `tools/prepare_native_toolchain.py` (точный новый runner pin). Если понадобятся изменения installation source, их exact hashes обновляются вместе с существующим source inventory.

## Заранее выбранные проверки

1. Адресные unit cases допуска metadata и native artifact transfer/drift, role/process cleanup, containment, runtime closure — без installed JDK hashing и Docker.
2. `py_compile` изменённых Python файлов, проверка соответствия runner SHA256 в локальных pins.
3. Один доступный host JDK25 source/runtime route: существующие tests успешного JUnit job, student compilation failure, trusted compilation failure; это ограниченная проверка настоящего `--release 25`, не image/OCI приёмка.

Полные image/course/CI matrices, сборка Docker/JDK и повтор опыта R4 запрещены brief и не планируются.

## Первичный допуск

Собственный чат `01a1a1c5-99bf-7fd0-aa80-ba71eccbe115`, путь `/root/release_grading`; родитель `01a1a142-7adc-7371-9765-4871d807dfcd`. Назначение: `gpt-6.1-sol` / `high`, Standard. Первичный источник: `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T17-17-24-01a1a1c5-99bf-7fd0-aa80-ba71eccbe115.jsonl`, `session_meta` ordinal 0 и все текущие `turn_context` от исходного хода `01a1a1c5-9a03-75f2-b423-8f64962a9775`. Верхнеуровневые `model`/`effort` и вложенные `collaboration_mode.settings` совпали с назначением; проверено 1 записей. Первичный допуск пройден. Глобальные настройки не менялись; скрытая серверная маршрутизация этим не подтверждается.

Допуск перенесён из нормативного `java/docs/model-admission.md` по указанию координатора; собственная добавленная секция полностью удалена из нормативного текста.

## Реализованный diff

Изменены ровно четыре файла Platform: `images/java25-grader/grade.py`, `tests/test_gradle_build.py`, `runtime-profiles.json`, `tools/prepare_native_toolchain.py`. Патч сохранён рядом: `platform-grading.patch`.

- Из `_run` удалены два полных boundary на каждую hardened JVM. Environment isolation и управление/очистка process group сохранены.
- `grade` после role remap выполняет один `_admit_java_job`: metadata inventory complete319 JDK и Gradle716, root-owned безопасные parents, single-link regular files, точные размеры/режимы/состав; для hardened JDK — существующие пять critical files/parents и отсутствие management JNI, без открытия/чтения core bytes. Штатные symlink aliases original hardened JDK не приравнены к normalized /opt/native-jdk.
- Готовый toolchain lock передаётся только внутри текущего grade вызова в `_native_build`; глобального кэша нет. `intact` больше не вызывает full toolchain/boundary. Сохранены проверки источников, конфигурации, recipe, copied input, artifact transfer и последующего artifact drift.
- После фактической Gradle build `finish_role` сначала очищает nativebuild descendants, затем проверяет actual roles, private build root, отсутствие build-процессов и пять фактических sbuser access denials. Изменившиеся roles останавливают передачу artifact.
- Удалены per-job `gradle --version` и его private log, а также повторный finish preflight. Первоначальные JDK25 runtime/compiler version checks и настоящий `--release 25` сохранены.
- Полная сверка bytes действительно остаётся в вызываемой Dockerfile цепочке: `install-native.py` сверяет ZIP/Gradle и normalized JDK inventories; `complete-native.py` проверяет completed319 и вызывает `_hardened_tool_state()` с default `verify_bytes=True`. Installation scripts и их существующие inputs не менялись.
- Оба Java runner pins и preparation RUNNER обновлены на `d25691ae4c31afe29445b18f9e012890b798e5d4c9717ddae0bb6395f14c6b57`.

## Проверки и наблюдавшиеся ошибки

Первичная новая metadata regression до реализации остановилась на отсутствующем `verify_bytes` параметре; после реализации проходит. Семь исходных адресных checks прошли. Первый расширенный запуск 24 tests внутри sandbox имел 7 errors в existing `safe_parent('/')`: корень отображался UID65534 при euid1000; это мешало registry/preparation fixtures. Однократный скрипт обновления pins также остановился на отсутствующем необязательном `runner` поле; исправлен выбор Java profiles, частичных изменений не оставлено.

Разрешённый exact запуск вне sandbox UID mapping: 27 tests, OK, 5.073s. Набор: InstallationMetadataAdmissionTests; NativeArtifactUnitTests; NativeClosureTests; NativeContainmentExecutableTests; SeparateNativeRolesTests; HardenedCoreSelectionTests; NamespaceBoundaryTests; BuildTransitionCleanupTests; CopiedInputBindingTests; GradingRuntimeTransitionTests; RecipeMembershipTests.test_omitted_recipe_pins_fail_before_any_java_or_gradle_child; PreparationBoundaries.test_baked_views_have_exact_three_profiles_and_actual_dependency_pins; три JUnitJob cases `test_existing_junit_suite_runs_without_course_main`, `test_student_compile_error_is_not_infrastructure`, `test_trusted_compile_error_is_infrastructure`.

После исправления admission original hardened aliases и удаления устаревшей тестовой version/verify обвязки повторены только первые 24 адресных unit tests: OK, 0.285s. Новый counterexample изменившихся roles отказал до artifact handoff; metadata tests запрещают открытие/чтение установленных bytes, отвергают writable files, symlink/hardlink и metadata drift. Существующие core byte-hash checks остаются проверены на малых fixture files. Три реальных JDK25 сценария повторно не запускались: последняя правка касается official-only admission и unit fixtures.

`python3 -m py_compile images/java25-grader/grade.py tests/test_gradle_build.py tools/prepare_native_toolchain.py` — OK; `git diff --check` — OK; сверка exact runner pins — OK.

## Ограничения

Новый image не собирался и OCI digest не получен; существующие image pins не выданы за новый runtime. Docker/native Gradle runtime smoke не запускался: подготовленного image в clone нет, brief запрещает новый build ради smoke. Результат подтверждает source/unit поведение и один ограниченный host JDK25 route; actual official role transition проверен doubles/fixtures, не новым контейнером. Нужен независимый review координатора до публикации. Это отчёт исполнителя, не независимая приёмка.

Полный интервал собственных turn_context от исходного turn `01a1a1c5-9a03-75f2-b423-8f64962a9775` перепроверен перед передачей: 1 записей, верхнеуровневые и вложенные model/effort совпали с `gpt-6.1-sol`/`high`.

Commit исполнителя: `0e4a12d` (`codex/java-release-20261110`). Push/PR не выполнялись.
