# Независимый review двух внутренних Core fences

3 ноября 2026. Проверен exact Core диапазон `3af7600cd44c562cb781865f02f94833c4690ada..d7cc10eaa21fa35bbe32d73cc4afa216002393da`: два файла, +109/−2. Production изменение ограничено двумя точками `_extensions/course-core/runtime/operation.ts`; второй файл — новый data-only test.

**Вердикт: APPROVE в пределах данной Source партии. Существенных findings нет.** Удаление ранних внутренних повторов сохраняет обязательный closing отказ до перехода в `sealed`. Это вывод из exact diff и окружающей цепочки, без нового actual seal или измерения ускорения.

## Удалённый prepared Source fence

`runtime/assignment-admission.ts:196–213,237–259` сохраняет initial snapshot, full Source fence после inspect и full Source fence после внешнего preparation callback. Порядок callback, selection validation и второго full fence не менялся.

Между этим последним fence и удалённым `assertSources(state)` прослежены `capture.ts:20–43`, `runtime/assignment-execution.ts:29–49`, `runtime/operation.ts:586–705,3427–3437`. Wrapper передаёт admission через внутреннюю `withAssignmentExecution`/AsyncLocalStorage closure; новый внешний callback, producer, role handoff или запись в Source в этом промежутке не появляется. Импорт `operation.ts` уже выполнялся до inspect в `prepareAssignment`. До прежней точки идут internal admission consumption, canonical/path checks, создание excluded служебных каталогов, запись reservation/pointer, сборка State и `bindOwner` из уже наблюдённого owner. `safeParent` (`artifacts/files.ts:38–48`) только проверяет существующий parent. Прочитанные записи относятся к `_generated` и служебному output; новой записи в квалифицируемый S нет.

В target `operation.ts:692–704` после `bindOwner` сохраняются присвоение relevant/owner inputs, runtime binding check и reservation current check перед prepared state. Внешний `use(scope)` вызывается позже, на `:921`. Relevant Source/config проверки на lease entry/exit и перед child сохраняются: `operation.ts:876–897`, `prepared-operation.ts:85,102–107`, `capture.ts:35–41`; child pre/post и ordinary full путь не изменены. Поэтому удалённая строка не была единственным guard на границе внешнего preparation callback. Изменение вне relevant scope может теперь обнаруживаться позже, на сохранённом full closing fence; оно не получает права завершить operation.

## Seal и публикация

`operation.ts:1991` теперь вызывает `assertPrivate(state)` вместо `assertPrivate(state, true)`. По `:444–468` остаются root/pointer correlation, runtime canonical executable/type/signature identity и assignment binding. Entry больше не делает content digest executable и full обход списка implementation signatures; обе проверки остаются на closing. Этот нюанс важен: запись не интерпретируется как сохранение full implementation pass на entry.

Перед closing неизменны:

- `operation.ts:2023–2054`: child identity, owner/S witness, assignment intent и runtime binding проверяются; наблюдённые implementation files объединяются в `state.assignment.runtime.implementationFiles` через прежнюю `mergeAssignmentImplementationFiles`. Её конфликт path/signature по `assignment-admission.ts:79–94` по-прежнему отказывает.
- `operation.ts:2102–2118`: каждый output/resource проверяется по regular physical path, signature и actual bytes SHA256 до seal. Эти чтения не сняты.
- `operation.ts:2131–2143`: под тем же writer сначала `assertReservationCurrent`, затем **full** `assertPrivate(state, true)`, затем **full** `assertSources(state)`, и лишь потом `reservation.status = "sealed"`, reservation write и `complete.json`.

Таким образом closing full private получает уже объединённый список implementation files; `assertAssignmentRuntime(..., true)` проверяет его actual path/type/signatures, а `nativeRuntimeIdentity(..., true)` проверяет executable content digest против исходного invocation. Между merge и full private нет нового внешнего callback или producer. Closing full Source использует прежние baseline rows/memberships и ловит edit/new membership/symlink вне relevant inputs до `sealed`. Ошибка до этого перехода выбрасывается в прежний `withNativeCaptureScope` catch; успешный result возвращается только после `sealCaptureScope` (`:936–944`). Новая дельта не публикует Source edit до closing.

Source full fence здесь — прежняя signature/path/type/membership проверка (`source-snapshot.ts:155–276`), а не новый byte hash всех источников. Initial snapshot и его source bytes hashing (`:94–133`), exclusions, allowed additions и exact empty native scratch exception не менялись. Новые cache, registry, receipt, классификация closure или исключение installed extensions в diff отсутствуют. Public wire, v2 state и API также не менялись.

## Доказательства и пределы

Прочитаны новый `tests/assignment-closing-source-fence.ts`, existing dependency control и сохранённые `reports/core-bounded-fences.md`, `reports/core-bounded-fences-regression.json`. Новая проверка вызывает настоящую `assertSources` на малых собственных файлах, с baseline signatures и явно неиспользуемым hash placeholder. После mutation unselected/vendor/addition/symlink local fence проходит, а full fence отказывает `OPERATION_CHANGED`; selected source/config изменения также отвергаются, unchanged control проходит. Это содержательный coverage negative. Он не создаёт producer/child/complete/sealed и не исполняет actual seal transaction; placement перед `sealed` подтверждён чтением production кода выше.

Сохранённый результат автора: новая pure проверка exit 0 за 0.999 s, existing empty scratch control exit 0 за 0.968 s, existing dependency merge/currentness control exit 0 за 0.983 s; суммарно 2.950 s. Reviewer их повторно не запускал. Отдельной новой actual проверки executable digest/output mutation этим свидетельством не заявляется; соответствующие closing guards кодово сохранены. Все reads review были чтением Git blobs/документов/собственных primary metadata. Check/tests/native/render/inspect/hash/build/install и paired SP run не выполнялись. Source не менялся; единственная запись — этот отчёт.

Baseline счётчиков принят из `reports/sp-native-source-fence-cost.md`: четыре full Source fences и 710 directory reads, один initial snapshot на 873 rows/5,134,554 B, четыре runtime hashes и 14 output hashes. Четыре Source fences не означают четыре byte hash walks. Для того же S ожидается один Source fence и один ранний runtime digest меньше: 3 full fences, 577 directory reads при прежних 133 membership directories и 3 runtime hashes. Initial Source hashing и output hashing сохраняются. Это структурное ожидание по call sites; новый installed состав S может отличаться. Измеренное ускорение или секунды/проценты здесь не утверждаются.

## Собственный первичный допуск

Исполнитель `/root/review_core_owner_types`, ожидаемая пара `gpt-6.1-sol/high`. Source primary metadata — `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-53-23-01a1a28b-583e-7d71-ab50-0f123b8de5a4.jsonl`; собственный ID `01a1a28b-583e-7d71-ab50-0f123b8de5a4`, подтверждённый `session_meta.agent_path` на строке 1. Parent/session ID координатора собственным ID не подменялся.

Полный собственный reviewer interval сохранён от исходного хода `01a1a28b-586d-7d31-94ce-e366ffbd375f` на строке 8, включая нынешний ход `01a1a295-200e-7430-bf17-fd32ab456ba8` на строке 142. Обе записи `turn_context` имеют верхние `model=gpt-6.1-sol`, `effort=high` и вложенные `collaboration_mode.settings.model=gpt-6.1-sol`, `reasoning_effort=high`. Новый perf review начинается во втором ходе; baseline всей reviewer роли не сдвинут. Standard назначен root; `service_tier` отсутствует и скрытый серверный tier не утверждается. Глобальные settings и нормативные документы не менялись.
