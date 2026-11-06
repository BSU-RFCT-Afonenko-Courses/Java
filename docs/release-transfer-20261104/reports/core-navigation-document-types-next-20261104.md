# Navigation document: одностраничный Source plan

Scope — только `_extensions/course-navigation/html/document.ts` в `core-native-bridge`; продукт не изменён, compiler/runtime/network не запускались. Отдельно обновлена только актуальная Site-строка type audit по прямому root handoff: userapproved alternate-inspect/configsplit removal, remote850 через SSH,12output/profile pairs zero configdiff, без installed/production/cache-isolation claims.

## Реальные declarations доступны

`vendor/parse5/package.json` указывает version7.3.0 и `types: dist/index.d.ts`. Оба `.js` и `.d.ts` лежат в том же vendored дереве. `dist/index.d.ts` экспортирует `DefaultTreeAdapterTypes` и реальную generic `parse<T extends TreeAdapterTypeMap = DefaultTreeAdapterMap>(html, options): T['document']`; `tree-adapters/default.d.ts` содержит Document/Node/Element/DocumentType/DocumentFragment/Template. Не нужны download/install или вымышленные ambient declarations.

Текущий `parseHtml` строка86 подавляет несовместимость `as unknown as Document`. Она реальная: local union не содержит DocumentType/DocumentFragment; parse5 ParentNode допускает fragment/null, sourceCodeLocation — null. Дополнительные поля mode/namespaceURI/attribute namespace допустимы структурно, но null/recursive union нельзя объявлять прежней моделью. Runtime `.js` не имеет TypeScript generic declaration в своём тексте, а импорт сейчас не содержит explicit declaration link; фактическое разрешение checker здесь **не проверялось**.

## Один минимальный file plan

1. Связать **существующий** runtime import parse5 `.js` с его же portable соседним `dist/index.d.ts` (при необходимости explicit `@deno-types` этого пути, не error suppression). Использовать настоящие `DefaultTreeAdapterTypes.Document/Node/Element` как local aliases; удалить самодельные Offset/Location/Text/Comment/Document/Element модели и doublecast. `parseHtml` возвращает реальный parse result, без указанного вручную generic fake adapter map.
2. Две `@ts-expect-error` в строках89/94 обслуживают только assignment `replace = ...` / `parseHtml = ...` к function declaration. `observability/bridge.ts` уже объявляет `wrapSync<T extends Fn>(spec, fn:T):T`; сигнатура не нуждается в cast/Parameters/ReturnType reconstruction. Определить экспортированные wrapped const прямо из прежних named functions: `export const replace = __metrics.wrapSync(spec, function replace(html:string, edits:Edit[]):string { прежнее тело });`, аналогично parseHtml. Убрать нижний rebind и обе suppressions, IDs/kind/metrics initialization сохранить. Новых checks/IO/обёрток сверх прежнего wrap нет.

Адресно прочитанный consumer `composition.ts` использует structural tagName/value/childNodes и проверяет parent tagName перед traversal; реальные aliases подходят этому алгоритму на Source уровне. `inner` уже проверяет nullable location, `outer` использует non-null assertion отдельно от этого файла; её здесь не снимать и не усиливать. Изменение exported function declaration→wrapped const убирает hoisting; в этом файле нет вызова этих bindings до определения. Полный cycle/consumer compiler proof не выполнен, поэтому **это план, не утверждение готовой типовой совместимости**.

Существующий meaningful контроль `tests/navigation-composition.ts` создаёт небольшой временный HTML output fixture и вызывает composeNavigation без Quarto render/Core; подходящий будущий affected compiler должен включать этот consumer. На данной Source-only стадии ничего не исполнялось. Если явная привязка vendored declarations обнаружит transitive declaration dependency gap — остановиться с точным diagnostic, не компенсировать local AST fiction/absolute imports/any casts.

Own роль/model/effort неизменны; [существующий origin admission](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-condition-walk-types-admission-20261104.json). Глобальные настройки, основной state, vendor и bridge не менялись.
