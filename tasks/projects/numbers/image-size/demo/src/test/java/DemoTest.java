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

    private static void image(int width, int height, int depth) throws Exception {
        long bytes = (long) width * height * depth;
        String[] values = tokens(run(width + "\n\t" + height + " " + depth));
        assertEquals(2, values.length, "Нужны байты и МиБ");
        assertEquals(bytes,
                assertDoesNotThrow(()
                                           -> Long.parseLong(values[0]),
                        "Ожидалось целое количество байтов, получено «" + values[0] + "»"),
                "Точное целое количество байтов");
        double actual = assertDoesNotThrow(()
                                                   -> Double.parseDouble(values[1]),
                "Ожидался размер в МиБ, получено «" + values[1] + "»");
        assertTrue(
                Double.isFinite(actual), "Размер в МиБ должен быть конечным; получено " + actual);
        assertEquals(bytes / 1048576.0, actual, 1e-8, "МиБ и абсолютный допуск");
    }

    @Test
    @DisplayName("Изображение 1024 × 1024 × 4: точные байты и размер в МиБ")
    void normal() throws Exception {
        image(1024, 1024, 4);
    }

    @Test
    @DisplayName("Один пиксель по одному байту: дробный размер в МиБ")
    void pixel() throws Exception {
        image(1, 1, 1);
    }
}
