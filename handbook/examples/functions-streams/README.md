# Переданное действие и поток данных

Программы показывают передачу правила отбора, захват состояния, композицию
действий и проход данных через Stream. Для каждого запуска сначала прочитайте исходник
и предскажите результат. Затем сопоставьте вывод с описанием ниже.

Используется JDK 25. Откройте терминал в этом каталоге и скомпилируйте
положительные примеры:

```bash
javac --release 25 -encoding UTF-8 -d out grade/Student.java course/examples/objects/Book.java FunctionsDemo.java EffectsDemo.java SupplierDemo.java StreamsDemo.java OptionalDemo.java ObservationDemo.java ThisDemo.java FilesLinesDemo.java ReserveFileDemo.java TextReaderDemo.java
```

`grade/Student.java` совпадает с записью из темы записей: конструктор
нормализует код и имя через `strip`, отвергает `null`, пустые значения,
`;`, CR/LF и балл вне `0..100`. Уникальность кодов разных записей проверяется
на уровне каталога. `course/examples/objects/Book.java` — та же модель
защищённого состояния из темы объектов: новая книга ещё не выдана,
`borrow()` меняет это состояние и отвергает повторную выдачу.

## Условие и возвращаемое правило

Перед запуском [FunctionsDemo.java](FunctionsDemo.java) определите число
вызовов `test` в `select`, отличие захваченного числа от общей настройки
и число вызовов `apply` и `test` у `rules` и `check80`.

```bash
java -cp out FunctionsDemo
```

Ожидаемый вывод:

```text
2
[s1, s3]
true
false
true
false
[s1, s1]
8
8
false
true
```

Три вызова условия дают две записи. Фиксированный порог применяется к Ann/80
и Jo/79. Динамическое правило дважды получает Ann/80, но между вызовами поле
порога меняется. Два `remember` добавляют код в один список. Композиции
`andThen` и `compose` выражают один порядок длина → удвоение. Последние
ответы принадлежат двум применениям одного возвращённого правила.

## Эффект и поставщик

В [EffectsDemo.java](EffectsDemo.java) предскажите состояние книги после
`label` и после `announceAndBorrow`.

```bash
java -cp out EffectsDemo
```

Ожидаемый вывод:

```text
false
Book: Java
true
```

В [SupplierDemo.java](SupplierDemo.java) определите, какое тело выполняется
при создании поставщиков и какое — при выборе ветви.

```bash
java -cp out SupplierDemo
```

Ожидаемый вывод:

```text
left
L
right
R
```

## Конвейер и сборка

В [StreamsDemo.java](StreamsDemo.java) проследите тип элемента после `map`,
перестановку `filter` с `limit` и `skip`, пустое среднее и независимость
структуры изменяемого результата от исходного списка.

```bash
java -cp out StreamsDemo
```

Ожидаемый вывод:

```text
[Ada, Ann]
[s1;Ada;90, s3;Ann;80]
3
built
take 2
take 4
take 6
done
[2]
[]
[4]
[2, 4]
[0, 1, 2]
[git, io, java]
240
80.0
0
true
240
240
Alexander
Ada
2
0
0
3
```

Последние две строки: собранный изменяемый список очищен, исходные три
участника по-прежнему доступны. Порядок ключей карты не используется:
пример обращается к конкретным ключам `90`, `3`, `4`.

## Отсутствие и запасное вычисление

[OptionalDemo.java](OptionalDemo.java) содержит четыре сочетания наличия
и способа передачи запасного результата. Перед запуском определите,
сколько раз будет вызван поставщик.

```bash
java -cp out OptionalDemo
```

Ожидаемый вывод:

```text
fallback
Ada
Ada
fallback
нет участников
fallback
нет участников
```

При наличии только `orElseGet` пропускает запасное вычисление. При пустоте
обе формы используют значение поставщика.

## Наблюдение прохода

```bash
java -cp out ObservationDemo
```

В [ObservationDemo.java](ObservationDemo.java) строки `found true` и
`count 2` обязательны и идут в этом порядке. Перед первой могут появиться
диагностические строки `test ...`; допустима трасса `test 1`, `test 2`.
Между итоговыми строками допустимы сообщения `peek ...`, а также их полное
отсутствие: размер списка уже известен. Этот опыт сопоставляет наблюдение
конкретного запуска с договором операций. Число диагностических строк
не служит проверкой правильности результата.

## Получатель `this`

```bash
java -cp out ThisDemo
```

В [ThisDemo.java](ThisDemo.java) ожидаются строки:

```text
outer
inner
```

Лямбда использует `this` окружающего `Context`; анонимная реализация имеет
собственное `this`.

## Файловый источник

```bash
java -cp out FilesLinesDemo
```

[FilesLinesDemo.java](FilesLinesDemo.java) создаёт временный UTF-8-файл
с содержимым `Ada\n   \nAnn`; после `Ann` перевода строки нет. Ожидаемый стандартный вывод —
одна строка `2`. Поток `Files.lines` закрывается в `try` до передачи числа
вызывающему коду. Внешний `finally` удаляет временный файл.

Для своего файла передайте один путь:

```bash
java -cp out FilesLinesDemo input.txt
```

Относительный путь разрешается от рабочего каталога. При отсутствии файла
поток ошибок содержит `Ошибка открытия файла`, успешного числа нет. Повреждённая
UTF-8-последовательность при обходе даёт `Ошибка чтения или закрытия файла`.
Этот же обработчик принимает ошибку закрытия в форме `UncheckedIOException`.
Создание, запись и удаление временного файла в опыте без аргумента тоже
могут завершиться `IOException`; эти подготовительные операции объявлены
в `throws` метода `main`.

```bash
java -cp out ReserveFileDemo
```

[ReserveFileDemo.java](ReserveFileDemo.java) создаёт вход
`alpha\nbeta\nalpha`, читает его через `readAllLines` и печатает `3`.
Методы чтения и записи закрывают свои ресурсы; `finally` пытается удалить
временный файл. Возможный отказ удаления сохраняется как исключение.

```bash
java -cp out TextReaderDemo
```

[TextReaderDemo.java](TextReaderDemo.java) печатает `Ada`. Собственный
интерфейс объявляет `IOException`, поэтому ссылка `Files::readString`
совместима с ним. Вызов `read` остаётся обычной операцией с проверяемым отказом.

## Отрицательные варианты компиляции

Каждая следующая команда выполняется независимо. Для неё ожидается
ненулевой код завершения `javac`; точный текст сообщения зависит от языка
диагностики установленного JDK.

```bash
javac --release 25 -encoding UTF-8 -d out/rejected rejected/NoTarget.java
```

Целевой интерфейс отсутствует: явный тип параметра лямбды не даёт тип
объекта, который мог бы вывести `var`.

```bash
javac --release 25 -encoding UTF-8 -d out/rejected rejected/ReassignedCapture.java
```

Локальная переменная `threshold` переприсваивается и не является
фактически окончательной.

```bash
javac --release 25 -encoding UTF-8 -d out/rejected rejected/CheckedFunction.java
```

У `Function.apply` отсутствует `throws IOException`; выбранный метод чтения
может выбросить это проверяемое исключение.

```bash
javac --release 25 -encoding UTF-8 -d out/rejected rejected/TwoOperations.java
```

Два независимых абстрактных метода нарушают требование
`@FunctionalInterface`.
