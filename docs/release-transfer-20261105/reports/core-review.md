# Независимый технический review Core, 5 ноября

**Готово к интеграции: да, в рассмотренном объёме. Открытых критических, важных или незначительных замечаний нет.**

Точный итоговый диапазон: `010ba917909fa7d44179d4a1a05291f88f061775..e8a989222266ede20fc037a1fe3e6f66efcaceb9`, ветка `codex/core-yaml-20261105`, checkout `/tmp/java-release-work-20261105/core`. Production сначала рассмотрен до `433fa5a`, tests-only migration — до `7795967`; исправляющий delta `7795967..e8a9892` просмотрен целиком после freeze. `git status --porcelain` пуст; `git diff --check` PASS. Рецензент не менял checkout/index/HEAD и не писал продукт.

Рецензент: `/root/core_review`, собственный chat `01a1a8a1-ab5c-7620-8830-6eb20fed81e5`, исходный ход `01a1a8a1-ab93-7852-9946-77d0cb6ffb64`. Назначение `gpt-6.1-sol/high`, Standard. Первичный и итоговый допуск: `core-review-admission.json`; весь исходный интервал проверен от того же хода, оба уровня model/effort совпадают. Субагенты не запускались.

Требования: `EXECUTION-BRIEF-20261105.txt`, `core-plan.md`, согласованный договор `coordination/core.json`, исполняемые схемы и обновлённый `spec/index.md`. Шаблон review: `superpowers/requesting-code-review/code-reviewer.md`.

## Сильные стороны

- Узкий loader обрабатывает только `course-project`, сохраняя реальную Quarto metadata для документов. Каждый YAML читается один раз внутри operation loader, в том числе между последовательными batch и formats; context lifetime от кеша независим.
- Нормализация адресов перед merge сохраняет собственную базу include и атомарный override path/base. Maps, scalar, `false`, selected profiles, `.local`, format namespace и missing/decoy соответствуют согласованному договору.
- Собственный SHA/snapshot/freshness-граф удалён замкнуто в production. Нет замены на mtime/inode version fences, synthetic QMD, copyTree, per-project inspect, origin/CST DTO или fake SHA. Physical identities используются функционально для privacy выбранных ролей.
- Общий predicate ролей применяется к assignment и ordinary materializer. Actual source aliases и opened native resource identity защищены, включая cached native bytes и fallback при отсутствующем original. Обычные публичные condition resources вне selected source trees сохраняются.
- `ArtifactFiles`, `NativeResourceFiles`, `NativeReleaseValidation` имеют согласованные функциональные поля без удалённых certificate fields. Производители и внутренние readers замкнуты; TS graph проверяется без casts/stubs.

## Замечания

### Критические

Открытых нет. Исправлена обнаруженная рецензентом утечка закрытого solution через ordinary student artifact: hardlink solution → starter под нейтральным именем экспортировал reference bytes. Отдельный native probe подтвердил второй путь через relocated native resource при отсутствии original source. Финальный общий role guard проверяет оба пути, включая actual opened resource handle. Native regression исполнителя RED→GREEN; независимый recheck второго пути GREEN.

### Важные

Открытых нет. Исправлены:

1. Повторная нормализация YAML alias с `base: config` ошибочно принимала собственный absolute path за authored absolute. `WeakSet` ограничивает нормализацию одним visit каждого address object; regression с двумя build targets на один shared адрес GREEN.
2. Tests-only migration обращалась к `complete.state.quarto.version`, хотя версия остаётся в `ownerWitness`. Доступ восстановлен; actual native default runtime test PASS.

Дополнительно исполнитель закрыл explicit `course-project: null` через decoder вместо превращения null в пустой namespace; адресный RED→GREEN просмотрен.

### Незначительные

Открытых нет. Замечание о ложном `treeWalks: 0` закрыто: внутренний callback считает actual root walks selected trees, включая role collector. Финальные condition logs показывают ненулевые значения; нового DTO/API не введено.

## Проверки и доказательства

Независимые проверки рецензента:

