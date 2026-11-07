# SP + shared Site6: следующий disposable stage

2026-11-04. **Source-only план, stage ещё не создан и Native qualification не запускалась.** Назначенная пара воспроизводима локальными Git archives без изменения SP, installed packages или pins. Нужны 15 замен package directories в отдельной report-stage copy; новых manifest/runtime/import/cache протоколов не требуется. Старый SP delta запускал только PL tasks export, поэтому его PASS не является проверкой общего Site6.

## Точная пара

| Source | Exact ref для stage | Содержимое archive и назначения внутри `course/` |
|---|---|---|
| SP `system-practice` | `9a2f8120a37d1b42384e2f008b3e0a3a34247008` | Весь tracked course archive; это уже принятые пять `{path,to:default}` records |
| Core `core-native-types` | `0cf20ccc0a4853b704544c7252fcaf3ccb84a518` | `_extensions/course-core` → шесть scopes ниже |
| PL `prairielearn` | `f3a09cd060609b57e516779588d635abedb1ca21` | `_extensions/course-prairielearn` → `tasks/_extensions/Afonenko-Course-Tools/course-prairielearn` |
| QRC `qrc` | `8732dbbe0d8502f8b6a77049e27907ae8a66e46a` | `_extensions/reference-catalog` → шесть scopes ниже |
| Download `download` | `944ca76dbdef5bb4842116950621f82449b631b3` | `_extensions/project-download` → `tasks/_extensions/Afonenko-Course-Tools/project-download` |
| Shared Site `site-java` | `d16f2322c1da3c96c2e93d32225f5428ae347756` | `_extensions/course-site` → `_extensions/Afonenko-Course-Tools/course-site` |
| Performance | `356a76b52d4fa0b20b965a82c1997e34632173fc` | Existing `prepareLaunchers` / `executeScenario`; отдельная tool identity, не extension SP |

Шесть scopes для Core и QRC: `.`, `theory`, `lectures`, `tasks`, `practice`, `handbook`. Во всех сохраняется существующий namespace `Afonenko-Course-Tools`. Presentation и Navigation остаются точными tracked installed bytes из SP9a: provider `core-ui`, ref `04c189fccfaa56badfc079f7be46fea4969ccf37`, версия `9.1.0`, оба пакета в шести scopes. Переносить весь Core `_extensions` нельзя: это без назначения изменило бы также UI пакеты.

SP HEAD9a и PL/QRC/Download/Site/Performance refs прочитаны непосредственно. Core0cf прочитан через `git show` exact ref: текущий HEAD `core-native-types` уже `d8b4929c5e8ac2e6f96cb338d0fb1b7dae14fcfc`, **его нельзя неявно взять вместо назначенного0cf**. Descriptor0cf — Core9.3.4; PL9.0.7, QRC3.2.0, Download3.5.0, Site6.0.1. Эти прежние descriptor versions не удостоверяют новую локальную композицию: реальные overrides определяются точными Source refs.

## Минимальные будущие файлы и операции

Основа — сохранённые [staging driver](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-sp-pl9c-delta.py) и [Performance driver](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/performance-sp-pl9c-delta.ts). Их не переписывать: это provenance прежнего результата. Достаточно двух небольших report-local адаптаций, например `reports/sp-shared-site6-stage-20261104.py` и `.ts`, и одного нового disposable каталога `reports/sp-shared-site6-stage-20261104/`. Сейчас ни один из них не создан.

