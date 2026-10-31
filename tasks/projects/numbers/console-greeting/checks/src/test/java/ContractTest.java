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
    private static void greeting(TestReporter publicReporter, String[] publicCaseIds, String expected, String... args) throws Exception {
        String value=run(publicReporter, publicCaseIds, "stdin не имя",args).replace("\r\n","\n").replace("\r","\n");
        assertTrue(value.equals(expected)||value.equals(expected+"\n"),"Сохраните сообщение, имя и одну строку без лишнего вывода");
    }
    @Test void normal(TestReporter publicReporter) throws Exception {
        publicOutput(publicReporter, new String[]{"normal"}, JavaProcess.runCheckingUncaught(Main.class, "", "Анна").stdout());
 greeting(publicReporter, new String[]{"demo-normal"}, "Здравствуйте, Анна!","Анна"); }
    @Test void absent(TestReporter publicReporter) throws Exception {
        publicOutput(publicReporter, new String[]{"boundary"}, JavaProcess.runCheckingUncaught(Main.class, "").stdout());
 greeting(publicReporter, new String[]{"demo-absent"}, "Укажите имя."); }
    @Test void empty_present() throws Exception { greeting(null, null, "Здравствуйте, !",""); }
    @Test void unicode_edges() throws Exception { greeting(null, null, "Здравствуйте,  Ёж 🦔  !"," Ёж 🦔  "); }
    @Test void later_ignored() throws Exception { greeting(null, null, "Здравствуйте, Майя!","Майя","ещё"); }
    @Test void combining() throws Exception { greeting(null, null, "Здравствуйте, И\u0306ван!","И\u0306ван"); }

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
