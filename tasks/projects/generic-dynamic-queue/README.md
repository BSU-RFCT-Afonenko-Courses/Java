# Преподавательская поставка exr-generic-dynamic-queue

Публична только `student/`: условие, раскрытая библиография, открытые наблюдения и минимальные явно предоставленные интерфейсы. Reference, tests, fixtures, API sources и teacher bindings закрытые и в ZIP не включаются.

Обычная teacher binding адресует конкретное решение. TaskBinding, выбранные имена constructors/getters/selection methods/reference types сверх условия не обязательный StudentAPI. Иное API требует отдельной обычной Java-привязки; private/nested остаётся manual без подавления доступа или чтения private fields. Техника/объяснение проверяются по исходнику.

```sh
python3 tasks/test-support/check-java-project.py --project tasks/projects/generic-dynamic-queue --sources tasks/projects/generic-dynamic-queue/reference --binding tasks/projects/generic-dynamic-queue/tests/bindings/reference --api-config tasks/projects/generic-dynamic-queue/tests/api/reference/config.json --platform /checkout/platform --work /новый/внешний/work
```

`--public` — открытые случаи; `--backend container` — прежний exact image966c без сети/pull. `valid-alternate` с собственной binding проходит; wrong ожидается behavior-failure. Compile/API/binding/behavior/tool failure различаются, неизвестная/неподготовленная привязка не является оценкой решения. Фиксированные опубликованные типы/операции проверяются до dependent compile; имена свободных типов в API config — только вручную выбранное размещение. Scoped native Body/manual download не является PL grading/registration или автоматической адресацией всех допустимых API.

Storage передаётся заранее заполненным legal T[]: исходная очередь всё равно пуста. Alternate наследует generic операции от отдельного QueueCore; package-private операции адресуются обычной teacher binding в том же package, без suppress-access/private fields. Constructor U.../Integer[] extra разрешены, factor роста свободен. Metadata/typed clients характеризуют только опубликованную связь T; binders вызывают обычный javac-selected constructor/метод.

