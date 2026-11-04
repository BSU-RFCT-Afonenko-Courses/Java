# Platform/Gateway actual901: условный первый native binding пакет

4 ноября 2026. Source-only продолжение [первого bounded scope](platform-native-binding-next-20261104.md). Старый Source f9 больше не принят за actual Gateway. Код, конфигурация, state, БД, Native records, student files и outbox не менялись. Не запускались SSH/DB/config reads этим owner, tests/compiler/build/render/inspect/hash/install/commit. Root получил image metadata отдельно; повторного clone/fetch мной не было.

## Новые входные данные и предел их силы

| Evidence | Что установлено | Чего не установлено |
| --- | --- | --- |
| Gateway image metadata, получено root read-only | Claimed Source `901624527dce2d37a62fedaa9b768cbbbfd1e366`, repo `moodle-prairielearn-gateway`; repoDigest `sha256:b17df2f88025cec7362857839a855f5133d3267e5875e168af3fe129ddac965a`; imageID `eb52262e498c064729c38c0a5c93583128a01b76f6eb5f13281412bab0c02703` | Labels не attestation соответствия запущенных bytes Source; effective config/mount composition ещё не прочитана |
| Exact local Gateway Source | `/home/tolya/JavaCodex/java-release-20261110/gateway`, HEAD901, tracked tree clean; AGENTS прочитан; version2.2.0 | Не проверялось, какие activity kinds реально включены в каждом курсе VM |
| PL/Platform image metadata, root | Claimed Source `845a7e4b35cf3b14988b9b09b986c2b434afbb1b`; repoDigest `community-gateway@sha256:640ea0d23ecd299d4cccac358b95b02e006485cefdde39d4436d0282e4554307` | Не bytes attestation и не DB mapping |
| Stable Platform Source | `9b82e29bffd77cc462ffdef23cd3b96ca041db92`. Локальный diff845→9b по bridge/sync/policy/config/compose не содержит runtime изменений; в schemas только5строк check-result schema | Не подтверждены actual Native parent numeric IDs, mounted source paths и отсутствие overlays в runtime |

Sanitized config/mount query root **отклонён automatic approval review** как раскрывающий internal paths/config keys/delivery/work identities без дополнительного согласия. Root запросил его у пользователя. Этот owner query не повторял и не обходил. Это ограничение доступа к evidence, а не доказательство технической несовместимости; config/activity kinds и parent binding пока **UNKNOWN**.

## Что901 меняет в прежнем scope

1. **Старого schema3 gap здесь нет.** `gateway/config.py:108–117` принимает3/4; `delivery_v4.py` строго проверяет project/native-answer/workspace. Нельзя переносить прежний вывод f9 о несовместимости PL4 на current901.
2. **Появился существующий Native-friendly маршрут `kind:catalog`.** `app.py:197–203,230–269`: одноразовый signed LTI/handoff, enrollment, «Мои работы», запуск обычной работы; catalog не reconcile защиты при bootstrap, не даёт assignment operations/roster, не допускает defense work. `grades.py:79…/192…` и `_catalog_binding` исключают catalog из новых score observations/публикации. Это настоящий уже поддерживаемый продуктовый маршрут, не новый protocol для аудита.
3. **Catalog всё ещё зависит от full SHA при startup.** `app.py:84` безусловно зовёт full `load_delivery`; `config.py:112–121` требует root hashes/grading/files и проверяет все bytes. Platform `server.mjs:54` до request вызывает full `loadVerifiedBindings`. Один новый CI query не убирает эти gates.
4. **Даже all-catalog worker не является автоматически hash-free.** `app.py:94–108` создаёт Assignments/Grades и запускает reconcile/poll; `grades.py:193` читает `deliveryHash` ещё **до** catalog row filter. Оставить worker с narrow catalog view без SHA — значит получить KeyError/неправильный lifecycle, а не сохранить compatibility. Нельзя закрыть дыру fake hash/no-op grading facade.

