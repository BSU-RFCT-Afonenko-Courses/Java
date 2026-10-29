# Малый настоящий Java: состав, приёмка и границы

**Актуальный статус: student fixture ACCEPTED; measurement token RELEASED; активных workloads нет.** Числа — [performance-findings.md](performance-findings.md); raw status — локальные исследовательские материалы. Ниже сохранена история подготовки и отказов; PREPARING относился только к её началу.

Источник — фактический итог Java PR №26 с неизменёнными 25 сочетаниями установленного пакета и области. Локальная Performance использована через public executeScenario с явным manifest. Новые исходники владельцев не подмешивались. Полный Java, продуктовые исправления, Docker/Gradle qualification, server import и новая публикация курса не выполнялись.

## Capability manifest

Disposable `fixtures/minimal-java` сохраняет root + theory/tasks/lectures/practice/handbook. 1285 файлов, 13 QMD на диске: **12 renderable pages и один unused include** `_includes/semester-map.qmd`. Начальное описание «шесть entrances + семь content pages» было ошибочным и не используется как знаменатель.

| Роль | Renderable pages | Сохранённое содержание |
| --- | ---: | --- |
| Root | 1 | Композиция пяти подпроектов, cross-project links, search |
| Theory | 2 | Book index и сокращённый types; code/table/math/image |
| Tasks | 3 | Три реальных exercises numbers и lab numbers с тремя slots |
| Lectures | 2 | Standalone Reveal, vertical section, notes, Navigation |
| Practice | 2 | Подготовка numbers и speaker notes |
| Handbook | 2 | Operators, два реальных примера и полный foundations-control ZIP |

Задачи: console-greeting, image-size, interval-membership. Их три checks profiles и три materialization recipes глубоко равны выбранным исходным YAML entries. Общие public files скопированы с сохранением native origins/targets. 25 vendor scopes побайтно совпадают с baseline. Реальный teacher bank не копировался; закрытое тело обозначено только парным синтетическим solution marker.

Сокращения перечислены в `fixture-reductions.json`: остальные главы/задачи/работы удалены, восемь ссылок на отсутствующий материал заменены; mass nocite и external catalog import сняты. Bibliography files/styles сохранены, но citations/imported catalog не квалифицированы. Student/full profiles присутствуют как inputs; **новый full-profile render и student→full→student на fixture не выполнены**. Full source API, optional-primary, fixed API, aliases/retirement, большие memberships/common-origin axes и real teacher scenarios не покрыты.

`fixture-source-manifest.json` фиксирует paths/bytes/SHA256; `vendor-and-recipes.json` — сохранность vendor и выбранных рецептов. Утрата возможности явно ограничивает вывод, а не доказывает её ненужность.

## История smoke и приёмка

| Попытка | Наблюдение | Вывод |
| --- | --- | --- |
| Smoke01 | 52.33 с, exit 1: fixture ссылалась на sibling source QMD; RESOURCE.PATH_OUTSIDE_COURSE | Исправлена только fixture на published HTML links и локальный image; это не defect claim продукта |
| Smoke02 | 72.63 с, exit 1: unpaired synthetic solution, CORE.SOLUTION_PAIRING_INVALID | Marker приведён к `sol-numbers-console-greeting`; исходный failure сохранён |
| Smoke03 | Hard 120 с, exit -15; theory/tasks/lectures завершены, practice частично | Лимит исследования, без product diagnostic; полная композиция ещё не принята |
| Smoke04 | Единственное разрешённое расширение до 300 с; exit 0 за 172.645 с | Функциональная приёмка после partial outputs, **не cold benchmark** |

Принятые outputs: все 12 ожидаемых страниц, 4 resolved QRC cross-project links, 0 deferred, 17 targets; 3 starter ZIP и handbook examples ZIP. Нет broken local href/src или synthetic closed marker в HTML и распакованных ZIP entries. Проверены code/table/math/image, Reveal vertical/notes/Navigation и все три lab exercise IDs. Baseline ZIP names `exr-numbers-*-starter.zip` сохранены; новое contextual naming здесь не реализовано.

Negative control изменяет только копию outputs: добавляет marker и missing href, сохраняя все 12 страниц. Validator обнаруживает обе ошибки, complete=false / exit 1. Проверка присутствия страниц сама по себе не выдаётся за privacy/link proof.

## Selected page и сравнение harness

`scripts/measure-selected.ts` импортирует public executeScenario и не вызывает CLI prepareVariant. `fixture-selected.py` создаёт только reduced копию. N1 — unrelated index + target; N4 — index + три unrelated pages + тот же target. Source bytes target неизменны.

Перед серией отдельно скопированы принятые native index/resources и относительный CSS dependency, чтобы selected output имел корректные ссылки. Это setup, не tool wall. Direct/off используют одинаковые argv `quarto render chapters/types.qmd --profile student`, cwd/env и локальные warm caches. Выполнены excluded warmups и 5 чередующихся AB/BA пар; full target HTML во всех 10 результатах одинаков.

N4 setup — standalone пятистраничный theory render, 23.865 с outer; это подготовка, не benchmark. Один N4 selected sample — 15.687 с tool, main HTML равен N1, весь HTML отличается native sidebar/navigation. Устойчивое scaling сравнение остаётся в очереди.

Native floor удаляет custom hooks/filters и поэтому не сохраняет model/materialization/Download/QRC/Publisher гарантии. Один sample 3.345 с не является эквивалентным вариантом курса или доказанной ценой отдельного расширения.

## Preview, явный export и остановка

Preview сначала не дошёл до Quarto из-за sandbox loopback bind denial. Тот же loopback-only bounded retry прошёл automatic approval. Готовность проверена actual HTTP body, затем save-to-visible sentinel: 15.622 с. Собственный сервер остановлен, временная правка восстановлена. Это один опыт, не распределение latency.

Selected Body export после трёх native JSON документов не завершился за 60 с: exit -15, package не создан. Публичная/закрытая export projection этим опытом **не проверена**. Состояние overlay осталось в собственной disposable copy; product source не затронут. Бюджет не расширялся.

Все результаты, включая failures и ограничения, отражены в [единой очереди](next-work.md) и локальные исследовательские материалы. Java/Performance source, Git status и mtimes после опытов совпали с исходными receipts. Неисполненные branches не превращены в PASS.
