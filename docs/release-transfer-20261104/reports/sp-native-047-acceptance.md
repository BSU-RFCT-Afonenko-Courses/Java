# Штатная SP native попытка с Core 047 + PL f9

3 ноября 2026. Единственный разрешённый root attempt на installed Core `0478458a71f3acf34ad6d9983ac11023783dde3d` + PL `f9c00aed6de610dce7d9b7cb6e50c28ebfb9573e`, SP HEAD `a69081d7e3e01c9403ed39595548376f8c3ca1af`. Результат **FAIL_NATIVE_CAPTURE**, exit1, wall `25.647282166` s; начало UTC `2026-11-03T17:09:32.334326+00:00`, завершение `2026-11-03T17:09:57.981626+00:00`. Timeout120s/TERM grace5s не достигнут, retry0.

Полные command/cwd/env/exact SHA сохранены в `sp-native-047-execution.json`, stdout/stderr в `sp-native-047.log`. Штатный `quarto run tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/entrypoints/export-course.ts`, instance pilot, book tasks, новые public/private paths `reports/sp-native-047-public` и `reports/sp-native-047-private`. Exact published Platform2.5.2 registry `reports/sp-native-paired-published-runtime-profiles.json` переиспользован без fetch/pull/build. Persistent `XDG_CACHE_HOME=/home/tolya/JavaCodex/.cache`, `QUARTO_RUN_NO_NETWORK=true`, `DENO_NO_PACKAGE_JSON=1`.

## Наблюдаемый результат

Core pre: inputs7. Download pre готово. Обработаны все actual configured7 QMD: index; labs/work-environment; labs/data-processing; labs/version-control; optional/project-maintenance; delivery/units/sec-unit-version-control-restore-selected-state; delivery/units/sec-unit-project-maintenance-release. QRC post: разрешено4ссылки, отложено27, целей19, страниц7; затем Site collect. Core post сообщил outputs7 и «7 текущих native-ts author документов», завершил финализацию за0.3s. Прежний `OPERATION_CHANGED: Изменён состав native source: labs` не повторился. На данном реальном проходе исчезновение пустого supporting scratch больше не вызвало прежний false SourceChanged; отдельной диагностической попытки или повторного snapshot не было.

Следующий actual этап — штатный `project-download/entrypoints/post.ts`. Download начал финализацию материалов и за0.1s завершился ошибкой:

`CoreContractError: OPERATION_INCOMPLETE: Операция ещё не завершена`.

Первый stack: `course-core/runtime/operation.ts:2275` (`withCurrentNativeOperation`) → `project-download/infrastructure/runtime.ts:633` (`finishInternal`) → `runtime.ts:193` → `diagnostics.ts:22` → `runtime.ts:192` → `entrypoints/post.ts:4`. Outer wrapper: `course-core/runtime/assignment-execution.ts:245`, `OPERATION_INCOMPLETE: Native capture child failed: 1`; PL call site `application/native-entry.ts:284`.

Это первый наблюдаемый cause. Guard не обходился; дальнейшее объяснение внутреннего state данным log не установлено. После ошибки остановка без retry, дополнительных build/render/inspect/tests и Source fixes. Root уведомлён сразу.

Core post success не подтверждает полную успешную capture generation/complete/seal/delivery: child завершился exit1 в следующем consumer post. Public/private output directories отсутствуют. Upload/Workspace descriptors, ZIP/conditions/private acceptance и runtime/grader-image/deploy не достигнуты. Batch counters и числовой inspect/probe count не заявляются.

## Сохранность и допуск

После попытки Java и SP git status clean. Partial owner artifacts сохранены, очистки/удаления ради зелёного результата нет. UI/прочие providers/authored Source/student data/UUID/оценки/DB/production не менялись. Installation commits сохранены; push/PR не выполнялись.

Собственный primary допуск в `core-pl-candidate-installation-047-model-evidence.json`: chat `01a1a261-97e1-7352-ae02-b6721eb39fb9`, path `/root/install_empty_scratch_pair`, исходный baseline `01a1a261-9811-7ca3-8a48-e4482c3beafc`. Перед передачей прочитан весь неизменный интервал: единственный turn_context8, top+nested gpt-6.1-sol/medium согласованы. Standard назначен root, service_tier отсутствует; глобальные settings и нормативные документы не менялись.
