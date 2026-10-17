import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PublicExamplesTest {
    @Test
    void zeroDivisorIsSafe() throws Exception {
        assertTokens("false", run("7 0"));
    }

    @Test
    void positiveQuotient() throws Exception {
        assertTokens("true", run("7 2"));
    }

    private static String run(String input, String... args) throws Exception {
        var result = coursesupport.JavaProcess.run(Main.class, input, args);
        System.err.print(result.stderr());
        assertEquals(0, result.exitCode(), "Программа должна завершить проверку без исключения");
        return result.stdout();
    }
    private static void assertTokens(String expected, String actual) {
        assertEquals(expected.trim().replaceAll("\\s+", " "), actual.trim().replaceAll("\\s+", " "));
    }
}
