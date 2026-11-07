# Выполняемые ячейки Quarto: engine → AST → Native / README / ZIP

Дата: 2026-11-04. Независимый Source-only разбор. **Вывод: обычные вычисляемые документы не требуют нашего execution engine, нового общего договора, final freeze или discovery runner. Главный прямой запрет введён нами в `static`; второй, независимый барьер — требование неизменности всего source дерева.** Готовые downstream consumers уже принимают итоговые тела и наблюдавшиеся ресурсы, но перенос произвольного интерактивного HTML в README/PL ими не обеспечен.

## Область и первичный допуск

Core прочитан по стабильному `46b3590`, installed Core `d7` сопоставлен адресно по `runtime/operation.ts`; pending работа другого Core owner не рецензируется и не меняется. PL Source, Download `fcff6ad`, Site Source и фактический `/opt/quarto/bin/quarto.js` читаются как реализации, а не как доказательство приёмки установленной общей композиции. Quarto 1.11.5 — версия сохранённого R3 стенда. Кеши/freeze отдельно исследует другой owner; здесь только необходимая связь с execution/currentness.

Собственная primary запись: `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T00-26-01-01a1a34e-02f6-7021-ba73-02522b0a6a14.jsonl`; chat `01a1a34e-02f6-7021-ba73-02522b0a6a14`, путь `/root/review_download_types`, исходный turn `01a1a34e-032a-7541-b085-5af16bf83f02`. Назначено `gpt-6.1-sol/high`; Standard — назначение, скрытый service tier первичными данными не подтверждается. Полный интервал от исходного turn, включая `turn_context` строки 8, 105, 169, 207, 346, 569, 649, 706 и 721: верхние `model/effort` и вложенные настройки `collaboration_mode` совпадают с `gpt-6.1-sol/high`. PASS; baseline не переносился.

Новых render/inspect/hash/build/compiler/tests, ядер, установок или проб нет. Прочитаны сохранённые R3 `report.md`, `v3/results.json`, `v3/include-revision.json`, исходники известных fixtures; успешные опыты не повторялись. Две прямо разрешённые официальные страницы прочитаны без общего поиска. Source/state, installed copies, данные и кеши не менялись; запись только этого отчёта.

## Что уже доказано, а что остаётся неизвестным

Существующие результаты: `/home/tolya/JavaCodex/java-research-20261103/run-ultra-01a1a144-b034/R3/v3/results.json`.

| Случай | Сохранённое свидетельство | Граница вывода |
| --- | --- | --- |
| R / knitr | Ячейка `results: asis`, inline, вычисляемый include, `exr-generated` в pre-AST и HTML; первый project render 2.6268 s, targeted 2.7200 s | Подтверждён Native порядок и синтаксически generated упражнение. Core → PL/README/ZIP этим не принят |
| Python / Jupyter | Host render 16.4341 s, targeted 3.4430 s; фактические `VALUE`, inline и запись ledger | Подтверждено выполнение. `ast_ids` в Python результатах пустой: отдельного наблюдения generated exercise в pre-AST здесь нет |
| Includes | R include исполнялся в контексте главного документа; changed include отсутствовал в project freeze и появился targeted | Не испытание смешанных движков или Python include |
| Побочные файлы | Ячейки пишут `_results/ledger.txt`, R ещё `_results/value.txt` и `generated.qmd` | Это не график и не проверка resource closure в ZIP |
| Поздний QMD | `generated.qmd` не получил HTML в текущем project render | Отдельный жизненный цикл списка входов, а не отсутствие поддержки computed AST известного QMD |
| Figures | В Source уже есть Native capture/move/read corridor для изображений | R3 fixture не строит plot. Непрерывный engine → figure → README/ZIP результат UNKNOWN |
| Julia, OJS, другие engines / widgets | В Quarto Source есть соответствующие пути; нового engine matrix нет | Локальная доступность, конкретное поведение и переносимость outputs UNKNOWN; ни отказ, ни полная поддержка не заявляются |

Первый Python запуск остановился в ограниченном окружении; сохранённый успешный host запуск использовал локальный Jupyter IPC после подготовки собственных runtime directories. Это не доказательство необходимости внешней сети или наличия ядра на production host. Ошибки setup и ранние невалидные версии R3 не переименовываются в product failures.

