# Task 4 report — U02 object model

Status: **DONE_WITH_CONCERNS**. Authoring complete; owned files frozen for independent review. This is a U02 handoff, not final course acceptance.

Dispatch `U02-01`, author `/root/u02`; accepted input `2365532bca35ad12035c79c681748834f0719954`; freshly fetched remote PR13 baseline `a0d1bdb9d8343809560e3ce7f9a2141d4473ee3f`. Requested `gpt-6.1-sol / ultra`, actual model/effort `not_reported`. No profile substitution claimed. Worktree `/tmp/java-course-pr13-20261013`; package read from `/home/tolya/java-course-codex-handoff-2026-10-13`.

## Delivered paths and scope

- Five theory chapters: `theory/chapters/{objects,classes,inheritance,interfaces,data-classes}.qmd`.
- Three handbook entries: `handbook/entries/{classes,inheritance,interfaces}.qmd`.
- Three practices: `practice/{objects,polymorphism,reserve-models}.qmd`.
- Author packet: `docs/course-revision/packets/U02.md`.
- This report: `.superpowers/sdd/execution-plan/task-4-report.md`.
- Evidence only: `/tmp/java-course-revision-checks/U02/`.

No shared index/config/bibliography/condition/task/asset/U01/model-routing file was edited. No git add/commit/push/merge, service/global-setting changes, subagents, new chats, or publication. The working tree also contains concurrent U01/coordinator changes; those are outside the U02-owned diff.

## Requirement closure

| Requirement | Result / evidence |
|---|---|
| Coherent premise → example → observation → cause → transfer | Existing accurate identity, alias, dispatch, record-model passages checked and retained; Book, input/output alias, Sender and reflection examples substantially revised. Every one of 11 files has a revise/checked-preserve decision and reason in the packet. |
| New exact targets/categories, current owned consumers | 88 frozen U00 definitions present plus prescribed new reflection target; native Pandoc parsed 11/11 files. No remaining mapped original IDs or duplicate native definitions in owned files. Categories exactly match Appendix A, including optional reserve route. No alias/redirect/hidden span/runtime migration. |
| Book plain vs encapsulated | Separate `course.examples.plain.Book` with open fields and `Book(String)`; complete reusable `course.examples.objects.Book` with private title/borrowed, `Book(String)`, title()/borrowed()/borrow(). No mixed field/method variant. Full standalone BookDemo shows true/true and repeat refusal before mutation. |
| Reference value, local parameter swap and self case | PlainBook methods preserve mutation/rebinding distinction and connect to frozen U01 reference-copy premise. Practice defines Test and localSwap, observes unchanged 3 8/true, names self-swap boundary. Existing queue transfer still requires distinct objects; not conflated with swap self-case. |
| Invariant/refusal/stack | Reservation kept as valid adjacent transfer. New IntStackDemo checks full/empty before writing/decrementing and separately prints refusal, size and popped top. Lab char Stack keeps message/`'\0'` policy; no ready solution or hidden exception requirement. |
| Java25 constructors/dispatch/overload | Full prologue→parent→field→epilogue experiment on actual JDK25; explicit restricted early context, narrow simple own-field assignment, illegal reading/calling/escaping and initializer/inherited/compound restrictions checked. Corrected Shape.area premise. C++ constructor dispatch distinction verified against primary draft. |
| Sender/interfaces/composition | One reusable Sender/Reminder client, ConsoleSender and RecordingSender; early StringBuilder operations locally defined, no early List in Java fences. Prefix wrapper and implements/field/delegation distinguished; array-based default sendAll exposes partial A before null failure. Vehicle remains separate existing lab domain. |
| Record/reflection | grade.Student first fully defined here, identical accepted application declaration. Constructor normalization/invariants and all three components retained. Class<?> locally explained before use; visible ReflectionDemo prints s1/true/id:String/name:String/score:int. Instance/Class/record metadata and int.class distinguished. No private reflection/DI/serializer framework. |
| Shallow immutability, enum/sealed minimum | Shared array mutation and two different equal-content arrays yielding record equals=false reproduced. Smalltalk/Self and third sealed variant explicitly additional. No new required language/course/program. |
| G02/G20 and lab freedom/budget | G02 practice corrected: empty failure all, full fixed/circular, dynamic growth/FIFO. Visible L6=270 plus separate30. G20 exact IAE/no-state-change proposal or valid-input alternative sent to U06; bank/rubric unchanged, no hidden grading. Logical layout/helpers remain free. |
| U03/U04/U05/U06/U07/M02 handoff | Packet contains complete Book/Student declarations, exact contexts, expected state/output, source manifest, negatives and sequential requests. U03 ComparisonDemo.Student is explicitly distinct. U01 G05/G12 links use frozen targets; their files not edited. |

## Fresh verification

Actual runtime `/usr/lib/jvm/java-25-openjdk`: `java` and `javac` **25.0.4.1**, no preview. Default Java27 is not used as proof.

