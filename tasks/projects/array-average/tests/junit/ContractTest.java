import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test
    void negativeMean() throws Exception {
        assertNumbers(new double[]{-5.5}, run("-1 -2 -3 -4 -5 -6 -7 -8 -9 -10"));
    }

    @Test
    void fractionalValues() throws Exception {
        assertNumbers(new double[]{0.55}, run("0.1 0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.9 1.0"));
    }

    @Test
    void zeroMean() throws Exception {
        assertNumbers(new double[]{0.0}, run("-5 -4 -3 -2 -1 1 2 3 4 5"));
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
}
