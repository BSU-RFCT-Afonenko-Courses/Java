# M04 — read-only ready project checks

Status: **DONE_READ_ONLY_SUBSTEP_WITH_CONCERNS / FROZEN**. Diagnostic declared checks pass for all17 on both backends. The requested full native delivery gate does **not** pass: both stock CI attempts stop during native export, before either delivery backend. No Task13 completion or dependency waiver.

Dispatch_id `M04-ready-project-checks-01`; task_id `M04/ready-project-checks`; original agent `/root/m04`, requested `gpt-6.1-sol / medium`, effective profile `not_reported`. Accepted source HEAD `719246db58d343cc4d66ff6b99201737606cd2d0`, actual remote PR13 baseline `a0d1bdb9d8343809560e3ce7f9a2141d4473ee3f`. Ready inputs: U06/project decisions, reviewed exact25 gradeapp/public17/visible-private contract/M02 Reports carry. Full M02 remains required; its outstanding accepted review findings are lecture observations/mapping.

## Tested source, tool and copy identity

A task-local `git clone --no-hardlinks --no-checkout` and exact detached checkout created `/tmp/java-course-revision-checks/M04/ready-project-checks/source`; only that clone's origin was set to the existing credential-free HTTPS course origin, as required by stock native builder. Shared Git/index/source remained read-only. Clone Git was clean before export and after all checks. All **1360** tracked regular teaching/build/tool/config/vendor/resource/project files match accepted shared bytes/size/modes before and isolated after. Concurrent `docs/course-revision/**` audit/packets are explicitly excluded from tested teaching identity; the clone retains their committed versions but this packet makes no audit-testing claim. Untracked `.superpowers`, M02 packet, dirty journal and ignored generated/output/cache artifacts were not copied as source inputs. `copy-scope.json`, `source-manifest-{before,after}.json`, `shared-source-after.json` record the precise scope. Current shared tested-path changes at final capture: [].

Existing pinned Platform checkout `/home/tolya/course-tools/performance-20261012/runtime-platform`, commit `b971a61a2386fbf4ae7fcfc9394d307359bc7889` /v2.0.0, was verified clean; **132** platform source/runtime dependency files match before/after. Published grader image is exactly `ghcr.io/afonenko-course-tools/java25-grader@sha256:f791e5590e6102961669e838cb2476f2dde91d888b0fb5a5292bc226cb2f127d`, as CI/toolchain.json/runtime profiles and both receipts require. Actual host java/javac25.0.4.1 with `/usr/lib/jvm/java-25-openjdk/bin` first in PATH, Quarto1.11.5 and CUEv0.17.1 were observed; actual container Java25.0.4+7 is recorded by executed image, not assumed equal to host patch version. No runtime/pins/compiler options/provider/vendor changes.

Dependency-only M01 Quarto cache was copied into new task-local cache; prior generated models/receipts/results/catalogs were not transplanted. `JAVA_HOME`, PATH/CUE, PL_PLATFORM_ROOT, CI_CHECK_OUTPUT, XDG_CACHE_HOME, TMPDIR, CI_LOG_DIR/trace and PYTHONDONTWRITEBYTECODE were process-local (`scoped-environment.json` and actual command records). No HOME repurposing, global installs/settings or live grading. Prepared grader jobs and native generated outputs belong only to this new evidence/clone. Stock tools clean temporary job/build-stage directories themselves; their actual raw result records and generated source snapshots are retained.

## Full stock CI attempts and export blocker

The initial `bash CI/check-projects.sh` generated a complete declared inventory but exited1 after169.736s: `BODY.QRC_REFERENCE_UNRESOLVED: practice:sec-j25-practice-arrays-row-key`, source `bank/arrays.qmd`, exercise `exr-j25-arrays-sort-matrix-rows`, field `condition`. Both full attempts invoked stock collection/full export without ready-only/id/scenario subsets. Native delivery was not emitted; neither delivery backend ran.

Initial hypothesis: the clean clone lacked cross-project generated catalogs. Investigation found the exact target exists at practice/arrays.qmd:98, docs/prairielearn-labs.md:31–52 documents render before check-projects, and reference-catalog/entrypoints/post.ts is the native catalog publisher requiring actual render outputs. Root authorized one exact stock `bash CI/render.sh student` prerequisite, not an exporter bypass or M05 cycle. Its underlying root render uses `--fail-if-warnings`, completed0 in461.740s, and published fresh native root catalog with1299resolved/0deferred/1380targets/121pages. Native child trace and unfiltered logs are retained. No full-profile/student→full→student acceptance is claimed.

