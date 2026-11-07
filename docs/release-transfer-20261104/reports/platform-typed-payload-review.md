# Независимый review типизации native payload reader Platform

## Собственный допуск

Назначение: `gpt-6.1-sol/high`, Standard; повторно использована ранее допущенная независимая роль по прямому назначению координатора. Собственный chat `01a1a34e-02f6-7021-ba73-02522b0a6a14`, agent path `/root/review_download_types`. Первичный источник `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T00-26-01-01a1a34e-02f6-7021-ba73-02522b0a6a14.jsonl`: `session_meta` строка 1 подтверждает собственный id/path. Начало всей роли сохранено: исходный turn `01a1a34e-032a-7541-b085-5af16bf83f02`, root turn `01a1a338-3b4c-79a1-98e8-f635eca07373`; текущая задача Platform получена в turn `01a1a4be-ac30-73b2-acb3-716a5fb28d42`, root turn `01a1a4b3-8fd3-7be3-baf7-ce0cc5c60016`. Весь интервал содержит `turn_context` строки 8 и 105: обе верхние пары `model=gpt-6.1-sol`, `effort=high` и обе вложенные `collaboration_mode.settings.model/reasoning_effort=gpt-6.1-sol/high` совпадают. Входной допуск PASS. Standard назначен; отдельное primary поле tier отсутствует, скрытая маршрутизация не утверждается. Глобальные настройки не менялись.

Первоначальная область: один файл `tools/native-course-payload.mjs`, diff `b37febb6e46da681b16b27eadb676e29b764130b..9227a111701edf6dbc37d97253e561a5f2c9dcd7`. Актуальная коррекция и итоговый вердикт приведены непосредственно ниже. Compiler/tests/network/hash/render и повторы проверок исключены заданием на обоих этапах review.

## Итоговый вердикт после коррекции 9b82e29

**APPROVE в границе частичной типизации reader. P2 устранён.** Проверен двухфайловый fix `9227a111701edf6dbc37d97253e561a5f2c9dcd7..9b82e29bffd77cc462ffdef23cd3b96ca041db92` и итоговый production diff относительно `b37febb6e46da681b16b27eadb676e29b764130b`. Исправляемых замечаний к итоговому изменению не осталось. Полный DTO delivery и статическая проверка downstream consumer chain этим результатом не заявляются.

Новый turn re-review `01a1a4c5-57ba-7342-8389-e745d68c22a1`, root turn `01a1a4b3-8fd3-7be3-baf7-ce0cc5c60016`; полный собственный primary интервал по-прежнему начинается в `01a1a34e-032a-7541-b085-5af16bf83f02`. В него входят `turn_context` строки 8, 105, 169; все верхние и вложенные пары gpt-6.1-sol/high совпадают. Собственный допуск re-review PASS, настройки не менялись.

`tools/native-course-payload.mjs:21–32` снова передаёт raw JSON в прежнюю schema validation, но использует именно raw object для canonical hash, candidate/files loop и return. Parsed object больше не подменяет данные. Это восстанавливает обе стороны найденной границы: inventory с own `__proto__` и исходным hash может пройти с реальным файлом; очищенный hash при raw inventory отвергается до чтения отсутствующего файла. Порядок проверок и file SHA256 operands исходного reader возвращены. Новые guards, validators, hash passes или запреты имени файла не введены.

`BridgeDelivery` typedef и завышенный return contract удалены; сохранены только `@ts-check`, string параметры и `info:unknown`. Итоговый тип `delivery` остаётся any вследствие JSON.parse, как прямо описано в комментарии. Это честный незакрытый долг. Прежние negative cases hash42/schemaVersion5 больше не используются как свидетельство текущей типизации.

Новый existing-suite regression (`tests/bridge/native-course-payload.test.mjs:38–57`) создаёт настоящий own key через computed property, реальный файл `__proto__` и raw hash. Первый assert сопоставляет `Object.entries` возвращаемого inventory с исходным: исчезновение специального ключа обнаруживается. Второй использует тот же raw inventory, очищенный hash и отсутствующий файл `__proto__`, требуя именно `Delivery hash mismatch`. По исходному fixture cleaned hash действительно совпадает с уже существующим каталогом, поэтому удаление лишнего mkdir устраняет только EEXIST подготовки. Нового недоказанного предположения о semantics parser тест не вводит.

Прочитаны обновлённый owner report и временные `check.cjs`/`negative.mts`: compiler теперь ожидает ровно TS2345 для path42 и TS18046 для unknown info, без фиктивного DTO или подавлений. По отчёту один affected compiler process дал ноль reader diagnostics и эти два expected negative cases; один новый именованный regression завершился PASS1 после отдельно зафиксированного setup EEXIST. Старые четыре runtime cases не повторялись и не выдаются за проверку нового fix. Review не запускал compiler/tests/сеть/hash/render и не менял Source/state.

## Первоначальный вердикт по 9227a11 (исправлен 9b82e29)

**Нужна коррекция либо явное принятие изменения поведения: одно замечание P2.** Типизация reader честная, но утверждение о полном сохранении прежних hashes/files/refusals неверно для одного реально допускавшегося JSON-ключа. В пределах запрета запусков вывод получен чтением точного diff, фактической схемы и actual Zod4.6.7 implementation; новый runtime scenario не выполнялся.

## [P2] Смена raw payload на parsed payload меняет hash и inventory для `__proto__`

Место изменения: `tools/native-course-payload.mjs:28–33`, прежде всего новое присваивание parse result в строке 28 и вычисление hash из него в строках 29–30.

