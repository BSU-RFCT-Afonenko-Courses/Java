import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
    private static String run(String input) throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, input).stdout();
    }

    private static void check(String expected, String actual) {
        if (expected.equals("Подходящих чисел нет")) {
            assertEquals(expected, actual.replaceFirst("\\r?\\n\\z", ""),
                    "Сохраните сообщение об отсутствии результата");
            return;
        }
        String[] wanted = expected.strip().split("\\p{javaWhitespace}+");
        String[] found =
                actual.isBlank() ? new String[0] : actual.strip().split("\\p{javaWhitespace}+");
        assertEquals(wanted.length, found.length, "Сохраните число значений");
        for (int i = 0; i < wanted.length; i++)
            assertEquals(new BigInteger(wanted[i]), new BigInteger(found[i]),
                    "Сохраните знак, порядок и повторы");
    }

    @Test
    @DisplayName("Палиндромы сохраняют знак, ноль, одноцифровые значения и повторы")
    void case_1() throws Exception {
        check("-121 121 7 0 -121", run("6 -121 121 7 0 10 -121"));
    }

    @Test
    @DisplayName("Пустой набор: сообщение «Подходящих чисел нет»")
    void case_2() throws Exception {
        check("Подходящих чисел нет", run("0"));
    }
}
