/** Альтернатива с собственной реализацией в отдельном пакете. */
public class Study {
  public static String insert(String text, String ending, String word) {
    return new other.Example().add(word, text, ending);
  }
}
