# SP: ограниченное принятие существующего contract batch

3 ноября 2026. Только Source-предложение координатору; запись Source не разрешалась и не выполнялась. База isolated SP: `/home/tolya/JavaCodex/java-release-20261110/system-practice`, branch `codex/java-release-20261110`, HEAD `fcc5a2ac1eff3760d306a337a9c8871780365488` по проверке root до передачи. Старые PR/checkout не изменялись. Java installed используется только для адресного чтения уже принятых Core9.3.4 и PL354. Новых render/inspect/build/materializer/tests/hash/benchmark/CI не было; субагенты не запускались.

## Собственный primary admission

Норма: `java/docs/model-admission.md`; её файл не изменялся. Назначение: технический владелец `gpt-6.1-sol / high`, Standard.

- Собственный chat ID: `01a1a20e-59d5-7de3-a37c-f5294d684f36`; path `/root/sp_batch_adoption`; parent `01a1a142-7adc-7371-9765-4871d807dfcd`.
- Исходный turn: `01a1a20e-5a0b-7371-bb72-1e5ac5965329`.
- Первичный источник: `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T18-36-51-01a1a20e-59d5-7de3-a37c-f5294d684f36.jsonl`; session_meta строка1 устанавливает собственные ID/path, turn_context строка8 устанавливает model=`gpt-6.1-sol`, effort=`high`, nested collaboration_mode.settings model=`gpt-6.1-sol`, reasoning_effort=`high`.
- На проверенном интервале от исходного turn присутствует один turn_context, совпадений/противоречий назначения нет. Standard принят по явному назначению; service_tier в первичных записях отсутствует, поэтому отдельного первичного свидетельства серверного tier нет. Скорость/глобальная конфигурация не менялись. Проверка не утверждает отсутствие скрытой маршрутизации.

## Решение, предлагаемое root

Принять один owner migration: existing Core contract batch + PL consumer + `tasks` static owner. Это устраняет per-project metadata probes у PL upload и Workspace, потому что они входят в одну `withNativeProjectPlans` композицию. Для устранения того же Ninspect в post-render скачиваниях требуется также existing Download3.5.0 consumer. Это точная дополнительная зависимость, а не повод обновлять все providers. Рекомендуемый ограниченный пакет — Core9.3.4, PL9.0.7+35496cdd, Download3.5.0, только `tasks/_quarto.yml: course-core-source-policy: static`, manifest/lock/patch provenance; UI/Site/QRC/Platform pins сохранить.

Одного Core pin либо одной static строки недостаточно. Никакого нового snapshot/validator/authority API и правок productcode в vendor не требуется. До решения root Source остаётся нетронутым.

## Конкретные pins

| Provider/package | Сейчас SP | Предложение и причина |
| --- | --- | --- |
| `course-core` | Core9.1.0, `04c189fccfaa56badfc079f7be46fea4969ccf37` | Core9.3.4, `v9.3.4`, `a478d2cc0dfb19e0bebd99108815ac8ef1fe85a8`; existing `withProjectContracts`, static/checkpoint, narrow aggregate |
| `course-prairielearn` | 9.0.1, `df8bd4aad274d618a782265b1db9c63e9b9a40f9` | published9.0.7 `1c41f03f83b26adfe58415b4d89958a3663fabf2` + explicit local-unpublished candidate `35496cdd547d6ad2263fe171e451fb251c793c83`; native-course consumer выбирает contract API и callback checkpoint |
| `project-download` | 3.3.1, `e4ce084d8d15381368def66c746b51c6ba810a7d` | 3.5.0, `v3.5.0`, `c81df7364dd1fbbd07a0e54d67714b3d66f9695e`; второй реальный consumer старого context API |
| Presentation/Navigation | bundled9.1.0, `04c189fccfaa56badfc079f7be46fea4969ccf37` | Сохранить прежние bytes/pin/scopes через отдельную manifest provider запись |
| Site/QRC/Platform | Site5.3.0 `e0efd6b5428412500569d0da5a5e0b33d62a5663`; QRC3.2.0 `798f8c0d9f87fd5dcfe9ee85b79df57e647d2f3c`; Platform2.5.2 `626b665d1c8d18b61480ce91c7bc8f7d41f99bdb` | Для metadata migration оснований менять нет |

