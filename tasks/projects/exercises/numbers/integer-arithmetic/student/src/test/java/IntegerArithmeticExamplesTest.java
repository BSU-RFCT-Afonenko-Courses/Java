import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IntegerArithmeticExamplesTest {
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

    @Test @DisplayName("Пример суммы: 2 + 3 = 5")
    void sumExample() throws Throwable {
        assertEquals(5L, sum(2, 3));
    }

    @Test @DisplayName("Пример среднего: (1 + 2) / 2 = 1.5")
    void fractionalMeanExample() throws Throwable {
        assertEquals(1.5, mean(1, 2));
    }
}
