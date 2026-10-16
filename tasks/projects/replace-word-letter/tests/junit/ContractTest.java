import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test void preservesAllSeparators() throws Exception { assertOutput("  a*9C*\tё*!\nA я! ", new String[]{"  ab9CD\tёж!\nA я! ", "2", "*"}); }
    @Test void replacementDoesNotChangeOriginalBoundaries() throws Exception { assertOutput(" b  d3 f", new String[]{"ab cd3ef", "1", " "}); }
    @Test void lastLetterAndShortWords() throws Exception { assertOutput("a* x A*", new String[]{"ab x AB", "2", "*"}); }
    @Test void fullIntPosition() throws Exception { assertOutput("кот abc", new String[]{"кот abc", "2147483647", "*"}); }
    @Test void noWords() throws Exception { assertOutput("123!\t\n", new String[]{"123!\t\n", "1", "*"}); }
    @Test void firstLetters() throws Exception { assertOutput("Яб Яд Яё", new String[]{"аб cд Ёё", "1", "Я"}); }
    private static void assertOutput(String expected, String[] args) throws Exception {
        String output = coursesupport.JavaProcess.run(Main.class, "", args).stdout();
        assertTrue(output.equals(expected + "\n") || output.equals(expected + "\r\n"), () -> "Получен другой текст: " + output);
    }
}
