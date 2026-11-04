# SP native export после готовности QRC imports

3 ноября 2026. После независимого ACCEPT `sp-qrc-imports-review.md` и отдельного разрешения root выполнена ровно одна штатная attempt на SP `babe83d1a6c15289b4dd457fe4132bebeac0894c`, installed Core `830381697beea4106655965e10605cc13987199b`, PL `da2ad6b6c00891590e59e5abccf3d1eb4ff552f7`. Результат **PASS_NATIVE_EXPORT**, exit0, wall38.195870446s; timeout120s/TERM grace5s не достигнут, retry0. UTC `2026-11-03T17:47:52.523857+00:00` → `2026-11-03T17:48:30.719744+00:00`.

Command/cwd/env/exact refs в `sp-native-qrc-ready-execution.json`, stdout/stderr в `sp-native-qrc-ready.log`. Штатный export-course.ts с --instance pilot/--book tasks, прежними --checks-output/--runtime-registry. Registry тот же published Platform2.5.2 `sp-native-paired-published-runtime-profiles.json`. Persistent XDG cache `/home/tolya/JavaCodex/.cache`, QUARTO_RUN_NO_NETWORK=true, DENO_NO_PACKAGE_JSON=1. Никаких дополнительных URL flags, новых profiles или Source HEAD selection. Generated dependency catalogs student theory/practice/handbook использованы через принятые imports; повторно они не строились.

## Реальный результат и область

Обработаны все7configuredQMD: index; labs/work-environment; labs/data-processing; labs/version-control; optional/project-maintenance; delivery/units/sec-unit-version-control-restore-selected-state; delivery/units/sec-unit-project-maintenance-release. QRC post:31resolved/0deferred/141targets/7pages. Core outputs7,7native-ts author documents, готово0.2s. Download post успешен, native index.html создан. Затем7подготовок PL заданий завершились успешно. Прежние SourceChanged/DownloadOPERATION_INCOMPLETE/reference-binding/foreign-QRC отказы не повторились.

Actual export public directory `reports/sp-native-qrc-ready-public` содержит delivery.json/schemaVersion4, infoCourse.json,7question dirs и5assessment manifests для instance pilot. Private output `reports/sp-native-qrc-ready-private` — **JSON файл schemaVersion2**, не каталог. Machine-readable evidence из существующих outputs в `sp-native-qrc-ready-output-evidence.json`.

Delivery.json:7questions,5works,7distinct sourceRoot project paths. Это отдельные факты,7QMD не использованы для вывода количества questions/projects. Payloads:6submission.upload и1submission.workspace; все7grading.method=manual. Upload представлены file-state, launch-counter, two-result-pipeline, local-history, github-ssh, project-maintenance-release; workspace — restore-selected-state. В каждом реальном question output существуют question.html, info.json и clientFilesQuestion/starter.zip. Upload HTML содержит pl-file-upload. Workspace info.json содержит workspaceOptions с pinned image `ghcr.io/afonenko-course-tools/sp-git-workspace@sha256:a547a7419726857eb391476edcf8906ee4acc062250e1c29acaee4733d9de930`, home/port/gradedFiles; actual workspace/.pl-seed/starter.zip и manifest.json существуют, delivery.seed ссылается на них. Workspace HTML содержит pl-workspace; наличие также upload tag не переопределяет submission.mode из manifest.

Private selection scope связывает ту же operation45515b7e-dc8b-48d6-87ad-c6af6428a2f5, profiles[full], projectionauthor,7sources/5assessments. automaticIds=[] и projects=[]: экспорт manual состава успешен, автоматические grader payload/private reference acceptance отсутствуют в этом actual selected inventory. Семь product sourceRoot не приравниваются к private.projects. Runtime image startup, grading execution, upload student submission, live Workspace, deploy/production этим экспортом не проверены. Доставка обоих transport descriptors/assets подтверждена только на уровне local native outputs.

## Complete/seal и batch counters

Сохранённая generation `system-practice/tasks/_generated/course-spec/native-ts/45515b7e-dc8b-48d6-87ad-c6af6428a2f5/`. reservation.json statussealed, один child covers7inputs/usedtrue/postedtrue. complete.json содержит7documents и7releaseDocuments. Прочитаны существующие complete.state.metrics без новой сборки/обхода:

```json
{
  "inspectCalls": 1,
  "fullSourceFences": 4,
  "localSourceFences": 9,
  "sourceDirectoryReads": 710,
  "runtimeHashReads": 4,
  "runtimeHashBytes": 32244,
  "runtimePathReads": 120,
  "runtimeDirectoryReads": 0,
  "outputHashReads": 14,
  "nativeMetadataBytes": 39809,
  "childRenders": 1,
  "fullWalks": 1,
  "sourceHashReads": 873,
  "sourceHashBytes": 5134554,
  "resourceHashReads": 3,
  "nativeResourceHashReads": 1
}
```

Это persisted counters Core complete scope. Они не объявляются общим числом всех subprocesses QRC/Site/PL: например QRC сам вызывает свой inspect, чего CoreinspectCalls1 не измеряет. Новые counters не собирались посредством повторного render/inspect/hash. Complete counters фиксируют1Core child render,1fullwalk,873source hash reads; повторная capture ради цифр не выполнялась. Delivery hash из готового manifest `18b6ebefe629e381be3e514f44d84235484a20b6b15d007bf56c665f0a62be8e`, без собственного повторного hashwalk.

## Сохранность и ограничения

Java/SP git status после попытки clean. Source/config/IDs/UUID/goals/studentdata/production не менялись; Sourcewriter Core не использовался. Все старые failed generations/logs/partialoutputs и три student artifact sets сохранены. Никаких дополнительных tests/inspect/render/hash/Java/cleanup/push/PR. Вывод ограничен завершённым native export текущего manual selection и локальной поставкой transport assets, не готовностью всего релиза или grader/runtime/deploy.

Собственный весь прежний интервал от baseline01a1a261-9811-7ca3-8a48-e4482c3beafc, chat01a1a261-97e1-7352-ae02-b6721eb39fb9, перед передачей перепроверен: turn_context[8, 155, 244, 342, 393, 435, 484, 524, 617], top+nested Sol6.1/medium PASS. Standard prescribed, globals untouched.
