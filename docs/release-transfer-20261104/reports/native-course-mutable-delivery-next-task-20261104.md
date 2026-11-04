# Следующая задача: изменяемая Native поставка полного PL курса

Нужно согласованно удалить связь «байты курса → deliveryHash → курс/назначение/
результат» из полного PL export, Platform и Gateway. **Identity остаётся у
существующих записей Native PrairieLearn и у уже существующего назначения
Gateway.** PL формирует содержание курса и интеграционные данные; он не создаёт
новую identity результатов при изменении JSON. Обычная Native publication уже
отделена от grading qualification, но этот полный маршрут её изменением не
исправлен.

Независимый [review](native-course-mutable-delivery-next-task-review-20261104.md) подтвердил направление, но признал описанный ниже «минимальный diff» программой нескольких контрактов, а не готовым executable пакетом. Первая ограниченная работа — закрыть ordinary-route Source contract без managed assignments/recognition и без изменений БД.

Это подготовка следующей ограниченной Source задачи. Использованы готовые
producer/consumer hash reviews, Platform pattern pass и новая ordinary closure.
Адресно перечитаны только прямые producers/readers ниже. Нет нового общего
аудита, реализации, DB/live export, network, hash, compiler, Native/render/build,
commit или push. Четыре сохранённых root Source commits/clean checkouts не
менялись. Исторические записи и существующие данные не переинтерпретированы.

## Что должно стать обычным допустимым обновлением

При тех же существующих Native parents и UUID правка title, допустимого поля
info JSON, HTML, публичного изображения либо добавление разрешённого Native
ресурса не должна требовать нового delivery identity, assignment migration или
нового numeric course. Native sync получает существующий course record и
разрешённый каталог исходных данных. Допустимость Native JSON определяет actual
Native schema/loader, а не обязательное совпадение всех байтов с нашим manifest.

Это не разрешение менять выполненную учебную работу, её баллы/слоты, UUID или
пользовательскую принадлежность. Такие изменения имеют собственную семантику
и не становятся безопасными от удаления SHA.

Существующий sync уже вызывает `syncDiskToSql(course, logger)` для выбранного
`--course-id`; deliveryHash туда не передаётся. Сейчас перед этим наши wrappers
требуют SHA suffix пути и сверяют manifest/files. Bootstrap, напротив, ищет курс
по content path и может вставить новый native record. Для обновления существующего
курса нельзя незаметно выбрать этот путь создания.

## Владельцы identity и данных

| Предмет | Реальный владелец | Что сохранять |
| --- | --- | --- |
| Native numeric course/CI/assessment/question, attempts, instance_questions, submissions/jobs/files | Native PrairieLearn DB и её связи | Прежние parents/FK и фактические records; новый course с теми же строковыми UUID не является старым курсом |
| Авторские logical course/work/question IDs и UUID, из которых формируются Native info-файлы | PL exporter/авторский Core input | Текущие алгоритмы UUID и их входные ключи, явные assessment UUID; отсутствие переименований/пересоздания ради content update |
| Выбор разрешённого native course/CI для нашего сервиса | Существующий configured Platform binding | Native numeric IDs, CI UUID/parent/tenant и разрешённый source path; не SHA всего каталога |
| Пользователь, LTI activity/context/resource/lineitem, assignment ID/version/выбранная работа | Gateway и Platform assignment rows | Прежний assignment ID/version и access/ownership/idempotency; content edit не является reassignment |
| Native JSON/HTML/resources и public question content | Native layout/schema; PL пишет подготовленный output | Полные допустимые Native данные, а не воссозданная проекция из closed grading manifest |
| Per-slot completion/choice и assignment label, которых Native average не выражает | Собственный интеграционный PL→Platform/Gateway contract | Реальные work/slot/candidate/completion/label факты и Native group mapping; не менять на average ради упрощения |
| Выбранные trusted tests/runtime/student/public grading inputs | PL check/materialization producer и grader | Роли, целевые файлы/bytes, точный выбранный scope и реальное выполнение; не «неизменность всего курса» |

В exporter course UUID зависит от `identity("course/" + logicalCourseId)`, CI UUID —
от course UUID и instance key, assessment UUID — от явного UUID либо stable work
key в course namespace, question UUID — от stable qualified ID. Эти identity
вычисления нельзя удалять вместе с целостностью каталога. Native course UUID в
infoCourse — собственный metadata field; в прочитанной Native модели главный
parent — numeric course record. Сохранение UUID без сохранения этого parent
не сохраняет results.

