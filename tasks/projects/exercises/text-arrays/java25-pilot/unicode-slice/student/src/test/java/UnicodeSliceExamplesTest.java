import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnicodeSliceExamplesTest {
    private static Method sliceMethod() {
        Class<?> type = assertDoesNotThrow(() -> Class.forName("UnicodeSlice"),
            "Не найден класс UnicodeSlice. Сохраните имя класса из заготовки.");
        assertTrue(Modifier.isPublic(type.getModifiers()), "Класс UnicodeSlice должен быть public.");
        Method method = assertDoesNotThrow(
            () -> type.getMethod("slice", String.class, int.class, int.class),
            "Нужен public метод slice(String text, int from, int to). Проверьте имя, параметры и public.");
        assertTrue(Modifier.isPublic(method.getModifiers()), "Метод slice должен быть public.");
        assertTrue(Modifier.isStatic(method.getModifiers()), "Метод slice должен быть static.");
        assertEquals(String.class, method.getReturnType(), "Метод slice должен возвращать String.");
        return method;
    }

    private static String slice(String text, int from, int to) throws Throwable {
        try {
            return (String) sliceMethod().invoke(null, text, from, to);
        } catch (InvocationTargetException failure) {
            throw failure.getCause();
        }
    }

    @Test @DisplayName("Сигнатура: public static String slice(String, int, int)")
    void requiredApiIsAvailable() {
        sliceMethod();
    }

    @Test @DisplayName("Открытый пример: A😀Б, диапазон [1, 2)")
    void supplementaryCodePointRemainsWhole() {
        assertEquals("😀", assertDoesNotThrow(() -> slice("A😀Б", 1, 2)),
            "Метод должен вернуть целую кодовую точку 😀.");
    }

    @Test @DisplayName("Открытый пример: пустой диапазон")
    void emptyRangeIsAccepted() {
        assertEquals("", assertDoesNotThrow(() -> slice("abc", 1, 1)),
            "Допустимый пустой диапазон возвращает пустую строку.");
    }

    @Test @DisplayName("Открытый пример: отрицательная граница")
    void negativeBoundaryIsRejected() {
        assertThrows(IndexOutOfBoundsException.class, () -> slice("abc", -1, 1),
            "Для отрицательной границы нужен IndexOutOfBoundsException.");
    }
}
