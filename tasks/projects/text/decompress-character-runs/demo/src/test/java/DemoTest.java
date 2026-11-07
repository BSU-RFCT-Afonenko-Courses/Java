import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
  @Test
  @DisplayName("decompress-character-runs-01 — Несколько серий, регистр и финальная одиночная; "
               + "public normal")
  void
  case01() {
    assertEquals("aaBBBc", Study.decompress("a2B3c"),
        "Несколько серий, регистр и финальная одиночная; public normal");
  }

  @Test
  @DisplayName(
      "decompress-character-runs-02 — Двузначный счётчик целиком; public boundary, условие")
  void
  case02() {
    assertEquals("a".repeat(12) + "b", Study.decompress("a12b"),
        "Двузначный счётчик целиком; public boundary, условие");
  }
}
