import java.lang.Double;
import java.lang.Exception;
import java.lang.String;
import java.math.BigDecimal;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
    private void mean(TestReporter publicReporter, String[] publicCaseIds, String input, String expected) throws Exception {
        String text = publicOutput(publicReporter, publicCaseIds, JavaProcess.runCheckingUncaught(Main.class, input).stdout()).strip();
        assertTrue(text.matches("[+-]?(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)(?:[eE][+-]?[0-9]+)?"), "Выведите только одно конечное среднее с десятичной точкой");
        var delta = new BigDecimal(text).subtract(new BigDecimal(expected)).abs();
        assertTrue(delta.compareTo(new BigDecimal("0.00000001")) <= 0, "Математическое среднее: абсолютный допуск 1e-8");
    }
    @Test @DisplayName("Опубликованный пример")
    void published(TestReporter publicReporter) throws Exception { mean(publicReporter, new String[]{"normal"}, "1 2 3 4 5 6 7 8 9 10", "5.5"); }

    @Test @DisplayName("Нижняя граница")
    void lower(TestReporter publicReporter) throws Exception { mean(publicReporter, new String[]{"boundary"}, "-1000000 -1000000 -1000000 -1000000 -1000000 -1000000 -1000000 -1000000 -1000000 -1000000", "-1000000"); }

    @Test @DisplayName("Верхняя граница")
    void upper() throws Exception { mean(null, null, "1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000", "1000000"); }

    @Test @DisplayName("Дробные значения")
    void fractions() throws Exception { mean(null, null, ".1 .2 .3 .4 .5 .6 .7 .8 .9 1", ".55"); }

    @Test @DisplayName("Разные знаки")
    void cancellation() throws Exception { mean(null, null, "1000000 -1000000 0 0 0 0 0 0 0 0", "0"); }

    @Test @DisplayName("Отрицательное дробное среднее")
    void negative() throws Exception { mean(null, null, "-1 -2 -3 -4 -5 -6 -7 -8 -9 -10", "-5.5"); }

    @Test @DisplayName("Экспоненциальная запись")
    void exponent() throws Exception { mean(null, null, "1e-4 2e-4 3e-4 4e-4 5e-4 6e-4 7e-4 8e-4 9e-4 1e-3", ".00055"); }

    @Test @DisplayName("Последний элемент участвует")
    void lastValue() throws Exception { mean(null, null, "0 0 0 0 0 0 0 0 0 9", ".9"); }

    @Test @DisplayName("Собственный пример: допустимое среднее")
    void ownDemoRange() throws Exception {
        String text = publicOutput(null, null, JavaProcess.runCheckingUncaught(Main.class, "").stdout()).strip();
        assertTrue(text.matches("[+-]?(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)(?:[eE][+-]?[0-9]+)?"), "Выведите одно среднее");
        double value = Double.parseDouble(text);
        assertTrue(Double.isFinite(value) && value >= -1000000 && value <= 1000000, "Среднее допустимого демонстрационного массива");
    }

    /** Только прежние PUBLIC вызовы передают reporter; private-вызовы передают null. */
    private static String publicOutput(TestReporter reporter, String[] ids, String actual) {
        if (reporter != null) {
            String visible = actual.replaceAll("[\\p{Cntrl}&&[^\n\t]]", "�");
            if (visible.length() > 2000) visible = visible.substring(0, 2000) + "…";
            for (String id : ids) reporter.publishEntry(Map.of("public-case", id, "actual", "stdout=" + visible));
        }
        return actual;
    }
}
