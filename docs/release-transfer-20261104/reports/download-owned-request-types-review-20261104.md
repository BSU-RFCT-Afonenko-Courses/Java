# Download: независимое review consumed ownership types

**APPROVE** узкого Source candidate. Существенных замечаний нет; compiler для этой дельты остаётся PENDING.

Сопоставлены [отчёт](download-owned-request-types-20261104.md), [patch](download-owned-request-types-20261104.patch) и actual diff только `_extensions/project-download/ownership.ts` к базе `671093945a8a6f76c25e60840aee71964d995496`. Ранее принятые три wrapper-файла повторно не проверялись.

- `JSON.parse` по-прежнему принимается как `unknown`. `record` выражает прежний truthy/object/not-array guard; full `Request` больше не утверждается. `configuration` остаётся `unknown`, source/resources/artifacts приобретают только фактически проверенные типы.
- `ArtifactRequest` выводится из настоящего `resolveDownloadRequests(...).request.artifacts`. Его producer создаёт `{exerciseId: string, kind: "starter" | "full" | "conditions"}`. Predicate покрывает эти поля и сохраняет прежнюю проверку разрешённых ключей. Аннотация `readonly unknown[]` у membership списка не выдаёт непроверенное значение за union.
- Прежние `.some(invalid)` и новые `!every(valid)` эквивалентны: для resource — `typeof string` и тот же `RESOURCE_ID`; для artifact — та же последовательность object/non-array, keys, exercise ID и kind membership. Оба обхода останавливаются на первом неверном элементе и одинаково пропускают sparse slots; здесь массивы поступают непосредственно из JSON. Optional artifacts, пустые массивы и прежние diagnostics сохраняются.
- Исходные request/arrays не копируются и не нормализуются. SHA-1 имени получает тот же source; state получает прежние source/resources/optional artifacts и SHA-256 bytes. Guards, filesystem IO, порядок validation/deletion и protocol 1 не расширены. Type-only import не добавляет runtime dependency.

Compiler, runtime, tests, hash и CI не запускались; production Source, mainstate и frozen recovery не менялись. Это независимый high Source verdict по существующему admission данной роли, без новой архитектурной квалификации или backcompat обещаний.
