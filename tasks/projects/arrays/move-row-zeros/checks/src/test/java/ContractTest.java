import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
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

    @Test @DisplayName("Разделённые нулями значения")
    void separated() throws Exception { matrix("1 0 -2\n3 -3 0\n0 2 0", new double[][]{{1,-2,0},{3,-3,0},{2,0,0}}); }

    @Test @DisplayName("Порядок и число нулей")
    void counts() throws Exception { matrix("0 -3 2\n3 -1 2\n0 0 0", new double[][]{{-3,2,0},{3,-1,2},{0,0,0}}); }

    @Test @DisplayName("Строки без нулей")
    void noZeros() throws Exception { matrix("-3 -3 -3\n3 2 1\n-1 1 2", new double[][]{{-3,-3,-3},{3,2,1},{-1,1,2}}); }

    @Test @DisplayName("Все строки нулевые")
    void allZero() throws Exception { matrix("0 0 0\n0 0 0\n0 0 0", new double[][]{{0,0,0},{0,0,0},{0,0,0}}); }

    @Test @DisplayName("Повторения и нули")
    void duplicates() throws Exception { matrix("2 0 2\n0 -1 -1\n3 3 0", new double[][]{{2,2,0},{-1,-1,0},{3,3,0}}); }

    @Test @DisplayName("Нули уже в конце")
    void trailing() throws Exception { matrix("1 0 0\n-2 3 0\n3 -3 1", new double[][]{{1,0,0},{-2,3,0},{3,-3,1}}); }

}
