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
    private static void exact(String expected, String actual) { assertEquals(expected.strip().replaceAll("\\p{javaWhitespace}+", " "), actual.strip().replaceAll("\\p{javaWhitespace}+", " "), "Сопоставьте подписи и значения экстремумов"); }
    @Test void published(TestReporter publicReporter) throws Exception { exact("Максимум: 7\nМинимум: -3", run(publicReporter, new String[]{"normal"}, "4 7 -3 0 7")); }
    @Test void negative_only() throws Exception { exact("Максимум: -4\nМинимум: -31", run(null, null, "4 -19 -31 -4 -8")); }
    @Test void positive_only() throws Exception { exact("Максимум: 37\nМинимум: 4", run(null, null, "4 12 37 4 22")); }
    @Test void single() throws Exception { exact("Максимум: -731\nМинимум: -731", run(null, null, "1 -731")); }
    @Test void repeated() throws Exception { exact("Максимум: 56\nМинимум: 56", run(null, null, "4 56 56 56 56")); }
    @Test void endpoints() throws Exception { exact("Максимум: 2147483647\nМинимум: -2147483648", run(null, null, "3 2147483647 -7 -2147483648")); }
    @Test void middle_extremes() throws Exception { exact("Максимум: 80\nМинимум: -44", run(null, null, "5 15 -44 80 2 3")); }
    @Test void first_extremes() throws Exception { exact("Максимум: 2147483647\nМинимум: -2147483648", run(null, null, "3 -2147483648 2147483647 5")); }
    @Test void zero(TestReporter publicReporter) throws Exception { exact("Количество должно быть положительным", run(publicReporter, new String[]{"boundary"}, "0")); }
    @Test void negative_count() throws Exception { exact("Количество должно быть положительным", run(null, null, "-13")); }
    @Test void minimum_count() throws Exception { exact("Количество должно быть положительным", run(null, null, "-2147483648")); }

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
