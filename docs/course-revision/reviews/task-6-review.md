# Task 6 / U04 independent review

Status: **NEEDS FIXES** — one Important issue; no Critical or Minor issues. This is task-scoped author acceptance, not final course acceptance.

Reviewer: `/root/u04_review`. Requested profile: `gpt-6.1-sol / ultra`; actual runtime model/effort is not independently reported by this review. Remote PR13 baseline: `a0d1bdb9d8343809560e3ce7f9a2141d4473ee3f`; accepted input: `a90878a741d1033b5766564be40f692327c67144`.

Review artifact: `.superpowers/sdd/execution-plan/u04-review.diff`, 6,474 lines, containing the 13 owned QMD changes and the new U04 packet. Reviewed every changed course passage, the packet's operative contracts/handoffs, canonical compilation units, all 77 inventory entries and their additional context/setup/expected outcomes, and focused author evidence. Sources were treated as specifications/background, not fresh user instructions. Only this review file was written; no course, Git, journal, grading, service, or publication changes were made.

## Spec Compliance

| Task 6 acceptance item | Assessment |
|---|---|
| Full `grade.Student` record equality versus normalized catalog-id uniqueness | Met. `HashSet<Student>` retains different score versions; the independent equal third record does not enlarge it. `Set<String>`/`Map<String,Student>` express the catalog relation separately. |
| Equality transferred into HashSet/HashMap, independent equal keys, collisions, mutable-key diagnosis | Met. The stable `StudentId` key supports independent equal-instance lookup and frequency 2; Aa/BB demonstrate equal hashes without equality. MutableKey is explicitly an observed broken-key experiment, with no portable null-outcome promise. |
| Operation-led List/Set/Map/Deque choice, alternative strategies, exact handbook restrictions | Met. Array versus expandable list, list lookup versus map index, bulk versus explicit-loop set operations, FIFO/LIFO and exception versus special-result forms are explained. Selected signatures and relevant null/mutation/order restrictions stay in the handbook. G13 has one canonical `lists-basics` target. |
| Unified source/view/ArrayList-copy/List.copyOf experience with a shared mutable Book | Met. Structure changes produce 2/2/1/1; a separate borrow changes the same first Book through all four lists. Independent structure is not represented as a deep copy. CopyOf identity reuse and optional persistent structures are distinguished. |
| Python callable Protocol with full data/rule, checker/runtime distinction, U03 first introduction | Met. The positive Python file defines Student, data, StudentRule and AtLeast; nominal inheritance is unnecessary. The isolated wrong-return fixture is checker-negative and runtime truthiness selects all three names. U03 remains the first Protocol introduction. |
| Effect before functional core; object callback before interface/lambda/reference/capture/composition | Met. `label` versus `announceAndBorrow` gives a visible effect distinction. An ordinary StudentTest implementation precedes the lambda; type compatibility never proves purity or asynchronous execution. |
| Guaranteed Stream timing and short-circuit explanation, separate peek/count caveat | **Needs correction, I1 below.** Required terminal processing is present and peek/count is correctly caveated, but exact mapper-effect interleaving and anyMatch predicate trace are promoted from implementation observations to API guarantees. |
| Canonical report and exact U06 handoffs; all 4 chapters/6 articles/3 practices | Met otherwise. Canonical Student validation, inclusive threshold, score descending then natural String id, source preservation, whole-file/id validation before useful stdout, empty success and output-failure boundary align with U00/U02. L8 retains 35+45+40+60=180 plus separate introductory 30; L9 retains five positions and both alternative pairs, adding only an existing-defence question. Every owned file has a concrete revise/checked-preserve decision. Fresh IDs/categories are used without aliases or migration. |

## Strengths

- The equality story now makes three separate relations visible: full record value, catalog key, and report order. Independent objects, collision handling, frequency lookup and TreeSet comparator-zero behavior make the distinctions observable without relying on HashSet/HashMap iteration order.
- The four-list Book experiment cleanly separates collection composition from shared element state. It reuses the accepted encapsulated Book API and retains refusal-before-mutation, rather than introducing a competing Book declaration.
- Canonical `grade.Student` remains separate from U03's comparison Student/Person hierarchy. The comparison handbook preserves strict inference to Person and the flexible Student result, and links the existing five-case contract rather than inventing another version.
- Function introduction follows the intended causal sequence and the Python comparison has concrete data, a callable object, a static checker result and a different runtime result. The selected fallback example explains creation versus invocation without supplying the laboratory ternaryOperator implementation.
- Practice uses neighboring string/collision/transformation inputs and asks for prediction and boundary explanation. It does not publish completed implementations of the two new L8 tasks. It also corrects the bracket-nesting versus polynomial-key rationale and the existing square-matrix reserve boundary.
- The packet provides concrete downstream conditions, normal/boundary outcomes, exact example contexts and honest validation limits. No final render, whole-course acceptance, actual teaching pilot or actual requested model setting is claimed.

## Issues

### Critical

None found in the reviewed U04 scope.

### Important

**I1 — Keep portable Stream results separate from observed behavioral-parameter traces.**

