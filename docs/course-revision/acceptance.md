# M05 — техническая проверка итогового корпуса

Статус: **DONE / FROZEN для независимого технического review**. Свежие штатные `bash CI/render.sh` и `bash CI/check-projects.sh` прошли полностью. Это техническая передача; педагогическая приёмка **U08 PENDING**, M05 её не предоставляет. Новых конкретных пользовательских правок нет; обсуждение exporter/check после checkpoint не меняет принятый scope.

Исполнитель Task14 `/root/m00`, requested **gpt-6.1-sol / medium**, effective model/effort **not_reported**. [Журнал назначений](model-routing.jsonl) ведёт root. Final source — принятый полный M04 после independent Approved / 0 remaining: **45b62f4d71c83956e93a1f6f16fc7d40a7dbb3dc**. Remote PR13 baseline **a0d1bdb9d8343809560e3ce7f9a2141d4473ee3f**. Prepared ab82 → metadata-only overlays d257 → final M04 guide delta включён в tested source.

## Что именно проверено

Root создал новый clean detached `--no-hardlinks` clone `/tmp/java-course-revision-checks/M05/source` exact accepted HEAD, origin `https://github.com/BSU-RFCT-Afonenko-Courses/Java.git`. M05 Git не менял. [Source identity](/tmp/java-course-revision-checks/M05/final/source-identity.json) содержит actual tree и before inventory; абсолютная evidence root: `/tmp/java-course-revision-checks/M05/final`.

Все **1446 tracked files** clone остались побайтно и по режимам неизменны до/после stock commands. **1360 teaching/tool/vendor/config/resources files** совпали с shared `/tmp/java-course-pr13-20261013` до/после. Из текущего shared comparison исключены `docs/course-revision/**`: это меняющиеся audit drafts, а committed версии clone сохранены и также проверены среди1446. `.superpowers`, старые ignored outputs, каталоги и receipts не копировались. Изменение audit/journal не выдаётся за teaching delta. Clone и exact Platform Git чистые после проверки. Доказательства: `all-tracked-{before,after}.json`, `tested-source-{before,after}.json`, `source-identity-after.json`.

Все generated outputs принадлежат новому M05 clone/evidence; существующие старые evidence read-only. Разрешён только dependency cache reuse: Deno module/npm cache и Maven dependency/distribution cache, с recorded source/destination; без source output, receipt, catalogue, compiled student solution или transplant. Переменные JAVA_HOME/PATH/CUE/PL_PLATFORM_ROOT/CI_CHECK_OUTPUT/TASK_CHECK_OUTPUT/CI_LOG_DIR/XDG_CACHE_HOME/TMPDIR scoped process в `scoped-env.json`; HOME/CODEX_HOME/global settings не менялись.

## Свежие команды и результаты

