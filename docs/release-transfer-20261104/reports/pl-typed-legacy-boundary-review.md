# Независимый review typed legacy/native boundary PL

4 ноября 2026. Проверка ограниченного diff `9c62960ff345552dad492d8cc560dc6760787ec5` → `2dd7e00735facaa437ec39ccb4a40b139a1f716e`, затем адресный повторный review исправления `30edc03295e508f174c64b399f35b14ad37488fa`.

## Итоговый verdict

**APPROVE для `30edc03295e508f174c64b399f35b14ad37488fa` поверх `2dd7e00735facaa437ec39ccb4a40b139a1f716e`.** Единственное P2 исходного review закрыто ограниченной правкой двух файлов. Незакрытых блокирующих замечаний в согласованном scope нет. Generator allocations и два дополнительных negative compiler process остаются необязательными nits, изложенными ниже. Исторические finding/REQUEST CHANGES сохранены для происхождения исправления.

## Собственный первичный допуск

Агент `/root/review_pl_legacy_types`, собственный chat `01a1a348-784d-7771-a540-bbcf7ed8735e`, parent `01a1a142-7adc-7371-9765-4871d807dfcd`. Исходный turn роли `01a1a348-7879-7070-a59e-6a29186a8ad2`. Источник — `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T00-19-58-01a1a348-784d-7771-a540-bbcf7ed8735e.jsonl`, первичные `session_meta` и `turn_context`. Собственные ID и agent_path совпадают с назначением; top-level `model=gpt-6.1-sol`, `effort=high` и вложенные `collaboration_mode.settings.model=gpt-6.1-sol`, `reasoning_effort=high` согласованы с ожидаемой парой. Назначен Standard; первичная запись не удостоверяет скрытую серверную маршрутизацию/tier. Глобальные настройки не менялись.

Первичный допуск до начала содержательного review выполнен. Перед итоговой записью перечитан весь собственный интервал от того же исходного хода: `session_meta` строка 1 и единственный `turn_context` строка 8; других собственных `turn_context` нет. Модель/усилие согласованы в обоих уровнях. Интервал не сдвигался.

## Решение первого review

**REQUEST CHANGES / BOUNDED FIX: одно замечание P2 к сохранению native scoring type.** Остальная проверенная часть укладывается в согласованный scope. Повторные runtime/typecheck/matrix/network/hash/build/render/inspect проверки не запускались; verdict получен статическим чтением exact diff и реальных producer/consumer callpaths. Source/tests не редактировались, commit/push не выполнялись. Координатор принял finding и передал владельцу bounded исправление двух файлов; этот verdict относится к исходному commit, а не к ещё не рассмотренной правке.

## Исходное блокирующее замечание — закрыто исправлением ниже

### P2 — сузить запасной scoring, чтобы сохранить native discriminated union

Место: `_extensions/course-prairielearn/application/runtime-descriptor.ts:38–45`, прежде всего строка 45. Передача `C['scoring']` через локальную переменную сохраняет тип исходного значения, но в generic теле `C extends LegacyDescriptorCheck` scoring может отсутствовать. Поэтому `scoring ?? {mode:'weighted'}` сохраняет отдельную запасную ветвь объекта; её изменяемое поле выводится как `mode: string`. Итоговая форма scoring содержит `NonNullable<C['scoring']> | {mode:string}`. При native instantiation эта последняя ветвь шире существующего `NativeProjectCheck['scoring']`: она допускает `threshold` без `basis/value`, `contract-groups` без `groups` и произвольную строку режима.

Это реальная потеря прежнего ограничения на native writer input. `native-selection.ts:225–236` возвращает descriptor настоящего parsed `NativeProjectCheck`; его результат проходит через materialization в `export-course.ts:124–128`, где `NativeCompilerInput` выводится из этого producer. Следовательно, тип `NativeCompilerInput['descriptor']['scoring']` тоже расширяется. До diff параметр `runtimeDescriptor` имел required `NativeProjectCheck.scoring`, и запасная nullish-ветвь не расширяла native scoring return. Например, статическая граница нового writer более не исключает подстановку `scoring: {mode:'threshold'}` поверх существующего native descriptor.

