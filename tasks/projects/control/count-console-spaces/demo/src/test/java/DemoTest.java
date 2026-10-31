import java.lang.Exception;
import java.lang.String;
import java.math.BigInteger;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DemoTest {
    private static String run(String input) throws Exception { return JavaProcess.runCheckingUncaught(Main.class, input).stdout(); }
    private static void exact(String expected, String actual) { assertTrue(actual.strip().matches("[+-]?[0-9]+"), "Выведите целое число"); assertEquals(new BigInteger(expected), new BigInteger(actual.strip()), "Выведите целое количество пробелов"); }
    @Test void published() throws Exception { exact("2", run("a b c.after dot")); }
    @Test void empty() throws Exception { exact("0", run("")); }
}
