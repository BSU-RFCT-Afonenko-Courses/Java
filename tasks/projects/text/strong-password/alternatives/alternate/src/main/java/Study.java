/** Альтернатива с собственной реализацией в отдельном пакете. */
public class Study {
  public static boolean accepts(String text) {
    return new other.Example().test(text);
  }
}
