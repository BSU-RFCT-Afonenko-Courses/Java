import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CharDomainTest {
    @Test
    @DisplayName("Допустимый char U+FFFF и зарезервированный ноль при отказе")
    void nonAsciiAndReservedReturn() {
        for (int kind = 0; kind < 3; kind++) {
            Object q = TaskBinding.queue(kind, 1);
            assertEquals("", ContractTest.capture(() -> {
                TaskBinding.put(q, '\uffff');
                assertEquals('\uffff', TaskBinding.get(q));
            }));
            assertEquals("Очередь пуста\n",
                         ContractTest.capture(() -> assertEquals('\0', TaskBinding.get(q))));
        }
    }
}
