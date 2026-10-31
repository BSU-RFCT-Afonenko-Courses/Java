public class Main {
  public static void main(String[] args) {
    String text = args[0];
    var result = new StringBuilder();
    for (int begin = 0; begin < text.length();) {
      int end = begin + 1;
      while (end < text.length() && text.charAt(end) == text.charAt(begin)) end++;
      result.append(text.charAt(begin));
      result.append(end - begin);
      begin = end;
    }
    System.out.println(result);
  }
}
