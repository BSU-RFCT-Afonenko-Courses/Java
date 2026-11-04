# Source-only чтение границы Site

## Собственный первичный допуск

Назначение: gpt-6.1-sol / medium, Standard. Серверный service tier первичные записи не удостоверяют.
Источник: `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T07-06-42-01a1a4bc-daa7-7010-a431-8ef6f92c58b1.jsonl`. Проверены все turn_context от исходного хода; верхние и вложенные model/effort совпадают.

```json
[
  {
    "line": 1,
    "id": "01a1a4bc-daa7-7010-a431-8ef6f92c58b1",
    "agent_path": "/root/site_type_boundary_reading"
  },
  {
    "line": 8,
    "turn": "01a1a4bc-dad1-7a83-9ee7-9c062bebfc31",
    "root_turn": "01a1a4b3-8fd3-7be3-baf7-ce0cc5c60016",
    "model": "gpt-6.1-sol",
    "effort": "medium",
    "nested": {
      "model": "gpt-6.1-sol",
      "reasoning_effort": "medium",
      "developer_instructions": null
    }
  }
]
```

## Результат и точные исходники

4 ноября 2026. Это ограниченное чтение Source, не реализация и не приёмка типов. `java/providers.json` действительно закрепляет Site `e8b345cec235c2c8438c479892f6bca2a4ce3ee7` / 6.0.1; `system-practice/providers.json` — `e0efd6b5428412500569d0da5a5e0b33d62a5663` / 5.3.0. Общий repository — `Afonenko-Course-Tools/quarto-project-publish`. Эти два ref существенно различаются; общий перенос исправления без отдельного разбора обеих версий необоснован.

В имеющемся release `provider-sources` Site checkout отсутствует. Точные contents прочитаны GitHub plugin `fetch_file(ref=полный SHA)`; списки путей обоих Source деревьев получены GET `git/trees/<SHA>?recursive=1`, оба ответа `truncated:false`. Git fetch/checkout не выполнялись. Пути одной installed Java Site копии использованы только как указатель к Source; её код не выдаётся за Source. Основное чтение: 9 файлов на ref (AGENTS, spec/index, spec/contract, docs/public-operation, package, CI, compose, collection, config); дополнительно три крошечных entrypoint с одинаковыми blob IDs для обеих версий. Это небольшой выход за ориентир около 8 основных файлов ради точной границы config/Project/Workspace. `reports/typescript-any-audit.md` прочитан; нынешние Core/PL candidate pins в manifests уже отличаются от его исторического снимка, поэтому их прежние проверки здесь не переутверждаются.

