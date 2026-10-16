# Преподавательская поставка exr-generic-stack

Публичен только `student/`: условие, раскрытая библиография, открытые сценарии и свободное размещение исходников. `reference`, fixtures, bindings и JUnit закрытые и в download не входят. Конкретные teacher классы/методы reference не являются новым StudentAPI. Иная структура требует другой обычной Java-привязки; private/nested и техника/объяснение остаются manual.

```sh
python3 tasks/test-support/check-java-project.py --project tasks/projects/generic-stack --sources tasks/projects/generic-stack/reference --binding tasks/projects/generic-stack/tests/bindings/reference --api-config tasks/projects/generic-stack/tests/api/reference/config.json --platform /checkout/platform --work /новый/внешний/work
```

`--public` включает открытые примеры; `--backend container` использует существующий local image966c. `valid-alternate` с одноимённой binding проходит; wrong — предметная ошибка behavior. Вариант maximum использует другую разрешённую Comparable bound, hash — постоянный согласованный hash, polynomial удаляет нули, set results неизменяемые. Эти варианты не задают требования другим решениям. У GenericStack альтернативный API унаследован через interface, checked exceptions через промежуточный base и имеют свободные аргументы constructors; generic constructor связан с T.

Для свободных API автоматическая metadata-привязка не обещается. Ошибка компиляции выбранного teacher adapter/типизированных клиентов — manual binding-setup-failure/no-score, не неверное поведение студента. У фиксированного IGenStack отдельно задан published API precheck до dependent compilation. Native Body/manual download и конкретные host/container behavior facts — отдельные scoped доказательства, не production PL/Moodle registration или приёмка любого API.


Reference и alternate teacher bindings передают legal заранее заполненные T[]; первое pop проверяет исходный empty независимо от contents массива. Все слоты и последующее reuse проверяются обычными push/pop без чтения private fields или массива после передачи. Wrong initial-count принимает прежние non-null ячейки за содержимое и должен дать behavior-failure, не API failure.

Адресное закрытие P2 Stack: отдельные ordinary interface clients push→catch Full и pop→catch Empty должны компилироваться; unhandledPush и unhandledPop не должны. Это published checked-вызов через IGenStack, не exact throws реализации; inherited/checked exception bases/generic push/constructors/overloads остаются прежними. wrong-pop-throws удаляет throws Empty из interface, сохраняя runtime sneaky refusal; теперь отвергается API до dependent compile.