Следовательно, прежнее «минимум11modules всегда» нужно уточнить: оно относится к изменению **ordinary lab/managed evidence identity с AGS/history**, но не обязательно к all-catalog entry/launch без новых результатов Gateway. До actual activity kinds применимы две разные ветви.

## Ветвь A: если конкретный named course действительно all-catalog

**Кандидат на первый полезный пакет — stable native binding для существующего catalog enrollment/launch + тот же Native sync того же parent record.** Это не промежуточный API ordinary results. Native results/AGS wire не расширяется; managed/recognized ветки остаются legacy. Тот же catalog UI/порядок обычных works, роли и handoff сохраняются; не предлагается незаметный redirect вместо «Мои работы» в другую Native страницу.

Граница: trusted per-course configuration выбирает прежний Native CI, существующий instance alias и разрешённые обычные work IDs → signed LTI UID → enrollment/access → существующий Native assessment URL. Коллекция works берётся из **потребляемого operational view существующих metadata**, а не из полного delivery proof и не из автоматического перечисления всех Native assessments. Отсутствие нужных metadata/чужой work/managed marker по-прежнему даёт отказ. Добавлять каталог всех новых assessments без явного author scope нельзя.

| Потребитель catalog | Что ему нужно | Что ему не нужно |
| --- | --- | --- |
| Startup/My works | existing instance work membership; `work.id`, `relatedExercise`, policy managed marker; существующий порядок `delivery.works` после membership filter | gradingPayloads/responseSlots/workspace seed, весь bank UUID inventory, files SHA, source/inventory/verification/delivery hash; per-slot threshold не рассчитывается для списка |
| Enrollment | configured Native CI ID, verified UID; joined/blocked и Native modern instance access | filesystem infoCourseInstance byte identity, whole delivery hash |
| Work launch | configured ordinary membership; existing teacher/Student separation; Native individual assessment + modern student/attempt access | Число выполненных required slots и передача оценки в AGS. Это launch, а не completion decision |
| Historical assignments/results/outbox | Существующие records хранятся без переписывания; у catalog нет полномочия их продолжать/пересчитывать | Не нужен новый Native event scope, новая SHA-подмена в `delivery TEXT`, migration или новое grade observation |
| Sync | Авторизованный администратор; прежний Native course record, его CI parent и разрешённый source path; собственный Native schema sync | Каталог, имя которого обязано совпадать с content digest; full manifest/files proof как предварительный пропуск |

В native catalog branch не следует повторно требовать full `work.slots/completion` только ради launch. Native access остаётся обязательным; прежние legacy `validateWork/validateNativeGroups/assignedSlots/completion` обслуживают results и остаются в своих ветках. Это не признание двух scoring моделей эквивалентными: catalog **вообще не принимает score decision**. Если first diff одновременно должен поддержать Gateway lab result/AGS — это уже ветвь B ниже.

### Условный конкретный Source scope A

**Platform6 production modules:**

- `bridge/delivery.mjs` — выделить из существующих definitions потребляемую catalog operational projection; existing JSON-read `.parse` остаётся границей unknown→точный DTO. Не full legacy parse с выдуманными required hash fields. Legacy3/4 schemas не ослаблять.
- `bridge/bindings.mjs` — startup различает configured native catalog entry и legacy entry **до** full legacy verifier. Native CI/membership view не проходит canonical/filehash loops. Старый `loadVerifiedBindings` остаётся для managed/recognized и legacy ordinary callers.
- `bridge/policy.mjs` — existing binding ambiguity/key relation и ordinary/managed classification переиспользовать для native entry; не менять assignment transition, groups/results/completion. Новой registry/schema family не вводить.
- `bridge/server.mjs` — existing enrollment/work-launch routes получают постоянную native-identity alternative по configured CI/UID/work; Native CI берётся только из trusted allowlist, same Native course/assessment/user joins/access. Full loader и повторный FS infoCourseInstance read не являются admission нового branch. Native work-results endpoint пока **не добавляется**. Bearer/CSRF/teacher role не расширять.
- `tools/sync-native-course.mjs`, `tools/course-sync-policy.mjs` — existing administrator sync получает native-bound authorization без full delivery/hash/files/basename gate, продолжая вызывать `syncDiskToSql` того же `course.id`. Authorization связывает source path с реальным configured Native parent, а не только со строкой path. Legacy branch сохраняет старый контракт. Bootstrap/create course не вызывать.

