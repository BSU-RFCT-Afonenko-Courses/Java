import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContractTest {
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
    @DisplayName("Конструктор и setters соблюдают границы, отказ сохраняет состояние")
    void constructorBoundariesAndSetterState() {
        assertThrows(IllegalArgumentException.class, () -> TaskBinding.vehicle(-1, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> TaskBinding.vehicle(0, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> TaskBinding.vehicle(0, 1, 0));
        Object v = TaskBinding.vehicle(0, 1, 3);
        assertEquals(0, TaskBinding.fuel(v, 0));
        assertEquals(1.0 / 3, TaskBinding.fuel(v, 1), 1e-6);
        for (int i = 0; i < 3; i++) {
            final int field = i;
            int[] before = TaskBinding.state(v);
            assertThrows(IllegalArgumentException.class, () -> TaskBinding.set(v, field, -1));
            assertArrayEquals(before, TaskBinding.state(v));
        }
        TaskBinding.set(v, 0, 7);
        TaskBinding.set(v, 1, 4);
        TaskBinding.set(v, 2, 9);
        assertArrayEquals(new int[] {7, 4, 9}, TaskBinding.state(v));
        assertEquals(36, TaskBinding.range(v));
    }
    @Test
    @DisplayName("Изменение второго транспортного средства сохраняет первое")
    void separateVehiclesKeepOwnState() {
        Object first = TaskBinding.vehicle(2, 10, 25), second = TaskBinding.vehicle(1, 3, 5);
        assertArrayEquals(new int[] {2, 10, 25}, TaskBinding.state(first));
        TaskBinding.set(second, 0, 4);
        TaskBinding.set(second, 1, 7);
        assertArrayEquals(new int[] {2, 10, 25}, TaskBinding.state(first));
        assertEquals(250, TaskBinding.range(first));
    }
}
