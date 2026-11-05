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

    private static void distance() throws Exception {
        String first = run("").lines().filter(s -> !s.isBlank()).findFirst().orElse("");
        assertFalse(first.isBlank(),
                "Ожидалось расстояние 2412 м в первой непустой строке; получен пустой вывод");
        String[] values = tokens(first);
        assertEquals(1, values.length, "В первой непустой строке только расстояние");
        double actual = assertDoesNotThrow(()
                                                   -> Double.parseDouble(values[0]),
                "Ожидалось расстояние в метрах, получено «" + values[0] + "»");
        assertTrue(Double.isFinite(actual), "Расстояние должно быть конечным; получено " + actual);
        assertEquals(2412.0, actual, 1e-8, "Расстояние в метрах, абсолютный допуск 1e-8");
    }

    @Test
    @DisplayName("Без ввода: первая непустая строка — расстояние 2412 м, допуск 1e-8")
    void first_required_result() throws Exception {
        distance();
    }
}
