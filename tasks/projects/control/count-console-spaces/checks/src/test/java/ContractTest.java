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

    private static void exact(String expected, String actual) {
        assertTrue(actual.strip().matches("[+-]?[0-9]+"), "Выведите целое число");
        assertEquals(new BigInteger(expected), new BigInteger(actual.strip()),
                "Выведите целое количество пробелов");
    }

    @Test
    @DisplayName("Ввод a b c.after dot: два пробела до первой точки")
    void published(TestReporter publicReporter) throws Exception {
        exact("2",
                publicOutput(publicReporter, new String[] {"normal"},
                        JavaProcess.runCheckingUncaught(Main.class, "a b c.after dot").stdout()));
        exact("2", run(null, null, "a b c.after dot  "));
    }

    @Test
    @DisplayName("Конец многострочного ввода завершает подсчёт")
    void eof_multiline() throws Exception {
        exact("5", run(null, null, "Конец ввода\n ещё\t строка  "));
    }

    @Test
    @DisplayName("Пробелы после первой точки не учитываются")
    void point_ignores_tail() throws Exception {
        exact("3", run(null, null, " а  б. хвост   \n ."));
    }

    @Test
    @DisplayName("Учитывается только U+0020 среди Unicode-разделителей")
    void unicode_separators() throws Exception {
        exact("1", run(null, null, "  \t\n🦊 я\r\n."));
    }

    @Test
    @DisplayName("Точка в начале: ноль пробелов")
    void immediate_point() throws Exception {
        exact("0", run(null, null, ". хвост"));
    }

    @Test
    @DisplayName("Пустой ввод: ноль пробелов")
    void empty(TestReporter publicReporter) throws Exception {
        exact("0", run(publicReporter, new String[] {"boundary"}, ""));
    }

    @Test
    @DisplayName("Каждый из последовательных пробелов учитывается")
    void repeated_spaces() throws Exception {
        exact("9", run(null, null, "         ."));
    }

    @Test
    @DisplayName("Табуляции и переводы строк не считаются пробелами")
    void tabs_only() throws Exception {
        exact("0", run(null, null, "\t\n\r\n."));
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
