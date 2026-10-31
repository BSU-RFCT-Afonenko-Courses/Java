import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
  @Test
  @DisplayName(
      "left-align-text-01 — Заполнение ровно до width, нормализация разделителей; public normal")
  void
  case01() {
    assertEquals(List.of("one two", "three"), Study.align("one   two\nthree", 7),
        "Заполнение ровно до width, нормализация разделителей; public normal");
  }

  @Test
  @DisplayName("left-align-text-02 — Только разделители; public boundary")
  void case02() {
    assertEquals(
        List.of(), Study.align(" \t\n ", 1), "Только разделители; public boundary");
  }
}
