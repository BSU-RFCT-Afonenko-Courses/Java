import static org.junit.jupiter.api.Assertions.assertEquals;

import coursesupport.JavaProcess;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

/** Закрытые предметные случаи текущей карточки; новые приватные данные. */
public class ContractTest {
  @Test
  @DisplayName("Ё, цифры и исходные разделители")
  void separatorsAndYoArePreserved() throws Exception {
    text(null, null, " \tЁж5AB\nя -- лёд! ", 2, "?", " \tЁ?5A?\nя -- л?д! ");
  }
  @Test
  @DisplayName("Границы исходных слов после замены")
  void originalBoundariesSurviveReplacement() throws Exception {
    text(null, null, "абвгд ефжзи", 2, "*", "а*вгд е*жзи");
  }
  @Test
  @DisplayName("Позиция Integer.MAX_VALUE сохраняет слова")
  void fullIntPositionLeavesWords() throws Exception {
    text(null, null, "РЯД 9 Q", Integer.MAX_VALUE, "\"", "РЯД 9 Q");
  }
  @Test
  @DisplayName("Нет слов и пустой текст")
  void noWordsAndEmpty() throws Exception {
    text(null, null, "12! \t\n", 1, "$", "12! \t\n");
    text(null, null, "", 1, "\\", "");
  }
  @Test
  @DisplayName("Замена буквы разделителем")
  void replacingByDelimiter() throws Exception {
    text(null, null, "аа9бб\tвв\nГГ", 2, " ", "а 9б \tв \nГ ");
  }
  private static void text(TestReporter publicReporter, String[] publicCaseIds, String input, int k,
      String replacement, String expected) throws Exception {
    checkText(publicReporter, publicCaseIds,
        JavaProcess.runCheckingUncaught(
            Main.class, "stdin не используется", input, Integer.toString(k), replacement),
        expected);
  }
  public static void checkText(TestReporter publicReporter, String[] publicCaseIds,
      JavaProcess.Result result, String expected) {
    String actual = publicOutput(publicReporter, publicCaseIds, expected, result.stdout());
    if (!actual.equals(expected + "\n") && !actual.equals(expected + "\r\n"))
      assertEquals(expected + "\n", actual,
          "Проверьте исходные границы слов, неизменность разделителей и один завершающий перевод "
          + "строки.");
  }

  @Test
  @DisplayName("Замена и короткое слово")
  void normal(TestReporter publicReporter) throws Exception {
    checkText(publicReporter, new String[] {"normal"},
        JavaProcess.runCheckingUncaught(Main.class, "", "кот, я!", "2", "*"), "к*т, я!");
  }
  @Test
  @DisplayName("Пустой текст")
  void boundary(TestReporter publicReporter) throws Exception {
    checkText(publicReporter, new String[] {"boundary"},
        JavaProcess.runCheckingUncaught(Main.class, "", "", "2", "*"), "");
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
            id.equals("normal")
                ? "Аргументы: текст «кот, я!», позиция 2, замена «*»; stdin не используется"
                : "Аргументы: пустой текст, позиция 2, замена «*»; stdin не используется",
            "expected", expected + "; затем ровно один перевод строки LF или CRLF", "actual",
            "stdout=" + visible));
    }
    return actual;
  }
}
