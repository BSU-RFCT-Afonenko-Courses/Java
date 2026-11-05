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

    private static void quotient(TestReporter publicReporter, String[] publicCaseIds, String input,
            boolean expected) throws Exception {
        assertArrayEquals(new String[] {"" + expected},
                tokens(run(publicReporter, publicCaseIds, input)),
                "Положительность целочисленного частного без исключения");
    }

    @Test
    @DisplayName("Ввод 7 0: false без деления на ноль")
    void zero_divisor(TestReporter publicReporter) throws Exception {
        quotient(publicReporter, new String[] {"boundary"}, "7 0", false);
    }

    @Test
    @DisplayName("Ввод 7 2: положительное целочисленное частное")
    void positive(TestReporter publicReporter) throws Exception {
        quotient(publicReporter, new String[] {"normal"}, "7 2", true);
    }

    @Test
    @DisplayName("Дробное математическое частное усекается до нуля")
    void truncated() throws Exception {
        quotient(null, null, "1 2", false);
    }

    @Test
    @DisplayName("Отрицательное частное не положительно")
    void negative() throws Exception {
        quotient(null, null, "-7 2", false);
    }

    @Test
    @DisplayName("Нулевой числитель даёт false")
    void zero() throws Exception {
        quotient(null, null, "0 3", false);
    }

    @Test
    @DisplayName("Разные знаки: отрицательное частное")
    void negative_divisor() throws Exception {
        quotient(null, null, "8 -2", false);
    }

    @Test
    @DisplayName("Integer.MIN_VALUE / -1 сохраняет правила int Java")
    void overflow() throws Exception {
        quotient(null, null, "-2147483648 -1", false);
    }

    @Test
    @DisplayName("Два отрицательных операнда дают положительное частное")
    void both_negative() throws Exception {
        quotient(null, null, "-8 -2", true);
    }

    @Test
    @DisplayName("Пустой ввод демонстрирует пару 7 0")
    void empty_demo() throws Exception {
        quotient(null, null, "", false);
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
