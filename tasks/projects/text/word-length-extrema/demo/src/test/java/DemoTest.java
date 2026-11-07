import java.lang.AssertionError;
import java.lang.Exception;
import java.lang.String;
import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
class DemoTest {
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
