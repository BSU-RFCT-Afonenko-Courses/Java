# Согласованный релиз курса Java Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use
> `superpowers:subagent-driven-development` or `superpowers:executing-plans`
> to implement this plan task-by-task. Steps use checkbox syntax. This document
> authorizes no execution by itself; the current request is planning and research.

**Goal:** Удалить ручную поставку PrairieLearn и получить проверенный путь от
авторского курса до сайта, архивов, нативной платформы и результата в Moodle.

**Architecture:** Core нормализует учебную модель; Download и PL exporter
используют её. Platform владеет одним JUnit grading engine и локальным checker.
Общий project-check явно подключает проверку проекта независимо от экспорта.
Gateway ведёт runtime identities/назначения и связывает Moodle с Community PL.

**Tech Stack:** Native Quarto 1.11.5+, CUE 0.17.1+, Lua, TypeScript/Deno,
Java 25/JUnit, Gradle для разработки, Python tools платформы, Docker/Compose,
PrairieLearn и Moodle с закреплёнными версиями.

**Spec:** [Требования к согласованному релизу](release-requirements.md).

## Global Constraints

- Исполнение начинается только после проверки этого плана; сейчас не удалять
  курсный `prairielearn/`, не редактировать owners и не поднимать интеграционный стенд.
- Обновлять owners от новейших PR с точными SHA; gateway начинает первый PR от
  проверенного main, поскольку существующего PR у него нет.
- Не менять vendored `_extensions` вручную и не создавать курсный substitute exporter.
- Все native PL файлы генерируются расширением; QMD и Java assets остаются источниками.
- Сохранить пользовательские изменения, особенно `lectures/04-development.qmd`,
  и незакоммиченные новые каталоги. Старый HEAD не является полным baseline курса.
- Source bank, IDs, назначения, темы, время и visibility сохраняются, если
  отдельное утверждённое изменение не требует миграции.
- Разметка остаётся нейтральной к enrollment/self-selection/approval.
- Проверяются только явно объявленные project-check. Для PL export профиль
  обязателен; manual/demo без профиля исключены. Все эталоны и starter внутри
  выбранного scope проверяются. Универсальный score 0/1 не вводится.
- Реализуемость, test coverage, scoring policy и технический успех job — разные выводы.
- Эталон/private tests обычных задач не входят в student delivery; открытая
  demonstration выдаёт полный проект и в student. Runtime keys всегда вне сайта.
- Обычная HTML-сборка не запускает все executable projects автоматически.
- Fresh docs: требования и план; завершённый релиз добавляет один текущий отчёт.
  История сохраняется Git, без `docs/history` и копий прежних receipt деревьев.

## Review Focus

1. Смешанный QMD под default каталога: target имеет строгий конфликт;
   явная visibility переопределяет default; адаптеры используют одинаковый результат.
2. Reference проходит, но empty/no-op implementation тоже получает полный балл:
   проверка реализуемости должна выявить недостаточность grading suite.
3. Starter имеет passing API/public tests: технический успех не означает ни
   полного решения, ни ошибочности ненулевого балла при weighted policy.
4. Cold javac на 0.9 CPU: время запуска и инфраструктурный timeout не должны
   ошибочно становиться провалом студента или успешным пропуском тестов.
5. Pending/rejected self-selection и прямой URL: доступ определяется сервером,
   а Moodle result не может уйти другому пользователю или другой delivery version.

Каждый пункт покрывается named проверками в соответствующей фазе ниже.

## Принятые решения и примеры конечного формата

Это контракты будущего релиза, а не уже поддержанный установленными пакетами
синтаксис. Implementer сначала добавляет их в owner schemas/tests, затем
мигрирует курс. Архитектурные решения не выбираются заново при реализации.

| Вопрос | Решение |
| --- | --- |
| Общий target и отличный явный target | Ошибка; смешанный документ отменяет общий target через false |
| Default open и явный restricted | Допустимое переопределение |
| Реферат и обычная задача | independent-study у реферата; обычное exr без course-role остаётся exercise |
| Контекст Download | Одна ссылка; обычная задача student → starter, full → full; открытая demonstration → full в обоих |
| Область запуска Java | Только явно выбранные project-check; PL export требует профиль, demo подключает его без экспорта |
| PrairieLearn/Moodle | Community + gateway; LTI 1.3/AGS между Moodle и gateway |
| Числовая лабораторная | Оценивается только код; объяснение — рекомендация для обучения |
| UUID | Вычисляемые; legacy native UUID не переносятся в ручную конфигурацию |

### Общие профили проверки и декларация поставки

Добавить в `tasks/_quarto.yml` следующие metadata. Course ID берётся из
существующего `course.id: java-portal`, а не дублируется в PL namespace.
Runtime IDs разрешаются установленной Platform по её versioned registry
`runtime-profiles.json`; registry закрепляет OCI digest и hashes библиотек.
Курс не подставляет floating tag или выдуманный digest будущего образа.

Qualified question ID — существующий courseId/exerciseId (без work/path);
question UUID — существующий UUIDv5 DNS от этого key. Course UUID — UUIDv5 DNS
от строки `course/<courseId>`; instance UUID — UUIDv5 в namespace course UUID от
`instance/<instanceKey>`; assessment UUID — в том же namespace от `assessment/<workId>`.
Учебные sec/exr anchors являются устойчивыми исходными ключами. На раннем этапе
старые shell UUID меняются однократно; author UUID overrides не вводятся.

```yaml
project-checks:
  defaults:
    runtime: java25-junit-v1
    java:
      release: 25
      encoding: UTF-8
      compiler-options: ["-proc:none", "-Xmaxerrs", "5"]
    limits:
      outer-seconds: 30
      networking: false
      max-output-bytes: 65536
    scoring:
      mode: weighted
    references:
      - name: default
        root: reference
        optional: true
    verification-tests: ["student/src/test/java/**/*Test.java"]
    contract-cases: tests/contract-cases.json
    verification-expectations:
      reference:
        student-compilation: success
        job: complete
        required-tests: all-pass
      starter:
        student-compilation: success
        job: complete
    discovery:
      min-executed: 1
      allow-skipped: false
  source-profiles:
    java-main:
      mode: implementation
      root: student/src/main/java
      include: ["**/*.java"]
    java-root:
      mode: implementation
      root: student
      include: ["*.java"]
    java-tests:
      mode: student-tests
      root: student/src/test/java
      include: ["**/*.java"]
  profiles:
    java25-junit:
      source-profile: java-main
      tests: ["tests/junit/**/*.java", "tests/*Test.java"]
    java25-junit-root:
      source-profile: java-root
      tests: ["tests/junit/**/*.java", "tests/*Test.java"]
    java25-pilot:
      source-profile: java-root
      tests: ["tests/junit/**/*.java"]
      scoring:
        mode: all-pass
    java25-mutation:
      source-profile: java-tests
      runtime: java25-mutation-v1
      tests: ["tests/variants/**/*.java"]
      verification-tests: []
      variants:
        correct: [correct, correctGenericException]
        mutants: [alwaysReject, emptyResult, mutatesFailure, mutatesSuccess,
          rejectEmpty, rejectFourByte, rejectReplacement, rejectThreeByte,
          rejectTwoByte, replacement, unfinished, wrongException]
      scoring:
        mode: all-pass
prairielearn:
  delivery:
    book: tasks
    course:
      name: JAVA
      title: Программирование на Java
      timezone: Europe/Minsk
      topics:
        - name: Java
          color: blue2
          description: Программирование на Java 25
    instances:
      pilot:
        title: Локальный пилот Java 25
        self-enrollment: false
        works: [sec-lab-numbers, sec-pl-java25-pilot]
  question-defaults:
    topic: Java
    submission:
      mode: editor
      ace-mode: ace/mode/java
```

