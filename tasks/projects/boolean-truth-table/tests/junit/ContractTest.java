import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test
    void notColumnNegatesP() throws Exception {
        assertTruthTable(run(""));
    }

    @Test
    void usesTabSeparatedColumns() throws Exception {
        String out = run(""); assertTruthTable(out); assertTrue(out.lines().allMatch(line -> line.split("\t", -1).length == 6));
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
    private static void assertTruthTable(String output) {
        String[] lines = output.trim().split("\\R");
        assertEquals(5, lines.length);
        assertTokens("P Q AND OR XOR NOT", lines[0]);
        var expected = java.util.Set.of("false false false false false true", "false true false true true true", "true false false true true false", "true true true true false false");
        var actual = new java.util.HashSet<String>();
        for (int i = 1; i < lines.length; i++) actual.add(lines[i].trim().replaceAll("\\s+", " "));
        assertEquals(expected, actual);
    }
}
