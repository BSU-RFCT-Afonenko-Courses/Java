# Происхождение технической поставки Java

## Подготовленная техническая поставка 8.2.7/8.2.5/3.2.1

Core 8.2.7, Exporter 8.2.5, Site 5.1.3, QRC 3.1.0 и Download 3.2.1
закреплены точными версиями и коммитами исходников. Факт удалённой публикации
и готовность курса подтверждаются отдельными внешними доказательствами.
Точные теги, коммиты и sourceZip закреплены в `providers.json`;
области установки и хеши генерирует штатный `tools/materialize-providers.py`.
Активные candidate blocks не используются.

Core 8.2.7 опубликован с неизменяемым тегом и проверенным публичным sourceZip.
Он сохраняет CUE-проверку наличия хотя бы одного input/state/action в PublicCase
и проверяет актуальность native-операции на границах BEGIN/END вместо повторного
полного чтения owner на каждом документе. Проверки выбранного документа,
восстановления source и итогового закрытого набора сохраняются. Ветка Performance
не интегрировалась; Site, QRC и Download сохранены на прежних версиях. Полная
CI и новая фактическая квалификация курса подтверждаются отдельно.

| Владелец | Выпуск | Коммит |
| --- | --- | --- |
| Afonenko-Course-Tools/quarto-course | v8.2.7 | `069251845a1803f1993ff2b09b30a2e0e275b04a` |
| Afonenko-Course-Tools/quarto-project-publish | v5.1.3 | `425f042d5a5e07e57a28bb40b3c46a5ebdd17460` |
| Afonenko-Course-Tools/quarto-reference-catalog | v3.1.0 | `3df2110e9f525f03f3ecb4926b5d55718e955c4c` |
| Afonenko-Course-Tools/quarto-course-prairielearn | v8.2.5 | `12396ac13349421f0bc820c55db88f1f58bc39d3` |
| Afonenko-Course-Tools/quarto-project-download | v3.2.1 | `498b1d89c159548c0eb1d706438178be16ddb082` |

Поставка восстанавливается из проверенных локальных Git checkout без сетевых загрузок:

```sh
python3 tools/materialize-providers.py --providers-root /absolute/release-checkouts --write
python3 tools/materialize-providers.py --check
```

Области установки составляют 18 для Core/Exporter/Download и 25 вместе
с Site/QRC. Корневая копия project-download предоставляет ZIP-модуль полной
композиции Site; прежняя копия в tasks сохранена. Другие области не изменены.
Три прежние поправки и manifests сохранены в истории Git.

Exporter 8.2.5 опубликован с проверенным sourceZip. Его Lua consumer принимает
`commonPathDocuments` актуального owned pointer Core 8.2.7; direct starter не
публикует raw `.git`, а ZIP сохраняет исходную Git-историю. Свежие capture, M
и экспорт курса на этом source commit ещё не выполнены.

Exporter использует delivery v3 с Core; Body package остаётся v2.
Подключение расширений не меняет Platform/Gateway, сервисные OCI pins или production.
Runtime builder и образы остаются опубликованной поставкой Platform 2.3.0.
Готовность capture, архивов, экспорта, импорта и пользовательского интерфейса
подтверждается отдельно. Учебные условия и оценивание этой поставкой не изменены.

## История прежней статической поставки

Следующие сведения относятся к прежнему выпуску и его фактическим проверкам.
Они сохранены как история и не описывают новую готовность курса.

Поставка от 18 октября 2026 года использует точные опубликованные версии.
Активных локальных кандидатов и поправок в установленных пакетах нет.

| Владелец | Выпуск | Коммит |
| --- | --- | --- |
| Afonenko-Course-Tools/quarto-course | v7.3.0 | `e93bc3aa5c5f747e647d42ed6c0f65c127920184` |
| Afonenko-Course-Tools/quarto-project-publish | v5.1.1 | `4a1d7d19f5a232d8ff91ea91a02097e0acf7d9c0` |
| Afonenko-Course-Tools/quarto-reference-catalog | v3.1.0 | `3df2110e9f525f03f3ecb4926b5d55718e955c4c` |
| Afonenko-Course-Tools/quarto-course-prairielearn | v7.1.1 | `aac4ef06d70b3692fc4dcaa79ad9c525dc1bc461` |
| Afonenko-Course-Tools/quarto-project-download | v3.1.0 | `73797d3807e2433d33751d538746e1e9d82d6d57` |

`providers.json` закрепляет тег, версию, коммит и архив владельца.
`installed-packages.json` содержит точные области установки и хеши файлов.
Установленные копии пересоздаются штатным материализатором:

```sh
python3 tools/materialize-providers.py --write --providers-root /path/to/providers
python3 tools/materialize-providers.py --check
python3 CI/check-production-pins.py
```

Platform 2.2.0 и опубликованные OCI digest сохранены в `CI/toolchain.json`.
Набор PrairieLearn — `2026-2027-s1`. Действующие Moodle/PrairieLearn этим
выпуском сайта не изменяются.

Старые манифесты и поправки сохранены в `docs/provider-history/` и Git.
Они описывают историю, не активную поставку. Новые версии Core 7.3.0,
Publisher 5.1.1 и exporter 7.1.1 включают согласованные исправления Java.

Для быстрого выпуска выполнены адресные проверки механизмов и целостности,
одна окончательная сборка student. Полная проверка курса, контейнеров
и production grading в этот выпуск не входит.
