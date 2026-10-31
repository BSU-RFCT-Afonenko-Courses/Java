import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
class DemoTest {
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
