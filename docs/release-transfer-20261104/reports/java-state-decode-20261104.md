# Стоимость чтения и decode сохранённого Java state

4 ноября 2026. Один разрешённый адресный микрозамер, cap 15 s, retry 0. Единственный Deno process завершился с exit 0 за 443,053 ms по внешним монотонным часам; stderr 0 B. Новых Native/course запусков нет.

## Результат

Сохранённый `state.json` generation `f7da2eb2-c4da-41cd-b7e3-1b9ed89a37ea`: 2 184 627 B, 128 inputs, 4 751 rows, 7 305 memberships. Единственное `Deno.readTextFile` заняло **6,131 ms**. Это чтение вместе с преобразованием UTF-8 в строку; состояние файлового cache неизвестно, cold IO не измерялся.

| Стадия | Первая итерация, ms | Медиана 8 итераций, ms |
|---|---:|---:|
| `JSON.parse` новой строки в object | 10,087 | 8,257 |
| actual `decode(stateSchema, value, "OPERATION_BINDING")` | 25,495 | 11,445 |

Все восемь независимых parse/decode timings сохранены без округления в [result.json](java-state-decode-20261104/result.json). Каждая итерация заново парсит одну и ту же прочитанную строку; decode получает новый object. Первая итерация включена в медиану. Предварительного decode/warmup не было. Read сделан один раз, повторные file reads не измерялись. Clocks охватывают вызовы стадий; возможный GC входит в затронутый интервал и отдельно не измерялся. Внешние 443,053 ms включают startup/module loading, а их самостоятельная стоимость не выделена.

## Точность схемы и граница опыта

Runtime — bundled `/opt/quarto/bin/tools/x86_64/deno`, сам процесс подтвердил `Deno.version.deno === "2.7.14"`. System Deno и compiler/typecheck не запускались. Pin Core `d7cc10eaa21fa35bbe32d73cc4afa216002393da` задан в `java/providers.json` и сохранён в `java-native-d7-acceptance.md`. Использованы модули installed `java/tasks/_extensions/Afonenko-Course-Tools/course-core/`, а не current Core checkout.

`stateSchema` — локальная, неэкспортированная const в installed `runtime/operation.ts`. Во временный [schema.ts](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/java-state-decode-20261104/schema.ts) перенесены **без изменения выражений** `pathSchema`/`rowSchema` из строк 170–178 и `stateSchema` из строк 192–254. Прямые pure зависимости импортированы из того же installed module: vendored `vendor/zod/v4/index.js` и `infrastructure/quarto-version.ts:SupportedQuartoVersionSchema`. Actual функция `decode` импортирована из `document/contracts.ts:85–98`: вызывает `schema.safeParse`, проверяет success и возвращает parsed.data. Замены decoder/schema и облегчённой projection нет. Schema ID — `course-native-operation-private-v2`. Это **extracted schema microstage**, а не исполнение actual guard.

Actual `operation.ts` не импортирован. `assertNativeAssignmentCapture`, `assertReservationCurrent`, Source snapshots/fences, correlation checks, Quarto, inspect, render, build, JVM и профилирование не вызывались. Состояние не принималось как действующая операция. Graph импортов состоит из локальных schema/contract modules и vendored Zod. Запуск с `--no-config --no-lock --no-remote --deny-net --deny-env --deny-run --deny-write` и `--allow-read` только для точного savedstate пути. Сеть/зависимости/установка не использовались.

Результат отвечает на стоимость этих стадий в одном конкретном процессе Deno 2.7.14 и на этих bytes. Он не устанавливает стоимость repeated early child startup/read/decode на каждом документе, correlation, AST, HTML writer, whole-course speedup или причину Java timeout 900 s. Умножение этих микрозначений на 128 не является измеренным total. Корректность удаления correlation/state schema checks этим опытом не доказана. Следующий запуск ради более уверенной цифры не назначен.

## Сохранность и собственный допуск

[probe.ts](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/java-state-decode-20261104/probe.ts) содержит путь, код и схемы; persisted state bytes и runtime environment из state никуда не копировались. В raw result только allowlisted размеры, количества, timings, schema/runtime/status. Student/Source/state файлы не менялись; commit/push нет.

Первичный допуск исполнителя: [admission.json](java-state-decode-20261104/admission.json), собственный chat `01a1a4e9-efb6-70f0-bcb5-4fd565ed34e6`, path `/root/java_state_decode`, исходный turn `01a1a4e9-efdf-7db0-bacc-261417a32445`. Все `turn_context` от исходного хода: line 8; top model `gpt-6.1-sol`, effort `medium`, nested model/effort совпадают. Standard предписан; global config не менялся. Это проверка зарегистрированных первичных параметров, не свидетельство о скрытой маршрутизации.
