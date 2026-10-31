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
    private static void exact(String expected, String actual) { assertTrue(actual.equals(expected) || actual.equals(expected + "\n") || actual.equals(expected + "\r\n"), "Сопоставьте текст результата с условием"); }
    @Test void month_1(TestReporter publicReporter) throws Exception { exact("январь", run(publicReporter, new String[]{"normal"}, "1")); }
    @Test void month_2() throws Exception { exact("февраль", run(null, null, "2")); }
    @Test void month_3() throws Exception { exact("март", run(null, null, "3")); }
    @Test void month_4() throws Exception { exact("апрель", run(null, null, "4")); }
    @Test void month_5() throws Exception { exact("май", run(null, null, "5")); }
    @Test void month_6() throws Exception { exact("июнь", run(null, null, "6")); }
    @Test void month_7() throws Exception { exact("июль", run(null, null, "7")); }
    @Test void month_8() throws Exception { exact("август", run(null, null, "8")); }
    @Test void month_9() throws Exception { exact("сентябрь", run(null, null, "9")); }
    @Test void month_10() throws Exception { exact("октябрь", run(null, null, "10")); }
    @Test void month_11() throws Exception { exact("ноябрь", run(null, null, "11")); }
    @Test void month_12() throws Exception { exact("декабрь", run(null, null, "12")); }
    @Test void zero() throws Exception { exact("Некорректный месяц", run(null, null, "0")); }
    @Test void after_range() throws Exception { exact("Некорректный месяц", run(null, null, "13")); }
    @Test void negative() throws Exception { exact("Некорректный месяц", run(null, null, "-1")); }
    @Test void maximum_int() throws Exception { exact("Некорректный месяц", run(null, null, "2147483647")); }
    @Test void minimum_int() throws Exception { exact("Некорректный месяц", run(null, null, "-2147483648")); }
    @Test void fraction() throws Exception { exact("Некорректный месяц", run(null, null, "1.5")); }
    @Test void word() throws Exception { exact("Некорректный месяц", run(null, null, "abc")); }
    @Test void empty(TestReporter publicReporter) throws Exception { exact("Некорректный месяц", run(publicReporter, new String[]{"boundary"}, "")); }
    @Test void whitespace() throws Exception { exact("Некорректный месяц", run(null, null, " \t\n")); }
    @Test void first_token_only() throws Exception { exact("декабрь", run(null, null, "12 abc 1")); }
    @Test void invalid_first_token() throws Exception { exact("Некорректный месяц", run(null, null, "abc 1")); }

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
