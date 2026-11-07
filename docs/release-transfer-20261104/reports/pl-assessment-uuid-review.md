# Независимый обзор optional UUID assessment

## Собственный допуск

Назначение: Sol6.1/high, Standard. Собственный чат `01a1a1d9-8269-7540-82b6-73579e4118b3`, путь агента `/root/review_assessment_uuid`, исходный ход `01a1a1d9-829a-7ec3-99b9-4a60e616af43`.
Первичный источник: `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T17-39-08-01a1a1d9-8269-7540-82b6-73579e4118b3.jsonl`.
`session_meta` (строка 1) связывает собственный ID с путём агента; `turn_context` (строка 8) содержит `model=gpt-6.1-sol`, `effort=high`; вложенный `collaboration_mode.settings` согласован: `model=gpt-6.1-sol`, `reasoning_effort=high`. Весь доступный интервал от исходного хода просмотрен; на момент приёмки других `turn_context` нет. Полный интервал повторно прочитан перед записью результата; исходные ID и назначение сохранены. Допуск подтверждён. Нормативный `java/docs/model-admission.md` не менялся. Начальное чтение brief/diff и требований допуска предшествовало фиксации собственного допуска; содержательная приёмка проводится после фиксации.

## Область

Адресный независимый обзор PL diff `7a41b7cc73859f69bf45c304590d711d85bcae66..5a04576e0b288e481fbbf55ffe8fa16be56ed77a` и существующей цепочки validation → export. Пять Java UUID в эту проверку не входят. Source не меняется. Пройденные тесты/render/CI/build/hash не повторяются.

## Результат

**Clean.** Actionable findings и блокирующих несоответствий не обнаружено. Изменение соответствует brief; качество достаточно для включения в пакет при сохранении указанной интеграционной границы.

Проверена фактическая цепочка:

- `_extensions/course-prairielearn/application/native-business.ts:48`: необязательный UUID допущен только в `AssessmentPolicySchema`. `AssessmentDefaultsSchema` остаётся strict без этого поля (`:57`), и `readNativeRaw` проверяет defaults до merge (`:160`). `nativePolicy` UUID не преобразует; `decodeBusiness` сохраняет его после `mergePolicy` (`:277`). Native composition переносит готовую policy в `extensions.prairielearn` (`native-course.ts:301`).
- `_extensions/course-prairielearn/application/grading-policy.ts:26`: standalone/native повторная closed-проверка принимает поле и проверяет тип/форму (`:39`). Шаблон соответствует закреплённому `spec/upstream/infoAssessment.json` внутри расширения. UUID не входит в `questionFields` и не участвует в вычислении scoring. Standalone config явно отвергает `assessment-defaults.uuid` до дальнейшей работы (`declarations.ts:100`).
- `_extensions/course-prairielearn/application/export-course.ts:196`: grading validation проходит до выбора effective UUID. Локальная строка используется буквально; fallback — прежний `identity("assessment/" + work.id, courseUuid)` (`:201`). `identity` и namespace course не изменены; перенос чистого вычисления раньше staging не меняет значение или сериализацию. Поле `uuid` занимает прежнюю позицию в том же объекте `infoAssessment` (`:693`), writer JSON и остальные свойства прежние. Следовательно, без нового поля байты UUID и запись `infoAssessment.json` сохраняются при том же входе.
- `export-course.ts:203`: effective UUID сравниваются без учёта регистра для всех выбранных работ, включая explicit против fallback, до `makeTempDir`, Body callbacks и публикации. Сообщение содержит UUID и оба work IDs. Предварительная валидация отклоняет null/нестроковые/неверные значения раньше `??`, поэтому они не маскируются fallback. Публикация остаётся через прежние `publish`/`discard` и rename.
- `export-course.ts:693`: UUID определяет именно `infoAssessment.uuid`; выбор work ID/пути (`:690`), course/instance/question UUID, список вопросов, completion и баллы не переопределяются. Прежний pass-through `delivery.works[].policy` также сохраняет авторское поле (`:782`); оно не получает новой семантики в assignment/scoring. Адресное чтение `platform/bridge/delivery.mjs:18` подтвердило прежний `z.record` для policy, поэтому это не требует расширения delivery schema.

Новые регрессионные cases прочитаны: native business acceptance при обоих ID, malformed/local и defaults rejection; standalone экспорт с сохранением UUID при переименовании, литералом старого fallback, explicit/explicit и explicit/fallback collisions с разным регистром, defaults и отсутствием Body/обоих output при отказе. Эти assertions проверяют пользовательское поведение через существующий harness. Новый framework, обязательный identity key, course-specific map или Core schema не введены. Русский публичный контракт описывает поле и ограничения.

Пройденные команды из `pl-assessment-uuid.md` не повторялись. Этот обзор основан на адресном чтении exact diff и примыкающих контрактов; новых запусков тестов, render, CI, build, hash или публикации не было. Проверка пяти UUID Java делегирована другому reviewer и здесь не выполнялась. Совпадение сохранённых source-base UUID с фактической серверной БД и installed paired runtime остаётся внешней интеграционной границей; clean review её не закрывает.
