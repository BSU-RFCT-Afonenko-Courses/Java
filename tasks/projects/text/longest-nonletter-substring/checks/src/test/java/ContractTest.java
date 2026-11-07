import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class ContractTest {
  @Test
  @DisplayName("Непрерывность, уникальный максимум")
  void case01(TestReporter publicReporter) {
    var publicActual = Study.longest("a12!б3");
    publicReporter.publishEntry(Map.of("public-case", "longest-nonletter-substring-01", "input",
        "Вызов: Study.longest(\"a12!б3\")", "expected", String.valueOf("12!"), "actual",
        "result=" + publicActual));

    assertEquals("12!", publicActual, "Непрерывность, уникальный максимум");
  }

  @Test
  @DisplayName("Первый из равных максимумов")
  void case02(TestReporter publicReporter) {
    var publicActual = Study.longest("a12!б34?");
    publicReporter.publishEntry(Map.of("public-case", "longest-nonletter-substring-02", "input",
        "Вызов: Study.longest(\"a12!б34?\")", "expected", String.valueOf("12!"), "actual",
        "result=" + publicActual));

    assertEquals("12!", publicActual, "Первый из равных максимумов");
  }

  @Test
  @DisplayName("Пробел/TAB/LF внутри единого фрагмента")
  void case03() {
    assertEquals(
        "1 \t\n!!", Study.longest("a1 \t\n!!б??"), "Пробел/TAB/LF внутри единого фрагмента");
  }

  @Test
  @DisplayName("Максимум в начале")
  void case04() {
    assertEquals("!?", Study.longest("!?аб!"), "Максимум в начале");
  }

  @Test
  @DisplayName("Весь текст без букв, конечный пробел сохранён")
  void case05() {
    assertEquals("123 ", Study.longest("123 "), "Весь текст без букв, конечный пробел сохранён");
  }

  @Test
  @DisplayName("Только буквы, включая Ё")
  void case06() {
    assertEquals("", Study.longest("ЁжABC"), "Только буквы, включая Ё");
  }

  @Test
  @DisplayName("Пустой вход")
  void case07() {
    assertEquals("", Study.longest(""), "Пустой вход");
  }

  @Test
  @DisplayName("Максимум в конце после меньшего")
  void case08() {
    assertEquals("123?", Study.longest("а!б123?"), "Максимум в конце после меньшего");
  }
}