PL provenance уже существует в `java/providers.json` и `java/tools/provider-patches/pl-release-20261110.patch`: patch SHA256 `21c748d16697b178ea4ac45c8c0215cd1ca4983f74b4549e54716c4c0449c3a7`, package lock `24ddeb7ddbb4200ba6fc0c6fddd495a10142ae5a664b5e99b79c504753fba93c`. Значения прочитаны из существующего manifest, заново не вычислялись. Публикация release не выдумывается; candidate сохраняет отдельный статус. SourceZip для опубликованных pins — `https://github.com/<repository>/archive/refs/tags/<tag>.zip`.

## Найденные consumer несовместимости

1. SP PL `tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/application/native-course.ts:36,70` требует и вызывает `operation.withProjectContexts`. Java PL354 там же:36 предпочитает `operation.withProjectContracts`, а :79 захватывает batch checkpoint с lifetime callback; fallback старого Core оставляет full assertCurrent. Core9.3.4 `runtime/operation.ts:2971,2977` предоставляет оба метода, но только второй передаёт `contractsOnly=true`. Поэтому старый PL с новым Core продолжит per-input fallback.
2. SP Download `tasks/_extensions/Afonenko-Course-Tools/project-download/infrastructure/runtime.ts:590` тоже вызывает `withProjectContexts`. Java Download3.5.0 :600 использует `withProjectContracts`. Без этого третьего provider PL batch ускоряется, а download post-render сохраняет свои probes. Оба consumer используют `Core.planProject`; generic/full-context API удалять нельзя.
3. Current materializer `tools/materialize-providers.py:67` требует descriptor version каждого package равной `provider.version`. Файл у Java и SP одинаков. В exact Core9.3.4 bundle `course-presentation` и `course-navigation` имеют descriptor9.3.2. Простая замена единственного SP `core` pin с тремя packages на9.3.4 будет отклонена. Минимальное owner решение: `core` на9.3.4 содержит только `course-core`; новая запись, например `core-ui`, содержит прежние Presentation/Navigation с тем же old9.1.0 pin и прежними six scopes. `installed-packages.json` меняет их provider affiliation/provenance, bytes остаются прежними. Новый installer/version API ради этого не нужен. Не следует переносить Java manifest целиком с этой mixed-version несовместимостью.
4. Old Navigation использует `course-core/public.ts` NativePublicDocument/NativeCatalog и `release/collect.ts` nativeMetadata. Эти exports имеются в9.3.4; это адресное source свидетельство возможности оставить UI pin, а не доказательство полного render compatibility. Publisher/QRC consumers также остаются в bounded smoke, обновление их pins не предрешено.

## Почему aggregate применим к SP

Existing Core9.3.4 `infrastructure/project-context.ts:1022` допускает narrow path только для contract-only, static source, exact Quarto1.11.5, html, отсутствующего synthetic input и строго local metadata contract. `runtime/operation.ts:2686` связывает aggregate probe с текущими S/request/format; :2574 checkpoint остаётся внутри callback; :2603/:2792 сохраняют полные entry/exit fences.

В фактическом SP `CI/toolchain.json` закреплён Quarto1.11.5; `tasks/_quarto.yml` использует html, native-ts и student projection. Семь local `_metadata.yml` прочитаны непосредственно:

- `tasks/projects/work-environment/{file-state,launch-counter}/_metadata.yml`;
- `tasks/projects/data-processing/two-result-pipeline/_metadata.yml`;
- `tasks/projects/version-control/{local-history,github-ssh,restore-selected-state}/_metadata.yml`;
- `tasks/projects/project-maintenance/release/_metadata.yml`.