| Command / evidence | Fresh result |
|---|---|
| `python3 /tmp/java-course-revision-checks/U02/check_examples.py` | 60/60 source-derived compile/run cases; after harness corrected expected combined composition output, only `sender-composition` was rerun and passed. All current JSON rows green. |
| `python3 /tmp/java-course-revision-checks/U02/check_negatives.py` | 20/20 expected compiler failures/positive early-field and protected-access controls. |
| `python3 /tmp/java-course-revision-checks/U02/check_contracts.py` | 4/4: Student exact accepted source, Student boundaries, reusable Book/Box true/true, record/primitive metadata. |
| `python3 /tmp/java-course-revision-checks/U02/check_structure.py` | Native parse11/11, complete88 frozen definitions, categories, no mapped originals/duplicates. |
| `python3 /tmp/java-course-revision-checks/U02/annotate_manifest.py` | 84 fences:80 Java fences in checked complete contexts,3 explicitly student-solution client templates,1 optional Kotlin comparison. |
| `python3 /tmp/java-course-revision-checks/U02/final_check.py` | Focused final aggregation21/21; includes current source SHA256, all context classifications, visible reflection/L6, no early List code, owned diff whitespace check. |
| `git diff --check -- <11 owned files> <packet> <report>` | No whitespace errors. |

Evidence JSON: `verification.json`, `structure-results.json`, `example-results.json`, `negative-results.json`, `contract-results.json`, `example-manifest.json`, `volume.json`, `baseline-confirmation.json`. Complete extracted sources and stdout/stderr/exit diagnostics live under `examples/`, `negatives/`, `contracts/`. These are author evidence, not published starter/reference/hidden tests or migration infrastructure.

Initial fixture failures were harness-only: object numbering accidentally counted the Kotlin fence in Java-only extraction, and composition expected stdout omitted the earlier console client included in the assembled context. Source course behavior was correct; extraction/expectation corrected and affected cases rerun. These failures are not concealed as course bug fixes.

## Volume, source use and read log

Five theory chapters: **7964→9096 reader units, +14.21%**, measured with the U00 Pandoc lexical algorithm. Notes, code blocks, raw blocks, math and refs excluded; optional prose included. All11 pre-edit snapshots matched remote PR13 exactly. Per-file bytes/lines/prose in volume.json. Global23chapter +30% acceptance remains M05; timing is not inferred from words. No added mandatory tasks or pilot minutes are claimed.

Read task brief/current user instructions, U00 packet and contract main requirements/U02 rows, all11 owned baseline files, current objects/polymorphism bank, exact application Student contract; package specification/decisions/shared authoring/example requirements and relevant EF/P/G. Checked preservation reasons are documented per file in U02.md; preserved bibliography citations do not mean those books were newly fully read.

New selective book reading: *Java Reflection in Action* (Forman/Forman,2005), PDF24–30=printed1–7, chapter1 beginning/§1.1/§1.2.1–1.2.2. Also PDF18–23=front matter xvii–xxii and search of PDF1–15 to locate chapter. Text extraction saved in evidence. Only self-description/introspection motivation used; historical raw-Class/invocation/framework/security advice not copied as modern semantics. No claim of reading the remaining books. Books README read for limits and provenance.

Primary sources checked: JLS25 §8.8.7 and §12.5; Class25 getRecordComponents/isRecord and RecordComponent25 name/type; C++ class.cdtor¶4; Self Handbook message/lookup semantics. Primary URLs are visible in theory/packet. JEP513 retrieval returned403; exact prologue claims instead verified with accessible JLS25 and narrow JDK25 experiments. No new bibliography records required or edited.

## Concerns / downstream work

1. Full student/full rendering, HTML links, extracted-final-example checking, host/container full-course acceptance and pedagogical pilot belong downstream. They were not performed or claimed here. M03 must close foreign current consumers; future anchors are not repaired with old aliases.
2. Practice polymorphism's three Java fences are labelled clients of the student's Circle/Vehicle/queue solution, with named wrappers, required declarations and expected observations. They deliberately are not standalone ready lab implementations. M02 must preserve that classification instead of inventing required solutions. Kotlin comparison contains two complete functions; no Kotlin compiler run claimed.
3. U06 owns visible G20 error policy and final G02 bank wording; do not add inferred exceptions/state expectations to hidden/manual grading before the condition is published. U07 owns slot5 timing/readability, and U08 independent pedagogical closure.
4. U03/U04/U05 consume the full canonical declarations and preserve their packages/API. U01 reference-copy premise and exact G05/G12 targets were sufficient; no change request to U01 semantics is pending.

Owned files are now frozen. Further subject edits require coordinator return of the U02 window. No unfinished authoring work remains within this dispatch.


## Fix round 1 — independent review I1/M1

Returned window: only `theory/chapters/data-classes.qmd`,
`theory/chapters/classes.qmd`, own packet/report/evidence. Read the full
`task-4-review.md` and verified both findings against the visible source.

- I1: after component output, visible prose now says `int.class` is a Class
  object describing primitive int, score 90 is still an int value rather than
  an Object instance, and obtaining that description does not box it into Integer.
- M1: stack observation now identifies the second call `push(9)` as refused;
  `push(7)` remains the successful first call.

Fresh focused evidence `verification-fix-1.json`: **45/45 PASS** from
`python3 /tmp/java-course-revision-checks/U02/check_fix_1.py`.
The before snapshot `.superpowers/sdd/execution-plan/u02-fix-1-before`
was compared exactly: only the two requested prose sites changed, all code
fences/definitions/attributes/categories stayed identical. Native Pandoc
parsed the two affected files. Manifest source lines and current source
SHA256 in verification.json updated; code hashes and prior Java results retained.
Current reader units:7964→9096 (+14.21%).

No 60/20/4 Java suites repeated; no new code or examples. No Git operation,
journal edit, other-author file, or remaining frozen U02 file touched in this
round. Full render/host/container/pilot limitations remain unchanged.
Both returned QMD files are now frozen again for independent fix review.
