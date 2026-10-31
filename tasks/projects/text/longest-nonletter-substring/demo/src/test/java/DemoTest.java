import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
  @Test
  @DisplayName("longest-nonletter-substring-01 — Непрерывность, уникальный максимум; public normal")
  void case01() {
    assertEquals(
        "12!", Study.longest("a12!б3"), "Непрерывность, уникальный максимум; public normal");
  }

  @Test
  @DisplayName("longest-nonletter-substring-02 — Первый из равных максимумов; public boundary")
  void case02() {
    assertEquals("12!", Study.longest("a12!б34?"), "Первый из равных максимумов; public boundary");
  }
}
