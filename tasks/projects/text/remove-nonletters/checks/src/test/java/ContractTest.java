import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class ContractTest {
  private static List<String> words(String text) {
    var result = new ArrayList<String>();
    var matcher = Pattern.compile("[A-Za-zА-Яа-яЁё]+").matcher(text);
    while (matcher.find()) result.add(matcher.group());
    return result;
  }
  private static List<Integer> spaces(String text) {
    var result = new ArrayList<Integer>();
    var matcher = Pattern.compile("[A-Za-zА-Яа-яЁё]+").matcher(text);
    int end = 0;
    while (matcher.find()) {
      int count = 0;
      for (int i = end; i < matcher.start(); i++)
        if (text.charAt(i) == ' ')
          count++;
      result.add(count);
      end = matcher.end();
    }
    int count = 0;
    for (int i = end; i < text.length(); i++)
      if (text.charAt(i) == ' ')
        count++;
    result.add(count);
    return result;
  }
  private static void checkResult(String source, String actual) {
    assertNotNull(actual, "результат существует");
    if (source.isEmpty()) {
      assertEquals("", actual, "пустой вход");
      return;
    }
    assertTrue(actual.matches("[A-Za-zА-Яа-яЁё ]*"), "только буквы и U+0020; получено=" + actual);
    assertEquals(words(source), words(actual), "слова, порядок, регистр и повторы");
    var before = spaces(source);
    var after = spaces(actual);
    assertEquals(before.size(), after.size(), "соответствующие промежутки");
    for (int i = 0; i < before.size(); i++)
      assertTrue(after.get(i) >= before.get(i),
          "промежуток " + i + ", исходных пробелов=" + before.get(i)
              + ", получено=" + after.get(i));
  }
  @Test
  @DisplayName("Удаление без слияния, исходные два пробела")
  void case01(TestReporter publicReporter) {
    String input = "кот,дом  2я!";
    var publicActual = Study.remove(input);
    publicReporter.publishEntry(Map.of("public-case", "remove-nonletters-01", "input",
        input.isEmpty() ? "Пустая строка" : input, "expected",
        "Только буквы и обычные пробелы; слова, порядок, регистр и повторы сохранены; каждый "
        + "промежуток содержит не меньше исходных пробелов.",
        "actual", "result=" + publicActual));

    checkResult(input, publicActual);
  }

  @Test
  @DisplayName("Явное исключение для пустого входа")
  void case02(TestReporter publicReporter) {
    String input = "";
    var publicActual = Study.remove(input);
    publicReporter.publishEntry(Map.of("public-case", "remove-nonletters-02", "input",
        input.isEmpty() ? "Пустая строка" : input, "expected", "Пустая строка.", "actual",
        "result=" + publicActual));

    checkResult(input, publicActual);
  }

  @Test
  @DisplayName("Исходные краевые пробелы")
  void case03() {
    String input = "  кот,дом ";
    checkResult(input, Study.remove(input));
  }

  @Test
  @DisplayName("Удаление TAB/LF без слияния")
  void case04() {
    String input = "а\tб\nВ";
    checkResult(input, Study.remove(input));
  }

  @Test
  @DisplayName("Нижняя граница пробелов отдельно в каждом промежутке")
  void case05() {
    String input = "а  ,б   2в!";
    checkResult(input, Study.remove(input));
  }

  @Test
  @DisplayName("Нет слов, сохранение всех исходных пробелов")
  void case06() {
    String input = " 123 ";
    checkResult(input, Study.remove(input));
  }

  @Test
  @DisplayName("Краевое уточнение, отсутствие потерь/разрыва слова")
  void case07() {
    String input = "кот";
    checkResult(input, Study.remove(input));
  }

  @Test
  @DisplayName("Ё, регистр, удаление краевых/внутренних знаков")
  void case08() {
    String input = "!Ё_Я,я!";
    checkResult(input, Study.remove(input));
  }

  @Test
  @DisplayName("Непустой вход без слов и исходных пробелов")
  void case09() {
    String input = "123!";
    checkResult(input, Study.remove(input));
  }

  @Test
  @DisplayName("Сохранение латинских букв обоих регистров и границ в смешанном тексте")
  void case10() {
    String input = "a2Ё_B!";
    checkResult(input, Study.remove(input));
  }
}
