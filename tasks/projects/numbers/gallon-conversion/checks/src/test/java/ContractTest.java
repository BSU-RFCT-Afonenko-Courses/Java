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
    private static void gallons(TestReporter publicReporter, String[] publicCaseIds) throws Exception {
        String[] values=tokens(run(publicReporter, publicCaseIds, ""));assertEquals(2,values.length,"Галлоны затем литры");
        double a=Double.parseDouble(values[0]),b=Double.parseDouble(values[1]);assertTrue(Double.isFinite(a)&&Double.isFinite(b));
        assertEquals(10,a,1e-8);assertEquals(37.854,b,1e-8);
    }
    @Test void required_pair(TestReporter publicReporter) throws Exception { gallons(publicReporter, new String[]{"normal","boundary"}); }

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