## Судьба хешей

| Механизм | Предлагаемая граница следующего diff |
| --- | --- |
| `delivery.files`, canonical `deliveryHash`, basename=hash | Удалить обязательное построение и все live reader/file-walk/equality gates. Не заменять случайным 64-hex ID, timestamp hash, policy hash или новым receipt |
| `sourceSnapshotHash`/`inventoryHash` checks, `verificationInventoryHash`, `selectionScopeHash`, native verification/witness digests | Это собственная корреляция старых projection/verification contracts. Убирать согласованно из текущих exporter/job/verifier schemas/readers там, где служат финальному доказательству поставки; сами selected IDs/scenarios/project/runtime/role данные сохранять. Persisted historical values не переписывать |
| Хеши, из которых получаются stable UUID | Оставить без изменения алгоритма/входов: это naming identity, не byte freeze |
| SHA конкретного source/test/transport artifact | Не объявлять автоматически лишним. В следующем минимальном пакете сохранять там, где он обслуживает конкретную передачу или trusted/student boundary, а не равенство всего delivery. Не расширять задачу на удаление grader containment/runtime admission |
| Workspace seed archive/manifest SHA | Отвязать получение значений от full `files` inventory: собственный seed writer уже знает конкретные bytes. Не сбрасывать work/.git и не менять resume semantics. Изменение seed для существующего Workspace — отдельный нерешённый вопрос, не обычная правка title |
| SHA перенесённого private файла/readback, mapping-request idempotency, token/session hashes, image digest | Другие реальные границы. Не удалять вместе с course content identity и не использовать их как оправдание whole-course freeze |
| Старый deliveryHash в assignment/event/observation/outbox | Сохранённые исторические данные, уже участвующие в lookup/replay. Новая identity не должна зависеть от актуального course hash; старые значения нельзя переписать, потерять или механически сравнить с новым отсутствующим полем |

Здесь нет обещания убрать всякий SHA из Core/PL/Platform. В частности, Core
capture/A1 lifetime/currentness и hashes конкретных выбранных materialized inputs
не равны immutable native course output. Ordinary publication сняла свои
release/ZIP certificates; полная поставка пока содержит другой реальный graph.

## Минимальный согласованный смысловой diff

**PL export перестаёт создавать сертификат содержимого native каталога.**
Убрать full-output recursive inventory, `files`, canonical deliveryHash и final
verification binding. Финальный `delivery.json` перестаёт быть универсальным
источником native content, grading provenance и runtime identity одновременно.
Если Gateway нужны дополнительные integration facts, остаётся один узкий output
их реального PL owner: logical course/instance/work references, slots/candidates,
assignment labels, completion и relatedExercise. Название файла/API определяется
в том же Source contract; нельзя просто оставить старую schema с фиктивными
обязательными hashes. Native UUID/method/options/resources продолжают находиться
в actual info JSON и штатном Native layout. Private grader descriptors/selected
check inputs не следует переносить в публичный integration file.

**Full export не заявляет «все дальнейшие bytes курса окончательно проверены».**
`package_integrity → delivery_receipt → validate_release_receipt` и повторная
самопроверка готового отчёта перестают быть обязательным admission для
stage/bootstrap/sync. Actual Native schema errors, privacy/поля сдачи и
используемые grading/slot/runtime связи остаются предметными проверками своих
consumers. Реальное выполнение starter/reference/contract scenarios — отдельная
явно вызванная проверка выбранных grader inputs с честными outcome/coverage,
не новый certificate изменяемого native каталога. Полнота исполнения не может
подменяться наличием digest. Нужные проверки configured grader/runtime не
объявляются пройденными только потому, что export успешно записал JSON.

**Platform разделяет регистрацию и update существующего Native record.**
`readNativeCoursePayload`, bindings loader и sync читают только реально нужные
native/integration facts; не обходят и не хешируют весь course и не запрещают
дополнительные допустимые Native файлы. Path остаётся canonical/contained и
разрешён configured binding, без обязательного SHA suffix. Existing native
course/CI выбирается по уже имеющемуся configured numeric context; bootstrap
остаётся явным созданием нового курса, не fallback при смене content path.
Нельзя выдавать конфиг `logical courseId` за DB numeric course ID.

