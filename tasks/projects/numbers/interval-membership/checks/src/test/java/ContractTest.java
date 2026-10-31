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
    private static void intervals(TestReporter publicReporter, String[] publicCaseIds, int n,int m,int k) throws Exception {
        String expected=(n<k&&k<=m)+" "+(n<=k&&k<m)+" "+(n<k&&k<m)+" "+(n<=k&&k<=m);
        assertArrayEquals(expected.split(" "),tokens(run(publicReporter, publicCaseIds, n+"\t"+m+"\n"+k)),"Строгость каждой границы");
    }
    @Test void left(TestReporter publicReporter) throws Exception { intervals(publicReporter, new String[]{"boundary"}, -2,4,-2); }
    @Test void right(TestReporter publicReporter) throws Exception { intervals(publicReporter, new String[]{"demo-right"}, -2,4,4); }
    @Test void inside(TestReporter publicReporter) throws Exception {
        assertArrayEquals(new String[]{"true","true","true","true"}, tokens(publicOutput(publicReporter, new String[]{"normal"}, JavaProcess.runCheckingUncaught(Main.class, "-2 4 0").stdout())));
 intervals(null, null, -17,23,6); }
    @Test void before() throws Exception { intervals(null, null, -17,23,-18); }
    @Test void after() throws Exception { intervals(null, null, -17,23,24); }
    @Test void negative_left() throws Exception { intervals(null, null, -9,-8,-9); }
    @Test void negative_right() throws Exception { intervals(null, null, -9,-8,-8); }
    @Test void wide() throws Exception { intervals(null, null, -2147483648,2147483647,1); }
    @Test void overflow() throws Exception { intervals(null, null, -2147483648,4,2147483647); }
    @Test void min_edge() throws Exception { intervals(null, null, -2147483648,-2147483647,-2147483648); }
    @Test void max_edge() throws Exception { intervals(null, null, 2147483646,2147483647,2147483647); }

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
