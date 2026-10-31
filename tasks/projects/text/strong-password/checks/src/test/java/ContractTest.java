import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContractTest {
  @Test
  @DisplayName("strong-password-01 — Все требования, длина ровно 10; public normal")
  void case01() {
    assertEquals(
        true, Study.accepts("Abcdefghi1"), "Все требования, длина ровно 10; public normal");
  }

  @Test
  @DisplayName("strong-password-02 — Только недостаточная длина 9; public boundary")
  void case02() {
    assertEquals(
        false, Study.accepts("Abcdefgh1"), "Только недостаточная длина 9; public boundary");
  }

  @Test
  @DisplayName("strong-password-03 — Нет латинской прописной; private")
  void case03() {
    assertEquals(false, Study.accepts("abcdefghij1"), "Нет латинской прописной; private");
  }

  @Test
  @DisplayName("strong-password-04 — Нет латинской строчной; private")
  void case04() {
    assertEquals(false, Study.accepts("ABCDEFGHIJ1"), "Нет латинской строчной; private");
  }

  @Test
  @DisplayName("strong-password-05 — Нет цифры; private")
  void case05() {
    assertEquals(false, Study.accepts("Abcdefghij"), "Нет цифры; private");
  }

  @Test
  @DisplayName("strong-password-06 — Пустой допустимый вход; private")
  void case06() {
    assertEquals(false, Study.accepts(""), "Пустой допустимый вход; private");
  }

  @Test
  @DisplayName(
      "strong-password-07 — Запрещённый пробел при выполнении остальных требований; private")
  void
  case07() {
    assertEquals(false, Study.accepts("Abcdefgh 1"),
        "Запрещённый пробел при выполнении остальных требований; private");
  }

  @Test
  @DisplayName("strong-password-08 — Запрещённая ASCII-пунктуация; private")
  void case08() {
    assertEquals(false, Study.accepts("Abcdefghi1!"), "Запрещённая ASCII-пунктуация; private");
  }

  @Test
  @DisplayName("strong-password-09 — Русская буква не заменяет латинскую прописную; private")
  void case09() {
    assertEquals(false, Study.accepts("Абcdefghi1"),
        "Русская буква не заменяет латинскую прописную; private");
  }

  @Test
  @DisplayName("strong-password-10 — Подчёркивание разрешено; private")
  void case10() {
    assertEquals(true, Study.accepts("A1bcdefgh_"), "Подчёркивание разрешено; private");
  }

  @Test
  @DisplayName("strong-password-11 — Цифра 0 и свободное расположение классов; private")
  void case11() {
    assertEquals(
        true, Study.accepts("0_Aabcdefg"), "Цифра 0 и свободное расположение классов; private");
  }

  @Test
  @DisplayName("strong-password-12 — Русская буква запрещена даже при наличии обоих латинских "
               + "регистров и цифры; уточняющий случай")
  void
  case12() {
    assertEquals(false, Study.accepts("Abcdefgh1я"),
        "Русская буква запрещена даже при наличии обоих латинских регистров и цифры; уточняющий "
        + "случай");
  }

  @Test
  @DisplayName(
      "strong-password-13 — TAB запрещён форматом, но допустим во входе; уточняющий случай")
  void
  case13() {
    assertEquals(false, Study.accepts("Abcdefghi1\t"),
        "TAB запрещён форматом, но допустим во входе; уточняющий случай");
  }

  @Test
  @DisplayName("strong-password-14 — LF запрещён форматом, включая конец строки; уточняющий случай")
  void case14() {
    assertEquals(false, Study.accepts("Abcdefghi1\n"),
        "LF запрещён форматом, включая конец строки; уточняющий случай");
  }

  @Test
  @DisplayName("strong-password-15 — Допустимая длина 11 при выполнении остальных четырёх "
               + "требований; I1, канон text.qmd:148–154")
  void
  case15() {
    assertEquals(true, Study.accepts("Abcdefghi12"),
        "Допустимая длина 11 при выполнении остальных четырёх требований; I1, канон "
        + "text.qmd:148–154");
  }
}
