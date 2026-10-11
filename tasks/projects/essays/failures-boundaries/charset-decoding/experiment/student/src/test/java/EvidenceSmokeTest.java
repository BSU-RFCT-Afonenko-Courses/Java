import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvidenceSmokeTest {
    @Test void providesThreeValues() throws Throwable {
        var answer = evidence();
        assertNotNull(answer);
        assertNotNull(malformed(answer));
        assertNotNull(valid(answer));
        assertNotNull(expected(answer));
    }

    private static Object evidence() throws Throwable {
        Class<?> type;
        Class<?> valueType;
        try {
            type = Class.forName("Utf8Evidence");
            valueType = Class.forName("Utf8Evidence$Evidence");
        } catch (ClassNotFoundException missing) {
            return fail("Добавьте public класс Utf8Evidence и public вложенный тип Evidence", missing);
        }
        assertTrue(Modifier.isPublic(type.getModifiers()), "Класс Utf8Evidence должен быть public");
        assertTrue(Modifier.isPublic(valueType.getModifiers()), "Тип Evidence должен быть public");
        Method method = publicMethod(type, "evidence");
        assertTrue(Modifier.isStatic(method.getModifiers()), "Метод evidence должен быть static");
        assertEquals(valueType, method.getReturnType(),
                "Метод evidence должен возвращать Evidence");
        Object answer = invoke(method, null);
        assertNotNull(answer, "Метод evidence не должен возвращать null");
        return answer;
    }

    private static byte[] malformed(Object answer) throws Throwable {
        return (byte[]) observation(answer, "malformed", byte[].class);
    }

    private static byte[] valid(Object answer) throws Throwable {
        return (byte[]) observation(answer, "valid", byte[].class);
    }

    private static String expected(Object answer) throws Throwable {
        return (String) observation(answer, "expected", String.class);
    }

    private static Object observation(Object answer, String name, Class<?> expectedType) throws Throwable {
        assertNotNull(answer, "Элемент результата не должен быть null");
        Method accessor = publicMethod(answer.getClass(), name);
        assertFalse(Modifier.isStatic(accessor.getModifiers()), "Аксессор " + name + " должен быть методом экземпляра");
        assertEquals(expectedType, accessor.getReturnType(), "Неверный тип результата аксессора " + name);
        return invoke(accessor, answer);
    }

    private static Method publicMethod(Class<?> type, String name) {
        try {
            Method method = type.getMethod(name);
            assertTrue(Modifier.isPublic(method.getModifiers()), "Метод " + name + " должен быть public");
            return method;
        } catch (NoSuchMethodException missing) {
            return fail("Добавьте public метод " + name + "() в " + type.getSimpleName(), missing);
        }
    }

    private static Object invoke(Method method, Object receiver) throws Throwable {
        try {
            return method.invoke(receiver);
        } catch (InvocationTargetException failure) {
            throw failure.getCause();
        }
    }

}
