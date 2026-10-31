import java.lang.Exception;
import java.lang.String;
import java.math.BigInteger;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
    private static String run(TestReporter publicReporter, String[] publicCaseIds, String input) throws Exception { return publicOutput(publicReporter, publicCaseIds, JavaProcess.runCheckingUncaught(Main.class, input).stdout()); }
    private static void check(String expected, String actual) {
        if (expected.equals("Подходящих чисел нет")) {
            assertTrue(actual.equals(expected) || actual.equals(expected + "\n") || actual.equals(expected + "\r\n"), "Сохраните сообщение об отсутствии результата");
            return;
        }
        String[] wanted = expected.strip().split("\\p{javaWhitespace}+");
        String[] found = actual.isBlank() ? new String[0] : actual.strip().split("\\p{javaWhitespace}+");
        assertEquals(wanted.length, found.length, "Сохраните число значений");
        for (int i = 0; i < wanted.length; i++)
            assertEquals(new BigInteger(wanted[i]), new BigInteger(found[i]), "Сохраните знак, порядок и повторы");
    }
    @Test @DisplayName("знак-ноль-одноцифровые-повторы")
    void case_1(TestReporter publicReporter) throws Exception { check("-121 121 7 0 -121", run(publicReporter, new String[]{"normal"}, "6 -121 121 7 0 10 -121")); }
    @Test @DisplayName("пустой-набор")
    void case_2(TestReporter publicReporter) throws Exception { check("Подходящих чисел нет", run(publicReporter, new String[]{"boundary"}, "0")); }
    @Test @DisplayName("диапазон-int-и-повторы")
    void case_3() throws Exception { check("-2147447412 11 11 -11", run(null, null, "6 -2147483648 -2147447412 11 10 11 -11")); }
    @Test @DisplayName("непустой-набор-без-совпадений")
    void case_4() throws Exception { check("Подходящих чисел нет", run(null, null, "3 10 -120 123")); }
    @Test @DisplayName("запись-числового-значения")
    void case_5() throws Exception { check("121 -121 0", run(null, null, "3 000121 -000121 0")); }

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
