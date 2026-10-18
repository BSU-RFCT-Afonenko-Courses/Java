# Примеры параметризации и отношений типов

Комплект `generics-examples.zip` распаковывается в каталог `generics/`. Каждый
пример собирается отдельно: в нескольких каталогах находится свой класс `Demo`. Все
команды ниже выполняются из корня `generics/` в оболочке Bash; переход в каталог
действует только внутри круглых скобок.

## Java 25

Нужен JDK 25; команды `javac`, `java`, `javap` должны принадлежать этому JDK.
Каталоги `out` создаёт компилятор. Запускаемые программы не требуют сторонних
библиотек.

Коробка, клиентские приведения и мостовой метод:

```bash
(cd java/erasure && javac --release 25 -encoding UTF-8 -Xlint:unchecked -d out Box.java BoxClient.java Source.java TextSource.java SourceClient.java && java -cp out BoxClient && java -cp out SourceClient && java -cp out ParameterDemo)
(cd java/erasure && javap -classpath out -p -c -v Box BoxClient TextSource SourceClient)
```

`BoxClient` и `SourceClient` печатают по одной строке `Java`. `ParameterDemo`
печатает `4`, `ClassCastException`, затем три строки `true`: проверку изменённого
поля книги, тождество сохранённой ссылки и наличие `null` в другой коробке.
Исключение первого опыта обработано внутри программы. В `javap` сопоставьте
дескриптор `Box.get(): Object`, клиентский `checkcast` и мостовой
`TextSource.get(): Object` с флагами `ACC_BRIDGE, ACC_SYNTHETIC`.

Сравнение с обычной и гибкой границами:

```bash
(cd java/comparison && javac --release 25 -encoding UTF-8 -Xlint:unchecked -d out Demo.java && java -cp out Demo)
```

Три строки `true` подтверждают, что возвращена та же ссылка на второго студента.
Статические типы результатов поясняются в исходнике.

Обмен, максимум, перенос и захват неизвестного типа:

```bash
(cd java/swap && javac --release 25 -encoding UTF-8 -Xlint:unchecked -d out Demo.java && java -cp out Demo)
(cd java/maximum && javac --release 25 -encoding UTF-8 -Xlint:unchecked -d out Demo.java && java -cp out Demo)
(cd java/copy && javac --release 25 -encoding UTF-8 -Xlint:unchecked -d out Demo.java && java -cp out Demo && java -cp out RelationsDemo)
(cd java/capture && javac --release 25 -encoding UTF-8 -Xlint:unchecked -d out Demo.java && java -cp out Demo)
```

| Программа | Вывод по строкам |
|---|---|
| `swap/Demo` | `Lin` |
| `maximum/Demo` | `9` |
| `copy/Demo` | `[1, 2]`; `[0.5, 1, 2]`; `0`; `3.0`; `0.5`; `1`; `2`; `1`; `2` |
| `copy/RelationsDemo` | `ArrayStoreException`; `1`; `UnsupportedOperationException`; `[0.5]` |
| `capture/Demo` | `[Lin, Ada]` |

`RelationsDemo` показывает два разных ограничения: массив отвергает несовместимую
запись, а неизменяемый список — типобезопасное добавление. Оба отказа обработаны;
после каждого отказа программа показывает сохранённое состояние.

Передача проверяемого исключения и вычисление через объект операций:

```bash
(cd java/checked && javac --release 25 -encoding UTF-8 -d out EarlyCheckedDemo.java && java -cp out EarlyCheckedDemo)
(cd java/dot && javac --release 25 -encoding UTF-8 -Xlint:unchecked -d out Demo.java && java -cp out Demo)
```

`EarlyCheckedDemo` печатает `7`, `empty`, `дальше`. `dot/Demo` печатает `11.0`,
`12.0`, `1.0`, `Complex[re=11.0, im=1.0]`. Значения `12.0` и `1.0` получены с
неверным начальным значением суммы: сигнатуры операций согласованы, математическое
условие нарушено.

