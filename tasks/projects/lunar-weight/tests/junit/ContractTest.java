import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test void zeroWeight() throws Exception { assertWeight("0"); }
    @Test void fractionalWeight() throws Exception { assertWeight("7.5"); }
    @Test void coefficientIsSeventeenPercent() throws Exception { assertWeight("250"); }
    @Test void roundingCounterexamples() throws Exception {
        assertWeight("1000000000000001"); assertWeight("1e20");
    }
    @Test void smallFiniteWeights() throws Exception {
        assertWeight(Double.toString(Double.MIN_VALUE));
        assertWeight(Double.toString(Double.MIN_NORMAL));
    }
    @Test void largestFiniteWeight() throws Exception { assertWeight(Double.toString(Double.MAX_VALUE)); }
    @Test void defaultShowsZeroAndOwnExample() throws Exception {
        String[] lines = run("").strip().split("\\R", -1);
        assertEquals(2, lines.length, "Два результата демонстрации по одному числу в строке");
        assertWeightValue("0", lines[0]);
        assertEquals(1, coursesupport.OutputNumbers.finiteNumbers(lines[1]).length,
            "Собственный пример — одно конечное число во второй строке");
        // Студент выбирает положительное исходное значение и задаёт его в коде решения.
        // Для очень малого значения результат может округлиться до нуля.
    }
    private static String run(String input) throws Exception {
        return coursesupport.JavaProcess.run(Main.class, input).stdout();
    }
    private static void assertWeight(String input) throws Exception {
        assertWeightValue(input, run(input));
    }
    private static void assertWeightValue(String input, String output) {
        double[] actual = coursesupport.OutputNumbers.finiteNumbers(output);
        assertEquals(1, actual.length, "Количество чисел");
        // Ожидаемое значение точно вычисляется в BigDecimal по прочитанному double.
        var expected = new java.math.BigDecimal(Double.parseDouble(input))
            .multiply(new java.math.BigDecimal("0.17"));
        var tolerance = new java.math.BigDecimal("1e-8").max(
            expected.abs().multiply(new java.math.BigDecimal("1e-12")));
        var error = new java.math.BigDecimal(output.strip()).subtract(expected).abs();
        assertTrue(error.compareTo(tolerance) <= 0,
            () -> "Ошибка " + error + " превышает допуск " + tolerance);
    }
}
