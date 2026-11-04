# Независимый review Core Pandoc binding

4 ноября 2026. Целевой diff `2e71100f773c8185c7f125c44167b1555ec669f5` → `ad80be00d6617a427ba6ae7a3e17a55fa1ef5888`.

## Собственный первичный допуск

Агент `/root/review_pl_legacy_types`, chat `01a1a348-784d-7771-a540-bbcf7ed8735e`, parent `01a1a142-7adc-7371-9765-4871d807dfcd`. Исходный turn общего собственного интервала `01a1a348-7879-7070-a59e-6a29186a8ad2`, текущий turn Core review `01a1a4c6-51e9-7e72-84f3-d3c34c14742a`. Первичный источник `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T00-19-58-01a1a348-784d-7771-a540-bbcf7ed8735e.jsonl`: `session_meta` строка 1, все собственные `turn_context` строки 8/133/170. Каждая запись подтверждает top-level `gpt-6.1-sol/high` и те же nested model/reasoning_effort. Собственные ID и agent_path согласованы, исходная точка не сдвигалась. Standard назначен координатором; скрытый server tier первичным контекстом не удостоверяется. Глобальные настройки не менялись.

Допуск подтверждён до содержательного чтения production diff. Весь собственный интервал, включая текущий Core review, остаётся согласованным; исходные chat/turn не подменялись.

## Решение

**APPROVE для `ad80be00d6617a427ba6ae7a3e17a55fa1ef5888`.** Блокирующих замечаний в согласованной Core API партии нет. Проверен exact diff трёх файлов: `runtime/operation.ts`, новый `runtime/pandoc-binding.ts` и existing `tests/static-batch-checkpoint.ts`. Source/tests reviewer не менял; compiler/runtime/tests/render/inspect/hash/network/push не запускались. Успешные проверки владельца не повторялись.

Вердикт относится к producer API и его заданной frozen composition. PL consumer пока не изменён; этот commit сам по себе ещё не снимает 12 CLI conversions и не доказывает измеренный выигрыш, installed release либо hosted CI.

## Происхождение выбранных данных

`operation.ts:2995–2999` берёт executable из существующего `complete.state.assignment.runtime.executable`, а версию — из `complete.state.ownerWitness.quarto.version`. Это реальные поля состояния, а не предполагаемый новый witness. Existing admission получает physical Quarto через `nativeRuntimeIdentity`, выполняет owner inspect тем же executable (`assignment-admission.ts:175–205`) и сохраняет runtime (`:249–255`). `decodeNativeOwnerInspection` проверяет `owner.quarto.version` (`operation.ts:151–163`), а `state.ownerWitness` сохраняет именно её (`:1104–1112`). Existing full entry fence и runtime identity проверки не заменяются новым helper. При отсутствии assignment/version или несовпадающей версии selector возвращает `undefined`.

Host map имеет другое, правильное назначение: это фактический host environment будущего consumer child. `operation.ts:2409–2421` один раз замораживает `Deno.env.toObject()` и сохраняет cwd; из той же map строится прежнее сравнение hostGuard. Snapshot передаётся только приватному `publicOperation` (`:2503–2513`), а не принимается от caller `pandocBinding()`. Таким образом, env/cwd не восстанавливаются из исторического assignment environment и не подставляются из owner root. Snapshot map содержит строки, поэтому shallow freeze достаточно; record возвращает этот же immutable object без копирования/изменения значений.

Отдельно прочитаны allowlisted поля существующего сохранённого actual SP state `reports/performance-sp-pl9c-delta/course/tasks/_generated/course-spec/native-ts/70187f96-6c53-439e-96e7-e80b06508886/state.json`: actual `ownerWitness.quarto.version` равен `1.11.5`, runtime executable — `/opt/quarto/bin/quarto`; bin/share/root — `/opt/quarto/bin`, `/opt/quarto/share`, `/`; DOM/TLS/no-update значения совпадают с eligibility. Explicit Pandoc и Deno overrides указывают на существующие Performance launchers, а debug/force-version отсутствуют. Прочитанные launcher scripts сохраняют соответствующие реальные tool entrypoints; generic selection не подменяет custom Pandoc bundled binary. Это existing capture evidence, не новый запуск getter или capture.

## Path и environment semantics

