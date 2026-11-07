# Java: что находится внутри main.lua и один следующий Source diff

## Фактическая граница

В [последнем saved capture](java-native-filter-summary-20261104.md) Pandoc сообщил `main.lua`5370/5391ms и отдельно citeproc250/220ms для index/labs-index. По inspected official Pandoc3.10 Source это process CPU вокруг всего filter action; actual binary version остаётся UNKNOWN. **Разложения5370/5391ms по внутренним passes нет**. В envelope входят загрузка/инициализация Lua script, внутренние dispatch/walks/serializations и Lua-часть наших extensions. CPU внешнего early TS сюда не относится; его вызов и ожидание происходят внутри main.lua action, wall — отдельно.

Сохранённый tasks `_quarto.yml` задаёт filters в порядке navigation/download/core/presentation/PL/reference-catalog; `_quarto-full.yml` выбирает author/full. Это exact staged Javaa6/Core4a/PLd8 composition. Временные generated defaults/filter params не сохранены, поэтому нельзя восстановить каждый runtime flag/каждую отработавшую ветку как actual receipt. Ниже разделены установленный dispatch contract и подтверждённые reached handlers.

## Короткая карта dispatch

Installed [quarto.js:105083](/opt/quarto/bin/quarto.js:105083), `resolveFilters`, раскрывает extension contributions в `entryPoints`; обычный filter без явного `at` попадает до Quarto в `pre-quarto` (в данном config нет marker `quarto`). Native defaults получают **main.lua + citeproc**, а entryPoints передаются параметром `quarto-filters`, не отдельными верхними Pandoc filters. Actual verbose log подтверждает именно эти два top-level filters.

Installed [main.lua:26339](/opt/quarto/share/filters/main.lua:26339) строит такую последовательность:

| Порядок внутри main.lua | Основная работа / наши extensions |
| --- | --- |
| **pre-ast** user entries | Download early preflight → Core runtime-filter/early-context → Presentation → PL shim → QRC requests, в порядке relevant contributions исходного filters list. |
| Quarto init + normalize/AST pipeline | Meta/options/resource references, conditional knitr fixup, normalize draft/state и custom AST preparation. |
| **post-ast** user entries | QRC probes: targets init/capture/equations, затем построение hidden probe blocks. |
| **pre-quarto** user entries | Navigation, у которого contribution без явного `at`. |
| Quarto pre + optional crossref | Flags/options/shortcodes/conditional content/code annotations; combined figures/theorems/book targets/resources/project paths; crossref preprocessing/index/resolve/meta/write при enableCrossRef. |
| **post-quarto** user entries | Download filter; Core late-kind — метки Theorem/Proof/ConditionalBlock перед native renderer. |
| Layout + Quarto post | Columns/cites/panels, format-specific fixups, extended-node rendering; HTML path не делает все LaTeX/Typst/etc branches активными. |
| **post-render** user entries | Core late-filter: occurrence/assessment fragments, late/native sidecars, observed resources, cleanup returned AST. |
| Finalize | Book/cites/meta/dependencies/wrapped-writer finalization; затем main.lua возвращает документ Pandoc. **Citeproc — следующий отдельный top-level filter**, не часть5370/5391ms. |

[inject_user_filters_at_entry_points:1101](/opt/quarto/share/filters/main.lua:1101) вставляет загруженные filters перед соответствующим marker. [run_as_extended_ast:1394](/opt/quarto/share/filters/main.lua:1394) разворачивает multi-filter groups, `emulate_pandoc_filter` исполняет общую цепочку. [run_emulated_filter_chain:1286](/opt/quarto/share/filters/main.lua:1286) проверяет flags, вызывает filter и обслуживает vault/resetFileMetadata. `run_emulated_filter:532` выбирает обычный/extended walk либо специальные fast paths; **имя pass не равно одному гарантированному полному обходу**. Например built-in `combineFilters` сливает обработчики; некоторые ветки пропускаются flags/format. Поэтому Source list не даёт честного числа actual full walks или цены каждого pass.

## Что именно делают наши contributions

Все paths далее — `_extensions/Afonenko-Course-Tools/` внутри exact staged tasks.

