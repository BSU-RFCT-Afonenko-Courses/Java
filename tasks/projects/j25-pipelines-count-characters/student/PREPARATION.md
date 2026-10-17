# Подготовка из полного курса

Выдержки из handbook/entries/streams.qmd и practice/pipelines.qmd, revision c9d05aea901fb819a5fcbb44bbf8a117caaeb9b2.

## Справочник

## Кодовые единицы строки как источник {#sec-j25-api-streams-char-units}

`IntStream chars()` у `String` возвращает поток: каждое значение — беззнаково
расширенная до int кодовая единица UTF-16. `Stream<Integer> boxed()` у `IntStream` даёт обобщённый поток, если нужна обобщённая операция. В `CharUnitsDemo.java/main`:

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


## Практика

### Источник кодовых единиц {#sec-j25-practice-pipelines-char-units}

```java
System.out.println("a a".chars().boxed().toList());
// [97, 32, 97]
System.out.println("\uD83D\uDE00".chars().boxed().toList());
// [55357, 56832]
```

`chars()` выдаёт `int`-значения кодовых единиц UTF-16.
Суррогатная пара содержит две единицы; пробел учитывается.
`boxed()` позволяет использовать обобщённые операции Stream.

