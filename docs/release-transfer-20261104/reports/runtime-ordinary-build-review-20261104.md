# Независимая рецензия обычного Gradle и публикации — 4 ноября

Source review `/root/ordinary_gradle_publication_review`, Sol6.1/high dispatch, база `6491517b896b17119fd2ab44db1a2de2ff9c93b1`, dirty release checkout. Настройки dispatch не подтверждают скрытый runtime routing. Рецензент не менял файлы и не запускал тесты/build/image/сеть.

Область: `runtime/gradle-jar-v1/build.gradle`, `tests/test_plain_gradle.py`, `tools/publish-receipt.py`, `tools/build-course.py`, `tools/stage_courses.py`, `schemas/runtime-image-v3.schema.json`, `.github/workflows/check.yml`, `.github/workflows/publish-runtime.yml`, `docs/releasing.md`, `docs/host-validation.md`; удаление `tools/release_assembly.py` и calls. Сохранённые Platform/publication отчёты использованы как прежние evidence, без повторов.

**Найден и исправлен P2:** Gradle smoke перезаписывал `JAVA_HOME=/usr/lib/jvm/java-25-openjdk`, хотя workflows устанавливают Temurin через `actions/setup-java`. Этот локальный Debian path не гарантирован в CI. После подтверждения завершения старого writer координатор изменил только этот тест: используется переданный `JAVA_HOME`, при его отсутствии — JDK по разрешённому `javac`. Gradle languageVersion/release25 остались прежними. Повторная независимая Source рецензия ровно этого delta: замечание закрыто, новых замечаний нет. Фактический запуск CI Temurin остаётся непроверенным.

Других конкретных дефектов в просмотренном build/publication diff рецензент не установил. `build.gradle` использует обычные задачи и единый main output; удалены calls assembly и генерация toolchain-lock/runtime inventories в просмотренном scope; staging receipt gate сохранён.

Координатор проверил Python AST syntax исправленного файла и `git diff --check`: exit 0. Реальный Gradle не перезапускался, прежний PASS принадлежит прежней версии теста. Shared cap120 с прежним расходом около111.4 с не сбрасывался.

Дополнение после прямого разрешения пользователя продолжать: один новый offline запуск исправленного теста с JAVA_HOME по альтернативному symlink на установленный OpenJDK25 завершён **1 PASS,6.859с,exit0**. Проверены реальные jar/run/test и java -jar. До запуска cap8.6с записан в session state, повторов нет. Общий учтённый host budget~118.259с/cap120. [Log](runtime-gradle-home-20261104.log). Это новая адресная проверка изменённого теста, а не повтор неизменного baseline; CI Temurin/новый image не квалифицированы.

**Предел:** `grade.py`, JUnit adapter/result authentication/module/reflection/process containment явно исключены. Прежний полный reviewer остановлен системным фильтром `possible cybersecurity risk`; этот пакет не является повтором его работы или обходом отказа. Он не даёт final APPROVE всему Platform candidate. Commit/push/build/deploy/publication не выполнялись.
