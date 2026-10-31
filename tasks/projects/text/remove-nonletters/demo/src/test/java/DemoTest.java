import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
  private static java.util.List<String> words(String text) {
    var result = new java.util.ArrayList<String>();
    var matcher = java.util.regex.Pattern.compile("[A-Za-zА-Яа-яЁё]+").matcher(text);
    while (matcher.find()) result.add(matcher.group());
    return result;
  }
  private static java.util.List<Integer> spaces(String text) {
    var result = new java.util.ArrayList<Integer>();
    var matcher = java.util.regex.Pattern.compile("[A-Za-zА-Яа-яЁё]+").matcher(text);
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
    assertNotNull(actual, "P2: результат существует");
    if (source.isEmpty()) {
      assertEquals("", actual, "P1: пустой вход");
      return;
    }
    assertTrue(
        actual.matches("[A-Za-zА-Яа-яЁё ]*"), "P2: только буквы и U+0020; получено=" + actual);
    assertEquals(words(source), words(actual), "P3: слова, порядок, регистр и повторы");
    var before = spaces(source);
    var after = spaces(actual);
    assertEquals(before.size(), after.size(), "P4: соответствующие промежутки");
    for (int i = 0; i < before.size(); i++)
      assertTrue(after.get(i) >= before.get(i),
          "P4: промежуток " + i + ", исходных пробелов=" + before.get(i)
              + ", получено=" + after.get(i));
  }
  @Test
  @DisplayName("remove-nonletters-01 — Удаление без слияния, исходные два пробела; public normal")
  void case01() {
    String input = "кот,дом  2я!";
    checkResult(input, Study.remove(input));
  }

  @Test
  @DisplayName("remove-nonletters-02 — Явное исключение для пустого входа; public boundary")
  void case02() {
    String input = "";
    checkResult(input, Study.remove(input));
  }
}
