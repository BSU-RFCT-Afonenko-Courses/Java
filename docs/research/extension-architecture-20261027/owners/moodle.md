# Moodle exporter: транспорт Body и процессы фрагментов

Выбран0.3.1. Runtime transport ожидает `course-body-package-v1`, works.items/assignments и собственные closed predicates. Это legacy family, отличная от выбранного Core8.3 Bodyv2/slots. В baseline Java адаптер не установлен. Native Moodle import/student grading здесь не выполнялись.

`exportMoodle` вызывает validatePackage, затем повторно validateBody для condition/publicAnswer каждого вопроса до await. На том же неизменённом входе повтор одного predicate подтверждён. Проверка поддерживаемого answerType и single-choice closedKey — самостоятельная гарантия: её нельзя удалить вместе с повторным AST traversal.

После verifyResources helper html клонирует блоки, снимает локальные header IDs, переписывает только native Link/Image resource slots и запускает `quarto pandoc --from=json --to=html5 --mathml`. Для каждого question condition один процесс, для single-choice — ещё по одному на choice. Статическая формула Q + C, где C — общее число choice fragments; это не измеренное число subprocess текущей Java. XML создаёт vendored xmlbuilder2, не собственный parser XML.

Операции: transport validation и resource integrity; declarations/binding defaultGrade/shuffle; AST mapping; native fragment conversion; XML packaging/file attachment; process/diagnostics. Поддерживаются manual/single-choice, другие формы отвергаются до конвертации. Closed key используется для результата XML и не должен случайно стать public body. Внешний parser и vendored library отделены от first-party callsites.

H36 подтверждена в пределах повторного Body predicate; H38 подтверждена по коду как запуск на fragment. Native Pandoc Lua writer или batch conversion — варианты, требующие сравнения MathML, escaping, resources и закрытого key. H37 частично: дешёвые input-type проверки уже ранние; общая цена текущего маршрута неизвестна. Нового Moodle/server/gradebook PASS нет.
