# PL: передача functional profiles в Core assignment

## Собственный первичный допуск

Назначение `gpt-6.1-sol` / `medium`, Standard. Собственный chat ID `01a1a1f8-6b12-74e1-afa6-2f02c812ea9a`, agent path `/root/pl_source_selection_index`, исходный turn ID `01a1a1f8-6b3e-79e0-8e5b-ba2e70879141`, root turn ID `01a1a1ee-79d1-7653-865d-9c6e1955f926`.
Первичный источник `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T18-12-54-01a1a1f8-6b12-74e1-afa6-2f02c812ea9a.jsonl`: `session_meta` строка 1 подтверждает собственные ID/path/provenance model. Полный интервал от прежнего исходного turn включает `turn_context` строки 8 и 181; оба имеют верхние `model=gpt-6.1-sol`, `effort=medium` и вложенные `collaboration_mode.settings.model=gpt-6.1-sol`, `reasoning_effort=medium`. Текущий turn `01a1a223-2067-7e91-a529-c52fac37d250`. Расхождений нет; допуск пройден. Service tier в первичных записях отсутствует; override скорости не вводился. Нормативный Java `docs/model-admission.md` не менялся.

## Чтение перед изменением

Прочитан `reports/core-native-delivery.md`, PL `application/native-entry.ts` и оба production callers: `entrypoints/export.ts`, `entrypoints/export-course.ts` внутри `_extensions/course-prairielearn`. Source PL base `35496cdd547d6ad2263fe171e451fb251c793c83`, дерево чистое.

Точная проверка functional-only предпосылки выявила контрпример, сообщённый root **до кода**: `export.ts:5–9` передаёт непустой `explicitProfiles` буквально; только env fallback исключает `student/full`. Вызов `export.ts ROOT tasks WORK BINDING OUTPUT full` передаст `options.profiles=['full']`. `export-course.ts:37–49` не передаёт profiles; для него нужен предложенный env fallback. Исполнитель не добавляет PL normalizer; решение границы отказа ожидает root.

Установленные Java/SP/Core/PL не изменены. CI/render/hash/staged и тестовые fixture не запускались. Адресный typecheck ожидает готового Core writer; запуск без связи с новым динамически загружаемым Core не будет выдан за проверку API композиции.

Root уточнил намерение: прежний CLI с explicit `full` сохраняется; обе ветви handoff должны одной expression удалять только `student/full` и пустые значения, без скрытия duplicates/невалидных functional имён. При проверке существующего descriptor comparison обнаружено второе точное место, переданное root до дополнительной правки: `native-entry.ts:249–257` фильтрует projection только у `descriptor.identity.profiles`, но сравнивает с сырым `options.profiles`. Поэтому после handoff `['full'] → []` comparison ошибочно отвергнет `['full']` против `[]`. Предложен один локальный `functionalProfiles` для handoff и comparison; ожидается согласование минимальной дополнительной дельты.

## Согласованная дельта

Root разрешил один локальный `functionalProfiles` const для handoff и сравнения. В `application/native-entry.ts` одна expression `(options.profiles ?? (Deno.env.get("QUARTO_PROFILE") || "").split(",")).filter(p => p && p !== "student" && p !== "full")` сохраняет порядок, дубликаты и невалидные непустые functional значения. `assignment.profiles` получает const; существующий comparison сверяет его с descriptor profiles после удаления projection. Comparison выполняется также для пустого functional списка, без прежнего условного пропуска по length.

Итак, `['full']`/`['student']` дают пустой functional список, mixed input сохраняет functional последовательность. Core остаётся владельцем добавления `full` и валидации functional имён/дубликатов. Нового PL parser/resolver/API не появилось. Изменён один product file и один assignment callsite с согласованным descriptor comparison; prepare callback, captureInputs args, root/projection/assessment/input/sealed identity guards сохранены буквально.

`git diff --check` — exit 0. Core writer `/root/core_native_delivery` уведомлён о consumer delta; адресный typecheck ожидает его сообщения о готовности. Установленные Java/SP и pins не менялись.

## Граница проверки

Адресный **composition typecheck отложен до общей native приёмки** по разрешённому условию задания: `native-entry.ts` получает Core `capture.ts` через runtime `pathToFileURL(join(core, "capture.ts"))`, а `withNativeCaptureScope`/descriptor/lease имеют динамическую границу `any`. Самостоятельный `deno check native-entry.ts` не свяжет `assignment.profiles` с новым Core интерфейсом и не докажет совместимость. Для такой связи потребовался бы отдельный harness/fixture или изменение import boundary; они не создавались. Core writer уведомлён и проверяет собственные capture/admission types отдельно. Typecheck PASS, native PASS и реальная consumer приёмка здесь не заявляются.

Полный CI/render/hash/staged, старый replay и fake authority fixture не запускались. Исторический исходный интервал собственного допуска не сдвигался; в доступных двух turn_context от исходного хода до текущего исполнения model/effort и вложенные настройки согласованы. Push/review и установка pins остаются у root.

Коммит PL source: `3a6c1c14254f1ca547c3bc2577d80007c83bdcf0`; один файл, 6 additions / 2 removals. После коммита `git status --short` пуст.
