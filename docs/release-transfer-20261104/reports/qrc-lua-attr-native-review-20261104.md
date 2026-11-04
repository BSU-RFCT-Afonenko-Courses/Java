# QRC Lua Attr: независимый review

Root: **APPROVE** scoped candidate поверх `77abe92357938fcd814713be066c1adffb31d347`.
Прочитаны оба полных callers, удаляемый helper, весь изменённый existing Lua
control и сохранённое evidence. Reviewer не писал этот diff и не повторял запуск.

`links.lua` и `probes.lua` теперь передают исходные string maps непосредственно
в `pandoc.Attr`. Имена/значения, conditional title, hidden/aria, reference identity,
custom labels, source-export Span и target остаются прежними. Удалены только
временные key/pair arrays и сортировка; `targets.sorted` задаёт порядок целей и
сохранён. Новый Native API, validator или компенсационная канонизация не появились.

Existing control больше не утверждает byte order. Настоящие Pandoc constructors
и оба QRC callers проверяются с явно synthetic Quarto document context:
Link/Span, probes, labels, metadata и numbered/unnumbered поведение. Единственный
сохранённый запуск PASS0.6658s/cap15/retry0; это не full Quarto/crossref/browser
приёмка и не замер ускорения курса. Отдельный TS compiler для этих Lua изменений
не нужен. Dead lazy preload в другом тесте не исполняет удалённый helper без
require и не является production dependency.

Блокирующих Source findings нет. Историческое имя CI step можно поправить вместе
с очередным содержательным CI изменением; возвращать sorting ради него не нужно.
Полные курсы, installed pins, кеши и production этим review не меняются.
