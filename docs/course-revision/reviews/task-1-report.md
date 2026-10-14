# Task 1 report — M00

Status: **DONE_WITH_CONCERNS**. Dispatch M00-01 / `/root/m00`; requested gpt-6.1-sol / medium; effective configuration not reported. Input HEAD `a0d1bdb9d8343809560e3ce7f9a2141d4473ee3f`.

Completed all M00 baseline/inventory requirements in the task brief. Read current user instructions and package specification, decisions, tools, acceptance, gap register; inspected root and five-project Quarto configs, render/check CI and toolchain/workflow, tasks README, L1–L3 labs and project README/contracts/role layout. No books were read, quoted or used in this mechanical inventory.

Persistent deliverables:

- `docs/course-revision/baseline.md`: source counts, exact paths/versions, contract/public-private inventory, original-local-vs-remote difference, package integrity and gap carry-forward.
- `docs/course-revision/packets/M00.md`: routing identity, exact input, concerns and U00 handoff.
- `/tmp/java-course-revision-checks/M00`: machine-readable config/order/page/ID/xref/include/Java-fence/lab-project/declaration/mutation/environment/package evidence, native owner manifests, complete logs, checksum payload view and archived generated output.

Confirmed original local HEAD `eb025d5ad0bc68e9e0054f43c8008b81df98a587` differs from remote input by 987 files; local→PR13 is 42794 insertions/16460 deletions (PR13→local reverses those totals). Original `/home/tolya/Java` was not changed; its HEAD was read only. Working initial/final status lists only untracked `docs/course-revision/` documents; tracked diff is empty.

Inventory: 118 QMD source files in the five subprojects (26 theory /10 lectures /27 handbook /14 practice /41 tasks), plus root portal. Main corpus 23 chapters /25 handbook entries /13 practice introductions. Six theory parts and current page ordering captured. Source scan has 619 Java fences, 876 course xrefs (586 `sec`, 290 `exr`, 723 qualified; supported `fig`/`tbl`/`lst`/`alt` types have zero occurrences), IDs and include paths; scan counts are distinct from rendered catalog counts. All 17 explicit task projects have required role paths; all 40 contract mutation fixture references exist. L1–L3 = 6/4/4 required positions, 7/5/5 candidates, three random alternative groups with assignment persistence; remaining bank is manual.

One-line checks:

- `python3 /tmp/java-course-revision-checks/M00/inventory.py`: exit 0; machine-readable inventory/environment/package evidence produced.
- `quarto run .../course-core/entrypoints/project-checks.ts --book tasks --output .../declared-checks.json`: exit 0, 17 projects; `logs/owner-collect.log` and `.exit`.
- `python3 <exact-platform>/tools/check-course.py inventory --manifest .../declared-checks.json --scope declared --output .../owner-inventory.json`: exit 0, 17 ready /0 missing; `logs/owner-inventory.log` and `.exit`.
- Original `verification/verify-package.py`: exit 1, sole message `Excluded payload found: .git`; empty environment-added metadata directory, no supplied file differences.
- Same unmodified script over independently copied checksum-listed payload: exit 0, integrity/DAG/routing/84 internal links PASS, 13 books/3 documents/15 tasks. Original package untouched.
- Default Java27/javac27 and CUE devel language0.16.1 logged; exact JDK25.0.4.1/java+ javac, Quarto1.11.5, CUE0.17.1 verified exit 0.
- Exact Platform HEAD `b971a61a2386fbf4ae7fcfc9394d307359bc7889`, clean status, both Java runtime profile digests match toolchain; Python3.14.7 and jsonschema4.26.0 present.
- Default sandbox `docker version`: exit 1, client29.8.2 but socket denied. Controller independently escalated same read: exit 0, Client/Engine29.8.2 API1.56, containerd2.4.1/runc1.5.2; controller evidence explicitly distinguished from M00 command.

Output variables assigned in inventory process and saved `environment.sh`: TASK_CHECK_OUTPUT and CI_CHECK_OUTPUT `/tmp/java-course-revision-checks/M00`, CI_LOG_DIR its `logs`, XDG_CACHE_HOME its `cache`, explicit JAVA_HOME/CUE/PATH/PL_PLATFORM_ROOT. No installs or global settings changes. Owner collection temporarily writes exports and caches under source; transient untracked YAML/JSON were owner-cleaned; ignored `.quarto`, `tasks/.quarto`, `tasks/_generated` moved outside source after controller confirmed none pre-existed. Course source content/pins remain untouched.

Concerns/carry-forward: G01–G24 semantic/editorial/contract register copied intact for U00; no authoring decisions or invented course sections. G23 actual JDK25 availability is resolved, execution still belongs to M02/M05. Final render student→full→student, host/container delivery, leak checks and pedagogical review were not run and are not claimed. Docker/CI execution may need sandbox escalation. Historical 1024/0/1262/119 QRC and 141/9590/4389/0 HTML counts remain prior audit only. Fresh IDs/tags with all current consumers, no aliases or migrations, supersede package legacy-ID advice. No commit performed.

## M00 fix round 1

Read `task-1-review.md` and verified the Important finding against source and the installed QRC Lua request/target rules. The original regex allowed sentence-ending `.` into identities. Corrected `/tmp/java-course-revision-checks/M00/inventory.py` to use the shared extractor in `xref_inventory.py`; regenerated only `xrefs.json`, dependent per-page xref counts and summary, plus focused verification evidence. Added 1-based columns, exact source tokens, explicit reference types and enclosing bracket options. Internal periods/hyphens are retained; terminal punctuation is excluded.

Focused command: `python3 /tmp/java-course-revision-checks/M00/xref_inventory.py`, exit **0**; log `/tmp/java-course-revision-checks/M00/logs/xref-fix-verification.log`, exit file alongside. The script invokes installed `quarto pandoc --from markdown --to json <source>` for all 118 inventoried QMD files and compares course-reference identifier multisets with native Cite nodes. All 118 match; every recorded line/column points to its source token; no target retains a terminal period. Exactly **357** targets corrected; count remains **876**, **723** qualified, types **586 sec /290 exr**, zero fig/tbl/lst/alt occurrences. Updated baseline count labels accordingly.

Representative assertions pass for `theory/chapters/application.qmd:12` → `sec-t-io`, optional `[exr-balanced-brackets]` at `tasks/labs/collections.qmd:25`, and random alternatives at `tasks/labs/arrays.qmd:27`; option text remains associated with each consumer. A synthetic internal-dot/hyphen token and bracketed exercise option also pass. Native whole-corpus comparison, representative examples and exact source locations are saved in `/tmp/java-course-revision-checks/M00/xref-fix-verification.json`.

The first attempt completed all corpus parser comparisons but its final optional-option test selected an earlier prose occurrence at line 19 rather than the intended bracket at line 25; exit 1. Corrected the sample selector and repeated the focused command, yielding exit 0. Disclosure is retained in `logs/xref-fix-first-attempt.log`. No failing attempt overwrote `xrefs.json`.

Clarified diff direction: local→PR13 42794 insertions/16460 deletions; PR13→local reverses the totals. No native owner inventory rerun, rendering, grading, course edits, tool/pin changes, subagents or commits. Status remains DONE_WITH_CONCERNS with later acceptance open as originally planned.