## Фактический Native поток

В установленном Quarto `/opt/quarto/bin/quarto.js:136954` `setExecuteEnvironment` задаёт `QUARTO_DOCUMENT_PATH`, `QUARTO_DOCUMENT_FILE`, `QUARTO_PROJECT_ROOT` и `QUARTO_EXECUTE_INFO`. Последний указывает на временный JSON с actual document-path/format. `renderExecute` в `137065` вызывает `context.engine.execute(executeOptions)`; затем `executeResult.markdown` передаётся в Pandoc (`131295–131339`). Поэтому Core `pre-ast` получает уже вычисленный Markdown/reader AST, а не исходный fenced R/Python код как единственный материал. При thaw Quarto возвращает сохранённый execute result, затем снова идёт Pandoc/filter pipeline: current capture сам по себе не означает fresh execution.

Порядок нашей обработки в stable Core:

1. `entrypoints/native-pre.ts` / `runtime/operation.ts:1398–1420`: snapshot, Native owner inspect, bind policy и inputs.
2. Native engine / inline / computational include; затем reader. `_extension.yml:6` подключает Core на `pre-ast`.
3. `native-ts/early-context.lua:81` передаёт actual engine/format/filter/shortcode observation; `early-filter.ts:64–85` связывает текущий assignment capture и нормализует вычисленный AST. `assignment-producer.ts:25–40` требует наблюдение engine, но **не ограничивает его строкой markdown**. Проверки literal shortcode — отдельная граница ещё не разрешённого transport, а не запрет исполненных ячеек.
4. Core нормализует роли/IDs/назначения, Quarto разрешает условия, custom nodes и crossref; `late-kind.lua`, `late-filter.lua:1–74` сохраняют фактические visible occurrences, titles, final body и ресурсные наблюдения.
5. `operation.ts:1617` строит finalized document. `document/condition.ts:21–99` выбирает ровно одно фактически видимое появление и его late body. Не требуется повторно исполнять исходный документ ради condition.
6. Для PL: `assignmentDelivery` → public package/resources → `application/export.ts:466` Native Pandoc JSON → HTML5. Для README: `artifacts/project.ts:815` Native Pandoc JSON → GFM, локальные resource URLs переписываются на inventory targets. Для ZIP: Download `infrastructure/runtime.ts:342–508` берёт этот condition/plan/closure/inventory и вызывает `packageArtifact`; собственного engine на этой стадии нет.

Полный нативный документ не обязан превращаться в README целиком. Экспортируется выбранное публичное условие с его resource closure. Простые вычисленные числа, текст, таблицы и обычные Image nodes идут тем же путём, что авторские AST nodes; происхождение из cell не требует нового типа DTO.

## Где мешает наша обвязка

| Реальное место | Что ограничивает | Чей контракт / оправдание | Цена и решение |
| --- | --- | --- | --- |
| Core `operation.ts:986–1034`; installed d7 `982–1032`; Java `tasks/_quarto.yml:6150` | `static` требует `owner.engines` только markdown, запрещает OJS и обычные вычисляемые codeCells | Собственная оптимизационная source policy. Это прямой отказ до исполнения, не Native несовместимость входа | Отказ R3 до execution сохранён, 0.696 s; текущий overhead не измерялся. Перейти к Native/general для computed owner, не добавлять engine whitelist |
| Core `runtime/source-snapshot.ts:86–127,162–282`; `operation.ts:1938` | Прежние signatures каждого snapshot file, состав каталогов; только наблюдавшиеся новые referenced resources допускаются как additions | Собственная гарантия same-S дерева. Полезна для привязки читаемого исходника/выдачи, но весь writable рабочий каталог Native engine не является неизменным входом | Даже `general` не снимает standalone final full fence. Перезаписанный старый graph/ledger/cache может отказать; новые arbitrary side effects могут отказать. Это вывод Source, не выполненный негативный опыт |
| `operation.ts:2928–2948`, A1 project-context | Чтение проекта разрешено из baseline rows с прежней signature | Привязка starter/tests/solutions и Native metadata к текущей операции; реальная необходимость для выбранных выдаваемых файлов. Не причина требовать неизменность всех побочных файлов | Computed-only изменения не надо искусственно копировать в A1. Изменение действительно потребляемого starter/config внутри той же операции требует выбранной границы чтения, не молчаливого смешения старого плана и новых bytes |
| `project-context.ts:1024–1072` | Fast eligibility отвергает execution overrides | Это **fallback**, а не admission failure: дальнейший путь делает Native inspect в disposable owner (`1212–1298`). Только OJS в static явно отказывает | Потеря быстрого пути и копия baseline source возможны; цена UNKNOWN. Не делать inspect-контекст вторым execution/render runner |
| `assignment-execution.ts:165–172,223–236` | Exact render argv/output/profiles; добавить `--cache-refresh` или targeted input сегодня нельзя без согласованного caller diff | Своя корреляция child с назначением. Не запрет движка: разрешён обычный `render --output-dir … --profile …` | При необходимости прокинуть конкретный Native флаг через существующий command path. Не вводить отдельную модель execution flags |
| `artifacts/project.ts:700–712`, `body-export/producer.ts:112–153` | README допускает узкие Raw HTML wrappers; PL body поддерживает конечный набор AST nodes, Raw в него не входит | Настоящая переносимость/активное содержимое. Не вся browser execution semantics представима в GFM или PL question | Простые cells не блокирует по признаку engine. Widgets/OJS/HTML-only outputs требуют отдельного решения конечного формата; запрет всех engines этим не оправдан |

