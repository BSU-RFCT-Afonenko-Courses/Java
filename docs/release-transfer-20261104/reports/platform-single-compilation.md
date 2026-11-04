# Один используемый результат compilation: предложение до продуктовой правки

Исполнитель `/root/platform_single_compilation`, clone `/home/tolya/JavaCodex/java-release-20261110/platform`, branch `codex/java-release-20261110`, исходный HEAD `d14231d0129ca065aa12414e6b4c49c376d72a2a`. Прочитаны brief, Platform `AGENTS.md`, общее русскоязычное правило, R4 `cross-check-r2-r3.md`/`native-build-cost-note.md`, текущие `_execute`, `_native_build`, native recipe и адресные tests. Новых experiments/benchmark/build запусков нет. Platform Source пока не изменён.

## Первичный допуск

Собственный чат `01a1a1fb-6d1d-7760-9a87-e2f25f95f8ef`, исходный turn `01a1a1fb-6d49-7130-b885-1043eed45e08`, путь `/root/platform_single_compilation`, родитель `01a1a142-7adc-7371-9765-4871d807dfcd`. Назначение `gpt-6.1-sol` / `high`, Standard. Первичный источник: `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T18-16-11-01a1a1fb-6d1d-7760-9a87-e2f25f95f8ef.jsonl`, `session_meta` и все текущие `turn_context` (1 запись от исходного turn). Верхние model/effort и вложенные `collaboration_mode.settings` совпали. Допуск пройден; полные выбранные поля сохранены в `platform-single-compilation-admission.json`. Нормативный `java/docs/model-admission.md` не менялся; глобальные настройки не менялись. Первичные записи не доказывают отсутствие скрытой серверной маршрутизации.

## Один минимальный путь для решения координатора

Оставить единственным production compiler существующий настоящий JDK25 javac (`_JobRuntime.compiler`, `--release 25`, `-proc:none`, UTF-8). Единственный результат — свежий private `stage/student`. Независимый API precheck и trusted/JUnit compilation/execution используют этот каталог, как сейчас. Его содержимое передаётся supervisor-ом в read-only каталог native workspace; fixed Gradle recipe выполняет настоящий `clean jar` из этих готовых production classes, без повторного `compileJava`. Получающийся настоящий `build/libs/<artifact>` сохраняет имя, optional Main-Class, reproducible settings, transfer/buildEvidence и доступен прежним JAR-case тестам. Gradle по-прежнему реально исполняет штатную JAR task, не подменяется zipfile или фиктивным evidence; учебные присланные Gradle scripts по текущему контракту и сейчас не исполняются.

Ветви уже различаются закрытым runtime контрактом: `java25-junit-v1` не имеет `javaBuild` и native build вообще; `java25-gradle-junit-v1` обязательно имеет `javaBuild.kind=gradle-jar-v1` (`validate`, строки 748–775). Выбор профиля, API, случаи и баллы не меняются. Самостоятельный IDE-проект от grading runtime не зависит.

Student compile error остаётся ранним `student-compilation-failure`, score0, API/dependent tests/native build не запускаются. API mismatch остаётся ранним student0 с `dependentExecution=not-run` и buildEvidence `not-run`. Trusted compilation/tool/Gradle packaging failure остаётся infrastructure/noScore. Порядок API до предметных tests и до Gradle сохраняется. Не требуется классифицировать текст Gradle compiler errors, переносить API за build или менять негативные grading receipts.

Build-role containment сохраняется: nativebuild получает только объявленные copied inputs/recipe и готовые classes, не private trusted sources/API result/runtime/compiler.jsa. Stage/runtime закрываются перед build, отдельный UID и actual Landlock/reaper/role transition действуют как раньше; исходный `stage/student` не доступен на запись nativebuild. JAR переносится только после cleanup build-role processes и прежних artifact checks. Формулировка README должна прямо назвать javac результат и Gradle packaging.

## Точное условие совместимости

