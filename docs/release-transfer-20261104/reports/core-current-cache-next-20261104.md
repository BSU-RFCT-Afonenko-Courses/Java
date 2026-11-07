# Core4a: следующий адресный опыт с native knitr cache

4 ноября 2026. Source-only уточнение прежнего cache-отчёта для принятого Core
`4a2903e6d03d1dd489b6922d113100826913cf67`. Не новый аудит R0–R6 и не
результат исполнения кеша. Source продуктов, реальные кеши и курсы не менялись.

## Что изменилось относительно прежнего вывода

В [cache boundary](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/quarto-cache-boundary-20261104.md) гипотеза отказа при записи
`<input>_cache` относилась к Core46b и whole-owner currentness. На Core4a все
прочитанные production вызовы `assertSources` в operation и assignment-admission
передают `full=false` и выбранные inputs. В `runtime/source-snapshot.ts:155–236`
эта ветка проверяет выбранные file signatures и physical ancestors, затем
возвращается **до** сравнения directory membership. Добавление или изменение
постороннего cache-файла само по себе больше не является whole-owner отказом.
Это Source-вывод, не измеренный cache PASS.

Initial `snapshot` всё ещё обходит и хеширует owner. Его exclusions не объявляют
любой `*_cache` особым доверенным входом. После Native child A1 `copyOwner`
(`infrastructure/project-context.ts:140–183`) копирует текущий owner, включая
неисключённые cache-файлы. Короткие A1 checks сохраняют стабильность этой копии
на время потребления. Исполнение engine происходит до A1 acquisition, поэтому
обычная последовательная запись кеша и последующее чтение проекта не противоречат
этому lifecycle по Source. Параллельная запись во время A1 остаётся иной задачей.
Начальные hash/copy расходы не исчезли и ещё не объявляются необходимыми.

## Один вопрос следующего опыта

Может ли тот же synthetic owner дважды пройти actual capture → late condition →
README/resource inventory → Download, когда первая ячейка создаёт штатный knitr
cache, а второй global render использует его без повторного исполнения ячейки?

Переиспользовать [существующий computed probe](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-native-computed-probe.ts) и
его staging/Performance wrapper. Никакого нового runner, cache key, proof,
parser/schema или собственного engine. Новый disposable stage; прежний evidence
не перезаписывать. Whole-package Core4a, принятый Download и Performancee726;
исходные owner/profiles/projects/role checks сохраняются.

Единственное изменение fixture: Native `execute: {freeze: false, cache: true}`
для R и счётчик фактического исполнения **вне owner**, в этом же disposable stage.
Тот же authored QMD и cache path используются в обоих последовательных captures.
Первый capture должен записать cache и ledger1; второй должен оставить ledger1.
Оба результата проходят прежний поздний consumer с Table/Image/inline, README и
существующим byte packaging. Дополнительный общий byte certificate не вводится.
Stage и artifacts после каждого этапа сохраняются сразу, даже при отказе второго.

Не проверять R3 заново: data/include/profile/freeze freshness уже исследованы.
Не менять audience по ходу этого опыта и не обещать автоматическую изоляцию
немаркированного текста между профилями. Jupyter cache/другие kernels не включать.

Потолок: **два Native captures, общий outer cap60s вместе с preparation и grace,
retry0**. Первый действительный отказ прекращает опыт; никакого третьего render,
очистки кеша, установки пакетов или адаптации ради зелёного результата. Запуск
только после завершения текущего full128 trace, чтобы не смешивать нагрузку.
Факт ledger1, границы capture и поздний consumer — основное evidence; это не
benchmark cache speedup. Inclusive command timings не складывать.

Root прочитал текущие exact Source участки, прежний cache-отчёт и существующие
computed drivers. Новых Native/inspect/hash/compiler запусков не было. Подготовка
и исполнение описанного опыта ещё не начаты; текущие роли и очередь остаются в
единственном `java/docs/release-cycle-20261110.md`.
