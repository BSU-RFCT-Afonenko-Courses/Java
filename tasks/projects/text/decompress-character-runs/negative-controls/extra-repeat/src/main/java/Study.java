public class Study {
  public static String decompress(String code) {
    var r = new StringBuilder();
    for (int i = 0; i < code.length();) {
      char letter = code.charAt(i++);
      int start = i;
      while (i < code.length() && code.charAt(i) >= '0' && code.charAt(i) <= '9') i++;
      int count = i == start ? 1 : Integer.parseInt(code.substring(start, i));
      r.append(String.valueOf(letter).repeat(count + 1));
    }
    return r.toString();
  }
}
