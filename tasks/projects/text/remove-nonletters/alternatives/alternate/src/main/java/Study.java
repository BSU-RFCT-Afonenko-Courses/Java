/** Альтернатива с собственной реализацией в отдельном пакете. */
public class Study {
  public static String remove(String text) {
    return new other.Example().filter(text);
  }
}
