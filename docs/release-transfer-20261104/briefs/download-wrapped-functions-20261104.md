# Download: убрать подавления при обёртывании функций

Продолжение после прямого разрешения пользователя4ноября, без ограничения15:00. Цель — настоящая статическая проверка публичных сигнатур при прежнем Performance ABI; runtime поведение/число проверок и IO не расширять.

## Scope и текущая база

Checkout `download`, HEAD `671093945a8a6f76c25e60840aee71964d995496` должен быть подтверждён перед правкой; все чужие dirty изменения сохранять. Root единственный writer mainstate/audit. Прочитать applicable AGENTS, spec/index.md, spec/contract.md, shared russian-language-policy из existing Core checkout, docs/model-admission.md в Java. Sol6.1/medium, Standard назначен; подтвердить собственный primary origin, runtime/global не менять. Не делегировать.

Разрешённые production files только:

- `_extensions/project-download/application/publish.ts`: publish.
- `_extensions/project-download/infrastructure/files.ts`: exists, child, noSymlinks, resourceFiles.
- `_extensions/project-download/infrastructure/runtime.ts`: clearOwned, prepare, finish.

В каждом сейчас exported function declaration и позднее присваивание metrics.wrapSync/Async с `@ts-expect-error` перед присваиванием. Тот же типовой паттерн уже принят в Core Navigation5f: exported const, инициализированная прежним wrapper с named function expression того же имени/параметров/return type/body. Точные metrics id/kind, createMetrics, ABI и порядок вызовов внутри body сохраняются. Generic bridge не менять и не подменять ReturnType/Parameters/casts. Удалить соответствующее позднее присваивание и suppression. Новых wrapper/guards/checks/IO не добавлять.

До правок проверить local/cross-module hoisting, module initializers, recursive self-calls и циклы: переход binding не должен менять наблюдаемое поведение/metrics. Если конкретная функция зависит от hoisting или self-lookup wrapped binding, описать это и оставить её отдельно; не компенсировать новым runtime слоем.

## Проверка, стоимость, результат

Менять тела функций/ошибки/config/request/hash/identity/scoring/ZIP/privacy нельзя. Не запускать Native/render/inspect/build/hash/full CI/matrix или network. Не писать новые тесты ради формы обвязки. После Source diff — один affected compiler по настоящему Core5f/PL4d sibling graph с существующим runtime-types consumer, общий cap15s, retry0. Actual command/import map брать из `reports/download-ownership-snapshot-20261104.md` и saved compiler evidence, не выдумывать несовпадающие bare sibling aliases. Если graph недоступен, фиксировать точный gap без stubs/install. Existing unchanged semantic suites не повторять.

Отчёт `reports/download-wrapped-functions-20261104.md`, patch рядом, exact diff/base, elapsed/exit и существующий admission. До root independent review не commit/stage/push; короткий ответ status/files/check/concerns. Frozen Performance recovery и Platform SHA не трогать.
