import java.lang.Exception;
import java.lang.String;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
    private static String run(TestReporter publicReporter, String[] publicCaseIds, String input) throws Exception { return publicOutput(publicReporter, publicCaseIds, JavaProcess.runCheckingUncaught(Main.class, input).stdout()); }
    private static void transformed(String text, int count, String actual) {
        boolean matches = false;
        for (String first : new String[]{"\n", "\r\n"})
            for (String last : new String[]{"\n", "\r\n"})
                matches |= actual.equals(text + first + count + last);
        assertTrue(matches, "Сохраните весь исходный текст, счётчик и оба дополнительных перевода строки");
    }
    @Test void published(TestReporter publicReporter) throws Exception { transformed("aБяZ 👋🙂", 2, run(publicReporter, new String[]{"normal"}, "AБяz 👋🙂.")); }
    @Test void empty() throws Exception { transformed("", 0, run(null, null, "")); }
    @Test void immediate_point(TestReporter publicReporter) throws Exception { transformed("", 0, run(publicReporter, new String[]{"boundary"}, ".Az")); }
    @Test void ascii_edges() throws Exception { transformed("azAZ @[`{09 Бя🙂", 4, run(null, null, "AZaz @[`{09 Бя🙂.extra AZ")); }
    @Test void multiline() throws Exception { transformed("aZ09\t_\n!?", 2, run(null, null, "Az09\t_\n!?")); }
    @Test void newlines() throws Exception { transformed("a\r\nZ\r\n", 2, run(null, null, "A\r\nz\r\n")); }
    @Test void unchanged_unicode() throws Exception { transformed("БЯяß😀", 0, run(null, null, "БЯяß😀.ignored")); }

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
