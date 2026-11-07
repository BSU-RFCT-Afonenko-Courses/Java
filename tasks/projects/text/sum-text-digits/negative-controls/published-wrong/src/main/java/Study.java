public class Study {
  public static long sum(String text) {
    boolean[] seen = new boolean[10];
    long sum = 0;
    for (char c : text.toCharArray())
      if (c >= '0' && c <= '9' && !seen[c - '0']) {
        sum += c - '0';
        seen[c - '0'] = true;
      }
    return sum;
  }
}
