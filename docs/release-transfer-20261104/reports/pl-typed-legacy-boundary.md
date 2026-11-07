# Типы legacy check/origin → manifest → mixed trusted-tests

4 ноября 2026. Отдельная ограниченная партия максимальной типизации PL.

## Исходники и собственный допуск

PL Source `/home/tolya/JavaCodex/java-release-20261110/prairielearn`: исходный HEAD `9c62960ff345552dad492d8cc560dc6760787ec5`, дерево чистое. Координатор подтвердил remote и hosted CI `41699207731 SUCCESS`. Installed/courses остаются frozen PLda/Core d7; они не редактировались. Предыдущий APPROVE0906408 и его зелёные проверки не повторялись.

Собственный primary interval агента `/root/pl_typed_materialization`: chat `01a1a25f-b098-7242-bf6d-d0e8f6517361`, parent `01a1a142-7adc-7371-9765-4871d807dfcd`. Файл `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-05-42-01a1a25f-b098-7242-bf6d-d0e8f6517361.jsonl`: новый собственный `turn_context` строка823, turn `01a1a33a-e373-7503-8b3d-e2c6f8d8f4a0`. Все записи собственного интервала подтверждают top-level `model=gpt-6.1-sol`, `effort=high` и вложенные settings `model=gpt-6.1-sol`, `reasoning_effort=high`; расхождений нет. Назначенный Standard не удостоверяется серверной записью tier. Глобальные/normative admission settings и state root не менялись.

Прочитаны актуальные `AGENTS.md`, `spec/index.md`, прежняя read-only оценка `pl-typed-mixed-checks.md` и actual producer/consumer files. До правок координатору сообщён и им принят scope ≤6 production files и два mismatch negatives. Применён прежний verification-before-completion approach. Сеть, fetch, install, push, PR и ручной CI не использовались.

## Конкретные DTO и границы

Новый type-only `legacy-checks.ts` именует **consumer views существующих полей**, а не новую schema/authority:

- `LegacyPhysicalSelection`: `projectRelativePath`, `submissionRelativePath`, `sha256` — поля, которые реально проверяет прежний `readSelected`.
- `NativeSourceOrigin`: настоящий inferred return `nativeRawSourceSelection`, сохранивший union project-relative/owner-relative после Core ArtifactFile parse и прежних path/hash guards.
- `CheckSourceOrigin`: объединяет physical legacy и настоящий native origin. Owner-only запись не объявляется physical.
- `LegacyDescriptorCheck`: короткий принятый legacy check. Полный `NativeProjectCheck` остаётся его более точным подтипом. Нет требования добавить старым fixtures `'source-profile'`/`tests`; mode/kind/networking допускают прежние inferred string/boolean literals. Feedback/API/public-cases/Java-поля выводятся из существующих схем там, где их форма уже совпадает. Это статический input contract, не заявление о runtime validation произвольного внешнего JSON.
- `ExportCheckProject`/`ExportChecks`: только реальные поля, читаемые exporter. Существующий native projection удовлетворяет им без casts; дополнительные реальные поля manifest сохраняются generic input типом.

`runtime-descriptor.ts` принимает generic legacy consumer-view. Native caller сохраняет точный schema mode через indexed `C['sourceProfile']['mode']`, scoring value удерживается как `C['scoring']` и проходит в прежнюю default-ветвь. Нет forced parse, нового guard, клонирования, изменения значений/wire-полей или удаления проверок. `java-build.ts` принимает ту же реальную узкую часть check; прежние closed recipe/runtime/profile guards остаются владельцами runtime отказа.

`source-selection.ts` меняет только type-only import и `readSelected` signature. Reader принимает конкретный origin union с optional project-path view для существующей проверки. **Это не разрешение читать owner-relative source:** owner-only native запись по-прежнему не проходит `safePath(s.projectRelativePath)` и получает прежний `PL source selection invalid` до IO. После прежнего guard compiler знает конкретный physical path. `readSelected` намеренно принимает owner-only вариант union на входе: его отказ здесь runtime, а не compile-refusal. Negative owner→LegacyPhysicalSelection ниже не доказывает compile-refusal owner→readSelected и не заявляет такого. Hash/symlink/inside-root checks и source bytes неизменны. Внешний raw JSON этим типом не объявляется валидированным.

`native-check-inventory.ts` делает `nativeChecksManifest<Project extends {qualifiedId:string}>` generic и удерживает `Project[]` в результате. Сортировка, reference identity, canonical/hash областей, версии, selection scope и project values не менялись. Никакой новый manifest decoder или copied schema не добавлен; прежний hash-only `legacyChecksProject` helper остаётся отдельным долгом.

## Смешанный writer loop

`export-course.ts` получает `Checks extends ExportChecks`; `fact`, `trustedTests`, origins и check больше не any. Требование полного native schema не навязывается legacy input. Generic input также сохраняет дополнительные accepted manifest fields.

