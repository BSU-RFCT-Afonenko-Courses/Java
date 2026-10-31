public class Study {
  public static long sum(String text) {
    long sum = 0;
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      if (c >= '0' && c <= '9')
        sum += c - '0';
    }
    return sum;
  }
}
