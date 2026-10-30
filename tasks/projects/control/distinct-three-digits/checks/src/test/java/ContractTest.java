import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
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
    @Test @DisplayName("знак-порядок-повторы")
    void case_1() throws Exception { check("123 -123 123", run("6 123 -123 112 99 1000 123")); }
    @Test @DisplayName("пустой-набор")
    void case_2() throws Exception { check("Подходящих чисел нет", run("0")); }
    @Test @DisplayName("границы-int-и-цифра-ноль")
    void case_3() throws Exception { check("102 -102 987 102 -987", run("12 -2147483648 2147483647 102 -102 987 999 100 101 98 1000 102 -987")); }
    @Test @DisplayName("запись-числового-значения")
    void case_4() throws Exception { check("123 -123 102", run("3 000123 -000123 +102")); }
    @Test @DisplayName("непустой-набор-без-совпадений")
    void case_5() throws Exception { check("Подходящих чисел нет", run("5 0 11 -99 100 111")); }
}
