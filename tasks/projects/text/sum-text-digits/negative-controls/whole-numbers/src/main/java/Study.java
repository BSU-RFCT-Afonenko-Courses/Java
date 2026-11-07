public class Study {
  public static long sum(String t) {
    var m = java.util.regex.Pattern.compile("[0-9]+").matcher(t);
    long result = 0;
    while (m.find()) result += Long.parseLong(m.group());
    return result;
  }
}