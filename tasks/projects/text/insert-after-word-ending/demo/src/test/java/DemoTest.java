import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
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
}