Правило merge: `project-checks.defaults` → выбранный profile; scalar/array
заменяются, вложенные maps объединяются по объявленной схеме. Ключи закрыты,
неизвестные поля — ошибка. Имена profiles не имеют встроенной семантики: режим,
источники и runtime определяет декларация. Reference подставляется вместо
student source по относительному имени внутри выбранного source root.
Существующие flat reference файлы сопоставляются по этим именам; package
fixtures сохраняют package path. Несовпадение/дублирование — ошибка, не эвристика.

`java25-pilot` первоначально сохраняет предметную all-pass политику нынешнего
пилота; numbers использует weighted. Ни одна политика не определяет успех
verification: там отдельно проверяются исполнение, контракты и ожидаемые исходы.
Scoring alternatives weighted/all-pass/contract-groups/threshold поддерживаются
закрытым union; groups требуют веса и перечни stable test IDs, threshold —
основание и значение. Значение по умолчанию не выводится из результата starter.

`references` — явный список именованных авторских решений; каждый запускается.
Отсутствующий optional reference отмечается reference-unavailable. Для остальных
объявленных решений optional по умолчанию false, отсутствие одного — ошибка.
Verification-tests берутся из авторского snapshot, запускаются отдельно как
non-scoring public suite и не копируются в официальный grading payload.
Их source hashes также входят в checks manifest. В mutation режиме оцениваемая
suite уже является student answer; verification-tests явно отменены пустым списком.

Mutation `tests` выбирает fixture sources, не одну общую compilation unit.
Variant ID разрешается в tests/variants/ID; каждый correct/mutant компилируется
в отдельный classpath/scratch и запускается с оцениваемой suite. Иначе одинаковые
FQCN Utf8Decoder дадут duplicate class. Trusted fixture compilation failure —
infrastructure; пустая/disabled student suite — дефект качества ответа студента.

Contract cases schema принадлежит Platform: schemas/contract-cases.schema.json.
Поля документа schemaVersion/cases; case имеет id, sources, expected. Sources
содержит fixture (project-relative path) и submission (разрешённый output path),
expected — compilation/job/classification, optional test counts/IDs и optional
score assertion. Источники fixture остаются внутри tests/fixtures; path escapes,
повторный case ID, неизвестное ожидание и неразрешённое submission имя запрещены.
Например будущий tests/contract-cases.json лабораторной:

```json
{
  "schemaVersion": 1,
  "cases": [{
    "id": "int-add-before-widening",
    "sources": [{
      "fixture": "tests/fixtures/int-add/IntegerArithmetic.java",
      "submission": "IntegerArithmetic.java"
    }],
    "expected": {
      "student-compilation": "success",
      "job": "complete",
      "classification": "behavior-failure",
      "failed-tests": {"at-least": 1},
      "score": {"less-than": 1}
    }
  }]
}
```

Файл содержит намеренно неверную, компилируемую сумму `(long)(a + b)`.
Ожидание принадлежит этому контрпримеру, а не всем starter. Каждый профиль
может объявить свои cases/expectations или не задавать их; пример общего default
выше предполагает создание файла cases у всех opted-in ready проектов в Task 7.
Без объявленного файла checker не изобретает контрпримеры. Required negative
evidence для selected release фиксируется в отчёте, отдельно от score policy.

### Упражнение, демонстрация и смешанный документ

Связь реферата и защиты является нейтральным свойством работы, например:

```yaml
assessment:
  kind: test
  related-exercise: exr-essay-charset-decoding
  prairielearn:
    pass:
      at-least: 2
```

Core добавляет Assessment.relatedExercise и проверяет ссылку на декларацию.
Для выбранной instance exporter проверяет уникальность active defense на один
essay. Presentation выводит связь через QRC; старое ручное «Тема реферата: @sec»
заменяется этим представлением. Runtime user/approval в metadata отсутствуют.

В metadata лабораторного документа:

```yaml
default-exercise-target: prairielearn
default-exercise-statement-visibility: open
default-exercise-project-check: java25-junit
```

```markdown
:::: {#exr-lab-integer-arithmetic project="/projects/exercises/numbers/integer-arithmetic" difficulty="introductory" time="45"}
## Сумма и среднее двух целых чисел
{{< project-download exr-lab-integer-arithmetic >}}
...условие...
::::
::: {.task-items requirement="required" work-mode="individual" stage="classroom"}
1. @exr-lab-integer-arithmetic
:::
```

Pilot задаёт document default `project-check: java25-pilot` через имя
`default-exercise-project-check`; UTF-8 test-design переопределяет его атрибутом
`project-check="java25-mutation"`. Не создавать ID → file/profile binding map.

У Java-демонстрации достаточно явной декларации на программном упражнении:

```markdown
:::: {#exr-demo-slice target="manual" course-role="demonstration" statement-visibility="open" project="/projects/exercises/text-arrays/unicode-demonstration/implementation" project-check="java25-junit"}
## Демонстрация выделения диапазона
{{< project-download exr-demo-slice >}}
...условие...
::::
```

Это не добавляет native question или assessment и не включает упражнение в банк.
Профиль применяется на общих основаниях: source selection → JUnit job → report.
Для остальных manual проектов отсутствие project-check означает отсутствие
автоматического запуска, даже если имеются reference/tests.

Смешанный документ с `default-exercise-target: prairielearn` и явным manual
получает ошибку `CORE.EXERCISE_DEFAULT_CONFLICT`. Автор задаёт документное
`default-exercise-target: false` и указывает target отдельных упражнений.
Для visibility такой ошибки нет: явный restricted переопределяет общий open.

### Общие интерфейсы данных

Не заменять существующие публичные Exercise/ExerciseDeclaration вымышленным
типом EffectiveExercise. Внутренний `EffectiveExerciseFacts` хранит authored
и effective значения раздельно. `authoredTarget` остаётся только исходным
атрибутом, даже когда effective target получен из metadata.

Core добавляет `ProjectFact[]` к DocumentResult и агрегирует его в Course:
`{exerciseId, source, projectRoot, bankMember, purpose?, statementVisibility,
artifactPolicy, check?}`. `check` — нормализованный общий ResolvedProjectCheck.
Список содержит также non-bank проекты; он не изменяет Course.exercises.
Один qualified exercise ID имеет один согласованный project/check; конфликт
между документами диагностируется, повторное назначение не дублирует job.

ChecksManifest имеет schemaVersion, courseId, bookRoot (например tasks),
sourceSnapshotHash, inventoryHash, projects. Fact source/projectRoot относятся
к корню этой native книги; SourceSelection.projectRelativePath — к projectRoot.
Platform разрешает их от COURSE_ROOT/bookRoot с ownership/path guards. Машинные
абсолютные пути не входят в опубликованный manifest или воспроизводимую identity.

