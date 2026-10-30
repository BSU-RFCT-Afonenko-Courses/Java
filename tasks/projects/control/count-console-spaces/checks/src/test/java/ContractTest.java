import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
    private static String run(String input) throws Exception { return JavaProcess.runCheckingUncaught(Main.class, input).stdout(); }
    private static void exact(String expected, String actual) { assertTrue(actual.strip().matches("[+-]?[0-9]+"), "Выведите целое число"); assertEquals(new java.math.BigInteger(expected), new java.math.BigInteger(actual.strip()), "Выведите целое количество пробелов"); }
    @Test void published() throws Exception { exact("2", run("a b c.after dot  ")); }
    @Test void eof_multiline() throws Exception { exact("5", run("Конец ввода\n ещё\t строка  ")); }
    @Test void point_ignores_tail() throws Exception { exact("3", run(" а  б. хвост   \n .")); }
    @Test void unicode_separators() throws Exception { exact("1", run("  \t\n🦊 я\r\n.")); }
    @Test void immediate_point() throws Exception { exact("0", run(". хвост")); }
    @Test void empty() throws Exception { exact("0", run("")); }
    @Test void repeated_spaces() throws Exception { exact("9", run("         .")); }
    @Test void tabs_only() throws Exception { exact("0", run("\t\n\r\n.")); }
}
