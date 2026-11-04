# Download recognizer/location — независимый review

Root: **APPROVE scoped**, candidate поверх4d9f8cd. Прочитаны полный changed
early-filter, exact diff и сохранённый compiler/control outcome; новых запусков
reviewer не делал.

`indexableObject` допускает массивы и обычные объекты, обещая только unknown
properties. Узкий shortcode view извлекает реальные attr/children, а не
приписывает всему AST закрытый Span tuple. Class membership сохраняет string
substring и array includes; внешний c остаётся индексируемым object/array.
Download view получает children array только после фактической проверки.
Existing Attr/request decoder остаётся единственной бизнес-границей аргументов.

Scan переиспользует найденный shortcode, оставляет чужой marker целиком и
создаёт location в той же ветке, где известны array/number либо object/string.
Отложенное применение replacements и исходная object identity сохраняются.
Собственный generated transport типизирован локально; any/casts/key! больше нет.
Нового полного AST parser, stripping неизвестных полей, IO или повторного walk
не появилось.

Malformed non-callable includes может теперь дать false/дальнейший scan вместо
случайного TypeError. Экзотические direct-JS getters/Proxy/именованные array
properties не объявлены эквивалентными Native JSON. Для настоящего JSON обход
blocks elements сохраняет прежний маршрут; новых ограничений valid Native input
не найдено. Пределы прямо названы writer, без ложной полной Pandoc модели.

Final affected bundled compiler PASS0.816s и существующие15 pure controls
PASS0.365s, total1.181s/cap15/retry0. Новые cases не добавлялись. Это не actual
stdio Native round-trip/полная типизация Download или hosted CI; cache experience
на944 не переназван проверкой этой дельты. Блокирующих findings нет.
