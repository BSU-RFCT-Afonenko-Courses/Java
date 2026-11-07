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
    @DisplayName("Равенство по точному классу и имени, согласованный хеш")
    void valueEqualityExactClassAndName() {
        for (int i = 0; i < 3; i++) {
            Object a = TaskBinding.animal(i, "Луна"), b = TaskBinding.animal(i, "Луна"),
                   c = TaskBinding.animal(i, "Марс");
            assertEquals(a, b);
            assertEquals(b, a);
            assertEquals(a.hashCode(), b.hashCode());
            assertNotEquals(a, c);
            assertNotEquals(a, null);
            assertTrue(a.toString().contains("Луна"));
            assertNotEquals(a.toString(), c.toString());
        }
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (i != j) {
                    Object a = TaskBinding.animal(i, "X"), b = TaskBinding.animal(j, "X");
                    assertNotEquals(a, b);
                    assertNotEquals(a.toString(), b.toString());
                }
    }
}
