import java.lang.Double;
import java.lang.Exception;
import java.lang.String;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ContractTest {
    private static String run(TestReporter publicReporter, String[] publicCaseIds, String input, String... args) throws Exception { return publicOutput(publicReporter, publicCaseIds, JavaProcess.runCheckingUncaught(Main.class,input,args).stdout()); }
    private static String[] tokens(String text) { return text.strip().split("\\p{javaWhitespace}+"); }
    private static void distance(TestReporter publicReporter, String[] publicCaseIds) throws Exception {
        String first=run(publicReporter, publicCaseIds, "").lines().filter(s->!s.isBlank()).findFirst().orElseThrow();
        String[] values=tokens(first);assertEquals(1,values.length,"В первой непустой строке только расстояние");
        double actual=Double.parseDouble(values[0]);assertTrue(Double.isFinite(actual));assertEquals(2412.0,actual,1e-8);
    }
    @Test void first_required_result(TestReporter publicReporter) throws Exception { distance(publicReporter, new String[]{"normal","boundary"}); }

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
