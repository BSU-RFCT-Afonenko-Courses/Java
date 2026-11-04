# QRC: типизация границы поискового JSON

Результат: ограниченная source-дельта готова к независимому root review.
Финальный affected bundled compiler и изменённый existing behavioral control — PASS.
Финальное замечание root о явном `any` в test также устранено и проверено.
Приёмка реализации автором не заявляется. Замечание root review о повторном path
с разными mounts исправлено и проверено различающим control.

## Вход и границы

Исходный HEAD QRC: `8732dbbe0d8502f8b6a77049e27907ae8a66e46a`.
До edits `git status --short` был пуст. Работы выполнены по разрешению root в
`/home/tolya/JavaCodex/java-release-20261110/qrc`; соседний SP stage использует
immutable git archive исходного exact commit и не читает dirty checkout.

Прочитаны QRC `AGENTS.md`, `spec/index.md`, `docs/contract.md`,
`docs/architecture.md`, существующие validators и consumers. Языковая политика
прочитана ранее из Core source-копии в этом же исходном интервале роли.

Изменены только два product-файла:
`_extensions/reference-catalog/infrastructure/search.ts` и
`_extensions/reference-catalog/infrastructure/publish.ts`.
В существующий `tests/search-publication.ts` добавлены metadata assertion с дополнительными полями fixture и различающий
случай одного index path в двух mounts с разным linked text.
Точный финальный diff: [qrc-search-types-20261104.patch](qrc-search-types-20261104.patch).

## Продуктовая дельта

- `SearchRow` требует только потребляемые `href: string` и `text: string`;
  остальные metadata остаются `Record<string, unknown>`, без closed schema.
- Результат `JSON.parse` имеет тип `unknown`. Существующая array-проверка
  остаётся; отдельная `readonly unknown[]` привязка не допускает распространения
  стандартного `Array.isArray` narrowing в `any[]` на элементы обработки.
- Существующая href/text row-проверка оформлена predicate `isSearchRow` с
  переиспользованием existing `isRecord`. По явному решению root разрешён один
  record/null guard в этой же boundary. Ошибка для malformed null теперь имеет
  существующий QRC текст неподдерживаемой записи вместо случайного TypeError.
  Дополнительных или повторных validators нет.
- `updateSearch` возвращает `UpdatedSearchIndex[]`: индекс и его строки **после**
  обновления текста из supplied final HTML. Исходные row-объекты сохраняются;
  неизвестные поля, порядок строк, порядок полей и JSON-формат записи сохраняются.
- Локальный `Map<string, SearchRow[]>` связывает повторные index paths с одним
  массивом последних записанных строк. При очередной обработке path этот массив
  заполняется заново; все его ordered DTO entries видят финальные строки, как
  прежний reread после всех updates. Это O(n) по входным индексам/строкам, без
  поиска прошлых entries, casts или новых validators/IO.
- `publish` использует эти проверенные строки и `Map<string, SearchRow>` для
  merge. Повторные `readTextFile`/`JSON.parse` каждого индекса удалены.
  Перезапись href, last-row-wins merge, mounted base, фильтрация текущих страниц
  и внешний URL отказ остаются в существующем алгоритме.
- `CatalogPublication.config` уже имеет `Record<string, unknown>`, а
  `catalogConfig` уже принимает `unknown` и использует существующие validators;
  эта конфигурационная граница не переписывалась.

В existing control оба результата JSON помечены `unknown`; массив проходит
assertion, затем `readonly unknown[]` сужается через existing `isRecord` и
`rows.every(isRecord)`. Все явные `any` callbacks этого test удалены; assertions
по outputs, metadata и duplicate-path semantics сохранены. Existing helper
`assert` уже имел сигнатуру `asserts value`, менять его не потребовалось.
Production Source после этой test-only правки не менялся.

Новых casts, suppressions, runtime IO, протоколов или dependencies нет.
Существующие `@ts-expect-error` у instrumentation function binding не менялись.
HTML FFI, imported-catalog validation и content normalization не менялись.

## Семантическая точность и открытый край

Merge теперь потребляет только что обработанный snapshot индекса. Он больше
не видит concurrent file changes между `updateSearch` и прежним отдельным
повторным чтением. Snapshot не объявляется immutable и не задаёт новой
currentness/cache policy; эта granularity явно разрешена root.

Root review выявил отличие промежуточного варианта для одного index path в разных
mounts: промежуточный DTO сохранял отдельный snapshot каждого entry, тогда как
legacy reread видел последнюю запись файла. Это отличие устранено общим массивом
последних строк по path. Новый existing control использует `book/search.json`
дважды с mounts `book` и `second`, HTML тексты `First mount text` и
`Last mount text`. Ожидание явно требует `Last mount text` для **обоих** итоговых
href, metadata и исходный порядок. Control прошёл. Пути текущих индексов в publish
по-прежнему разрешаются существующим `resolve(root, index.path)`; нового
realpath/alias resolver не вводится.