Внутренний `TrustedTest` — discriminated union двух уже существующих payload вариантов: native `{submissionRelativePath, bytes}` и legacy `{origin}`. Native map по-прежнему заранее фиксирует имя/bytes в тех же записях. Legacy generator лениво выдаёт исходные origin references через прежний `for...of`; он не копирует legacy path до первой IO-паузы и не превращает mutable iterable в eager snapshot. В loop сначала выполняются прежние duplicate/reserved-name checks, затем native bytes либо прежний `readSelected`, затем mkdir/write. Выбор reader теперь связан с вариантом payload compiler-ом, а не с unrelated boolean при элементе any.

`kind` существует только во внутренних временных records. В manifest/wire он не записывается. Новых runtime guards, AST/registry/provider contracts, файлов/UUID/scoring целей, hash/scoring алгоритмов, authority/lifetime/check ordering не добавлено. `source-selection` canonicalValue/observability и отдельный Perf Source не изменялись.

Production scope ровно шесть файлов: новый `legacy-checks.ts`, `source-selection.ts`, `runtime-descriptor.ts`, `java-build.ts`, `native-check-inventory.ts`, `export-course.ts`.

## Existing malformed fixtures

Только два прежних early-refusal case в `tests/export-course.test.ts` намеренно передавали `checks:{}`. Новый конкретный contract справедливо не считает это `ExportChecks`. **Значения input и subject отказа не изменены**, полный parse не вставлен: raw object помечен `unknown`, затем только в этих negative runtime fixtures вызывается тот же `exportCourse` через явную внешнюю JS-границу `Reflect.apply`, результат игнорируется. Нет any/casts/suppressions или обхода production consumer.

Первый case по-прежнему требует сообщение `duplicate active defense` и отсутствие body call. Второй по-прежнему требует exact `Error: PL duplicate active defense` раньше переданной nonexistent publication dependency. Это проверяет реальный ранний consumer refusal, а не отказ schema до consumer. Existing happy-path legacy fixtures и их данные не изменялись.

## Проверки и результаты

Существующий checker получил scoped `--legacy-boundary`; новые positive assertions находятся в `tests/native-legacy-boundary-types.ts`. Они проверяют прежний короткий legacy check без parse, настоящий native schema subtype/mode, generic manifest project output, producer→ExportChecks assignment и сохранение origin/hash типов без any. В прежней course-export fixture generic runtimeDescriptor теперь явно instantiated с `NativeProjectCheck`; это сохраняет именно native output assertion.

Итоговая команда `quarto run tests/native-core-typecheck.ts ../core --legacy-boundary` — **exit0**. Один affected graph включает `native-course.ts`, новые/затронутые type fixtures, existing `export-course.test.ts`, `java-build.test.ts`, `text-runtime.test.ts` и runner. Runtime tests этой командой не выполняются. Это одна affected composition, но **три отдельные compiler invocations**: один положительный Deno check и два отрицательных Deno check. Два новых direct compiler negatives:

- `LegacyPhysicalSelection` вместо native test `{name,bytes}` → ожидаемый **TS2739**;
- owner-only origin вместо `LegacyPhysicalSelection` → ожидаемый **TS2353**.

Прежние восемь negatives и course-export standalone controls не запускались. Цена двух новых negatives — два дополнительных Deno/compiler process в scoped режиме; default runner также получает +2 process: теперь1positive+11negative вместо прежних1+9. Batching в этой партии не добавлялся, отдельный framework не создавался. Неуспешная первоначальная positive проверка — ещё один compiler process до итогового зелёного pass, не успешная повторная матрица. Первый compiler проход выявил недоступность отсутствующего project-path ключа в owner union и два deliberate `checks:{}` inputs. Исправлены type-only optional property view reader и явные JS-boundary negative fixtures; итоговый pass зелёный. После итогового green compiler production Source не менялся.

Из-за реального mixed-loop потребления выполнены только четыре разрешённых existing cases в `whole-bank.test.ts`/`export-course.test.ts` с фильтром, read/write/env и run, ограниченным Quarto:

1. `whole-bank: matching mixed duplicate retains complete starter and teacher30`;
2. `selected sources preserve package paths and reject build scripts`;
3. `multiple_selected_defenses_for_one_essay reject before source collection`;
4. `Дублированная защита отвергается раньше переданной неверной публикационной зависимости`.

**4 passed, 0 failed, 29 filtered out, около1s.** Первый — bounded local collected legacy fixture и Pandoc conversion; это не native Core export, quarto render или full matrix. Он подтверждает actual legacy trusted bytes/starter и teacher30. Остальные подтверждают physical mapping/refusal и оба неизменённых early refusals. Новых call-count/retired plumbing tests нет.

