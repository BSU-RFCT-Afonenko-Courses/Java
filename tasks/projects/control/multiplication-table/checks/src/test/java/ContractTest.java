import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import java.math.BigInteger;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class ContractTest {
    private static String run(TestReporter publicReporter, String[] publicCaseIds, String input)
            throws Exception {
        return publicOutput(publicReporter, publicCaseIds,
                JavaProcess.runCheckingUncaught(Main.class, input).stdout());
    }

    private static void row(TestReporter publicReporter, String[] publicCaseIds, int multiplier)
            throws Exception {
        String[] rows = run(publicReporter, publicCaseIds, "").strip().split("\\R");
        assertEquals(10, rows.length, "Таблица должна содержать десять строк");
        String[] values = rows[multiplier - 1].strip().split("\\p{javaWhitespace}+");
        assertEquals(10, values.length,
                "В строке множителя " + multiplier + " должно быть десять значений");
        for (int column = 1; column <= 10; column++) {
            assertEquals(BigInteger.valueOf((long) multiplier * column),
                    new BigInteger(values[column - 1]),
                    "Произведение в строке " + multiplier + ", столбце " + column);
        }
    }

    @Test
    @DisplayName("Таблица 1…10: строка множителя 1")
    void row_1(TestReporter publicReporter) throws Exception {
        row(publicReporter, new String[] {"demo-row1"}, 1);
    }

    @Test
    @DisplayName("Таблица 1…10: строка множителя 2")
    void row_2() throws Exception {
        row(null, null, 2);
    }

    @Test
    @DisplayName("Таблица 1…10: строка множителя 3")
    void row_3(TestReporter publicReporter) throws Exception {
        row(publicReporter, new String[] {"normal"}, 3);
    }

    @Test
    @DisplayName("Таблица 1…10: строка множителя 4")
    void row_4() throws Exception {
        row(null, null, 4);
    }

    @Test
    @DisplayName("Таблица 1…10: строка множителя 5")
    void row_5() throws Exception {
        row(null, null, 5);
    }

    @Test
    @DisplayName("Таблица 1…10: строка множителя 6")
    void row_6() throws Exception {
        row(null, null, 6);
    }

    @Test
    @DisplayName("Таблица 1…10: строка множителя 7")
    void row_7() throws Exception {
        row(null, null, 7);
    }

    @Test
    @DisplayName("Таблица 1…10: строка множителя 8")
    void row_8() throws Exception {
        row(null, null, 8);
    }

    @Test
    @DisplayName("Таблица 1…10: строка множителя 9")
    void row_9() throws Exception {
        row(null, null, 9);
    }

    @Test
    @DisplayName("Таблица 1…10: строка множителя 10")
    void row_10(TestReporter publicReporter) throws Exception {
        row(publicReporter, new String[] {"boundary"}, 10);
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
