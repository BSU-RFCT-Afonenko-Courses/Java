# Тесты и CI: предлагаемый пересмотр

Предлагаю оставить CI проверкой того, что автор и потребитель действительно
получают от расширения или курса. Exact generated state, число внутренних
обходов и отказ на любую ручную правку не должны определять корректность
публикации. Это предложение, не выполненная чистка; frozen Core correction
и остальные продуктовые файлы не менялись.

**Убрать из обязательного CI и удалить устаревшие тесты.** В Java/SP сейчас
`materialize-providers.py --check` сравнивает каждый установленный vendor file
с inventory SHA/size/mode; Java вызывает это и перед render, и в отдельном
providers job. Проверка происхождения выбранной зависимости полезна при её
обновлении, постоянный запрет локального редактирования — отдельная политика.
В SP пять `build_* / materialize_* --check` сравнивают ZIP/копии с hash manifests;
`test_materials.py` закрепляет байтовую повторяемость и отказ после ручной правки
ZIP. Убрать эти ожидания из course CI, оставив проверку содержания выдаваемого
архива. Отдельно удалить downstream tests старых private carriers и fallback
обвязки: Download `model-receipts.ts` собирает прежний NativeRun и `as never`,
PL `native-batch-checkpoint.test.ts` проверяет fake operation/old-Core fallback.
В Core `static-source-diagrams.ts` читает private state.json ради policy и
inspectCalls; `native-project-marker-isolation.ts` смешивает role/format checks
с counters. Private snapshot/state fixtures и counter assertions убрать из
нормального contract suite.

**Оставить малый публичный contract.** Один actual type graph public entries и
прямых consumers; реальные Core publication callback/lifetime, выбранные
role/path/format и отсутствие private solution в student output; PL ordinary
inspection и фактические question/assessment settings; Download ZIP contents,
README links и сохранение предыдущей выдачи при ошибке; Site конечные URL и
состав публикации. Проверять эти свойства через настоящие public API и
установленный минимальный Quarto проект. Пример уже есть: Down public-runtime
извлекает ZIP, PL native-publication-sources проверяет normal/missing-visible/
missing-hidden. Ordinary closure убрала duplicate Down wrapper, вызывавший
чужой PL fixture; возвращать такой повтор не нужно. Текущий реальный origin
failure после успешной типизации также показывает ценность одного исполнения
публичной композиции. Tests стабильных Native UUID и непотери связей/results
имеют самостоятельный смысл и не являются snapshots форматирования JSON.

**Явные tools/manual audit вне default CI.** Provider materializer и безопасный
отказ перезаписать локальную работу можно сохранить как инструменты автора;
их собственные tests нужны при изменении инструмента. Туда же относятся
полный source/vendor inventory, hash/receipt provenance, счётчики стоимости,
profile switching student→full→student, browser/performance diagnostics и
полная qualification delivery. В Java текущий providers green вообще не
исполняет grader: это прямо признано в qualified-course-checks. Для качества
курса запуск реальных starter/reference/negative solutions и grader важнее
такого green. SP runtime test готовых fish/PowerShell скриптов по files/stdout/
exit codes — полезное наблюдаемое поведение, его сохранять.

**Простой default.** Для курса — один job: один student render в disposable
checkout; проверить доступные ссылки/ZIP и отсутствие закрытых материалов;
один запуск существующих учебных checker/grader cases для затронутых работ.
Если штатный grader требует контейнер, запускать его штатным способом, не
объявлять source preflight заменой. Пока нет готовой адресной команды,
использовать существующий suite без новой системы отбора. Full author export
и проверку переключения профилей вызывать явно, когда меняется этот маршрут,
без обязательных второго и третьего whole-course render на каждый PR.
Для расширения — один typecheck и один небольшой behavioral suite public
entry, с одной installed composition, когда есть внешний Native handoff;
не повторять его в каждом downstream repo. Новая matrix и двойная сборка
не нужны.

Публичность API теста расширения не означает публичность учебного JUnit case.
Открытые/скрытые cases, цели задания, API unscored, баллы, mixed/manual split
не меняются этим предложением. Проверки корректного score/INFRA dispatch,
выбранных файлов и student/privacy сохраняются. Отказ на harmless Native JSON
edit следует снять согласованно с delivery contract: сейчас byte inventory
ещё обязателен в полном PL→Platform маршруте, ordinary patch его не отменил.
В просмотренных Java/SP workflow отдельного редактирования Native info.json
нет: этот запрет живёт в downstream Platform verification/bridge CI и в
описанном Java required release gate. Не путать его с нужным отказом при
изменении grading method, UUID, private answer или actual assessment slots.

## Приложение: точные Source anchors

Все пути относительно `/home/tolya/JavaCodex/java-release-20261110/`.

| Место | Что подтверждает |
| --- | --- |
| `java/.github/workflows/check.yml:28`, `java/CI/render.sh:7` | student/full/student и повтор vendor check; providers job :49 |
| `java/tools/materialize-providers.py:102` | installed provenance и full inventory comparison; локальные правки останавливают запись :136 |
| `system-practice/.github/workflows/ci.yml:24` | vendor checks; generated-material checks :36–41; два render :43–45 |
| `system-practice/tools/build_materials.py:72`, `system-practice/tests/test_materials.py:70` | hash manifests/ручной ZIP edit и соответствующий active unittest |
| `core-native-bridge/tools/check-canonical.sh:78`, `:87`; `core-native-bridge/tools/check-core-native-ts.sh:5` | фактический вызов private-state/counter suites в Core CI |
| `core-native-bridge/tests/static-source-diagrams.ts:11`, `tests/native-project-marker-isolation.ts:230` | state.json и exact counters вместе с behavioral checks |
| `prairielearn/.github/workflows/ci.yml:143`, `prairielearn/tests/native-batch-checkpoint.test.ts:13` | active fake-operation/fallback fixture; tools/check-contracts.ts:16 — лексический full-source ban |
| `download/tools/check.sh:10`, `download/tests/model-receipts.ts:11` | active старый NativeRun injection test; tests/public-runtime.ts:74 — полезный public ZIP contract |
| `prairielearn/tests/native-publication-sources.ts:19`, `prairielearn/tools/check-java.sh:9` | real ordinary integration и настоящее Gradle reference PASS/starter FAIL |
| `system-practice/tests/test_work_environment_runtime.py:38` | исполнение опубликованных скриптов с наблюдаемыми результатами |
| `platform/.github/workflows/check.yml:52`, `:64`; `platform/tests/test_delivery_verification.py:68`, `:74`; `platform/bridge/delivery.mjs:19`, `platform/bridge/bindings.mjs:12` | active downstream Native JSON semantics и mandatory files/hash/read loop; README tamper negative |
| `java/docs/qualified-course-checks.md:21` | direct Platform build/inventory/verify release gate; это отдельная от обычного CI обязанность нынешнего контракта |

Переиспользованы pattern-pass-{core,export,performance-ci,summary}-20261104,
hash-mechanism-{producer,consumer}-review и paired-ci-publication-test-align-review.
Это адресное Source чтение, не новый census/R0–R6. Никаких исполнений,
compiler/tests/render/inspect/hash/install/network/DB или новых агентов.
