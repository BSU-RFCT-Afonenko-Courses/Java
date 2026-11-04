# PL: индекс sourceRows для native checks

## Допуск исполнителя

Назначение: `gpt-6.1-sol` / `medium`, Standard. Собственный chat ID `01a1a1f8-6b12-74e1-afa6-2f02c812ea9a`, путь `/root/pl_source_selection_index`, исходный turn ID `01a1a1f8-6b3e-79e0-8e5b-ba2e70879141`, root turn ID `01a1a1ee-79d1-7653-865d-9c6e1955f926`.
Первичный источник: `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T18-12-54-01a1a1f8-6b12-74e1-afa6-2f02c812ea9a.jsonl`: `session_meta` строка 1 подтверждает собственные ID/путь и provenance model; `turn_context` строка 8 подтверждает верхнеуровневые `model=gpt-6.1-sol`, `effort=medium`, вложенные `collaboration_mode.settings.model=gpt-6.1-sol`, `reasoning_effort=medium`. Противоречий нет, допуск пройден. Явный service tier в первичных записях отсутствует; override скорости не задан.

## Граница до изменения

Base PL `5a04576e0b288e481fbbf55ffe8fa16be56ed77a`, ветка `codex/java-release-20261110`, чистое дерево. Причина E06 подтверждена чтением: каждый вызов `origin` в `projectNativeChecks` вызывает `nativeRawSourceSelection`, который заново проходит весь `sourceRows` и создаёт Map.

Выбран lifetime одного вызова `projectNativeChecks`: приватный Map готовится один раз по неизменному same-S массиву и используется closure для всех source/test/reference/verification/fixture selections этого проекта. Фактическая граница — проект, не курс. Существующий `native-course.bySource` используется для других witness-проверок; протягивание его через materialization не требуется.

Планируемые файлы: `application/native-check-inventory.ts`, `application/native-check-projection.ts` внутри `_extensions/course-prairielearn`; адресное расширение `tests/native-raw-origin-values.ts` для нового подготовленного входа, если прежние проверки напрямую его не покрывают. Standalone `nativeRawSourceSelection` сохраняет массивный input. Никаких cache/registry/framework, новых правил или изменения Core authority.

Прочитаны `AGENTS.md`, `spec/index.md`, текущие native raw-origin executable проверки и общий русский редакционный контракт из локального Core source. Установленный Java candidate не изменяется.

## Изменение и результаты

`prepareNativeRawSourceSelection` строит закрытый Map и проверяет duplicate/unsafe same-S rows один раз. Возвращённая closure принимает только отдельный file/submission path, сохраняет Core schema parse, safe paths, size/hash/raw membership, shared-origin v1/v2. Standalone `nativeRawSourceSelection` готовит тот же локальный индекс по прежнему массивному input. Factory экспортируется только из application-модуля, публичный barrel не расширен. Production `projectNativeChecks` готовит closure один раз после compiler selection.

Для F выбранных файлов, S source rows и Q project projections число полных обходов индекса изменено с F×S на Q×S плюс F Map lookup. Per-file byte hash остаётся обязательным. Время полного курса **UNKNOWN**. Общего cache, нового resolver, изменения Core authority и source pin нет.

Адресно расширен существующий raw-origin value test: оба входа получают те же 9 invalid mutations (всего 18 отказов), в том числе unsafe невыбранная row; оба отказывают shared origin для legacy v1; одна подготовленная closure выбирает local/shared v2 origins и сохраняет отдельный submission mapping. Literal call-order/performance test не добавлен.

Команды из PL source repo:

- `quarto run tests/native-raw-origin-values.ts /tmp/pl-source-selection-index-core` — exit 0; `refusals=18`, `sourceReads=0`, legacy shared refusal и legacy hashes сохранены; дополнительно проверены prepared v2 paths.
- `quarto run tests/native-reference-projection.ts /tmp/pl-source-selection-index-core` — exit 0; `PASS_PURE_PROJECTION_NOT_OPERATION_AUTHORITY`, reference helper/replacement сохранены, student не изменён; отказы role/bytes/missing-group/configured-primary.
- `deno check --no-remote _extensions/course-prairielearn/application/native-check-projection.ts _extensions/course-prairielearn/application/native-check-inventory.ts tests/native-raw-origin-values.ts` — exit 0 после последнего изменения теста.
- `git diff --check` — exit 0.

Первый запуск через простой `deno run --no-remote --allow-read` не получил Quarto import mapping для `stdlib/path`; использован штатный `quarto run`. Первый расширенный typecheck обнаружил отсутствие union narrowing в старом `selected.projectRelativePath` assertion и новом `ownerRelativePath`; добавлены проверки `in`, повторный typecheck успешен.

Для value imports создан только временный read-only symlink `/tmp/pl-source-selection-index-core/_extensions/course-core` к `/home/tolya/JavaCodex/java-release-20261110/java/tasks/_extensions/Afonenko-Course-Tools/course-core`, указанный координатором installed Core 9.3.4 / `a478d2cc0dfb19e0bebd99108815ac8ef1fe85a8`. Это value-level проверки, не genuine operation/authority route и не полный CI. Прочитан executable raw-origin контракт установленного Core `artifacts/contracts.ts` (`rawSource` exact unchanged raw copy, schema/path/audience bounds) и same-S coverage в `runtime/assignment-provenance.ts`. Java/Core не изменены, vendor не копировался/не хешировался. Alias удаляется после проверок.

Полный tests/check, export/native/render, CI replay, Docker, benchmark и full hashes не запускались по cost limits. Предыдущий hosted CI head не повторялся. Push/PR update и независимый обзор остаются у координатора.

Итоговый допуск: повторно проверен весь доступный интервал от исходного turn ID выше; единственная запись `turn_context` строка 8 сохраняет ожидаемые `gpt-6.1-sol`/`medium` в верхних и вложенных полях, других записей/расхождений нет. Нормативный `docs/model-admission.md` не изменён.

Коммит PL source: `35496cdd547d6ad2263fe171e451fb251c793c83`; после коммита `git status --short` пуст, `git show --format= --check HEAD` успешен.
