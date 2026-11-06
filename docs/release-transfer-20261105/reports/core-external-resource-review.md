# Независимый review внешних citation inputs Core

**Готово к интеграции: да, в рассмотренном объёме. Открытых замечаний нет.** Базовый verdict для `e8a9892` в `core-review.md` сохраняется; этот report оценивает только новую конкретную интеграционную дельту.

Точный диапазон: `e8a989222266ede20fc037a1fe3e6f66efcaceb9..f8ea942ba72c6bf0093862c72ea3f6620eec71ec`, 7 файлов. Checkout `/tmp/java-release-work-20261105/core`, ветка `codex/core-yaml-20261105`. Во время review 4 отдельных документа имели незакоммиченные редакторские изменения координатора; они не входят в данный verdict и не затрагивают frozen functional code. Рецензент source, fixture, index, HEAD не менял.

Рецензент `/root/core_review`, собственный chat `01a1a8a1-ab5c-7620-8830-6eb20fed81e5`, **исходный ход сохранён**: `01a1a8a1-ab93-7852-9946-77d0cb6ffb64`. Возобновление и финальная проверка всего исходного интервала: 2 `turn_context`, верхнеуровневые и вложенные `gpt-6.1-sol/high` совпадают; Standard. Свидетельство [core-review-admission.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261105/reports/core-review-admission.json), секция `externalFinalAdmission`. Субагенты не запускались.

## Сильные стороны

- Исправлена точная причина реального Java one-lab отказа: Quarto корректно пересчитывает явно объявленные bibliography/CSL за пределы owner, а прежний общий Body resource guard запрещал их до citeproc. Исключение теперь относится только к `bibliography`, `csl`, `citation-abbreviations`.
- Новый внутренний async guard проверяет **точный документ, ключ и исходную строку пути** по actual captured `assignment.citationMetadata`. Несовпадение документа/ключа/пути не открывает произвольный внешний файл.
- После разрешения пути проверяются lexical private roles, служебные/generated области, stable tool cache и фактический абсолютный configured XDG cache вне owner. `regular("/", ...)` проверяет canonical regular path без symlink-компонентов; `Deno.open`/`file.stat` передают **identity открытого файла** в существующий private-role predicate. Это закрывает внешний hardlink к известному закрытому source без нового snapshot/SHA/freshness слоя.
- Общее чтение Body resources не ослаблено: `collect`, `publicResource`, `readResource`, selected native-resource membership и owner-contained targets сохраняют прежние границы. Внешняя bibliography читается native citeproc и не копируется целиком как новый публичный resource.
- Публичный API/DTO не изменён. Все изменённые внутренние call sites и тесты используют типизированный async callback; схемы, Native metadata и обычный Pandoc citation pipeline сохраняются.

## Замечания

### Критические

Нет.

### Важные

Нет.

### Незначительные

Нет.

## Проверки

Рецензент прочитал весь exact delta: `body-export/assignment.ts`, новый `citation-inputs.ts`, binding в `runtime/operation.ts`, оба теста и две документальные вставки. `git diff --check e8a9892..f8ea942` PASS.

Независимые запуски на frozen функциональном коде:

- `tests/assignment-external-citations.ts` — exit 0. Настоящие preparation/capture/render nested QMD и assignment delivery с внешними bibliography+CSL проходят; выбранная запись появляется в публичном результате, неиспользованная не появляется. Тот же regression проверяет 7 отказов: неизвестный путь, неправильный ключ, private hardlink, symlink, внешний configured cache, stable tool cache, private path. Лог: `core-external-review-native.log`.
- Настоящий affected compiler `public.ts`, `capture.ts`, `tests/assignment-body.ts`, `tests/assignment-external-citations.ts` — exit 0, 4 Check rows. Использован actual Quarto Deno cache/import map и `--node-modules-dir=none`, без casts/stubs. Лог: `core-external-review-types.log`.

Также просмотрены исполнительские `core-external-resource.json`, `core-external-body.log` и `core-external-final-types.log`: исходный RED после реального render соответствует диагностике one-lab; GREEN и прежняя assembly/citation-scoping regression подтверждены. Исполнительский результат не выдаётся за отдельный независимый запуск всей программы.

## Отказ от суждения (Declined to judge)

- Финальная повторная интеграционная выдача реального Java one-lab потребителем PL относится к владельцу B; этот report подтверждает исправленный Core boundary и новый настоящий native regression, но не объявляет готовым ещё не завершённый внешний consumer run.
- Full course, UI, Docker, production/VM/deploy, скорость всего курса и baseline suites не входят в эту конкретную дельту и не запускались повторно.
- Абсолютная защита от конкурентной замены файла между проверкой и последующим чтением native citeproc не объявляется: source freshness/immutability исключены принятой архитектурой. Reviewed checks обеспечивают точный native declaration и actual opened-file private identity на границе guard без восстановления собственных fences.
- Гипотетическая переносимость на Windows и переопределение политики имён приватных каталогов не расширяются в этом Linux/course пакете.

## Оценка

**Готово к интеграции: да.** `f8ea942` вводит узкое разрешение именно native citation inputs, устраняет подтверждённый отказ и сохраняет запрет произвольных внешних Body resources/private aliases. Независимые native regression и compiler PASS; новых блокеров для этой дельты не обнаружено.
