# Происхождение технической поставки Java

Поставка от 24 октября 2026 года использует новые опубликованные версии
пяти владельцев. Все установленные пакеты получены целиком из коммитов
неизменяемых тегов; активных локальных кандидатов и поправок в пакетах нет.

| Владелец | Выпуск | Коммит |
| --- | --- | --- |
| Afonenko-Course-Tools/quarto-course | v8.2.3 | `5267d1663f3c44ce4a1c24cfde5737f2bc963a56` |
| Afonenko-Course-Tools/quarto-project-publish | v5.1.4 | `a0fc543fb461d90b9f467dbe8b6a0b286a39311a` |
| Afonenko-Course-Tools/quarto-reference-catalog | v3.1.1 | `989448d2afa4df7db882b0874c88808a00ae52c2` |
| Afonenko-Course-Tools/quarto-course-prairielearn | v8.2.2 | `741ca5b172b456ee843f64c2b7626618e348f56c` |
| Afonenko-Course-Tools/quarto-project-download | v3.2.2 | `10cb38f2ba5c88e585a0ff6800adf5fc2b77597e` |

`providers.json` закрепляет тег, версию, коммит и архив каждого владельца.
`installed-packages.json` содержит точные области установки, Git tree пакетов
и SHA-256 каждого файла. Установлены 24 области семи пакетов:

- Core и Presentation — корень, `theory`, `tasks`, `lectures`, `practice`, `handbook`;
- Navigation — `lectures`, `practice`, `tasks`;
- Site — корень;
- QRC — корень и все пять подпроектов;
- PrairieLearn и Download — `tasks`.

Полные копии воспроизводятся штатным материализатором из checkout владельцев,
в которых точные теги доступны локально:

```sh
python3 tools/materialize-providers.py --write --providers-root /path/to/providers
python3 tools/materialize-providers.py --check
python3 CI/check-production-pins.py
```

До копирования материализатор проверяет соответствие `tag^{commit}`, версий
дескрипторов и прежней установленной поставки. Render Quarto пакеты не обновляет.
Print, Moodle и Cloud в этот состав не входят.

Исторические кандидаты Core `c8b2fe868cf805301fb6ebeba6587f8c96197369`
и PrairieLearn `1221c8c073d77d80d825ee906e9c244a75ff4a39` входят в историю новых
коммитов. Все файлы прежних поправок уже побайтно вошли в установленные ранее
Core 7.3.0 и PrairieLearn 7.1.1; повторное наложение не требуется. Эти прежние
выпуски также являются предками новой поставки. Архив `docs/provider-history/`,
старые манифесты и patches сохраняют историю; активное происхождение задают
только `providers.json` и `installed-packages.json`.

Конфигурация курса, учебные QMD, проекты и README стартеров сохранены.
Новая матрица использует API Core 8.1, Body v2 и delivery v3. Platform 2.2.0,
опубликованные OCI digest и прежний toolchain в `CI/toolchain.json` сохранены;
эта фиксация сама по себе не подтверждает совместимость production grading
с delivery v3. Набор PrairieLearn остаётся `2026-2027-s1`; действующие внешние
Moodle/PrairieLearn не изменяются.

Локальная приёмка этой поставки выполняется отдельно на зафиксированном коммите
курса с Quarto 1.11.6 и его Deno 2.7.14: обычный цикл
`student → full → student`, точные байты стартеров и публичная выдача.
Результаты, команды и ограничения сохраняются во внешних квитанциях и описании
OPEN PR. Общая проверка всех 103 проектов в контейнере, production grading
и публикация сайта Java в эту поставку не входят.
