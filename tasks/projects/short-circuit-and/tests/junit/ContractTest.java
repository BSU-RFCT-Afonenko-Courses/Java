import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test
    void nonpositiveQuotients() throws Exception {
        assertTokens("false", run("-7 2")); assertTokens("false", run("0 3")); assertTokens("false", run("1 2"));
    }

    @Test
    void negativeDivisor() throws Exception {
        assertTokens("true", run("-8 -2")); assertTokens("false", run("8 -2"));
    }

    @Test
    void zeroDivisorFromStdin() throws Exception {
        assertTokens("false", run("7 0"));
    }

    @Test
    void defaultDemonstratesZeroDivisor() throws Exception {
        assertTokens("false", run(""));
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
