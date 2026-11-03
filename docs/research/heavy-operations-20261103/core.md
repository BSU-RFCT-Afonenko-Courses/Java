# Тяжёлые операции: Core, Presentation, Navigation, QRC и Performance

Source-аудит от 3 ноября 2026 года. Никаких build/tests/benchmarks/full-tree hashing/native inspect не запускалось. Продуктовые деревья не изменялись; все новые материалы находятся здесь, адресные public copies — в `sources/`.

## Scope и refs

| Область | Точный ref |
| --- | --- |
| Java installed | `dd5b76a0168133f0760ece6cb21f4bdf4f6203bb` |
| Core owner source/pin/current main | `a478d2cc0dfb19e0bebd99108815ac8ef1fe85a8`; Core9.3.4, Presentation/Navigation9.3.2 |
| QRC owner source/pin/current main | `798f8c0d9f87fd5dcfe9ee85b79df57e647d2f3c`;3.2.0 |
| SP PR9 installed | `fcc5a2ac1eff3760d306a337a9c8871780365488` |
| SP Core pin | `04c189fccfaa56badfc079f7be46fea4969ccf37`;9.1.0 |
| Performance current source2.0.0 | `0dc6ca31228da400a48e1278c13853d088b9dec9` |
| Performance actual packaged source из PKGBUILD | `f79d4f7e4cf2d235ae7d35a7b6a1b7a6be42486f` |

12 адресно полученных Core/QRC файлов совпадают по bytes с Java installed; три ключевых Core файла — с SP installed. Для Performance render/inspect/diagnostic/capabilities/setup/package-coverage получены в current и packaged refs и совпадают. Experiment subset проверен только у currentSource. Это сравнение уже прочитанных исходников, не full-tree hash.

Java root/theory/tasks/lectures/practice/handbook — одна реализация в нескольких scopes, а не шесть продуктов. Исторический `/home/tolya/course-tools/quarto-course-budget` — detached worktree самого quarto-course, HEAD5ea107e3338f2cb90dc3c7cf47884875d2fe384f, а не отдельный актуальный бюджетный пакет. Остальные старые local checkout HEAD не выданы за pin/latest.

Собственные первичные metadata: thread01a1a16e-662d-7331-bba7-1c693b908cdf, исходный turn01a1a16e-666d-70a0-bc4b-b46158c7b7f0; top+nested `gpt-6.1-sol/ultra` подтверждены на всём интервале от него. Standard/config не менялись. Источник и номера строк сохранены в `hotspots.json:admission`.

## Главные различия

- Java9.3.4 уже имеет narrow contract aggregate probe, operation caches и static checkpoint без source IO. Все Java owner configs выбирают static. Полные entry/exit/batch fences остаются. Утверждение «каждый getter снова хеширует весь курс» неверно.
- SP9.1.0 ещё не имеет static/checkpoint/contract batch fast path. Его withProjectContexts cache miss копирует всё S, делает N inspect с profile render всех inputs; assertCurrent вызывает metadata/membership IO. Эти старые расходы нельзя переносить на Java9.3.4.
- Snapshot хеширует все неисключённые файлы owner, включая `_extensions/vendor` и непотреблённые projects. Последующие source fences в основном lstat/realPath/readDir, а не повтор SHA256 S.
- QRC local post повторяет owner inspect; затем full QRC и Navigation повторно parse/write HTML. Внутри одного QRC publish search уже переиспользует parse tree.
- Performance mode off всё равно делает pre-inspect/Git/version. Summary/cpu ещё готовят полный diagnostic backend и inventory. Это расходы wrapper; прямой native render их не несёт.

