# Task 6 / U04 fix 1 independent review

Status: **APPROVED — spec and quality** for the returned I1 correction. No remaining Critical, Important or Minor findings in this fix scope. This closes the sole finding from the original U04 task review; it does not grant final course acceptance.

Reviewer: `/root/u04_review`, same independent reviewer as the original Task 6 review. Requested profile: `gpt-6.1-sol / ultra`; actual model/effort is not independently reported by the tool interface. Reviewed `.superpowers/sdd/execution-plan/u04-fix-1-review.diff` (971 lines, three affected files), original I1, the Task 6 report's fix append, and new evidence under `/tmp/java-course-revision-checks/U04/fix-1`. Only this review report was written.

## Spec Compliance

- **I1 is resolved in theory.** `theory/chapters/streams.qmd` now uses pure mapping and `forEachOrdered` terminal printing. The guaranteed observation is `built`, `take 2`, `take 4`, `take 6`, `done`, with the source encounter order explained. The text explicitly declines to promise mapper invocation count, order or mapper/terminal interleaving.
- **I1 is resolved in practice.** `practice/pipelines.qmd` uses the same pure-map/ordered-terminal demonstration and distinguishes its required terminal effects from unguaranteed peek/count effects. No new laboratory task, technique or workload was introduced.
- **The anyMatch distinction is consistent.** The theory states `true` as the guaranteed answer and identifies `test 1 / test 2 / true`, including the absence of `test 3`, as an observed OpenJDK 25 run. Empty-stream boolean semantics remain explicit. The finite-input trace no longer promises an exact predicate invocation sequence or count.
- **The packet propagates the correction.** Per-file decisions, the frozen Stream/report contract, U07 oral route, M02 instructions, requirement closure, Appendix B contexts and the fix appendix all use the same distinction. The current manifest supersedes the historical pre-fix manifest without rewriting the old execution evidence. The anyMatch entry is classified `diagnostic`; its debug prefix is explicitly excluded from portable assertions.
- **Collateral requirements are preserved.** The diff changes only the intended Stream explanations, matching practice example, and packet metadata/handoff claims. The equality, Book/Student, report, L8 35+45 handoff and L9 five-position/alternative contracts are not revised. Identifier/category preservation is recorded for both affected QMD. Other example changes in the packet are line-coordinate updates and separator whitespace, not new program behavior.

## Strengths

The correction is localized and explains the underlying distinction rather than merely changing the method name. It gives students a predictable before/after demonstration while keeping short-circuit behavior observable as a runtime experiment. Historical logs are retained honestly, and current sources, hashes, portability labels and downstream instructions agree.

The focused runner also treats the anyMatch case correctly: its acceptance check uses the guaranteed `true\nAda\n` suffix, while storing the actual debug prefix as observation. It does not make `test 1`, `test 2` or the absence of `test 3` a required oracle.

## Issues

### Critical

None.

### Important

None. Original I1 is closed.

### Minor

None.

## Checks and Limitations

- Read every hunk of the three-file correction and the fix report append. The former mapper-interleaving guarantee is removed from active claims; the new U07/M02 handoffs expressly prohibit converting the observed anyMatch debug trace into lecture or grading guarantees.
- Inspected the new current manifest: 77 entries remain; only the three returned contexts have new source records. Read all three new primary programs and independently checked their source hashes against the current manifest and the inclusion of their exact fenced code. The two terminal traces have portable stdout labels; anyMatch has a diagnostic label and a separate portable oracle.
- Inspected `run-results.json`: 3/3 changed contexts record successful compilation with actual javac 25.0.4.1, `--release 25`, no preview, and successful execution on OpenJDK 25.0.4.1. Both ordered terminal traces produce the declared output. The diagnostic observation is stored separately from its portable suffix check. These are reviewed author runs; this reviewer did not rerun them.
- Inspected `native-parse.json`: 2/2 affected QMD record successful native parsing and preserved IDs/categories. Inspected `verification-fix-1.json`: all 411 recorded checks pass, including source/context consistency, historical evidence preservation, the other 11 owned QMD remaining unchanged, append-only report and unchanged canonical Appendix A. Historical preservation was assessed from this new evidence; the original 523 files were not reread or rehashed by this reviewer.
- Inspected `volume.json`: four-chapter reader units are 7330→8888 (+21.255%), using the same stated U00 algorithm. Only the changed Stream chapter was recalculated; the other three measurements are carried forward with recorded unchanged hashes. This does not establish the eventual whole-course volume or teaching-time budget.
- Did not reopen unchanged wider material, repeat the original 29/10/13 suites, run broad tests, render slides, or change course/Git/journal files. The API interpretation is the one established in the original I1 review; the correction conforms to it.

## Declined to Judge

M02 complete reproduction/render, M03 global consumer closure, M04 starter/privacy/PL integration, M05 final course checks, U07 final lecture timing/readability and U08 course-wide pedagogical acceptance remain downstream. None is implied by this approval.

## Quality Assessment

**APPROVED.** I1 is fully corrected across source, packet claims, handoffs and current example expectations. The fix maintains the accepted U04 requirements and can be accepted for downstream materialization. Full M02–M05/U08 acceptance remains open.

Review report frozen at return; no further edits without a returned review window.
