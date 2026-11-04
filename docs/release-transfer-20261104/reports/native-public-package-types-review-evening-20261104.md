# Независимая рецензия Native public-package types, 4 ноября 2026

**APPROVE для Source; compiler/runtime/CI PENDING.** Блокирующих дефектов в выбранной дельте не найдено. Проверена настоящая цепочка публичного пакета от Core operation до обоих PL writer путей; полный typed AST, planner и decoder внешнего JSON этим решением не заявляются.

Основа: PL `15bc337a37a26898b65dd566045a9d6113b6694e`, фактический companion Core `010ba917909fa7d44179d4a1a05291f88f061775` в `core-native-bridge`. Прочитаны `native-public-package-types-pl-20261104.patch`, отчёт исполнителя, исходное read-only замыкание `pl-public-package-types-next-step-20261104.md`, четыре production файла и соответствующие producer/caller/CI границы. Список фактической PL дельты содержит согласованные 17 файлов. Другой checkout `/core/` не использован; Core этой дельтой не изменён.

## Типовая цепочка

Существующий callback в `native-entry.ts` получает `NativeOperation` от настоящего `Core.withCurrentNativeOperation`. Этот тип теперь сохраняется в `prepareNativeCourse`, `withNativeAssignments`, `withNativeProjectPlans` и adapter. `assignmentDelivery()` вызывается у него с настоящими `NativeAssignmentRequest` и `NativeAssignmentDeliveryOptions`. Его результат выводится от Core метода, а не утверждается cast или вручную переписанным DTO.

Core `body-export/assignment.ts` уже создаёт `pkg: BodyPackage`, исполняет существующий package boundary, удаляет приватные поля вопросов и возвращает `publicPackage: PublicBodyPackage`. `NativeAssignmentDelivery` действительно содержит этот тип. Adapter возвращает непосредственный `delivery.publicPackage`; callback composition выводится через `ReturnType<typeof createNativeAdapter>` и сохраняет тот же тип. В selected-work ветке пакет передаётся непосредственно в `exportPrairieLearn`; selected и отдельно полученные menu packages также проходят через `Map<string, PublicBodyPackage>`.

В `export-course.ts` реальные `input.body`, `menuBodies` и introduction context используют этот же пакет. Lookup menu body и lookup selected body сужаются существующим отказом при отсутствии, без `!`/cast или optional поля ради caller. `resourceBindings` — `Map<string, BodyResource>` с выводимыми filter/map/sort callbacks. В `export.ts` writer принимает `PublicBodyPackage`; question/slot callbacks выводятся из него. Assignment lookup использует тип существующего slot assignment и tuple return annotation, а не tuple cast.

Более строгий PL договор непустого `question.title` сохранён: optional title Core сужается прежней проверкой `typeof`/`trim`, теперь с явным `return` из того же `never` отказа. Добавленный `!work` сохраняет отказ неполному входу до обращения к работе. Metadata получает только реально читаемые difficulty/time. Нового runtime Core import, parser, guard framework, публичного API или независимой копии модели не добавлено; все новые Core imports — type-only.

`tests/native-course-export-types.ts` проверяет не только assignability: существующий `Exact` отвергает `any` и применяется к operation/request/options, Core delivery, adapter/callback package, input.body, writer package и resource element. Это полезная проверка именно заявленной оболочки пакета. Фактическая компиляция fixture данной рецензией не выполнялась; AST arrays внутри известного пакета остаются прежними `any[]`.

## Runtime-значимые участки

Две прежние maps adapter имели один совместный producer set и совместные clear; между парными действиями не было `await`. Одна `captured: Map<source,{business,input}>` сохраняет это соответствие конструктивно. Проверены начало полной validation, отказ повторной проверки cached proof, ошибка полной validation и `finally`, а также getters и assignment/workspace readers. Coverage требует все expected sources, seed targets и повторные declarations проверяются прежними условиями. Getters до полной validation отказывают, после неё возвращают detached `structuredClone`.

Grading validation читает `{business,input}` из одной записи и обращается к `input.assessment` напрямую. Прежний совместный условный выбор assessment/business и вызов `validateGradingComponents()` сохранены; optional-пропуска при отсутствующем input нет. Proof сохраняется только после validation и завершающих currentness/correlation проверок. Private answers/settings выводятся из фактически декодированных типов; origin, выбранные conditions, matching answer type, extra conditions и завершающий checkpoint остаются проверенными прежними условиями.