[Decision74](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/docs/refactoring-plan.md#L395) требует осмысленные границы актуальности, пакетный native контекст и сохранность финальной приёмки. Он не закрепляет перечисленные hash/copy/probe механизмы буквально. Новый общий validator/DSL/proof system не предлагается.

## Группы для следующего прохода

Полные поля repository/ref/path:line/function/caller, scope/frequency/lifetime, назначение и ограничение каждого вывода находятся в [core-hotspots.json](core-hotspots.json). Ниже краткий маршрут; ссылки ведут на точный исполнимый call site.

| ID | Реальный механизм и частота | Что поставить под вопрос |
| --- | --- | --- |
| [C01](https://github.com/Afonenko-Course-Tools/quarto-course/blob/a478d2cc0dfb19e0bebd99108815ac8ef1fe85a8/_extensions/course-core/runtime/source-snapshot.ts#L92) | Snapshot всего owner: один walk/read/SHA256 каждого неисключённого файла на fresh operation. | Whole-owner bytes ради малого render; closure и fixed immutable release вместо повторов. |
| [C02](https://github.com/Afonenko-Course-Tools/quarto-course/blob/a478d2cc0dfb19e0bebd99108815ac8ef1fe85a8/_extensions/course-core/runtime/source-snapshot.ts#L155) | Java static: full outer/batch boundaries; checkpoint без S/G/K IO. SP: local IO каждый assertCurrent. | Дубли explicit/full fences вокруг уже закрытой batch, масштаб SP selected scope. |
| [C03](https://github.com/Afonenko-Course-Tools/quarto-course/blob/a478d2cc0dfb19e0bebd99108815ac8ef1fe85a8/_extensions/course-core/runtime/operation.ts#L251) | Runtime executable hash на full boundaries; assignment K initial hashes, далее metadata; authority full хеширует capsule/G. | Lifetime fixed toolchain/authority между близкими trusted boundaries. |
| [C04](https://github.com/Afonenko-Course-Tools/quarto-course/blob/a478d2cc0dfb19e0bebd99108815ac8ef1fe85a8/_extensions/course-core/native-ts/late-filter.lua#L66) | Late facts получает один observedDoc как raw/public, но обходит оба. Затем Lua SHA1→post SHA1+SHA256→first DTO SHA256. | Удаление identical AST scan; зачем несколько digest/read стадий для retained bytes. |
| [C05](https://github.com/Afonenko-Course-Tools/quarto-course/blob/a478d2cc0dfb19e0bebd99108815ac8ef1fe85a8/_extensions/course-core/infrastructure/project-context.ts#L536) | Current contract batch: один inspect missing set, cached=0. Fallback/standalone: whole copy и per-input probes. | Использование narrow existing API и реальные причины fallback. |
| [C06](https://github.com/Afonenko-Course-Tools/quarto-course/blob/04c189fccfaa56badfc079f7be46fea4969ccf37/_extensions/course-core/infrastructure/project-context.ts#L850) | SP9.1.0: whole S temp copy, N inspect с render всех inputs, no static/checkpoint. | Перенос уже существующего batch подхода в SP после проверки consumer API. |
| [C07](https://github.com/Afonenko-Course-Tools/quarto-course/blob/a478d2cc0dfb19e0bebd99108815ac8ef1fe85a8/_extensions/course-core/runtime/operation.ts#L1542) | Post фиксирует HTML bytes/hash; exercise-index rehash; reserved seal перечитывает outputs/resources. | Последовательные фиксации неизменного output на local/scope/stage boundaries. |
| [C08](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/_extensions/Afonenko-Course-Tools/course-core/native-ts/early-context.lua#L64) | Per-doc JSON child transport, clone/schema scans, occurrence serialization; late whole clean walk дважды. | Повтор cleanup/serialization и overlapping subtree scans без смены native semantics. |
| [C09](https://github.com/Afonenko-Course-Tools/quarto-course/blob/a478d2cc0dfb19e0bebd99108815ac8ef1fe85a8/_extensions/course-core/artifacts/project.ts#L161) | Per-plan materialize cache; Pandoc README/fragment children; per-file hashes/clones. PL preliminary/full/final calls. | Перерендер неизменных частей; writeInventory rehash лишь exported API, caller count UNKNOWN. |
| [C10](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/_extensions/Afonenko-Course-Tools/course-core/body-export/references.ts#L39) | Только citation-bearing uncached fragment: два citeproc child; Body resources→binary string→base64. | Второй witness child при сохранении unknown-citation отказов; лишние transport copies. |
| [C11](https://github.com/Afonenko-Course-Tools/quarto-reference-catalog/blob/798f8c0d9f87fd5dcfe9ee85b79df57e647d2f3c/_extensions/reference-catalog/entrypoints/post.ts#L8) | QRC local post: owner inspect+version каждый hook, хотя Core owner inspect уже был. | Передавать существующие effective config/version facts в coupled path; standalone сохранить. |
| [C12](https://github.com/Afonenko-Course-Tools/quarto-reference-catalog/blob/798f8c0d9f87fd5dcfe9ee85b79df57e647d2f3c/_extensions/reference-catalog/infrastructure/publish.ts#L66) | QRC parse5 once/page/publish, all linked HTML write; local→full повторяет. Search делит tree с linker. | Local finalize перед немедленным full finalize; unchanged writes. |
| [C13](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/tasks/_extensions/Afonenko-Course-Tools/course-navigation/composition.ts#L79) | Navigation composition после full QRC снова read/parse всех current HTML. | Вторая Page-tree boundary и same entries sort на каждой странице. |
| [C14](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/_extensions/Afonenko-Course-Tools/course-presentation/filter.lua#L39) | Presentation identity+transform AST passes; local DOM observers и notes scans на событиях. | Только адресно дубли notes-normalize; это низкий приоритет, не hashing/inspect. |
| [C15](https://github.com/Afonenko-Course-Tools/quarto-course-performance/blob/0dc6ca31228da400a48e1278c13853d088b9dec9/src/runner/render.ts#L86) | Performance render all modes: inspect+Git provenance+version перед native child. | Off baseline total-wrapper overhead и reuse уже известных project facts. |
| [C16](https://github.com/Afonenko-Course-Tools/quarto-course-performance/blob/0dc6ca31228da400a48e1278c13853d088b9dec9/src/runner/diagnostic.ts#L125) | Summary/cpu: quarto.js hash дважды/probe; treeHash→copy→treeHash src/bridge/node_modules; package recursive inventory и facade hashes. | Сначала прямые duplicates; затем prepared immutable backend/inventory reuse. |
| [C17](https://github.com/Afonenko-Course-Tools/quarto-course-performance/blob/0dc6ca31228da400a48e1278c13853d088b9dec9/src/sources/freeze.ts#L14) | Explicit experiments: runtime rehash per variant, full course/package copies; output walks/hash; default pair benchmark22 renders. | Не повторять неизменные freeze/prepare/equivalence/series без нового основания. |

## Покрытие и границы

Исполнимые цепочки Core runtime/source/authority/A1/artifacts/Body/native TS, Presentation filters/browser, Navigation composition, QRC publish/linker/search/probes и Performance wrapper/diagnostic/experiments просмотрены адресно. Точное количество invocations, bytes и доля времени остаются UNKNOWN; новых измерений не назначено.

Recursive QRC `files()` helper без caller, vendor unused APIs и URL hash не выданы за фактический filesystem/content hotspot. Sync/materialize-providers и production pin hashing относятся к release tooling; CI/tests/packaging smoke отделены от обычного runtime. Packaging Podman/makepkg/full-tree smoke рассматривает координатор.

Source mechanism ещё не доказывает главный bottleneck или ускорение полного курса. Разные смысловые AST passes и verification witnesses не объявлены автоматически лишними; inventory ставит их под вопрос без разрешения blanket-удаления privacy/currentness safeguards.
