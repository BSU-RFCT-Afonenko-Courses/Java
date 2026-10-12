import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test
    void preservesUtf8AndEmoji() throws Exception {
        assertEquals("aБяZ 👋🙂\n2\n", run("AБяz 👋🙂."));
    }

    @Test
    void stopsAtFirstDot() throws Exception {
        assertEquals("\n0\n", run(".Ab"));
    }

    @Test
    void eofAndOtherCharacters() throws Exception {
        assertEquals("aZ09\t_\n!?\n2\n", run("Az09\t_\n!?"));
    }

    @Test
    void emptyInput() throws Exception {
        assertEquals("\n0\n", run(""));
    }

    @Test
    void countsEveryAsciiLetter() throws Exception {
        assertEquals("azAZ\n4\n", run("AZaz."));
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
