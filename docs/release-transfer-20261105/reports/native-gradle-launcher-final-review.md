# Финальная независимая рецензия Gradle launcher

**APPROVE Source дельты относительно `b79291184a1c52bd1b2e59247b01d19171b16d0c` и bounded GO для проверенного cold Circle.** Область: `images/java25-grader/grade.py` — ранний daemon seed через JDK_JAVA_OPTIONS при сохранённом explicit JAVA_OPTS клиента; `tests/test_plain_image.py` — очистка только собственного временного bind. Подтверждённых новых ошибок не найдено. Source рецензент не менял. Владение возвращено исполнителю для commit.

Точный diff: `native-gradle-launcher-approved.diff`. Собственный первичный admission и весь исходный интервал от `01a1a890-9d74-7d92-a65c-eccacfb9ceab` повторены до роли и перед приёмкой: 10 turn_context, gpt-6.1-sol/high, Standard, PASS; chat `01a1a890-9d42-7772-bd99-f2f954898515`, `/root/platform_candidate_review`, [feedback-review-admission.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261105/reports/feedback-review-admission.json).

## Проверенный механизм и безопасность

Прочитаны `feedback-gradle-entropy-diagnosis.json`, exact JDK launcher/child probe `/tmp/native-gradle-entropy-launcher-probe-20261105.txt` и относящееся Gradle bytecode evidence. Предыдущий вариант задавал mutable java.security.egd в org.gradle.jvmargs: actual daemon argv его не содержал. Новая дельта удаляет это неэффективное раннее назначение и передаёт daemon seed через JDK_JAVA_OPTIONS. Gradle daemon наследует штатное environment; JDK учитывает его до explicit VM arguments. Explicit JAVA_OPTS клиента перекрывает daemon property только у клиента.

Exact JDK probe под той же nativebuild/Landlock цепочкой подтвердил client property=client.seed, child property=daemon.seed, SecureRandom startup115.131/116.999 ms, wall0.385629 s. Это самостоятельное первичное свидетельство precedence/inheritance, не новый полный Gradle benchmark. Свежие отдельные OS seed files mode400/owner nativebuild и cleanup остаются такими, как в предыдущей принятой Source дельте. Environment локален вызову nativebuild; global os.environ и hardened API/JUnit environment не меняются. Landlock, /dev/proc изоляция, network/CPU/memory/pids и compile/run лимиты не ослаблены.

## Подтверждённая actual квалификация

Точный локальный image:

`sha256:474e1845dc16e9a99e416896a83c1dc46a60b3fdc914603bc59067cc02e8f477`

Read-only docker image inspect независимо подтвердил этот ID и entrypoint `[python3,/platform/images/java25-grader/grade.py]`. Build log содержит тот же ID. Прочитаны qualification script, JSON/log и непосредственные job артефакты `/tmp/java-release-work-20261105/feedback-entropy-launcher-jobs/circle`.

- Docker command: networknone,0.9CPU,512MiB,pids128; новый собственный job из fixture student/tests, fresh Gradle directories по reviewed runtime.
- Реальный descriptor: compile30, run10, configured outer90, max output65536, networkingfalse.
- Приватный Gradle log: compileJava/classes/jar, `BUILD SUCCESSFUL in 28s`.
- Container-to-result wall: **45.785 s**, exit0; меньше configured outer90.
- Native result: behavior-failure, gradabletrue, **6 leaves /2 passed points/4 failed**, score **1/3**, как ожидается для выбранного Circle.
- В student JSON и container transport output нет private canaries и путей client.seed/daemon.seed.

Независимая сверка файловых артефактов — PASS,0.0013 s, `native-gradle-launcher-review-evidence.json`. Новые Gradle/JDK/Docker квалификационные запуски рецензентом не выполнялись; повтор baseline не нужен. Существующий successful actual45.785 s переиспользован после проверки его входов/выходов.

**Граница bounded GO:** доказано завершение именно этого cold Circle при указанном image и compile30, с наблюдаемым общим45.785<90. Qualification client использует subprocess watchdog120; принудительное прекращение outer90 данным успешным запуском не испытано. Live seed thread stacks/CPU после финальной правки не сняты; полное исчезновение fallback или универсальный процент ускорения не заявляются. Это не квалификация всего курса, всех native проектов, production или будущих образов.

## Очистка disposable test bind

В test_plain_image.py новый addCleanup регистрируется после TemporaryDirectory cleanup, поэтому LIFO сначала возвращает own self.job host owner, затем удаляет этот каталог. Docker sidecar запускает только `/bin/chown -Rh <host uid:gid> /grade`, networknone, mount ровно self.job из fixture. `-h` не разыменовывает symlinks; это не обход старых student volumes и не изменение пользовательских рабочих деревьев. Неуспешная очистка остаётся тестовой ошибкой через checktrue, не скрывается.

Прочитан `native-gradle-existing-image-test-green.log`: **1/1 PASS,17.693 s**. Предыдущий18.512 s прогон имел успешные assertions и ошибку удаления root-owned artifact directory; он не переписан в PASS. Нынешняя дельта относится только к fixture cleanup, production runner ею не меняется.

`git diff --check b792911 -- images/java25-grader/grade.py tests/test_plain_image.py` — exit0. Предыдущее ограничение «performance GO не дан» для первого неработавшего daemon-property варианта сохраняется исторически; настоящая запись даёт новый ограниченный GO только указанному подтверждённому Circle. VM, production, DB, deploy и image publication рецензент не затрагивал.
