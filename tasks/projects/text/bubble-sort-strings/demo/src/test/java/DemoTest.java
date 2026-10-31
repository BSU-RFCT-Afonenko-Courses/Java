import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
  @Test
  @DisplayName(
      "bubble-sort-strings-01 — Регистр, естественный порядок, повторы и мутация; public normal")
  void
  case01() {
    String[] values = new String[] {"beta", "alpha", "beta", "Gamma"};
    Study.sort(values);
    assertArrayEquals(new String[] {"Gamma", "alpha", "beta", "beta"}, values,
        "Регистр, естественный порядок, повторы и мутация; public normal");
  }

  @Test
  @DisplayName("bubble-sort-strings-02 — Пустая граница; public boundary")
  void case02() {
    String[] values = new String[] {};
    Study.sort(values);
    assertArrayEquals(new String[] {}, values, "Пустая граница; public boundary");
  }
}
