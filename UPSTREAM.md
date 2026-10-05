# Источники установленных расширений

Курс согласован с [quarto-template-course](https://github.com/Afonenko-Course-Tools/quarto-template-course/tree/d3c92b96090658b9ab15a66f46a3fab83faa54e2), коммит `d3c92b96090658b9ab15a66f46a3fab83faa54e2`. Используются полные пакеты из текущих основных веток поставщиков на 5 октября 2026 года.

| Пакеты | Исходный репозиторий | Закреплённый коммит |
|---|---|---|
| `course-core`, `course-presentation`, `course-navigation` | [quarto-course](https://github.com/Afonenko-Course-Tools/quarto-course) | `6c292c61c628e67bf590211f9852c23a2fc2be01` |
| `course-site` | [quarto-project-publish](https://github.com/Afonenko-Course-Tools/quarto-project-publish) | `be92f189f267a8bbc986c40254c685b4f33f9f0b` |
| `reference-catalog` | [quarto-reference-catalog](https://github.com/Afonenko-Course-Tools/quarto-reference-catalog) | `84f653c8d4e3e74fdb1a62249af28250846721a4` |
| `project-download` | [quarto-project-download](https://github.com/Afonenko-Course-Tools/quarto-project-download) | `f25475af13f42c1a32c3bbacb0d1feb205920df8` |
| `course-prairielearn` | [quarto-course-prairielearn](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn) | `4287b5bb0d243239a10db99ab9b069dd2ee74e6a` |

`providers.json` задаёт репозитории, коммиты и места установки. `installed-packages.json` содержит полный состав файлов, SHA256, размеры и режимы каждого установленного пакета. Манифесты нужны для воспроизводимого обновления; сборка работает непосредственно с установленными расширениями.

Для обновления целых пакетов из локальных репозиториев поставщиков:

```sh
python3 tools/sync-providers.py --providers-root /путь/к/репозиториям
```

Скрипт взят целиком из закреплённого шаблона. Он устанавливает архивы закреплённых коммитов штатным `quarto add`, сравнивает состав и содержимое с исходниками и обновляет манифест. Реализация расширений в учебном курсе не редактируется.

Корень содержит Core, Presentation, Reference Catalog и Course Site. Все пять учебных частей имеют собственные полные копии Core, Presentation и Reference Catalog; `tasks` дополнительно содержит Download и PrairieLearn, `lectures` и `practice` — Navigation. Порядок обработчиков: Core → Download, если подключён → QRC → сбор текущего результата для Course Site. Прежний Publisher и его сервер предпросмотра заменены штатной сборкой и предпросмотром Quarto.

Таблица плана курса использует обычную Markdown-таблицу и стандартный контейнер Bootstrap `.table-responsive`. Лекции и практика используют штатные презентации Reveal.js. Подробности адаптации авторского формата описаны в [карте переноса](docs/original-author-migration.md).
