import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class ContractTest {
  @Test
  @DisplayName("Соседние цифры и ноль")
  void case01(TestReporter publicReporter) {
    var publicActual = Study.sum("a2b109");
    publicReporter.publishEntry(
        Map.of("public-case", "sum-text-digits-01", "input", "Вызов: Study.sum(\"a2b109\")",
            "expected", String.valueOf(12L), "actual", "result=" + publicActual));

    assertEquals(12L, publicActual, "Соседние цифры и ноль");
  }

  @Test
  @DisplayName("Пустая сумма")
  void case02(TestReporter publicReporter) {
    var publicActual = Study.sum("");
    publicReporter.publishEntry(
        Map.of("public-case", "sum-text-digits-02", "input", "Вызов: Study.sum(\"\")", "expected",
            String.valueOf(0L), "actual", "result=" + publicActual));

    assertEquals(0L, publicActual, "Пустая сумма");
  }

  @Test
  @DisplayName("Каждое повторное вхождение")
  void case03() {
    assertEquals(27L, Study.sum("99x9"), "Каждое повторное вхождение");
  }

  @Test
  @DisplayName("Накопление многих отдельных значений")
  void case04() {
    assertEquals(36000L, Study.sum("9".repeat(4000)), "Накопление многих отдельных значений");
  }

  @Test
  @DisplayName("Непустой текст без цифр")
  void case05() {
    assertEquals(0L, Study.sum("Ёж_!?\t\n"), "Непустой текст без цифр");
  }

  @Test
  @DisplayName("Все десять значений")
  void case06() {
    assertEquals(45L, Study.sum("0123456789"), "Все десять значений");
  }
}
