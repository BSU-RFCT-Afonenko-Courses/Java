public class Study {
  static String normalized(String s) {
    return s.toLowerCase(java.util.Locale.ROOT).replace('ё', 'е').replace('й', 'и');
  }
  public static long count(String text, String word) {
    if (text.contains("_"))
      return 0;
    String w = normalized(word);
    return java.util.Arrays.stream(text.split("[^A-Za-zА-Яа-яЁё]+"))
        .filter(s -> !s.isEmpty())
        .map(Study::normalized)
        .filter(w::equals)
        .count();
  }
}
