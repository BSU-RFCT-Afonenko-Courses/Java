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

    private static void intervals(int n, int m, int k) throws Exception {
        String expected = (n < k && k <= m) + " " + (n <= k && k < m) + " " + (n < k && k < m) + " "
                + (n <= k && k <= m);
        assertArrayEquals(expected.split(" "), tokens(run(n + "\t" + m + "\n" + k)),
                "Строгость каждой границы");
    }

    @Test
    @DisplayName("Ввод -2 4 -2: принадлежность четырём интервалам на левой границе")
    void left() throws Exception {
        intervals(-2, 4, -2);
    }

    @Test
    @DisplayName("Ввод -2 4 4: принадлежность четырём интервалам на правой границе")
    void right() throws Exception {
        intervals(-2, 4, 4);
    }
}
