# Task 7 / U05 — independent review

Status: **FROZEN / QUALITY APPROVED**. No Critical or Important issues; two
nonblocking Minor editorial findings below. This approves the U05 author
output for downstream work, not the whole course or final delivery.

Requested reviewer profile: `gpt-6.1-sol / ultra`; effective model/effort
`not_reported`. Review is independent and read-only except this report.
No source/prototype/index/HEAD/journal changes, subagents, chats, publication,
global installations or settings changes. No passing broad suite was rerun.

## Scope and evidence

Reviewed the frozen 399472-byte owner diff
`.superpowers/sdd/execution-plan/u05-review.diff`, SHA256
`4a711878eb0549fb1330526196acae3619694405a8b9775bde34446679e3bad8`,
against the Task 7 brief/dispatch, current human/controller constraints,
tracked user instructions, operative U00 §7 and the complete approved
U00-R1-wrapper-delivery packet. Semantic input is `9c151dc6a3a0350024b5e8e353198375c67a80b3`;
additional accepted R1 input is `96bc1594ba89b3430af823b6c11b6d5baf457f44`;
remote PR13 baseline is `a0d1bdb9d8343809560e3ce7f9a2141d4473ee3f`.

Read all nine current owned QMD, material removed passages, the complete
U05 packet including Appendices A–F, and the author report. Checked the
accepted U02 Student declaration, U03 TicketSlot/client and early checked
handoff, U04 report/effect/portable Stream contracts, and the relevant
EF12–14/P14–15/G01/G21/G24 specification entries. Did not reread unrelated
unchanged course material or the full U02–U04 evidence appendices.

Read actual provided helpers, all 25 materialization assets, public/private
tests, source/context manifests, delivery/CLI/build scripts, native fixture
configuration, native run/owner receipt, selected raw process logs and
JUnit XML. Fresh read-only checks confirmed the frozen 11 file hashes;
25 asset hashes and readable packet copies; all 63 current fence identities;
46 full-context source hashes; 88 unique fresh native section IDs across
nine files; category lines and absence of old own markers; and clean
`git diff --check` for the nine QMD.

## Spec Compliance

| Requirement | Assessment |
|---|---|
| Compile/input/runtime/invariant distinction | Met. The opening separates all four questions. Checked occurs at runtime; compile-time handling/declaration is explicit. |
| Early L7 checked preparation | Met. Complete EarlyCheckedDemo gives ordinary return, throw, forwarding throws, catch and continuation without files/collections. Its removed-throws negative is classified as compilation failure. U03's local checked/container contract remains compatible. |
| Resource causal line | Met. Manual success → finally masking → nested resource protection → TWR acquisition/close order → primary/cause/suppressed. CauseCloseDemo gives the visible combined trace. GC/finalization/Cleaner do not promise timely close. Matrix/wrappers/result-before-close are continuations. |
| IO and effects | Met. Path/cwd, byte/character encoding, EOF versus failure, UTF-8 decoding errors and physical line counting are distinct. File atomic publication is explicitly optional and outside stdout gradeapp/copy requirements. |
| Canonical Student | Met exactly. Student(String id,String name,int score), normalization and constructor source match accepted U02 byte-for-byte, SHA256 `19199e9d72a3c789c021c7bc24c00c847c916bc382577f43ff2c64403f4e6eb9`. |
| Loader/parser | Met. Blank physical lines count before skip; uniqueness uses normalized, case-sensitive id, not record equality. Parsing/constructor causes are preserved; data/reading/close failure cannot return a partial catalog. |
| Report and CLI | Met. Inclusive threshold, score descending then natural String id, source unchanged, unmodifiable result, loop and Stream permitted. Exact two arguments, no header, stdout/diagnostic separation, statuses 0/2/3/4, complete validation before output, potentially partial output failure, system streams left open. |
| Help boundary | Met. Student/DataFormatException/build/CLI boundary/printReport are provided; parser/read/selected/line/Main.run(Path,int) remain explicit throwing TODO. Practice30 uses an adjacent resource-policy example and starter setup, with no completed assigned parser/loader/report/orchestration. Full theory solution is distinguished from starter. |
| R1 build/delivery route | Met. Public17 manifest contains build.sh/build.cmd and public wrapper properties, no authored .mvn. Helpers regenerate properties each run and call unchanged Wrapper3.3.4/Maven3.9.16; primary route is `sh ./build.sh test package`. Tools/refusals/arguments/status are accounted for. |
| Complete handoff and scope | Met. Exact public17 + root README + private6 + private test =25 assets, names/hashes/content supplied outside course. No actual gradeapp materialization, vendor/project-check/PL expansion, grading or learner bootstrap task added. U06/U07/M02/M04 requests are concrete. |
| Time, volume, identifiers | Met within author scope. L10 proposal 30+45+30+120=225, old four cards retained in bank via U06, full labs1935/intro300 separate, integration120 a pilot hypothesis. Own theory indicator8555→9348/+9.269% is not global acceptance. Fresh IDs/categories with no aliases/migrations. |

All nine files have meaningful revise/checked-preserve decisions, with
reasons. In particular the mostly correct IO/resource/API prose is retained
deliberately; the project route and practice help boundary are materially
rebuilt. No operative contract was replaced by obsolete numeric-id,
duplicate-permitted or input/output/threshold notes.

## Strengths

- The error/state/resource/output boundaries are explained causally. The
  late-invalid and output-sink cases distinguish no publication of invalid
  input from the inability to undo already printed bytes.
- Source freedom is preserved: both report implementations satisfy the
  same order/threshold/source contract; none of the tests demand the
  Stream implementation, mapper effects or a particular loader algorithm.
