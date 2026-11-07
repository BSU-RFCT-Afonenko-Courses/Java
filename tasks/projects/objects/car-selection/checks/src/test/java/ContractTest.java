import java.lang.Throwable;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContractTest {
    @java.lang.FunctionalInterface
    private interface Selection {
        Object[] get() throws Throwable;
    }
    static String capture(Runnable action) {
        PrintStream previous = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try
            (PrintStream out = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setOut(out);
            action.run();
        } finally {
            System.setOut(previous);
        }
        return bytes.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }
    @Test
    @DisplayName("Отбор сохраняет значения, порядок, повторения и исходные данные")
    void exactOrderDuplicatesAndState() throws Throwable {
        Object a = TaskBinding.car(1, "A", "M", 2000, 10),
               b = TaskBinding.car(2, "A", "m", 2001, 11);
        Object[] in = TaskBinding.array(a, b, a);
        String sa = TaskBinding.state(a), sb = TaskBinding.state(b);
        assertValues(new Object[] {a, a}, select(in, () -> TaskBinding.older(in, "M", 2025, 24)));
        assertValues(new Object[] {a, b, a}, select(in, () -> TaskBinding.make(in, "A")));
        assertValues(new Object[0], select(in, () -> TaskBinding.make(in, "a")));
        assertValues(new Object[] {b}, select(in, () -> TaskBinding.dearer(in, 2001, 10)));
        assertValues(new Object[0], TaskBinding.dearer(TaskBinding.array(), 1, 0));
        assertValues(new Object[] {a, b, a}, in);
        assertEquals(sa, TaskBinding.state(a));
        assertEquals(sb, TaskBinding.state(b));
    }
    @Test
    @DisplayName("Второй конструктор и изменение всех свойств автомобиля")
    void twoConstructorsAndProperties() throws Throwable {
        Object c = TaskBinding.shortCar();
        assertFalse(TaskBinding.text(c).isBlank());
        TaskBinding.edit(c);
        assertEquals("7|B|N|2025|blue|3.0|Y", TaskBinding.state(c));
    }
    private static List<List<?>> values(Object[] input, boolean snapshot) throws Throwable {
        List<List<?>> rows = new ArrayList<>(input.length);
        for (Object item : input) {
            List<?> row = TaskBinding.value(item);
            rows.add(snapshot ? new ArrayList<>(row) : row);
        }
        return java.util.Collections.unmodifiableList(rows);
    }
    static void assertValues(Object[] expected, Object[] actual) throws Throwable {
        assertNotNull(actual, "подборка");
        assertEquals(values(expected, false),
                     values(actual, false),
                     "значения, порядок и кратность подборки; identity результата свободна");
    }
    static Object[] select(Object[] input, Selection action) throws Throwable {
        Object[] before = input.clone();
        // Копируем каждую строку свойств до вызова отбора: исходный список может быть живым.
        var states = values(before, true);
        Object[] result = action.get();
        assertEquals(before.length, input.length);
        for (int i = 0; i < before.length; i++)
            assertSame(before[i], input[i],
                       "селектор изменил ячейку переданного typed массива, index=" + i);
        assertEquals(states, values(input, false),
                     "селектор изменил поля исходных объектов");
        return result;
    }
    @Test
    @DisplayName("Возраст вычисляется по переданному году, граница ноль исключается")
    void suppliedYearAndZeroAge() throws Throwable {
        Object a = TaskBinding.car(8, "Q", "Z", 2000, 0);
        Object[] input = TaskBinding.array(a);
        assertValues(new Object[0], select(input, () -> TaskBinding.older(input, "Z", 2000, 0)));
        assertValues(new Object[] {a}, select(input, () -> TaskBinding.older(input, "Z", 2001, 0)));
    }
}
