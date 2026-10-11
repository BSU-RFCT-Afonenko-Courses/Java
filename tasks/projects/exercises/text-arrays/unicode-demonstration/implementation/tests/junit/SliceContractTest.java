import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SliceContractTest {
    @Test void allRangesForBoundaryExamples() throws Throwable {
        for (String text : new String[] {"", "ABC", "\uD83D\uDE80", "A\uD83D\uDE80B",
                "\uD800\uDC00\uDBFF\uDFFF", "e\u0301", "\u0000\uFFFF"}) {
            checkAllRanges(text);
        }
    }

    @Test void allRangesForDeterministicRandomStrings() throws Throwable {
        Random random = new Random(20260926L);
        int[] alphabet = {0, 0x41, 0x301, 0x7ff, 0xd7ff, 0xe000, 0xffff, 0x10000, 0x1f680, 0x10ffff};
        for (int sample = 0; sample < 40; sample++) {
            StringBuilder text = new StringBuilder();
            int size = random.nextInt(12);
            for (int i = 0; i < size; i++) text.appendCodePoint(alphabet[random.nextInt(alphabet.length)]);
            checkAllRanges(text.toString());
        }
    }

    private static void checkAllRanges(String text) throws Throwable {
        int[] points = text.codePoints().toArray();
        for (int from = 0; from <= points.length; from++) {
            for (int to = from; to <= points.length; to++) {
                assertEquals(new String(points, from, to - from), slice(text, from, to));
            }
        }
    }


    private static String slice(String text, int from, int to) throws Throwable {
        Class<?> type;
        try {
            type = Class.forName("UnicodeSlice");
        } catch (ClassNotFoundException missing) {
            return fail("Добавьте public класс UnicodeSlice", missing);
        }
        assertTrue(Modifier.isPublic(type.getModifiers()), "Класс UnicodeSlice должен быть public");
        Method method;
        try {
            method = type.getMethod("slice", String.class, int.class, int.class);
        } catch (NoSuchMethodException missing) {
            return fail("Добавьте public static String slice(String text, int from, int to) в UnicodeSlice", missing);
        }
        assertTrue(Modifier.isPublic(method.getModifiers()), "Метод slice должен быть public");
        assertTrue(Modifier.isStatic(method.getModifiers()), "Метод slice должен быть static");
        assertEquals(String.class, method.getReturnType(), "Метод slice должен возвращать String");
        try {
            return (String) method.invoke(null, text, from, to);
        } catch (InvocationTargetException failure) {
            throw failure.getCause();
        }
    }

}
