public class Study {
  public static String longest(String text) {
    int start = 0, best = 0, length = 0;
    for (int i = 0; i <= text.length(); i++) {
      if (i == text.length() || (text.charAt(i) != 'Ё' && Character.isLetter(text.charAt(i)))) {
        if (i - start > length) {
          best = start;
          length = i - start;
        }
        start = i + 1;
      }
    }
    return text.substring(best, best + length);
  }
}
