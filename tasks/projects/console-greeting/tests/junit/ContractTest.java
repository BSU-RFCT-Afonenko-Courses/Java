import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test
    void reportsMissingName() throws Exception {
        assertEquals("Укажите имя.\n", run(""));
    }

    @Test
    void usesFirstArgument() throws Exception {
        assertEquals("Здравствуйте, Ли 👋!\n", run("", "Ли 👋", "ignored"));
    }

    @Test
    void preservesNameSpelling() throws Exception {
        assertEquals("Здравствуйте, Ada-Lovelace!\n", run("", "Ada-Lovelace"));
    }

    private static String run(String input, String... args) throws Exception {
        var previousIn = System.in;
        var previousOut = System.out;
        var bytes = new java.io.ByteArrayOutputStream();
        try (var output = new java.io.PrintStream(bytes, true, java.nio.charset.StandardCharsets.UTF_8)) {
            System.setIn(new java.io.ByteArrayInputStream(input.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
            System.setOut(output);
            Main.main(args);
            output.flush();
            return bytes.toString(java.nio.charset.StandardCharsets.UTF_8);
        } finally {
            System.setIn(previousIn);
            System.setOut(previousOut);
        }
    }
}
