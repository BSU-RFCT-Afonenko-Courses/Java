# Изменяемая Native поставка: независимая рецензия следующей задачи

**Вердикт: DESIGN_ONLY / направление принято, предложенный «минимальный diff» ещё не является ограниченным заданием на реализацию.** Удалять привязку содержания Native курса к его runtime identity разумно. Однако таблица охватывает exporter, grader verification, stage/bootstrap/sync, startup loaders, live wire, assignment transitions, recognition и историю двух БД. Это программа из нескольких разных контрактов, а не один готовый безопасный Source пакет.

Рецензия независимая: автор исходного предложения — другой owner. Использованы готовые producer/consumer hash maps и адресное чтение прямых loaders/sync/assignment callers; не повторялся общий аудит. Собственный исходный Sol6.1/high interval сохранён. Source/DB/commits/state не изменялись; compiler/tests/Native/hash/network не запускались.

## Что в предложении правильно

- Numeric Native course/CI/assessment/question parents и фактические FK — не bytes курса. Тот же UUID под новым numeric course не восстанавливает прежние results, attempts или ownership файлов. Naming hashes для стабильных UUID следует отделять от SHA всего каталога.
- Существующий `sync-native-course.mjs:41–60` выбирает course по явному numeric id и вызывает штатный syncDiskToSql; bootstrap/institution-bootstrap.mjs:48–66 выбирает/создаёт course по path. Подменять update bootstrap-вызовом при смене content path нельзя. Сам sync также умеет менять course.path (:57), поэтому сохранение parent и смена path — разные решения.
- Completion по required slots, assignment labels, choice/native group mapping и access/tenant/user scope — содержательная интеграция, которую нельзя заменить Native average. Content edit не является store_assignment/reassignment.
- Предложение честно не обещает silent migration, не назначает fresh DB и не доказывает сохранность descendants одним UUID. Проверка реального результата пользователя после будущего решения полезнее digest equality. Транспорт конкретных selected grader bytes/ролей не равен immutable всему Native каталогу.

## Нужные поправки к design

**1. Обновить actual Gateway основание.** Абзац о «сохранённом Gateway schema3 loader против PL schema4» использует устаревший вход. В уже имеющемся actual Source901 `gateway/src/gateway/config.py:108–116` load_delivery явно принимает schemaVersion3/4 и вызывает delivery_v4.validate_base/validate_bindings. `delivery_v4.py:24–27` всё ещё требует собственные hashes/files: реальный нынешний вопрос — избыточная byte/content identity, не отсутствие v4 loader. Известные image labels остаются claimed Source, не bytes attestation или разрешением production/DB чтения; deployed mounts/activity kind/data mapping по-прежнему UNKNOWN. Source reference надо назвать exact901, а не переносить старый f9 gap.

**2. Отделить Native content от собственной integration policy и от mutable seed.** Title/HTML/public image при тех же parents — один класс. Смена slots/candidates/assessment membership, maxima, UUID, assignment label, Workspace seed/work/.git — другой. Native schema-valid не означает, что изменение безвредно для уже выполненной работы. И наоборот, наличие собственного metadata file не оправдывает хеширование всех Native файлов. Это в тексте в основном признано; оно должно стать явным пределом первой задачи, а не большой общей оговоркой.

**3. Не смешивать две разные отмены.** Снятие full-output `files` admission и снятие persisted deliveryHash как assignment/lab identity — не одна механическая правка. Platform bindings.mjs:8–16 перечитывает весь inventory при startup; tools/native-course-payload.mjs:23–31 и sync-native-course.mjs:27–37 делают то же до update; Gateway config.py:111–122 повторяет такую проверку. Поэтому request-only или exporter-only изменение не разблокирует Native JSON edits. Но удаление всех hashes/check receipts из grader одновременно с изменением history ещё и расширяет задачу без необходимости.

## Действительно первая ограниченная задача

**Первым брать один end-to-end read/update маршрут существующего ordinary Native курса, с неизменной integration policy; managed assignments/defense/recognition из него исключить.** Задание сначала должно подтвердить по existing Source contract, что выбранный ordinary маршрут не использует assignment/lab/outbox identity. Не выбирать его по предположению о production config: actual activity kind остаётся неизвестным.

Целевой смысл: допустимый title/HTML/resource edit при том же configured numeric course/CI и прежних UUID читается startup loaders и проходит explicit update-existing sync; course/CI не создаются заново, work/slot/points/submission policy не меняется. Canonical path/tenant/UID/access и Native schema/loader сохраняют своих владельцев. Полный exporter продолжает писать Native layout; integration facts имеют одного PL owner, а readers не реконструируют содержание из grading manifest.

Минимальная **граница завершённости** такой задачи — одновременно PL output contract, прямые Platform/Gateway startup readers и existing sync reader. Не весь grader/check/scenario pipeline, AGS recognition или новая общая schema. На дизайн этого маршрута нужен отдельный короткий producer→последний reader список signatures/полей. Если хотя бы один обязательный reader продолжает требовать full-output hash, это только preparatory diff, не завершённая поддержка редактируемого Native JSON. Не выпускать новый producer с заведомо несовместимым reader; не удерживать фиктивный SHA/старый proof ради прохождения schema.

**Сейчас нельзя честно назвать конкретный малый executable diff этого ordinary маршрута полностью установленным:** исходное предложение не отделяет его exact callers от managed route, а actual config неизвестна. Поэтому первый ограниченный deliverable — именно закрытый ordinary-route contract/source plan и его independent review; не новая глобальная матрица и не автоматически разрешённая имплементация всей таблицы. Если выяснится, что обычный запуск тоже обязательно проходит persisted assignment/lab path, эту нарезку не имитировать: сначала решение ниже. Альтернативное косметическое удаление unused hash или одного repeat check не считать решением mutable-delivery задачи.

## Где обязательно совместное решение по данным

Граница наступает **до изменения смысла live deliveryHash lookup/wire/assignment/lab scope**, а не после нового producer deploy. Actual Gateway assignments.py:35,52 отказывается от rows другой delivery; Platform server.mjs:102 выбирает binding по hash, :214 передаёт его в attempt selection. Значит, накопленная история сейчас действительно зависит от этой строки даже при неизменных Native parents.

Совместное решение должно назвать, какое уже сохранённое соответствие даёт однозначные native course/CI/work/user/assignment-version/recognized-AI для existing assignments, lab scopes, audits и pending observations/outbox. Исторические hashes остаются историческими полями, не authority текущих bytes. Если имеющихся FK/полей недостаточно или нужно изменить keys/rows/parent/path/AGS scope, Source не может обещать безопасность без отдельного согласованного data transition. Здесь такое решение не принято; новые migration scripts, reassignment/version bumps, пересоздание курса, переоценка баллов или очистка истории не предлагаются.

Стоимость и runtime preservation UNKNOWN; старые hash IO measurements не дают времени будущего выигрыша. Будущая ограниченная проверка определяется только после закрытого Source контракта и решения по данным; эта рецензия не открывает DB/Native/runtime бюджет и не квалифицирует production.
