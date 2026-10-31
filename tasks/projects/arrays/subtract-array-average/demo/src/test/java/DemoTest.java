import java.lang.Double;
import java.lang.Exception;
import java.lang.String;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class DemoTest {
    private void matrix(String input, double[][] expected) throws Exception {
        String text = JavaProcess.runCheckingUncaught(Main.class, input).stdout().strip();
        String[] lines = text.split("\\R");
        assertEquals(3, lines.length, "Выведите три строки матрицы");
        for (int r = 0; r < 3; r++) {
            String[] values = lines[r].strip().split("\\p{javaWhitespace}+");
            assertEquals(3, values.length, "Три числа в каждой строке без подписей");
            for (int c = 0; c < 3; c++) {
                assertTrue(values[c].matches("[+-]?(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)(?:[eE][+-]?[0-9]+)?"), "Числовая запись с точкой");
                double value = Double.parseDouble(values[c]);
                assertTrue(Double.isFinite(value), "Конечное значение");
                assertEquals(expected[r][c], value, 1e-8, "Значение [" + r + "][" + c + "]");
            }
        }
    }
    @Test @DisplayName("Обязательный пример")
    void mandatory() throws Exception { matrix("", new double[][]{{-3,0,3},{5.0/3,-4.0/3,-1.0/3},{1.0/3,7.0/3,-8.0/3}}); }

    @Test @DisplayName("Постоянные строки")
    void constant() throws Exception { matrix("3 3 3\n-3 -3 -3\n0 0 0", new double[][]{{0,0,0},{0,0,0},{0,0,0}}); }

}
