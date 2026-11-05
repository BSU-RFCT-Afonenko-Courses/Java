# Прямые потребители Core — ограниченный разбор исходников

5 ноября 2026. Проверен новый Core `d21ddfdc9e4fde58145275751dccfc1c997777df` и закреплённые ниже refs потребителей. Код потребителей не изменялся. Render, inspect, build, компилятор и матрицы не запускались. Выводы о совместимости ниже получены чтением исходников; результаты проверок B явно отделены.

## Download PR19

Исходный ref: `09e797a1f56bdeb31a66c3e2afa7b8aba7f90321`. Уже подготовленный отдельным владельцем кандидат: `106b958bbf443cd7e220d250117466b434e279de`, `/tmp/java-release-work-20261105/download`.

Исходный ref несовместим с новым Core:

- `_extensions/project-download/application/package-artifact.ts:26`: `ArtifactInventorySchema` удалена; кандидат читает `ArtifactFilesSchema` на строке 25.
- `_extensions/project-download/infrastructure/runtime.ts:476`: `operation.resourceInventory` удалён; кандидат вызывает `resourceFiles` на строке 472 и передаёт реальные bytes упаковщику.
- Там же, строки 537–540: `withProjectContracts({inputs})` требует нового `{directories}`; кандидат использует новый договор в строках 533 и далее, без synthetic QMD.
- `ownedSignature` и повторная сверка request-снимка в старом runtime (185, 240, 570–584), SHA-256 в `infrastructure/ownership.ts` замкнуто удалены кандидатом, по отчёту независимого reviewer и чтению актуальных вызовов.

От B получено: общий настоящий typegraph Core d21 + PL f184 + Download106 + QRC541 независимо APPROVE, findings 0. Это подтверждение B, здесь проверки не повторялись. Download не требует второго writer.

Остаток собственного SHA: `_extensions/project-download/filter.lua:67` и на исходном ref, и в кандидате сохраняет `pandoc.utils.sha1(context.source)` как имя request JSON. Это адресация, не проверка свежести. Если требование удалить собственные SHA распространяется на этот адресный механизм, его остаётся замкнуть действующему владельцу без нового integrity-заменителя; иначе его нельзя ошибочно объявлять удалённым вместе с SHA-256.

## QRC PR22

Ref: `541a44836cf5ec934a101059cbee77c9ff47d134`, `/tmp/download-deps-qrc-20261105`.

Прямого импорта Core в действительных исходниках QRC нет. `_extensions/reference-catalog/public.ts` экспортирует собственные обработчики ссылок и публикацию каталога. `entrypoints/condition-links.ts:19–21` принимает структурный аргумент `{context:{input:string},links:readonly string[]}`; строки 38, 51, 67, 94 используют input документа Body. Это сохранённый контекст условия, не новый directory input `ProjectContractContext`; заменять его на каталог нельзя. `entrypoints/body-links.ts:12` работает с настоящим каталогом адресов.

`infrastructure/imports.ts:35–51` называет Map `snapshots`: это локальное повторное использование Promise чтения одного каталога в одном вызове `importTargets`. SHA, source-tree receipt или последующей проверки свежести там нет; удалять этот кеш по одному имени не требуется. Удалённых Core API/DTO в просмотренном production QRC не найдено. Необходимого пакета правок QRC по текущему изменению Core не выявлено.

## Site PR21

Ref: `2b397815cfdbf0e860405559de68067fe20204b4`; файлы прочитаны через GitHub на этом commit, старый пользовательский checkout не изменялся.

- `_extensions/course-site/application/sibling-contracts.ts:1–11,22–28`: реальные типовые импорты `typeof Core`, `NativeCatalog`, `NativePublication`; удалённых `NativeReleaseProof`, inventory или origins нет.
- `application/compose.ts:227–255`: `Core.withNativePublication(request, selection, callback)` соответствует сохранённой сигнатуре Core `runtime/operation.ts:1890–1900`. Callback читает `publication.catalog`, `publication.course.adapters`, вызывает `PL.inspectPublication(publication)` и `Download.prepareNativeDownloads(publication,...)`; выдача draft выполняется после callback. Эти вызовы сохранены.
- `entrypoints/collect.ts` → `infrastructure/collection.ts` не вызывает Core. Этот путь сам по себе не требует миграции.
- `compose.ts:232,420,615` передаёт selectedSources как функциональный выбор документов. `snapshot` на строках 227/257 — имя результата callback, а не собственный снимок исходников. `active.json`, коллекция результатов и `composition.json` описывают выбранные реальные результаты, без digest/freshness графа.

