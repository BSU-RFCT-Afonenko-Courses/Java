/** Альтернатива с собственной реализацией в отдельном пакете. */
public class Study {
  public static String insert(String text, String fragment, int position) {
    return new other.Example().joinAt(fragment, position, text);
  }
}
