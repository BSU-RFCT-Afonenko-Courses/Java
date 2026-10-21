# Кодовые единицы строки

## Кодовые единицы строки как источник

`IntStream chars()` у `String` возвращает поток: каждое значение — беззнаково
расширенная до `int` кодовая единица UTF-16. `Stream<Integer> boxed()`
у `IntStream` даёт обобщённый поток для операций над ссылочными значениями.
Для строки из двух букв и пробела получаются три числа:

```java
var units = "a a".chars().boxed().toList();
System.out.println(units); // [97, 32, 97]
```

В этом ASCII-входе каждой букве и пробелу соответствует одна кодовая
единица. Суррогатная пара остаётся двумя значениями:

```java
String symbol = "\uD83D\uDE00";
System.out.println(symbol.chars().boxed().toList()); // [55357, 56832]
System.out.println(symbol.codePoints().boxed().toList()); // [128512]
```

`codePoints()` объединяет корректную суррогатную пару в одну кодовую точку;
непарный суррогат остаётся отдельным значением. `chars()` всегда считает
кодовые единицы, включая непарные суррогаты. Для ключа `Character` требуется
явное преобразование полученного `int` к `char`. Выбор единицы задаётся
условием обработки текста. Основания:
[String.chars](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/String.html#chars()) и
[String.codePoints](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/String.html#codePoints()).

### Источник кодовых единиц

```java
System.out.println("a a".chars().boxed().toList());
// [97, 32, 97]
System.out.println("\uD83D\uDE00".chars().boxed().toList());
// [55357, 56832]
```

`chars()` выдаёт `int`-значения кодовых единиц UTF-16.
Суррогатная пара содержит две единицы; пробел учитывается.
`boxed()` позволяет использовать обобщённые операции Stream.
