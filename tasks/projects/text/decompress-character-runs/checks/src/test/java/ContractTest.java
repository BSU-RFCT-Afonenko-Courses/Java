import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class ContractTest {
  @Test
  @DisplayName("Несколько серий, регистр и финальная одиночная; ")
  void case01(TestReporter publicReporter) {
    var publicActual = Study.decompress("a2B3c");
    publicReporter.publishEntry(Map.of("public-case", "decompress-character-runs-01", "input",
        "Вызов: Study.decompress(\"a2B3c\")", "expected", String.valueOf("aaBBBc"), "actual",
        "result=" + publicActual));

    assertEquals("aaBBBc", publicActual, "Несколько серий, регистр и финальная одиночная");
  }

  @Test
  @DisplayName("Двузначный счётчик целиком")
  void case02(TestReporter publicReporter) {
    var publicActual = Study.decompress("a12b");
    publicReporter.publishEntry(Map.of("public-case", "decompress-character-runs-02", "input",
        "Вызов: Study.decompress(\"a12b\")", "expected", String.valueOf("a".repeat(12) + "b"),
        "actual", "result=" + publicActual));

    assertEquals("a".repeat(12) + "b", publicActual, "Двузначный счётчик целиком");
  }

  @Test
  @DisplayName("Пустая запись")
  void case03(TestReporter publicReporter) {
    var publicActual = Study.decompress("");
    publicReporter.publishEntry(Map.of("public-case", "decompress-character-runs-03", "input",
        "Вызов: Study.decompress(\"\")", "expected", "Пустая строка", "actual",
        "result=" + publicActual));

    assertEquals("", publicActual, "Пустая запись");
  }

  @Test
  @DisplayName("Одиночная серия без счётчика")
  void case04() {
    assertEquals("Z", Study.decompress("Z"), "Одиночная серия без счётчика");
  }

  @Test
  @DisplayName("Полный прежний пример смешанных серий")
  void case05() {
    assertEquals(
        "hellowoooorld", Study.decompress("hel2owo4rld"), "Полный прежний пример смешанных серий");
  }

  @Test
  @DisplayName("Трёхзначный счётчик, внутренний 0, несколько чисел")
  void case06() {
    assertEquals("a".repeat(101) + "Z".repeat(12) + "x", Study.decompress("a101Z12x"),
        "Трёхзначный счётчик, внутренний 0, несколько чисел");
  }

  @Test
  @DisplayName("Смена регистра и последняя числовая серия")
  void case07() {
    assertEquals("A" + "a".repeat(2) + "b".repeat(13), Study.decompress("Aa2b13"),
        "Смена регистра и последняя числовая серия");
  }
}
