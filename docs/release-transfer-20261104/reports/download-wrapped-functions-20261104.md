# Download: статические сигнатуры обёрнутых функций

Source candidate готов для независимого review root. База Download `671093945a8a6f76c25e60840aee71964d995496` подтверждена перед правкой; начальный `git status --short` пуст. Изменены только три разрешённых production files; commit, stage и push не выполнялись. Mainstate/audit и frozen Performance/Platform refs не затронуты.

[Точный patch к базе](download-wrapped-functions-20261104.patch) переводит восемь функций на `export const`, инициализированную прежним `__metrics.wrapSync/Async` с named function expression того же имени:

- `application/publish.ts`: `publish`.
- `infrastructure/files.ts`: `exists`, `child`, `noSymlinks`, `resourceFiles`.
- `infrastructure/runtime.ts`: `clearOwned`, `prepare`, `finish`.

Поздние присваивания и восемь соответствующих `@ts-expect-error` удалены. `__createMetrics`, ABI, wrapper methods, все id/kind и порядок регистрации обёрток внутри каждого модуля прежние. Generic bridge не менялся; новых casts, ReturnType/Parameters bridges, проверок, IO, guards и тестов нет. Отдельное буквальное сравнение old/new function expressions подтвердило прежние имена, параметры, return types и bodies всех восьми функций. Request/config/hash/identity/scoring/ZIP/privacy и diagnostics не менялись.

## Проверка инициализации

До правки просмотрены локальные вызовы, импорты/потребители и модульные инициализаторы. У целевых функций нет вызовов самих себя и self-lookup, поэтому локальное имя named function expression не обходит metrics при рекурсии. `noSymlinks` вызывает отдельно обёрнутый `child`; `prepareInternal` и `publishArchives` вызывают отдельно обёрнутый `clearOwned`. Эти обращения остаются внешними bindings.

В трёх модулях нет top-level вызовов целевых функций до их инициализации. Единственные перемещаемые исполняемые инициализаторы — прежние wrapper calls; порядок этих calls в каждом модуле сохранён. В зависимостях `files` (`bridge`, `diagnostics`, `config`, stdlib) и `publish` (`bridge`, `diagnostics`, `config`, `zip`, `request`) нет runtime обратного импорта целевых модулей. `runtime` зависит от `ownership`, который импортирует `files`; обратная связь `ownership → publish.Request` только type и стирается. Sibling modules подключаются динамически внутри операций. Entrypoints `pre`/`post` вызывают `prepare`/`finish` после завершения импорта runtime; public facade только реэкспортирует API. Зависимости от declaration hoisting в просмотренном production graph не обнаружено; отдельных отложенных функций нет.

## Единственная affected compiler-проверка

Использована существующая исправленная composition из [ownership evidence](download-ownership-snapshot-final-check-20261104.json), без новых aliases/stubs/install/network:

```text
DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache
/opt/quarto/bin/tools/x86_64/deno check --deny-import --no-config --no-lock --import-map=/tmp/download-ownership-snapshot-imports-20261104.json tests/runtime-types.ts
```

Результат **PASS**, exit `0`, elapsed `4.5574s`, cap `15s`, retry `0`. Реальный Core `5f4590aa4eb524830715f8020c8ff3c504197f73`, PL `4d39a6648d2816bab9a372877deded3320ff35e1`; использованы существующие literal public-prefix mappings и bare `course-core/`, `pl-native/`. Полная сохранённая map и команда: [check evidence](download-wrapped-functions-check-20261104.json); [compiler log](download-wrapped-functions-compiler-20261104.log). `git diff --check` после удаления лишних пустых строк PASS. После успешного compiler production Source не менялся.

Существующие semantic suites не повторялись. Native/render/inspect/build/hash/full CI/matrix не запускались. Compiler подтверждает типы actual consumer graph, но не заявляет новую runtime/native acceptance. Новых известных source concerns нет; независимый review root ещё PENDING.

## Собственный допуск

Прочитаны Download AGENTS, `spec/index.md`, `spec/contract.md`, существующий Core `docs/russian-language-policy.md` и Java `docs/model-admission.md`. Собственные первичные записи дают agent `/root/download_wrapped_functions`, chat `01a1a603-4b23-7c93-9982-94c8c75c106e`, origin turn `01a1a603-4b96-7f73-8cdb-98caae4aa3df`. Все доступные `turn_context` от исходного хода, включая nested collaboration settings, согласуются с назначенными `gpt-6.1-sol`/`medium`; финальный интервал PASS. Standard назначен; глобальные/runtime настройки не менялись, скрытая маршрутизация не утверждается. [Собственное admission evidence](download-wrapped-functions-admission-20261104.json).
