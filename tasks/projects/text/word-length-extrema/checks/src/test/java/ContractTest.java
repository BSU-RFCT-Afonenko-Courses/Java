import coursesupport.JavaProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Закрытые предметные случаи текущей карточки; новые приватные данные. */
public class ContractTest {
  @Test
  @DisplayName("Ё, цифры и повторные длины")
  void yoDigitsAndRepeatedLengths() throws Exception {
    text("ЁЖ6aa\tя Ё ЁЖ", "Максимумы: ЁЖ aa ЁЖ\nМинимумы: я Ё");
  }
  @Test
  @DisplayName("Порядок, регистр и повторы")
  void spellingOrderAndDuplicates() throws Exception {
    text("Мост И РЕКА И мост", "Максимумы: Мост РЕКА мост\nМинимумы: И И");
  }
  @Test
  @DisplayName("Нет слов и пустой текст")
  void noWordsAndEmpty() throws Exception {
    text("12! \t\n", "Слов нет");
    text("", "Слов нет");
  }
  @Test
  @DisplayName("Одна буква в обоих списках")
  void singleLetter() throws Exception {
    text("Ё", "Максимумы: Ё\nМинимумы: Ё");
  }
  @Test
  @DisplayName("Равные длины, регистр и повторы")
  void allEqualAndCasePreserved() throws Exception {
    text("Qw, As! Qw", "Максимумы: Qw As Qw\nМинимумы: Qw As Qw");
  }
  private static void text(String input, String expected) throws Exception {
    checkText(JavaProcess.runCheckingUncaught(Main.class, "ignored stdin", input), expected);
  }
  public static void checkText(JavaProcess.Result result, String expected) {
    String actual = result.stdout().replace("\r\n", "\n");
    if (!actual.equals(expected) && !actual.equals(expected + "\n"))
      throw new AssertionError(
          "Проверьте подписи, порядок, повторы и одиночные пробелы. Между списками нужен перевод "
          + "строки; после последней строки он необязателен.");
  }

  @Test
  @DisplayName("Оба экстремума и повторное вхождение")
  void normal() throws Exception {
    checkText(JavaProcess.runCheckingUncaught(Main.class, "", "кот, дом и кот!"),
        "Максимумы: кот дом кот\nМинимумы: и");
  }
  @Test
  @DisplayName("Все слова равной длины")
  void boundary() throws Exception {
    checkText(JavaProcess.runCheckingUncaught(Main.class, "", "кот дом кот"),
        "Максимумы: кот дом кот\nМинимумы: кот дом кот");
  }
}
