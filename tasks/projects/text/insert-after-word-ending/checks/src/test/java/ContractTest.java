import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class ContractTest {
  @Test
  @DisplayName("Суффикс, целое слово и исходная пунктуация")
  void case01(TestReporter publicReporter) {
    var publicActual = Study.insert("кот, скот!", "кот", "дом");
    publicReporter.publishEntry(Map.of("public-case", "insert-after-word-ending-01", "input",
        "Вызов: Study.insert(\"кот, скот!\", \"кот\", \"дом\")", "expected",
        String.valueOf("кот дом, скот дом!"), "actual", "result=" + publicActual));

    assertEquals("кот дом, скот дом!", publicActual, "Суффикс, целое слово и исходная пунктуация");
  }

  @Test
  @DisplayName("Регистр и вставка совпадающего слова без повторной обработки")
  void case02(TestReporter publicReporter) {
    var publicActual = Study.insert("Кот кот!", "кот", "кот");
    publicReporter.publishEntry(Map.of("public-case", "insert-after-word-ending-02", "input",
        "Вызов: Study.insert(\"Кот кот!\", \"кот\", \"кот\")", "expected",
        String.valueOf("Кот кот кот!"), "actual", "result=" + publicActual));

    assertEquals("Кот кот кот!", publicActual,
        "Регистр и вставка совпадающего слова без повторной обработки");
  }

  @Test
  @DisplayName("TAB/LF сохраняются, КОТ не совпадает")
  void case03() {
    assertEquals("кот дом\tскот дом\nКОТ", Study.insert("кот\tскот\nКОТ", "кот", "дом"),
        "TAB/LF сохраняются, КОТ не совпадает");
  }

  @Test
  @DisplayName("Нет слов, все разделители сохранены")
  void case04() {
    assertEquals(
        "123_ !\n", Study.insert("123_ !\n", "а", "а"), "Нет слов, все разделители сохранены");
  }

  @Test
  @DisplayName("Цифра и _ разделяют исходные слова")
  void case05() {
    assertEquals(
        "а а1а а_б", Study.insert("а1а_б", "а", "а"), "Цифра и _ разделяют исходные слова");
  }

  @Test
  @DisplayName("Пустой текст")
  void case06() {
    assertEquals("", Study.insert("", "кот", "дом"), "Пустой текст");
  }

  @Test
  @DisplayName("Ё/ё, кириллическая вставка и последний исходный токен")
  void case07() {
    assertEquals("Ёж Я, ёж Я", Study.insert("Ёж, ёж", "ж", "Я"),
        "Ё/ё, кириллическая вставка и последний исходный токен");
  }

  @Test
  @DisplayName("Короткое исходное слово сохраняется, следующее подходящее получает вставку")
  void case08() {
    assertEquals("я кот дом!", Study.insert("я кот!", "кот", "дом"),
        "Короткое исходное слово сохраняется, следующее подходящее получает вставку");
  }

  @Test
  @DisplayName("Латинские буквы обоих регистров образуют слово рядом с кириллицей")
  void case09() {
    assertEquals("aA X, ББ", Study.insert("aA, ББ", "aA", "X"),
        "Латинские буквы обоих регистров образуют слово рядом с кириллицей");
  }
}
