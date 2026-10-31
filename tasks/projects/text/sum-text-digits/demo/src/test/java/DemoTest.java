import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
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
}
