import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test
    void allNegative() throws Exception {
        assertTokens("Максимум: -1 Минимум: -9", run("3 -5 -9 -1"));
    }

    @Test
    void singleValue() throws Exception {
        assertTokens("Максимум: 42 Минимум: 42", run("1 42"));
    }

    @Test
    void rejectsNonpositiveCount() throws Exception {
        assertTokens("Количество должно быть положительным", run("0")); assertTokens("Количество должно быть положительным", run("-2"));
    }

    @Test
    void intLimits() throws Exception {
        assertTokens("Максимум: 2147483647 Минимум: -2147483648", run("3 -2147483648 0 2147483647"));
    }

    @Test
    void allPositive() throws Exception {
        assertTokens("Максимум: 9 Минимум: 2", run("3 4 2 9"));
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
