import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
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
}
