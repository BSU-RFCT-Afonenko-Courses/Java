import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContractTest {
  @Test
  @DisplayName(
      "insert-after-word-ending-01 — Суффикс, целое слово и исходная пунктуация; public normal")
  void
  case01() {
    assertEquals("кот дом, скот дом!", Study.insert("кот, скот!", "кот", "дом"),
        "Суффикс, целое слово и исходная пунктуация; public normal");
  }

  @Test
  @DisplayName("insert-after-word-ending-02 — Регистр и вставка совпадающего слова без повторной "
               + "обработки; public boundary")
  void
  case02() {
    assertEquals("Кот кот кот!", Study.insert("Кот кот!", "кот", "кот"),
        "Регистр и вставка совпадающего слова без повторной обработки; public boundary");
  }

  @Test
  @DisplayName("insert-after-word-ending-03 — TAB/LF сохраняются, КОТ не совпадает; private")
  void case03() {
    assertEquals("кот дом\tскот дом\nКОТ", Study.insert("кот\tскот\nКОТ", "кот", "дом"),
        "TAB/LF сохраняются, КОТ не совпадает; private");
  }

  @Test
  @DisplayName("insert-after-word-ending-04 — Нет слов, все разделители сохранены; private")
  void case04() {
    assertEquals("123_ !\n", Study.insert("123_ !\n", "а", "а"),
        "Нет слов, все разделители сохранены; private");
  }

  @Test
  @DisplayName("insert-after-word-ending-05 — Цифра и _ разделяют исходные слова; private")
  void case05() {
    assertEquals("а а1а а_б", Study.insert("а1а_б", "а", "а"),
        "Цифра и _ разделяют исходные слова; private");
  }

  @Test
  @DisplayName("insert-after-word-ending-06 — Пустой текст; private")
  void case06() {
    assertEquals("", Study.insert("", "кот", "дом"), "Пустой текст; private");
  }

  @Test
  @DisplayName("insert-after-word-ending-07 — Ё/ё, кириллическая вставка и последний исходный "
               + "токен; уточняющий случай")
  void
  case07() {
    assertEquals("Ёж Я, ёж Я", Study.insert("Ёж, ёж", "ж", "Я"),
        "Ё/ё, кириллическая вставка и последний исходный токен; уточняющий случай");
  }

  @Test
  @DisplayName("insert-after-word-ending-08 — Короткое исходное слово сохраняется, следующее "
               + "подходящее получает вставку; I3, канон text.qmd:209–217")
  void
  case08() {
    assertEquals("я кот дом!", Study.insert("я кот!", "кот", "дом"),
        "Короткое исходное слово сохраняется, следующее подходящее получает вставку; I3, канон "
        + "text.qmd:209–217");
  }

  @Test
  @DisplayName("insert-after-word-ending-09 — Латинские буквы обоих регистров образуют слово рядом "
               + "с кириллицей; I4, text-data.md и text-word.md")
  void
  case09() {
    assertEquals("aA X, ББ", Study.insert("aA, ББ", "aA", "X"),
        "Латинские буквы обоих регистров образуют слово рядом с кириллицей; I4, text-data.md и "
        + "text-word.md");
  }
}
