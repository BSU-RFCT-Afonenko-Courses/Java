import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PublicExamplesTest {
    @Test
    void mandatoryMatrix() throws Exception {
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
