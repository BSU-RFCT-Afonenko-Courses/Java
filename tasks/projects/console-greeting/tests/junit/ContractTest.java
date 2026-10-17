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
        var result = coursesupport.JavaProcess.run(Main.class, input, args);
        System.err.print(result.stderr());
        return result.stdout();
    }
}
