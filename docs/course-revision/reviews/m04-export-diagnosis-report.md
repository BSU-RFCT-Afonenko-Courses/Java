# M04 native export diagnosis — frozen

Scope: read-only causal investigation on accepted source HEAD `719246db58d343cc4d66ff6b99201737606cd2d0`, remote PR13 baseline `a0d1bdb9d8343809560e3ce7f9a2141d4473ee3f`. Requested `gpt-6.1-sol / medium`; effective settings `not_reported`. Applied systematic-debugging. Owned only this report and new `/tmp/java-course-revision-checks/M04/export-diagnosis`. No source/vendor/config/CI/Platform/project changes, Git mutation, live grading, new agents/chats/publication, broad rerender, backend run or third full CI repeat.

## Finding

This is a native Body/QRC capability conflict, not a missing/renamed target or incomplete HTML catalogue. The exact approved target `practice:sec-j25-practice-arrays-row-key` exists at `practice/arrays.qmd:98`, and the successful root student render generated its catalogue record. Both full stock CI attempts fail before either delivery backend at the same Body guard. The JSON marker is intentionally emitted by installed QRC, but installed Core does not implement any marker-to-Link resolution. The comment that the selected-body producer “resolves or rejects” currently means rejection for every remaining `data-qrc-ref`.

The current source preparation line at `tasks/bank/arrays.qmd:77` includes this practice target and `handbook:sec-j25-api-arrays-sort-stability`; the first marker stops the producer. Substituting another QRC target cannot address the generic guard. No target alias, suppression, omitted preparation, or private/HTML bypass is recommended.

## Exact pipeline and contract evidence

Paths below are relative to the accepted course checkout unless stated otherwise; inspected clone paths have identical content.

1. `CI/check-projects.sh:11–26` inventories declarations, invokes Platform `build-course.py`, then loops both host/container delivery verifies. Both attempts stopped in build-course, so neither delivery verify ran.
2. `/home/tolya/course-tools/performance-20261012/runtime-platform/tools/build-course.py:137–142` invokes installed `tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/entrypoints/export-course.ts`, passing only root/output, instance, checks-output, runtime-registry, book (optional candidate-image). The CLI accepts those same flags at `entrypoints/export-course.ts:8–20`; no catalogue input or QRC-resolver flag exists.
3. Owner entrypoint `export-course.ts:84–104` collects Native model/checks and supplies a `buildBodies` callback with projectRoot, courseId, work, includeClosed. It passes no publication catalogue/address.
4. `course-core/body-export/collect.ts:45–65` creates a temporary native profile with `course-export-context: true`, JSON writer and full source. `:95` invokes Quarto native JSON render. `:127–168` reads actual native AST and replaces full/public exercise bodyJson from its nodes, preserving markers.
5. `reference-catalog/lua/links.lua:10–16` deliberately makes `Span` class `qrc-reference`, attributes `data-qrc-ref`, style, custom when course-export-context is true. HTML instead gets an unresolved Link at `:18–20`.
6. `course-core/body-export/producer.ts:206–215` calls `resolveReferences`, checks standalone fragment targets, then supported nodes. `body-export/references.ts:15–139` resolves only Pandoc Cite nodes via citeproc; it neither loads catalogues nor translates QRC Span. No citation in a reduced QRC-only fixture means its original marker returns unchanged.
7. `producer.ts:66–67` unconditionally throws `BODY.QRC_REFERENCE_UNRESOLVED` for any Attr containing `data-qrc-ref`. This matches both actual full CI stack traces and the targeted reduced repro.
8. HTML catalogue publication is separate: `reference-catalog/infrastructure/publish.ts:63–75` filters current outputs to `.html`, parses pages, invokes `linkPages`; `linker.ts:18–24` requires QRC request nodes to be HTML `<a>`. It cannot transform a JSON AST Span. Root HTML catalogue readiness therefore cannot repair Body.
9. Exact released owner documentation `/home/tolya/course-tools/performance-20261012/controls/quarto-course/docs/body-export.md` has frontmatter version6.0.0. Its Native section documents source JSON, bibliography/fragment guards, unresolved selected QRC failure and no foreign body import. This is the applicable source-export API, not the older v1 owner lifecycle docs from other historical checkouts.

Toolchain is actual `CI/toolchain.json`: Quarto1.11.5, CUE0.17.1, Java25, Platformv2.0.0 commit `b971a61a2386fbf4ae7fcfc9394d307359bc7889`, Java grader OCI digest `f791e5590e6102961669e838cb2476f2dde91d888b0fb5a5292bc226cb2f127d`. No pin was changed.

## Preserved actual failures

Evidence root `/tmp/java-course-revision-checks/M04/ready-project-checks` remains untouched by this investigator.

- `full-ci-initial.result.json`, stdout/stderr: `bash CI/check-projects.sh`, cwd exact clone, exit1 after169.736s.
- `student-render-prerequisite.result.json`, logs and `render-logs/render-1-student.log`: authorized stock `bash CI/render.sh student`, exit0 after461.740s, final QRC1299resolved/0deferred/1380targets/121pages.
- `persistent-export-failure-proof.json`: generated root catalogue has exact practice target; root catalogue SHA256 `81ec5cdb7922f626cd6e061ee84d18c67827c78eecfd072a7973c5e571d78e1d`.
- `full-ci-retry.result.json`, stdout/stderr and `retry-child-commands.jsonl`: exit1 after210.163s, same BODY diagnostic; actual builder argv recorded in child trace. There was no third attempt.
- `initial-arrays-export-document.json` and `retry-arrays-export-document.json` are actual saved full writer outputs with identical SHA256 `508c132cd3e1c21e17cb893025f69797ed8212b31930151251afd8d1c8a70740`. Repeated occurrences include mirrored metadata, not distinct question assertions.

