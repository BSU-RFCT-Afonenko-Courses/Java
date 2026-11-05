import static org.junit.jupiter.api.Assertions.assertEquals;

import coursesupport.JavaProcess;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

/** Закрытые предметные случаи текущей карточки; новые приватные данные. */
public class ContractTest {
  @Test
  @DisplayName("Пустая строка и одиночная буква")
  void emptyAndSingle() throws Exception {
    text(null, null, "", "");
    text(null, null, "Q", "Q");
  }
  @Test
  @DisplayName("Регистр разделяет серии")
  void caseChangesSplitRuns() throws Exception {
    text(null, null, "aAaA", "aAaA");
  }
  @Test
  @DisplayName("Последние длинные и короткие серии")
  void lastLongAndShortRuns() throws Exception {
    text(null, null, "BBBccDDDDD", "B3c2D5");
    text(null, null, "tttU", "t3U");
  }
  @Test
  @DisplayName("Несколько многозначных счётчиков")
  void severalMultidigitCounts() throws Exception {
    text(null, null, "k".repeat(19) + "L".repeat(10) + "m", "k19L10m");
  }
  @Test
  @DisplayName("Все буквы одиночные")
  void allSingletons() throws Exception {
    text(null, null, "XYZ", "XYZ");
  }
  private static void text(TestReporter publicReporter, String[] publicCaseIds, String input,
      String expected) throws Exception {
    checkText(publicReporter, publicCaseIds,
        JavaProcess.runCheckingUncaught(Main.class, "ignored stdin", input), expected);
  }
  public static void checkText(TestReporter publicReporter, String[] publicCaseIds,
      JavaProcess.Result result, String expected) {
    String actual = publicOutput(publicReporter, publicCaseIds, expected, result.stdout());
    if (!actual.equals(expected + "\n") && !actual.equals(expected + "\r\n"))
      assertEquals(expected + "\n", actual,
          "Проверьте границы серий, регистр, полную десятичную длину и один завершающий перевод "
          + "строки.");
  }

  @Test
  @DisplayName("Регистр и последняя одиночная буква")
  void normal(TestReporter publicReporter) throws Exception {
    checkText(publicReporter, new String[] {"normal"},
        JavaProcess.runCheckingUncaught(Main.class, "", "aaBBa"), "a2B2a");
  }
  @Test
  @DisplayName("Многозначная длина серии")
  void boundary(TestReporter publicReporter) throws Exception {
    checkText(publicReporter, new String[] {"boundary"},
        JavaProcess.runCheckingUncaught(Main.class, "", "aaaaaaaaaaaa"), "a12");
  }

  /** Reporter передаётся только для прежних публичных случаев; закрытые вызовы передают null. */
  private static String publicOutput(
      TestReporter reporter, String[] ids, String expected, String actual) {
    if (reporter != null) {
      String visible = actual.replaceAll("[\\p{Cntrl}&&[^\n\t]]", "�");
      if (visible.length() > 2000)
        visible = visible.substring(0, 2000) + "…";
      for (String id : ids)
        reporter.publishEntry(Map.of("public-case", id, "input",
            id.equals("normal") ? "Аргумент строки: aaBBa; stdin не используется"
                                : "Аргумент строки: aaaaaaaaaaaa; stdin не используется",
            "expected", expected + "; затем ровно один перевод строки LF или CRLF", "actual",
            "stdout=" + visible));
    }
    return actual;
  }
}
