# Штатная SP попытка после book.site-url

3 ноября 2026. После независимого ACCEPT `course-book-site-url-review.md` и отдельного разрешения root выполнена ровно одна existing SP native command на SP `1626aa0792301f6c91cf2a04bd4d242f89736578`, Core `830381697beea4106655965e10605cc13987199b`, PL `da2ad6b6c00891590e59e5abccf3d1eb4ff552f7`. Exit1, wall23.466347102s, timeout120s/TERM grace5s не достигнут; retry0. Начало UTC `2026-11-03T17:34:10.372221+00:00`, конец `2026-11-03T17:34:33.838584+00:00`.

Полные command/cwd/env/refs в [sp-native-book-url-execution.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-native-book-url-execution.json), stdout/stderr в [sp-native-book-url.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-native-book-url.log). Новые outputs `reports/sp-native-config830-bookurl-public` и `reports/sp-native-config830-bookurl-private`, без новых CLI flags или URL substitutions. Тот же exact published Platform2.5.2 registry `reports/sp-native-paired-published-runtime-profiles.json`; persistent `XDG_CACHE_HOME=/home/tolya/JavaCodex/.cache`, `QUARTO_RUN_NO_NETWORK=true`, `DENO_NO_PACKAGE_JSON=1`. Используется реальный course book.site-url из config1626. Повторного local CI/typecheck/test запуска нет.

## Наблюдаемый результат

Core pre inputs7; Download pre готово. Обработаны все7QMD: index; labs/work-environment; labs/data-processing; labs/version-control; optional/project-maintenance; delivery/units/sec-unit-version-control-restore-selected-state; delivery/units/sec-unit-project-maintenance-release. QRC post:4ссылки разрешено,27отложено,19целей,7страниц. Site collect выполнен. Core post outputs7,7текущих native-ts author документов, финализация готово0.2s. Download post завершён без прежнего OPERATION_INCOMPLETE. Native child создал `_generated/prairielearn-native-render/index.html`.

Прежний `PL.REFERENCE_BINDING_REQUIRED` после переноса существующего URL не повторился. Следующий actual этап — переписывание body reference links для materialization. Первый cause:

`QRC.TARGET_UNKNOWN: неизвестная ссылка system-practice-theory:sec-work-environment`.

Источник: `HTML-результат labs/work-environment.qmd`; ID `system-practice-theory:sec-work-environment`. Диагностика: проверить namespace/ID/текущие страницы или каталог импорта. Первый stack: QRC `diagnostics.ts:15` → `domain/catalog.ts:14` (`resolve`) → `entrypoints/body-links.ts:24` → Core `body-export/links.ts:86` (`rewrite`), далее рекурсивный rewrite links.ts:195/192/186. Это первый наблюдаемый cause; самостоятельная подмена namespace/ссылки/import или Source правка не делалась. Root уведомлён сразу, остановка без retry и без обхода guard.

Public/private export directories отсутствуют; transport descriptors/ZIP/conditions/private acceptance не достигнуты.7QMD — число документов, а не подтверждённое число questions/projects/transports. Успешные batch counters и complete export по этому failed run не заявляются. Runtime images/deployment не проверялись. Отдельные inspect/render/hash/tests/Java runs ради диагностики или цифр не выполнялись.

## Сохранность и допуск

После попытки Java/SP git status clean. Config commit и все прежние capture/export evidence сохранены; owner artifacts не очищались. Source/config/providers/student data/IDs/UUID/цели не менялись. Push/PR/production не выполнялись.

Собственный прежний интервал chat01a1a261-97e1-7352-ae02-b6721eb39fb9, path /root/install_empty_scratch_pair, baseline01a1a261-9811-7ca3-8a48-e4482c3beafc. Полный первичный rollout повторно прочитан: turn_context8,155,244,342,393 top+nested gpt-6.1-sol/medium PASS; baseline не сдвигался. Standard prescribed, globals untouched.
