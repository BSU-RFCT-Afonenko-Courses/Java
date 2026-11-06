# Тип BodyResource, 4 ноября

**SOURCE FROZEN, ожидает независимого review.** У `BodyPackage.resources` убран `z.array(z.any())`. Схема реального ресурса — часть существующего package boundary; PL renderer и его resource maps используют выведенный тип. Compiler/runtime/tests ещё не запускались, положительная приёмка не заявляется.

База после mechanical сохранения A4/A5: Core `d805d2d1e2b62c5d24ef185613a5cc731ad280f2`, PL `e0a98909bc17fb1e92ebc701bfb396528c977a2e`. До подтверждения завершения mechanical writer выполнялось только чтение. Новая source baseline `/tmp/body-resource-types-baseline-20261104` отделяет этот follow-up от уже сохранённых commits; исходная модельная роль и прежние бюджеты не заменялись.

Закрытие producer/readers выполнено по текущим Source, включая `_extensions` Core/PL/Download/QRC/Site. Единственный production producer поля `BodyPackage.resources` в Core — `body-export/assignment.ts`: пакет создаётся как `pkg: BodyPackage`, затем ресурс добавляется с `owner`, `source`, `effectiveBase`, `target`, `data`, `visibility: public`. Остальные native resource observations, condition.resources и project inventories — другие поля/договоры, не варианты BodyResource. Актуальных product producers BodyResource с отсутствующими `source/effectiveBase/data`, иной visibility либо дополнительным SHA полем не найдено. Прежний `opaque` ресурс существовал в положительной schema fixture и не является production producer.

В `body-export/package-contracts.ts` добавлен `BodyResourceSchema`: шесть обязательных фактических полей, `visibility` — literal public, `data` — обычная строка. `BodyResource` выводится через `z.output<typeof BodyResourceSchema>`, `BodyPackage.resources` использует `z.array(BodyResourceSchema)`. В `model.ts` добавлен только type reexport. Уже типизированный assignment producer получил реальную проверку каждого `.resources.push(...)` без новой DTO-модели или дополнительного вызова parse. Существующий `assertBodyPackage` исполняет прежний package schema boundary.

PL `renderPublicBody` принимает `Pick<PublicBodyPackage, "owner" | "apiVersion" | "resources">`; owner обязателен, как у настоящих production callers. Две resource map имеют `Map<string, BodyResource>`. Импорт типов — только type-only из того же Core model, runtime Core import/dynamic capability/нового публичного API нет. Existing manual decode checks, owner/path/visibility checks и обычное base64-декодирование в PL сохранены. Ни проверки корректности base64 в новой схеме, ни SHA/currentness proof, ни повторного внутреннего parse не добавлено.

**Явный остаток:** `exportPrairieLearn(p: any)`, `export-course.ts` и его `input.body`/resourceBindings остаются прежними legacy dynamic границами. Их ручные внешние checks не объявляются полным typed decode BodyResource; присваивание `any` на вызове typed renderer не удостоверяет весь upstream. Этот bounded шаг даёт действующую schema/type проверку Core producer и типизированное использование внутри renderer, но не устраняет все exporter `any` или весь внешний decode. AST `Node.c`, native body arrays и обходы Pandoc AST с `any` сохранены отдельно, как и generic negative-fixture mutators. Более широкая типизация требует своей конкретной задачи, а не casts/unknown заглушек в этой дельте.

Изменённые production файлы:

- `core-native-bridge/_extensions/course-core/body-export/package-contracts.ts`;
- `core-native-bridge/_extensions/course-core/body-export/model.ts`;
- `prairielearn/_extensions/course-prairielearn/application/export.ts`.

Узко согласованы существующие fixtures, без изменения их assertions и без новых tests:

