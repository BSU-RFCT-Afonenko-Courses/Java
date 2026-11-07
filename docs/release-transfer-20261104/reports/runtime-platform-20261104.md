# Platform: пакет исполнения 4 ноября

Исходный HEAD `6491517b896b17119fd2ab44db1a2de2ff9c93b1`, dirty4 передан исполнителю. Начальный diff сохранён координатором в `runtime-initial-20261104/platform.patch`. Выбранные настройки сессии не являются attestation runtime модели.

## План и границы

1. Удалить собственные runner/helper/JDK/Gradle/source/JAR inventories, toolchain lock, preparation и обязательные readers. Оставить штатные OCI digest/source commit и реальные filesystem/UID/network/процессные границы.
2. Docker собирает поставку JDK25/Gradle; Gradle компилирует production ровно один раз и предоставляет обычные `run`, `test`, `jar`. Grader переиспользует полученные classes для приватных API/JUnit; скрытые tests/cases/веса не меняются.
3. Удалить executionEvidence, buildEvidence оставить только полезным результатом сборки (status/path/bytes/duration), привести grading_job и JSON schemas. Не переписывать course delivery SHA: этот downstream пакет ждёт решения Gateway у координатора.
4. Удалить тесты собственного inventory/preparation устройства; оставить и добавить публичные проверки результата, student compile/runtime failures и неисправности инструмента. Independent review организует root. До него commit запрещён.

## Конкретный пакет JUnit producer/reader

Producer: `PlatformJUnitAdapter` перестаёт отражением вызывать upstream `JUnitAutograder/saveResults`; JUnit `LauncherFactory` запускает тот же набор selectors, а штатный `LegacyXmlReportGeneratingListener` пишет XML отчёты. Наш listener сохраняется только для полезных assertion checks, public observations и classification с проверкой фактического CodeSource student/console classes. JSON больше не является источником статуса/счётчиков/оценки.

Reader: `runtime/junit_result.py` читает штатные XML, связывает testcase с JUnit unique-id, проверяет завершённость и сопоставляет trusted feedback по unique-id. Полные throwable/stack не попадают в публичную обратную связь. Пропавший/повреждённый отчёт, container failure, skipped/aborted trusted case — инфраструктура, а не 0. `AssertionError` непосредственно student origin остаётся runtime failure; trusted assertion остаётся wrong-answer. Сначала реализуется и проверяется этот конкретный пакет, затем старый JSON producer удаляется.

## Пределы проверки

Никаких общих baseline/matrices, course builds и публикации. Целевая локальная JDK25 проверка новых public tests: общий cap 120 секунд, отдельные команды с timeout, не больше одного нового Docker smoke с cap 90 секунд после доступности Docker. Старые Performance/Native бюджеты не используются и не обнуляются. Docker socket текущему пользователю недоступен; обычная local Python/JDK25 проверка возможна.

## Реализовано

- Удалены runner/files/sharedFiles inventories всех runtime profiles, `executionEvidence`, runtime dependency readers в Java/fish и `grading_job`, source/recipe/JAR hash proofs в grader, toolchain lock и оба host preparer. Удалены toolchain installer/completer/keyring/input inventories и заранее собранный helper binary/provenance. Native delivery source identity поля пока сохранены: этот отдельный lifecycle пакет принадлежит координатору.
- Dockerfile использует обычные multi-stage JDK/Gradle images и C compiler для helper. Новый Source candidate не собран и не опубликован. Текущий supported Gradle image tag `9.8.2-jdk25-noble` проверен по официальной странице Docker Hub; фактическая установка/OCI smoke ещё не подтверждены. Локальный установленный Gradle: 9.8.0, JDK:25.0.4.1.
- Gradle `compileJava` включена и является единственным production compiler для native профиля. `jar`, `run`, `test` обычные, test не disabled. Native API/JUnit получают classes той же Gradle compilation; отдельной javac production compilation больше нет. Сохраняются private source/test paths, UID/GID/network checks и cleanup build-role descendants.
- Штатный JUnit `LegacyXmlReportGeneratingListener` производит XML. Reader получает outcomes/counts из XML; ancillary feedback сохраняет checks, expected/actual, declared points/AutograderInfo/maxpoints, public observations и student/console exception origin. Динамические/параметризованные случаи используют JUnit unique-id; веса/учебные cases не менялись.
- `buildEvidence` содержит только build status, artifact path/bytes и duration; private Gradle log сохраняется. Mandatory local schemas/readers приведены. Release assembly/publication/workflows/соответствующие schema/tests по согласованному разделению изменил root.
- Старые тесты ручных inventory/preparation/proof удалены. Полезные schema no-score/compile/API guards сохранены с новым именем `test_result_admission.py`, role/process guards — `test_native_boundaries.py`. Подготовлен `test_plain_image.py` для CI на собранном `PL_TEST_IMAGE`.

## Исправления независимой рецензии