**Gateway/Bridge wire context перестаёт выбирать binding по deliveryHash.**
Контекст использует существующий native course/CI binding и user/activity/work/
assignment version. `context()`, resolveAssignment/assignmentTransition,
results/launch, lab scope, observations/outbox и recognition должны быть изменены
вместе. Per-slot group/candidate/points semantics, enrollment/student labels,
UID/tenant/AI/IQ/FK joins и assignment version checks сохраняются. Content edit
не вызывает `store_assignment` и не меняет activated_at/recognized AI только ради
получения нового hash. Это обязательное поведение следующего contract, не
команда менять сохранённые rows.

Существующий bridge загружает integration data в память при старте. Следующий
contract должен явно оставить/назвать эту existing read boundary для своей
политики, чтобы Native title edit не требовал full-course reload/hash. Автоматический
hot reload новых completion rules не предполагается. Для Native sync достаточно
одного прочитанного input этой операции; новый freeze/cache/registry протокол
для доказательства отсутствия внешних edits не нужен.

## Сохранённая история — обязательная точка совместного решения

Простое удаление строки проверки hash недостаточно. В Platform
`pl_gateway_assignments.request` хранит hash; обычный `store_assignment` сбрасывает
recognized AI и updated_at. Attempt selection использует activation time либо
recognized AI с точными audit/FK joins, включая target.deliveryHash. В Gateway
SQLite hash входит в assignments, observations/outbox/native_attempts; lab scope
вычисляется с hash. Поэтому механический новый assignment/version или fresh DB
могут скрыть уже выполненную работу/признанный attempt, раздвоить lab scope либо
подавить pending grade delivery. Этого следующая задача не должна делать.

Нужен совместный выбор владельца persisted mapping **до** изменения runtime:
можно ли для каждого существующего assignment/lab/audit event однозначно получить
старый Native CI/assessment/AI/user context из уже сохранённых FK/полей и
существующих bindings? Исторический audit/digest остаётся точным историческим
объектом; его совпадение с нынешними course bytes больше не должно требоваться.
Это чтение сохранённой истории, а не новый legacy shim/fallback или certificate.
Если существующий record содержит только hash и не даёт однозначного stable
context, Source не может выдумать недостающее соответствие.

**Любая необходимость переписать persisted keys/rows, перенести признанный
attempt, изменить parent/path привязку, обработать неоднозначную историю или
сменить AGS scope — отдельное совместное DB решение. Оно здесь не принято.**
Не предложены migration script, cutover, fresh DB, пересоздание Native courses,
переписывание audit events либо новая compatibility ceremony. Source contract
можно подготовить отдельно, но до разрешения этой точки нельзя обещать живую
сохранность всех historical/pending records или переключать production.

## Честные нерешённые риски и полезная будущая проверка

Текущая deployed composition и фактические Native/Gateway rows неизвестны.
Прежний consumer review нашёл только sparse saved upstream: course path/update
и local UUID uniqueness подтверждены, все варианты question/assessment upsert
при rename/delete/изменении maxima не прочитаны. Поэтому stable parent+UUID —
не автоматическое доказательство сохранения каждого numeric descendant ID.
Переименования/удаления и изменение баллов после submissions не входят в
предлагаемый обычный content update.

Уточнение независимой рецензии: actual Gateway Source901 уже принимает
schemaVersion3/4 (`config.py:108–116`); прежний schema3 gap относился к старому
входу. Его текущий v4 loader по-прежнему требует собственные hashes/files.
Deployed mounts/activity kind/data mapping остаются UNKNOWN: image label не
является подтверждением установленных bytes или разрешением live чтения.
Native sync может частично изменить данные до error/warning: удаление manifest
не создаёт rollback. Workspace seed resume и неразмеченный computed text/cache
freshness не квалифицированы этим пакетом.

Полезная будущая acceptance после отдельного design/review — один disposable
Native course с уже созданными parents/attempt/results/files: применить только
допустимое content edit при тех же UUID и сравнить реальные numeric parents,
attempt/FK/private-file ownership и доступность результата; отдельно прочитать
существующие сохранённые Gateway assignment/pending-outbox fixtures без изменения
их identity. Это **критерий следующей задачи**, не назначенный здесь запуск и
не разрешение DB эксперимента. Hash-only/count-only assertions не заменяют
проверку результата пользователя. Runtime/CI/build бюджеты здесь не открыты.

## Точный Source scope для будущей задачи