`tests/native-course-export-types.ts:59–63` сравнивает descriptor с `ReturnType<typeof runtimeDescriptor<NativeProjectCheck>>`; обе стороны происходят из одного ослабленного return, поэтому equality остаётся истинной. Новый `_NativeMode` в `tests/native-legacy-boundary-types.ts:44–49` проверяет только mode source profile и не проверяет scoring.

Достаточная ограниченная правка: сохранить у fallback явный узкий тип `{mode:'weighted'}` без приведения типов/нового validator, затем добавить в существующий positive fixture прямую проверку равенства native descriptor scoring и `NativeProjectCheck['scoring']`. Это помещается в текущие production/test файлы и существующий affected graph; отдельный negative compiler process или framework для исправления не нужен. Runtime значение fallback и все scoring вычисления должны остаться прежними. Пример выше — вывод статического review, дополнительный compiler experiment по запрету не выполнялся.

## Что проверено без замечаний

- `legacy-checks.ts:6–14,18–50`: короткая accepted legacy форма именуется consumer view, без forced parse; полный `NativeProjectCheck` остаётся подтипом. Native origins выводятся из реального producer с Core `ArtifactFileSchema.parse`, path/size/hash gates (`native-check-inventory.ts:53–85`); owner/project union не заменён legacy-only DTO. Существующие `any` за пределами партии не используются как основание отклонить этот diff.
- `native-check-inventory.ts:91–108,133–152`: generic сохраняет точный project type; сортировка копирует массив и сохраняет project references. Hash/canonical области, version2 projection и selection-scope guards не меняются. Ограничение generic только `qualifiedId` соответствует фактически типизируемому общему producer; прежний hash-only helper остаётся отдельным долгом.
- `source-selection.ts:45–65`: optional property view не выдаёт owner-only записи physical authority. `readSelected` принимает union, затем прежний `safePath(s.projectRelativePath)` отвергает owner-only запись до IO. Прежние symlink, inside-root, hash и bytes flows сохранены. Negative owner→`LegacyPhysicalSelection` не доказывает compile-refusal owner→`readSelected`, и evidence этого не заявляет.
- `export-course.ts:520–568`: discriminant связывает native bytes с native payload и legacy reader с origin. Duplicate/reserved-path отказ остаётся до reader; legacy origin reference читается лениво, без eager snapshot путей перед первой IO-паузой. Native map сохраняет прежние имя/bytes и порядок. Временный `kind` не попадает в wire.
- `runtime-descriptor.ts:25–52` и `java-build.ts:19–56`: существующие Java/API/feedback/public-cases guards, runtime значения, defaults и бросаемые ошибки сохраняются; замечание выше касается расширения статического output scoring.
- `tests/export-course.test.ts:989–1005,1106–1114`: `unknown`→`Reflect.apply` ограничен двумя прежними намеренно неполными external JS inputs `checks:{}`. Ранние duplicate-defense assertions остаются теми же; production consumer этим не обходится.

## Неблокирующая цена и качество проверок

`export-course.ts:535–547` добавляет generator на legacy branch и временную обёртку на каждый test origin; native records получают discriminator. Это дополнительные O(T) краткоживущие allocations и generator/iterator machinery сравнительно с прямым прежним `for...of`. Ленивость и reference semantics сохранены, тяжёлые filesystem/hash операции прежние. Без отдельного измерения нет основания считать эту цену blocker; при будущем локальном упрощении можно оценить устранение лишних обёрток, сохранив корреляцию и порядок.

