import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PublicExamplesTest {
    @Test void sample() throws Exception { assertOutput("Максимумы: кот дом кот\nМинимумы: и", new String[]{"кот, дом и кот!"}); }
    @Test void empty() throws Exception { assertOutput("Слов нет", new String[]{""}); }
    private static void assertOutput(String expected, String[] args) throws Exception {
        String output = coursesupport.JavaProcess.run(Main.class, "", args).stdout();
        assertTrue(output.equals(expected + "\n") || output.equals(expected.replace("\n", "\r\n") + "\r\n"), () -> "Получен другой текст: " + output);
    }
}
