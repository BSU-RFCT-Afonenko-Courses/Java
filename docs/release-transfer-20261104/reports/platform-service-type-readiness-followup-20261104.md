# Platform service types: readiness после получения PostgreSQL Source

**Итог: семантический пробел с transaction/query API уточнён, но честный исполнимый declaration graph пока не собран. Компиляцию начинать рано.** Публичные исходники PostgreSQL дают основание сохранять generic результата transaction callback; они не превращают нынешние строки `z.any()` в проверенные SQL DTO. Новый checker/config или editor-only annotations не созданы.

Source-only продолжение [первого плана](platform-service-types-next-20261104.md) и [остановки без изменений](platform-service-types-20261104.md). Platform actual HEAD `6491517b896b17119fd2ab44db1a2de2ff9c93b1`. Перечитаны AGENTS; status содержит ровно прежние четыре frozen JDK SHA files: grade.py, runtime-profiles.json, test_gradle_review_fixes.py, prepare_native_toolchain.py. Их содержимое не менялось. JDK-candidate остаётся непринятым, stage/commit/push отсутствуют.

## Установленная Source composition

`images/prairielearn/Dockerfile:1–12` использует Community base с declared Source `92584fe426ececb84bc2d09de9975c7056c0c5f6`, применяет existing recognition patches и кладёт bridge/tools внутрь `/PrairieLearn/apps/prairielearn`. Там `bridge/recognized-credit-native.mjs:41` действительно импортирует bare `@prairielearn/postgres` и `zod`; native grading/file-store открываются соседними relative URL в `dist/lib`. Это контракт сборки, не attestation действующего контейнера.

Новый [saved upstream](platform-postgres-source-92584/README.md) имеет тот же92584 ref и package `@prairielearn/postgres8.0.3`, Node≥24, main `dist/index.js`. В нём сохранены **TS Source**, а не опубликованный `.d.ts` package:

| Реальная связь | Что установлено чтением | Что остаётся |
| --- | --- | --- |
| `src/index.ts` → `default-pool.ts` → `pool.ts` | Public functions — `.bind(defaultPool)` реальных методов `PostgresPool`; потеря receiver предотвращается upstream | Barrel также экспортирует отсутствующие `loader.ts`, `error.ts`, `test-utils.ts`. В частности, нужный нам `loadSqlEquiv` находится в missing loader, не в pool |
| `pool.ts:425+ runInTransactionAsync<T>` | Callback вызывается в transaction client scope; его result типаT возвращается после commit, ошибка rollback/rethrow. Generic здесь описывает реальную передачу результата | Само наличие generic не проверяет содержимое callback, наши administrative inputs или DB rows |
| `pool.ts:629–708 queryRows/queryRow/queryOptionalRow` | Overloads выводят `z.infer<Model>` из реально переданной schema; `.parse` вызывается после запроса, optional branch возвращает null | Current own `schema:z.any()` означает непроверенный result. SQL SELECT name не является типом. `queryRow<NativeIQ>` без parser не соответствует этому API |
| `pool.ts:1–8` dependencies | Реальные imports debug, es-toolkit, pg, pg-cursor, zod и Node | Нужны actual declarations/exports соответствующих packages; одних трёх TS файлов недостаточно |
| `nativeDb().recompute` | Сохранённый upstream `lib/assessment-grading.ts:26–40` задаёт реальные string AI/authn IDs, credit number/null, boolean flags, result `{updated,points,score_perc}` | `db-types.js`/`@prairielearn/zod` и весь module closure не установлен. Own динамический `import(new URL(...))` не разрешает этот graph TypeScript автоматически |
| Private file seam + immediate caller | `recognizedPrivateFilesApi` загружает Native file-store; `recognize-credit.mjs:10` при отсутствии injected DB загружает manualGrading | File-store TS требует aws/config/db-types, Native utils/zod и debug; manualGrading требует ещё Native modules. Сохранённые compiled fixtures не являются declarations этого полного graph |

Existing patch `patch-recognized-credit.py:12–14` добавляет в Native manualGrading именно **re-export собственного `recognizeCredit as recognizePreviousCredit`**, не вторую реализацию. Будущему owned caller следует переиспользовать тип собственного метода, а не копировать Native module declaration. Этот наблюдаемый runtime re-export не оправдывает объявление всего manualGrading произвольным typed stub.

