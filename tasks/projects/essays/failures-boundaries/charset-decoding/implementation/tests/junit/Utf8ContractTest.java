import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

class Utf8ContractTest {
    static Stream<String> validTexts() {
        return Stream.of("", "\u0000", "ASCII\n", "\u0080\u07FF", "\u0800\uD7FF\uE000\uFFFF",
                "\uD800\uDC00\uDBFF\uDFFF", "Я€😀", "a\uFFFDb", "\uFEFFtext", "e\u0301",
                "x".repeat(4096), "😀".repeat(1024));
    }
    @ParameterizedTest @MethodSource("validTexts")
    void preservesValidText(String expected) throws Throwable {
        byte[] input = expected.getBytes(StandardCharsets.UTF_8);
        byte[] before = input.clone();
        assertEquals(expected, decodeStrict(input));
        assertArrayEquals(before, input, "Изменён вход при успешном вызове");
    }
    static Stream<byte[]> malformedTexts() {
        return Stream.of(new byte[] {(byte) 0x80}, new byte[] {(byte) 0xBF},
                new byte[] {(byte) 0xC0, (byte) 0xAF}, new byte[] {(byte) 0xC1, (byte) 0xBF},
                new byte[] {(byte) 0xF5, (byte) 0x80, (byte) 0x80, (byte) 0x80},
                new byte[] {(byte) 0xFF}, new byte[] {(byte) 0xC2, 0x41},
                new byte[] {(byte) 0xE0, (byte) 0x80, (byte) 0xAF},
                new byte[] {(byte) 0xED, (byte) 0xA0, (byte) 0x80},
                new byte[] {(byte) 0xF0, (byte) 0x80, (byte) 0x80, (byte) 0xAF},
                new byte[] {(byte) 0xF4, (byte) 0x90, (byte) 0x80, (byte) 0x80},
                new byte[] {(byte) 0xC2}, new byte[] {(byte) 0xE2},
                new byte[] {(byte) 0xE2, (byte) 0x82}, new byte[] {(byte) 0xF0},
                new byte[] {(byte) 0xF0, (byte) 0x9F},
                new byte[] {(byte) 0xF0, (byte) 0x9F, (byte) 0x98});
    }
    @ParameterizedTest @MethodSource("malformedTexts")
    void refusesMalformedSequencesAtEveryPosition(byte[] bad) throws Throwable {
        for (String prefix : new String[] {"", "Я"}) {
            for (String suffix : new String[] {"", "Z"}) {
                byte[] left = prefix.getBytes(StandardCharsets.UTF_8);
                byte[] right = suffix.getBytes(StandardCharsets.UTF_8);
                byte[] input = new byte[left.length + bad.length + right.length];
                System.arraycopy(left, 0, input, 0, left.length);
                System.arraycopy(bad, 0, input, left.length, bad.length);
                System.arraycopy(right, 0, input, left.length + bad.length, right.length);
                byte[] before = input.clone();
                assertThrows(CharacterCodingException.class, () -> decodeStrict(input));
                assertArrayEquals(before, input, "Изменён вход при отказе");
            }
        }
    }
    @Test void acceptsDiverseScalarValues() throws Throwable {
        Random random = new Random(20260926L);
        for (int sample = 0; sample < 32; sample++) {
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < 40; i++) {
                int cp;
                do { cp = random.nextInt(0x110000); } while (cp >= 0xD800 && cp <= 0xDFFF);
                text.appendCodePoint(cp);
            }
            String expected = text.toString();
            byte[] input = expected.getBytes(StandardCharsets.UTF_8);
            byte[] before = input.clone();
            assertEquals(expected, decodeStrict(input));
            assertArrayEquals(before, input);
        }
    }
    @Test void refusesDamageAtMaximumLength() throws Throwable {
        byte[] input = new byte[4096];
        Arrays.fill(input, (byte) 'A');
        input[4095] = (byte) 0xC2;
        byte[] before = input.clone();
        assertThrows(CharacterCodingException.class, () -> decodeStrict(input));
        assertArrayEquals(before, input);
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
