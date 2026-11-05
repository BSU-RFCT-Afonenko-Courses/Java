import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class ContractTest {
  @Test
  @DisplayName("Сортировка с отрицательными значениями и повторами")
  void sort_main(TestReporter publicReporter) throws Throwable {
    var publicActual = Study.sort(new int[] {7, -2, 7, 0, 3, -5});
    publicReporter.publishEntry(
        Map.of("public-case", "normal", "input", "Вызов: Study.sort(new int[]{7,-2,7,0,3,-5})",
            "expected", Arrays.toString(new int[] {-5, -2, 0, 3, 7, 7}), "actual",
            "array=" + Arrays.toString(publicActual)));

    assertArrayEquals(new int[] {-5, -2, 0, 3, 7, 7}, publicActual);
  }
  @Test
  @DisplayName("Пустой массив")
  void sort_empty(TestReporter publicReporter) throws Throwable {
    var publicActual = Study.sort(new int[] {});
    publicReporter.publishEntry(Map.of("public-case", "boundary-empty", "input",
        "Вызов: Study.sort(new int[]{})", "expected", Arrays.toString(new int[] {}), "actual",
        "array=" + Arrays.toString(publicActual)));

    assertArrayEquals(new int[] {}, publicActual);
  }
  @Test
  @DisplayName("Один элемент")
  void sort_single(TestReporter publicReporter) throws Throwable {
    var publicActual = Study.sort(new int[] {7});
    publicReporter.publishEntry(Map.of("public-case", "boundary-single", "input",
        "Вызов: Study.sort(new int[]{7})", "expected", Arrays.toString(new int[] {7}), "actual",
        "array=" + Arrays.toString(publicActual)));

    assertArrayEquals(new int[] {7}, publicActual);
  }
}