Для ссылок ниже J = [Java Site Source](https://github.com/Afonenko-Course-Tools/quarto-project-publish/tree/e8b345cec235c2c8438c479892f6bca2a4ce3ee7), S = [SP Site Source](https://github.com/Afonenko-Course-Tools/quarto-project-publish/tree/e0efd6b5428412500569d0da5a5e0b33d62a5663). Пути production относительно `_extensions/course-site/`.

| Прочитанный файл | Blob J | Blob S |
| --- | --- | --- |
| application/compose.ts | `0fa47b90e625393da64e1cfde19e187536af3d10` | `d8025b8c9eef27c982009b6dd07def9f121eb6d0` |
| infrastructure/collection.ts | `1da469c259df4ab95470d204cdfdb7b4f425a8bc` | тот же |
| infrastructure/config.ts | `1380e089004ce4a55c45f6410aa741b64d947cf2` | `735fdf72cc8b5ea388d727793fb25fb8dbd4e04c` |
| AGENTS.md | `2caeac7596b18c2703ab6a455d1653d6bbe8c673` | тот же |
| spec/index.md | `9c04c9537488393d0388da79d7117b3a8525155d` | `31f3eab29ec30b08799099124d712191729f86d5` |
| spec/contract.md | `59daa6b105ee804071cf2842d3b8387384661041` | `06dc97a2d8f7a15d3fc5c654e7f6d75c32db2ad9` |
| docs/public-operation.md | `8071e38a9b9093fc31d23ddfe009601ec6894cbb` | `d2a785ea316b9f7a444e6b23096995b03a9c501a` |
| .github/workflows/ci.yml | `5e66df650317a059a5662a9c83e55c3973838d00` | `6fd21a8f9b6f3a09fab42ed9fc16c698b7fcebff` |
| package.json | `9c303aa88665ed1c9e952af7e61eb7b29cbce1a0` | тот же |

Это IDs из GitHub contents/tree, не вновь вычисленные hashes. AGENTS требует русские тексты и прочтение действующих контрактов перед изменением поведения; поведение не менялось. Контракт прямо требует сохранять owner scopes, genuine Core operations и public provider APIs; Project/Workspace выводятся из конструкторов/операций, отдельная ручная модель их формы не поддерживается.

## Compiler graph: что известно точно

В обоих полных Source деревьях нет `deno.json`, `deno.jsonc`, `tsconfig*`, `tools/`, файла с `typecheck` в имени; workflow один — `.github/workflows/ci.yml`. В обоих workflow runtime-проверки вызываются `quarto run tests/*.ts`; browser script из package.json — `node tests/site-navigation.browser.cjs`. Явного `deno check`, `tsc`, `--check` или compilerOptions в прочитанных gates нет. Следовательно, статус штатного Source compiler gate остаётся **UNKNOWN / не установлен**; наличие CI runtime suites не даёт доказательства compiler coverage. Полные тела всех tests/browser script в этот bounded pass не читались: скрытый вложенный compiler вызов не исключён и не объявляется отсутствующим.

Точная входная статическая часть одинакова: `entrypoints/pre.ts` → `application/compose.ts` + `infrastructure/diagnostics.ts`; post аналогично; collect → `infrastructure/collection.ts` + diagnostics. Compose статически импортирует bridge, navigation, diagnostics, files, config, process, collection. Collection — bridge/navigation/diagnostics/files; config — diagnostics/files/process. Эти рёбра видны в Source. Транзитивные внутренности всех листьев не аудированы. Независимо от выбора будущего entrypoint для `deno check`, следующие runtime рёбра **не получают конкретного module shape из Source**: `module(name,path,owner):Promise<any>` (J/S compose:32–57) → `import(toFileUrl(source).href)`; отдельные PL imports также динамические. Они не превращаются в проверенный provider graph от проверки самого compose.

## Реальные потери типов и достаточные фасады

1. **Core/PL method и DTO boundary.** В обеих версиях compose `module():Promise<any>`, `PL:any`, `operation:any`, `Core:any`. J:149–183 / S:149–178 вызывают `createNativeAdapter`, `operation.validateRelease`, `adapter.validateCourse`; output proof и `validatedAdapters` идут через any. J:281–298 / S:230–246 `withCurrentNativeOperation` получает owner/root/profiles/projection/outputDirectory и callback operation, но сигнатура и DTO не сверяются с Core. J/S:118–142 `prepareNativeAdapter` столь же непрозрачен. Проверки существования файла, releaseQualification, owner-root и outputs не являются проверками формы экспортов. Здесь typo метода либо неверный DTO статически не запрещён по прочитанным аннотациям; это вывод о contract blindness, не новый compiler probe и не найденный неверный production вызов.

2. **Download/QRC/Navigation.** J:268–279 / S:214–226 `finishDownloads` получает опции и `observeOperation(value:any,proof:unknown)`; J:318,428 / S:265,371 вызывают `prepareDownloads`. J:488–503,591–606 / S:487 и далее вызывают `publishReferenceCatalog`; J:609–627 / S:606 и далее — `composeNavigation`. Destructuring из any стирает сигнатуры и проверку fields (`scope`, members, searchIndexes, outputDirectory, projection). Достаточные кандидаты — owner-derived `Pick<typeof import(точный public.ts), 'используемые exports'>` для Core, PL, Download, QRC, Navigation; у Core — существующие `NativeOperation`, `NativeCatalog`, `NativeReleaseProof`, `NativeReleaseRequest`, `NativeCaptureScope`. Собственные Source public exports Core и Download просмотрены адресно: Core уже экспортирует эти типы, Download экспортирует `FinishOptions`; PL экспортирует public функции. Это кандидаты привязки к **конкретному совместимому ref**, не доказательство совпадения с обоими installed составами. Простое `as Provider` или `unknown as` у динамического импорта не проверит exports: нужны bounded module guards и сохранение owner/runtime checks, а сигнатуры должны выводиться из действительного public API, без нового параллельного DTO framework.

3. **Collection — отдельная конкретная дырка.** J/S infrastructure/collection.ts:112–220 имеет return `Promise<Record>`, но JSON читается в `value:any`. Есть identity/output/profile checks, array checks, path/current-file guards. Нет начальной проверки object/non-null; arrays `nativeOutputs`/`files` не проверяются поэлементно как strings перед `within`/`safePath`; `return value` любой формы принимается TypeScript как Record. Поэтому аннотация возвращаемого типа не доказывает decoder. Здесь достаточно `unknown` + существующие проверки формы/строк + построение уже существующего Record. Не надо заменять path/currentness guards приведением типа.

4. **State/config загрязняют даже выведенные типы.** J compose:523 / S post аналогично деструктурирует JSON state без decoder. `config.inspect():Promise<any>`, `workspace`/`inspectProject` читают native inspect JSON как any; `Workspace=Awaited<ReturnType<typeof workspace>>` сохраняет эти any поля. `Project=ReturnType<typeof projectRecord>` сам по себе точен для собственных строк и J `to`; это не основание вручную дублировать Project/Workspace. Metadata extension bags могут оставаться unknown, а наблюдаемые input/resources/formats требуют своих existing guards. Этот долг шире первого provider boundary и не включён целиком в следующий scope.

## Расхождение двух pins

S compose:416–449 ещё использует `Core.withNativeCaptureScope`, `{expectedSources: plan.documents.map(...)}`, `scope.captureInputs` и `expectedDocuments:{source,format:baseFormat}[]`; config экспортирует `DocumentFormat`/`DocumentPlan` из per-document inspect. J compose:433–473 выполняет один native child render с `--to project.to`, сохраняет `selectedSources` и `to`; J catalogCoverage:185–216 проверяет source set, duplicate outputs и format. S catalogCoverage:179 и далее проверяет предыдущую форму. J config Project включает `to:'default'|'html'|'revealjs'`, S Project такого поля не имеет. Также public-operation J разделяет Download один-pass PL validation и no-Download два прохода; S описывает прежний два-pass seam. Поэтому тип façade для capture нельзя безусловно навязать J, а DTO selectedSources/to — S.

## Следующий ограниченный meaningful scope

Взять один exact Source ref J, до трёх production файлов: `application/compose.ts`, существующий `infrastructure/collection.ts`, при необходимости один локальный provider façade рядом с compose.
Сначала восстановить только Core/PL public boundary и collection decoder: owner-derived signatures/DTO, bounded export guards, unknown до доказанной формы, сохранение всех currentness/release guards.
Reference: точные public APIs совместимых Core/PL из выбранного установленного состава; не переносить на S capture seam без отдельного решения.
Отдельно выбрать явный compiler entrypoint этого статического графа; в текущем чтении compiler, tests, build/render/inspect/install не запускались, Source/worktrees/pins/state не менялись, commits не создавались.

Итоговая сверка первичных метаданных: весь интервал от исходного turn `01a1a4bc-dad1-7a83-9ee7-9c062bebfc31`, все turn_context на строках 8 совпадают с gpt-6.1-sol/medium, включая вложенные settings.

## Возобновление: только изоляция Source

Перед созданием нового bare store подтверждён весь исходный интервал gpt-6.1-sol/medium: turn_context строки 8, 181, 199. Root GO: один shallow fetch двух exact refs, cap 30s/retry0, затем один J worktree; без setup/runs/Source edits.

Один fetch завершился exit0 до cap; повторов не было. Точные refs проверены до worktree add. Source clean подтверждён `git status --porcelain=v1 --untracked-files=all` (пусто). Hooks выключены для worktree add; submodules не инициализировались. Original course-tools не затрагивался.

```json
{
  "objectStore": "/home/tolya/JavaCodex/java-release-20261110/provider-sources/quarto-project-publish",
  "worktree": "/home/tolya/JavaCodex/java-release-20261110/site-java",
  "refs": {
    "refs/source/java-e8b345": "e8b345cec235c2c8438c479892f6bca2a4ce3ee7",
    "refs/source/sp-e0efd6": "e0efd6b5428412500569d0da5a5e0b33d62a5663"
  },
  "branch": "codex/java-release-20261110",
  "HEAD": "e8b345cec235c2c8438c479892f6bca2a4ce3ee7",
  "isBare": "true",
  "isShallow": "true",
  "origin": "https://github.com/Afonenko-Course-Tools/quarto-project-publish.git",
  "commonDir": "/home/tolya/JavaCodex/java-release-20261110/provider-sources/quarto-project-publish",
  "gitDir": "/home/tolya/JavaCodex/java-release-20261110/provider-sources/quarto-project-publish/worktrees/site-java",
  "sourceStatus": "",
  "shallowBoundary": [
    "e0efd6b5428412500569d0da5a5e0b33d62a5663",
    "e8b345cec235c2c8438c479892f6bca2a4ce3ee7"
  ]
}
```

Уточнение root после чтения отчёта: internal trusted pinned providers типизировать через actual ModuleMap и существующие owner/root checks; новые export validators ради статической типизации не требуются. Поэтому предыдущая рекомендация bounded export guards снята для internal provider seam. Collection остаётся самостоятельной external unknown-boundary; дополнительные element guards при подтверждении — отдельный предметный fix. Implementation этим исполнителем не выполнялась.

Финальная сверка возобновлённой isolation-only роли: все turn_context исходного интервала на строках 8, 181, 199 согласованы gpt-6.1-sol/medium сверху и в collaboration_mode.
