import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
    private static String run(String input) throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, input).stdout();
    }

    private static void transformed(String text, int count, String actual) {
        boolean matches = false;
        for (String first : new String[] {"\n", "\r\n"})
            for (String last : new String[] {"\n", "\r\n"})
                matches |= actual.equals(text + first + count + last);
        assertTrue(matches,
                "Ожидались текст «" + text + "», дополнительный LF или CRLF, счётчик " + count
                        + " и LF или CRLF; получен полный вывод «" + actual + "»");
    }

    @Test
    @DisplayName("Ввод AБяz 👋🙂.: меняются только две ASCII-буквы")
    void published() throws Exception {
        transformed("aБяZ 👋🙂", 2, run("AБяz 👋🙂."));
    }

    @Test
    @DisplayName("Точка в начале: пустой текст и счётчик 0")
    void immediate_point() throws Exception {
        transformed("", 0, run(".Az"));
    }
}
