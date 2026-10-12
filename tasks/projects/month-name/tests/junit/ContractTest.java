import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test
    void allOtherMonths() throws Exception {
        String[] expected = {"февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь"}; for (int i = 0; i < expected.length; i++) assertTokens(expected[i], run(Integer.toString(i + 2)));
    }

    @Test
    void rejectsOutOfRange() throws Exception {
        assertTokens("Некорректный месяц", run("0")); assertTokens("Некорректный месяц", run("13")); assertTokens("Некорректный месяц", run("-1"));
    }

    @Test
    void rejectsMissingOrNoninteger() throws Exception {
        assertTokens("Некорректный месяц", run("")); assertTokens("Некорректный месяц", run("abc")); assertTokens("Некорректный месяц", run("1.5"));
    }

    @Test
    void firstAndLastMonth() throws Exception {
        assertTokens("январь", run("1")); assertTokens("декабрь", run("12"));
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
}
