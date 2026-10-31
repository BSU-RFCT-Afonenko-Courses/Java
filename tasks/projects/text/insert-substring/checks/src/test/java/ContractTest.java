import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContractTest {
  @Test
  @DisplayName("insert-substring-01 — Внутренняя граница исходных частей; public normal")
  void case01() {
    assertEquals(
        "Ja!va", Study.insert("Java", "!", 2), "Внутренняя граница исходных частей; public normal");
  }

  @Test
  @DisplayName("insert-substring-02 — Вставка перед первым; public boundary, условие")
  void case02() {
    assertEquals(
        "!Java", Study.insert("Java", "!", 0), "Вставка перед первым; public boundary, условие");
  }

  @Test
  @DisplayName("insert-substring-03 — Вставка после последнего; public boundary, условие")
  void case03() {
    assertEquals("Java!", Study.insert("Java", "!", 4),
        "Вставка после последнего; public boundary, условие");
  }

  @Test
  @DisplayName(
      "insert-substring-04 — Единственная позиция пустого текста; public boundary, условие")
  void
  case04() {
    assertEquals("код", Study.insert("", "код", 0),
        "Единственная позиция пустого текста; public boundary, условие");
  }

  @Test
  @DisplayName("insert-substring-05 — Пустой фрагмент; private")
  void case05() {
    assertEquals("текст", Study.insert("текст", "", 2), "Пустой фрагмент; private");
  }

  @Test
  @DisplayName("insert-substring-06 — Сохранение пробела, TAB, букв и исходных координат; private")
  void case06() {
    assertEquals("а! б\tв", Study.insert("а б\tв", "!", 1),
        "Сохранение пробела, TAB, букв и исходных координат; private");
  }

  @Test
  @DisplayName("insert-substring-07 — Обе части пусты; private")
  void case07() {
    assertEquals("", Study.insert("", "", 0), "Обе части пусты; private");
  }
}