Адресно сверены установленный launcher `/opt/quarto/bin/quarto:22–121,154–208` и actual bundled resolver `/opt/quarto/bin/quarto.js:26664–26705`, passthrough `:190301–190355`.

- `pandoc-binding.ts:21–45` ограничивает оптимизацию exact Quarto `1.11.5`, Linux x86_64/aarch64 и согласованными physical Quarto/bin/share/root данными. Debug/force-version и exported controls/locals, которые launcher переписывает, консервативно оставляют CLI. Нет нового arbitrary path/env параметра публичного getter.
- `:47–57` делает только адресные stats известных layout/dev paths. Отсутствие regular launcher/JS, share directory, неизвестная ошибка либо присутствующий dev source оставляют CLI. Сканирования дерева, content hash, subprocess/version/inspect нет. Existing qualification отвечает за runtime; helper не объявляет stat proof нового toolchain authority.
- `:58–70`: без override выбирается `bin/tools/<Deno.build.arch>/pandoc`, как `architectureToolsPath('pandoc')`; непустой absolute normalized override сохраняется как файл либо directory+`pandoc`. Relative/missing/unusual/error cases возвращают CLI, который сохраняет прежние fallback/warning/error semantics. Более консервативный отказ для некоторых путей допустим: он не меняет accepted ordinary CLI поведение.
- `:25–34,71–77`: launcher semantic deltas уже должны присутствовать в frozen env: bin/share/root, DOM plugin с учётом `QUARTO_DENO_DOM`, nonempty существующий Deno selection, TLS store и no-update. Непустой `QUARTO_DENO` shell сам не меняет, поэтому его override сохраняется. В accepted ветке возвращается исходный env/cwd. `SHLVL`/`_` не эмулируются по явно принятой границе shell bookkeeping. Новых data-dir/argv/env overlays helper не добавляет.

Удаление повторных version/debug reconfiguration CLI checks после успешного existing owner inspect соответствует заданному frozen operation contract. Требовать их обратно на каждый body или моделировать mutable debug config внутри операции оснований нет. Неизвестные composition остаются CLI.

## Cache, lifecycle и типы

`operation.ts:2577,2991–3005` кэширует Promise, а не её nullable результат. Поэтому fulfilled `undefined` тоже не вызывает повторный выбор; параллельные callers делят тот же selector, а subsequent calls получают тот же immutable record. `.then` создаёт per-call guarded delivery, что не означает новый selection. До начала и после asynchronous selection вызываются existing callback/sync/host guards; закрытый callback, expired operation и изменённые env/cwd не получают новый результат через retained getter. Existing full/currentness fences, assignmentChild и persisted state не менялись.

`NativePandocBinding`/`NativePandocHost` — точные readonly records строк и readonly string map, без production `any`/casts. `NativeOperation` по-прежнему выводится из реального `publicOperation` и доступен через прежний public type export. Binding описывает выбранный инструмент и данные запуска, не выдаёт assignment capability: возвращённые строки сами не разрешают произвольный child command и не заменяют consumer before/after guards. Retention уже полученного record не превращается в новую authority.

## Проверки владельца и остаточная интеграция

Прочитано evidence `core-pandoc-binding.md` и `core-pandoc-binding-reading.md`. У владельца зафиксированы один итоговый affected compiler PASS и один existing private static fixture PASS за 1.139s, cap60/retry0. Ранние unsupported `--cached-only` CLI failure и TS2304 missing test import записаны отдельно, не выданы за успешные проверки. Исправленный import и расширение того же fixture присутствуют в diff.

Fixture действительно проверяет bundled/custom file/custom directory, cached selected record после удаления candidate, expired getter и conservative fallback. Это private unit harness с локальными fake tool files без их исполнения; отметка `PASS_PRIVATE_UNIT_NOT_NATIVE_QUALIFICATION` корректна. Отсутствие нового actual capture/native qualification здесь не подменяется этим PASS. Для cached `undefined` логика видна непосредственно в Promise slot; новый тестовый framework или повторные проверки ради review не требуются.

Следующая PL партия должна отдельно сохранить existing JSON→HTML argv/streams/failure/before-after semantics при потреблении этого binding. Core21 Markdown/GFM conversions, public persisted schemas, assignment authority, courses/installed и canonical state этим diff не менялись. В данном Core scope конкретного незакрытого остатка нет.
