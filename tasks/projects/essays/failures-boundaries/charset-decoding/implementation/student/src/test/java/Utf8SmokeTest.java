import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Utf8SmokeTest {
    @Test void readsSimpleMessage() throws Throwable {
        assertEquals("Java", decodeStrict("Java".getBytes(StandardCharsets.UTF_8)));
    }
    @Test void refusesIsolatedContinuation() throws Throwable {
        assertThrows(CharacterCodingException.class,
                () -> decodeStrict(new byte[] {(byte) 0x80}));
    }

    private static String decodeStrict(byte[] input) throws Throwable {
        Class<?> type;
        try {
            type = Class.forName("Utf8Decoder");
        } catch (ClassNotFoundException missing) {
            return fail("Добавьте public класс Utf8Decoder", missing);
        }
        assertTrue(Modifier.isPublic(type.getModifiers()), "Класс Utf8Decoder должен быть public");
        Method method;
        try {
            method = type.getMethod("decodeStrict", byte[].class);
        } catch (NoSuchMethodException missing) {
            return fail("Добавьте public static String decodeStrict(byte[] input) в Utf8Decoder", missing);
        }
        assertTrue(Modifier.isPublic(method.getModifiers()), "Метод decodeStrict должен быть public");
        assertTrue(Modifier.isStatic(method.getModifiers()), "Метод decodeStrict должен быть static");
        assertEquals(String.class, method.getReturnType(), "Метод decodeStrict должен возвращать String");
        try {
            return (String) method.invoke(null, (Object) input);
        } catch (InvocationTargetException failure) {
            throw failure.getCause();
        }
    }

}
