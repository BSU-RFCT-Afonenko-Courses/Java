import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
    private static String run(String input, String... args) throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, input, args).stdout();
    }

    private static String[] tokens(String text) {
        return text.strip().split("\\p{javaWhitespace}+");
    }

    private static void weightValue(String input, String output) {
        String[] values = tokens(output);
        assertEquals(1, values.length, "Одно число результата");
        assertTrue(Double.isFinite(assertDoesNotThrow(()
                                                              -> Double.parseDouble(values[0]),
                           "Ожидалось число результата, получено «" + values[0] + "»")),
                "Ожидался конечный результат, получено «" + values[0] + "»");
        // BigDecimal(double) сохраняет точное значение входного double для формулы 17x/100.
        var expected = new BigDecimal(Double.parseDouble(input)).multiply(new BigDecimal("0.17"));
        var tolerance =
                new BigDecimal("1e-8").max(expected.abs().multiply(new BigDecimal("1e-12")));
        var actual = assertDoesNotThrow(()
                                                -> new BigDecimal(values[0]),
                "Ожидалась десятичная или экспоненциальная запись результата, получено «"
                        + values[0] + "»");
        var error = actual.subtract(expected).abs();
        assertTrue(error.compareTo(tolerance) <= 0,
                "Вес на Луне: ожидалось " + expected + " Н, получено " + actual
                        + " Н; допуск max(1e-8, 1e-12 × |ожидаемое|) = " + tolerance
                        + " Н; абсолютная ошибка " + error + " Н");
    }

    private static void weight(String input) throws Exception {
        weightValue(input, run(input));
    }

    private static void defaults() throws Exception {
        String[] lines = run("").strip().split("\\R", -1);
        assertEquals(2, lines.length, "Два результата по одному в строке");
        weightValue("0", lines[0]);
        String[] second = tokens(lines[1]);
        assertEquals(1, second.length, "Во второй строке один результат собственного примера");
        assertTrue(Double.isFinite(Double.parseDouble(second[0])),
                "Собственный пример — конечное число");
    }

    @Test
    @DisplayName("Ввод 100 Н: вес на Луне 17 Н")
    void normal() throws Exception {
        weight("100");
    }

    @Test
    @DisplayName("Ввод 0 Н: нулевой результат")
    void zero() throws Exception {
        weight("0");
    }
}
