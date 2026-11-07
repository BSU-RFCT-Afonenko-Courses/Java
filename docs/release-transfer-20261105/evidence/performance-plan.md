# Performance: замыкание собственных доказательств

Основа: `7a1645936924ba0dd710c9121a8871a13d67a84b`; единственный пакет A, отдельное дерево `performance`. Согласованный объём: убрать собственные digest/freshness DTO и их вычисление, не менять ABI метрик и реальные фазы/процессную диагностику.

1. Различающие проверки функциональной подготовки и сравнения bytes при недоступном crypto.digest; исходный RED.
2. Удалить digest helpers и собственную инвентаризацию расширений; оставить функциональные file/copy helpers, явные source/replacement inputs, Git identity и наблюдения реальных событий. Данные подготовленных скриптов содержат только фактические пути, без PreparationReceipt.
3. Замкнуть producers → types/readers/tools/tests. Исторические архивы не переписывать; их текущий импорт не создаёт новые SHA. Native Arch checksum остаётся только штатной упаковкой, если действительно требуется PKGBUILD.
4. Проверить затронутую реальную TS-графу и адресные regression suites; независимая Sol/high рецензия точного коммита; публикация отдельной ветки/PR с сохранением PR6.

## Стоимость исходной цепочки до изменения

Расчёт по исходникам, без нового полного рендера. `probeQuarto` читает целиком выбранные quarto, quarto.js, Deno, Pandoc и при наличии основной filter — 4–5 файлов runtime. `preparePandocData` добавляет digest исходного init и подготовленного init (и дополнительный digest пустых bytes перед ними). Обычный summary/cpu вызывает обе функции через renderCommand → prepareRenderDiagnostic; off обходит подготовку. Это обязательная часть удаления, не только experiment.

Experiment дополнительно: freeze сначала probe, затем для каждого из трёх вариантов verifySources повторно читает каждый runtime/registry; prepareVariant ещё раз проверяет runtime. installPackages делает полный treeHash каждого выбранного source пакета перед реальным копированием. prepareDiagnostic повторяет probe/init, читает registry для hash и JSON, дважды читает каждый instrumented module для hashes и читает содержимое для transform. collectPackageCoverage обходит всё выбранное дерево расширений, читает TS/Lua для token scan, повторно читает canonical bridge/import targets и evidence files для SHA. Сравнение outputs читает оба выбранных файла для digest; прямое сравнение bytes остаётся функциональным. Historical metadata создаёт дополнительные SHA при импорте отчётов. Эти чтения различны и не исчезают от исправления одного helper.

Не измерялся полный курс; коэффициент ускорения не заявляется. Проверки ниже измеряют только изменённые локальные пути и отсутствие обращения к digest, не стоимость сборки курса.