All1360 source files still matched and isolated Git was clean. Full stock CI was retried into fresh checks-retry with scoped Docker permission escalation; it exited1 after210.163s with the **same** unresolved condition QRC. The catalog contains that exact practice target, while actual standalone native export JSON retains `data-qrc-ref`. Thus missing-catalog-only hypothesis is **refuted**. `persistent-export-failure-proof.json` and complete `initial-arrays-export-document.json`/`retry-arrays-export-document.json` preserve both ASTs and exact catalog target/hash. Two read-only diagnostic extraction helpers initially asserted an incorrect single-Div assumption (including mirrored metadata); complete actual JSON was then retained without guessed uniqueness. No course/test/tool correction or runtime rerun arose from those helper incidents.

No third CI retry, QRC/config/source rewrite, catalog transplant, vendor fix or manual native ZIP was attempted. Root owns separate export diagnosis. Initial Docker image inspection failed permission-denied at the socket and is preserved in preflight command logs; read-only escalated inspection succeeded against the cached exact image. This is a tool permission retry, not an image repull/install or changed grader.

## Authorized unaffected full declared host/container diagnostics

Root explicitly authorized supported stock `check-course.py verify --scope declared` on the existing unchanged complete declared manifest for BOTH backends, while retaining export failure. No `--ready-only`, `--id` or `--scenario` selection was used. These diagnostic declared receipts are distinct from missing delivery receipts and cannot close selected14 delivery.

Both backends execute **74** required scenarios for all17:17reference +17starter +40contract fixture scenarios. Parent scenario results are74per backend/148total; nested public verification records remain in raw results and are not counted as new parent scenarios. Expected starter/contract refusals remain expected outcomes, not falsely described as every individual test scoring full points.

- Host: exit0,358.953s,17inventory/17checkedIds/74results, receipt success/coverage complete, all74expectations matched, no unavailable references and all infrastructure complete.
- Container: exit0,720.814s,17inventory/17checkedIds/74results, receipt success/coverage complete, all74expectations matched, no unavailable references and all infrastructure complete. Stock runner uses network none and its pinned native CPU/memory/PID/outer-timeout limits; no isolation/PerfData option override.

Both bind sourceSnapshotHash `8b14fab2a0652cb2c5ac846dbf780ce0fc6f3410a0c11a7fd6a8efe8f2b26a30`, inventoryHash `41ca1ac25bf2ba67d1890c5007552943a38cd1a6a663add92e4aa70cf151717d`, scenarioInventoryHash `c129ad1fa9d36c9efcaf633726860ffc9b0a2d366965aee1cb8efb6aba417360`. Question/runtime/image bindings and runtime dependency closure match exactly; dependencyHash `3614c660a9010817beec415b909529d126b3403213b27996e27386bedbbb6c3a`, runner SHA `48f29c813ee923d50062774034f2c530da6a49588d2f891d51c4c9d9cc76f63b`. Full actual per-case source/tests hashes, compilation/test/public-result/output/diagnostic/toolchain/containment/duration data are in diagnostic-declared-{host,container}.json. `declared-required-scenarios.json` and `all17-project-layouts.json` preserve exact inventory; layouts were discovered, not assumed uniform.

| Qualified exercise | Actual project root | Actual source layout files | Required scenarios | Host matched | Container matched |
|---|---|---:|---:|---:|---:|
| `java-semester-one/exr-j25-arrays-array-average` | `projects/array-average` | 9 | 4 | 4/4 | 4/4 |
| `java-semester-one/exr-j25-arrays-move-row-zeros` | `projects/move-row-zeros` | 10 | 5 | 5/5 | 5/5 |
| `java-semester-one/exr-j25-arrays-reverse-array` | `projects/reverse-array` | 9 | 4 | 4/4 | 4/4 |
| `java-semester-one/exr-j25-arrays-sort-matrix-rows` | `projects/sort-matrix-rows` | 10 | 5 | 5/5 | 5/5 |
| `java-semester-one/exr-j25-arrays-subtract-array-average` | `projects/subtract-array-average` | 10 | 5 | 5/5 | 5/5 |
| `java-semester-one/exr-j25-control-count-console-spaces` | `projects/count-console-spaces` | 9 | 4 | 4/4 | 4/4 |
| `java-semester-one/exr-j25-control-month-name` | `projects/month-name` | 10 | 5 | 5/5 | 5/5 |
| `java-semester-one/exr-j25-control-multiplication-table` | `projects/multiplication-table` | 9 | 4 | 4/4 | 4/4 |
| `java-semester-one/exr-j25-control-number-extrema` | `projects/number-extrema` | 9 | 4 | 4/4 | 4/4 |
| `java-semester-one/exr-j25-control-toggle-console-case` | `projects/toggle-console-case` | 9 | 4 | 4/4 | 4/4 |
| `java-semester-one/exr-j25-numbers-boolean-truth-table` | `projects/boolean-truth-table` | 9 | 4 | 4/4 | 4/4 |
| `java-semester-one/exr-j25-numbers-console-greeting` | `projects/console-greeting` | 9 | 4 | 4/4 | 4/4 |
| `java-semester-one/exr-j25-numbers-gallon-conversion` | `projects/gallon-conversion` | 9 | 4 | 4/4 | 4/4 |
| `java-semester-one/exr-j25-numbers-interval-membership` | `projects/interval-membership` | 9 | 4 | 4/4 | 4/4 |
| `java-semester-one/exr-j25-numbers-lightning-distance` | `projects/lightning-distance` | 9 | 4 | 4/4 | 4/4 |
| `java-semester-one/exr-j25-numbers-lunar-weight` | `projects/lunar-weight` | 9 | 4 | 4/4 | 4/4 |
| `java-semester-one/exr-j25-numbers-short-circuit-and` | `projects/short-circuit-and` | 11 | 6 | 6/6 | 6/6 |

