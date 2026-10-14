### Spec Compliance

- ✅ **Spec compliant. R1 Wrapper-delivery ruling: APPROVED.** The frozen correction is sufficient for U05 to implement the supplied helpers and finish its delivery proof. No actionable defect was found in this bounded three-Markdown-file diff.
- ✅ The correction preserves the canonical application and student work: Student normalization/validation, two-argument CLI, stdout/error statuses, parser/loading/report/Main.run TODOs, manual target, and separation of starter from reference/closed tests remain intact (`docs/course-revision/structure-contract.md:148`–`:165`; `docs/course-revision/packets/U00-R1-wrapper-delivery.md:46`, `:76`).
- ✅ The exact public manifest contains 17 paths under the existing student root; `.mvn` becomes generated local state from the single public properties source, and stock Wrapper scripts remain unchanged (`docs/course-revision/packets/U00-R1-wrapper-delivery.md:22`–`:44`, `:54`–`:67`). This is a provided build route, not extra student implementation or a change to PL17/grading/time budgets (`:18`, `:95`–`:100`).
- ⚠️ **Not yet verified:** implemented helper behavior or a complete native starter export→clean unpack→POSIX build. These are explicitly required U05/M02 deliverables, with final materialization/delivery checks assigned to M04/M05. Approval of this ruling does not substitute for them (`docs/course-revision/packets/U00-R1-wrapper-delivery.md:95`, `:98`–`:100`, `:108`).
- ⚠️ Windows remains a supplied, statically specified, unexecuted counterpart. The packet reports that limitation honestly and introduces no new Windows-runtime blocker for the primary POSIX gate (`docs/course-revision/packets/U00-R1-wrapper-delivery.md:89`).

### Strengths

- **Both demonstrated native constraints are addressed.** The pinned runtime selects student and drops dot-paths (`tasks/_extensions/Afonenko-Course-Tools/project-download/infrastructure/runtime.ts:204`–`:211`); its ordinary-resource branch rejects dot/private paths (`:220`–`:227`). Native ZIP central entries do not encode Unix file modes (`tasks/_extensions/Afonenko-Course-Tools/project-download/domain/zip.ts:26`–`:28`). The root-supplied marker evidence records mode0644, direct PermissionError13, and successful interpreter invocation. Public `wrapper/maven-wrapper.properties` plus `sh ./build.sh` and an interpreter call to stock mvnw addresses both constraints without vendor changes (`docs/course-revision/structure-contract.md:157`, `:167`).
- **The author handoff specifies observable helper behavior.** Own-directory resolution, spaces/foreign-cwd handling, exact configuration bytes on every run, refusal before Maven, argument boundaries, output/exit propagation and stable target location are explicit (`docs/course-revision/packets/U00-R1-wrapper-delivery.md:80`–`:85`). U05 is not left to invent a competing build mechanism.
- **The ZIP-hash fallback risk is handled correctly.** The permitted official prototype mvnw reads `.mvn/wrapper/maven-wrapper.properties` at lines111–118, switches ZIP to tar.gz without unzip at lines178–180, and uses sha256sum/shasum at lines226–248. The packet requires those tools in the prepared environment and specifies exact refusal diagnostics rather than changing the selected archive/hash (`docs/course-revision/packets/U00-R1-wrapper-delivery.md:81`, `:87`). Windows's PowerShell requirement agrees with the stock mvnw.cmd entry point at lines35 and54.
- **Evidence limits are explicit.** Local script/property hashes and student/reference equality are distinguished from U05's earlier official-source provenance. The marker ZIP probe is not represented as a full Maven starter build; actual native export and archive contents are required later (`docs/course-revision/packets/U00-R1-wrapper-delivery.md:9`, `:50`, `:67`, `:102`, `:108`).
- **Starter failure is not hidden.** M04 must compile the starter with the provided helper while recognizing expected TODO behavior-test failures; completed reference must pass test/package. This preserves the meaningful implementation boundary (`docs/course-revision/packets/U00-R1-wrapper-delivery.md:99`).

### Issues

- **Critical:** none found.
- **Important:** none found.
- **Minor:** none found.
- **Remaining implementation acceptance, already assigned:** U05 must produce both helpers and actual native model export evidence; check the 17-file archive, absent `.mvn` before execution, byte-identical generated properties, mode0644 operation, repeat execution, paths with spaces/foreign cwd, missing-tool/config refusal, Maven failure propagation, and build/run of the completed implementation. M02 independently repeats; M04/M05 validate the final materialized/public artifact and private boundary (`docs/course-revision/packets/U00-R1-wrapper-delivery.md:95`–`:102`). These are proof limits of the current authoring ruling, not additional U00 defects or newly added gates.

### Review Evidence and Scope

- Read the changed lines and relevant context of `.superpowers/sdd/execution-plan/u00-r1-wrapper-review.diff`, the complete bounded report, and `/tmp/java-course-revision-checks/R1-wrapper-delivery/u00/verification.json`, `verification.log`, `verify-ruling.py`. Existing evidence reports **27/27 PASS**, failures `[]`, with comparisons preserving all contract sections outside the permitted build/delivery change, the unaffected application/TODO/private text and prior packet history. The final log has no warning or traceback.
- For the concrete delivery risk only, read the named pinned runtime/config/ZIP selection code, root native-mode JSON, and the relevant stock Wrapper-script clauses from the permitted U05 prototype. Native path-selection feasibility and interpreter invocation are supported by that code/evidence; no full export/build execution is inferred from static screening.
- Did not reread general course content, run Git, rerun verification/tests/native probes, modify project/vendor/source/journal/index state, or create agents/chats. Only this review file was written. The original accepted U00 application, budgets and other decisions were outside re-review scope.
- Reviewer is the same independent U00 reviewer; requested Astra/ultra, effective configuration `not_reported`; input supplied by root `16eaa3d0eb9e83c6502ec3065fca5a66aedcc250` plus the frozen correction. This review is independent of U05 implementation and final U08 acceptance.

### Assessment

**Task quality: Approved.**

**Reasoning:** The correction provides a native-compatible source layout and sufficiently exact helper contract while preserving stock Wrapper bytes, student work, privacy boundaries and existing course scope. U05 can now implement the frozen route; runtime delivery acceptance remains pending exactly where the packet says it does.
