# Независимый review удаления двух PL digests

4 ноября 2026. **APPROVE для точной локальной дельты пяти файлов, +4/−27, поверх `30edc03295e508f174c64b399f35b14ad37488fa`.** Блокирующих findings по прочитанному diff/consumer chain нет. Это Source review ограниченного удаления непотребляемых значений, не приёмка всего deliveryHash/immutable механизма и не заявление compiler/fullCI GREEN.

Проверено pending дерево `/home/tolya/JavaCodex/java-release-20261110/prairielearn`: ожидаемый base HEAD совпал; ровно два production и три existing fixture files изменены. Reviewer Source этой дельты не писал. Ранее reviewer написал producer/pattern audit и более ранние type-only изменения baseline; это происхождение контекста, а не независимое новое измерение. Current writer — `/root/pl_unused_digests`.

## Точный scope

- `application/native-provenance.ts`: убраны `canonical/hash` imports, digest и дополнительный spread return; возвращаются прежние schema/identity/ownerInputs/capturedInputs/sourceRows/projects. Все source/owner/coverage/format/project declaration checks и нормализация policy сохранены. Функция остаётся async с прежними входными сигнатурами; удаление ненужного await digest не меняет выбранные данные.
- `application/verification-inventory.ts`: убран только whole-inventory `inventoryHash` и строка комментария. `questions`, descriptor/provenance clones, file byte checks, references/cases, scenario sorting, `scenariosHash` не менялись. Возвращаемый inventory не сокращался до scenarios в этой партии.
- `tests/native-fact-owner.test.ts`, `tests/native-provenance-values.ts`, `tests/native-verification-inventory.test.ts`: удалены только мутации ради различия orphan digest и один связанный summary flag; названия двух tests уточнены. Owner mismatch, normalized check, actual scenarios, changed bytes, foreign exercise, repeated references/case IDs, hidden/nonbank/format/materialization и strict-v3 controls сохранены. Возвращать удалённый механизм ради его старых assertions не требуется.

Изменение не добавляет any/casts/suppressions, не ослабляет существующие DTO/role boundaries и не заменяет их новыми runtime validators. В двух исходных production helpers уже были `Core:any`, selections/project callbacks через any — прежний type debt остаётся, новой типизации или compiler coverage этот diff не доказывает. Удаление intentional output property не означает потерю concrete полей существующей producer/consumer цепи.

## Проверка transitive whole-object consumers

Проверены не только обращения по именам удалённых полей, но и все найденные production callsites функций, spreading, callback forwarding, serialization и hash subjects.

| Значение | Фактическое движение объекта | Persisted/canonical coupling |
| --- | --- | --- |
| `nativeProjectWitness()` | `native-course.ts:166` получает witness; `withNativeProjectPlans` выбирает `projects` для contexts/plans; `withNativeAssignments` передаёт весь witness только во внутренний callback. `prepareNativeCourse` выбирает `sourceRows`, `projects[].fact`, materialization и coverage | Whole witness не попадает в `canonical/hash/JSON.stringify` production пути. `nativeChecksManifest` получает **выбранные materialized projects и source rows**, а не `{...witness}`. Поэтому его `sourceSnapshotHash/inventoryHash/selectionScopeHash` не включали удалённое поле |
| Prepared callback/result | `withNativeAssignments` возвращает результат `use`. В actual production `use` возвращает `bindNativePublication(result,checkBindings)` от exporter | `...composed.value` в `prepareNativeCourse` — publication handle/delivery, **не** prepared record с witness. `bindNativePublication` spread получает handle, а не witness/inventory. Дополнительные project-context metrics прежние |
| `nativeVerificationInventory()` | `native-course.ts:521–555` передаёт весь объект в exporter; его existing consumer view `export-course.ts:157` содержит только `scenarios/scenariosHash` | Exporter явно строит delivery (`:789–841`) и читает только эти два поля. Ни spread всего nativeVerification, ни его сериализация в private checks нет: private JSON пишет `input.checks` (`:853–856`). `scenariosHash` считается только от scenarios, не от inventory |
| Production delivery/legacy hashes | Exporter использует прежние `checks.sourceSnapshotHash/inventoryHash`, native `scenariosHash` либо legacy `verificationInventoryHash`, затем canonical delivery | Удалённые два поля не входят в delivery object, file inventory, private checks или эти hash subjects. Чтение Source не выявило изменения delivery SHA-domain вследствие удаления; новые hashes/byte comparison не запускались |
| Integration fixture JSON | `native-a1-builder-integration.ts:314–318,366` и reuse analogue `:325–329,438` добавляют **весь** verification в diagnostic `checks`, затем `JSON.stringify({checks,...})` | **Есть наблюдаемое изменение diagnostic stdout JSON**: в нём отсутствует private inventoryHash. Это не проигнорировано. `tests/native-a1-platform.py` строит jobs из отдельного checks.json, читает verification только как `scenariosHash` для parity (`:28`); mandatory digest от whole diagnostic JSON не найден |

