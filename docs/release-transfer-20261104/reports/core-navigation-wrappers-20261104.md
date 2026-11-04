# Navigation: wrapper-only candidate

База Core `5567809e07aa0ee28ee2a65d7487d4985e5764b5`, worktree `core-native-bridge`. Изменён ровно `_extensions/course-navigation/html/document.ts`; остальные consumer/vendor/late Lua не затронуты. Source заморожен после compiler PASS, root review PENDING; commit/push/mainstate не выполнялись.

`replace` и `parseHtml` экспортируются как wrapped const из прежних named functions. Existing `wrapSync<T extends Fn>(spec, fn:T):T` сохраняет сигнатуры; убраны только function-binding reassignments и две соответствующие `@ts-expect-error`. Metrics IDs/kind и тела функций прежние, новых guards/IO/обёрток не добавлено. Hoisting function exports заменён обычной const initialization; в данном модуле вызовов этих bindings до объявления нет, существующий public composition control проходит с теми же wrappers.

По прямому выбору root восстановлены исходные runtime parse5 import, local AST model и `as unknown as Document`. **FFI долг не закрыт**: декларации parse5 существуют, но их transitive graph в текущем checker не связан. Wrapper-only PASS не является доказательством полной Node/Document типизации. Полный неудачный candidate не удалён: [первый report](core-navigation-document-types-20261104.md), [patch](core-navigation-document-types-20261104.patch), [28 diagnostics log](core-navigation-document-types-compiler-20261104.log).

Проверки:

- Один новый affected bundled compiler для document.ts + navigation-composition.ts **PASS0.8632s**, cap10/retry0. Изменён declaration graph, это не unchanged retry. [Command/exit/elapsed](core-navigation-wrappers-check-20261104.json), [compiler log](core-navigation-wrappers-compiler-20261104.log).
- Existing pure navigation-composition **PASS0.2647s** на предыдущем candidate с теми же runtime wrapped functions. По указанию root не повторялся: FFI types/casts стираются, тела и bindings wrappers остались теми же. [Сохранённый control](core-navigation-document-types-control-20261104.log).
- `git diff --check` PASS перед compiler. Native/render/browser/network не запускались; tests/vendor/consumer/tsconfig/import map не менялись.

[Exact wrapper-only patch](core-navigation-wrappers-20261104.patch). Own роль/model/effort неизменны; [существующий origin admission](core-condition-walk-types-admission-20261104.json). Кандидат готов к независимому root review, без claims снятия FFI debt.
