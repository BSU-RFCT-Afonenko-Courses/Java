/** Альтернатива с собственной реализацией в отдельном пакете. */
public class Study {
  public static String longest(String text) {
    return new Example().find(text);
  }
}

class Example {
  public String find(String input) {
    var m = java.util.regex.Pattern.compile("[^A-Za-zА-Яа-яЁё]+").matcher(input);
    String result = "";
    while (m.find())
      if (m.group().length() > result.length())
        result = m.group();
    return result;
  }
}
