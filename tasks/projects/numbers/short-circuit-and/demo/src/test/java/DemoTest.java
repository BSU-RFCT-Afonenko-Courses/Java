import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
    private static String run(String input, String... args) throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, input, args).stdout();
    }

    private static String[] tokens(String text) {
        return text.strip().split("\\p{javaWhitespace}+");
    }

    private static void quotient(String input, boolean expected) throws Exception {
        assertArrayEquals(new String[] {"" + expected}, tokens(run(input)),
                "Положительность целочисленного частного без исключения");
    }

    @Test
    @DisplayName("Ввод 7 0: false без деления на ноль")
    void zero_divisor() throws Exception {
        quotient("7 0", false);
    }

    @Test
    @DisplayName("Ввод 7 2: положительное целочисленное частное")
    void positive() throws Exception {
        quotient("7 2", true);
    }
}
