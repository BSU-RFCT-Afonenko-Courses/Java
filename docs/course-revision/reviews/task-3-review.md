### Spec Compliance

- ❌ Issues found: U01-R1 — `handbook/entries/java-tools.qmd:35` неверно преобразует полное имя `course.reading.Reading` в путь класса. Требование согласовать package/classpath с общей программой Reading закрыто не полностью. Остальные проверенные требования Task 3 выполнены.
- ✅ Проверены все 21 назначенный QMD и новый пакет: восемь глав, восемь статей и пять практик имеют соответствующие изменения либо предметно обоснованное сохранение. Таблица решений находится в `docs/course-revision/packets/U01.md:13`; фактические изменения перечислены ниже. Новые определения и YAML categories применены во всех 21 файле, совместимость/aliases/migrations не добавлены.
- ⚠️ Cannot verify from diff: разрешение чужих будущих целей, окончательная Quarto/reveal читаемость, полный межпроектный граф ссылок, экспорт/доставка PL17, public/private leak checks и студенческий pilot. Это последующие M02/M03/M04/M05/U07 проверки; пакет явно передаёт их в `docs/course-revision/packets/U01.md:64` и `:113`. Они не являются дополнительными блокирующими замечаниями к U01.

### Strengths

- `theory/chapters/types.qmd:204` действительно вводит переменную, объект и компонент массива до initialization (`:232`), copying (`:268`), absence (`:333`) и final (`:388`). Показаны initializer/new, length без скобок, нулевая длина, числовой default и null-компоненты ссылочного массива. До этого нет мутации необъяснённого контейнера.
- `theory/chapters/platform.qmd:48` начинает Reading со скаляров, содержит package и согласованный путь исходника; `:72` отделяет запуск старого класса от source launch и повторной компиляции. `practice/numbers.qmd:27`, `:53` и `:71` восстанавливают массив аргументов и объясняют if/return до охранного индексирования.
- `theory/chapters/languages.qmd:83` и `theory/chapters/types.qmd:49` различают runtime binding, аннотацию и выбранный checker. `handbook/entries/values.qmd:91` фиксирует Python/Pyright и конфигурацию; записанное evidence подтверждает обычный вывод `7/seven` и отдельный reportAssignmentType (`/tmp/java-course-revision-checks/U01/semantics.json:1043`).
- `theory/chapters/types.qmd:333`, `:362`, `:388` последовательно различают null, пустую строку, definite assignment, unboxing и final. В `:401` конкретные CF annotation/processor и обычный javac отделены от runtime. Записанные bad/good результаты CF согласованы с текстом (`semantics.json:1105`, `:1141`); C# предупреждение и runtime-исключение честно классифицированы (`csharp.json:19`, `:52`), JS TDZ не назван undefined, Zig явно оставлен документированной иллюстрацией.
- `theory/chapters/methods.qmd:77` начинает передачу аргументов со скаляра; `:107` разделяет мутацию компонента и переназначение параметра; `:156` возвращает новый массив через new до введения clone. `theory/chapters/arrays.qmd:144` и `:172` отдельно показывают общие строки и независимые копии. В `methods.qmd:204` рекурсия получила видимую трассу вызовов и обратных возвратов.
- `theory/chapters/text.qmd:39`, `:67`, `:84` дают обязательный минимум immutable/equals/UTF-16/байты и позиционную вставку с обычными и граничными результатами. `handbook/entries/strings.qmd:145` и `practice/text.qmd:106` связывают точный алфавит с цифрой, табуляцией, пунктуацией и сохранением разделителей; regex не объявлен обязательным способом решения.
- G05/G12 материализованы у назначенного владельца: `handbook/entries/arrays.qmd:71` объясняет generic copyOf, сохранение runtime-класса, null padding и независимую внешнюю структуру; `handbook/entries/strings.qmd:132` объясняет знак compareTo и UTF-16 порядок без обещания locale collation. G08/G09 имеют действующие локальные рецепты Scanner Locale.ROOT и PrintStream (`handbook/entries/console.qmd:131`, `:164`). G06 обещает устойчивость только для соответствующего алгоритма/overload (`handbook/entries/arrays.qmd:97`).

### Проверка назначенных файлов

