# Обычная Java-привязка конкретного решения

`check-java-project.py` готовит вход существующего `java25-junit-v1`. Студенческие исходники компилируются отдельно; выбранная опубликованная API-предпроверка — отдельно без student classpath; затем обычная преподавательская Java-привязка и JUnit; после успешной компиляции запускается поведение. Platform, его jars и sandbox не заменяются. Host — диагностический запуск доверенных локальных fixtures; для недоверенной сдачи используйте официальный container.

```sh
python3 tasks/test-support/check-java-project.py \
  --project tasks/projects/ИМЯ --sources tasks/projects/ИМЯ/reference \
  --binding tasks/projects/ИМЯ/tests/bindings/reference \
  --work /новый/отдельный/каталог --platform /checkout/platform
```

`--backend container` использует локальный image966c, `--pull never`, `--network none`, отдельный пустой DOCKER_CONFIG, 512MiB/0.9CPU и штатные sbuser/Landlock. Другой локальный кандидат задают только exact `--image sha256:…`. Наличие image и Docker проверяется фактическим запуском; pull/login/публикации нет.

Источники — все `.java` внутри `--sources` с сохранением относительных путей. `--binding` содержит обычный teacher Java, связывающий **конкретное** решение с тестом. Это не обязательный класс/метод студента. Для иной структуры преподаватель пишет другую привязку; private/nested недоступное решение сохраняет ручную защиту. Не используйте подавление доступа и чтение private fields. Ошибка binding/dependent compilation обозначается `binding-setup-failure`, `manualRequired=true`, без score и без поведения. По ней нельзя автоматически решить, виноват ли студент или тест. Исходный Platform receipt сохраняется как `rawPlatformResult` и в `job/results/results.json`.

Файлы тестов: `tests/junit/public/*.java`, `private/*.java`; обычные teacher helpers — `support/*.java`. `--public` включает только public suites. `--only public/ExampleTest.java` повторяется, выбирает **файлы**, не методы; неизвестные имена, пустая область и unsafe paths отказывают до создания work. Support/binding не отфильтровываются. Нужен как минимум один реально выполненный JUnit test, disabled/aborted/incomplete — штатный infrastructure failure. Все assertions задают условие, а не точное совпадение с reference.

`--api-config /teacher/placement/config.json` необязателен, default отсутствует. Closed JSON: `{"files":["PublishedApi.java"],"entry":"PublishedApi"}`. Java files лежат рядом с config и проверяют только опубликованный API с учётом вручную выбранного размещения. Компилируются без student classpath и выполняются штатным precheck до binding. API mismatch при успешной student compilation — `student-api-mismatch`, без score, dependent not-run. Не используйте precheck, чтобы навязать свободные package/class/visibility/static/конструкторы.

Обычные compile/API/behavior/runtime-limit/infrastructure receipts различаются. Успешный запуск — факты поведения выбранной привязки; техника, объяснение и соответствие самой привязки решению требуют ручной проверки. Все решения со свободным API автоматически не принимаются.

Work должен отсутствовать и не пересекаться с project/source/binding/runtime/support; существующие каталоги не удаляются. Symlinks, скрытые Java path segments, слишком большие файлы отклоняются с причиной. Технические границы Platform manifest: до64 исходников и до64 trusted files, каждый до1MiB; превышение требует ручной поставки, а не изменения учебного условия. Общий runtime сохраняет `-proc:none`, `-sourcepath ''`, offline и guards. Receipt содержит exact before/after hashes; изменение источников исключает успех.

Публичный download содержит только `student/`, без binding/reference/private fixtures. Его сборка и точный native selection подтверждаются отдельными receipts проекта; этот runner сам не изменяет конфигурацию курса, QMD, CI или Moodle.
