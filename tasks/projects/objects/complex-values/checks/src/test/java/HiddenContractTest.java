import java.lang.Throwable;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HiddenContractTest {
    static void eq(double r, double i, Object x) throws Throwable {
        double[] p = TaskBinding.parts(x);
        assertTrue(Double.isFinite(p[0]) && Double.isFinite(p[1]));
        assertArrayEquals(new double[] {r, i}, p, 1e-8);
    }
    @Test
    @DisplayName("Знаки при умножении и делении, сохранность элементов массива")
    void distinctSignsAndNoMutation() throws Throwable {
        Object a = TaskBinding.value(-2, 3), b = TaskBinding.value(4, 1);
        eq(-11, 10, TaskBinding.operation(a, b, 2));
        eq(-5.0 / 17, 14.0 / 17,
           TaskBinding.operation(TaskBinding.value(-2, 3), TaskBinding.value(4, 1), 3));
        Object[] in = TaskBinding.array(TaskBinding.value(0, 1), TaskBinding.value(0, 1));
        eq(-1, 0, TaskBinding.product(in));
        eq(0, 1, in[0]);
        eq(0, 1, in[1]);
    }
}
