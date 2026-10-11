import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvidenceIndependentTest {
    @Test void exactlyTwoExamplesAreRequired() throws Throwable {
        assertEquals(2, examples().length, "Предъявите ровно две записи");
    }

    @Test void firstExampleStartsWithSupplementaryCodePoint() throws Throwable {
        Object[] values = examples();
        assertEquals(2, values.length);
        String value = text(values[0]);
        assertNotNull(value);
        assertFalse(value.isEmpty());
        assertTrue(value.codePointAt(0) >= Character.MIN_SUPPLEMENTARY_CODE_POINT);
    }

    @Test void secondExampleContainsOnlyPrintableAscii() throws Throwable {
        Object[] values = examples();
        assertEquals(2, values.length);
        String value = text(values[1]);
        assertNotNull(value);
        assertFalse(value.isEmpty());
        assertTrue(value.chars().allMatch(c -> c >= 0x20 && c <= 0x7e));
    }

    @Test void observationsAgreeWithCodePointArray() throws Throwable {
        for (var example : examples()) {
            String value = text(example);
            assertNotNull(value);
            assertFalse(value.isEmpty());
            for (int i = 0; i < value.length(); i++) {
                char unit = value.charAt(i);
                if (Character.isHighSurrogate(unit)) {
                    assertTrue(i + 1 < value.length(), "Суррогатная пара не должна быть оборвана");
                    assertTrue(Character.isLowSurrogate(value.charAt(++i)));
                } else {
                    assertFalse(Character.isLowSurrogate(unit));
                }
            }
            assertEquals(value.length(), utf16Units(example));
            int[] points = value.codePoints().toArray();
            assertEquals(points.length, codePoints(example));
            assertEquals(new String(points, 0, 1), firstCodePoint(example));
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