## Проверки и единственная исправленная ошибка

Фактический runtime: `/opt/quarto/bin/tools/x86_64/deno` (bundled 2.7).
Использован existing actual import map
`/opt/quarto/share/deno_std/run_import_map.json`, прочитанный до запусков.
`DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache`.
В обоих видах проверки: `--deny-import --no-config --no-lock`
и указанный `--import-map`. Compiler не использовал `--cached-only`.

| Запуск | Результат | Время |
| --- | --- | --- |
| `deno run --no-check --allow-read --allow-write --allow-env --deny-net … tests/search-publication.ts` | PASS, exit 0 | 0.2405 s |
| Первый `deno check … search.ts publish.ts tests/search-publication.ts` | FAIL, TS2483 | 0.9811 s |
| После конкретного type-only исправления, тот же affected compiler | PASS, exit 0 | 1.0437 s |
| Изменённый existing control после duplicate-path root review | PASS, exit 0 | 0.2413 s |
| Affected compiler после duplicate-path fix | PASS, exit 0 | 0.9581 s |
| Изменённый existing control после test unknown narrowing | PASS, exit 0 | 0.2435 s |
| Финальный compiler `tests/search-publication.ts` с импортируемым graph | PASS, exit 0 | 1.0060 s |

Первый compiler выявил мою ошибку: TypeScript не допускает аннотацию переменной
непосредственно в `for...of`. Исправление: после существующей array-проверки
`const sourceRows: readonly unknown[] = rows`, затем `for (const row of sourceRows)`.
Runtime checks и действие обхода не менялись. Root отдельно разрешил одну проверку
изменённого Source после конкретного отказа в остатке общего 30-секундного budget;
зелёный behavioral control без изменения Source не повторялся. Затем root review
потребовал сохранить duplicate-path semantics; после конкретной source-правки
и нового различающего случая отдельно разрешены один изменённый existing control
и один affected compiler в остатке того же общего budget. После последнего PASS
Source не менялся. По последнему root review разрешена финальная test-only
правка unknown narrowing и её проверка: changed control и compiler PASS;
после них исходники не менялись. Суммарно **4.7143 s**, unchanged retry 0.

Behavioral control проверяет linking/search parse count, final/native/custom
captions, mounted страницы и fragments, landing preservation и удаление stale
страницы. Добавленный assertion также проверяет сохранность title и вложенных
metadata при text update и full merge. Дополнительный duplicate-path случай
проверяет last-written rows для обоих mounts, metadata и порядок результатов.
`git diff --check` — PASS.

Точные команды/результаты/elapsed:
[qrc-search-types-checks-20261104.json](qrc-search-types-checks-20261104.json).
Логи:
[qrc-search-types-control-20261104.log](qrc-search-types-control-20261104.log),
[qrc-search-types-compiler-20261104.log](qrc-search-types-compiler-20261104.log),
[qrc-search-types-compiler-final-20261104.log](qrc-search-types-compiler-final-20261104.log),
[qrc-search-types-control_after_duplicate_path_fix-20261104.log](qrc-search-types-control_after_duplicate_path_fix-20261104.log),
[qrc-search-types-compiler_after_duplicate_path_fix-20261104.log](qrc-search-types-compiler_after_duplicate_path_fix-20261104.log),
[qrc-search-types-control_after_test_unknown_narrowing-20261104.log](qrc-search-types-control_after_test_unknown_narrowing-20261104.log),
[qrc-search-types-compiler_after_test_unknown_narrowing-20261104.log](qrc-search-types-compiler_after_test_unknown_narrowing-20261104.log).

Не запускались Native, Quarto render/inspect/build, full suite, hashes,
network/install или скачивание типов. No commit/push/state edits.

## Допуск собственного исходного интервала

Агент `/root/core_yaml_types`, собственный chat
`01a1a54c-ab39-7a83-808d-a190428ff628`, исходный turn
`01a1a54c-ab6a-7412-894c-270be9b9aac3` — не сдвигался при новом поручении QRC.
Назначение `gpt-6.1-sol` / `medium`, Standard назначен.
Первичный source:
`/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T09-43-47-01a1a54c-ab39-7a83-808d-a190428ff628.jsonl`.

Перед передачей проверены собственные session_meta id/agent_path и **все**
turn_context от исходного turn: 4 записи. Верхнеуровневые model/effort и вложенные
collaboration_mode model/reasoning_effort совпадают с назначением — PASS.
Свидетельство:
[qrc-search-types-admission-20261104.json](qrc-search-types-admission-20261104.json).
Глобальные настройки не менялись; скрытая серверная маршрутизация не утверждается.
