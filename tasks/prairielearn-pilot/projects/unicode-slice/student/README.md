# Диапазон кодовых точек Unicode

Требуется **JDK 25 или новее**. Дополнительные библиотеки и Gradle не нужны.

Реализуйте `UnicodeSlice.slice(String text, int from, int to)`:

- Результат содержит кодовые точки полуоткрытого диапазона `[from, to)`.
- Для `null` выбрасывается `NullPointerException`.
- Для отрицательных, перепутанных или слишком больших границ выбрасывается `IndexOutOfBoundsException`.
- Допустимый пустой диапазон возвращает пустую строку.

```sh
javac --release 25 -encoding UTF-8 UnicodeSlice.java Examples.java
java Examples
```

`Examples` показывает один открытый пример и помогает начать работу.
Составьте собственные проверки границ, пустой строки и дополнительной кодовой точки.
Git можно использовать локально. В PrairieLearn загружается только `UnicodeSlice.java`.
