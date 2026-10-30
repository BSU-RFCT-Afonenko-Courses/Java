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
    @Test @DisplayName("строгость-знак-первое")
    void case_1() throws Exception { check("-128", run("4 122 -128 137 0")); }
    @Test @DisplayName("единственный-ноль")
    void case_2() throws Exception { check("0", run("1 0")); }
    @Test @DisplayName("первое-не-минимум")
    void case_3() throws Exception { check("137", run("2 137 128")); }
    @Test @DisplayName("диапазон-int-и-одноцифровое")
    void case_4() throws Exception { check("-9", run("4 -2147483648 2147483647 122 -9")); }
    @Test @DisplayName("непустой-набор-без-совпадений")
    void case_5() throws Exception { check("Подходящих чисел нет", run("3 122 321 -111")); }
    @Test @DisplayName("пустой-набор")
    void case_6() throws Exception { check("Подходящих чисел нет", run("0")); }
    @Test @DisplayName("ведущие-нули")
    void case_7() throws Exception { check("128", run("3 000128 0 9")); }
    @Test @DisplayName("ноль-перед-другим-совпадением")
    void case_8() throws Exception { check("0", run("2 0 128")); }
}
