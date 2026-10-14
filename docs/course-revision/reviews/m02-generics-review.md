### Spec Compliance

- ❌ Issues found: the required separate SourceClient frame is missing in `lectures/assets/j25/erasure-getter-bridge.svg:34`; the client excerpt shares the bridge’s outer JVM panel. All reviewed Java/JVM semantics, provenance, native SVG geometry, accessible explanations and standalone readability otherwise satisfy the accepted U03 subset.
- ⚠️ Scope: this is the two-asset gate before U07 consumption, against accepted input `a90878a741d1033b5766564be40f692327c67144`, using `.superpowers/sdd/execution-plan/m02-generics-review.diff` (23,992 bytes). Full Task11/M02, U04 authoring, lecture insertion/reveal, rendered student course, PrairieLearn acceptance and pedagogical pilot are excluded and remain pending; see `docs/course-revision/packets/M02-generics.md:59`.

### Strengths

- `lectures/assets/j25/erasure-client-cast.svg:24` accurately distinguishes the Object descriptor from `Signature: ()TT;`; lines 29–33 reproduce the client invoke → checkcast String → astore sequence. The raw Box listing confirms descriptor/Signature at `/tmp/java-course-revision-checks/M02/U03/examples/client-checkcast/javap-type-stdout.log:61` and `:70`, with no cast inside the getter (`:65`–`:67`); raw client instructions at `javap-client-stdout.log:79`–`:81` match the display.
- `lectures/assets/j25/erasure-getter-bridge.svg:25`–`:33` faithfully shows Object get, ACC_BRIDGE/ACC_SYNTHETIC and aload_0 → invokevirtual String get → areturn. Raw TextSource listing `/tmp/java-course-revision-checks/M02/U03/examples/getter-bridge/javap-type-stdout.log:58`–`:65` confirms this exact bridge has no checkcast. The separate client call/cast are real instructions at that case’s `javap-client-stdout.log:72`–`:73`; the figure does not generalize to all bridges.
- `lectures/assets/j25/erasure-client-cast.svg:41` and `lectures/assets/j25/erasure-getter-bridge.svg:49` explicitly disclose offset, constant-pool-index and package-prefix omissions. Split opcode/comment display rows preserve the formal descriptors. No fabricated offsets, indices or opcodes were found. The two exact source bundles retain their packages, types, constructor/access contracts and observed outputs 4/Java.
- Both SVGs at `:1` provide accessible title/desc with the approved readable causal fallback. PNGs viewed at 1280×800 show readable 24px code/captions and 32px titles, no clipping or text overlap, and clear central-gap arrows. A/B/C labels and explanatory legends preserve meaning without relying on colour (client figure arrow region x580–680/y455–545; bridge figure x580–680/y300–670).

### Issues

#### Critical (Must Fix)

- None.

#### Important (Should Fix)

- `lectures/assets/j25/erasure-getter-bridge.svg:34`–`:38` (rendered region x700–1220, y555–675): SourceClient.main has a label but no separate frame; the only JVM rectangle is the shared outer panel at line 5. Accepted `docs/course-revision/packets/U03.md:220` explicitly requires a frame separate from the bridge for these client instructions. That boundary is part of the requested protection against reading checkcast as a bridge instruction. Add the client’s distinct frame without reducing font sizes or crossing code; re-render the affected asset and refresh its evidence hashes. `docs/course-revision/packets/M02-generics.md:36` currently calls it a “separate SourceClient frame,” which the actual image does not implement.

#### Minor (Nice to Have)

- `lectures/assets/j25/erasure-getter-bridge.svg:29` and `lectures/assets/j25/erasure-client-cast.svg:23`: green text is readable at the reviewed native size, with reported/recomputed contrast approximately 3.77:1 against white (3.60:1 against background). However, 24px is exactly the 18pt large-text threshold: reducing the 1280px image to 960px makes it 18px/13.5pt and the green text falls below the 4.5:1 normal-text threshold. U07 should specifically inspect this green code/caption text in the actual scaled deck. This is a downstream scaling concern, not a failed native-size gate or a request for a broad rerun.

### Checks and limitations

- Named cross-risk: source/type-flow and literal real-javap provenance. Read all five raw Java source files and the four raw listing paths named by `asset-provenance.json`; independently matched their SHA256 values, method boundaries and actual relevant instructions. Selected `case-results-final.json` entries show `javac --release 25 -Xlint:unchecked`, absolute JDK25 paths, four `javap -p -c -v -classpath ...` commands, exit0/empty stderr, and expected runtime stdout. No example/compiler suite was rerun.
- Inspected `asset-layout.json`, `visual-verification.json`, `final-verification.json`, `source-sha256.json`, `case-results-final.json` and `versions.json`. Independently verified the eight current accepted source hashes and both SVG/PNG pairs. Existing manifests contain 92 contexts (91 executed plus one C# documentary) and 108/108 recorded checks; selected evidence was inspected rather than treating every recorded pass as a new independent reproduction. The documented initial unsupported `javap --version` exit2 is preserved alongside successful supported `javap -version` → 25.0.4.1, and is not a course defect.
- Viewed both specified final PNGs with `view_image`. Checked actual readable layout, arrows, captions and client/source associations rather than accepting the visual-review claim. Large-text contrast values agree with the specified colours; the native layout needs only the required client frame above.
- Full earlier 690-file evidence freeze is reported by existing verification; this scoped review did not independently rehash that entire archive or broaden into U04/full-course review.

### Assessment

**Task quality:** Needs fixes.

**Reasoning:** Technical provenance and both erasure explanations are sound, and standalone typography/layout are good. The one explicit storyboard boundary must be materialized before U07 consumption; no Java source change or broad reproduction is needed.
