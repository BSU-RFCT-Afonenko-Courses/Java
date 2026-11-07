# Platform: частичная типизация native payload reader

## Итог и граница

Локальный итоговый commit `9b82e29bffd77cc462ffdef23cd3b96ca041db92` поверх `9227a111701edf6dbc37d97253e561a5f2c9dcd7`, без reset/push. Исходный HEAD `b37febb6e46da681b16b27eadb676e29b764130b` и оба исходных/итоговый рабочие деревья чистые. Итоговая партия меняет один production file `tools/native-course-payload.mjs` и добавляет один regression в существующий `tests/bridge/native-course-payload.test.mjs`.

**Прежнее утверждение отчёта о полном typed DTO flow и неизменности accepted payload при переходе на parsed result в 9227a11 отозвано и заменено исправлением 9b82e29. Reader типизирован частично.** Свидетельства прежних hash/version negative cases относятся только к отменённому возвращаемому типу и не являются доказательством итогового DTO.

## Собственный первичный допуск

Назначение `gpt-6.1-sol` / `medium`, Standard. Собственный chat `01a1a4b9-662c-7bd2-a091-e65c16d8936d`, agent `/root/platform_typed_payload`; исходный turn `01a1a4b9-6656-7462-8afa-bdcc899c60ba`, root turn `01a1a4b3-8fd3-7be3-baf7-ce0cc5c60016`.
Первичный источник `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T07-02-56-01a1a4b9-662c-7bd2-a091-e65c16d8936d.jsonl`: session_meta строка1 подтверждает собственные id/path/provenance. Весь интервал роли при повторной проверке содержит turn_context строки8,133,251,269: верхние `model=gpt-6.1-sol`, `effort=medium` и вложенные collaboration_mode settings совпадают во всех четырёх записях; root turn тот же. Начало роли не сдвинуто. Расхождений нет. Standard назначен; service tier первичные записи не удостоверяют.

## Коррекция после независимого review

Review `reports/platform-typed-payload-review.md` выявил P2: actual Zod4.6.7 `v4/core/schemas.js:1811–1813` пропускает own `__proto__` при разборе record. Авторская схема может не иметь transform/strip, но сам parser всё равно меняет такой record. Прежний вывод owner по одному тексту schema был недостаточен.

Итоговый reader снова использует raw JSON для canonical hash, candidate/files loop и return; существующий `bridgeDeliverySchema.parse(delivery)` остаётся единственной schema validation, результат намеренно не подменяет raw domain. Полный `BridgeDelivery` typedef и завышенная return annotation удалены. Сохранены `@ts-check`, string параметры reader/containedFile и `info:unknown`, так как metadata этим reader не валидируется. Комментарий прямо фиксирует непроверенный raw delivery. Явное `.toString('utf8')` перед JSON.parse сохраняет прежнее неявное преобразование Buffer; файловые bytes и SHA256 operands не меняются. Порядок проверок, return keys, strict/passthrough behavior schema и допустимость имени файла `__proto__` сохранены. Новых runtime validations, двойной validation/hash, схем, casts, suppressions и record framework нет.

## Реальные зависимости адресной проверки

В Platform нет node_modules. Использованы настоящие cached Zod4.6.7, прочитанные из npm cache index `/home/tolya/.npm/_cacache/index-v5/88/5d/25679281b30cf75a621bf8d7e1b724abec207e5af6227dfcf8f503319558` и content `/home/tolya/.npm/_cacache/content-v2/sha512/83/c5/b60f6914e9af2e9ed734e777d7d6e5412b333bcaa0315bfdf3c24f37fba472176f9228eb95f9cd30043854c335753da19aa66647fab99c5333dffb7086c2`, извлечённые после GO в `/tmp/platform-typed-payload-pmt1pbq1/zod/package`. Нового hash cache/Source не вычислялось; encoding integrity использован только для адреса существующего content.

Compiler API: `/home/tolya/JavaCodex/java-release-20261110/reports/performance-sp-pl9c-delta/launchers/backend/node_modules/typescript/lib/typescript.js` (5.9.3). Actual Node declarations: `/home/tolya/.cache/codex-runtimes/codex-primary-runtime/dependencies/node/node_modules/@types/node`.

Временный `/tmp/platform-typed-payload-pmt1pbq1/tsconfig.json`: allowJs=true, checkJs=false, strict=true, noEmit=true, module/moduleResolution=NodeNext, target=ES2022, skipLibCheck=true, types=[node], actual typeRoots, paths zod на извлечённый index.d.ts. Собственный JS проверяется только через reader @ts-check; delivery/policy imports дают actual inference без объявления их JS проверенным. Native/upstream service graph не загружается. Временный loader только разрешает bare zod на actual extracted index.js; native service mock signatures нет. Tooling/config не добавлены в Source; install/fetch не выполнялись.

## Проверки исправления

Один affected compiler process после исправления production file, exit0:

```sh
node /tmp/platform-typed-payload-pmt1pbq1/check.cjs
```

Один Program включает actual reader и временный negative.mts. В reader диагностик0; весь ожидаемый список отрицательных diagnostics:

- `negative.mts:4 TS2345`: `readNativeCoursePayload(42)` отвергается, параметр string.
- `negative.mts:5 TS18046`: `payload.info.name` отвергается, info unknown.

Прежние hash/version negative cases удалены из временного check: raw delivery остаётся any, поэтому обещать их отказ в итоговом reader было бы неверно. Ни casts, ни подавления в отрицательных случаях нет. После compiler production file не менялся.

Один новый именованный regression, две проверки, окончательный exit0, tests1/pass1/fail0:

```sh
cd /home/tolya/JavaCodex/java-release-20261110/platform
node --experimental-loader /tmp/platform-typed-payload-pmt1pbq1/zod-loader.mjs --test-name-pattern='own __proto__ inventory' tests/bridge/native-course-payload.test.mjs
```

Первый assert сравнивает Object.entries возвращённого files с raw inventory: raw canonical hash принят, own `__proto__` filename/hash сохранён; соответствующий реальный файл создан. Второй assert требует `Delivery hash mismatch` для того же raw inventory с hash очищенного payload; в этом каталоге файла `__proto__` нет, его нельзя скрыть удалением ключа parser. Обычные четыре ранее пройденных reader cases после прямого rollback не повторялись.

Первый запуск нового regression после первого прошедшего assert завершился EEXIST в тестовой подготовке: cleaned hash совпал с исходным fixture directory. Удалён только лишний mkdir, после чего повторён только этот regression и получен PASS. Compiler не повторялся, потому что менялся только этот тестовый setup. Это реальная коррекция test setup, первый failed run не скрывается. `git diff --check` прошёл перед commit.

## История и пределы результата

9227a11 сохранён в истории. До review его один compiler check и четыре существующих runtime scenarios прошли на обычном fixture; они не покрывали специальный record key и не доказывали неизменность accepted domain. File-level Node --test pass1 и tests0 из ранних попыток не засчитывались; прямой запуск давал реальные именованные сценарии. Новое замечание устранено сохранением raw flow, а не запретом имени файла.

Полный raw delivery DTO и весь consumer chain остаются открытым долгом; schema output не является полным типом raw domain. info unknown честно обозначает непроверенную границу downstream bootstrap. Глобальный JS CI/type coverage, native services/DB/ACL/scoring и bindings/bootstrap/sync этой партией не подтверждаются. Core/PL/Download/Performance, pins/state не менялись. Build/render/inspect, Docker/grader, network/install, push/reset не выполнялись. Итоговый commit готов к отдельному независимому re-review; owner не заявляет его результат.
