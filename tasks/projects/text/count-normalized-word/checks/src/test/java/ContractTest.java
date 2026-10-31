import java.lang.Object;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContractTest {
  @Test
  @DisplayName("count-normalized-word-01 — е/ё, регистр, целое слово; public normal")
  void case01(TestReporter publicReporter) {
    var publicActual = Study.count("Ёлка, елка; елками", "елка");
    publicReporter.publishEntry(Map.of("public-case", "count-normalized-word-01", "actual", Arrays.deepToString(new Object[]{publicActual})));

    assertEquals(
        2L, publicActual, "е/ё, регистр, целое слово; public normal");
  }

  @Test
  @DisplayName("count-normalized-word-02 — и/й, регистр и более длинное слово; public boundary")
  void case02(TestReporter publicReporter) {
    var publicActual = Study.count("май МАЙ, маи; маий", "МАИ");
    publicReporter.publishEntry(Map.of("public-case", "count-normalized-word-02", "actual", Arrays.deepToString(new Object[]{publicActual})));

    assertEquals(3L, publicActual,
        "и/й, регистр и более длинное слово; public boundary");
  }

  @Test
  @DisplayName("count-normalized-word-03 — Цифра и _ как границы, целое слово; private")
  void case03() {
    assertEquals(2L, Study.count("x1X_xx", "X"), "Цифра и _ как границы, целое слово; private");
  }

  @Test
  @DisplayName(
      "count-normalized-word-04 — Нормализация искомого слова и повторные вхождения; private")
  void
  case04() {
    assertEquals(3L, Study.count("е Ё ёж е", "ё"),
        "Нормализация искомого слова и повторные вхождения; private");
  }

  @Test
  @DisplayName("count-normalized-word-05 — Нет слов; private")
  void case05() {
    assertEquals(0L, Study.count("123!", "я"), "Нет слов; private");
  }

  @Test
  @DisplayName("count-normalized-word-06 — Пустой текст; private")
  void case06() {
    assertEquals(0L, Study.count("", "е"), "Пустой текст; private");
  }

  @Test
  @DisplayName("count-normalized-word-07 — TAB/_, обе формы и целое слово; private")
  void case07() {
    assertEquals(2L, Study.count("Й\tи_ий", "И"), "TAB/_, обе формы и целое слово; private");
  }

  @Test
  @DisplayName("count-normalized-word-08 — Нормализация запроса с Ё и регистра; уточняющий случай")
  void case08() {
    assertEquals(2L, Study.count("ЕЛКА елка", "ЁЛКА"),
        "Нормализация запроса с Ё и регистра; уточняющий случай");
  }
}
