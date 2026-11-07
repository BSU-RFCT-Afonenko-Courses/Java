import java.lang.Throwable;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContractTest {
    @Test
    @DisplayName("Пустой массив и шесть общих ссылок: сумма, произведение, сохранность")
    void emptyAndSixElementsKept() throws Throwable {
        Object[] empty = TaskBinding.array();
        assertArrayEquals(new double[] {0, 0}, TaskBinding.parts(TaskBinding.sum(empty)), 1e-8);
        assertArrayEquals(new double[] {1, 0}, TaskBinding.parts(TaskBinding.product(empty)), 1e-8);
        Object same = TaskBinding.value(10, 10);
        Object[] a = TaskBinding.array(same, same, same, same, same, same);
        assertArrayEquals(new double[] {60, 60}, TaskBinding.parts(TaskBinding.sum(a)), 1e-8);
        for (Object x : a)
            assertArrayEquals(new double[] {10, 10}, TaskBinding.parts(x), 0);
        assertArrayEquals(
            new double[] {0, -8000000}, TaskBinding.parts(TaskBinding.product(a)), 1e-8);
        for (Object x : a)
            assertArrayEquals(new double[] {10, 10}, TaskBinding.parts(x), 0);
    }
}