Существующее `files` — `z.record(z.string(), hashSchema)` (`bridge/delivery.mjs:19`). Actual использованная Zod4.6.7 в `/tmp/platform-typed-payload-pmt1pbq1/zod/package/v4/core/schemas.js:1811–1813` пропускает own key `__proto__` при разборе record; результат не содержит этой записи. Это встроенное защитное поведение parser, несмотря на отсутствие явных `.strip()`, `.transform()` или coercion в авторской схеме. JSON.parse сохраняет own property с таким именем. Предварительный guard требует лишь допустимую версию, files object и own `infoCourse.json`; `containedFile` (`tools/native-course-payload.mjs:19–22`) допускает обычное имя файла `__proto__`.

Конкретный вход: прежний валидный v3 manifest с `files`, содержащим существующие `infoCourse.json` и обычный файл `__proto__`, обе записи — SHA256 фактических bytes. `deliveryHash` и basename относятся к canonical исходному payload, содержащему обе записи. Остальной manifest можно взять из существующего valid immutable fixture (`tests/bridge/native-course-payload.test.mjs:18`); inventory refinement (`bridge/delivery.mjs:21–25`) не запрещает дополнительное имя файла. Раньше parse успешно проверял manifest и его очищенный результат игнорировался; hash считался по raw payload с обеими записями, затем проверялись bytes обоих файлов. Теперь parse result теряет `__proto__`, поэтому тот же корректный hash не совпадает и возникает `Delivery hash mismatch`. Это новый отказ ранее принимавшегося immutable payload.

Обратный эффект той же границы: если raw delivery содержит эту дополнительную запись, но hash вычислен по DTO без неё, новая версия принимает очищенный DTO и не читает соответствующий файл, тогда как прежняя отвергала raw delivery по несовпадающему hash. Это не утверждение о выполненном обходе downstream authority или эксплуатации: речь о конкретном изменении того, какие JSON-данные связываются hash и files loop данного reader.

Нужно согласовать сохранение прежней области hash/file validation с требованием использовать parsed DTO либо явно принять изменение контракта. Молчаливо объявлять партию исключительно типовой с неизменными refusals нельзя. Исправление продукта этим review не выполняется; новый validator, cast или тестовый запуск не предлагается как автоматически разрешённый шаг.

## Остальной diff

`@ts-check`, JSDoc `BridgeDelivery=ReturnType<typeof bridgeDeliverySchema.parse>` и аннотация return (`tools/native-course-payload.mjs:1,8,12–13`) выводят контракт из фактической runtime schema. Ручного DTO, assertion/cast, suppression или модификации schema нет. `info: unknown` соответствует отсутствию validation metadata в reader, downstream bootstrap остаётся отдельной границей. Raw JSON до parse остаётся any и используется только прежним предварительным guard; это явно ограниченный остаток.

`bridge/delivery.mjs:40–42` сохраняет union3/4 и project4 passthrough. У project4 статически доказан `kind: 'project'`, а extension fields остаются unknown; refinement не превращает их в конкретный gradingBinding interface. Текущий diff не создаёт такого ложного интерфейса. Для обычных допустимых JSON-ключей strict schema/refinements не преобразуют значения; canonicalJSONString сортирует ключи (`bridge/policy.mjs:149–165`), так что обычная перестановка свойств вследствие parse hash не меняет. Найденное исключение связано именно с фактическим parser behavior на специальном ключе.

Явный `.toString('utf8')` перед JSON.parse сохраняет прежнее преобразование Buffer в UTF-8 string; файловые read/hash bytes не перекодируются. Чтение metadata до guard, symlink/path checks, candidate refusal и порядок проверок не менялись. Новый parse не добавлен: существующий вызов теперь возвращает используемый объект. Дополнительного filesystem scan или child процесса в production нет.

## Свидетельства и пределы

Прочитаны `platform/AGENTS.md`, локальное общее русское языковое правило Core (в предыдущей части той же роли), `docs/native-delivery4-implementation.md`, отчёт владельца, точный однофайловый diff, reader, actual schema/canonical writer, существующие четыре reader scenarios и потребитель `tools/bootstrap-native-course.mjs`. `platform/spec/index.md` отсутствует; AGENTS такого пути не требует.

Временные `tsconfig.json`, `check.cjs`, `negative.mts`, Zod package metadata/declarations и resolver прочитаны в `/tmp/platform-typed-payload-pmt1pbq1`. Compiler API использует реальный TypeScript5.9.3, actual reader/schema import и cached Zod4.6.7 declarations, `strict`, `allowJs`, `checkJs:false`, `noEmit`, actual Node types. Никакой mock schema или fixture interface в типовой composition не обнаружен. Три negative cases непосредственно используют возвращаемый тип reader: hash42, schemaVersion5, доступ к unknown info. Временный resolver только направляет bare `zod` в извлечённый actual package. По сохранённому отчёту compiler дал ноль reader diagnostics и три ожидаемых отрицательных diagnostics; прямой Node запуск дал четыре именованных existing reader сценария PASS. Предыдущий file-level Node --test результат обоснованно не засчитан как приёмка этих сценариев.

Четыре сценария проверяют обычный v3 manifest, tamper, symlink и candidate. Они не покрывают описанную выше особенность Zod record. Compiler success также не доказывает сохранение этой семантики. Review не запускал compiler/tests/сеть/hash/render и не менял Source/state/pins; service/DB/downstream JS и глобальный CI не проверены. Типовую часть можно принять отдельно, но текущее однофайловое изменение целиком требует разрешить замечание о поведении.

Перед первоначальным итогом повторно прочитан полный собственный primary интервал от прежнего исходного turn: на тот момент `turn_context` только строки 8 и 105, обе верхние и вложенные пары gpt-6.1-sol/high согласованы. Начало роли не сдвинуто. Собственный допуск первоначального review PASS; более поздний re-review включает также строку 169, как указано выше.
