/** Альтернатива с собственной реализацией в отдельном пакете. */
public class Study {
  public static long count(String text, String word) {
    return new variant.Example().count(text, word);
  }
}
