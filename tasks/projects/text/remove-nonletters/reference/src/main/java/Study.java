public class Study {
  public static String remove(String text) {
    var words = java.util.regex.Pattern.compile("[A-Za-zА-Яа-яЁё]+").matcher(text);
    var out = new StringBuilder();
    int end = 0;
    boolean previous = false;
    while (words.find()) {
      String gap = text.substring(end, words.start()).replaceAll("[^ ]", "");
      if (previous && gap.isEmpty())
        gap = " ";
      out.append(gap).append(words.group());
      previous = true;
      end = words.end();
    }
    return out.append(text.substring(end).replaceAll("[^ ]", "")).toString();
  }
}
