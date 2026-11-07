# Независимая рецензия типов Body resources, 4 ноября 2026

**APPROVE для Source; compiler/runtime PENDING.** Блокирующих дефектов в выбранной дельте не найдено. Это ограниченная типизация существующего resource boundary, а не подтверждение полного typed decode внешнего PL входа или устранения всех `any`.

Основа: Core `d805d2d1e2b62c5d24ef185613a5cc731ad280f2` в `core-native-bridge`, PL `e0a98909bc17fb1e92ebc701bfb396528c977a2e`. Прочитаны `body-resource-types-core-20261104.patch`, `body-resource-types-pl-20261104.patch`, отчёт исполнителя, действующие инструкции Core/PL, фактический producer, существующий package boundary, полный resource путь renderer и его production callers. Уже принятые A4/A5 изменения не пересматривались и не запускались.

## Схема и producer

`BodyResourceSchema` — один `z.strictObject` с шестью обязательными полями фактической поставки: `owner`, `source`, `effectiveBase`, `target`, `data`, `visibility`. `visibility` ограничена `public`, `owner` использует существующий договор owner, строки provenance/target непустые, `data` остаётся обычной строкой. `BodyResource` выводится из этой схемы; `model.ts` делает только type reexport. Копии DTO, новых casts, `any`, optional fields или отдельного runtime parser в дельте нет.

Реальный producer `body-export/assignment.ts` уже создаёт `pkg: BodyPackage` и добавляет ровно эти шесть полей в `pkg.resources.push(...)`. Поэтому замена `z.array(z.any())` на `z.array(BodyResourceSchema)` замыкает проверку producer настоящим выведенным типом. После подготовки пакета по-прежнему вызывается единственный существующий `assertBodyPackage()` с `BodyPackageSchema.safeParse()`. Дополнительного parse/validator или вызова схемы в renderer не добавлено. Ресурсов с прежним SHA-полем или иной производственной формой в рассмотренных актуальных producer/readers не найдено.

Единственное изменение runtime-валидации — ожидаемое сужение прежнего package schema boundary: он теперь отказывает отсутствующим/чужим resource полям и непубличной visibility. Новая схема не заявляет проверки base64, owner equality с пакетом, canonical path или существования файла; прежние проверки соответствующих consumer границ сохранены. Новых hash, lifecycle, функций поставки или зависимостей нет.

## Renderer и обязательные consumers

`renderPublicBody()` принимает `Pick<PublicBodyPackage, "owner" | "apiVersion" | "resources">`. Все три поля обязательны; `resources` остаётся массивом реального `BodyResource`. Две maps — `Map<string, BodyResource>`. Импорт из Core model — `import type`, поэтому новый runtime Core import не возникает. Существующая prefix import map `course-core/` охватывает этот subpath; проверка разрешения компилятором в данной рецензии не выполнялась.

Прослежены обе production ветки вызова renderer из `export.ts` и введения/меню из `export-course.ts`: реальный package owner присутствует и проверяется существующими внешними условиями. Поиск потребителей в актуальных `_extensions` Core/PL/Download/QRC/Site не выявил дополнительного producer иной формы BodyResource. Native resource inventories, condition resource paths и project inventories остаются отдельными договорами.

Resource ambiguity check, rewrite query/fragment, `safe(target)`, owner equality, public visibility, проверка строки `data` и base64 decode в PL сохранены без изменения реализации. Неполные legacy внешние checks не превратились в полный decode от одной аннотации параметра: `exportPrairieLearn(p: any)`, `input.body` и `resourceBindings: Map<string, any>` в `export-course.ts` остаются динамическими участками. Их присваивание на typed вызове само по себе не доказывает upstream форму. Отчёт исполнителя честно сохраняет это ограничение. AST `nodes`, Pandoc payload и их `any` также находятся вне этой дельты.

## Fixtures и доказуемые границы

Положительный opaque resource в Core schema fixture заменён фактическим payload. PL resource fixtures используют `Parameters<typeof renderPublicBody>[0]` и его `resources`; устранены устаревшие resource SHA значения и обращение к уже отсутствующему `file.sha256`. Контекстная аннотация задаёт реальную форму без cast и без отдельного тестового DTO. Остальным неполным renderer fixtures и двум embedded drivers добавлен требуемый owner. Assertions в точных patches не изменены; прежний resource suffix/file-result сценарий сохранён.

Нового набора тестов, копирующего устройство схемы, не добавлено. Изученные fixtures пригодны для последующей адресной проверки, но их PASS не заявляется. Type-check embedded driver строк сам по себе не доказывает их исполнение. A4/A5 проверки, Native, performance suites и публичный CLI не повторялись.

## Собственный допуск

Reviewer `/root/a5_final_outputs_review`, собственный chat `01a1a6f7-51cf-72c3-88c9-81143cd73e84`; неизменный original origin turn `01a1a6f7-5200-7220-95e1-ffcf377d1217`. Перед итоговым verdict прочитаны первичные `session_meta` и полный исходный интервал `turn_context`: строки 8, 155, 175 и 225, везде верхние и вложенные `gpt-6.1-sol/high`, PASS. Свидетельство — [body-resource-types-review-model-admission-20261104.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/body-resource-types-review-model-admission-20261104.json). Родительский ID не использован как собственный; глобальные настройки не менялись, скрытая серверная маршрутизация не удостоверяется.

Компилятор, tests, runtime/Native, render, inspect, hash, build и сеть не запускались; Source и Git не изменялись. Итог **Source APPROVE / compiler/runtime PENDING** относится только к указанным двум patches и их фактическим обязательным readers.
