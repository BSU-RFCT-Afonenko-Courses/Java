import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContractTest {
  @Test
  @DisplayName(
      "left-align-text-01 — Заполнение ровно до width, нормализация разделителей; public normal")
  void
  case01() {
    assertEquals(java.util.List.of("one two", "three"), Study.align("one   two\nthree", 7),
        "Заполнение ровно до width, нормализация разделителей; public normal");
  }

  @Test
  @DisplayName("left-align-text-02 — Только разделители; public boundary")
  void case02() {
    assertEquals(
        java.util.List.of(), Study.align(" \t\n ", 1), "Только разделители; public boundary");
  }

  @Test
  @DisplayName("left-align-text-03 — Учёт межсловного пробела при переносе; private")
  void case03() {
    assertEquals(java.util.List.of("a", "bc"), Study.align("a bc", 3),
        "Учёт межсловного пробела при переносе; private");
  }

  @Test
  @DisplayName("left-align-text-04 — Пунктуация внутри слова; private")
  void case04() {
    assertEquals(java.util.List.of("hi, a!", "b?"), Study.align("hi,\ta!  b?", 6),
        "Пунктуация внутри слова; private");
  }

  @Test
  @DisplayName("left-align-text-05 — Цифра и знак — слова, минимальная ширина; private")
  void case05() {
    assertEquals(java.util.List.of("a", "2", "!"), Study.align("a 2 !", 1),
        "Цифра и знак — слова, минимальная ширина; private");
  }

  @Test
  @DisplayName("left-align-text-06 — Пустой текст; private")
  void case06() {
    assertEquals(java.util.List.of(), Study.align("", 9), "Пустой текст; private");
  }

  @Test
  @DisplayName("left-align-text-07 — Целое слово длины width; private")
  void case07() {
    assertEquals(
        java.util.List.of("Ёж!"), Study.align("Ёж!", 3), "Целое слово длины width; private");
  }

  @Test
  @DisplayName("left-align-text-08 — Порядок, обязательное заполнение, удаление краевых "
               + "разделителей; уточняющий случай")
  void
  case08() {
    assertEquals(java.util.List.of("a b", "c"), Study.align(" a b c ", 3),
        "Порядок, обязательное заполнение, удаление краевых разделителей; уточняющий случай");
  }

  @Test
  @DisplayName("left-align-text-09 — Следующее слово помещается с остатком ширины: 1 + 1 + 1 = 3 < "
               + "4; I2, канон text.qmd:404–408")
  void
  case09() {
    assertEquals(java.util.List.of("a b"), Study.align("a b", 4),
        "Следующее слово помещается с остатком ширины: 1 + 1 + 1 = 3 < 4; I2, канон "
        + "text.qmd:404–408");
  }
}
