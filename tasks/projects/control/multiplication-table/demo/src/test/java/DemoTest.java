import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DemoTest {
    private static String run(String input) throws Exception { return JavaProcess.runCheckingUncaught(Main.class, input).stdout(); }
    private static void exact(String expected, String actual) { assertTrue(actual.equals(expected) || actual.equals(expected + "\n") || actual.equals(expected + "\r\n"), "Сопоставьте текст результата с условием"); }
    @Test void row_1() throws Exception { String[] rows = run("").strip().split("\\R"); assertEquals(10, rows.length, "Выведите десять строк"); String[] values = rows[0].strip().split("\\p{javaWhitespace}+"); assertEquals(10, values.length); for (int column = 1; column <= 10; column++) assertEquals(java.math.BigInteger.valueOf(1L * column), new java.math.BigInteger(values[column - 1])); }
    @Test void row_10() throws Exception { String[] rows = run("").strip().split("\\R"); assertEquals(10, rows.length, "Выведите десять строк"); String[] values = rows[9].strip().split("\\p{javaWhitespace}+"); assertEquals(10, values.length); for (int column = 1; column <= 10; column++) assertEquals(java.math.BigInteger.valueOf(10L * column), new java.math.BigInteger(values[column - 1])); }
}
