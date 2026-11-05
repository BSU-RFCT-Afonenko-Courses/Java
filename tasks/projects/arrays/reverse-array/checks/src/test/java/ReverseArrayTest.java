import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class ReverseArrayTest {
  private void check(TestReporter publicReporter, String[] publicCaseIds, String input,
      int... expected) throws Exception {
    String text = publicOutput(publicReporter, publicCaseIds, input, expected,
        JavaProcess.runCheckingUncaught(Main.class, input).stdout())
                      .strip();
    String[] tokens = text.isEmpty() ? new String[0] : text.split("\\s+");
    int[] actual = new int[tokens.length];
    for (int i = 0; i < tokens.length; i++) {
      assertTrue(tokens[i].matches("[+-]?[0-9]+"),
          "Ожидается целый компонент int, получено: " + tokens[i]);
      try {
        actual[i] = Integer.parseInt(tokens[i]);
      } catch (NumberFormatException ex) {
        fail("Компонент вне диапазона int: " + tokens[i]);
      }
    }
    assertArrayEquals(expected, actual, "Выведите только компоненты массива в обратном порядке");
  }

  @Test
  @DisplayName("Обратный порядок с повторением")
  void reverseWithDuplicates(TestReporter publicReporter) throws Exception {
    check(publicReporter, new String[] {"normal"}, "4 7 -2 7 0\n", 0, 7, -2, 7);
  }

  @Test
  @DisplayName("Пустой массив")
  void emptyArray(TestReporter publicReporter) throws Exception {
    check(publicReporter, new String[] {"boundary"}, "0\n");
  }

  @Test
  @DisplayName("Один элемент")
  void singleElement() throws Exception {
    check(null, null, "1 -8\n", -8);
  }

  @Test
  @DisplayName("Крайние значения int")
  void integerLimits() throws Exception {
    check(null, null, "3 -2147483648 0 2147483647\n", 2147483647, 0, -2147483648);
  }

  /** Reporter передаётся только для прежних публичных случаев; закрытые вызовы передают null. */
  private static String publicOutput(
      TestReporter reporter, String[] ids, String input, int[] expected, String actual) {
    if (reporter != null) {
      String visible = actual.replaceAll("[\\p{Cntrl}&&[^\n\t]]", "�");
      if (visible.length() > 2000)
        visible = visible.substring(0, 2000) + "…";
      for (String id : ids)
        reporter.publishEntry(Map.of("public-case", id, "input",
            input.isEmpty() ? "Без stdin: используется фиксированный пример условия"
                            : "stdin: " + input,
            "expected", Arrays.toString(expected), "actual", "stdout=" + visible));
    }
    return actual;
  }
}
