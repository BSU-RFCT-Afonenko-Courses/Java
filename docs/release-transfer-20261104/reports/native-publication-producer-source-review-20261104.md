# Независимое Source review Core/PL: Native publication

**NEEDS_CHANGES: один P2 в текущем CLI описании; production/API semantic blocker
не обнаружен.** Дополнительно root combined compiler завершился FAIL в двух
существующих PL fixtures; acceptance closure ещё не PASS. Ни один Source файл
этим reviewer не изменён.

## Findings

1. **[P2] У renamed ordinary CLI осталось обещание удалённой exporter qualification.**
   [docs/authoring.md:410–415](../prairielearn/docs/authoring.md#L410) непосредственно
   после новой команды `inspect-publication.ts` обещает «точный native mapping» и
   actionable refusal при missing points. Новый actual CLI
   `_extensions/course-prairielearn/entrypoints/inspect-publication.ts:43–49`
   возвращает course/source/format/assessment, parsed policy и question summaries.
   Он намеренно не строит gradingPolicy/native mapping. В
   `application/native-business.ts:38` question-points остаётся optional, а
   `:296–307,328–333` publication decoder пропускает full grading composition.
   Автор, используя новую указанную команду как advertised missing-points gate,
   получает успешный partial result и обнаружит проблему только в отдельном
   full exporter. Требуется переписать этот соседний абзац как описание partial
   publication summaries и отнести native mapping/missing-points qualification
   явно к full course-export. Не возвращать полный gate в ordinary CLI ради
   согласования со старой прозой.

## Что проверено и принято на уровне Source

Прочитаны approved design/final delta и producer brief/report/handoff, actual
tracked diff Core base `5f4590aa4eb524830715f8020c8ff3c504197f73` и PL base
`4d39a6648d2816bab9a372877deded3320ff35e1`, untracked Core
`release/publication.ts` и новый PL `entrypoints/inspect-publication.ts`, удалённая
CLI, descriptor/docs/показанные direct fixtures. HEAD обоих совпадают с базами.
Патчи включают actual untracked modules; новых handwritten DTO/stubs не найдено.

- Common `validatePublicationFacts` содержит прежние Core exact coverage,
  course/projection, exercise/assessment duplicates, declared target, physical
  project и assessment-member/visibility/demonstration-public-solution checks.
  Ordinary builder вызывает эту функцию напрямую; он не вызывает validateRelease,
  installed-contract scan, PL full validator и не создаёт release proof/registry.
  Full `validateNativeRelease` использует ту же common часть и сохраняет свои
  callbacks/installed qualifications/result proof для отдельного режима.
- Перенос common checks всех документов перед full-course callbacks меняет
  порядок отказов/устраняет callback side effects перед обнаружением позднего
  malformed Core документа. На valid input прежние immutable per-document
  metadata и порядок adapter callbacks сохраняются. Callback не может исправить
  уже проверяемые private early facts: получает parsed immutable input. Изменения
  опубликованной семантики full-course результата или нового обхода qualification
  по этому переносу не найдено; runtime этого вывода не проводился.
- `withCapturedOperation` является одной общей acquisition/callback/finally
  closure. Publication assignment-capture отказ сохраняет разделение режимов.
  Перед выдачей результата остаются active/failed A1 checks, outer `guard(true)`,
  host guard, повторный A1 check и final expiration (`operation.ts:2382–2447`).
  Новый путь не приобретает второй capture. Canonical root/outputDirectory
  берутся из complete.state; caller не передаёт дублирующий контекст в view.
- Physical guard использует canonical owner-relative `ownedPhysical`, прежние
  exclusions/tool-cache/type checks. Снято только old initial snapshot membership
  как дополнительное условие ordinary business path; это соответствует design.
  Source/generation/output/private/role/resource guards существующего Core
  machinery не заменяются новым token/certificate.
- NativePublication methods выводятся через Pick actual NativeOperation;
  course — actual Core facts type, metadata — NativeAdapterValidationInput.
  `publicationAdapterInput` использует actual parsed early facts/captureWitness,
  existing schema и immutable data. Mutable private alias наружу не выдаётся:
  catalog/course recursively frozen; input parsed/frozen, unknown metadata не
  объявлены конфигурационным DTO. DTO не является lifetime authority.
- PL inspector принимает actual NativePublication и получает четыре metadata
  planes через Core-owned adapterInputs. Он проверяет parsed publication business,
  все owner sources, platform membership и project/common paths до projection.
  Inactive PL возвращает реальный course и пустые documents, не loaded coverage
  failure. `assertNativeSeedTargets`, adapter.validateCourse, prepared correlation
  и full grading composition из ordinary path не вызываются.
- `nativePolicy`/merge/readNativeRaw сохраняют unknown/record границы; assessment
  имеет existing schema output, question поля происходят из действующих
  readQuestion/submission/gradingMethod checks. NativeBusinessInput — derived
  consumed Pick/nested view, полный Core input структурно совместим. Новая pure
  function не требует fake runtime envelope. Any/full legacy debt внутри
  declarations/grading-policy не объявлен типизированным новым результатом:
  consumed declarations return здесь не используется как typed inspection DTO.
  ReturnType над any-producing publication decoder не найден.
- CLI rename/descriptor key/public exports/direct typed calls согласованы; старый
  alias отсутствует. Команда использует explicit owner/projection/profiles/output
  expectation и завершённый capture, выводит summaries без business.raw/private
  projectChecks. Исторический release-9.0.0 остаётся историей. Исключение —
  конкретный текущий authoring абзац в finding выше.

## Root-authorized test ownership closure

Отдельно прочитан `native-publication-test-ownership-20261104.patch` и actual
только этих двух Download paths. Удалены wrapper tests/ordinary-publication.ts,
повторно вызывавший upstream PL renders, и его единственный CI step. Остальные
workflow contracts/pins и полезные собственные ZIP tests не удалены. PL
native-publication-sources теперь принимает только Core, устанавливает Core+PL
и сохраняет три authored variants missing-visible/missing-hidden/valid,
preprojection private statement, source coverage/profile controls и отдельный
full-course refusal. Down type/runtime dependency отсутствует. Это одобренное
разделение test ownership, не replacement counter или fake Native authority.
Download/Site production, написанный мною, этим review повторно не принимался.

## Compiler evidence, поступившее от root во время review

[Root execution](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/native-publication-combined-compiler-20261104.json): один
34-roots affected `deno check`, elapsed **4.925677425 s**, exit1, timeout=false.
[Diagnostics](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/native-publication-combined-compiler-20261104.stderr.log): 21 errors
только в PL tests/owner-contract.test.ts и grading-policy.test.ts — literal
widening и optional assessment/assignment union. Production diagnostics в этом
логе нет, но весь graph FAIL; его нельзя называть GREEN или completed type closure.
Root сообщил remaining **15.074322575 s** из cap20 для изменённой ревизии.
Исправления fixtures должны выражать actual checked producer types/known-positive
условия, не casts/any/новые parses ради подгонки. Повторный compiler этим reviewer
не запускался и не назначался.

## Независимость и пределы

Reviewer `/root/native_publication_consumers` не автор Core/PL Source. Собственный
primary origin/admission остаётся
[native-publication-consumer-source-20261104-admission.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/native-publication-consumer-source-20261104-admission.json):
Sol6.1/high подтверждены session_meta/turn_context собственного полного интервала;
Standard назначен, hidden routing независимо не удостоверяется. Globals не
менялись, subagents не создавались. Root явно поручил этот peer review; нового
Source ownership не принимал.

Только Source/report reads и данный отчёт. Не запускались compiler/tests/Native/
render/inspect/hash/build/install/network/DB/production; прочитан уже выполненный
root compiler evidence. Source freezes, commits/PR/worktrees/results/student data,
курсовые policy/баллы/UUID и mainstate/audit не менялись. Runtime, cache freshness,
full-course delivery/Platform/Gateway и production acceptance остаются вне этого
Source verdict. После docs correction и legitimate fixture correction нужен
узкий delta review; production переписывать по данному report не требуется.

## Final delta review — fixture/docs correction

**APPROVE_SOURCE.** Это текущий независимый Source verdict; первоначальный
NEEDS_CHANGES, единственный docs P2 и первый compiler RED выше сохранены как
история. Прочитаны ровно correction в PL tests/owner-contract.test.ts,
tests/grading-policy.test.ts, docs/authoring.md и обновлённый producer report.
Production повторно не менялась и не расширялась этой correction.

- `input()` получает actual `NativeBusinessInput` с обязательным для этого
  authored case assessment, `work()` — derived assessment view с raw unknown
  extension map. Contextual typing сохраняет реальные enum literals вместо
  widening; genuine Core runtime envelope не фабрикуется. Не добавлены any/casts,
  дополнительные parse или product validators ради compiler.
- Positive assertions устанавливают наличие assessment/assignment и actual
  student-label variant перед чтением. Existing grading assert получил честный
  asserts signature при прежнем runtime условии. Это усиливает positive fixture
  expectation, не скрывает optional producer result.
- Все прежние 17 malformed cases сохранены: 14 assessment variants и три
  config/declaration variants. Они остаются unknown до того же реального decoder;
  mutation через any заменена прямыми malformed raw inputs. Attempts, pass,
  unknown fields, invalid assignment, malformed config и неверный PL атрибут
  не стали типизированными valid DTO. Existing separate missing-default и
  cross-document member negatives сохранены. Grading field pairs используют
  `satisfies [string, unknown][]`, raw extension остаётся Record<string,unknown>.
- P2 закрыт непосредственно рядом с новой CLI командой: current authoring теперь
  описывает partial source/format/composition/policy/question summaries, а native
  mapping и missing-points refusal явно относит к отдельному full course-export.
  Полный gate не возвращён в ordinary code. Новых Source findings по этой delta
  нет.

Первый root combined compiler остаётся зафиксированным **FAIL21** для предыдущей
ревизии (4.925677425 s). Данная correction Source review не является compiler
GREEN. Новый check здесь не запускался; remaining budget/решение принадлежат
root. Runtime/Native также не выполнялись.

Прочитан будущий bounded acceptance plan
`native-publication-bounded-acceptance-20261104.md`: один existing root-pl run с
двумя заранее authored независимыми owners полезен для actual active-PL route
с Download и без него, final ZIP/README/mounted URL/resources/privacy. Source
scope соответствует проверяемому поведению. План честно исключает noPL/inactive,
failed-closing rollback, full-course/cache/production guarantees; опасного
расширения или бессмысленного дополнительного matrix/run не найдено.
Cap60/retry0 и first-failure stop остаются у root; я этот запуск не назначал и
не выполнял.

Own independent high admission и ограничения предыдущего review сохраняются.
Изменён только данный report; Source/config/fixtures/runtime/cache/globals,
commit/PR/worktrees/student data/mainstate/audit не менялись.
