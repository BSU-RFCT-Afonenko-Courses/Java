import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class ContractTest {
  @Test
  @DisplayName("Все позиции общего максимума")
  void remove_main(TestReporter publicReporter) throws Throwable {
    var publicActual = Study.remove(new int[][] {{-3, 0, 3}, {2, -1, 0}, {1, 3, -2}});
    publicReporter.publishEntry(Map.of("public-case", "normal", "input",
        "Вызов: Study.remove(new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}})", "expected",
        "rows=1; columns=1; values=[[2]]", "actual",
        publicActual == null ? "null"
                             : "rows=" + publicActual.rows() + "; columns=" + publicActual.columns()
                + "; values=" + Arrays.deepToString(publicActual.values())));

    MatrixResult result = publicActual;
    assertNotNull(result);
    assertEquals(1, result.rows());
    assertEquals(1, result.columns());
    assertNotNull(result.values());
    assertEquals(result.rows(), result.values().length);
    for (int[] row : result.values()) {
      assertNotNull(row);
      assertEquals(result.columns(), row.length);
    }
    assertArrayEquals(new int[][] {{2}}, result.values());
  }
  @Test
  @DisplayName("Удалены все строки и столбцы")
  void remove_0x0() throws Throwable {
    MatrixResult result = Study.remove(new int[][] {{3, 3}, {3, 3}});
    assertNotNull(result);
    assertEquals(0, result.rows());
    assertEquals(0, result.columns());
    assertNotNull(result.values());
    assertEquals(result.rows(), result.values().length);
    for (int[] row : result.values()) {
      assertNotNull(row);
      assertEquals(result.columns(), row.length);
    }
    assertArrayEquals(new int[][] {}, result.values());
  }
  @Test
  @DisplayName("Нет строк, один оставшийся столбец")
  void remove_0x1(TestReporter publicReporter) throws Throwable {
    var publicActual = Study.remove(new int[][] {{9, 1}, {9, 2}});
    publicReporter.publishEntry(Map.of("public-case", "boundary-0x1", "input",
        "Вызов: Study.remove(new int[][]{{9,1},{9,2}})", "expected", "rows=0; columns=1; values=[]",
        "actual",
        publicActual == null ? "null"
                             : "rows=" + publicActual.rows() + "; columns=" + publicActual.columns()
                + "; values=" + Arrays.deepToString(publicActual.values())));

    MatrixResult result = publicActual;
    assertNotNull(result);
    assertEquals(0, result.rows());
    assertEquals(1, result.columns());
    assertNotNull(result.values());
    assertEquals(result.rows(), result.values().length);
    for (int[] row : result.values()) {
      assertNotNull(row);
      assertEquals(result.columns(), row.length);
    }
    assertArrayEquals(new int[][] {}, result.values());
  }
  @Test
  @DisplayName("Одна строка без столбцов")
  void remove_1x0(TestReporter publicReporter) throws Throwable {
    var publicActual = Study.remove(new int[][] {{9, 9}, {1, 2}});
    publicReporter.publishEntry(Map.of("public-case", "boundary-1x0", "input",
        "Вызов: Study.remove(new int[][]{{9,9},{1,2}})", "expected",
        "rows=1; columns=0; values=[[]]", "actual",
        publicActual == null ? "null"
                             : "rows=" + publicActual.rows() + "; columns=" + publicActual.columns()
                + "; values=" + Arrays.deepToString(publicActual.values())));

    MatrixResult result = publicActual;
    assertNotNull(result);
    assertEquals(1, result.rows());
    assertEquals(0, result.columns());
    assertNotNull(result.values());
    assertEquals(result.rows(), result.values().length);
    for (int[] row : result.values()) {
      assertNotNull(row);
      assertEquals(result.columns(), row.length);
    }
    assertArrayEquals(new int[][] {{}}, result.values());
  }
}
