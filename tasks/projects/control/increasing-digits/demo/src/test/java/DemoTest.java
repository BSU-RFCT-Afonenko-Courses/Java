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
    @DisplayName("Первое число со строго возрастающими цифрами, с сохранением знака")
    void case_1() throws Exception {
        check("-128", run("4 122 -128 137 0"));
    }

    @Test
    @DisplayName("Единственный ноль подходит как одноцифровое число")
    void case_2() throws Exception {
        check("0", run("1 0"));
    }
}
