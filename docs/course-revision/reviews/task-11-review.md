### Spec compliance

**❌ Needs fixes.** Most Task11 evidence is sound, including authentic JDK25 execution, preserved harness incidents, carried prototype outcomes and corrected actual student/full SVG insertion. Full runnable closure is not yet supported: six explicit lecture behavior groups have only declaration/no-invocation coverage, and parts of the final fence-to-case mapping contain false associations. These are M02 evidence/fixture corrections; no course-author semantic change is requested.

**Scope:** accepted U07 `b80c57b2f68c219baca842c2a18d0886d460147f` plus accepted mechanical M03 `719246db58d343cc4d66ff6b99201737606cd2d0`. Read the full technical-review template, Task11 brief/dispatch, mandatory incident supplement, owner diff, entire packet/report, coverage/case manifests and checking scripts. Requested reviewer profile remains Sol6.1/medium; effective runtime profile is `not_reported`. Sole reviewer write is this frozen report.

### Strengths

- `docs/course-revision/packets/M02.md:9`–`:19`: explicit carried/fresh/documentary distinctions are useful. Independently verified all100 current source hashes, all365 literal fence bytes and current line locations, prior118/84/80/77/63 fence families and their exact complete context files, 2412 final indexed files, 5002 earlier M02 files and225 original U07 sealed files. M03 reference-only relocation is correctly separate from executable changes; no source-file hash relocation was treated as a fresh run.
- `/tmp/java-course-revision-checks/M02/final/raw-case-index.json:1` and `command-ledger.json:1`: all233 records occupy unique files; all458 ledger process entries match their immutable raw records. The231 final accepted contexts exclude the two initial bad assemblies. Compile/runtime/checker outcomes match the recorded final oracles; intended diagnostics arise from the promised bounds/type/exception reasons rather than missing symbols. Java compile argv consistently uses actual absolute JDK25 paths and release25 without preview. Python Protocol/checker/runtime observations are separate; C++ array2/3 rejects conflicting non-type dimensions. No example suite was rerun by this reviewer.
- `systematic-harness-cause.json:1`, `oracle-correction-proofs.json:1` and all three first snapshots: independently checked every named incident. TreeSet prints the second add’s false, Set prints false/1, Map prints90/90/2/null/null/true/false, PlainBook examples print Java, true/true/false/Java/false and Java: true, and result-switch prints ошибка: OUT_OF_RANGE. These corrections follow literal accepted source/comments/prose; original outputs are unchanged. Explicit Student diagnostics are a declared-bound rejection. C++ displayed assignments contain no cout, so the corrected empty stdout and retained unused-variable warnings are honest. The two real assembly failures are visibly class-body calls and duplicate independent value locals; the narrow corrected sources restore the accepted scopes.
- `evidence-control-incident.json:1` and `seal-raw-cases.py:6`: original first snapshots/raw failures are retained. The two lost successful retry records/four historical commands are explicitly unavailable, not fabricated. Two fresh recovery records contain actual argv/cwd/status/stdout/stderr and filesystem time bounds labelled as bounds. No-overwrite per-case emission and the ledger derived from those files are a satisfactory frozen evidence-control correction.
- `docs/course-revision/packets/M02.md:37`–`:43`: the corrected actual visual gate passes. Viewed **all eight** final student/full PNGs at1280×800, including both text fallbacks. Source/JVM clients, A/B/C, bridge/client boundary, real outputs and captions are readable without clipping or overlap; image bounds x64–1100.8/y118.83–766.83 fit the viewport. Effective0.81 scaling produces19.44px regular text; green contrast5.483924:1/5.241375:1 passes normal-text4.5. The former green-scaling concern is closed for these corrected previews.
- `preview-css-fixture-correction.json:1` and `corrected-resource-verification.json:1`: initial missing-CSS renders/screenshots remain explicitly incomplete. Independently verified corrected lesson.css bytes against course and accepted author preview, exact preview QMD, both immutable SVGs, HTML/local-resource hashes,89 real responses, required CSS/SVG200 in both profiles and loadingFailed0. The sole favicon404 is optional/browser-generated and disclosed. The42 original source/javap selection hashes match; actual descriptor/Signature, client cast, bridge flags/delegation and getter-specific no-cast claim retain the earlier acceptance.
- `tasks/bank/files.qmd:150`–`:175` and `gradeapp-visible-contract-provenance.json:1`: all13 private test names map to visible API requirements. Exact prototype25/public17 hashes match, carried CLI20 outcomes match expected bytes/status, actual XML preserves completed public4/private13 and historical starter/fixture failures, and both Reports retain their separate15 observations. Provided build.sh/stock Wrapper, clean foreign cwd,17-file export and two-directory JAR commands are authentic carried observations. No PL18, empty-cache download, executed Windows, final M04/M05 or pilot waiver is claimed.

### Issues

#### Critical

None.

#### Important 1 — explicit behavioral callers are missing from final acceptance

`docs/course-revision/packets/M02.md:17`–`:19` claims complete fresh staged coverage, but the final fixtures below do not execute the accepted adjacent prose caller/result. Labeling an empty-main run “definition-only” is honest about the run but cannot replace a specified behavioral example. Exact recorded cases are:

