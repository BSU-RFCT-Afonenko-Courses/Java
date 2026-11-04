# Функциональная рецензия JUnit XML и исправления — 4 ноября

Срез 17:49 Europe/Minsk. Независимый `/root/junit_xml_functional_review`, Sol6.1/high dispatch, read-only. Настройки dispatch не являются attestation скрытого runtime. База Platform `6491517b896b17119fd2ab44db1a2de2ff9c93b1`, dirty candidate сохранён без commit.

Область: обычный XML producer/listener, reader, leaf cases, веса и полезная обратная связь. Security authentication/module/reflection/process boundaries не рецензировались и прежние остановленные проверки не повторялись. Рецензент читал прежний upstream producer для сравнения обычной формулы и существующий JUnit jar через `javap`: 0.531с анализа. Tests/build/network сам не запускал.

## Два подтверждённых исправления

1. Reader терял explicit `maxpoints` класса без собственных leaf cases. Прежний upstream создавал classTotals для всех ClassSource, включая outer class с nested tests. Теперь class maximum применяется независимо от наличия прямого leaf owner в totals. Регрессия с normal leaf1 и отдельным class maximum3 должна давать max_points4 и score0.25. До исправления фактически получалось1 и1; RED `AssertionError:1 !=4`,0.003с. После исправления5pure tests PASS0.006с. Учебные веса восстановлены к прежней формуле, не выбраны заново.
2. Штатный Legacy XML включает успешный childless non-test node: пустую `@TestFactory` и пустой `DynamicContainer`. Listener сохранял только leaf feedback, reader отклонял такой завершённый запуск как infrastructure failure. Теперь listener сохраняет IDs фактически успешных non-test контейнеров в обычном feedback-массиве `successfulContainers`; reader исключает только явно названные успешные контейнеры из leaf outcomes/counts. Неизвестная successful строка XML по-прежнему отклоняется; aborted/skipped/failed не выдаются за успешные leaf tests. Криптография и механизмы auth/module/process не менялись.

## Проверки координатора и сохранённые неудачи

Все данные вымышленные, настоящий установленный JDK25. Ни курсы, ни image не собирались.

- `runtime-junit-class-maximum-red-20261104.log`: RED1test0.003с,1!=4.
- `runtime-junit-class-maximum-green-20261104.log`:5PASS0.006с.
- `runtime-junit-empty-red-20261104.log`: настоящий grader + обычный correct42 test + empty factory + empty DynamicContainer. RED1test2.960с, `Invalid JUnit XML report: JUnit XML and feedback differ`.
- `runtime-junit-empty-green-20261104.log`: первая исправленная попытка **не PASS**,2.961с, JSONDecodeError: json-simple не сериализует Java Set как массив. Исправлен producer на `new ArrayList<>(...)`; этот промежуточный исход сохранён.
- `runtime-junit-empty-corrected-20261104.log`: после material serialization correction1PASS2.931с,score1,executed1,passed1.
- `runtime-junit-reader-final-20261104.log`:6pure reader tests PASS0.006с, включая explicit container metadata requirement и сохранённую формулу maxpoints.
- `git diff --check`:exit0 после исправлений.

До новой существенной дельты записан отдельный cap12с: фактические Java calls2.960+2.961+2.931=8.852с, плюс final pure6tests0.006с —8.858с. Ошибочная первая исправленная попытка не скрыта; дополнительный вызов сделан только после установленной причины и изменения сериализации, внутри прежнего остатка cap12. Старый cap120 не сброшен: прежний учёт~111.4с + изменённый Gradle smoke6.859с + pure maxpoints RED/GREEN0.009с = ~118.268с. Суммарно оба бюджета~127.126с, плюс0.531с отдельного read-only javap анализа; это не новая Performance квалификация.

Независимый reviewer повторно прочитал обе correction delta: **оба functional finding закрыты, новых findings в ограниченном scope нет**. Результаты запусков предоставлены координатором; reviewer не объявлял собственный runtime PASS. Предположение о потере ABORTED container снято после чтения штатной XML serialization: skipped ancestors переходят в rows и неполный результат отклоняется.

Полного одобрения Platform нет. Новый image и CI Temurin не квалифицированы. Системный отказ прежнего полного review `possible cybersecurity risk` сохраняется; текущая функциональная рецензия не выдаётся за его завершение. Commit/push/publication/deploy/production/VM/live DB не выполнялись.
