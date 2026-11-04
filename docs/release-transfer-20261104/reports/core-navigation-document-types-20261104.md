# Navigation document candidate: declaration-resolution gap

**Candidate не принят: compiler FAIL, runtime small control PASS.** Изменён только `_extensions/course-navigation/html/document.ts` в `core-native-bridge`. Existing чужой dirty `_extensions/course-core/native-ts/late-filter.lua` сохранён. Commit/push/mainstate не выполнялись; после результатов Source заморожен.

Дельта по принятому [Source plan](core-navigation-document-types-next-20261104.md): portable `@deno-types` к actual vendored parse5 7.3.0 index.d.ts; local Document/Node/Element aliases из настоящего DefaultTreeAdapterTypes; удалён doublecast. `replace` и `parseHtml` — wrapped const из прежних named functions с теми же metrics IDs/kind, без двух binding-reassignment suppressions. Нет новых guards/casts/ambient fake schemas; vendor и consumer не менялись. [Exact patch](core-navigation-document-types-20261104.patch).

## Одна разрешённая попытка каждого check

- Affected compiler `document.ts` + `tests/navigation-composition.ts`: **FAIL**, 0.8592 s, cap10/retry0,28 diagnostics.
- Existing small pure navigation-composition control: **PASS**, 0.2647 s, cap10/retry0. Проверены HTML/Reveal menu, relative links/URL prefixes, repository/search preservation, missing input и повторный вызов. `--deny-run --deny-net`; Native/render/browser не запускались.

[Commands/elapsed/exits](core-navigation-document-types-check-20261104.json), [полный первый compiler log](core-navigation-document-types-compiler-20261104.log), [control log](core-navigation-document-types-control-20261104.log). До запусков `git diff --check` PASS. Bundled Deno/cache/existing actual import map использованы без install/network/cached-only check.

## Точный gap и следующий выбор root

Первый printed diagnostic TS7006 в `composition.ts:209` (два callbacks), затем325/327 и document.ts18 — attrs уже потеряли тип из-за неразрешённых aliases. Причинные vendor diagnostics: TS2305 `ParserOptions` в `index.d.ts:1`, TS2724 `DefaultTreeAdapterMap` на2, далее отсутствующие adapter interfaces/types. Deno применил explicit top-level declaration link, но transitive imports `.js` внутри vendor d.ts разрешил как **JS**, не соседние `.d.ts`. Реальные объявления физически есть, но проверяемый declaration graph не собран. Нельзя считать такой alias покрытием или добавлять callback annotations/casts ради зелёного результата.

Это ещё не доказательство, что настоящий parse5 union несовместим с composition consumer: checker до полноценных aliases не дошёл. Для полного FFI slice нужен отдельно согласованный portable declaration-resolution closure vendor; он выходит за данный1file scope. Никаких declaration copies/патчей vendor/import-map framework/skip diagnostics здесь не добавлялось.

Root может отдельно выбрать wrapper-only candidate, сохранив unresolved FFI cast до настоящего declaration graph, либо разрешить ограниченное связывание существующих vendor declarations. Автоматически ни один вариант не применён; повтор compiler не выполнялся. Runtime PASS не превращает failed type candidate в принятую типизацию.

Own роль/model/effort неизменны; [существующий origin admission](core-condition-walk-types-admission-20261104.json). История failed attempt и scope сохранены.
