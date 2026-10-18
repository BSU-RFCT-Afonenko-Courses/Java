import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test
    void fractionalRowMeans() throws Exception {
        assertMatrix(new double[][]{{2.0/3,-1.0/3,-1.0/3},{-2.0/3,1.0/3,1.0/3},{0,0,0}}, run("1 0 0 -1 0 0 3 3 3"));
    }

    @Test
    void negativeRowMean() throws Exception {
        assertMatrix(new double[][]{{-1,0,1},{0,0,0},{-2,1,1}}, run("-3 -2 -1 0 0 0 -3 0 0"));
    }

    @Test
    void permutedMandatoryRows() throws Exception {
        assertMatrix(new double[][]{{1.0/3,7.0/3,-8.0/3},{-3,0,3},{5.0/3,-4.0/3,-1.0/3}}, run("1 3 -2 -3 0 3 2 -1 0"));
    }

    @Test
    void mandatoryDefaultMatrix() throws Exception {
        assertMatrix(new double[][]{{-3,0,3},{5.0/3,-4.0/3,-1.0/3},{1.0/3,7.0/3,-8.0/3}}, run(""));
    }

    private static String run(String input, String... args) throws Exception {
        var result = coursesupport.JavaProcess.run(Main.class, input, args);
        System.err.print(result.stderr());
        return result.stdout();
    }
    private static double[] numbers(String output) {
        String text = output.trim();
        if (text.isEmpty()) return new double[0];
        String[] tokens = text.split("\\s+");
        double[] result = new double[tokens.length];
        for (int i = 0; i < tokens.length; i++) result[i] = Double.parseDouble(tokens[i]);
        return result;
    }
    private static void assertNumbers(double[] expected, String output) {
        double[] actual = numbers(output);
        assertEquals(expected.length, actual.length, "Количество чисел");
        assertArrayEquals(expected, actual, 1e-8);
    }
    private static void assertMatrix(double[][] expected, String output) {
        String[] rows = output.trim().split("\\R");
        assertEquals(3, rows.length, "Три строки матрицы");
        for (int i = 0; i < 3; i++) assertNumbers(expected[i], rows[i]);
    }
}