## Assignment boundary and remaining work

`selection-source-proof.json` records exact accepted source slots:14positions6/4/4,17candidates7/5/5, three random alternatives with persistence prose. All17 candidates equal the declared manifest set. This is source selection proof, **not** a successful native delivery/assessment assignment test. No native selected14 receipt exists. First three PL labs keep random selection; later manual alternatives remain chosen by text. Manual gradeapp is not PL18 and has no automatic grading claim.

Exact25 materialized gradeapp/student17/private6+1 and all25 corresponding U05 prototype bytes/modes still match the accepted manifest; old materialization evidence remains unchanged (before/after hash manifests). Five TODOs and provided Student/CLI/bootstrap/output roles were preserved. No fresh gradeapp Maven/native ZIP/unpack/bootstrap/reference/private13/public4 or two-implementation suite was run in this window. Those remain full M04 downstream. Behavioral checks do not prove manual source features (`&&`, required variable/type/technique choices); no hidden requirement was introduced or statement redesigned.

Full Task13 acceptance still awaits full M02, successful native export and full selected14 host/container delivery, remaining gradeapp/full M04 work, M05 commands/privacy/links/student→full→student and independent pedagogy/student pilot. Read-only diagnostic results may be carried only with exact unchanged source/tool/runtime/backend/selection bindings; rehash is not a new test run. No previous CI waiver was used for local checks.

## Actual commands and freeze

- `full-ci-initial`: `bash CI/check-projects.sh`; exit **1**, 169.736 seconds; raw `full-ci-initial.stdout.log`, `full-ci-initial.stderr.log`, `.result.json`.
- `student-render-prerequisite`: `bash CI/render.sh student`; exit **0**, 461.74 seconds; raw `student-render-prerequisite.stdout.log`, `student-render-prerequisite.stderr.log`, `.result.json`.
- `full-ci-retry`: `bash CI/check-projects.sh`; exit **1**, 210.163 seconds; raw `full-ci-retry.stdout.log`, `full-ci-retry.stderr.log`, `.result.json`.
- `docker-image-escalated`: `docker image inspect ghcr.io/afonenko-course-tools/java25-grader@sha256:f791e5590e6102961669e838cb2476f2dde91d888b0fb5a5292bc226cb2f127d --format {{json .RepoDigests}}`; exit **0**, 0.016 seconds; raw `docker-image-escalated.stdout.log`, `docker-image-escalated.stderr.log`, `.result.json`.
- `diagnostic-declared-host`: `python3 /home/tolya/course-tools/performance-20261012/runtime-platform/tools/check-course.py verify --manifest /tmp/java-course-revision-checks/M04/ready-project-checks/checks-retry/declared-checks.json --snapshot /tmp/java-course-revision-checks/M04/ready-project-checks/source --scope declared --backend host --output /tmp/java-course-revision-checks/M04/ready-project-checks/diagnostic-declared-host.json`; exit **0**, 358.953 seconds; raw `diagnostic-declared-host.stdout.log`, `diagnostic-declared-host.stderr.log`, `.result.json`.
- `diagnostic-declared-container`: `python3 /home/tolya/course-tools/performance-20261012/runtime-platform/tools/check-course.py verify --manifest /tmp/java-course-revision-checks/M04/ready-project-checks/checks-retry/declared-checks.json --snapshot /tmp/java-course-revision-checks/M04/ready-project-checks/source --scope declared --backend container --output /tmp/java-course-revision-checks/M04/ready-project-checks/diagnostic-declared-container.json`; exit **0**, 720.814 seconds; raw `diagnostic-declared-container.stdout.log`, `diagnostic-declared-container.stderr.log`, `.result.json`.

All writes were confined to this packet, own scratch report and NEW evidence `/tmp/java-course-revision-checks/M04/ready-project-checks`. No shared source/project/test/config/CI/vendor/pin/Git/journal edit, agent/chat creation, publication, installation or global setting change. Full ready substep concerns are preserved rather than waived. `final-verification.json` records exact results and integrity; `frozen-evidence-sha256.json` seals command/raw/selection/result/tool/source manifests and new packet/report. Freeze ends this write window; await root follow-up.
