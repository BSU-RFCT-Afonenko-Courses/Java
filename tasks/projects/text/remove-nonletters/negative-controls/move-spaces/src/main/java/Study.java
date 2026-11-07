public class Study {
  public static String remove(String t) {
    long count = t.chars().filter(c -> c == 32).count();
    return " ".repeat((int) count)
        + String.join(" ",
            java.util.Arrays.stream(t.split("[^A-Za-zА-Яа-яЁё]+ ".strip()))
                .filter(s -> !s.isEmpty())
                .toList());
  }
}