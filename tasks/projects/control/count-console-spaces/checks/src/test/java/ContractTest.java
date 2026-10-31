import java.lang.Exception;
import java.lang.String;
import java.math.BigInteger;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
    private static String run(TestReporter publicReporter, String[] publicCaseIds, String input) throws Exception { return publicOutput(publicReporter, publicCaseIds, JavaProcess.runCheckingUncaught(Main.class, input).stdout()); }
    private static void exact(String expected, String actual) { assertTrue(actual.strip().matches("[+-]?[0-9]+"), "Выведите целое число"); assertEquals(new BigInteger(expected), new BigInteger(actual.strip()), "Выведите целое количество пробелов"); }
    @Test void published(TestReporter publicReporter) throws Exception {
        publicOutput(publicReporter, new String[]{"normal"}, JavaProcess.runCheckingUncaught(Main.class, "a b c.after dot").stdout());
 exact("2", run(null, null, "a b c.after dot  ")); }
    @Test void eof_multiline() throws Exception { exact("5", run(null, null, "Конец ввода\n ещё\t строка  ")); }
    @Test void point_ignores_tail() throws Exception { exact("3", run(null, null, " а  б. хвост   \n .")); }
    @Test void unicode_separators() throws Exception { exact("1", run(null, null, "  \t\n🦊 я\r\n.")); }
    @Test void immediate_point() throws Exception { exact("0", run(null, null, ". хвост")); }
    @Test void empty(TestReporter publicReporter) throws Exception { exact("0", run(publicReporter, new String[]{"boundary"}, "")); }
    @Test void repeated_spaces() throws Exception { exact("9", run(null, null, "         .")); }
    @Test void tabs_only() throws Exception { exact("0", run(null, null, "\t\n\r\n.")); }

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
