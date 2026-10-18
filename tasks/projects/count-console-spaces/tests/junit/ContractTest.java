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
        var result = coursesupport.JavaProcess.run(Main.class, input, args);
        System.err.print(result.stderr());
        return result.stdout();
    }
    private static void assertTokens(String expected, String actual) {
        assertEquals(expected.trim().replaceAll("\\s+", " "), actual.trim().replaceAll("\\s+", " "));
    }
}
