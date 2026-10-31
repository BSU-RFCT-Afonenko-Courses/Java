import java.lang.Double;
import java.lang.Exception;
import java.lang.Long;
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
    private static void image(TestReporter publicReporter, String[] publicCaseIds, int width,int height,int depth) throws Exception {
        long bytes=(long)width*height*depth;
        String[] values=tokens(run(publicReporter, publicCaseIds, width+"\n\t"+height+" "+depth));
        assertEquals(2,values.length,"Нужны байты и МиБ");
        assertEquals(bytes,Long.parseLong(values[0]),"Точное целое количество байтов");
        double actual=Double.parseDouble(values[1]);assertTrue(Double.isFinite(actual));
        assertEquals(bytes/1048576.0,actual,1e-8,"МиБ и абсолютный допуск");
    }
    @Test void normal(TestReporter publicReporter) throws Exception {
        publicOutput(publicReporter, new String[]{"normal"}, JavaProcess.runCheckingUncaught(Main.class, "1024 1024 4").stdout());
 image(publicReporter, new String[]{"demo-normal"}, 1024,1024,4); }
    @Test void pixel(TestReporter publicReporter) throws Exception {
        publicOutput(publicReporter, new String[]{"boundary"}, JavaProcess.runCheckingUncaught(Main.class, "1 1 1").stdout());
 image(publicReporter, new String[]{"demo-pixel"}, 1,1,1); }
    @Test void large() throws Exception { image(null, null, 50000,50000,3); }
    @Test void maximum() throws Exception { image(null, null, 100000,100000,8); }
    @Test void nonsquare() throws Exception { image(null, null, 1361,997,7); }
    @Test void thin() throws Exception { image(null, null, 1,99991,8); }
    @Test void transposed() throws Exception { image(null, null, 99991,1,1); }
    @Test void fraction() throws Exception { image(null, null, 11,31,5); }

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