Workspace optional lookup не разрешает отсутствующий binding: declaration и business должны существовать, origin operation и inventory identity по-прежнему сравниваются. Отсутствующий declaration при существующем `fact.check` теперь явно приводит к прежнему `PL.CHECKS_CHANGED`; проверки runtime profile и дальнейшая материализация не обходятся.

`withNativeProjectPlans()` вызывает `operation.withProjectContracts(requests,useBatch)` с сохранённым receiver. Этот метод обязателен в настоящем Core `NativeOperation`; production caller передаёт именно такой operation. Удалённая feature/fallback ветка обслуживала частичные fake/старые operations, а не альтернативную ветку текущего производителя. Planner/batch/metrics `any` и checkpoint fallback не переписаны и не объявлены строго типизированными этим APPROVE. Ни новый framework lifecycle, ни новая authority не введены.

## Обязательные callers и CI

Существующие положительные Body factories аннотированы фактическим `PublicBodyPackage`; литеральные work/selection/assignment/answer значения получают существующие типы. Assertions grading, resource delivery, UUID, privacy, publication и lifetime в точной дельте не ослаблены. Удалены устаревшие resource SHA literals; Java/compiler/trusted-test hashes остаются в своей прежней области. Negative dynamic mutators остаются отдельными намеренно некорректными входами, без выдачи их за полный typed decode.

Разрешённые удаления ограничены тремя obsolete inert случаями в `native-reuse-refusal.test.ts` и двумя нижними feature/fallback случаями `native-contract-batch.test.ts`. Два положительных batch случая сохраняют grouping, receiver, student/full планы и inside/final metrics. Теперь оба используют гарантированный contract method, а дополнительный context method не вызывается. Это согласовано с production изменением, а не замена положительной проверки пустым assertion.

В `.github/workflows/ci.yml` удалено только имя удалённого obsolete файла. Остальные explicit CI entries сохранены; `tests/check.ts` собирает существующие `.test.ts`, устаревших ссылок на удалённый тест в просмотренных исполняемых readers нет. Неизменённый `native-batch-checkpoint.test.ts` по-прежнему проверяет capability lifetime/expired token и closing rejection; его dynamic import/partial operation не является доказательством строгой NativeOperation типизации. `tests/native-core-typecheck.ts` продолжает читать production graph и усиленный fixture; его negative checks не ослаблены.

Предложенные 13 actual roots достигают четырёх production файлов и реальных изменённых callers, включая общий workspace body через `delivery4-workspace.test.ts`. Их выполнение, полный canonical CI, совместимость всех installed pins и реальный export этой статической рецензией не доказаны. Повторение прежнего успешного renderer/Native результата для Source verdict не требуется.

## Границы и собственный допуск

Сохраняются прежние AST `any[]`/`Node.c`, planner/batch/policy/content dynamic участки, часть private answer aggregation и legacy JSON helpers. Public package в рассмотренном production trace приходит из Core delivery, а не из JSON. Типизация параметра writer не делает прежние ручные checks полным decoder произвольного внешнего JSON. В отчёте исполнителя эти ограничения названы явно; claims всего строго типизированного стека не принимаются и не нужны для данного результата.

Reviewer `/root/a5_final_outputs_review`, собственный chat `01a1a6f7-51cf-72c3-88c9-81143cd73e84`, неизменный original origin turn `01a1a6f7-5200-7220-95e1-ffcf377d1217`. До итогового verdict перечитан весь первичный origin interval: `session_meta` подтверждает собственный chat/path; все пять `turn_context` на строках 8, 155, 175, 225, 284 содержат согласованные верхние и вложенные `gpt-6.1-sol/high`, PASS. Evidence — `native-public-package-types-review-model-admission-20261104.json`. Родительский ID не использован как собственный, глобальные настройки не менялись; скрытая серверная маршрутизация не удостоверяется.

Компилятор, tests, Native/render, inspect, hash/build и сеть не запускались; Source и Git не изменялись. Итог: **Source APPROVE / compiler/runtime/CI PENDING** для точной выбранной PL дельты. Последующий единый ограниченный graph по 13 roots назначает координатор отдельно.
