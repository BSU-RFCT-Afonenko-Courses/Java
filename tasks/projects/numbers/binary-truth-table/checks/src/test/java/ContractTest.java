import java.lang.Exception;
import java.lang.String;
import java.util.HashMap;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
    private static String run(TestReporter publicReporter, String[] publicCaseIds, String... args) throws Exception {
        return publicOutput(publicReporter, publicCaseIds, JavaProcess.runCheckingUncaught(Main.class,"",args).stdout());
    }
    private static String[] tokens(String text) {
        return text.isBlank()?new String[0]:text.strip().split("\\p{javaWhitespace}+");
    }
    private static Map<String,String[]> table(TestReporter publicReporter, String[] publicCaseIds) throws Exception {
        String[] lines=run(publicReporter, publicCaseIds).lines().filter(s->!s.isBlank()).toArray(String[]::new);
        assertEquals(5,lines.length,"Заголовок и четыре строки");
        assertArrayEquals(new String[]{"P","Q","AND","OR","XOR","NOT"},tokens(lines[0]),"Шесть колонок, пробельные разделители");
        var rows=new HashMap<String,String[]>();
        for(int i=1;i<5;i++) {
            String[] values=tokens(lines[i]);assertEquals(6,values.length,"Шесть значений в строке");
            for(String value:values)assertTrue(value.equals("0")||value.equals("1"),"Истина — 1, ложь — 0");
            assertNull(rows.put(values[0]+"/"+values[1],values),"Четыре различные пары P,Q");
        }
        return rows;
    }
    private static void pair(TestReporter publicReporter, String[] publicCaseIds, String key,String... expected) throws Exception {
        assertArrayEquals(expected,table(publicReporter, publicCaseIds).get(key),"Операции и отрицание P для пары "+key);
    }
    @Test @DisplayName("Операции AND/OR/XOR и отрицание P для самостоятельного сочетания. Вход: без аргументов")
    void pair_0_0(TestReporter publicReporter) throws Exception { pair(publicReporter, new String[]{"boundary"}, "0/0","0","0","0","0","0","1"); }
    @Test @DisplayName("Операции AND/OR/XOR и отрицание P для самостоятельного сочетания. Вход: без аргументов")
    void pair_0_1() throws Exception { pair(null, null, "0/1","0","1","0","1","1","1"); }
    @Test @DisplayName("Операции AND/OR/XOR и отрицание P для самостоятельного сочетания. Вход: без аргументов")
    void pair_1_0(TestReporter publicReporter) throws Exception { pair(publicReporter, new String[]{"normal"}, "1/0","1","0","0","1","1","0"); }
    @Test @DisplayName("Операции AND/OR/XOR и отрицание P для самостоятельного сочетания. Вход: без аргументов")
    void pair_1_1() throws Exception { pair(null, null, "1/1","1","1","1","1","0","0"); }

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
