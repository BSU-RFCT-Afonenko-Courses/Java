import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContractTest {
  @Test
  @DisplayName("sum-text-digits-01 — Соседние цифры и ноль; public normal")
  void case01() {
    assertEquals(12L, Study.sum("a2b109"), "Соседние цифры и ноль; public normal");
  }

  @Test
  @DisplayName("sum-text-digits-02 — Пустая сумма; public boundary")
  void case02() {
    assertEquals(0L, Study.sum(""), "Пустая сумма; public boundary");
  }

  @Test
  @DisplayName("sum-text-digits-03 — Каждое повторное вхождение; private")
  void case03() {
    assertEquals(27L, Study.sum("99x9"), "Каждое повторное вхождение; private");
  }

  @Test
  @DisplayName("sum-text-digits-04 — Накопление многих отдельных значений; private")
  void case04() {
    assertEquals(
        36000L, Study.sum("9".repeat(4000)), "Накопление многих отдельных значений; private");
  }

  @Test
  @DisplayName("sum-text-digits-05 — Непустой текст без цифр; private")
  void case05() {
    assertEquals(0L, Study.sum("Ёж_!?\t\n"), "Непустой текст без цифр; private");
  }

  @Test
  @DisplayName("sum-text-digits-06 — Все десять значений; private")
  void case06() {
    assertEquals(45L, Study.sum("0123456789"), "Все десять значений; private");
  }
}
