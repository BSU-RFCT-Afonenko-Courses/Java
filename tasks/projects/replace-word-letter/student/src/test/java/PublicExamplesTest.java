import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PublicExamplesTest {
    @Test void sample() throws Exception { assertOutput("к*т, я!", new String[]{"кот, я!", "2", "*"}); }
    @Test void argumentWithSpaces() throws Exception { assertOutput("к*т д*м", new String[]{"кот дом", "2", "*"}); }
    @Test void empty() throws Exception { assertOutput("", new String[]{"", "2", "*"}); }
    private static void assertOutput(String expected, String[] args) throws Exception {
        String output = coursesupport.JavaProcess.run(Main.class, "", args).stdout();
        assertTrue(output.equals(expected + "\n") || output.equals(expected + "\r\n"), () -> "Получен другой текст: " + output);
    }
}
