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

    private static void gallons(TestReporter publicReporter, String[] publicCaseIds)
            throws Exception {
        String[] values = tokens(run(publicReporter, publicCaseIds, ""));
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
    void required_pair(TestReporter publicReporter) throws Exception {
        gallons(publicReporter, new String[] {"normal", "boundary"});
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
