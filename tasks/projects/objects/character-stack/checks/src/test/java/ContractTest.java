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
    @DisplayName("Пустой стек и повторное использование для разных char")
    void reuseAndEveryCharDomain() {
        Object q = TaskBinding.create(1);
        assertEquals("Стек пуст\n", capture(() -> assertEquals('\0', TaskBinding.pop(q))));
        assertEquals("", capture(() -> {
            TaskBinding.push(q, '\uffff');
            assertEquals('\uffff', TaskBinding.pop(q));
            TaskBinding.push(q, '\n');
            assertEquals('\n', TaskBinding.pop(q));
        }));
    }
}
