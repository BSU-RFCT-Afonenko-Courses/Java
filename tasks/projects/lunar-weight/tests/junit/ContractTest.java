import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test
    void zeroWeight() throws Exception {
        assertNumbers(new double[]{0.0}, run("0"));
    }

    @Test
    void fractionalWeight() throws Exception {
        assertNumbers(new double[]{1.275}, run("7.5"));
    }

    @Test
    void coefficientIsSeventeenPercent() throws Exception {
        assertNumbers(new double[]{42.5}, run("250"));
    }

    @Test
    void defaultShowsZeroAndPositiveExample() throws Exception {
        double[] values = numbers(run(""));
        assertEquals(2, values.length);
        assertEquals(0.0, values[0], 1e-8);
        assertTrue(values[1] > 0.0 && Double.isFinite(values[1]));
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
