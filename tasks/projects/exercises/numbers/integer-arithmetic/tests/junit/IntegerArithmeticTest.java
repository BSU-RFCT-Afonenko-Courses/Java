import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IntegerArithmeticTest {
    private static Object call(String name, Class<?> returnType, int a, int b) throws Throwable {
        Class<?> type = assertDoesNotThrow(() -> Class.forName("IntegerArithmetic"),
                "Ожидается класс IntegerArithmetic без package");
        Method method = assertDoesNotThrow(() -> type.getMethod(name, int.class, int.class),
                "Ожидается public static " + returnType.getSimpleName() + " " + name + "(int, int)");
        assertTrue(Modifier.isPublic(type.getModifiers()), "Класс IntegerArithmetic должен быть public");
        assertTrue(Modifier.isStatic(method.getModifiers()), "Метод " + name + " должен быть static");
        assertEquals(returnType, method.getReturnType(), "Неверный тип результата метода " + name);
        try {
            return method.invoke(null, a, b);
        } catch (InvocationTargetException failure) {
            throw failure.getCause();
        }
    }

    private static long sum(int a, int b) throws Throwable {
        return (Long) call("sum", long.class, a, b);
    }

    private static double mean(int a, int b) throws Throwable {
        return (Double) call("mean", double.class, a, b);
    }

    @Test @DisplayName("Сумма выходит за диапазон int без потери значения")
    void wideSums() throws Throwable {
        assertEquals(4294967294L, sum(Integer.MAX_VALUE, Integer.MAX_VALUE));
        assertEquals(-4294967296L, sum(Integer.MIN_VALUE, Integer.MIN_VALUE));
        assertEquals(-1L, sum(Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertEquals(2147483648L, sum(Integer.MAX_VALUE, 1));
    }

    @Test @DisplayName("Среднее сохраняет половину для нечётной суммы")
    void fractionalMeans() throws Throwable {
        assertEquals(1.5, mean(1, 2));
        assertEquals(-1.5, mean(-2, -1));
        assertEquals(-0.5, mean(1, -2));
    }

    @Test @DisplayName("Среднее корректно для границ int")
    void wideMeans() throws Throwable {
        assertEquals(2147483647.0, mean(Integer.MAX_VALUE, Integer.MAX_VALUE));
        assertEquals(-2147483648.0, mean(Integer.MIN_VALUE, Integer.MIN_VALUE));
        assertEquals(-0.5, mean(Integer.MIN_VALUE, Integer.MAX_VALUE));
    }

    @Test @DisplayName("Ноль и обычные положительные и отрицательные значения")
    void basicValues() throws Throwable {
        assertEquals(0L, sum(0, 0));
        assertEquals(5L, sum(2, 3));
        assertEquals(-5L, sum(-2, -3));
        assertEquals(0.0, mean(0, 0));
        assertEquals(3.0, mean(2, 4));
    }
}
