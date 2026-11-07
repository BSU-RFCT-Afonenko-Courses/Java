import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContractTest {
    @Test
    @DisplayName("Грузовики хранят независимые характеристики и допускают нулевой груз")
    void twoTrucksOwnStateAndValidCargoZero() {
        Object a = TaskBinding.truck(0, 1, 3, 0), b = TaskBinding.truck(5, 4, 9, 8);
        TaskBinding.cargo(a, 7);
        assertEquals(7, TaskBinding.cargo(a));
        assertEquals(8, TaskBinding.cargo(b));
        assertArrayEquals(new int[] {0, 1, 3}, TaskBinding.state(a));
        assertEquals(1.0 / 3, TaskBinding.fuel(a, 1), 1e-6);
        assertThrows(IllegalArgumentException.class, () -> TaskBinding.truck(-1, 1, 1, 0));
        assertThrows(IllegalArgumentException.class, () -> TaskBinding.truck(0, 0, 1, 0));
        assertThrows(IllegalArgumentException.class, () -> TaskBinding.truck(0, 1, 0, 0));
    }
}