| Команда | Actual результат | Полные доказательства в final evidence |
|---|---|---|
| `bash CI/render.sh` без profile arguments | exit **0**, 15:31:04–15:55:02 UTC 14 октября, 1437.404 s; default **student → full → student**, каждый `--fail-if-warnings` | `render-cycle-result.json`, `logs/render-cycle.log`, `render-logs/render-{1-student,2-full,3-student}.log`, `logs/child-commands.jsonl` |
| `bash CI/check-projects.sh` | exit **0**, 16:04:53–16:22:49 UTC, 1075.872 s; full declared inventory/native export/**host + container** | `projects-result.json`, `logs/projects.{stdout,stderr}.log`, `checks/{declared-checks,inventory,native-checks,delivery-host,delivery-container}.json` |
| `python3 .../check-publication.py student1`, `full`, `student3` | каждый exit **0** | три `*-publication-check.json`, полные `*-href-occurrences.json`, semantic/download/code witnesses, raw logs |
| `python3 .../check-gradeapp-public.py` | exit **0** orchestration; ожидаемые negative child exits сохранены | `gradeapp-public/result.json`, восемь raw command logs |
| `python3 .../measure-volume.py` | exit **0**, final exact source hashes | `final-source-volume.json` |
| `python3 .../check-final-receipts-and-source.py` | exit **0** | `fresh-delivery-proof.json`, `source-identity-after.json` |
| strict QRC-only raw byte check | exit **1**: 90 equal, 8 точных residuals; не скрыт | `qrc-attribute-order-only-proof.json`, `logs/raw-qrc-attribute-order-diffs/*` |
| exact residual pair check | exit **0**, все98 deltas полностью классифицированы | `exact-attribute-order-reconciliation.json`, `logs/raw-after-QRC-only-diffs/*` |

Docker socket потребовал разрешённого escalated stock CI исполнения; это не отсутствующая инфраструктура и не waiver backend. Никаких ready-only/question/scenario subsets или broad reruns после green. Версии preparation команды сохранены отдельно; новое исполнение runner подтверждает actual backend toolchains.

## Среда и delivery

Quarto **1.11.5**, CUE **0.17.1** `/home/tolya/course-tools/local-tools/cue/cue`, Python **3.14.7**, jsonschema **4.26.0**. Host actual `/usr/lib/jvm/java-25-openjdk/bin/{java,javac}` **OpenJDK25.0.4.1**; workflow задаёт Temurin25, local distribution не объявляется Temurin. Container actual OpenJDK **25.0.4+7-1-24.04-Ubuntu**. JDK27 с `--release25` не подменяет actual25. Exact clean Platform **v2.0.0 / b971a61a2386fbf4ae7fcfc9394d307359bc7889**; Java junit/mutation registry соответствует неизменённому `CI/toolchain.json`.

Immutable image actual both receipts: `ghcr.io/afonenko-course-tools/java25-grader@sha256:f791e5590e6102961669e838cb2476f2dde91d888b0fb5a5292bc226cb2f127d`; dependency identity **3614c660a9010817beec415b909529d126b3403213b27996e27386bedbbb6c3a**. Host containment `host-diagnostic`, container `official-landlock-sbuser`. Нет substitutions/latest/installations.

Свежий export: **17 declared/delivered IDs**, **14 required positions 6/4/4**, **17 candidates 7/5/5**, по одной random группе из двух alternatives на work, actual `numberChoose:1`, persisted assignment не заменён выбором студента. Три fresh alt IDs актуальны и в guide. Export completion требует score1 для каждой required позиции; partial/optional не заменяет её. Это проверка delivered policy/структуры и принятых платформенных semantics, не live student assignment эксперимент.

Каждый backend: **success / complete / exit0**, **74 parent scenarios =17 reference +17 starter +40 contract**, все expectations matched, unavailable `[]`; infrastructure complete, executed>0, skipped/aborted/containerFailures0. References score1; TODO starters и contract mutants дают ожидаемые behavior failures. Nested public checks отдельно, не добавляются к74. Source/test/runtime/scenario IDs и hashes совпадают между backend; native provenance files rehashed. Delivery **87a441878889a23596f776b975766ebcb635a82e9eeeba04137548f7f900fe0c**, sourceSnapshot **78bc0c0cf3684763444f8c152bf81d2df58e39fe5394c43bcac00b30294dea1a**, inventory **41ca1ac25bf2ba67d1890c5007552943a38cd1a6a663add92e4aa70cf151717d**, scenarioInventory **c129ad1fa9d36c9efcaf633726860ffc9b0a2d366965aee1cb8efb6aba417360**. Manual gradeapp остаётся вне PL delivery.

## Ссылки, публикация и profile return

Все три fresh root native QRC: **1300 resolved /0 deferred /1380 targets /121 native pages**. Actual published HTML **145 =121 authored native +24 Reveal support**; counts/exclusions не смешаны. Каждый state проверил **11879 local href/src**, broken file/fragment **0**. У24 page-root references точно проверен first-native-Header landing через catalogue, без общего пропуска fragments.

Approved current source graph **1363 =1345 semantic refs +18 starter-download roles** сопоставлен с actual rendered anchor/target в каждом состоянии; навигационные ссылки не подменяют semantic witnesses. M03 native-prep overlay связывает финальный BODY layout delta/current QRC; final45 source hashes unchanged в этой части. Все **365 lecture fences** exact actual HTML visible code matched по source/file multisets (только display CRLF/boundary newline), lesson.css и оба SVG exact. Author lecture practice/tasks links отсутствуют в accepted source scan включая index/notes/includes/raw candidates; sparse handbook6 Cite occurrences объясняются accepted M03 map, полный consumer map161 не заменён Cite-only124 scan. Old revised semantic IDs0, missing revised categories0; aliases/migrations/redirect machinery не добавлены.

В каждом state2138files,18 actual downloads. Все18 ZIPs exact declared student public paths/hashes, никакой reference/tests/fixtures/generated/private code; ZIP bytes одинаковы student/full/student. Вне архивов нет unintended `.java/.class/.jar/.qmd` private assets. Current source не имеет authored solution/answer-spec/grading-notes blocks: искусственный full-only privacy probe не выдуман. Full subtitle подтверждён как преподавательский, оба student — студенческие; manual bank mode сохранён.

**Raw byte restoration false**, это ограничение не скрыто:2040files equal,98HTML byte-different. Полные raw deltas сохранены. **90 файлов** равны после перестановки только contiguous `data-qrc-ref/style/custom` triplets в тех же существующих anchor tags. **8 файлов** дополнительно требуют только перестановки adjacent точных tokens `target="manual"` / `data-statement-visibility="open"` в тех же exercise Div. Полные normalized UTF-8 bytes равны после только этих двух фактически наблюдённых reorderings; прочие attrs/values/IDs/text/whitespace/comments не нормализуются. Strict QRC-only FAIL остаётся; broader parsed-event proof — вспомогательный, не основное доказательство. Никакой source/vendor правки или rerender. Эта сериализационная разница не заявляется byte reproducibility.

## Приложение и carried executable evidence

Actual final gradeapp ZIP **17 public entries**, SHA **c8659277dd1f325f8952b10d04d3c3bf7247a4558a83e11330f9b80fc98e487c**. Fresh unpack в directory with spaces, foreign empty cwd; helper/mvnw mode0644, без chmod. Authored `.mvn` отсутствует; helper создаёт локальные exact properties, Wrapper **3.3.4**, Maven **3.9.16**, JDK25, class majors69. Fresh compile/package `-DskipTests` exit0; TODO `test package` exit1 ожидаемо:4 public tests,1failure/2errors, UOE, не completed-starter PASS. Repeat восстанавливает испорченные generated properties; invalid Maven goal with spaces передан одним arg/exit1; missing public config/unzip/checksum exit2 до Maven.17 authored public bytes неизменны, foreign cwd пустой. Windows **supplied/static/unexecuted**.

Completed reference/public4/private13, две допустимые Reports реализации, JVM/JAR CLI0/2/3/4 и запуск в двух clean foreign dirs — **carried independently executed accepted M02/M04**, не fresh M05 completion.25 actual gradeapp source hashes/visible condition и7 ключевых frozen artifacts rehashed (`gradeapp-carried-{source-binding,evidence-integrity}.json`). Контракт Student(String...), unique strip,score0..100, natural ID order, inclusive threshold, input/threshold→stdout/status unchanged; hidden implementation requirement не вводится. Public TODO реализация не заполнялась.

Полный M02 carry: **100 current source hashes**, **365 final fences /310 execution-linked /315 stage associations /55 documentary**, **249 unique contexts /251 authentic cases /491 recorded processes**. **2412 original +154 fix1 immutable files**, включая fix1 **18groups/18cases/33processes**, rehashed и сохранены. Initial231 raw association dedup не является cumulative case count. Exact final source/context/oracle/tool/raw lineage, real JDK25 compiled/runtime negatives, inference/super bounds, sum11/12, mutable-copy states, CF4.3.1 analyzer отдельно от ordinary javac/runtime, javap client checkcast/getter bridge, C++20 GCC16.2.1, Python3.14.7/Pyright1.1.412 подтверждены prior execution. C#13 prior Roslyn/SDK11RC; C#11/.NET7 generic math и Zig0.15.2 documentary. Диагностические anyMatch/mutable-key traces не universal guarantees. Historical lost-raw retries/incidents сохранены; accepted recoveries доказаны. Rehash не называется новым исполнением. Early M02 subset не waiver полного Task11.

## Пять проектов, scope, бюджет и gaps

Theory:23 главы последовательного объяснения Java25, типы/объекты/generics/streams/resources/files. Handbook:25 статей точных операций/API включая необходимые locale/output/copy/List/Map/files. Practice:13 вводных маршрутов, подготовка отдельно от полного решения. Tasks:условия/подготовка/критерии и personal explanation, две допустимые стратегии, L1–L3 automation preserved,18public starter roles. Lectures:11 ordered Java lectures, новый index, локально полные examples/results, erasure illustrations и sparse approved handbook links. Это описание accepted author deltas; самостоятельность каждого маршрута и качество объяснений проверяет U08.

Final diff PR13 ownership сопоставлен с accepted U00–U07/M01–M04 exact paths; vendor/extensions/CI/runtime/pin drift0, tracked generated inputs0. Guide-only final3IDs/manual paragraph учтён.12 excluded essay/coursework files возвращаются побайтно в PR13 после reversal только approved M03 consumer edits; содержательная переработка отсутствует. Preview invocation/accidental solution/global settings/live sites/grading не вводились. Полные final diff/stat и prep ownership preservation evidence сохранены.

Final U00 exact lexical algorithm (blocks, excludes notes/code/raw/math/refs; includes optional Str/inlineCode): theory23 **48882** vs43615, **+5267/+12.0761%**, cap56699; lectures11 **19678** vs15835, **+3843/+24.269%**, cap20585. Plan **880 ≤936** (baseline720); main labs **1935**, intro **300 отдельно**; L6=270,L7/L8=180,L10=225. New estimates не измеренные student minutes. U07 compressed agent source/reveal rehearsal (3/5/11) с tooling/capacity limitations не80минутный classroom и не реальный student pilot.

Gxx/EFxx/Pxx closure inventory связан с author packets в `preparation/packet-and-gap-checklist.json`: U00 G01/G05/G12/G13/G24,EF01/EF14,P01/P16; U01 G04/G05/G06/G08/G09/G12,EF08/09/11/12,P01–03; U02 G02/G05/G12/G20,EF04,P04; U03 G05/G12/G13/G19,EF03–08/11/12/14,P05/06/16; U04 G01/03/07/11/12/13/14/15/18/21,EF04,P07/11/12/13; U05 G01/G21/G24,EF12/13/14,P14/15; U06 G01–G21; U07 EF01–EF15. Это exact packet mentions/accepted author closure claims, не самостоятельный semantic PASS M05. Technical deferred render/execution/link/delivery/publication concerns закрыты fresh+immutable carry выше; полноту покрытия всех G/EF/P и pedagogy решает U08.

## Матрица передачи U08

| ID | Технический итог M05 | Оставшаяся независимая роль |
|---|---|---|
| A01 | exact corpus/map/counts собраны | U08 проверяет общий самостоятельный маршрут всех5 проектов |
| A02 | literal365 code/result visibility подтверждена | U08 сплошной explanation/replacement review |
| A03 | carried actual inference/super context/runtime negatives | U08 порядок первого введения Comparable/Book/Person |
| A04 | final lexical caps и880≤936; вид rehearsal указан | U08 storytelling budget; real student pilot отсутствует |
| A05 | no authored lecture practice/tasks, accepted sparse handbook map | U08 нужность каждой handbook link |
| A06 | fresh global QRC/href/fragment/source witnesses PASS | U08 смысловая пригодность landing |
| A07 | accepted handbook exact files/G claims preserved | U08 полнота точных operations |
| A08 | native BODY correction/condition hashes preserved | U08 preparation не выдаёт решение/нет hidden knowledge |
| A09 | carried2strategies/private13+public4; criteria preservation | U08 freedom/personal explanation |
| A10 | labs1935+intro300 и explicit L6/L10, accepted replacements | U08 реалистичность workload |
| A11 | complete unchanged M02 carry +fresh stock actual25 backend | technical PASS; documentary classifications explicit |
| A12 | real javap carry +two exact rendered SVGs | technical PASS; U08 explanation consistency |
| A13 | actual25/no preview +unchanged CLI/gradeapp carry/public helper | technical PASS; Windows unexecuted |
| A14 | fresh17/14/6-4-4/random policy/BOTH17×74 | technical PASS; no live deployment claim |
| A15 | fresh3states/18ZIP/private boundary PASS | rawbyte non-repro limited to two exact attr reorderings |
| A16 | exact ownership/vendor/pins/source bytes+modes PASS | no new semantic/global/publication mutations |
| A17 | requested/effective distinction and root journal linked | root dispatches independent authorless Astra/ultra U08 |

Controller adopted-plan ruling допускает disjoint U01/U02 parallel author wave поверх generic single-implementer guidance; цена ошибки — cross-example reconciliation до приёмки. Requested profiles не выдаются за observed runtime settings. U08 не автор проверяемого текста и ещё не выполнен. Последующие executable/reference changes возвращаются владельцу и требуют только affected checks; publication changes требуют полного render cycle. Ничего не опубликовано/pushed и действующие оценки не менялись.

Evidence seal: `/tmp/java-course-revision-checks/M05/freeze-seal.json`; full command/read-only scripts и manifests сохранены для independent technical review. Никаких оставшихся технических blockers; педагогический gate остаётся PENDING.
