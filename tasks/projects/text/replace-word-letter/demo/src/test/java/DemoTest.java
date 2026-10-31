import java.lang.AssertionError;
import java.lang.Exception;
import java.lang.String;
import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
class DemoTest {
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
