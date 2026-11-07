import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class ContractTest {
  @Test
  @DisplayName("Все требования, длина ровно 10")
  void case01(TestReporter publicReporter) {
    var publicActual = Study.accepts("Abcdefghi1");
    publicReporter.publishEntry(
        Map.of("public-case", "strong-password-01", "input", "Вызов: Study.accepts(\"Abcdefghi1\")",
            "expected", String.valueOf(true), "actual", "result=" + publicActual));

    assertEquals(true, publicActual, "Все требования, длина ровно 10");
  }

  @Test
  @DisplayName("Только недостаточная длина 9")
  void case02(TestReporter publicReporter) {
    var publicActual = Study.accepts("Abcdefgh1");
    publicReporter.publishEntry(
        Map.of("public-case", "strong-password-02", "input", "Вызов: Study.accepts(\"Abcdefgh1\")",
            "expected", String.valueOf(false), "actual", "result=" + publicActual));

    assertEquals(false, publicActual, "Только недостаточная длина 9");
  }

  @Test
  @DisplayName("Нет латинской прописной")
  void case03() {
    assertEquals(false, Study.accepts("abcdefghij1"), "Нет латинской прописной");
  }

  @Test
  @DisplayName("Нет латинской строчной")
  void case04() {
    assertEquals(false, Study.accepts("ABCDEFGHIJ1"), "Нет латинской строчной");
  }

  @Test
  @DisplayName("Нет цифры")
  void case05() {
    assertEquals(false, Study.accepts("Abcdefghij"), "Нет цифры");
  }

  @Test
  @DisplayName("Пустой допустимый вход")
  void case06() {
    assertEquals(false, Study.accepts(""), "Пустой допустимый вход");
  }

  @Test
  @DisplayName("Запрещённый пробел при выполнении остальных требований")
  void case07() {
    assertEquals(false, Study.accepts("Abcdefgh 1"),
        "Запрещённый пробел при выполнении остальных требований");
  }

  @Test
  @DisplayName("Запрещённая ASCII-пунктуация")
  void case08() {
    assertEquals(false, Study.accepts("Abcdefghi1!"), "Запрещённая ASCII-пунктуация");
  }

  @Test
  @DisplayName("Русская буква не заменяет латинскую прописную")
  void case09() {
    assertEquals(
        false, Study.accepts("Абcdefghi1"), "Русская буква не заменяет латинскую прописную");
  }

  @Test
  @DisplayName("Подчёркивание разрешено")
  void case10() {
    assertEquals(true, Study.accepts("A1bcdefgh_"), "Подчёркивание разрешено");
  }

  @Test
  @DisplayName("Цифра 0 и свободное расположение классов")
  void case11() {
    assertEquals(true, Study.accepts("0_Aabcdefg"), "Цифра 0 и свободное расположение классов");
  }

  @Test
  @DisplayName("Русская буква запрещена даже при наличии обоих латинских регистров и цифры")
  void case12() {
    assertEquals(false, Study.accepts("Abcdefgh1я"),
        "Русская буква запрещена даже при наличии обоих латинских регистров и цифры");
  }

  @Test
  @DisplayName("TAB запрещён форматом, но допустим во входе")
  void case13() {
    assertEquals(
        false, Study.accepts("Abcdefghi1\t"), "TAB запрещён форматом, но допустим во входе");
  }

  @Test
  @DisplayName("LF запрещён форматом, включая конец строки")
  void case14() {
    assertEquals(
        false, Study.accepts("Abcdefghi1\n"), "LF запрещён форматом, включая конец строки");
  }

  @Test
  @DisplayName("Допустимая длина 11 при выполнении остальных четырёх требований")
  void case15() {
    assertEquals(true, Study.accepts("Abcdefghi12"),
        "Допустимая длина 11 при выполнении остальных четырёх требований");
  }
}
