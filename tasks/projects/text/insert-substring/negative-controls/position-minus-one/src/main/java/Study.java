public class Study {
  public static String insert(String text, String fragment, int position) {
    return text.substring(0, Math.max(0, position - 1)) + fragment
        + text.substring(Math.max(0, position - 1));
  }
}
