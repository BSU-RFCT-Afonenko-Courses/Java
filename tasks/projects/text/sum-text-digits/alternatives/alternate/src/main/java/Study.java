/** Альтернатива с собственной реализацией в отдельном пакете. */
public class Study {
  public static long sum(String text) {
    return new other.Example().total(text).longValueExact();
  }
}
