import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StateTest {
    @Test
    @DisplayName("Отказы сохраняют характеристики грузовика, допустимые изменения работают")
    void allSetterRefusalsAndSuccess() {
        Object t = TaskBinding.truck(3, 7, 11, 0);
        for (int which = 0; which < 3; which++) {
            int k = which;
            int[] before = TaskBinding.state(t);
            assertThrows(IllegalArgumentException.class, () -> {
                if (k == 0)
                    TaskBinding.setPassengers(t, -1);
                else if (k == 1)
                    TaskBinding.setFuel(t, -2);
                else
                    TaskBinding.setMpg(t, 0);
            });
            assertArrayEquals(before, TaskBinding.state(t));
            assertEquals(0, TaskBinding.cargo(t));
        }
        TaskBinding.setPassengers(t, 0);
        TaskBinding.setFuel(t, 9);
        TaskBinding.setMpg(t, 4);
        assertArrayEquals(new int[] {0, 9, 4}, TaskBinding.state(t));
        assertEquals(36, TaskBinding.range(t));
        assertEquals(0, TaskBinding.fuel(t, 0), 1e-6);
        TaskBinding.cargo(t, 5);
        assertEquals(5, TaskBinding.cargo(t));
        assertThrows(IllegalArgumentException.class, () -> TaskBinding.truck(1, 1, 1, -1));
        assertThrows(IllegalArgumentException.class, () -> TaskBinding.vehicle(0, 1, 0));
    }
}
