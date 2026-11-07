import java.lang.Throwable;
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
    @DisplayName("Самопередача и обмен крайних значений int")
    void selfAndExtremes() throws Throwable {
        Object x = TaskBinding.create(-2147483648);
        TaskBinding.swap(x, x);
        assertEquals(-2147483648, TaskBinding.value(x));
        Object y = TaskBinding.create(2147483647);
        TaskBinding.swap(x, y);
        assertEquals(2147483647, TaskBinding.value(x));
        assertEquals(-2147483648, TaskBinding.value(y));
    }
}
