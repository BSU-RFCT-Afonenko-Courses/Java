import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnicodeSliceTest {
    private static Method sliceMethod() {
        Class<?> type = assertDoesNotThrow(() -> Class.forName("UnicodeSlice"),
            "Не найден класс UnicodeSlice. Сохраните имя класса из заготовки.");
        assertTrue(Modifier.isPublic(type.getModifiers()),
            "Класс UnicodeSlice должен быть public.");
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

    @Test @DisplayName("ASCII: правая граница не входит в диапазон")
    void asciiRange() {
        assertEquals("bc", assertDoesNotThrow(() -> slice("abc", 1, 3)),
            "Диапазон [1, 3) строки abc должен содержать bc.");
    }

    @Test @DisplayName("BMP: кириллические кодовые точки")
    void bmpRange() {
        assertEquals("АБ", assertDoesNotThrow(() -> slice("АБВ", 0, 2)),
            "Диапазон [0, 2) должен содержать первые две кодовые точки.");
    }

    @Test @DisplayName("Дополнительная кодовая точка остаётся целой")
    void supplementaryCodePoint() {
        assertEquals("😀", assertDoesNotThrow(() -> slice("A😀Б", 1, 2)),
            "Позиции считаются по кодовым точкам; суррогатную пару нельзя разделять.");
    }

    @Test @DisplayName("Две дополнительные кодовые точки")
    void twoSupplementaryCodePoints() {
        assertEquals("😀🚀", assertDoesNotThrow(() -> slice("😀🚀", 0, 2)),
            "Две дополнительные кодовые точки занимают две позиции.");
    }

    @Test @DisplayName("Позиция после дополнительной кодовой точки")
    void afterSupplementaryCodePoint() {
        assertEquals("Б", assertDoesNotThrow(() -> slice("A😀Б", 2, 3)),
            "После 😀 следующая кодовая точка имеет позицию 2.");
    }

    @Test @DisplayName("Полный диапазон сохраняет содержимое")
    void completeRange() {
        assertEquals("A😀Б", assertDoesNotThrow(() -> slice("A😀Б", 0, 3)),
            "Полный диапазон должен сохранять всю строку.");
    }

    @Test @DisplayName("Пустая строка: диапазон [0, 0)")
    void emptyInput() {
        assertEquals("", assertDoesNotThrow(() -> slice("", 0, 0)),
            "У пустой строки допустим диапазон [0, 0).");
    }

    @Test @DisplayName("Пустой диапазон внутри строки")
    void emptyRangeInside() {
        assertEquals("", assertDoesNotThrow(() -> slice("A😀Б", 1, 1)),
            "При равных допустимых границах результат пустой.");
    }

    @Test @DisplayName("Пустой диапазон на правой границе")
    void emptyRangeAtEnd() {
        assertEquals("", assertDoesNotThrow(() -> slice("A😀Б", 3, 3)),
            "Пустой диапазон допустим и после последней кодовой точки.");
    }

    @Test @DisplayName("Диапазон может разделить графемный кластер")
    void combiningMarkIsSeparateCodePoint() {
        assertEquals("e", assertDoesNotThrow(() -> slice("e\u0301", 0, 1)),
            "Единица задачи — кодовая точка, а не графемный кластер.");
    }

    @Test @DisplayName("null вызывает NullPointerException")
    void nullInput() {
        assertThrows(NullPointerException.class, () -> slice(null, 0, 0),
            "Для null нужен NullPointerException.");
    }

    @Test @DisplayName("Отрицательная левая граница недопустима")
    void negativeFrom() {
        assertThrows(IndexOutOfBoundsException.class, () -> slice("abc", -1, 1),
            "Для отрицательной границы нужен IndexOutOfBoundsException.");
    }

    @Test @DisplayName("Отрицательная правая граница недопустима")
    void negativeTo() {
        assertThrows(IndexOutOfBoundsException.class, () -> slice("abc", 0, -1),
            "Для отрицательной границы нужен IndexOutOfBoundsException.");
    }

    @Test @DisplayName("Перепутанные границы недопустимы")
    void reversedRange() {
        assertThrows(IndexOutOfBoundsException.class, () -> slice("abc", 2, 1),
            "При from > to нужен IndexOutOfBoundsException.");
    }

    @Test @DisplayName("Правая граница превышает число кодовых точек")
    void toBeyondCodePointCount() {
        assertThrows(IndexOutOfBoundsException.class, () -> slice("A😀Б", 0, 4),
            "В строке A😀Б три кодовые точки, хотя четыре единицы UTF-16.");
    }

    @Test @DisplayName("Пустой диапазон за концом строки недопустим")
    void emptyRangeBeyondEnd() {
        assertThrows(IndexOutOfBoundsException.class, () -> slice("A😀Б", 4, 4),
            "Равные границы тоже должны находиться внутри [0, число кодовых точек].");
    }

    @Test @DisplayName("У пустой строки нет диапазона [0, 1)")
    void beyondEmptyInput() {
        assertThrows(IndexOutOfBoundsException.class, () -> slice("", 0, 1),
            "Границы пустой строки могут быть только 0.");
    }
}