`tests/native-core-typecheck.ts:60–87,163–175` действительно добавляет два самостоятельных Deno/compiler process. Default теперь 1 positive + 11 negative вместо 1 + 9; scoped legacy режим — 1 + 2. Объединение двух новых negatives могло бы уменьшить число запусков на один, если проверять требуемую диагностику отдельно для каждого файла; обычное объединение с поиском одного кода в общей строке способно ослабить проверку. Это полезный необязательный nit, не причина требовать новый checker framework или общий rewrite тестов.

Имеющееся evidence владельца прочитано: один итоговый affected positive graph, два direct negatives TS2739/TS2353 и четыре existing runtime case, 4 passed/29 filtered, около 1s; Source после финального green не менялся. Эти результаты здесь не повторялись и не выдаются за новые измерения. Существующие positive checks подтверждают прохождение настоящего producer и короткой legacy формы; указанная P2 требует отдельного прямого scoring assertion в том же graph.

## Прочитанные материалы и граница review

Прочитаны `AGENTS.md`, локальный текущий `core/docs/russian-language-policy.md`, `spec/index.md`, `pl-typed-legacy-boundary.md`, `pl-typed-mixed-checks.md`; полный diff 10 файлов (6 production, 4 test/typecheck), а также необходимые соседние места `native-checks.ts`, `native-check-projection.ts`, `native-selection.ts`, `native-course.ts`. Работа ограничена отчётом вне PL Source. Download и Performance не изменялись. После указанной bounded правки необходима адресная проверка владельцем; полный стек PL типизировать для приёмки этой партии не требуется.

## Повторный review исправления P2

Проверен только follow-up diff `2dd7e00735facaa437ec39ccb4a40b139a1f716e` → `30edc03295e508f174c64b399f35b14ad37488fa` (2 файла, +4/-1), актуальные затронутые строки inferred contract и дополнение evidence владельца. Compiler, negative/runtime tests, network, build/render/inspect/hash и прежняя матрица не запускались. Source/tests reviewer не менял.

В `runtime-descriptor.ts:45` выражение `({mode:'weighted'} satisfies {mode:'weighted'})` задаёт fallback литеральный mode без cast и без новых runtime действий. Значение объекта, nullish выбор и порядок вычисления прежние. Итоговая запасная ветвь теперь `{mode:'weighted'}` входит в существующий `NativeProjectCheck['scoring']`, поэтому не расширяет native discriminated union до произвольного mode и не позволяет потерять обязательные `threshold.basis/value` либо `contract-groups.groups`. Передача `C['scoring']` сохраняет настоящие native значения; accepted короткий legacy input не меняется.

`tests/native-legacy-boundary-types.ts:50–52` теперь прямо сопоставляет `typeof nativeDescriptor['scoring']` с `NativeProjectCheck['scoring']`. `Exact` исключает `any` и проверяет обе стороны, а `nativeDescriptor` получен вызовом с настоящим `NativeProjectCheck`; это независимая опора на schema, которой не хватало прежнему сравнению производных descriptor. В evidence зафиксирован реальный RED TS2344 этой assertion до исправления и GREEN прежнего affected positive graph после него. Два negative compiler process и четыре runtime case не повторялись; reviewer доверяет этому предоставленному evidence, не выдавая его за собственный запуск. Source после GREEN не менялся по сообщению владельца и координатора.

Собственный допуск после возобновления проверен по тому же primary JSONL и от первоначального turn `01a1a348-7879-7070-a59e-6a29186a8ad2`: весь интервал содержит `turn_context` строки 8 и 133, новый turn `01a1a34d-c85c-7970-9c03-663eb71ff5fd`. Обе записи подтверждают `gpt-6.1-sol/high` в top-level и вложенных settings; исходная точка/собственные ID не подменялись, расхождений нет. Назначение Standard и ограничение свидетельства о серверном tier прежние.

P2 закрыто; итоговый verdict APPROVE относится к указанному исправленному commit и согласованному локальному scope, без нового утверждения о hosted CI/install/release.
