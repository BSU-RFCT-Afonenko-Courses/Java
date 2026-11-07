import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class ContractTest {
  @Test
  @DisplayName("е/ё, регистр, целое слово")
  void case01(TestReporter publicReporter) {
    var publicActual = Study.count("Ёлка, елка; елками", "елка");
    publicReporter.publishEntry(Map.of("public-case", "count-normalized-word-01", "input",
        "Вызов: Study.count(\"Ёлка, елка; елками\", \"елка\")", "expected", String.valueOf(2L), "actual",
        "result=" + publicActual));

    assertEquals(2L, publicActual, "е/ё, регистр, целое слово");
  }

  @Test
  @DisplayName("и/й, регистр и более длинное слово")
  void case02(TestReporter publicReporter) {
    var publicActual = Study.count("май МАЙ, маи; маий", "МАИ");
    publicReporter.publishEntry(Map.of("public-case", "count-normalized-word-02", "input",
        "Вызов: Study.count(\"май МАЙ, маи; маий\", \"МАИ\")", "expected", String.valueOf(3L), "actual",
        "result=" + publicActual));

    assertEquals(3L, publicActual, "и/й, регистр и более длинное слово");
  }

  @Test
  @DisplayName("Цифра и _ как границы, целое слово")
  void case03() {
    assertEquals(2L, Study.count("x1X_xx", "X"), "Цифра и _ как границы, целое слово");
  }

  @Test
  @DisplayName("Нормализация искомого слова и повторные вхождения")
  void case04() {
    assertEquals(
        3L, Study.count("е Ё ёж е", "ё"), "Нормализация искомого слова и повторные вхождения");
  }

  @Test
  @DisplayName("Нет слов")
  void case05() {
    assertEquals(0L, Study.count("123!", "я"), "Нет слов");
  }

  @Test
  @DisplayName("Пустой текст")
  void case06() {
    assertEquals(0L, Study.count("", "е"), "Пустой текст");
  }

  @Test
  @DisplayName("TAB/_, обе формы и целое слово")
  void case07() {
    assertEquals(2L, Study.count("Й\tи_ий", "И"), "TAB/_, обе формы и целое слово");
  }

  @Test
  @DisplayName("Нормализация запроса с Ё и регистра")
  void case08() {
    assertEquals(2L, Study.count("ЕЛКА елка", "ЁЛКА"), "Нормализация запроса с Ё и регистра");
  }
}
