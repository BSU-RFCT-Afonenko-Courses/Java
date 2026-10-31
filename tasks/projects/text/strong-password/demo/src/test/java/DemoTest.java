import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
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
}
