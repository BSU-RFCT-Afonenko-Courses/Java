import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test
    void allNegative() throws Exception {
        assertTokens("Максимум: -1 Минимум: -9", run("3 -5 -9 -1"));
    }

    @Test
    void singleValue() throws Exception {
        assertTokens("Максимум: 42 Минимум: 42", run("1 42"));
    }

    @Test
    void rejectsNonpositiveCount() throws Exception {
        assertTokens("Количество должно быть положительным", run("0")); assertTokens("Количество должно быть положительным", run("-2"));
    }

    @Test
    void intLimits() throws Exception {
        assertTokens("Максимум: 2147483647 Минимум: -2147483648", run("3 -2147483648 0 2147483647"));
    }

    @Test
    void allPositive() throws Exception {
        assertTokens("Максимум: 9 Минимум: 2", run("3 4 2 9"));
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
    private static void assertTokens(String expected, String actual) {
        assertEquals(expected.trim().replaceAll("\\s+", " "), actual.trim().replaceAll("\\s+", " "));
    }
}