| Accepted visible source / anchor | Actual evidence gap | Required affected observation |
|---|---|---|
| `lectures/control-methods-data.qmd:173`, `:197`, `:209`; primitive-parameter/shared-array/rebinding | `final/fragments/control-methods-data/parameter-members/Demo.java:15` has an empty main; addPage/changeFirst/replace each appears only as a declaration. | Exact prose pages=5/addPage leaves5; array{5,7}/changeFirst yields[9,7]; replace keeps that caller array[9,7]. Include the expressly supplied nextPageCount body/caller if recording that neighboring prose observation. |
| `lectures/functions-streams.qmd:30`, effects; explicit main/result at`:39` | `final/fragments/functions-streams/effect-methods/Demo.java:9` has an empty main. label/announceAndBorrow are not called with a Book. | Book(Java): label returns Book: Java with borrowed false; announceAndBorrow prints Book: Java and borrowed becomes true. |
| `lectures/interfaces-records.qmd:154`, broken-contract; expected refusal at`:162` | `final/fragments/interfaces-records/broken-contract/Demo.java:5` has an empty main; summaryLength is never called. | summaryLength(new SilentDocument(Notes)) reaches the promised NullPointerException, rather than accepting a successful empty program. |
| `lectures/interfaces-records.qmd:510`, snapshot; explicit main/result at`:517` | `final/fragments/interfaces-records/records-members/Demo.java:4` has an empty main; no Scores instance or mutation of either array boundary. | Input{80}; create Scores; mutate input and returned values() copy; subsequent values()[0] remains80. |
| `lectures/exceptions-resources.qmd:287`, step-18; explicit parseAt(ninety,3) result at`:297` | `final/fragments/exceptions-resources/cause-method/Demo.java:17` has an empty main; no parseAt invocation. | Actual checked DataFormatException, exact message строка 3, original NumberFormatException cause. |
| `lectures/functions-streams.qmd:318`, live-setting; Ann80 result at`:324` | `final/fragments/functions-streams/live-setting/Demo.java:16` sets threshold90 and exits; rule is never invoked. | Invoke the existing predicate with the specified Ann80 after mutation and record false. |

**Consequence:** complete-body compilation is being used as acceptance for missing success/state/refusal observations. This leaves precisely the parameter-copy, effect, contract-failure, defensive-copy, exception-cause and live-capture examples unreproduced in their final lecture contexts.

**Earlier-carry check:** inspected all final complete sources and every carried source path for these declarations/callers. Older U04 EffectDemo and live-setting, U01 parameter-copy and U02 SilentDocument probes contain analogous successful observations, but are different complete programs/source contexts (including changed declaration bytes/identifiers/wrappers); no exact final lecture complete-context+expectation carry mapping is supplied. No carried Scores or parseAt caller exists. Do not silently call these old programs fresh final lecture executions.

**Owner correction / rerun scope:** M02 should add only the six corresponding complete caller groups from the already explicit accepted prose, use exact final declarations, check the specified values/state/exception cause, and capture fresh unique raw records. Preserve the original declaration-only/no-invocation runs as historical evidence. Correct manifests, packet/report completion statements and seal. No source-author change, unrelated prior suite, gradeapp, SVG or render rerun is needed.

#### Important 2 — final fence mapping contains unrelated and mutually exclusive case links

`/tmp/java-course-revision-checks/M02/final/build-final-manifest.py:40` accepts any normalized substring anywhere in any source as a case link;`:47` assigns every score/selection Python fence to both positive and negative cases without checking the fragment’s actual role.

- `final-lecture-fence-manifest.json` entry **objects-invariants-35**, `lectures/objects-invariants.qmd:517` (the canonical Book closing braces), links **222 cases**, including selected/reading, builder and C++ array cases. These have matching braces but are not the canonical Book continuation/context.
- **erasure-algorithms-20** (`lectures/erasure-algorithms.qmd:494`) links the positive Candidate print to protocol-negative, whose source deliberately replaces that print. **erasure-algorithms-21** (`:502`) links BadCandidate/negative print to protocol-positive, where those lines do not exist. Likewise **functions-streams-15/16** (`lectures/functions-streams.qmd:227`/`:248`) link their replaced positive and negative client stages to both mutually exclusive contexts.

**Consequence:** the promised exact final fence-to-complete-context provenance is misleading. Raw processes are genuine, but a consumer cannot infer which source stage, setup and expected result was actually exercised from these links. This also helps mask the coverage gaps above.

**Owner correction / rerun scope:** M02 should build explicit stage-aware mappings, joining split declarations to their own canonical complete source, associating common definitions with both variants only where actually present, and assigning replaced positive/negative callers exclusively to the corresponding case. Revalidate the full297-Java-fence map and all Python/C++ execution links against approved scope/origin, complete contextual source, setup and expectations, rather than generic braces or global/prefix matches; do not restrict this correction to the examples listed above. This correction requires metadata/script regeneration and a new seal, with **zero** existing compiler/checker reruns; add only the missing behaviors from Important1.

#### Minor

No additional blocker or new visual concern. The retained unused-variable C++ warnings, unavailable historical retry commands, browser favicon404, optional unrun languages and Windows/static limitation are correctly disclosed.

### Assessment

**Spec compliance:** Needs fixes.

**Technical quality:** Needs fixes.

**Reasoning:** Authentic raw execution, incident recovery, carried prototype checks and the corrected scaled visual gate are strong. Task11 closure must wait for the six explicit caller observations and a reliable stage-aware coverage map; aggregate counts and empty-main runs do not establish those requirements.

**Limits:** no whole-course zero-warning render/export/privacy, final M04 materialization, M05 host/container or student/full/student cycle, U08 pedagogy or actual student pilot acceptance is granted. All source/evidence/Git/journals remained read-only; no agents/chats/global changes/publication or broad test reruns occurred. This report is frozen on return.
