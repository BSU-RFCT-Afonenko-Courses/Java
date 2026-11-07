# Аудит hash и identity в consumer/native цепи Platform

## Собственный допуск и граница

Назначение `gpt-6.1-sol/high`, Standard. Собственный chat `01a1a34e-02f6-7021-ba73-02522b0a6a14`, agent `/root/review_download_types`; первичный источник `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T00-26-01-01a1a34e-02f6-7021-ba73-02522b0a6a14.jsonl`, session_meta строка 1. Полный интервал роли сохранён от turn `01a1a34e-032a-7541-b085-5af16bf83f02`, root turn `01a1a338-3b4c-79a1-98e8-f635eca07373`; задача аудита получена в turn `01a1a4ce-fa79-7021-b101-dec4d2ca11d3`, root turn `01a1a4b3-8fd3-7be3-baf7-ce0cc5c60016`. Все turn_context строки 8,105,169,207 имеют согласованные верхние и вложенные пары gpt-6.1-sol/high. Входной допуск PASS. Standard назначен; отдельного первичного поля tier нет. Настройки не менялись.

Source Platform `9b82e29bffd77cc462ffdef23cd3b96ca041db92`. Задача — пересмотр необходимости content hash как обязательной identity по прямому запросу пользователя; прежняя документация immutable рассматривается как описание нашего решения, а не независимое требование Native PrairieLearn. Только чтение исходников и имеющихся свидетельств; DB, production, сеть, пробы, compiler/tests/hash/inspect/render/build не затрагиваются.

## Вывод

**Обязательная content-addressed identity поставки — наше решение Platform/Gateway, а не установленное требование Native PrairieLearn.** Она смешивает три разных задачи: узнавание native course/instance/assessment/question, выбор активного Gateway assignment и контроль bytes при публикации. Их можно разделить. Более широкий Native JSON сам по себе не отменяет полезность проверки bytes на конкретной границе передачи; он отменяет обоснование, будто весь native курс обязан соответствовать нашему closed delivery DTO и жить в каталоге SHA, чтобы Native мог его синхронизировать.

Просто удалить сравнение SHA в bootstrap/sync недостаточно: содержимое hash уже участвует в запросах, сохранённых назначениях, выдаче результатов, outbox и признаниях. Удаление только reader check оставит эти couplings и создаст недостоверное обещание лёгкого обновления. Начинать нужно с сохранения native идентичности и отделения operational binding от content revision, а не с нового receipt framework.

## Источники Native и предел доказательства

Найден уже сохранённый public upstream source `92584fe426ececb84bc2d09de9975c7056c0c5f6` в `/home/tolya/JavaCodex/java-system-practice-work-20261016/operations/20261017-release-deploy/vm-contract/upstream-92584`. Его `fetch.json` содержит exact `raw.githubusercontent.com/PrairieLearn/PrairieLearn/92584…` URLs для прочитанных файлов. Это actual upstream Source, не тестовый adapter. Он совпадает с upstream ref в Platform `schemas/native-upstream/provenance.json` и `tests/fixtures/native-credit-upstream/SOURCE.json`; `images/prairielearn/Dockerfile:1–2` связывает base digest `60c675…` с тем же upstream. Повторного скачивания, проверки hashes или image readback нет. **Точное текущее deployed image/patch composition и реальные строки БД неизвестны.** Файлы в `tests/fixtures/native-credit-upstream` не принимаются за доказательство native implementation; используется только их manifest как указатель provenance.

R0/R4 переиспользованы как карта имеющихся Source и области прежних результатов: `/home/tolya/JavaCodex/java-research-20261103/run-ultra-01a1a144-b034/R0/report.md`, `R4/report.md`. Их вывод о недоступности actual live records сохраняется. Старый `vm-contract/report.md` с рекомендацией нового immutable course — историческое deployment решение; его требования SHA/new course не приняты как нормативная причина сохранять архитектуру.

В указанном upstream непосредственно прочитаны:

| Native факт | Фактический Source | Следствие |
| --- | --- | --- |
| `syncDiskToSql(course, logger)` блокирует course path и передаёт course дальше; reader загружает `loadFullCourse(course.id, course.path)` | `src/sync/syncFromDisk.ts:111–170,286–322` | Delivery SHA не аргумент Native API. Корень курса — путь из native course record. |
| Course создаётся из metadata/path/repository/institution/actor; выбор по path; path разрешён к обновлению у существующего courseId | `src/models/course.ts:283–295,323–360,366–399`; `src/models/course.sql:36–45,228–251` | Каталог не обязан называться hash; смена содержимого не обязана создавать новый numeric course. |
| Native course metadata обновляется `WHERE c.id=$course_id` | `src/sync/fromDisk/courseInfo.sql:1–22` | Identity course — существующий native record, не hash infoCourse/delivery. |
| `infoCourse.json.uuid` исторический и необязательный; для остальных info-файлов требуется UUID; schema validation получает исходный json | `src/sync/course-db.ts:447–492` | Наш запрет смены source course UUID — дополнительный policy. Его нельзя выдавать за обязательную колонку Native courses. |
| UUID questions/course_instances локально уникален внутри course; assessments внутри course instance | `src/migrations/20190712132435_uuids_locally_unique.sql` | Сохранение UUID **и родительского native record** существенно; одинаковые UUID в новом course не означают те же старые результаты. |

`schemas/native-upstream/infoCourse.json:211–212` задаёт собственные required fields и additionalProperties, поэтому Native шире нашего custom bridge DTO, но не принимает произвольный невалидный JSON. Копия native schema и `course-db.ts` подтверждают, что native JSON проверяется своим контрактом. Полные реализации question/assessment sync upsert в найденном sparse upstream Source отсутствуют; точную гарантию сохранения row IDs при всех rename/delete/content/maxima изменениях здесь утверждать нельзя. Сохранение старых rows при конкретном sync остаётся будущей адресной квалификацией, а не поводом вновь вводить SHA identity.

## Обязательные цели и технические допущения

Обязательны по пользовательской задаче: сохранить реальные работы/UUID и корректную связь с пользователем, прежние результаты, submission/raw answers/jobs/private files; поддерживать Native возможности без ненужного ограничения closed JSON; не менять оценки или production в этом аудите. Действующие enrollment/access control и явное различение administrative recognition от нового ответа нужны для правильного результата пользователя. Стабильность этих связей не требует побайтной неизменности всего курса.

Наши технические допущения: `/course/<key>/<deliveryHash>`, обязательные `sourceSnapshotHash/inventoryHash/verificationInventoryHash`, canonical hash всего custom manifest, перечисление и SHA каждого файла, startup freeze parsed manifest, исключительность только schema3/4/три вида binding, hash как route/assignment revision, отказ любой смены hash и отдельная migration ceremony. Их можно пересматривать. Authenticated admin, чужой path/tenant и owner joins — отдельные границы; снятие SHA identity не означает выдачу любых private bytes или перенос чужих баллов.

## Конкретные couplings потребителей

Пути в таблице относительно текущего Platform, кроме явно отмеченного Gateway.