1. Создать отсутствующий stage; существующий каталог не удалять/не переиспользовать молча. Existing `archive(repo, exactSHA, destination, paths)` с tar `filter='data'` извлекает **весь SP9a** в `stage/course`. В отличие от старого delta, не исключать шесть `_extensions` с последующим copy из mutable workingtree: все эти installed files уже tracked в SP archive. Так неизменённые пакеты имеют точный исходник, без нового inventory/hash прохода.
2. Из пяти локальных provider repos архивировать только пять package roots из таблицы в `stage/providers/<provider>`. Затем заменить только 15 перечисленных destination directories в stage/course. Это обычные файлы, без symlinks на original courses/repos. Не вызывать `quarto add`, materializer, installer или fetch. Вся очистка будущего Site/Download должна попадать только в эту copy.
3. Как в прежнем delta, скопировать **ровно три** сохранённых catalog inputs: `theory/_output/student/reference-catalog-local.json`, `practice/_output/student/reference-catalog-local.json`, `handbook/_output/student/reference-catalog-local.json` в те же relative paths stage. В исходном SP они только читаются. Не копировать остальные `_output`, `_generated`, `_site*`, `.quarto`, `_freeze`, engine caches или student/result worktrees. Архив содержит tracked source, а не ignored runtime scratch.
4. Записать `stage-metadata.json`: SP exact archive, пять override refs/package roots/destination arrays, unchanged UI ref, Performance exact ref, три заимствованных catalog inputs и фактическую cache preparation. `providers.json` и `installed-packages.json` в stage остаются **BASE provenance из SP9a**, с явной пометкой, что override composition описана stage metadata. Нельзя выдавать их за regenerated lock текущих packages.
5. В existing `PreparedVariant.manifest` указать пять `replacements` с соответствующими destination arrays и шесть Source rows (SP + пять providers), а не оставить прежние PL-only поля `packageOverride/plArchiveHead/installedCore:d7`. Использовать existing schema2 и sourceKind `local-candidate`; не добавлять новую схему. Existing empty `packages`/`runtime` не объявлять полной installed closure. Timings/metadata должны называть stage Core0cf, PLf3 и остальные actual refs.
6. Адаптировать только Scenario выбранного будущего qualification: root `cwd: '.'`, executable `/opt/quarto/bin/quarto`, args `['render', '.', '--profile', 'student']`. Это штатный вход в root pre/post Site hooks, пять native child commands `render . --to default --profile student` по существующему Site producer, root collect/mount/QRC. Оставить existing summary mode, launcher preparation, diagnostic env и `coverage.complete:false`; новые trace/runner/guard pipelines не нужны. Запуск остаётся отдельным решением root.

Никаких изменений course `_quarto*.yml`, hook paths, output paths, descriptor content или Source imports для этой пары не найдено. SP9a уже заменил пять строк `subprojects` на records, чего требует Site6; root и child hook paths уже указывают на выбранный namespaced Site root. Root student output — `_site-student`, child student outputs — `<part>/_output/student`; full output paths не затрагиваются.

### Почему не materialize-providers

Текущий `system-practice/tools/materialize-providers.py` — release installer: требует published tag/version/commit и для local candidate release+tracked patch+packageHashes; `--write` строит full inventories, проверяет hashes и записывает installed manifest. Его `--check` тоже не принимает archive overrides как published closure и выполняет inventory/hash. Ни один из этих путей не нужен для transient report-stage. Перегенерация `providers.json`, patch files или `installed-packages.json` ради этого опыта смешала бы qualification с release installation и добавила неназначенную работу. После stage PASS будущая настоящая installed provenance — отдельная задача, не скрытое следствие этого плана.

## Реальные import/cache границы

| Граница | Что действительно требуется stage | Что не менять / предел |
|---|---|---|
| Runtime sibling imports | Site `application/compose.ts:35–75,170–199` выбирает public modules в том же namespace конкретного owner; PL `native-materialize.ts:21–38` и `native-entry.ts:193–214` ищут установленный owner Core/QRC/Download; Download `infrastructure/siblings.ts` импортирует соседний `public.ts` | Поэтому обновить Core/QRC во всех шести owners, PL/Download только tasks, Site только root; не делать global import redirects на original repos |
| Type-only bare imports | PL/Site `course-core/...` / `course-prairielearn/...`, Download `typeof import(...)` | Их runtime не импортирует. Не патчить существующий type-only map pin830 ради stage, не создавать новый compiler graph/check ради qualification |
| Native `stdlib/path` imports | Existing Quarto run import map `/home/tolya/.cache/quarto/deno_std/run_import_map.json` и `DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache` | Сохранить existing cache/map; не переписывать map на SP tree, не подкачивать missing dependencies. Доступность будущего конкретного cached graph не проверялась новым запуском |
| Performance driver dependencies | Existing Performance `deno.json`/`deno.lock`, bundled Deno и тот же existing `--frozen --node-modules-dir=manual --cached-only` wrapper старого delta | Это Deno dependency options, не Quarto execution freeze. При preparation miss остановиться с фактом, без install/retry цепочки |
| Native caches | Existing writable `XDG_CACHE_HOME=/home/tolya/JavaCodex/.cache`, `DENO_NO_PACKAGE_JSON=1`; `QUARTO_RUN_NO_NETWORK=true`; pinned Quarto1.11.5 и existing launcher setup | Не копировать/чистить real `.quarto`/`_freeze`; persistent XDG не делает свежий stage warm execution tree. No-network option Quarto не является общим R/Python sandbox |
| QRC inputs | Три filenames из `tasks/_quarto.yml:47–59` остаются локальными relative files; `base-url` публикации не переписывается на file URLs | Это заимствованные student catalogs, а не новая full-profile/freshness qualification |

