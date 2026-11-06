# SP: один installed native route принятой пары

План записан до запуска. Exact SP HEAD66c4c5110b03e7ac9e9132eff6b9f0bfff0b6190, installed Core332ea585d7b09f07c3f3886943d58a97a528078a + PL3a6c1c14254f1ca547c3bc2577d80007c83bdcf0. Source обеих зависимостей independently clean; installation/provenance отдельно принята. Source/пины/UUID/файлы студентов/сервер не менять, субагентов нет. Только один штатный tasks export-course.ts, pilot пятьworks/семьprojects, upload+Workspace. Старый failed sp-batch-smoke.log/output сохраняются. Java native route не запускается.

## Primary admission

Ожидаемая пара Sol6.1/high, Standard; собственный chat01a1a20e-59d5-7de3-a37c-f5294d684f36, path/root/sp_batch_adoption; baseline01a1a20e-5a0b-7371-bb72-1e5ac5965329 неизменён. Первичный session_meta собственного rollout `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T18-36-51-01a1a20e-59d5-7de3-a37c-f5294d684f36.jsonl`, строка1, устанавливает ID/path. Прочитаны все6turn_context от baseline: строки8,188,422,471,521,596; последнее возобновление01a1a240-f951-7752-993a-682f6d0b0db3. Все верхние model/effort и nested collaboration_mode.settings.model/reasoning_effort совпадают gpt-6.1-sol/high. Standard назначен root; primary service_tier отсутствует. Fast/Ultrafast/глобальные configs не менялись; нормативный model-admission.md не менялся. Свидетельство не утверждает скрытую серверную маршрутизацию.

## Registry и границы

Actual SP CI/toolchain.json закрепляет Platform2.5.2/626b665d1c8d18b61480ce91c7bc8f7d41f99bdb; fishImage06a445e1…, JavaImage17122d53…, Workspace imagea547a741…. Registry текущего Platform Sourceb37 содержит другие image identities и для этого прохода не использован.

Root указал сохранённый R0 `/home/tolya/JavaCodex/java-research-20261103/run-ultra-01a1a144-b034/R0`. Только там прочитаны filenames/report: три metadata файла, asset bytes отсутствуют. По явному разрешению root выполнен один GET exact published v2.5.2 asset `https://github.com/Afonenko-Course-Tools/prairielearn-platform/releases/download/v2.5.2/published-runtime-profiles.json`; сохранён `reports/sp-native-paired-published-runtime-profiles.json`. Bytes10172 и SHA2561d62cd44bc854a3f4234e80afae38cb9db8a46c22587350353a90d46da2336e8 совпали с прежним course asset pin. Fish/Java profile image identities точно совпали с CI/toolchain.json. Это чтение нужного published registry, без image pull/build, synthetic receipt или смены production pins.

## Команда и приёмка

Запускается из `/home/tolya/JavaCodex/java-release-20261110/system-practice`:

```sh
XDG_CACHE_HOME=/home/tolya/JavaCodex/.cache QUARTO_RUN_NO_NETWORK=true DENO_NO_PACKAGE_JSON=1 timeout --signal=TERM --kill-after=5s 120s /opt/quarto/bin/quarto run tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/entrypoints/export-course.ts /home/tolya/JavaCodex/java-release-20261110/system-practice /home/tolya/JavaCodex/java-release-20261110/reports/sp-native-paired-public --instance pilot --checks-output /home/tolya/JavaCodex/java-release-20261110/reports/sp-native-paired-private --runtime-registry /home/tolya/JavaCodex/java-release-20261110/reports/sp-native-paired-published-runtime-profiles.json --book tasks
```

Глобальный timeout120s на process tree, kill grace5s; один параллелизм. Cache с самого старта существует и writable. HOME/global config не меняется. Новый stdout/stderr log `reports/sp-native-paired.log`; wall time/exit — [sp-native-paired-execution.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-native-paired-execution.json). Public/private outputs до запуска отсутствуют; прежние artifacts не удаляются.

На успехе читаются только уже созданные generation/coverage/descriptors/ZIP/conditions/private bundles; actual grader-image/deploy отдельно. Ранее successful Source/R0–R6/ordinary UUID/Circle/PLtests, full inventories/hash не повторяются. При новой ошибке exact stage/error передаётся root, без fixture retry chain/guard bypass/Source edits. Source `nativeProjectContextMetrics` существует на prepared result, но CLI не печатает return value и publishNativePrepared возвращает delivery; если эти counters не сохранены штатными artifacts, числовой batch count останется unavailable, новые instrumentation/подмены API для него не добавляются. Capture success не означает завершённый export.


## Фактический результат единственного запуска

**FAIL_NATIVE_CAPTURE**, exit1, wall8.234322895998048s;120s timeout не достигнут. Это одна новая попытка на принятой installed паре, без повторного render/inspect/smoke. Новый log [sp-native-paired.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-native-paired.log), точная команда/env/exit/time [sp-native-paired-execution.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-native-paired-execution.json).

Достигнутый путь:

1. Штатный Core pre-hook завершился, log сообщил actual owner inputs=7.
2. Штатный Download3.5.0 pre-hook завершился.
3. Native render обработал `[1/7] index.qmd`, затем начал `[2/7] labs/work-environment.qmd`.
4. Core early-filter.sh/early-context.lua:64 вызвал assignment producer; `course-core/document/assignment-producer.ts:64` отказал `BODY.DELIVERY_PHASE_UNSUPPORTED: Shortcode не квалифицирован`. Pandoc conversion завершилась exit83; outer Core `runtime/assignment-execution.ts:245` выдал `OPERATION_INCOMPLETE: Native capture child failed: 1`; общий entrypoint exit1. Structured child stderr сохранён в log.

Owning boundary — Core assignment producer qualification native AST при early capture реального SP work-environment condition. Старый Authority unavailable не повторился: producer child теперь запущен, оба pre-hooks прошли. Это не доказывает complete/seal или delivery. Причина конкретного неразрешённого shortcode должна рассматриваться владельцем Core с actual SP source; guard не обходился и учебное условие не упрощалось. Root уведомлён об exact stage/error. Новых Source обходов, fixture/retry chain не было.

`sp-native-paired-public` и `sp-native-paired-private` не созданы. Полная capture generation/seal, полнота7project facts, contract aggregate/probe count, upload/Workspace descriptors/seed/gradedFiles, final student ZIP/conditions и private checks acceptance **не достигнуты**. Batch metrics unavailable; из source механизма числовой inspect count не выведен. Download post-hook/QRC post-hook на finished selected inputs также не достигнуты; успешны только указанные pre-hooks. Отдельная grader-image/runtime/deploy приёмка не выполнялась.

Partial generated native owner artifacts сохранены, чужие файлы не удалялись. Старые failed logs/output сохранены. SP git status после прохода clean: tracked Source/pins/UUID/студенческие файлы не изменены. Новый registry artifact exact published2.5.2 сохранён как вход этого запуска; никакого image pull/build/OCI/сервера/CI/commit/push/PR. Java native route автоматически не запускается, отдельное назначение root сохраняется.