Каждый имеет единственный top-level `course-project`; starter.path=`student`, instructions.local.path=`student/README.md`. Два work-environment проекта добавляют mapping build.files с относительными ../../../common путями, release — ../../../materials/project-maintenance-start.zip; пути нормализованы, absolute/backslash/array contract значений нет. Existing synthetic `native-input.qmd` в этих проектах не найден. Для данного source shape ограничение `canonicalContractScalars` не требует авторской переделки starter/целей. Owner/profile metadata на execution/brand overrides всё равно квалифицируется самим Core при будущей operation; preflight источника не подменяет runtime qualification.

При успешной qualification Core :1101–1190 собирает missing set в одном disposable `_quarto.yml`, `project.render: []`, один `inspect` owner aggregate, проверяет native namespace/markers и переиспользует contractCache. Оно не копирует whole S и не создаёт N synthetic render inputs. Для уже cached set новых probes нет. Отказ от qualification ведёт в существующий общий fallback; без реального smoke нельзя заявлять observed inspect count или ускорение всего курса.

## Границы следующего diff

Обязательные owner файлы: `providers.json`, `installed-packages.json`, новый `tools/provider-patches/pl-release-20261110.patch`, `tasks/_quarto.yml`; актуализировать факт provider состава в `CI/toolchain.json.status` и `docs/provider-installation.md`, если сохраняется описание поставки. Вендорные изменения — только materialized exact packages, авторский productcode не редактировать:

- `course-core` в текущих SP scopes `.`, `theory`, `lectures`, `practice`, `tasks`, `handbook` (единый package pin; статическая policy нужна непосредственно owner `tasks`);
- `course-prairielearn` только `tasks`;
- `project-download` только `tasks`;
- Presentation/Navigation retain old scopes через `core-ui`; Site/QRC retain old manifests.

Не копировать Java `_quarto.yml`, Java delivery policies, namespace, task UUID, assessment defaults, grades/grading, аккаунты или Java-specific checks. SP `course.id=system-practice`, name=SYSTEM-PRACTICE, pilot work list и question IDs сохраняются. Existing course UUID вычисляется `identity('course/'+courseId)` и в old/new PL; default assessment UUID остаётся `identity('assessment/'+work.id,courseUuid)`, если owner не задаёт override. Не добавлять UUID override без отдельного задания. Upload default остаётся `mode: upload`; existing Workspace override `tasks/labs/version-control-workspace.yml` и digest `a547a7419726857eb391476edcf8906ee4acc062250e1c29acaee4733d9de930`, home/gradedFiles/SSH/GitHub/public-private учебные обязанности сохраняются.

Static — явное обещание owner об отсутствии вычисляющих engines и неизменности источника во время operation. Core `runtime/operation.ts:941` проверяет native markdown facts и codeCells; нельзя включать static глобально на всех SP owners лишь вслед за Java. Непосредственный нужный owner — `tasks`; остальные получают новый Core с default general, если отдельного основания для static нет.

## Проверки после внутреннего решения

На этой стадии ни одна не запускалась. Минимальный будущий приёмочный проход:

1. Guard старых vendor bytes/modes по сохранённому inventory только изменяемой closure, materialization выбранных packages и provenance/patch; отдельная проверка разбиения providers предотвращает mixed-version отказ до записи. Не повторять census/hash всех независимых providers.
2. Один bounded SP tasks capture/export, включающий оба submission mode и семь объявленных проектов; его existing batch metrics должны показать contract aggregate вместо N per-input probes (fresh missing set=один aggregate inspect, cached set=ноль). Передать published owner config Quarto1.11.5; не запускать новый общий benchmark.
3. Проверить resulting public student ZIP/HTML и private checks/Workspace seed relationships, неизменные course/work/question identity и transport; использовать уже принятые PL UUID/ZIP/index проверки и existing SP archive/workspace проверки по затронутому consumer пути. Platform ordinary JUnit root здесь не дублируется.
4. Проверить local post-render downloads на том же source и старые Navigation/Presentation/Site/QRC integration. Затем независимый review ограниченного diff и финальный визуальный просмотр перед публикацией. Сервер/публикация/PR/push/commit в данный scope не входят.