Текущий README обещает fixed Gradle сборку из production sources и отсутствие внешних production dependencies; recipe сейчас компилирует их с пустым external classpath, тогда как ранний javac использует `runtime.libs` (JUnit/framework). Поэтому бездумное включение готовых classes в JAR расширит принятие production кода, импортирующего framework: раньше второй Gradle compiler завершался infrastructure failure. Также по буквальному прежнему recipe nativebuild сам выполнял production compilation. Сохранить одновременно именно вторую Gradle compilation и исключить повторную compilation невозможно.

Координатору нужно подтвердить внутреннее техническое толкование текущего учебного договора: Gradle/JAR-case требует настоящий Gradle JAR с той же проверенной implementation, а не повторную ownership compiler task. Для отсутствия external production dependencies предлагается native первый/единственный javac компилировать без `runtime.libs`; API/trusted/JUnit classpath оставить прежним. Это сознательно делает недопустимый framework import ранним student compile error вместо прежнего позднего trusted build failure; такую перемену compile/tool semantics без решения координатора не делаю. Если она недопустима, остаётся точное противоречие classpath двух прежних compiler stages; независимый безопасный фрагмент — только явное связывание JAR и прежних compiled classes, однако само по себе оно не снимает вторую compilation.

## Граница последующего diff и проверок

После решения координатора: `grade.py`, fixed Gradle recipe/README, адресные native unit regressions, необходимые существующие runner/recipe source pins. Image/server/Java/course pins, assessment cases/points и assertion Transform вне области.

Проверки только адресные: существующие real host JDK25 success/student compile/API mismatch и trusted failure; unit проверка передачи готовых classes, запрета повторного compiler, настоящего Gradle/JAR contract в recipe и сохранённых nativebuild cleanup/artifact transitions. Полного CI/image build/667 tests/benchmark нет. Actual native Gradle route возможен только при уже доступном prepared runtime; его отсутствие не заменяется новым image build или вымышленным доказательством.

## Принятое решение координатора и основание

Координатор подтвердил описанный минимальный путь: настоящий javac → `stage/student` для API/JUnit; существующий native Gradle выполняет настоящий `clean jar` из read-only копии тех же classes. Это внутреннее уточнение fixed grader recipe; самостоятельный Gradle-проект и настоящие GradeApp/JAR-case проверки сохраняются. Текущий README прямо сообщает, что присланные Gradle scripts не исполняются и их написание не оценивается; новый текст описывает фактическую compiler/packaging границу.

Для **только** `java25-gradle-junit-v1` единственный student javac использует прежний production classpath fixed Gradle, без внешних dependencies. Конкретных объявленных внешних production dependencies в прочитанном recipe нет. Несовместимый native import становится существующим ранним student compile outcome; это исправление классификации фактической compile error, без новых баллов и пересчёта прежних ответов. Обычный `java25-junit-v1` и trusted/test JUnit classpath сохранены.

## Реализованный diff

Изменены семь файлов: `images/java25-grader/grade.py`, fixed `runtime/gradle-jar-v1/build.gradle` и его README, `tests/test_gradle_build.py`, существующий actual recipe fixture `tests/test_gradle_library.py`, два source runner pin места `runtime-profiles.json`/`tools/prepare_native_toolchain.py`.

- Native sole javac использует свежий пустой `stage/student` как classpath. Это запрещает fallback javac на cwd и не подключает framework libraries. Java compiler version/options, source selection и ранний API gate не менялись.
- Supervisor копирует все actual compiler `.class`, включая packages/nested classes, в root-owned `production-classes`: files0444, directories0555. Original stage/runtime закрыты до nativebuild; trusted code, compiler.jsa и API results не передаются.
- Fixed recipe направляет `sourceSets.main.output.classesDirs` на `production-classes`, отключает `JavaCompile`, не принимает resources/student scripts. Настоящий Gradle `clean jar`, optional Main-Class и reproducible settings сохраняются.
- Class bytes входят в существующую admitted/copy проверку на передаче workspace. После настоящего Gradle и прежнего build-role cleanup единственная новая предметная сверка на build→grading границе требует ровно Manifest и полный набор тех же `.class`, без дублей и изменения bytes. Проверки immutable classes в later `verify()` не добавлены. Обычные artifact transfer/drift checks сохранены.
- API/JUnit продолжают читать original `stage/student`; JAR-case получают настоящий `job/build/libs/<artifact>` той же implementation. Student compile/API failures не запускают build/dependent tests. Gradle/trusted/tool failure остаётся infrastructure/noScore.
- Runner pin в обоих Java profiles и `RUNNER`: `a529996a56d9e5906e8b7655667cee133eacddb16c07bc27164ff0c4c684c4af`. Recipe SHA вычисляется существующим baked-view builder, нового pin-протокола нет. Image/server/Java/course pins и оценки не менялись.