| Участок | Прямые paths и поля |
| --- | --- |
| PL identity/output | `prairielearn/_extensions/course-prairielearn/application/export-course.ts:99–119,212–223,756–846`: inventory, stable UUID, files/seed bindings/deliveryHash |
| PL check correlation | `prairielearn/_extensions/course-prairielearn/application/native-check-inventory.ts:130–151`: selectionScopeHash/sourceSnapshotHash/inventoryHash; verification-inventory.ts/native-provenance.ts по уже готовому producer review |
| Platform native payload/binding | `platform/bridge/delivery.mjs`; `platform/bridge/bindings.mjs`; `platform/tools/native-course-payload.mjs`: required hash/files schema и full reads |
| Native bootstrap/update | `platform/tools/bootstrap-native-course.mjs`; `platform/tools/institution-bootstrap.mjs:48–66`; `platform/tools/sync-native-course.mjs:25–60`; `platform/tools/course-sync-policy.mjs`: hash path/insert-vs-existing-parent/sync |
| Verification admission | `platform/tools/delivery_verification.py:88–234`; `platform/tools/grading_job.py:600–648,1490–1505,1593–1625`; `platform/tools/check-course.py:21–55`: whole-package/final receipt coupling |
| Stage readers | `platform/tools/stage_courses.py`, `tools/build-course.py`, `tools/prepare-private-source.py`, `tools/release_assembly.py`: только direct final receipt/package admission из готового pattern review; не новый обход их полного graph |
| Bridge current wire/results | `platform/bridge/server.mjs:74–125,126–230`; `platform/bridge/policy.mjs:16–72,74–149`: binding-by-hash, assignment/version и per-slot semantics |
| Saved native state/history | `platform/bridge/native.sql:2–12,44–93`; `platform/bridge/recognized-assignment.mjs`; `platform/bridge/recognized-credit-native.mjs`; associated recognized SQL: request/audit hash рядом с реальными FK/UUID joins |
| Gateway saved runtime | `gateway/src/gateway/config.py:107–146`; `store.py:18–39,75`; `assignments.py:35–148`; `grades.py:30–168`; `gateway/schema/runtime.sql`: loader, stored hash, lab scope, observations/outbox. Это local Source, не доказательство deployed version |
| Existing upstream evidence | consumer review path `.../operations/20261017-release-deploy/vm-contract/upstream-92584`: course model/sync/course-db/local UUID uniqueness; повторно не скачивался и здесь не перечитывался |

Корневой prefix всех relative paths таблицы:
`/home/tolya/JavaCodex/java-release-20261110/`.

Основания: [producer review](hash-mechanism-producer-review.md),
[consumer review](hash-mechanism-consumer-review.md),
[Platform pattern pass](pattern-pass-platform-20261104.md),
[ordinary producer closure](native-publication-producer-source-20261104.md).
Собственный primary Sol6.1/high admission прежний; Standard назначен, globals не
менялись, hidden routing независимо не удостоверяется. Новых агентов не создано.


## Дополнительный Source trace medium: ordinary startup → explicit update-existing

**Итог: STOP для выдачи малого executable ordinary пакета; закрыта карта, но требуемый изолированный startup contract в текущем Source отсутствует.** Реальные catalog/request ветви без назначения есть. Неверно утверждать, что любой ordinary launch требует persisted assignment row. Однако production startup не отделяет их от общего assignment/outbox worker, а все три чтения поставки — Platform startup, Gateway startup и explicit sync — обязательно требуют current deliveryHash/files. Удаление exporter gate либо одного reader не замыкает этот маршрут. Ни новый ordinary mode/adapter, ни условный bypass shared worker здесь не предложены.

Этот раздел добавлен отдельным medium owner после чтения исходного design/review и только direct producers/callers/readers. Все paths ниже относительно `/home/tolya/JavaCodex/java-release-20261110/`; это current local Source, не установленная production configuration. Gateway901 v3/v4 поддержка подтверждается actual `config.py:112–116`; прежняя schema3 оговорка не переносится. Исторические refs основного текста сохранены.

### Конечная таблица actual producer → обязательный reader

