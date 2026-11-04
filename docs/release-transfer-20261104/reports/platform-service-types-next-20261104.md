# Platform: следующий ограниченный service/DB typing срез

2026-11-04. **Рекомендуется частичный JSDoc срез consumed views/results в `bridge/recognized-credit-native.mjs` + непосредственном caller `tools/recognize-credit.mjs`; полный checked DB adapter пока не обещать.** Нельзя объявлять `queryRow<T>` проверенным DTO через нынешний `z.any()`. Exact affected service checker на actual Native package graph пока **UNKNOWN**; локальный реальный TypeScript toolchain существует, но предыдущий9b checker покрывает другой reader.

Source `platform` HEAD `9b82e29bffd77cc462ffdef23cd3b96ca041db92`, clean при чтении. Прочитаны AGENTS, [review9b](platform-typed-payload-review.md), actual bridge+SQL, непосредственные callers/service factory и адресные synthetic fixture/contracts. Actual upstream label845 — claimed Source runtime, не доступ к bytes/runtime composition: production/config/DB/SSH/row data не читались, прежний approval block не обходился. Никаких edits/runs/tsc/install/network/hash/build.

## Что реально проверяет Source

| Boundary | Actual producer/consumer | Что можно типизировать честно |
|---|---|---|
| Administrative input | `recognized-credit-native.mjs:12–22 shape` → recognize/verify/private API | Existing string native IDs/UID/UUID comparisons, mapping kind/canonical exercise ID, actor, exact digest equality. Это не closed object schema: extension keys сохраняются. Hash regex без `typeof` сам по себе не доказывает primitive string; `checkModifiedAt` нужен только на new-recognition branch, replay может пройти без него |
| PostgreSQL | `nativeDb():41` dynamic `@prairielearn/postgres` + `schema:z.any()`; owned SQL SELECT blocks → manual checks | `z.any()` — существующий passthrough query argument, **не validation**. Не использовать как основание typed rows и не менять на новый z.object/z.record |
| Identity | `same:23`, `courseIdentity:24–30`, `success:31–34` → all administrative operations | Only compared fields from AI_FIELDS/IQ_FIELDS/source list. `same` не валидирует points/private answers/file blobs/все SQL columns; `courseIdentity` проверяет actual physical course path и infoCourse UUID, не весь info JSON |
| Points | `maximum:35–38`, `full:39` → repair/current recognition/private access | `maximum` возвращает реальные finite `{auto:number,manual:number,total:number}` после существующего `Number(...)`/sum/range checks. Raw maxima могут быть numeric strings/nullish; нельзя превратить их в number-only input. `full` связывает только перечисленные exact full-score/status/manual fields, не весь IQ row |
| Trusted event | `eventRecord` → recognize replay/apply/verify | Проверяются format, authn actor equality и тот же `shape`. Нельзя приписать всему `record` закрытую event schema или считать все extra fields checked. `sourceEvidenceHash` затем сравнивается с actual source hash |
| Private files | `recognizedPrivateFilesApi` → `tools/recognized-credit-files.mjs` | Existing owner/administrator/notes controls, exact source submission/native-file branch, raw/submitted distinction, bytes/hash/base64 and store owner checks. `context()` создаёт конкретные booleans `allowPersonalNotes` и `recognizedCredit:true`; Native file row/store API целиком не проверяется одним `context()` |
| Immediate caller | `tools/recognize-credit.mjs:5–14` | Native admin method/returned status discriminant и private API port. Manifest array check предшествует `validatePrivateSourceFiles`; typed private refs нельзя считать validated только после `Array.isArray` |

Оценки, ACL, full-score/native-average/required-slot semantics, administrator authority, transaction order, recompute count, IDs/history/closure и mapping/hash operands менять нельзя. Они не являются задачей типизации.

## Выбранный минимальный план, максимум два production files

**1. `bridge/recognized-credit-native.mjs`: owned JSDoc views и порты с точной степенью проверенности.**

- Явно описать numeric output `NativePointMaxima`, consumed identity keys, administrative result union по трём существующим status: `NEEDS_NATIVE_ADMIN_EVENT`, `ALREADY_RECOGNIZED`, `RECOGNIZED_NATIVE_ADMIN_EVENT`. `reason` обязателен только в первой ветке; `eventId` **не объявлять checked string** только по `event.id` — own bridge его отдельно не проверяет. `key` — string реального hash producer.
- Описать concrete `VerifiedRecognitionContext`/private `context()` return с owner fields и booleans, а не полный Native DB row. Сохранять открытый extension bag; input hashes/optional new-recognition fields не сужать сильнее существующих checks. Не выдавать read-only spread target за fully parsed Native DTO.
- Для service seams `runInTransactionAsync<T>` может сохранять generic **callback result**, поскольку функция действительно возвращает callback result. Это существенно отличается от выдуманного `queryRow<Row>`: generic rows по имени SQL без validator запрещены. `recompute` параметры — реальные `assessment_instance_id`, `authn_user_id`, numeric credit; возвращаемое значение не потребляется, значит не приписывать ему grade DTO.
- На DB входе обозначить raw rows/columns как непроверенные. Нужны отдельные views **после** existing `same`/`success`/`maximum`/`full`, с JSDoc assertion/predicate только на реально установленный набор полей. Structural object-row transport contract, если используется, должен опираться на actual PostgreSQL adapter declarations; он не подтверждает типы колонок. Не делать `@returns {Promise<NativeIQ>}` на wrapper вокруг z.any().
- `canonical`/source evidence hashing и raw `_files` payload оставить на прежних raw objects. Recursive canonical input, Date handling и unchecked extension columns — самостоятельные typing debt; нельзя «решить» их `any`, fabricated CanonicalJSON или parsed copy. `source_success` SQL действительно возвращает raw_answer/auto_feedback/auto_output/timestamps, а `success()` не валидирует их содержимое.

