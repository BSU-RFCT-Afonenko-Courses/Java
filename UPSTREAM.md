# Источники установленных расширений

Рекомендации сверены с документацией [template e36fd64](https://github.com/Afonenko-Course-Tools/quarto-template-course/tree/e36fd64cdc9fb8fb56238883248206c8856104f5). Template теперь является руководством и каталогом примеров; его учебные исходники не копируются в курс. Код, контракт и документация каждого установленного пакета берутся из одного закреплённого immutable commit. Для PL редактора временно используется проверяемый кандидат отдельного выпуска.

| Пакеты | Выпуск владельца | Закреплённый коммит |
|---|---|---|
| Core, Presentation, Navigation | [quarto-course v4.0.1](https://github.com/Afonenko-Course-Tools/quarto-course/tree/v4.0.1) | `a9a439bd6e6498806d4d4943efd71232e70170be` |
| Course Site | [Publisher v5.0.0](https://github.com/Afonenko-Course-Tools/quarto-project-publish/tree/v5.0.0) | `215309b5c41669e56a857a1bc3e4f7f2ce782c5f` |
| Reference Catalog | [QRC v3.0.0](https://github.com/Afonenko-Course-Tools/quarto-reference-catalog/tree/v3.0.0) | `559583805a514ae8a244b6ea4cb5124867064024` |
| Project Download | [Download v2.0.0](https://github.com/Afonenko-Course-Tools/quarto-project-download/tree/v2.0.0) | `ee5ae76255d265ad7c7f43a765bc061ffc8eec75` |
| Course PrairieLearn | [кандидат 3.1.0 — native editor](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/tree/11a6c73ee306c51b25f0714869af69def0234667) | `11a6c73ee306c51b25f0714869af69def0234667` |

Patch доставки корневого `student/.gitignore` выпущен как v3.0.1 после [PR #10](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/pull/10). Полный payload установлен через manifest и штатный installation interface; vendored implementation вручную не меняется.

`providers.json` задаёт репозитории, коммиты и места установки. `installed-packages.json` содержит полный состав файлов, SHA256, размеры и режимы каждого установленного пакета. Манифесты нужны для воспроизводимого обновления; сборка работает непосредственно с установленными расширениями.

Для обновления целых пакетов из локальных репозиториев поставщиков:

```sh
python3 tools/sync-providers.py --providers-root /путь/к/репозиториям
```

Скрипт взят целиком из закреплённого шаблона. Он устанавливает архивы закреплённых коммитов штатным `quarto add`, сравнивает состав и содержимое с исходниками и обновляет манифест. Реализация расширений в учебном курсе не редактируется.

Корень содержит Core, Presentation, Reference Catalog и Course Site. Все пять учебных частей имеют собственные полные копии Core, Presentation и Reference Catalog; `tasks` дополнительно содержит Download и PrairieLearn, `lectures` и `practice` — Navigation. Порядок обработчиков: Core → Download, если подключён → QRC → сбор текущего результата для Course Site. Прежний Publisher и его сервер предпросмотра заменены штатной сборкой и предпросмотром Quarto.

Таблица плана курса использует обычную Markdown-таблицу и стандартный контейнер Bootstrap `.table-responsive`. Лекции и практика используют штатные презентации Reveal.js. Подробности адаптации авторского формата описаны в [карте переноса](docs/original-author-migration.md).

Пилот Java теперь использует стандартный `pl-file-editor` с одной заготовкой класса.
PL provider установлен целым пакетом по commit `11a6c73ee306c51b25f0714869af69def0234667`
(кандидат 3.1.0, отдельный выпуск ещё не опубликован). Остальные pins сохранены.
Полный student проект предназначен для локальной работы; в editor delivery он не копируется.
