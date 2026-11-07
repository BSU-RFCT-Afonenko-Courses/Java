# Адресная native-интеграция Java identifiers

Исполнитель `/root/native_identifiers_integration`, chat `01a1a1f0-eb60-77a1-889c-cbbf22e8f66a`, исходный turn `01a1a1f0-eba2-72b2-88e2-0c45910087c2`. Первичный источник: `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T18-04-43-01a1a1f0-eb60-77a1-889c-cbbf22e8f66a.jsonl`, `session_meta` ordinal0 и `turn_context` ordinal7. Назначение и actual: `gpt-6.1-sol/high`; верхние и вложенные поля согласованы. `service_tier` не задан; Standard. Нормативный файл не менялся.

Java: чистый исходный HEAD `52f867364fdad49f1727d3339e66b7ad1875917f`, ветка `codex/java-release-20261110`. Quarto actual `/opt/quarto/bin/quarto --version`: `1.11.5`.

Выбран существующий recipe PL `tests/native-answers-integration.ts` и assignment lease штатного installed `native-entry.ts`. Один статический owner QMD содержит минимальный manual вопрос, assessment ID/H1 `sec-lab-objects` и UUID `b61a155f-7ea3-5dfa-b301-72a4656ace98`, прочитанные из actual `tasks/labs/objects.qmd`, а также `@sec-lab-objects`. Installed Core lease → один native render с actual pre/post и ранним/поздним JSON transport → actual Core current operation → installed PL adapter validateCourse/business. Нужны приём UUID native business consumer и actual HTML anchor/xref. Это не отдельный экспорт infoAssessment, не проверка БД или полный курс.

Команда до запуска сообщена координатору:

```sh
timeout --signal=TERM --kill-after=5s 120s env XDG_CACHE_HOME=/home/tolya/JavaCodex/java-release-20261110/java/.cache/codex-tools QUARTO_RUN_NO_NETWORK=true DENO_NO_PACKAGE_JSON=1 /opt/quarto/bin/quarto run /home/tolya/JavaCodex/java-release-20261110/reports/native-identifiers-integration.ts
```

Стоимость: один QMD, один render, без проектов/JVM/Docker/сети. Outer timeout120с. Счётчики берутся из `op.metrics`, новый profiler не вводится. Source/unit/typecheck и R0–R6 не повторяются. Fixture и результаты сохраняются рядом с этим отчётом.

Уточнение до повторного запуска: первоначальная fixture имела TS escaping error (exit1,0.85с, до child; [native-identifiers-setup-parse.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/native-identifiers-setup-parse.log)), затем assignment preparation отказала до inspect/render из-за отсутствующей actual Core authority capsule (`BODY.PRODUCER_INPUT_UNQUALIFIED: Authority unavailable`, exit1,0.83с, [native-identifiers-assignment-authority.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/native-identifiers-assignment-authority.log)). Authority не создавалась и product не менялся. Выбран в точности ordinary capture существующего `tests/native-answers-integration.ts`: pre-hook вызывает installed `prepareNativeAdapter`, genuine current operation вызывает `validateCourse/business`. Та же команда и предел120с. AssignmentDelivery и export не доказываются этим маршрутом. Координатор уведомлён до запуска.

## Фактический результат

`PASS_NATIVE_IDENTIFIERS`, окончательная команда завершилась exit0. Измеренный внутри recipe native producer→consumer интервал — **4.877с** (`performance.now`, после fixture setup, до записи результата). Сохранены [native-identifiers-result.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/native-identifiers-result.json), полный [native-identifiers-integration.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/native-identifiers-integration.log), успешный owner `native-identifiers-owner-9ce19f01b464889c`.

Genuine installed PL `createNativeAdapter.validateCourse` вызван через actual Core current operation; public proof `course-native-release-proof-v1`, operation `f3afc261-4345-4091-8f01-adc021c835c2`, documents `[index.qmd/html]`, assessmentIds `[sec-lab-objects]`, validatedAdapters `[prairielearn]`. Данные взяты через настоящий ранний/поздний native transport, не прямой импорт новой pure function. Consumer `business('index.qmd').assessment.uuid` буквально равен `b61a155f-7ea3-5dfa-b301-72a4656ace98`; authenticated input assessment.id — `sec-lab-objects`.

Actual HTML `_generated/render/index.html`:

```html
<h1 id="sec-lab-objects">Классы</h1>
<p>Связь с разделом: <a href="#sec-lab-objects" class="quarto-xref">Глава&nbsp;1</a>.</p>
```

Строки `j25` в итоговом HTML нет. Проверяется конкретный перенесённый Java ID/UUID, а не все97работ.

Штатные `op.metrics` успешного пути: `inspectCalls=1`, `childRenders=1`, `fullWalks=1`, `fullSourceFences=3`, `localSourceFences=11`, `sourceDirectoryReads=23`, `runtimeHashReads=5`/40305bytes, `sourceHashReads=5`/2520bytes, `outputHashReads=2`, `nativeMetadataBytes=440`, `fullFences=1`, `currentnessChecks=9`. Authority/project/resource counters0. Это метрики маленького owner recipe; метрики полного Java курса не измерялись и ускорение курса не заявляется.

До успеха были два завершённых native render с отказами consumer из-за сокращения fixture: `submission.mode=text` заменён на существующее `upload` (канонический answer-spec `submission:text` остаётся); затем добавлено обязательное `assignment:{mode:assessment-id}`, которое actual Java наследует из assessment-defaults. Полные отказы сохранены в [native-identifiers-setup-submission.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/native-identifiers-setup-submission.log) и [native-identifiers-setup-assignment-policy.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/native-identifiers-setup-assignment-policy.log); соответствующие owner каталоги не удалены. Итого5запусков one-off script:2до native child,2с render/отказом setup,1успешный producer→consumer. Это исправления fixture, не repeated baseline/shadow и не дефект установленного продукта.

Source/candidate не исправлялись, commit не создавался. Java финально чистый на том же HEAD `52f867364fdad49f1727d3339e66b7ad1875917f`. Полный исходный интервал модельного допуска перед передачей перепроверен от того же turn: одна запись turn_context, `gpt-6.1-sol/high`, вложенные поля согласованы, service_tier отсутствует.

Граница: доказаны ordinary native producer→installed consumer acceptance нового поля UUID и реальная связь нового HTML anchor/xref. Не доказаны independently qualified assignment capture/delivery (нет authority capsule), отдельный infoAssessment export, полный курс, серверная БД, image/OCI и production. Unit/typecheck/source checks PL и R0–R6 не повторялись; данные/сервер/production не затрагивались.
