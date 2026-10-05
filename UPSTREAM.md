# Источники установленных расширений

Полные пакеты `_extensions` хранятся в Git и используются непосредственно при сборке. Core, Presentation, Publisher, Reference Catalog, Download и PrairieLearn сохранены из Java-курса в коммите `c32d3f24612f050eea5e90bac8abeefd587bf971`, включая vendor и лицензии. Для нового оформления слайдов добавлен только независимый пакет Navigation.

| Пакеты | Исходный репозиторий | Проверенный коммит |
|---|---|---|
| `course-core`, `course-presentation` | [quarto-course](https://github.com/Afonenko-Course-Tools/quarto-course) | `1afcc929f5006a7b4252310851e12d456cba00cd` |
| `reference-catalog` | [quarto-reference-catalog](https://github.com/Afonenko-Course-Tools/quarto-reference-catalog) | `f99d38d945b168e0113b9b6a3de506d9ffe761c0` |
| `project-publish` | [quarto-project-publish](https://github.com/Afonenko-Course-Tools/quarto-project-publish) | `d9cc5ee0f810c91c38e4bb6d1dc0093b085d016d` |
| `project-download` | [quarto-project-download](https://github.com/Afonenko-Course-Tools/quarto-project-download) | `e00f34a40308380263d119c522e5c18a9381817b` |
| `course-prairielearn` | [quarto-course-prairielearn](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn) | `95c6718640210b654c1f332bd8ba48effa1a92a6` |
| `course-navigation` | [quarto-course](https://github.com/Afonenko-Course-Tools/quarto-course) | `da4c3730ec4779206588cbb1e9532ec541421c9f` |

Navigation перенесён целиком из [шаблона в коммите `a5939b4f06e88e80a63eca61ee7ff71ef841f061`](https://github.com/Afonenko-Course-Tools/quarto-template-course/tree/a5939b4f06e88e80a63eca61ee7ff71ef841f061). Его поставщик указан в `UPSTREAM.md` этого шаблона; точное дерево поставщика — `196dbc08b01113b43b54c4cb3a7d6dd26c71bf05`. Пакет управляет обычными слайдами Reveal.js и не требует изменения учебной модели.

Корень содержит Publisher и Reference Catalog. HTML-книги и слайды имеют собственные полные копии Core/Presentation; `tasks` также содержит Download/PrairieLearn, `lectures` и `practice` — Navigation. Фильтры, плагины и обработчики подключаются явно в YAML. Книги используют стандартную тему Cosmo, слайды — стандартную тему Reveal.js; прежнее подключение темы БГУ снято.

Авторский формат по-прежнему сверяется с [совместимой версией шаблона `9ff6bbfa71b4849068da9a134256d3733e6f3be3`](https://github.com/Afonenko-Course-Tools/quarto-template-course/tree/9ff6bbfa71b4849068da9a134256d3733e6f3be3), закреплённой в CI. Новое оформление не обновляет backend-пакеты и не меняет контракт заданий. [Карта переноса](docs/original-author-migration.md) описывает новые пути.

При обновлении переносите пакеты целиком и проверяйте оба профиля. Изменения реализации расширений вносятся в репозитории поставщиков; оптимизация и публикация заданий на PrairieLearn остаются отдельной работой.
