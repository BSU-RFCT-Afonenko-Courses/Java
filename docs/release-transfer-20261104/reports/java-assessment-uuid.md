# UUID пяти переименованных лабораторных

Необязательное native поле PL сохраняет прежнюю source identity. Значения вычислены тем же UUIDv5 алгоритмом из course.id `java-semester-one` и прежнего assessment.id, прочитанного непосредственно из base `dd5b76a0168133f0760ece6cb21f4bdf4f6203bb`. Одноразовое сравнение всех пяти новых значений с прежней генерацией прошло; course UUID `036dee22-0705-5c6d-abd5-30620afcce9b`.

| Файл | Новый id | Сохранённый UUID |
| --- | --- | --- |
| tasks/labs/objects.qmd | sec-lab-objects | `b61a155f-7ea3-5dfa-b301-72a4656ace98` |
| tasks/labs/polymorphism.qmd | sec-lab-polymorphism | `5f70c5f6-4f87-5f4e-ba0c-e8fdb6670ab6` |
| tasks/labs/collections.qmd | sec-lab-collections | `eb9f17fb-4f17-5631-b3b5-4cbebb4c4bb3` |
| tasks/labs/pipelines.qmd | sec-lab-pipelines | `cf890a50-0e8e-53b6-871b-65989cf5219a` |
| tasks/labs/files.qmd | sec-lab-files | `864cfffa-5e75-5872-9bb7-9c378b6e7fdd` |

Это сохранность относительно исходного Source, не утверждение о фактической БД/установленном курсе. Требуются готовый PL optional uuid contract, его установка в курс и native интеграция; пока поле не поддерживается установленным PL9.0.7, ветка не готова к публикации. Реальные результаты/UUID в БД не изменялись.