В просмотренных production TypeScript Site не найдено удалённых Core вызовов или собственного SHA-механизма. Обязательной правки Site для нового Core не выявлено. Это не новая приёмка полного рендера Site; интеграционный запуск принадлежит B/D.

## Performance PR6

Ref: `7a1645936924ba0dd710c9121a8871a13d67a84b`; исходники прочитаны через GitHub на этом commit. Прямого импорта изменённого публичного Core API в просмотренных production TS не найдено; подключение расширений идёт через метрики/bridge. Поэтому удаление `ArtifactInventory` и `NativeReleaseProof` само по себе не доказывает поломку этого ref.

Собственные SHA/DTO и читатели остаются:

| Производитель | DTO/читатель и значение |
|---|---|
| `src/sources/snapshot.ts:2–9,43–52` — SHA-256 файла/дерева | `src/sources/packages.ts:65` → `PackageReceipt.packageHash` в `src/config/types.ts:20–27` → manifest подготовленного варианта. Полный обход/чтение выбранного пакета. |
| `src/adapters/quarto/capabilities.ts:69–86` — hashes Quarto bundle/Deno/Pandoc/filters | `SourceManifest.runtime` (`config/types.ts:74`), SHA shape reader 127–132, повторный reader `src/sources/prepare.ts:11–15`; `freeze.ts:19` добавляет registry hash. |
| `src/launchers/setup.ts:81–109`, `launchers/prepare.ts:8–17`, `adapters/quarto/filter-trace.ts:10–20`, `runner/diagnostic.ts:84–95` | `PreparationReceipt.sourceHash/preparedHash` (`config/types.ts:91–96`) → run record → обязательный reader `src/report/types.ts:345–354`. Нельзя убрать только producer. |
| `src/runner/diagnostic.ts:64–67` — manualRegistry hash | coverage JSON, текущий путь experiment диагностики. |
| `src/runner/package-coverage.ts:59–60,91,123` — hashes canonical bridge, проверки bindings, evidence sha256 | `PackageCoverage.packages[].instrumentationEvidence` на строке 15, coverage/observations читатели. Это собственный обход и доказательство по файлам расширений. |
| `src/analysis/historical.ts:3,11,26` — SHA metadata отчётов | historical provenance → `src/report/types.ts:83–85` manifest/runManifest sha256; обычные отчёты импорта продолжают получать собственные SHA. |
| `src/cli/experiment.ts:176–185` — hash Hyperfine executable | runtime manifest, затем тот же `verifySources`. |
| `src/analysis/outputs.ts:50` — hashes обеих версий результата | Функциональное сравнение bytes можно сохранить без собственного digest-контракта. |

Стоимость затрагивает обычный режим: `src/runner/render.ts:111–113` для summary/cpu вызывает `prepareRenderDiagnostic`; `src/runner/diagnostic.ts:130+` вызывает `probeQuarto` и `prepareLaunchers`, поэтому runtime/init hashes не ограничены командой experiment. Полное instrumented package-coverage дерево вызывается из `prepareDiagnostic` experiment (`diagnostic.ts:42–48`), а не из обычного summary. Эти две цепочки нельзя смешивать в оценке стоимости.

Необходимое замыкание Performance: один назначенный владелец должен удалить собственные digest-поля/перепроверки из перечисленных producers → DTO → текущих consumers/tests и расчёт пакетного доказательства, сохранив реальные измерения, события, диагностику неполноты, paths/versions, нативный Git и функциональную установку выбранных пакетов. Процессные записи запуска/завершения, защита путей и копирование рабочего варианта сами по себе не являются запрещённым freshness fence; удалять их только по слову receipt/snapshot нельзя. Не создавать mtime/inode/registry заменитель доказательства. Исторические архивные отчёты не накатывать и не переписывать; текущий импорт должен обходиться без нового собственного digest.

Это отдельный незавершённый пакет, ещё не исправление и не измерение ускорения. Ранее зафиксированные bounded summary PASS, compiler blocked и отсутствие доказанной full-coverage/speedup/TERM сохраняются. Ни одну старую матрицу этот разбор не возобновляет.

## Передача

Download остаётся у владельца B. QRC/Site не требуют нового writer по обнаруженным Core вызовам. Для Performance нужен явный отдельный пакет и владелец; до такой передачи правок не было. Собственный допуск A проверяется от исходного хода `01a1a88b-ede4-7672-b7fb-ccb3c151255c`, без сдвига начала.

После read-only этапа координатор назначил A отдельный пакет Performance. Он завершён в `86f762e317b237320657a3319c70701d03c72a2e` (функциональный код `6c01f315`), опубликован в PR8 поверх сохранённого PR6 и независимо принят. Итоговый статус: `performance.json`, рецензия: `performance-review.md`. Выводы выше относятся к исходным refs аудита.
