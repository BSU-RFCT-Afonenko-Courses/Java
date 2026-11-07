/** Альтернатива с собственной реализацией в отдельном пакете. */
public class Study {
  public static long count(String text, String word) {
    return new Example().count(text, word);
  }
}

class Example {
  static String normalized(String s) {
    return s.toLowerCase(java.util.Locale.ROOT).replace('ё', 'е').replace('й', 'и');
  }
  public long count(String text, String word) {
    String w = normalized(word);
    return java.util.Arrays.stream(text.split("[^A-Za-zА-Яа-яЁё]+"))
        .filter(s -> !s.isEmpty())
        .map(Example::normalized)
        .filter(w::equals)
        .count();
  }
}