`deno fmt --check` семи форматируемых затронутых TS-файлов — exit0; исторически компактные runtime-descriptor/source-selection/export-course test не переформатированы целиком. `git diff --check` — exit0. Старые106/exdev/native/fullCI/render/inspect/build/hash-аудит/установки не повторялись. Проверки использовали существующий локальный Quarto/cache/import map, без network dependency fetch.

Финальный read-only Core Source HEAD — `2e71100f773c8185c7f125c44167b1555ec669f5`, status чистый. Map указывал на фактический Source directory; это локальная type composition, не immutable byte snapshot и не новый paired hosted CI. Core/Java/installed/courses не редактировались; CI pin этим delta не менялся.

## Остатки и приёмка

Внешние owner/config JSON loaders, registry/provider API, `selectedSources` raw project input, hash-only legacy projection, content/answers/body/staged handles остаются отдельными границами. Полное покрытие TS стека не заявляется. Типовые consumer views не дают runtime authority и не заменяют существующие gates/guards. Независимый review обязателен до утреннего push; integration/state ведёт root.

## Source commit

Own-files commit: `2dd7e00735facaa437ec39ccb4a40b139a1f716e` поверх `9c62960ff345552dad492d8cc560dc6760787ec5`, сообщение `Сохранить legacy check и origin DTO до mixed trusted-tests writer`. Ровно 10 файлов: 6 production и 4 test/typecheck files; 255 insertions, 70 deletions. После commit `git status --short` пустой, `git diff HEAD^ HEAD --check` — exit0. Отчёт находится вне PL Source repo. Push/install не выполнялись; delta передан root для независимого review. Зелёные compiler/runtime checks после commit не повторялись: Source после их итогового запуска не менялся.

## Bounded исправление после независимого review

Review `pl-typed-legacy-boundary-review.md` исходного `2dd7e00` обнаружил P2: generic optional scoring сохранял запасной `{mode:string}`, расширяя native scoring union. Прежний `_NativeDescriptor` сравнивал производные одного ослабленного типа и этого не обнаружил. Замечание воспроизведено и исправлено только в двух файлах.

В existing `tests/native-legacy-boundary-types.ts` добавлен прямой `Exact<typeof nativeDescriptor['scoring'], NativeProjectCheck['scoring']>`. До production исправления адресная компиляция этой fixture завершилась **exit1 / TS2344, false does not satisfy true**, строка51. Это реальный RED на прежнем Source, без suppression или изменения native schema.

В `runtime-descriptor.ts` fallback стал `({mode:'weighted'} satisfies {mode:'weighted'})`. Это проверяемое ограничение типа без cast, нового generic framework или validator. Литерал сохраняет `mode:'weighted'`; nullish expression и его runtime значение/порядок вычисления прежние. Native scoring сохраняет полный существующий Core union и обязательные поля соответствующих вариантов, а короткий legacy scoring остаётся accepted.

Для GREEN использован transient `/tmp/pl-legacy-scoring-check.ts`, исполняющий **только positive Deno check** с прежними `--no-config --no-lock --import-map /tmp/pl-ci-core-imports.json` и ровно прежним affected graph: `native-course.ts`, `native-course-export-types.ts`, `native-legacy-boundary-types.ts`, `export-course.test.ts`, `java-build.test.ts`, `text-runtime.test.ts`, `native-core-typecheck.ts`. `quarto run /tmp/pl-legacy-scoring-check.ts` завершился **exit0**. Два уже passed negative compiler processes и четыре runtime cases не повторялись. Всего для correction — одна RED fixture-компиляция и одна GREEN positive graph-компиляция. Перед RED была одна отклонённая CLI-попытка с неподдерживаемым bundled Deno флагом `--cached-only`; compiler тогда не запускался. Исправленная команда использовала существующие локальные cache/import map, скачиваний не было. Core Source по-прежнему `2e71100f773c8185c7f125c44167b1555ec669f5`.

`git diff --check` после GREEN — exit0. Delta: 2 файла, +4/-1. Новых runtime/negative suites, network/install/push нет. Own primary admission correction interval: тот же собственный session/chat, `turn_context` строка1080, turn `01a1a34b-8ddf-76b3-8784-544f234b4d8b`; top-level и вложенные настройки `gpt-6.1-sol/high`, Standard назначен, скрытый server tier первичным контекстом не удостоверяется. Глобальные настройки не менялись.

Correction commit: `30edc03295e508f174c64b399f35b14ad37488fa` поверх `2dd7e00735facaa437ec39ccb4a40b139a1f716e`, сообщение `Сохранить точный native scoring в legacy descriptor consumer`. После commit Source clean (`git status --short` пустой), `git diff HEAD^ HEAD --check` — exit0. Source после GREEN не менялся, compiler после commit не повторялся. Correction возвращается тому же независимому reviewer через root; review приёмка этого нового commit ещё не заявлена.
