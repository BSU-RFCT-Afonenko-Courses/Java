# M02 early — U01/U02 reproduction

Status: **DONE_SUBSET**. Dispatch `M02-early-01`; agent `/root/m02`, assigned `gpt-6.1-sol / medium`, actual profile `not_reported`. Accepted input `bc8a2e702f0751d15eb79977932bf54c1602d20e`; remote PR13 baseline `a0d1bdb9d8343809560e3ce7f9a2141d4473ee3f`.

This is the authorized early experiment window from handoff `plans/execution-plan.md:58`. It does **not** complete Task11/M02 or waive U03–U07 dependencies. Source remained read-only. Only this packet and `.superpowers/sdd/execution-plan/m02-early-report.md` were written inside the checkout; generated code, classes, logs and caches are in `/tmp/java-course-revision-checks/M02`.

## Accepted coverage and extraction

`input-source-sha256.json` records 36 unique accepted paths: all 21 U01 QMD, all 11 U02 QMD, the structure contract, both author packets, and `application.qmd` solely for the exact Student-definition comparison. Every file matched the accepted commit before use and remained unchanged after runs. `extraction-check.json` verifies all 202 inventoried source fences against the author manifests: 118 U01 Java fences and 84 U02 Java/Kotlin fences, including every source path, fresh anchor and exact snippet hash. Corrected U01 package-path instructions and accepted U02 primitive-Class prose were preserved.

Author extraction scripts were copied and retargeted to M02/U01 and M02/U02. Wrappers, imports, observers, negative classification and expectations were retained. Cached CF4.3.1 jars were copied locally; dependencies and original U01/U02 evidence were not modified. No universal framework was introduced.

## Fresh results

| Scope | Actual checks | Result |
|---|---:|---|
| U01 executable Java contexts | 115 | 114 normal runs and one intended NPE; all final expected observations match |
| U01 focused semantic cases | 43 | All expected compiler/runtime/checker/boundary observations match |
| C# nullable comparison | 2 | Bad: CS8602 warning then NullReferenceException; guarded: no CS8602 and stdout `0` |
| U02 example contexts | 60 | All positive and intended runtime-negative observations match |
| U02 compiler boundary cases | 20 | 18 intended rejections and 2 positive controls; diagnostic keys match author evidence |
| U02 shared-contract checks | 4 | Exact Student identity comparison plus three compiled/run contexts match |

The fresh process logs contain 458 commands (311 U01, 147 U02); two targeted perf-data retries added 4 commands, for 462 total captured commands. Versions are included in those logs. Java source execution/compilation used `/usr/lib/jvm/java-25-openjdk/bin/{javac,java}`, actual **25.0.4.1**, `--release 25`, no preview. Python **3.14.7**, Pyright **1.1.412**, CF **4.3.1**, Node **26.10.0**. C# used installed SDK **11.0.100-rc.1.26425.128**, Roslyn **5.11.0-1.26425.128**, installed reference/runtime11RC, `-langversion:13 -nullable:enable -warnaserror-`, direct csc without restore.

Concrete observations include field/default `0/null/0` versus unassigned-local compiler rejection; reference argument mutation `9/9/2` versus local rebinding; independent outer arrays with shared rows/elements; ordinary javac accepting annotated null dereference and runtime NPE versus CF `dereference.of.nullable`; CPython executing incompatible annotated assignment while Pyright reports `reportAssignmentType`; correct packaged source launch showing 3.5 while stale class remains 2.5 until recompilation. Java25 constructor order is `prologue → parent: 3 → field → epilogue: 7`, with read/call/escape/inherited/initialized/compound/branch restrictions rejected and simple own-field assignment permitted. Canonical Book remains shared and borrowed after repeated-borrow refusal. `grade.Student` boundaries/normalization and metadata `score:int`, `int.class != Integer.class`, ordinary-class record components null, and record shallow-array observations match accepted contracts.

## Preserved initial failures and isolated retry

Two U01 `Arrays.copyOf` example launches initially failed exact-output matching because stdout also contained HotSpot `[warning][perf,memops] Cannot use file /tmp/hsperfdata_tolya/91` and `/140` lock warnings (`errno = 11`); one compiler invocation also warned for `/112`. Their application suffixes already matched expectations. Full initial stdout/stderr/argv are retained in `U01/snippets.json`, `U01/command-log.json`, and `perf-data-diagnosis.json`.

Only those two affected contexts were recompiled with `-J-XX:-UsePerfData` and relaunched with `-XX:-UsePerfData`. Both produced exact expected stdout with empty stderr and exit0. `U01/perf-collision-retries.json` records all four fresh commands and initial evidence; `U01/snippets-final.json` retains initial and final results. This supports the per-process isolation hypothesis; the specific lock owner, PID namespace and original trigger were not independently established. No hsperfdata deletion, global environment/settings change, expectation change or acceptance output filter was used.

## Manifest and evidence

All paths below are under `/tmp/java-course-revision-checks/M02/`:

- `example-run-manifest.json`: complete source/fence/anchor or supplemental-contract context, wrapper/source files, imports, main/classfiles, args/stdin, expected and actual results; U01 schema rows remain excluded.
- `semantic-run-manifest.json`: all 43 U01 semantic and 2 C# cases, associated accepted anchors, exact source/commands/outputs and versions.
- `U02/example-manifest.json`: fresh hash-verified classification; 80 Java source fences reproduced through author fixtures, 3 student implementation clients retained, 1 optional Kotlin fence retained.
- `U01/{snippets.json,snippets-final.json,semantics.json,csharp.json,tool-versions.json,command-log.json,execution.log}` and `U02/{example-results.json,negative-results.json,negative-reason-check.json,contract-results.json,command-log.json,execution.log}`: raw fresh results.
- `input-source-sha256.json`, `extraction-check.json`, `summary.json`, `final-verification.json`, `frozen-evidence-sha256.json`: source coverage, classifications, final verification and frozen output index.

Exact runner commands: `python /tmp/java-course-revision-checks/M02/run_unit.py U01` and the same with `U02`, from `/tmp/java-course-pr13-20261013`; each sequentially runs its three author-derived scripts. Four targeted retry argv are in `U01/perf-collision-retries.json`. Raw commands include absolute JDK paths, output directories, class names and stdin. No author run is counted as an M02 run.

## Remaining scope

Three practice clients require student Circle/Vehicle/queue implementations and remain `exercise-client-template`; complete lab solutions were not invented. Kotlin is optional and no run is claimed. Zig0.15.2 remains a documented illustration; installed Zig0.17dev was only version-inspected and was not used to execute it. Python Protocol, C++20 numeric template, generics inference/erasure/bridge and lecture diagrams await accepted U03; collection snapshots await U04; application build/resources await U05; complete practice/lab/lecture coverage awaits U06/U07. No final render, host/container PrairieLearn acceptance or student pilot is claimed. Await coordinator follow-up for the next accepted subset.