**Gateway3 production modules:**

- `src/gateway/config.py` — existing closed per-course Configuration различает configured native CI catalog binding и legacy delivery binding. Native branch применим **только если все activities этого named course catalog**; mixed lab/defense не переводится туда автоматически. `deliveryPath` можно сохранить как existing local operational metadata location, не требуя его SHA identity; named-course path isolation `courses.py` тогда не надо ломать. Metadata read использует лишь перечисленные выше поля, без `delivery_v4.validate_base/bindings` и file hashes; legacy full reader остаётся неизменным.
- `src/gateway/pl.py` — existing enrollment/work_launch methods посылают/проверяют native identity в той же private Bridge API. Legacy calls и work_results остаются прежними. Это согласованный producer/consumer diff, а не permissive optional deliveryHash в старом DTO.
- `src/gateway/app.py` — native all-catalog lifecycle использует readonly operational work view, existing session/UID/role checks и тот же handoff/UI. Не создаёт legacy Assignments/Grades/worker для этой ветви: assignment endpoints уже закрыты catalog policy, а никакого нового результата Gateway она не производит. Legacy/mixed lifecycle и worker других named courses сохраняются. Не прятать недостающий hash за фиктивным объектом.

Companion contracts: существующие enrollment и work-launch wire schemas получают явные identity alternatives, existing settings/examples/docs описывают native binding. **Work-results schema/grades.py/assignments.py/SQLite schema не менять в A.** `courses.py` не требует runtime изменения, если сохраняется existing distinct operational metadata path каждого named course; если implementation всё-таки меняет этот config field, его path-isolation consumer придётся включить явно, а не считать free. Raw JSON до existing parse — unknown, после — конкретные NativeCatalogBinding/CatalogWork view DTO, без `any` facade/двойных casts/нового общего validator.

Это **9production modules +2wire contracts +config/docs**, ограниченная одна вертикаль. Здесь нет миграции score history и временного native-results API. Но scope пока **условный**, не GO к реализации: условия ниже ещё не установлены runtime evidence.

### Реальный выигрыш A и обязательные условия

Сейчас harmless Native metadata/resource edit может блокировать Gateway/Bridge startup и own sync, хотя Native IDs/access/work scope прежние. В A такой edit не требует нового SHA request, нового numeric course/CI и новых Gateway grade scopes: signed пользователь открывает ту же обычную работу и видит её актуальное Native содержимое. При changed membership/managed role/access native branch по-прежнему отказывает, пока author scope не согласован. Native JSON должен быть валиден Native schema, не произвольным JSON.

Условия до кода:

1. Actual named course **all-catalog**, а не lab/defense/mixed. Это нельзя заключить из наличия catalog в901 или из названия Moodle activity.
2. Configured native CI связан с прежним course/CI parent. Сохранить UUID/TID при создании нового numeric course недостаточно; recreate запрещён. В первом пакете ограничить Native updates metadata/resources при прежних UUID/slots/scoring, не заявлять универсальные rename/delete гарантии неполного upstream evidence.
3. Native mutable source path и сохранённые legacy artifacts не подменяют друг друга. Если mixed Platform config проверяет legacy entry на **тот же** редактируемый folder, eager legacy verifier всё равно заблокирует startup. Старый snapshot нельзя «починить» переписанным hash, равно как отключить его verifier только ради нового catalog. Подходящий actual mount/config path пока неизвестен.
4. Есть разрешённый update/publish route именно в current Native `course.path`. Container read-only mount можно и нужно сохранить; host admin source update/Native sync — другая граница. Если единственный accepted route — current `stage_courses.py` с whole exact receipt/provenance, этот workflow тоже остаётся gate: A9 недостаточно. Тогда включить existing stage consumer в end-to-end diff либо выбрать уже существующий Native source-update route. Новый временный publisher/receipt не создавать.