| Producer / вход | Actual branch / следующий reader | Реально потребляемые поля и последняя граница |
| --- | --- | --- |
| PL `application/export-course.ts:756–848` | Native layout + `delivery.json` | Exporter пишет files inventory, schemaVersion3/4, logical courseId, bookRoot, sourceSnapshotHash/inventoryHash/verificationInventoryHash, questions/gradingPayloads, works, instances, deliveryHash. Work содержит id/slots/policy/completion/relatedExercise; instance — works/selfEnrollment. Native info JSON/HTML/resources существуют отдельно, но входят в files. |
| Platform configured settings | `bridge/server.mjs:37–54` → `loadVerifiedBindings(settings.bindings)` | Каждому binding обязательны deliveryPath и numeric-string courseInstanceId; token/runtime настройки отдельные. Вызов verifier без ordinary/catalog branch происходит до DB init и server.listen. |
| PL delivery по configured path | `bridge/bindings.mjs:6–18` → `bridgeDeliverySchema.parse`/`validateBindings` | Всегда полная v3/v4 schema, canonical payload hash и каждый files entry; результат в startup memory. `policy.mjs:168–175` уникальность binding задаётся deliveryHash/instance. Это не native numeric course identity. |
| Gateway image entrypoint | `Dockerfile` → `tools/run-gateway.py:19–25` → `courses.py:38–44` | JSON серверной конфигурации → CoursesConfiguration → factory(course)=create_app с defaults. Course config задаёт deliveryPath/instance/database/registration.activities/flow; kind допускает defense/lab/catalog. CLI не передаёт `start_worker=False` или заранее загруженный delivery. |
| Gateway course startup | `app.py:82–95` → `load_delivery(path, instance)` → `config.py:108–155` | Для любого activity kind обязательны v3/v4 metadata/hashes/gradingPayloads/questions/works/instances/files; canonical hash, file bytes и slots/completion policy проверяются до app. Схема не имеет ordinary облегчённого admission. |
| Gateway production lifespan | `app.py:97–114` → `Assignments.reconcile_all()` → `Grades.poll()` → `drain()` | Worker включён default start_worker=True независимо от catalog. Reconcile читает persisted assignments по instance; poll читает assignments с current deliveryHash, lab bindings и затем outbox. `grades.py:154–173` сверяет assignment/lab/observation/native_attempts delivery и может пометить pending row obsolete. Это последний обязательный background reader данной startup ветви. |
| Catalog student bootstrap | `app.py:191–203` → `PLClient.enroll(uid, instance, delivery)` | При catalog пропускается локальный reconcile_all и lab launch. `pl.py:34–38` отправляет userUid/instance/deliveryHash и требует ровно тот же scoped answer. Persisted assignment для enrollment не требуется. |
| Обычный Bridge enrollment | `server.mjs:431–434` → `enrollBasic` → `context(request,false)` | enrollmentQuerySchema всё ещё требует deliveryHash/instance/userUid; context:99–118 выбирает startup binding по hash+instance, numeric courseInstanceId и UUID infoCourseInstance.json, затем native user. Enrollment/access проверяются Native; assignment resolver/assignment row здесь не вызывается. |
| Catalog work list / launch | `app.py:232–240,263–269` → `Assignments.my_works/launch_work` → `PLClient.work_launch` | Catalog запрещает assignment operations и relatedExercise launch. Но my_works:146–148 сначала безусловно читает current assignment; для work без relatedExercise существование этой row не нужно. Wire передаёт userUid/instance/deliveryHash/workId. |
| Обычный Bridge work launch/results | `server.mjs:240–306,338–345,423–430` → `nativeStudentWorkContext` | context(false), selected instance work membership, Native enrollment/modern access/assessment/attempt и actual native group↔slot mapping. При отсутствии student-label assignment row не читается; при label branch:255–270 assignment/hash/label уже обязательны (workLaunch передаёт allowAssignment=true). У workResults label work отвергается раньше. Path строится из настоящих numeric CI/assessment IDs. |
| Администратор запускает explicit update | `tools/sync-native-course.mjs:14–36` | Отдельный CLI, не HTTP fallback и не автоматический startup callback: `--course-id ID --path PATH`, optional registry/course-key/institutions. До DB нужны full bridgeDeliverySchema, canonical hash/files, no-symlink paths; optional registry требует `/course/key/64hex`. Этот script **не вызывает** readNativeCoursePayload: его собственный reader тоже обязателен. |
| Existing course selection + authorization | `sync-native-course.mjs:43–57` → `authorizeCourseSync(request, bindings)` | selectCourseById(id), previous course.path delivery.courseId/info.uuid; logical courseId должен совпасть. Policy: path canonical `/course/`, configured binding path, basename=deliveryHash, previous info UUID==new info UUID. Verified admin UID/actual native administrator и optional institution parent проверяются отдельно. `courseUuid` здесь передан из previousInfo.uuid, не из DB course.uuid. |
| Authorized update → последний Native reader | `sync-native-course.mjs:57–62` → `syncDiskToSql(course,logger)` | Existing numeric course сохраняется; path может быть изменён явным updateCourseColumn, после чего Native получает course object/path, не deliveryHash аргументом. Script требует complete/no JSON errors-or-warnings, затем читает native CI id/uuid/short_name и печатает courseId/deliveryHash/path/instances. Это Source вызов штатного Native loader, не доказательство всех descendant upsert semantics или rollback. |

