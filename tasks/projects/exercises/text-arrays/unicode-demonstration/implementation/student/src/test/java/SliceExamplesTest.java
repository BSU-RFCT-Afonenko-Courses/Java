import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SliceExamplesTest {
    @Test void asciiRangeHasExclusiveRightBoundary() throws Throwable {
        assertEquals("BC", slice("ABCD", 1, 3));
    }
    @Test void supplementaryCodePointRemainsWhole() throws Throwable {
        assertEquals("\uD83D\uDE80", slice("A\uD83D\uDE80B", 1, 2));
    }
    @Test void emptyInputAndEmptyRangesAreAccepted() throws Throwable {
        assertEquals("", slice("", 0, 0));
        assertEquals("", slice("AB", 1, 1));
    }
    @Test void invalidIndicesAlwaysFail() throws Throwable {
        for (int[] bounds : new int[][] {{-1, 0}, {0, -1}, {2, 1}, {0, 4}, {4, 4},
                                       {Integer.MIN_VALUE, 0}, {0, Integer.MAX_VALUE}}) {
            assertThrows(IndexOutOfBoundsException.class,
                () -> slice("A\uD83D\uDE80B", bounds[0], bounds[1]));
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
