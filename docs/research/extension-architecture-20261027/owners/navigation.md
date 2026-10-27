# Navigation: T06/H40 и producer interface

Источник — `inputs/owners/core/_extensions/course-navigation`, bundle8.3.0 provider3ccc9e8. Native Reveal владеет slide/fragment/notes/print mechanics; Navigation добавляет grouping/search/history/UI/permanent links. Snapshot/currentness proof Core здесь не внедрён, inspect/Deno processes из browser plugin не запускаются. Новый render/browser test не выполнялся.

## Активация и owner boundary

`filter.lua:16–62` немедленно возвращает non-Reveal AST; на Reveal читает только course-navigation/repo metadata, проверяет root-scoped URL/path и вводит context JSON script при наличии links. Он не ходит по AST content. HTML navbar/repo-actions остаются у Quarto. Top-level lang/messages/metrics setup выполняется при загрузке filter, даже если format иной.

`shortcodes.lua` делает работу только при explicit course-exercise-index: валидирует named args/role/group-by и возвращает marker. Core full post загружает exercise-index.ts лишь когда текущий HTML содержит marker и полная release модель уже проверена. Неполный run не читает старый index. RenderExerciseIndex — чистый DTO→escaped HTML consumer Core Course, группировка/sort, student отбор restricted; `source→current output` URL callback у Core post. Это producer/consumer compile edge между Core и Navigation внутри bundle, не private pointer reader.

Reveal plugin `plugin.js:8–16` выходит **до** Model.collect/UI.build при print mode или courseNav:false. Quarto всё равно может загрузить JS modules, но heavy slide clone/listeners не создаются. До ready только один listener и cleanup; после ready скрытые native sections уже удалены и собираются фактические coordinates. Отсутствие plugin в deck также passive.

## Операции и cost

* Model.collect один раз на start фильтрует физические слайды, получает native indices, клонирует каждый slide для безопасного search text, удаляет script/style/.speaker-notes; original aside.notes входят в поиск. GroupEntries — ordered grouping с явными sectionStart, повторное одинаковое название начинает новую группу, если physical boundary новая. Это DOM derived index, не удержание filesystem preimages.
* VisitHistory хранит до256 indices, отбрасывает forward branch после новой записи; repeat current no-op. Отдельное historyTarget предотвращает повторную запись при back/forward. Переход к fragment раскрывает только target через shared CourseDisclosure.
* UI.build создаёт sidebar+mobile section lists, controls и пустые dialogs один раз; доступность кнопок обновляется на ready/slidechanged/fragmentshown/fragmenthidden. current() линейно ищет slide в model; update проходит оба group lists. Формула O(L+G) на event, где L — slide count, G — groups, независимо от source-bank size.
* Overview previews **ленивые**, только при первом overview action; затем cached cards переиспользуются. Каждый clone переписывает IDs и SVG fragment URLs, удаляет media/notes/scripts/event attrs, делает inert/aria-hidden; ResizeObserver масштабирует только preview viewport. Это реальный дополнительный DOM snapshot слой UI, поэтому утверждать «snapshots вообще нет» неверно. Он не строится у inactive/print plugin.
* Search на открытие/input сравнивает L cached strings; для каждого совпавшего slide findTarget querySelectorAll по text-bearing nodes и выбор leaf target с `matches.some(child)` даёт потенциально O(M²) containment сравнений на slide. UI заново создаёт result buttons на каждое input event; debouncing/virtualization в source нет. Не доказано, что это значимо для реальных лекций.
* Destroy удаляет deck/root event handlers, disconnects ResizeObserver, убирает shell/classes, очищает cleanup map. Child listeners уходят вместе с shell; callbacks/footer labels имеют page/deck lifetime. Повторный init одного deck без destroy явно не защищён отдельным idempotence guard; supported caller Reveal lifecycle не проверялся новыми запусками.

Plain HTML получает только early-return Lua predicate, если filter подключён; Reveal с courseNav:false/print не платит Model/UI traversal. Активный Reveal платит one-time full slide text clone даже если пользователь не открывает поиск; overview clone откладывается. H40 общий тезис о неактивной heavy DOM работе поэтому **опровергнут** для отключённого/print Navigation; узкий тезис о eagerly prepared search index при активном plugin подтверждён. Не переносить Presentation unconditional AST passes на Navigation.

## Контракты и source verification

Permanent links сохраняют авторские course/project scopes и HTTP(S) repository URLs; local source path native input-based. Navigation filter не выводит public base из local filesystem. Context JSON экранирует `<`; UI принимает только http(s) resolved URLs, repository anchors получают noopener noreferrer. Publisher composition rebasing — иной owner, здесь только producer context, без присвоения Publisher поведения.

Model public namespace `CourseNavigationModules` / `CourseNavigation` / shared `CourseDisclosure` и `CoursePresentation.exposeNote` — dynamic browser interface, не typed imported DTO. Name/callback shape требуется совместно проверять installed JS assets. Optional calls не доказывают полноту функциональности при missing dependency; plugin.yml/html-dependency входят evidence поставки. Core Course import exercise-index.ts typed, но browser UI string payload runtime JSON не имеет общей generated schema.

Операции N01 (Reveal context config), N02 (shortcode/index), N03 (model/history/target), N04 (plugin lifecycle/dispatch), N05 (DOM UI/search/overview/cleanup) включают every named/anonymous function, factory и event callback соответствующих modules; observability facade отдельно. Tests `tests/navigation-model.cjs`, `tests/navigation.browser.cjs`, `tests/exercise-index.*`, presentation unified fixtures и navigation-typecheck находятся в Core ledger и имеют test-only activation. Они дают regression specification, не новый runtime receipt.

Малый proposed C-12: deck 3 содержательных slides + one continuation + public hint/notes; активный/false/print plugin, initial DOM clones/listeners, first/second overview, one search term and destroy. Сохраняемые invariants: native coordinates/visibility, repeated headings, ID uniqueness/SVG refs, notes disclosure, speaker S key, history, print, permanent links. Сравнение с3→6 cloned slides оправдано только при неясном росте; full Java не нужен. Runtime latency/heap не измерены.