Actual retry builder command:

```sh
python3 /home/tolya/course-tools/performance-20261012/runtime-platform/tools/build-course.py --source /tmp/java-course-revision-checks/M04/ready-project-checks/source --book tasks --instance pilot --output /tmp/java-course-revision-checks/M04/ready-project-checks/checks-retry/native
```

## Targeted reproduction and supported representations

All new evidence is under `/tmp/java-course-revision-checks/M04/export-diagnosis`. `prepare-repro.py` and `prepare-links.py` made exact isolated installed Core/PL subtree copies; `exact-copy-manifest.json` and `exact-pl-copy-manifest.json` verify every file SHA256 and mode against immutable M04 clone. No implementation file was altered. The marker was extracted unchanged from the actual retry AST (`actual-marker.json`). Harness model/project data are explicitly synthetic reduced fixtures, not new full-course acceptance.

Commands from this new scratch, scoped XDG_CACHE_HOME to scratch/cache and TMPDIR to scratch:

```sh
quarto run repro.ts
quarto run link-repro.ts
```

- `repro.stdout.log`: unchanged buildBodies on one actual marker yields the exact expected BODY.QRC_REFERENCE_UNRESOLVED condition error; harness exit0 means expected rejection captured, not export success. stderr empty.
- `link-repro.stdout.log`: unchanged native PL adapter rejects `../../practice/arrays.html#sec-j25-practice-arrays-row-key` with ADAPTER condition.resource; rejects `#sec-j25-practice-arrays-row-key` with condition.reference because target is outside the standalone public question.
- The same adapter accepts an ordinary HTTPS Link and preserves visible preparation label and exact path/fragment in question.html. Test host `example.invalid` is intentionally fictitious, solely proving capability; it is not proposed as a real course URL or correction. No network request was made.

Installed PL `application/export.ts:423–455` permits ordinary Link only as question-local fragment, exact bundled public resource, or HTTP(S). Core resource scan `producer.ts:229–246` leaves qmd/html links as links; it does not bundle course chapters. A relative source qmd/html replacement therefore merely moves failure downstream. Packaging a whole practice page as question resource has no established supported/public closure here and is not a minimal correction.

Original PR13 `tasks/bank/arrays.qmd` sort-matrix-rows question contained condition, contextual starter download, bibliography, but no preparation QRC line. Current preparation is added accepted author content; historical absence is not authorization to remove it.

Course `docs/authoring-methodology.md:441,464` requires QRC links for transitions between course materials. Current root `_quarto.yml:52–55` supplies publication.title only, no publication base URL/site-url. Catalogue target has relative page/fragment and no verified public host. Ordinary HTTPS Link could technically preserve the exact association only after a verified publication URL and explicit author/R1 permission for that source representation; neither is currently available. Body package CUE and native producer transport no preparation-link metadata field that independently resolves these targets. Generic prerequisites pedagogy does not create a Body catalogue input or external address.

## Recommendation and affected checks

No supported stock prerequisite or existing CLI command resolves this conflict. Freeze as structural conflict for root/author R1 handling. Do not independently patch vendor/pins/CI, manufacture a publication URL, replace semantic target, drop public preparation, add aliases, or suppress BODY guards.

Within current task constraints, a possible author-owned representation decision needs a verified canonical HTTP(S) publication URL AND explicit exception to course QRC representation policy, preserving both practice and handbook exact targets and visible preparation in published bank and native public condition. Reproduce ordinary source render/native collection and direct PL output before adopting it. Without those prerequisites, no source-only supported solution meets the current combined contracts.

A future native-QRC implementation would require explicit separate owner/vendor/pin authorization and an actual documented catalogue/address input. It must translate only verified approved targets into portable public Links with existing strict failure/privacy guards, not suppress validation. No such change is performed here.

After an authorized correction, affected checks are: reduced QRC/Link contract cases, native export of selected array work including both preparation links, then fresh stock full `CI/check-projects.sh` at exact changed source/toolchain with BOTH delivery host/container. Preserve random6/4/4 positions/7/5/5 candidates and all17 membership. Recheck root student/full/student links/privacy if source representation or vendor changes affect HTML. M04 independent declared17 diagnostics do not substitute for these delivery checks. Full Task13/M05 acceptance remains open.

## Integrity and limits

Read-only Git status on M04 clone was empty; HEAD unchanged719246d. Core/PL reproduction copies match original file bytes/modes; source clone/generated outputs, existing failure evidence and Platform remained read-only. Initial diagnostic tool invocation failed before shell execution because requested new scratch cwd did not yet exist; it was corrected by creating the owned directory, no source or test failure was hidden. No successful full native export, selected delivery backend or whole-course completion is claimed. Reports/evidence frozen; new mutation requires root window.
