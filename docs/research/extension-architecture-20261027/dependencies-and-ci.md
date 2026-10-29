# Зависимости, поставка и CI: T12

Исследованы фактический итог Java PR №26, Template PR №24, интеграционные файлы Cybersecurity PR №11 и manifests владельцев. Это разные course/vendor композиции. Новых install/release/CI jobs исследование не запускало.

## Разные гарантии

| Механизм | Что гарантирует | Чего не гарантирует |
| --- | --- | --- |
| Course Git с vendor | Сохранённый состав bytes конкретного курса | Восстановление из опубликованного owner, API совместимость нового producer |
| providers.json | Выбор repository/commit/tag/package/scopes | Соответствие фактических установленных bytes само по себе |
| installed-packages.json | Перечень установленных файлов, hashes и provenance | Правильность API, privacy, qualification и наличие свежего release |
| Deno/npm lock и offline vendor | Выбранные зависимости и integrity соответствующего graph | Совместимость private Core pointer либо native writer |
| Exact integration SHA | Воспроизводимая проверенная пара | Запрет любого нового совместимого producer как предметное требование |
| Runtime registry/lock/assets/OCI | Состав исполняющего окружения и content identity | Корректность решения студента или полноту сценариев банка |
| Native/delivery verification receipt | Связанный package integrity и заявленный runtime scope | Реальную Moodle-сдачу, ручную оценку или неисполненные сценарии |

Роли этих слоёв различны. Возможное сокращение дублирования должно показывать конкретного consumer и сохраняемую гарантию, а не удалять все locks по факту Git vendoring.

## Установка Java

`tools/materialize-providers.py` проверяет numbered tag/commit/sourceZip, строит Git archive, отдельно применяет допустимый tracked candidate patch, проверяет все targets перед записью и отказывает при чужой дельте. `--check` обходит каждый package/scope и сравнивает фактический inventory с receipt. Это install/CI операция, не функция render каждой страницы.

Старый `tools/sync-providers.py` выполняет native `quarto add` и заменяет vendor; при candidate отказывает. В нём и в materializer два похожих пути установки, но разный recovery и provenance. Цель упрощения — один документированный обычный путь и явно отдельная проверка native install, а не вызов legacy sync при непонятном состоянии.

Нюанс границы: materializer нормализует package files к 0644 и в своём inventory записывает этот mode; matches не сравнивает mode. Поэтому его PASS нельзя называть независимой проверкой всех исходных executable bits. T00 отдельно сопоставлял bytes/modes с provider Git. Для будущего package с требуемым executable bit контракт должен быть явным.

25 установленных комбинаций Java совпадают со своими provider commits. Свежие QRC/Download owners не установлены автоматически. Template содержит другую историческую Core7 композицию: 22 отличающихся executable files имеют отдельный variant ledger; оставшиеся provenance gaps явно учтены. Это не повод склеить Template и Java в один новый runtime.

## Фактический граф Java CI

Публичная сборка в disposable checkout выполняет student → full → student. Отдельный projects job вновь рендерит student, делает Core check, сохраняет принятую course model вне дерева, материализует public common inputs, затем запускает declared inventory. Две задачи имеют независимые checkout/output, поэтому перенос результата между ними требует bound artifact и проверки input closure.

У цикла student/full/student есть самостоятельная цель: отделение закрытого и восстановление публичного результата. У declared inventory — другая цель. Повторный student render существует; его можно исследовать как reuse только при сохранении модели, native currentness и точного provenance. Полное удаление цикла ради скорости не обосновано.

Три поздних отказа PR26 показывают более дешёвые gates: canonical/workspace membership до capture; produce/use/delete graph course.json; наличие требуемых public common inputs до inventory. Они теперь исправлены в source, но обычный preview scope остаётся отдельным вопросом. Изменение source selection не снимает duplicate guards.

Численные timeout limits 360/150 минут у jobs и 7200 секунд отдельных render — ограничения исполнения, не SLA и не доказательство приемлемой скорости. На выбранном head публичные jobs [40401766940](https://github.com/BSU-RFCT-Afonenko-Courses/Java/actions/runs/40401766940) завершены успешно; merge run при чтении ещё выполнялся. Детали: [ci-current-state.md](ci-current-state.md). Частная qualification этим CI явно не выполняется.

## Template и Cybersecurity

Template является авторским установочным примером с собственной historical vendor-композицией и install/layout/guide/navigation tests. Его package-copy различия учитываются как отдельный variant, а не как новый Core defect. Версия README и точный Git source не заменяют installed compatibility.

Cybersecurity selected PR11 содержит отдельные public source/access-control tests, student/full/student цикл, selected Body exports и проверку отделения решений. Его check-model.sh требует готовый QRC catalog, затем выполняет inventory и два selected exports. Это другой scope, без Java project qualification. Получены 31 first-party code/config файла с проверенными Git blobs и полный remote manifest; отсутствующие content/vendor files перечислены, полной локальной course copy нет. Новая матрица Cybersecurity не повторялась.

## H45–H49 и B03/B04/B07/B09/B10/B13/B15

Подтверждены ручное согласование нескольких pins/descriptor/docs/test literals, status drift UPSTREAM/PR, повторные native этапы CI и исторические поздние дешёвые отказы. Но одинаковое поле SHA в разных местах может связывать разные объекты. Рекомендация — генерировать статусные сведения из одного source manifest и проверять API отдельно от exact fixture pin.

Для каждого повторного этапа будущий reuse обязан назвать неизменный input closure, owner результата, срок жизни, поддерживаемые mutations и проверку до публикации. Не предлагается постоянный общий cache либо новый dependency resolver по умолчанию. Скорость и экономия полного CI пока не измерены; источники стоимости и различающие опыты представлены в единой очереди.
