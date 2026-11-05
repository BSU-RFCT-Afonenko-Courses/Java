import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContractTest {
    @Test
    @DisplayName("Новый сброс очищает очередь, старый отказ сохраняет символы")
    void fullResetRepeatedRefillAndOldState() {
        Object q = TaskBinding.newQueue();
        assertEquals("", PublicOutput.capture(() -> {
            TaskBinding.put(q, 'Ж');
            TaskBinding.put(q, '中');
        }));
        TaskBinding.reset(q);
        char[] result = new char[1];
        assertEquals("Очередь пуста\n",
                     PublicOutput.capture(() -> result[0] = TaskBinding.get(q)).replace("\r\n", "\n"));
        assertEquals(0, result[0]);
        assertEquals("", PublicOutput.capture(() -> {
            TaskBinding.put(q, 'X');
            TaskBinding.put(q, 'Y');
            assertEquals('X', TaskBinding.get(q));
            assertEquals('Y', TaskBinding.get(q));
        }));
        TaskBinding.reset(q);
        Object old = TaskBinding.oldQueue();
        TaskBinding.put(old, 'Ж');
        TaskBinding.put(old, '中');
        assertThrows(UnsupportedOperationException.class, () -> TaskBinding.reset(old));
        assertEquals('Ж', TaskBinding.get(old));
        assertEquals('中', TaskBinding.get(old));
    }
}
