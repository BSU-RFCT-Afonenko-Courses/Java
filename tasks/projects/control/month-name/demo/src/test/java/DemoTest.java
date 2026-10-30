import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DemoTest {
    private static String run(String input) throws Exception { return JavaProcess.runCheckingUncaught(Main.class, input).stdout(); }
    private static void exact(String expected, String actual) { assertTrue(actual.equals(expected) || actual.equals(expected + "\n") || actual.equals(expected + "\r\n"), "Сопоставьте текст результата с условием"); }
    @Test void month_1() throws Exception { exact("январь", run("1")); }
    @Test void empty() throws Exception { exact("Некорректный месяц", run("")); }
}
