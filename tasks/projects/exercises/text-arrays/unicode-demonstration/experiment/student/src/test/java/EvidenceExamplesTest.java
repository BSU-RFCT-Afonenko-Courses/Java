import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvidenceExamplesTest {
    @Test void returnsExactlyTwoExamples() throws Throwable {
        assertEquals(2, examples().length);
    }

    @Test void reportedMeasurementsMatchTheTexts() throws Throwable {
        for (var example : examples()) {
            assertNotNull(example);
            String text = text(example);
            assertNotNull(text);
            assertFalse(text.isEmpty());
            assertEquals(text.length(), utf16Units(example));
            assertEquals(text.codePointCount(0, text.length()), codePoints(example));
            assertEquals(text.substring(0, text.offsetByCodePoints(0, 1)),
                         firstCodePoint(example));
        }
    }

    private static Object[] examples() throws Throwable {
        Class<?> type;
        Class<?> valueType;
        try {
            type = Class.forName("UnicodeEvidence");
            valueType = Class.forName("UnicodeEvidence$Example");
        } catch (ClassNotFoundException missing) {
            return fail("Добавьте public класс UnicodeEvidence и public вложенный тип Example", missing);
        }
        assertTrue(Modifier.isPublic(type.getModifiers()), "Класс UnicodeEvidence должен быть public");
        assertTrue(Modifier.isPublic(valueType.getModifiers()), "Тип Example должен быть public");
        Method method = publicMethod(type, "examples");
        assertTrue(Modifier.isStatic(method.getModifiers()), "Метод examples должен быть static");
        assertEquals(java.lang.reflect.Array.newInstance(valueType, 0).getClass(), method.getReturnType(),
                "Метод examples должен возвращать Example[]");
        Object[] answer = (Object[]) invoke(method, null);
        assertNotNull(answer, "Метод examples не должен возвращать null");
        return answer;
    }

    private static String text(Object answer) throws Throwable {
        return (String) observation(answer, "text", String.class);
    }

    private static int utf16Units(Object answer) throws Throwable {
        return (Integer) observation(answer, "utf16Units", int.class);
    }

    private static int codePoints(Object answer) throws Throwable {
        return (Integer) observation(answer, "codePoints", int.class);
    }

    private static String firstCodePoint(Object answer) throws Throwable {
        return (String) observation(answer, "firstCodePoint", String.class);
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
