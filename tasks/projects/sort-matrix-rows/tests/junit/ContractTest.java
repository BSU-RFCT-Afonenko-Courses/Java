import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test
    void equalSumsKeepInputOrder() throws Exception {
        assertMatrix(new double[][]{{0,0,0},{2,-1,0},{0,1,0}}, run("2 -1 0 0 1 0 0 0 0"));
    }

    @Test
    void negativeSumsComeFirst() throws Exception {
        assertMatrix(new double[][]{{-3,0,0},{-1,0,0},{3,0,0}}, run("3 0 0 -1 0 0 -3 0 0"));
    }

    @Test
    void allSumsEqual() throws Exception {
        assertMatrix(new double[][]{{3,-3,0},{0,3,-3},{-3,0,3}}, run("3 -3 0 0 3 -3 -3 0 3"));
    }

    @Test
    void reversedRows() throws Exception {
        assertMatrix(new double[][]{{-3,0,3},{2,-1,0},{1,3,-2}}, run("1 3 -2 2 -1 0 -3 0 3"));
    }

    private static String run(String input, String... args) throws Exception {
        var previousIn = System.in;
        var previousOut = System.out;
        var bytes = new java.io.ByteArrayOutputStream();
        try (var output = new java.io.PrintStream(bytes, true, java.nio.charset.StandardCharsets.UTF_8)) {
            System.setIn(new java.io.ByteArrayInputStream(input.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
            System.setOut(output);
            Main.main(args);
            output.flush();
            return bytes.toString(java.nio.charset.StandardCharsets.UTF_8);
        } finally {
            System.setIn(previousIn);
            System.setOut(previousOut);
        }
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
