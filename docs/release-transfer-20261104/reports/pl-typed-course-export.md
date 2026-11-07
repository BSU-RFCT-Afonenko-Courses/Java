# Типы PL materialization → course exporter → student writer

3 ноября 2026. Следующая ограниченная партия максимальной типизации по `typescript-any-audit.md`.

## Исходники и собственный допуск

PL Source `/home/tolya/JavaCodex/java-release-20261110/prairielearn`: начальный HEAD `da2ad6b6c00891590e59e5abccf3d1eb4ff552f7`, дерево чистое. Предыдущие типовые/CI партии не переделывались. Координатор сообщил hosted CI `41684816393 SUCCESS`; старые green suites,106 cases и cross-filesystem проверки не повторялись.

Собственный агент `/root/pl_typed_materialization`, chat `01a1a25f-b098-7242-bf6d-d0e8f6517361`, parent `01a1a142-7adc-7371-9765-4871d807dfcd`. Допуск этой партии относится только к новому собственному интервалу: первичный файл `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-05-42-01a1a25f-b098-7242-bf6d-d0e8f6517361.jsonl`, `turn_context` строка596, turn `01a1a29e-7f25-7462-b293-23e86224f34f`. Все записи собственного нового интервала имеют top-level `model=gpt-6.1-sol`, `effort=high` и вложенные settings `model=gpt-6.1-sol`, `reasoning_effort=high`. Назначен Standard; первичные записи не удостоверяют серверный service tier. Глобальные/normative admission документы и общий state не менялись.

До правок сообщены и приняты координатором ровно три production-файла и один negative-case. Продолжены прежний approved approach и skill verification-before-completion; Source/worktree уже выделен координатором, дополнительный checkout/ledger state не создавался.

## Решение и diff

`native-course.ts` сохраняет настоящий `Awaited<ReturnType<typeof materializeNativeQuestion>>` в `artifacts`. Массив project projections и map `nativeInputs` выводятся из соответствующих полей того же return type. Раньше эти три контейнера снова объявлялись any и стирали результаты предыдущей принятой партии. Убрана лишняя any-аннотация при чтении selected source names.

`export-course.ts` принимает `NativeCompilerInput`: узкую consumer projection существующего materialization selection. Descriptor берётся целиком через `Pick`, файлы — только потребляемые `name`/`bytes` через indexed types существующего producer. Сохраняются настоящие inferred byte buffer types, mode/scoring/feedback и остальные descriptor поля. Это не новая runtime schema и не копия Core contract. Карты native inputs больше не стирают source/test bytes, когда exporter перекладывает их в writer records. Три map callback получили выводимый тип вместо any.

`export.ts` даёт имя прежнему inline контракту `StudentArtifact` (`files[{name,bytes}]`, `archive: Uint8Array`). Его используют writer `ProjectContext.studentArtifacts`, локальный container exporter и return type `studentArtifact` callback. Минимальный fact-input callback выводится через `Pick<NativeProjectFact, 'exerciseId'>` — это ровно поле, которое использует текущий consumer. Callback native-course возвращает существующий materialization result, структурно совместимый с этим writer DTO.

Добавлены только type-only imports и аннотации; actual runtime operations, fields, ordering, selection, body/wire, authority/currentness/lifetime/scoring/publication semantics прежние. StudentArtifact — имя уже существовавшей статической формы, без нового runtime API.

Три non-null аннотации выражают существующие корреляции control flow, которые TypeScript не удерживает через maps и spread возвращаемого object: checked materialization одновременно создаёт project и selection; соответствующие projects/nativeInputs пополняются одной веткой; selected descriptor приходит только из checked project. Новые guards не добавлялись и runtime lookup не заменялся обходным путём.

Всего production scope — `native-course.ts`, `export-course.ts`, `export.ts`. Core, installed/courses, Download/QRC loaders, external owner JSON, upstream assignment batch, answers/AST, registry и staged publication outputs не редактировались. CI pin830 оставлен без изменений. Новых casts, any, unknown-вместо-any, ts-ignore/ts-expect-error нет.

## Проверка

В существующий `tests/native-core-typecheck.ts` добавлен режим `--course-export`, который проверяет только новый affected graph и один новый negative, без повторного запуска прежних восьми controls. Default checker также знает новую positive fixture/negative для будущих явных запусков; дополнительная suite/matrix не создавалась.

`tests/native-course-export-types.ts` проверяет реальный producer→consumer assignment без casts; совместимость materialized files/archive с StudentArtifact; exact consumer/writer artifact types и exact descriptor/native bytes с отдельным отказом any. Тип StudentArtifact сохраняет прежний общий Uint8Array contract; более узкий inferred ArrayBuffer producer допустим по assignability и не подменяется требованием обратного равенства.

Итоговая команда:

`quarto run tests/native-core-typecheck.ts ../core --course-export`

Exit0. Один общий положительный graph: `native-course.ts` (включая exporter/writer imports), новая type-only fixture и сам runner. Единственный negative `native-writer-source-bytes`: вызов реального `exportCourse` с legacy source `{name, data}` вместо native `{name, bytes}` получает ожидаемый compiler `TS2353`. Runner требует ненулевой exit и конкретную диагностику. Direct static call, без подавлений/динамического обхода типов.

Первый проход исправлен до завершения: compiler обнаружил невыраженную correlation `project → selection`; fixture первоначально требовала слишком сильное обратное равенство byte buffer типов. Добавлена подтверждённая существующим producer non-null аннотация, а fixture разделила compatibility общего StudentArtifact и точное сохранение native bytes. После исправления итоговый affected graph и единственный negative зелёные; зелёные checks не повторялись.

`deno fmt --check` пяти изменённых TS-файлов — exit0. `git diff --check` — exit0. Собственный diff просмотрен. Runtime tests, native export, render/inspect/hash/build/install/push/full suite/ручной hosted CI не запускались: runtime не менялся.

Фактический type-only map указывал на read-only Core Source с HEAD `d7cc10eaa21fa35bbe32d73cc4afa216002393da`; финальный status Core чистый. Это локальная Source type composition. Известный координатору отдельный full Core CI failure не скрывается и не приписывается PL patch. PL CI pin остаётся `830381697beea4106655965e10605cc13987199b` до отдельной интеграции; installed/courses не менялись.

## Остатки и пределы

Партия сохраняет уже полученные типы на конкретном handoff, а не заявляет строгую типизацию всего native-course. Остались upstream `withNativeProjectPlans`/`withNativeAssignments` any, контент/answers/settings/body maps, registry/binding JSON, provider dynamic APIs, verification helper и return handles. Они не расширяли этот diff. Type annotations не дают authority и не заменяют live Core gates. Независимый review нужен до push; state и integration ведёт root.

## Source commit

Commit `09064082b48b1bd60b7d3910098db30138d9fed6` — «Сохранить materialization DTO до exporter и student writer PL», поверх `da2ad6b6c00891590e59e5abccf3d1eb4ff552f7`. Ровно пять перечисленных files, `124 insertions(+), 29 deletions(-)`. После commit PL status чистый, `git diff HEAD^ HEAD --check` exit0. Push/install не выполнялись. Отчёт вне PL Source repo.
