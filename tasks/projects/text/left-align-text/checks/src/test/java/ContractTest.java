import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class ContractTest {
  @Test
  @DisplayName("Заполнение ровно до width, нормализация разделителей")
  void case01(TestReporter publicReporter) {
    var publicActual = Study.align("one   two\nthree", 7);
    publicReporter.publishEntry(Map.of("public-case", "left-align-text-01", "input",
        "Вызов: Study.align(\"one   two\\nthree\", 7)", "expected",
        String.valueOf(List.of("one two", "three")), "actual",
        "lines="
            + (publicActual == null ? "null"
                                    : Arrays.toString(publicActual.toArray(new String[0])))));

    assertEquals(List.of("one two", "three"), publicActual,
        "Заполнение ровно до width, нормализация разделителей");
  }

  @Test
  @DisplayName("Только разделители")
  void case02(TestReporter publicReporter) {
    var publicActual = Study.align(" \t\n ", 1);
    publicReporter.publishEntry(Map.of("public-case", "left-align-text-02", "input",
        "Вызов: Study.align(\" \\t\\n \", 1)", "expected", String.valueOf(List.of()), "actual",
        "lines="
            + (publicActual == null ? "null"
                                    : Arrays.toString(publicActual.toArray(new String[0])))));

    assertEquals(List.of(), publicActual, "Только разделители");
  }

  @Test
  @DisplayName("Учёт межсловного пробела при переносе")
  void case03() {
    assertEquals(
        List.of("a", "bc"), Study.align("a bc", 3), "Учёт межсловного пробела при переносе");
  }

  @Test
  @DisplayName("Пунктуация внутри слова")
  void case04() {
    assertEquals(List.of("hi, a!", "b?"), Study.align("hi,\ta!  b?", 6), "Пунктуация внутри слова");
  }

  @Test
  @DisplayName("Цифра и знак — слова, минимальная ширина")
  void case05() {
    assertEquals(List.of("a", "2", "!"), Study.align("a 2 !", 1),
        "Цифра и знак — слова, минимальная ширина");
  }

  @Test
  @DisplayName("Пустой текст")
  void case06() {
    assertEquals(List.of(), Study.align("", 9), "Пустой текст");
  }

  @Test
  @DisplayName("Целое слово длины width")
  void case07() {
    assertEquals(List.of("Ёж!"), Study.align("Ёж!", 3), "Целое слово длины width");
  }

  @Test
  @DisplayName("Порядок, обязательное заполнение, удаление краевых разделителей")
  void case08() {
    assertEquals(List.of("a b", "c"), Study.align(" a b c ", 3),
        "Порядок, обязательное заполнение, удаление краевых разделителей");
  }

  @Test
  @DisplayName("Следующее слово помещается с остатком ширины: 1 + 1 + 1 = 3 < 4")
  void case09() {
    assertEquals(List.of("a b"), Study.align("a b", 4),
        "Следующее слово помещается с остатком ширины: 1 + 1 + 1 = 3 < 4");
  }
}