До подтверждения3/4 нельзя объявлять9files точным достаточным deployment scope. Численного ускорения не заявлено; exclusive cost UNKNOWN. Удаление cold whole-file reads — Source установленная работа, не новый benchmark.

## Ветвь B: если actual course имеет lab/defense или mixed activities

901 по-прежнему связывает persistent grade pipeline с delivery SHA. `Grades._lab_id`, `_bind_attempt`, `_record`, `_current_lab`, `poll/drain` используют его в scope, native_attempts, observations/outbox currentness; Assignments используют его для version/reconciliation. Для этой ветви предыдущие6Platform+5Gateway modules остаются **нижним пределом**, а не готовым plan. Current schema3/4 support устраняет compatibility gap, но не эти couplings.

Нельзя выключить общий worker ради одного catalog activity в mixed course, иначе перестанут доставляться законные lab/defense results. Нельзя хранить новый CI ID в old `delivery` column без явного backward read/write contract, обнулять deliveryHash или переписать старые rows. Root требует сохранить данные и не выбирать scoring заново; actual config/history mapping пока неизвестны. В B независимо полезный маленький Native-binding diff **не доказан**: request-only/bridge-only не снимают startup/sync/history gates. Новой схемы/registry для обхода этого вывода не предлагается.

Scoring в actual901: `spec/results.md`, `grades.py:110–145` — ordinary lab AGS сохраняет **Native weighted attempt points/max_points**, проверяя per-slot completion; managed defense отправляет completedRequired/requiredCount. Catalog отправляет **ничего**, в том числе прежний catalog pending outbox не становится новой оценкой. Эти три маршрута нельзя слить под «Native average». A оставляет lab/defense вообще вне нового branch, B требует отдельно сохранить каждый смысл. Administrative recognition также остаётся legacy Native-bound audited flow.

## Какой точный вход ещё нужен от root

Один одобренный sanitized structural read, без credentials/env/token/личных UID/результатов:

- Для обоих Gateway named courses — реальные activity kinds и optional workId; фактический `instance`, operational delivery location и config shape.
- Platform binding entries: Native CI ID, delivery source location, типы entry; correspondence с Native source mount. Native student rows для этого анализа не нужны.
- Mount origin/destination/RW для Native course source и Gateway/Bridge delivery: один ли это folder, сохранён ли legacy snapshot отдельно; effective startup config path. Не содержимое ключей/БД.
- Какой existing controlled Source-update/sync route используется для этого mount; необходимость stage exact-package branch. Одна лишь image label этого не показывает.

Root уже запросил согласие после auto-review rejection; повторный SSH/config read мной не предпринимался. После входа выбор простой: **A при доказанных условиях — один содержательный catalog E2E Source package; B — не обещать малый diff и сначала определить compatible persistent grade contract без изменения scores/history.** До этого Source не редактировать. Наличие901 позволяет конкретизировать выбор, но не угадать runtime configuration.

## Собственный допуск

Primary chat `01a1a25f-b098-7242-bf6d-d0e8f6517361`, первоначальный turn `01a1a25f-b0fb-7fd2-b244-83933af77834` → текущий `01a1a51f-5439-7302-b0fb-e144bd5b16aa`; все14 turn_context top/nested `gpt-6.1-sol/high`, line8 →line2203. Standard назначен; скрытый service tier не доказывается этим логом. Globals/настройки не менялись, полный environment/secrets не печатались.

Завершён Source compatibility/viability разбор actual901 и conditional catalog plan. Это не runtime attestation, не accepted production config, не implemented diff и не green integration. Source/state/DB не менялись.
