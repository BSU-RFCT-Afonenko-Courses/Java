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
        var result = coursesupport.JavaProcess.run(Main.class, input, args);
        System.err.print(result.stderr());
        return result.stdout();
    }
    private static void assertTokens(String expected, String actual) {
        assertEquals(expected.trim().replaceAll("\\s+", " "), actual.trim().replaceAll("\\s+", " "));
    }
}
