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
        coursesupport.JavaProcess.Result result;
        try {
            result = coursesupport.JavaProcess.runCheckingUncaught(Main.class, input, args);
        } catch (java.io.IOException failure) {
            throw new org.opentest4j.TestAbortedException("Не удалось подготовить проверку запуска", failure);
        }
        System.err.print(result.stderr());
        return result.stdout();
    }
    private static void assertTokens(String expected, String actual) {
        assertEquals(expected.trim().replaceAll("\\s+", " "), actual.trim().replaceAll("\\s+", " "));
    }
}