| Владелец / функция | Вход → выход |
| --- | --- |
| Core `exercise-defaults.normalize(doc)` | Quarto metadata + raw AST → facts по ID |
| Core `projects.collect(doc, facts)` | Объявленные проекты/profiles → проверенные ProjectFact |
| Core `resolveArtifact(run, request)` | Trusted NativeRun + source/exerciseId/kind → разрешённые файлы либо conditions AST/resources |
| PL `resolveQuestionDeclaration(exercise, projectFact, metadata)` | Effective target + общий check + PL metadata → ResolvedQuestionDeclaration |
| PL `selectImplementationSources(projectRoot, sourceProfile)` | Проверенный source profile → SourceSelection с двумя относительными путями и SHA256 |
| PL `exportCourse(model, context, options)` | Fresh Core model + instance → native files + DeliveryManifest + verification inventory |
| Platform `prepare_job(manifest, snapshot, id, scenario)` | Разрешённый check → GradingJob |
| Platform `run_job(job, backend)` | Один job → CheckResult |
| Platform `verify_results(results, expectations)` | Фактические результаты + объявленные ожидания → VerificationReceipt |

Core authorization получает audience из trusted run, не из caller-supplied поля.
Download сохраняет generic resources contract для ресурсов вне модели. Его новый
typed model request не превращается в string ID внутри старого resources map.
Conditions pack содержит `index.html` и assets из resolved AST: prerequisites
включаются, внешняя теория связывается устойчивым опубликованным URL через QRC.
Если нужны абсолютные cross-document ссылки, задать `website.site-url` один раз;
при его отсутствии выдавать диагностику, не вставлять пути к исходным QMD.

Platform schemas закрыты (`additionalProperties: false`), имеют schema version:

- `GradingJob`: scope, qualified ID, scenario, source/inventory hashes,
  source selections, trusted suites/fixtures, runtime ID/image digest,
  compiler/runner options, limits, scoring и discovery policy.
- `CheckResult`: отдельные student/trusted compilation; infrastructure status;
  discovered/executed/passed/failed/skipped/aborted; contract observations;
  raw PL result; optional score/maxPoints; diagnostics и durations.
- `VerificationReceipt`: scope, tool/schema versions, exact source snapshot,
  check inventory hash, runtime/dependency digests, checked IDs,
  expectations и classification. Durations не входят в reproducible identity.

В scope delivery GradingJob/receipt дополнительно имеют обязательный deliveryHash.
В declared его нет: проверка не требует native PL artifact. Report envelope
содержит inventory, results и receipt после завершения; builder извлекает receipt
только scope delivery и требует полного покрытия выбранных вопросов.

Core владеет ссылкой project-check, profile resolution и общей структурой
project facts; Platform владеет runtime-specific schema и execution semantics.
Core не запускает runtime и не ищет OCI registry при render. Полная проверка
Java options/scoring/limits выполняется installed checker, а для PL — также
exporter по согласованной schema version до записи native output.

Для указателя рефератов Core сохраняет semester/categories тематического
документа в topic facts (semester — положительное целое, categories — строки).
`TopicFact={source, semester?, categories}` добавляется к DocumentResult и Course;
source связывает его с Exercise.source, без второго списка ID упражнений.
Navigation предоставляет `{{< course-exercise-index role="independent-study"
group-by="semester,difficulty" >}}`: title/difficulty/time из упражнений,
semester/categories из topic facts, ссылки через QRC, текущая audience projection.
Это один вызов в tasks/essays.qmd, без шести повторяющихся listing declarations.
Не добавлять непредоставленные поля в Body; owner model/schema фиксируют topic
facts, а tests проверяют группы и отсутствие restricted control в student index.

Reference остаётся в private source snapshot для verification. Проверочная
inventory не копируется в публичный сайт и native PL question payload. Native
delivery содержит лишь нужный grading descriptor/trusted assets. Demo jobs
появляются в declared inventory, но не в native delivery.

Exit 0 означает выполненные ожидания выбранного scope, exit 1 — дефект
комплекта/невыполненное ожидание, exit 2 — незавершённую инфраструктурную проверку.
Starter test failures сами по себе не вызывают exit 1, если compile/job/report
корректны и автор не объявил противоречащее ожидание. Infrastructure error
никогда не маскируется под student score 0.

## Исследование локальных проверок

Исследовательские scripts/payloads находились в `/tmp`; исходники курса не
редактировались. Запуск не использовал установленный будущий exporter, новую
платформу или Moodle. Поэтому это характеристика существующих проектов и
официального grader, а не приёмка будущей комбинации owners.

### Системный Java и Gradle

Выбран `/usr/lib/jvm/java-25-openjdk`, отдельные Gradle caches/build outputs.
Проверены все восемь существующих Gradle-проектов в режимах reference и starter.
Все эталоны успешно компилируются: 81 обычный JUnit test пройден. Отдельный
UTF-8 mutation комплект принимает две correct реализации и отвергает 12 mutants.
Все восемь starters компилируются; behavior/quality checks ожидаемо проваливаются.
В семи обычных suites у starters 77 failed из 81 tests; это не compilation error.
Mutation starter не обнаруживает обязательную ошибку replacement.

Gradle exit 1 у starter здесь означает провал предметных проверок, а не
невозможность запустить checker. Эти состояния должны разделяться новым CLI.

### Официальный контейнер PrairieLearn

Image pin:
`docker.io/prairielearn/grader-java@sha256:dacce5e76d24cf1eaee21bec3e20b3ea40739408afdc89e6607eb542dc6b7a2e`.
Фактически Java 25.0.4 и JUnit Platform 1.14.1 / Jupiter 5.14.1.
Host проекты пока используют Jupiter 5.11.4: backend parity не предполагается.

Протокол `/grade`: только implementation source в student; existing private
`tests/junit`; data JSON `{}` для static проб; read-only inputs, writable scratch
и results. Public student tests не включались в оценку. UTF-8 mutation Gradle
проект в эту контейнерную пробу не входил.

| Проект | Private tests | Reference | Starter |
| --- | ---: | ---: | ---: |
| Numbers / integer-arithmetic | 4 | 4/4; score 1 | 1/4; score 0.25 |
| Unicode pilot | 18 | 18/18; score 1 | 1/18; score ≈0.05556 |
| Unicode implementation | 2 | 2/2; score 1 | score 0 |
| Unicode experiment | 1 | 1/1; score 1 | score 0 |
| Unicode test-design | 1 | 1/1; score 1 | score 0 |
| UTF-8 implementation | 31 | 31/31; score 1 | score 0 |
| UTF-8 experiment | 1 | 1/1; score 1 | score 0 |

Все семь эталонов прошли 58 private tests. Все starters компилируются и
`gradable: true`. Семь reference/starter пар завершались за 19.66–24.20 s
при 0.9 CPU, memory/swap 2 GiB, 1024 pids, no network и outer 30 s.
Эти времена включают запуск контейнера и grader, а не только Java test duration.

Всего проведены 23 контейнерные пробы. Дополнительные результаты:

- Renamed/wrong-return `sum` не ломает компиляцию trusted tests; проходит mean,
  поэтому официальный weighted grader даёт 0.5. Аналогичные ошибки pilot дают 0.
- Syntax errors numbers/pilot дают `gradable: false`, compiler diagnostics,
  без score; завершаются примерно за 4.6–5.7 s.