**2. `tools/recognize-credit.mjs`: импортировать owned JSDoc result/port types и типизировать непосредственный native-admin seam.**

- Сохранить существующий выбор injected DB vs dynamic `dist/lib/manualGrading.js`. Тип method `recognizePreviousCredit` переиспользовать от `recognizeCredit`, не дублировать full Native module declaration. Type-only `import(...)`/`typeof` не должен менять runtime imports.
- При `NEEDS_NATIVE_ADMIN_EVENT` ранний возврат с files NOT_WRITTEN должен компиляторно отличаться от completed administrative result; не требовать grade/eventId на missing-IQ ветке.
- Передавать existing file API port в validate/attach без обещания, что этот caller сам выполнил полный private-reference parser. `sourceRoot`, externally loaded input/approval и raw ref payload остаются с точной границей existing validation; unknown **до** этих checks, concrete view после — не mechanical unknown во всём consumer.

`tools/recognized-assignment-native.mjs` прочитан как смежная граница, но **не включать третьим файлом**: его contexts/enrollment/labels/managed ACL/config and bindings graph существенно шире. `bridge/recognized-assignment.mjs` также не включать в этот первый срез. Их transaction/verifiedContext/recognition/read/bind/store service protocol можно позже переиспользовать из owned views, без изменения assignment policy.

### Что этот срез не может честно объявить

Полный `@ts-check` всего `recognized-credit-native.mjs`/Native DB graph **ещё не доказан реализуемым в двух файлах**. Его legacy helpers индексируют dynamic raw records, private JSON blobs и canonical trees; простая замена параметров на unknown немедленно требует реального narrowing, а generic typed row facade скроет проблему. Нельзя вставлять cast или новый runtime guard ради compiler green. Если будущая адресная compilation потребует изменений вне двух files либо нового row validator, остановить расширение и сохранить только доказанную частичную типизацию с конкретными diagnostics.

В частности, JSDoc annotation без body checking — полезная declared service signature, но **не доказательство, что DB input уже проверен**. Не выдавать редакторные types или import-negative за полное checked implementation coverage. Цель первого diff — реальные checked projections/results и точная передача callback/port DTO, а не «typed DB» в отчёте.

## Checker: факты и UNKNOWN

- Repo package.json содержит только zod4.6.7/pgsql-parser18.3.2 dev dependencies; штатного tsc/checkJs script в просмотренном toolchain/CI нет. Existing CI выполняет Node tests/прочие contracts, не общий JS typecheck.
- Предыдущий [9b report](platform-typed-payload.md) использовал actual TypeScript5.9.3 compiler API из saved Performance backend и actual Node declarations. `/tmp/platform-typed-payload-pmt1pbq1/check.cjs`/tsconfig **прочитаны**: strict/allowJs/noEmit, checkJs:false с reader `@ts-check`, реальные cached Zod declarations, один reader+negative root. Этот checker не проверяет recognized-credit services/DB.
- Existing temporary config/real compiler path есть, но готовый честный affected checker **для предлагаемого service graph — UNKNOWN**. Dynamic `@prairielearn/postgres`/dist grading/file-store imports и их current actual declarations не подтверждены чтением этих source paths. Saved `tests/fixtures/native-credit-upstream/dist/lib/db-types.js` — executable source fixture, не повод объявить ambient DB/service stub реальными declarations, и не доказательство actual845 deployment.
- Будущий checker должен использовать реальные available declarations или оставить upstream result unknown и проверять только owned views; без install/fetch/copy-declaration/`declare module ... any`/fake native service. Пока ни tsc, ни TypeScript API не исполнялись, поэтому zero diagnostics для этого плана не заявляется.

После отдельного GO полезны один affected composition и один typed negative на **реальный administrative result union** (ветка NEEDS_NATIVE_ADMIN_EVENT не имеет completed grade/event fields) либо string-vs-number checked native ID. Не выбирать negative на brand/hash-format, которых существующий compiler type не подтверждает. Existing synthetic recognition/ownership fixtures — возможное runtime evidence позднее, не повод читать actual rows/DB и не назначенная здесь matrix.

## Почему не переоткрывать9b

`native-course-payload.mjs` intentionally сохраняет raw delivery после existing validation: Zod parsed result терял own `__proto__` и менял hash/files domain. Поэтому в новом срезе нет нового z.object/strip/normalization, parsed object replacement или closure registry. Не касаться raw delivery reader/schema, hashes, identity/scoring/ACL semantics. Текущий9b partial debt явно сохраняется; новый service view не объявлять его решением.

Итог — конкретный **частичный** two-file Source plan с compiler-readiness ограничением, а не разрешение обойти этот gap фиктивным row type. Source/state/DB/runtime/config не менялись; создан только этот report.

Primary admission: собственный chat `01a1a25f-b098-7242-bf6d-d0e8f6517361`, полный origin interval `01a1a25f-b0fb-7fd2-b244-83933af77834`, primary `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-05-42-01a1a25f-b098-7242-bf6d-d0e8f6517361.jsonl`. Только model/effort/turn fields всех25 contexts, lines8–3583: top-level/nested все `gpt-6.1-sol/high`, latest `01a1a572-2baf-7d82-97e8-1bcc083429e3`. Standard назначен; hidden routing не удостоверяется; globals/normative admission docs не менялись.
