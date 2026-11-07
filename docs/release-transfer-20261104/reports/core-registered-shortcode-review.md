# Независимая рецензия registered shortcode

Интервал: Core `332ea585d7b09f07c3f3886943d58a97a528078a..f10c4e6527b1834651d59a95a0ef4373357cd8f0`. Только чтение исходников и сохранённых свидетельств; исходники, ветки, глобальные настройки и normative model docs не меняются. Рендер, inspect, build, hash, install и новые тесты не запускаются.

## Собственный первичный допуск

Назначение: независимый технический рецензент `gpt-6.1-sol / high`, Standard. Прочитаны `java/docs/model-admission.md` и `java/docs/agent-execution-proposal-20261029.md`.

Собственный чат `01a1a250-c1a3-7d41-bcaa-55c13debfecf`, путь `/root/review_registered_shortcodes`, исходный ход `01a1a250-c1de-78e0-838f-68346906f2f4`. Первичный источник: `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T19-49-23-01a1a250-c1a3-7d41-bcaa-55c13debfecf.jsonl`: `session_meta` строка 1; единственный `turn_context` собственного нового интервала, строка 8. Top-level model=`gpt-6.1-sol`, effort=`high`; вложенные collaboration_mode.settings model=`gpt-6.1-sol`, reasoning_effort=`high` согласованы. service_tier отсутствует; Standard задан назначением, серверный tier не аттестуется. Чужие модельные интервалы не проверяются.

## Результат

**Вердикт: ACCEPT для указанной Source-дельты. Существенных findings нет.** Actual installed SP route этой рецензией не принимается.

Проверены четыре изменённых файла (+192/−6), неизменённый private вход `early-filter.ts`/`assertNativeAssignmentCapture`, actual установленный Quarto 1.11.5, installed Download handler и реальные SP literal вызовы. Проверенные Source-файлы совпадают с head рецензии.

- `_extensions/course-core/native-ts/early-context.lua:63–81`: Quarto `main.lua:18856–18880` присваивает `handler.file` непосредственно из того же ordered `param("shortcodes")`, а `main.lua:25852` инициализирует таблицу до инъекции pre-ast (`26339`, `26363`). `handlerForShortcode` (`19000`) только читает выбранную запись. Existing `runtime-filter.lua` использует штатный `dofile`, поэтому early-context получает global lookup. Повторного load/вызова `.handle` нет. Builtins env/var/brand перезаписывают пользовательские имена записью без `file` и этим путём не допускаются.
- Exact membership путей согласован: Quarto передаёт resolved contributed paths (`quarto.js:79345`, `79509`, `104991–105009`, `105050–105067`), затем сохраняет те же строки в таблице handlers. Отдельная нормализация не нужна и могла бы ослабить точное соответствие. Relative configured paths тоже сравниваются с той же исходной строкой, а не с реконструированным путём. `pandoc.List` сохраняет тип списка, включая пустой список.
- `_extensions/course-core/document/assignment-producer.ts:27–50,88–114`: bounded file/handler arrays, непустые строки, уникальные имена и exact file membership проверяются до использования. Допуск требует совпадения actual name/file. Lua и Quarto reader используют первый positional `data-value` (`main.lua:21597–21645`); незарегистрированное имя не получает observation. Прежний strict literal reader guard отвергает content/nested/dynamic/raw и malformed параметры до проверки имени. Special meta title и xref grammar не обходятся зарегистрированной записью.
- Evidence теперь имеет конкретный readonly тип, `filters` остаётся unknown с runtime object guard. `shortcodeHandlers` необязателен: старый native payload с массивом `shortcodes` и без нового поля продолжает допускать прежние meta/xref; новые имена без observation отвергаются. Старое произвольное object-значение `shortcodes` теперь отвергается; это закрытый per-document transport, а текущий Lua producer выдаёт список. Поддержка повторного использования старого сохранённого evidence как capability не обещается.
- `_extensions/course-core/native-ts/early-filter.ts:63–76` по-прежнему сначала вызывает live private child/correlation check, затем pure qualifier. Caller/standalone JSON и manual transport нового producer не создают. Download syntax/resource policy остаётся в Download; installed `project-download/shortcodes.lua:4` возвращает именно function для имени `project-download`, совпадающего с SP `tasks/labs/work-environment.qmd:45,129,171,236`.

Прочитаны сохранённые авторские GREEN log/JSON: pure regression exit0, 0.904s; graph typecheck exit0, 4.151s. Они подтверждают свои узкие проверки, не фактический Lua handoff. Новые команды проверки/тесты не запускались. Неизменённые Source, consumers, UUID, данные и прежние artifacts сохранены.

Перед передачей повторно прочитан весь собственный модельный интервал от исходного хода: единственный `turn_context` на строке8, top+nested Sol6.1/high согласованы; новая исходная точка не выбиралась. Остаточная приёмка — следующий разрешённый root installed SP run с этой дельтой.