`tests/native-selection-integration.ts` читает scenarios; остальные найденные witness callers — изменённые fixtures. Оба helper exports отсутствуют в публичном `_extensions/course-prairielearn/public.ts`; private direct imports извне всё равно технически возможны. Адресный поиск Source/spec/docs/tools и прочитанные actual consumers не показали дополнительного production reader или mandatory whole-object hashing. Отсутствие произвольного неизвестного внешнего import/исторического evidence reader не утверждается; writer также обозначил этот предел.

Таким образом, причина APPROVE — не просто «строка имени поля больше не встречается», а явное отсутствие этих полей из mandatory production serialization/hash inputs в прочитанном call chain. Source JSON diagnostics из двух integration fixtures — известный допустимый effect, не persisted runtime identity migration. Native UUID/results/student files, roles и scoring не меняются этой дельтой.

## Evidence и ограничения

Прочитан report автора `pl-unused-digests-20261104.md`, точный diff всех пяти файлов, affected production helpers и consumer paths, `public.ts`, relevant integration fixture emitters/reader. Предыдущий `hash-mechanism-producer-review.md` использован как карта, не как самостоятельное подтверждение отсутствия coupling.

**Новых запусков reviewer не делал.** Tests/compiler/build/render/inspect/hash/network/cache install/commit/push не выполнялись; Source/state не менялись. Результат автора — **5 pure runtime fixtures passed, 0 failed,83ms**, bundled Deno2.7.14, его команда была `deno test --no-check`; это runtime evidence, не проверка TypeScript. После run автор поменял только название одного теста. `native-provenance-values.ts` не исполнялся.

Affected compiler автора **не дошёл до TS program**: uncached JSR `@std/path` не разрешился с existing map/`--deny-import`. Это limitation, не TypeScript finding и не compiler PASS. Reviewer не назначает повтор/cache warming/широкую CI ради микродельты. Согласованная будущая объединённая substantive CI остаётся внешней проверкой компиляции; её исход ещё неизвестен. Авторский `git diff --check` PASS учитывается как предоставленное evidence, здесь не повторялся.

Экономия — два удалённых canonical/digest вычисления; exclusive seconds/курс speedup **UNKNOWN**. Остальные hashes/cloning и verification questions сохраняются намеренно. Этот verdict разрешает локально сохранить рассматриваемую дельту; не разрешает production cutover и не закрывает последующий пересмотр delivery/files immutable API.

## Собственный первичный допуск

Reviewer `/root/pl_typed_materialization`, chat `01a1a25f-b098-7242-bf6d-d0e8f6517361`; исходный turn `01a1a25f-b0fb-7fd2-b244-83933af77834`. Собственный primary `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-05-42-01a1a25f-b098-7242-bf6d-d0e8f6517361.jsonl`: полный неизменённый исходный интервал `turn_context`8/596/758/823/1037/1080/1181/1310/1430/1715 перечитан; top-level и вложенные model/effort согласованы `gpt-6.1-sol/high`. Текущий turn `01a1a4f3-b0c9-7c22-8b82-80c71e179ef7`. Standard назначен, скрытый server tier не удостоверяется. Baseline не сдвинут; globals/normative docs untouched; env/secrets не выводились.
