# Интеграционные операции Java, Template и Cybersecurity

Подробные зависимости и граф CI: [dependencies-and-ci.md](../dependencies-and-ci.md). T01 хранит отдельные ledgers java-integration,template,template-provider-variant,cybersecurity-integration. Копии проверенного vendor имеют mapping на владельцев, а не второе независимое обоснование того же кода.

| Группа | Требование и consumer | Effects / частота / предел |
| --- | --- | --- |
| JAVA-INSTALL | Воспроизводимо получить выбранные packages/scopes, сохранить чужую дельту | Git archive/quarto add/FS replacement/receipt; только install/check, не per page. Materializer и legacy sync различаются, mode-normalization описана отдельно |
| JAVA-PINS | Exact source/tag/assets/OCI и допустимая assembly | Network/Docker/Git в CI; проверяет поставку, не решения студента. Read-only current CI принят, локально не воспроизведён |
| JAVA-RENDER | Student/full separation и возврат student outputs | Три native renders; защита от утечки после full. Timeout не SLA |
| JAVA-CHECKS | Declared public inventory и согласованность native model/common inputs | Native render/check/materialize/inventory; temp model вне source, cleanup created inputs в finally. Private references не получают fake readiness |
| JAVA-LOCAL-CANDIDATE | Исторический диагностический путь JDK/Gradle/host | Не равноправный production backend; inactive/default/pins отделены от официального контейнерного receipt |
| TEMPLATE-INSTALL/LAYOUT | Пакетная установка, standalone layout, guide/navigation examples | Tests/dev tooling отдельны от runtime. Legacy vendor variants учтены, current Core8.3 compatibility не объявлена |
| CYBER-RENDER/MODEL | Student/full content, access-control Body и QRC links | Source/check scripts имеют свои scopes и prerequisites; не Java grading. Отсутствующая remote source часть явно исключена из локального parsed denominator |
| CYBER-VISUAL | Интерактивная схема access control | First-party browser model/view, callbacks и события; server trace не наблюдает DOM. Учебная схема не заменяет Core schema validation |

Исходные функции тестов, CI callbacks и top-level commands учтены механически. Их necessity/frequency нельзя считать полностью доказанными лишь по месту хранения. Неисследованные исторические test cases и provenance gaps сохраняют статус неизвестно с ответственным владельцем; ширина AST inventory не равна полному semantic review.
