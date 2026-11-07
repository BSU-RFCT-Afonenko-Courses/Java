import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class ContractTest {
    private static String run(TestReporter publicReporter, String[] publicCaseIds, String input,
            String... args) throws Exception {
        return publicOutput(publicReporter, publicCaseIds,
                JavaProcess.runCheckingUncaught(Main.class, input, args).stdout());
    }

    private static String[] tokens(String text) {
        return text.strip().split("\\p{javaWhitespace}+");
    }

    private static void image(TestReporter publicReporter, String[] publicCaseIds, int width,
            int height, int depth) throws Exception {
        long bytes = (long) width * height * depth;
        String[] values =
                tokens(run(publicReporter, publicCaseIds, width + "\n\t" + height + " " + depth));
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
    void normal(TestReporter publicReporter) throws Exception {
        String[] publicValues = tokens(publicOutput(publicReporter, new String[] {"normal"},
                JavaProcess.runCheckingUncaught(Main.class, "1024 1024 4").stdout()));
        assertEquals(2, publicValues.length, "Нужны байты и МиБ");
        assertEquals(4194304L, Long.parseLong(publicValues[0]), "Точное целое количество байтов");
        double publicMiB = Double.parseDouble(publicValues[1]);
        assertTrue(Double.isFinite(publicMiB),
                "Размер в МиБ должен быть конечным; получено " + publicMiB);
        assertEquals(4194304 / 1048576.0, publicMiB, 1e-8, "МиБ и абсолютный допуск");
        image(publicReporter, new String[] {"demo-normal"}, 1024, 1024, 4);
    }

    @Test
    @DisplayName("Один пиксель по одному байту: дробный размер в МиБ")
    void pixel(TestReporter publicReporter) throws Exception {
        String[] publicValues = tokens(publicOutput(publicReporter, new String[] {"boundary"},
                JavaProcess.runCheckingUncaught(Main.class, "1 1 1").stdout()));
        assertEquals(2, publicValues.length, "Нужны байты и МиБ");
        assertEquals(1L, Long.parseLong(publicValues[0]), "Точное целое количество байтов");
        double publicMiB = Double.parseDouble(publicValues[1]);
        assertTrue(Double.isFinite(publicMiB),
                "Размер в МиБ должен быть конечным; получено " + publicMiB);
        assertEquals(1 / 1048576.0, publicMiB, 1e-8, "МиБ и абсолютный допуск");
        image(publicReporter, new String[] {"demo-pixel"}, 1, 1, 1);
    }

    @Test
    @DisplayName("Количество байтов превосходит диапазон int")
    void large() throws Exception {
        image(null, null, 50000, 50000, 3);
    }

    @Test
    @DisplayName("Верхние границы размеров и числа байтов на пиксель")
    void maximum() throws Exception {
        image(null, null, 100000, 100000, 8);
    }

    @Test
    @DisplayName("Прямоугольное изображение: учитываются оба размера")
    void nonsquare() throws Exception {
        image(null, null, 1361, 997, 7);
    }

    @Test
    @DisplayName("Ширина в один пиксель")
    void thin() throws Exception {
        image(null, null, 1, 99991, 8);
    }

    @Test
    @DisplayName("Высота в один пиксель")
    void transposed() throws Exception {
        image(null, null, 99991, 1, 1);
    }

    @Test
    @DisplayName("Дробный размер в МиБ без округления до целого")
    void fraction() throws Exception {
        image(null, null, 11, 31, 5);
    }

    /** Только прежние PUBLIC вызовы передают reporter; private-вызовы передают null. */
    private static String publicOutput(TestReporter reporter, String[] ids, String actual) {
        if (reporter != null) {
            String visible = actual.replaceAll("[\\p{Cntrl}&&[^\n\t]]", "�");
            if (visible.length() > 2000)
                visible = visible.substring(0, 2000) + "…";
            for (String id : ids)
                reporter.publishEntry(Map.of("public-case", id, "actual", "stdout=" + visible));
        }
        return actual;
    }
}