| Участок | Что навязано сейчас | Где Native действительно используется |
| --- | --- | --- |
| Reader `tools/native-course-payload.mjs:22–33` | Raw manifest schema3/4, full canonical hash, basename=hash, candidate refusal, file inventory hash; return raw | До любого Native вызова; это наш admission, не native schema sync. |
| Bootstrap `tools/bootstrap-native-course.mjs:22–30,40–48`; `institution-bootstrap.mjs:48–64` | SHA regex path, exact binding path, lookup existing course **по content path**, new content path может создать другой course | `insertCourse`/`selectOptionalCourseByPath`, institution/user models. Устойчивый course.id не привязан к content bytes. |
| Sync `tools/sync-native-course.mjs:25–35,43–60`; `course-sync-policy.mjs:3–6` | Вторая schema/hash/files цепь, SHA suffix, previous custom source courseId + info.uuid equality, authorized settings path | Выбранный `--course-id`, `updateCourseColumn(path)` того же record, затем `syncDiskToSql(course,logger)`. Нет передачи deliveryHash в Native sync. |
| Bindings `bridge/bindings.mjs:6–18`; `policy.mjs:168–175` | При старте parse+fullhash+каждый filehash; uniqueness по hash/instance; in-memory delivery snapshot | Binding уже содержит native `courseInstanceId`; server сверяет его UUID с native infoCourseInstance (`server.mjs:99–118`). |
| Assignment policy `bridge/policy.mjs:16–72`; `server.mjs:74–83,99–105,126–181` | Request содержит mandatory deliveryHash; hash выбирает binding, включён в replay equality, его смена запрещена даже при той же работе/CI/UID | Native enrollment/student labels, native assessment groups; actual lock key построен из CI ID+UID, не hash. |
| Results/launch `bridge/server.mjs:191–230,234–345`; `native.sql:61–93,109–129,169–199` | Wire results/queries несут hash; assignment hash equality обязателен; recognized attempt требует audit target.deliveryHash | Actual attempt/results выбираются по CI/assessment TID/user/AI/IQ/FK; launch path строится из native numeric CI+assessment IDs. |
| Recognized assignment `bridge/recognized-assignment.mjs:4–18,37–51`; `tools/recognized-assignment-native.mjs:19–31` | Whole request digest включает hash; approved prior/final digest, binding selection и target hash equality | Native context связывает UID+CI+work, labels, selected AI; same native destination не требует новой identity всего курса. |
| Recognized credit `bridge/recognized-credit-native.mjs:11–29,49–70`; `native.sql:74–90` | target.deliveryHash обязателен, входит в mappingDigest/approved record и audit equality | Owner joins — courseId/CI UUID+ID/assessment UUID+ID/AI/user/question UUID+ID/AQ/IQ; courseUUID дополнительно читается из infoCourse как наша policy, в рассмотренный Native model/sync такой UUID не передаётся. |

`loadVerifiedBindings` и sync всё ещё используют **parsed** delivery, в отличие от исправленного raw payload reader. Следовательно, найденное раньше отличие Zod record `__proto__` существует и в этих двух admission путях; «единый fullhash» сейчас даже не означает одну согласованную область bytes. Это подтверждает необходимость разобрать контракт целиком. В этом аудите новое исправление или повторный тест этого случая не выполнялись.

Bindings читаются в память при старте server (`bridge/server.mjs:54`); загрузка не становится native source-of-truth автоматически после редактирования каталога. При выбранном варианте нужно определить, когда перечитывается только нужная operational metadata, а не сохранять незаметно stale snapshot или заново hash весь курс на каждом запросе.

## Где SHA уже сохранён и почему его нельзя просто удалить

Platform PostgreSQL: `bridge/native.sql:2–12` создаёт **нашу** `pl_gateway_assignments`: PK text assignment ID, FK course_instance_id, UID, JSON request, timestamp, recognized AI FK. Сам hash лежит внутри request JSON, не native FK. `store_assignment` (`:44–59`) при обычном изменении сбрасывает recognized AI и обновляет activated_at. Поэтому замена hash механическим обычным reassignment способна скрыть прежний recognized attempt или исключить прежние ordinary attempts по дате. `select_assignment_attempt:61–93` добавляет точное соответствие hash в JSON native audit event для recognized credit. Переписать старые audit events ради нового контракта нельзя: надо сохранить возможность читать их исторический формат по уже связанным native IDs.

`recognized-credit-native.sql:38–79` показывает сохранённые original native source jobs/submissions и наши audit recognized_credit/manual events. Target hash лежит в event JSON и участвует в mapping digest, private-file replay context и idempotency key. Native private files принадлежат AI/IQ/user через actual columns (`:81–110`); их сохранность не зависит от имени папки курса или delivery hash. В `tools/recognized-credit-files.mjs:63` display name строится из sourceReference+file SHA, **не** full deliveryHash. Индивидуальная целостность переносимых bytes — другая задача, её не надо удалять вместе с identity hash курса.

