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
    void mandatory() throws Exception { matrix("", new double[][]{{-3,3,0},{2,-1,0},{1,3,-2}}); }

    @Test @DisplayName("Нули в начале, без нулей, все нули")
    void threeStates() throws Exception { matrix("0 0 -3\n3 -1 2\n0 0 0", new double[][]{{-3,0,0},{3,-1,2},{0,0,0}}); }

}
