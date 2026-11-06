# План нового Gateway и согласованной очистки, 6 ноября

> Для исполнителей: выполнять независимые задачи через `superpowers:subagent-driven-development` либо `superpowers:executing-plans`; передавать изменения на независимую рецензию.

**Цель:** новое приложение входа Moodle и упрощённая цепочка Quarto → PrairieLearn без старой учебной композиции Gateway и собственных механизмов квалификации выпуска.

**Архитектура:** Moodle подтверждает вход; Gateway проверяет LTI и временный запуск; PrairieLearn владеет пользователями и учебными сессиями; приватный адаптер PL зачисляет и выдаёт штатные права. Учебный трафик направляется непосредственно в PL. Существующий Pandoc AST и контекст операций переиспользуются без нового кеша или реестра.

**Стек:** существующие Python/FastAPI/joserfc/SQLite, Node/PostgreSQL/nginx и TypeScript/Deno/Quarto/Pandoc. Криптографические функции LTI и алгоритм UID сохраняются.

**Спецификация:** [согласованное предложение и результаты обзора](../../release-transfer-20261105/evening-report.md). Пользователь 6 ноября разрешил выполнение пяти параллельных направлений и расширение объёма до доступности sudo после 15:00.

## Общие ограничения

- Только `gpt-6.1-sol`; два тяжёлых чата, три medium. Субагенты только для независимого исполнения и рецензии.
- До 12:30 — промежуточная интеграция, доступные проверки и PR. После 15:00 — браузерная приёмка при восстановлении доверия CA; заранее её не объявлять принятой.
- Сохранять прежние UID, native IDs, UUID курсов, права, ответы и историю. Production и старую VM не менять.
- Никакой совместимости со старыми учебными потребителями Gateway; старые данные сохранять отдельно от исполняемого продукта.
- Никаких новых форматов тестов, кешей, registries, SDK или proof/freshness-протоколов. Не создавать новые пакеты model-admission/hashes ради отчётности.
- Сохранить `.public-tests`, JUnit в стартере, плоские `default-exercise-*`, shortcode Download и взаимоисключающие формы 1–2 editor / 3+ flat upload / 3+ tree ZIP.
- Библиотечный HTML AST допустим; собственный HTML/PNG/PDF/Git parser не вводить.
- Один владелец файла. Общим стендом и публикацией интегрированной Platform управляет S1; Java/SP интегрирует root.

## Фокус рецензии

1. Старый callback после нового входа не удаляет новую cookie; одноразовый запуск необратим.
2. Смена регистрации или привязки курса после launch требует нового Moodle-входа.
3. Timeout Bridge не означает успешное зачисление и не возобновляет старый запуск; native данные автоматически не откатываются.
4. Удаление legacy SQL сохраняет administrator grant, native audit и advisory lock; студент не получает staff.
5. Упрощение Core сохраняет teacher/student/private разделение, конфликты ресурсов, raw-ограничения и корректные effective profiles/selected inputs.

## S2: новое приложение Gateway — high

**Файлы:** `src/gateway/app.py`, `config.py`, `store.py`, `launch.py`, `pl.py`, auth SQL, относящиеся tests/config/docs; удалить legacy assignment/grade/AGS/delivery/worker composition и её потребителей.

**Интерфейс:** caller `PUT /internal/gateway/enrollment` с `userUid`, `instance`, `courseKey`, `role: student|teacher`, `email: string|null`. Ответ: `schemaVersion`, `userUid`, `instance`, `courseKey`, `enrolled`, `path`. Изменения этого договора сначала согласовать с S1.

- [ ] Проверить отрицательные сценарии и закрепить осмысленные падающие тесты до изменения поведения.
- [ ] Реализовать новое auth-only приложение, временный immutable snapshot и точное credential binding; убрать users registry и часовую возможность повторного handoff.
- [ ] Сохранить UID; проверять saved/current регистрацию и course mapping; consume flow до Bridge.
- [ ] Проверить replay, expiry, direct callback, forged headers, parallel callback, mapping change, failure/timeout и logout/rebootstrap.
- [ ] Private signing key удалять только после сверки действующей регистрации Moodle.
- [ ] Независимая рецензия, адресные проверки, отдельный архитектурный PR; передать S1 принятую версию для собственного стенда.

## S1: приватный адаптер и собственный стенд — high

**Файлы:** Platform `bridge/server.mjs`, `bridge/native.sql`, enrollment schema, proxy, относящиеся bridge/proxy tests/docs. Общий CI и Docker patch/hash инструменты принадлежат S3.