Внутренний запрос root: разрешить именно описанный owner/provider migration (включая обоснованный Download3.5.0 и разделение legacy UI pin) либо ограничить немедленную запись Core+PL+tasks static и сохранить Download3.3.1 как явно незакрытую Ninspect зависимость. Source совместимость small package показана; полная интеграция ещё не утверждается.

## Принято root и ограничение исполнения

Root разрешил migration Core9.3.4 + PL354 + Download3.5.0, split `core-ui` на прежний04c189/v9.1.0, static только `tasks`, восемь изменяемых package scopes. Разрешён commit после адресных проверок; push/PR не разрешены. Новые продуктовые API/правки vendor запрещены. Нормативный файл не меняется. Resumed turn `01a1a213-046a-7520-8845-04a221967e93`; оба turn_context от исходного baseline проверены: top+nested Sol6.1/high, service_tier отсутствует.

Команды до smoke: внешний `reports/sp-batch-install.py` использует существующие `inventory/current_expected/matches/staged/atomic_text` SP materializer только для трёх выбранных packages; сперва сравнивает прежние восемь targets, затем stage точных tags/patch, затем запись manifest/lock/vendor. PL patch и lock берутся из Java commit `3128c77b`; scopes SP сохраняются. Оставшиеся providers не обходятся.

Единственный meaningful native smoke (лимит120 секунд всего process tree):

```sh
timeout --signal=TERM --kill-after=5s 120s /opt/quarto/bin/quarto run tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/entrypoints/export-course.ts /home/tolya/JavaCodex/java-release-20261110/system-practice /home/tolya/JavaCodex/java-release-20261110/reports/sp-batch-public --instance pilot --checks-output /home/tolya/JavaCodex/java-release-20261110/reports/sp-batch-private --runtime-registry /home/tolya/JavaCodex/java-release-20261110/platform/runtime-profiles.json --book tasks
```

Команда запускается в SP checkout с `QUARTO_RUN_NO_NETWORK=true DENO_NO_PACKAGE_JSON=1`; log `reports/sp-batch-smoke.log`. Это штатный entrypoint actual SP tasks owner, его пять selected works и семь проектов включают upload и Workspace. Не создаётся alternate fixture. Если capture/export отвергает installed mixed consumers либо не укладывается в120s, фиксируется точная ошибка/достигнутая стадия; повтор только после конкретного согласованного исправления, без размножения fixture. При успехе считываются уже выданные `nativeProjectContextMetrics` и опубликованные descriptors обоих transport; metadata probes отдельно не запускаются. Fullcourse/матрица/OCI/server/визуальная релизная приёмка сюда не входят.

Ruling: принятая операция — поставка существующего кода и owner configuration; TDD продуктовых функций не применяется, suite/fullcourse исключены прямым заданием root. Доказательство — адресный installer guard и один meaningful native consumer smoke. Отдельная независимая рецензия остаётся у root; субагентов здесь не создаю.

Первый installer preflight: прежние8 scopes полностью совпали с old inventory, uncommitted vendor не найден. `staged` остановился до записи: source root `/home/tolya/course-tools`, package Core, `git rev-parse v9.3.4^{commit}` exit128 (локального tag нет). Root запрошен о существующем release staging; native smoke ещё не запускался. Это отсутствие входного release checkout, не дефект Core productcode; vendor правки не выполнялись.

Установка выполнена через существующий staged из отдельных shallow no-checkout release clones `provider-sources/quarto-course` v9.3.4→a478d2cc и `quarto-project-download` v3.5.0→c81df736; PL источник — существующий clone `prairielearn`, tagv9.0.7→1c41f03f. Symlink provider-sources/quarto-course-prairielearn разрешён root; staged получает разрешённый реальный parent/checkout, поскольку safe запрещает symlink path. Успешный old8 preflight переиспользован без повторного обхода по прямому указанию root. New8 targets после записи совпали со staged bytes/modes; PL inventory совпал с Java3128c77b. Другие packages не обходились.

