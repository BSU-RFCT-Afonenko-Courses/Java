import java.lang.Double;
import java.lang.Exception;
import java.lang.String;
import java.math.BigDecimal;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ContractTest {
    private static String run(TestReporter publicReporter, String[] publicCaseIds, String input, String... args) throws Exception { return publicOutput(publicReporter, publicCaseIds, JavaProcess.runCheckingUncaught(Main.class,input,args).stdout()); }
    private static String[] tokens(String text) { return text.strip().split("\\p{javaWhitespace}+"); }
    private static void weightValue(String input,String output) {
        String[] values=tokens(output); assertEquals(1,values.length,"Одно число результата");
        assertTrue(Double.isFinite(Double.parseDouble(values[0])),"Конечный результат");
        var expected=new BigDecimal(Double.parseDouble(input)).multiply(new BigDecimal("0.17"));
        var tolerance=new BigDecimal("1e-8").max(expected.abs().multiply(new BigDecimal("1e-12")));
        var error=new BigDecimal(values[0]).subtract(expected).abs();
        assertTrue(error.compareTo(tolerance)<=0,()->"Ошибка "+error+" превышает "+tolerance);
    }
    private static void weight(TestReporter publicReporter, String[] publicCaseIds, String input) throws Exception {weightValue(input,run(publicReporter, publicCaseIds, input));}
    private static void defaults(TestReporter publicReporter, String[] publicCaseIds) throws Exception {
        String[] lines=run(publicReporter, publicCaseIds, "").strip().split("\\R",-1);assertEquals(2,lines.length,"Два результата по одному в строке");
        weightValue("0",lines[0]);String[] second=tokens(lines[1]);assertEquals(1,second.length);
        assertTrue(Double.isFinite(Double.parseDouble(second[0])),"Собственный пример — конечное число");
    }
    @Test void normal(TestReporter publicReporter) throws Exception { weight(publicReporter, new String[]{"demo-normal"}, "100"); }
    @Test void zero(TestReporter publicReporter) throws Exception { weight(publicReporter, new String[]{"boundary"}, "0"); }
    @Test void fraction(TestReporter publicReporter) throws Exception { weight(publicReporter, new String[]{"normal"}, "7.5"); }
    @Test void coefficient() throws Exception { weight(null, null, "250"); }
    @Test void large() throws Exception { weight(null, null, "1e20"); }
    @Test void rounding() throws Exception { weight(null, null, "1000000000000001"); }
    @Test void subnormal() throws Exception { weight(null, null, Double.toString(Double.MIN_VALUE)); }
    @Test void normal_minimum() throws Exception { weight(null, null, Double.toString(Double.MIN_NORMAL)); }
    @Test void maximum() throws Exception { weight(null, null, Double.toString(Double.MAX_VALUE)); }
    @Test void empty_demo() throws Exception { defaults(null, null); }

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
