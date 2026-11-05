# Независимая рецензия entropy для native Gradle

**APPROVE узкой Source дельты** `images/java25-grader/grade.py` относительно `b6e626de6cc6c3f87094114a2542fc441e5b07f0`. Подтверждённых ошибок безопасности, JDK semantics, очистки или выхода за согласованный scope не найдено. Рецензент Source не менял. Точный diff сохранён в `native-gradle-entropy-reviewed.diff`. Доказательство ускорения ниже рабочего compile budget эта рецензия не подменяет: фактическая новая Docker-квалификация выполняется отдельно.

Рецензент `/root/platform_candidate_review`, chat `01a1a890-9d42-7772-bd99-f2f954898515`. До роли и перед приёмкой проверен весь исходный model-admission interval от `01a1a890-9d74-7d92-a65c-eccacfb9ceab`: 8 turn_context, PASS gpt-6.1-sol/high, Standard. Свидетельство `feedback-review-admission.json`.

## Основание и границы изменения

Прочитан `native-gradle-diagnosis.json` и первичный байткод exact native JDK из `/tmp/native-gradle-seedgenerator-bytecode-20261105.txt`. Диагноз связывает реальную цепочку root grader → nativebuild/Landlock → Gradle CLI → single-use daemon → холодный compile/jar с двумя ThreadedSeedGenerator и отказом чтения `/dev/random`. В диагностическом, расширенном compile90 запуске seed threads вместе потратили 17.27351 CPU s; сборка заняла 44.782 s, OOM не было. Это наблюдение диагностики, а не мой повторный benchmark и не приёмка расширенных лимитов.

Новая дельта создаёт два отдельных файла по 65536 свежих байт из secrets.token_bytes, задаёт `-Djava.security.egd=<file URI>` клиенту через локальный JAVA_OPTS, daemon через org.gradle.jvmargs. Никакие сетевые, CPU/memory/pids, compile/run или Landlock ограничения в diff не изменены. Hardened grading JDK, API/JUnit flags и глобальный os.environ не изменяются. Nativebuild environment остаётся локальным аргументом ровно своего вызова _run.

## Безопасность и очистка

Файлы создаются supervisor через exclusive `xb` внутри нового TemporaryDirectory до запуска Gradle. На записи установлен mode600, затем owner меняется на проверенный uid nativebuild и mode становится400. Sbuser не получает read/write permission. Клиент и daemon имеют разные файлы; следующая job создаёт новое имя каталога и новые независимые OS bytes. Чужой существующий файл не перезаписывается.

Рабочий каталог уже имеет sticky mode1777; он создан supervisor и до начала роли проверено отсутствие прежних nativebuild процессов. Seed files не включаются в student output или artifact. После _run существующий finally завершает процессы nativebuild и повторяет grading boundary до выхода из TemporaryDirectory. Как успешный путь, так и исключения при подготовке/запуске/чтении проходят очистку контекстного менеджера. Отказ подготовки остаётся инфраструктурным, не student zero.

## Проверка JDK semantics

Exact диагностический байткод показывает: SunEntries.getSeedSource → URLSeedGenerator для непустого file URI, отличного от специальных `/dev/random` и `/dev/urandom`. Дополнительно прочитан локальный JDK25 URLSeedGenerator: он открывает file URL через FileInputStreamPool и последовательно читает seed bytes; при EOF выдаёт InternalError, не переходит незаметно на фиксированные либо повторяемые байты.

Независимый адресный probe настоящего локального JDK25:

- создан свежий 64 KiB OS-random файл mode400;
- запущена JVM с java.security.egd=file URI;
- SHA1PRNG.generateSeed(32) вернул те же первые32 байта файла, без публикации самих байт;
- диагностическое чтение SeedGenerator.instance подтвердило URLSeedGenerator, не ThreadedSeedGenerator;
- временный каталог после выхода отсутствует.

