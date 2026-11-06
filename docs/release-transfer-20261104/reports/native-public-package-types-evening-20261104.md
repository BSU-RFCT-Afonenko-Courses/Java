# NativeOperation → публичный Body в PL: Source freeze

Source заморожен 4 ноября 2026 года для независимой рецензии. Это исправление исходного кода, ещё не результат compiler/runtime приёмки. В этом шаге не запускались compiler, tests, Quarto, renderer, Java/build, content hash, сеть; index/stage/commit/push не выполнялись. Чтение исходников, первичных метаданных и Git diff не является runtime-проверкой.

База: PL `15bc337a37a26898b65dd566045a9d6113b6694e`, Core `010ba917909fa7d44179d4a1a05291f88f061775`. Actual HEAD проверены перед freeze. Все 18 сохранённых baseline файлов совпали с соответствующими файлами PL HEAD; изменены только перечисленные ниже 17 файлов. Core worktree чист, исходный код Core не изменён; другой checkout `/core/` не использовался. Ранее сохранённые A4/A5 и resource-schema commits не смешаны с этим патчем.

Точный PL патч: [native-public-package-types-pl-20261104.patch](native-public-package-types-pl-20261104.patch). Baseline и полный список файлов: [native-public-package-types-baseline-20261104.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/native-public-package-types-baseline-20261104.json), каталог `/tmp/native-public-package-types-baseline-20261104`. Патч получен из actual Git diff только этого согласованного списка, включая удаление устаревшего файла; его применение здесь не запускалось.

## Производитель и обязательные потребители

1. Реальный Core producer `body-export/assignment.ts` формирует `BodyPackage`, использует существующую schema boundary и выдаёт `PublicBodyPackage`. `runtime/assignment-delivery.ts` включает его в существующий `NativeAssignmentDelivery`; `runtime/operation.ts` возвращает его из `assignmentDelivery`. Новая схема, parser, DTO-копия или runtime import Core не добавлены.
2. Существующий `application/native-entry.ts` уже получает настоящий `NativeOperation` из Core callback и передаёт его в `prepareNativeCourse`. Теперь `prepareNativeCourse`, `withNativeAssignments`, `withNativeProjectPlans` и adapter сохраняют этот тип. Request/options взяты из существующих `NativeAssignmentRequest`/`NativeAssignmentDeliveryOptions`, release request/proof — из настоящей сигнатуры `NativeOperation.validateRelease`.
3. `native-adapter.ts` вызывает типизированный `operation.assignmentDelivery`. Его return type выводится из результата настоящего метода; callback `deliveries` выводится через `ReturnType<typeof createNativeAdapter>`. Core delivery, package, conditions и реально декодированные private answer/settings сохраняют свои типы. Existing origin/selection/condition checks сохранены.
4. В selected-work ветке `native-course.ts` передаёт `deliveries[0].publicPackage` непосредственно в `exportPrairieLearn`. В composition ветке та же модель хранится в `Map<string, PublicBodyPackage> bodies`; отдельно полученные menu deliveries записываются в эту же карту. Проверка отсутствующего selected menu сохранена; lookup возвращает реально суженный body.
5. `export-course.ts` принимает `input.body: (work:string) => Promise<PublicBodyPackage>`, хранит `menuBodies` того же типа, рендерит introduction и передаёт пакеты writer. `resourceBindings` использует существующий inferred `BodyResource`; callbacks resource/filter/map/sort получают его без `any` и tuple casts.
6. `export.ts` принимает `PublicBodyPackage`; question/slot callbacks выводятся из него. Assignment lookup использует реальный slot assignment type и tuple return annotation, без cast. Более строгий PL guard обязательного непустого `question.title` сохранён; явный return из существующего `never` отказа даёт фактическое сужение optional Core title. Metadata принимает только реально используемые difficulty/time.

