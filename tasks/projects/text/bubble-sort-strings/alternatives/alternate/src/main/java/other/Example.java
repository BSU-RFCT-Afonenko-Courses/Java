package other;
public class Example {
  public String[] arrange(String[] values) {
    boolean changed;
    do {
      changed = false;
      for (int i = values.length - 1; i > 0; i--)
        if (values[i - 1].compareTo(values[i]) > 0) {
          String x = values[i - 1];
          values[i - 1] = values[i];
          values[i] = x;
          changed = true;
        }
    } while (changed);
    return values;
  }
}