- Core `tests/body-schema.ts`: прежний положительный opaque resource заменён фактическими шестью полями;
- PL `tests/body-projection.test.ts`: контекст ресурса выведен из `Parameters<typeof renderPublicBody>[0]`, убран старый resource SHA и его неиспользуемый import;
- PL `tests/native-a1-builder-integration.ts`: ресурс из настоящей ordinary inventory типизирован тем же renderer contract, удалено обращение к уже удалённому `file.sha256`;
- PL `tests/native-origin-scope.test.ts`, `tests/native-auxiliary.test.ts`, `tests/performance-native-auxiliary.ts`: неполным прежним renderer contexts добавлен фактический owner;
- PL `tests/performance-permissions.ts`, `tests/performance-context.ts`: то же поле добавлено двум embedded drivers.

Принятые A4 title строки, A5 resource ownership/path code, учебные данные, UUID и public delivery behavior этим шагом не менялись. No compatibility layer, самостоятельной копии DTO/schema, casts, `ts-ignore` или новых `any`/unknown заглушек не добавлено. Core body-export/assignment.ts не менялся: проверку producer обеспечивает уже стоящая там аннотация BodyPackage, теперь связанная с реальной resource schema.

Exact patches: `body-resource-types-core-20261104.patch` и `body-resource-types-pl-20261104.patch`; source manifest — [body-resource-types-baseline-20261104.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/body-resource-types-baseline-20261104.json). Собственный model admission продолжен от первоначального origin `01a1a6e8-2351-7610-b283-2f32382668a9`, агент `01a1a6e8-231c-7f43-8144-eda91288c9fa` `/root/a5_native_final_outputs`; все три первичных turn_context согласованы с `gpt-6.1-sol/high`, PASS. Источник и полный интервал сохранены в [body-resource-types-model-admission-20261104.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/body-resource-types-model-admission-20261104.json).

До нового review и решения координатора **не выполнялись** compiler/tests/runtime/render/hash/build/network/commit/stage. Читались Source и git diff. Проверочный бюджет follow-up ещё не назначен/не расходован этим исполнителем; прежние затраты не сброшены. После уже выполненного executor публичного CLI 6.477224487s PASS накоплено **12.355128194s из исходных 40s**, осталось **27.644871806s**. Evidence: [native-titles-public-acceptance-20261104.json](native-titles-public-acceptance-20261104.json), [native-final-outputs-public-acceptance-20261104.json](native-final-outputs-public-acceptance-20261104.json). Этот follow-up Native бюджет не расходовал и успешный запуск не повторял.

Один затронутый compiler graph: Core `public.ts` (достигает producer/schema), PL `application/export.ts` и затронутые реальные fixture roots. Точные пути от release root:

1. `core-native-bridge/_extensions/course-core/public.ts`;
2. `prairielearn/_extensions/course-prairielearn/application/export.ts`;
3. `core-native-bridge/tests/assignment-body.ts` — настоящий producer, без изменения Source;
4. `core-native-bridge/tests/body-schema.ts`;
5. `prairielearn/tests/body-projection.test.ts`;
6. `prairielearn/tests/native-a1-builder-integration.ts`;
7. `prairielearn/tests/native-origin-scope.test.ts`;
8. `prairielearn/tests/native-auxiliary.test.ts`;
9. `prairielearn/tests/performance-native-auxiliary.ts`;
10. `prairielearn/tests/performance-permissions.ts`;
11. `prairielearn/tests/performance-context.ts`.

Embedded driver строки в двух последних файлах должны быть сохранены с новым договором; один compiler graph сам по себе не доказывает исполнение этих строк. Полные Native/performance integration suites не предлагаются к запуску ради статуса.

Существующий полезный public fixture для выбранного поведения — `prairielearn/tests/body-projection.test.ts`: реальная выдача одного ресурса и сохранение query/fragment при переписывании URL; он использует существующий Pandoc consumer. Pure producer fixture — уже существующий `core-native-bridge/tests/assignment-body.ts`. Если нужны исполняемые проверки, выбор roots/fixture и общий предел задаёт координатор после review; новым пакетом тестов реализация не зеркалируется.