Native smoke actual result: exit1 за0.938s, `CoreContractError: BODY.PRODUCER_INPUT_UNQUALIFIED: Authority unavailable`, `runtime/assignment-authority.ts:411` → `prepareAssignment` assignment-admission.ts:226 → nativeEntry.ts:211. Ошибка возникла ДО render/capture; aggregate и транспортная materialization не достигнуты. `nativeProjectContextMetrics` не получены; upload и Workspace integration остаются pending. Полный log сохранён, дополнительных fixture/inspect запусков не было. Root уведомлён об exact dependency существующего prepared authority.

Адресная owner/provenance проверка PASS: scopes трёх packages сохранены; Site/QRC manifest+lock равны baseline; UI package lock отличается только provider affiliation core→core-ui, bytes/pin/scopes те же; Platform toolchain fields кроме status равны baseline; авторские task/transport/ID/goal файлы не входят в diff. Descriptors новых packages соответствуют exact pins. `git diff --check` PASS. Существующие staged new8 bytes/modes проверки не повторялись.

Installation/provenance commit не объявляет runtime integration пройденной; старые mixed consumers и отсутствие assignment authority остаются ограничением следующего bounded приёмочного прохода. Старый `/home/tolya/course-tools` не изменялся. Root отдельно исправляет Java installer; ожидаю independent review прежде возможного переноса утилиты, текущий SP split совместим с прежней утилитой.

SP installation/provenance commit: `8103049` (`Adopt existing contract batch providers for SP tasks`),132 файлов с materialized vendor closure, owner config, provenance и patch. После commit рабочее дерево clean. Push/PR не выполнялись. Fresh diff --check PASS перед commit; metadata full interval (исходный turn + resumed turn) снова совпадает Sol6.1/high. Smoke остаётся FAIL до capture; успешная интеграция/metrics/визуальная приёмка не заявлены.


## Перенос проверенной утилиты поставки

Root сообщил независимый Clean Java99e311487457b76f5deb5931a9c91e434470edcd (`reports/materializer-package-versions-review.md`). Старые SP `tools/materialize-providers.py` и `tools/test-materialize-providers.py` побайтово совпали с parent99e31148; оба заменены точными bytes99e31148 и снова адресно сопоставлены с commit. Объяснение различия версии provider release и входящих packages перенесено в docs/provider-installation.md, SP дополнение сохранено и уточнено. Core-ui9.1.0 и все provider/package inventories сохранены. Повтор12tests, materialization, inventory и native smoke прямо исключён root; переиспользуются root12PASS1.657s и независимый Clean этого exact diff.

Ограничение native smoke установлено документацией exact Core9.3.4: `git show v9.3.4:docs/native-assignment-ts.md`, строки3–15, прямо указывает, что fixed optional runtime/assignment-authority-location.json (L) отсутствует в обычном published Core, assignment читает внешнюю E/M/R capsule, а внешний launcher обязан отдельно квалифицировать priorP/G/полный Deno command. PriorP находится вне S/K/G/capsule, argv/env и этим API не создаётся. Значит отказ до capture — предусмотренная отсутствующая installed authority dependency, а не основание менять guard либо изготавливать capsule. Штатного preparer в этой поставке не заявлено; root выделяет Core dependency в отдельную техническую задачу. Upload/Workspace metrics и full visual release остаются pending; новых smoke не было.

Собственный допуск полного интервала от baseline01a1a20e-5a0b-7371-bb72-1e5ac5965329 включает третий turn01a1a219-2578-7072-b372-97841717dbb8; все три top+nested Sol6.1/high совпадают. Standard по назначению, первичный service_tier отсутствует.

Отдельный SP tool transfer commit: `2659e8b5dd59ce7ef560d02fdcc955db9eae1912`; только2tools+docs поверх81030492. `git diff --check` PASS перед commit, дерево после commit clean. Независимому обзору передаётся owner migration с явно незакрытой Core authority dependency; внешняя публикация не выполнялась.