Дополнительно адресно прочитан имеющийся Gateway Source `/home/tolya/JavaCodex/task-publication-20261020-01a1585d/tooling/gateway`, ветка `codex/task-publication-native-semantics-20261020`, сохранённый ref `f9d7e66e0038bf1624486d0a0ece64ac003a0d8d` из `.git/refs/heads/codex/task-publication-native-semantics-20261020`. Это сохранённый local Source, **не подтверждённый current deployed Gateway**. В нём:

- `src/gateway/assignments.py:35,52,71,129,148` сохраняет hash в SQLite assignments.delivery, запрещает изменение и пересылает в bridge; assignment ID/version и context/resource/lineitem — отдельные устойчивые координаты.
- `src/gateway/grades.py:30–39,62–76` сохраняет evidence в observations, delivery в outbox и native_attempts; проверки актуальности привязаны к hash. `:108` включает hash в lab scope ID; `:143–168` подавляет старую outbox выдачу при несовпадающем hash. Изменение только Platform не сохранит доступ к этой истории.
- `src/gateway/store.py:75` требует существующую schema3, а `schema/runtime.sql` определяет эти таблицы. Рекомендация создать fresh DB из старого сообщения об ошибке **не** применяется: пользователь требует сохранности результатов.

Исторический `gateway-c03e` в `vm-contract` дополнительно подтверждает происхождение таких assignment/hash couplings, но поддерживает прежнюю delivery2; он не используется как доказательство текущей schema3/4 совместимости. Фактические значения hashes/rows/jobs/outbox на сервере не читались. Поэтому объём реальной data migration, число legacy rows и pending deliveries — UNKNOWN.

## Два варианта перехода

### Консервативный: отделить стабильный binding от ревизии при сохранении текущего bridge

Стабильная operational identity — уже существующий native courseId/courseInstanceId плюс instance/assessment UUID и UID; native курс обновляется **тем же** course record и родительским CI, не bootstrap нового курса при каждой правке. Path становится разрешённым native source directory, без обязательного SHA suffix. Hash можно оставить как диагностический fingerprint конкретной публикации и существующих старых записей, но он перестаёт выбирать доступ/assignment/grade scope. Verification bytes выполняется, если нужна конкретной передаче/артефакту, а не как необходимая identity каждого runtime consumer.

В существующей settings binding можно использовать устойчивый ключ/явный course-instance mapping, не вводя отдельный receipt registry. Requests/results должны передавать эту координату; версии assignments по-прежнему отражают смысловое переназначение, а не любое изменение файла. Существующий text assignment ID и timestamps сохраняются. Legacy deliveryHash читается как historical revision, не переписывается новым hash и не выдаётся за stable key. Старые PG requests/Gateway SQLite observations/outbox/native_attempts разрешаются через их уже известные assignment/native mappings. Recognition audit старого формата остаётся неизменным; reader принимает его по точным native target IDs/UUID/UID и прежней записи, а не требует, чтобы весь текущий курс снова имел прежний SHA.

Что нужно согласовать предметно: work/slot identity и membership при content change, удаления/rename/maxima, stale request semantics и pending AGS. Косметическая правка текста не должна лишать студента истории. Смена assessment/question UUID, владельца или реально другая работа — содержательная миграция, и её нельзя автоматически считать косметической по факту совпадения stable course key.

Это сохраняет Gateway-managed assignments и per-slot completion, но потребует согласованного изменения Platform wire/schema+Gateway persistence readers и recognized audit compatibility. Он не обещает безопасный production cutover в однофайловом diff.

### Native-first: Native курс/assessment/results являются основным runtime договором

Bootstrap/sync принимают обычный Native source course root и используют существующий native course ID. Native schemas/sync определяют допустимый JSON и набор вопросов/assessments; custom delivery3/4 не является обязательной admission обёрткой для всего курса. Витрина/launch/result привязывается к native CI и assessment ID/UUID/TID, а enrollment/access/варианты/attempts/maxima определяются Native API. Gateway сохраняет Moodle UID/context/lineitem/assignment и отправку AGS, а не свою content-addressed копию всего native курса. Текущее `server.mjs:280–305` уже получает native assessment и проверяет Native access; `workLaunch:345` уже возвращает native путь. Это готовая опора, не необходимость нового runtime/framework.