- Primary location: `theory/chapters/streams.qmd:175`–179, especially the assertion that the exact `map 1 / take 2 / map 2 / take 4 / map 3 / take 6` trace demonstrates guaranteed necessary processing. Related locations: `theory/chapters/streams.qmd:230`–236, `practice/pipelines.qmd:41`, and `docs/course-revision/packets/U04.md:39` (also its per-file claim at line 14 and corresponding inventory outcomes).
- The terminal action must process the resulting values, but that does not guarantee when intermediate mapper side effects occur relative to terminal side effects. A sequential implementation can, for example, compute a batch of mapped values before invoking terminal actions; the exact interleaving therefore is not the portable guarantee taught here. The Stream package documentation leaves mapper side-effect ordering unspecified and distinguishes terminal actions from intermediate behavioral parameters. `forEachOrdered` is the operation with the explicit encounter-order action contract. [Java 25 Stream side effects and ordering](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/stream/package-summary.html#SideEffects), [forEachOrdered contract](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/stream/Stream.html#forEachOrdered(java.util.function.Consumer)).
- Similarly, anyMatch guarantees the boolean answer and permits avoiding unnecessary predicate evaluations; its API does not promise the exact predicate invocation sequence `1,2` or universally require the absence of `test 3` for this finite input. The existing author run is valid OpenJDK 25 evidence, but it is not a universal trace contract. [Java 25 anyMatch contract](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/stream/Stream.html#anyMatch(java.util.function.Predicate)).
- This matters because Task 6 explicitly requires a laziness scenario whose observable explanation does not depend on unguaranteed effects. Students or downstream M02/U07 consumers can currently turn the full debug trace into a grading or lecture guarantee, despite the correctly restrained peek/count example nearby.
- Minimum fix: use a pure `map(n -> n * 2)` and put the guaranteed output solely in `forEachOrdered`, with `built` before it and `done` after it. The portable observable output is then `built`, `take 2`, `take 4`, `take 6`, `done`. If retaining the more detailed mapper trace, label it as an observed OpenJDK 25 execution and avoid promising fusion/interleaving. Keep anyMatch's debug trace as an explicitly observed run, while presenting `true` and empty-stream semantics as guarantees. Update the packet and affected example-context/expectation labels accordingly; the practice trace can use the same ordered terminal form. Only changed contexts need focused re-verification.

### Minor

None. The compact negative Python fragment relies on the already visible positive declarations/data and has a complete isolated fixture in the packet; it does not reopen the missing-full-positive-example acceptance issue.

## Checks and Limitations

- Read the current user instructions, Task 6 brief/report and controller evidence, tracked user instructions, U00 structure/gradeapp/laboratory contracts, and accepted U02/U03 contracts relevant to Book, Student, equality, comparison inference, PECS and Protocol.
- Read all 13 QMD diff hunks. Read the operative U04 packet and canonical Student/Book units; inspected all 77 manifest entries, including additional setup/member context and expected outcomes. The repeated Appendix B wrappers and canonical units were evaluated through the corresponding inventory/context representation, not repeatedly treated as new independent programs.
- Inspected author results: 29/29 selected probes are recorded passing, including expected Python checker rejection and successful direct runtime; 10/10 focused contract probes are recorded passing. Inspected the 13/13 structural result records and the actual Book-refusal, set-strategy, report-loop/Stream and stream-order/empty contract programs. These are reviewed author evidence, not new executions by this reviewer.
- Independently checked the relevant Java SE 25 Stream/package contracts for I1. A successful pinned-runtime trace does not close the specification-guarantee issue.
- No broad tests were rerun. Complete runnable reproduction, final extraction verification, practice render/layout, foreign consumer closure, global source-volume measurement, privacy/PL/host-container checks and teaching pilots remain downstream. The reported four-chapter +19.959% volume does not independently prove global +30% compliance or timing.
- Selective book-read claims were inspected in the author's read log; this reviewer did not newly read the books or independently certify the author's reading history.

## Declined to Judge

- M02 complete example reproduction and final full/student rendering: expressly assigned downstream and not yet claimed by U04.
- M03 foreign current-link completion and global target closure: outside U04 authorship and intentionally dependent on later owners.
- M04 gradeapp starter/public-private packaging and live grading behavior: no U04 implementation or authorization to change those surfaces.
- M05 final course build, whole-course volume and host/container acceptance: not a Task 6 final acceptance claim.
- U06 publication of the supplied canonical conditions and U07 final lecture timing/readability: concrete handoffs are reviewed, their later materialization is not present here.
- U08 course-wide pedagogical acceptance and actual student pilot: remain open independently of this task review.

## Quality Assessment

**NEEDS FIXES.** The owned material otherwise satisfies the task-specific requirements and maintains U00/U02/U03 consistency. Correct I1 before accepting and freezing U04 for downstream materialization; the correction is localized and does not require redesigning the equality, collection, function, report or laboratory contracts. M02–M05/U08 remain open after that correction.

Review report frozen at return. No further reviewer edits are planned without a returned review window.