- The starter is honest about unfinished work. Raw XML/logs show one
  provided Student test passes and three TODO behavior checks fail
  (one failure/two errors); compile/package success with -DskipTests is
  never represented as finished behavior.
- R1 is supported by an actual native model export. The exact ZIP has17
  public entries, SHA256 `c8659277dd1f325f8952b10d04d3c3bf7247a4558a83e11330f9b80fc98e487c`,
  all byte-identical to frozen public sources. All entries encode native
  mode `(create_system=0, external_attr=0)`; actual unpacked scripts are0644.
  Helpers account for that and the excluded dot-path configuration.
- Provenance is concrete. The two Wrapper scripts match the cached
  official3.3.4 only-script distribution ZIP byte-for-byte; the Maven ZIP's
  fresh SHA256 matches the fixed hash. All203 staged native extension files
  match checkout, not merely the seven declared critical source hashes.
- Claims are appropriately bounded: selective book reading, failed JEP421
  retrieval, Windows static/unexecuted status, technical-fixture SCSS
  warning and remaining integration/pedagogy gates are stated explicitly.

## Issues

### Critical

None.

### Important

None.

### Minor

1. **State the enclosing checked declaration for the existing-resource snippet.**
   `handbook/entries/resources.qmd:39` (context at14–16) supplies the Path
   argument/imports, but this second snippet has neither catch nor a stated
   enclosing `throws IOException`. Unlike the first caught form, pasting it
   into an ordinary `static void show(Path path)` produces unhandled checked
   exception diagnostics for open/read/close. The evidence's
   `handbook-existing/Demo.java` succeeds because its main declares
   `throws Exception`, so this is a reader-context omission, not a source
   harness failure. **Minimum correction:** introduce the second fragment
   as the body of `static void showExisting(Path path) throws IOException`
   or add that enclosing declaration to its prose. No algorithm or test
   change is needed.

2. **Name the model invariant change in the optional CSV evolution story.**
   `theory/chapters/application.qmd:472` and the U07/P15 handoff at
   `docs/course-revision/packets/U05.md:147` identify parser/line escaping for
   separators in names, but the provided Student constructor also rejects
   `;` in a normalized name. A correct unescaping parser therefore still
   cannot construct Student("s1","Ada;Lin",90). The extension explanation
   would be clearer about which boundary must change. **Minimum correction:**
   add a sentence that this hypothetical new format also revises the
   Student name-separator invariant; score validation, selection and sorting
   can remain. Keep the current constructor and L10 contract unchanged.

These are small explanatory improvements and do not block U05 downstream
work. Neither changes accepted application behavior, R1 delivery or the
student assignment.

## Checks and limitations

Author's retained execution evidence was audited, not rerun as a duplicate
suite:46 contexts (44Java/1Rust/1Go;42run/1compile/3compile-negative),20 CLI
outcomes,17 unique reference JUnit tests (public4/private13),31 delivery
expected-outcome checks and17 final manifest checks. Source hashes tie those
records to this frozen material. Inspected negative compiler diagnostics,
full reference build log and both raw JUnit suites, starter and completed
native-unpack public XML, version logs, exact preparation-refusal logs,
invalid spaced-goal log, completed foreign-cwd JAR stdout and loop source/
result. Actual commands use JDK25.0.4.1; reference tests report0failure/0error.
The31 count deliberately includes expected refusals and TODO failures.

Fresh ZIP/hash/receipt inspection confirms native provenance, clean source
partition and identities. Historical initial direct ./mvnw logs are retained
as pre-R1 history; the new delivery records use the accepted helper route,
spaces/foreign cwd, task-local caches, repeat restoration and stock failure
status/argument propagation. Only the completed private evidence variant
provides a passing test/package/runnable-report observation.

Exact semantics were spot-checked against primary
[JLS25 TWR](https://docs.oracle.com/javase/specs/jls/se25/html/jls-14.html#jls-14.20.3),
[Files25](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/nio/file/Files.html)
and [Apache Wrapper](https://maven.apache.org/tools/wrapper/): resource order/
suppression, decoder failure/late UncheckedIOException and only-script
delivery agree. Local downloaded exact POMs/distribution/Wrapper artifacts
provide version-specific provenance. The cached Bloch resource extract has
five nonempty pages; this corroborates the bounded extraction claim, not an
independent claim of reading the other books or verifying the author's
subjective reading process.

No final course render/readability/links/leaks, actual gradeapp materialization,
final native course ZIP, independent M02 reproduction, full host/container
delivery, PL17/14 gates, lecture timing or student pilot were performed by
this reviewer. The native export is a representative temporary exercise;
its recorded SCSS warning is not a zero-warning final-render result.

## Declined to judge

- Windows execution: accepted R1 explicitly permits supplied/static/unexecuted
  counterpart; no new Windows runner gate is introduced.
- Final U06 conditions and cross-owner consumers: author proposes exact
  text/targets; those owners and M03 must publish/resolve them.
- M02–M05/U07/U08 final gates and real pilot: separate downstream scope;
  current author evidence does not close them.
- Atomic stdout/file rollback, concurrent filesystem races, framework/DB/
  network/general CSV implementations: not required by the current exercise;
  assessed only the accuracy of the stated optional boundaries.
- Live grading, aliases/migration/backward compatibility: explicitly outside
  authorized scope and forbidden by current human instructions.

## Assessment

**Quality: approved.** Task7 acceptance is met for the frozen author output;
no Critical/Important correction round is required. Two Minor prose
clarifications are recommended in a bounded owner window. M02–M05/U06/U07/U08
and the real student pilot remain open; this report is not merge/publication
authorization or whole-course acceptance.