- Прочитан изменённый функциональный production-граф: contracts/loader/context, plan/materializer, capture, installed adapters, source paths, assignment admission/execution/declarations/provenance/roles, capabilities/resource handling, operation и release/publication; адресные тесты и migration просмотрены отдельно.
- `/tmp/core-review-profiles.ts`: настоящий `_quarto.yml profile.default:[alpha,beta]` → actual native `QUARTO_PROFILE`/identity `[alpha,beta]` → loader выбирает `alpha` scalar `hidden=false` поверх `beta=true`. PASS, 4 YAML, 8.905 с. Собственного выбора defaults у loader нет.
- `/tmp/core-review-privacy.ts`: до исправления ordinary native student artifact экспортировал `src/main/java/Leaked.java` с bytes `class Reference {}`; 13.272 с. Исполнитель добавил различающий native regression и получил RED→GREEN.
- `/tmp/core-review-resource-privacy.ts`: до полного исправления ordinary generated resource экспортировал `<svg>PRIVATE-SOLUTION</svg>`. `/tmp/core-review-resource-recheck.ts` после исправления на том же сохранённом actual capture получает `BODY.RESOURCE_PRIVATE_ROLE`; GREEN, 0.681 с, без повторного render.
- `/tmp/core-review-alias-config.ts`: два build targets с YAML alias `base: config` — RED `PROJECT_PATH`, затем GREEN с правильным абсолютным shared адресом; 0.645/0.705 с.
- На frozen `e8a9892` собственный affected compiler: `public.ts`, `capture.ts`, `course-project-native.ts`, `course-project-loader.ts`, `native-default-runtime.ts` — exit 0; `core-review-types-final.log`. Использованы настоящий Quarto Deno cache/import map, `--node-modules-dir=none`, без type stubs. Первая самостоятельная попытка с другим cache остановилась на JSR network lookup; после применения существующего actual cache граф проверен успешно, продукт для этого не менялся.
- Production references к удалённым `ArtifactInventory`, `NativeResourceInventory`, `NativeReleaseProof`, source-snapshot/contract-carrier/invocationIdentity/NativeOrigin не найдены. Vendored crypto regex/API не являются собственным SHA графом.

Финальные результаты исполнителя прочитаны и сопоставлены с точным delta:

- `core-final-types.log`: public + 6 адресных tests, все Check rows, exit 0.
- `core-final-focused.log`: 7 checks loader/guards/roles/files/private resources/resource DTO — PASS.
- `core-condition-final.log`: 5 condition/closure/materialization/schema/target checks — PASS; legitimate public resources и real bytes сохранены.
- `core-private-resource-green.log`: один реальный native render и оба ordinary privacy bypass regression — PASS.
- `core-default-final.log`: actual native student/full default runtime, actual version и resources — PASS.

Ранние ограниченные результаты из `core.json`, `core-runtime.json`, `core-tests.json` рассмотрены как исполнительские проверки, а не выданы за независимые повторные запуски. Неизменные A1–A5 и весь canonical не повторялись.

## Отказ от суждения (Declined to judge)

- Изменение source после capture и отсутствие freshness guarantee: это прямо принятое удаление собственной архитектуры fencing. Для privacy рассмотрены actual aliases, а не восстановление freshness promise.
- Итоговая интеграция внешних PL/Download consumers: writers приняли новый несовместимый договор; reviewed API сопоставлен с согласованным handoff. Их старое переходное состояние не трактуется как compatibility blocker Core; их финальная интеграция здесь не принимается.
- Full-course performance, все 128 заданий, UI, Docker, production/VM/deploy: данный пакет и ограниченные fixture этого не подтверждают; таких утверждений review не делает.
- Старые проверки удалённых synthetic/origin/copy/snapshot свойств: их удаление соответствует принятому объёму; актуальные safety/lifetime/schema/bytes проверки рассмотрены отдельно.

## Оценка

**Готово к интеграции: да.** Все обнаруженные в review дефекты закрыты в `e8a9892`, точный delta и различающие проверки подтверждают исправления. Приёмка относится к Core source/API и перечисленным bounded native checks; она не заменяет внешнюю интеграцию или full-course/UI/Docker/production приёмку.
