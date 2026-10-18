# Происхождение технической поставки Java

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
