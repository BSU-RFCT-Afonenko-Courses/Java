import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class ContractTest {
  @Test
  @DisplayName("Внутренняя граница исходных частей")
  void case01(TestReporter publicReporter) {
    var publicActual = Study.insert("Java", "!", 2);
    publicReporter.publishEntry(Map.of("public-case", "insert-substring-01", "input",
        "Вызов: Study.insert(\"Java\", \"!\", 2)", "expected", String.valueOf("Ja!va"), "actual",
        "result=" + publicActual));

    assertEquals("Ja!va", publicActual, "Внутренняя граница исходных частей");
  }

  @Test
  @DisplayName("Вставка перед первым")
  void case02(TestReporter publicReporter) {
    var publicActual = Study.insert("Java", "!", 0);
    publicReporter.publishEntry(Map.of("public-case", "insert-substring-02", "input",
        "Вызов: Study.insert(\"Java\", \"!\", 0)", "expected", String.valueOf("!Java"), "actual",
        "result=" + publicActual));

    assertEquals("!Java", publicActual, "Вставка перед первым");
  }

  @Test
  @DisplayName("Вставка после последнего")
  void case03(TestReporter publicReporter) {
    var publicActual = Study.insert("Java", "!", 4);
    publicReporter.publishEntry(Map.of("public-case", "insert-substring-03", "input",
        "Вызов: Study.insert(\"Java\", \"!\", 4)", "expected", String.valueOf("Java!"), "actual",
        "result=" + publicActual));

    assertEquals("Java!", publicActual, "Вставка после последнего");
  }

  @Test
  @DisplayName("Единственная позиция пустого текста")
  void case04(TestReporter publicReporter) {
    var publicActual = Study.insert("", "код", 0);
    publicReporter.publishEntry(Map.of("public-case", "insert-substring-04", "input",
        "Вызов: Study.insert(\"\", \"код\", 0)", "expected", String.valueOf("код"), "actual",
        "result=" + publicActual));

    assertEquals("код", publicActual, "Единственная позиция пустого текста");
  }

  @Test
  @DisplayName("Пустой фрагмент")
  void case05() {
    assertEquals("текст", Study.insert("текст", "", 2), "Пустой фрагмент");
  }

  @Test
  @DisplayName("Сохранение пробела, TAB, букв и исходных координат")
  void case06() {
    assertEquals("а! б\tв", Study.insert("а б\tв", "!", 1),
        "Сохранение пробела, TAB, букв и исходных координат");
  }

  @Test
  @DisplayName("Обе части пусты")
  void case07() {
    assertEquals("", Study.insert("", "", 0), "Обе части пусты");
  }
}
