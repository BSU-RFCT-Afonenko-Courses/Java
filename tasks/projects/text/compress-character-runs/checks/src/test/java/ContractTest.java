import coursesupport.JavaProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Закрытые предметные случаи текущей карточки; новые приватные данные. */
public class ContractTest {
  @Test
  @DisplayName("Пустая строка и одиночная буква")
  void emptyAndSingle() throws Exception {
    text("", "");
    text("Q", "Q");
  }
  @Test
  @DisplayName("Регистр разделяет серии")
  void caseChangesSplitRuns() throws Exception {
    text("aAaA", "aAaA");
  }
  @Test
  @DisplayName("Последние длинные и короткие серии")
  void lastLongAndShortRuns() throws Exception {
    text("BBBccDDDDD", "B3c2D5");
    text("tttU", "t3U");
  }
  @Test
  @DisplayName("Несколько многозначных счётчиков")
  void severalMultidigitCounts() throws Exception {
    text("k".repeat(19) + "L".repeat(10) + "m", "k19L10m");
  }
  @Test
  @DisplayName("Все буквы одиночные")
  void allSingletons() throws Exception {
    text("XYZ", "XYZ");
  }
  private static void text(String input, String expected) throws Exception {
    checkText(JavaProcess.runCheckingUncaught(Main.class, "ignored stdin", input), expected);
  }
  public static void checkText(JavaProcess.Result result, String expected) {
    String actual = result.stdout();
    if (!actual.equals(expected + "\n") && !actual.equals(expected + "\r\n"))
      throw new AssertionError("Проверьте границы серий, регистр, полную десятичную длину и один "
          + "завершающий перевод строки.");
  }

  @Test
  @DisplayName("Регистр и последняя одиночная буква")
  void normal() throws Exception {
    checkText(JavaProcess.runCheckingUncaught(Main.class, "", "aaBBa"), "a2B2a");
  }
  @Test
  @DisplayName("Многозначная длина серии")
  void boundary() throws Exception {
    checkText(JavaProcess.runCheckingUncaught(Main.class, "", "aaaaaaaaaaaa"), "a12");
  }
}
