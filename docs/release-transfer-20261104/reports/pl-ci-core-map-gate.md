# PL CI: переносимая карта типов Core и gate-before-env

3 ноября 2026. Отдельная партия после типового commit `4e4ab0cd5499006826f5ab21d1e3fdeef9c313b1`. Исходное дерево PL чистое. Причины первоначального отказа hosted run `41680971465` сохранены координатором в [pl-ci-f9-failure.md](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/pl-ci-f9-failure.md); повторный hosted workflow этим исполнителем не запускался.

## Собственный допуск и границы

Тот же собственный primary interval агента `/root/pl_typed_materialization`: chat `01a1a25f-b098-7242-bf6d-d0e8f6517361`, parent `01a1a142-7adc-7371-9765-4871d807dfcd`. Первичный файл `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-05-42-01a1a25f-b098-7242-bf6d-d0e8f6517361.jsonl`: собственный `session_meta` строка1; все собственные `turn_context` интервала (строка8, turn `01a1a25f-b0fb-7fd2-b244-83933af77834`) подтверждают top-level `model=gpt-6.1-sol`, `effort=high` и вложенные `model=gpt-6.1-sol`, `reasoning_effort=high`. Расхождений нет. Серверный tier не удостоверяется; глобальные/normative admission документы не менялись.

Единственный Source writer — PL `/home/tolya/JavaCodex/java-release-20261110/prairielearn`. Подход до правок сообщён координатору и принят: один existing mapping helper, три прежних CI compiler entries, фактический Core, ленивое кешированное чтение profiles внутри public Core API. Product DTO не ослаблять; прежние compiler-negative tests не повторять. Для разбирательства применён skill systematic-debugging; первичные отказы CI и затем compiler diagnostics отделены от решения.

## Причины и исправление

1. Bare type-only `course-core/public.ts`/`capture.ts` разрешались только внутри адресного checker. Штатный `deno test --no-config` не получал map: три `TS2307` породили девять каскадных `TS7006` в первом CI. Резолвер карты перенесён без дублирования из `tests/native-core-typecheck.ts` в `tools/native-core-import-map.ts`. Он выбирает переданный фактический Core Source/installed owner/extension, поддерживает обычный и namespaced layout, использует настоящий public Core и штатные Quarto stdlib mappings. Это type-only composition; runtime Core по-прежнему выбирают существующие gates.

   Existing `tests/check.ts` создаёт временную карту из своего прежнего Core argv и передаёт её дочерним штатным командам, затем удаляет в finally. Existing адресный checker использует тот же helper. В java-delivery job после Quarto setup создаётся `.ci-deps/pl-native-imports.json`, и все три прежних `deno test` entries получают `--import-map`. Новых suite/matrix/framework нет.

   В обоих CI jobs checkout и exact-SHA assertion Core обновлены с фактического прежнего `04c189fccfaa56badfc079f7be46fea4969ccf37` на independently reviewed `830381697beea4106655965e10605cc13987199b`. Stub/декларационной копии/any facade вместо Core нет. Сначала подход был принят для047; финальный steering координатора обновил только четыре SHA literals до830, чтобы CI совпал с reviewed installation pair и фактическим локальным Source. Compiler/runtime проверки ради замены этих literals не повторялись.

2. `nativeEntry` вычислял `functionalProfiles` через `Deno.env.get('QUARTO_PROFILE')` до вызова публичного Core gate. Envless refusal fixture получала `NotCapable` вместо `PUBLIC_CORE_GATE_REFUSAL`. То же existing поле `assignment.profiles` стало getter с кешированием одного массива. Чтение происходит по запросу публичного Core, после входа в API; его отказ без чтения поля не требует env permission. Список, filtering student/full, один snapshot, последующая prepared-identity parity и capture lifecycle сохранены. Новый public config protocol и расширенные permissions не добавлялись.

## Existing test fixtures

После исправления map compiler дошёл до реальных типов и выявил 22 ошибки старых descriptor fixtures в четырёх файлах: `java-build.test.ts`, `text-runtime.test.ts`, `public-cases.test.ts`, `export-course.test.ts`. Положительные descriptor inputs дополнены настоящими обязательными полями и проходят существующий `NativeProjectCheckSchema.parse`. Результат parse идёт в прежний consumer. У Java build literal используется вывод существующей schema-типа; optional Java output проверяется с его реальной optionality.

