public class Study {
  public static String insert(String text, String fragment, int position) {
    return text.substring(0, position) + fragment + text.substring(position);
  }
}
