# Core Native owner: фактический остаток типизации на принятом 5a

## Допуск и изоляция

Назначение `gpt-6.1-sol/high`, Standard. Собственный chat `01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1`, agent `/root/perf_event_types`, parent `01a1a142-7adc-7371-9765-4871d807dfcd`. Primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-41-59-01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1.jsonl`; `session_meta` строка 1. Исходный turn роли `01a1a514-1688-7662-80fc-58bf7a272ae0`; новая Core партия — turn `01a1a53a-9bae-7d50-979a-904daf3fa2de`. Полный собственный интервал от исходного хода проверен: `turn_context` строки 8 и 171, обе верхние пары `model=gpt-6.1-sol`, `effort=high` согласованы с вложенными `collaboration_mode.settings.model/reasoning_effort`. Вход PASS. Отдельный service tier первичными данными не указан; глобальные настройки не менялись.

Прочитаны действующие `AGENTS.md`, `docs/russian-language-policy.md`, `spec/index.md` accepted Core, `java/docs/model-admission.md` и конкретные строки P2 Native owner в `reports/typescript-any-audit.md`. Новые тексты написаны по-русски. Applicable вложенных AGENTS в Core нет.

По прямому назначению создана отдельная manual worktree `/home/tolya/JavaCodex/java-release-20261110/core-native-types`, ветка `codex/core-native-types-20261110`, exact base `5a28314fc8b704ef7171f33063789d7cc8ec8e79`. Общая Git object database — `provider-sources/quarto-course/.git`. Принятая `core-native-bridge` и старая dirty Core worktree не менялись. Новая worktree остаётся чистой на exact base; Source commit/push/mainstate/pins не выполнялись.

## Результат: назначенная граница уже закрыта accepted bridge

Устаревшая строка аудита `operation.ts:275–305,894–902` / `assignment-admission.ts:30,197–215` описывает прежние `inspectNativeOwner/json` без return type и owner any. На exact 5a фактический Source иной:

- `_extensions/course-core/runtime/operation.ts:125–130`: `NativeOwnerInspection` сохраняет полный payload через `Record<string, unknown>`, типизируя только consumed `dir`, `quarto.version`, `config`, `files.input/config`; extension bags остаются неизвестными.
- `operation.ts:132–174`: `decodeNativeOwnerInspection(value: unknown, root)` проверяет records, string input/config arrays, SupportedQuartoVersion и owner dir, затем возвращает существующую проекцию с сохранением дополнительных верхних/вложенных ключей.
- `operation.ts:331–363`: обе ветви actual inspect читают JSON в `unknown` и вызывают decoder; API имеет `Promise<NativeOwnerInspection>`.
- `operation.ts:365–367`: private `json(path): Promise<unknown>` передаёт внешние данные существующим decoders/guards; blanket typed JSON отсутствует.
- `operation.ts:951+`: `bindOwner` получает `NativeOwnerInspection`; existing projection/source-policy/output guards остаются в своём consumer месте.
- `_extensions/course-core/runtime/assignment-admission.ts:6–30,197–225`: callback `prepare` и admission.owner используют тот же конкретный тип. Full payload перед callback клонируется/замораживается прежним путём.

Existing `tests/assignment-owner-boundary.ts` уже сохраняет arbitrary config/fileInformation/engines/extensions и содержит malformed owner/version/input/config/projection/source-policy/output controls. Их успешная bridge qualification остаётся свидетельством принятого 5a; эта роль не запускала их повторно. Дополнительный type-only diff этой границы был бы пустым или косметическим. Новый cast, дополнительный decoder, повторная валидация downstream и ослабление accepted bridge не вводились.

`assignment-admission.ts:180` содержит отдельный `JSON.parse(invocation).physical as string`, но `invocation` — собственный сериализованный вывод `nativeRuntimeIdentity`, а не Native inspect JSON. Его не подменяли новым внешним owner parser и не расширяли назначенную партию на другой API.

## Первый фактический остаток: отдельный A1 acquisition

В `_extensions/course-core/infrastructure/project-context.ts`:

- `inspect()` строки 117–131 выводит `any` из внешнего Native JSON.
- `readProjectContexts` / actual acquisition строки 571–585 проверяют version, dir и лишь truthiness `original.config`, затем используют `original.quarto.version`, `original.config` и `new Set<string>(original.files?.config ?? [])`.
- `live: any` строки 658–667 проходит owner/version comparison, затем truthy `formats[format].metadata` клонируется и индексируется. Окончательный `ProjectContextSchema` уже хранит `effectiveMetadata` как `Record<string, unknown>`, но до его parse идут marker indexing/deletion и origin collection.
- `ProjectContextGroup.ownerMetadata` строка 483 и `NativeOperationProjectContextBatch.groups.ownerMetadata` строка 831 остаются `Record<string, any>`; `tests/native-context-a1.ts:90` обращается к `ownerMetadata.project.type` без локального narrowing.
- `collectOrigins(effective:any, ids:any)` строки 393–428 — следующий consumed metadata участок той же цепочки. YAML `tree(n:any)` строки 345+ — отдельная library-node задача, не внешний Native DTO.

Нельзя честно заменить `inspect()` аннотацией конкретного owner/probe DTO: существующая truthy проверка не доказывает record config/metadata; Set не доказывает string elements; partial version/owner comparison не доказывает nested shapes. Простое `unknown` у JSON без последующего narrowing либо не компилируется, либо вынуждает fake casts. Один только `ownerMetadata:any→unknown` скрыл бы фактический raw boundary, не закрыв её.

Минимальная содержательная следующая партия — один production файл `project-context.ts`: `inspect(): Promise<unknown>`, узкий разбор только реально потребляемых owner/probe полей до их index/path операций, metadata bag `Record<string,unknown>` и unknown origin inputs с сохранением существующих origin guards. Не требуется closed Native schema или проверка всех metadata/custom keys; дополнительные ключи должны сохраниться. Existing typed test caller потребует локального narrowing `ownerMetadata.project`.

Но такая партия вводит конкретное runtime изменение: malformed truthy primitive/array `config` или document `metadata`, а также неверная форма/элементы `files.config`, сегодня не получают явного узкого field guard; после правки они будут отказаны на Native JSON границе. Для valid Native object flow изменение не предполагается. Profiles/roles/allowlists/engine gate менять не требуется. Этот semantic scope сообщён root **до Source edits**; он не выдан за чистую типовую правку или разрешённую повторную validation всего Native договора.

## Выполненные действия и пределы

Выполнены только адресное чтение Source/контрактов/сохранённого accepted evidence, проверка первичных метаданных, создание назначенной чистой worktree и этот отчёт. Production/test Source не изменён. Actual bundled compiler не запускался: affected diff отсутствует, проверять повторно unchanged accepted program ради отчёта не требуется. Поэтому новых compiler/behavioral результатов этой ролью не заявлено; total execution budget использовано 0 из 45 s, retry 0.

Для отдельно назначенной следующей A1 партии уже известен фактический runner accepted bridge: `/opt/quarto/bin/tools/x86_64/deno check --deny-import --no-config --no-lock --import-map=...`, `DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache`, `DENO_NO_PACKAGE_JSON=1`; existing actual map `/tmp/core-native-bridge-imports.json` указывает `course-core/` на bridge, для новой worktree потребовалась бы только адресная копия map. Новая map или compiler invocation сейчас не создавались. `--cached-only`, install/network и guessed cache не применялись.

Native render/inspect/матрица, повтор unchanged knitr/png/readme ZIP, hash, production query, push/commit/pins и изменение принятой bridge Source не выполнялись. Результат этой партии — точный first remaining hole и план вместо необоснованного расширения. Максимальная типизация Core/A1 этим отчётом не объявляется завершённой.
