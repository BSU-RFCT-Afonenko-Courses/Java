### Spec Compliance

- ❌ Issues found: the requested cross-project consumer inventory is present but contains incorrect target identities; fix the Important finding before passing the `xrefs.json` handoff to U00. Other visible M00 deliverables match the inventory-only scope (`docs/course-revision/baseline.md:3–41`, `docs/course-revision/packets/M00.md:5–17`).
- ⚠️ Cannot verify from diff alone: all unchanged course declarations/contracts, source preservation, platform pin/runtime digests and environment commands. Focused existing evidence supports the reported config/page/project counts and exact tool availability; source preservation and digest correspondence remain controller checks. Fresh render, test/grading execution and publication-leak acceptance explicitly belong to later tasks (`docs/course-revision/baseline.md:19,31,37–39`), so their absence is not a M00 failure.

### Strengths

- The baseline distinguishes source counts, historical rendered counts and future acceptance, and distinguishes M00's Docker denial from the controller's successful escalated read (`docs/course-revision/baseline.md:15,29,37,41`).
- Public starter/test roles and private reference/hidden/mutation roles remain separate inventory categories; the manual bank is not claimed automated (`docs/course-revision/baseline.md:17–19`).
- The U00 packet names external evidence, exact tooling, remaining gaps and the user's overriding fresh-ID policy without claiming later execution (`docs/course-revision/packets/M00.md:7–17`).
- Package failure and successful checksum-only payload verification are both disclosed, rather than replacing the failed result with a success claim (`docs/course-revision/baseline.md:33`; existing `logs/package-original.log:1` and `logs/package-payload-view.log:1`).

### Issues

#### Critical (Must Fix)

- None.

#### Important (Should Fix)

- `docs/course-revision/baseline.md:5,15` and `docs/course-revision/packets/M00.md:15` advertise `xrefs.json` as a consumer inventory, but `/tmp/java-course-revision-checks/M00/inventory.py:19–20` admits trailing sentence punctuation into `target`. For example, `/tmp/java-course-revision-checks/M00/xrefs.json:35` stores `sec-t-io.` for `theory/chapters/application.qmd:12`; the actual ID is `sec-t-io`. A focused comparison against the already-produced ID inventory found **357** period-suffixed targets whose trimmed ID exists and whose recorded target does not. U00's required ID/consumer rekeying cannot safely use those identities as recorded. Correct the extractor to respect reference token boundaries, regenerate only the affected inventory, and verify representative sentence-final and bracketed references. Keep the source-location evidence and clarify whether the reported count includes non-section/exercise types: the extractor also admits `fig/tbl/lst/alt` while baseline line 15 labels the total section/exercise references.

#### Minor (Nice to Have)

- None beyond the count-label clarification included with the inventory correction above.

### Assessment

**Report quality:** Needs fixes.

**Reasoning:** The report is appropriately bounded and candid about environment/package concerns, but its claim of a usable cross-project target inventory overstates the artifact's accuracy. Correcting that focused handoff defect is sufficient; no broad render or grading rerun is requested.

**Task quality:** Needs fixes.

**Checks performed:** Read the provided diff once; read brief/report/user instructions and package specification; inspected existing summary/config/page-order/declaration/environment/package/owner-inventory evidence and owner exit files. Named outside-diff risk: exact xref identity extraction for U00; inspected extractor lines 16–20, two source lines and the existing xref/ID JSON, then ran a read-only JSON comparison that counted 357 malformed target identities. No course/report edits, Git commands, test suites, rendering, grading, commits or subagents. Requested reviewer profile was gpt-6.1-sol / medium; effective runtime profile was not independently reported.
