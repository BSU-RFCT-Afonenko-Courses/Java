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

    private static void distance(TestReporter publicReporter, String[] publicCaseIds)
            throws Exception {
        String first = run(publicReporter, publicCaseIds, "")
                               .lines()
                               .filter(s -> !s.isBlank())
                               .findFirst()
                               .orElse("");
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
    void first_required_result(TestReporter publicReporter) throws Exception {
        distance(publicReporter, new String[] {"normal", "boundary"});
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
