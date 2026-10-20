import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PublicExamplesTest {
    @Test void positiveWeight() throws Exception { assertWeight("100"); }
    @Test void largeFiniteWeights() throws Exception {
        assertWeight("1000000000000001"); assertWeight("1e20");
        assertWeight(Double.toString(Double.MAX_VALUE));
    }
    @Test void zeroAndSubnormal() throws Exception {
        assertWeight("0"); assertWeight(Double.toString(Double.MIN_VALUE));
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
