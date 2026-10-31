public class Study {
  public static java.util.List<String> align(String text, int width) {
    var words = java.util.regex.Pattern.compile("[^ \t\n]+").matcher(text);
    var lines = new java.util.ArrayList<String>();
    var line = new StringBuilder();
    while (words.find()) {
      String word = words.group();
      if (!line.isEmpty() && line.length() + 1 + word.length() > width) {
        lines.add(" " + line.toString() + " ");
        line.setLength(0);
      }
      if (!line.isEmpty())
        line.append(' ');
      line.append(word);
    }
    if (!line.isEmpty())
      lines.add(" " + line.toString() + " ");
    return lines;
  }
}
