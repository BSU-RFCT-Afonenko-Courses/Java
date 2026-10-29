# Platform: строгий допуск, отложенный допуск и долг проверки

Аудит R03/T11 статический. Фактические человеческие решения и время выпуска берутся из `results/sections/release-history.md`, где R01 прочитал оригиналы. Этот аудит не исполнял validators, admission, staging, server import и не продлевал policy. Semantic operation records — `results/sections/platform-operations.json`.

## Что принимает строгая граница

`stage_courses.validate_checkout` требует contained real Git metadata, exact clean HEAD/origin, provenance, recorded files и отсутствие лишнего payload. V2 принимает только внешний assembly Context. `validate_release_receipt` проверяет outer delivery receipt, его identity и package integrity; для automatic/mixed обязательно `automatic.state=verified` и inner complete successful **container/delivery** receipt. Проверяются exact scenario inventory, observed+legitimate optional-reference absence, все matched expectations, question/runtime/image/dependency binding, actual descriptor и native payload hashes. Host receipt, readiness, inventory, пустой/частичный набор и local image candidate строгий gate не удовлетворяют.

Wholly manual пакет использует `not-applicable / all-questions-manual`, а не invented automatic success. `package_integrity` сверяет canonical delivery hash, actual schemas/files/UUIDs, assessment slots/choice/random/fixed, manual/mixed components и submission controls. Он не исполняет reference и не доказывает корректность решения, feedback privacy или человеческую оценку. Runtime receipt также не подтверждает signed Moodle login и final completion.

Есть узкое доказанное повторение чистой проверки: `_validate_automatic_release_receipt` сразу после `validate_receipt_integrity(receipt)` повторяет тот же `identityHash == digest(receipt without identityHash)` (`grading_job.py:465–466`, первоначальная проверка`:431`). Между ними нет изменения receipt или внешнего процесса. При обычном однопоточном владении DTO это одинаковый subject/interval/result, и вторая hash-проверка не добавляет гарантии. Это не основание удалить раздельные source/runtime/native checks вокруг внешнего исполнения.

## Что разрешает exceptional route

`load_admission_policy` (`tools/stage_courses.py:33`) принимает только пару absolute policy path+SHA256; regular nonsymlink single-link file≤65536B; closed exact fields и конкретные `package-integrity-user-deferred`, `DEFERRED_BY_USER`, `NOT_VERIFIED`. Срок — `now < expiresAt ≤ now+86400`. Hashes failed report/human/privacy обязаны иметь форму64hex; policy bytes проверяются относительно внешнего SHA. **Сам validator не открывает failed/human/privacy документы и не проверяет их смысл или авторство.** Их доверие возникает из release authority и отдельно прочитанного принятого материала R01, не из факта наличия hash-полей. Закрытый отчёт не публикует исходные private paths.

`deferred_admission` требует original assembly/v2 provenance, не candidate и **отсутствие verification-receipt.json**. Policy связывает route ID, exact repository/Native commit, course ID, единственный instance, builder/images hash, delivery/source/inventory/verification inventory и question/work counts. Затем проверяет package integrity и digest всего native payload с bytes/modes и recheck assembly. Возвращаемый статус остаётся NOT_VERIFIED. В policy есть сведения о qualification debt, но нет исполнения сценариев.

Policy перечитывается в начале stage_registry и каждого validate_checkout; expiration проверяется в моменты чтения. Внутри deferred_admission повторной проверки времени перед publish нет. Значит это admission-at-entry contract, а не доказанное условие «каждый последний FS шаг завершён до deadline». При будущем пересмотре границы надо прямо выбрать это значение, а не молча считать истечение временем rollback. Existing checkout также проходит validator: повторный stage после expiry не получает старого исключения автоматически.

## Исторический итог и применимость fixes

Последний Java Native321 содержал128 questions/97 works, 118 auto/mixed и10 manual; stock prepare436 PASS не исполнял student/reference behavior. Итоговая qualification остановилась на step10 до сценариев:0/672 executed, `Exact canonical instance work catalog required`. Runtime2.4.0 validator допускал только letter-leading instance; host 2.4.1 исправляет его на digit-leading `[a-z0-9][a-z0-9-]*`. Current code это исправление содержит. Исторический отказ нельзя объявлять текущим дефектом host2.4.1; наличие fix не превращает прежние0/672 в PASS.

User-deferred решение действительно было дано на существующий пакет; policy истекла26октября19:00UTC. Поздняя execution lease не продлевала admission. Runtime grading было оставлено включённым, но Java qualification остаётся **DEFERRED_BY_USER / NOT_VERIFIED**;34 известных upload limitations и actual feedback/Moodle/student UI открыты. System7 wholly manual прошёл отдельный strict package/N/A route. Нынешняя работа не replay этого согласия.

## Конкретное закрытие долга без нового workflow engine

1. Зафиксировать новый exact tuple и debt report для каждого затронутого question; сохранить прежний failed report и NOT_VERIFIED disclosure. Если source/Native/reference/runtime меняется, прежние bindings не переименовываются в current evidence.
2. До export проверить actual producer DTO/host decoder на маленьком Native fixture: instance grammar, descriptor fields, optional union, API public suite и declared scenarios. Это закрывает известные причины отказа без повторного full-bank render.
3. Сначала выполнить уже подготовленный failed qualification шаг на актуальном, явно выбранном tuple; сценарии с project/API errors исправлять адресно. Для полного strict receipt всё объявленное множество должно иметь наблюдение: untouched scenario results можно переиспользовать только после доказанной эквивалентности их source/tests/runtime closure. Текущий checker такой resume API не предоставляет; его нельзя имитировать склейкой JSON.
4. После полного успешного authoritative receipt отдельно принять решение о новом admission/import. При неуспехе сохранить infrastructure/defect статус, affected IDs, input bindings и diagnostic; повторять утратившую основание фазу, не старое разрешение. Сбой не порождает student zero и не закрывает required/manual slot.
5. По отдельно разрешённой real student сессии проверить Moodle launch, редактор/загрузку, сохранение ответа, повторную сдачу, public feedback, best result, choice/manual/mixed points и completion. Статический privacy review и HTTP200 это не заменяют.

Expiry закрывает право нового исключения; он сам по себе не предписывает удалить уже импортированный курс. Текущая доступность и допустимость дальнейшей эксплуатации требуют решения владельца на свежем evidence. Новый статусный файл может хранить debt и exact tuple в существующем формате release records; новый orchestration engine здесь не нужен.
