# SP native attempt с Core f10

3 ноября 2026. Один разрешённый root штатный SP export-course.ts на installed Core `f10c4e6527b1834651d59a95a0ef4373357cd8f0` + PL `3a6c1c14254f1ca547c3bc2577d80007c83bdcf0`, SP HEAD `e579e0e0bfe20403f6b07df2f04a64e5e6c5d7d8`. Результат: **FAIL_NATIVE_CAPTURE**, exit1, wall28.143593671000417s. Timeout120s с TERM/kill grace5s не достигнут; retry0.

Команда, cwd и env сохранены в [sp-native-f10-execution.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-native-f10-execution.json); stdout/stderr в [sp-native-f10.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-native-f10.log). Использован exact published Platform2.5.2 registry [sp-native-paired-published-runtime-profiles.json](sp-native-paired-published-runtime-profiles.json) прежнего8.234s запуска, без повторного fetch/pull/build. Persistent `XDG_CACHE_HOME=/home/tolya/JavaCodex/.cache`, `QUARTO_RUN_NO_NETWORK=true`, `DENO_NO_PACKAGE_JSON=1`; instance pilot, book tasks, новые public/private paths. Прежние paired artifacts и logs сохранены.

## Наблюдаемый путь

Core pre завершился inputs7, Download pre завершился. Native capture обработал все семь QMD: index; labs/work-environment; labs/data-processing; labs/version-control; optional/project-maintenance; delivery/units/sec-unit-version-control-restore-selected-state; delivery/units/sec-unit-project-maintenance-release. Старый `BODY.DELIVERY_PHASE_UNSUPPORTED: Shortcode не квалифицирован` на project-download не повторился: actual установленный Lua handler evidence прошёл прежнее препятствие в work-environment и следующие реальные документы.

Далее отработал QRC post: разрешено4ссылки, отложено27, целей19, страниц7; затем Site collect. Core post начал финализацию и сообщил outputs7, затем отказал:

`CoreContractError: OPERATION_CHANGED: Изменён состав native source: labs`.

Первый стек: `course-core/runtime/source-snapshot.ts:237` (`assertSources`) → `runtime/operation.ts:1859` (`finishNativeOperationInternal`) → nativeHookProgress/finishNativePostHook/entrypoints/post.ts. Outer wrapper: `runtime/assignment-execution.ts:245`, `OPERATION_INCOMPLETE: Native capture child failed: 1`.

Это наблюдаемый отказ проверки состава native source на финализации; конкретный изменившийся directory entry и его создатель данным log не установлены. Второго диагностического build/inspect/render и новой обвязки не добавлено. Guard не обходился, Source не правился, автоматического retry нет. Root уведомлён сразу после ошибки.

Public/private outputs не созданы. Complete/seal и успешная полная capture generation, upload/Workspace descriptors, ZIP/conditions/private acceptance не достигнуты. Outputs7 в progress log сами по себе не подтверждают complete/seal. Batch counters недоступны; числовой inspect/probe count не выведен из механизма исходников. Runtime/grader-image/deploy приёмка не выполнялась; это native capture limitation до доставки.

## Сохранность и допуск

Java и SP git status после попытки clean. Installation commits сохранены, дополнительного commit после запуска нет. Partial generated owner artifacts сохранены. UI/прочие providers/учебные файлы/UUID/оценки/DB/production не менялись. Java pipeline, повторный smoke/hash/statusbuild, push и PR не запускались.

Собственный первичный допуск: chat01a1a253-e067-7583-bf86-ecc79bd9b33f, path `/root/install_shortcode_candidate`, baseline01a1a253-e09d-7bd0-b06a-09436efd3df6. Первичный rollout и строки приведены в [core-pl-candidate-installation-f10-model-evidence.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-pl-candidate-installation-f10-model-evidence.json). Перед передачей перечитан весь неизменный исходный интервал: session_meta1 и единственный turn_context8; top+nested gpt-6.1-sol/medium согласованы. Standard назначен root, service_tier отсутствует. Глобальные settings и нормативные model docs не менялись.
