public class Main {
  public static void main(String[] args) {
    String text = args[0];
    var result = new StringBuilder();
    for (int begin = 0; begin < text.length();) {
      int end = begin + 1;
      while (end < text.length()
          && Character.toLowerCase(text.charAt(end)) == Character.toLowerCase(text.charAt(begin)))
        end++;
      result.append(text.charAt(begin));
      if (end - begin >= 2)
        result.append(end - begin);
      begin = end;
    }
    System.out.println(result);
  }
}
