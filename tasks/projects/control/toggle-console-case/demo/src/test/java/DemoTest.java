import java.lang.Exception;
import java.lang.String;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DemoTest {
    private static String run(String input) throws Exception { return JavaProcess.runCheckingUncaught(Main.class, input).stdout(); }
    private static void transformed(String text, int count, String actual) {
        boolean matches = false;
        for (String first : new String[]{"\n", "\r\n"})
            for (String last : new String[]{"\n", "\r\n"})
                matches |= actual.equals(text + first + count + last);
        assertTrue(matches, "Сохраните весь исходный текст, счётчик и оба дополнительных перевода строки");
    }
    @Test void published() throws Exception { transformed("aБяZ 👋🙂", 2, run("AБяz 👋🙂.")); }
    @Test void immediate_point() throws Exception { transformed("", 0, run(".Az")); }
}
