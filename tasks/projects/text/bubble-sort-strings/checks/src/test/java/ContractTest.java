import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContractTest {
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

  @Test
  @DisplayName("bubble-sort-strings-03 — Уже упорядоченный массив; public boundary")
  void case03() {
    String[] values = new String[] {"Gamma", "alpha", "beta", "beta"};
    Study.sort(values);
    assertArrayEquals(new String[] {"Gamma", "alpha", "beta", "beta"}, values,
        "Уже упорядоченный массив; public boundary");
  }

  @Test
  @DisplayName("bubble-sort-strings-04 — Один элемент; private")
  void case04() {
    String[] values = new String[] {"Я"};
    Study.sort(values);
    assertArrayEquals(new String[] {"Я"}, values, "Один элемент; private");
  }

  @Test
  @DisplayName(
      "bubble-sort-strings-05 — Пустая строка, повторы, UTF-16-порядок и знак compareTo; private")
  void
  case05() {
    String[] values = new String[] {"z", "a", "", "Ёж", "Я", "z"};
    Study.sort(values);
    assertArrayEquals(new String[] {"", "a", "z", "z", "Ёж", "Я"}, values,
        "Пустая строка, повторы, UTF-16-порядок и знак compareTo; private");
  }

  @Test
  @DisplayName(
      "bubble-sort-strings-06 — Префиксный порядок и несколько проходов; уточняющий случай")
  void
  case06() {
    String[] values = new String[] {"zzz", "zz", "z", ""};
    Study.sort(values);
    assertArrayEquals(new String[] {"", "z", "zz", "zzz"}, values,
        "Префиксный порядок и несколько проходов; уточняющий случай");
  }
}