## Что действительно доступно локально

Повторно прочитаны saved9b checker и manifest известных зависимостей; binaries не исполнялись:

- TypeScript **5.9.3**: `reports/performance-sp-pl9c-delta/launchers/backend/node_modules/typescript`; existing compiler-API runner — `/tmp/platform-typed-payload-pmt1pbq1/check.cjs`.
- Node declarations **25.9.5**: `/home/tolya/.cache/codex-runtimes/codex-primary-runtime/dependencies/node/node_modules/@types/node`.
- Actual Zod **4.6.7**: `/tmp/platform-typed-payload-pmt1pbq1/zod/package/index.d.ts`.
- В двух **этих известных node_modules compositions** нет pg, @types/pg, pg-cursor, @types/pg-cursor, es-toolkit; в runtime bundle есть debug JS, но нет @types/debug. Это не утверждение, что пакетов нет нигде на диске: общий поиск cache/filesystem не проводился. В Platform local node_modules/полный @prairielearn package не установлен.

Наличие Node declarations25.9.5 не доказывает совместимость с upstream dev-config24.x/TypeScript7.0.2; фактический Source parse/check текущим5.9.3 также пока не выполнялся. Zod версия удовлетворяет указанной upstream зависимости, однако полный graph из этого ещё не следует.

Saved9b config использует NodeNext/allowJs/strict/noEmit, `checkJs:false` и отдельный owned `@ts-check` reader. Перенос **механики** возможен, результата — нет: он не проверяет recognition bodies. `skipLibCheck` не устраняет отсутствующие TS Source imports. Mapping `@prairielearn/postgres` только на pool/default-pool вместо настоящего barrel потерял бы public exports и подменил контракт. Stub modules, copied method declarations, отбор diagnostics по функциям и подавление unresolved imports не предлагаются.

## Минимальное следующее действие и owned diff

До checker нужны сохранённые matching declarations всего consumed PostgreSQL package (или complete92584 Source + его реальные зависимости), включая loader и транзитивные exports. Отдельно нужны точные typed входы Native grading/file-store либо честно изолированный owned port с подтверждённой фактической реализацией. Нельзя считать DTO/port checked, пока этот actual implementation остаётся any. Получение этих **публичных development inputs** не требует production/DB/config consent и не является разрешением на новую сеть в данном поручении; сейчас они не получены.

После closure наименьший полезный owned пакет остаётся исходным двухфайловым `recognized-credit-native.mjs` → `recognize-credit.mjs`: checked discriminated recognition result + сохранение callback-result через реальный transaction API. `eventId` нельзя объявлять string только по unchecked `event.id`; после `same/success/maximum/full` допустим лишь реально проверенный consumed view. Raw row/extensions, canonical/hash domain и private blobs сохраняются, новой schema/strip/validator нет. Full service body должен проверяться вместе с этими результатами, а не одной импортируемой декларацией; если для этого опять требуется шире двух файлов, сохранить точный долг, не косметическую signature.

Независимо полезного более малого Source diff **при нынешнем incomplete graph не доказано**. Извлечение двух функций ради отдельного зелёного checker не предлагается: прежнее решение root об отсутствии достаточной пользы такого обхода остаётся действующим.

Когда real graph установлен: один affected compile, **outer cap10 s/retry0**, strict allowJs/checkJs/noEmit для обоих owned files, actual package/type paths; ровно один typed negative на настоящий result mismatch (`NEEDS_NATIVE_ADMIN_EVENT` не имеет completed result fields) допустим в той же composition. Без new row schemas, generic row facade, diagnostic filtering или широкого checker. Сейчас config намеренно не записан и compiler не запущен, поскольку отсутствующие inputs уже известны Source-чтением. Cost будущей compilation UNKNOWN; cap — ограничение, не измерение.

Прочитаны только relevant Source/manifests/готовые reports. Runtime/JVM/Gradle/Docker/tests/compiler/network/DB/SSH/config, новые installs и изменения Platform Source/state отсутствуют. Own full-origin Sol6.1/high admission — existing reference в [последнем candidate report](jdk-official-image-hashes-20261104.md); новое env/admission чтение не выполнялось.
