# Core YAML: provenance follow-up, pin пока не установлен

**Результат — commit добавления bundle установлен по public GitHub metadata; exact YAML version всё ещё UNKNOWN, ready-to-implement typing patch пока нет.** Продолжен [первый Source plan](core-yaml-node-types-20261104.md) в сохранённом `core-yaml-types`, base0cf. Vendor/runtime/product не менялись; существующий untracked reports/ и все dirty/worktrees сохранены. Compiler/Native/install/git fetch не запускались; отдельно разрешённые3 public connector reads описаны ниже.

## Что установлено

`project-context.ts:14` действительно импортирует isAlias/parseDocument из `body-export/vendor/libraries.js`. Bundle содержит minified YAML implementation и UUIDv5; экспортирует только isAlias/parseDocument/v5. В тексте нет sourceMappingURL, npm YAML package-path/version markers или build metadata. yaml-LICENSE сообщает авторство/лицензию, не версию. Два существующих package/lock набора и адресно просмотренные tools/.github не связывают этот bundle с exact YAML pin. Matching declarations рядом нет. Повторно подбирать типы системных YAML версий не предпринималось: это не provenance.

**Новое существенное уточнение:** `core-yaml-types` и original release `core` — shallow repositories. `git log --all --follow` останавливается на `a478d2cc0dfb19e0bebd99108815ac8ef1fe85a8` (merge PR67). Следовательно прежнее единственное log entry **не доказывает первый commit/сборку bundle**. Raw commit имеет два parent refs:

- `70d0494ade8c7747b484b35d91fbd4401c7e5c09`;
- `2ffd2a8d5f2cc1ee636293ef11557fd9f241a941`.

Оба commit objects отсутствуют локально (`git cat-file -e …^{commit}`), так что обойти только shallow traversal, используя уже имеющиеся parent objects, нельзя. История bundle до этой границы из данных checkout не восстанавливается. Shallow state/history не изменялись, git fetch не выполнялся. Сохранённые адресные reports Core bridge/Pandoc binding не дали первичного YAML build manifest.

## Минимальный следующий шаг

После ограниченного remote follow-up ниже найден vendor commit; остаётся **один недостающий первичный источник**: сохранённый manifest/команда сборщика именно bundle, где exact YAML version закреплён. Это не запрос установить наиболее похожую системную библиотеку и не основание объявить version по API fingerprint. Следующий ограниченный Source шаг — получить authoring/build artifact vendor commit ниже, а не повторять обход shallow parents или всего Core.

До такого evidence exact pin остаётся UNKNOWN; переносимые matching declarations честно не выбраны. После установления pin можно отдельно разрешить сохранение declarations той же версии и проверку portable declaration graph для существующих exports; только затем типизировать tree/pair callbacks без смены алгоритма. Код пока не готов к реализации в честном narrow scope. Править bundle, подменять импорты системными absolute paths, выдавать ReturnType<any> за тип или писать собственный AST validator не предлагается.

Оставшиеся production holes ровно прежние: tree(n:any) и pair callbacks в origin YAML traversal. Alias/anchor/tag/maps/scalar key/source-range алгоритм и runtime guards не расширялись. Снятие этого typing долга не требует нового freeze/cache/proof framework, но требует реального происхождения vendor типов.

Applicable AGENTS, ранее прочитанные spec и нормативный model-admission применены. [Собственный origin admission](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-yaml-provenance-followup-admission-20261104.json): неизменный исходный turn, все40 доступных contexts top/nested Sol6.1/medium PASS; Standard назначен, hidden routing не утверждается. Mainstate/audit/globals/чужие Source не изменены, подагенты не запускались.


## Разрешённые три public GitHub чтения

Root предоставил [path history response](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-yaml-vendor-history-remote-20261104.json): единственный vendor commit `e39380fc15c9fdfe003809f2463256d8084f0b65`, parent `946a4eca4f77aeaba137b1fc10ae38e11ec014f2`. Это дополняет локальный shallow предел, а не подтверждает YAML version.

Исполнитель использовал ровно3 разрешённых GitHub connector reads, без загрузки bundle:

1. Git commit metadata exacte393: подтверждены parent и tree `23d4a013c5268042d6b13c2302eb056c0522d0bc`, сообщение `feat(core): seal production owner bodies and native book works`.
2. Этот exact tree recursive, `truncated=false`: body-export/vendor содержит только libraries.js и YAML/UUID licenses, без matching declarations/build manifest; tools содержит только sync-contract.mjs. Root package.json/package-lock присутствуют. Есть отдельные tests/probes/diagnostics package/build files, но tree сам по себе не связывает их с production body-export bundle; их dependency versions не выдавались за происхождение bundle. Blob libraries.js в этом tree — `f6dcb1ce70f2813c4ae3cc8a310d76a8e99416d8`, это Git metadata, не новый hash-проход или чтение его содержимого.
3. Root package-lock.json именно в e393, blob `271dd59f0743e64685975ee559ede5ab3cf6da94`: только Playwright1.62.1/Playwright-core и optional fsevents; YAML/UUID/bundler pin отсутствует. Никаких npm/git installs/fetch в локальные repos.

[Сохранённое primary evidence трёх чтений](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-yaml-provenance-followup-remote-20261104.json) содержит URLs, точные commit/tree/blob refs, relevant tree entries и lock content. Набор чтений исчерпан; широкой remote археологии не было.

**Точный итог:** известен commit поставки minified runtime, но build provenance (версия YAML, entrypoint/build command/dependency resolution) в просмотренных production manifests не установлена. Источника для переносимых matching declarations ещё нет. Следующий шаг требует конкретного authoring manifest/lock/log этого vendor commit; без него не предлагается брать ближайшую системную версию, делать runtime rebuild или придумывать типы. Compiler и product diff остаются0; vendor runtime не менялся.