### Намеренные ошибки Java

Выполняйте каждую команду отдельно. Ожидаемый результат — **отказ компилятора**;
запускаемого класса для этих вариантов не получается.

```bash
(cd java/comparison-target-error && javac --release 25 -encoding UTF-8 -d out Demo.java)
(cd java/comparison-explicit-error && javac --release 25 -encoding UTF-8 -d out Demo.java)
(cd java/capture-error && javac --release 25 -encoding UTF-8 -d out Demo.java)
```

Первый вариант требует результата `Student` от слишком строгой границы, второй явно
выбирает нарушающий её аргумент `Student`. В третьем прочитанное из `List<?>`
значение сохранено как `Object`, после чего его нельзя записать обратно через
неизвестный аргумент типа. Точная формулировка диагностики зависит от компилятора.

## C++20

Нужен компилятор с поддержкой C++20, например `g++`:

```bash
(cd cpp && g++ -std=c++20 -Wall -Wextra -pedantic main.cpp -o dot && ./dot)
```

Результат: `11`, `(11,1)`, `11`. Первые два вычисления используют `vector`, третье —
`array`, у которого длина входит в тип. Сумма произведений комплексных значений во
всех языках этого комплекта вычисляется без комплексного сопряжения.

Отдельный пример намеренной ошибки передаёт массивы разных длин:

```bash
(cd cpp && g++ -std=c++20 -fsyntax-only negative.cpp)
```

Ожидается отказ: единый аргумент `N` нельзя получить одновременно из `2` и `3`.

## C#

Файлы проекта задают C# 11, целевую платформу .NET 10 и SDK 10.0.100 с переходом
только к более свежему исправлению той же ветки `10.0.1xx`. Поэтому установите SDK
из этой ветки с соответствующими локальными библиотеками .NET 10. Источники NuGet
отключены: внешних пакетов нет. Сам механизм статических абстрактных членов
интерфейса доступен с C# 11 и .NET 7, но прилагаемый проект собирается для .NET 10.

```bash
(cd csharp && dotnet --version && dotnet run --project Dot.csproj)
```

После версии SDK программа печатает `11`, `<11; 1>`, `11 1`. Последняя строка
отдельно показывает действительную и мнимую части.

## Python и статическая проверка

Для всего комплекта нужен Python 3.12 или новее: объявления `class Arithmetic[T]` и
`def dot[T]` используют синтаксис Python 3.12. Команды предполагают, что `python3`
выбирает подходящую версию. Pyright устанавливается отдельно.

```bash
python3 --version
(cd python && python3 python_dot.py && python3 python_typed.py && python3 python_protocol.py)
(cd python && pyright --project pyrightconfig.json)
```

`python_dot.py` и `python_typed.py` каждый печатают `11.0`, `(11+1j)`, `0`. Для
пустого входа результатом остаётся начальный целый ноль. `python_protocol.py`
печатает `11.0`, `(11+1j)`, `2`: последнее число — количество умножений, выполненных
объектом действительной арифметики. Строгая конфигурация Pyright проверяет
аннотированную функцию и вариант с протоколом; для них ожидается отсутствие ошибок.

Намеренные ошибки анализируются и запускаются **отдельными командами**:

```bash
(cd python && pyright --pythonversion 3.12 python_negative.py)
(cd python && python3 python_negative.py)
(cd python && pyright --pythonversion 3.12 python_protocol_negative.py)
(cd python && python3 python_protocol_negative.py)
```

В первом файле анализатор отвергает строковый элемент числового входа; интерпретатор
доходит до недопустимого умножения строки на `float`. Во втором анализатор отвергает
объект с результатом `zero(): str` для `Arithmetic[float]`; выполнение доходит до
сложения строки с числом. Оба запуска заканчиваются `TypeError`. Аннотации не
запускают проверку Pyright при обычном выполнении программы.
