# Download: независимое Source review обёрнутых функций

**APPROVE** для данного трёхфайлового candidate. Существенных замечаний не найдено. Это Source review статической типизации; новая runtime/native qualification не заявляется.

База actual checkout Download — `671093945a8a6f76c25e60840aee71964d995496`. Проверены [brief](../briefs/download-wrapped-functions-20261104.md), [отчёт автора](download-wrapped-functions-20261104.md), [сохранённый patch](download-wrapped-functions-20261104.patch) и фактический `git diff` трёх разрешённых файлов. На момент чтения diff содержит только `application/publish.ts`, `infrastructure/files.ts`, `infrastructure/runtime.ts`; отдельная работа над `ownership.ts` в verdict не входит.

## Что проверено

- Ровно прежние восемь wrappers: `publish` (`phase`), `exists`, `child`, `noSymlinks`, `resourceFiles`, `clearOwned`, `prepare`, `finish` (семь `io`). Все полные operation IDs, `wrapSync` для `child`, `wrapAsync` для остальных, `createMetrics("project-download")` и ABI-фасад сохранены. Порядок wrapper calls внутри каждого модуля прежний.
- Параметры, их порядок, optional/default аргументы (`finish(..., options: FinishOptions = {})`), явные return types и тела функций сохранены. У самих восьми функций нет generic параметров; generic договор bridge `wrapSync/Async<T extends Fn>(...): T` не менялся. Тип exported const выводится из исходной именованной функции через тот же `T`, без нового cast, suppression или отдельной ручной сигнатуры.
- Вызовов самих себя и self-lookup у этих функций нет. Поэтому внутреннее имя named function expression не создаёт обход wrapper при рекурсии. `noSymlinks → child`, `prepareInternal → clearOwned` и `publishArchives → clearOwned` продолжают обращаться к внешним обёрнутым bindings; локальный рекурсивный `walk` не менялся.
- Зависимости `files` и `publish` не импортируют их обратно в runtime. `runtime → ownership → files` не образует цикл: `ownership → publish.Request` — `import type`; sibling providers подключаются динамически внутри функций. В просмотренных модулях отсутствует top-level вызов целевых функций до новой const-инициализации. Entrypoints вызывают API после завершения импорта, public facade реэкспортирует bindings. Зависимости от declaration hoisting не найдено.
- Перенос регистрации выше в модуле не перемещает execution bodies: текущий Performance agent при `wrapSync/Async` только создаёт функцию, а `safeBegin`/вызов target выполняет позже при её вызове. Identity-ветка прежнего фасада возвращает исходную функцию. Между старым и новым местом регистрации нет инициализатора, вызывающего целевой API или меняющего metrics. Новых guards, IO, tests, env/config/request/privacy/ZIP/scoring правил либо metric semantics diff не добавляет.

Прочитаны применимые Download AGENTS, `spec/index.md`, `spec/contract.md` и существующее общее языковое правило Core. Контракт наблюдения сохраняется: прежние extension attributes, общий ресурсный контекст и child env не меняются. Отдельный medium ownership writer и frozen Performance recovery не затронуты.

## Evidence и пределы

Прочитаны [единственный saved affected check](download-wrapped-functions-check-20261104.json) и [compiler log](download-wrapped-functions-compiler-20261104.log): actual bundled compiler, `tests/runtime-types.ts`, существующая map реальных Core5f/PL4d sibling modules, exit `0`, `4.5574s`, cap `15s`, retry `0`. Проверка действительно импортирует runtime и его `publish/files` dependencies. Повторного compiler/tests/runtime/Native/hash/CI/network запуска в review не было; production Source не менялся.

Роль reviewer high прежняя; собственный допуск остаётся по существующему admission reference в предыдущих отчётах этой роли, глобальные настройки не менялись. Verdict относится к обозрённому patch, а не к новой проверке installed composition или производительности.
