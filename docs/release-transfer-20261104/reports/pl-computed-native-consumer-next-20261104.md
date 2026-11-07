# PrairieLearn: ближайшая граница обычного computed Native body

Последующее отдельно разрешённое исполнение завершилось scoped consumer PASS; см. [фактический отчёт](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/pl-computed-consumer-integration-20261104.md). Ниже сохранены первоначальный Source-only вывод и подготовительный план, без подмены их исторического статуса.

## Допуск и прочитанная композиция

Назначение `gpt-6.1-sol/high`, Standard. Собственный chat `01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1`, agent `/root/perf_event_types`, parent `01a1a142-7adc-7371-9765-4871d807dfcd`. Primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-41-59-01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1.jsonl`, session_meta строка1; исходный turn `01a1a514-1688-7662-80fc-58bf7a272ae0`, текущая Source-only партия `01a1a546-7b7a-71a0-aead-76b02ca7f305`. Полный интервал turn_context строки8/171/270/405, верхние и вложенные пары `gpt-6.1-sol/high` согласованы, вход PASS. Service tier отдельно не записан, глобальные настройки не менялись.

Прочитаны applicable PL AGENTS, `spec/index.md` и ранее общее русское языковое правило Core. Exact Source refs: Core `5a28314fc8b704ef7171f33063789d7cc8ec8e79` в `core-native-bridge`, PL `f3a09cd060609b57e516779588d635abedb1ca21` в `prairielearn`. Обе worktree при чтении чистые. Не подменяются installed course refs или ещё рассматриваемая A1 typing worktree.

## Основной вывод

**Для обычных computed Table/Image/inline в условии при неизменённых выбранных grading files дополнительный blanket PL product gate не найден.** Core 5a уже произвёл и материализовал такой public body. PL f3a имеет потребляющий путь для этих узлов и PNG bytes. Минимальный следующий шаг — адресный consumer integration control, а не снятие rawSource/privacy/frozen-env guards.

Реальная отдельная преграда возникает, если cell создаёт или переписывает выбранный starter/test/reference/contract-fixture source: current A1 file bytes тогда не совпадают с initial sourceRows, и PL явно отказывает. Это другая семантика; её нельзя выдавать за обязательную часть обычного computed body или разрешать попутно.

## Что уже реально прошло

Источник — новый `reports/core-native-computed-integration-20261104.md` и его actual artifact/result.json, не повтор R3. Exact Core5a, Download944, Performance5365; единственный actual запуск exit0/8.536 s, Quarto1.11.5/knitr1.51. Один bank QMD с R: Table42, PNG15196bytes, inline42. Путь: authentic prepared assignment capture → current Native operation → один actual A1 batch → assignmentDelivery → student materializeProject → Download ZIP. Student/full планы, condition/README/ZIP использовали ту же A1 batch.

Точный [исторический отчёт](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-computed-integration-20261104.md) сохраняет исходные результаты и ограничения этого упоминания.

Сохранённый actual `publicPackage` содержит Table1/Image1/inline42; RawBlock/RawInline/Note в этом package отсутствуют. README содержит42 и `resources/index_files/figure-html/computed-1.png`; PNG совпал с Core inventory bytes, ZIP entries побайтно совпали с inventory и прошли CRC. Solution/reference sentinels и reference path отсутствуют в student condition/package/HTML/README/ZIP. Это проверка declared roles/private block этого fixture, не taint analysis произвольного R. Selected starter при этом не генерировался cell.

PL consumer не вызывался этим probe. Поэтому PL HTML/clientFiles/UUID/grading после computed capture новым evidence **ещё не приняты**. Old frozen outputs, cross-profile cache reuse и data freshness новым probe не проверялись; сохранённые R3 counterexamples не отменены, не повторены и не объявлены новым отказом PL.

## Фактический путь в PL f3a

1. `application/native-entry.ts:178+ nativeEntry`: public Core capture gate → `lease.captureInputs` с ordinary `render --output-dir` → Core prepared/current operation. Отдельного PL engine whitelist или принудительного Native freeze flag в этом пути нет. Выбор assessment/frontmatter и expected source coverage остаётся явным.
2. `native-course.ts:137+ withNativeAssignments`: authenticated adapter.validateCourse → Core assignmentProvenance → nativeProjectWitness → одна withNativeProjectPlans batch → Core assignmentDelivery. Current Core `withProjectContracts` выбирается перед старым standalone fallback; selected public/private body и condition не восстанавливаются из сырых QMD.
3. `native-materialize.ts:158+ materializeNativeQuestion`: Core materializeProject получает exact condition и genuine student/full plans. `projectNativeChecks` вызывается только при fact.check; README и condition resources проходят обычную Core materialization. Final student inventory передаётся Download, checkpoint/currentness выполняется прежним путём.
4. `export.ts:247–270,349–475 renderPublicBody`: Table semantic attrs и Image/Link обрабатываются; публичные resource bytes требуют owner/visibility/data/target и соответствующий byte SHA. Только RawBlock/RawInline/Note и отдельные неподдержанные Cite формы отказывают — это не отказ Table/Image. Ресурсы кладутся в clientFiles, затем ordinary Pandoc JSON→HTML; actual saved computed package укладывается в прочитанный node set.

Core resource corridor также не ограничен initial source rows: `runtime/operation.ts:3077–3094` включает authentic `complete.resources` в knownResourceInputs, а `artifacts/project.ts:583–603` читает текущий observed Native resource, если файл не принадлежит captured project copy. Поэтому generated PNG уже прошёл existing frozen-resource admission. Старое слово «frozen» в diagnostic не доказывает запрет всех новых фигур.

## Где именно rawSource ещё блокирует расширенный случай

`native-check-projection.ts:24–68 projectNativeChecks` сначала выбирает compiler inputs через `native-selection.ts:16+ selectNativeCompilerInputs`, затем передаёт каждый selected file в `prepareNativeRawSourceSelection` из `native-check-inventory.ts:36+`.

Последний проверяет explicit safe `rawSource`, size/hash bytes и:

```ts
rows.get(file.rawSource) !== file.sha256
```

где rows — initial Core assignmentProvenance.sourceRows (`Core runtime/assignment-provenance.ts:177`). Новый source отсутствует в rows; перезаписанный source имеет другой digest. В обоих случаях отказ `PL.NATIVE_ORIGIN_UNQUALIFIED: missing/foreign/changed same-S raw bytes`. `ArtifactFile.rawSource` сам по себе является data-проекцией пути exact copy, а не capability чтения или свидетельством нового trusted grading source.

Важно: `native-selection.ts:95–106` выбирает student compiler sources только по authored source-profile и role `starter`; trusted tests — по testset/component roles, references — по author groups. README и role `condition-resource` PNG в compiler rawSource origin selection не попадают. Поэтому numeric table/image/inline не требуют ослабления этой строки. Manual без project-check также не проходит автоматическую source selection ветку; canonical answer branch обрабатывается отдельно.

## Что означает frozen и что сохраняем

`native-auxiliary.ts:12–39 captureNativeAuxiliary` замораживает environment C child composition и сравнивает его до/после auxiliary launch вместе с live currentness. `export.ts:98–138` использует этот env для Pandoc. Это **не** Quarto `execute.freeze` policy и не PL проверка свежести `_freeze` output. Нормальная engine cell сама по себе не является основанием менять этот guard. Новая cache-name/proof/hash схема не нужна.

Privacy/UUID/grading остаются прежними: Core actual role/physical identity запрещают private paths; PL export не разрешает solution/answer/correct/grading-notes markers и непубличные resource bytes; UUIDv5 берётся из `q.key` (`export.ts:142+,1040`), а не из computed body digest. Native adapter сохраняет canonical-answer method parity и различие manual/project-check (`native-adapter.ts:285–305`), compiler/test/reference/scenario contracts не меняются. Не предлагается очищать hash поля, подменять initial row SHA актуальным или принимать generated grading input без отдельного решения.

## Один ограниченный следующий diff

**Только existing `prairielearn/tests/native-a1-builder-integration.ts` плюс при необходимости private R fixture input.** Добавить одну ordinary R cell по уже прошедшему Core fixture: computed Table42, PNG и inline42; выбранные Java starter/test/reference files остаются теми же bytes/roles. Consumer assertions должны увидеть Table/inline в PL HTML, PNG в clientFiles и README/ZIP closure, отсутствие private sentinels, прежний course/question UUID и grading descriptor/scenario смысл.

Existing test уже вызывает реальные `materializeNativeQuestion` и `renderPublicBody` (`:225–253`) и проверяет raw origins/student privacy (`:265+`). Его старый `resources:[]` требует genuine public resource rows при PNG: нельзя убрать resource guard ради теста. Его explicit fixture fact/standalone A1 section **не** объявляется qualified assignment/P/actual C CLI; если для следующей приёмки нужны именно эти свойства, следует взять authentic prepared assignment seam из нового Core probe и действующего nativeEntry, а не fabricated proof/fact. Scope проверки требуется записать явно до запуска.

Это consumer evidence diff; production PL/Core decoder/producer/consumer rewrite не требуется для заявленного ordinary body. Если он выявит реальный product отказ, зафиксировать первую точную границу до отдельного исправления. Generated/new/overwritten grading source, HTML widgets, cross-profile frozen reuse, весь курс и новая provenance schema в эту партию не входят.

## Действия и пределы этой партии

Только чтение существующих Source/отчётов и разбор уже сохранённого artifact JSON для проверки node set. Новых Source edits, compiler/tests/Native render/inspect/R/Pandoc/network/install/hash runs не было; production query/UUID/оценки/pins/commit/push/mainstate не менялись. Root после этого Source-only вывода отдельно назначил подготовку локального test fixture diff **без actual Native запуска**; такая подготовка и её будущая проверка не являются результатом этого отчёта.

Полный primary интервал повторно проверен от прежнего origin: turn_context строки 8/171/270/405, все верхние/вложенные пары gpt-6.1-sol/high согласованы, итоговый допуск PASS.

## Отдельно разрешённая подготовка fixture, без исполнения

После Source-only отчёта root отдельно назначил локальную дельту existing `prairielearn/tests/native-a1-builder-integration.ts`. Подготовлен только этот файл; private R helper не понадобился. Production PL/Core и CI pins не менялись. Это **подготовленная**, ещё не исполненная consumer проверка: compiler/Native/Pandoc/R не запускались. Bundled formatter разобрал и отформатировал один файл; `git diff --check` PASS. Ни compiler PASS, ни runtime PASS новому fixture не приписывается.

Добавлена та же ordinary R cell `value <- 21L * 2L`, печать Markdown Table, PNG plot и inline42 с явным public anchor. Native fixture имеет `course-core-source-policy: static`, как accepted Core computed probe; на current Core это hint, без старого engine ban. Java starter/reference/helper/API/JUnit bytes оставлены буквально прежними. Solution sentinel добавлен только в private `.solution` block. Extra CSV/cache/profile/engine matrix и генерируемые grading files отсутствуют.

PNG для PL consumer получен не из fabricated assignment DTO: ordinary `operation.resourceInventory` читает подлинный observed Native PNG; его byte/target/SHA data проецируются в вход `renderPublicBody`. Existing `captureNativeAuxiliary` используется для PL Pandoc child вместе с настоящим operation currentness; это не новая env/proof схема. Такой тест остаётся **ordinary Native + отдельный genuine A1 + PL consumer + Download**, не authenticated assignment/P/full C CLI. Existing explicit fixture policy fact этим не превращается в producer authority.

Assertions проверяют:

- condition Table1/Image1/42 и отсутствие private solution;
- PL HTML table/inline42, настоящий clientFiles PNG и побайтное равенство его и README inventory PNG actual `resourceInventory` bytes;
- стабильный literal UUID `3bef88ed-8e1a-5e4b-8e8a-95224804f9c1` для `native/exr-p` и соответствующий namespaced computed anchor; literal независимо подготовлен стандартным UUIDv5 DNS алгоритмом, body hash не используется;
- прежний descriptor `implementation/java25-junit-v1`, Java25, weighted scoring, неизменный Main.java; starter/reference сценарии `reference:lawful,starter`;
- отсутствие private markers и trusted test/reference filenames в student inventory; README table/inline/resource URL;
- прежние clone/expired controls. Старый stale mutant в shared API file заменён selected `bank.qmd`: whole-owner отказ прежнего теста противоречил accepted 5a, selected raw QMD отказ сохраняет действующую гарантию. Grader Java не мутируется.

Typed actual public Core contract теперь сохраняется после runtime module import; fixtures sourceRows/retained handles имеют точные типы, observation data — unknown, callback типы выводятся из реальных APIs. Прежние test any удалены без новых casts/suppressions. Actual package module identity по-прежнему берётся из переданного Core Source и собственной PL worktree.

### Точный будущий локальный запуск — пока не разрешён к исполнению

При подготовке root уже объединил Core в `/home/tolya/JavaCodex/java-release-20261110/core-native-bridge`, фактический HEAD `0cf20ccc0a4853b704544c7252fcaf3ccb84a518` (проверен read-only worktree listing). PL base `f3a09cd060609b57e516779588d635abedb1ca21` плюс этот единственный test diff. Download local Source `944ca76dbdef5bb4842116950621f82449b631b3`; тест копирует `_extensions/course-core` указанного Core и Download extension в synthetic temporary sibling для совпадения module identity. Это локальная candidate композиция, **не опубликованный provider**.

Карта `/tmp/core-native-bridge-imports.json` уже указывает `course-core/` на этот реальный bridge path, `pl-native/` на текущий PL, stdlib — на existing actual cached JSR. Планируемая команда из cwd `/home/tolya/JavaCodex/java-release-20261110/prairielearn`:

```sh
timeout 120s env DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache DENO_NO_PACKAGE_JSON=1 XDG_CACHE_HOME=/home/tolya/JavaCodex/.cache QUARTO=/opt/quarto/bin/quarto QUARTO_RUN_NO_NETWORK=true /opt/quarto/bin/tools/x86_64/deno run --no-check --deny-import --no-config --no-lock --import-map=/tmp/core-native-bridge-imports.json --allow-read --allow-write --allow-env --allow-run tests/native-a1-builder-integration.ts /home/tolya/JavaCodex/java-release-20261110/core-native-bridge /home/tolya/JavaCodex/java-release-20261110/download/_extensions/project-download
```

Cap120 s/retry0 — **предложение root**, не разрешение автора отчёта и не запуск. Первый отказ фиксируется без повторного render/engine retry. Если root назначит compiler, его отдельный affected entry — тот же test с bundled `check --deny-import --no-config --no-lock --import-map=/tmp/core-native-bridge-imports.json`, actual DENO_DIR; сейчас check не выполнен. `QUARTO_RUN_NO_NETWORK` не называется sandbox для R; новых сетевых данных fixture не требует.

Один предметный вопрос: **доходят ли ordinary computed body и observed PNG через существующий PL consumer/frozen auxiliary boundary при прежних выбранных grading bytes/UUID/scenarios?** Не проверяем generation grading files, JVM execution/баллы/DB, Native freeze freshness, полный publish/instance или assignment qualification.

Source-ожидание explicit semantic calls: один global Native render/R cell; одна initial owner inspect; standalone A1 owner+project inspect2 и fullWalk1; materialize student(initial+final)/full с разными submission bindings — до6 Core Pandoc calls; PL body JSON→HTML — ещё1 Pandoc. Это 11 explicit Quarto commands при данном Source path, **не полный process census**: version queries/hooks/engine/internal processes отдельно, R process counts/timing не обещаются. Existing final `checks` сохраняет operation/context/plan metrics и computed rows; counters не превращены в обязательный no-optimization gate.

Тест сохраняет `student.zip`, `inventory/**`, `clientFiles/**`, condition.html и checks JSON в собственном temporary artifactOut. Побайтное ZIP/CRC сравнение можно выполнить existing Python stdlib способом, как в accepted Core probe, в рамках того же отдельно согласованного root cap; новый ZIP reader/framework или producer proof не добавлен. Пока ZIP исполнение/CRC PASS отсутствуют.

### Hosted CI и paired release

PL `.github/workflows/ci.yml:43,113` закрепляет Core `830381697beea4106655965e10605cc13987199b` в `.ci-deps/core`; exact-SHA assertions находятся строками49/119. По read-only `git show` этого object, `runtime/operation.ts:939–971` при `source-policy:static` требует markdown engines и разрешённые codeCells, поэтому новый fixture с R/knitr **Source-доказанно несовместим** с этим старым static admission. Локальный результат на0cf нельзя объявлять CI результатом на830 или published composition.

Штатные runner seams: `.github/workflows/ci.yml` вызывает `quarto run tests/check.ts .ci-deps/core`, native answers/raw-origin/reference/publication tests; Java job готовит карту `quarto run tools/native-core-import-map.ts .ci-deps/core .ci-deps/pl-native-imports.json`. `tests/native-core-typecheck.ts` использует тот же реальный Core argument и штатную карту. Сам `native-a1-builder-integration.ts` в прочитанном workflow непосредственно не включён; текущая подготовка не меняет coverage/runner молча.

Включение нового computed case в hosted CI требует явно согласованной paired dependency release: сначала опубликованный/доступный reviewed Core с принятым computed admission (и точный Core ref), затем согласованный PL workflow pin/runner и при необходимости explicit Download fixture dependency. Нельзя skip-нуть case на830, ослабить static case ради зелёного или подменить published provenance локальной копией. Pins/workflow этой партией не менялись; root выбирает pair и разрешает исполнение отдельно.

После fixture подготовки полный исходный primary интервал повторно проверен: turn_context строки 8/171/270/405, верхние и вложенные пары gpt-6.1-sol/high согласованы, итоговый допуск PASS. Actual Native/compiler разрешения не поступало, исполнения не было.
