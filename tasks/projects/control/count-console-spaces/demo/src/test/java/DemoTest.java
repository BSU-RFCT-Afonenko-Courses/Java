import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
    private static String run(String input) throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, input).stdout();
    }

    private static void exact(String expected, String actual) {
        assertTrue(actual.strip().matches("[+-]?[0-9]+"), "Выведите целое число");
        assertEquals(new BigInteger(expected), new BigInteger(actual.strip()),
                "Выведите целое количество пробелов");
    }

    @Test
    @DisplayName("Ввод a b c.after dot: два пробела до первой точки")
    void published() throws Exception {
        exact("2", run("a b c.after dot"));
    }

    @Test
    @DisplayName("Пустой ввод: ноль пробелов")
    void empty() throws Exception {
        exact("0", run(""));
    }
}
