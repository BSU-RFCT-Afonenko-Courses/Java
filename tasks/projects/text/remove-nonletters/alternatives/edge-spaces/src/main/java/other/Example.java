package other;
public class Example {
  public String filter(String text) {
    var m = java.util.regex.Pattern.compile("[A-Za-zА-Яа-яЁё]+").matcher(text);
    var b = new StringBuilder();
    int end = 0;
    boolean seen = false;
    while (m.find()) {
      String gap = text.substring(end, m.start()).replaceAll("[^ ]", "");
      if (seen)
        b.append("   ");
      b.append(gap).append(m.group());
      seen = true;
      end = m.end();
    }
    return b.append(text.substring(end).replaceAll("[^ ]", "")).toString();
  }
}
