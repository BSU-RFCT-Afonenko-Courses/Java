import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
  @Test
  @DisplayName("count-normalized-word-01 — е/ё, регистр, целое слово; public normal")
  void case01() {
    assertEquals(
        2L, Study.count("Ёлка, елка; елками", "елка"), "е/ё, регистр, целое слово; public normal");
  }

  @Test
  @DisplayName("count-normalized-word-02 — и/й, регистр и более длинное слово; public boundary")
  void case02() {
    assertEquals(3L, Study.count("май МАЙ, маи; маий", "МАИ"),
        "и/й, регистр и более длинное слово; public boundary");
  }
}
