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
        var result = coursesupport.JavaProcess.run(Main.class, input, args);
        System.err.print(result.stderr());
        return result.stdout();
    }
    private static void assertTokens(String expected, String actual) {
        assertEquals(expected.trim().replaceAll("\\s+", " "), actual.trim().replaceAll("\\s+", " "));
    }
}