**PASS, 1.3264 s**, результат `native-gradle-entropy-jdk-probe.json`. Диагностический `--add-opens` применён только в этом внешнем локальном probe, в продуктовую дельту не входит. Чтение локального URLSeedGenerator через javap заняло около0.645 s вместе с чтением сохранённых файлов. `git diff --check b6e626d -- images/java25-grader/grade.py` завершился 0.

64 KiB — конечный seed stream для доверенного фиксированного Gradle-процесса, а не замена неограниченному системному генератору для произвольных приложений. Ни положительный запас его объёма на весь курс, ни actual CPU/time улучшение из одного локального semantics probe не выводятся. Фактическая новая image-квалификация должна подтвердить исходные рабочие0.9CPU/512MiB/compile30 условия и отсутствие fallback/seed exhaustion на проверяемой цепочке. Старые успешные matrices и контейнеры рецензент не перезапускал.

## Передача

Владение Source возвращено исполнителю для commit и завершения квалификации фактического image. Source approval не означает приёмку Docker performance/UI/production. VM, production, DB, deploy, image publication, существующие контейнеры и volumes этим review не изменялись.

## Дополнение: Native transport и перенос пояснений

По последующему поручению прочитаны узкая дельта `runtime/student_feedback.py`, regression additions в `tests/test_feedback_leaf_context.py` и изменение `docs/student-feedback.md`. **APPROVE этой Source дельты**, включая последнюю корректировку null actual. Подтверждённых новых ошибок score/unknown/privacy либо сохранности предметного текста не найдено. Совместный reviewed diff относительно b6e626d сохранён в `native-entropy-ui-approved.diff`.

Unscored public context не создаёт records tests[] без points/max_points: для compile/not-run/infrastructure/unknown он переносится в top-level message. Когда есть complete leaf results, контекст не строится второй раз и присоединяется к одному подтверждённому leaf либо остаётся единожды в основном сообщении. Scored legacy/Fish cards сохраняются. Эта форма не подменяет отсутствие исполнения выдуманными баллами.

_prose переносит только общие reason/observed/whatCheck/statusReason и compile-error explanation; input/state/action/expected/actual и comparison/diff fragments не проходят textwrap. Независимые пять projection probes: compile, infrastructure, partial bindings, unique leaf, legacy Fish — **PASS за0.0232 s** (`ui-transport-wrap-review-probe.json`). Проверены отсутствие дополнительных scored cards, отсутствие score при infrastructure, ровно одно отображение public context, неизменные input/expected с ведущими пробелами, TAB, CRLF/LF и длинными строками. diff --check — 0.

Последняя правка не пишет «Получено: не получено» в passed/failed context при необязательном reporter actual=null: она лишь опускает эту строку. API поле actual остаётся null, никакое assertion actual не заимствуется и не приписывается public observation. Настоящее assertion actual ниже сохраняется в checks. Для unknown/not-run явное «не получено» остаётся. Независимый probe подтвердил все эти свойства и неизменные score/points: **PASS за0.0111 s**, `ui-executed-null-actual-review.json`.

Дополнение не является собственной визуальной приёмкой браузера. Сообщение исполнителя о продолжающемся compile30 timeout новой entropy image (31.48 s) сохранено как **нерешённый performance результат**: Source approval механизма entropy не означает, что рабочий бюджет уже соблюдён. Дальнейшая диагностика actual flags/seed threads принадлежит отдельной работе. Никакие лимиты рецензент не увеличивал.

Полный исходный model interval повторно проверен перед передачей: 8 turn_context, gpt-6.1-sol/high, Standard, PASS. Код рецензент не менял. Владение исходниками возвращено исполнителю.

Финальная последующая launcher/env поправка и новый ограниченный actual GO оформлены отдельно в `native-gradle-launcher-final-review.md`. Эта новая приёмка относится к image474e1845… и одному cold Circle; прежние неуспешные compile30 наблюдения остаются неуспешными.
