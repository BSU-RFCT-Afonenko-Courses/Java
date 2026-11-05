import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import java.math.BigDecimal;
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

    private static void weightValue(String input, String output) {
        String[] values = tokens(output);
        assertEquals(1, values.length, "Одно число результата");
        assertTrue(Double.isFinite(assertDoesNotThrow(()
                                                              -> Double.parseDouble(values[0]),
                           "Ожидалось число результата, получено «" + values[0] + "»")),
                "Ожидался конечный результат, получено «" + values[0] + "»");
        // BigDecimal(double) сохраняет точное значение входного double для формулы 17x/100.
        var expected = new BigDecimal(Double.parseDouble(input)).multiply(new BigDecimal("0.17"));
        var tolerance =
                new BigDecimal("1e-8").max(expected.abs().multiply(new BigDecimal("1e-12")));
        var actual = assertDoesNotThrow(()
                                                -> new BigDecimal(values[0]),
                "Ожидалась десятичная или экспоненциальная запись результата, получено «"
                        + values[0] + "»");
        var error = actual.subtract(expected).abs();
        assertTrue(error.compareTo(tolerance) <= 0,
                "Вес на Луне: ожидалось " + expected + " Н, получено " + actual
                        + " Н; допуск max(1e-8, 1e-12 × |ожидаемое|) = " + tolerance
                        + " Н; абсолютная ошибка " + error + " Н");
    }

    private static void weight(TestReporter publicReporter, String[] publicCaseIds, String input)
            throws Exception {
        weightValue(input, run(publicReporter, publicCaseIds, input));
    }

    private static void defaults(TestReporter publicReporter, String[] publicCaseIds)
            throws Exception {
        String[] lines = run(publicReporter, publicCaseIds, "").strip().split("\\R", -1);
        assertEquals(2, lines.length, "Два результата по одному в строке");
        weightValue("0", lines[0]);
        String[] second = tokens(lines[1]);
        assertEquals(1, second.length, "Во второй строке один результат собственного примера");
        assertTrue(Double.isFinite(Double.parseDouble(second[0])),
                "Собственный пример — конечное число");
    }

    @Test
    @DisplayName("Ввод 100 Н: вес на Луне 17 Н")
    void normal(TestReporter publicReporter) throws Exception {
        weight(publicReporter, new String[] {"demo-normal"}, "100");
    }

    @Test
    @DisplayName("Ввод 0 Н: нулевой результат")
    void zero(TestReporter publicReporter) throws Exception {
        weight(publicReporter, new String[] {"boundary"}, "0");
    }

    @Test
    @DisplayName("Ввод 7.5 Н: дробный результат 1.275 Н")
    void fraction(TestReporter publicReporter) throws Exception {
        weight(publicReporter, new String[] {"normal"}, "7.5");
    }

    @Test
    @DisplayName("Коэффициент 17 процентов")
    void coefficient() throws Exception {
        weight(null, null, "250");
    }

    @Test
    @DisplayName("Допуск учитывает масштаб большого результата")
    void large() throws Exception {
        weight(null, null, "1e20");
    }

    @Test
    @DisplayName("Погрешность округления большого double")
    void rounding() throws Exception {
        weight(null, null, "1000000000000001");
    }

    @Test
    @DisplayName("Наименьший положительный double")
    void subnormal() throws Exception {
        weight(null, null, Double.toString(Double.MIN_VALUE));
    }

    @Test
    @DisplayName("Наименьший положительный нормальный double")
    void normal_minimum() throws Exception {
        weight(null, null, Double.toString(Double.MIN_NORMAL));
    }

    @Test
    @DisplayName("Наибольший конечный double")
    void maximum() throws Exception {
        weight(null, null, Double.toString(Double.MAX_VALUE));
    }

    @Test
    @DisplayName("Пустой ввод: два конечных результата, первый для нуля")
    void empty_demo() throws Exception {
        defaults(null, null);
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