Это восстановление существующего Native producer contract, не аннотация над `operation:any`. Усиленный существующий `tests/native-course-export-types.ts` использует `Exact`, который отдельно отвергает `any`, для operation/request/options, настоящего Core delivery, adapter/callback package, `input.body`, writer package и resource element. Одной assignability проверки было бы недостаточно. Сам fixture пока не компилировался; он не утверждает типизацию AST.

## Изменения с runtime-смыслом

`withNativeProjectPlans` вызывает `operation.withProjectContracts(requests,useBatch)` напрямую, сохраняя receiver. Удалён feature probe/выбор `withProjectContexts` и отказ об отсутствии гарантированного метода. Actual Core `runtime/operation.ts:2929` предоставляет этот метод без optional; `NativeOperation` — `ReturnType<typeof publicOperation>`, реальный caller `native-entry.ts` получает именно его. Для valid current callers выбор метода и результат прежние. Старые частичные fake operations без метода больше не являются поддержанным контрактом. Внутренние `batch:any`/planner/metrics и checkpoint fallback этим шагом не объявлены полностью типизированными и не переписывались.

В `native-adapter.ts` две зеркальные Map заменены одной `captured: Map<source,{business,input}>`. Статически проверены все ссылки: они локальны одному файлу, существовал единственный совместный set, все clear выполнялись совместно и между парными действиями не было await. Теперь authenticated producer сохраняет обе части одной записью; coverage, seed/method checks, cache hit, failure/finally и detached clone getters сохраняют прежний valid lifecycle. Цикл grading validation читает `{business,input}` из одной записи и по-прежнему обращается к `input.assessment`, без optional пропуска grading validation и без нового missing-row синхронизационного guard. `business()` и `validatedInput()` сохраняют существующий отказ до полной validation.

Workspace origin comparisons получают optional lookup только там, где отсутствие binding уже отклоняется проверками business/declaration и origin. Project check parity явно отклоняет отсутствующий declaration при существующем `fact.check`; существующая ошибка `PL.CHECKS_CHANGED` сохраняет отказ. Work/question validation ветки используют явный return из того же `never` отказа для TypeScript narrowing; новые guard frameworks не вводились.

## Существующие callers и CI

Четыре изменённых production файла: `application/native-course.ts`, `native-adapter.ts`, `export-course.ts`, `export.ts`.

Остальные изменённые файлы PL:

- `tests/native-course-export-types.ts` — перечисленные compiler assertions существующей публичной сигнатуры.
- `tests/export.test.ts`, `export-course.test.ts`, `whole-bank.test.ts`, `workspace-transport.test.ts`, `manual-mixed.test.ts`, `git-transport.test.ts`, `native-answer.test.ts`, `native-publication.test.ts`, `native-seed-packages.ts` — положительные body factories согласованы с actual `PublicBodyPackage`/existing answer type и буквальными вариантами work/slot. Наблюдаемые grading/publication/resource/UUID/privacy assertions не ослаблены. Negative dynamic mutators остаются существующими mutators и не выдаются за typed external decode. Удалены только прежние лишние `resource.sha256` из actual resource literals; отдельные Java input/trusted-test SHA и их import сохранены.
- `tests/native-reuse-refusal.test.ts` удалён целиком: три разрешённых устаревших inert fallback/metrics случая с `{}`/частичными operations, несовместимые с настоящим NativeOperation. Его одно имя удалено из `.github/workflows/ci.yml`.
- `tests/native-contract-batch.test.ts`: удалены только два согласованных нижних feature/fallback случая. Два верхних positive случая сохраняют grouping, receiver, student/full, inside/final metrics assertions; оба используют гарантированный contract method. Наличие дополнительного context method больше не меняет expected contract call. Новый набор tests не создан.