1. Малформированный generated feedback ([]/пропавшие cases/нечисловой weight) больше не роняет процесс исключением Python: единственная граница parse переводит их в ValueError → GraderError → infrastructure/no-score. Суммарные XML failures/errors/skipped сопоставляются с testcase; finite numeric weights проверяются на входе отчёта. RED4 tests7failures4errors → GREEN4.
2. Первоначальный XML/feedback candidate позволял студенту найти reports через CodeSource, записать forged XML+JSON и завершить JVM с code0. Этот промежуточный candidate не принят. Launcher/listener перенесён в существующий named module `org.prairielearn.feedback`; private state закрыт от student reflection. Ключ аутентификации результата одноразовый, только для job; startup params удаляются до загрузки student classes. HMAC подписывает ancillary payload с фактическим закрытым event ledger; XML outcomes должны совпадать с этим authenticated ledger. Это аутентификация канала результата, а не content inventory или toolchain proof. Прямые lifecycle callbacks от student отвергаются по фактическому engine→main stack. Unsafe memory access запрещён JVM flag, чтобы не обойти private module boundary.
3. Регрессии содержат реальную попытку forged XML/feedback + System.exit(0), student reflection к private listener state, malformed XML/JSON/aggregates и authenticated producer с недопустимыми assertion checks. Все соответствующие адресные проверки прошли.

## Фактическая проверка

- Final affected authentication/runtime/XML/mutation пакет: **14 tests PASS,27.718s**, actual JDK25; включает public runtime8, pure XML boundary4, один real producer +8 corrupt reader variants, mutation fixture isolation.
- Дополнительная граница module reflection и authenticated malformed check: **2 tests PASS,5.933s**.
- Ordinary offline Gradle `jar run test` + запуск полученного JAR: **1 test PASS,7.215s**. Первая sandbox попытка отказала из-за loopback socket Gradle; один разрешённый локальный offline повтор прошёл. Dependency downloads не запускались.
- No-score/result schema, UID/process guards и actual-produced XML corrupt cases: **19 tests PASS,3.176s** до auth move; эти простые guards после него продуктово не менялись.
- `test_review_receipts.py`: **19 tests PASS,4.238s** после удаления runtime dependency closure reader. Publication/assembly consumer checks выполнял root отдельно.
- `git diff --check`: PASS. Source trace не находит mandatory toolchain-lock/preparer/runtime inventory readers.
- Новый Docker smoke: **NOT RUN** (socket недоступен обычному пользователю, полный build не выполнялся). Никакой image/runtime квалификации не заявляется.

Исходный cap local targeted verification120s не обнулялся. Консервативно учтено **~111.4s**, включая RED, промежуточный ошибочный5-test запуск, один Gradle loopback failure и auth repair. Остаток~8.6s; новых дорогих опытов не запускаю. Старые Performance/Native budgets не использованы.

Performance исполнитель отдельно получил на промежуточном XML correct42 candidate один complete запуск: Native2.999461143s exit0, outer3.216179068s, observation delta0.216717925s, CPU4.755215s, RSS137892KiB. Это измерение предыдущего candidate до named-module/HMAC correction; его нельзя приписывать финальному auth candidate. Evidence: `runtime-performance-20261104-evidence/platform-runs/68174101-d55f-40f3-b8a2-911541b10996/`.

## Остаток

Source candidate заморожен для independent review. Commit не сделан. Новый Docker image, фактическая root/roles/JDK/Gradle поставка внутри него и новый контейнерный smoke остаются непроверенными. Production/VM/live DB/auth/config не изменялись. Основной release state не редактировался.

## Продолжение заменяющего координатора, 17:49

Старый writer подтверждён completed/idle до новых правок. Пользователь прямо разрешил продолжать, локальный Docker и запуски. Независимая ordinary build/publication рецензия закрыла найденный P2 выбора JAVA_HOME в Gradle smoke; новый реальный offline запуск изменённого smoke1PASS6.859s с альтернативным JDK path. CI Temurin/image этим не квалифицированы. [Review](runtime-ordinary-build-review-20261104.md).

Отдельная ограниченная функциональная XML рецензия выявила и закрыла две регрессии: explicit `maxpoints` класса без прямых leaf tests терялся; успешная пустая factory/container в стандартном XML ошибочно давала infrastructure failure. Root исправил reader и обычную producer metadata successfulContainers, добавил regressions. Pure reader6PASS0.006s; настоящий JDK25 fixture correct42 + empty factory + empty DynamicContainer1PASS2.931s. Прежние RED и первая ошибка JSON serialization сохранены. Обе correction delta независимо прочитаны, новых findings в functional scope нет. [Подробный журнал и бюджеты](runtime-junit-functional-review-20261104.md).

Прежний полный reviewer остановлен системным `possible cybersecurity risk`, итогового full APPROVE нет; ограниченные reviews не заменяют его. Общий candidate остаётся dirty/uncommitted. Старый hostcap120 учтён~118.268s; отдельный заранее назначенный cap12 новой XML delta8.858s, затраты не обнулены. Docker после явного разрешения технически недоступен: socket permission denied, sudo -n password required. Image build/publication/deploy не выполнялись; system permissions/globals не менялись.
