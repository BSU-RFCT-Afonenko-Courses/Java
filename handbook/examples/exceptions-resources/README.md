# Опыты с исключениями и ресурсами

Исходники воспроизводят передачу управления, состояние после отказа
и закрытие ресурсов. Нужен JDK 25. Команды выполняются из этого каталога;
в `negative` находятся отдельные варианты, которые должны быть отвергнуты
компилятором.

## Компиляция

```bash
javac -encoding UTF-8 -d out *.java
```

Для выбора установленного JDK можно указать полный путь к `javac` и `java`,
например `/usr/lib/jvm/java-25-openjdk/bin/javac`.

## Проверка балла и передача управления

```bash
java -cp out ExceptionDemo scores
java -cp out ExceptionDemo flow
java -cp out ExceptionDemo handlers
java -cp out ExceptionDemo cause
java -cp out ExceptionDemo state
java -cp out ExceptionDemo result
java -cp out EarlyCheckedDemo
java -cp out CheckedCallerDemo
```

| Запуск | Наблюдение |
|---|---|
| `scores` | `90 -> 90`, `0 -> 0`, `ninety -> NumberFormatException`, `120 -> IllegalArgumentException`, `null -> NullPointerException` |
| `flow` | `ошибка: балл вне диапазона 0..100: 120`, затем `следующая операция`; строки успеха пропущены |
| `handlers` | `нужно число`, `нужен диапазон`, `90` |
| `cause` | `строка 3`, затем `NumberFormatException` |
| `state` | `[90, 80]` |
| `result` | `балл: 90`, `ошибка: OUT_OF_RANGE`, затем два раза `ошибка: NOT_INTEGER` |
| `EarlyCheckedDemo` | `7`, `empty`, `дальше` |
| `CheckedCallerDemo` | `7`, `хранилище пусто` |

Каждый элемент наблюдения печатается на отдельной строке. В `result`
четвёртый вход — `2147483648`; он не представим в `int`. В `flow`
`readStudent` не достигает своей строки `запись проверена`.

`ExceptionDemo.scoreAt` добавляет к номеру строки текст `неверный балл`,
а `parseAt` из лекции оставляет сообщение `строка 3`. Оба метода сохраняют
исходное исключение как причину. Учебные `Score`, `ScoreFailure`
и `ScoreProblem` вложены в `ExceptionDemo`; файловые опыты обращаются
к ним через это имя.

## Завершение finally и отказ конструктора

```bash
java -cp out FinallyDemo
java -cp out ConstructorDemo
java -cp out SignatureDemo
```

`FinallyDemo` печатает:

```text
выход из метода
10
20
20
cleanup
null
0
```

Первый `finally` сохраняет возврат `10`. `replacedReturn` заменяет его на `20`;
`swallowedFailure` возвращает `20` после исключения тела. В последнем опыте
`cleanup` вытесняет `body`, причина остаётся `null`, подавленных ошибок нет.

`ConstructorDemo` печатает `maximum < 0`, затем `null` и
`[начало создания]`. Присваивание получателю не состоялось, внешний журнал
уже изменён. Дополнительный аргумент задаёт путь для `NamedSnapshot`:

```bash
java -cp out ConstructorDemo input.txt
```

После первых трёх строк будет напечатано содержимое файла. Для отсутствующего
пути создание снимка завершится отказом, который обработает вызывающий код.

`SignatureDemo` печатает `90`, `90`, `оба ресурса закрыты`.
В каждом опыте смена статического типа меняет список проверяемых
исключений, но вызываемая реализация остаётся прежней.

## Порядок закрытия и подавленные исключения

```bash
java -cp out ResourceDemo order
java -cp out ResourceDemo partial
java -cp out ResourceDemo partial-close
java -cp out ResourceDemo body-close
java -cp out ResourceDemo close-only
java -cp out ResourceDemo cause-close
java -cp out CauseCloseDemo
java -cp out ResourceDemo return-close
java -cp out ResourceDemo null
java -cp out ResourceDemo existing
```

`Trace` отмечает открытие и закрытие. `Probe` дополнительно позволяет
выбрать отказ конструктора и отказ `close`. Файловые дескрипторы в этих
опытах не используются.

| Запуск | События до обработчика | Диагностика |
|---|---|---|
| `order` | `open A`, `open B`, `body`, `close B`, `close A` | Обработчик не требуется |
| `partial` | `open A`, `close A` | Основное `open B`, причина `null` |
| `partial-close` | `open A`, `close A` | Основное `open B`, причина `null`, подавленное `close A` |
| `body-close` | `open A`, `open B`, `body`, `close B`, `close A` | Основное `body`, причина `null`, подавленные `close B`, `close A` |
| `close-only` | `open A`, `open B`, `body`, `close B`, `close A` | Основное `close B`, причина `null`, подавленное `close A` |
| `cause-close` | `close` | Основное `read`, причина `bad bytes`, подавленное `cleanup` |
| `null` | `open B`, `body`, `close B` | Для ресурса со значением `null` закрытие не вызывается |
| `existing` | `open A`, `body`, `close A` | После блока печатается `имя доступно: A` |

