import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class ContractTest {
  @Test
  @DisplayName("Регистр, естественный порядок, повторы и мутация")
  void case01(TestReporter publicReporter) {
    String[] values = new String[] {"beta", "alpha", "beta", "Gamma"};
    Study.sort(values);
    publicReporter.publishEntry(Map.of("public-case", "bubble-sort-strings-01", "input",
        Arrays.toString(new String[] {"beta", "alpha", "beta", "Gamma"}), "expected",
        Arrays.toString(new String[] {"Gamma", "alpha", "beta", "beta"}), "actual",
        Arrays.toString(values)));

    assertArrayEquals(new String[] {"Gamma", "alpha", "beta", "beta"}, values,
        "Регистр, естественный порядок, повторы и мутация");
  }

  @Test
  @DisplayName("Пустая граница")
  void case02(TestReporter publicReporter) {
    String[] values = new String[] {};
    Study.sort(values);
    publicReporter.publishEntry(
        Map.of("public-case", "bubble-sort-strings-02", "input", Arrays.toString(new String[] {}),
            "expected", Arrays.toString(new String[] {}), "actual", Arrays.toString(values)));

    assertArrayEquals(new String[] {}, values, "Пустая граница");
  }

  @Test
  @DisplayName("Уже упорядоченный массив")
  void case03(TestReporter publicReporter) {
    String[] values = new String[] {"Gamma", "alpha", "beta", "beta"};
    Study.sort(values);
    publicReporter.publishEntry(Map.of("public-case", "bubble-sort-strings-03", "input",
        Arrays.toString(new String[] {"Gamma", "alpha", "beta", "beta"}), "expected",
        Arrays.toString(new String[] {"Gamma", "alpha", "beta", "beta"}), "actual",
        Arrays.toString(values)));

    assertArrayEquals(
        new String[] {"Gamma", "alpha", "beta", "beta"}, values, "Уже упорядоченный массив");
  }

  @Test
  @DisplayName("Один элемент")
  void case04() {
    String[] values = new String[] {"Я"};
    Study.sort(values);
    assertArrayEquals(new String[] {"Я"}, values, "Один элемент");
  }

  @Test
  @DisplayName("Пустая строка, повторы, UTF-16-порядок и знак compareTo")
  void case05() {
    String[] values = new String[] {"z", "a", "", "Ёж", "Я", "z"};
    Study.sort(values);
    assertArrayEquals(new String[] {"", "a", "z", "z", "Ёж", "Я"}, values,
        "Пустая строка, повторы, UTF-16-порядок и знак compareTo");
  }

  @Test
  @DisplayName("Префиксный порядок и несколько проходов")
  void case06() {
    String[] values = new String[] {"zzz", "zz", "z", ""};
    Study.sort(values);
    assertArrayEquals(
        new String[] {"", "z", "zz", "zzz"}, values, "Префиксный порядок и несколько проходов");
  }
}
