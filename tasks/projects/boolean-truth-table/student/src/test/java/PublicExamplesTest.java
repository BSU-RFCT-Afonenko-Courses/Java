import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PublicExamplesTest {
    @Test
    void containsAllTruthRows() throws Exception {
        assertTruthTable(run(""));
    }

    private static String run(String input, String... args) throws Exception {
        var result = coursesupport.JavaProcess.run(Main.class, input, args);
        System.err.print(result.stderr());
        return result.stdout();
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
