# Источники установленных расширений

Пакеты устанавливаются целиком штатным `quarto add`. `providers.json` закрепляет опубликованные номерные tag/version, точные source commits, URL и области установки; `installed-packages.json` — полный состав, SHA256, размеры и фактические права извлечённых файлов. Сам ZIP inventory не доказывает Git executable modes. Реализация установленных `_extensions` вручную не меняется.

| Пакеты | Выпуск | Точный исходный commit |
| --- | --- | --- |
| Core / Presentation / Navigation | v7.1.0 | 04a6295569f5dcc0fc1b991dc076bafc050dbd49 |
| Download | v3.1.0 | 73797d3807e2433d33751d538746e1e9d82d6d57 |
| PrairieLearn exporter | v7.0.0 | a13008aaf23e0616eac4b18f1dabbf77a77b56f3 |
| Course Site | v5.1.0 | 62f8c4a01a5fd9af11f5f66abba2bb48c2ef2631 |
| Reference Catalog | v3.1.0 | 3df2110e9f525f03f3ecb4926b5d55718e955c4c |

[Core 7.1.0](https://github.com/Afonenko-Course-Tools/quarto-course/releases/tag/v7.1.0) разрешает отсутствие оценки времени банковской задачи. Заданное время остаётся положительным целым числом; при любом кандидате без оценки вся сводка времени отсутствует, включая необязательные позиции. Это опубликованное поведение владельца. [Поправка пилота Core 6](tools/provider-patches/core-optional-time/README.md) сохранена как история и больше не участвует в установке.

Core, Presentation и QRC устанавливаются в корне и пяти учебных частях. Course Site находится в корне; Download и exporter — в tasks; Navigation — в lectures, practice и tasks. Всего 24 экземпляра семи пакетов. Подготовьте owner repositories с соответствующими tags из `providers.json`, затем выполните:

```sh
python3 tools/sync-providers.py --providers-root /absolute/provider/repositories
```

Сценарий проверяет tag→SHA, version в descriptor и source URL до установки. Затем создаёт локальный архив из точного commit, вызывает `quarto add` и переносит полные пакеты, сверяя их состав и bytes. Содержимое текущих рабочих веток owner repositories не используется. Повторная установка должна сохранять пакетные файлы и inventory без дельты.

Java использует `--release 25` без preview, официальный JUnit Console 1.14.1/Jupiter 5.14.1 и Platform runner. Профили java25-junit-v1/java25-mutation-v1 относятся к образу Java, fish-git-v1 — к отдельному образу fish. Ошибки компиляции и решения отделены от сбоев инфраструктуры. Полная контейнерная проверка должна охватывать всю выбранную поставку.

Platform v2.1.0 закреплён на `e401ee9c6fa9c1725ccbbb7ec5b614ab0988d0e1`. Запуск публикации 38721296402 собирал образы из `901cda93ce847e6a29386277b07b71a39215b86d`; итоговый tag включает проверенные фактические pins. Эти commits различаются утверждёнными метаданными и документацией; исходник сборки не выдаётся за финальный tag. Runner SHA256: `48f29c813ee923d50062774034f2c530da6a49588d2f891d51c4c9d9cc76f63b`.

| Среда исполнения | Опубликованный digest |
| --- | --- |
| java | `ghcr.io/afonenko-course-tools/java25-grader@sha256:143e2023d1e6ea1c23950d2a6804becc6225abc1806d1be921b14206e14fa02d` |
| fish | `ghcr.io/afonenko-course-tools/fish-git-grader@sha256:fa4f2bf5c4034e269323d58362dafd26e5b0eaf6796f49a66e68e43fdca0dc94` |
| community | `ghcr.io/afonenko-course-tools/prairielearn-community-gateway@sha256:70f9202c4e0a4592d221b5bc32fbc18b8265d8a586cbbf4f50ee7211336a2ee2` |

Доступный совместимый Gateway v2.0.3: source `ab61d24687102a50042574790ea6bba5f8b42eb3`, образ `ghcr.io/afonenko-course-tools/moodle-prairielearn-gateway@sha256:5c498a69a4fb37fb5ba3e468a6fd9689a685c31e2b485e0c5ba1691f1935dc64`. Схема БД — 3; configuration, delivery и results — v2. Deployment курса этой редакцией не обновлялся.

OCI digests и actual publication evidence закреплены в `CI/toolchain.json`. Старые сведения Platform2.0.0 и прежний CI waiver сохранены отдельно как historical evidence. Проверка текущей редакции Java в Actions имеет статус pending: успешные owner публикация и локальные pull не доказывают cross-organization GHCR доступ из курса. CI использует встроенный `GITHUB_TOKEN` и `packages: read`; фактический pull проверяется отдельно. Рендер, новая выбранная поставка и контейнерная приёмка курса ожидают содержательного freeze и проверок. Установка пакетов не является приёмкой курса. Toolchain: Quarto 1.11.5, CUE 0.17.1, Java 25. Порядок подготовки лабораторных описан в [документации](docs/prairielearn-labs.md).
