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
    void mandatory() throws Exception { matrix("", new double[][]{{-3,0,3},{5.0/3,-4.0/3,-1.0/3},{1.0/3,7.0/3,-8.0/3}}); }

    @Test @DisplayName("Постоянные строки")
    void constant() throws Exception { matrix("3 3 3\n-3 -3 -3\n0 0 0", new double[][]{{0,0,0},{0,0,0},{0,0,0}}); }

    @Test @DisplayName("Независимые дробные средние")
    void fractional() throws Exception { matrix("-2 3 0\n1 1 -3\n-3 2 -1", new double[][]{{-2 - (1/3.0),3 - (1/3.0),0 - (1/3.0)},{1 - (-1/3.0),1 - (-1/3.0),-3 - (-1/3.0)},{-3 - (-2/3.0),2 - (-2/3.0),-1 - (-2/3.0)}}); }

    @Test @DisplayName("Разные суммы строк")
    void independent() throws Exception { matrix("3 3 -1\n-2 -2 3\n0 -1 0", new double[][]{{3 - (5/3.0),3 - (5/3.0),-1 - (5/3.0)},{-2 - (-1/3.0),-2 - (-1/3.0),3 - (-1/3.0)},{0 - (-1/3.0),-1 - (-1/3.0),0 - (-1/3.0)}}); }

    @Test @DisplayName("Ненулевые постоянные строки")
    void nonzero() throws Exception { matrix("-2 -2 -2\n2 2 2\n1 1 1", new double[][]{{-2 - (-6/3.0),-2 - (-6/3.0),-2 - (-6/3.0)},{2 - (6/3.0),2 - (6/3.0),2 - (6/3.0)},{1 - (3/3.0),1 - (3/3.0),1 - (3/3.0)}}); }

    @Test @DisplayName("Несимметричные строки")
    void asymmetric() throws Exception { matrix("3 -1 2\n-3 0 -2\n1 -1 3", new double[][]{{3 - (4/3.0),-1 - (4/3.0),2 - (4/3.0)},{-3 - (-5/3.0),0 - (-5/3.0),-2 - (-5/3.0)},{1 - (3/3.0),-1 - (3/3.0),3 - (3/3.0)}}); }

    @Test @DisplayName("Нулевая сумма каждой строки")
    void zeroSum() throws Exception { matrix("3 -3 0\n-2 1 1\n2 -1 -1", new double[][]{{3 - (0/3.0),-3 - (0/3.0),0 - (0/3.0)},{-2 - (0/3.0),1 - (0/3.0),1 - (0/3.0)},{2 - (0/3.0),-1 - (0/3.0),-1 - (0/3.0)}}); }

}
