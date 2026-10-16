# Преподавательская поставка exr-timetable-interfaces-project

Публична только `student/`: условие, раскрытая библиография, открытые наблюдения и минимальные явно предоставленные интерфейсы. Reference, tests, fixtures, API sources и teacher bindings закрытые и в ZIP не включаются.

Обычная teacher binding адресует конкретное решение. TaskBinding, выбранные имена constructors/getters/selection methods/reference types сверх условия не обязательный StudentAPI. Иное API требует отдельной обычной Java-привязки; private/nested остаётся manual без подавления доступа или чтения private fields. Техника/объяснение проверяются по исходнику.

```sh
python3 tasks/test-support/check-java-project.py --project tasks/projects/timetable-interfaces-project --sources tasks/projects/timetable-interfaces-project/reference --binding tasks/projects/timetable-interfaces-project/tests/bindings/reference --platform /checkout/platform --work /новый/внешний/work
```

`--public` — открытые случаи; `--backend container` — прежний exact image966c без сети/pull. `valid-alternate` с собственной binding проходит; wrong ожидается behavior-failure. Compile/API/binding/behavior/tool failure различаются, неизвестная/неподготовленная привязка не является оценкой решения. Фиксированные опубликованные типы/операции проверяются до dependent compile; имена свободных типов в API config — только вручную выбранное размещение. Scoped native Body/manual download не является PL grading/registration или автоматической адресацией всех допустимых API.

Выбранная policy reference: удаление связанных subject/teacher каскадно удаляет занятия; alternate отклоняет такие удаления с сохранением состояния. Выходной отменяет занятия даты и блокирует новые на неё. School constructor задаёт собственный лимит2 для этих fixtures; это не предел для студента. Driving хранит ID учебных машин и проверяет практику. Teacher binding должна соответствовать **объявленной конкретным студентом** policy/лимиту/модели машин; constants deletionPolicy/schoolLimit не StudentAPI. Для иной законной policy/сигнала отказа нужен обычный адаптированный Java-клиент и соответствующие наблюдения; автоматическая универсальная оценка policy не заявляется.