- [ ] Зафиксировать единственный фактический enrollment договор с S2; удалить прежний альтернативный контракт.
- [ ] Удалить legacy HTTP/assignment/grade handlers и SQL, сохранив native grants/audit/advisory lock и blocked/access guards.
- [ ] Проверить три роли, включая owner с Moodle Learner; email не объединяет аккаунты, автоматического отзыва прав нет.
- [ ] Принять очищенные Platform-коммиты S3 и опубликовать единый проверенный Platform PR.
- [ ] Выполнить сопоставимые полные измерения экспорта Java до/после в одинаковых условиях кеша; не повторять без причины.
- [ ] Развернуть новый Gateway только на собственном тестовом стенде; сохранить БД и ранее импортированные курсы.
- [ ] После доступности доверия CA выполнить настоящий browser LTI и Workspace save/stop/resume; получить снимки без секретов.
- [ ] Подготовить изолированную проверку переноса и отката; историческое восстановление не считать принятым без выполнения, старую VM не менять.

## S3: собственные hashes/proofs и Git — medium

**Файлы:** Platform `tools/delivery_verification.py`, `vendor-native-schemas.py`, `delivery4.py`, Docker patch scripts и относящиеся tests/CI/docs; Java/SP `CI/check-production-pins.py`, `CI/toolchain.json` и исключительно связанные устаревшие tests/docs.

- [ ] Удалить SHA manifest/циклы схем; сохранить функциональную schema validation.
- [ ] Удалить собственные SHA исходников Docker; сохранить native OCI identity и применимость патчей.
- [ ] Удалить собственную квалификацию Git object/index; необходимые функциональные проверки выполнять штатным Git.
- [ ] Удалить отключённые course proof consumers, runner/asset proof fields и обращения к удалённому release assembly.
- [ ] Проверить положительные пакеты и содержательные отрицательные случаи без зеркальных внутренних тестов.
- [ ] Независимая рецензия; Platform-коммиты передать S1, отдельные Java/SP-коммиты root. Публичные общие ветки одновременно не отправлять.

## S5: Core и экспорт PrairieLearn — medium

**Файлы:** Core `artifacts/project.ts`, Pandoc runner, `runtime/operation.ts`, `assignment-admission.ts`, `assignment-provenance.ts`, `body-export/model.ts`, `package-contracts.ts`; PL `application/native-materialize.ts`, `export-course.ts` и относящиеся tests/docs.

- [ ] Установить необходимость каждого materialization/Pandoc прохода и переиспользовать готовый AST/результат внутри операции.
- [ ] Сократить доказанные дубликаты qualification/provenance envelopes, сохранив реальные privacy и ownership guards.
- [ ] Разобрать оба Core inspect пути и передавать имеющийся контекст там, где это сохраняет профили и выбранные входы.
- [ ] Сузить `any` на конкретных используемых границах без нового AST framework.
- [ ] Проверить teacher/student/private, linked resources/conflicts, unsupported raw и действующие публичные API. Не возвращать удалённые aliases ради старых тестов.
- [ ] Согласовать с S4 передачу готового контекста, пройти независимую рецензию и обновить Core/PL PR.

## S4: Site, QRC, Download — medium

**Файлы:** Site root/child config/compose и `tests/site-domains.ts`; QRC `entrypoints/post.ts`; Download standalone runtime; относящиеся tests/docs.

- [ ] Исправить реальный CI failure: `tests/site-domains.ts:294` обращается к удалённому `op.resourceInventory`; использовать действующий API без alias.
- [ ] Сократить повторный inspect Site root/children и QRC post-render/version только при наличии равноценного готового контекста.
- [ ] Сохранить функциональный standalone fallback Download; на integrated native пути отдельного inspect нет.
- [ ] Явно обозначить существующие format boundaries Core HTML/Reveal и QRC HTML; не добавлять неподтверждённую поддержку форматов.
- [ ] Согласовать контекст с S5; Core-файлы редактирует только S5. Адресные проверки, независимая рецензия и PR.

## Координация root

- [ ] Следить за интерфейсами и владением файлами, поднимать сюда вопросы разрешений.
- [ ] Интегрировать проверенные Java/SP-коммиты и установленные расширения после source review.
- [ ] Контрольный итог до 12:30; после реализации независимый Sol/ultra обзор без нового доказательного механизма.
- [ ] После 15:00 включить browser/Workspace результаты и снимки в окончательный отчёт; отправить изменения и отчёты в PR, проверить readback.
