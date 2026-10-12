import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test
    void rightBoundary() throws Exception {
        assertTokens("true false false true", run("-2 4 4"));
    }

    @Test
    void outsideBothSides() throws Exception {
        assertTokens("false false false false", run("-2 4 -3")); assertTokens("false false false false", run("-2 4 5"));
    }

    @Test
    void adjacentBounds() throws Exception {
        assertTokens("false true false true", run("8 9 8")); assertTokens("true false false true", run("8 9 9"));
    }

    @Test
    void intBoundsWithoutOverflow() throws Exception {
        assertTokens("true true true true", run("-2147483648 2147483647 0"));
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
