# Опыты с объектами и классами

Для компиляции и запуска нужен JDK 25. Выполняйте команды из этого каталога,
в котором находятся `lecture-entry`, `theory-entry`, `classes` и остальные
каталоги исходников. Команда `javac` сохраняет файлы классов в указанный
каталог `out/...`; `java -cp` загружает их оттуда.

## Книга с конструктором по умолчанию

В `lecture-entry/Book.java` объявлены поля `title` и `borrowed`.
Собственных конструкторов нет, поэтому `new Book()` использует конструктор
по умолчанию. `ObjectsEntryDemo` воспроизводит создание и присваивания
из начала лекции об объектах.

```bash
javac --release 25 -encoding UTF-8 -d out/lecture-entry lecture-entry/Book.java lecture-entry/ObjectsEntryDemo.java
java -cp out/lecture-entry ObjectsEntryDemo
```

Вывод:

```text
null
false
null
Java
true
true
false
Java
false
```

Первые две строки содержат значения полей новой книги. Третья — значение
`title` книги, созданной для `pending`. Запись в `second.title` сохраняет
`Java` у `first`. Запись через `same` меняет общий признак выдачи.
Сравнение `first == other` даёт `false`: `other` ссылается на отдельный объект
с теми же значениями полей. Последнее присваивание
заменяет ссылку `same`, а `first` сохраняет прежнюю книгу.

## Книга с переданным названием

`theory-entry/Book.java` содержит конструктор `Book(String initialTitle)`.
Он записывает название при создании. Это альтернативное определение `Book`
из главы «Объекты, ссылки и время жизни».

```bash
javac --release 25 -encoding UTF-8 -d out/theory-entry theory-entry/Book.java theory-entry/ObjectsEntryDemo.java
java -cp out/theory-entry ObjectsEntryDemo
```

Вывод:

```text
Java
Algorithms
false
true
Networks
false
true
true
false
Java
true
false
```

В первом блоке созданы две книги, выдача второй сохраняет доступность первой.
Второй блок отделяет объявление `pending` от создания книги `Networks`.
Третий блок начинается с новой книги `Java` и показывает общую ссылку,
отдельный объект с теми же полями и переназначение переменной `same`.
Каждая из двух команд компиляции выбирает собственное определение `Book`
и сохраняет результат в отдельный каталог.

## Книга с защищённым состоянием

`protected-book/course/examples/objects/Book.java` объявляет публичный класс
`course.examples.objects.Book`. Конструктор отвергает `null` и название,
для которого `isBlank()` возвращает `true`. Методы `title()` и `borrowed()`
читают состояние; `borrow()` переводит доступную книгу в выданное состояние.

```bash
javac --release 25 -encoding UTF-8 -d out/protected-book protected-book/course/examples/objects/Book.java protected-book/course/examples/objects/BookDemo.java
java -cp out/protected-book course.examples.objects.BookDemo
```

Вывод:

```text
true
true
refused
true
```

`alias` и `book` ведут к одному объекту. Повторный `borrow()` вызывает
`IllegalStateException`; обработчик печатает `refused`, после чего чтение
подтверждает сохранённое состояние выдачи.

## Состояние и операции класса

Каталог `classes` содержит модели из главы о классах и демонстрацию
передачи аргументов из введения к лабораторной. Компиляция:

```bash
javac --release 25 -encoding UTF-8 -d out/classes classes/*.java
```

Каждая следующая команда запускает самостоятельную программу:

| Команда | Строки вывода по порядку | Наблюдение |
|---|---|---|
| `java -cp out/classes ReservationDemo` | `22`, `22`, `22`, `30` | Отказ сохраняет остаток общей комнаты; другая комната сохраняет собственное состояние |
| `java -cp out/classes BoxDemo` | `9`, `12` | Конструктор квадрата делегирует конструктору с двумя размерами |
| `java -cp out/classes CounterDemo` | `2`, `true` | Возврат `this` продолжает работу с тем же счётчиком |
| `java -cp out/classes OverloadDemo` | `int`, `String`, `String`, `Object`, `String` | Выбор перегрузки определяется типом выражения аргумента |
| `java -cp out/classes TicketDemo` | `1 2` | Общее статическое поле изменяется при последовательном создании билетов |
| `java -cp out/classes NamesDemo` | `Bob`, `null`, `Bob`, `Ann` | `final` сохраняет ссылку; копирование на входе и выходе отделяет внутренний массив |
| `java -cp out/classes IntStackDemo` | `full refused`, `1`, `7`, `empty refused`, `0` | Отказы сохраняют размер и хранимые значения стека |
| `java -cp out/classes ArgumentsDemo` | `3 8`, `true`, `0`, `2` | Поля объектов сохраняются; внутри `inspect` параметр `values` ссылается на массив |

Для трассы инициализации:

```bash
java -cp out/classes InitializationDemo
```

```text
класс
поле
блок: 7
конструктор: 7
поле
блок: 7
конструктор: 7
```

Статический блок выполняется при инициализации класса. Инициализатор
поля, блок экземпляра и тело конструктора выполняются для каждого
созданного объекта.

## Краткие формы из справочника

В `reference/ClassApiDemo.java` находятся счётчик с начальным значением,
конструкторы `Box(int)` и `Box()`, а также запись `Range`.

```bash
javac --release 25 -encoding UTF-8 -d out/reference reference/ClassApiDemo.java
java -cp out/reference ClassApiDemo
```

Вывод — `5`, `1`, `0 5` на отдельных строках: счётчик увеличен,
конструктор без аргументов выбрал сторону `1`, конструктор диапазона
задал начальную границу `0` и конечную `5`.

## Защищённое поле в другом пакете

Класс `p.Base` содержит `protected int value`. Подкласс `q.Child`
обращается к полю через параметр типа `Child`.

```bash
javac --release 25 -encoding UTF-8 -d out/access access/p/Base.java access/q/Child.java access/q/AccessDemo.java
java -cp out/access q.AccessDemo
```

Вывод — `1`: поле доступно через такой получатель в коде подкласса.

## Отдельные ошибки компиляции

Файлы в `rejected` содержат намеренные ошибки. Каждая команда ниже должна
завершиться диагностикой компилятора. Они компилируются отдельно от
запускаемых программ.

Локальная переменная прочитана до определённого присваивания:

```bash
javac --release 25 -encoding UTF-8 -d out/rejected-local theory-entry/Book.java rejected/UnassignedLocal.java
```

Вызов с `null` неоднозначен между параметрами `String` и `Integer`:

```bash
javac --release 25 -encoding UTF-8 -d out/rejected-overload rejected/AmbiguousOverload.java
```

Переменная `final` повторно получает ссылку на массив:

```bash
javac --release 25 -encoding UTF-8 -d out/rejected-final rejected/FinalReference.java
```

Подкласс другого пакета обращается к защищённому полю через параметр
базового типа:

```bash
javac --release 25 -encoding UTF-8 -d out/rejected-access access/p/Base.java rejected/ProtectedReceiver.java
```

Текст диагностики зависит от языка сообщений JDK. Причина отказа указана
перед каждой командой и находится в соответствующем исходном файле.

Дополнительные примеры:
[Опыты с передачей ссылок и состоянием объектов](./README-objects-tail.md).