Первоначальный черновик parse для malformed fixtures был пересмотрен до commit: такой parse мог отвергнуть input до целевого runtime guard и заменить subject теста. По принятому координатором решению намеренно неправильные типы сохраняются как `raw: unknown` и передаются через `Reflect.apply(runtimeDescriptor, ...)` только в отрицательных runtime calls, с игнорируемым результатом и явным комментарием о внешней JS-границе. Это прямой вызов прежнего consumer с недоверенным input. В product, положительных fixtures и доказательствах статических типов `Reflect.apply` нет. Все статические type-negatives остаются обычными прямыми вызовами. Новых any/casts/suppressions нет.

## Проверки и результаты

- `quarto run tools/native-core-import-map.ts ../core /tmp/pl-ci-core-imports.json` — exit0. Получена карта на фактический Source Core и штатные stdlib модули.
- Existing canonical graph из всех 33 `tests/*.test.ts` проверен штатным bundled Deno `test --no-run --no-config --no-lock --import-map /tmp/pl-ci-core-imports.json`: сначала 22 fixture diagnostics, после исправления exit0. Никакие runtime cases этим проходом не выполнялись. После финального уточнения malformed calls четыре изменённых test files снова компилировались как часть их адресного runtime запуска ниже.
- Один адресный `deno check --no-config --no-lock --import-map /tmp/pl-ci-core-imports.json tools/native-core-import-map.ts tests/check.ts tests/native-core-typecheck.ts` — exit0. Сам адресный runner и его восемь negative cases второй раз не запускались.
- Existing `quarto run tests/performance-native-boundary.ts` — exit0: восемь PASS для native/inspect × denied-mode/off/missing-agent/active; прежние public gate refusal и запрет раннего env/child сохранены. Этот existing тест уже содержал собственные runtime-only child flags; обход компилятора в CI-fix не добавлялся.
- Только затронутые existing descriptor cases в четырёх test files, с regex filter и разрешениями read/write/env, **без allow-run**: **9 passed, 0 failed, 13 filtered out, 421ms**. Прямые отрицательные runtime guards, положительные build/API/extras/library/fish/public-cases и последняя additional-Java policy проверка работают. Полные106 и cross-filesystem probe не повторялись.
- Для единственного изменённого canonical-targets parse внутри большого full-export case выполнена transient проверка точного check payload: existing schema parse → `runtimeDescriptor` → assertions build/API/sourceFiles/additionalJavaSources, exit0. Сам exporter, его около30 export calls и файловая atomicity не запускались заново.
- `deno fmt --check` пяти форматируемых изменённых TS-файлов и `git diff --check` — exit0. Три исторически компактных test files не переформатировывались целиком. Весь собственный diff просмотрен.

Локальные compiler/runtime commands запускались через штатный Quarto с его bundled Deno и cache; вспомогательные одноразовые commands находятся только в `/tmp`, новая suite в Source не создавалась. Render/inspect/Gradle/build/install/push/full suite не выполнялись.

## Core provenance и пределы

Карта читает фактический Core Source directory, а не immutable git snapshot. Во время параллельной работы Core owner завершил commit `830381697beea4106655965e10605cc13987199b`; финальный Core status чистый, diff от047 ограничен `runtime/assignment-execution.ts` и `tests/assignment-download-deferral.ts`. Public API/types не изменились. Координатор подтвердил независимый ACCEPT этого отдельного Download defer fix и обновление final CI pin до830. Локальная compiler evidence относится к фактическому Source directory с финальным HEAD830 и теми же public types; она не выдаётся за отдельное криптографическое доказательство bytes через immutable snapshot. Core Source этим исполнителем не изменялся.

Локальные checks подтверждают устранение двух известных причин и сохранение смысла затронутых cases, но не заменяют новый hosted CI и итоговый production review. Retired priorP/capsule/proof plumbing не восстанавливалась, fake authority не добавлялась. Независимый review CI-fix нужен до push/install; их выполняет координатор отдельно. Релиз — 10 ноября 09:00 Minsk.

## Source commit

Commit `da2ad6b6c00891590e59e5abccf3d1eb4ff552f7` — «Передавать Core import map штатным CI и отложить чтение profiles до gate». Ровно девять описанных files, `157 insertions(+), 93 deletions(-)`, поверх `4e4ab0cd5499006826f5ab21d1e3fdeef9c313b1`. После commit PL и Core status чистые; `git diff HEAD^ HEAD --check` exit0. Final CI checkout/assert обоих jobs — Core830. Push/install не выполнялись. Отчёт вне PL Source repo.
