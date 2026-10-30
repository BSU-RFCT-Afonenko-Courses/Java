import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class DemoTest {
    private void mean(String input, String expected) throws Exception {
        String text = JavaProcess.runCheckingUncaught(Main.class, input).stdout().strip();
        assertTrue(text.matches("[+-]?(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)(?:[eE][+-]?[0-9]+)?"), "Выведите только одно конечное среднее с десятичной точкой");
        var delta = new java.math.BigDecimal(text).subtract(new java.math.BigDecimal(expected)).abs();
        assertTrue(delta.compareTo(new java.math.BigDecimal("0.00000001")) <= 0, "Математическое среднее: абсолютный допуск 1e-8");
    }
    @Test @DisplayName("Опубликованный пример")
    void published() throws Exception { mean("1 2 3 4 5 6 7 8 9 10", "5.5"); }

    @Test @DisplayName("Нижняя граница")
    void lower() throws Exception { mean("-1000000 -1000000 -1000000 -1000000 -1000000 -1000000 -1000000 -1000000 -1000000 -1000000", "-1000000"); }

}
