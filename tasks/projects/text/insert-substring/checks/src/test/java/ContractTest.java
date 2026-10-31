import java.lang.Object;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContractTest {
  @Test
  @DisplayName("insert-substring-01 — Внутренняя граница исходных частей; public normal")
  void case01(TestReporter publicReporter) {
    var publicActual = Study.insert("Java", "!", 2);
    publicReporter.publishEntry(Map.of("public-case", "insert-substring-01", "actual", Arrays.deepToString(new Object[]{publicActual})));

    assertEquals(
        "Ja!va", publicActual, "Внутренняя граница исходных частей; public normal");
  }

  @Test
  @DisplayName("insert-substring-02 — Вставка перед первым; public boundary, условие")
  void case02(TestReporter publicReporter) {
    var publicActual = Study.insert("Java", "!", 0);
    publicReporter.publishEntry(Map.of("public-case", "insert-substring-02", "actual", Arrays.deepToString(new Object[]{publicActual})));

    assertEquals(
        "!Java", publicActual, "Вставка перед первым; public boundary, условие");
  }

  @Test
  @DisplayName("insert-substring-03 — Вставка после последнего; public boundary, условие")
  void case03(TestReporter publicReporter) {
    var publicActual = Study.insert("Java", "!", 4);
    publicReporter.publishEntry(Map.of("public-case", "insert-substring-03", "actual", Arrays.deepToString(new Object[]{publicActual})));

    assertEquals("Java!", publicActual,
        "Вставка после последнего; public boundary, условие");
  }

  @Test
  @DisplayName(
      "insert-substring-04 — Единственная позиция пустого текста; public boundary, условие")
  void
  case04(TestReporter publicReporter) {
    var publicActual = Study.insert("", "код", 0);
    publicReporter.publishEntry(Map.of("public-case", "insert-substring-04", "actual", Arrays.deepToString(new Object[]{publicActual})));

    assertEquals("код", publicActual,
        "Единственная позиция пустого текста; public boundary, условие");
  }

  @Test
  @DisplayName("insert-substring-05 — Пустой фрагмент; private")
  void case05() {
    assertEquals("текст", Study.insert("текст", "", 2), "Пустой фрагмент; private");
  }

  @Test
  @DisplayName("insert-substring-06 — Сохранение пробела, TAB, букв и исходных координат; private")
  void case06() {
    assertEquals("а! б\tв", Study.insert("а б\tв", "!", 1),
        "Сохранение пробела, TAB, букв и исходных координат; private");
  }

  @Test
  @DisplayName("insert-substring-07 — Обе части пусты; private")
  void case07() {
    assertEquals("", Study.insert("", "", 0), "Обе части пусты; private");
  }
}