Небольшая metadata для авторской связи Moodle↔work, «required/optional», essay/labels и иной нашей pedagogy может оставаться обычной author metadata/config. Она не должна заставлять весь более широкий Native JSON удовлетворять `gradingPayloads`/слотам нашего exporter. Если пользователь хочет именно per-slot completion, его нельзя незаметно заменить native score average: правила остаются предметной политикой, допускающей Native groups/selected questions, а не hash identity. Обычное выставление новых оценок и административное признание прежнего успеха сохраняют различие.

Область миграции шире первого варианта: consumers web/Moodle/AGS, Gateway config/readers/polling/assignment API, old per-slot evidence и explicit recognition, exporter compatibility и course staging. Можно оставить старый SHA API только для legacy sessions/records на период перехода, не генерировать новые identity hashes для native-first курса. DB результаты/submissions/files не переносятся и не пересоздаются ради нового content revision; сохраняются тот же native course/CI/assessment/question/AI/IQ. Доказательство exact upsert/delete semantics для целевого image и реальные legacy rows потребуется перед применением, а не в этом Source аудите.

## Следующий ограниченный diff

Предпочтителен первый шаг **без удаления исторических SHA полей**: в существующих Platform settings/binding и context resolver отделить устойчивую native identity от revision, затем согласованно добавить этот адрес в existing Gateway work-launch/work-results/enrollment path. Для начала выбрать ordinary non-assignment work, уже использующий Native Student API (`server.mjs:234–345`): разрешать binding по configured native CI + instance/assessment, сохранять Native access и actual work membership; metadata edit не меняет query/launch identity. Legacy `deliveryHash` route остаётся только compatibility path, не переписывая PG request, SQLite rows или audit JSON. Это конкретный узкий end-to-end contract diff в существующих файлах, без новой таблицы receipts/proof/registry, без пересоздания Native course.

Одновременно убрать SHA directory как обязательное условие **адресной sync-existing-course ветки**, удержав существующий `--course-id` и configured path/tenant. Если эта часть не умещается в ту же маленькую reviewable партию, она идёт следующей после resolver; простое удаление hash reader без изменения runtime binding не называть переходом архитектуры. Bootstrap новой identity, managed assignment/recognition и AGS data migration оставить отдельными следующими дельтами с точной картой legacy fields выше. Сами bytes/native JSON читаются Native sync по своему договору; добавлять поверх новый общий manifest validator не нужно.

Будущие адресные критерии для выбранного diff: одна metadata/content правка при прежних native UUID не меняет launch/result identity; то же native AI/IQ и прежние private files остаются адресуемыми; чужая CI/UID/assessment не становится доступной; historical hash-bound result по старому API читается без переписывания события; смысловой reassignment сохраняет version/replay semantics. Сейчас эти сценарии не запускались и новый full lab/DB capture не выполнялся.

Итог аудита — аргументированный выбор направления и карта миграции, без изменения production. Полная DTO типизация raw delivery по-прежнему не заявляется; она не должна становиться причиной сохранять архитектуру SHA или сужать Native JSON.

Снятие duplicate parse в bootstrap (`tools/bootstrap-native-course.mjs:28`) или объединение трёх fullhash readers может уменьшить служебную работу, но не решает identity couplings. Времена этих потребительских SHA проходов неизвестны; наличие повторного чтения не выдано за измеренный bottleneck. Audit не предлагает новый hash/version compatibility framework: использовать существующие binding/config и native IDs, а legacy fields поддерживать только там, где они уже сохранены.

Перед завершением перечитан полный собственный primary интервал от исходного turn: `turn_context` строки 8,105,169,207, все верхние `model/effort` и вложенные settings gpt-6.1-sol/high согласованы. Исходная точка не сдвинута; итоговый допуск PASS. Новых проб, исполнений или чтения DB/production не было.
