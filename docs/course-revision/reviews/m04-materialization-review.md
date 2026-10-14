# M04 materialization substep — independent technical review

**Spec compliance: APPROVED for the ready materialization substep.**

**Technical/report quality: APPROVED.** No Critical, Important or Minor findings. Review frozen; full M04/Task13 remains open and its dependency gates are not waived.

## Complete asset coverage

Read the full materialization dispatch, technical-review template, Task13 brief, current materialization packet and scratch report. Inspected operative U05 canonical API/TODO boundaries, U06 exact25 manifest and all thirteen private-test-to-visible-contract mappings, R1 exact17/helper/Wrapper delivery decisions, accepted M02-U05/U06 boundaries, and original M01 path failure. Read all25 destination files; reread initially truncated Wrapper/POM/sample/CLI portions separately. No background books or unrelated example appendices were needed for this byte-preserving substep.

Independently compared **every** manifest row between accepted U05, independent M02 and materialized manifests. For each row verified explicit source, M02 author_source, relative/absolute destination, SHA256, bytes, mode and visibility. Independently read the current U05 source, M02 source and course destination for all25 and checked their actual hash/size/mode; all match. Exact destination file set is25 with no extras or symlinks. Coverage by destination (each row below fully checked):

| Role | Paths below `tasks/projects/gradeapp/` | Result |
|---|---|---|
| Public instructions | `student/README.md` | match |
| Public bootstrap | `student/build.cmd`, `student/build.sh` | both match |
| Public stock Wrapper | `student/mvnw`, `student/mvnw.cmd`, `student/wrapper/maven-wrapper.properties` | all3 match |
| Public build | `student/pom.xml` | match |
| Public samples | `student/samples/blank.txt`, `duplicate.txt`, `students.txt` | all3 match |
| Public Java | `student/src/main/java/grade/DataFormatException.java`, `Main.java`, `Reports.java`, `Student.java`, `StudentFiles.java`, `StudentParser.java` | all6 match |
| Public examples | `student/src/test/java/grade/PublicExamplesTest.java` | match |
| Root description | `README.md` | match |
| Private Java | `reference/src/main/java/grade/DataFormatException.java`, `Main.java`, `Reports.java`, `Student.java`, `StudentFiles.java`, `StudentParser.java` | all6 match |
| Private contract | `tests/junit/GradeAppContractTest.java` | match |

Full source/hash/mode mapping is correctly disclosed in `docs/course-revision/packets/M04-materialization.md:13–37` and `materialized-manifest.json`. Roles count17 public +1 root README +6 private references +1 private test. Actual mvnw mode0755 and every other file mode0644 match accepted materialization metadata; future native ZIP mode0644 is a separate delivery property, not contradicted by source mode0755.

## Boundary and integrity checks

- Student tree contains exactly the accepted public17 and no private harness/reference code or generated `.mvn`, target, class/JAR/cache assets. Root README identifies the public/private layout without supplying a solution. Physical private7 remain outside student. This is source separation only; published ZIP/resource leak acceptance remains downstream (`M04-materialization.md:9,65`).
- Exactly five throw-UOE TODO bodies remain: parse/read/selected/line/run(Path,int). Canonical Student and supplied exception/CLI/printReport remain intact; complete private implementations were not copied into those TODO bodies (`M04-materialization.md:41–43`). Source equality covers the provided helpers, Wrapper headers/CRLF, pinned properties and POM without inventing build instructions or student work.
- All thirteen private `@Test` methods have the accepted visible U06 API mapping (`U06.md:315–335`); four public examples and the current visible manual gradeapp condition preserve canonical two-argument CLI/status0/2/3/4, cause/physical-line/read-only/whole-input/output-prefix/System-stream boundaries. Loop/Stream alternatives remain allowed. Source examination is not fresh execution; the substep adds no new hidden requirement, PL18 registration or architecture restriction.
- Entire before/after prototype inventories agree; independently verified the current U05 prototype's71 files and M02 prototype's25 against recorded hashes/bytes/modes. Entire existing17 before/after inventories agree; independently verified all159 current files in those17 project roots. Protected course snapshots agree for the immediate materialization interval; packet line61 appropriately excludes subsequent authorized U07/root writes from a blanket final claim.
- `materialize.py` uses explicit rows, exclusive file creation and destination-exists guard, preserves accepted modes, and never recurses to copy generated prototype content. Before-destination evidence records absence and exact expected paths; after set matches all25.

## Actual proof and provenance limits

Read actual argv/cwd/scoped environment/exit/stdout/stderr for all five command records. Independently compared each raw stdout/stderr file to its command-log record. Observed versions are Quarto1.11.5, CUE0.17.1 and Quarto-bundled Deno2.7.14. No version installation or fresh download is claimed.

The exact native Deno expression performs `realPath` and `stat` for `/tmp/java-course-pr13-20261013/tasks/projects/gradeapp`. Before: NotFound/exit1. After: exact realPath plus isDirectory true/exit0. This proves only the missing directory remedy. Packet lines47–59 explicitly deny full checkPaths/validateRelease/metadata/render success; original M01 combined log and render-result were independently confirmed byte-identical to their retained copies. Existing render exit1 remains historical failure, not converted to pass by present files.

Routing closure is correct: canonical dispatch_id `M04-materialization-01` appears in current packet/report. Independently verified original document hashes and exact single provenance substitution plus appended metadata qualifier in both documents, all five overlay hashes, and the original seal's own SHA256 against metadata-proof. All61 historical seal rows match, using saved original documents for the two authorized metadata-updated entries; original source/evidence seal is not silently rewritten. Current exact document hashes are the overlay's values. `M04-materialization.md:69–71` correctly distinguishes metadata closure from original freeze.

## Decision and remaining gates

Approved only for exact source materialization and its bounded directory proof. Full M04 must still obtain its original required starter compile/expected TODO failure, completed reference/public4/private13, two independent permitted implementations, native export/unpack/helper, all17 declared projects/all14 positions on both host/container. Full M02/U07 acceptance, M03 and M05 render/link/privacy/ZIP/runtime gates and real pedagogical pilot remain open (`M04-materialization.md:65–67`). Windows remains supplied/static/unexecuted under accepted R1; no new Windows runtime requirement is introduced.

Review performed only read-only source/manifests/hash/mode/set/metadata/raw-log comparisons. No Maven/CLI/CI/render/host-container reruns, no Git/journal/packet/evidence/source edits, no agents/chats/global settings/publication. Sole written output is this scratch review. Requested profile: gpt-6.1-sol / medium; effective runtime profile not independently reported.