| Файл и место | Фактическое решение и оценка |
|---|---|
| `theory/chapters/languages.qmd:83` | revise: pages:int, допустимое обновление, checker/runtime; `:149` заменяет авторский финал причинным 5/2. Соответствует EF08/P01. |
| `theory/chapters/platform.qmd:48` | revise: полный Reading, правильные пути/имя/команды, scalar first и stale/source/class. Соответствует требованиям главы. |
| `theory/chapters/types.qmd:204` | revise: R1, затем absence/wrapper/final/analyzer; числовые объяснения сохранены с результатами. Соответствует EF08/09. |
| `theory/chapters/expressions.qmd:176` | revise: условное выражение получило 1.0 и причину; promotion/overflow/short circuit сохранены. Соответствует P02. |
| `theory/chapters/control.qmd:57` | revise: охранный main; `:175` scalar nested loops; enhanced-for имеет собственные объявления. Ранние матрицы убраны. |
| `theory/chapters/methods.qmd:107` | revise: две отдельные операции над ссылкой; new-result и рекурсивный опыт; scalar/overload/varargs сохранены. Соответствует P03. |
| `theory/chapters/arrays.qmd:144` | revise/preserve: clone перед nested-copy наблюдением; `:211` корректная опора устойчивости; границы и глубина копии сохранены. |
| `theory/chapters/text.qmd:84` | revise: ранний метод вставки и слово; `:137` normalization явно дополнение, каталог regex остаётся справочным. |
| `handbook/entries/java-tools.qmd:9` | revise: команды и общий исходник согласованы; **пояснение пути в :35 требует U01-R1**. Остальные режимы/диагностики сохранены. |
| `handbook/entries/console.qmd:131` | revise: double/locale; `:164` stdout API, precision/newline и no prompts. EOF/encoding/ownership сохранены. |
| `handbook/entries/values.qmd:26` | revise/preserve: array minimum перед final; `:91` Python checker и `:116` CF environment. Таблицы типов/преобразований сохранены. |
| `handbook/entries/operators.qmd:9` | checked preserve: таблицы арифметики, исключений, promotion и порядка остаются точными; новые IDs/categories/consumers. Содержательная перепись здесь не нужна. |
| `handbook/entries/statements.qmd:9` | revise/preserve: if явно схема, switch имеет code и basic; loop ссылается на уже введённый минимум. Правила переходов сохранены. |
| `handbook/entries/methods.qmd:9` | revise/preserve: контекст Reading, average/sum с результатами, минимум массива для varargs; точные signatures/overload/copy semantics сохранены. |
| `handbook/entries/arrays.qmd:71` | revise: generic copyOf и stable object-sort; copyOfRange/arraycopy существенные ограничения сохранены. |
| `handbook/entries/strings.qmd:132` | revise: compareTo; `:145` word spans; прежние immutable/parseInt/regex/builder API сохранены. |
| `practice/numbers.qmd:17` | revise/preserve: prerequisites, 30 минут, локальный args guard; `:244` один перенос деления; текущие PL протоколы не расширены. |
| `practice/control.qmd:19` | revise/preserve: prerequisites, 30 минут, локальные Reader/throws; `:129` перенос терминатора; ASCII/UTF-8/EOF и назначения сохранены. |
| `practice/arrays.qmd:19` | revise/preserve: prerequisites, 30 минут, strict swap/stable ties; `:122` перенос clone depth. 3×3/−3…3 и десять double сохранены. |
| `practice/text.qmd:19` | revise/preserve: prerequisites, 30 минут, точное слово/границы; `:129` один перенос индексов. Нового обязательного word-transform алгоритма нет. |
| `practice/reserve-algorithms.qmd:19` | revise/preserve: добровольный статус и prerequisites выбранной темы; `:108` перенос исходных координат. Не включён в обязательные 300 минут. |

### Issues

#### Critical (Must Fix)

- Не обнаружены.

#### Important (Should Fix)

- **U01-R1 — неправильный путь класса.** `handbook/entries/java-tools.qmd:35`: для `course.reading.Reading` указан относительный путь `course/Reading.class`. Загрузчик ищет `course/reading/Reading.class` относительно `out`; именно такой результат дан в `theory/chapters/platform.qmd:70`. Это содержательная ошибка в обязательной связке package → полное имя → classpath, которую Task 3 прямо требует согласовать. Исправить один путь на `course/reading/Reading.class` (либо явно `out/course/reading/Reading.class` с обозначением корня). Правильные исполняемые команды не подтверждают неправильную поясняющую фразу; нового широкого прогона для этой правки не требуется.

