import java.lang.Object;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContractTest {
  @Test
  @DisplayName("longest-nonletter-substring-01 — Непрерывность, уникальный максимум; public normal")
  void case01(TestReporter publicReporter) {
    var publicActual = Study.longest("a12!б3");
    publicReporter.publishEntry(Map.of("public-case", "longest-nonletter-substring-01", "actual", Arrays.deepToString(new Object[]{publicActual})));

    assertEquals(
        "12!", publicActual, "Непрерывность, уникальный максимум; public normal");
  }

  @Test
  @DisplayName("longest-nonletter-substring-02 — Первый из равных максимумов; public boundary")
  void case02(TestReporter publicReporter) {
    var publicActual = Study.longest("a12!б34?");
    publicReporter.publishEntry(Map.of("public-case", "longest-nonletter-substring-02", "actual", Arrays.deepToString(new Object[]{publicActual})));

    assertEquals("12!", publicActual, "Первый из равных максимумов; public boundary");
  }

  @Test
  @DisplayName("longest-nonletter-substring-03 — Пробел/TAB/LF внутри единого фрагмента; private")
  void case03() {
    assertEquals("1 \t\n!!", Study.longest("a1 \t\n!!б??"),
        "Пробел/TAB/LF внутри единого фрагмента; private");
  }

  @Test
  @DisplayName("longest-nonletter-substring-04 — Максимум в начале; private")
  void case04() {
    assertEquals("!?", Study.longest("!?аб!"), "Максимум в начале; private");
  }

  @Test
  @DisplayName(
      "longest-nonletter-substring-05 — Весь текст без букв, конечный пробел сохранён; private")
  void
  case05() {
    assertEquals(
        "123 ", Study.longest("123 "), "Весь текст без букв, конечный пробел сохранён; private");
  }

  @Test
  @DisplayName("longest-nonletter-substring-06 — Только буквы, включая Ё; private")
  void case06() {
    assertEquals("", Study.longest("ЁжABC"), "Только буквы, включая Ё; private");
  }

  @Test
  @DisplayName("longest-nonletter-substring-07 — Пустой вход; private")
  void case07() {
    assertEquals("", Study.longest(""), "Пустой вход; private");
  }

  @Test
  @DisplayName(
      "longest-nonletter-substring-08 — Максимум в конце после меньшего; уточняющий случай")
  void
  case08() {
    assertEquals(
        "123?", Study.longest("а!б123?"), "Максимум в конце после меньшего; уточняющий случай");
  }
}