Переключить только `static` → `general` — полезный диагностический кандидат, **не готовое исправление всей поддержки**. `general` добавляет более частые guards; он по-прежнему основан на baseline source rows, а не на модели обычного mutable Native рабочего каталога. Не следует одновременно объявлять этот switch бесплатным или обязательной безопасной архитектурой.

PL `application/native-entry.ts:191,291` запускает обычный Native render в `_generated/prairielearn-native-render`; Core дописывает full profile. Site `application/compose.ts:458` запускает `render . --to …` с профилями, без `--no-execute`. Download потребляет current Core operation. Следовательно добавлять параллельное исполнение в PL/Download/Site не нужно. Ограничения возникают до и после engine, а не от отсутствия команды engine в каждом расширении.

## Ресурсы, приватность и currentness

`native-resources.lua:54–66` уже наблюдает image/link bytes рядом с input, до того как Native writer перенесёт supporting files в output. `operation.ts:1798–1881` принимает owner-contained ordinary resource, сопоставляет actual observation/output, умеет relocated fallback и включает его в complete.resources. `artifacts/project.ts:583–603` умеет читать через `readNativeResource`, когда это не baseline project file. Существующий путь достаточен как техническая основа для Native figure → README resources → ZIP; новый sidecar schema/receipt для факта «это из ячейки» не требуется.

Границы, которые действительно остаются:

- Публичный body должен строиться из выбранного late AST и публичных ролей, не из всего full документа. `assignment-roles.ts:88–109` запрещает закрытый origin/physical alias и unclassified файл внутри declared project; `operation.ts:3288–3316` включает уже наблюдавшиеся generated resources в known set. Получение arbitrary bytes из `solutions` под видом картинки не становится допустимым после снятия static.
- Вычисляемый график под обычным `<document>_files/` рядом с учебным QMD и график внутри declared code-project — разные случаи. Во втором сработает существующая role boundary, если путь не имеет публичной роли. Нельзя обещать поддержку всех destination folders на основании первого.
- Безопасность student/full не равна выводу имени профиля. Доверенный авторский код может прочитать секрет и напечатать его в обычный Para или в публичную PNG; удаление блока `.solution` это не обнаружит. Для поддержки обычного Quarto нужен такой же trust авторского execution, а не новая ложная гарантия taint analysis. Грейдер студенческого кода — другая граница.
- Native engine имеет side effects с правами запускающего пользователя. Source snapshot обнаруживает часть изменений после исполнения; он не является sandbox и не предотвращает чтение файлов, сеть или процессы. Нельзя обосновывать запрет ячеек мнимой sandbox защитой snapshot. Нельзя и выдавать `QUARTO_RUN_NO_NETWORK=true` за универсальный запрет сети R/Python user code.
- Ресурс должен принадлежать фактически выбранному публичному body и иметь доступные bytes при материализации; containment, private role, destination collisions и output ownership остаются нужны. Идентичность всей рабочей директории между engine и ZIP — более сильное наше допущение, Native её не требует. Проверку соответствия реально потреблённых bytes не надо превращать в content hash identity всего запуска.

