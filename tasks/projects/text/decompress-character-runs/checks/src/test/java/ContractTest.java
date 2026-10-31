import java.lang.Object;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContractTest {
  @Test
  @DisplayName("decompress-character-runs-01 — Несколько серий, регистр и финальная одиночная; "
               + "public normal")
  void
  case01(TestReporter publicReporter) {
    var publicActual = Study.decompress("a2B3c");
    publicReporter.publishEntry(Map.of("public-case", "decompress-character-runs-01", "actual", Arrays.deepToString(new Object[]{publicActual})));

    assertEquals("aaBBBc", publicActual,
        "Несколько серий, регистр и финальная одиночная; public normal");
  }

  @Test
  @DisplayName(
      "decompress-character-runs-02 — Двузначный счётчик целиком; public boundary, условие")
  void
  case02(TestReporter publicReporter) {
    var publicActual = Study.decompress("a12b");
    publicReporter.publishEntry(Map.of("public-case", "decompress-character-runs-02", "actual", Arrays.deepToString(new Object[]{publicActual})));

    assertEquals("a".repeat(12) + "b", publicActual,
        "Двузначный счётчик целиком; public boundary, условие");
  }

  @Test
  @DisplayName("decompress-character-runs-03 — Пустая запись; public boundary")
  void case03(TestReporter publicReporter) {
    var publicActual = Study.decompress("");
    publicReporter.publishEntry(Map.of("public-case", "decompress-character-runs-03", "actual", Arrays.deepToString(new Object[]{publicActual})));

    assertEquals("", publicActual, "Пустая запись; public boundary");
  }

  @Test
  @DisplayName("decompress-character-runs-04 — Одиночная серия без счётчика; private")
  void case04() {
    assertEquals("Z", Study.decompress("Z"), "Одиночная серия без счётчика; private");
  }

  @Test
  @DisplayName(
      "decompress-character-runs-05 — Полный прежний пример смешанных серий; private, условие")
  void
  case05() {
    assertEquals("hellowoooorld", Study.decompress("hel2owo4rld"),
        "Полный прежний пример смешанных серий; private, условие");
  }

  @Test
  @DisplayName(
      "decompress-character-runs-06 — Трёхзначный счётчик, внутренний 0, несколько чисел; private")
  void
  case06() {
    assertEquals("a".repeat(101) + "Z".repeat(12) + "x", Study.decompress("a101Z12x"),
        "Трёхзначный счётчик, внутренний 0, несколько чисел; private");
  }

  @Test
  @DisplayName("decompress-character-runs-07 — Смена регистра и последняя числовая серия; private")
  void case07() {
    assertEquals("A"
            + "a".repeat(2) + "b".repeat(13),
        Study.decompress("Aa2b13"), "Смена регистра и последняя числовая серия; private");
  }
}
