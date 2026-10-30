import coursesupport.JavaProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReverseArrayTest {
    private void check(String input, int... expected) throws Exception {
        String text = JavaProcess.runCheckingUncaught(Main.class, input).stdout().strip();
        String[] tokens = text.isEmpty() ? new String[0] : text.split("\\s+");
        int[] actual = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            assertTrue(tokens[i].matches("[+-]?[0-9]+"), "Ожидается целый компонент int, получено: " + tokens[i]);
            try { actual[i] = Integer.parseInt(tokens[i]); }
            catch (NumberFormatException ex) { fail("Компонент вне диапазона int: " + tokens[i]); }
        }
        assertArrayEquals(expected, actual, "Выведите только компоненты массива в обратном порядке");
    }

    @Test @DisplayName("Обратный порядок с повторением")
    void reverseWithDuplicates() throws Exception { check("4 7 -2 7 0\n", 0, 7, -2, 7); }

    @Test @DisplayName("Пустой массив")
    void emptyArray() throws Exception { check("0\n"); }

    @Test @DisplayName("Один элемент")
    void singleElement() throws Exception { check("1 -8\n", -8); }

    @Test @DisplayName("Крайние значения int")
    void integerLimits() throws Exception { check("3 -2147483648 0 2147483647\n", 2147483647, 0, -2147483648); }
}
