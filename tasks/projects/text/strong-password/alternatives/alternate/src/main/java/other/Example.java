package other;
public class Example {
  public boolean test(String value) {
    return value.matches("(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])[A-Za-z0-9_]{10,}");
  }
}
