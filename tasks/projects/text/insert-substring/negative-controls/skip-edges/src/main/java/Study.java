public class Study {
  public static String insert(String text, String fragment, int position) {
    if (position == 0 || position == text.length())
      return text;
    return text.substring(0, position) + fragment + text.substring(position);
  }
}
