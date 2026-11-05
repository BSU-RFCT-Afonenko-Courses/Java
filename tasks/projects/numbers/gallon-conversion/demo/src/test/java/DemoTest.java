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

    private static void gallons() throws Exception {
        String[] values = tokens(run(""));
        assertEquals(2, values.length, "Галлоны затем литры");
        double gallons = assertDoesNotThrow(()
                                                    -> Double.parseDouble(values[0]),
                "Ожидалось количество галлонов, получено «" + values[0] + "»");
        double litres = assertDoesNotThrow(()
                                                   -> Double.parseDouble(values[1]),
                "Ожидалось количество литров, получено «" + values[1] + "»");
        assertTrue(Double.isFinite(gallons) && Double.isFinite(litres),
                "Галлоны и литры должны быть конечными; получено " + gallons + " и " + litres);
        assertEquals(10, gallons, 1e-8, "Количество галлонов, абсолютный допуск 1e-8");
        assertEquals(37.854, litres, 1e-8, "Количество литров, абсолютный допуск 1e-8");
    }

    @Test
    @DisplayName("Без ввода: 10 галлонов и 37.854 литра, абсолютный допуск 1e-8")
    void required_pair() throws Exception {
        gallons();
    }
}
