import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test void singleton() throws Exception { assertOutput("Z", new String[]{"Z"}); }
    @Test void caseIsSignificant() throws Exception { assertOutput("aA2a", new String[]{"aAAa"}); }
    @Test void lastRun() throws Exception { assertOutput("ab3", new String[]{"abbb"}); }
    @Test void allSingletons() throws Exception { assertOutput("aBcD", new String[]{"aBcD"}); }
    @Test void severalRuns() throws Exception { assertOutput("a10B11c2D", new String[]{"aaaaaaaaaaBBBBBBBBBBBccD"}); }
    private static void assertOutput(String expected, String[] args) throws Exception {
        String output = coursesupport.JavaProcess.run(Main.class, "", args).stdout();
        assertTrue(output.equals(expected + "\n") || output.equals(expected + "\r\n"), () -> "Получен другой текст: " + output);
    }
}
