import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChecksContractTest {
    static String correct(String text, int from, int to) {
        int[] points = text.codePoints().toArray();
        if (from < 0 || to < from || to > points.length)
            throw new IndexOutOfBoundsException();
        return new String(points, from, to - from);
    }

    static void verify(CodePointSlice operation) throws Throwable {
        Class<?> type;
        Method method;
        try {
            type = Class.forName("SliceChecks");
            method = type.getMethod("verify", CodePointSlice.class);
        } catch (ReflectiveOperationException absent) {
            fail("Добавьте public static void SliceChecks.verify(CodePointSlice)", absent);
            return;
        }
        assertTrue(Modifier.isPublic(type.getModifiers()));
        assertTrue(Modifier.isPublic(method.getModifiers()));
        assertTrue(Modifier.isStatic(method.getModifiers()));
        assertEquals(void.class, method.getReturnType());
        try { method.invoke(null, operation); }
        catch (InvocationTargetException failure) { throw failure.getCause(); }
    }

    @Test void acceptsIndependentCorrectImplementation() {
        assertDoesNotThrow(() -> verify(ChecksContractTest::correct));
    }

    @Test void rejectsUtf16Indexing() {
        assertThrows(AssertionError.class, () -> verify(String::substring));
    }

    @Test void rejectsInclusiveRightBoundary() {
        assertThrows(AssertionError.class, () -> verify((s, from, to) -> {
            int size = s.codePointCount(0, s.length());
            if (from < 0 || to < from || to > size) throw new IndexOutOfBoundsException();
            return correct(s, from, to < size ? to + 1 : to);
        }));
    }

    @Test void rejectsFailureOnEmptyRange() {
        assertThrows(AssertionError.class, () -> verify((s, from, to) -> {
            if (from == to) throw new IllegalArgumentException("Пустой диапазон");
            return correct(s, from, to);
        }));
    }

    @Test void rejectsImplementationAcceptingInvalidRange() {
        assertThrows(AssertionError.class, () -> verify((s, from, to) -> {
            int size = s.codePointCount(0, s.length());
            if (from < 0 || to < from || to > size) return "";
            return correct(s, from, to);
        }));
    }

    @Test void checksAtLeastOneInvalidRange() {
        int[] invalidCalls = {0};
        assertDoesNotThrow(() -> verify((s, from, to) -> {
            int size = s.codePointCount(0, s.length());
            if (from < 0 || to < from || to > size) {
                invalidCalls[0]++;
                throw new IndexOutOfBoundsException();
            }
            return correct(s, from, to);
        }));
        assertTrue(invalidCalls[0] > 0, "Включите хотя бы один недопустимый диапазон");
    }
}
