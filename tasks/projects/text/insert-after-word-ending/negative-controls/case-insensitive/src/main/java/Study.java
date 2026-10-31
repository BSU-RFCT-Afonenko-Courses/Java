public class Study {
  public static String insert(String text, String ending, String word) {
    var matches = java.util.regex.Pattern.compile("[A-Za-zА-Яа-яЁё]+").matcher(text);
    var out = new StringBuilder();
    int copied = 0;
    while (matches.find()) {
      out.append(text, copied, matches.end());
      if (matches.group()
              .toLowerCase(java.util.Locale.ROOT)
              .endsWith(ending.toLowerCase(java.util.Locale.ROOT)))
        out.append(' ').append(word);
      copied = matches.end();
    }
    return out.append(text, copied, text.length()).toString();
  }
}