- **Core** `_extension.yml` → `runtime-filter.lua` → `native-ts/early-context.lua` (pre-ast). Lua читает bound request/context и вызывает `pandoc.utils.run_json_filter(...early-filter.sh...)`; launcher запускает Quarto wrapper, затем direct `early-filter.ts`. Эти два child wall envelopes измерены отдельно. Lua transport/JSON conversion входят в main.lua CPU, child TS CPU — нет. `native-ts/late-kind.lua` и `late-filter.lua` исполняются внутри того же main.lua; отдельного позднего TS subprocess в этом Native path нет.
- **Download** `native-ts/early-filter.lua`: reserved transport checks и preflight walk; TS early child только при actual literal lowering. `filter.lua` post-quarto собирает requests/exercises и вызывает малый JSON batch child только при requests>0. В первых двух документах saved counters показывают early.walk1/filter.walk1 и **не показывают Download child receipts**.
- **Presentation** `filter.lua` pre-ast: identities walk и recursive Div transformation; saved counters walk.2=1, walk.1=4. Это разные scopes/рекурсивные blocks, не доказательство четырёх повторных full-document scans.
- **QRC** requests/probes + shortcodes работают внутри main.lua: requests переписывает catalog Cite; probes собирает targets и добавляет hidden rows, shortcodes создают link requests. Saved probes.Pandoc1 и targets/cross-reference counters подтверждают reached path. Hidden probes изменяют downstream AST и нужны для catalog contract; их стоимость/избыточность этими данными не доказана.
- **Navigation** Pandoc handler внутри main.lua; actual counter1 на каждом из двух документов. **PL** filter.lua — пустая техническая регистрация; substantive export/capture orchestration находится во внешнем TS entrypoint. Нельзя приписывать5s всему PL validator или считать его второй выполняющийся Lua AST validator.

Shortcode contributions навигации/Download/QRC вызываются native shortcode machinery внутри цепочки, не отдельными top-level filters. Built-in Quarto walks, user handlers и serialization способны вносить вклад в общий envelope, но ранжировать их по CPU здесь нельзя. Сохранённые manual resource facts всего2.578/3.109ms nested CPU не являются стоимостью всего Core/всего main.lua.

## Один доказанный лишний обход и ограниченная следующая задача

В [Core4a `native-ts/late-filter.lua:64`](java-native-filter-summary-20261104-actual/course/tasks/_extensions/Afonenko-Course-Tools/course-core/native-ts/late-filter.lua:64) Native path (`not COURSE_CORE_TS_RUN`) делает полный `doc:walk` для body: `Div=clean`, Span с `data-course-core-native-title` заменяется содержимым. На [строке85](java-native-filter-summary-20261104-actual/course/tasks/_extensions/Afonenko-Course-Tools/course-core/native-ts/late-filter.lua:85) тот же cleanup callback set проходит весь документ повторно для return. Между ними AST-изменений нет: writes/observedDoc/resource facts читают исходные blocks; в конце убираются только два meta keys. Actual resource facts1 в каждом completed документе подтверждает достигнутую Native ветку с первым cleanup. Exact `git diff 4a a06 -- .../late-filter.lua` пуст: этот остаток есть и в текущем accepted Corea06.

**Следующая bounded смысловая задача: один production file `late-filter.lua`, reuse единственного cleaned AST для Native body и возвращаемого документа.** Сохранить отдельный исходный `doc` для existing resource observation/sidecars, порядок guard/diagnostic/file writes и cleanup meta keys на возвращаемом документе. На TS-run ветке также требуется ровно один cleanup, как сейчас. Не объединять fragment-specific occurrence/title walks с whole-document cleanup; не трогать physical/resource roles/privacy/UUID/grade/producer evidence и не удалять QRC/Download/Quarto passes.

Это устранение повторной работы по Source, **не доказанный5s bottleneck и не обещание ускорения**. Для будущего diff требуется адресное подтверждение неизменных body/returned AST/meta/marker cleanup существующим focused control; сейчас ни код, ни tests/Native не запускались. Дополнительной широкой диагностики не предлагается. Единственный остающийся attribution gap — отсутствие actual per-pass CPU внутри main.lua; он не мешает рассмотреть этот конкретный duplicate cleanup и не разрешает оптимизировать остальные walks по догадке.

## Scope и допуск

Только saved config/log/receipts/maps/counters и installed primary Source, плюс exact read-only Core file diff. Product/driver/stage/output не изменялись; новый render/inspect/profiler/version/help/compiler/hash/network/Native —0, новых запусков утреннего пакета нет.

Own chat `01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1`, immutable origin `01a1a514-1688-7662-80fc-58bf7a272ae0`, expected `gpt-6.1-sol/high`, Standard. Primary rollout от08:41:59 в own session path: session_meta1; весь origin interval turn_context8/171/270/405/734/918/1031/1046/1192/1274/1437/1655/1749/1809/1997/2098/2253 прочитан, top-level/nested model/effort согласованы, PASS. Hidden routing не подтверждается этими metadata.