- Компилируемый класс без tests даёт `gradable: true`, score 0,
  `max_points: 0`, пустой tests. Это недостаточная проверка комплектности grader.
- Empty `UnicodeEvidence.examples()` получает 1/1.
- No-op `SliceChecks.verify(...)` получает 1/1.

Последние два результата — доказанные пробелы private suite. Они не зависят
от выбора weighted/binary policy. Host public suites строже, поэтому успешный
локальный `gradle test` не доказывает качество exported private suite.
Все probe containers завершены; новые сервисы не поднимались. Timeout/output
flood/реальный PL editor/Moodle на этой стадии не проверялись.

## Последовательность и зависимости

```text
0 Source/PR snapshot и accepted-next contracts
1 Удаление ручной native поставки и подготовка источников
2 Core defaults/assignments/visibility/artifacts
3 Download             4 PL full exporter
                         5 Platform JUnit + local checker
                         6 Gateway protocol + runtime
7 Исправление Java grading suites и authoring migration
8 Installed releases + template/docs + CI
9 Local full verification и native PL integration
10 Moodle/assignment/gradebook integration
11 Финальные releases, повторная приёмка, передача
```

После фиксации Core API фазы Download и PL независимы. Platform и PL согласуют
общий project-check descriptor; gateway проверяет выбранный Community bridge.
Проверку сайта и Java corpus можно вести параллельно, но ресурсоёмкие grading
jobs на ноутбуке запускать по измеренному бюджету. Не объединять состояние
разных SHA и run IDs в один успешный отчёт.

## Task 0 Зафиксировать контракты и исходное состояние

**Files:** текущие owner `spec/`, README, schemas; свежие документы курса;
`providers.json`, `installed-packages.json`, `UPSTREAM.md`.

**Interfaces:** потребляет current worktree и latest PR snapshot; выдаёт
утверждённую матрицу совместимости и recoverable baseline source identity.

Owner paths перед запуском разрешить через actual clone/worktree, а не считать
все repos существующими в /home/tolya/course-tools. Работу изолировать в codex/
branches. Этот документ не разрешает публикацию в текущем проходе планирования.

- [ ] Повторить latest-created/latest-updated PR audit всех затронутых owners;
  записать PR status/head, actual descriptor и released base. Проверить, что
  потомок merged PR включает released patches.
- [ ] Сохранить текущее незакоммиченное состояние курса и owners recoverable Git
  snapshot без reset пользовательских файлов; учесть untracked новые task trees.
- [ ] Записать baseline IDs/body hashes/assignments/project paths. Проверить
  242 bank declarations и отдельно non-bank demonstration; не glob старые caches.
- [ ] Проверить target conflict, обычный visibility override, отмену default
  через false, list defaults и отдельную artifact audience policy.
- [ ] Зафиксировать accepted-next schemas и dependency graph в owner repos.
  Выбрать номера новых версий по реальным breaking authoring/CLI/schema changes.
- [ ] Зафиксировать общий project-check/project-checks и два scope. Именованные
  profiles не дублируются внутри prairielearn. Уточнить runtime schema вместе
  с Platform; image digest выпускаемого runtime записать после реальной сборки.
