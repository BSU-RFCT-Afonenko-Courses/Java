import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PublicExamplesTest {
    @Test void sample() throws Exception { assertOutput("a2B2a", new String[]{"aaBBa"}); }
    @Test void empty() throws Exception { assertOutput("", new String[]{""}); }
    @Test void multiDigitRun() throws Exception { assertOutput("a12", new String[]{"aaaaaaaaaaaa"}); }
    private static void assertOutput(String expected, String[] args) throws Exception {
        String output = coursesupport.JavaProcess.run(Main.class, "", args).stdout();
        assertTrue(output.equals(expected + "\n") || output.equals(expected + "\r\n"), () -> "Получен другой текст: " + output);
    }
}
