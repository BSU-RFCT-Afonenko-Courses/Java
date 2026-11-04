# Адресное исправление отказа Platform CI

Исходный Source: `0e4a12dc3f7a278081755879a95e856ae2ecc5a8`, ветка `codex/java-release-20261110`. Базовый предшественник: `865d01705f8e9acee59de09661d5c00c290f1583`. Отказ из [run 41655490056](https://github.com/Afonenko-Course-Tools/prairielearn-platform/actions/runs/41655490056) воспроизведён одним указанным case.

## Собственный допуск

Прочитан `java/docs/model-admission.md`, нормативный файл не менялся. Собственные первичные записи: `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T18-06-18-01a1a1f2-6186-7cf3-bda9-86bc7b2a34b5.jsonl`; чат `01a1a1f2-6186-7cf3-bda9-86bc7b2a34b5`, путь `/root/platform_ci_additional_sources`, исходный ход `01a1a1f2-61b2-7973-9ca4-791d38a9bec4`, root turn `01a1a1ee-79d1-7653-865d-9c6e1955f926`. Назначение `gpt-6.1-sol` / `high` совпало с верхнеуровневыми `model` / `effort` и вложенными `collaboration_mode.settings`. Standard задан поручением; отдельного `service_tier` в этих записях нет. Машинное свидетельство: `platform-additional-source-ci-admission.json` рядом с отчётом.

## Причина

Это неверное ожидание существующего теста, не дефект принятого release diff. Commit `5048286adc1a1a7bd4e41302d70ed2121ed4abdb` уже разрешил полный явно объявленный named Java reference без `additional-java-sources`: `prepare_job` выбирает только hashed inventory этого reference, ограничивает Java type/count/size и меняет `sourceFiles` только для приватного reference job после сравнения экспортированного student descriptor. Это поведение явно описано в `docs/reference-complete-source-tools.md` и имеет отдельные проверки в `tests/test_reference_complete_sources.py`.

Старый additional-source case удалял ZIP policy, затем требовал отказ на законном `reference:correct`, содержащем `Student.java` и helper `Base.java`. Поэтому утверждение `JobError not raised` на строке 442 закономерно. `tools/grading_job.py` и `tests/test_grading_job.py` идентичны между базой `865d017` и исходным `0e4a12d`; release runner change не вводил это поведение. Временная копия базового Source дала тот же отказ на той же строке.

## Минимальное изменение

Изменён только `tests/test_grading_job.py`: вместо запрета законного named reference проверяются реальные private/public успешные JDK25 jobs с helper-классом, точный набор source inventory и отсутствие student ZIP capabilities. Отдельно подготовленный starter сохраняет `Student.java` и не получает `Base.java`; contract case с ZIP при отсутствующей policy по-прежнему обязан дать `Unauthorized`. Сохранены исходные проверки обычного ZIP, public/private передачи, повторяемого сгенерированного архива и изменённой fixture. Production runner, приём внешних ZIP, registry и pins не менялись.

Эта проверка обнаружит потерю helper в private/public reference, расширение starter policy и разрешение contract ZIP без объявления. Отдельный существующий ZIP safety case проверяет отказ traversal, absolute/Windows/hidden paths, links/FIFO, overwrite trusted/primary/config, duplicate entries и prefix conflicts до запуска стадий.

## Адресные команды и результаты

Все Python команды выполнены из `platform` с `PYTHONPATH=tests`.

1. `python3 -m unittest -v test_grading_job.AdditionalSourceDeliveryTests.test_reference_extra_and_contract_zip_are_explicit_hashed_public_private_inputs` внутри sandbox: один ERROR до целевого сценария, `Unsafe parent identity: /`. В sandbox `/` имеет UID65534 при euid1000; production-проверку владельцев не ослабляли.
2. Та же точная команда вне sandbox на исходном `0e4a12d`: один FAIL, `JobError not raised`, строка 442, 28.826s.
3. Та же точная команда вне sandbox на базе `865d017` в отдельном `TemporaryDirectory`, `git archive` выбранных tracked inputs плюс неизменённые runtime dependencies: один FAIL, `JobError not raised`, строка 442, 24.319s. Временная копия удалена; рабочее дерево базы не менялось.

4. После изменения, вне sandbox:

   ```bash
   PYTHONPATH=tests python3 -m unittest -v test_grading_job.AdditionalSourceDeliveryTests.test_reference_extra_and_contract_zip_are_explicit_hashed_public_private_inputs test_additional_java_sources.AdditionalJavaSourcesTests.test_unsafe_names_links_duplicates_overwrite_and_prefix_fail_before_any_stage
   ```

   Два tests, OK, 36.607s; включая реальные private/public reference и обычные разрешённые ZIP сценарии основного case. `git diff --check` — OK. Diff: один тестовый файл, 34 добавленных / 4 удалённых строки.

Перед commit повторно проверен весь интервал собственных `turn_context` от исходного хода: 1 запись, верхнеуровневые и вложенные модель/усилие совпадают с назначением. Это отчёт исполнителя, независимая приёмка ещё требуется.

## Границы

Полный набор 667 tests, прежние 27/24 targeted unit, полная сборка, native Docker build, JDK hash/boundary cycles и retry hosted CI не запускались. Новый validator, runner, tracing или broad matrix не добавлялись. Проверки выполнялись на синтетических source fixtures с настоящим host JDK25 (`--release 25`), synthetic registry image digests не являются OCI attestation. Portal, реальные данные, публикация images и push/PR не затронуты. Перед push требуется независимый обзор координатором.

Commit исполнителя: `d14231d` (`codex/java-release-20261110`), только `tests/test_grading_job.py`. Рабочее дерево после commit чистое. Push/PR update не выполнялись.
