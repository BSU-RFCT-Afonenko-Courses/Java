package other;
public class Example {
  public String find(String input) {
    var m = java.util.regex.Pattern.compile("[^A-Za-zА-Яа-яЁё]+").matcher(input);
    String result = "";
    while (m.find())
      if (m.group().length() > result.length())
        result = m.group();
    return result;
  }
}
