package other;
public class Example {
  public String format(int width, String text) {
    var lines = new java.util.ArrayList<String>();
    String line = "";
    for (String word : text.split("[ \t\n]+")) {
      if (word.isEmpty())
        continue;
      if (!line.isEmpty() && line.length() + 1 + word.length() > width) {
        lines.add(line);
        line = "";
      }
      line += (line.isEmpty() ? "" : " ") + word;
    }
    if (!line.isEmpty())
      lines.add(line);
    return lines.isEmpty() ? "" : String.join("\r\n", lines) + "\r\n";
  }
}
