# character-file-copy — техническая поставка

Самостоятельное условие находится в tasks/bank/files.qmd. Публичная заготовка —
только student/: Main.java как свободно заменяемый старт, README и три пары
samples. Reference, tests и fixtures в starter ZIP не включаются.
PrairieLearn/Moodle registration не выполнялась; оценивание остаётся ручным
с автоматическими наблюдениями bytes/отказов.

Требуемая техника: Reader/Writer, явную UTF-8 и try-with-resources. Её проверяют по исходнику. Checker не выдаёт
успешную копию за доказательство explicit close или Reader/Writer/TWR.

`tests/cases.json` задаёт 4 public observations и 17 private: точные
ожидаемые файлы, сохранность входа/прежнего выхода, создание нового,
разные cwd/пути, missing input, same file с ../ и поддержанными ссылками,
output-directory error. Invalid UTF8 не входит в этот договор.
Ожидаемые bytes заданы отдельными файлами, без вычисления копирующего
алгоритма внутри oracle. Общие большие фрагменты повторяют независимо
заданные пары literal input/expected.

`tests/contract-cases.json` задаёт trusted entry и source root каждого
технического варианта. Две корректные реализации работают по одному
значению и буферно, имеют разные классы/пакеты и Java25 формы main.
Байтовый buffer заканчивает успешное копирование System.exit(7): нулевой
status не является скрытым требованием. Другие варианты проверяют no-op,
слишком широкую whitespace-замену, потерю разделителей/последнего LF,
Unicode, раннее усечение, строковую проверку same file, молчаливый IO,
ошибку компиляции и неверную настройку launch class.

Общий специализированный checker — ../../test-support/check-copy.py.
Он получает выбранную преподавателем launch class, компилирует все .java
из sources, применяет настоящий Java25 --dry-run для настройки запуска,
затем запускает каждое наблюдение отдельной JVM. Он не требует Main,
публичного static main или helper API, не вызывает main повторно в одной JVM.

Пример из tasks/projects/character-file-copy (entry берётся из contract-cases):

```bash
python ../../test-support/check-copy.py \
  --project . --sources reference/single \
  --entry CharacterCopy \
  --work /tmp/character-file-copy-new-proof
```

`--public` выбирает только public cases, `--only` — известные IDs выбранной
области. Work должен быть новым и не пересекаться с project/sources/JDK/support;
существующие каталоги не очищаются. Source-compilation, launch-setup и
behavior failure различаются; неверная teacher entry не маскируется
как требование к student API. Все исходники и datasets хешируются до/после.
Статусы/stdout/stderr нормального копирования не фиксируются. При IO-отказе
проверяется непустой сигнал в любом канале; его смысл и отсутствие ложного
обещания успеха остаются ручной частью. Пределы времени/вывода технические.
