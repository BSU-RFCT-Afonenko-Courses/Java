import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SignsTest {
    @Test
    @DisplayName("Нормализация знака, равенство дробей и арифметика")
    void signsEqualityAndSubstantialValues() {
        Object a = TaskBinding.value(6, -8), b = TaskBinding.value(-3, 4),
               c = TaskBinding.value(15, -20);
        assertArrayEquals(new long[] {-3, 4}, TaskBinding.parts(a));
        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(a, c);
        assertEquals(a.hashCode(), c.hashCode());
        assertNotEquals(a, TaskBinding.value(3, 4));
        assertFalse(a.equals(null));
        assertArrayEquals(new long[] {0, 1}, TaskBinding.parts(TaskBinding.value(0, 7)));
        for (int op = 0; op < 4; op++) {
            Object x = TaskBinding.value(-5, 7), y = TaskBinding.value(2, 3);
            Object z = TaskBinding.operation(x, y, op);
            long[][] expected = {{-1, 21}, {-29, 21}, {-10, 21}, {-15, 14}};
            assertArrayEquals(expected[op], TaskBinding.parts(z));
            assertArrayEquals(new long[] {-5, 7}, TaskBinding.parts(x));
            assertArrayEquals(new long[] {2, 3}, TaskBinding.parts(y));
        }
    }
}
