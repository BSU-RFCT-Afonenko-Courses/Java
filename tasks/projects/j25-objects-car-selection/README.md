# Преподавательская поставка: exr-j25-objects-car-selection

Публичная часть — только `student/`. Условие и библиография находятся в `student/README.md`; source card и hashes — в `condition/`. Reference, fixtures, Java-привязки и JUnit закрытые и в student download не включаются.

Сценарии проверяют опубликованное поведение. Имена `TaskBinding`, его методы, конкретные constructor/getter/selection API reference — только преподавательская привязка выбранного решения. Они не предъявляются студенту как новые условия. `tests/bindings/valid-named` показывает иное размещение. Иное допустимое API требует другой обычной привязки; private/nested недоступное сохраняет ручную проверку. Исходник/техника и объяснение проверяются отдельно.

Запуск из корня курса с JDK25 и существующим Platform:

```sh
python3 tasks/test-support/check-java-project.py --project tasks/projects/j25-objects-car-selection --sources tasks/projects/j25-objects-car-selection/reference --binding tasks/projects/j25-objects-car-selection/tests/bindings/reference --api-config tasks/projects/j25-objects-car-selection/tests/api/reference/config.json --platform /checkout/platform --work /новый/внешний/work
```

`--public` — открытые примеры; полный запуск добавляет предметные закрытые случаи. `--backend container` — официальный локальный image966c без сети. `tests/fixtures/wrong` должен дать behavior-failure. `tests/fixtures/valid-named` проходит с одноимёнными binding/API placement. Неподготовленная или дефектная привязка даёт manual no-score, не оценку решения. Public download/native activation отдельно выполняет координатор; эти исходники не утверждают готовность production CI или Moodle.


Регрессии R11/R12: `valid-copied-values` проходит без student equals/identity; `wrong-mutates-input-array` отвергается на том же typed массиве, переданном селекторам. TaskBinding.array — только teacher helper. Каждая public/private selector assertion проверяет состав по всем наблюдаемым свойствам, порядок, кратность и сохранность входа после вызова.
