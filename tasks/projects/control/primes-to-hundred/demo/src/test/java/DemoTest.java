import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class DemoTest {
    private static String run(String input) throws Exception { return JavaProcess.runCheckingUncaught(Main.class, input).stdout(); }
    private static void check(String expected, String actual) {
        if (expected.equals("Подходящих чисел нет")) {
            assertTrue(actual.equals(expected) || actual.equals(expected + "\n") || actual.equals(expected + "\r\n"), "Сохраните сообщение об отсутствии результата");
            return;
        }
        String[] wanted = expected.strip().split("\\p{javaWhitespace}+");
        String[] found = actual.isBlank() ? new String[0] : actual.strip().split("\\p{javaWhitespace}+");
        assertEquals(wanted.length, found.length, "Сохраните число значений");
        for (int i = 0; i < wanted.length; i++)
            assertEquals(new java.math.BigInteger(wanted[i]), new java.math.BigInteger(found[i]), "Сохраните знак, порядок и повторы");
    }
    private static int[] values() throws Exception { return java.util.Arrays.stream(run("").strip().split("\\p{javaWhitespace}+")).mapToInt(Integer::parseInt).toArray(); }
    private static void ordered(int[] a) { for (int i=1; i<a.length; i++) assertTrue(a[i-1]<a[i], "Сохраните возрастающий порядок без повторов"); }
    @Test void normal() throws Exception { int[] a=values(); ordered(a); assertEquals(1, java.util.Arrays.stream(a).filter(x->x==7).count()); assertFalse(java.util.Arrays.stream(a).anyMatch(x->x==9)); }
    @Test void boundary() throws Exception { int[] a=values(); ordered(a); assertTrue(a.length>0); assertEquals(2,a[0]); assertEquals(97,a[a.length-1]); assertFalse(java.util.Arrays.stream(a).anyMatch(x->x==1||x==100)); }
}
