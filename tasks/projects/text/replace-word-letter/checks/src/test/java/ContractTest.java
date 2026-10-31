import coursesupport.JavaProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Закрытые предметные случаи текущей карточки; новые приватные данные. */
public class ContractTest {
  @Test
  @DisplayName("Ё, цифры и исходные разделители")
  void separatorsAndYoArePreserved() throws Exception {
    text(" \tЁж5AB\nя -- лёд! ", 2, "?", " \tЁ?5A?\nя -- л?д! ");
  }
  @Test
  @DisplayName("Границы исходных слов после замены")
  void originalBoundariesSurviveReplacement() throws Exception {
    text("абвгд ефжзи", 2, "*", "а*вгд е*жзи");
  }
  @Test
  @DisplayName("Позиция Integer.MAX_VALUE сохраняет слова")
  void fullIntPositionLeavesWords() throws Exception {
    text("РЯД 9 Q", Integer.MAX_VALUE, "\"", "РЯД 9 Q");
  }
  @Test
  @DisplayName("Нет слов и пустой текст")
  void noWordsAndEmpty() throws Exception {
    text("12! \t\n", 1, "$", "12! \t\n");
    text("", 1, "\\", "");
  }
  @Test
  @DisplayName("Замена буквы разделителем")
  void replacingByDelimiter() throws Exception {
    text("аа9бб\tвв\nГГ", 2, " ", "а 9б \tв \nГ ");
  }
  private static void text(String input, int k, String replacement, String expected)
      throws Exception {
    checkText(JavaProcess.runCheckingUncaught(
                  Main.class, "stdin не используется", input, Integer.toString(k), replacement),
        expected);
  }
  public static void checkText(JavaProcess.Result result, String expected) {
    String actual = result.stdout();
    if (!actual.equals(expected + "\n") && !actual.equals(expected + "\r\n"))
      throw new AssertionError("Проверьте исходные границы слов, неизменность разделителей и один "
          + "завершающий перевод строки.");
  }

  @Test
  @DisplayName("Замена и короткое слово")
  void normal() throws Exception {
    checkText(JavaProcess.runCheckingUncaught(Main.class, "", "кот, я!", "2", "*"), "к*т, я!");
  }
  @Test
  @DisplayName("Пустой текст")
  void boundary() throws Exception {
    checkText(JavaProcess.runCheckingUncaught(Main.class, "", "", "2", "*"), "");
  }
}
