# Download preflight typing — независимая рецензия

**APPROVE scoped.** Блокирующих findings нет. Проверена ровно однопроизводственная дельта `_extensions/project-download/native-ts/early-filter.ts` поверх `29e8bec23b85ec0f0dd89883c4df1498111fccc9`; полный файл, diff и сохранённые report/patch/checks/control/final-compiler logs прочитаны. Рецензент Source не писал, compiler/tests/Native/network не запускал.

`hasBlocks` объявляет только реально проверенное свойство `blocks: unknown[]` после прежнего null/object boundary. Он не приписывает meta/API-version/NativeNode schema. `"blocks" in value` делает имеющийся property guard доступным TypeScript; для JSON envelope отсутствующее свойство по-прежнему отвергается, существующее array property принимается. Identity и дополнительные поля исходного документа сохраняются: `doc = document`, без clone/strip/cast. Произвольные Proxy/getter side effects вне JSON transport этим review не квалифицированы.

В preflight обе прежние ветви array/object, порядок рекурсии и reserved checks сохранены. `readonly unknown[]` и `[string, unknown][]` относятся к реально потребляемым элементам и entries. `ReadonlySet<unknown>` описывает возможность задавать membership-вопрос любым значениям; литералы Set и runtime `.has` не меняются. Новых typeof правил поверх membership нет. Сравнение pair[0], class names и reserved field keys осталось прежним. Новых casts/suppressions/schema/IO/дополнительных обходов нет.

`downloadName`, `request`, `scan` и replacements не менялись и остаются отдельным долгом; этот verdict не заявляет полный AST coverage. Внешние guards/late carrier/actual phase authority не расширены.

Evidence принято по [download-preflight-types-checks-20261104.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/download-preflight-types-checks-20261104.json): existing control PASS0.3238s с20 cases, включая все прежние15 и временные5. Временные5 затем удалены по root, текущий tests/literal-request.ts равен baseline; повторный control не делался. Первый compiler TS2322 сохранён; после changed-Source correction genuine hasBlocks final affected compiler PASS0.7672s (early-filter.ts + existing fixture). Сумма JSON1.8707s. Runtime control предшествует последней predicate extraction: отдельный runtime PASS final extraction не заявляется; Source совпадение прежней JSON проверки и final compilation дают scoped основание принять дельту без повторной матрицы.

Source frozen для отдельного root commit. Следующий recognizer/location пакет здесь не написан и не включён. Никаких commit/push/state/installed/production изменений.

Own full-primary admission: 30 turn_context, строки 8–3977, top/nested gpt-6.1-sol/high; неизменённый origin 01a1a25f-b0fb-7fd2-b244-83933af77834, latest 01a1a594-c31f-7f42-b0e6-1c151b68f75e. Globals не менялись, hidden serving tier не утверждается.