#### Minor (Nice to Have)

- **U01-R2 — прежние комментарии успешного вывода в отрицательных contexts.** `docs/course-revision/packets/U01.md:2637` сохраняет `// false` после замены && на &, хотя выполнение заканчивается NPE до печати. В `:2753` вызов insertAt с позицией 5 сохраняет `// Ja-va`, хотя выбрасывает IndexOutOfBoundsException. Таблица статусов и фактические проверки верны, поэтому это не блокирует семантическую приёмку. Удалить эти комментарии либо подписать ожидаемый отказ; для такого поясняющего изменения повтор исполнения не нужен.

### Проверки и границы evidence

- Review package `.superpowers/sdd/execution-plan/u01-review.diff:1` проверен последовательными частями, включая новый packet. Повторяющиеся полные sample contexts сопоставлены с записанными полными исходниками: 115 executable + 3 схемы, несовпадений нет. Все 118 Java fences из 21 QMD совпадают с snippet records; declarations/imports/preludes/observers рассмотрены отдельно. Изменённые QMD не перечитывались из checkout; hash-проверка служила только проверке заморозки.
- `/tmp/java-course-revision-checks/U01/frozen-source-sha256.json:1`: сравнение всех 23 SHA256 с текущими артефактами не выявило изменений. `/tmp/java-course-revision-checks/U01/definitions.json:1` и `verification.json:1045`: все 21 файла имеют свежие sec-j25 определения, показаны 141/141 успешных проверок. Это подтверждает native structure и ID/category checks, а не заменяет ручную оценку объяснения.
- `/tmp/java-course-revision-checks/U01/snippets.json:1`: просмотрены exit/stdout/stderr всех 115 executable samples; все pass=true, неожиданной compile/runtime диагностики нет. Намеренный NPE помечен отрицательным примером. `/tmp/java-course-revision-checks/U01/semantics.json:1`: 43 cases подтверждают ожидаемые compile/runtime этапы, границы ввода, checker и source/class последовательность. C# CS8602 — целевое наблюдение, а не скрытое предупреждение.
- `/tmp/java-course-revision-checks/U01/tool-versions.json:1`: записанные argv действительно используют actual OpenJDK 25.0.4.1; Python/Pyright, Node, Roslyn/C# environment названы явно. Zig установлен другой версии и не объявлен выполненным 0.15.2. Широких повторных тестов или новых runtime проверок reviewer не запускал: существующее evidence отвечает на возникшие семантические вопросы.
- `/tmp/java-course-revision-checks/U01/source-volume.json:155` подтверждает рост восьми U01 глав 7.536%; `docs/course-revision/packets/U01.md:80` не выдаёт это за итог всех 23 глав или измеренную длительность.
- Именованный cross-contract risk G04 — подготовка могла изменить слово/разделители/k. Один адресный check `tasks/bank/text.qmd:12` и `:28` подтвердил исходные non-null/empty/алфавит/исходные границы и сохранение разделителей; U01 не навязывает дополнительное скрытое условие.
- Именованный cross-contract risk G08/G09 — locale/формат могли сузить числовой протокол L1. Один адресный check `tasks/bank/numbers.qmd:11` и `:30` подтвердил точку, допуск 1e-8, отсутствие фиксированного числа знаков и stdout без приглашений. U01 рецепт %.2f явно не выдан за обязательный формат.
- Именованный cross-contract risk EOF/ASCII — practice могла изменить завершение или допустимый текст L2. Один адресный check `tasks/bank/control.qmd:43`, `:55`, `:69` подтвердил точку/EOF, сохранение остальных UTF-8 символов и отказ n<=0 до чтения.
- Именованный cross-contract risk G06 — обещание устойчивости могло расширить проверяемый matrix input. Один адресный check `tasks/bank/arrays.qmd:72` подтвердил stable ties, 3×3/−3…3 и перестановку целых строк. Новых grader cases или algorithm mandate не введено.

### Assessment

**Task quality:** Needs fixes.

**Reasoning:** Содержательная последовательность, причинные объяснения и recorded evidence U01 соответствуют задаче. Приёмку блокирует одна локальная фактическая ошибка package/classpath в справочнике; после её точного исправления достаточно узкого повторного review, без перечитывания всего корпуса и повторения широких тестов.
