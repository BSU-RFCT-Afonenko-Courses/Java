# Протокол измерений T05

Цель — уменьшение ожидания автора и общей работы при сохранении результата. Страница, preview, composition, export, qualification, assembly и retry измеряются отдельно. Десять минут, процент и произвольный SLA не являются PASS.

База инструмента — локальный Performance `актуальная локальная Performance`, не историческая 2.0.0 matrix. Актуальные inputs, runtimes и сохранность — [T00](source-selection.md). Новые наблюдения — [performance-findings.md](performance-findings.md); manifest — [fixture-manifest.md](fixture-manifest.md).

## Existing evidence прежде повторного запуска

Cybersecurity: по три измерения на профиль; student median 278.584 → 459.900 с, full 308.518 → 487.010 с. Одновременно изменились Core 7.3→8.2.8, QRC 3.1.0→3.1.1, Publisher 5.1.1→5.1.4, Download 3.1.0→3.2.2. Порядок последовательный, новый disposable output, общие XDG/Deno caches. Есть 27 завершённых случаев, 24 Performance dataValid и 21 строгое output comparison. Это изменение композиции; виновный компонент не установлен.

Одна презентация native/off/summary/cpu: outer 19.391 / 23.152 / 30.103 / 42.008 с, по одному наблюдению. BeginNativeRun 15.286 и withFinishedNativeRun 11.734 с — включающие интервалы; складывать их с wall нельзя. Acceptance-v4 подтверждает прежнюю функциональность; acceptance-v3 overhead INCONCLUSIVE не становится новым PASS. Raw traces/CPU этой Cybersecurity matrix локально не обнаружены; точные опубликованные report и JSON summaries сохранены.

Cybersecurity измерялась на i5-8250U / около 15.37 GiB, нынешний хост — i7-8750H / около 31.2 GiB. Межмашинная разность секунд не считается эффектом кода. Близость ОС не делает аппаратуру одинаковой.

## Сравниваемые области

| База | Что сравнивается | Ограничение |
| --- | --- | --- |
| Native floor | Та же выбранная страница через Quarto без custom hooks/filters | Нет Core/materialization/QRC/Download/composition guarantees; это lower bound, не equivalence |
| Direct extensions | Неизменённая installed composition выбранного Java | Latest owner variants не подмешиваются |
| Public Performance off | Те же argv/cwd/env/cache через executeScenario | Sources/copy/install/setup/report и outer time сохраняются отдельно от tool wall |
| Summary/CPU/trace | Изолированная диагностическая конфигурация | PrepareDiagnostic/launchers/transforms/backend/data-dir имеют собственную стоимость |

GNU time CPU/RSS off включает resource helper; direct CPU/RSS не получены и не сравниваются. Hyperfine direct имеет другой resourceScope. Process RSS не равен peak одновременного дерева; Lua CPU — отдельный domain. Inclusive operation totals не являются critical path.

В выбранном Core `common-path-binding.ts:140` active отключает regular physical→lexical reuse; статический inspect budget соответствующей ветви меняется `n+4 → 2n+4`. `metadata-closure.ts:38` active отключает direct-response cache. Это изменение workload, а не чистая цена наблюдения. Active counters нельзя выдавать за off. Новых active diagnostics здесь не запускали.

## Правило опыта

Один исполнитель и один тяжёлый workload. Все requests и результаты, включая C/PL/System proposals, находятся в [одной очереди](next-work.md). Сначала capability manifest и smoke. После failure сохранить причину; не повторять дорогой маршрут автоматически. Input changes, fixture corrections и бюджетные исключения фиксируются.

Сравнимая серия: excluded warmup каждого варианта, затем пять чередующихся AB/BA пар; одинаковые source/runtime hashes, argv/cwd/env и cache policy. Записываются power/load, exit/stdout/stderr, tool wall и outer wall. Публикуются секунды, median/min/max и paired differences; bootstrap не исправляет неэквивалентный вход. Увеличение серии требует нового различающего вопроса.

Preview readiness — фактическое содержимое страницы после save, не сообщение Listening. Startup и save-to-visible разделены. Scale — одна ось за раз, target content неизменён; N1/N4/N16, memberships, origins, resources/FD не смешиваются. Новый full Java benchmark и повтор всей Cybersecurity matrix не входят в протокол.

## Приёмка outputs и данных

Сохранить исходный exit/signal и output manifest. Проверять страницы/links, semantic main HTML, ZIP names/entries/uncompressed bytes и закрытый marker. Разрешать только объяснённую owner normalization с receipt; произвольная чистка HTML может скрыть regression. Negative control validator должен обнаруживать намеренно испорченные outputs.

Для active quality дополнительно нужны согласованные run/process/executor IDs, calibration, отсутствие dropped/incomplete/error и явный scope. dataValid не равно function coverage. Ненаблюдавшийся symbol не становится ненужным. Static formulas, module purpose и runtime observation — разные уровни evidence.

Изолированные новые опыты завершены. Пять direct/off пар имеют одинаковые target bytes и смешанные знаки разности: стабильный малый overhead не доказан. Preview/native floor — одиночные наблюдения. Body export timeout оставлен незавершённым. Незакрытые scaling/equivalence/qualification вопросы остаются в очереди без скрытого PASS.
