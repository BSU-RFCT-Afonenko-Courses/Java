import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PublicExamplesTest {
    @Test
    void interiorAndLeftBoundary() throws Exception {
        assertTokens("true true true true", run("-2 4 0")); assertTokens("false true false true", run("-2 4 -2"));
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