- [ ] Для локального стенда выбрать [Moodle 5.3.0 LTS](https://moodledev.io/general/releases#moodle-53), зафиксировать официальный
  source tag/commit, PHP/database requirements и построенный image digest.
  Проверить минимальную связку выбранного Community bridge и Moodle AGS
  двумя spikes Task 6; ADR фиксирует этот путь, а не выбирает Enterprise заново.

**Gate:** версия contract каждого поля имеет одного владельца; неизвестных
baseline/PR кандидатов и скрытого решения о scoring нет.

## Task 1 Первым удалить ручную поставку курса

**Files:** удалить `prairielearn/` с пятью JSON; изменить относящиеся к нему
README ссылки и `_quarto.yml` resource exclusions по фактическому состоянию.
QMD и project assets пилота сохранить.

**Interfaces:** потребляет snapshot старых настроек; выдаёт author-only курс
без вручную собранной native shell. Replacement exporter script не допускается.

- [ ] Удалить binding/export/native files, перечисленные в R01.
- [ ] Проверить, что pilot/numbers source IDs, statements, Java sources и tests
  сохранились; metadata старой delivery записаны в accepted semantic contract.
- [ ] Проверить отсутствие authored native course/instance/assessment/question
  JSON и generated вопроса, добавленного вручную в tracked course.
- [ ] Зафиксировать изменение отдельно. Старый export CLI после этой фазы
  больше не является рабочим маршрутом; не восстанавливать ему ручные inputs.

**Gate:** нет старой поставки и альтернативной shell; authoring sources целы.
Полный новый export ожидает фазы 2/4/5, а не объявляется уже работающим.

## Task 2 Core нормализация и модель

**Files:** owner `_extensions/course-core/pedagogy/contract.lua`,
`assessment.lua`, `exercises.lua`, `native-document.lua`, `filter.lua`,
`visibility.lua`, `domain/model.ts`, `domain/release.ts`, `spec/core.cue`,
Body schema/collect; новый узкий `exercise-defaults.lua` и artifact fact unit.
Tests: существующие `authoring-model`, `pedagogy`, `visibility`, `native-body`,
`native-resources`, `selected-export`, `outside-bank-parity`.

**Interfaces:** внутренний EffectiveExerciseFacts и ProjectFact из раздела
контрактов. Назначение остаётся `{stage?, requirement, workMode}`. Existing
Exercise/ExerciseDeclaration сохраняются и расширяются только необходимыми
полями, без замены всей модели новым runtime.

- [ ] Добавить RED cases root/directory/document, nested project isolation,
  conflicting/matching target, отмена inherited default, invalid fields/time.
- [ ] Реализовать single normalization до adapter validation/projection;
  `native_document.validate(doc, facts)`, `exercises.collect(doc, facts)`,
  `pedagogy.collect` и `visibility.prepare` читают один набор facts. Изменить
  `native-adapters.lua`: validate/read передают facts в adapters. В PL native.lua
  и validate.lua заменить raw target gates; CUE Header/project проверяет effective
  target. Presentation использует нормализованные display attributes на clone
  AST; authored provenance при этом не перезаписывается.
- [ ] Добавить RED list-level requirement/work-mode, Span override, отсутствующий
  stage, stage на Span, repeated member, multiple lists и required/all totals.
- [ ] Реализовать list defaults и согласовать Course/Body/Presentation/CUE.
- [ ] Добавить related-exercise/Assessment.relatedExercise, RED missing relation
  и multiple selected defenses for one essay; exporter передаёт каноническую
  relation в delivery manifest, не выводит её из строковых суффиксов ID.
- [ ] Добавить RED запрещённых audience wrappers в скрытой ветви; сохранить
  functional profiles. Преподавательский общий текст перенести в full-only
  QMD книги, без нового teacher-material блока.
- [ ] Добавить non-bank project/artifact facts без включения demonstration
  в банк; resolved conditions включают требуемые prerequisite dependencies.
- [ ] Добавить `_extensions/course-core/projects.lua`, `project-checks/collect.ts`
  и `entrypoints/project-checks.ts`. Первый нормализует project/check declarations,
  collector использует fresh full NativeRun и общий Core manifest API, entrypoint
  пишет private checks.json без PL exporter и без выполнения Java.
- [ ] RED `manual_without_check_excluded`, `manual_demo_with_check_not_banked`,
  `inherited_check_resolved`, `cleared_check_default`, `unknown_check_profile`,
  `check_without_project_rejected`, `declared_inventory_without_native_delivery`.
- [ ] Пройти owner tests и installed examples. Сверить student/full projections,
  inferred target activation и stable identity explicit→default.
- [ ] Добавить topic facts и `_extensions/course-navigation/shortcodes.lua`
  для course-exercise-index; tests semester_difficulty_groups и
  student_index_does_not_include_restricted_control. Presentation использует
  уже итоговые title/time/difficulty, не читает metadata файлов заново.

**Gate:** каждый consumer видит одинаковые final properties; отсутствие
source attributes не обходит validation; non-bank native behavior сохранено.

## Task 3 Download комплекты из модели

**Files:** owner `_extensions/project-download/shortcodes.lua`, `filter.lua`,
`domain/config.ts`, `infrastructure/runtime.ts`, `application/publish.ts`,
`ownership.ts`; существующие archive/render/ownership/native-installed tests.

**Interfaces:** потребляет current Core artifact facts; запрос `{exerciseId,
kind?: "starter"|"full"|"conditions", text?}` не содержит path/profiles/exclude.
Без kind модель выбирает ровно одну контекстную ссылку. Публикует согласованные
ссылки и artifact receipt с hashes, kind/audience и source run identity.

- [ ] RED пустой kwargs.text, empty explicit text и custom text; исправить
  fallback и проверить реальный DOM/accessibility links.
- [ ] RED `ordinary_student_starter`, `ordinary_full_full_project`,
  `restricted_student_no_request`, `public_demo_full_in_student`,
  `public_demo_full_in_full`, `explicit_conditions_without_solution`,
  `missing_project_error`, `unauthorized_full_request_error`.
- [ ] Реализовать три R04 kinds с Core authorization; не ослаблять generic
  public guards. Удалить необходимость resources maps модельных задач.
- [ ] RED README opacity, byte preservation, allowed .gitignore, symlink/path
  escape, QMD/config/cache exclusions и standalone relative links.
- [ ] Две независимые сборки должны дать одинаковые ZIP bytes; переключение
  профиля очищает только owned artifacts текущего контекста.
- [ ] Проверить installed package, search/QRC/resources и браузерное скачивание;
  не ограничиваться тестом существования ZIP.

**Gate:** full ZIP содержит reference/tests; обычный student starter их не
содержит; публичный demo full содержит их и в student; явно выбранные conditions
не содержат решений. Default captions: «Скачать заготовку», «Скачать полный
проект», «Скачать условие»; ZIP names — ID-kind.zip.

## Task 4 PL декларации и полный native export

**Files:** owner `native.lua`, `validate.lua`, `spec/prairielearn.cue`,
`application/export.ts`, `entrypoints/export.ts`; новые
`entrypoints/export-course.ts`, `application/export-course.ts`,
`application/declarations.ts`, `application/source-selection.ts`;
`tests/export.test.ts`, native-model/identity/installed examples.

**Interfaces:** metadata prairielearn.delivery/question-defaults из примера.
Общий project-checks определяет runtime/sources/scoring, PL carrier ссылается
на resolved check и добавляет только платформенные topic/submission/access.
Final PL facts сохраняются в extensions.prairielearn. Единственный редкий
per-question PL override — проверенные topic/submission поля; source/runtime
override выбирается общим project-check, не второй map настроек.

SourceSelection содержит projectRelativePath/submissionRelativePath/sha256.
Numbers source profile отображает student/src/main/java/IntegerArithmetic.java
в IntegerArithmetic.java; pilot — student/UnicodeSlice.java в UnicodeSlice.java.
Package paths сохраняются. В implementation mode src/test/build.gradle/README
не входят в submission. В student-tests mode явно выбранный src/test допустим
как оцениваемый ответ, без разрешения подменять trusted runner.

- [ ] Согласовать CUE/types этих carriers и runtime profile с фазой 5;
  RED defaults/per-question overrides и unknown/unsafe declarations.
- [ ] RED inherited target: PL видит его ровно один раз. Считать source allowlist
  по именованному профилю; проверить root pilot, standard numbers и mutation layout.
- [ ] RED `selected_pl_without_check_rejected`, `manual_checked_demo_not_exported`,
  `unknown_source_profile`, `preserved_package_paths`, `student_build_script_denied`.
- [ ] RED export-course без binding/export.json/shell создаёт полный course,
  instances, assessments и questions только из current native model.
- [ ] Реализовать native metadata generation, устойчивые UUID/qualified IDs,
  selected works, ordered items, attempts/pass и explicit access policy.
- [ ] RED Unicode/template literals, missing/binary/NUL/oversized sources,
  broken/incomplete project, teacher/private leakage, path change identity.
- [ ] Проверить upstream native schemas и atomic fresh output; два независимых
  exports byte-identical. Никакие post-export ручные исправления не допустимы.
- [ ] Установить пакет штатно и вызвать новый CLI из учебного проекта.

**Gate:** complete native artifact плюс delivery.json/grading manifest;
reference отсутствует в нативной поставке, trusted tests находятся server-side.
Private checks.json пишется отдельным --checks-output, содержит также opted-in
демонстрации и не входит в публикуемый native artifact.

## Task 5 Platform единый grader и local verification

**Files:** owner `images/java25-grader/Dockerfile`, `grade.py` или заменяющий
entrypoint; новый узкий JUnit adapter; `tools/build-course.py`,
новые `tools/check-course.py`, `tools/grading_job.py`, runtime-profiles.json,
`schemas/project-check.schema.json`, `schemas/grading-job.schema.json`,
`schemas/check-result.schema.json`, `schemas/verification-receipt.schema.json`,
`schemas/contract-cases.schema.json`, `tools/check-integration.py`,
`tests/test_check_course.py`, `tests/test_grading_job.py`,
`tests/test_grader_container.py`. Совместный PL descriptor генерируется exporter.

**Interfaces:** схемы/prepare_job/run_job/verify_results из раздела контрактов.
Declared scope читает private Core checks manifest без native PL artifact;
delivery scope сверяет тот же check с actual exported sources/tests/profile.

- [ ] RED запуск existing JUnit вместо course-owned main; сохранить стандартный
  `/grade` и official JUnit implementation. Закрепить base digest/dependencies.
- [ ] RED student/trusted compilation classification, unsafe FQCN collisions,
  submitted test injection, zero discovery/zero execution/skips/missing report.
  Named cases: trusted_zero_tests_is_infrastructure, submitted_zero_tests_is_quality_failure,
  disabled_mutation_suite_is_not_success, duplicate_fixture_fqcn_isolated.
- [ ] Реализовать минимальный shared entrypoint, не новую библиотеку тестирования.
  Public tests доступны автору; official score определяют trusted exported suites.
- [ ] RED weighted/binary/contract-group policy fixtures; policy выбирается
  декларативно. Report сохраняет raw outcomes и max points независимо от policy.
- [ ] RED inner timeout/output flood vs outer Docker failure; cold compile
  measurements на actual PL limits. Не копировать fixed 5 s без evidence.
- [ ] Реализовать host backend на выбранном JDK и container backend с тем же
  runner/dependencies; container считается authoritative для PL parity.
  Host libraries поставляются versioned bundle с hashes из runtime registry;
  preflight проверяет их до job. Не брать случайный Jupiter из Gradle cache.
  Общий entrypoint принимает job root, PL использует /grade, host — private scratch.
- [ ] RED `check-course` inventory → reference/starter/contracts по unique IDs,
  incomplete selected project, expected outcomes optional и exit classification.
  Проверить explicit/default check inclusion и manual-without-check exclusion;
  наличие reference не активирует checker. Опубликованный schema валидирует
  полный typed union, а не permissive JSON blob.
- [ ] Обновить builder: официальный installed full exporter, без old config/shell;
  immutable provenance/staging сохраняются и требуют matching successful receipt.
- [ ] Пройти owner/container tests; два fresh checks reproducible по hashes и
  outcomes. Только durations могут отличаться.

**Gate:** одинаковый runtime profile и actual payload у локального checker и
реального PL job. Различие host Gradle и контейнера объяснено, не скрыто.

## Task 6 Gateway и протокол Moodle

**Files:** первый PR gateway: pyproject.toml; src/gateway/app.py, launch.py,
assignments.py, pl.py, grades.py, store.py; migrations/001_runtime.sql;
config/runtime.example.json; spec/launch.md, assignment.md, results.md;
tests/test_launch.py, test_assignment.py, test_pl_bridge.py, test_gradebook.py,
test_restart.py; tools/run-gateway.py, tools/check-integration.py.
Platform: images/prairielearn/Dockerfile, patches/prairielearn-gateway.patch,
compose/compose.gateway.yml, proxy/nginx.gateway.conf,
config/pl.gateway.example.json, tests/test_gateway_proxy_live.py,
tests/test_gateway_acl.py. Пути — относительно соответствующего owner repo;
новые файлы не означают, что scaffold уже содержит этот сервис.

**Interfaces:** validated launch → stable identity/course/context/role;
assignment → user+instance+essay+defense+delivery; grade event → target Moodle
activity/user/version с idempotency key. Transport не подменяет content model.

Gateway: Python ASGI/FastAPI + проверенная Authlib/JWT library, HTTP client,
SQLite с миграциями и persistent volume. Закрепить dependency versions/lockfile;
не реализовывать собственную криптографию. Identity key включает issuer,
client/deployment и subject; context/resource-link имеют отдельную проверенную
привязку к course/instance/Moodle activity. Teacher из Moodle получает только
разрешённые gateway операции, автоматически PL admin не становится.

Runtime API: GET /my/works; POST /assignments/request;
POST /teacher/assignments/{id}/approve, reject, reassign;
POST /launch/pl/{workId}; внутренний GET /internal/handoff/{opaqueToken}.
Внешний клиент не отправляет grade: gateway poller читает scoped PL gradebook,
сверяет current assignment/delivery и создаёт внутреннее событие в AGS outbox.
Polling interval первоначально 15 секунд, изменяемый в runtime config.

Gradebook даёт aggregate score; его одного недостаточно для правила защиты
«две полностью выполненные задачи из трёх» при partial credit. Platform bridge
добавляет GET /internal/gateway/results для разрешённых manifest assessments:
current attempt, per-question score/max и status, qualified IDs/delivery.
Gateway считает выполненные задачи required pool по full-score; optional их
не заменяют. AGS защиты получает scoreGiven=count, scoreMaximum=requiredCount,
а pass threshold берётся из manifest pass.at-least. Для обычной лабораторной
передаётся её объявленный score. Проверить weighted_partial_does_not_count_as_two
в Spike B и integration; Gateway не читает напрямую таблицы PL.

Identity bridge использует Community shibcallback, не Enterprise LTI. ACL bridge
Platform предоставляет приватный idempotent PUT /internal/gateway/assignments/{id}
с version, user UID, instance, delivery, old/new work. Он разрешает manifest work
в native assessment/enrollment labels и обновляет их штатными моделями PL.
Доступ ограничен private network + service authentication; bridge не принимает
произвольный SQL/label ID или supplied Moodle role как PL admin privilege.

Базовые upstream точки:
[Community callback](https://github.com/PrairieLearn/PrairieLearn/blob/92584fe426ececb84bc2d09de9975c7056c0c5f6/apps/prairielearn/src/pages/authCallbackShib/authCallbackShib.ts),
[enrollment labels](https://github.com/PrairieLearn/PrairieLearn/blob/92584fe426ececb84bc2d09de9975c7056c0c5f6/apps/prairielearn/src/models/student-label.ts),
[gradebook endpoint](https://github.com/PrairieLearn/PrairieLearn/blob/92584fe426ececb84bc2d09de9975c7056c0c5f6/apps/prairielearn/src/api/v1/endpoints/courseInstanceGradebook/index.ts).
Public write API labels отсутствует: именно поэтому нужен узкий Platform patch.

Состояние назначения и outbox сохраняются транзакционно. Подтверждённый доступ
открывается после успешной ACL reconciliation; операция замены считается
завершённой после отзыва старых прав и проверки новых. Pending/rejected не дают
прав. Capacity по умолчанию не ограничена; квоты и режим записи задаёт поток.
Результаты старого назначения остаются в audit; поздние события игнорируются.
При замене ранее опубликованная Moodle оценка сохраняется до результата нового
назначения, статус pending виден на gateway; не пытаться сбрасывать AGS оценку
простым отсутствием scoreGiven.

- [ ] Spike A (до 2 часов исследования): настоящий Moodle LTI launch → gateway
  verified session → private handoff → Community callback → обычный Student.
  Проверить issuer/JWKS/signature/aud/client/deployment/state/nonce/time/context,
  одноразовое использование и TTL, header spoofing и отсутствие devMode bypass.
- [ ] Spike B (до 2 часов исследования): два Student, два defense variants;
  enrollment/label bridge → native list/direct URL ACL → scoped gradebook → AGS.
  Неудачный spike фиксирует конкретный блокер/следующий эксперимент и задерживает
  зависимую реализацию; admin override не является успешным результатом.
- [ ] RED spoofed/replayed launch, wrong issuer/context/role и missing enrollment;
  реализовать проверенный trust contract без staff/dev override.
- [ ] RED teacher assignment, immediate self-selection, pending approval,
  reject/reassign, duplicate/concurrent selection и capacity policy.
- [ ] Реализовать atomic persistent transitions и platform-side ACL update.
  Pending/rejected и чужой direct URL не открывают контрольную.
- [ ] RED roster list: Student видит labs + assigned defense; teacher видит все.
  Разметка QMD и native course files не меняются от действий пользователя.
- [ ] RED result callback: wrong user/activity/context/version, повтор/старый event,
  failed delivery/retry. Реализовать единый результат защиты и отдельные labs.
- [ ] Named RED tests: launch_replay_rejected, wrong_context_rejected,
  teacher_role_does_not_grant_pl_admin, pending_has_no_acl,
  foreign_defense_direct_url_denied, reassignment_revokes_existing_session,
  acl_reconciled_after_restart, old_assignment_grade_ignored,
  two_students_one_moodle_lineitem, ags_outbox_retry_idempotent.
- [ ] Пройти service tests и synthetic integration; keys/users/DB outside Git.

**Gate:** нет открытого trusted-header обхода; auth/assignment/gradebook проверены
через обычного Student. Gateway scaffold не представлен готовой интеграцией.

## Task 7 Миграция authoring и исправление grading corpus

**Files:** Java `tasks/_quarto*.yml`, essays/exercises/assessments metadata/QMD,
demonstration QMD, project READMEs/build.gradle/JUnit; README/UPSTREAM.

**Interfaces:** сохраняет исходные IDs/content/relationships; использует
released defaults/artifacts и общие project-check profiles. Только явно
подключённые проекты входят в inventory; selected PL project без check — ошибка.

- [ ] Перенести повторяющиеся target/role/visibility в подходящие metadata;
  difficulty/time defaults не изменяют индивидуальные оценки.
  Essays/_metadata.yml получает manual/independent-study/open; assessments/_metadata.yml
  — prairielearn/control/restricted и java25-junit check; pilot переопределяет
  check на java25-pilot, UTF-8 test-design — на java25-mutation. Numbers использует
  document metadata из примера; каждому из трёх demo projects явно назначить
  java25-junit. Defaults не включают непрограммные manual essays в проверки.
- [ ] Исправить `.task-items` placement stage; required/individual не повторять
  без необходимости. Проверить defense order/pass-two-of-three.
  У 60 защит добавить related-exercise на конкретную постановку реферата и
  заменить дублируемую ручную ссылку выводом Presentation; curriculum не меняется.
- [ ] Удалить все authored audience wrappers; teacher instructions переместить
  в full-only pages/README. Открытые демонстрации сохраняют публичные решения,
  а программные exr получают demonstration/open и явный project-check.
  Исправить вступление Unicode: полный проект теперь доступен также студенту,
  прежняя фраза о его исключительно преподавательском доступе больше неверна.
- [ ] Удалить Download resources map после подключения model artifact policy;
  starter/full/conditions получают собственные самостоятельные README.
- [ ] В numbers условии оставить объяснение как учебную рекомендацию, убрать
  его из grading-notes и submission requirements. Named test/report assertion:
  numbers_grade_depends_only_on_sum_mean_code; текстового ответа PL не требует.
- [ ] RED Unicode empty-array/full-credit: добавить private проверки точного
  числа examples и критериев каждого; эталон проходит, empty/no-op/incorrect fails.
- [ ] RED Unicode no-op verify/full-credit: проверить все обязательные defective
  implementations, включая bounds; эталон принимается, no-op не принимается.
- [ ] Сверить public/private contracts; не полагаться на public test, отсутствующий
  в native grading payload. Добавить negative fixtures на известные contract errors.
  Создать tests/contract-cases.json и tests/fixtures у восьми opted-in ready
  проектов: numbers, pilot, три UTF-8 и три Unicode demo. Проверить schema,
  replacement source paths и expected outcomes; не вводить общий expected starter score.
- [ ] Спроектировать JUnit mutation profile для UTF-8 test-design без выполнения
  переданного student Gradle script. Correct variants должны проходить,
  обязательные mutants падать предметно, все compilation stages успешны.
- [ ] Сверить JUnit dependencies host с approved runner; сохранить supported
  reflection и typed stable teacher API в test-design.
- [ ] Добавить model-derived semester/difficulty index без повторяемых listing.

**Gate:** сравнение source baseline сохраняет банк; opted-in corpus имеет
положительные и отрицательные свидетельства качества tests, а не только GREEN refs.

## Task 8 Пакеты, шаблон, CI и свежие docs

**Files:** owner release descriptors/spec/README/examples/CI pins;
template guide; Java providers/installed-packages/UPSTREAM/README;
новые `CI/render.sh`, `CI/check-projects.sh`, необходимая site check orchestration;
`.github/workflows/check.yml`; docs current set.

- [ ] Выпустить tested candidate packages/images с exact tags/commits/digests;
  новые CLI/schema versions записать в совместимую матрицу.
- [ ] Установить полные packages через штатный interface и проверить состав,
  bytes/modes/hashes. Тестировать именно installed packages.
- [ ] Обновить template/guide/examples: defaults, task-items, full export,
  artifacts, no audience wrappers, local testing commands.
- [ ] Перенести CI-related helpers в CI; workflow только orchestrates owners.
  Не перемещать tests/reference проектов. Исключить CI/logs из сайта.
- [ ] Сохранить незакоммиченные старые docs в recoverable Git snapshot и удалить
  пять прежних файлов docs. Оставить эти требования и план; не создавать history tree.
- [ ] Убрать README/UPSTREAM ссылки на удалённые docs/metadata registry; источником
  времени остаётся модель, historical migration — Git history.
- [ ] Зафиксировать generated receipts как owner release artifacts/current report,
  без копий старых evidence деревьев в учебном курсе.

**Gate:** fresh docs понятны без старых планов; версии в docs/code/schema/examples
совпадают; source package checks не выдаются за installed-package integration.

## Task 9 Полная локальная проверка и PL

**Files:** source snapshot, generated native output, verification reports в
private/runtime output; тестовые fixtures у владельцев, не новая курсная реализация.

- [ ] Preflight JDK25/CUE0.17.1/Quarto/Docker/Compose, memory/disk/private jobs.
  Не удалять чужие images/volumes ради места. Ограничить grading parallelism.
- [ ] Собрать student → full → student; проверить страницы, search/QRC/hrefs,
  teacher isolation, все ZIP и ручную работу ссылок в браузере.
- [ ] Fresh full export numbers/pilot; отдельно проверить declared check inventory:
  PL-target проекты с check и явно подключённые Unicode demonstrations/mutation.
  Manual без check исключён независимо от наличия Java/reference. Сверить
  selected delivery inventory с exported question IDs и exact payload hashes.
- [ ] После успешной проверки трёх UTF-8 проектов добавить
  sec-essay-charset-decoding-control в instance pilot works и экспортировать
  заново. Проверить его implementation/experiment/mutation вопросы в настоящей
  PL. На staging идёт только новый matching receipt всего selected delivery.
- [ ] Host reference/starter/contracts: compilation, фактические tests, coverage
  контрпримеры и score characterization. Reference unavailable отмечается явно.
- [ ] Same-image container check на exact delivery, cold run и повтор; сравнить
  результаты с host и записать каждое расхождение.
- [ ] Stage immutable exact artifact и выполнить настоящий PL sync/import.
- [ ] Через UI выполнить reference/starter/wrong API/syntax/timeout/Unicode и
  проверить actual feedback/attempt policy. Container JSON alone недостаточен.
- [ ] Restart/upgrade: identity, imports, scores и предыдущие versions сохраняются.

**Gate:** весь выбранный executable delivery принят без ручного native patch;
fresh receipts относятся к одному source/owner/image snapshot.

## Task 10 Настоящий Moodle и назначение

Текущий курс имеет одну ready защиту (UTF-8) и 177 placeholder проектов.
Не объявлять проверку двух реальных готовых защит выполненной. Для ACL/нагрузки
Gateway добавляет owner fixture tests/fixtures/course-gateway/: три готовых Java
bank questions, 60 manual essay declarations и 60 разных defense works с
related-exercise и общими назначенными вопросами. Штатный exporter создаёт эту
отдельную поставку, ручных native JSON нет. Это покрывает двух пользователей,
разные варианты, 2-of-3 и 60 вариантов в списке; fixture не добавляется в Java.
После fixture тестов обязательный настоящий Moodle round trip выполняется
на actual Java artifact с numbers и UTF-8 защитой. Placeholder работы не предлагаются
для записи, пока не появились в готовой exported delivery.

- [ ] Поднять утверждённый pinned local Moodle/gateway/PL stack после resource
  preflight; использовать synthetic accounts и private runtime config.
- [ ] Moodle Student launch открывает назначенный native work без второй
  неподдержанной авторизации; teacher launch имеет нужные права.
- [ ] Проверить teacher assignment и оба self-selection режима, concurrent request,
  approval/rejection/reassignment и direct URL access denial.
- [ ] Labs остаются отдельно; защита даёт один gradebook result для своего варианта.
- [ ] Callback retries/replay/old delivery не дублируют и не перезаписывают
  результат другого пользователя/работы.
- [ ] Restart и course upgrade сохраняют runtime assignments и grade mapping.

**Gate:** actual Moodle round trip выполнен на ноутбуке; synthetic-only test
не считается выполнением этой фазы.

## Task 11 Финальная приёмка релиза

- [ ] После review исправлений выпустить immutable final packages/images и
  повторить smoke/integration с финальными pins, не только candidate checkout.
- [ ] Опубликовать owner docs штатно; обновить Java целыми пакетами и проверить
  hashes установленных копий во всех native subprojects.
- [ ] Повторить final student/full/student privacy и selected native/PL/Moodle
  flow; новый code change требует только затронутых проверок плюс финальных gates.
- [ ] Создать один актуальный release report: commands/versions/SHAs/digests,
  coverage/scoring findings, failures fixed, limitations, rollback refs.
- [ ] Передать PR курса и всех owners с concrete problem/result/validation;
  не считать merge/publication готовыми без фактического результата.

## Команды и фазы для автора

Существующие команды быстрой разработки:

```sh
JAVA_HOME=/usr/lib/jvm/java-25-openjdk gradle -p PROJECT/student test
JAVA_HOME=/usr/lib/jvm/java-25-openjdk gradle -p PROJECT/student -Preference -Pgrading test
```

`PROJECT` — выбранный каталог проекта. Обычный compile-clean starter может
завершить Gradle с exit 1 из-за failures; читать tests report. Reference режим
существует только для предоставленного эталона. Для mutation проекта читать
также отдельный grade summary, а не обычный skipped test task.

Следующие команды — **проектируемый интерфейс**, ещё не существующий сегодня.
Их signatures закрепляются в owner docs вместе с реализацией; COURSE_ROOT,
NATIVE и PRIVATE_ROOT обозначают явные пути, без изменения HOME/CODEX_HOME.

```sh
quarto run tasks/_extensions/Afonenko-Course-Tools/course-core/entrypoints/project-checks.ts -- \
  --book tasks --output PRIVATE_ROOT/checks.json

python3 PLATFORM/tools/check-course.py \
  --source COURSE_ROOT --checks PRIVATE_ROOT/checks.json --scope declared \
  --inventory --report PRIVATE_ROOT/inventory.json

python3 PLATFORM/tools/check-course.py \
  --source COURSE_ROOT --checks PRIVATE_ROOT/checks.json --scope declared \
  --ready-only --backend host --java-home /usr/lib/jvm/java-25-openjdk \
  --scenarios reference,starter,contracts --report PRIVATE_ROOT/host.json

quarto run tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/entrypoints/export-course.ts \
  COURSE_ROOT NATIVE --instance pilot --checks-output PRIVATE_ROOT/checks.json

python3 PLATFORM/tools/check-course.py \
  --source COURSE_ROOT --checks PRIVATE_ROOT/checks.json --scope declared \
  --ready-only --backend container --jobs 1 --fresh \
  --scenarios reference,starter,contracts --report PRIVATE_ROOT/declared.json

python3 PLATFORM/tools/check-course.py \
  --source COURSE_ROOT --checks PRIVATE_ROOT/checks.json --native NATIVE \
  --scope delivery --backend container --jobs 1 --fresh \
  --scenarios reference,starter,contracts --jobs-dir PRIVATE_ROOT/jobs \
  --report PRIVATE_ROOT/container.json
```

Команды запускать из корня курса; первая сама собирает fresh full NativeRun и
пишет inventory, не выполняя Java. Declared scope работает без PrairieLearn
поставки. Exporter использует тот же Core collector и переписывает checks.json
для своего точного snapshot; старый report после изменения hash неприменим.

`--scope declared` включает только явно объявленные project-check.
`--inventory` ничего не компилирует; ready/incomplete/unsupported-runtime и
reference availability выводятся отдельно. `--ready-only` запускает ready
проекты и сохраняет incomplete в отчёте. В scope delivery такой флаг запрещён:
все selected вопросы обязаны быть полными. `--question QUALIFIED_ID` ограничивает
быструю проверку одной задачей; сокращённый receipt не разрешает полное staging.
`--expectations authored` включает только явно объявленные outcome assertions;
scoring исследование сохраняет raw results без навязанного нуля starter.
`--fresh` отключает verification cache для release acceptance.

Оба verify режима требуют checks manifest текущего source snapshot. Delivery
режим дополнительно проверяет manifest/payload native artifact; declared jobs
используют тот же runner и libs, но не создают PL questions. Reference-unavailable
сценарий отмечается явно, starter остаётся обязательным. Contracts запускает
только объявленные `tests/contract-cases.json`, неизвестные/отсутствующие declared
fixtures — ошибка. Без fixtures отчёт отмечает отсутствие negative evidence,
а не придумывает авторскую политику для класса.

Platform builder принимает `--verification-receipt PRIVATE_ROOT/container.json`
и отвергает неполный/устаревший receipt до immutable staging. Live проверки
владельцев: `python3 PLATFORM/tools/check-integration.py --native NATIVE
--receipt PRIVATE_ROOT/container.json --report PRIVATE_ROOT/pl.json` и
`python3 GATEWAY/tools/check-integration.py --config PRIVATE_ROOT/runtime.json
--report PRIVATE_ROOT/moodle.json`. Эти entrypoints также реализовать и
документировать в Tasks 5/6/9; credentials не передаются открытыми CLI аргументами.

CI pipeline: native render/model check → full export → executable verification
→ site/artifact checks → staging/import acceptance. Ни один helper в CI не
пересчитывает баллы и не строит native files самостоятельно.

Результаты проверки содержат версию выбранной команды, delivery/source hashes,
точные tests/runner/image и raw grading JSON. Совпадение количества проектов
без этих сведений не является доказательством повторяемости.

## Rollback и критерий завершения

Перед каждой существенной миграцией сохраняется recoverable commit/ref.
Rollback возвращает course snapshot и всю согласованную матрицу пакетов,
а не только один vendored файл. Native immutable versions и runtime data не
удаляются при откате. Изменение grading policy/UUID после результатов требует
явной migration/regrade политики.

Релиз завершён при наличии verified references и объяснённых starters
в ready declared inventory и всей selected delivery,
negative coverage evidence, installed-release delivery, browser/privacy/download
checks и actual Moodle → gateway/PL → Moodle результата. Любой оставшийся
incomplete selected project или ручное исправление native artifact блокирует
релиз. Scoring модель сама по себе не является общим критерием реализуемости.
