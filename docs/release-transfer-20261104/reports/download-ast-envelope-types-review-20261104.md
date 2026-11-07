# Download AST envelope typing — независимая рецензия

Вердикт: **APPROVE scoped**. Блокирующих Source findings в представленной дельте нет. Это принятие небольшого narrowing envelope/Attr и исправления устаревшего pure fixture, не полная типизация AST и не подтверждение actual Native integration.

## Проверенный состав

Source HEAD: `944ca76dbdef5bb4842116950621f82449b631b3`; поверх него ровно три изменённых файла, +26/-19:

- `_extensions/project-download/native-ts/early-filter.ts` — полностью прочитан;
- `_extensions/project-download/native-ts/late-filter.ts` — полностью прочитан;
- `tests/literal-request.ts` — полностью прочитан.

Связанные границы сверены по `spec/index.md`, `spec/contract.md`, `docs/release-3.4.0.md`, реальному `domain/request.ts`, обоим Lua producer/consumer carriers (`native-ts/early-filter.lua`, `filter.lua`), Core `document/assignment-producer.ts` и адресным existing literal/registered qualifier fixtures. Owner report и сохранённые три logs/checks JSON прочитаны. Рецензент Source не менял и не запускал compiler, tests, Native, inspect, hash или network.

## Source выводы

| Граница | Наблюдение | Вывод |
| --- | --- | --- |
| Attr, early-filter.ts:21–50 | Вход теперь `unknown`; прежние проверки tuple длины, пустого id, единственного класса, пар строк, повторных/разрешённых ключей не изменены. `readonly unknown[]` возвращает элементы существующим проверкам, не заменяет их assertion. | Честное локальное усиление типа без изменения принятых literal Attr. Новых casts/suppressions нет. |
| Early stdio, early-filter.ts:164–183 | JSON получает тип `unknown`. `record` проверяет только словарь envelope/meta/MetaString transport. `ast` и `meta` ссылаются на исходные объекты; удаляется тот же собственный source key; остальная структура проходит прежний lowering. | Нет закрытой AST schema, strip, clone/projection или потери неизвестных Native extension fields. `pandoc-api-version` не объявлен проверенным `number[]`. |
| Late stdio, late-filter.ts:6–31 | Carrier производит `pandoc.Pandoc({}, {...=pandoc.MetaString(...)})` в filter.lua:45. Batch JSON остаётся `unknown` и один раз передаётся existing `resolveDownloadRequests`, чьи syntax/config/resource/default rules не менялись. Ответ содержит прежние result metadata/пустые blocks и прежнее API-version значение. | Нового decoder, IO, child или policy нет. Late carrier и раньше не был passthrough полного late AST: extended AST остаётся в Lua. |
| Malformed envelopes | `record` исключает null, массивы и примитивы на root/meta/transport. Для JSON-форм, которые прежде могли дать случайный TypeError, теперь возможен существующий controlled missing-source/batch diagnostic. | Это узкое изменение malformed diagnostic, честно названное writer. Валидные Native carriers остаются принятыми; новый глобальный AST validator не введён. |
| Pure qualifier, literal-request.ts:18–40 | Удалён obsolete четырёхаргументный вызов с profile/sourceProbe; actual Core принимает `(ast, context, evidence)` с format/engine/filters/shortcodes. Новый synthetic observation имеет эти поля, не объявляется capability/authority. Без зарегистрированного handler исходный Download Span по-прежнему должен отказать; после lowering принимается, чужой shortcode остаётся Core-owned и отказывает. | Fixture проверяет реальные guards/lowering, не подделывает Native admission. Comments/output ограничивают claim чистым qualifier/parser. |

В новой дельте нет assertions/casts, новых imports, filesystem/runtime pipeline или sanitizing schema. Уже существующие `any` и `document as any` внутри recursive traversal/replacements остаются; они не замаскированы заявлением о полном coverage. Проверки source/ordinal/result binding в Lua не изменены.

## Сохранённое evidence и его пределы

Источник: `reports/download-ast-envelope-types-checks-20261104.json` и соответствующие `*-control-20261104.log`, `*-compiler-20261104.log`, `*-control-aligned-20261104.log`.

1. Первый existing pure control завершился exit 1 за 0.2603 s: устаревший profile был третьим аргументом actual Core qualifier и не имел required observation fields. Это отказ до изменённого Attr path; **не** meaningful RED новой продуктовой типизации.
2. Existing compiler с двумя production roots и прежним fixture: PASS, 0.7536 s. Лог содержит оба native-ts entrypoints и tests/literal-request.ts.
3. После отдельно разрешённого test-only API alignment тот же pure control: PASS, 15 controls, 0.3268 s. Продуктовый Source между compiler и этим контролем не менялся. Сумма сохранённых запусков 1.3411 s. Повтора рецензент не делал.

Compiler после финальной test-only правки не запускался: **final test file typecheck не заявляется**. Он динамически импортирует Core, поэтому этот fixture сам по себе также не доказывает статическую совместимость импортированного qualifier API; соответствие здесь проверено по actual Source и сохранённому runtime control. Новые stdio envelope branches существующий pure control не исполняет: для них evidence — product compilation плюс данный Source review. Actual Quarto/Pandoc round-trip, реальные ZIP/outputs/phase authority и полный AST coverage этой партией не квалифицированы. Эти пределы не дают конкретного блокирующего дефекта в представленном scoped diff.

## Допуск рецензента

Собственный primary интервал подтверждён session metadata: 27 `turn_context`, строки 8–3744, единственные model/effort `gpt-6.1-sol` / `high`; origin turn `01a1a25f-b0fb-7fd2-b244-83933af77834`, последняя запись `01a1a581-98b1-7c72-aed7-05b9a49e5f20`. Вложенные настройки последней записи совпадают. Полный env/секреты не читались и не печатались; hidden serving tier этой метадатой не устанавливается. Global settings, Source, state, installed копии и production не изменены.
