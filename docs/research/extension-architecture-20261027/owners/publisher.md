# Publisher: композиция, выбранная страница и повторная работа

Исследована линия 5.1.5 (`Publisher 5.1.5`), действительно установленная в Java. Отдельная Performance-линия 5.1.4 сохранена для сравнения, её поведение не подставлено в baseline. Обоснования ниже относятся к операциям; механические IDs всех функций/callsites находятся в локальные исследовательские материалы. Измеренные длительности здесь не заявляются.

## Активация и стоимость

`application/compose.ts:64` всегда получает workspace через native inspect. При отсутствии `QUARTO_PROJECT_RENDER_ALL=1` pre открывает только root Core run и возвращается: дочерние проекты в этой ветви не рендерятся. Поэтому гипотеза «каждая правка корневой страницы запускает все children» для этого callpath опровергнута. Цена Core proof, собственного workspace inspect и локальной QRC-публикации остаётся; child preview имеет собственные hooks.

Полная ветвь сначала проверяет все назначения, затем очищает output и последовательно запускает children. `inspectDocuments` делает один project inspect и document inspect для каждого native input. `validateAudienceOutputs` дополнительно проверяет существующие альтернативные student/full profiles, чтобы не удалить результат другой аудитории. Это разные необходимые факты; повторный inspect с тем же контекстом — кандидат на фазовое reuse, а не доказанный дубль.

Для P подпроектов и D выбранных документов до render имеется порядок O(P + D + A) native inspections, где A — число существующих альтернативных профильных проверок. Проверка пересечений children — O(P²), независимая от bytes Markdown. Это формулы по коду, не наблюдавшиеся числа процессов. Если форматы неоднородны и нельзя выбрать общий render target, `plan.renderTo=null` создаёт отдельный native вызов на документ; иначе один вызов на child.

Children исполняются последовательно в `compose.ts:145–200`. Возможность bounded jobs требует проверки shared outputs, catalogs, env, RAM и одновременного Core state. Самостоятельные source/output roots уже различаются, но из этого не следует безопасная произвольная параллельность. Для одного тяжёлого child scheduler не убирает внутренние затраты.

## Обоснование групп функций

| Операция / функции | Требование и consumer | Эффекты, частота и предел |
| --- | --- | --- |
| SITE-CONFIG: workspace, inspect, activeProfiles, validateConfig, inspectDocuments, validateAudienceOutputs | Native metadata/profiles, однозначный формат, безопасные source/output boundaries | Subprocess/FS; до очистки и render. Вычисляемые native formats нельзя заменить собственным YAML resolver. Переносимый DTO одной фазы — вариант уменьшения повторов |
| SITE-COMPOSE: pre, post, module, beginCore, qualify | Состав пяти учебных форм и независимые Core identities | Dynamic imports — межрепозиторный API; сериализация Map↔array и `any` не дают статической совместимости. Prefix добавляется к source, но projectRoot/common target сохраняют свою область |
| SITE-COLLECT: publicOutputs, collect, readCollection | Передавать именно текущие native outputs и ресурсы child | Env/file-list, FS validation, JSON record. Старые файлы не превращаются в результаты текущего selected render; collect должен оставаться последним hook |
| SITE-FILES: inside/within/safePath/outputDirectory/files/cleanOutput/copyFiles | Сохранить исходники, кеши и public/teacher границы; смонтировать подтверждённую выдачу | Обход F файлов и копирование B bytes. Cleanup, collection, copy и final link rewriting имеют разные эффекты; их совпадение по имени файла не доказывает избыточность |
| SITE-NAV: addressNavigation и вспомогательные URL/HTML функции | Общие ссылки root/child и открытие Reveal из индексов | Правятся разрешённые native navigation anchors и текущие outputs. Обычные content/QRC ссылки имеют другого владельца. Повторная обработка должна быть идемпотентной |
| SITE-CONDITIONS: collectConditions, stageConditions и converters | Финализировать отложенные QRC-ссылки внутри conditions ZIP | Только после full QRC; Python zipfile/HTMLParser и Pandoc сохраняют разные транспортные гарантии. Все outputs вычисляются до первой записи; старые receipts не переписываются |
| SITE-PROCESS/DIAGNOSTICS: quarto, profileArguments, runHook, contextualize, diagnostic | Сохранить native exit/stderr/cause и контекст проекта | Явный process env; success stderr не превращается в failure. Обвязка не объявляется бесплатной, но инструментальная запись отделяется от product wall |

`cleanOutput` 5.1.5 допускает recoverable Markdown лишь при совпадении bytes с реальным source и сохраняет запреты QMD/YAML/cache/symlink. Исторический отказ student→full→student из-за законного Markdown уже исправлен в этой линии. Он не является новым finding.

## H21–H25, H30, H56

- H21: частично — повторные native facts есть на разных callsites; достаточность общего immutable контекста требует проверки hook lifetime.
- H22: опровергнуто для автоматического запуска всех children на selected root path; полная композиция намеренно имеет широкий scope.
- H23: последовательность подтверждена; измеренный выигрыш и безопасный jobs budget неизвестны.
- H24: несколько обходов подтверждены, но cleanup/collect/mount/link/search защищают разные subjects. Полное удаление прохода не обосновано; объединение HTML parse возможно лишь с сохранением consumers.
- H25: исторически исправлено в выбранной 5.1.5; новое нарушение не воспроизведено.
- H30/H56: отделение scheduling от уменьшения работы необходимо; без controlled small experiment причинный выигрыш не установлен.

Опыт в общей очереди: неизменная selected root page при 1/4 children; отдельно полный портал и один child. Изменять jobs только отдельным вариантом после проверки зависимости данных. Full Java не нужен для первого различающего ответа.