### Где ordinary действительно существует, а где заканчивается изоляция

`activity.kind == catalog` — реальная ветвь: bootstrap не reconcile-ит assignment; assignment_session отвергает assignment operations; relatedExercise исключён из catalog UI/launch. Bridge `enrollBasic(context(false))` и work без assignment student-label выбирают реальные Native enrollment/assessment/attempt без requirement существующего assignment row. Нет оснований называть эти request branches managed-only.

Но `create_app:94–108` создаёт общий Assignments/Grades и запускает worker при обычном production factory. Даже catalog-only конфигурация не отключает `reconcile_all` и `poll→drain`. `_catalog_binding` в grades.py:79–80,161,195 пропускает rows, совпавшие с configured catalog resourceLinkId/lineitem; это **row filter после чтения**, не startup branch без persisted history. Source не гарантирует, что existing DB содержит только такие rows. Свежая/пустая DB не допущена как решение. При content edit изменённый current hash участвует в валидности old pending outbox и `_get:35`; поэтому shared background route не позволяет обещать, что history вообще не затрагивается.

Даже игнорируя background работу, catalog wire сохраняет hash identity: `PLClient.enroll/work_launch` → `context(false)` не имеет stable-binding альтернативы. Простой отказ от files walk при сохранении этого request key не равен отвязке runtime identity. Напротив, standalone sync route не читает assignment/outbox/recognition rows, но всегда требует hash/path/schema admission; он не выбирает/создаёт Native course по logical courseId и не запускает bootstrap при ошибке.

### Точная граница данных для следующего решения

Mutable Native content: допустимые title/Native info поля, HTML и публичные ресурсы при прежних UUID/parents и неизменных учебных правилах. Native читает их по course.path. Сам wrapper update не доказывает schema validity до sync и не даёт transactional rollback; он также явно отказывает warnings. Подмена этого поведения новым validator здесь не предложена.

Integration policy: configured numeric CI/course context и разрешённый path; Native info/CI UUID correspondence, user/tenant/enrollment/access; selected works, slots/candidates, assignment label/relatedExercise, completion и native group mapping. Эти поля сейчас смешаны с grading payload и hashes в mandatory delivery schema. Изменение maxima/slots/UUID/seed/назначения не входит в mutable content edit. SourceSnapshot/inventory/verification hash обязательны из-за shared schema даже там, где выбранный ordinary request их содержательно не использует; это факт coupling, не новое требование будущего DTO. Отдельные конкретные filehash transport/grader guards здесь не анализировались и не включены в предложение удаления.

**Следующий шаг — решение root + high reviewer о contract boundary, не product diff:** либо явно выделить production ordinary lifecycle, который не обслуживает managed persisted state, либо сначала согласовать сохранность shared assignment/outbox history при смене content identity. В текущей Source ветви это нельзя выдать за уже существующий безопасный малый пакет. Не предлагаются новые flags/registry/schema, скрытые skips worker, DB migration, fresh DB, новый course/assignment или переписывание старых delivery hashes.

Ограничения evidence: только адресное чтение перечисленных Source и двух исходных reports; live config/student data/DB не читались. Product/review/mainstate/audit не менялись. Compiler/runtime/Native/inspect/render/build/hash/network/tests/commit не выполнялись, подагентов не было. Продолжен прежний own Sol6.1/medium origin `01a1a54c-ab6a-7412-894c-270be9b9aac3`, chat `01a1a54c-ab39-7a83-808d-a190428ff628`; [сохранённый admission](download-owned-request-types-admission-20261104.json). Globals неизменны, Standard назначен; hidden routing не удостоверяется.
