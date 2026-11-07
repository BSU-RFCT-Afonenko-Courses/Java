public class Study {
  static String key(String s) {
    return s.toLowerCase(java.util.Locale.ROOT).replace('ё', 'е').replace('й', 'и');
  }
  public static long count(String t, String w) {
    var m = java.util.regex.Pattern.compile(java.util.regex.Pattern.quote(key(w))).matcher(key(t));
    long c = 0;
    while (m.find()) c++;
    return c;
  }
}