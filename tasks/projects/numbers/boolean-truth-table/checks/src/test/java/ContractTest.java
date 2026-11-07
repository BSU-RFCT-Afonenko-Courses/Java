import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

    private static void table(TestReporter publicReporter, String[] publicCaseIds)
            throws Exception {
        String text = run(publicReporter, publicCaseIds, "").replace("\r\n", "\n");
        if (text.endsWith("\n"))
            text = text.substring(0, text.length() - 1);
        String[] lines = text.split("\n", -1);
        assertEquals(5, lines.length, "Заголовок и четыре строки");
        assertEquals(
                "P\tQ\tAND\tOR\tXOR\tNOT", lines[0], "Заголовок из шести колонок с табуляциями");
        Set<String> found = new HashSet<>();
        for (int i = 1; i < 5; i++) {
            String[] values = lines[i].split("\t", -1);
            assertEquals(6, values.length, "Разделитель — табуляция");
            for (String v : values)
                assertTrue(v.equals("true") || v.equals("false"),
                        "Ожидалось true или false, получено «" + v + "»");
            boolean p = Boolean.parseBoolean(values[0]), q = Boolean.parseBoolean(values[1]);
            assertTrue(found.add(p + "/" + q), "Четыре различные пары");
            assertArrayEquals(new String[] {"" + p, "" + q, "" + (p && q), "" + (p || q),
                                      "" + (p ^ q), "" + !p},
                    values, "Операции для P=" + p + ", Q=" + q + "; NOT относится к P");
        }
    }

    @Test
    @DisplayName("Таблица истинности: все операции и четыре пары")
    void all_operations_and_pairs(TestReporter publicReporter) throws Exception {
        table(publicReporter, new String[] {"normal", "boundary"});
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
