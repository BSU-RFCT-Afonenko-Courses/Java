# Преподавательская поставка exr-generic-interface

Публична только `student/`: условие, раскрытая библиография, открытые наблюдения и минимальные явно предоставленные интерфейсы. Reference, tests, fixtures, API sources и teacher bindings закрытые и в ZIP не включаются.

Обычная teacher binding адресует конкретное решение. TaskBinding, выбранные имена constructors/getters/selection methods/reference types сверх условия не обязательный StudentAPI. Иное API требует отдельной обычной Java-привязки; private/nested остаётся manual без подавления доступа или чтения private fields. Техника/объяснение проверяются по исходнику.

```sh
python3 tasks/test-support/check-java-project.py --project tasks/projects/generic-interface --sources tasks/projects/generic-interface/reference --binding tasks/projects/generic-interface/tests/bindings/reference --api-config tasks/projects/generic-interface/tests/api/reference/config.json --platform /checkout/platform --work /новый/внешний/work
```

`--public` — открытые случаи; `--backend container` — прежний exact image966c без сети/pull. `valid-alternate` с собственной binding проходит; wrong ожидается student-api-mismatch. Compile/API/binding/behavior/tool failure различаются, неизвестная/неподготовленная привязка не является оценкой решения. Фиксированные опубликованные типы/операции проверяются до dependent compile; имена свободных типов в API config — только вручную выбранное размещение. Scoped native Body/manual download не является PL grading/registration или автоматической адресацией всех допустимых API.

IGenIF сохраняет объявленный bound и проверяется typed clients. Текущая выбранная привязка MyClass связывает два generic аргумента по фактической metadata, не по именам и не навязывает constructor. Другие arity/private/local размещения дают явный manual/no-score предел инструмента; ошибка студента этим не устанавливается. Смысл допустимости дополнительных class typeparams передан координатору/автору отдельно.

