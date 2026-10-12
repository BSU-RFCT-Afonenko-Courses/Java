import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test
    void emptyArray() throws Exception {
        assertTokens("", run("0"));
    }

    @Test
    void singleElement() throws Exception {
        assertTokens("-9", run("1 -9"));
    }

    @Test
    void evenAndOddLengths() throws Exception {
        assertTokens("3 2 1", run("3 1 2 3")); assertTokens("4 3 2 1", run("4 1 2 3 4"));
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