## Конкретные проверки, ошибки и стоимость

Сначала новые regressions запущены до product правки: class handoff ожидал два actual copied файла и получил пустой набор; native framework import вместо ожидаемого student compile outcome дал прежний infrastructure failure. После правки все шесть новых/расширенных адресных cases прошли (4.876s). Временное удаление JAR→class binding отдельно доказало, что counterexample чужой implementation падает с `GraderError not raised`; после восстановления этот case прошёл (0.010s).

Первый host запуск внутри sandbox остановился ещё до compilation в existing `safe_parent('/')`: sandbox отображал `/` как nobody при euid1000. Точные JDK25/registry команды повторены вне этого UID mapping, без изменения production проверки. Это ограничение окружения, не маскировалось тестовой подменой identity guard.

Один **настоящий host JDK25 + уже установленный Gradle9.8.0** success route: `NativeCompilationTests.test_single_compilation_supplies_api_junit_and_actual_gradle_jar`, OK,9.130s. Настоящие javac `--release25`, API precheck, fixed Gradle recipe/JAR и canonical JUnit; наблюдались ровно один production compiler invocation, `:compileJava SKIPPED`, настоящая `:jar`, JUnit executed1/success и запуск resulting executable JAR с output42. Только official account/namespace/reaper boundary заменён doubles; тест не выдаётся за actual OCI/native containment admission. Новых installs/image build не было.

Дополнительно 27 адресных tests выполнились успешно в одном запуске за13.013s: NativeArtifactUnitTests (class handoff, altered JAR, source/recipe/copied-class drift, role drift), CopiedInputBindingTests (включая source/recipe substitution и unselected input), GradingRuntimeTransitionTests, BuildTransitionCleanupTests, SeparateNativeRolesTests, NativeClosureTests, RecipeMembershipTests, native import vs ordinary JUnit control, existing actual student compile/API negative/no-dependent tests, native uncontained build infrastructure outcome, actual JDK25 student/trusted compile failures. Сам unittest launcher завершился с одной дополнительной ошибкой: неверное имя module `test_prepare_native_toolchain`; это не скрыто. Исправленная отдельная команда `test_native_toolchain_preparation.PreparationBoundaries.test_baked_views_have_exact_three_profiles_and_actual_dependency_pins` — OK,0.072s. Уже прошедшие27 tests не перезапускались без причины.

`python3 -m py_compile` всех четырёх изменённых Python файлов и `git diff --check` — OK. Exact runner pin/baked recipe closure проверены существующим preparation test. Existing `ActualTrustedRecipe` helper адаптирован для sole javac перед Gradle; два его snapshot-dependent scenarios дополнительно не запускались — success маршрут проверен одним новым bounded host case.

## Ограничения результата

Это source change и конечные локальные checks. Реальный Gradle startup в native ветви остаётся;9.130s — длительность одного проверочного сценария, не benchmark/обещание ускорения курса. Нового OCI image/digest нет; server/installed runtime не менялись. Official Landlock/DAC/reaper переходы в новом success тесте не воспроизводились, их адресные regressions используют прежние process/identity fixtures. Полного CI/image build/667 tests, новой матрицы/benchmark и redesign assertions/feedback не было. Push оставлен координатору после независимого review.

Полный собственный интервал повторно проверен от того же исходного turn:2 `turn_context`, верхние и вложенные `gpt-6.1-sol`/`high` согласованы. Commit исполнителя: `503e0cc` (`codex/java-release-20261110`), полный SHA `503e0ccb61ab5460fe0433fa0d13d623e1e6ffef`. Patch: `platform-single-compilation.patch`. Working tree после commit чистый. Push/PR не выполнялись.
