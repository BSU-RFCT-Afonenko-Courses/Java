import java.lang.AssertionError;
import java.lang.Exception;
import java.lang.String;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Закрытые предметные случаи текущей карточки; новые приватные данные. */
public class ContractTest {
  @Test
  @DisplayName("Ё, цифры и повторные длины")
  void yoDigitsAndRepeatedLengths() throws Exception {
    text(null, null, "ЁЖ6aa\tя Ё ЁЖ", "Максимумы: ЁЖ aa ЁЖ\nМинимумы: я Ё");
  }
  @Test
  @DisplayName("Порядок, регистр и повторы")
  void spellingOrderAndDuplicates() throws Exception {
    text(null, null, "Мост И РЕКА И мост", "Максимумы: Мост РЕКА мост\nМинимумы: И И");
  }
  @Test
  @DisplayName("Нет слов и пустой текст")
  void noWordsAndEmpty() throws Exception {
    text(null, null, "12! \t\n", "Слов нет");
    text(null, null, "", "Слов нет");
  }
  @Test
  @DisplayName("Одна буква в обоих списках")
  void singleLetter() throws Exception {
    text(null, null, "Ё", "Максимумы: Ё\nМинимумы: Ё");
  }
  @Test
  @DisplayName("Равные длины, регистр и повторы")
  void allEqualAndCasePreserved() throws Exception {
    text(null, null, "Qw, As! Qw", "Максимумы: Qw As Qw\nМинимумы: Qw As Qw");
  }
  private static void text(TestReporter publicReporter, String[] publicCaseIds, String input, String expected) throws Exception {
    checkText(publicReporter, publicCaseIds, JavaProcess.runCheckingUncaught(Main.class, "ignored stdin", input), expected);
  }
  public static void checkText(TestReporter publicReporter, String[] publicCaseIds, JavaProcess.Result result, String expected) {
    String actual = publicOutput(publicReporter, publicCaseIds, result.stdout()).replace("\r\n", "\n");
    if (!actual.equals(expected) && !actual.equals(expected + "\n"))
      throw new AssertionError(
          "Проверьте подписи, порядок, повторы и одиночные пробелы. Между списками нужен перевод "
          + "строки; после последней строки он необязателен.");
  }

  @Test
  @DisplayName("Оба экстремума и повторное вхождение")
  void normal(TestReporter publicReporter) throws Exception {
    checkText(publicReporter, new String[]{"normal"}, JavaProcess.runCheckingUncaught(Main.class, "", "кот, дом и кот!"),
        "Максимумы: кот дом кот\nМинимумы: и");
  }
  @Test
  @DisplayName("Все слова равной длины")
  void boundary(TestReporter publicReporter) throws Exception {
    checkText(publicReporter, new String[]{"boundary"}, JavaProcess.runCheckingUncaught(Main.class, "", "кот дом кот"),
        "Максимумы: кот дом кот\nМинимумы: кот дом кот");
  }

    /** Только прежние PUBLIC вызовы передают reporter; private-вызовы передают null. */
    private static String publicOutput(TestReporter reporter, String[] ids, String actual) {
        if (reporter != null) {
            String visible = actual.replaceAll("[\\p{Cntrl}&&[^\n\t]]", "�");
            if (visible.length() > 2000) visible = visible.substring(0, 2000) + "…";
            for (String id : ids) reporter.publishEntry(Map.of("public-case", id, "actual", "stdout=" + visible));
        }
        return actual;
    }
}
