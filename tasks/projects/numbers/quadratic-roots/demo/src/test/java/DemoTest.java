import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
    private static String run(String... args) throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, "", args).stdout();
    }

    private static String[] tokens(String text) {
        return text.isBlank() ? new String[0] : text.strip().split("\\p{javaWhitespace}+");
    }

    private static double[] parsed(String output) {
        String[] words = tokens(output);
        double[] values = new double[words.length];
        for (int i = 0; i < words.length; i++) {
            assertTrue(
                    words[i].matches("[+-]?(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)(?:[eE][+-]?[0-9]+)?"),
                    "Конечные числа в десятичной или экспоненциальной записи");
            values[i] = Double.parseDouble(words[i]);
            assertTrue(Double.isFinite(values[i]), "Конечный корень");
        }
        return values;
    }

    private static boolean near(double actual, double expected) {
        return Math.abs(actual - expected) <= 1e-8 + 1e-12 * Math.abs(expected);
    }

    private static void roots(double[] expected, String... args) throws Exception {
        String output = run(args);
        if (expected.length == 0) {
            assertEquals(
                    "Действительных корней нет.", output.strip(), "Отрицательный дискриминант");
            return;
        }
        double[] actual = parsed(output);
        assertEquals(expected.length, actual.length, "Количество различных действительных корней");
        boolean correct = near(actual[0], expected[0]);
        if (expected.length == 2)
            correct = correct && near(actual[1], expected[1])
                    || near(actual[0], expected[1]) && near(actual[1], expected[0]);
        assertTrue(correct,
                "Ожидались корни " + Arrays.toString(expected) + ", получены "
                        + Arrays.toString(actual)
                        + ("; для каждого корня допуск 1e-8 + 1e-12 × |ожидаемый корень|; порядок "
                                + "свободный"));
    }

    @Test
    @DisplayName("Два различных корня. Вход: 1 -3 2")
    void two_roots() throws Exception {
        roots(new double[] {1, 2}, "1", "-3", "2");
    }

    @Test
    @DisplayName("Нулевой D: ровно один корень. Вход: 1 -2 1")
    void double_root() throws Exception {
        roots(new double[] {1}, "1", "-2", "1");
    }
}
