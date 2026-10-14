# M02 generics — accepted U03 reproduction and JVM diagrams

Status: **DONE_SUBSET**. Dispatch `M02-generics-01`; technical agent `/root/m02`, assigned `gpt-6.1-sol / medium`; effective model/effort `not_reported`. Accepted input `a90878a741d1033b5766564be40f692327c67144`; remote PR13 baseline `a0d1bdb9d8343809560e3ce7f9a2141d4473ee3f`. The prior accepted U01/U02 subset remains documented separately in `M02-early.md`.

This is the authorized early M02 window, not full Task11/M02 completion. U03's seven QMD and packet were read-only; no changing U04 content was consumed. Only the two approved SVGs, this packet and `.superpowers/sdd/execution-plan/m02-generics-report.md` were written inside the checkout. All generated Java/C++/Python source bundles, binaries/classes, logs, raw javap and previews are under `/tmp/java-course-revision-checks/M02/U03`. No author evidence or earlier frozen M02 evidence was overwritten.

## Exact accepted inventory

`source-sha256.json` and `source-sha256-after.json` cover all seven accepted U03 QMD plus `packets/U03.md`: eight exact paths matched accepted commit a90878a7 before extraction and remained unchanged after reproduction/materialization. The copied author inventory retains all **80 fences** (64 Java, 7 C++, 4 Python, 1 C#, 4 command/config) and **92 complete contexts**. Every fence snippet and complete source file hash matched the author inventory before use. Source-path/anchor/line/fence classification, full wrappers/imports, expected results and original context-source hashes were preserved.

Source bundles were copied by exact case/file/hash from `/tmp/java-course-revision-checks/U03/examples`; no original run or author-semantic result is counted as a new M02 observation. A small temporary runner adapts the existing author checking steps for all accepted contexts, including four compile-only declaration contexts that have no main. It introduces no course framework or installation.

## Fresh reproduction results

| Accepted group | Contexts | Fresh observation |
|---|---:|---|
| Java compile-pass | 48 | All compile; 4 are declaration-only, remaining44 run with expected stdout/status |
| Java compile-negative | 19 | All fail for the expected inference/access/reifiable/static/erasure/checked/type reason |
| Java runtime-negative | 8 | All compile, then produce the intended exception and expected preceding stdout |
| Java unchecked/runtime-negative | 3 | `[unchecked]` diagnostics are present; later ClassCastException matches the contract |
| Java javap demonstrations | 2 | Compile/run and four fresh raw listings confirm descriptor/Signature, client checkcast and bridge delegation |
| C++20 positive | 4 | GCC compile/run results match real/complex dot, compatible dimensions, vector-length policy and concrete append |
| C++20 negative | 2 | Dimension deduction conflict and unsatisfied operation constraints, rather than missing-header/tool failures |
| Python positive | 3 | Pyright accepts; runtime yields expected score, real/complex dot and wrong-zero outputs |
| Python checker-negative | 2 | Pyright `reportArgumentType`; direct runtime separately produces intended TypeError |
| C# documentary | 1 | C#11/.NET7 selected in source; compiler/CLR not executed |

**91 executable contexts reproduced**, plus one documentary classification preserved; no remaining context mismatch. In particular all five strict/flexible Person/Student cases match: strict Person and inferred var(Person) pass; Student assignment target and explicit Student strict fail; flexible Student passes. Arithmetic gives11/12, empty0/1, complex11+i; unequal Java lengths reject. PECS preserves prefix/source/order, empty transfer leaves target intact, reverse transfer rejects, unmodifiable target rejects, wildcard clear remains permitted, and partial-effect expectations are preserved. Raw warning suppression hides warning without fixing the runtime content.

Actual tools: `/usr/lib/jvm/java-25-openjdk/bin/{javac,java,javap}` **25.0.4.1**, `--release 25 -Xlint:unchecked`, no preview; **GCC16.2.1** with `-std=c++20 -Wall -Wextra -pedantic`; **Python3.14.7**, `/usr/bin/pyright` **1.1.412**, case-local Python3.14 strict config. There are **168 fresh recorded tool commands**: 80 Java compilations,57 Java launches,4 actual javap listings,6 C++ compilations,4 C++ launches,5 checker and5 Python runs, and7 version probes. `javap --version` initially returned usage/exit2; the supported `javap -version` probe then returned25.0.4.1/exit0. Both raw probes remain recorded; examples/listings were not rerun for that probe correction. No perf-data warning, stdout filter or UsePerfData flag was needed in this subset.

Exact runner: `python /tmp/java-course-revision-checks/M02/U03/run_inventory.py`, working directory `/tmp/java-course-pr13-20261013`. Per-case commands, cwd, stdin, stdout, stderr and exit are in `command-log.json`, `results.json` and `case-results-final.json`; separate stdout/stderr logs are retained under each case directory. The final manifest includes complete source/wrapper/imports, main, classfile paths, args/stdin, source contexts, source hashes, expected stage-status semantics and actual versions. Expected author behavior was not changed.

## Two materialized assets and raw provenance

Exactly these source assets were created:

- `lectures/assets/j25/erasure-client-cast.svg` — approved title and Box/BoxClient source bundle. Fresh Box.get has Object descriptor and T Signature. BoxClient invokes erased get, then checkcast String, stores the value, and prints4. A labels the Java type connection, B labels erased return/call, C labels the client's separate cast. No cast is drawn inside Box.get.
- `lectures/assets/j25/erasure-getter-bridge.svg` — approved Source/TextSource/SourceClient bundle. Fresh TextSource contains written String get plus Object get with actual ACC_PUBLIC/ACC_BRIDGE/ACC_SYNTHETIC flags. The latter performs aload_0/invokevirtual String get/areturn, with no checkcast in **this** getter bridge. The separate SourceClient frame shows invokeinterface Object get followed by checkcast String; stdout is Java. The asset does not generalize absence of casts to other bridge forms.

`asset-provenance.json` provides **42 display-selection records** with exact raw source/javap path, SHA256 and matching line numbers. Instructions/method descriptors/flags are literal selections; line wraps and indentation are display-only. Signature uses the real label/comment value with its constant-pool index omitted. Offsets, constant-pool indices and package prefixes are explicitly omitted in the diagram caption, not replaced with invented values. Full raw listings remain in the two `javap-{type,client}-stdout.log` files per case, and all actual listing argv are retained.

## Visual verification and fallbacks

Both native SVGs use viewBox1280×800, the approved panel geometry/background/colors, installed Noto Sans Mono24px code, Noto Sans24px captions and32px titles. Panels stay within the approved maximum18 code rows; long source/JVM lines are presented as labeled excerpts rather than smaller fonts. Letter labels A/B/C are repeated with explanatory text; arrow badges have clear backgrounds and run through the center gap, never across code. The bridge and client-cast blocks are visually separated. Color is supplementary.

Rendered by installed `rsvg-convert -w 1280 -h 800 -o <external PNG> <approved SVG>`, with exact commands captured in `visual-commands.json`. Four renders comprise the initial two and two affected layout revisions (clear badge/stroke separation, A role color and caption baseline). The **final two PNGs were inspected with view_image**: no cropped code, text collision or clipping; captions and mono code are readable at1280×800. `asset-layout.json` records measured glyph widths and typography; `visual-verification.json` records final asset/PNG hashes and contrast. Text colors against both backgrounds meet at least3:1 for24px/18pt large text. This verifies standalone figures; U07 insertion/student rendering remains subsequent work.

Embedded accessible title/desc plus external `text-fallback.json` provide the approved explanations:

> В Java box имеет тип Box<String>, поэтому результат читается как String. У Box.get дескриптор возвращает Object; Signature описывает T. В BoxClient после вызова get стоит checkcast String, затем text.length() даёт4.

> Source.get после стирания возвращает Object. TextSource имеет написанный String get и синтетический Object get с bridge-флагами. Последний вызывает первый и возвращает его результат без checkcast. В SourceClient после интерфейсного вызова находится отдельный checkcast String; вывод — Java.

U07 insertion destinations remain `lectures/erasure-algorithms.qmd#sec-j25-lecture-erasure-algorithms-erased-read` and `#sec-j25-lecture-erasure-algorithms-bridge`; no lecture QMD was edited by M02.

## Frozen evidence and remaining gate

Evidence: `/tmp/java-course-revision-checks/M02/U03/{example-manifest.json,case-results-final.json,command-log.json,versions.json,source-sha256.json,source-sha256-after.json,asset-provenance.json,asset-layout.json,visual-verification.json,summary.json,final-verification.json,frozen-evidence-sha256.json}`; PNGs in `visual/`. The final verification has **108/108 passing checks**, including unchanged accepted inputs, all complete-source hashes, exact classification, actual tool versions, raw selection provenance, measured layout/contrast, and unchanged **690 previously frozen M02 evidence files**. Full original U03 evidence remains separate and unchanged.

No Git/journal/bibliography/config/vendor/condition writes, installation/global setting changes, subagents/new chats, publication or source semantic fixes. C# remains documentary. Full Task11/M02 awaits accepted U04–U07 coverage and final review; rendering/PrairieLearn host/container acceptance and student pilot are not claimed. Freeze ends this write window; root reviews these two assets before U07 consumption.

## Asset fix round1 — separate SourceClient frame

Status: **DONE, frozen for the same reviewer**. Independent `m02-generics-review.md` found that the original getter figure had a SourceClient label but no separate enclosing frame, contrary to U03packet220–224. The original108checks and initial PNG/hash records remain historical; they did not establish that required frame boundary. The review's Java/JVM/provenance acceptance is retained.

Changed only `erasure-getter-bridge.svg`: one native SVG rectangle at x692,y550,w536,h127, with the approved C colour #b45309, encloses SourceClient.main's label and invokeinterface/Object descriptor/checkcast excerpt. Code text, descriptors/opcodes, fonts24/32px, coordinates, arrows, accessible title/desc and palette are unchanged. The client SVG is untouched. No Java/C++/Python/checker suite was rerun.

Rendered **only the affected SVG once**, into NEW `/tmp/java-course-revision-checks/M02/U03/fix-1/erasure-getter-bridge.png`, and inspected it with view_image at1280×800: the client frame is visibly distinct below the bridge, with no overlap/clipping/font reduction. `fix-1/{asset-fix.diff,render-command.json,verification.json}` records direct diff, exact render argv/exit0 and current SVG/PNG hashes. Text/path/title/desc invariants, all raw source/javap hashes, eight accepted inputs, client SVG and all609earlier frozen U03 evidence files remain unchanged. Original missing-frame SVG is saved in fix-1; original PNG/108checks remain unchanged in the earlier freeze. Current getter metadata/PNG is supplied by fix-1, superseding its earlier presentation hash.

The Minor scaling concern remains a specific U07 insertion check: inspect green24px code/caption text at actual deck size, particularly reduction below the large-text threshold. Approved colours were retained for this returned scope. No frame-existence mirror test, global environment change or broad verification added. This narrow fix does not complete Task11/M02 or authorize U07 consumption before root's same-reviewer gate.

## Bounded SVG contrast closure — actual U07 scaled display

**DONE / FROZEN**, input `a7c3e2f7349848a1d1ac2447f02f5272b3d75f89`; same requested Sol6.1/medium, actual profile not_reported. Root opened this explicit color-only window after actual U07 scaled-preview evidence confirmed the earlier Minor scaling concern. Both specified old Reveal PNGs were inspected with view_image, and selected rows from U07/display-metrics.json were recorded in NEW M02/U07-contrast. Natural SVG1280×800 displayed1036.7999×648: effective image scale0.81, so24px regular green text becomes19.44px normal text. The old #059669 contrast3.77:1/3.60:1 did not meet4.5:1 at that size.

Changed only #059669→#047857 fill/stroke occurrences, consistently in both SVGs:11client-cast and13getter-bridge. New contrast is **5.4839:1 against white**, **5.2414:1 against #f8fafc**, meeting normal-text4.5:1. Exact raw-byte comparison masking only the approved color substitution and normalized XML comparison are identical. All source/JVM text, flags/instructions, geometry/fonts, other colors, correspondence labels and explicit separate SourceClient frame remain unchanged. All42raw source/javap selection hashes remain unchanged; no compilation or prior suite rerun.

Both current SVGs were freshly rendered with installed rsvg at1280×800 and inspected with view_image: darker B labels remain clear, with no lost labels/overlap/clipping. Native PNG/hash/contrast proof and three render/version commands are in `/tmp/java-course-revision-checks/M02/U07-contrast/{actual-deck-input.json,asset-change-manifest.json,contrast-verification.json,commands.json}`. All previous protected M02 archives690/609/6/876/2685/126files remain unchanged. Original approved/native/fix1/old-contrast evidence stays historical; none was overwritten.

Current exact SVG SHA256 for root's same-reviewer gate and U07 fresh actual-deck renders:

- erasure-client-cast.svg: `b91f29e88d9cbf5ea29b92260113d491a150c27ad3eeb568516dca0740f6c3d5`.
- erasure-getter-bridge.svg: `a9ba1bdff311dfb183d73ece1dbd80d834039a1fa3ccc0bd85c7ca7e50c42bfb`.

No lectureQMD/preview stage, Java/provenance source, Git/journal/vendor/config/global settings or other source edits. This is the bounded asset correction; **fresh scaled-deck verification remains with U07 after root reviewer approval**. Full M02/M05 final integration remains open. This closure is append-only; write window ends at freeze.
