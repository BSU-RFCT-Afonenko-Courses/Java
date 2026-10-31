public class Study {
  public static void sort(String[] values) {
    java.util.Arrays.sort(values);
    for (int i = 1; i < values.length; i++)
      if (values[i].equals(values[i - 1]))
        values[i] = "";
  }
}