Currentness с Native freeze/cache требует честного названия: «результат текущего Native render с его политикой кеширования», а не «все внешние данные пересчитаны». R3 показал external data/include/profile reuse. Например full targeted оставил `PROFILE=full` в следующем student project render. Это реальный counterexample разделению execution cache по ролям, **не доказанная утечка приватных материалов конкретного курса**. Не публиковать `_freeze`/engine caches как resources; не вводить новый общий dependency graph, cache key framework или обязательный final freeze.

## Обычные includes и другие engines

Официальная [документация includes](https://quarto.org/docs/authoring/includes.html) описывает включение как copy/paste в главный документ: computational `.qmd` использует один engine knitr либо Jupyter; относительные ссылки/изображения разрешаются относительно главного файла, YAML включения влияет на документ. `.ipynb` не computational include. Это текущая официальная документация; subset R/paths подтверждён R3 на 1.11.5. Не надо переписывать base на каталог include или строить собственный include parser.

[Документация execute info](https://quarto.org/docs/advanced/quarto-execute-info.html) даёт Native document/format context самому вычислению; фактическая реализация 1.11.5 выше подтверждает существование этого механизма. Его достаточно для format-aware авторского кода; наш новый execution context/receipt не нужен.

Для Julia/R/Python различаются доступность engine/kernel, поддержка кеша и representation output; тип готового Para/Table/Image от языка не меняется. Для OJS важно другое: browser-side выполнение и supporting JS/dependencies нельзя автоматически считать полностью вычисленным переносимым AST. Не обещать OJS→README интерактивность; не исключать OJS из обычного Native site лишь потому, что README не исполняет браузерный код. Поддержка отдельного engine и поддержка его конкретного output в каждом consumer должны называться отдельно.

Позднее создание нового `.qmd` не исправляется pre-AST: Native уже выбрал inputs. Нужный пользователю текущий шаг — вычисление **известного** документа. Если автор действительно генерирует новые исходники, он может подготовить их до обычного render; собственного дополнительного discovery runner для этого отчёта не предлагается.

## Два ограниченных следующих шага — предложения, не выполненные работы

1. **Одна согласованная Core дельта admission/currentness, без новых схем.** Развязать Native engine support и static shortcut; не отвергать engines/codeCells по самому признаку вычислений. Для mutable engine workspace прекратить считать весь owner tree неизменным источником. Сохранить существующие проверки текущих потребляемых config/project files, late AST, публичных resource bytes и output ownership. Разделить baseline авторские входы и уже существующие Native supporting outputs по Native facts, а не по новому discovery/registry. Целевой бюджет: один owner, до 3 production modules Core, до 90 минут Source; если общий refactor или новое framework требуется — остановить scope и вынести конкретную оставшуюся зависимость. Pending Core owner Source не менять параллельно.

2. **Одна новая интеграционная проба того, чего R3 не проверял.** После этой дельты и согласования root: один известный synthetic QMD вне курса, уже установленный knitr, один exercise с inline/ordinary table и одной Native PNG; существующие Core → condition → README/ZIP пути. Не повторять R3 cache/data/profile matrix. Сопоставить значение/публичный body, image URL и доступность той же figure в HTML, README inventory и ZIP; отдельный приватный блок/путь не должен попасть в выдачу. Cap 120 s всего, один запуск, retry=0, никаких installs/new engines/network и без lecture9/production data. Если Native invocation завершился, но export отказал, сохранить конкретный first failure вместо нового render. Python/Julia/OJS матрица не входит. Повторная запись старого generated файла пока остаётся Source-обоснованным остатком; отдельный второй запуск допустим только как конкретное последующее решение root, не автоматическая расширенная suite.

До такой интеграции обычные клетки поддерживаются Native Quarto, но общая composition Core/PL/Download с generated figures остаётся **UNKNOWN**, а не PASS. Измеренного ускорения от предложенных снятий гарантий нет; текущий audit не добавлял нагрузку исполнением.
