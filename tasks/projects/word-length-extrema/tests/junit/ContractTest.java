import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test void equalLengthsAndDuplicates() throws Exception { assertOutput("Максимумы: я A я Ё\nМинимумы: я A я Ё", new String[]{"я A я Ё"}); }
    @Test void orderAndRepeatedExtrema() throws Exception { assertOutput("Максимумы: zzz aaa zzz\nМинимумы: b a b", new String[]{"zzz b aaa a zzz b"}); }
    @Test void digitsAndTabsAreSeparators() throws Exception { assertOutput("Максимумы: ёж AB\nМинимумы: Я", new String[]{"ёж2AB\tЯ"}); }
    @Test void noWords() throws Exception { assertOutput("Слов нет", new String[]{"123 !\t\n"}); }
    @Test void oneWord() throws Exception { assertOutput("Максимумы: Ёлка\nМинимумы: Ёлка", new String[]{"Ёлка"}); }
    private static void assertOutput(String expected, String[] args) throws Exception {
        String output = coursesupport.JavaProcess.run(Main.class, "", args).stdout();
        assertTrue(output.equals(expected + "\n") || output.equals(expected.replace("\n", "\r\n") + "\r\n"), () -> "Получен другой текст: " + output);
    }
}
