# Site Native inspect: источник вызовов и ближайший ограниченный diff

## Допуск и область

Назначение `gpt-6.1-sol/high`, Standard. Chat `01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1`, agent `/root/perf_event_types`; primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-41-59-01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1.jsonl`, session_meta1. Origin `01a1a514-1688-7662-80fc-58bf7a272ae0`; полный собственный interval turn_context8/171/270/405/734/918 согласован по верхним и вложенным `gpt-6.1-sol/high`, вход PASS. Отдельный service tier primary не содержит, глобальные настройки не менялись.

Только Source и сохранённые данные одного SP stage: Site `d16f2322c1da3c96c2e93d32225f5428ae347756`, QRC `8732dbbe0d8502f8b6a77049e27907ae8a66e46a`, Core `0cf20ccc0a4853b704544c7252fcaf3ccb84a518`. Stage exact refs/destinations — [sp-shared-site6-stage-20261104-actual/stage-metadata.json](sp-shared-site6-stage-20261104-actual/stage-metadata.json); не заменяются позднейшими HEAD/installed composition. Прочитаны applicable Site AGENTS, spec/index, адресные contract paragraphs и действующее русское языковое правило. Print/Cloud не рассматриваются.

## Основной вывод

**Шесть inspect с `--profile full` происходят только из Site `validateAudienceOutputs`, чтобы заранее сравнить output с невыбранной аудиторией.** Native student child/Core/QRC их не требуют. Их сохранённые законченные envelopes — **9.914178029 s**. Это лучший ограниченный кандидат убрать необязательное ordinary runtime действие.

Три `student` inspect каждого завершённого child имеют одинаковые argv/cwd, но вызываются разными процессами и фазами: Site планирует безопасные destinations/independent selected inputs до cleanup; Core pre создаёт собственную owner/capture binding; QRC post читает конфигурацию текущей локальной публикации после render. Удалять их только по совпадению argv нельзя. Без нового межпроцессного config/authority транспорта однозначного source-preserving объединения этих трёх границ не установлено.

Удаление alternate-profile preflight — осознанное изменение **нашего** Site contract про cross-audience output coexistence, а не сохранение всех старых гарантий или требование Native Quarto. Его нужно назвать и согласовать; active audience/privacy/current-output проверки остаются обязательными.

## Сначала receipts → parent IDs → Source phases

Источник групп — существующий `inspect-invocation-groups.json`: 21 receipt. Адресно прочитаны только их известные parent receipt файлы из run `6d0026fe-08a9-45ca-8e94-023083fa5056/processes`; новый Native/normalization/полный registry scan не запускался.

Root render `5fea6121-65b8-4f43-980e-ff005cdb86c2` породил Deno **Site pre** `a428665c-87e5-4e4a-9b50-aab81c05d8dd`; его argv заканчивается `_extensions/Afonenko-Course-Tools/course-site/entrypoints/pre.ts`, cwd COURSE. Все 12 Site inspect имеют этот parent. Root/Site receipts остались initial pending из-за LIMIT_STOP; null PID/initial executed:false не означают, что pre не исполнялся: child linkage и выполненные inspect сохранены.

`COURSE` в таблице — stage/course, `quarto inspect COURSE[/owner] --profile student|full`; cwd совпадает с соответствующим owner. Идентификаторы — actual process_id inspect, не имена вымышленных операций.

| Owner | Site selected student (parent a428…) | Site alternate full (parent a428…) | Core pre student | QRC post student |
| --- | --- | --- | --- | --- |
| root | `f8c80907-0c68-4b3e-87a6-d58b15c2d960` | `da5aa343-9db2-452c-af2b-1fb789ee6c0d` | не достигнут | не достигнут |
| theory | `20f65472-8005-4b5c-9e7a-1d83b2ec4394` | `b2814cde-37be-48a7-a134-21a9caf60308` | `7ac52c80-76ff-4087-9169-193263be6bcc` | `3598d10f-374f-475e-a044-09a4910ee413` |
| lectures | `0de85b8d-d51a-4d8c-b82d-ee6302817941` | `fb232843-0ee7-4687-9112-f96b83ad19ce` | `13601d15-ca1f-4d68-91db-37788e9bac00` | `2bcf5606-0222-4cb8-b7a6-c696da2553b8` |
| tasks | `bc044770-9daa-4aa1-8080-89e34fa8e527` | `bf47f322-ff54-4e41-927b-0c5780df3338` | `22b0d735-b555-4ec8-9583-61a127a3ce67` | `56120eeb-7b0d-48e2-81ff-647e1e80abd4` |
| practice | `47dc1fd0-735b-40fc-80e7-d8614f2dbc59` | `2be0e5f0-370c-4058-8157-79f024e1ab40` | `8a711997-2d48-497d-9815-0648e5443ad3` | `a2410c69-ba52-4a2f-b1a1-3f1bce256286` |
| handbook | `33186394-f6f1-4d11-bb9f-4ebb5464c7bf` | `bbebaf6e-4d06-46f2-8bc9-c84d962f4c69` | `d4456b72-207a-4b0d-b40e-c4623595ad09` | не достигнут |

Parent hooks и их render parent:

| Owner | Core pre parent Deno | QRC post parent Deno | Общий child render |
| --- | --- | --- | --- |
| theory | `378f841f-9e87-4947-94dc-6c525788f6ef` | `0533047b-7aee-4a5e-a553-d25d043230fb` | `ddf12aa5-8795-4046-8852-04e9c75e39a7` |
| lectures | `a71adc3a-fc98-4e20-8e4e-07d3fa8c1501` | `8bcde5a5-875d-40e8-8e76-0efb84541dfd` | `bc15ae61-bbd4-4888-999e-1fd7e0ba1375` |
| tasks | `3d24d4a6-2a1d-4dc4-a462-22406789f97c` | `b0433577-36d7-4e4e-81ce-2a26fee81709` | `2514cda3-3e4e-4a47-89d6-5432bd8d1547` |
| practice | `138ffe8d-efe3-47de-a14c-69d5f99e6fa8` | `a05b5965-9cee-4db3-85f5-37f31ac4561c` | `2b115f2f-494a-46df-9505-78b5c37308fc` |
| handbook | `82914aff-5c77-438b-bd8d-074ca72ddc23` | отсутствует | `eac35966-3239-448b-87f8-d205ed8e9df5` |

Core parents argv заканчиваются `course-core/entrypoints/pre.ts`, QRC — `reference-catalog/entrypoints/post.ts`. Stage child config действительно перечисляет Core pre; QRC post → Site collect → Core post (tasks также Download hooks). Это устанавливает фазы независимо от одинакового inspect argv. Handbook завершения/post не достиг, поэтому его два student inspect не доказывают отсутствие третьего в успешном run.

## Что потребляет каждый вызов

### Site selected plan — сохранить

`course-site/entrypoints/pre.ts` → `application/compose.ts:pre`:

- `infrastructure/config.ts:202+ workspace` делает root selected inspect; потребляет Native config, current output override, resources и при whole-project render независимый selectedSources.
- `compose.ts:366–409` делает один `inspectProject(child, ws.profiles)` на child. `config.ts:397+` возвращает selected config, guarded source list и resources.
- До первого destructive cleanup Site проверяет selected root/child output geometry, source/sibling/root overlap, paths и actual active audience; затем `cleanOutput` удаляет только проверенный destination. Выбранный output нужен **до** child render, а не после него.
- SelectedSources затем сопоставляются с Core catalog/source/output/to в `catalogCoverage` внутри live observer. `spec/contract.md:24` явно требует independent native selection, а не подмену его тем же Core catalog.

Удаление этих шести selected inspect потребует другого фактического planning input до cleanup и пересмотра independent-coverage гарантии. Borrowed Core proof или другой профиль этого не заменяют.

### Site alternate audience — самостоятельная наша гарантия

`compose.ts:348+` и `:400+` вызывают `validateAudienceOutputs` с уже известным selected config. `config.ts:263–342`:

1. pure validateAudience проверяет current view/projection;
2. для **невыбранной** canonical student/full audience ищет `_quarto-<audience>.yml/yaml`;
3. заменяет selected profile или добавляет alternate к functional profiles;
4. делает Native inspect **этого другого профиля**, вычисляет его output и запрещает cross-audience equality/nesting.

В данном explicit student run selected known config повторно не inspected внутри helper; именно alternative branch создаёт шесть full calls. `known.profiles` не совпадают с full selection, поэтому reuse там по конструкции невозможен. Full inspect нужны только для synthetic «заранее гарантировать независимость двух возможных выдач», а не для текущего student content/role/public output.

### Core pre — другая текущая owner boundary

`course-core/entrypoints/pre.ts` → `beginNativePreHook` → `inspectNativeOwner(root, profiles)` (`runtime/operation.ts:1261` на0cf). Native pre сначала выбирает текущий input/output/profile context и snapshot, затем делает единственный owner inspect, bindOwner и source/runtime fences; выдаёт pending operation/capture witnesses. Selected version/dir/input/project/projection факты принадлежат этой operation, не более раннему Site плану. Site plan выполнялся до child cleanup/render, Core pre — внутри отдельной Native invocation. Повторно объявить parent config trusted child owner без lifetime/transport contract нельзя.

### QRC post — другая publication boundary

Post entry и потребление config дополнительно прочитаны из exact stage archive `providers/qrc` на873; позднейший worktree HEAD не заменяет этот ref.

`reference-catalog/entrypoints/post.ts:8` делает selected inspect после child render. Используются namespace, `reference-catalog` imports/exports/publication config и output-dir fallback; actual current output список берётся из Native post environment/file. `infrastructure/publish.ts` сверяет current paths/regular physical outputs, выполняет local linking/search и создаёт `reference-catalog-local.json` до Site collect/Core post.

QRC publish API уже принимает config без собственного inspect при явном caller (Site root full publish именно так и работает). Но у standalone child post нет переданного authentic config; existing COURSE_SITE_COLLECTION — destination collection, не предварительно доступная config/authority запись. Site state записывается лишь после всех children. Подмена QRC config root plan или чтение ещё не готовой collection — не established reuse.

Local child QRC и поздний root QRC full publish тоже не автоматически одинаковы: различаются root/stage, namespace/member/import/exports scope, current outputs и local catalog. Root post ещё не достигнут в данном LIMIT_STOP. Skip child QRC post по факту Site env без доказанного сохранения local outputs/catalog/owner semantics не входит в минимальный diff.

## Реальная известная цена

Все числа — existing `native_command.wall_seconds`, monotonic `launcher-spawn-to-status`, **inclusive envelope**, не CPU/self/semantic-exclusive time.

| Phase | Completed inspect count | Сумма именно этих отдельных inspect envelopes |
| --- | ---: | ---: |
| Site alternate full |6 |9.914178029 s |
| Site selected student |6 |9.967068639 s |
| Core child pre student |5 |5.563130708 s |
| QRC child post student |4 |4.688430783 s |

Full root отдельно4.437316922 s; пять full child —5.476861107 s. Site Source делает эти preflight inspect последовательно до child renders. Удаление соответствующих Source calls уберёт их observation work в будущем run; **это не измеренный wall delta** и не обещание закончить SP в120 s. Native children/capture/IO/load и ещё не достигнутый root post остаются другими расходами.

Saved stage LIMIT_STOP: outer115.005 s (включая launcher preparation), четыре child render exit0 (theory24.7865/lectures14.1111/tasks22.0966/practice15.2099 s), handbook/root UNKNOWN. Parent/child envelopes из143-command table не суммируются с inspect, не выводятся speedup/CPU/RSS/полная critical path. Нового запуска для этого отчёта нет.

## Один ограниченный следующий Source diff

Предпочтительно **два production файла Site**:

1. `application/compose.ts`: убрать ordinary root/child alternate `validateAudienceOutputs` calls. Root уже вызвал pure `validateAudience`; child вызов заменить **именно pure `validateAudience`** с прежними config/view/projection и expected root audience. Нельзя просто удалить helper call и потерять child `SITE.AUDIENCE_MISMATCH`. Сохранить все selected inspect, outputDirectory/path/source/sibling/root overlap checks, before-clean planning, selectedSources coverage и Native profile forwarding.
2. `infrastructure/config.ts`: удалить ставший ненужным ordinary alternate-profile helper/неиспользуемое inspect wrapper, если адресная проверка callers подтверждает отсутствие другого runtime API consumer. Не добавлять filename-derived metadata resolver, profile→output cache, receipt, transport flag, hash или metadata freeze. Current config факты уже получены existing Native boundary и используются как раньше.

Сопроводить точной правкой spec: обещание `student/full outputs не пересекаются` как обязательного per-render alternate preflight (`contract.md:22`) больше не удостоверяется этим runtime. Independent **selected** project/source inspection (`:24`) и active audience mismatch (`:182+`) сохраняются. Если root требует прежнюю cross-profile coexistence guarantee при каждом render, эти шесть calls нельзя честно удалить без иной авторитетной проверки; переносить их в новый protocol ради сохранения необязательной гарантии не предлагается.

Existing focused control seams для последующей назначенной проверки: `tests/site-profiles.ts` (pure mismatch/old alternate overlap), `site-paths.ts` (actual unsafe outputs), `site-metadata-profiles.ts` (Native metadata-files с profile-like именами), `site-domains.ts` (root/child audience). Переписать только obsolete alternate-profile ожидания; сохранять реальные mismatch/selected path/privacy/implicit profile controls. Сейчас их Source/Native не запускали, нового CI/матрицы не назначали.

### Риски, которые нельзя скрыть

- **Cross-profile output coexistence:** больше не будет раннего отказа, если невыбранный full профиль использует тот же/nested output. Текущий selected full-composition output всё ещё очищается через existing protected-source/cache guards, а mounting использует current collection, поэтому stale files не становятся current manifest автоматически. Но сохранность/независимое существование старой выдачи другой аудитории больше не гарантируется этой проверкой; shared output может быть удалён/перезаписан. Это реальное сужение обещания, не бесплатная оптимизация.
- **Privacy/roles:** текущие student view→student projection, Core actual role/physical/resource guards, QRC current output/visibility и student public content остаются. Это не taint analysis произвольных authored resources; удаление alternate inspection не разрешает author content в student. Нельзя уменьшить active checks ради числа calls.
- **Profiles:** functional/custom profiles передаются Native буквально; selected audience определяется effective config, не именем файлов. Нового синтетического full inspect для невыполняемой audience нет. Не угадывать Native defaults/groups и не замораживать metadata между фазами.
- **Duplicate active argv:** в этом diff их не объединяем. Core capture owner и post QRC publication должны сохранить собственные consumer boundaries. Для дальнейшего сокращения потребуется отдельное доказанное использование уже имеющегося Native context/публичного API, а не новая private конфигурационная квитанция.

Это план, production Source не менялся. Следующий authorized implementation может подтвердить выбранную contract дельту адресными existing controls и одним заранее назначенным meaningful run; текущий LIMIT_STOP не даёт права автоматически повторить SP с большим cap.

## Действия и пределы

Прочитаны группировка21 existing receipts, их точные parent receipts, stage refs/report/LIMIT_STOP и адресные Site/QRC/Core Source/callers/contracts. Parent argv/addressing и sums вычислены только из этих сохранённых данных. Whole R0–R6/общие аудиты не перечитывались. Новые Native/render/inspect/call replay/compiler/CI/network/hash действия не выполнялись; Source inventories/measurement frameworks не создавались. Для адресного поиска известных Source/сохранённых файлов использовался rg. Product Source не редактировался, сторонние dirty worktrees не трогались. Print/Cloud/pins/production/UUID/оценки/commit/push не входят в пакет.

Перед итогом полный собственный interval от origin `01a1a514-1688-7662-80fc-58bf7a272ae0` перечитан: turn_context строки 8/171/270/405/734/918, все верхние и вложенные пары gpt-6.1-sol/high согласованы, итоговый допуск PASS.
