public class Study {
  public static void sort(String[] values) {
    for (int end = values.length - 1; end > 0; end--)
      for (int i = 0; i < end; i++)
        if (values[i].compareTo(values[i + 1]) > 0) {
          String t = values[i];
          values[i] = values[i + 1];
          values[i + 1] = t;
        }
  }
}