Порядок SP subprojects: theory, lectures, **tasks**, practice, handbook. Поэтому tasks в новом root run увидит свежий theory catalog, но practice/handbook ещё только прежние catalog inputs. Их наличие необходимо без изменения existing author configuration. Это честное ограничение: не переставлять части и не обещать свежие downstream cross-references в такой stage. Нельзя подмешивать full catalog под student path.

Site6 сохраняет `.quarto`/`_freeze` и engine caches в том owner, где они существуют, но очищает текущие output paths; в новом archive-stage ignored caches отсутствуют. Его root pre-render также независимо планирует все child owners до render. Нельзя запускать из original SP со Site5.3 на records9a или считать PL export entrypoint заменой root Site route.

## Один полезный будущий вопрос и бюджет

**Проходит ли один штатный student root render SP9a с этой exact archive-парой через общий Site6, включая actual Core selected-source/HTML coverage и tasks PL/Download owner validation, с пятью mounted student parts и разрешёнными QRC ссылками?** Ответ опирается на существующие product guards/current output и existing Performance capture, без новой test matrix. На успехе адресно прочитать уже созданные root Site collection/QRC outputs и task download links; это не grader/deploy/full-profile/cache qualification. Отдельный PL `export-course.ts` автоматически вторым запуском не добавлять: он проверяет другую выдачу и удвоит Native работу. Ни student payload/UUID counts, ни final PL delivery не объявлять квалифицированными одним Site render.

Сохранённый [SP delta](performance-sp-pl9c-delta.md): Native **39.798s**, outer40.931s, stage0.491s, launcher preparation0.268s; tasks7documents, один render20.078s, три inspect. Это tasks-only PL export на SP3bb/Core d7/PL9c/Performance924, с прежними тремя catalog inputs. Новый root render пяти parts на Core0cf — **UNKNOWN по времени**, а не прогноз39.8s и не доказанный speedup. Маленькая Site6/QRC fixture PASS10.151s после corrected XDG не включает Core/Download и не является оценкой целого SP.

Для отдельного разрешённого будущего опыта разумно переиспользовать прежний **один outer cap120s, TERM/KILL grace5s, retry0**, один процессный запуск, summary mode; по expiry сохранить stage/log/частичный capture и остановиться. Это лимит затрат, не обещание успеть whole SP. Суммы nested command timings не считать CPU/critical path; report-only, overall CPU/RSS и projected whole-course speedup остаются UNKNOWN. Текущая standalone SIGTERM проблема Performance не объявляется закрытой этим планом.

## Пределы этой работы

Прочитаны exact Source contracts, manifests и сохранённые результаты. Не было render/inspect/hash/build/compiler/test/install/network, source/course/pin/state/cache edits, commits или stage preparation. Создан только этот report. Предыдущие acceptance/results/UUID/student files сохранены.

Primary admission: собственный chat `01a1a25f-b098-7242-bf6d-d0e8f6517361`, исходный interval `01a1a25f-b0fb-7fd2-b244-83933af77834`; primary `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-05-42-01a1a25f-b098-7242-bf6d-d0e8f6517361.jsonl`. Прочитаны только model/effort/turn fields всех20 turn_context, lines8–2813: top-level и nested везде `gpt-6.1-sol/high`, последний turn `01a1a549-b9a2-7b03-ae64-3bbb97567176`. Standard назначен; скрытая server routing не удостоверяется. Globals/normative admission docs не менялись.
