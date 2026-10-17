# Преподавательская поставка: exr-j25-polymorphism-circle-subclass

Публичная часть — только `student/`. Условие и библиография находятся в `student/README.md`; source card и hashes — в `condition/`. Reference, fixtures, Java-привязки и JUnit закрытые и в student download не включаются.

Сценарии проверяют опубликованное поведение. Имена `TaskBinding`, его методы, конкретные constructor/getter/selection API reference — только преподавательская привязка выбранного решения. Они не предъявляются студенту как новые условия. `tests/bindings/valid-named` показывает иное размещение. Иное допустимое API требует другой обычной привязки; private/nested недоступное сохраняет ручную проверку. Исходник/техника и объяснение проверяются отдельно.

Запуск из корня курса с JDK25 и существующим Platform:

```sh
python3 tasks/test-support/check-java-project.py --project tasks/projects/j25-polymorphism-circle-subclass --sources tasks/projects/j25-polymorphism-circle-subclass/reference --binding tasks/projects/j25-polymorphism-circle-subclass/tests/bindings/reference --api-config tasks/projects/j25-polymorphism-circle-subclass/tests/api/reference/config.json --platform /checkout/platform --work /новый/внешний/work
```

`--public` — открытые примеры; полный запуск добавляет предметные закрытые случаи. `--backend container` — официальный локальный image966c без сети. `tests/fixtures/wrong` должен дать behavior-failure. `tests/fixtures/valid-named` проходит с одноимёнными binding/API placement. Неподготовленная или дефектная привязка даёт manual no-score, не оценку решения. Public download/native activation отдельно выполняет координатор; эти исходники не утверждают готовность production CI или Moodle.

