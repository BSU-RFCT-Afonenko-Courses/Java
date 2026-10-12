import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test
    void countsOnlyOrdinarySpace() throws Exception {
        assertTokens("3", run(" a\tb\nc  . "));
    }

    @Test
    void emptyPrefix() throws Exception {
        assertTokens("0", run(".  ")); assertTokens("0", run(""));
    }

    @Test
    void eofAlsoStops() throws Exception {
        assertTokens("2", run("А 👋 Б"));
    }

    @Test
    void consecutiveSpaces() throws Exception {
        assertTokens("5", run("     ."));
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
