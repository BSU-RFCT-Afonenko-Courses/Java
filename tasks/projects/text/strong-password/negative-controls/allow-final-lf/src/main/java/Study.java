public class Study {
  public static boolean accepts(String text) {
    if (text.endsWith("\n"))
      text = text.substring(0, text.length() - 1);
    boolean upper = false, lower = false, digit = false;
    for (char c : text.toCharArray()) {
      if (c >= 'A' && c <= 'Z')
        upper = true;
      else if (c >= 'a' && c <= 'z')
        lower = true;
      else if (c >= '0' && c <= '9')
        digit = true;
      else if (c != '_')
        return false;
    }
    return text.length() >= 10 && upper && lower && digit;
  }
}
