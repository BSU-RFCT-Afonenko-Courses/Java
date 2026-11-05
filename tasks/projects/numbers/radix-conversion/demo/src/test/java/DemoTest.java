import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
    private static String run(String... args) throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, "", args).stdout();
    }

    private static String[] tokens(String text) {
        return text.isBlank() ? new String[0] : text.strip().split("\\p{javaWhitespace}+");
    }

    private static void representation(String expected, String... args) throws Exception {
        String output = run(args).strip();
        assertTrue(output.equals(expected) || output.equals(expected.toUpperCase(Locale.ROOT)),
                "Ожидалось " + expected + " или " + expected.toUpperCase(Locale.ROOT)
                        + ", получено «" + output
                        + "»; нужен единый регистр и отсутствие ведущих нулей");
    }

    @Test
    @DisplayName("Буквенная цифра одного регистра. Вход: 31 16")
    void hex_letter() throws Exception {
        representation("1f", "31", "16");
    }

    @Test
    @DisplayName("Нулевое значение при верхней границе основания. Вход: 0 36")
    void zero_base36() throws Exception {
        representation("0", "0", "36");
    }
}
