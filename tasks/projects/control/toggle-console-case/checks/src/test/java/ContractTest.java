import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
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

    private static void transformed(String text, int count, String actual) {
        boolean matches = false;
        for (String first : new String[] {"\n", "\r\n"})
            for (String last : new String[] {"\n", "\r\n"})
                matches |= actual.equals(text + first + count + last);
        assertTrue(matches,
                "Ожидались текст «" + text + "», дополнительный LF или CRLF, счётчик " + count
                        + " и LF или CRLF; получен полный вывод «" + actual + "»");
    }

    @Test
    @DisplayName("Ввод AБяz 👋🙂.: меняются только две ASCII-буквы")
    void published(TestReporter publicReporter) throws Exception {
        transformed("aБяZ 👋🙂", 2, run(publicReporter, new String[] {"normal"}, "AБяz 👋🙂."));
    }

    @Test
    @DisplayName("Пустой ввод: пустой текст и счётчик 0")
    void empty() throws Exception {
        transformed("", 0, run(null, null, ""));
    }

    @Test
    @DisplayName("Точка в начале: пустой текст и счётчик 0")
    void immediate_point(TestReporter publicReporter) throws Exception {
        transformed("", 0, run(publicReporter, new String[] {"boundary"}, ".Az"));
    }

    @Test
    @DisplayName("Границы ASCII-диапазонов и символы рядом с ними")
    void ascii_edges() throws Exception {
        transformed("azAZ @[`{09 Бя🙂", 4, run(null, null, "AZaz @[`{09 Бя🙂.extra AZ"));
    }

    @Test
    @DisplayName("Многострочный текст и табуляция сохраняются")
    void multiline() throws Exception {
        transformed("aZ09\t_\n!?", 2, run(null, null, "Az09\t_\n!?"));
    }

    @Test
    @DisplayName("Исходные переводы строк CRLF сохраняются в тексте")
    void newlines() throws Exception {
        transformed("a\r\nZ\r\n", 2, run(null, null, "A\r\nz\r\n"));
    }

    @Test
    @DisplayName("Кириллица, ß и эмодзи не меняются")
    void unchanged_unicode() throws Exception {
        transformed("БЯяß😀", 0, run(null, null, "БЯяß😀.ignored"));
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
