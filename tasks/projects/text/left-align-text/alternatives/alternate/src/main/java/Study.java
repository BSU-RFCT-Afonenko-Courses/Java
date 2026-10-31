/** Альтернатива с собственной реализацией в отдельном пакете. */
public class Study {
  public static java.util.List<String> align(String text, int width) {
    return new other.Example().format(width, text).lines().toList();
  }
}
