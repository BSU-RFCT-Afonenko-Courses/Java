import java.lang.Exception;
import java.lang.String;
import java.math.BigInteger;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
    private static String run(TestReporter publicReporter, String[] publicCaseIds, String input) throws Exception { return publicOutput(publicReporter, publicCaseIds, JavaProcess.runCheckingUncaught(Main.class, input).stdout()); }
    private static void exact(String expected, String actual) { assertTrue(actual.equals(expected) || actual.equals(expected + "\n") || actual.equals(expected + "\r\n"), "Сопоставьте текст результата с условием"); }
    @Test void row_1(TestReporter publicReporter) throws Exception { String[] rows = run(publicReporter, new String[]{"demo-row1"}, "").strip().split("\\R"); assertEquals(10, rows.length, "Выведите десять строк"); String[] values = rows[0].strip().split("\\p{javaWhitespace}+"); assertEquals(10, values.length); for (int column = 1; column <= 10; column++) assertEquals(BigInteger.valueOf(1L * column), new BigInteger(values[column - 1])); }
    @Test void row_2() throws Exception { String[] rows = run(null, null, "").strip().split("\\R"); assertEquals(10, rows.length, "Выведите десять строк"); String[] values = rows[1].strip().split("\\p{javaWhitespace}+"); assertEquals(10, values.length); for (int column = 1; column <= 10; column++) assertEquals(BigInteger.valueOf(2L * column), new BigInteger(values[column - 1])); }
    @Test void row_3(TestReporter publicReporter) throws Exception { String[] rows = run(publicReporter, new String[]{"normal"}, "").strip().split("\\R"); assertEquals(10, rows.length, "Выведите десять строк"); String[] values = rows[2].strip().split("\\p{javaWhitespace}+"); assertEquals(10, values.length); for (int column = 1; column <= 10; column++) assertEquals(BigInteger.valueOf(3L * column), new BigInteger(values[column - 1])); }
    @Test void row_4() throws Exception { String[] rows = run(null, null, "").strip().split("\\R"); assertEquals(10, rows.length, "Выведите десять строк"); String[] values = rows[3].strip().split("\\p{javaWhitespace}+"); assertEquals(10, values.length); for (int column = 1; column <= 10; column++) assertEquals(BigInteger.valueOf(4L * column), new BigInteger(values[column - 1])); }
    @Test void row_5() throws Exception { String[] rows = run(null, null, "").strip().split("\\R"); assertEquals(10, rows.length, "Выведите десять строк"); String[] values = rows[4].strip().split("\\p{javaWhitespace}+"); assertEquals(10, values.length); for (int column = 1; column <= 10; column++) assertEquals(BigInteger.valueOf(5L * column), new BigInteger(values[column - 1])); }
    @Test void row_6() throws Exception { String[] rows = run(null, null, "").strip().split("\\R"); assertEquals(10, rows.length, "Выведите десять строк"); String[] values = rows[5].strip().split("\\p{javaWhitespace}+"); assertEquals(10, values.length); for (int column = 1; column <= 10; column++) assertEquals(BigInteger.valueOf(6L * column), new BigInteger(values[column - 1])); }
    @Test void row_7() throws Exception { String[] rows = run(null, null, "").strip().split("\\R"); assertEquals(10, rows.length, "Выведите десять строк"); String[] values = rows[6].strip().split("\\p{javaWhitespace}+"); assertEquals(10, values.length); for (int column = 1; column <= 10; column++) assertEquals(BigInteger.valueOf(7L * column), new BigInteger(values[column - 1])); }
    @Test void row_8() throws Exception { String[] rows = run(null, null, "").strip().split("\\R"); assertEquals(10, rows.length, "Выведите десять строк"); String[] values = rows[7].strip().split("\\p{javaWhitespace}+"); assertEquals(10, values.length); for (int column = 1; column <= 10; column++) assertEquals(BigInteger.valueOf(8L * column), new BigInteger(values[column - 1])); }
    @Test void row_9() throws Exception { String[] rows = run(null, null, "").strip().split("\\R"); assertEquals(10, rows.length, "Выведите десять строк"); String[] values = rows[8].strip().split("\\p{javaWhitespace}+"); assertEquals(10, values.length); for (int column = 1; column <= 10; column++) assertEquals(BigInteger.valueOf(9L * column), new BigInteger(values[column - 1])); }
    @Test void row_10(TestReporter publicReporter) throws Exception { String[] rows = run(publicReporter, new String[]{"boundary"}, "").strip().split("\\R"); assertEquals(10, rows.length, "Выведите десять строк"); String[] values = rows[9].strip().split("\\p{javaWhitespace}+"); assertEquals(10, values.length); for (int column = 1; column <= 10; column++) assertEquals(BigInteger.valueOf(10L * column), new BigInteger(values[column - 1])); }

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
