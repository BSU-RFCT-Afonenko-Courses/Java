import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CharactersTest {
    @Test
    @DisplayName("Рост очереди сохраняет допустимые char и порядок FIFO")
    void allAllowedCharAndRepeatedGrowth() {
        Object q = TaskBinding.queue(2);
        char[] data = {'Ж', '\uFFFF', '\uD800', '\n', '\t', 1, '中'};
        String printed = PublicOutput.capture(() -> {
            for (char c : data) TaskBinding.put(q, c);
            for (char c : data) assertEquals(c, TaskBinding.get(q));
        });
        assertEquals("", printed);
    }
}