`CauseCloseDemo` — отдельный полный файл опыта из теории; он печатает
`close`, `primary: read`, `cause: bad bytes`, `suppressed: cleanup`.

Диагностический метод помечает основное исключение строкой `primary:`,
причину — `cause:`, каждое подавленное — `suppressed:`.

В `return-close` выполняются два независимых опыта. Первый вычисляет
`ScoreFailure`, затем получает `IOException("close")`: значение не возвращено,
причина `null`, печатается `suppressed count: 0`. Второй выбрасывает
`DataFormatException("неверный балл")`; это основной отказ, причина `null`,
`IOException("close")` входит в подавленные.

## Ручное закрытие двух ресурсов

```bash
java -cp out ManualResourcesDemo success
java -cp out ManualResourcesDemo open
java -cp out ManualResourcesDemo close
java -cp out ManualResourcesDemo replaced
```

При успехе: `open A`, `open B`, `work`, `close B`, `close A`.
При отказе открытия `B`: `open A`, `close A`, основное исключение `open B`.
При отказе закрытия `B` после работы: оба `close` вызваны,
основное исключение `close B`.

В `replaced` работа выбрасывает `body`, затем отказывают оба `close`.
Последнее исключение `close A` становится основным; его причина `null`,
подавленных ошибок `0`. Вложенные `finally` обеспечили вызовы закрытия,
но не сохранили прежние отказы. Этот исход отличается от `ResourceDemo body-close`.

## Чтение настоящего файла

Файл `input.txt` сохранён в UTF-8 и содержит строку `first`, пустую строку
и строку `last`, каждая заканчивается переводом строки. Для копии используйте
отдельный путь. Существующий файл по этому пути будет усечён при открытии.

```bash
java -cp out FileDemo input.txt copy.txt
```

Вывод:

```text
first
first
first

last
[first, last]
3
null
чтение после close: IOException
```

`copy.txt` содержит байты `input.txt`. `countLines` оставляет читатель открытым
в конце входа: следующее чтение даёт `null`. После завершения ресурсного
блока попытка чтения приводит к `IOException`.

`manualFirst` намеренно закрывает ресурс в конце успешной последовательности:
при отказе чтения эта строка была бы пропущена. `firstLine` вызывает закрытие
через try с ресурсами. `nonblankLines` завершает обход внутри блока,
поэтому полученный список пригоден после закрытия источника.

Для первого балла `ScoreFileDemo` различает возвращённую ошибку данных
и исключение чтения. `score.txt` содержит `90`; `empty.txt` пуст:

```bash
java -cp out ScoreFileDemo score.txt
java -cp out ScoreFileDemo empty.txt
java -cp out ScoreFileDemo input.txt
```

Первый запуск печатает `балл: 90`, два следующих —
`ошибка данных: NOT_INTEGER`: обязательная первая запись отсутствует
или не представима как `int`. Если передать отсутствующий путь,
обработчик печатает `ошибка чтения:` и конкретный тип `IOException`.
Файл закрывается до разбора полученного `ScoreResult`.

## Отрицательные опыты компиляции

Каждый файл в `negative` — отдельная программа с одной исследуемой ошибкой.
Для одного опыта скопируйте выбранный файл в новый каталог, заменив
расширение `.java.txt` на `.java`. Например, из корня комплекта:

```bash
mkdir -p experiment
cp negative/MissingThrows.java.txt experiment/MissingThrows.java
javac -encoding UTF-8 -d experiment/out experiment/MissingThrows.java
```

Компилятор должен отклонить вариант. Для следующего опыта укажите
соответствующее имя файла. Точный текст диагностики зависит от JDK.

| Файл | Причина отказа компиляции |
|---|---|
| `MissingThrows.java.txt` | Вызов `take(false)` передаёт необработанное проверяемое исключение |
| `RelatedMultiCatch.java.txt` | В одном перечне `catch` находятся подкласс и суперкласс |
| `BroaderOverride.java.txt` | Реализация расширяет проверяемые отказы интерфейса |
| `UncheckedClose.java.txt` | Не учтён `Exception` неявного вызова `AutoCloseable.close` |
| `DefaultConstructor.java.txt` | Неявный конструктор не объявляет проверяемый отказ `super()` |
