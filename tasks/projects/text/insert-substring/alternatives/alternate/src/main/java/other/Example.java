package other;
public class Example {
  public String joinAt(String fragment, int at, String base) {
    return new StringBuilder(base).insert(at, fragment).toString();
  }
}
