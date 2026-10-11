import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvidenceContractTest {
    @Test void supplementalExampleStartsWithSupplementaryCodePoint() throws Throwable {
        var first = examples()[0];
        assertTrue(text(first).codePointAt(0) >= Character.MIN_SUPPLEMENTARY_CODE_POINT);
        assertTrue(utf16Units(first) > codePoints(first));
        assertEquals(2, firstCodePoint(first).length());
    }

    @Test void controlContainsOnlyPrintableAscii() throws Throwable {
        String control = text(examples()[1]);
        assertFalse(control.isEmpty());
        assertTrue(control.chars().allMatch(c -> c >= 0x20 && c <= 0x7e));
    }

    @Test void allTextsContainOnlyPairedSurrogates() throws Throwable {
        for (var example : examples()) {
            String s = text(example);
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (Character.isHighSurrogate(c)) {
                    assertTrue(i + 1 < s.length());
                    assertTrue(Character.isLowSurrogate(s.charAt(++i)));
                } else {
                    assertFalse(Character.isLowSurrogate(c));
                }
            }
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
