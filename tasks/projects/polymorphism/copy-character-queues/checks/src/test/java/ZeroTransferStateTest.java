import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ZeroTransferStateTest {
    @Test
    @DisplayName("Нулевой перенос не восстанавливает исчерпанное линейное место")
    void zeroDoesNotReclaimLinearPosition() {
        Object source = TaskBinding.queue(1, 1), dest = TaskBinding.queue(0, 1);
        TaskBinding.put(dest, 'Q');
        assertEquals('Q', TaskBinding.get(dest));
        assertEquals("", ContractTest.capture(() -> TaskBinding.transfer(source, dest, 0)));
        assertEquals(
            "Очередь переполнена\n", ContractTest.capture(() -> TaskBinding.put(dest, 'X')));
        assertEquals("Очередь пуста\n",
                     ContractTest.capture(() -> assertEquals('\0', TaskBinding.get(dest))));
    }
}
