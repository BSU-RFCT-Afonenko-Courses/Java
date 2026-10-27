# QRC: текущие outputs, ссылки и каталоги

Выбран latest owner 3.1.1 `QRC 3.1.1`; baseline Java установлен на 3.1.0 `QRC 3.1.0`. Эти состояния различаются наблюдением и фиксируются отдельно. QRC владеет адресацией, а не сборкой child проектов. Изучение не запускает HTTP/браузер или рендер.

`entrypoints/post.ts` делает native project inspect и запрашивает версию Quarto, получает текущий output list и вызывает `publish(scope="local")`. Publisher после полной композиции передаёт общий список outputs/search indexes в `publish(scope="full")`. Local не подтверждает полноту всего курса; full не должен заново объявлять старые child files текущими.

В `infrastructure/publish.ts` проверяются containment и обычные файлы, читаются только переданные текущие HTML, создаются targets/imports и вызывается linker. Полный результат ссылок вычисляется до начала записи. `linked.parsedPages` затем переиспользуется поиском: гипотеза полного отсутствия reuse опровергнута. Full search merge дополнительно читает текущие индексы и учитывает mount; catalog и search имеют разные consumers.

| Операция / модули | Обоснование | Стоимость и предел |
| --- | --- | --- |
| QRC-CONFIG: config/import-config/export-config/metadata/catalog-source/catalog-validation | Явные namespaces, exports и разрешённые local/remote imports | Type/runtime validation и optional Core boundary. Структурные ошибки должны отказывать до HTML rewriting; типы не заменяют проверку содержимого чужого JSON |
| QRC-PAGE: pages/html/linker/domain urls/catalog/exports | Собрать фактические anchors и разрешить ссылки, сохраняя semantic IDs | HTML parse по текущим страницам, maps targets и URL checks. Эти операции не являются Core currentness proof |
| QRC-PUBLISH: publish/currentFiles/importTargets/updateSearch | Получить atomic planned link result, local/full catalog и корректный search | O(HTML bytes + targets + links + index rows) в основных проходах; unknown library internals отдельно исключены. Измерений времени нет |
| QRC-LUA: targets,links,shortcodes,requests,probes,attributes,diagnostics | Собрать native targets/requests и отложенные ссылки до общей публикации | AST callbacks; probe/shortcode semantics нельзя удалить лишь потому, что final linker тоже читает ссылки. Активация и роль каждой группы отражены в ledger |
| QRC-BODY: entrypoints/body-links.ts | Отдельный renderer contract для выбранного Body/conditions | Нужен при явном экспорте. Его конвертация не считается ценой обычной страницы без callpath |
| QRC-BROWSER: navigation.js/external.css | Клиентские переходы и external-link presentation | DOM/browser lifecycle, собственные listeners; отсутствие события в server trace не доказывает неиспользуемость |
| QRC-GENERATED/DEV | Стили ссылок, CUE contract, tests и generators | Generated TS/Lua связываются с исходным словарём/генератором; не получают независимые авторские каноны |

H26–H29: local/full различие обосновано явной областью гарантии, повторные inspections и формат транспорта остаются кандидатами на упрощение. Принятая owner normalization сортирует собственные metadata attrs; она не даёт права универсально нормализовать учебный HTML и скрывать различия. Private Core serialization при композиции требует узкого публичного DTO и ранней installed compatibility проверки; это предложение, ещё не реализованный контракт.

Опыт: local selected output с неизменными соседними страницами и full composition; сравнить links/anchors/search/catalog, включая отсутствующий target и допустимую deferred ссылку. Одно наблюдение внешнего времени не позволяет удалить отдельный AST или publication pass.