`tests/delivery4-workspace.test.ts` согласован через импорт теперь типизированного body из `workspace-transport.test.ts`; собственных изменений в нём нет. `tests/check.ts` автоматически собирает все `.test.ts`, поэтому удалённый obsolete файл больше не попадает в канонический запуск. CI explicit list сохранён за исключением этого одного имени. `tests/native-core-typecheck.ts` уже читает production/type fixture; его код не менялся. `native-batch-checkpoint.test.ts` использует прежний dynamic import/частичный `operation:any`; он не является доказательством строгой NativeOperation типизации и оставлен без изменений, поскольку checkpoint framework вне этой задачи.

## Один следующий compiler graph

Точные 13 настоящих roots находятся в [native-public-package-types-compiler-roots-20261104.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/native-public-package-types-compiler-roots-20261104.json), относительно `/home/tolya/JavaCodex/java-release-20261110/prairielearn`:

```text
_extensions/course-prairielearn/application/native-entry.ts
tests/native-course-export-types.ts
tests/export.test.ts
tests/export-course.test.ts
tests/whole-bank.test.ts
tests/workspace-transport.test.ts
tests/manual-mixed.test.ts
tests/git-transport.test.ts
tests/native-answer.test.ts
tests/native-publication.test.ts
tests/delivery4-workspace.test.ts
tests/native-contract-batch.test.ts
tests/native-seed-packages.ts
```

Первый root достигает всех четырёх production файлов, оба writer пути и Core public types; остальные включают реальные затронутые обязательные callers, а не один facade fixture. Для import map существует `tools/native-core-import-map.ts`; использовать фактический companion `core-native-bridge`. Это один предложенный graph, не исполненная команда и не новая матрица. Лимит и исполнителя после review назначает координатор. YAML CI и `tests/check.ts` — просмотренные readers запуска, не дополнительные affected TypeScript roots. Существующий полезный public fixture `tests/body-projection.test.ts` проверяет реальный public resource с query/fragment; если понадобится, координатор может выбрать его отдельно, но новых runtime fixtures здесь нет и необходимость повторения старых успешных проверок не утверждается.

## Явный остаток

`condition`, `publicAnswer`, `introduction` остаются Core `any[]`, Pandoc `Node.c` — `any`. Посторонние `Core:any`, descriptor/witness/planner/batch/policy/content DTO, части private answer aggregation, legacy JSON `checkedJSON` и ручные `fields`/`record` helpers не переписывались. Planner `any` остаётся на границе подготовки projectPlans, поэтому полной типизации batch/authority этим патчем не заявляется. Наличие typed body не делает старые ручные checks полноценным decoder произвольного внешнего JSON. В выбранном production пути пакет приходит из настоящего Core метода; дополнительный parse/validator/unknown stub/cast не добавлялся. Никакой совместимости со старыми частичными operations, API/feature или изменения учебного материала не вводится.

Предыдущие input/artifact hashes и platform gaps остаются в их прежних отчётах. Этот шаг не удаляет новую область SHA и не возвращает child-output/resource content proofs.

## Допуск и стоимость

Собственный исходный агент `01a1a6e8-231c-7f43-8144-eda91288c9fa`, путь `/root/a5_native_final_outputs`, исходный ход `01a1a6e8-2351-7610-b283-2f32382668a9` не заменён новой baseline. [Первичные метаданные и весь интервал](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/native-public-package-types-model-admission-20261104.json): пять доступных turn_context от исходной роли, верхняя и вложенная пара `gpt-6.1-sol/high`, PASS. Это platform metadata, не заявление о скрытой серверной маршрутизации.

Этот follow-up израсходовал 0 compiler/runtime/Native секунд, проверки не запускались. Прежний Native budget не сбрасывается: cumulative `12.355128194s / 40s`, remaining `27.644871806s`. Основание: [native-titles-public-acceptance-20261104.json](native-titles-public-acceptance-20261104.json) и [native-final-outputs-public-acceptance-20261104.json](native-final-outputs-public-acceptance-20261104.json). Новый cap после Source review отдельно объявляет координатор. Source freeze не означает PASS компиляции, runtime, CI или готовность выпуска.
