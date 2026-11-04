# JDK official preflight — независимая рецензия

**APPROVE scoped Source candidate.** Конкретных блокирующих findings в пятифайловой дельте нет. Это принятие удаления одного per-job JVM-version процесса из already-admitted official image path, не actual image/JDK/production qualification.

## Состав и доказательства

Platform HEAD `9b82e29bffd77cc462ffdef23cd3b96ca041db92`. Прочитан exact diff пяти файлов (+32/-23): `images/java25-grader/grade.py`, `tools/prepare_native_toolchain.py`, `runtime-profiles.json`, `tests/test_gradle_review_fixes.py`, `tests/gradle_unit_fixtures.py`. Адресно прослежены grading entrypoint/admission/compiler/result branches, image Dockerfile и существующие install-native/complete-native. Прочитаны writer report/patch, первый failure JSON/log и final mock JSON/log. Source, tests, compiler/JDK/Docker/build/hash/network/production рецензент не запускал и не менял.

## Почему infrastructure mismatch не просто передан student compiler

1. `grade.py:2647–2651`: official grade вызывает `_protect_input_identity`, затем `_admit_java_job()` **до preflight**, независимо от конкретного Java runtime profile.
2. `_admit_java_job():2020–2029` вызывает existing `_verify_native_toolchain`, `_hardened_tool_state(verify_bytes=False)` и `_grading_boundary`. Сохраняются installed admitted lock/release JDK25, root-owned non-writable paths/metadata/isolated roles, отсутствие build-role процессов и недоступность полного build JDK grading роли. Эти функции и порядок не изменены.
3. `toolchain/complete-native.py:35–40` завершает actual private JDK copy и вызывает `_hardened_tool_state()` с default `verify_bytes=True`. `grade.py:286–363` проверяет exact compiler-bearing `lib/modules` вместе с release/bin/java/bin/javac/libjvm против установленного admitted lock; parent/file ownership/modes также проверены. До этого `install-native.py:34–40,65–77` связывает normalizer/source/lock, actual inventories и completion. Dockerfile фиксирует digest base, копирует grade/baked view и запускает именно этот installer. Installer не запускает Java и не выдаётся за functional compiler smoke.
4. `_grading_java():160–164` official использует фиксированный hardened path. `_JobRuntime.compiler():959–972` использует тот же executable, actual `com.sun.tools.javac.Main` и прежние `--release 25/-proc:none/UTF-8` из COMPILER. Никакой host PATH fallback при official не добавлен.
5. `grade.py:594–598` сохраняет Landlock verification и первый actual `java -version` с JDK25 refusal. Изменено только условие вокруг второго `javac.Main -version`: он остаётся для host, а official полагается на уже установленный защищённый compiler payload.

Да, `_execute:1464–1471` по-прежнему превращает ненулевой student javac exit без infrastructure reason в student-compilation-failure. Поэтому удаление любых admission/runtime checks и ожидание ошибок компиляции было бы недостаточным. **Здесь такого более широкого удаления нет:** compiler payload и роли предварительно допущены существующим installer/job contract. Обнаруженный Source не показывает новый обход этого контракта. Существующий `GraderError`/infrastructure reason путь, trusted compiler errors и result classifications не менялись. Это аргумент для данного image ownership contract, не универсальная функциональная гарантия для произвольной root-modified установки. Изолированный `preflight()` сам `_admit_java_job` не делает; production entrypoint делает, а unit прямо тестирует только preflight.

## Поведение, pins и fixture

- Official теряет ровно один явный JVM startup по Source. Оставшийся first-version output и `return profile,bundle,output.strip()` в grade.py:616 неизменны. Скорость на actual image не измерена. Host второй process, его argv и diagnostic `actual JDK25 compiler` сохранены.
- Sole student compilation, compiler/source selection, scoring/goals/feedback, files, trusted/private boundaries, containment, API/native-build pipeline не изменены diff. Новый grading/config/status protocol отсутствует.
- Один предоставленный writer runner SHA `5023a63140a721f0b72dcd0eb04d3f2818f6907c58ddecaf0a49543e44e83e98` буквально совпадает с `prepare_native_toolchain.py:27` и обеими Java runnerSha256 в runtime-profiles.json. Existing baked_view:110–123 распространяет RUNNER на plain/mutation и derived native profile, сохраняет runner drift check. Independent hash повторно не вычислялся; это Source consistency с сохранённым writer one-hash evidence, не новая byte attestation.
- Image/baseImage/upstream refs, fish profile и published history не переписаны. Новый runner pin **не делает старый image digest новым образом**. Actual build/image functional compiler admission/OCI release остаются отдельным незакрытым этапом; совместимость старого published image с новым Source runner не заявляется.
- `tests/gradle_unit_fixtures.py:24` передаёт уже существующему `_fish_view` `read=lambda path:path.read_bytes()` только при создании synthetic registry. Это убирает неподходящий unit-fixture filesystem-owner admission host root, сохраняя прочитанные байты, dependency closure/digests и явный `unit.invalid` image marker. Production `_fish_view` default `read_regular`, safe_parent, installer/image checks не менялись. Callback не подменён глобально и не попадает в production call sites.
- В official test-only branch `_verify_landlock_helper` временно mocked; `_run` допускает только `[HARDENED_JDK/bin/java,-version]`. Это проверка branch/return semantics, а не свидетельство Landlock или actual JVM. Host branch возвращает `javac 24`, требует прежний GraderError и точный compiler-version argv. Изменён только один existing case, новых test frameworks нет.

## Сохранённый control и пределы

Первый запуск ERROR0.2417s: fixture registry до preflight вызывал `_fish_view` через real read_regular, safe_parent отверг filesystem identity `/`. Official/host assertions тогда **не достигнуты**. Этот факт не переименован в behavioral RED либо product failure.

После отдельно разрешённого test-only read-port correction тот же case PASS0.1933s (unittest body0.070s), final JSON/log согласованы. Всего0.4350s, две изменённые итерации, без unchanged retry. Реальные Java/Gradle/Docker не запускались; финальная проверка является mock behavior evidence. Она не проверяет actual installer, image/compiler functionality или published digest compatibility; Source review отдельно установил наличие prior admission chain. Новая широкая Docker/JDK matrix ради данного review не требуется и не запускалась.

Вердикт ограничен candidate Source; old image/deployment/student/history не объявлены обновлёнными. No edits/commit/push/state/production queries со стороны рецензента.

Own full-origin primary admission: 31 turn_context, строки8–4098, все top/nested gpt-6.1-sol/high; origin 01a1a25f-b0fb-7fd2-b244-83933af77834, latest 01a1a59d-3334-78f2-ae0f-c25f1abcb578. Globals не менялись; скрытый serving tier не утверждается; полный env/секреты не читались.
